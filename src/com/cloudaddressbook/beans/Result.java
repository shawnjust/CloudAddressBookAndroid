package com.cloudaddressbook.beans;

import org.w3c.dom.Element;

import com.cloudaddressbook.wsclient.NetWorkHelper;

public class Result {
	public boolean success = false;
	public int code = 0;
	public String message;
	public Object object;

	public Result() {

	}

	public Result(Element element) {
		success = Boolean.parseBoolean((String) NetWorkHelper.getValueSafely(element, "isSuccess"));
		message = NetWorkHelper.getValueSafely(element, "message");
	}

	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
