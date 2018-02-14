package googleAnalytics.ServiceImpl.dataSet1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.cassandra.support.exception.CassandraInvalidQueryException;
import org.springframework.stereotype.Service;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

import ch.qos.logback.classic.Logger;
import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet1.Currency;
import googleAnalytics.utility.Utility;

@Service("currencyService")
public class CurrencyServiceImpl implements DataSetService {

	static int count = 0;
	Logger logger;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		Metric revenue = new Metric().setExpression("ga:revenuePerUser").setAlias("revenue");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:currencyCode"));
		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:date"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.yearlyDateRange())
				.setDimensions(dimensionsList).setMetrics(Arrays.asList(revenue)).setIncludeEmptyRows(false)
				.setPageSize(10000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String clientStamp) {
		int flag = 0;
		count = 0;
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {

			for (Report report : reports) {
				List<ReportRow> rows = report.getData().getRows();

				for (ReportRow row : rows) {

					List<String> dimensions = row.getDimensions();
					List<DateRangeValues> metrics = row.getMetrics();

					if ((int) Double.parseDouble(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
						continue;
					} else {

						flag = 1;
						Currency bean = new Currency();
						GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
						String date = dimensions.get(2).substring(0, 4) + "-" + dimensions.get(2).substring(4, 6) + "-"
								+ dimensions.get(2).substring(6, 8);
						Date yearlydate;
						try {
							yearlydate = format.parse(date);
							Calendar cal = Calendar.getInstance();
							cal.setTime(yearlydate);
							bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
							bean.setYear(Integer.parseInt(dimensions.get(1)));
							bean.setTransactionRevenue(String.valueOf(metrics.get(0).getValues().get(0)));
							bean.setClientStamp(clientStamp);
							if (dimensions.get(0).equals("(not set)")) {
								bean.setCurrencyCode("USD");
							} else {
								bean.setCurrencyCode(dimensions.get(0));

							}
							bean.setDate(date);
						} catch (ParseException e) {

							e.printStackTrace();
						}
						try {

							writeDataToCassandra.writeCurrencyData(bean);
							count++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (CassandraInvalidQueryException e) {
			String str = "";
			logger.error(str + " is empty");
		}

	}

	@Override
	public int countTableRows() {

		return CurrencyServiceImpl.count;
	}
}
