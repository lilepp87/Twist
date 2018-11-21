package com.tracfone.twist.enrollment;

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

public class Enrollment {


	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public Enrollment() {
		
	}

	public void enterEmailFnLnAndPin(String pinpart) throws Exception {
		String email = TwistUtils.createRandomEmail();
		String pin= phoneUtil.getNewPinByPartNumber(pinpart);
		//activationPhoneFlow.typeInTextField("email",email);
		activationPhoneFlow.getBrowser().emailbox("email").setValue(email);
		activationPhoneFlow.typeInTextField("firstName", "first");
		activationPhoneFlow.typeInTextField("lastName", "last");
		activationPhoneFlow.typeInTextField("pin", pin);
	}

	public void enterCreditCard(String cardType) throws Exception {
		String cardNum = TwistUtils.generateCreditCard(cardType);
		activationPhoneFlow.chooseFromSelect("cardType", cardType);
		activationPhoneFlow.typeInTextField("account_number", cardNum);
		activationPhoneFlow.typeInTextField("cvv", "123");
		activationPhoneFlow.typeInTextField("address", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("state", "CA");
		activationPhoneFlow.typeInTextField("bilZip", "94043");
		activationPhoneFlow.pressSubmitButton("Continue[1]");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}
	
	public void confirmActivationWithEnrollment(String cellTech) throws Exception {
		Assert.assertTrue(activationPhoneFlow.h2Visible("Steps to Complete your Activation"));
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, cellTech, "New", "UDP Activation with Enroll");
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