package com.tracfone.twist.b2c;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class CSR {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;

	public CSR() {

	}
	
	public void selectGuestUser() throws Exception {
		myAccountFlow.clickLink("Place Order (Guest)");
	}

	public void loginAsCSR() throws Exception {
		myAccountFlow.typeInTextField("logonId", "testcsr8");
		myAccountFlow.typeInPasswordField("logonPassword", "abc123");
		myAccountFlow.clickDivMessage("Sign In");
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}