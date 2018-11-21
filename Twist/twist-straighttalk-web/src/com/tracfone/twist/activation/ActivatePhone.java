package com.tracfone.twist.activation;

import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.sahi.client.ElementStub;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.context.OnStraighttalkHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private DeactivatePhone deactivatePhone;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String REFURBISHED_STATUS = "REFURBISHED";
	private static final String CDMA = "CDMA";
	private static final String QUEUED = "QUEUED";
	private static final String NOW = "NOW";
	private static final String STOLEN_STATUS = "Stolen";
	private static final String ACTIVE_STATUS = "Active";
	//TODO: remove
	public String simPartNumber;
	//TODO: remove
	public String cellTechtype;
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	//TODO: remove
	private String error_msg;
	private static final String COMPLETED = "completed";
	//TODO: remove
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	private ResourceBundle rb1 = ResourceBundle.getBundle("straighttalk");
	//TODO: remove
	private Map familyEsns;

	public ActivatePhone() {

	}
//
	public void goToActivateOption() throws Exception {
		TwistUtils.setDelay(3);
		activationPhoneFlow.clickLink("ACTIVATE");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("ACTIVATE");
	}
//	
	public void goToActivateBYOPOption() throws Exception {
		activationPhoneFlow.clickLink("ACTIVATE");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("ACTIVATE[1]");
	}
//	
	public void selectHaveASIM() throws Exception {
		activationPhoneFlow.clickLink("SELECT");
	}
	
	private void enterEsnZipCodeAndPin(ESN esn, String zipCode, String pinNumber) throws Exception {
		enterEsnAndZip(esn, zipCode);

		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		activationPhoneFlow.selectRadioButton("use_service_card_yes");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.PinCardText.name, newPin);
		storeRedeemData(esn, newPin);
	}

	private void enterEsnAndZip(ESN esn, String zip) {
		if (esnUtil.getCurrentESN() != null) { // Added for LRP
			String email = esnUtil.getCurrentESN().getEmail();
			esnUtil.setCurrentESN(esn);
			esnUtil.getCurrentESN().setEmail(email);
			System.out.println(esnUtil.getCurrentESN().getEmail());
		} else {
			esnUtil.setCurrentESN(esn);
		}
		String esnStr;
		
		//Enter hex ESN for iPhone 
		if (esn.getPartNumber().contains("API") || esn.getPartNumber().contains("BYOPHEX")) {
			esnStr = esn.tryToEnterHexEsn(phoneUtil).toLowerCase();
		} else {
			esnStr = esn.getEsn();
		}
		
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.EsnText.name, esnStr);
		//activationPhoneFlow.pressButton("validateEsnBtn");
		if (esn.getPartNumber().startsWith("GP")) {
			activationPhoneFlow.pressButton("brand_phone_btn");
		} else if (activationPhoneFlow.buttonVisible("validateEsnBtn")) {
			activationPhoneFlow.pressButton("Submit");
		}
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ZipCodeText.name, zip);
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setActionType(6);
		esn.setRedeemType(false);
		esn.setTransType("Straight Talk Activation with PIN");
		esn.setPin(pin);
	}
//
	public void enterEsnForPartAndSim(String status, String partNumber, String sim) throws Exception {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(5);
		
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.startsWith("PHST") || partNumber.startsWith("ST256PBYOP")) {
				TwistUtils.setDelay(5);
				String esnStr = phoneUtil.getNewByopEsn(partNumber, sim);
				esn = new ESN(partNumber, esnStr);
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if (partNumber.startsWith("STSAS960")) {
				TwistUtils.setDelay(5);
				String esnStr = phoneUtil.getESNForIMSI(partNumber, "TF64SIMC4");
				esn = new ESN(partNumber, esnStr);
				joinSimCard(sim, esn);
			} else {
				String esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
				esn = new ESN(partNumber, esnStr);
				joinSimCard(sim, esn);
			}
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)
				|| REFURBISHED_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.getCurrentESN();
			// If esn not activated already during flow pop a recent one
			if (esn == null) {
				esn = esnUtil.popRecentActiveEsn(partNumber);
			}
			deact(status, esn, status);
			joinSimCard(sim, esn);
			goToActivateOption();
		} else if (status.equalsIgnoreCase("Returned")){//added for Micro Sanity scenario
			esn = esnUtil.getCurrentESN();
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		TwistUtils.setDelay(10);		
		if (activationPhoneFlow.textboxVisible("byop_sim_number")) {
			activationPhoneFlow.typeInTextField("byop_sim_number", esn.getSim());
			activationPhoneFlow.selectCheckBox("byop-sim-number-check");
			activationPhoneFlow.pressSubmitButton("CONTINUE");
		} else {
			activationPhoneFlow.typeInTextField("input_esn", esn.getEsn());
			activationPhoneFlow.selectCheckBox("activation-check");
			activationPhoneFlow.pressButton("CONTINUE");
		}
		
		if (activationPhoneFlow.textboxVisible("input_sim")) {
			activationPhoneFlow.typeInTextField("input_sim", esn.getSim());
			activationPhoneFlow.selectCheckBox("activation-check-sim");
			activationPhoneFlow.pressButton("CONTINUE[1]");
		}
		TwistUtils.setDelay(10);
		//If submitted ESN/SIM throws an error
		Assert.assertFalse(activationPhoneFlow.getBrowser().exists(activationPhoneFlow.getBrowser().byId("error")));
		
		if (esn.getFromEsn() == null) {
			esn.setFromEsn(fromEsn);
		}

		esnUtil.setCurrentESN(esn);
		TwistUtils.setDelay(12);
		
		if (activationPhoneFlow.getBrowser().paragraph("In order to connect to the Straight Talk 4G LTE network, your " +
				"device must operate on Band II, IV or in certain areas, Band 12. For 3G or other 4G service, your device " +
				"must operate on Band II (1900 MHz) or IV (1700/2100 MHz). GSM devices that do not support these bands may " +
				"operate at 2G (EDGE) speeds.").isVisible()){
			activationPhoneFlow.pressButton("Continue");
		}
		if (activationPhoneFlow.buttonVisible("flash_continue_btn")) {
			activationPhoneFlow.pressButton("flash_continue_btn");
		}
	}
	
	public void enterExistingEsnFromOtherBrand() {
		TwistUtils.setDelay(5);
		activationPhoneFlow.typeInTextField("input_esn", esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.selectCheckBox("activation-check");
		activationPhoneFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(10);
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.typeInTextField("phoneNumber", min);
		activationPhoneFlow.pressButton("CONTINUE[1]");
		TwistUtils.setDelay(10);
	}
//
	public void enterEsnForPartZipCodeSimAndPinNumberInWMKiosk(String status, String partNumber, String zipCode, String sim,
			String pinNumber) throws Exception {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(5);
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.startsWith("PHST") || partNumber.startsWith("ST256PBYOP")) {
				TwistUtils.setDelay(5);
				String esnStr =  phoneUtil.getNewByopEsn(partNumber, sim);
				esn = new ESN(partNumber,esnStr);
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if (partNumber.startsWith("STSAS960")) {
				TwistUtils.setDelay(5);
				String esnStr = phoneUtil.getESNForIMSI(partNumber, "TF64SIMC4");
				esn = new ESN(partNumber, esnStr);
				joinSimCard(sim, esn);
			} else {
				String esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
				esn = new ESN(partNumber, esnStr);
				joinSimCard(sim, esn);
			}
			if (partNumber.contains("VV10")) {
				TwistUtils.setDelay(5);
				enterEsnAndZip(esn, zipCode);
			} else {
				enterEsnZipCodeAndPin(esn, zipCode, pinNumber);
			}
			TwistUtils.setDelay(5);
			pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.CreateAccountButton.name);
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
			enterEsnZipCodeAndPin(esn, zipCode, pinNumber);
		} else if (STOLEN_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentStolenEsn(partNumber);
			enterEsnZipCodeAndPin(esn, zipCode, pinNumber);
			pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.CreateAccountButton.name);
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		esn.setFromEsn(fromEsn);
	}
	
	public void joinSimCard(String simCard, ESN esn) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			System.out.println("SIM: " + newSim);
			phoneUtil.addSimToEsn(newSim, esn);
		}
	}
//
	public void completeActivationProcessDependingOnStatusAndCellTech(String status, String cellTech) throws Exception {
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.divVisible("YOUR SERVICE WAS ACTIVATED. WE'RE ALMOST DONE!")) {
			TwistUtils.setDelay(85);
		}
		Assert.assertTrue(activationPhoneFlow.divVisible("YOUR SERVICE WAS ACTIVATED. WE'RE ALMOST DONE!"));
		Assert.assertFalse(activationPhoneFlow.textboxVisible("cvv"));
		
		activationPhoneFlow.clickLink("Setup Completed");
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
		finishPhoneActivation(cellTech, status);
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
		if (activationPhoneFlow.submitButtonVisible("default_cancel_btn")) {
			activationPhoneFlow.pressSubmitButton("default_cancel_btn");
		}		
		TwistUtils.setDelay(16);
		
		//StraightTalk Rewards PopUp
		if (activationPhoneFlow.buttonVisible("×Close[2]")) {
			activationPhoneFlow.getBrowser().button("×Close[2]").click();
		} else if (activationPhoneFlow.buttonVisible("×Close[1]")) {
			activationPhoneFlow.getBrowser().button("×Close[1]").click();
		} else {
			activationPhoneFlow.getBrowser().execute("location.reload()");
			TwistUtils.setDelay(3);
		}
	}
	
	public void completeWMActivationProcessDependingOnStatusAndCellTech(String status, String cellTech) throws Exception {
		TwistUtils.setDelay(20);
		if (!activationPhoneFlow.h2Visible("Steps To Complete Your Activation")) {
			TwistUtils.setDelay(55);
		}
		Assert.assertTrue(activationPhoneFlow.h2Visible("Steps To Complete Your Activation"));
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
		finishPhoneActivation(cellTech, status);
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}
	
	/*public void completeBYOPActivation() throws Exception {
		completeActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, CDMA);
	}*/
//	TODO: Probably delete and merge above
	public void completeActivation(String sim) throws Exception {
		if (sim != null && !sim.isEmpty() && !sim.equalsIgnoreCase("TF128PSIMS8M")) {
			completeWMActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, "GSM");
		} else {
			completeWMActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, CDMA);
		}
	}
	
	public void finishPhoneActivation(String cellTech, String status) throws Exception {
		TwistUtils.setDelay(10);
		ESN currEsn = esnUtil.getCurrentESN();
		if (currEsn.getTransType().isEmpty()) {
			currEsn.setTransType("ST Web Activate Phone");
		}
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, currEsn.getTransType());
		//TwistUtils.setDelay(10);
//		phoneUtil.checkRedemption(currEsn);
		currEsn.clearTestState();
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}
//
	private void deact(String status, ESN esn, String reason) throws Exception {
		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			deactivatePhone.stDeactivateEsn(esn.getEsn(), reason);
			phoneUtil.updateLatestIGRecordforEsn(esn.getEsn());
			phoneUtil.setDateInPast(esn.getEsn());
			activationPhoneFlow.navigateTo(OnStraighttalkHomePage.URL); //$NON-NLS-1$
			esnUtil.addRecentPastDueEsn(esn);
		}
	}
	
	public void makePastDue() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		deact(PAST_DUE_STATUS, esn, PAST_DUE_STATUS);
	}
//	
	public void setupUpToPhoneWithZipSimPin(String status, String part, String zip, String sim, String pin)	throws Exception {
		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			goToActivateOption();
			enterEsnForPartAndSim("New", part, sim);
			chooseANewNumberForZip(zip);
			enterPin(pin);
			completeActivationProcessDependingOnStatusAndCellTech("New", "CDMA");
		}
	}
//	
	public void enterWMEsnForPartSimZipCodeAndPinNumber(String status, String partNumber, String sim,
			String zip, String pin) throws Exception {
		ESN esn;
		if (partNumber.startsWith("PHST") || partNumber.startsWith("ST256PBYOP")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			joinSimCard(sim, esn);
		}
		esnUtil.setCurrentESN(esn);
		String pinNumber = phoneUtil.getNewPinByPartNumber(pin);
		String esnStr;
		
		//Enter hex ESN for iPhone 
		if (esn.getPartNumber().contains("API")) {
			esnStr = esn.tryToEnterHexEsn(phoneUtil);
		} else {
			esnStr = esn.getEsn();
		}
		
		activationPhoneFlow.typeInTextField("esn", esnStr);
		activationPhoneFlow.typeInTextField("zipcode", zip);
		activationPhoneFlow.typeInTextField("pin", pinNumber);
		activationPhoneFlow.pressButton("Continue");
		TwistUtils.setDelay(10);
		
		if (partNumber.startsWith("GP")) {
			activationPhoneFlow.pressButton("Accept");
		} else if (partNumber.startsWith("STZEZ2") || partNumber.startsWith("STREAX")
				|| partNumber.startsWith("PHST") || partNumber.startsWith("STAVV")) {
			if(activationPhoneFlow.getBrowser().exists(activationPhoneFlow.getBrowser().byId("cell_phone"))){
				String email = TwistUtils.createRandomEmail();
				String min = TwistUtils.generateRandomMin();
				activationPhoneFlow.typeInTextField("email", email);
				activationPhoneFlow.typeInTextField("cell_phone", min);
				activationPhoneFlow.pressButton("Continue[1]");
			}
		}
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
	}
//	
	public void goToStraightTalkHomePage() throws Exception {
		activationPhoneFlow.navigateTo(OnStraighttalkHomePage.URL);
	}
	
	public void goToActivateFromAccount() throws Exception {
		activationPhoneFlow.clickLink("ACTIVATE");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("ACTIVATE");
	}

	public void enterEsnZipCodeAndPinNumber(String zip, String pinPart) throws Exception {
		activationPhoneFlow.typeInTextField("input_esn", esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("ZipInputBtn");
		activationPhoneFlow.typeInTextField("input_zip", zip);
		activationPhoneFlow.pressButton("enter-sim-submit[1]");
		TwistUtils.setDelay(10);
		String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		activationPhoneFlow.clickLink("PinInputBtn");
		activationPhoneFlow.typeInTextField("service_card_input", newPin);
		if (activationPhoneFlow.buttonVisible("enter-sim-submit[2]")) {
			activationPhoneFlow.pressButton("enter-sim-submit[2]");
		} else {
			activationPhoneFlow.pressButton("enter-sim-submit");
		}
		storeRedeemData(esnUtil.getCurrentESN(), newPin);
	}
//	
	public void enterPin(String pinPart) throws Exception {
		if (!pinPart.isEmpty()) {
			TwistUtils.setDelay(5);
			if (!activationPhoneFlow.linkVisible("PinInputBtn") && !activationPhoneFlow.linkVisible("ENTER PIN")) {
				System.out.println("'PinInputBtn' & 'ENTER PIN' were not found, reloading the page & waiting for 15sec");
				activationPhoneFlow.getBrowser().execute("location.reload()");
				TwistUtils.setDelay(15);
				if (activationPhoneFlow.linkVisible("ZipInputBtn")) {
					activationPhoneFlow.clickLink("ZipInputBtn");
					activationPhoneFlow.typeInTextField("input_zip", esnUtil.getCurrentESN().getZipCode());
					activationPhoneFlow.pressButton("enter-actvn-zip-submit");
				} else if (activationPhoneFlow.textboxVisible("zip")) {
					activationPhoneFlow.typeInTextField("zip", esnUtil.getCurrentESN().getZipCode());
					activationPhoneFlow.pressButton("enter-zip-submit");
				}
			}
			TwistUtils.setDelay(10);
			if (activationPhoneFlow.linkVisible("PinInputBtn")) {
				activationPhoneFlow.clickLink("PinInputBtn");
			} else {
				activationPhoneFlow.clickLink("ENTER PIN");
			}
			
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			try {
				activationPhoneFlow.typeInTextField("service_card_input", pin);
			} catch (Exception e){
				activationPhoneFlow.getBrowser().byId("service_card_input").setValue(pin);
			}
			activationPhoneFlow.pressButton("enter-svcpin-submit");
		}
	}

	public void clickOnRewards() throws Exception {
		activationPhoneFlow.clickLink("Rewards");
	}

	public void enrollInLRPInsideAccount() throws Exception {
		activationPhoneFlow.pressButton("Enroll Now");
		activationPhoneFlow.selectCheckBox("termOfUse");
		activationPhoneFlow.pressButton("Continue");
		
		String email = esnUtil.getCurrentESN().getEmail();
		TwistUtils.setDelay(20);
		// To validate points from DB to UI for total reward Points
		String STURL=rb1.getString("ST_URL");		
		/*if (!STURL.contains("test.straighttalk.com")){
			if (activationPhoneFlow.getBrowser().emailbox("userID").isVisible()){
			activationPhoneFlow.getBrowser().emailbox("userID").setValue("sb41pm2e@tracfone.com");
			activationPhoneFlow.getBrowser().password("password").setValue("tracfone");
			activationPhoneFlow.getBrowser().submit("LOG IN").click();
			}
			TwistUtils.setDelay(60);
			logIn();
		}else {*/
			TwistUtils.setDelay(30);
			String points=phoneUtil.getTotalLRPPointsbyEmail(email);
			System.out.println("Reward Points " + points);
			Assert.assertTrue(activationPhoneFlow.getBrowser().div("lang_search pull-right col-sm-5 col-xs-5 col-lg-4").text().contains("200 Points Total"));
			activationPhoneFlow.navigateTo(rb1.getString("ST_URL"));//ST_URL
			//activationPhoneFlow.clickLink("Return to Account Summary");	
			activationPhoneFlow.clickLink("my account");
		//}
	}

	public void checkTheRewardPointsForLRPProgramEnrollment() throws Exception {
		// To validate points from DB to UI for Trans Type PROGRAM_ENROLLMENT
		String points = phoneUtil.getLRPPointsbyEmailforTranstype("PROGRAM_ENROLLMENT", esnUtil.getCurrentESN().getEmail());
		System.out.println("Enrollment Points " + points);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("rewardpoints_available").text()
				.contains("You Have" + points + " total points My Rewards Member since"));
	}

/*	public void setupUpToPhoneWithZipSimPinInsideLRPAccount(String status, String part, String zip, String sim, String pin)	throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		ESN toEsn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (part.startsWith("PHST")) {
				toEsn = new ESN(part, phoneUtil.getNewByopEsn(part, sim));
			} else {
				toEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
				joinSimCard(sim, toEsn); 
			}
			toEsn.setFromEsn(fromEsn);
			
			esnUtil.setCurrentESN(toEsn);
			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
		} else {
			goToActivateOption();
			enterEsnZipSimAndPinNumber(part, zip, sim, pin);
			completeActivation(sim);
			setupEsnBasedOnStatusAndEsn(status, esnUtil.getCurrentESN()); 
			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
			esnUtil.getCurrentESN().setFromEsn(fromEsn);
		}
	}*/
	
	public void verifyTheTotalRewardPointsInMyAccount() throws Exception {
		if (activationPhoneFlow.submitButtonVisible("default_submit_btn")) {
			activationPhoneFlow.pressSubmitButton("default_submit_btn");
		} else if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
		}
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);
		}
		String email = esnUtil.getCurrentESN().getEmail();
		// To validate points from DB to UI for total reward Points
		String points=phoneUtil.getTotalLRPPointsbyEmail(email);
		System.out.println("Reward Points " + points);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("rewardpoints_available").text()
				.contains("You Have" + points + " total points My Rewards Member since"));
	}

	
	public void checkForRewardPointAllocationForPinAndTransType(String pin, String transType)
			throws Exception {
		if (!pin.isEmpty()) {
			// Get points from DB for PinPart
			String points = phoneUtil.getLRPPointsforPinPart(pin);
			System.out.println("points::" + points);
			// To check for Reward points earned with UI
			if (transType.equals("REDEMPTION")) {
				Assert.assertTrue(activationPhoneFlow.getBrowser().table("form_fields_ild").text().contains("Reward Points earned " + points));
				Assert.assertTrue(activationPhoneFlow.getBrowser().table("rewards_txnsummary").text().contains("Reward Points may not be immediately added to your account. Please allow a period of 30 days for our system to update. Note: Reward points will only be earned upon Redemption of the Airtime Card, not when the card is added to your Straight Talk Reserve."));
			}
			System.out.println(esnUtil.getCurrentESN().getEmail());
			TwistUtils.setDelay(40);

			if (transType.equals("PORT_IN")) {
				completeUpgradeProcessInTas();
				loginToMyAccount();
			}

			String dbpoints = phoneUtil.getLRPPointsbyEmailforTranstype(transType, esnUtil.getCurrentESN().getEmail());
			System.out.println("dbpoints::" + dbpoints);
			Assert.assertTrue(points.equals(dbpoints));
		}
	}

	public void checkForRewardPointAllocationForPlanForTransType(String purchaseType, String plan, String transType) throws Exception {
		String autorefillpoints = phoneUtil.getLRPPointsForTransType("AUTO_REFILL");
		String plan1 = plan.replace(' ', '_');
		String pin = rb1.getString(plan1);
		String points = phoneUtil.getLRPPointsforPinPart(pin);

		// To check for Reward points earned with UI
		//System.out.println(activationPhoneFlow.getBrowser().table("form_fields_ild").getText());
		//Assert.assertTrue(activationPhoneFlow.getBrowser().table("form_fields_ild").text().contains("Reward Points earned " + points));
		// To check for Reward points earned with DB for TransType Activation
		System.out.println(esnUtil.getCurrentESN().getEmail());
		TwistUtils.setDelay(100);
		String dbpoints = phoneUtil.getLRPPointsbyEmailforTranstype(transType, esnUtil.getCurrentESN().getEmail());
		System.out.println("dbpoints::" + dbpoints);
		Assert.assertEquals(dbpoints, points, points);
		if (purchaseType.equalsIgnoreCase("REOCCURING")) {
			// AUTO_REFILL
			String dbrefillpoints = phoneUtil.getLRPPointsbyEmailforTranstype("AUTO_REFILL", esnUtil.getCurrentESN().getEmail());
			Assert.assertTrue(autorefillpoints.equals(dbrefillpoints));
		}
	}

	public void completeActivationProcessDependingOnStatusForLRPAndCellTech(String status, String cellTech) throws Exception {
		TwistUtils.setDelay(10);
		activationPhoneFlow.clickLink("Setup Completed");
		finishPhoneActivation(cellTech, status);
		//Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
		Assert.assertFalse(activationPhoneFlow.checkboxVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.TermsCheck.name));	
	}
	
	public void enterPinInsideLRPAndAddForTranstype(String pinNumber, String howToAdd , String TransType) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		ESN esn = esnUtil.getCurrentESN();
		
		if (NOW.equalsIgnoreCase(howToAdd)) {
			activationPhoneFlow.typeInTextField(PaymentFlow.BuyAirtimeStraighttalkWebFields.AirtimePinText.name, newPin);
			activationPhoneFlow.pressButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.GoSubmit.name);
			if (activationPhoneFlow.submitButtonVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddNowSubmit.name)) {
				activationPhoneFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddNowSubmit.name);
				esn.setActionType(6);
				Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
				checkForRewardPointAllocationForPinAndTransType(pinNumber, TransType);
				activationPhoneFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);		
				storeRedeemData(esn, newPin);
				phoneUtil.checkRedemption(esn);
			}
			Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
			Assert.assertTrue(activationPhoneFlow.getBrowser().table("rewards_txnsummary").text().contains("Reward Points may not be immediately added to your account. Please allow a period of 30 days for our system to update. Note: Reward points will only be earned upon Redemption of the Airtime Card, not when the card is added to your Straight Talk Reserve."));
			activationPhoneFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);		
			storeRedeemData(esn, newPin);
			phoneUtil.checkRedemption(esn);
						
		} else {
			activationPhoneFlow.typeInTextField(PaymentFlow.BuyAirtimeStraighttalkWebFields.AirtimePinQueueText.name, newPin);
			activationPhoneFlow.pressButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.GoQueueSubmit.name);
			if (activationPhoneFlow.submitButtonVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddReserveSubmit.name)) {
				activationPhoneFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddReserveSubmit.name);
			}
			esn.setActionType(401);
			Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
			Assert.assertTrue(activationPhoneFlow.getBrowser().table("rewards_txnsummary").text().contains("Reward Points may not be immediately added to your account. Please allow a period of 30 days for our system to update. Note: Reward points will only be earned upon Redemption of the Airtime Card, not when the card is added to your Straight Talk Reserve."));
			activationPhoneFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);		
			storeRedeemData(esn, newPin);
			phoneUtil.checkRedemption(esn);
			
		}
		
	}

	public void goToAccountSummary() throws Exception {
		activationPhoneFlow.clickLink("Return to Account Summary");
	}
	
	public void completeUpgradeProcessInTas() throws Exception {
		ESN toEsn = esnUtil.getCurrentESN();
		activationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
		//login
		activationPhoneFlow.typeInTextField("it1", "Itquser");
		activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
		
		if (activationPhoneFlow.buttonVisible("Login")) {
			activationPhoneFlow.pressButton("Login");
		} else {
			//activationPhoneFlow.pressSubmitButton("Login");
			activationPhoneFlow.pressSubmitButton("cb1");
		}
	
		activationPhoneFlow.clickLink("Incoming Call");
		//search profile
		activationPhoneFlow.typeInTextField("/r2:\\d:it1/", toEsn.getEsn());
		if (activationPhoneFlow.buttonVisible("Search Service")) {
			activationPhoneFlow.pressButton("Search Service");
		} else {
			activationPhoneFlow.pressSubmitButton("Search Service");
		}
		
		if (activationPhoneFlow.buttonVisible("Continue to Service Profile")) {
			activationPhoneFlow.pressButton("Continue to Service Profile");
		}
		//complete port
		activationPhoneFlow.clickLink("History");
		activationPhoneFlow.clickLink("Ticket History");
		activationPhoneFlow.clickLink("r2:1:r1:1:t1:0:ot2");
		activationPhoneFlow.clickLink("r2:1:r1:2:sdi13::disAcr");
		if (activationPhoneFlow.buttonVisible("r2:1:r1:2:r1:0:cb1")) {
			activationPhoneFlow.pressButton("r2:1:r1:2:r1:0:cb1");
		} else if (activationPhoneFlow.submitButtonVisible("r2:1:r1:2:r1:0:cb1")) {
			activationPhoneFlow.pressSubmitButton("r2:1:r1:2:r1:0:cb1");
		} else if (activationPhoneFlow.linkVisible("r2:1:r1:2:r1:0:cb1")) {
			activationPhoneFlow.clickLink("r2:1:r1:2:r1:0:cb1");
		}
		//activationPhoneFlow.pressButton("r2:1:r1:2:r1:0:cb1");
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));	
	}
	
	public void loginToMyAccount() throws Exception {
		activationPhoneFlow.navigateTo(OnStraighttalkHomePage.URL);
		if (activationPhoneFlow.linkVisible("Logout[1]")) {
			activationPhoneFlow.clickLink("Logout[1]");
		}
		String email = esnUtil.getCurrentESN().getEmail();
		String pwd = esnUtil.getCurrentESN().getPassword();
		
		activationPhoneFlow.clickLink("sign_in_out_link");
		activationPhoneFlow.typeInTextField("userID", email);
		activationPhoneFlow.typeInPasswordField("password", pwd);
		activationPhoneFlow.pressButton("default_login_btn");
	}

	//TODO: redo
	public synchronized void enterEsnForPartSim(String status,
			String partNumber, String simPart) throws Exception {
		activationPhoneFlow.clickLink("Incoming Call");
		simPartNumber = simPart; 
		ESN esn = null;

		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.matches("PH(SM|ST|TC|NT|TF).*")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if ("byop".equalsIgnoreCase(partNumber)) {
				esn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
			} else {
				esn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
						sim = simUtil.getNewSimCardByPartNumber(simPart);
						esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
					}
					phoneUtil.addSimToEsn(sim, esn);
				}
			}
			if (partNumber.matches("PHNT(64|128)PSIM(C4|T5).*")) {
				String pin = phoneUtil.getNewPinByPartNumber("NTAPP6U040FREE");
				phoneUtil.addPinToQueue(esn.getEsn(), pin);
			}
			pressButton("New Contact Account");
		} else if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
			esn = // esnUtil.popRecentActiveEsn(partNumber);//
			new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));
			phoneUtil.clearOTAforEsn(esn.getEsn());
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1/",esn.getEsn());
			pressButton("Search Service");
			// Continue to profile page.
			checkForTransferButton();
		}
		// else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
		// esn = esnUtil.popRecentPastDueEsn(partNumber);
		// }
		else {
			throw new IllegalArgumentException("Phone Status not found");
		}

		esnUtil.setCurrentESN(esn);
	}
	
	private void checkForTransferButton() {
		if (getButtonType("Continue to Service Profile")) {
			pressButton("Continue to Service Profile");
		}
	}
	
	public boolean getButtonType(String buttonName) {
		if (activationPhoneFlow.buttonVisible(buttonName) || activationPhoneFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized void activatePhoneByUsingPinDependingOnStatusOfCellTechZip(String pinPart, 
			String status, String cellTech, String zip) throws Exception {
		//Continue to profile page.
		this.cellTechtype = cellTech;
		checkForTransferButton();
		String newPin;
		if("NO PIN".equalsIgnoreCase(pinPart)){
			newPin = pinPart;
		}else{
			 newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		}
		
		esnUtil.getCurrentESN().setPin(newPin);
		activateWithNewPin(newPin, status, cellTech, zip);
	}

	private synchronized void activateWithNewPin(String newPin, String status, String cellTech, String zip)
			throws Exception {
		ESN esn  = esnUtil.getCurrentESN();
		String partNumber= esn.getPartNumber();
		
		if(!partNumber.matches("BYO.*")){
			activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation");
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			esn.setZipCode(zip);
			if(!"reactivation".equalsIgnoreCase(status)){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
				pressButton("Validate");
				TwistUtils.setDelay(5);
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", zip);
				/*
				if (partNumber.equalsIgnoreCase("byop")
						|| partNumber.equalsIgnoreCase("byophex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_0/");
				} else if (partNumber.equalsIgnoreCase("byod")
						|| partNumber.equalsIgnoreCase("byodhex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_1/");
				}
				*/
			} else {
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_2/");
			}
			
			pressButton("Continue");			
		} else {
			// Check for SIM null
			if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/")
					&& activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/")
							.getValue().equalsIgnoreCase("")) {
				String new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", new_sim);
				esn.setSim(new_sim);
			}
			if (!"reactivation".equalsIgnoreCase(status)) {
				if (simPartNumber.contains("CL7") && zip.equalsIgnoreCase("33178")) {
					// CLARO
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", "00692");
				} else {
					activationPhoneFlow.typeInTextField("/r2:\\d:r1:\\d:it2/", zip);
				}
			}
			// logger.info("PIN:" + newPin);
			esnUtil.getCurrentESN().setPin(newPin);
			if ("NO PIN".equalsIgnoreCase(newPin)) {

			} else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
				pressButton("Validate Card");
				TwistUtils.setDelay(10);
				String error_msg1 = activationPhoneFlow.getBrowser().div("d1::msgDlg::_cnt").getText();
				if (activationPhoneFlow.linkVisible("Close[1]")) {
					activationPhoneFlow.clickLink("Close[1]");
				}
				String error_msg2 = activationPhoneFlow.getBrowser()
						.span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
				Assert.assertTrue("ESN:" + esnUtil.getCurrentESN().getEsn() + ";" + "Error " + error_msg2 + ":" + error_msg1,
						activationPhoneFlow.cellVisible("Valid PIN"));
			}
			pressButton("Activate");
			TwistUtils.setDelay(10);
			if (activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()) {
				error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
			}
			if (activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/")
					.isVisible()) {
				error_msg = error_msg
						+ activationPhoneFlow.getBrowser()
								.table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").getText();
			}
			if (activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")) {
				pressButton("Code Accepted");
			}
			Assert.assertTrue("ESN:" + esnUtil.getCurrentESN().getEsn() + ";" + "Error " + error_msg,
					activationPhoneFlow.h2Visible("Transaction Summary"));
			// finishPhoneActivation(cellTech, status);
			esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "TAS Activation with PIN [" + esnUtil.getCurrentBrand()
					+ "]");
			pressButton("Refresh");
			// Assert.assertFalse("Please check for T number for ESN: " +
			// esnUtil.getCurrentESN().getEsn(),
			// activationPhoneFlow.getBrowser().span("/r2:\\d:ot26/").getText().startsWith("T"));
		}
	}

	public void checkoutAndProcessTransaction() throws Exception {
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")) {
			pressButton("Refresh Table");
			pressButton("Process Transaction");
			TwistUtils.setDelay(45);
			String currentesn = esnUtil.getCurrentESN().getEsn();
			Assert.assertTrue(activationPhoneFlow.cellVisible("Transaction Summary (Individual/Multiple Devices)"));
			logger.info("Processing Activation of master ESN:" + currentesn);
			String stageStatus = phoneUtil.getStatusOfESNFromStage(currentesn);
			Assert.assertTrue("STAGE TABLE STATUS NOT COMPLETED FOR MASTER:" + currentesn, stageStatus.equalsIgnoreCase(COMPLETED));
			finishPhoneActivationForTotalWirelss(esnUtil.getCurrentESN(), "CDMA", "New");
			phoneUtil.clearOTAforEsn(currentesn);
			if (familyEsns != null) {
				Set keys = familyEsns.keySet();
				Iterator itr = keys.iterator();
				String child_partnum;
				String child_esn;
				logger.info(keys.size());
				while (itr.hasNext()) {
					child_partnum = (String) itr.next();
					child_esn = (String) familyEsns.get(child_partnum);
					String childStageStatus = phoneUtil.getStatusOfESNFromStage(child_esn);
					Assert.assertTrue("STAGE TABLE STATUS NOT COMPLETED FOR CHILD:" + currentesn,
							childStageStatus.equalsIgnoreCase(COMPLETED));
					logger.info("Processing Activation of:" + child_partnum + " - " + child_esn);
					ESN childESN2 = new ESN(child_partnum, child_esn);
					finishPhoneActivationForTotalWirelss(childESN2, cellTechtype, "New");
					phoneUtil.clearOTAforEsn(child_esn);
				}
			}
			pressButton("Refresh");
		}
	}

	private void finishPhoneActivationForTotalWirelss(ESN childESN,String cellTech, String status) throws Exception {
//		TwistUtils.setDelay(13);
		esnUtil.addRecentActiveEsn(childESN, cellTech, status, "TAS Activation["+esnUtil.getCurrentBrand()+"]");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(childESN.getEsn());
	}
//	
	public void chooseANewNumberForZip(String zip) throws Exception {
		if (zip.equals("692")) {
			zip = "00692";
		}
		TwistUtils.setDelay(20);
		esnUtil.getCurrentESN().setZipCode(zip);
		if (activationPhoneFlow.linkVisible("ZipInputBtn")) {
			activationPhoneFlow.clickLink("ZipInputBtn");
			activationPhoneFlow.typeInTextField("input_zip", zip);
			activationPhoneFlow.pressButton("enter-actvn-zip-submit");
		} else if (activationPhoneFlow.textboxVisible("zip")) {
			activationPhoneFlow.typeInTextField("zip", zip);
			activationPhoneFlow.pressButton("enter-zip-submit");
		}
		TwistUtils.setDelay(6);
	}

	public void enterBYOPEsnZip(String Zipcode) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.EsnText.name,esn.getEsn());
		activationPhoneFlow.pressButton("validateEsnBtn");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ZipCodeText.name, Zipcode);
		if (activationPhoneFlow.textboxVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.SimCardText.name)){
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.SimCardText.name, esn.getSim());
		}
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.CreateAccountButton.name);
	}

	public void completeBYOPActivationFor5DayFreeTrail() throws Exception {
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("orderSummaryDIVFirst").text().contains("5-Day Unlimited Talk, Text & Data Plan with first 5 GB at High Speed, and then at 2G*"));
		completeActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, CDMA);
	}

	public void selectDeactivationOption() throws Exception {
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("Transactions");
		activationPhoneFlow.clickLink("Deactivation");
		TwistUtils.setDelay(5);
	}

	public void selectADeactivationReason(String reason) throws Exception {
		if (activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/")) {
			activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/");
			if (activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/")) {
				activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/");
			}
			if (activationPhoneFlow.buttonVisible("Continue")) {
				activationPhoneFlow.pressButton("Continue");
			} else if (activationPhoneFlow.submitButtonVisible("Continue")) {
				activationPhoneFlow.pressSubmitButton("Continue");
			}
		}
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/", reason);
		if (activationPhoneFlow.buttonVisible("Deactivate")) {
			activationPhoneFlow.pressButton("Deactivate");
		} else {
			activationPhoneFlow.pressSubmitButton("Deactivate");
		}
	}

	public void verifyMessageThatLineHasBeenSuccessfullyDeactivated() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h2Visible("Transaction Summary") || 
				activationPhoneFlow.divVisible("Your phone has been deactivated."));
	}

	public void logIn() throws Exception {
		activationPhoneFlow.navigateTo(rb1.getString("ST_Login"));
		ESN currEsn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField("userid", currEsn.getEmail());
		activationPhoneFlow.typeInPasswordField("password", currEsn.getPassword());
		pressButton("default_login_btn");
		TwistUtils.setDelay(10);
	}

	public void reactivateEsn() throws Exception {
		activationPhoneFlow.clickLink("Activate Device");
		activationPhoneFlow.pressButton("CONTINUE");
	}

	public void finishPhoneActivationWithAnd(String cellTech, String status) throws Exception {
		activationPhoneFlow.clickLink("Setup Completed");
		finishPhoneActivation(cellTech, status);
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
	}

	public void addESNToPromoGroup() throws Exception {
		int promoAdded = 0;
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		String email = esnUtil.getCurrentESN().getEmail();
		promoAdded = phoneUtil.LRPAddEsntoPromoGroup(esn, min, email);
		System.out.println("PromoAdded : " + promoAdded);
	}
	
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else if (activationPhoneFlow.submitButtonVisible(button)) {
			activationPhoneFlow.pressSubmitButton(button);
		}
//		} else if (activationPhoneFlow.buttonVisible("logged_in_continue_btn")) {
//			activationPhoneFlow.pressButton("logged_in_continue_btn");
//		}
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
	
	public void setDeactivatePhone(DeactivatePhone deactivation) {
		this.deactivatePhone = deactivation;
	}
	
	public void deactivateTheEsn() throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		deactivatePhone.stDeactivateEsn(esnStr, "");
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		phoneUtil.setDateInPast(esnStr);
		activationPhoneFlow.navigateTo(OnStraighttalkHomePage.URL);
	}
	
	public void goToStudentDiscount() throws Exception {
		String studentDiscount= rb1.getString("ST_URL") +"/Studentdiscount";
		activationPhoneFlow.navigateTo(studentDiscount);
	}
	
	public void checkStudentDiscountFrame() throws Exception {	
		Assert.assertTrue(activationPhoneFlow.h1Visible("Student discount"));
		Assert.assertTrue(activationPhoneFlow.divVisible("layoutContainers"));
		/*Assert.assertTrue(activationPhoneFlow.linkVisible("Register"));
		Assert.assertTrue(activationPhoneFlow.linkVisible("Log In"));
		Assert.assertTrue(activationPhoneFlow.h1Visible("Calling All Students!"));*/	
	}
	
	public void addReserveCardNowForLRP(String redeem, String pinNumber, String transType) throws Exception {
		if (redeem.equalsIgnoreCase("Yes")) {
			activationPhoneFlow.clickLink("Manage existing plans in your Straight Talk Reserve™");
			activationPhoneFlow.selectCheckBox("add_now_warning");
			activationPhoneFlow.pressSubmitButton("Add Now");
			activationPhoneFlow.pressButton("Confirm");
			checkForRewardPointAllocationForPinAndTransType(pinNumber, transType);
		}
	}
	
	public void goToDevices() throws Exception {
		activationPhoneFlow.clickLink("Devices");
	}
	
	public void selectDevice(String device) throws Exception {
		if (device.equalsIgnoreCase("Hotspot")) {
			//activationPhoneFlow.clickImage("Shop Hot Spot");
			activationPhoneFlow.getBrowser().area("SHOP DEVICES").click();
		} else if (device.equalsIgnoreCase("Home Phone")) {
			//activationPhoneFlow.clickImage("Shop New Home Phone");
			activationPhoneFlow.getBrowser().area("SHOP HOME PHONES").click();
		} else if (device.equalsIgnoreCase("Remote Alert")) {
			activationPhoneFlow.clickImage("Shop Remote Alert");
		} else {
			throw new IllegalArgumentException("Unknown Device type called: " + device);
		}
	}
	
	public void enterZipcode(String zip) throws Exception {
		TwistUtils.setDelay(8);
		//Zipcode popup
		if (activationPhoneFlow.getBrowser().byId("zipPopup_zc").isVisible()) {
			activationPhoneFlow.getBrowser().byId("zipPopup_zc").setValue(zip);
			activationPhoneFlow.getBrowser().byId("pdp_zipbutton").click();
		}
	}
	
	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(activationPhoneFlow.h3Visible("ORDER SUMMARY"));
	}
	
	public void earnPointForAugeo(String Reason) throws Exception {
		activationPhoneFlow.clickLink("myrewardslink");		
		activationPhoneFlow.clickLink("Earn Points[1]");
		
		if (Reason.equalsIgnoreCase("Submit story")){
			activationPhoneFlow.clickImage("Tell us what you think. Submit story");
			activationPhoneFlow.typeInTextField("fullName", "TestFullName");
			activationPhoneFlow.typeInTextArea("testimonial","Thanks for the Points!");
			activationPhoneFlow.pressSubmitButton("SUBMIT TESTIMONIAL");
			Assert.assertTrue(activationPhoneFlow.strongVisible("Thank You For Submitting Your Testimonial"));
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			String points = phoneUtil.getLRPPointsbyEmailforTranstype("TESTIMONIAL_EXPERIENCE", esnUtil.getCurrentESN().getEmail());
			Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("You have earned "+ points +" points.").isVisible());
		}else if(Reason.equalsIgnoreCase("Earn now")){
			activationPhoneFlow.clickImage("You answer. We'll respond with points. Earn now");
			activationPhoneFlow.getBrowser().radio("answers[][3]").click();
			activationPhoneFlow.pressSubmitButton("CAST YOUR VOTE");
			Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("Thank you for your answer(s). Here are the most current results!").isVisible());
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			String points = phoneUtil.getLRPPointsbyEmailforTranstype("POLLS_ENGAGEMENT", esnUtil.getCurrentESN().getEmail());
			Assert.assertTrue(activationPhoneFlow.h1Visible("Thanks for Voting! You just earned "+points+" points."));
		}else if(Reason.equalsIgnoreCase("Share to Earn")){
			activationPhoneFlow.clickImage("Share to Earn. Post on your favorite social channels about your savings experience with Straight Talk and earn points.");
			Assert.assertTrue(activationPhoneFlow.h3Visible("Share Straight Talk Rewards"));
			activationPhoneFlow.getBrowser().link(33).click();// facebook
			activationPhoneFlow.pressButton("×[1]");
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			TwistUtils.setDelay(120);
			String points = phoneUtil.getLRPPointsbyEmailforTranstype("SOCIAL_MEDIA_FACEBOOK", esnUtil.getCurrentESN().getEmail());
			//assertion Thanks for sharing! You earned 50 points.
		}else if(Reason.equalsIgnoreCase("Watch & Earn")){
			activationPhoneFlow.clickImage("Watch & Earn. Watch Now");
			//Videos not working
		}else if(Reason.equalsIgnoreCase("Shake now")){
			activationPhoneFlow.clickLink("My Rewards[1]");
			activationPhoneFlow.clickImage("Grab a new offer. Click to see what instant savings you can shake up. Shake now");
			Assert.assertTrue(activationPhoneFlow.h1Visible("DealShake™ Some Savings!"));
			activationPhoneFlow.clickImage("Shake your phone icon");
			TwistUtils.setDelay(30);
			Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("Print coupon and bring in store to redeem.").isVisible());
			Assert.assertTrue(activationPhoneFlow.imageVisible("Congrats"));
			activationPhoneFlow.clickLink("REDEEM");
		}
	}
	
	public void waitForPage() throws Exception {
		TwistUtils.setDelay(25);
	}
	
	public void selectInternationalCalling() throws Exception {
		activationPhoneFlow.clickLink("SHOP");
		activationPhoneFlow.clickLink("International Calling");
		activationPhoneFlow.clickLink("buynowglobalId");
	}
	
	public void enterMinForESN(String partNum) throws Exception {
		//String esn = esnUtil.getCurrentESN().getEsn();
		//System.out.println(esn);
		//get an active esn for the input part number
		ESN esn = new ESN(partNum, phoneUtil.getActiveEsnByPartNumber(partNum));
		esnUtil.setCurrentESN(esn);
		
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		
		activationPhoneFlow.typeInTextField("input_phone_popup", min);
		System.out.print(min);
		activationPhoneFlow.pressButton("ild-min-submit");
	}
	
	public void wIFIRegistration() throws Exception {
		activationPhoneFlow.navigateTo(rb1.getString("ST_WIFIURL"));
		String ActivatedMin = esnUtil.getCurrentESN().getMin();
		String sim = esnUtil.getCurrentESN().getSim();		
		String simlastfourdigit = (sim).substring(sim.length()-4);		
		activationPhoneFlow.typeInTextField("input_phone", ActivatedMin);
		activationPhoneFlow.typeInTextField("input_esn", simlastfourdigit);
		activationPhoneFlow.pressButton("enroll");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("city", "Mountain view");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("txtzip_code", "94043"); 
		activationPhoneFlow.pressButton("CONTINUE");
		Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("Congratulations!! We have all the " +
				"information we need to enable your Wi-Fi calling in our system. This process may take up " +
				"to 1 hour to complete. To enable Wi-Fi calling on your phone:").isVisible());		
	}
	
	public void selectModelAndPlan() throws Exception {
		TwistUtils.setDelay(15);
		activationPhoneFlow.clickLink("SELECT[1]");
		TwistUtils.setDelay(8);
		if (activationPhoneFlow.linkVisible("ADD TO CART")) {
			activationPhoneFlow.clickLink("ADD TO CART");
		} else {
			activationPhoneFlow.getBrowser().byId("select_phone_bundles_btn").click();
		}
		TwistUtils.setDelay(15);

		if (activationPhoneFlow.linkVisible("Continue[1]")) { // Remote Alert
			activationPhoneFlow.clickLink("Continue[1]");
		} else if (activationPhoneFlow.linkVisible("Continue[2]")) { // Homephone
			activationPhoneFlow.clickLink("Continue[2]");
		} else {
			activationPhoneFlow.clickLink("Continue[5]"); // Hotspot
		}
		activationPhoneFlow.clickLink("guestShopperContinue");
	}
	
	public void goToShop(String status) throws Exception {
		activationPhoneFlow.clickLink("SHOP");

		if (!activationPhoneFlow.linkVisible("SHOP SMARTPHONES")) {
			TwistUtils.setDelay(7);
		}
		activationPhoneFlow.clickLink("SHOP SMARTPHONES");

		if (status.equalsIgnoreCase("new")) {
			if (!activationPhoneFlow.getBrowser().isVisible(activationPhoneFlow.getBrowser().byId("zipPopup_zc"))) {
				activationPhoneFlow.getBrowser().execute("location.reload()");
				TwistUtils.setDelay(10);
			}
			activationPhoneFlow.getBrowser().byId("zipPopup_zc").setValue("94043");
			activationPhoneFlow.pressSubmitButton("Continue");
			TwistUtils.setDelay(10);
		} else {
			activationPhoneFlow.typeInTextField("phoneNumber", esnUtil.getCurrentESN().getMin());
			activationPhoneFlow.pressSubmitButton("phoneNumber-button");
		}
	}
	
	public void browsePhonesBasedOnBrand(String brand) throws Exception {
		if (brand.equalsIgnoreCase("apple")) {
			activationPhoneFlow.clickLink("WC_CatalogEntryDBThumbnailDisplayJSPF_120001_link_9b_name");
		} else if (brand.equalsIgnoreCase("samsung")) {
			activationPhoneFlow.clickLink("WC_CatalogEntryDBThumbnailDisplayJSPF_116501_link_9b_price");
		} else {
			throw new IllegalArgumentException("Unknown brand type: " + brand);
		}
		TwistUtils.setDelay(5);
	}
	
	public void finishOrder() throws Exception {
		TwistUtils.setDelay(45);
		String orderString = activationPhoneFlow.getBrowser().heading2("panel-title title-panel-box number-order").getText();
		System.out.println(orderString);
//		ESN esn = new ESN("Order Number", orderString.substring(orderString.indexOf(":") + 1, orderString.lastIndexOf("Order") - 1));
//		esnUtil.setCurrentESN(esn);
	}
	
	public void printOrderNumber(){		
		ElementStub el = activationPhoneFlow.getBrowser().heading2("panel-title title-panel-box number-order");
		String orderId = activationPhoneFlow.getBrowser().getText(el);
		System.out.println(orderId);
		ESN esn = new ESN("Order Number", orderId.substring(orderId.indexOf(":")+1,orderId.lastIndexOf("Order")-1));
        esnUtil.setCurrentESN(esn);

		String[] possOrder = orderId.split("13,24");
		int orderNum =  -1;
		for (int i = 2; i < possOrder.length; i++)	{
			try {
				orderNum = Integer.valueOf(possOrder[i]);
				break;
			} catch (NumberFormatException ex) {}			
		}		
	}

}