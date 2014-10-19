package com.cloudaddressbook.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserDetail implements Serializable {

	private static final long serialVersionUID = -4008347386678062763L;
	private String name;
	private String email;
	private Map<String, String> content;

	public UserDetail() {
		content = new HashMap<String, String>();
	}

	public UserDetail(String name, String email) {
		this.name = name;
		this.email = email;

		this.content = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public void setContent(Map<String, String> content) {
		this.content = content;
	}
}
