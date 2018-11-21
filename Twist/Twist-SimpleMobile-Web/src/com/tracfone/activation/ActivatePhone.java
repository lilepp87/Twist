package com.tracfone.activation;

// JUnit Assert framework can be used for verification
import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;

import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private static CboUtils cboUtils;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private DeactivatePhone deactivatePhone;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String STOLEN_STATUS = "Stolen";
	private static final String GSM = "GSM";
	private static final String CDMA = "CDMA";
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	private static Logger logger = Logger.getLogger(ActivatePhone.class);

	public ActivatePhone() {

	}

	public void setupEsnBasedOnStatusAndPart(String status, String part) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			ESN esn = esnUtil.popRecentActiveEsn(part);
//			phoneUtil.deactivateSMEsn(esn.getEsn());
			activationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
			deactivatePhone.deactivateEsn(esn.getEsn(), "PASTDUE");
			 
			activationPhoneFlow.navigateTo(rb.getString("sm.homepage")); //$NON-NLS-1$
			TwistUtils.setDelay(10);
			esnUtil.addRecentPastDueEsn(esn);
		}
	}
	
	public void deactivateTheEsn() throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		deactivatePhone.stDeactivateEsn(esnStr, "");
		phoneUtil.setDateInPast(esnStr);
		activationPhoneFlow.navigateTo(rb.getString("sm.homepage"));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}
	
	/*public void deactivateTheEsnWithReason(String reason) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		deactivatePhone.stDeactivateEsn(esnStr, reason);
		phoneUtil.setDateInPast(esnStr);
		activationPhoneFlow.navigateTo(rb.getString("sm.homepage"));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}*/
	
	public void deactivateWithReason(String reason) throws Exception{
		esnUtil.setCurrentBrand("SIMPLE_MOBILE");
		String esn = esnUtil.getCurrentESN().getEsn();
		System.out.println("cbo :"+cboUtils);
		cboUtils.callDeactivatePhone(esn, phoneUtil.getMinOfActiveEsn(esn), reason, esnUtil.getCurrentBrand());
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		
	}

	public void goToActivateOption() throws Exception {
		//activationPhoneFlow.clickLink(rb.getString("sm.activate"));
		
		activationPhoneFlow.clickLink("Activate/Reactivate[1]");
//		activationPhoneFlow.clickLink("Activate an Individual Plan");
	}
	
	public void goToActivateOptionToPort() throws Exception {
		//activationPhoneFlow.clickLink(rb.getString("sm.activate"));
		activationPhoneFlow.clickSpanMessage("Sign Out");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("Activate/Reactivate[1]");
//		activationPhoneFlow.clickLink("Activate an Individual Plan");
	}

	public void completeActivationProcessDependingOnStatusAndCellTech(String status, String cellTech)
			throws Exception {
		if (STOLEN_STATUS.equalsIgnoreCase(status)) {
			boolean genericError = activationPhoneFlow.cellVisible("We cannot process your transaction with the information " +
					"provided. Please contact our Customer Care Center at 1-877-430-CELL (2355) for further assistance.");
			boolean statusError = activationPhoneFlow.divVisible("The Serial Number entered has an invalid status.  "
					+ "Please check the number and try again.");
			Assert.assertTrue(genericError || statusError);
		} else {
			TwistUtils.setDelay(20);
			Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || activationPhoneFlow.h2Visible("Billing Summary"));
			finishPhoneActivation(cellTech, status);
			activationPhoneFlow.pressSubmitButton(rb.getString("sm.DoneButton"));
			if (activationPhoneFlow.submitButtonVisible(rb.getString("sm.NoThanksText"))) {
				activationPhoneFlow.pressSubmitButton(rb.getString("sm.NoThanksText"));
			}
			TwistUtils.setDelay(10);
		}
	}

	public void finishPhoneActivation(String cellTech, String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(20);
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "SM Activation");
		TwistUtils.setDelay(10);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
		TwistUtils.setDelay(15);
	}

	public static void joinSimCard(String simCard, ESN esn) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			esn.setSim(newSim);
			TwistUtils.setDelay(15);
			phoneUtil.addSimToEsn(newSim, esn);
			TwistUtils.setDelay(7);
		}
	}

	private void enterEsnZipCodeAndPin(String zipCode, String pinNumber) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("pin is " + newPin);
		storeRedeemData(newPin);
		
		activationPhoneFlow.typeInTextField(rb.getString("sm.ESNText"), esn.getSim());
		activationPhoneFlow.selectCheckBox(rb.getString("sm.accept.checkbox"));
		pressButton(rb.getString("sm.continue"));
		TwistUtils.setDelay(10);
		if (buttonVisible(rb.getString("sm.popUp.continue"))) {
			pressButton(rb.getString("sm.popUp.continue"));	
		}
		TwistUtils.setDelay(45);
		//New Activation, not reactivation
		if (activationPhoneFlow.linkVisible(rb.getString("sm.NewPhoneLink"))) {
			activationPhoneFlow.clickLink(rb.getString("sm.NewPhoneLink"));
			TwistUtils.setDelay(15);
			activationPhoneFlow.typeInTextField(rb.getString("sm.ZipcodeText"), zipCode);
			activationPhoneFlow.clickLink(rb.getString("sm.continue[1]"));
			TwistUtils.setDelay(20);
		}

		//activationPhoneFlow.clickLink(rb.getString("sm.UsePinLink"));
		activationPhoneFlow.clickLink("haveServicePlan");
		activationPhoneFlow.typeInTextField(rb.getString("sm.PINcardText"), newPin);
//		pressButton(rb.getString("sm.continue"));
		esnUtil.setCurrentESN(esn);
//		activationPhoneFlow.typeInTextField(rb.getString("sm.ESNText"), esn.getSim());
//		activationPhoneFlow.typeInTextField(rb.getString("sm.ZipcodeText"), zipCode);
//		activationPhoneFlow.typeInTextField(rb.getString("sm.PINcardText"), newPin);
	}
	
	public void enterCurrentEsnZipAndPin(String zip, String pin) throws Exception {
		enterEsnZipCodeAndPin(zip, pin);
		TwistUtils.setDelay(10);
		pressButton(rb.getString("sm.continue"));
	}
	
	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
	}

	public void enterEsnForPartZipCodeSimAndPinNumber(String status, String partNumber, String zipCode,
			String sim, String pinNumber) throws Exception {
		ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.startsWith("PH")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else {
				esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				joinSimCard(sim, esn);
			}
			esnUtil.setCurrentESN(esn);
			enterEsnZipCodeAndPin(zipCode, pinNumber);
			TwistUtils.setDelay(5);
			pressButton(rb.getString("sm.continue"));
			
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
			esnUtil.setCurrentESN(esn);
			enterEsnZipCodeAndPin(zipCode, pinNumber);
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		
		TwistUtils.setDelay(10);
		logger.warn(esn.getEsn());
	}

	public void enterEsnForPort(String partNumber, String sim) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField(rb.getString("sm.ESNText"), esn.getSim());
		activationPhoneFlow.selectCheckBox("sim-activation-check");
		pressButton(rb.getString("sm.continue"));
		if(activationPhoneFlow.getBrowser().paragraph("In order to connect to the SIMPLE Mobile 4G LTE network, your device must operate on Band II, IV or in certain areas, Band 12. For 3G or other 4G service, your device must operate on Band II (1900 MHz) or IV (1700/2100 MHz). GSM devices that do not support these bands may operate at 2G (EDGE) speeds.").isVisible()){
			activationPhoneFlow.pressButton("Continue[2]");
		}
		TwistUtils.setDelay(13);
		activationPhoneFlow.clickLink("showPortNumberLink");
		logger.warn(esn.getEsn());
	}

	public void enterEsn(String partNumber, String sim) throws Exception {
		ESN esn;
		if (partNumber.startsWith("PH")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			joinSimCard(sim, esn);
		}
		ESN fromEsn = esnUtil.getCurrentESN();
		if (fromEsn != null) {
			esn.setFromEsn(fromEsn);
		}
		esnUtil.setCurrentESN(esn);
	
		activationPhoneFlow.typeInTextField(rb.getString("sm.ESNText"), esn.getSim());
		activationPhoneFlow.selectCheckBox(rb.getString("sm.accept.checkbox"));
		pressButton("simSubmit");
		TwistUtils.setDelay(5);
		pressButton(rb.getString("sm.popUp.continue"));
		TwistUtils.setDelay(17);
		//pressButton(rb.getString("sm.continue"));
		//TwistUtils.setDelay(13);
		activationPhoneFlow.clickLink("showPortNumberLink");
		logger.warn(esn.getEsn());
	}
	
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
	}
	
	private boolean buttonVisible(String button) {
		if (activationPhoneFlow.buttonVisible(button) ||
				activationPhoneFlow.submitButtonVisible(button)) {
			return true;
		} else {
			return false;
		}
	}

	public void enterEsnZipSimAndPinNumber(String partNumber, String zipCode, String sim, String pinNumber)
			throws Exception {
		enterEsnForPartZipCodeSimAndPinNumber(NEW_STATUS, partNumber, zipCode, sim, pinNumber);
	}

	public void setupUpToPhoneWithZipSimPin(String status, String part, String zip, String sim, String pin) throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		ESN toEsn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			/*if (part.startsWith("PH")) {
				toEsn = new ESN(part, phoneUtil.getNewByopEsn(part, sim));
				toEsn.setSim(phoneUtil.getSimFromEsn(toEsn.getEsn()));
			} else {
				toEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
				joinSimCard(sim, toEsn); 
			}
			
			//ESN toEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
			//joinSimCard(sim, toEsn);
			toEsn.setFromEsn(fromEsn);
			esnUtil.setCurrentESN(toEsn);*/
		} else {
			goToActivateOption();
			enterEsnZipSimAndPinNumber(part, zip, sim, pin);
			completeActivation(sim);
			setupEsnBasedOnStatusAndEsn(status, esnUtil.getCurrentESN()); 
			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
			esnUtil.getCurrentESN().setFromEsn(fromEsn);
		}
	}
	
	public void completeActivation(String sim) throws Exception {
		if (sim != null && !sim.isEmpty()) {
			completeActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, GSM);
		} else {
			completeActivationProcessDependingOnStatusAndCellTech(NEW_STATUS, CDMA);
		}
	}
	
	public void setupEsnBasedOnStatusAndEsn(String status, ESN esn) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.navigateTo(rb.getString("sm.csrhome")); //$NON-NLS-1$
			deactivatePhone.deactivateEsn(esn.getEsn(), "PASTDUE");
			phoneUtil.setDateInPast(esn.getEsn());
			activationPhoneFlow.navigateTo(rb.getString("sm.homepage")); //$NON-NLS-1$

			esnUtil.addRecentPastDueEsn(esn);
		} else if (STOLEN_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));//$NON-NLS-1$
			deactivatePhone.deactivateEsn(esn.getEsn(), "STOLEN");
			activationPhoneFlow.navigateTo(rb.getString("sm.homepage")); //$NON-NLS-1$

			esnUtil.addRecentStolenEsn(esn);
		}
	}

	public void enterEsnForPartZipCodeSim(String status, String partNumber, String zipCode, String sim)
			throws Exception {
		ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.startsWith("PH")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
				TwistUtils.setDelay(6);
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else {
				esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				joinSimCard(sim, esn);
			}
			activationPhoneFlow.typeInTextField("deviceSim", esn.getSim());
			activationPhoneFlow.selectCheckBox(rb.getString("sm.accept.checkbox"));
			TwistUtils.setDelay(8);
			pressButton("simSubmit");
			TwistUtils.setDelay(15);
			if (buttonVisible(rb.getString("sm.popUp.continue"))) {
				pressButton(rb.getString("sm.popUp.continue"));	
			}			
			TwistUtils.setDelay(17);
			if (!activationPhoneFlow.linkVisible("showNewNumberLink")) {
				TwistUtils.setDelay(70);
			}
			activationPhoneFlow.clickLink("showNewNumberLink");
			activationPhoneFlow.typeInTextField("zipcode1", zipCode);
			
			//activationPhoneFlow.selectRadioButton("use_service_card_no");
			activationPhoneFlow.clickLink("Continue[1]");
			esnUtil.setCurrentESN(esn);
			
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
			esnUtil.setCurrentESN(esn);
			
			activationPhoneFlow.typeInTextField(rb.getString("sm.ESNText"), esn.getSim());
			activationPhoneFlow.selectCheckBox("sim-activation-check"); 
			pressButton(rb.getString("sm.continue"));

		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
	}
	
	
	public void setTheStatusToForEsnAndSim(String status, String esn, String sim) throws Exception {
		ESN toEsn;
		if (esn.startsWith("PH")) {
			toEsn = new ESN(esn, phoneUtil.getNewByopEsn(esn, sim));
			toEsn.setSim(phoneUtil.getSimFromEsn(toEsn.getEsn()));
		} else {
			toEsn = new ESN(esn, phoneUtil.getNewEsnByPartNumber(esn));
			joinSimCard(sim, toEsn); 
		}
		esnUtil.setCurrentESN(toEsn);
		Leasing.setLeaseStatus(status, toEsn.getEsn());
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
	
	
	public void setDeactivatePhone(DeactivatePhone deactivatePhone) {
		this.deactivatePhone = deactivatePhone;
	}
	
	public void addAPhoneWithNoAccount() throws Exception {	
		activationPhoneFlow.pressSubmitButton("Add device to My Account");
		activationPhoneFlow.typeInTextField("esn", esnUtil.getCurrentESN().getSim());
		
		pressButton("continueButton");
		TwistUtils.setDelay(8);
		if (activationPhoneFlow.textboxVisible("phone_number")) {  // as in case of Inactive ESN not required
			activationPhoneFlow.typeInTextField("phone_number", esnUtil.getCurrentESN().getMin());
			TwistUtils.setDelay(8);
		}
		pressButton("addButton");
		String successMsg = "Your device has been successfully added to your account!"; 
		Assert.assertTrue(activationPhoneFlow.divVisible(successMsg));	
	}

	public void logIn() throws Exception {
		activationPhoneFlow.navigateTo(rb.getString("sm.homepage")); //sm.homepage
		if (activationPhoneFlow.spanVisible("sign_in_out_image")) {
			activationPhoneFlow.clickSpanMessage("sign_in_out_image");
		} 
		if (activationPhoneFlow.spanVisible("My Account")) {
			activationPhoneFlow.clickSpanMessage("My Account");
		}
		
		ESN currEsn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField("userid", currEsn.getEmail());
		activationPhoneFlow.typeInPasswordField("password", currEsn.getPassword());
		//activationPhoneFlow.pressButton("default_login_btn");
		if (activationPhoneFlow.buttonVisible("default_login_btn")) {
			activationPhoneFlow.pressButton("default_login_btn");
		} else if (activationPhoneFlow.submitButtonVisible("default_login_btn")) {
			activationPhoneFlow.pressSubmitButton("default_login_btn");
		}
	}

	public void setupEsnBasedOnStatus(String status) throws Exception {
		if (status.equalsIgnoreCase("Inactive")) {
			ESN esn = esnUtil.getCurrentESN();
			System.out.println(esn.getEsn());
			deactivatePhone.stDeactivateEsn(esn.getEsn(), "PASTDUE");
		}
	}	

	public void addANewPhoneWithStatusSimAndZipcodeToExistingAccount(String status, String partNumber, String sim ,String zipCode) throws Exception{	
    	activationPhoneFlow.pressSubmitButton("Add device to My Account");
    	ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.startsWith("PH")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else {
				esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				joinSimCard(sim, esn);
//				new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
//				joinSimCard(sim, esn);
			}
			esnUtil.setCurrentESN(esn);
			activationPhoneFlow.typeInTextField("esn" , esn.getSim());
			activationPhoneFlow.pressButton("Continue[1]");
			TwistUtils.setDelay(15);
			activationPhoneFlow.clickLink("Activate");
			activationPhoneFlow.selectRadioButton("inactiveEsn");
			activationPhoneFlow.selectCheckBox("sim-activation-check");
			activationPhoneFlow.pressButton("Continue");
			if(activationPhoneFlow.getBrowser().paragraph("In order to connect to the SIMPLE Mobile 4G LTE network, your device must operate on Band II, IV or in certain areas, Band 12. For 3G or other 4G service, your device must operate on Band II (1900 MHz) or IV (1700/2100 MHz). GSM devices that do not support these bands may operate at 2G (EDGE) speeds.").isVisible()){
				activationPhoneFlow.pressButton("Continue[2]");
			}
			TwistUtils.setDelay(15);
			activationPhoneFlow.pressButton(rb.getString("sm.popUp.continue"));
			if (activationPhoneFlow.linkVisible(rb.getString("sm.NewPhoneLink"))) {
				activationPhoneFlow.clickLink(rb.getString("sm.NewPhoneLink"));
				activationPhoneFlow.typeInTextField(rb.getString("sm.ZipcodeText"), zipCode);
				activationPhoneFlow.clickLink(rb.getString("sm.continue[1]"));
				TwistUtils.setDelay(17);
			}
		}
	}

	public void wIFIRegistration() throws Exception {		
		activationPhoneFlow.navigateTo(rb.getString("sm.WIFI"));
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		String sim = esnUtil.getCurrentESN().getSim();
		String simLastFour = sim.substring(sim.length()-4);
		activationPhoneFlow.typeInTextField("input_phone", min);
		activationPhoneFlow.typeInTextField("input_esn", simLastFour);
		activationPhoneFlow.pressButton("enroll");
		TwistUtils.setDelay(20);
		activationPhoneFlow.typeInTextField("address1", "1200 charleston town");
		activationPhoneFlow.typeInTextField("city", "Mountain view");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("txtzip_code", "94043"); 
		activationPhoneFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("Congratulations!! We have all the information we need to enable " +
				"your Wi-Fi calling in our system. This process may take up to 1 hour to complete. To enable Wi-Fi calling on your phone:").isVisible());	
	}

	public void loginWithAmazonCredentialsAndGoto(String gotoLink) throws Exception {
		TwistUtils.setDelay(10);
		activationPhoneFlow.navigateTo(rb.getString("sm.amazonpage"));
		TwistUtils.setDelay(5);
		try{
			activationPhoneFlow.clickLink("Login with Amazon");
			Browser popup1 = activationPhoneFlow.getBrowser().popup("Amazon.com Sign In");
			popup1.emailbox("email").setValue("itquser@yopmail.com");
			popup1.password("password").setValue("tracfone");
			popup1.submit("Sign in using our secure server").click();
			TwistUtils.setDelay(10);
			Browser popup2 = activationPhoneFlow.getBrowser().popup("Amazon.com Consent");
			if(popup2.isVisible(popup1.submit("consentApproved"))){
				popup2.submit("consentApproved").click();
			}
		}catch(Exception e){
			System.out.println("Already login or popup came up");
		}
		activationPhoneFlow.clickLink(gotoLink);
		TwistUtils.setDelay(10);
	}
}    