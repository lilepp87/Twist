package com.tracfone.twist.forgotpassword;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

import junit.framework.Assert;

public class ForgotPassword {

	private ActivationReactivationFlow activationPhoneFlow;

	public ForgotPassword() {
		
	}

	public void goToDealerPortalLoginAndClickOnForgotPassword() throws Exception {
		activationPhoneFlow.navigateTo("http://sit1.fastactportal.com/login");
		activationPhoneFlow.clickLink("Forgot Password");
	}

	public void enterUserNameAndClickReset(String user) throws Exception {
		activationPhoneFlow.typeInTextField("username", user);
		activationPhoneFlow.pressSubmitButton("Reset");
	}

	public void checkConfirmation() throws Exception {
		TwistUtils.setDelay(15);
		Assert.assertTrue(activationPhoneFlow.divVisible("An email has been sent to your email address. " +
				"Please follow instructions within."));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

}