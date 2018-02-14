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
import com.google.api.services.analyticsreporting.v4.model.Segment;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.bean.dataSet2.Organic30;
import googleAnalytics.utility.Utility;

@Service("organic30Service")
public class Organic30ServiceImpl implements DataSetService {
	static int count =0;
	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {

		GetReportsRequest getReport = null;

		
		Metric transationRevenue = new Metric().setExpression("ga:transactionRevenue");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:segment"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:year"));

		Segment organicSegment = Utility.buildSimpleSegment("Medium", "ga:medium", "EXACT", "organic");

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(Arrays.asList(transationRevenue))
				.setSegments(Arrays.asList(organicSegment)).setIncludeEmptyRows(true).setPageSize(5000);

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
				GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

				if (Double.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;

				Organic30 organicdata = new Organic30();
				String date = dimensions.get(1).substring(0, 4) + "-" + dimensions.get(1).substring(4, 6) + "-"
						+ dimensions.get(1).substring(6, 8);
				organicdata.setDate(date);
				Date yearlydate;
				try {
					yearlydate = format.parse(date);
					Calendar cal=Calendar.getInstance();
					 cal.setTime(yearlydate);
					//bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6, 8)));
					 organicdata.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			
				organicdata.setYear(Integer.parseInt(dimensions.get(2)));
				//organicdata.setDayOfYear(yesterday.get(Calendar.DAY_OF_MONTH));
					organicdata.setClientStamp(client_stamp);
					//organicdata.setDate();
					organicdata.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
					//organicdata.setYear(Integer.parseInt(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())).substring(0,4)));
					organicdata.setTransactionRevenue(metrics.get(0).getValues().get(0));
					organicdata.setEndDate(String.valueOf(format.format(yesterday.getTime())));
					writeDataToCassandra.writeOragnic30(organicdata);
				}
			}
		}

	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return Organic30ServiceImpl.count;
	}

}
