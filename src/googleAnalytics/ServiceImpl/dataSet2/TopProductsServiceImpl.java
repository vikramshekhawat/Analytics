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
import googleAnalytics.Service.ReportData;
import googleAnalytics.bean.dataSet2.TopProducts;
import googleAnalytics.utility.Utility;

@Service("topProductsService")
public class TopProductsServiceImpl implements DataSetService {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	static int count = 0;
	ReportData reportData = new ReportData();
	LatesttTableWriteInfo latestTableObj = new LatesttTableWriteInfo();
	@Autowired
	Environment env;

	@Override
	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException {
		List<Metric> metricList = new ArrayList<Metric>();
		metricList.add(new Metric().setExpression("ga:uniquePurchases"));
		metricList.add(new Metric().setExpression("ga:itemQuantity"));
		metricList.add(new Metric().setExpression("ga:itemRevenue"));
		List<Dimension> dimensionsList = new ArrayList<Dimension>();
		dimensionsList.add(new Dimension().setName("ga:productName"));
		dimensionsList.add(new Dimension().setName("ga:productSku"));
		dimensionsList.add(new Dimension().setName("ga:date"));
		dimensionsList.add(new Dimension().setName("ga:year"));
		return reportData.getreport(service, viewId, metricList, dimensionsList);
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
				GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();
				if (Integer.valueOf(metrics.get(0).getValues().get(0)) == 0 && flag == 0) {
					continue;
				} else {
					count++;
					flag = 1;

					TopProducts topProduct = new TopProducts();
					String date = dimensions.get(2).substring(0, 4) + "-" + dimensions.get(2).substring(4, 6) + "-"
							+ dimensions.get(2).substring(6, 8);
					topProduct.setDate(date);

					Date yearlydate;
					try {
						yearlydate = format.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(yearlydate);
						// bean.setDayOfMonth(Integer.parseInt(dimensions.get(1).substring(6,
						// 8)));
						topProduct.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					topProduct.setClientStamp(client_stamp);
					topProduct.setYear(Integer.parseInt(dimensions.get(3)));
					// topProduct.setDate();
					topProduct.setStartDate(String.valueOf(format.format(Utility.thirtyDaysAgo().getTime())));
					topProduct.setProductName(dimensions.get(0));
					topProduct.setProductSku(dimensions.get(1));
					topProduct.setItemQuantity(metrics.get(0).getValues().get(1));
					topProduct.setItemRevenue(metrics.get(0).getValues().get(2));
					topProduct.setUniquePurchases(metrics.get(0).getValues().get(0));
					topProduct.setEndDate(String.valueOf(format.format(Utility.yesterday().getTime())));
					writeDataToCassandra.writeTopProducts(topProduct);

				}
			}
		}

		latestTableObj.saveData(env.getProperty("29"), client_stamp, Utility.currentDate().get("dayOfYear"),
				Utility.currentDate().get("year"), Utility.currentDate().get("month"));
	}

	@Override
	public int countTableRows() {
		// TODO Auto-generated method stub
		return TopProductsServiceImpl.count;
	}

}
