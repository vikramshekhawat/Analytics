package googleAnalytics.bean.dataSet2;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "google_analytics_top_products")
public class TopProducts {

	@PrimaryKeyColumn(name = "client_stamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String clientStamp;

	@PrimaryKeyColumn(name = "year", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int year;

	

	@Column(value = "start_date")
	private String startDate;
	@Column(value = "end_date")
	private String endDate;

	@Column(value = "api_date")
	private String date;

	@PrimaryKeyColumn(name = "product_sku", ordinal = 3)
	private String ProductSku;
	@PrimaryKeyColumn(name = "day_of_year", ordinal = 4)
	private int dayOfYear;


	@Column(value = "product_name")
	private String ProductName;

	@Column(value = "unique_purchases")
	private String UniquePurchases;

	@Column(value = "item_quantity")
	private String ItemQuantity;

	@Column(value = "item_revenue")
	private String ItemRevenue;

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
		return ProductSku;
	}

	public void setProductSku(String productSku) {
		ProductSku = productSku;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getUniquePurchases() {
		return UniquePurchases;
	}

	public void setUniquePurchases(String uniquePurchases) {
		UniquePurchases = uniquePurchases;
	}

	public String getItemQuantity() {
		return ItemQuantity;
	}

	public void setItemQuantity(String itemQuantity) {
		ItemQuantity = itemQuantity;
	}

	public String getItemRevenue() {
		return ItemRevenue;
	}

	public void setItemRevenue(String itemRevenue) {
		ItemRevenue = itemRevenue;
	}

}
