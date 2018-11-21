package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class OnDealerportal {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	public static final String UDP_URL = "https://sit1.fastactportal.com";

	public OnDealerportal() {
		
	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(UDP_URL + "/login");
		myAccountFlow.typeInTextField("j_username", "admin@aol.com");
		myAccountFlow.typeInPasswordField("j_password", "abc123");
		myAccountFlow.pressSubmitButton("Login");
		TwistUtils.setDelay(5);
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}