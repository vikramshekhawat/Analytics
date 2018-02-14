package googleAnalytics.ServiceImpl.dataSet1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet1.SessionInfo;
import googleAnalytics.utility.Utility;

//Yearly Data
@Service("sessionService")
public class SessionServiceImpl implements DataSetService {
	static int count = 0;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		List<Metric> metricList = new ArrayList<>();
		Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
		Metric bounceRate = new Metric().setExpression("ga:bounceRate").setAlias("bounceRate");
		Metric avgSessionDuration = new Metric().setExpression("ga:avgSessionDuration").setAlias("avgSessionDuration");
		Metric transactions = new Metric().setExpression("ga:transactions").setAlias("transactions");
		Metric transactionRevenue = new Metric().setExpression("ga:transactionRevenue").setAlias("transactionRevenue");

		metricList = new ArrayList<>();
		metricList.add(sessions);
		metricList.add(bounceRate);
		metricList.add(avgSessionDuration);
		metricList.add(transactions);
		metricList.add(transactionRevenue);

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:year"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.yearlyDateRange())
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
					SessionInfo bean = new SessionInfo();
					GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

					String date = dimensions.get(0).substring(0, 4) + "-" + dimensions.get(0).substring(4, 6) + "-"
							+ dimensions.get(0).substring(6, 8);
					bean.setDate(date);
					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(yearlydate);

						bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
					} catch (ParseException e) {

						e.printStackTrace();
					}

					bean.setYear(Integer.parseInt(dimensions.get(1)));
					bean.setSessions(metrics.get(0).getValues().get(0));
					bean.setBounceRate(metrics.get(0).getValues().get(1));
					bean.setAvgSessionDuration(metrics.get(0).getValues().get(2));
					bean.setTransactions(metrics.get(0).getValues().get(3));
					bean.setTransactionRevenue(metrics.get(0).getValues().get(4));
					bean.setClient_stamp(client_stamp);

					writeDataToCassandra.writeSessionInformationToCassandra(bean);
				}
			}
		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return SessionServiceImpl.count;
	}

}
