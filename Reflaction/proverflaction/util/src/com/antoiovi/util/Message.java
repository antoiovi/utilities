package com.antoiovi.util;

import java.io.Serializable;

public class Message implements Serializable{

	private String msg;
	
	
	public Message() {
		super();
	}

	public void printMessage() {
		System.out.println(msg);
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
