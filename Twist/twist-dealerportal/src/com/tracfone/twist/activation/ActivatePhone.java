package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public ActivatePhone() {
		
	}

	public void goToActivation() throws Exception {
		activationPhoneFlow.clickLink("SIM Activation (GSM)");
	}
	
	public void goToPhoneNumberChange() throws Exception {
		activationPhoneFlow.clickLink("Phone Number Change");
	}
	
	public void goToSimChange() throws Exception {
		activationPhoneFlow.clickLink("Sim Change");
	}

	public void enterPhoneNumberAndZip(String zipCode) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.typeInTextField("phoneNumberDisplay", min);
//		activationPhoneFlow.getBrowser().accessor("document.frmMinChange.phoneNumber").setValue(min);
		
		activationPhoneFlow.typeInTextField("zip", zipCode);
		activationPhoneFlow.getBrowser().submit("Change Phone Number").hover();
		activationPhoneFlow.getBrowser().submit("Change Phone Number").focus();
		activationPhoneFlow.pressSubmitButton("Change Phone Number");
		if (!activationPhoneFlow.h4Visible("Phone number change requested successfully. " +
				"Please allow up to 48 hours for the process to complete.")) {
			TwistUtils.setDelay(40);
		}
		Assert.assertTrue(activationPhoneFlow.h4Visible("Phone number change requested successfully. " +
				"Please allow up to 48 hours for the process to complete."));
	}

	public void enterEsnSimZipPin(String esnPart, String sim, String zip, String pin) throws Exception {
		ESN newEsn;
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		
		if (esnPart.startsWith("PH")) {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart,esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			if (activationPhoneFlow.textboxVisible("esn")) {
				activationPhoneFlow.typeInTextField("esn", esn);
			}
			activationPhoneFlow.typeInTextField("sim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			if (activationPhoneFlow.textboxVisible("esn")) {
				activationPhoneFlow.typeInTextField("esn", esn);
			}
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("sim", newsim);
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		System.out.println("ESN: " + newEsn.getEsn());
		System.out.println("PIN: " + newPin);
		newEsn.setPin(newPin);
		
		activationPhoneFlow.typeInTextField("actZip", zip);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.getBrowser().emailbox("createEmail").setValue(randomEmail);
		esnUtil.getCurrentESN().setEmail(randomEmail);
	}
	
	public void enterByopEsnPin(String status, String pin) throws Exception {
		if (!"Active".equalsIgnoreCase(status)) {
			ESN esn = esnUtil.getCurrentESN();
			String newPin = phoneUtil.getNewPinByPartNumber(pin);

			System.out.println("ESN: " + esn.getEsn());
			System.out.println("PIN: " + newPin);
			esn.setPin(newPin);
			
			activationPhoneFlow.typeInTextField("esn", esn.getEsn());
			activationPhoneFlow.typeInTextField("actZip", "33178");
			activationPhoneFlow.typeInTextField("redemptionPin", newPin);
			confirmActivationForCellTech("CDMA");
		}
	}
	
	public void confirmActivationForCellTech(String cellTech) throws Exception {
		activationPhoneFlow.pressButton("btnContinue");
		TwistUtils.setDelay(3);
		if (activationPhoneFlow.buttonVisible("yes")) {
	//		This will click 'OK' on the Mexico roaming popup
			activationPhoneFlow.pressButton("yes");
		}
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.h2Visible("Steps to Complete your Activation")) {
			TwistUtils.setDelay(50);
		}
		
		Assert.assertFalse(activationPhoneFlow.listItemVisible("Invalid promo code"));//for Promo Code validation
		Assert.assertTrue(activationPhoneFlow.h2Visible("Steps to Complete your Activation"));
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, cellTech, "New", "UDP Activation with PIN");
	}
	
	public void enterPhoneAndSIM(String sim) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		String newSim = simUtil.getNewSimCardByPartNumber(sim);
		
		activationPhoneFlow.typeInTextField("phoneNumberDisplay", min);
//		activationPhoneFlow.getBrowser().accessor("document.frmSimChange.phoneNumber").setValue(min);
		activationPhoneFlow.typeInTextField("newSim", newSim);
		activationPhoneFlow.pressSubmitButton("Process");
		Assert.assertTrue(activationPhoneFlow.h4Visible("SIM Change successful"));
	}
	
	public void goTo(String brand) throws Exception {
		TwistUtils.setDelay(8);
		if (brand.equals("tracfone")) {
			activationPhoneFlow.clickLink("Tracfone");
		} else {
			activationPhoneFlow.clickLink(brand.toLowerCase());
		}
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

	public void checkVerbiage() throws Exception {
//	#CR 28168 Request to remove the verbiage “FOR BYOP ACTIVATION ONLY” and 
//	“FOR BRANDED PHONE ACTIVATION ONLY” from the activation flow on the fast act portal
//	under the NET10 and TELCEL sections.
		TwistUtils.setDelay(5);
		Assert.assertFalse(activationPhoneFlow.divVisible("FOR BRANDED PHONE ACTIVATION ONLY"));
		Assert.assertFalse(activationPhoneFlow.divVisible("FOR BYOP ACTIVATION ONLY"));
	}

	public void enterEsnSimZipPinAndAddSpace(String esnPart, String sim, String zip, String pin) throws Exception {
//		Test CR 25586
//		TRIM INPUTS BEFORE SUBMITTING TO BACK END - FASTACT PORTAL
		ESN newEsn;
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		
		if (esnPart.startsWith("PHSM")) {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("sim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("esn", esn + " ");
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("sim", newsim + " ");
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		
		System.out.println("ESN: " + newEsn.getEsn());
		System.out.println("PIN: " + newPin);
		newEsn.setPin(newPin);
		
		activationPhoneFlow.typeInTextField("actZip", zip + " ");
		activationPhoneFlow.typeInTextField("redemptionPin", newPin + " ");	
	}

	public void confirmCodeGenerationSuccessScreen() throws Exception {
		activationPhoneFlow.pressButton("btnContinue");
		Assert.assertTrue(activationPhoneFlow.h2Visible("Transaction Processed."));			
	}

	public void enterEsnMinAndSequenceForPP(String esnPart) throws Exception {
		String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
		activationPhoneFlow.typeInTextField("esn", esn);
		activationPhoneFlow.typeInTextField("phoneNumber", "9999999998");
		activationPhoneFlow.typeInTextField("Sequence", "0");		
	}

	public void goToMSL() throws Exception {
		activationPhoneFlow.clickLink("Master Subsidy Lock (MSL)");  
	}

	public void inputESNForMSLLookup(String esnpart) throws Exception {
		String esn = phoneUtil.getActiveEsnByPartNumber(esnpart);
		activationPhoneFlow.typeInTextField("esn", esn);
		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void checkMSLLookupResult() throws Exception {
		Assert.assertTrue(activationPhoneFlow.divVisible("No MSL found."));
	}

	public void goToActivationFor(String brand) throws Exception {
		if (brand.equals("tracfone")) {
			activationPhoneFlow.clickLink("Phone Activation");
		} else {
			activationPhoneFlow.clickLink("SIM Activation (GSM)");
		}
	}

	public void goToSimChangeFor(String brand) throws Exception {
		if (brand.equals("tracfone") || brand.equals("net10")) {
			activationPhoneFlow.clickLink("Sim Change / Upgrade Phone");
		} else {
			activationPhoneFlow.clickLink("Sim Change");		
		}
	}

	public void goToActivationForSharedPlan(String brand) throws Exception {
		activationPhoneFlow.clickLink("Shared Plan Activation");
	
	}

	public void enterZipPinAndEmail(String zip, String pin)
			throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
//	For SMSDP	
		System.out.println("PIN: " + newPin);
//		newEsn.setPin(newPin);
		
		activationPhoneFlow.typeInTextField("sharedZipCode", zip);
		activationPhoneFlow.typeInTextField("sharedRedemptionPin", newPin);
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.getBrowser().emailbox("sharedCreateEmail").setValue(randomEmail);
		activationPhoneFlow.pressButton("btnContinue");
		
	}

	
	public void enterESNSIMAndZip(String esnPart, String sim, Integer zip)
			throws Exception {
	
		ESN newEsn;
//		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		activationPhoneFlow.selectRadioButton("actProcessType2");
		
		if (esnPart.startsWith("PHSM")) {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("addLineSim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("addLineSim", esn + " ");
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("addLineSim", newsim + " ");
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		System.out.println("ESN: " + newEsn.getEsn());
		
		activationPhoneFlow.typeInTextField("addLineZip", zip + " ");
		pressButton("Process Activation[1]");
	}

	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}

	public void confirmSharedPlanActivationForCellTech(String cellTech)
			throws Exception {
		TwistUtils.setDelay(30);
		//Assert.assertTrue(activationPhoneFlow.h2Visible("Steps to Complete your Activation"));
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, cellTech, "New", "UDP Activation new group with PIN");
		activationPhoneFlow.pressButton("Continue");
	}

	public void addMaximumLinesForPINESNSIMZIP(String pin, String esnPart,
			String sim, Integer zip) throws Exception {
		
	
		//Setup Loop counts based on Plan
		int loops =0;
		if (pin.equals("SMP0050SDP2")){
			loops = 1;
		}
		else if (pin.equals("SMP0075SDP3")){
			loops = 2;
		}
		else if (pin.equals("SMP0100SDP4")){
			loops = 3;
		}

		//Select Activate new group with Pin
		activationPhoneFlow.selectRadioButton("actProcessType2");
		
		boolean firstEsn = true;

		
//		String esnStr = phoneUtil.getNewByopEsn(esnPart, sim);
//		ESN masterEsn = new ESN(esnPart, esnStr);
//		esnUtil.setCurrentESN(masterEsn);
//		String simStr = phoneUtil.getSimFromEsn(esnStr);
//		masterEsn.setSim(simStr);
//		activationPhoneFlow.typeInTextField("addLineSim", masterEsn.getSim());		
//		activationPhoneFlow.typeInTextField("addLineZip", zip + " ");
		ESN masterEsn = esnUtil.getCurrentESN();
		for (int i=0; i<loops; i++){
			pressButton("Add More Phones[1]");
	
			
			if (esnPart.startsWith("PHSM")) {
				String esn = phoneUtil.getNewByopEsn(esnPart, sim);
				ESN newEsn = new ESN(esnPart, esn);
				String newSim = phoneUtil.getSimFromEsn(esn);
				newEsn.setSim(newSim);
				activationPhoneFlow.typeInTextField("addLineSim", newSim);
				activationPhoneFlow.typeInTextField("addLineZip", zip + " ");
				masterEsn.addFamilyEsn(newEsn);
				System.out.println("ESN: " + newEsn.getEsn());
				
				
			} else {
				String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
				ESN newEsn = new ESN(esnPart, esn);
				if (!sim.isEmpty()) {
					String newsim = simUtil.getNewSimCardByPartNumber(sim);
					phoneUtil.addSimToEsn(newsim, newEsn);
					activationPhoneFlow.typeInTextField("addLineSim", newsim + " ");
					activationPhoneFlow.typeInTextField("addLineZip", zip + " ");
				}
				masterEsn.addFamilyEsn(newEsn);
				System.out.println("ESN: " + newEsn.getEsn());
			}
			
			//need to store outside loop
			
				
////				if(firstEsn = false){
//					System.out.println("Adding Family ESN: " + newEsn.getEsn());
//					newEsn.addFamilyEsn(newEsn);
////				}
				
//				firstEsn = false;
			
		}
			pressButton("Process Activation[1]");
			Assert.assertTrue(activationPhoneFlow.h2Visible("Steps to Complete your Activation"));
			TwistUtils.setDelay(30);
			esnUtil.addRecentActiveEsn(masterEsn, "GSM", "New", masterEsn.getTransType());
		}
	
	public void enterESNSIMAndZipForMultipleLines(String esnPart, String sim,
			Integer zip) throws Exception {
		ESN newEsn;
//		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		activationPhoneFlow.selectRadioButton("actProcessType2");
		
		if (esnPart.startsWith("PHSM")) {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("addLineSim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("addLineSim", esn + " ");
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("addLineSim", newsim + " ");
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		
		System.out.println("ESN: " + newEsn.getEsn());
		
		activationPhoneFlow.typeInTextField("addLineZip", zip + " ");
//		pressButton("Add More Phones[1]");
	}

	public void enterEsnSimZipPinForReactivation(String esn, String sim,
			Integer zip, String pin) throws Exception {
		
	}

	public void enterZipPinFor(String zip, String pin, String action)
			throws Exception {
		String currentEsn = esnUtil.getCurrentESN().getEsn();
		String currentSim = esnUtil.getCurrentESN().getSim();
		String phone = phoneUtil.getMinOfActiveEsn(currentEsn);
		String currentEmail = esnUtil.getCurrentESN().getEmail();
		
		System.out.println("**************MIN:**********"+phone);

		phoneUtil.clearOTAforEsn(currentEsn);
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		if(activationPhoneFlow.textboxVisible("esn")){
			activationPhoneFlow.typeInTextField("esn", currentEsn);
		}
		activationPhoneFlow.typeInTextField("sim", currentSim);
		activationPhoneFlow.typeInTextField("actZip", zip);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		activationPhoneFlow.getBrowser().emailbox("createEmail").setValue(currentEmail);
		activationPhoneFlow.pressButton("btnContinue");
	}

	public void selectAddANewDeviceToAnExistingGroup() throws Exception {
		activationPhoneFlow.selectRadioButton("typeActivation2");
		ESN masterEsn = esnUtil.getCurrentESN();
		String masterSim = masterEsn.getSim();
		String last4digits = masterSim.substring(masterSim.length()-4);
		
		activationPhoneFlow.typeInTextField("phoneNumber", phoneUtil.getMinOfActiveEsn(masterEsn.getEsn()));
		activationPhoneFlow.typeInTextField("fourDigitSim", last4digits);
		activationPhoneFlow.pressButton("Continue");
	}

	public void enterEsnSimZipPinPromo(String esnPart, String sim, String zip, String pin,String promo) throws Exception {
		ESN newEsn;
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		
		if (esnPart.startsWith("PHSM") || esnPart.startsWith("SM"))  {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("sim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("esn", esn);
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("sim", newsim);
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		
		System.out.println("ESN: " + newEsn.getEsn());
		System.out.println("PIN: " + newPin);
		newEsn.setPin(newPin);
		
		activationPhoneFlow.typeInTextField("actZip", zip);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		if (!promo.isEmpty()) {
			activationPhoneFlow.typeInTextField("promoCode", promo);
		}
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.getBrowser().emailbox("createEmail").setValue(randomEmail);
		esnUtil.getCurrentESN().setEmail(randomEmail);
	}

	public void validatePromo(String promo) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String objid = phoneUtil.validatePromo(esnStr, promo);
		System.out.println(objid);
	}

	public void changeDealerTo(String dealerId) throws Exception {
		String esn =esnUtil.getCurrentESN().getEsn();
		phoneUtil.changeDealerForEsn(esn,dealerId);		
	}
	
}