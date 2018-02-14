package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_currency")
public class Currency {

	

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1,type = PrimaryKeyType.PARTITIONED)
	private int year;

	
	@PrimaryKeyColumn(name = "day_of_year")
	private int dayOfYear;

	@Column(value = "transaction_revenue")
	private String transactionRevenue;
	@Column(value ="currency_code")
	private String currencyCode;
	@Column(value = "date")
	private String date;
	
//	public String getCurrencyCode() {
//		return currencyCode;
//	}
//	public void setCurrencyCode(String currencyCoder) {
//		this.currencyCode = currencyCoder;
//	}
	public String getClientStamp() {
		return clientStamp;
	}
	public void setClientStamp(String clientStamp) {
		this.clientStamp = clientStamp;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	
	public String getTransactionRevenue() {
		return transactionRevenue;
	}
	public void setTransactionRevenue(String transactionRevenue) {
		this.transactionRevenue = transactionRevenue;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
	
