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
import googleAnalytics.bean.dataSet2.ConversionPPC;
import googleAnalytics.utility.Utility;

@Service("ppcConversionService")
public class PpcConversionServiceImpl implements DataSetService {
	static int count =0;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		

		Metric metric = new Metric().setExpression("ga:goalConversionRateAll");

		List<Dimension> dimensionList = new ArrayList<Dimension>();
		dimensionList.add(new Dimension().setName("ga:date"));
		dimensionList.add(new Dimension().setName("ga:year"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.yearlyDateRange())
				.setDimensions(dimensionList).setMetrics(Arrays.asList(metric)).setIncludeEmptyRows(true).setPageSize(5000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		return service.reports().batchGet(getReport).execute().getReports();
	}

	@Override
	public void saveData(List<Report> reports, String client_stamp) {
		count =0;
		int flag = 0;
		for (Report report : reports) {
			List<ReportRow> rows = report.getData().getRows();

			for (ReportRow row : rows) {

				List<String> dimensions = row.getDimensions();
				List<DateRangeValues> metrics = row.getMetrics();
				
				if (Double.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;
					ConversionPPC conversionBean = new ConversionPPC();
					GoogleAnalyticsCassandraCrudImpl writDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

					String date = dimensions.get(0).substring(0, 4) + "-" + dimensions.get(0).substring(4, 6) + "-"
							+ dimensions.get(0).substring(6, 8);
					conversionBean.setDate(date);
					
					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal=Calendar.getInstance();
						 cal.setTime(yearlydate);
						//bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6, 8)));
						 conversionBean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
					
					conversionBean.setClient_stamp(client_stamp);
					conversionBean.setYear(Integer.parseInt(dimensions.get(1)));
					conversionBean.setGoal_conversion_rate_all(metrics.get(0).getValues().get(0));

					writDataToCassandra.writeConversionbPPCData(conversionBean);
				}
			}

		}

	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return PpcConversionServiceImpl.count;
	}
}
