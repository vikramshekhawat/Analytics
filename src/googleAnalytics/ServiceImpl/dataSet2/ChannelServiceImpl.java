package googleAnalytics.ServiceImpl.dataSet2;

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
import googleAnalytics.bean.dataSet1.Channel;
import googleAnalytics.utility.Utility;
//Monthly Data
@Service("channelService")
public class ChannelServiceImpl implements DataSetService {
	static int count =0;
	Logger logger;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		

		Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");

		List<Dimension> dimensionsList = new ArrayList<Dimension>();

		dimensionsList.add(new Dimension().setName("ga:channelGrouping"));
		dimensionsList.add(new Dimension().setName("ga:source"));
		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(Arrays.asList(sessions)).setIncludeEmptyRows(true).setPageSize(10000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String clientStamp) {
		int flag = 0;
		count =0;
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		Calendar thirtyDaysAgoFromYesterday = Calendar.getInstance();
		thirtyDaysAgoFromYesterday.add(Calendar.DATE, -31);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {

			for (Report report : reports) {
				List<ReportRow> rows = report.getData().getRows();

				for (ReportRow row : rows) {

					List<String> dimensions = row.getDimensions();
					List<DateRangeValues> metrics = row.getMetrics();

					if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
						continue;
					} else {
						
						flag = 1;
						Channel bean = new Channel();
						GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
						String date = dimensions.get(3).substring(0, 4) + "-" + dimensions.get(3).substring(4, 6) + "-"
								+ dimensions.get(3).substring(6, 8);
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
						bean.setStartDate(String.valueOf(format.format(thirtyDaysAgoFromYesterday.getTime())));
						bean.setChannelGrouping(dimensions.get(0));
						bean.setSource(dimensions.get(1));
						bean.setYear(Integer.parseInt(dimensions.get(2)));
						bean.setSessions(metrics.get(0).getValues().get(0));
						bean.setClient_stamp(clientStamp);
						bean.setEndDate(String.valueOf(format.format(yesterday.getTime())));
						try{

						writeDataToCassandra.writeChannelInformationToCassandra(bean);
						count++;
						}
						catch(Exception e){
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
		// TODO Auto-generated method stub
		return ChannelServiceImpl.count;
	}

}
