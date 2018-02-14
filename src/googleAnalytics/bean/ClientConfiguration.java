package googleAnalytics.bean;

import javax.jdo.annotations.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "google_analytics_client_config")
public class ClientConfiguration {

	@Column(name = "client_stamp")
	private String client_stamp;

	@Column(name = "account_Id")
	private String accountId;

	@Column(name = "view_Id")
	private String viewId;

	@Column(name = "refresh_Token")
	private String refreshToken;

	@Column(name = "company_Name")
	private String company_Name;

	public String getClient_stamp() {
		return client_stamp;
	}

	public void setClient_stamp(String client_stamp) {
		this.client_stamp = client_stamp;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getCompany_Name() {
		return company_Name;
	}

	public void setCompany_Name(String company_Name) {
		this.company_Name = company_Name;
	}

}
