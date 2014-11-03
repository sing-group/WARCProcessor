package com.warcgenerator.core.logic;

public class LogicCallback {
	private String message;
	private Object[] params;
	
	public LogicCallback(String message, Object[] params) {
		this.message = message;
		this.params = params;
	}
	
	public LogicCallback(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	
	
}
