package com.cloudaddressbook.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.cloudaddressbook.wsclient.NetWorkHelper;

public class UserDetail implements Serializable {

	private static final long serialVersionUID = -4008347386678062763L;
	private String name;
	private String email;
	private Map<String, String> content = new HashMap<String, String>();

	public UserDetail() {
	}

	public UserDetail(Element element) {
		name = NetWorkHelper.getValueSafely(element, "name");
		email = NetWorkHelper.getValueSafely(element, "email");
		try {
			Element ele = (Element) element.getElementsByTagName("map").item(0);
			NodeList list = ele.getElementsByTagName("entry");
			for (int i=0; i<list.getLength(); i++) {
				Element node = (Element) list.item(i);
				String key = NetWorkHelper.getValueSafely(node, "key");
				String value = NetWorkHelper.getValueSafely(node, "value");
				content.put(key, value);
			}
		} catch(Exception e) {
			
		}
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
