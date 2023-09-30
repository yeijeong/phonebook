package com.nasa.phonebook.dto;

public class PhoneBookDto {
	private String	loginid;
	private String	loginpw;
	private String	loginph;
	private String	username;
	private String	userph;
	private String	useraddr;
	private String	groupid;
	private String	groupname;
	
	@Override
	public String toString() {
		return "PhoneBookDto [loginid=" + loginid + ", loginpw=" + loginpw + ", loginph=" + loginph + ", username="
				+ username + ", userph=" + userph + ", useraddr=" + useraddr + ", groupid=" + groupid + ", groupname="
				+ groupname + "]";
	}
	
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getLoginpw() {
		return loginpw;
	}
	public void setLoginpw(String loginpw) {
		this.loginpw = loginpw;
	}
	public String getLoginph() {
		return loginph;
	}
	public void setLoginph(String loginph) {
		this.loginph = loginph;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserph() {
		return userph;
	}
	public void setUserph(String userph) {
		this.userph = userph;
	}
	public String getUseraddr() {
		return useraddr;
	}
	public void setUseraddr(String useraddr) {
		this.useraddr = useraddr;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
