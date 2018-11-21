package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class LogOut {

	private Browser browser;

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
	}

	public void tearDown() throws Exception {
		try {
			if (browser.link("log out").isVisible()) {
				browser.link("log out").click();
//				browser.close();
//				browser.open();
			} else {
				browser.navigateTo(OnDealerportal.UDP_URL);
				browser.link("log out").click();
//				browser.close();
//				browser.open();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		browser.close();
		browser.open();
	}

}