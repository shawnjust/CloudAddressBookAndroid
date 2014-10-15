package com.cloudaddressbook.wsclient;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.cloudaddressbook.beans.Result;
import com.cloudaddressbook.beans.User;

public class NetWorkHelper {
	private static NetWorkHelper instance = new NetWorkHelper();

	public static NetWorkHelper getInstance() {
		return instance;
	}

	public String retrieveDataFromNet(String httpUrl) {
		HttpGet httpRequest = new HttpGet(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			} else {
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Result regist(String email, String name, String password) {
		Map<String, User> map = UserInstanceHelper.getInstance().getUsers();
		if (map.containsKey(email)) {
			return new Result(false, "该邮箱地址已经被注册");
		} else {
			map.put(email, new User(name, email, password));
			return new Result(true, "注册成功");
		}
	}

	public Result login(String email, String password) {
		Map<String, User> map = UserInstanceHelper.getInstance().getUsers();
		if (map.containsKey(email)) {
			User user = map.get(email);
			if (user.getPassword().equals(password)) {
				return new Result(true, "登陆成功");
			} else {
				return new Result(false, "密码或账号错误");
			}
		} else {
			return new Result(false, "账号不存在");
		}
	}
}
