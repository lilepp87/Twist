package com.tracfone.context;

// JUnit Assert framework can be used for verification

import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;

public class OnTracFone {

	private WebDriver browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public OnTracFone(WebDriver browser) {
		this.browser = browser;
	}
	
	public void setUp() throws Exception {
		browser.get("https://sitcing.tracfone.com");
	}

	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
	}

}
