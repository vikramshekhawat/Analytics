package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_device_information")
public class Device {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	
	@PrimaryKeyColumn(name = "source", ordinal = 2)
	private String source;

	@PrimaryKeyColumn(name = "channel_grouping", ordinal = 3)
	private String channelGrouping;

	@PrimaryKeyColumn(name = "browser", ordinal = 4)
	private String browser;

	@PrimaryKeyColumn(name = "operating_system", ordinal = 5)
	private String operatingSystem;

	@PrimaryKeyColumn(name = "device_category", ordinal = 6)
	private String deviceCategory;
	

	@PrimaryKeyColumn(name = "day_of_year", ordinal = 7)
	private int dayOfYear;
	@Column(value = "start_date")
	private String startDate;

	@Column(value = "sessions")
	private String sessions;
	@Column(value = "api_date")
	private String date;

	@Column(value = "end_date")
	private String endDate;
	@Column(value = "new_users")
	private String newUsers;

	@Column(value = "page_views_per_session")
	private String pageviewsPerSession;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClient_stamp() {
		return client_stamp;
	}

	public void setClient_stamp(String client_stamp) {
		this.client_stamp = client_stamp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChannelGrouping() {
		return channelGrouping;
	}

	public void setChannelGrouping(String channelGrouping) {
		this.channelGrouping = channelGrouping;
	}

	public String getSessions() {
		return sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
