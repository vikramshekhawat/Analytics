package googleAnalytics.Dao;

import java.util.List;

import googleAnalytics.bean.ClientConfiguration;

public interface GoogleAnalyticsFetchSqlData {

	public List<ClientConfiguration> getGoogleAnalyticsDataFromSql();

	//public void updateToken(String accessToken, String client_stamp);

}
