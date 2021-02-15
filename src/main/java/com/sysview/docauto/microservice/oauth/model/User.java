package com.sysview.docauto.microservice.oauth.model;

import java.io.Serializable;

public class User implements Serializable {

	private Long id;
	private String name;
	private String firstSurname;
	private String secondSurname;
	private String userName;
	private String password;
	private String email;
	private Boolean isActive;
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstSurname=" + firstSurname + ", secondSurname="
				+ secondSurname + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", isActive=" + isActive + ", role=" + role + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
