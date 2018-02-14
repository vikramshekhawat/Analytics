package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_top20pages_information")
public class Top20Pages {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	// {Will contain the end date. Which would be same throughout}
	
	@PrimaryKeyColumn(name = "landing_path", ordinal = 2)
	private String landingPath;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 3)
	private int dayOfYear;

	// {Will contain the end date. Which would be same throughout}
	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	@Column(value = "api_date")
	private String date;

	@Column(value = "sessions")
	private String sessions;

	@Column(value = "bounce_rate")
	private String bounceRate;

	@Column(value = "page_view_per_session")
	private String pageviewsPerSession;

	@Column(value = "avg_session_duration")
	private String avgSessionDuration;

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setClient_stamp(String client_stamp) {
		this.client_stamp = client_stamp;
	}

	public String getLandingPath() {
		return landingPath;
	}

	public void setLandingPath(String landingPath) {
		this.landingPath = landingPath;
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

	public String getPageviewsPerSession() {
		return pageviewsPerSession;
	}

	public void setPageviewsPerSession(String pageviewsPerSession) {
		this.pageviewsPerSession = pageviewsPerSession;
	}

	public String getAvgSessionDuration() {
		return avgSessionDuration;
	}

	public void setAvgSessionDuration(String avgSessionDuration) {
		this.avgSessionDuration = avgSessionDuration;
	}

}
