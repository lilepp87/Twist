package com.tracfone.twist.Activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.junit.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	
	public PortNumber() {
		
	}

	public void chooseKeepMyNumberForPart(String FromPart) throws Exception {

		activationPhoneFlow.clickLink(props.getString("Port.KeepMyNumber"));
		ESN newEsn = esnUtil.getCurrentESN();
		String oldEsn = phoneUtil.getActiveEsnByPartNumber(FromPart);
		newEsn.setFromEsn(new ESN(FromPart, oldEsn));
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		
		activationPhoneFlow.typeInTextField(props.getString("Port.PortMIN"), min);
		activationPhoneFlow.clickLink(props.getString("Activate.continue"));
		String lastFour = oldEsn.substring(oldEsn.length()-4);
		activationPhoneFlow.typeInTextField(props.getString("Port.last4digits"), lastFour);
		activationPhoneFlow.clickLink(props.getString("Port.continue2"));
	
	}
	
	public void finishPorting() throws Exception {
		TwistUtils.setDelay(15);
		Assert.assertTrue(activationPhoneFlow.h4Visible("ORDER SUMMARY") ||activationPhoneFlow.h2Visible("ORDER SUMMARY") ||activationPhoneFlow.h4Visible("Billing Summary"));
		ESN toEsn = esnUtil.getCurrentESN();
		toEsn.setTransType("GS Phone Upgrade");
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(toEsn.getEsn());
		phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
		if (activationPhoneFlow.submitButtonVisible(props.getString("Activate.continue"))){
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
		}else{
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.DoneButton"));
		}
		if (activationPhoneFlow.submitButtonVisible(props.getString("Activate.NoThanksText"))) {
			activationPhoneFlow.pressSubmitButton(props.getString("Activate.NoThanksText"));
		}
		TwistUtils.setDelay(10);
	
	}
	public void enterCurrentMin() throws Exception {
		String fromEsn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		String fromMin = phoneUtil.getMinOfActiveEsn(fromEsn);
		activationPhoneFlow.clickLink(props.getString("Port.KeepMyNumber"));
		activationPhoneFlow.typeInTextField(props.getString("Port.PortMIN"), fromMin);
		activationPhoneFlow.clickLink(props.getString("Activate.continue"));
	
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

	public void enterExternalNumberFrom(String Provider) throws Exception {
		activationPhoneFlow.clickLink(props.getString("Port.KeepMyNumber"));
		String min = TwistUtils.generateRandomMin();
		ESN fromEsn = new ESN("External", "External");
		fromEsn.setMin(min);
		esnUtil.getCurrentESN().setFromEsn(fromEsn);
		activationPhoneFlow.typeInTextField(props.getString("Port.PortMIN"), min);
		activationPhoneFlow.clickLink(props.getString("Activate.continue"));
		TwistUtils.setDelay(5);
		activationPhoneFlow.chooseFromSelect(props.getString("Port.PhoneType"), "WIRELESS");
		activationPhoneFlow.chooseFromSelect(props.getString("Port.Provider"), Provider.toUpperCase());
		TwistUtils.setDelay(5);
		activationPhoneFlow.typeInTextField(props.getString("Port.AccountNumber"), Long.toString(TwistUtils.createRandomLong(10000000, 90000000)));
		activationPhoneFlow.typeInPasswordField(props.getString("Port.AccountPassword"), Long.toString(TwistUtils.createRandomLong(100000, 900000)));
		activationPhoneFlow.typeInTextField(props.getString("Port.ExtFirstname"), "TwistFirstName");
		activationPhoneFlow.typeInTextField(props.getString("Port.ExtLastname"), "TwistLastName");
		activationPhoneFlow.typeInTextField(props.getString("Port.SSN"), Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		activationPhoneFlow.typeInTextField(props.getString("Port.ExtAddress1"), "1295 Charleston Road");
		activationPhoneFlow.chooseFromSelect(props.getString("Port.ExtState"), "CA");
		activationPhoneFlow.typeInTextField(props.getString("Port.ExtZip"), "94043");
		activationPhoneFlow.typeInTextField(props.getString("Port.PhoneNumber2"), TwistUtils.generateRandomMin());
		activationPhoneFlow.clickStrongMessage(props.getString("Port.continue4"));	
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
		activationPhoneFlow.clickLink("Ticket Detail");
		TwistUtils.setDelay(5);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getFromEsn().getMin(), phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn()));
		
		activationPhoneFlow.clickLink("Complete Port");
		pressButton("Complete Port");
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		
		
	}	
	
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
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
