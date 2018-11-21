package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ExecutionException;

public class LogOut {

	private final Browser browser;

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
		if (browser.exists(browser.link("Salir"))) {
			browser.link("Salir").click();
		} else {
			try {
				browser.navigateTo("https://env6.spanish.net10.dev/direct/MyAccount");
				if (browser.exists(browser.link("Salir"))) {
					browser.link("Salir").click();
				}
			} catch (ExecutionException ex) {}
		}
	}

}