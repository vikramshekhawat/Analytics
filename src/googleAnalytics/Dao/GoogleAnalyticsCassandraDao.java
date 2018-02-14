package googleAnalytics.Dao;

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

public interface GoogleAnalyticsCassandraDao {

	// Data Set 1

	public void writeChannelInformationToCassandra(Channel bean);

	public void writeDeviceInformationToCassandra(Device bean);

	public void writeSessionInformationToCassandra(SessionInfo bean);

	public void writeTop20PagesInformationToCassandra(Top20Pages bean);

	public void writeChannelInformationToCassandra(TrafficBreakDown30days bean);

	public void writeTrafficBreakDownData(TrafficBreakDown bean);

	public void writeTrafficSummaryData(TrafficSummary bean);

	public void writeConversionbPPCData(ConversionPPC bean);

	public void writePpcAllYearData(PPCAllYear bean);

	public void writeCurrencyData(Currency bean);

	// Data Set 2

	public void writeConversionsAll(ConversionsALL bean);

	public void writeConversionsPPC(ConversionsPPC bean);

	public void writeConversionPPC(ConversionPPC bean);

	public void writeConvOverview(ConvOverview bean);

	public void writeConvOverviewPPC(ConvOverviewPPC bean);

	public void writeConvOverviewWithDimensions(ConvOverviewWithDimensions bean);

	public void writeConvPPCa30Data(ConvPPCa30 bean);

	public void writeConvRateAvgValueData(ConvRateAvgValue bean);

	public void writeCpc30Data(Cpc30 bean);

	public void writeDirect30Data(Direct30 bean);

	public void writeEcomAllYear(EcomAllYear bean);

	public void writeEcomTransactions30(EcomTransactions30 bean);

	public void writeEcomTransactions30direct(EcomTransactions30direct bean);

	public void writeEcomTransactions30organic(EcomTransactions30organic bean);

	public void writeEcomTransactions30PPC(EcomTransactions30PPC bean);

	public void writeGoalConversion1(GoalCompletion1 bean);

	public void writeGoalConversion2(GoalCompletion2 bean);

	public void writeGoalConversion3(GoalCompletion3 bean);

	public void writeGoalConversion4(GoalCompletion4 bean);

	public void writeGoalConversion5(GoalCompletion5 bean);

	public void writeOragnic30(Organic30 bean);

	public void writeTopProducts(TopProducts bean);

	public void writeTotalAdSpend(TotalAdSpend bean);

	public void writeTotalEcommerceRevenue(TotalEcommerceRevenue bean);
	public void writeLatestTableInfo(LatestTableInfo tableInfoBean);

}
