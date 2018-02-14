package googleAnalytics.ServiceImpl.dataSet1;

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
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import com.google.api.services.analyticsreporting.v4.model.Segment;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet1.ConversionsPPC;
import googleAnalytics.utility.Utility;

//Yearly Data
@Service("conversionsPPCService")
public class ConversionsPPCServiceImpl implements DataSetService {
	static int count = 0;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		Metric goalCompletions = new Metric().setExpression("ga:goalCompletionsAll").setAlias("goalCompletionsAll");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:segment"));

		String[] values = { "cpc", "google/cpc" };
		Segment mediumSegment = Utility.buildSimpleSegment("Sessions with Medium", "ga:medium", "IN_LIST", values);

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.yearlyDateRange())
				.setDimensions(dimensionsList).setMetrics(Arrays.asList(goalCompletions))
				.setSegments(Arrays.asList(mediumSegment)).setIncludeEmptyRows(true).setPageSize(5000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String client_stamp) {
		int flag = 0;
		count = 0;
		for (Report report : reports) {
			List<ReportRow> rows = report.getData().getRows();

			for (ReportRow row : rows) {

				List<String> dimensions = row.getDimensions();
				List<DateRangeValues> metrics = row.getMetrics();

				if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;

					ConversionsPPC bean = new ConversionsPPC();
					GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
					flag = 1;

					String date = dimensions.get(1).substring(0, 4) + "-" + dimensions.get(1).substring(4, 6) + "-"
							+ dimensions.get(1).substring(6, 8);
					bean.setDate(date);
					// Date yearlyDate=Date.valueOf(date);
					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(yearlydate);
						// bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6,
						// 8)));
						bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bean.setYear(Integer.parseInt(dimensions.get(0)));
					bean.setGoalCompletionsAll(metrics.get(0).getValues().get(0));
					bean.setClientStamp(client_stamp);

					writeDataToCassandra.writeConversionsPPC(bean);
				}
			}
		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return ConversionsPPCServiceImpl.count;
	}

}
