package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_ecom_all_year")
public class EcomAllYear {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	
	
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 2)
	private int dayOfYear;
	@Column(value = "date")
	private String date;

	@Column(value = "transaction_revenue")
	private String transactionRevenue;

	@Column(value = "transactions")
	private String transactions;

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

	public String getTransactionRevenue() {
		return transactionRevenue;
	}

	public void setTransactionRevenue(String transactionRevenue) {
		this.transactionRevenue = transactionRevenue;
	}

	public String getTransactions() {
		return transactions;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

}
