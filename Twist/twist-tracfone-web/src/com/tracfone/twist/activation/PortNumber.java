package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.ElementStub;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortNumber {

	private static final String CDMA = "CDMA";
	private static final String GSM = "GSM";
	private static  PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	
	private Properties properties = new Properties();
	
	public PortNumber() {

	}
	
//	public void goToActivatePhone() throws Exception {
//		activationPhoneFlow	.clickLink(properties.getString("activatePhone"));
//	} 
	
	public void enterPhoneOfTfBrand(String oldPart) throws Exception {
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
	
	public void goToActivatePhoneWith(String partNumber) throws Exception {
//		activationPhoneFlow.clickLink("Remove Phone");
		activationPhoneFlow.clickLink("ACTIVATE");
		if (partNumber.matches("PH(TF).*")) {
			activationPhoneFlow.clickLink("ACTIVATE[1]");
		}else{
			activationPhoneFlow.clickLink("ACTIVATE");
		}
//		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.WithUsRadio.name);
//		activationPhoneFlow.pressButton(properties.getString("submit"));
	}
	
	public void selectTransferMyNumberFromAnotherCompany() throws Exception {
		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.PortRadio.name);
		activationPhoneFlow.pressButton(properties.getString("submit"));
		activationPhoneFlow.selectCheckBox("terms_and_cond");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void enterNewEsnWithSim(String partNumber, String simCard) throws Exception {
		String esnStr;
		
		if (partNumber.matches("PH(TF).*")) {
			esnStr = phoneUtil.getNewByopEsn(partNumber,simCard);
		}else{
			esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);	
		}
		ESN esn = new ESN(partNumber, esnStr);
		esnUtil.setCurrentESN(esn);
		esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		System.out.println("ESN: " + esnStr);	
		if(simCard != null && !simCard.isEmpty() && !partNumber.matches("PH(TF).*")){
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			phoneUtil.addSimToEsn(newSim, esn);
		}
		
		if(partNumber.matches("PH(TF).*")){
			String newSim = esn.getSim();
			activationPhoneFlow.typeInTextField("portSim", newSim);
		}else{
		activationPhoneFlow.typeInTextField("portEsn", esnStr); 
		} 
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
}
	
	public void enterEsnAndNumberFromBrandWithPart(String brand, String fromPart) throws Exception {
		ESN toEsn = esnUtil.getCurrentESN();
		//String fromEsn = phoneUtil.getActiveEsnToUpgradeFrom(fromPart, toEsn.getEsn());
		//Assert.assertTrue(activationPhoneFlow.h1Visible("NUMBER TRANSFER"));
		String fromEsn = phoneUtil.getActiveEsnByPartNumber(fromPart);
		String min = phoneUtil.getMinOfActiveEsn(fromEsn);
		toEsn.setFromEsn(new ESN(fromPart, fromEsn));
		activationPhoneFlow.typeInTextField("currentMIN", min);
		activationPhoneFlow.pressButton("Continue");
		
		System.out.println("Old ESN: " + fromEsn);
		System.out.println("MIN: " + min);
		activationPhoneFlow.chooseFromSelect("phone_type", "WIRELESS");
		activationPhoneFlow.chooseFromSelect("current_provider", brand.toUpperCase());
		
		activationPhoneFlow.typeInTextField("old_esn", fromEsn);
		activationPhoneFlow.typeInTextField("old_esn_min", min);
		submitform("continue_button");
		//activationPhoneFlow.pressSubmitButton("Continue");
		//activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
	}
	
	public void finishPortingNumberFor(String cellTech, String pin) throws Exception {
		if (pin.isEmpty()) {
//			activationPhoneFlow.pressSubmitButton(properties.getString("skip"));
			activationPhoneFlow.clickDivMessage("PURCHASE AN AIRTIME CARD");
			
		} else {
			String newPinCard = phoneUtil.getNewPinByPartNumber(pin);
			activationPhoneFlow.typeInTextField("input_pin1[1]", newPinCard);
			//activationPhoneFlow.pressSubmitButton("ADD MY PIN");
			activationPhoneFlow.pressSubmitButton("Add PIN");
		}
		
		if (activationPhoneFlow.h1Visible(properties.getString("transactionSummary"))) {
			//activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
			if (activationPhoneFlow.submitButtonVisible(properties.getString("continue"))){
				activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			}else{
				activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
			}
		} else {
			TwistUtils.setDelay(60);
			if (activationPhoneFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
				while (activationPhoneFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
					activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
					TwistUtils.setDelay(2);
				}
								
			}
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			Assert.assertTrue(activationPhoneFlow.spanVisible(properties.getString("activationMessage")));
			activationPhoneFlow.pressButton(properties.getString("continue"));
			if (GSM.equalsIgnoreCase(cellTech)) {
				activationPhoneFlow.pressButton(properties.getString("continue"));
			}
			activationPhoneFlow.pressButton(properties.getString("noThanks"));
		}
		
		ESN toEsn = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		
		toEsn.setTransType("TF Port In");
		phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
	}
	
	public void completeTheProcess(String technology) throws Exception {
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationTracfoneWebFields.SubmitButton.name);
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueButton.name);
		if (CDMA.equalsIgnoreCase(technology)) {
			TwistUtils.setDelay(60);
			while (activationPhoneFlow.submitButtonVisible("Code Accepted")) {
				activationPhoneFlow.pressSubmitButton("Code Accepted");
				TwistUtils.setDelay(2);
			}
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueButton.name);
		} else {
			TwistUtils.setDelay(15);
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueImage.name);
		}
		Assert.assertTrue(activationPhoneFlow.spanVisible(ActivationReactivationFlow.ActivationTracfoneWebFields.ActivationMessage.name));
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueImage.name);
		if (GSM.equalsIgnoreCase(technology)) {
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueImage.name);
		}
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationTracfoneWebFields.NoThanksButton.name);
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

	public void selectBuyAServicePlan() throws Exception {
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueButton.name);
	}

	public void enterEsn(String partNumber) throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(activeEsn.getEsn());
//		esnUtil.popRecentActiveEsn(partNumber);
//		esnUtil.setCurrentESN(activeEsn);

		activationPhoneFlow.typeInTextField(PaymentFlow.BuyAirtimeTracfoneWebFields.EsnText.name, activeEsn.getEsn());
		activationPhoneFlow.pressSubmitButton(properties.getString("continueToCheckout"));
		
		TwistUtils.setDelay(9);
	}

	public void finishPorting() throws Exception {
		
		if (activationPhoneFlow.h2Visible(properties.getString("transactionSummary"))) {
          activationPhoneFlow.pressSubmitButton(properties.getString("continue2")); 
		}
		ESN toEsn = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		toEsn.setTransType("TF Port In");
		phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
	}
	

	public void enterEsnWithSim(String status, String partNumber, String simCard)
			throws Exception {
		ESN esn;
		
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
		} else if (partNumber.startsWith("PHTF")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simCard));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else if ("byop".equalsIgnoreCase(partNumber)) {
			esn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
		} else if (NEW_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		esnUtil.setCurrentESN(esn);
		System.out.println("new ESN: " + esn.getEsn());
		
		if (!simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status) && !partNumber.startsWith("PHTF")) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			phoneUtil.addSimToEsn(newSim, esn);
		}
		
		if (!partNumber.startsWith("PHTF")) {
			String esnText;
			if (activationPhoneFlow.textboxVisible("esnNew")) {
				esnText = "esnNew";
			} else {
				esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
			}
			//activationPhoneFlow.typeInTextField("esnNew", esn.getEsn());
			activationPhoneFlow.typeInTextField(esnText, esn.getEsn());
			activationPhoneFlow.pressButton(properties.getString("continue"));
			submitform("continue1");
		} else {
			activationPhoneFlow.typeInTextField("simNew", esn.getSim());
			activationPhoneFlow.pressButton(properties.getString("submit"));
		}
	
		activationPhoneFlow.pressButton("keep_current_phone_button");
			
		/*String zipCode = "33178";
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);				
			submitform("continue2");
			
			
		} else if(simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status)){
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);
			submitform("continue2");
			submitform("continue1");
		}*/		
	}
	
	private void submitform(String buttonName) {
		if(activationPhoneFlow.buttonVisible(properties.getString(buttonName))){
			activationPhoneFlow.pressButton(properties.getString(buttonName));
		}else if(activationPhoneFlow.submitButtonVisible(properties.getString(buttonName))){
			activationPhoneFlow.pressSubmitButton(properties.getString(buttonName));
		}
	}

	public void enterMinForExternalPortWithZipCodeAndProvider(String zipcode,String Provider) throws Exception {
		
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.CurrentMinText.name, TwistUtils.generateRandomMin());
		submitform("continue2");
		
		activationPhoneFlow.chooseFromSelect("phone_type", "WIRELESS");
		activationPhoneFlow.chooseFromSelect("current_provider", Provider.toUpperCase());
		//_current_provider_zip_code
		activationPhoneFlow.typeInTextField("_current_provider_zip_code",zipcode);
		//continue_button
		//Continue
		submitform("continue_button");
	
	}

	public void enterAccountDetails() throws Exception {
		activationPhoneFlow.typeInTextField("input_account_number",Long.toString(TwistUtils.createRandomLong(10000000, 90000000)));
		activationPhoneFlow.typeInPasswordField("input_account_password",Long.toString(TwistUtils.createRandomLong(1000000, 9000000)));
		if (activationPhoneFlow.textboxVisible("input_ssn")){
			activationPhoneFlow.typeInTextField("input_ssn",Long.toString(TwistUtils.createRandomLong(1000, 9999)));
		}
		activationPhoneFlow.typeInTextField("__first_name","TwistFirstName");
		activationPhoneFlow.typeInTextField("__last_name","TwistLastName");
		activationPhoneFlow.typeInTextField("__address1","1295 charleston Road");
		activationPhoneFlow.typeInTextField("__city","Mountain View");
		activationPhoneFlow.chooseFromSelect("__state", "CA");
		activationPhoneFlow.typeInTextField("__zip_code","94043");
		submitform("continue_button");
		
		//Address Directions
		if (activationPhoneFlow.textboxVisible("userParsedHouseNumber")){
			activationPhoneFlow.typeInTextField("userParsedHouseNumber","1295");
			activationPhoneFlow.chooseFromSelect("userParsedDirection", "SW");
			activationPhoneFlow.typeInTextField("userParsedStreetName","charleston Road");
			activationPhoneFlow.chooseFromSelect("userParsedStreetType", "AVE");
			activationPhoneFlow.typeInTextField("userParsedUnit","302");
			submitform("user_entered_continue_button");				
		}
	}


	public void finishExternalPortIn() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h1Visible("Transaction Summary"));
		submitform("continue");
		ESN toEsn = esnUtil.getCurrentESN();
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		toEsn.setTransType("TF External Port In");
		//phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
	
	}

}
