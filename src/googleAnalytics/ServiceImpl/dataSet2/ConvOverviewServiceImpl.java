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
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet2.ConvOverview;
import googleAnalytics.utility.Utility;

//Monthly Data
@Service("convOverviewService")
public class ConvOverviewServiceImpl implements DataSetService {
	static int count = 0;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		
		List<Metric> metricList = new ArrayList<Metric>();
		metricList.add(new Metric().setExpression("ga:goalCompletionsAll").setAlias("goalCompletionsAll"));
		metricList.add(new Metric().setExpression("ga:goal1Completions").setAlias("goal1Completions"));
		metricList.add(new Metric().setExpression("ga:goal2Completions").setAlias("goal2Completions"));
		metricList.add(new Metric().setExpression("ga:goal3Completions").setAlias("goal3Completions"));
		metricList.add(new Metric().setExpression("ga:goal4Completions").setAlias("goal4Completions"));
		metricList.add(new Metric().setExpression("ga:goal5Completions").setAlias("goal5Completions"));

		// Metric goalCompletions = new
		// Metric().setExpression("ga:goalCompletionsAll").setAlias("goalCompletionsAll");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:hour"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:goalCompletionLocation"));
		dimensionsList.add(new Dimension().setName("ga:year"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics((metricList)).setIncludeEmptyRows(true).setPageSize(10000);

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

		count = 0;
		// Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		for (Report report : reports) {
			List<ReportRow> rows = report.getData().getRows();

			if (rows != null) {
				for (ReportRow row : rows) {
					try {

						List<String> dimensions = row.getDimensions();
						List<DateRangeValues> metrics = row.getMetrics();

						if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
							continue;
						} else {
							count++;
							flag = 1;
							ConvOverview bean = new ConvOverview();
							GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

							String date = dimensions.get(1).substring(0, 4) + "-" + dimensions.get(1).substring(4, 6)
									+ "-" + dimensions.get(1).substring(6, 8);
							bean.setDate(date);
							bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
							bean.setEndDate(String.valueOf(format.format(yesterday.getTime())));

							Date yearlydate;

							yearlydate = format.parse(date);
							Calendar cal = Calendar.getInstance();
							cal.setTime(yearlydate);
							// bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6,
							// 8)));
							bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));

							bean.setHour(dimensions.get(0));
							bean.setGoalCompletionLocation(dimensions.get(2));
							bean.setYear(Integer.parseInt(dimensions.get(3)));

							bean.setGoalCompletionsAll(metrics.get(0).getValues().get(0));
							bean.setClientStamp(client_stamp);
							bean.setGoalCompletion1(metrics.get(0).getValues().get(1));
							bean.setGoalCompletion2(metrics.get(0).getValues().get(2));
							bean.setGoalCompletion3(metrics.get(0).getValues().get(3));
							bean.setGoalCompletion4(metrics.get(0).getValues().get(4));
							bean.setGoalCompletion5(metrics.get(0).getValues().get(5));
							writeDataToCassandra.writeConvOverview(bean);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return ConvOverviewServiceImpl.count;
	}

}
