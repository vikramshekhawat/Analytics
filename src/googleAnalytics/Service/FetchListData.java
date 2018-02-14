package googleAnalytics.Service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;

import googleAnalytics.bean.dataSet2.MetricAndDimensionsListBean;

public class FetchListData {
	MetricAndDimensionsListBean obj = new MetricAndDimensionsListBean();

	public List<List<MetricAndDimensionsListBean>> metricsAndDimensions(String goalCompletionNumber) {
		List finalList = new ArrayList<>();
		List<Metric> metricList = obj.getMetricList();
		List<Dimension> dimensionsList = obj.getDimensionsList();
		// metricList.add(new
		// Metric().setExpression(goalCompletionNumber).setAlias(goalCompletionNumber));
		// metricList.add(new
		// Metric().setExpression("ga:goalCompletionsAll").setAlias("goalCompletionsAll"));
		// dimensionsList.add(new Dimension().setName("ga:date"));
		// dimensionsList.add(new Dimension().setName("ga:year"));
		// dimensionsList.add(new Dimension().setName("ga:hour"));
		// dimensionsList.add(new
		// Dimension().setName("ga:goalCompletionLocation"));
		finalList.add(metricList);
		finalList.add(dimensionsList);

		return finalList;

	}

}
