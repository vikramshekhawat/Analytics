package googleAnalytics.DaoImpl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import googleAnalytics.Dao.GoogleAnalyticsCassandraDao;
import googleAnalytics.Env.EnvironmentClass;
import googleAnalytics.bean.CassandraCredentials;
import googleAnalytics.bean.dataSet1.Channel;
import googleAnalytics.bean.dataSet1.ConversionsPPC;
import googleAnalytics.bean.dataSet1.Currency;
import googleAnalytics.bean.dataSet1.Device;
import googleAnalytics.bean.dataSet1.PPCAllYear;
import googleAnalytics.bean.dataSet1.SessionInfo;
import googleAnalytics.bean.dataSet1.Top20Pages;
import googleAnalytics.bean.dataSet1.TrafficBreakDown;
import googleAnalytics.bean.dataSet1.TrafficBreakDown30days;
import googleAnalytics.bean.dataSet1.TrafficSummary;
import googleAnalytics.bean.dataSet2.ConvOverview;
import googleAnalytics.bean.dataSet2.ConvOverviewPPC;
import googleAnalytics.bean.dataSet2.ConvOverviewWithDimensions;
import googleAnalytics.bean.dataSet2.ConvPPCa30;
import googleAnalytics.bean.dataSet2.ConvRateAvgValue;
import googleAnalytics.bean.dataSet2.ConversionPPC;
import googleAnalytics.bean.dataSet2.ConversionsALL;
import googleAnalytics.bean.dataSet2.Cpc30;
import googleAnalytics.bean.dataSet2.Direct30;
import googleAnalytics.bean.dataSet2.EcomAllYear;
import googleAnalytics.bean.dataSet2.EcomTransactions30;
import googleAnalytics.bean.dataSet2.EcomTransactions30PPC;
import googleAnalytics.bean.dataSet2.EcomTransactions30direct;
import googleAnalytics.bean.dataSet2.EcomTransactions30organic;
import googleAnalytics.bean.dataSet2.GoalCompletion1;
import googleAnalytics.bean.dataSet2.GoalCompletion2;
import googleAnalytics.bean.dataSet2.GoalCompletion3;
import googleAnalytics.bean.dataSet2.GoalCompletion4;
import googleAnalytics.bean.dataSet2.GoalCompletion5;
import googleAnalytics.bean.dataSet2.LatestTableInfo;
import googleAnalytics.bean.dataSet2.Organic30;
import googleAnalytics.bean.dataSet2.TopProducts;
import googleAnalytics.bean.dataSet2.TotalAdSpend;
import googleAnalytics.bean.dataSet2.TotalEcommerceRevenue;

@Repository("googleAnalyticsCassandraDao")
public class GoogleAnalyticsCassandraCrudImpl implements GoogleAnalyticsCassandraDao {

	@Autowired
	private static EnvironmentClass environmentClass;

	private static final int MILLI_SECONDS = 1000;
	private static final int SECONDS = 60;
	private static final int HIGHER_TIMEOUT = MILLI_SECONDS * SECONDS;
	private static CassandraCredentials cassandraCredentials;

	private static List<InetSocketAddress> address;

	private static Cluster cluster;

	private static Session session;
	private static CassandraTemplate cassandraOperations;

	public GoogleAnalyticsCassandraCrudImpl() {
		getConnection();
	}

	public GoogleAnalyticsCassandraCrudImpl(CassandraCredentials cassandraCredentials,
			EnvironmentClass environmentClass) {
		GoogleAnalyticsCassandraCrudImpl.cassandraCredentials = cassandraCredentials;
		GoogleAnalyticsCassandraCrudImpl.environmentClass = environmentClass;
	}

	public CassandraCredentials getCassandraCredentials() {
		return cassandraCredentials;
	}

	public static void getConnection() {

		if (session != null) {

		} else {

			address = new ArrayList<InetSocketAddress>();
			address.add(new InetSocketAddress(cassandraCredentials.getContactpoint(), cassandraCredentials.getPort()));
			try {

				if ("local".equals(environmentClass.getDataFromPropertyFile("cluster.username"))) {
					cluster = Cluster.builder().addContactPointsWithPorts(address).build();
				} else
					cluster = Cluster.builder().addContactPointsWithPorts(address)
							.withCredentials(environmentClass.getDataFromPropertyFile("cluster.username"),
									environmentClass.getDataFromPropertyFile("cluster.password"))
							.build();
				cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(HIGHER_TIMEOUT)
						.setReadTimeoutMillis(HIGHER_TIMEOUT);
				session = cluster.connect(cassandraCredentials.getDb());
				cassandraOperations = new CassandraTemplate(session);
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
			}

		}
	}

	
	// Data Set 1
	
	@Override
	public void writeChannelInformationToCassandra(Channel bean) {
		cassandraOperations.insert(bean);
		
	}

	@Override
	public void writeDeviceInformationToCassandra(Device bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeSessionInformationToCassandra(SessionInfo bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTop20PagesInformationToCassandra(Top20Pages bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeChannelInformationToCassandra(TrafficBreakDown30days bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTrafficBreakDownData(TrafficBreakDown bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTrafficSummaryData(TrafficSummary bean) {
		cassandraOperations.insert(bean);
	}

	
	
	// Data Set 2
	
	@Override
	public void writeConversionbPPCData(ConversionPPC bean) {
		cassandraOperations.insert(bean);

	}

	@Override
	public void writePpcAllYearData(PPCAllYear bean) {
		cassandraOperations.insert(bean);

	}

	@Override
	public void writeConversionsAll(ConversionsALL bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConversionsPPC(ConversionsPPC bean) {
		cassandraOperations.insert(bean);
	}
	
	
	@Override
	public void writeConversionPPC(ConversionPPC bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConvOverview(ConvOverview bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConvOverviewPPC(ConvOverviewPPC bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConvOverviewWithDimensions(ConvOverviewWithDimensions bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConvPPCa30Data(ConvPPCa30 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeConvRateAvgValueData(ConvRateAvgValue bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeCpc30Data(Cpc30 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeDirect30Data(Direct30 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeEcomAllYear(EcomAllYear bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeEcomTransactions30(EcomTransactions30 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeEcomTransactions30direct(EcomTransactions30direct bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeEcomTransactions30organic(EcomTransactions30organic bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeEcomTransactions30PPC(EcomTransactions30PPC bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeGoalConversion1(GoalCompletion1 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeGoalConversion2(GoalCompletion2 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeGoalConversion3(GoalCompletion3 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeGoalConversion4(GoalCompletion4 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeGoalConversion5(GoalCompletion5 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeOragnic30(Organic30 bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTopProducts(TopProducts bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTotalAdSpend(TotalAdSpend bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeTotalEcommerceRevenue(TotalEcommerceRevenue bean) {
		cassandraOperations.insert(bean);
	}

	@Override
	public void writeCurrencyData(Currency bean) {
		cassandraOperations.insert(bean);
		
	}
	@Override
	public void writeLatestTableInfo(LatestTableInfo tableInfoBean) {
		cassandraOperations.insert(tableInfoBean);
	}

}
