package com.mhv.bean;

import java.sql.Timestamp;

public class User {

	private Long id;
	private String login;
	private String password;
	private String email;
	private String role;
	private Timestamp dateInscription;
	
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Timestamp getDateInscription() { return dateInscription; }
	public void setDateInscription(Timestamp dateInscription) { this.dateInscription = dateInscription; }

}
