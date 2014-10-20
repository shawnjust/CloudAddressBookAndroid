package com.cloudaddressbook.wsclient;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.cloudaddressbook.beans.Result;
import com.cloudaddressbook.beans.User;

public class NetWorkHelper {
	private final String URL_BASE = "http://shawnjust93.oicp.net:8080/CloudAddressBook/services/user/";
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
		String httpUrl = URL_BASE + "regist?email=" + email + "&name=" + name
				+ "&password=" + password;
		Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		Result result = new Result();
		try {
			documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			document = documentBuilder.parse(httpUrl);
			result = new Result(document.getDocumentElement());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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

	public static String getValueSafely(Element element, String key, int index) {
		try {
			String result = element.getElementsByTagName(key).item(0)
					.getFirstChild().getNodeValue();
			// Log.e(key, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getValueSafely(Element element, String key) {
		return getValueSafely(element, key, 0);
	}
}
