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
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.OrderBy;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet1.Top20Pages;
import googleAnalytics.utility.Utility;
//Monthly Data
@Service("top20PagesService")
public class Top20PagesServiceImpl implements DataSetService {
	static int count =0;
	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;
		
		List<Metric> metricList = new ArrayList<>();
		Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
		Metric bounceRate = new Metric().setExpression("ga:bounceRate").setAlias("bounceRate");
		Metric avgSessionDuration = new Metric().setExpression("ga:avgSessionDuration").setAlias("avgSessionDuration");
		Metric pageviewsPerSession = new Metric().setExpression("ga:pageviewsPerSession")
				.setAlias("pageviewsPerSession");

		OrderBy orderBy = new OrderBy().setFieldName("ga:sessions").setSortOrder("DESCENDING");
		int pageSize = 20;

		metricList = new ArrayList<>();
		metricList.add(sessions);
		metricList.add(bounceRate);
		metricList.add(avgSessionDuration);
		metricList.add(pageviewsPerSession);

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:landingPagePath"));
		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:date"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(metricList).setOrderBys(Arrays.asList(orderBy))
				.setPageSize(pageSize).setIncludeEmptyRows(true).setPageSize(5000);
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

			for (ReportRow row : rows) {

				List<String> dimensions = row.getDimensions();
				List<DateRangeValues> metrics = row.getMetrics();

				if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;
					Top20Pages bean = new Top20Pages();
					GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
					String date = dimensions.get(2).substring(0, 4) + "-" + dimensions.get(2).substring(4, 6) + "-"
							+ dimensions.get(2).substring(6, 8);
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
					bean.setDate(date);
					bean.setLandingPath(dimensions.get(0));
					bean.setYear(Integer.parseInt(dimensions.get(1)));
					bean.setSessions(metrics.get(0).getValues().get(0));
					bean.setBounceRate(metrics.get(0).getValues().get(1));
					bean.setAvgSessionDuration(metrics.get(0).getValues().get(2));
					bean.setPageviewsPerSession(metrics.get(0).getValues().get(3));
					bean.setClient_stamp(client_stamp);
					bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
					bean.setEndDate(String.valueOf(format.format(yesterday.getTime())));
					writeDataToCassandra.writeTop20PagesInformationToCassandra(bean);
				}
			}
		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return Top20PagesServiceImpl.count;
	}

}
