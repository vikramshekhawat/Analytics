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
import googleAnalytics.bean.dataSet1.PPCAllYear;
import googleAnalytics.utility.Utility;

//Yearly Data
@Service("ppcAllYearService")
public class PpcAllYearServiceImpl implements DataSetService {
	static int count = 0;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		GetReportsRequest getReport = null;

		List<Metric> metricList = new ArrayList<Metric>();

		Metric adCost = new Metric().setExpression("ga:adCost");
		Metric impressions = new Metric().setExpression("ga:impressions");
		Metric adClicks = new Metric().setExpression("ga:adClicks");
		Metric CTR = new Metric().setExpression("ga:CTR");
		Metric CPC = new Metric().setExpression("ga:CPC");
		Metric bounceRate = new Metric().setExpression("ga:bounceRate");
		Metric averageSessionDuration = new Metric().setExpression("ga:avgSessionDuration");

		metricList.add(adCost);
		metricList.add(impressions);
		metricList.add(adClicks);
		metricList.add(CTR);
		metricList.add(CPC);
		metricList.add(bounceRate);
		metricList.add(averageSessionDuration);

		List<Dimension> dimensionList = new ArrayList<Dimension>();
		dimensionList.add(new Dimension().setName("ga:date"));
		dimensionList.add(new Dimension().setName("ga:year"));

		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.yearlyDateRange())
				.setDimensions(dimensionList).setMetrics(metricList).setIncludeEmptyRows(true).setPageSize(5000);
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

				if (Double.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;

					PPCAllYear ppcAllyear = new PPCAllYear();
					GoogleAnalyticsCassandraCrudImpl writDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

					String date = dimensions.get(0).substring(0, 4) + "-" + dimensions.get(0).substring(4, 6) + "-"
							+ dimensions.get(0).substring(6, 8);
					ppcAllyear.setDate(date);
					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(yearlydate);

						ppcAllyear.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
					} catch (ParseException e) {

						e.printStackTrace();
					}

					ppcAllyear.setYear(Integer.parseInt(dimensions.get(1)));
					ppcAllyear.setAd_cost(metrics.get(0).getValues().get(0));
					ppcAllyear.setImpressiosn(metrics.get(0).getValues().get(1));
					ppcAllyear.setAd_clicks(metrics.get(0).getValues().get(2));
					ppcAllyear.setCTR(metrics.get(0).getValues().get(3));
					ppcAllyear.setCPC(metrics.get(0).getValues().get(4));
					ppcAllyear.setBounce_rate(metrics.get(0).getValues().get(5));
					ppcAllyear.setAvg_session_duration(metrics.get(0).getValues().get(6));
					ppcAllyear.setClient_stamp(client_stamp);

					writDataToCassandra.writePpcAllYearData(ppcAllyear);
				}
			}
		}

	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return PpcAllYearServiceImpl.count;
	}

}
