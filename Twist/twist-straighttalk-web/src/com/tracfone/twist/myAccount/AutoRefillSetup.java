package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import org.springframework.util.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;

public class AutoRefillSetup {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;

	public AutoRefillSetup() {

	}

	public void goToEnrollAutoRefillOption() throws Exception {
		myAccountFlow.clickLink("Enroll Existing Customer in Auto-Refill");
	}

	public void loginForEsn(String partNumber) throws Exception {
		ESN esn = esnUtil.popRecentActiveEsn(partNumber);
		esnUtil.setCurrentESN(esn);
		myAccountFlow.typeInTextField("userID", esn.getEmail());
		myAccountFlow.typeInPasswordField("password", esn.getPassword());
		myAccountFlow.pressSubmitButton("default_submit_btn");
	}

	public void enrollInAutoRefill() throws Exception {
		myAccountFlow.selectRadioButton("selected_serial_number");
		myAccountFlow.pressSubmitButton("Continue");
	}

	public void orderSummary() throws Exception {
		Assert.isTrue(myAccountFlow.h2Visible("ORDER SUMMARY"));
		myAccountFlow.pressSubmitButton("Done");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}