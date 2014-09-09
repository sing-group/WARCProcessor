package com.warcgenerator.gui.actions.common;

import org.apache.commons.lang.StringUtils;

public class RecentFileCBItem {
	private int id;
	private String name;
	private String path;
	public RecentFileCBItem(int id, String name, String path) {
		this.id = id;
		this.name = name;
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return id + " " + name + " [" + StringUtils.abbreviate(path, 25) + "]";
	}
}
