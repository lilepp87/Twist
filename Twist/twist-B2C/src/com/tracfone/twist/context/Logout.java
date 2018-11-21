package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class Logout {

	private Browser browser;

	public Logout(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
	}

	public void tearDown() throws Exception {
		if (browser.link("Sign Out").isVisible()) {
			browser.link("Sign Out").click();
		}
		browser.close();
	}

}