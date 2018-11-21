package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.util.Properties;

import net.sf.sahi.client.Browser;

public class LogOut {

	private final Browser browser;
	
	private Properties properties = new Properties();

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		// This method is executed before the scenario execution starts.
	}

	public void tearDown() throws Exception {
		try {
			logout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void logout() {
		if (browser.exists(browser.link(properties.getString("signOut")))) {
			browser.link(properties.getString("signOut")).click();
		} else {
			browser.navigateTo(properties.getString("TC_HOME"));
			if (browser.exists(browser.link(properties.getString("signOut")))) {
				browser.link(properties.getString("signOut")).click();
			}
		}
	}
}
