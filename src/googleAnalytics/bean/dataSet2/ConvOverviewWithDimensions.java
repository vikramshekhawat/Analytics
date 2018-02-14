package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="google_analytics_conv_overview_with_dimensions")
public class ConvOverviewWithDimensions {
	
	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@Column
	private String clientStamp;
	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	@Column
	private int year;
	@PrimaryKeyColumn(name = "api_date", ordinal = 2, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	@Column
	private String date;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 3)
	@Column
	private int dayOfYear;
	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	@Column(value="goal_completions_all")
	private String goalCompletionsAll;
	@Column(value="goal_completion_location")
	private String goalCompletionLocation;
	@Column(value="hour")
	private String hour;
	@Column(value="metrics")
	private String metrics;
	
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getGoalCompletionsAll() {
		return goalCompletionsAll;
	}
	public void setGoalCompletionsAll(String goalCompletionsAll) {
		this.goalCompletionsAll = goalCompletionsAll;
	}
	public String getGoalCompletionLocation() {
		return goalCompletionLocation;
	}
	public void setGoalCompletionLocation(String goalCompletionLocation) {
		this.goalCompletionLocation = goalCompletionLocation;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMetrics() {
		return metrics;
	}
	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}
	
	
	
	

}
