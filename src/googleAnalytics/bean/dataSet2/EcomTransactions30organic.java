package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_ecom_transactions_30_organic")
public class EcomTransactions30organic {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1)
	private int year;

	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;

	@PrimaryKeyColumn(name = "product_sku", ordinal = 2)
	private String productSku;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 3)
	private int dayOfYear;

	@Column(value = "product_name")
	private String productName;
	@Column(value = "api_date")
	private String date;

	@Column(value = "unique_purchases")
	private String uniquePurchases;

	@Column(value = "item_quantity")
	private String itemQuantity;

	@Column(value = "item_revenue")
	private String itemRevenue;

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

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUniquePurchases() {
		return uniquePurchases;
	}

	public void setUniquePurchases(String uniquePurchases) {
		this.uniquePurchases = uniquePurchases;
	}

	public String getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getItemRevenue() {
		return itemRevenue;
	}

	public void setItemRevenue(String itemRevenue) {
		this.itemRevenue = itemRevenue;
	}

}
