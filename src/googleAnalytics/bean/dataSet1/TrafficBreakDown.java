package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_traffic_breakdown")
public class TrafficBreakDown {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	@PrimaryKeyColumn(name = "api_date", ordinal = 3)
	private String date;

	@Column(value = "day_of_year")
	private int dayOfYear;

	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;

	@Column(value = "sessions")
	private String sessions;

	@PrimaryKeyColumn(name = "channel_grouping", ordinal = 4)
	private String channelGrouping;

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

	public String getClient_stamp() {
		return client_stamp;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
