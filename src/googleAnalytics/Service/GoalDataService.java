package googleAnalytics.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.Goals;

public class GoalDataService {

	private Analytics analyticsService;
	private static Goals goal = null;

	public void fetchGoalsData(Analytics analyticsService, String accountId, String webPropertyId, String profileId) {
		if (analyticsService != null) {
			this.analyticsService = analyticsService;
			try {
				goal = this.analyticsService.management().goals().list(accountId, webPropertyId, profileId).execute();
				

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public List<String> getGoal(int id) {
		List<String> list = new ArrayList<String>();
		
		for(int i=0;i<goal.getItems().size();i++){
		if (Integer.parseInt(goal.getItems().get(i).getId()) == id ) {
			
				list.add((goal.getItems().get(i)).getId());
				list.add((goal.getItems().get(i).getName()));
				list.add(String.valueOf((goal.getItems().get(i).getActive())));
				list.add(String.valueOf(goal.getItems().get(i).getValue()));
				list.add((goal.getItems().get(i).getType()));
			
			}
		 
		}	

		return list;
	}
}
