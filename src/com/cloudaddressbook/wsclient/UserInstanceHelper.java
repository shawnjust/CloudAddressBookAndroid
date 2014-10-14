package com.cloudaddressbook.wsclient;

import java.util.HashMap;
import java.util.Map;

import com.cloudaddressbook.beans.User;

public class UserInstanceHelper {
	private static UserInstanceHelper instace = new UserInstanceHelper();

	public static UserInstanceHelper getInstance() {
		return instace;
	}

	private UserInstanceHelper() {
		users.put("petter@gmail.com", new User("Petter", "petter@gmail.com",
				"1234"));
		users.put("marry@qq.com", new User("Marry", "marry@qq.com", "1234"));
		users.put("max@163.com", new User("Max", "max@163.com", "1234"));
		users.put("shawn@qq.com", new User("shawn", "shawn@qq.com", "1234"));
	}

	private Map<String, User> users = new HashMap<String, User>();

	public Map<String, User> getUsers() {
		return users;
	}

	public User selfInformation;

	public User getSelfInformation() {
		return selfInformation;
	}

	public void setSelfInformation(User selfInformation) {
		this.selfInformation = selfInformation;
	}

}
