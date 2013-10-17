package com.mhv.bean;

public class DatabaseServerConnection {

	private Long id;
	private String ipAddress;
	private Integer port;
	private String databaseName; 
	private String userName;
	private String password;
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getIpAddress() { return ipAddress; }
	public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
	public Integer getPort() { return port; }
	public void setPort(Integer port) { this.port = port; }
	public String getDatabaseName() { return databaseName; }
	public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
}
