package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_conversionsppa30")
public class ConvPPCa30 {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	

	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	
	@Column(value = "api_date")
	private String date;

	@PrimaryKeyColumn(name = "event_category", ordinal = 2)
	private String eventCategory;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 3)
	private int dayOfYear;

	@Column(value = "total_events")
	private String totalEvents;

	@Column(value = "hour")
	private String hour;

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

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getTotalEvents() {
		return totalEvents;
	}

	public void setTotalEvents(String totalEvents) {
		this.totalEvents = totalEvents;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

}
