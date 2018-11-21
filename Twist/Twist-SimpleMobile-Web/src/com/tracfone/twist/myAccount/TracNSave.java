package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class TracNSave {

	private ActivationReactivationFlow activationPhoneFlow;

	public void checkForTracNSaveInsideMyAccount() throws Exception {
		activationPhoneFlow.clickLink("Trac ‘N’ Save");
		activationPhoneFlow.spanVisible("My Trac 'N' Save");
		activationPhoneFlow.clickLink("My Account");
		activationPhoneFlow.clickLink("Trac 'N' Save");
		activationPhoneFlow.spanVisible("My Trac 'N' Save");
		activationPhoneFlow.clickLink("My Account");
		activationPhoneFlow.clickLink("Sign Out");
	}

	public void checkForTracNSaveOutsideMyAccount() throws Exception {
		activationPhoneFlow.clickLink("Trac 'N' Save");
		activationPhoneFlow.spanVisible("My Trac 'N' Save");
		activationPhoneFlow.clickLink("My Account");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

}