package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_traffic_summary")
public class TrafficSummary {
	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;
	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 2)
	private int dayOfYear;

	@Column(value = "date")
	private String date;

	// {will hold the end-date ,which would be yesterday's date.}
	
	@PrimaryKeyColumn(name = "source", ordinal = 3)
	private String source;

	@PrimaryKeyColumn(name = "browser", ordinal = 4)
	private String browser;

	@PrimaryKeyColumn(name = "operating_system", ordinal = 5)
	private String operatingSystem;

	@PrimaryKeyColumn(name = "device_category", ordinal = 6)
	private String deviceCategory;

	@PrimaryKeyColumn(name = "channel_grouping", ordinal = 7)
	private String channelGrouping;

	@Column(value = "landing_path")
	private String landingPath;

	@Column(value = "sessions")
	private String sessions;

	@Column(value = "new_users")
	private String newUsers;

	@Column(value = "page_views_per_session")
	private String pageviewsPerSession;

	@Column(value = "start_date")
	private String startDate;
	public int getDayOfYear() {
		return dayOfYear;
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

	public String getChannelGrouping() {
		return channelGrouping;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setChannelGrouping(String channelGrouping) {
		this.channelGrouping = channelGrouping;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getDeviceCategory() {
		return deviceCategory;
	}

	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public String getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(String newUsers) {
		this.newUsers = newUsers;
	}

	public String getPageviewsPerSession() {
		return pageviewsPerSession;
	}

	public void setPageviewsPerSession(String pageviewsPerSession) {
		this.pageviewsPerSession = pageviewsPerSession;
	}

}
