package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_conversions_ppc")
public class ConversionsPPC {

	

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	
	@PrimaryKeyColumn(name = "day_of_year")
	private int dayOfYear;

	@Column(value = "goal_completions_all")
	private String goalCompletionsAll;
	@Column(value = "date")
	private String date;
	
	public int  getDayOfYear() {
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

	public String getGoalCompletionsAll() {
		return goalCompletionsAll;
	}

	public void setGoalCompletionsAll(String goalCompletionsAll) {
		this.goalCompletionsAll = goalCompletionsAll;
	}

}
