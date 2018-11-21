package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private static final String STOLEN_STATUS = "STOLEN";
	private static final String GSM = "GSM";
	private static final String CDMA = "CDMA";
	private static final String NEW_STATUS = "NEW";
	private static ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	
	private static Properties properties = new Properties();

	public ActivatePhone() {
		
	}

	public void goToActivateOption() throws Exception {
		if(activationPhoneFlow.linkVisible(properties.getString("language"))){
			activationPhoneFlow.clickLink(properties.getString("language"));
		}
		activationPhoneFlow.clickLink(properties.getString("activeReactive"));
		//activationPhoneFlow.clickLink("Transferir Número");
//		activationPhoneFlow.clickLink("English");
//		activationPhoneFlow.clickLink("Activate/Reactivate");
		selectActivate();
	}
	
	public void selectActivate() throws Exception {
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}

	public void enterEsnForPartZipCodeAndSim(String status, String partNumber,
			String zip, String sim) throws Exception {
		ESN esn;
		if (partNumber.startsWith("PH")) {
			String esnStr = phoneUtil.getNewByopEsn(partNumber, sim);
			esn = new ESN(partNumber, esnStr);
			esn.setSim(phoneUtil.getSimFromEsn(esnStr));
		} else {
			String esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
			esn = new ESN(partNumber, esnStr);
		}
		esnUtil.setCurrentESN(esn);
		if(activationPhoneFlow.linkVisible(properties.getString("language"))){
			activationPhoneFlow.clickLink(properties.getString("language"));
		}
		enterEsnAndZip(zip);
		activationPhoneFlow.selectRadioButton("use_service_card_no");
		
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		TwistUtils.setDelay(25);

	}
	
	public void enterEsnForPartZipCodeSimAndPinNumber(String status, String partNumber, 
			String zip, String sim, String pinPart)	throws Exception {
		ESN esn;
		if (partNumber.startsWith("PH")) {
			String esnStr = phoneUtil.getNewByopEsn(partNumber, sim);
			esn = new ESN(partNumber, esnStr);
			esn.setSim(phoneUtil.getSimFromEsn(esnStr));
		} else {
			String esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
			esn = new ESN(partNumber, esnStr);
		}
		esnUtil.setCurrentESN(esn);
		if(activationPhoneFlow.linkVisible(properties.getString("language"))){
			activationPhoneFlow.clickLink(properties.getString("language"));
		}
		enterEsnZipAndPin(zip, pinPart);
		if (activationPhoneFlow.linkVisible(properties.getString("trackYourOrder"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		} else {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		}
		if (activationPhoneFlow.submitButtonVisible("Continuar[1]")){ //for CDMA
			activationPhoneFlow.pressSubmitButton("Continuar[1]");
		}
		dismissCdmaRoaming(pinPart);
		TwistUtils.setDelay(15);
	}

	public void dismissCdmaRoaming(String pinPart) {
		boolean isCDMA = esnUtil.getCurrentESN().getEsn().length() != 15;
		if (isCDMA && (pinPart.endsWith("60ILD") || pinPart.endsWith("ILD60"))) {
			String warningMess = activationPhoneFlow.getBrowser().div("window[1]").getText();
			String warningMess2 = activationPhoneFlow.getBrowser().div("window").getText();
			String warningMess3 = activationPhoneFlow.getBrowser().div("window[2]").getText();

			boolean foundWarning = warningMess.contains(properties.getString("foundWarning"));
			boolean foundWarning2 = warningMess2.contains(properties.getString("foundWarning"));
			boolean foundWarning3 = warningMess3.contains(properties.getString("foundWarning"));
			Assert.assertTrue("Didn't find warning for $60 plus for cdma", foundWarning || foundWarning2 || foundWarning3);
			activationPhoneFlow.pressSubmitButton(properties.getString("continue") + "[1]");
		}
	}

	private void enterEsnZipAndPin(String zip, String pinPart) throws Exception {
		enterEsnAndZip(zip);
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		activationPhoneFlow.typeInTextField("service_card_input", pin);
		storeRedeemData(esnUtil.getCurrentESN(), pin);
	}
	
	private void enterEsnAndZip(String zip) {
		String esnStr;
		ESN esn = esnUtil.getCurrentESN();
		
		//Enter hex ESN for iPhone 
		if (esn.getPartNumber().contains("API") || esn.getPartNumber().contains("BYOPHEX")) {
			esnStr = esn.tryToEnterHexEsn(phoneUtil);
		} else {
			esnStr = esn.getEsn();
		}
						
		activationPhoneFlow.typeInTextField("userinputesn", esnStr);
		activationPhoneFlow.typeInTextField("userinputzipcode", zip);
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setActionType(6);
		esn.setRedeemType(false);
		esn.setTransType("TC Activation with PIN");
		esn.setPin(pin);
	}
	
	public void enterEsnZipAndPinEnglish(String zip, String pinPart) throws Exception {
		enterEsnZipAndPin(zip, pinPart);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		dismissCdmaRoaming(pinPart);
	}

	public void completeActivationProcessDependingOnStatusAndCellTech(String status, String cellTech) throws Exception {
		if (STOLEN_STATUS.equalsIgnoreCase(status)) {
			boolean genericError = activationPhoneFlow.cellVisible("We cannot process your transaction with the information provided. Please contact our " +
					"Customer Care Center at 1-877-430-CELL (2355) for further assistance.");
			boolean statusError = activationPhoneFlow.divVisible("The Serial Number entered has an invalid status.  " +
					"Please check the number and try again.");
			Assert.assertTrue(genericError || statusError);
		} else {
			if (!activationPhoneFlow.submitButtonVisible("default_submit_btn")) {
				TwistUtils.setDelay(80);
			}
			activationPhoneFlow.pressSubmitButton("default_submit_btn");
			finishPhoneActivation(cellTech, status);
			TwistUtils.setDelay(30);
			if (activationPhoneFlow.submitButtonVisible("default_cancel_btn")) {
				activationPhoneFlow.pressSubmitButton("default_cancel_btn");
			}
		}
	}
	
	private void finishPhoneActivation(String cellTech, String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "Telcel Activate");
		TwistUtils.setDelay(10);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}
	
	public void completeBYOPActivation() throws Exception {
		completeActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, CDMA);
	}

	public void enterSimCardDependingOnCellTechAndStatus(String sim, String cellTech, String status) throws Exception {
		String phonePart = esnUtil.getCurrentESN().getPartNumber();
		TwistUtils.setDelay(8);
		if (GSM.equalsIgnoreCase(cellTech) && NEW_STATUS.equalsIgnoreCase(status) && !phonePart.startsWith("PH")) {
			String newSim = simUtil.getNewSimCardByPartNumber(sim);
			activationPhoneFlow.typeInTextField("sim", newSim);
			activationPhoneFlow.pressSubmitButton("default_submit_btn");
		}
	/*	else {
			if (activationPhoneFlow.submitButtonVisible("Continuar[1]")){
				activationPhoneFlow.pressSubmitButton("Continuar[1]");
			}
		}*/
		/*if (activationPhoneFlow.submitButtonVisible("Continuar[1]")){ //for CDMA
			activationPhoneFlow.pressSubmitButton("Continuar[1]");
		}*/
	}
	
	public void setupUpToPhoneWithZipSimPin(String status, String part, String zip, String sim, String pin)	throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		ESN toEsn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (part.startsWith("PH")) {
				toEsn = new ESN(part, phoneUtil.getNewByopEsn(part, sim));
				String toSim = phoneUtil.getSimFromEsn(toEsn.getEsn());
				toEsn.setSim(toSim);
			} else {
				toEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
				joinSimCard(sim, toEsn, status);
			}
			toEsn.setFromEsn(fromEsn);
			esnUtil.setCurrentESN(toEsn);
		} else {
//			goToActivateOption();
//			enterEsnZipSimAndPinNumber(part, zip, sim, pin);
//			completeActivation(sim);
//			setupEsnBasedOnStatusAndEsn(status, esnUtil.getCurrentESN()); 
//			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
//			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
//			esnUtil.getCurrentESN().setFromEsn(fromEsn);
		}
	}
	
	private void joinSimCard(String sim, ESN toEsn, String status) {
		boolean isGSM = toEsn.getEsn().length() == 15;
		if (isGSM && NEW_STATUS.equalsIgnoreCase(status)) {
			String newSim = simUtil.getNewSimCardByPartNumber(sim);
			phoneUtil.addSimToEsn(newSim, toEsn);
		}	
	}

	public void logOut() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("signOut"));
		activationPhoneFlow.navigateTo(properties.getString("TC_HOME"));
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
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void goToActivateOrReactivate() throws Exception {
		activationPhoneFlow.clickLink("English");
		activationPhoneFlow.clickLink("Activate/Reactivate");
		selectActivate();
	}

	public void enterSimCardJoinedToModelForZipAndPin(String simPart, String partNumber, String zip, String pinPart) 
			throws Exception {	
		String esn1 = phoneUtil.getNewEsnByPartNumber(partNumber);
		esnUtil.setCurrentESN(new ESN(partNumber, esn1));
		ESN esn = esnUtil.getCurrentESN();
		
		String newSim = simUtil.getNewSimCardByPartNumber(simPart);
		phoneUtil.addSimToEsn(newSim, esn);
		TwistUtils.setDelay(5);
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		
		activationPhoneFlow.typeInTextField("userinputesn", newSim);
		activationPhoneFlow.typeInTextField("service_card_input", pin);
		activationPhoneFlow.typeInTextField("userinputzipcode", zip);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}

}