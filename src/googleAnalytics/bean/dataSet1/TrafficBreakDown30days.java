package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_traffic_break_down_30days")
public class TrafficBreakDown30days {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	// {Will contain the end date. Which would be same throughout}
	

	@PrimaryKeyColumn(name = "channel_grouping", ordinal = 3)
	private String channelGrouping;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 4)
	private int dayOfYear;

	@Column(value = "sessions")
	private String sessions;

	// {Will contain the start date. Which would be same throughout}
	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	@Column(value = "api_date")
	private String date;

	public int getDayOfYear() {
		return dayOfYear;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getClient_stamp() {
		return client_stamp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setClient_stamp(String client_stamp) {
		this.client_stamp = client_stamp;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSessions() {
		return sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
	}

	public String getChannelGrouping() {
		return channelGrouping;
	}

	public void setChannelGrouping(String channelGrouping) {
		this.channelGrouping = channelGrouping;
	}

}
