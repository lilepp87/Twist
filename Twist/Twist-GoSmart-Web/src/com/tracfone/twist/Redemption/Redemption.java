package com.tracfone.twist.Redemption;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class Redemption {
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public Redemption() {
	}

	public void goToRefillAndAddNow() throws Exception {
		activationPhoneFlow.clickLink(props.getString("Redeem.RefillInsideAccount"));
		activationPhoneFlow.clickLink(props.getString("Redeem.AddPlan"));
		
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
	public void enterPinForAnd(String pinPart, String redeemType) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
	
		activationPhoneFlow.typeInTextField(props.getString("Redeem.NewAirtimeText"), pin);
		activationPhoneFlow.pressButton(props.getString("Redeem.AddCard"));
		
		if(activationPhoneFlow.submitButtonVisible(props.getString("Redeem.StashNow"))){
			if(redeemType.equalsIgnoreCase("Add Now")){
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.RefillNow"));
			esn.setActionType(6);
		}else{
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.StashNow"));
			esn.setActionType(401);
		}
	  }
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Go Smart Redeem PIN");
		phoneUtil.checkRedemption(esn);
	}

	public void checkSummaryAndProcess() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h2Visible(props.getString("Activate.OrderSummary")));
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
	}

	public void goToRefillAccount() throws Exception {
		activationPhoneFlow.clickLink("Refill Account");
	}

	public void enterMinPinAnd(String pinPart, String redeemType) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		
		activationPhoneFlow.typeInTextField(props.getString("Redeem.InputMin"), min);
		activationPhoneFlow.typeInTextField(props.getString("Redeem.InputPin"), pin);
		activationPhoneFlow.pressButton(props.getString("Activate.continue1"));
		
		if(activationPhoneFlow.submitButtonVisible(props.getString("Redeem.StashNow"))){
			if(redeemType.equalsIgnoreCase("Add Now")){
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.RefillNow"));
			esn.setActionType(6);
		}else{
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.StashNow"));
			esn.setActionType(401);
		}
	  }
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Go Smart Redeem PIN");
		phoneUtil.checkRedemption(esn);
		
	}
}
