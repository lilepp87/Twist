package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.io.EOFException;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ActivatePhone activatePhone;
	
	private Properties properties = new Properties();

	public PortNumber() {
		
	}

	public void selectTransferPhoneNumber() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("activeReactive"));
		activationPhoneFlow.selectRadioButton("activation_option[3]");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void selectUpgradePhone() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("activeReactive"));
		activationPhoneFlow.selectRadioButton("activation_option[2]");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void confirmPortMessage() throws Exception {
		activationPhoneFlow.selectCheckBox("existing_chkbox_selection");
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}
	
	public void enterNumberFromWithPart(String brand, String fromPart) throws Exception {
		String newEsn = esnUtil.getCurrentESN().getEsn();
		String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(fromPart, newEsn);
		esnUtil.getCurrentESN().setFromEsn(new ESN(fromPart, oldEsn));
		
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		activationPhoneFlow.typeInTextField("input_phone_number", min);
		//activationPhoneFlow.selectCheckBox("ckbox");
		if (activationPhoneFlow.textboxVisible("__first_name")){
		activationPhoneFlow.typeInTextField("__first_name", "TwistFirstName");
		activationPhoneFlow.typeInTextField("__last_name", "TwistLastName");
		activationPhoneFlow.typeInTextField("__phone_number2", min);
		activationPhoneFlow.typeInTextField("__address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("__city", "Mountain View");
		activationPhoneFlow.chooseFromSelect("__state", "CA");
		activationPhoneFlow.typeInTextField("__zip_code", "94043");
		activationPhoneFlow.typeInTextField("__country", "USA");
		activationPhoneFlow.typeInTextField("__confirm_email", TwistUtils.createRandomEmail());
		activationPhoneFlow.selectCheckBox("ckbx_copy_to_contact");		
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		}
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}
	public void enterExternalNumberFromAndZip(String brand, String zip) throws Exception {
		String min = TwistUtils.generateRandomMin();
		TwistUtils.setDelay(15);
		activationPhoneFlow.typeInTextField("input_phone_number", min);
		//activationPhoneFlow.selectCheckBox("ckbox");
		if (activationPhoneFlow.textboxVisible("__first_name")){
		activationPhoneFlow.typeInTextField("__first_name", "TwistFirstName");
		activationPhoneFlow.typeInTextField("__last_name", "TwistLastName");
		activationPhoneFlow.typeInTextField("__phone_number2", min);
		activationPhoneFlow.typeInTextField("__address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("__city", "Mountain View");
		activationPhoneFlow.chooseFromSelect("__state", "CA");
		activationPhoneFlow.typeInTextField("__zip_code", "94043");
		activationPhoneFlow.typeInTextField("__country", "USA");
		activationPhoneFlow.typeInTextField("__confirm_email", TwistUtils.createRandomEmail());
		activationPhoneFlow.selectCheckBox("ckbx_copy_to_contact");		
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		activationPhoneFlow.typeInTextField("input_account_number","4556784565098661");
		activationPhoneFlow.typeInPasswordField("input_account_password", "123");
		activationPhoneFlow.typeInTextField("input_ssn", "1234");
		}
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}
	public void finishPort() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.h2Visible("Order Summary"));
		activationPhoneFlow.pressSubmitButton("default_submit_btn");	
		
		ESN currESN = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(currESN.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(currESN.getEsn());
		
		if (activationPhoneFlow.submitButtonVisible("default_cancel_btn")) {
			activationPhoneFlow.pressSubmitButton("default_cancel_btn");	
		}
		
		currESN.setTransType("TC Port In");
		phoneUtil.checkUpgrade(currESN.getFromEsn(), currESN);
	}

	public void enterToAndFromPinDuringPin(String whatToAdd, String fromPin,String duringPin) throws Exception {
		ESN toEsn = esnUtil.getCurrentESN();
		String simOrEsn;
		if ("SIM".equalsIgnoreCase(whatToAdd)) {
			simOrEsn = toEsn.getSim();
		} else {
			simOrEsn = toEsn.getEsn();
		}

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.TargetEsnText.name, simOrEsn);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.TargetNickName.name, "nickname target");
		activationPhoneFlow.getBrowser().radio("current_esn").click();
		activationPhoneFlow.pressSubmitButton("default_submit_btn");

		if(!duringPin.isEmpty()){
			fromPin = duringPin;
		}
		activatePhone.dismissCdmaRoaming(fromPin);
		
		if(!duringPin.isEmpty()){
			String esnStr = phoneUtil.getNewPinByPartNumber(duringPin);
			activationPhoneFlow.typeInTextField("input_pin1",esnStr);
			activationPhoneFlow.pressSubmitButton("default_submit_btn");
		}
	}

	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(25);
		if (!activationPhoneFlow.h2Visible(properties.getString("ORDERSUMARY"))) {
			TwistUtils.setDelay(45);
		}
		Assert.assertTrue(activationPhoneFlow.h2Visible(properties.getString("ORDERSUMARY")));
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
		if (activationPhoneFlow.submitButtonVisible("default_cancel_btn")) {
			activationPhoneFlow.pressSubmitButton("default_cancel_btn");	
		}
		
		ESN esn = esnUtil.getCurrentESN();
		esn.setTransType("TC Phone Upgrade");
		phoneUtil.checkUpgrade(esn.getFromEsn(), esn);
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setActivatePhone(ActivatePhone activatePhone) {
		this.activatePhone = activatePhone;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

}