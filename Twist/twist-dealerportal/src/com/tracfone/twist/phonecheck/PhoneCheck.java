package com.tracfone.twist.phonecheck;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

public class PhoneCheck {
	
	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	

	public PhoneCheck() {
		
	}

	public void enterPhoneNumber() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String phone = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		phoneUtil.clearOTAforEsn(esn.getEsn());

		activationPhoneFlow.typeInTextField("phoneNumberDisplay", phone);
		activationPhoneFlow.getBrowser().execute("document.getElementById(\"phoneNumber\").value = \"" + phone + "\"");

		activationPhoneFlow.getBrowser().submit("Continue").focus();
		activationPhoneFlow.pressSubmitButton("Continue");
	}
	
	public void checkPhoneStatus() throws Exception {
		TwistUtils.setDelay(15);
		Assert.assertTrue(activationPhoneFlow.divVisible("52 - ACTIVE"));
		activationPhoneFlow.pressSubmitButton("Continue");
	}
	
	public void goToPhoneCheck() throws Exception {
		activationPhoneFlow.clickLink("Account Status");
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