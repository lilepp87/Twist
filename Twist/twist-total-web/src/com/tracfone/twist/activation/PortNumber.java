package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static Logger logger = LogManager.getLogger(PortNumber.class.getName());
	public PortNumber() {
		
	}

	public void selectTransferPhoneNumber() throws Exception {
		activationPhoneFlow.clickLink("Activate/Reactivate");
		activationPhoneFlow.clickLink("ACTIVATE");
		activationPhoneFlow.selectRadioButton("activation_option[3]");
		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void confirmPortMessage() throws Exception {
		activationPhoneFlow.selectCheckBox("checkbox_name");
		activationPhoneFlow.pressButton("ACCEPT");
	}

	public void enterNumberFromWithPart(String brand, String oldPart) throws Exception {
		String newEsn = esnUtil.getCurrentESN().getEsn();
		String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(oldPart, newEsn);
		esnUtil.getCurrentESN().setFromEsn(new ESN(oldPart, oldEsn));
		
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		
//		activationPhoneFlow.selectCheckBox("ckbox");
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		activationPhoneFlow.typeInTextField("input_phone_number", min);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}
	
	public void enterExternalNumberFrom(String brand) throws Exception {
		
		String min = TwistUtils.generateRandomMin();
		ESN fromEsn = new ESN("External", "External");
		fromEsn.setMin(min);
		esnUtil.getCurrentESN().setFromEsn(fromEsn);

		activationPhoneFlow.typeInTextField("portPhoneNumber", min);
		activationPhoneFlow.pressSubmitButton("portNumberButton");
		activationPhoneFlow.selectCheckBox("aceeptPort");
		activationPhoneFlow.chooseFromSelect("input_phone_type", "Wireless");
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		
		//activationPhoneFlow.typeInTextField("input_account_number", Long.toString(TwistUtils.createRandomLong(10000000, 90000000)));
		activationPhoneFlow.typeInTextField("input_account_number", "12345678-12345");
		activationPhoneFlow.typeInPasswordField("input_account_password", "1234");
		activationPhoneFlow.typeInTextField("txtfirstname", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("txtlastname", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("input_phone_number2", TwistUtils.generateRandomMin());
		//activationPhoneFlow.typeInTextField("input_ssn", Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		activationPhoneFlow.typeInTextField("txthouse", "241561 " + Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		activationPhoneFlow.chooseFromSelect("txtdirection", "S");
		activationPhoneFlow.typeInTextField("txtstreetname", "241561" + TwistUtils.createRandomUserId());
		activationPhoneFlow.chooseFromSelect("txtstreettype", "HWY");
		activationPhoneFlow.typeInTextField("txtunit", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("txtzip_code", "33165");
		activationPhoneFlow.chooseFromSelect("state", "FL");
		if(activationPhoneFlow.submitButtonVisible("CONTINUE[4]")){
			activationPhoneFlow.getBrowser().button("CONTINUE[4]").focus();
			activationPhoneFlow.pressSubmitButton("CONTINUE[4]");
			TwistUtils.setDelay(3);
		}
	}

	public void finishPort() throws Exception {
		TwistUtils.setDelay(20);//steps to complete your Activation
		//Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
		Assert.assertTrue(activationPhoneFlow.h1Visible("steps to complete your Activation"));
		activationPhoneFlow.pressSubmitButton("Continue");		
		
		ESN currESN = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(currESN.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(currESN.getEsn());
		
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);	
		}
		
		currESN.setTransType("TW Port In");
		phoneUtil.checkUpgrade(currESN.getFromEsn(), currESN);
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
	
}