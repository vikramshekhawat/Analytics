package googleAnalytics.bean.dataSet1;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_ppc_all_year")
public class PPCAllYear {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String client_stamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;
	@Column(value = "date")
	private String date;
	
	
	@PrimaryKeyColumn(name = "day_of_year")
	private int dayOfYear;

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	@Column(value = "ad_cost")
	private String ad_cost;

	@Column(value = "impressions")
	private String impressiosn;

	@Column(value = "ad_clicks")
	private String ad_clicks;

	@Column(value = "CTR")
	private String CTR;

	@Column(value = "CPC")
	private String CPC;

	@Column(value = "bounce_rate")
	private String bounce_rate;

	@Column(value = "avg_session_duration")
	private String avg_session_duration;

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

	public String getAd_cost() {
		return ad_cost;
	}

	public void setAd_cost(String ad_cost) {
		this.ad_cost = ad_cost;
	}

	public String getImpressiosn() {
		return impressiosn;
	}

	public void setImpressiosn(String impressiosn) {
		this.impressiosn = impressiosn;
	}

	public String getAd_clicks() {
		return ad_clicks;
	}

	public void setAd_clicks(String ad_clicks) {
		this.ad_clicks = ad_clicks;
	}

	public String getCTR() {
		return CTR;
	}

	public void setCTR(String cTR) {
		CTR = cTR;
	}

	public String getCPC() {
		return CPC;
	}

	public void setCPC(String cPC) {
		CPC = cPC;
	}

	public String getBounce_rate() {
		return bounce_rate;
	}

	public void setBounce_rate(String bounce_rate) {
		this.bounce_rate = bounce_rate;
	}

	public String getAvg_session_duration() {
		return avg_session_duration;
	}

	public void setAvg_session_duration(String avg_session_duration) {
		this.avg_session_duration = avg_session_duration;
	}

}
