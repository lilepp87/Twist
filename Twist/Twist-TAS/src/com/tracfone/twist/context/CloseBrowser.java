package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;

public class CloseBrowser {

	private Browser browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public CloseBrowser(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
		browser.close();
	}

	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
	}

}
