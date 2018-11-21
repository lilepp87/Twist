package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.util.Properties;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ExecutionException;

public class LogOut {

	private final Browser browser;
	private final Properties properties = new Properties("Net10");

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {

	}

	public void tearDown() throws Exception {
		if (browser.exists(browser.link("Sign Out"))) {
			browser.link("Sign Out").click();
		} else {
			try {
				browser.navigateTo(properties.getString("Twist.MyAccount"));
				if (browser.exists(browser.link("Sign Out"))) {
					browser.link("Sign Out").click();
				}
			} catch (ExecutionException ex) {}
		}
		browser.navigateTo(properties.getString("Twist.Net10URL"));
	}
}