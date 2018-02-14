package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_cpc_30")
public class Cpc30 {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 2)
	private int dayOfYear;

	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	@Column(value = "api_date")
	private String date;

	@Column(value = "transaction_revenue")
	private String transactionRevenue;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTransactionRevenue() {
		return transactionRevenue;
	}

	public void setTransactionRevenue(String transactionRevenue) {
		this.transactionRevenue = transactionRevenue;
	}

}
