package googleAnalytics.bean;

public class CassandraCredentials {

	private String contactpoint;
	private String db;
	private int port;

	public CassandraCredentials(String contactpoint, String db, int port) {
		this.contactpoint = contactpoint;
		this.port = port;
		this.db = db;
	}

	public String getContactpoint() {
		return contactpoint;
	}

	public void setContactpoint(String contactpoint) {
		this.contactpoint = contactpoint;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
}
