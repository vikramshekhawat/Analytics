package googleAnalytics.Service;

import java.io.IOException;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.Report;

public interface DataSetService {

	public List<Report> fetchData(AnalyticsReporting service, String viewId) throws IOException;
	
	public void saveData(List<Report> reports,String client_stamp);
	public int countTableRows();
	
}
