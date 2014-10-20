package com.example.utils.entities;

import java.io.Serializable;

public class Message  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	private String msg;
	private String date;
	private int type;
	public Message(int id, String userName, String msg, String date, int type) {
		super();
		this.id = id;
		this.userName = userName;
		this.msg = msg;
		this.date = date;
		this.type = type;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
