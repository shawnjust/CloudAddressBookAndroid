package com.example.utils.entities;

import java.io.Serializable;
/**
 * 询盘信息（概览列表页面）数据结构
 */
public class XunPanItem  implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String productName;
	private String time;
	private int state;
	private String country;
	private String language;
	private int followTime;
	private String eMail;
	private String accountType;
	
	
	
	
	public XunPanItem(String productName, String time, int state,
			String country, String language, int followTime, String eMail,
			String accountType) {
		super();
		this.productName = productName;
		this.time = time;
		this.state = state;
		this.country = country;
		this.language = language;
		this.followTime = followTime;
		this.eMail = eMail;
		this.accountType = accountType;
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getState() {
		return state;
	}
	public void getState(int state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getFollowTime() {
		return followTime;
	}
	public void setFollowTime(int followTime) {
		this.followTime = followTime;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
