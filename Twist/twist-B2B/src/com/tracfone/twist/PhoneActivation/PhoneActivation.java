package com.tracfone.twist.PhoneActivation;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

// JUnit Assert framework can be used for verification

public class PhoneActivation {

	private ActivationReactivationFlow activationPhoneFlow;

	public PhoneActivation() {

	}

	public void goToActivate() throws Exception {
		activationPhoneFlow.clickLink("Activate/ Reactivate");
	}
	
	public void activateMyNet10Phone() throws Exception {
		activationPhoneFlow.selectRadioButton("activate");
		activationPhoneFlow.clickLink("CONTINUE");
	}
	
	public void skipRegister() throws Exception {
		activationPhoneFlow.selectRadioButton("option_account_id");
		activationPhoneFlow.clickLink("SKIP");
		activationPhoneFlow.clickLink("CONTINUE");	
	}
	
	public void activatePhoneWithEsn(String esn) throws Exception {
		activationPhoneFlow.typeInTextField("userinputesn", esn);
		activationPhoneFlow.clickLink("CONTINUE");
		Assert.assertTrue(activationPhoneFlow.linkVisible("www.clearway.com"));
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
}