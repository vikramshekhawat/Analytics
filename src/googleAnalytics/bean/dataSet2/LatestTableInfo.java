package googleAnalytics.bean.dataSet2;

import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "latest_table_write_info")
public class LatestTableInfo {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "table_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String tableName;
	@PrimaryKeyColumn(name = "time_long", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private long timeLong;
	@Column(value = "month")
	private int month;
	// @Column(value = "day_of_month")
	// private int dayOfMonth;
	@Column(value = "day_of_year")
	private int dayOfYear;
	@Column(value = "year")
	private int year;
	@Column(value = "time")
	private Date time;

	public String getClientStamp() {
		return clientStamp;
	}

	public void setClientStamp(String clientStamp) {
		this.clientStamp = clientStamp;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public long getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(long timeLong) {
		this.timeLong = timeLong;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// public int getDayOfMonth() {
	// return dayOfMonth;
	// }
	// public void setDayOfMonth(int dayOfMonth) {
	// this.dayOfMonth = dayOfMonth;
	// }
	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
