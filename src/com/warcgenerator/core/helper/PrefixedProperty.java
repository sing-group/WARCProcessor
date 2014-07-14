package com.warcgenerator.core.helper;

import java.util.Properties;

public class PrefixedProperty extends Properties {
	private static final long serialVersionUID = 1L;

	public String getPropertyByGroup(String group, String key) {
        return getProperty(group + '.' + key);
    }
}