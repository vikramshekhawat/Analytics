package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_session_information")
public class SessionInfo {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	
	@PrimaryKeyColumn(name = "day_of_year")
	private int dayOfYear;
	@Column(value = "date")
	private String date;


	@Column(value = "bounce_rate")
	private String bounceRate;

	@Column(value = "avg_session_duration")
	private String avgSessionDuration;

	@Column(value = "transactions")
	private String transactions;

	@Column(value = "transaction_revenue")
	private String transactionRevenue;

	@Column(value = "sessions")
	private String sessions;

	@Column(value = "start_date")
	private String start_date;

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getClient_stamp() {
		return client_stamp;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setClient_stamp(String client_stamp) {
		this.client_stamp = client_stamp;
	}

	public String getSessions() {
		return sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
	}

	public String getBounceRate() {
		return bounceRate;
	}

	public void setBounceRate(String bounceRate) {
		this.bounceRate = bounceRate;
	}

	public String getAvgSessionDuration() {
		return avgSessionDuration;
	}

	public void setAvgSessionDuration(String avgSessionDuration) {
		this.avgSessionDuration = avgSessionDuration;
	}

	public String getTransactions() {
		return transactions;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	public String getTransactionRevenue() {
		return transactionRevenue;
	}

	public void setTransactionRevenue(String transactionRevenue) {
		this.transactionRevenue = transactionRevenue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
