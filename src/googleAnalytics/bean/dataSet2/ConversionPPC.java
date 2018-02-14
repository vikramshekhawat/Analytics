package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_conversion_ppc")
public class ConversionPPC {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	
	
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 2)
	private int dayOfYear;

	@Column(value = "goal_conversion_rate_all")
	private String goal_conversion_rate_all;
	@Column(value = "date")
	private String date;

	
	public int getDayOfYear() {
		return dayOfYear;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGoal_conversion_rate_all() {
		return goal_conversion_rate_all;
	}

	public void setGoal_conversion_rate_all(String goal_conversion_rate_all) {
		this.goal_conversion_rate_all = goal_conversion_rate_all;
	}

}
