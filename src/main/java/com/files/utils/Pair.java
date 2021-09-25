package com.files.utils;

public class Pair {
	 private Object data;
	 private String msg;
	public Pair(Object data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
