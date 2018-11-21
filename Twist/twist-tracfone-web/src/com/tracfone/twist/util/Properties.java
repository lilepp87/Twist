package com.tracfone.twist.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Properties {

	private final ResourceBundle RESOURCE_BUNDLE;

	public Properties() {
		RESOURCE_BUNDLE = ResourceBundle.getBundle("Tracfone");
	}

	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}