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
import googleAnalytics.bean.dataSet2.ConvRateAvgValue;
import googleAnalytics.utility.Utility;

@Service("convRateAvgValueService")
public class ConvRateAvgValueServiceImpl implements DataSetService {
	static int count = 0;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		
		Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
		Metric transactions = new Metric().setExpression("ga:transactions").setAlias("transactions");
		Metric transactionRevenue = new Metric().setExpression("ga:transactionRevenue").setAlias("transactionRevenue");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:date"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(Arrays.asList(sessions, transactions, transactionRevenue))
				.setIncludeEmptyRows(true).setPageSize(5000);
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

			if (rows != null) {
				for (ReportRow row : rows) {

					List<String> dimensions = row.getDimensions();
					List<DateRangeValues> metrics = row.getMetrics();
					String date = dimensions.get(1).substring(0, 4) + "-" + dimensions.get(1).substring(4, 6) + "-"
							+ dimensions.get(1).substring(6, 8);
					if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
						continue;
					} else {
						count++;
						flag = 1;
						ConvRateAvgValue bean = new ConvRateAvgValue();
						GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
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
						bean.setDate(date);
						bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
						bean.setYear(Integer.parseInt(dimensions.get(0)));
						bean.setSessions(metrics.get(0).getValues().get(0));
						bean.setTransactions(metrics.get(0).getValues().get(1));
						bean.setTransactionRevenue(metrics.get(0).getValues().get(2));
						bean.setClientStamp(client_stamp);
						bean.setEndDate(String.valueOf(format.format(yesterday.getTime())));
						writeDataToCassandra.writeConvRateAvgValueData(bean);
					}
				}
			}

		}
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return ConvRateAvgValueServiceImpl.count;
	}

}
