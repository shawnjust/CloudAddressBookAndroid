package com.example.utils.entities;

import java.io.Serializable;
import java.util.Map;

import com.cloudaddressbook.beans.UserDetail;

public class Message  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String email;
	private String userName;
	private String msg;
	private String date;
	private int type;
	public Message(UserDetail user) {
		userName = user.getName();
		msg = "请求加你为好友";
		email = user.getEmail();
		type = 0;
	}
	

	public Message(String email, String userName, String msg, String date,
			int type) {
		super();
		this.email = email;
		this.userName = userName;
		this.msg = msg;
		this.date = date;
		this.type = type;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
