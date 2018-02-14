package googleAnalytics.Service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import googleAnalytics.ServiceImpl.dataSet1.ChannelServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.ConversionsPPCServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.CurrencyServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.DeviceServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.PpcAllYearServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.SessionServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.Top20PagesServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.TrafficBreakDown30DaysServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.TrafficBreakDownServiceImpl;
import googleAnalytics.ServiceImpl.dataSet1.TrafficSummaryServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.CPC30ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConvOverviewPPCServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConvOverviewServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConvOverviewWithDimensionsServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConvPPCa30ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConvRateAvgValueServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.ConversionsALLServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.Direct30ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.EcomAllYearServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.EcomTransactions30PPCServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.EcomTransactions30ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.EcomTransactions30directServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.EcomTransactions30organicServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.GoalCompletion1ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.GoalCompletion2ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.GoalCompletion3ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.GoalCompletion4ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.GoalCompletion5ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.Organic30ServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.PpcConversionServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.TopProductsServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.TotalAdSpendServiceImpl;
import googleAnalytics.ServiceImpl.dataSet2.TotalEcommerceRevenueServiceImpl;

public class DataSetFactory implements ApplicationContextAware {

	private ApplicationContext context = null;

	public DataSetService getBean(int i) {
		if (i == 0)
			return (ChannelServiceImpl) context.getBean("channelService");
		if (i == 1)
			return (DeviceServiceImpl) context.getBean("deviceService");
		if (i == 2)
			return (ConversionsPPCServiceImpl) context.getBean("conversionsPPCService");
		if (i == 3)
			return (PpcAllYearServiceImpl) context.getBean("ppcAllYearService");
		if (i == 4)
			return (SessionServiceImpl) context.getBean("sessionService");
		if (i == 5)
			return (Top20PagesServiceImpl) context.getBean("top20PagesService");
		if (i == 6)
			return (TrafficBreakDown30DaysServiceImpl) context.getBean("trafficBreakDown30DaysService");
		if (i == 7)
			return (TrafficBreakDownServiceImpl) context.getBean("trafficBreakDownService");
		if (i == 8)
			return null; // (TrafficServiceImpl)
							// context.getBean("trafficService");
		if (i == 9)
			return (TrafficSummaryServiceImpl) context.getBean("trafficSummaryService");
		if (i == 10)
			return (ConversionsALLServiceImpl) context.getBean("conversionsALLService");
		// left
		if (i == 11)
			return (PpcConversionServiceImpl) context.getBean("ppcConversionService");
		if (i == 12)
			return (ConvOverviewPPCServiceImpl) context.getBean("convOverviewPPCService");
		if (i == 13)
			return (ConvOverviewServiceImpl) context.getBean("convOverviewService");
		// left
		if (i == 14)
			return (ConvOverviewWithDimensionsServiceImpl) context.getBean("convOverviewWithDimensionsService");
		// left
		if (i == 15)
			return (ConvPPCa30ServiceImpl) context.getBean("convPPCa30Service");
		if (i == 16)
			return (ConvRateAvgValueServiceImpl) context.getBean("convRateAvgValueService");
		if (i == 17)
			return (CPC30ServiceImpl) context.getBean("cPC30Service");
		if (i == 18)
			return (Direct30ServiceImpl) context.getBean("direct30Service");
		if (i == 19)
			return (EcomAllYearServiceImpl) context.getBean("ecomAllYearService");
		if (i == 20)
			return (EcomTransactions30directServiceImpl) context.getBean("ecomTransactions30directService");
		if (i == 21)
			return (EcomTransactions30organicServiceImpl) context.getBean("ecomTransactions30organicService");
		if (i == 22)
			return (EcomTransactions30PPCServiceImpl) context.getBean("ecomTransactions30PPCService");
		if (i == 23)
			return (EcomTransactions30ServiceImpl) context.getBean("ecomTransactions30Service");
		if (i == 24)
			return (GoalCompletion1ServiceImpl) context.getBean("goalCompletion1Service");
		if (i == 25)
			return (GoalCompletion2ServiceImpl) context.getBean("goalCompletion2Service");
		if (i == 26)
			return (GoalCompletion3ServiceImpl) context.getBean("goalCompletion3Service");
		if (i == 27)
			return (GoalCompletion4ServiceImpl) context.getBean("goalCompletion4Service");
		if (i == 28)
			return (GoalCompletion5ServiceImpl) context.getBean("goalCompletion5Service");
		if (i == 29)
			return (TopProductsServiceImpl) context.getBean("topProductsService");
		if (i == 30)
			return (Organic30ServiceImpl) context.getBean("organic30Service");
		if (i == 31)
			return (TotalAdSpendServiceImpl) context.getBean("totalAdSpendService");
		if (i == 32)
			return (TotalEcommerceRevenueServiceImpl) context.getBean("totalEcommerceRevenueService");
		if (i == 33)
			return (CurrencyServiceImpl) context.getBean("currencyService");
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
