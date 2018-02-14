package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_conv_overview_PPC")
public class ConvOverviewPPC {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	@Column(value = "start_date")
	private String startDate;

	@Column(value = "end_date")
	private String endDate;

	@Column(value = "goal_completions_all")
	private String goalCompletionsAll;
	@Column(value = "goal_completion_1")
	private String goalCompletion1;
	@Column(value = "goal_completion_2")
	private String goalCompletion2;
	@Column(value = "goal_completion_3")
	private String goalCompletion3;
	@Column(value = "goal_completion_4")
	private String goalCompletion4;
	@Column(value = "goal_completion_5")
	private String goalCompletion5;

	@PrimaryKeyColumn(name = "goal_completion_location", ordinal = 2)
	private String goalCompletionLocation;

	@PrimaryKeyColumn(name = "day_of_year", ordinal = 3)
	private int dayOfYear;

	@Column(value = "api_date")
	private String date;

	@Column(value = "hour")
	private String hour;

	public String getGoalCompletion1() {
		return goalCompletion1;
	}

	public void setGoalCompletion1(String goalCompletion1) {
		this.goalCompletion1 = goalCompletion1;
	}

	public String getGoalCompletion2() {
		return goalCompletion2;
	}

	public void setGoalCompletion2(String goalCompletion2) {
		this.goalCompletion2 = goalCompletion2;
	}

	public String getGoalCompletion3() {
		return goalCompletion3;
	}

	public void setGoalCompletion3(String goalCompletion3) {
		this.goalCompletion3 = goalCompletion3;
	}

	public String getGoalCompletion4() {
		return goalCompletion4;
	}

	public void setGoalCompletion4(String goalCompletion4) {
		this.goalCompletion4 = goalCompletion4;
	}

	public String getGoalCompletion5() {
		return goalCompletion5;
	}

	public void setGoalCompletion5(String goalCompletion5) {
		this.goalCompletion5 = goalCompletion5;
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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

}
