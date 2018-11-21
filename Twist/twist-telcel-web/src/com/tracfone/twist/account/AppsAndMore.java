package com.tracfone.twist.account;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class AppsAndMore {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();

	public AppsAndMore() {

	}

	public void checkForAppsAndMoreInsideMyAccount() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.clickLink(properties.getString("appsMore"));
		activationPhoneFlow.typeInTextField("param_min", min);
		activationPhoneFlow.clickLink("continue_btn");
		activationPhoneFlow.typeInTextField("param_min_lb", min);
		activationPhoneFlow.pressSubmitButton("default_submit_btn2");
		//TODO Check that you are actually on the right page
		activationPhoneFlow.clickLink(properties.getString("back"));
	}

	public void checkForAppsAndMoreOutsideMyAccount() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("signOut"));
		checkForAppsAndMoreInsideMyAccount();
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}