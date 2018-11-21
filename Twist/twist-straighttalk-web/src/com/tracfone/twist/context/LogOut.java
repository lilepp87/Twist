package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class LogOut {

	private final Browser browser;

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
//		try {
//			logout();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	public void tearDown() throws Exception {
		try {
			logout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
//
	private void logout() {
		if (browser.isVisible(browser.link("logout"))) {
			browser.link("logout").click();
		} else {
			browser.navigateTo(OnStraighttalkHomePage.URL);
			if (browser.isVisible(browser.link("logout"))) {
				browser.link("logout").click();
			}
		}
	}
}