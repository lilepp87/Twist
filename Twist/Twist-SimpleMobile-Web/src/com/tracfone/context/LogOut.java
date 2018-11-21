package com.tracfone.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class LogOut {

	private static Browser browser;

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		logout();
	}

	public void tearDown() throws Exception {
		logout();
		browser.close();
		browser.open();
		
	}

	public static void logout() {
		try {
			if (browser.link("sign_in_out_link").isVisible()) {
				browser.link("sign_in_out_link").click();
			} else {
				browser.navigateTo(OnSimpleMobileHomePage.SM_URL);
				if (browser.link("sign_in_out_link").isVisible()) {
					browser.link("sign_in_out_link").click();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}