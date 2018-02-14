package googleAnalytics.ServiceImpl.dataSet2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import com.google.api.services.analyticsreporting.v4.model.Segment;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet2.ConvPPCa30;
import googleAnalytics.utility.Utility;
//Monthly Data
@Service("convPPCa30Service")
public class ConvPPCa30ServiceImpl implements DataSetService {
	static int count =0;
	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		

		Metric totalEvents = new Metric().setExpression("ga:totalEvents").setAlias("totalEvents");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:hour"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:eventCategory"));
		dimensionsList.add(new Dimension().setName("ga:segment"));

		DimensionFilter eventCategoryFilter = new DimensionFilter().setDimensionName("ga:eventCategory")
				.setOperator("EXACT").setExpressions(Arrays.asList("registration"));

		DimensionFilter eventLabelFilter = new DimensionFilter().setDimensionName("ga:eventLabel").setOperator("EXACT")
				.setExpressions(Arrays.asList("sign_up"));

		DimensionFilterClause eventCategoryFilterClause = new DimensionFilterClause()
				.setFilters(Arrays.asList(eventCategoryFilter));
		DimensionFilterClause eventLabelFilterClause = new DimensionFilterClause()
				.setFilters(Arrays.asList(eventLabelFilter));

		String[] values = { "cpc", "google/cpc" };
		Segment mediumSegment = Utility.buildSimpleSegment("Sessions with Medium", "ga:medium", "IN_LIST", values);

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setSegments(Arrays.asList(mediumSegment))
				.setDimensionFilterClauses(Arrays.asList(eventCategoryFilterClause, eventLabelFilterClause))
				.setMetrics(Arrays.asList(totalEvents)).setIncludeEmptyRows(true).setPageSize(5000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String client_stamp) {
		int flag = 0;
		count =0;
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		Calendar thirtyDaysAgoFromYesterday = Calendar.getInstance();
		thirtyDaysAgoFromYesterday.add(Calendar.DATE, -31);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		for (Report report : reports) {
			List<ReportRow> rows = report.getData().getRows();

			if (rows != null) {
				for (ReportRow row : rows) {

					List<String> dimensions = row.getDimensions();
					List<DateRangeValues> metrics = row.getMetrics();

					if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
						continue;
					} else {
						count++;
						flag = 1;
						ConvPPCa30 bean = new ConvPPCa30();
						GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

						String date = dimensions.get(1).substring(0, 4) + "-" + dimensions.get(1).substring(4, 6) + "-"
								+ dimensions.get(1).substring(6, 8);
						bean.setDate(date);
						Date yearlydate;
						try {
							yearlydate = format.parse(date);
							Calendar cal=Calendar.getInstance();
							 cal.setTime(yearlydate);
							//bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6, 8)));
							 bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						bean.setHour(dimensions.get(0));
						bean.setEventCategory(dimensions.get(2));
						bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
						bean.setYear(Integer.parseInt(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())).substring(0,4)));
						bean.setTotalEvents(metrics.get(0).getValues().get(0));
						bean.setClientStamp(client_stamp);
                         bean.setEndDate(String.valueOf(format.format(yesterday.getTime())));
						writeDataToCassandra.writeConvPPCa30Data(bean);
					}
				}
			}

		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return ConvPPCa30ServiceImpl.count;
	}

}
