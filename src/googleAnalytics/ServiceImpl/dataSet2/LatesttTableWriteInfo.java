package googleAnalytics.ServiceImpl.dataSet2;

import java.util.Date;

import googleAnalytics.DaoImpl.GoogleAnalyticsCassandraCrudImpl;
import googleAnalytics.bean.dataSet2.LatestTableInfo;

public class LatesttTableWriteInfo {

	public void saveData(String table_name, String client_stamp,int dayOfYear,int year,int month ) {

		LatestTableInfo tableInfoBean = new LatestTableInfo();

		GoogleAnalyticsCassandraCrudImpl writeDataToCassandra = new GoogleAnalyticsCassandraCrudImpl();

		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		
		
		
		Date timestamp = new Date(System.currentTimeMillis());
		tableInfoBean.setDayOfYear(dayOfYear);
		tableInfoBean.setClientStamp(client_stamp);
		tableInfoBean.setYear(year);
		tableInfoBean.setMonth(month);
		//tableInfoBean.setDayOfMonth(dayOfMonth);
		tableInfoBean.setTimeLong((System.currentTimeMillis()));
		tableInfoBean.setTime(timestamp);
		tableInfoBean.setTableName(table_name);
		
		writeDataToCassandra.writeLatestTableInfo(tableInfoBean);
		
		
		
		
		
		
	}

}
