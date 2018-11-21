package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class Upgrade {

	private Browser browser;

	public Upgrade(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
	}

	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
		browser.navigateTo("http://sit1.net10.com");
	}

}