package googleAnalytics.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

import googleAnalytics.utility.Utility;

public class ReportData {
	public List<Report> getreport(AnalyticsReporting service, String viewId,List<Metric> metricList,List<Dimension> dimensionsList){
		GetReportsRequest getReport = null;
		
		
		
		
		
		
		
		
		ReportRequest request = new ReportRequest().setViewId("ga:" + viewId).setDateRanges(Utility.monthlyDateRange())
				.setDimensions(dimensionsList).setMetrics(metricList).setIncludeEmptyRows(true).setPageSize(10000);
		List<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		getReport = new GetReportsRequest().setReportRequests(requests);
		try {
			return service.reports().batchGet(getReport).execute().getReports();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
