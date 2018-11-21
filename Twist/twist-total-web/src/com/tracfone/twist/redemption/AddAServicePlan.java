package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;
import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AddAServicePlan {

	private static final String QUEUED = "QUEUED";
	private static PhoneUtil phoneUtil;
	private PaymentFlow paymentFlow;
	private static final String CDMA = "CDMA";
	private ESNUtil esnUtil;
	private static Logger logger = LogManager.getLogger(AddAServicePlan.class.getName());
	public AddAServicePlan() {

	}

	public void selectAddAServicePlan() throws Exception {
		paymentFlow.clickLink("Add Plan");
	}
	
	public void goToRefill() throws Exception {
		paymentFlow.clickLink("Refill Now");
	}

	public void enterPhone() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(3);
		paymentFlow.typeInTextField("phone_number1", esn.getMin());
		TwistUtils.setDelay(2);
	}

	public void enterPinAndAdd(String pinNumber, String howToAdd) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		ESN esn = esnUtil.getCurrentESN();
		// paymentFlow.pressButton("ADD");
		paymentFlow.typeInTextField("pin_number", newPin);
		// paymentFlow.pressSubmitButton("redeemPinButton");
		paymentFlow.clickLink("continue");
		TwistUtils.setDelay(15);
		if (QUEUED.equalsIgnoreCase(howToAdd)) {
			if (buttonVisible("end of service")) {

				paymentFlow.pressSubmitButton("end of service");
			}
			esn.setActionType(401);
		} else {
			if (buttonVisible("apply now")) {
				paymentFlow.pressSubmitButton("apply now");
			}

			else if (buttonVisible("apply now[1]")) {
				paymentFlow.pressSubmitButton("apply now[1]");
			}
			esn.setActionType(6);
		}
		/*if (QUEUED.equalsIgnoreCase(howToAdd)) {
			if (buttonVisible("ADD TO RESERVE[1]")) {
				pressButton("ADD TO RESERVE[1]");
			} else if (buttonVisible("ADD TO RESERVE")) {
				pressButton("ADD TO RESERVE");
			}
			esn.setActionType(401);
		} else {
			if (buttonVisible("ADD NOW[1]")) {
				pressButton("ADD NOW[1]");
			} else if (buttonVisible("ADD NOW")) {
				pressButton("ADD NOW");
			}*/
	
		TwistUtils.setDelay(15);
		Assert.assertTrue(paymentFlow.h3Visible("SERVICE SUMMARY") || paymentFlow.h3Visible("service summary"));
		pressButton("continue");
		storeRedeemData(esn, newPin);
		phoneUtil.checkRedemption(esn);
	

	}
	public void checkRedemption() throws Exception {
		//TwistUtils.setDelay(10);
		//Assert.assertTrue(paymentFlow.h3Visible("SERVICE SUMMARY") || paymentFlow.h3Visible("service summary") || paymentFlow.divVisible("SERVICE SUMMARY"));
		//Assert.assertTrue(paymentFlow.cellVisible("Your Service Plan was successfully added. You must keep your device(s) turned ON to receive your benefits."));
		//pressButton("continue");
		
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Total Wireless Add Plan");
		esn.setPin(pin);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	private void pressButton(String button) {
		if (paymentFlow.buttonVisible(button)) {
			paymentFlow.pressButton(button);
		} else if (paymentFlow.submitButtonVisible(button)) {
			paymentFlow.pressSubmitButton(button);
		}
	}
	
	private boolean buttonVisible(String button) {
		return paymentFlow.buttonVisible(button) || paymentFlow.submitButtonVisible(button);
	}

	public void enterPhoneAndPinCard(String pinNum) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String pin = phoneUtil.getNewPinByPartNumber(pinNum);
		String MIN = esn.getMin();
	    paymentFlow.typeInTextField("phone_number1", MIN);
	   // paymentFlow.pressButton("ADD");
	    paymentFlow.typeInTextField("pin_number", pin);
	    paymentFlow.clickLink("continue");
	    System.out.println(paymentFlow.divVisible("Your Service Plan was successfully added. You must keep your device(s) turned ON to receive your benefits."));
	    System.out.println(paymentFlow.tableHeaderVisible("Your Service Plan was successfully added. You must keep your device(s) turned ON to receive your benefits."));
	}

	public void completeTheProcess(String status) throws Exception {
	    TwistUtils.setDelay(20);
	    finishPhoneActivation(status);
	    Assert.assertTrue(paymentFlow.divVisible("Your Service Plan was successfully added. You must keep your device(s) turned ON to receive your benefits."));
        paymentFlow.clickLink("continue");
	}

	public void finishPhoneActivation(String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		if (currEsn.getTransType().isEmpty()) {
			currEsn.setTransType("TW Web Activate Phone");
		}
		TwistUtils.setDelay(30);
		esnUtil.addRecentActiveEsn(currEsn, CDMA, status, currEsn.getTransType());
		currEsn.clearTestState();
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}


}