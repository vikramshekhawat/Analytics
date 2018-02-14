package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_goal_completion_3")
public class GoalCompletion3 {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	@PrimaryKeyColumn(value = "month")
	private int month;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 2)
	private int dayOfYear;
	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;
	@Column(value = "api_date")
	private String date;
	@PrimaryKeyColumn(value = "hour", ordinal = 3, type = PrimaryKeyType.CLUSTERED)

	private int hour;
	@Column(value = "goalcompletion_location")
	private String goalCompletionLocation;
	@Column(value = "goalcompletions_all")
	private int goalCompletionsAll;

	@Column(value = "metrics")
	private int metrics;
	@Column(value = "goal_name")
	private String goalName;
	@Column(value = "goal_value")
	private String goalValue;
	@Column(value = "goal_id")
	private String goalId;
	@Column(value = "goal_type")
	private String goalType;
	@Column(value = "is_active")
	private String isActive;
	
	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public String getGoalName() {
		return goalName;
	}


	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}


	public String getGoalValue() {
		return goalValue;
	}


	public void setGoalValue(String goalValue) {
		this.goalValue = goalValue;
	}


	public String getGoalId() {
		return goalId;
	}


	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}


	public String getGoalType() {
		return goalType;
	}


	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getEndDate() {
		return endDate;
	}
	

	public int getHour() {
		return hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public String getGoalCompletionLocation() {
		return goalCompletionLocation;
	}


	public void setGoalCompletionLocation(String goalCompletionLocation) {
		this.goalCompletionLocation = goalCompletionLocation;
	}


	public int getGoalCompletionsAll() {
		return goalCompletionsAll;
	}


	public void setGoalCompletionsAll(int goalCompletionsAll) {
		this.goalCompletionsAll = goalCompletionsAll;
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

	public int getMetrics() {
		return metrics;
	}

	public void setMetrics(int metrics) {
		this.metrics = metrics;
	}

}
