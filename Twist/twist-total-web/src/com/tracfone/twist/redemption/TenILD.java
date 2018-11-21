package com.tracfone.twist.redemption;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class TenILD {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static Logger logger = Logger.getLogger(TenILD.class);
	
	public TenILD() {
	
	}

	public void goToInternationalCalling() throws Exception {
		activationPhoneFlow.clickLink("/International Calling.*?/");
	}

	public void buy10DollarIldCard() throws Exception {
	//	activationPhoneFlow.pressSubmitButton("buy");
	}

	public void enterPhoneNumberAndQuantity (String esnPart, String numIldCard) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
//		String esnStr = phoneUtil.getActiveEsnByPartNumber(esnPart);
//		phoneUtil.clearOTAforEsn(esnStr);
		String min = esnUtil.getCurrentESN().getMin();
//		ESN esn = new ESN(esnPart, esnStr);
//		storeRedeemInfo(esn);
//		esnUtil.setCurrentESN(esn);
		TwistUtils.setDelay(10);
//		logger.info(esn);
//		logger.info(min);
		activationPhoneFlow.typeInTextField("min", min);
		activationPhoneFlow.typeInTextField("qty", numIldCard);
//		activationPhoneFlow.pressSubmitButton("BUY");
		activationPhoneFlow.pressSubmitButton("Add Now");
		TwistUtils.setDelay(10);
	}

	private void storeRedeemInfo(ESN esn) {
		esn.setTransType("Total Wireless Buy $10");
		esn.setActionType(6);
		esn.setRedeemType(false);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
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

	public void guestCheckout() throws Exception {
		TwistUtils.setDelay(8);
		if (!activationPhoneFlow.submitButtonVisible("GUEST CHECKOUT")) {
			TwistUtils.setDelay(30);
		}
		activationPhoneFlow.pressSubmitButton("GUEST CHECKOUT");
		TwistUtils.setDelay(5);
	}

	public void enterPhoneNumber(String esnPart) throws Exception {
		String esnStr = phoneUtil.getActiveEsnByPartNumber(esnPart);
		phoneUtil.clearOTAforEsn(esnStr);
		String min = phoneUtil.getMinOfActiveEsn(esnStr);
		ESN esn = new ESN(esnPart, esnStr);
		storeRedeemInfo(esn);
		esnUtil.setCurrentESN(esn);
		TwistUtils.setDelay(10);
		logger.info(esn);
		logger.info(min);
		activationPhoneFlow.typeInTextField("min", min);
//		activationPhoneFlow.pressSubmitButton("BUY");
//		activationPhoneFlow.pressSubmitButton("Add Now");
		activationPhoneFlow.pressSubmitButton("Enroll in Instant Low Balance Refill");
	}

	public void confirmationFor10ILDEnrollment() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		List<String> productIdList = phoneUtil.getProductIDbyESN(esn.getEsn());
		for(String productId : productIdList){
			logger.info("Product Id: " + productId);
			Assert.assertTrue((productId.equalsIgnoreCase("TW_ILD_10") || productId.equalsIgnoreCase("BPTW_ILD_10")));
		}
	}
}
