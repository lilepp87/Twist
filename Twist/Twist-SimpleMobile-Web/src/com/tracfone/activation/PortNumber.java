package com.tracfone.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.ElementStub;

import junit.framework.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.DeactivationPhoneFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private ActivationReactivationFlow activationPhoneFlow;
	//private DeactivationPhoneFlow deactivationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	public PortNumber() {
		
	}

	public void selectTransferNumber() throws Exception {
		activationPhoneFlow.clickLink("Upgrade/Transfer current Service");
//		activationPhoneFlow.selectRadioButton("activation_option[2]");
//		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void acceptPortTerms() throws Exception {
		activationPhoneFlow.selectCheckBox("checkbox_name");
		activationPhoneFlow.pressButton("ACCEPT");
	}

	public void enterPhoneOfPart(String oldPart) throws Exception {
		ESN newEsn = esnUtil.getCurrentESN();
		String oldEsn = phoneUtil.getActiveEsnByPartNumber(oldPart);
		newEsn.setFromEsn(new ESN(oldPart, oldEsn));
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		
		activationPhoneFlow.typeInTextField("portPhoneNumber", min);
		activationPhoneFlow.clickLink(rb.getString("sm.continue"));
		String lastFour = oldEsn.substring(oldEsn.length()-4);
		activationPhoneFlow.typeInTextField("last4digits", lastFour);
		activationPhoneFlow.clickLink("Continue[2]");
	}
	
	public void enterPhoneOfBrand(String oldPart) throws Exception {
		ESN newEsn = esnUtil.getCurrentESN();
		String oldEsn = phoneUtil.getActiveEsnByBrand(oldPart);
		System.out.println("CUSTOMER ESN = " + oldEsn);
		//newEsn.setFromEsn(new ESN(oldPart, oldEsn));
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		System.out.println("CUSTOMER MIN = " +min);	
		
		ElementStub e = activationPhoneFlow.getBrowser().byId("phoneNumber");
		e.setValue(min);
		activationPhoneFlow.pressSubmitButton("phoneNumber-button");
	}
	

	public void enterPINForPort(String pinPart) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		TwistUtils.setDelay(7);
		activationPhoneFlow.clickLink(rb.getString("sm.UsePinLink"));
		activationPhoneFlow.typeInTextField(rb.getString("sm.PINcardText"), newPin);
		pressButton(rb.getString("sm.continue"));
	}

	public void checkConfirmation() throws Exception {
		Assert.assertTrue(activationPhoneFlow.tableHeaderVisible("Ticket Number"));
		activationPhoneFlow.pressSubmitButton("Done");
	
		ESN currESN = esnUtil.getCurrentESN();
//		phoneUtil.finishPhoneActivationAfterPortIn(currESN);
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(currESN.getEsn());
		TwistUtils.setDelay(5);
		
		currESN.setTransType("SM Port In");
		phoneUtil.checkUpgrade(currESN.getFromEsn(), currESN);
	}

	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
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

	public void enterExternalNumberFromAndZip(String brand, String zip) throws Exception {
		String min = TwistUtils.generateRandomMin();
		TwistUtils.setDelay(8);
		activationPhoneFlow.typeInTextField("portPhoneNumber", min);
		activationPhoneFlow.clickLink(rb.getString("sm.continue"));
		ESN fromEsn = new ESN("External", "External");
		fromEsn.setMin(min);
		esnUtil.getCurrentESN().setFromEsn(fromEsn);	
		activationPhoneFlow.chooseFromSelect("input_phone_type", "WIRELESS");
		activationPhoneFlow.chooseFromSelect("input_service_provider", brand.toUpperCase());
		activationPhoneFlow.typeInTextField("input_account_number", Long.toString(TwistUtils.createRandomLong(10000000, 90000000)));
		if (activationPhoneFlow.passwordVisible("input_account_password")) {
			activationPhoneFlow.typeInPasswordField("input_account_password", Long.toString(TwistUtils.createRandomLong(100000, 900000)));
		}
		activationPhoneFlow.typeInTextField("txtfirstname", TwistUtils.createRandomUserId());
		activationPhoneFlow.typeInTextField("txtlastname", TwistUtils.createRandomUserId());
		//activationPhoneFlow.typeInTextField("input_ssn", Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		activationPhoneFlow.typeInTextField("txtAddress1", "1295 charleston Road");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("txtzip_code", zip);
		activationPhoneFlow.typeInTextField("input_phone_number2", TwistUtils.generateRandomMin());
		activationPhoneFlow.clickStrongMessage("Continue[3]");
		TwistUtils.setDelay(15);
	}

	public void completePortTicket() throws Exception {
		activationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
		String esn = esnUtil.getCurrentESN().getEsn();
		TwistUtils.setDelay(5);
		if (activationPhoneFlow.passwordVisible("it2")) {
			activationPhoneFlow.typeInTextField("it1", "itquser");
			activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
			
			if (activationPhoneFlow.buttonVisible("cb1")){
				activationPhoneFlow.pressButton("cb1");
			} else {
				activationPhoneFlow.pressSubmitButton("Login");
			}
		}
		TwistUtils.setDelay(9);
		activationPhoneFlow.clickLink("Incoming Call");
        //String email = TwistUtils.createRandomEmail();
		enterActiveEsnAndMin(esn);

		TwistUtils.setDelay(10);
		if (activationPhoneFlow.buttonVisible("Continue to Service Profile")) {
			activationPhoneFlow.pressButton("Continue to Service Profile");
		} else if (activationPhoneFlow.submitButtonVisible("Continue to Service Profile")) {
			activationPhoneFlow.pressSubmitButton("Continue to Service Profile");
		}
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("History");
		activationPhoneFlow.clickLink("Ticket History");
		activationPhoneFlow.clickLink(phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn()));
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("Ticket Detail");
		TwistUtils.setDelay(5);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getFromEsn().getMin(), phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn()));
		
		activationPhoneFlow.clickLink("Complete Port");
		pressButton("Complete Port");
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		
		
	}	
	 public void enterActiveEsnAndMin(String activeEsn) {
		TwistUtils.setDelay(15);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("ActiveESN: " + activeEsn + " ActiveMin: " + activeMin);
		
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", activeEsn);
		if (activationPhoneFlow.buttonVisible("Search Service")) {
			activationPhoneFlow.pressButton("Search Service");
		} else {
			activationPhoneFlow.pressSubmitButton("Search Service");
		}
	}

	
}