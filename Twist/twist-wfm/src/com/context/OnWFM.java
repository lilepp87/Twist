package com.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
//import com.tracfone.twist.flows.tracfone.MyAccountFlow;
//import com.tracfone.twist.flows.tracfone.TracfoneFlows;
//import com.tracfone.twist.utils.ESNUtil;
//import com.tracfone.twist.utils.TwistUtils;

public class OnWFM {

	private Browser browser;
//	private MyAccountFlow myAccountFlow;
//	private ESNUtil esnUtil;
	public static final String WFM_URL = "http://sitz.myfamilymobile.tracfone.com";

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public OnWFM(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {
		Thread.sleep(3000);
		browser.navigateTo(WFM_URL);
	}

	public void tearDown() throws Exception {
//		esnUtil.clearCurrentESN();
	}

//	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
//		myAccountFlow = tracfoneFlows.myAccountFlow();
//	}
	
//	public void setEsnUtil(ESNUtil esnUtil) {
//		this.esnUtil = esnUtil;
//	}

}
