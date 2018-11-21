package com.tracfone.twist.Activation;

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification


public class TAS {
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";

public TAS() {
		
	}

	public void enterEsnForPartSim(String status, String partNumber, String sim)
			throws Exception {
			ESN esn;
			if (NEW_STATUS.equalsIgnoreCase(status)) {
				if (partNumber.matches("PH(GS|SMGS).*")) {
					esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
					esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
					esnUtil.setCurrentESN(esn);
					System.out.println(esnUtil.getCurrentESN().getEsn());
				} 
			} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
				esn = esnUtil.popRecentPastDueEsn(partNumber);
				esnUtil.setCurrentESN(esn);
			} else {
				throw new IllegalArgumentException("Phone Status not found");
			}
			activationPhoneFlow.clickLink("Incoming Call");
			pressButton("New Contact Account");
	}

	public void createAccountForWithNoEmail(String brand) throws Exception {
		esnUtil.setCurrentBrand(brand);
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:sbc3::content/");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		pressButton("Create Contact");
	}

	public void activatePhoneByUsingPinDependingOnStatusOfCellTechZip(String pinPart, String status, String cellTech, String zip)throws Exception {
		if (activationPhoneFlow.buttonVisible("Continue to Service Profile") || activationPhoneFlow.submitButtonVisible("Continue to Service Profile")) {
			pressButton("Continue to Service Profile");
		}		
		String newPin;
		newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		esnUtil.getCurrentESN().setPin(newPin);
		activationPhoneFlow.clickLink("Transactions");
		activationPhoneFlow.clickLink("Activation");
		activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
		pressButton("Validate");
		TwistUtils.setDelay(5);
		activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", zip);
		pressButton("Continue");
		pressButton("Process Transaction");
		pressButton("Refresh");
		finishPhoneActivation(cellTech, status);
		TwistUtils.setDelay(10);
		pressButton("Refresh");
		activationPhoneFlow.clickLink("Logout");
	}
	
	private void finishPhoneActivation(String cellTech, String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(10);
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "Go Smart Activation");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
		TwistUtils.setDelay(15);
	}

	
	public void goToTAS() throws Exception {		
		activationPhoneFlow.navigateTo(props.getString("GS.TasHomePage"));
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "itquser");
		activationPhoneFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abcd1234!");
		pressButton("Login");
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
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
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
	}
}
