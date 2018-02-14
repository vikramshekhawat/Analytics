package googleAnalytics.ServiceImpl.dataSet2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.Service.DataSetService;
import googleAnalytics.Service.GoalDataService;
import googleAnalytics.Service.ReportData;
import googleAnalytics.bean.dataSet2.GoalCompletion3;
import googleAnalytics.utility.Utility;

@Service("goalConversion3Service")
public class GoalCompletion3ServiceImpl implements DataSetService {
	static int count = 0;
	ReportData reportData = new ReportData();
	GoalDataService obj = new GoalDataService();
	LatesttTableWriteInfo latestTableObj = new LatesttTableWriteInfo();
	@Autowired
	Environment env;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {

		List<Metric> metricList = new ArrayList<Metric>();
		metricList.add(new Metric().setExpression("ga:goal3Completions").setAlias("goal3Completions"));
		metricList.add(new Metric().setExpression("ga:goalCompletionsAll").setAlias("goalCompletionsAll"));

		List<Dimension> dimensionsList = new ArrayList<Dimension>();
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:year"));
		dimensionsList.add(new Dimension().setName("ga:hour"));
		dimensionsList.add(new Dimension().setName("ga:goalCompletionLocation"));
		return reportData.getreport(service, viewId, metricList, dimensionsList);
	}

	@Override
	public void saveData(List<Report> reports, String client_stamp) {
		int flag = 0;
		count = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = obj.getGoal(3);
		for (Report report : reports) {
			List<ReportRow> rows = report.getData().getRows();
			for (ReportRow row : rows) {
				List<DateRangeValues> metrics = row.getMetrics();
				List<String> dimensions = row.getDimensions();

				if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;

					GoalCompletion3 bean = new GoalCompletion3();
					GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

					String date = dimensions.get(0).substring(0, 4) + "-" + dimensions.get(0).substring(4, 6) + "-"
							+ dimensions.get(0).substring(6, 8);
					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(yearlydate);

						bean.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));

					} catch (ParseException e) {

						e.printStackTrace();
					}
					// month
					bean.setMonth(Integer.parseInt(dimensions.get(0).substring(4, 6)));
					// api_date
					bean.setDate(date);
					// hour
					bean.setHour(Integer.parseInt(dimensions.get(2)));
					// goalCompletion location - dimension
					bean.setGoalCompletionLocation(dimensions.get(3));
					// start Date
					bean.setStartDate(String.valueOf(format.format(Utility.thirtyDaysAgo().getTime())));
					// year
					bean.setYear(Integer.parseInt(dimensions.get(1)));
					// ga:goal1Completions
					bean.setMetrics(Integer.parseInt(metrics.get(0).getValues().get(0)));
					// goal Completions All
					bean.setGoalCompletionsAll(Integer.parseInt(metrics.get(0).getValues().get(1)));
					// client_stamp
					bean.setClientStamp(client_stamp);
					// End date
					bean.setEndDate(String.valueOf(format.format(Utility.yesterday().getTime())));
					if (!list.isEmpty()) {
						bean.setGoalId(list.get(0));
						bean.setGoalName(list.get(1));
						bean.setIsActive(list.get(2));
						bean.setGoalValue(list.get(3));
						bean.setGoalType(list.get(4));
					}
					writeDataToCassandra.writeGoalConversion3(bean);

				}
			}

		}
		latestTableObj.saveData(env.getProperty("26"), client_stamp, Utility.currentDate().get("dayOfYear"),
				Utility.currentDate().get("year"), Utility.currentDate().get("month"));
	}

	@Override
	public int countTableRows() {

		return GoalCompletion3ServiceImpl.count;
	}

}
