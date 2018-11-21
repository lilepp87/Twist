package com.tracfone.twist.activation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.context.OnTotalWirelessHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private static PhoneUtil phoneUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private DeactivatePhone deactivatePhone;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String CDMA = "CDMA";
	private static final String BYOP = "TWBYO";
	private static final String HEX = "HEX";
	private static final String TABLET = "TWBYOD";
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	public ActivatePhone() {

	}

	public void goToActivateOption() throws Exception {
//		activationPhoneFlow.clickLink("Phones[3]");
		activationPhoneFlow.clickLink("Activate / Reactivate");
//		activationPhoneFlow.clickImage("exifviewer-img-1");
//		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}

	public void goToActivateOptionForport() throws Exception {
		activationPhoneFlow.clickLink("sign_in_out_link");
		activationPhoneFlow.clickLink("Activate / Reactivate");
//		activationPhoneFlow.clickImage("exifviewer-img-1");
//		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}
	
	public void chooseActivateOption() throws Exception {
		//activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}

	public void clickAddDevice() throws Exception {
		activationPhoneFlow.clickLink("Add Device");
	}

	private void enterPin(ESN esn, String pinNum) throws Exception {
		TwistUtils.setDelay(8);
		activationPhoneFlow.clickH3("use a service plan PIN");
		String pin = phoneUtil.getNewPinByPartNumber(pinNum);
		activationPhoneFlow.typeInTextField("servicePin", " " + pin + " ");
		activationPhoneFlow.pressSubmitButton("servicePinSubmit");

		storeRedeemData(esn, pin);
	}

	public synchronized void enterEsnZipAndDevicesWithSim(String zip, int numDevices, String partNumber, String simNumber) throws Exception {
		boolean firstEsn = true;
		ESN esn = esnUtil.getCurrentESN();
		ESN currEsn = esn;
		while (numDevices > 0) {
			if (!firstEsn && (partNumber.startsWith(BYOP)  || partNumber.startsWith(TABLET))) {
				String byopEsn = "3400" + TwistUtils.generateRandomEsn();
//				currEsn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
				currEsn = new ESN(partNumber, byopEsn);
				esn.addFamilyEsn(currEsn);
			} else if (!firstEsn) {
				currEsn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				if (!simNumber.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simNumber);
			        phoneUtil.addSimToEsn(sim, currEsn);
					TwistUtils.setDelay(10);
				}
				esn.addFamilyEsn(currEsn);
			}
			
			logger.info("ESN: " + currEsn.getEsn());
			if (currEsn.getPartNumber().contains(BYOP) || partNumber.startsWith(TABLET)) {
				enterByopEsn(zip, currEsn);
				//activationPhoneFlow.pressSubmitButton("ActiveDeviceNo");
			} else {
				TwistUtils.setDelay(6);
				if (!activationPhoneFlow.linkVisible("brandedPhoneImg")) {
					TwistUtils.setDelay(90);
				}
				activationPhoneFlow.clickLink("brandedPhoneImg");
				activationPhoneFlow.typeInTextField("deviceEsn", currEsn.getEsn());
				if(firstEsn){
					activationPhoneFlow.selectCheckBox("esn-activation-check");
					activationPhoneFlow.pressSubmitButton("deviceEsnSubmit");
					}
				if(!firstEsn){
					activationPhoneFlow.pressSubmitButton("deviceEsnSubmit");
				}
				
				TwistUtils.setDelay(10);
				if (!activationPhoneFlow.linkVisible("showNewNumberLink")) {
					TwistUtils.setDelay(40);
				}
				activationPhoneFlow.clickLink("showNewNumberLink");
				TwistUtils.setDelay(18);
				if (activationPhoneFlow.textboxVisible("zipCode")) {
					activationPhoneFlow.typeInTextField("zipCode", zip);
				} else {
					activationPhoneFlow.typeInTextField("zipCode1", zip);
				}
				activationPhoneFlow.selectCheckBox("esn-byop-activation-check");
				if (buttonVisible("newNumberButton")) {
					pressButton("newNumberButton");
				} else if (buttonVisible("additionalDivBtn")) {
					pressButton("additionalDivBtn");
				}
			}

 			TwistUtils.setDelay(3);
			firstEsn = false;
			numDevices--;
		}
		TwistUtils.setDelay(10);
		
		if (!activationPhoneFlow.h3Visible("/DEVICE 1 .*? ACTIVATION INSTRUCTIONS/")
				&& !activationPhoneFlow.h3Visible("payment_section_link")) {
			if (activationPhoneFlow.buttonVisible("No more lines at this time")) {
				activationPhoneFlow.pressButton("No more lines at this time");
			}
			//activationPhoneFlow.pressButton("No more lines at this time");
		}
	}
	
	private void enterByopEsn(String zip, ESN currEsn) {
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.linkVisible("byopPhoneImg")) {
			TwistUtils.setDelay(90);
		}
		activationPhoneFlow.clickLink("byopPhoneImg");
		if (currEsn.getPartNumber().contains(HEX)) {
			String hexEsn = phoneUtil.convertMeidDecToHex(currEsn);
			activationPhoneFlow.typeInTextField("deviceEsn", hexEsn);
		} else {
			activationPhoneFlow.typeInTextField("deviceEsn", currEsn.getEsn());
		}
		//activationPhoneFlow.pressSubmitButton("deviceEsnSubmit");
		activationPhoneFlow.typeInTextField("zipCode", zip);
		//activationPhoneFlow.selectCheckBox("esn-activation-check");
		activationPhoneFlow.selectCheckBox("esn-byop-activation-check");
		activationPhoneFlow.pressButton("additionalDivBtn");
		int edited = 0;
		for (int i = 0; i < 4 && edited == 0; i++) {
			TwistUtils.setDelay(4 + i);
			edited = phoneUtil.finishCdmaByopIgate(currEsn.getEsn(), "Verizon", "No", "No", "No","No");
			logger.info("edit: " + edited);
		}
		TwistUtils.setDelay(5);
		
		String isActive = "inActive";		
		if ("Active".equalsIgnoreCase(isActive)) {
			activationPhoneFlow.pressSubmitButton("ActiveDeviceYes");
			activationPhoneFlow.selectCheckBox("agree_to_port");
			
		} else {
			pressButton("ActiveDeviceNo");
		}
		//TwistUtils.setDelay(10);
		//pressButton("btnRegisterByopPhone");
		TwistUtils.setDelay(10);
		activationPhoneFlow.clickLink("showNewNumberLink");
	}

	public void enterZipAndAddDevicesWithSim(String zip, int numDevices, String partNumber,String simNumber) throws Exception {
		setUpMainEsn("New", partNumber,simNumber);
		enterEsnZipAndDevicesWithSim(zip, numDevices, partNumber, simNumber);		
	}

	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setActionType(6);
		esn.setRedeemType(false);
		esn.setTransType("Total Wireless Activation with PIN");
		esn.setPin(pin);
		logger.info(pin);
	}

	public void enterEsnForPartAndPinWithSim(String status, String partNumber, String pinNumber, String simNumber) throws Exception {
		ESN esn = setUpMainEsn(status, partNumber, simNumber);
		enterPin(esn, pinNumber);
	}

	private synchronized ESN setUpMainEsn(String status, String partNumber, String simNumber) {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
		if (NEW_STATUS.equalsIgnoreCase(status) && partNumber.startsWith(BYOP)) {
//			String byopEsn = phoneUtil.getNewByopCDMAEsn();
			String byopEsn = "3400" + TwistUtils.generateRandomEsn();
			esn = new ESN(partNumber, byopEsn);
		} else if (NEW_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simNumber.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simNumber);
	            phoneUtil.addSimToEsn(sim, esn);
				TwistUtils.setDelay(5);
			}
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		
		esnUtil.setCurrentESN(esn);
		esn.setFromEsn(fromEsn);
		return esn;
	}
	
	public void enterEsnForPortAndPinWithSim(String pinNumber) throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		enterPinForport(fromEsn, pinNumber);
	}

	/*public ESN setUpMainEsnForPort() {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
        //esn.setFromEsn(fromEsn);
	}*/
	private void enterPinForport(ESN fromEsn , String pinNum) throws Exception {
		TwistUtils.setDelay(15);
		activationPhoneFlow.clickH3("use a service plan PIN");
		String pin = phoneUtil.getNewPinByPartNumber(pinNum);
		activationPhoneFlow.typeInTextField("servicePin", " " + pin + " ");
		activationPhoneFlow.pressSubmitButton("servicePinSubmit");
        storeRedeemData(fromEsn, pin);
	}
	
	public void enterEsnForPartWithSim(String status, String partNumber,String simNumber) throws Exception {
		setUpMainEsn(status, partNumber, simNumber);
		activationPhoneFlow.clickLink("purchaseNewPlan");
	}

	public void enterEsnAndPinNumber(String partNumber, String pinNumber,String simNumber) throws Exception {
		enterEsnForPartAndPinWithSim(NEW_STATUS, partNumber, pinNumber,simNumber);
	}

	public void enterBYOPEsnZipAndPin(String zipCode, String pinNumber) throws Exception {
		enterPin(esnUtil.getCurrentESN(), pinNumber);
		//pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.CreateAccountButton.name);
	}

	public void completeActivationDependingOnStatus(String status) throws Exception {
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.h3Visible("/DEVICE 1 .*? ACTIVATION INSTRUCTIONS/")) {
			TwistUtils.setDelay(80);
		}
		Assert.assertTrue(activationPhoneFlow.h3Visible("/DEVICE 1 .*? ACTIVATION INSTRUCTIONS/"));
		TwistUtils.setDelay(10);
		finishPhoneActivation(status);
		activationPhoneFlow.clickLink("GO TO ACCOUNT SUMMARY");
		TwistUtils.setDelay(3);
		activationPhoneFlow.pressSubmitButton("view devices");
		TwistUtils.setDelay(2);
	}
	
	public void purchase10ILDGlobalCard () throws Exception {
		activationPhoneFlow.clickLink("Add Now");
		TwistUtils.setDelay(10);
//		activationPhoneFlow.clickLink("add now");
//		activationPhoneFlow.clickLink("Add $10 Global Card");
		//activationPhoneFlow.typeInTextField("qty", "2");
		//activationPhoneFlow.pressSubmitButton("buy");
	}
	
	public void selectILDQuantity(String numIldCard) throws Exception{
		activationPhoneFlow.typeInTextField("qty", numIldCard);
//		activationPhoneFlow.pressSubmitButton("buy");
		activationPhoneFlow.pressSubmitButton("Add Now");
	}
	
	public void completeActivation() throws Exception {
		completeActivationDependingOnStatus(NEW_STATUS);
	}

	public void finishPhoneActivation(String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		if (currEsn.getTransType().isEmpty()) {
			currEsn.setTransType("TW Web Activate Phone");
		}
		TwistUtils.setDelay(20);
		esnUtil.addRecentActiveEsn(currEsn, CDMA, status, currEsn.getTransType());
		currEsn.clearTestState();
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}

	public void setupEsnBasedOnStatusAndPart(String status, String part) throws Exception {
		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			ESN esn = esnUtil.popRecentActiveEsn(part);
			deact(status, esn, "PASTDUE");
		}
	}

	private void deact(String status, ESN esn, String reason) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			deactivatePhone.ntDeactivateEsn(esn.getEsn(), reason);
			phoneUtil.setDateInPast(esn.getEsn());
			activationPhoneFlow.navigateTo("http://sit1.totalwireless.com"); //$NON-NLS-1$
			phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
			//$NON-NLS-1$
			esnUtil.addRecentPastDueEsn(esn);
		}
	}

	public void makeUsed() throws Exception {
		System.out.println(esnUtil.getCurrentESN().getEsn());
		deact(PAST_DUE_STATUS, esnUtil.getCurrentESN(), "NO NEED OF PHONE");
	}
	
	public void makeUsedWithReason(String reason) throws Exception {
		System.out.println(esnUtil.getCurrentESN().getEsn());
		deact(PAST_DUE_STATUS, esnUtil.getCurrentESN(), reason);
	}

	public void setupUpToPhoneWithZipSimPin(String status, String part, String zip, String sim, String pin, String simNumber) throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		ESN toEsn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (part.startsWith("PH")) {
				toEsn = new ESN(part, phoneUtil.getNewByopEsn(part, sim));
			} else {
				toEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
			}
			toEsn.setFromEsn(fromEsn);
			esnUtil.setCurrentESN(toEsn);
		} else {
			goToActivateOption();
			enterEsnAndPinNumber(part, pin, simNumber);
			completeActivation();
			deact(status, esnUtil.getCurrentESN(), "PASTDUE");
			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
			esnUtil.getCurrentESN().setFromEsn(fromEsn);
		}
	}

	public void goToTotalWirelessHomePage() throws Exception {
		activationPhoneFlow.navigateTo(OnTotalWirelessHomePage.URL);
	}

	public void goToActivateFromAccount() throws Exception {
		activationPhoneFlow.clickLink("Activate Phone or SIM Card");
		activationPhoneFlow.clickLink("ACTIVATE");
		//activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}

	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else if (activationPhoneFlow.submitButtonVisible(button)) {
			activationPhoneFlow.pressSubmitButton(button);
		} else if (activationPhoneFlow.buttonVisible("logged_in_continue_btn")) {
			activationPhoneFlow.pressButton("logged_in_continue_btn");
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

	public void setDeactivatePhone(DeactivatePhone deactivation) {
		this.deactivatePhone = deactivation;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void purchase10ILDGlobalCardAndQuantity(String enrollment, String numIldCard)
			throws Exception {
		logger.info("Enrollment:: "+enrollment);
		if ("Yes".equalsIgnoreCase(enrollment)) {
			activationPhoneFlow.clickLink("Enroll in $10 Low Instant Balance Refill");
			// activationPhoneFlow.selectCheckBox("terms_");
			// activationPhoneFlow.submitButtonVisible("completeTransaction");
		} else {
			activationPhoneFlow.clickLink("Add Now");
			TwistUtils.setDelay(10);
			activationPhoneFlow.typeInTextField("qty", numIldCard);
			// activationPhoneFlow.pressSubmitButton("buy");
			activationPhoneFlow.pressSubmitButton("Add Now");
		}
	}

	public void completeTransaction() throws Exception {
		activationPhoneFlow.selectCheckBox("terms_");
		activationPhoneFlow.submitButtonVisible("completeTransaction");
		TwistUtils.setDelay(10);
	}
	
	private boolean buttonVisible(String button) {
		return activationPhoneFlow.buttonVisible(button) || activationPhoneFlow.submitButtonVisible(button);
	}

	public void finishPhoneActivationIfSim(String status, String sim)
			throws Exception {
		if (!sim.isEmpty()) {
			ESN currEsn = esnUtil.getCurrentESN();
			if (currEsn.getTransType().isEmpty()) {
				currEsn.setTransType("TW Web Activate Phone");
			}
			TwistUtils.setDelay(30);
			esnUtil.addRecentActiveEsn(currEsn, CDMA, status,
					currEsn.getTransType());
			currEsn.clearTestState();
			phoneUtil.clearOTAforEsn(currEsn.getEsn());
		} else {
			// Brightpoint

		}
	}

	public void selectDeviceType(String deviceType) throws Exception {
		if (!deviceType.equalsIgnoreCase("byop")) {
			activationPhoneFlow.clickH3("i have a new total wireless phone");
		} else {
			activationPhoneFlow.clickH3("i'm bringing my own phone");
		}
	}

	public void enterEsnToActivate() throws Exception {
		activationPhoneFlow.typeInTextField("deviceEsn", esnUtil.getCurrentESN().getEsn());
		if(activationPhoneFlow.checkboxVisible("esn-activation-check")){
			activationPhoneFlow.selectCheckBox("esn-activation-check");
			activationPhoneFlow.pressSubmitButton("deviceEsnSubmit");
		}
	}

	public void choosePhoneNumberOption(String numType) throws Exception {
		if (!numType.isEmpty()) {
			activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
		} else {
			activationPhoneFlow.clickH3("KEEP MY CURRENT PHONE NUMBER");
		}
	}

	public void chooseToAddDeviceOrActivateService(String choice) throws Exception {
		if (choice.equalsIgnoreCase("activate")) {
			// activationPhoneFlow.pressSubmitButton("checkOut");
			if (buttonVisible("No more lines at this time")) {
				pressButton("No more lines at this time");
			}
		}
	}
}