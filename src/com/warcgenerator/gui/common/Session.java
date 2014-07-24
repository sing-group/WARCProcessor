package com.warcgenerator.gui.common;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private static Map<String, Object> session;
	
	private static Map<String, Object> getSession() {
		if (session == null) {
			session = new HashMap<String, Object>();
		}
		return session;
	}
	
	public static void add(String key, Object value) {
		getSession().put(key, value);
	}
	
	public static Object get(String key) {
		return getSession().get(key);
	}
	
}
