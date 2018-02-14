package googleAnalytics.DaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import googleAnalytics.Dao.GoogleAnalyticsFetchSqlData;
import googleAnalytics.app.GoogleConnect;
import googleAnalytics.bean.ClientConfiguration;

@Repository("googleAnalyticsFetchSqlData")
public class GoogleAnalyticsMiscellaneousInformation implements GoogleAnalyticsFetchSqlData {

	@Autowired
	DataSource dataSource;

	JdbcTemplate jdbcTemplate;

	public GoogleAnalyticsMiscellaneousInformation(DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public List<ClientConfiguration> getGoogleAnalyticsDataFromSql() {

		String newQuery = "SELECT * FROM " + GoogleConnect.GOOGLE_ANALYTICS_CLIENT_CONFIG + ";";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(newQuery);
		List<ClientConfiguration> clientList = new ArrayList<>();
		try {
			for (Map<String, Object> row : rows) {
				ClientConfiguration clientInfo = new ClientConfiguration();
				// clientInfo.setAccessToken(row.get("accessToken").toString());
				clientInfo.setRefreshToken(row.get(GoogleConnect.REFRESH_TOKEN).toString());
				clientInfo.setAccountId(row.get(GoogleConnect.ACCOUNT_ID).toString());
				clientInfo.setClient_stamp(row.get(GoogleConnect.CLIENT_STAMP).toString());
				clientInfo.setViewId(row.get(GoogleConnect.VIEW_ID).toString());
				clientInfo.setCompany_Name(row.get(GoogleConnect.COMPANY_NAME).toString());
				clientList.add(clientInfo);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return clientList;

	}

	// @Override
	// public void updateToken(String accessToken, String client_stamp) {
	// jdbcTemplate.update("update "+
	// GoogleConnect.GOOGLE_ANALYTICS_CLIENT_CONFIG +" set accessToken = ? where
	// "+ GoogleConnect.CLIENT_STAMP+" = ?",
	// accessToken, client_stamp);
	// }

}
