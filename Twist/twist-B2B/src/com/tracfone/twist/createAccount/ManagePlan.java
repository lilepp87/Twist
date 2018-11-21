package com.tracfone.twist.createAccount;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ManagePlan {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	public ManagePlan() {

	}

	public void goToPhone() throws Exception {
		myAccountFlow.clickLink("My Phones & Plans");
	}
	
	public void changePlanTo(String plan) throws Exception {
		myAccountFlow.clickLink("Edit Plan");
		myAccountFlow.chooseFromSelect("/plan_100000.*/", plan);
		myAccountFlow.clickLink("continue to review");
	}

	public void finishChangingPlan() throws Exception {
		myAccountFlow.clickLink("save plan changes");
		Assert.assertTrue(false);
	}

	public void buyPlan(String plan) throws Exception {
		myAccountFlow.clickLink("Buy Airtime");
		myAccountFlow.chooseFromSelect("/plan_100000.*/", plan);
		myAccountFlow.clickLink("continue to payment");
	}

	public void finishBuyingPlan() throws Exception {
		myAccountFlow.selectRadioButton("paymentType");
		myAccountFlow.chooseFromSelect("ccPaymentSourceId", "/.*/");
		myAccountFlow.clickLink("continue to review");
		myAccountFlow.clickLink("submit order");
	}
	
	public void cancelPlan() throws Exception {
		myAccountFlow.clickLink("Cancel Plan");
		myAccountFlow.clickLink("Yes, cancel plan");
		Assert.assertTrue(false);
	}
	
	public void checkSuccessMessage() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(myAccountFlow.h1Visible("Thank you for your order!"));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}


}