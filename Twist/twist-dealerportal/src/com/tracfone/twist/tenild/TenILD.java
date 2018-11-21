package com.tracfone.twist.tenild;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class TenILD {
	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public TenILD() {
		
	}

	public void goTo10DollarILD() throws Exception {
		activationPhoneFlow.clickLink("Add PAY-AS-YOU-GO ILD");
	}

	public void enterPhoneAndILDPIN(String pinpart) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String phone = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		String pin = phoneUtil.getNewPinByPartNumber(pinpart);
		phoneUtil.clearOTAforEsn(esn.getEsn());
		
		activationPhoneFlow.typeInTextField("phoneNumberDisplay", phone);
//		activationPhoneFlow.getBrowser().accessor("document.frmRedemption.phoneNumber").setValue(phone);
		activationPhoneFlow.typeInTextField("redemptionPin", pin);
		activationPhoneFlow.getBrowser().submit("Continue").focus();
		activationPhoneFlow.pressSubmitButton("Continue");
		storeRedeemData(pin);
	}
	
	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
		esn.setTransType("UDP $10 ILD");
	}

	public void checkConfirmation() throws Exception {
		TwistUtils.setDelay(20);
		if (!activationPhoneFlow.h2Visible("ILD Redemption Completed")) {
			TwistUtils.setDelay(45);
		}
		Assert.assertTrue(activationPhoneFlow.h2Visible("ILD Redemption Completed"));
		activationPhoneFlow.pressSubmitButton("Continue");
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
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
	
}