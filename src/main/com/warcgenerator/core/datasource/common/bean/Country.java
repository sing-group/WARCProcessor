package com.warcgenerator.core.datasource.common.bean;


public class Country implements Comparable<Country> {
	private String code;
	private String name;

	public Country() {

	}

	public Country(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		Country c = (Country)obj;
		return (code.equals(c.code) &&
				name.equals(c.name));
	}

	@Override
	public int compareTo(Country arg0) {
		return this.getName().compareTo(arg0.getName());
	}
}
