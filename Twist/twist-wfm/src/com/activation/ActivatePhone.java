package com.activation;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
//import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
//import com.tracfone.twist.flows.tracfone.TracfoneFlows;
//import com.tracfone.twist.utils.ESNUtil;
//import com.tracfone.twist.utils.PhoneUtil;
//import com.tracfone.twist.utils.SimUtil;

public class ActivatePhone {

	@Autowired
	private TwistScenarioDataStore scenarioStore;
	private Browser browser;
//	private PhoneUtil phoneUtil;
//	private SimUtil simUtil;
//	private ESNUtil esnUtil;
//	private ActivationReactivationFlow activationPhoneFlow;

	public ActivatePhone(Browser browser) {
		this.browser = browser;
	}
	
/*	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}*/


	public void chooseToActivateBrandedPhone() throws Exception {
		browser.link("MY ACCOUNT").click();
		Thread.sleep(2000);
	}

}
