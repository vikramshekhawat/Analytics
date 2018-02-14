package googleAnalytics.bean.dataSet2;

import java.util.List;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;
public class MetricAndDimensionsListBean {
	private List<Metric> metricList;
	private List<Dimension> dimensionsList;
	public List<Metric> getMetricList() {
		return metricList;
	}
	public void setMetricList(List<Metric> metricList) { 
		this.metricList = metricList;
	}
	public List<Dimension> getDimensionsList() {
		return dimensionsList;
	}
	public void setDimensionsList(List<Dimension> dimensionsList) {
		this.dimensionsList = dimensionsList;
	}
	
	

}
