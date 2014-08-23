package com.warcgenerator.core.util;

public class Validator {
	public static boolean isNullOrEmpty(Boolean obj) {
		boolean nullOrEmpty = false;
		if (obj == null) {
			nullOrEmpty = true;
		}
		return nullOrEmpty;
	}
	public static boolean isNullOrEmpty(Integer obj) {
		boolean nullOrEmpty = false;
		if (obj == null) {
			nullOrEmpty = true;
		}
		return nullOrEmpty;
	}
	public static boolean isNullOrEmpty(String obj) {
		boolean nullOrEmpty = false;
		if (obj == null || obj.equals("")) {
			nullOrEmpty = true;
		}
		return nullOrEmpty;
	}
}
