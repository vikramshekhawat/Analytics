package googleAnalytics.ServiceImpl.dataSet2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet1.TrafficSummary;
import googleAnalytics.utility.Utility;

//Monthly Data
@Service("trafficSummaryService")
public class TrafficSummaryServiceImpl implements DataSetService {
	static int count = 0;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		List<Metric> metricList = new ArrayList<>();
		Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
		Metric newUsers = new Metric().setExpression("ga:newUsers").setAlias("newUsers");
		Metric pageviewsPerSession = new Metric().setExpression("ga:pageviewsPerSession")
				.setAlias("pageviewsPerSession");

		metricList = new ArrayList<>();
		metricList.add(sessions);
		metricList.add(newUsers);
		metricList.add(pageviewsPerSession);

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:channelGrouping"));
		dimensionsList.add(new Dimension().setName("ga:source"));
		dimensionsList.add(new Dimension().setName("ga:landingPagePath"));
		dimensionsList.add(new Dimension().setName("ga:browser"));
		dimensionsList.add(new Dimension().setName("ga:operatingSystem"));
		dimensionsList.add(new Dimension().setName("ga:deviceCategory"));
		dimensionsList.add(new Dimension().setName("ga:year"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(metricList).setIncludeEmptyRows(true).setPageSize(5000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String client_stamp) {
		int flag = 0;
		count = 0;
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
					TrafficSummary bean = new TrafficSummary();
					GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

					bean.setDate(String.valueOf(format.format(yesterday.getTime())));
					bean.setDayOfYear(yesterday.get(Calendar.DAY_OF_MONTH));
					bean.setChannelGrouping(dimensions.get(0));
					bean.setSource(dimensions.get(1));
					bean.setLandingPath(dimensions.get(2));
					bean.setBrowser(dimensions.get(3));
					bean.setOperatingSystem(dimensions.get(4));
					bean.setDeviceCategory(dimensions.get(5));
					bean.setYear(Integer.parseInt(
							String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())).substring(0, 4)));
					bean.setSessions(metrics.get(0).getValues().get(0));
					bean.setNewUsers(metrics.get(0).getValues().get(1));
					bean.setPageviewsPerSession(metrics.get(0).getValues().get(2));
					bean.setClient_stamp(client_stamp);
					bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));

					writeDataToCassandra.writeTrafficSummaryData(bean);
				}
			}
		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return TrafficSummaryServiceImpl.count;
	}

}
