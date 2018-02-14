package googleAnalytics.app;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.Report;

import googleAnalytics.DaoImpl.GoogleAnalyticsMiscellaneousInformation;
import googleAnalytics.Env.EnvironmentClass;
import googleAnalytics.Service.DataSetFactory;
import googleAnalytics.Service.GoalDataService;
import googleAnalytics.bean.ClientConfiguration;

public class GoogleConnect {

	static Logger logger = LoggerFactory.getLogger(GoogleConnect.class);

	private static final File DATA_STORE_DIR;
	public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	public static NetHttpTransport httpTransport;
	public static FileDataStoreFactory dataStoreFactory;
	public static GoogleCredential credential = null;
	private static AnalyticsReporting analytics = null;
	private static Analytics analyticsService = null;
	private static EnvironmentClass envBean = null;
	private static AbstractApplicationContext context = null;
	public static final String GOOGLE_ANALYTICS_CLIENT_CONFIG = "google_analytics_client_config";
	public static String REFRESH_TOKEN = "refresh_Token";
	public static String ACCOUNT_ID = "account_Id";
	public static String CLIENT_STAMP = "client_stamp";
	public static String VIEW_ID = "view_Id";
	public static String CLIENT_INFO = "client_Info";
	public static String COMPANY_NAME = "company_Name";
	static {
		context = new ClassPathXmlApplicationContext("spring.xml");
		context.registerShutdownHook();
		envBean = (EnvironmentClass) context.getBean("environmentClass");
		DATA_STORE_DIR = new File(envBean.getDataFromPropertyFile("filePath"));
	}

	public static void main(String[] args) {
		GoalDataService goalItems = new GoalDataService();

		try {

			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			GoogleAnalyticsMiscellaneousInformation dao = context.getBean("dao",
					GoogleAnalyticsMiscellaneousInformation.class);

			List<ClientConfiguration> clientInformation = dao.getGoogleAnalyticsDataFromSql();

			for (ClientConfiguration client : clientInformation) {
				VIEW_ID = client.getViewId();
				REFRESH_TOKEN = client.getRefreshToken();
				CLIENT_STAMP = client.getClient_stamp();
				COMPANY_NAME = client.getCompany_Name();
				ACCOUNT_ID = client.getAccountId();
				credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport())
						.setJsonFactory(new JacksonFactory())
						.setClientSecrets(envBean.getDataFromPropertyFile("ClientId"),
								envBean.getDataFromPropertyFile("ClientSecret"))
						.addRefreshListener(new CredentialRefreshListener() {
							@Override
							public void onTokenResponse(Credential credential, TokenResponse tokenResponse) {
								System.out.println("Credential was refreshed successfully.");
							}

							@Override
							public void onTokenErrorResponse(Credential credential,
									TokenErrorResponse tokenErrorResponse) {
								System.err.println("Credential was not refreshed successfully. "
										+ "Redirect to error page or login screen.");
							}
						}).build();

				credential.setRefreshToken(REFRESH_TOKEN);
				credential.refreshToken();
				System.out.println(credential.getAccessToken());
				// for reporting api
				analytics = new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
						.setApplicationName(envBean.getDataFromPropertyFile("APPLICATION_NAME")).build();
				// for management api
				analyticsService = new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
						.setApplicationName(envBean.getDataFromPropertyFile("APPLICATION_NAME")).build();

				goalItems.fetchGoalsData(analyticsService, ACCOUNT_ID, "UA-" + ACCOUNT_ID + "-1", VIEW_ID);
				// fetch table schema from DataSetFactory
				List<Report> reports = null;
				if (CLIENT_STAMP != null) {
					DataSetFactory dataSetFactory = context.getBean("dataSetFactory", DataSetFactory.class);
					for (int i = 24; i < 30; i++) {
						// int i=24;

						if (dataSetFactory.getBean(i) != null) {
							try {
								reports = dataSetFactory.getBean(i).fetchData(analytics, VIEW_ID);
								dataSetFactory.getBean(i).saveData(reports, CLIENT_STAMP);
							} catch (Exception e) {
								logger.info(ExceptionUtils.getStackTrace(e));
								System.out.println(ExceptionUtils.getStackTrace(e));
							}
							System.out.println("Client_Id = " + CLIENT_STAMP + " Company Name = " + COMPANY_NAME
									+ "  Table Name = " + envBean.getDataFromPropertyFile("" + i) + "  Rows= "
									+ dataSetFactory.getBean(i).countTableRows());
							logger.info("Client_Id = " + CLIENT_STAMP + " Company Name = " + COMPANY_NAME
									+ "  Table Name = " + envBean.getDataFromPropertyFile("" + i) + "  Rows= "
									+ dataSetFactory.getBean(i).countTableRows());

						}

					}

				}

			}

			context.close();
			System.exit(0);
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		} finally {
		}
	}

}
