package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private CboUtils cboUtils;
	private ResourceBundle rb = ResourceBundle.getBundle("straighttalk");
	
	public PortNumber() {
		
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
	
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}
//
	public void chooseToKeepNumberForPart(String fromPart) throws Exception {
		String fromEsnStr = phoneUtil.getEsnForPort(fromPart);
		String fromMin = phoneUtil.getMinOfActiveEsn(fromEsnStr);
		String hexEsn = phoneUtil.getHexEsnFromDecimalEsn(fromEsnStr);
		ESN fromEsn = new ESN(fromPart, fromEsnStr);
		esnUtil.getCurrentESN().setFromEsn(fromEsn);
		System.out.println("ESN: " + fromEsnStr + " MIN: " + fromMin + " Hex: " + hexEsn);

		activationPhoneFlow.clickLink("PhoneInputBtn");
		TwistUtils.setDelay(10);
		activationPhoneFlow.typeInTextField("input_phone_number", fromMin);
		activationPhoneFlow.pressButton("enter-actvn-phone-submit");
		TwistUtils.setDelay(20);
		activationPhoneFlow.typeInTextField("last_4_digit", fromEsnStr.substring(fromEsnStr.length() - 4));
		activationPhoneFlow.pressButton("validatePhoneEsnBtn");
		TwistUtils.setDelay(10);
	}
//
	public void enterExternalNumberFromAndZip(String brand, String zip) throws Exception {
		String min = TwistUtils.generateRandomMin();
		TwistUtils.setDelay(10);
		if (activationPhoneFlow.linkVisible("PhoneInputBtn")) {
			try {
				activationPhoneFlow.clickLink("PhoneInputBtn");
			} catch (Exception e) {
				activationPhoneFlow.getBrowser().byId("PhoneInputBtn").click();
			}
			activationPhoneFlow.typeInTextField("input_phone_number", min);
		} else {
			//BYOP CDMA requires a port in
			activationPhoneFlow.typeInTextField("byop-already-active-phonenumber", min);
		}
		
		if (activationPhoneFlow.buttonVisible("enter-actvn-phone-submit")) {
			activationPhoneFlow.pressButton("enter-actvn-phone-submit");
		} else {
			activationPhoneFlow.pressButton("byop-agreement-agree");
		}
		TwistUtils.setDelay(15);
		ESN fromEsn = new ESN("External", "External");
		fromEsn.setMin(min);
		esnUtil.getCurrentESN().setFromEsn(fromEsn);
		
		activationPhoneFlow.chooseFromSelect("input_phone_type", "WIRELESS");
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		activationPhoneFlow.typeInTextField("txtzip_code", zip);
		activationPhoneFlow.selectCheckBox("terms");
		activationPhoneFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(60);
		activationPhoneFlow.typeInTextField("input_account_number", Long.toString(TwistUtils.createRandomLong(10000000, 90000000)));
		if (activationPhoneFlow.passwordVisible("input_account_password")) {
			activationPhoneFlow.typeInPasswordField("input_account_password", Long.toString(TwistUtils.createRandomLong(100000, 900000)));
		}
		activationPhoneFlow.typeInTextField("txtfirstname", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("txtlastname", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("input_phone_number", TwistUtils.generateRandomMin());
		//activationPhoneFlow.typeInTextField("input_ssn", Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		activationPhoneFlow.typeInTextField("address1", "1295 charleston Road");
		activationPhoneFlow.typeInTextField("city", "Mountain View");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.pressButton("service-provider-submit");
	}
//
	public void finishPort() throws Exception {
		TwistUtils.setDelay(20);
		if (!activationPhoneFlow.getBrowser().strong("YOUR TRANSFER REQUEST WAS SUBMITTED. WE'RE ALMOST DONE!").isVisible()) {
			TwistUtils.setDelay(70);
		}
		Assert.assertTrue(activationPhoneFlow.getBrowser().strong("YOUR TRANSFER REQUEST WAS SUBMITTED. WE'RE ALMOST DONE!").isVisible());
		Assert.assertFalse(activationPhoneFlow.textboxVisible(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CvvText.name));

		activationPhoneFlow.clickLink("Setup Completed");
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
		// finishPhoneActivation(cellTech, status);
		// Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") ||
		// activationPhoneFlow.h2Visible("Billing Summary"));
		// activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.DoneButton.name);

		ESN currESN = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(currESN.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(currESN.getEsn());

		activationPhoneFlow.pressSubmitButton("default_submit_btn");
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);
		}
		currESN.setTransType("ST Port In");
		ESN fromESN = currESN.getFromEsn();
		phoneUtil.checkUpgrade(fromESN, currESN);
		// To Auto Complete Port Ticket Using CBO	
		String orderType = phoneUtil.getOrderType(currESN.getEsn());
		if (orderType.equals("EPIR") || orderType.equals("PIR") || orderType.equals("IPI")) {
			String actionItemId = phoneUtil.getactionitemidbyESN(esnUtil.getCurrentESN().getEsn());
			cboUtils.callcompleteAutomatedPortins(actionItemId, phoneUtil.getMinOfActiveEsn(currESN.getEsn().toString()));
		}
	}
	
	public void activateEsnSimPinBrandZipCelltechStatus(String esnPart, String simPart, String pinPart, String brand, String zip, String cellTech, String status) throws Exception{
		ESN currentEsn = new ESN(esnPart, phoneUtil.getNewEsnByPartNumber(esnPart));
		String sim = simUtil.getNewSimCardByPartNumber(simPart);
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		String email = "tas" + TwistUtils.createRandomEmail();
		String password = "123456";
		
		esnUtil.setCurrentESN(currentEsn);
		esnUtil.setCurrentBrand(brand);
		esnUtil.getCurrentESN().setSim(sim);
		esnUtil.getCurrentESN().setZipCode(zip);
		esnUtil.getCurrentESN().setEmail(email);
		esnUtil.getCurrentESN().setPassword(password);
		esnUtil.getCurrentESN().putInMap("securityPin", "8910");	
		esnUtil.getCurrentESN().putInMap("redCard", pin);

		cboUtils.callAddUser(currentEsn, "MIAMI", "FL", "TwistLastName", "05/15/1951");
		cboUtils.callSTActivate(esnUtil.getCurrentESN(), "1", "1", pin, "2");
		cboUtils.completePhoneActivationForAndStatus(cellTech, status);
	}
		
	public void deactivateWithReason(String reason) throws Exception{
		String esn = esnUtil.getCurrentESN().getEsn();
		cboUtils.callDeactivatePhone(esn, phoneUtil.getMinOfActiveEsn(esn), reason, esnUtil.getCurrentBrand());
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void updatePortCaseAndCompletePort() throws Exception {
		TwistUtils.setDelay(20);
		String ticket = phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn());
		System.out.println("New MIN (external) : "+esnUtil.getCurrentESN().getFromEsn().getMin()+", Port ticket : "+ticket);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getFromEsn().getMin(), ticket);
		loginToTas();
		activationPhoneFlow.clickLink("Incoming Call");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		pressButton("Search Service");
		activationPhoneFlow.clickLink("Transactions");
		TwistUtils.setDelay(15);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getFromEsn().getMin(), ticket);
		activationPhoneFlow.clickLink("Complete Ports");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1/",ticket);
		pressButton("Complete Port");
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));
		
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
			
	}
	
	public void loginToTas(){
		activationPhoneFlow.navigateTo(rb.getString("TAS_URL"));
		if (activationPhoneFlow.passwordVisible("it2")) {
			activationPhoneFlow.typeInTextField("it1", "itquser");
			activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
			
			if (activationPhoneFlow.buttonVisible("cb1")){
				activationPhoneFlow.pressButton("cb1");
			} else {
				activationPhoneFlow.pressSubmitButton("Login");
			}
		}
	}
	
	public void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}
}