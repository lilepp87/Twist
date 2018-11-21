package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ReUp {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private ResourceBundle rb = ResourceBundle.getBundle("SM");

	public ReUp() {

	}

	public void goToReUp() throws Exception {
		myAccountFlow.clickLink("ReUp");
		myAccountFlow.clickLink("ReUp[1]");
	}
	
	public void goToSimpleMobileWeb() throws Exception {
		myAccountFlow.navigateTo(rb.getString("sm.homepage"));
	}

	public void enterPhoneNumberForPurchase() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		esn.setMin(min);
		myAccountFlow.typeInTextField(rb.getString("sm.PurchaseMinText"), min);
		myAccountFlow.pressSubmitButton(rb.getString("sm.continue"));
		TwistUtils.setDelay(15);
	}
	
	public void selectMinForPurchase() throws Exception {
		TwistUtils.setDelay(10);
		myAccountFlow.pressSubmitButton(rb.getString("sm.continue"));
		TwistUtils.setDelay(15);
	}
	
	public void enterPhoneAndPIN(String pinPart) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		esn.setMin(min);
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		storeRedeemData(pin);
		System.out.println("PIN:" + pin);
		TwistUtils.setDelay(10);
		myAccountFlow.typeInTextField("inputMin", min);
		myAccountFlow.typeInTextField("inputAirtimePin", pin);
		clickButton("default_submit_btn");
		TwistUtils.setDelay(15);
		if (isButtonVisible("Add Now")) {
			clickButton("Add Now");
			System.out.println("Adding now");
			esn.setActionType(6);
		} else if (isButtonVisible("Add to Simple Mobile Reserve™")) {
			clickButton("Add to Simple Mobile Reserve™");
			System.out.println("Adding to Simple Mobile stash");
			esn.setActionType(401);
		} else {
			System.out.println("Adding to Simple Mobile stash");
			esn.setActionType(401);
		}
	}

	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
		esn.setTransType("SM Redeem PIN");
	}

	public void clickButton(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName)) {
			myAccountFlow.pressButton(buttonName);
		} else {
			myAccountFlow.pressSubmitButton(buttonName);
		}
	}

	public boolean isButtonVisible(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName) || myAccountFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
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