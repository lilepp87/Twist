package com.tracfone.twist.activation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.activation.ActivatePhone;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
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
	
	private static final String CDMA = "CDMA";
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String UPGRADE_INSTR = "Your phone will be programmed, once it completes you will hear a successful confirmation message.";
	private static final String String = null;

	private Properties properties = new Properties();
	private boolean msg;
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	
	public ActivatePhone() {

	}

	public void goToActivateMyTracFoneFor(String partNumber) throws Exception {
//		activationPhoneFlow.clickLink("Remove Phone");
		activationPhoneFlow.clickLink("ACTIVATE");
		if (partNumber.matches("PH(TF).*")) {
			//activationPhoneFlow.pressSubmitButton("ACTIVATE");
			activationPhoneFlow.pressSubmitButton("activate_byop");
		} else {
			activationPhoneFlow.clickLink("ACTIVATE");
		}
//		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.WithUsRadio.name);
//		activationPhoneFlow.pressButton(properties.getString("submit"));
	}

	public void setupESNBasedOnStatusAndPart(String status, String part) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			ESN esn = esnUtil.popRecentActiveEsn(part); 
			deactivatePhone.stDeactivateEsn(esn.getEsn(), "PASTDUE");
			activationPhoneFlow.navigateTo(properties.getString("TF_URL")); //$NON-NLS-1$
			phoneUtil.setDateInPast(esn.getEsn());
			TwistUtils.setDelay(10);
			esnUtil.addRecentPastDueEsn(esn);
		}
	}
	
	public void skipRegister() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("skipstep"));
//		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.RegisterButton.name);
	}
	
	public void enterEsnSIMCardAndZipCode(String status, String partNumber, String simCard, String zipCode) throws Exception {
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
		String zip = zipCode;
		
		if(zipCode.equals("692"))
			zip="00"+692;
		
		esn.setZipCode(zip);
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
            
            if(!activationPhoneFlow.textboxVisible(esnText)){
            	activationPhoneFlow.getBrowser().execute("top.location.reload()");
            }
            TwistUtils.setDelay(10);
            activationPhoneFlow.typeInTextField(esnText, esn.getEsn());
            activationPhoneFlow.pressButton(properties.getString("continue"));
            submitform("continue1");
		} else {
            
			if (activationPhoneFlow.linkVisible("Verizon Compatible Phone")) {		
    			activationPhoneFlow.clickLink("Verizon Compatible Phone");
			} else {
				activationPhoneFlow.clickLink("Other Carrier / Don't Know");
    		}
            String esnText;
            if (activationPhoneFlow.textboxVisible("esnNew")) {
            	esnText = "esnNew";
            } else {
            	esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
            }
            activationPhoneFlow.typeInTextField(esnText, esn.getEsn());
            activationPhoneFlow.pressButton(properties.getString("continue"));
            submitform("continue1");
            /*activationPhoneFlow.typeInTextField("simNew", esn.getSim());
            activationPhoneFlow.pressButton(properties.getString("submit"));*/
		}
		activationPhoneFlow.pressButton(properties.getString("getanewphonenumber"));
			
		if(!simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status) && partNumber.startsWith("PHTF")){
            activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zip);                     
            submitform("continue2");
            activationPhoneFlow.clickLink("Continue");
            if(activationPhoneFlow.buttonVisible("Yes, take me there.")){
                  activationPhoneFlow.pressButton("Yes, take me there.");
            }
        }else if (!simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status)) {
            activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zip);                     
            submitform("continue2");
            submitform("continue3");
            if(activationPhoneFlow.divVisible("body")){
            if(activationPhoneFlow.getBrowser().div("body").text().contains("Unfortunately, your local calling area does not provide data services; " +
					"therefore, you will only be able to use your TracFone to make/receive calls and send/receive Text Messages. You can continue with the activation process acknowledging that your handset's browser will not work. Click 'YES'" +
					" to continue with the Activation Process or 'NO' if you wish to exit.")){
				
				activationPhoneFlow.pressSubmitButton("Yes");
				
			}
           }/*else{
			submitform("continue2");
			submitform("continue3");
			}*/
      } else if(simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status)){
            activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);
            submitform("continue2");
            submitform("continue1");
            if(activationPhoneFlow.divVisible("body")){
            if(activationPhoneFlow.getBrowser().div("body").text().contains("Unfortunately, your local calling area does not provide data services; " +
					"therefore, you will only be able to use your TracFone to make/receive calls and send/receive Text Messages. You can continue with the activation process acknowledging that your handset's browser will not work. Click 'YES'" +
					" to continue with the Activation Process or 'NO' if you wish to exit.")){
				
				activationPhoneFlow.pressSubmitButton("Yes");
				
			}
           }/*else{

				submitform("continue2");
				submitform("continue1");
			}*/
      }
      }
//			else if (NEW_STATUS.equalsIgnoreCase(status)) {
//			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);
//			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
//		}

	private void submitform(String buttonName) {
		if (activationPhoneFlow.buttonVisible(properties.getString(buttonName))) {
			activationPhoneFlow.pressButton(properties.getString(buttonName));
		} else if (activationPhoneFlow.submitButtonVisible(properties.getString(buttonName))) {
			activationPhoneFlow.pressSubmitButton(properties.getString(buttonName));
		}
	}

	public void enterEsnAndZipCode(String status, String partNumber, String zipCode) throws Exception {
		enterEsnSIMCardAndZipCode(status, partNumber, "", zipCode);
	}

	public void enterPinCardNumber(String pinCard1, String pinCard2, String pinCard3) throws Exception {
		if (!pinCard1.isEmpty()) {			
			enterPin(pinCard1, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard1Text.name);
			activationPhoneFlow.clickSpanMessage("Enter more");
			enterPin(pinCard2, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard2Text.name);
			activationPhoneFlow.clickSpanMessage("Enter more");
			enterPin(pinCard3, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard3Text.name);
			activationPhoneFlow.pressButton("Add PIN"); 
			
		} else {
			activationPhoneFlow.pressSubmitButton("skipDiv");
		}
	}

	private void enterPin(String pinPart, String pinText) {
		if (!pinPart.isEmpty()) {
			String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			storeRedeemData(newPin);

			System.out.println("PIN CARD: " + newPin);
			StringBuilder spacedPin = new StringBuilder(newPin.length() + 4);
			for (int i = 0; i < newPin.length(); i += 3) {
				spacedPin.append(newPin.substring(i, i + 3));
				spacedPin.append(' ');
			}
			activationPhoneFlow.typeInTextField(pinText, spacedPin.toString());
		}
	}

	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setActionType(401);
		esn.setRedeemType(false);
		esn.setTransType("Tracfone Activate with PIN");
	}

	public void delay() throws Exception {
		TwistUtils.setDelay(60);
	}

	public void delay40Seconds() throws Exception {
		TwistUtils.setDelay(20);
	}

	public void verifyMessageForCellTech() throws Exception {
		String zip = esnUtil.getCurrentESN().getZipCode();
		System.out.println("Zip::"+zip);
		/*if(zip.equalsIgnoreCase("63552")){
			for(int i=0;i<=6;i++){
			activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
			}
			//activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
		}*/
			for(int i=0;i<=6;i++){
                   if (activationPhoneFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
					activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
				}
			}
		
		if (activationPhoneFlow.h2Visible("PROGRAM MIN")) {
			activationPhoneFlow.pressButton(properties.getString("continue"));
		}
		
		if (activationPhoneFlow.buttonVisible(properties.getString("continue2"))) {
			activationPhoneFlow.pressButton(properties.getString("continue2"));
		} else if (activationPhoneFlow.submitButtonVisible(properties.getString("continue2"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
		}
		
		if (activationPhoneFlow.listItemVisible(properties.getString("phoneProgrammedSuccessfulMessage"))) {
			activationPhoneFlow.pressButton(properties.getString("continue"));
		}
		
		Assert.assertTrue(activationPhoneFlow.spanVisible(properties.getString("activationMessage")));
		if (activationPhoneFlow.buttonVisible(properties.getString("continue2"))) {
			activationPhoneFlow.pressButton(properties.getString("continue2"));
		} else if (activationPhoneFlow.submitButtonVisible(properties.getString("continue2"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
		} else if (activationPhoneFlow.buttonVisible("Go to Protect my Phone")) { // for BYOP part numbers
			activationPhoneFlow.pressButton("Go to Protect my Phone");
		}
	}

	public void skipPINNumber() throws Exception {
		if (activationPhoneFlow.submitButtonVisible("skipDiv")) {
			activationPhoneFlow.pressSubmitButton("skipDiv");	
		} else if (activationPhoneFlow.buttonVisible("skipDiv")) {
			 activationPhoneFlow.pressButton("skipDiv");	
		} else {
			activationPhoneFlow.pressButton("SKIP ");	
		}
	}

	public void completeTheProcessForPhone(String cellTech) throws Exception {
		if (activationPhoneFlow.buttonVisible(properties.getString("continueCompleteActivation"))) {
			activationPhoneFlow.pressButton(properties.getString("continueCompleteActivation"));
		} else if ("GSM".equalsIgnoreCase(cellTech)) {
			activationPhoneFlow.pressButton(properties.getString("continue2"));
		}
		ESN esn = esnUtil.getCurrentESN();
		if (activationPhoneFlow.buttonVisible(properties.getString("noThanks"))) {
			activationPhoneFlow.pressButton(properties.getString("noThanks"));
		}
		esnUtil.addRecentActiveEsn(esn, cellTech, NEW_STATUS, "Tracfone Activate");
		esnUtil.getCurrentESN().setMin(phoneUtil.getMinOfActiveEsn(esn.getEsn()));
		phoneUtil.clearOTAforEsn(esn.getEsn());

		
//		if (activationPhoneFlow.linkVisible(properties.getString("signOut"))) {
//		activationPhoneFlow.clickLink(properties.getString("signOut"));
//		}
//		else
//			activationPhoneFlow.navigateTo(properties.getString("TF_URL"));
//		if (!esnUtil.getCurrentESN().getPin().isEmpty()) {
//			phoneUtil.checkRedemption(esn);
//		}
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
	
	public void setDeactivatePhone(DeactivatePhone deactivatePhone) {
		this.deactivatePhone = deactivatePhone;
	}

	public void buyProtectionPlanWithForEsn(String plan, String cardType, String partnumber) throws Exception {	
		if (partnumber.startsWith("TFAPBYOP") || partnumber.startsWith("TFBYOPC")) {
			activationPhoneFlow.pressSubmitButton("Enroll Now");
		} else {
			if (plan.equalsIgnoreCase("Monthly Easy Exchange Plus")) {
				activationPhoneFlow.pressSubmitButton("Enroll Now");
			} else if (plan.equalsIgnoreCase("Annual Easy Exchange Plus")) {
				activationPhoneFlow.pressSubmitButton("Enroll Now");
			} else if ( plan.equalsIgnoreCase("Annual Easy Exchange")) {
				activationPhoneFlow.pressSubmitButton("Enroll Now");
			} else {
				throw new IllegalArgumentException("Could not find protection plan: " + plan);
			}
		}
		String card = TwistUtils.generateCreditCard(cardType);		
		activationPhoneFlow.chooseFromSelect("account_type", cardType);
		activationPhoneFlow.typeInTextField("account_number", card);
		activationPhoneFlow.typeInTextField("cvvnumber", "671");
		activationPhoneFlow.chooseFromSelect("credit_expiry_mo", "07");
		activationPhoneFlow.chooseFromSelect("credit_expiry_yr", "2021");
		activationPhoneFlow.typeInTextField("first_name", "Twist FirstName");
		activationPhoneFlow.typeInTextField("last_name", "Twist LastName");
		activationPhoneFlow.typeInTextField("payment_src_name", "Twist NickName");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("city", "Moutain View");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("zip", "94043");
		activationPhoneFlow.typeInTextField("phone", "3057150000");
		if (activationPhoneFlow.labelVisible("*Email")) {
			String randomEmail = esnUtil.getCurrentESN().getEmail();
			activationPhoneFlow.typeInTextField("useremail", randomEmail);
		}
//		activationPhoneFlow.selectCheckBox("terms");
		activationPhoneFlow.pressSubmitButton(properties.getString("submit2"));
		//activationPhoneFlow.clickLink(properties.getString("continue"));
		activationPhoneFlow.clickLink("Continue");
		if (activationPhoneFlow.buttonVisible(properties.getString("noThanks"))) {
			activationPhoneFlow.pressButton(properties.getString("noThanks"));
		}
	}

	public void enterBYOPESNAndZip(String zipCode) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		activationPhoneFlow.typeInTextField("esnNew", esn);
		activationPhoneFlow.pressButton("submitform");
		activationPhoneFlow.pressButton("get_new_phone_button");
		activationPhoneFlow.typeInTextField("zipCode", zipCode);
		activationPhoneFlow.pressButton("submitform");
		//activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}

	public void enterMinForReactivation() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		
		String esnText;
		if (activationPhoneFlow.textboxVisible("esnExis")) {
			esnText = "esnExis";
		} else {
			esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
		}
		activationPhoneFlow.typeInTextField(esnText, esn.getMin());
		activationPhoneFlow.pressButton(properties.getString("submit"));
	}

	public void selectThePlanToPurchasePromo(String plan,String Reoccuring, String promo) throws Exception {
//		activationPhoneFlow.getBrowser().heading4("PURCHASE AN AIRTIME CARD").click();
//		activationPhoneFlow.clickDivMessage("PURCHASE AN AIRTIME CARD");
		activationPhoneFlow.pressSubmitButton("Buy Now"); 
		activationPhoneFlow.clickDivMessage("close");
		if(Reoccuring.equalsIgnoreCase("Reoccuring")){
			String AutoRefillID = getAutoRefillIDFromCard(plan);
			activationPhoneFlow.selectCheckBox(AutoRefillID);
			}else{
	 		String linkId = getLinkIdFromCard(plan);
	 		//activationPhoneFlow.clickLink(linkId);
	 		activationPhoneFlow.clickDivMessage(linkId);
			}
		
		if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[1]")) {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[1]");
		} else if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[2]")) {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[2]");
		} else {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART");
		}
		
		if (activationPhoneFlow.buttonVisible("Continue to Checkout")) {
			activationPhoneFlow.pressButton("Continue to Checkout");
		} else if (activationPhoneFlow.submitButtonVisible("Continue to Checkout")) {
			activationPhoneFlow.pressSubmitButton("Continue to Checkout");
		} else if (activationPhoneFlow.buttonVisible("CONTINUE TO  CHECKOUT")) {
			activationPhoneFlow.pressButton("CONTINUE TO  CHECKOUT");
		} else {
			activationPhoneFlow.pressSubmitButton("CONTINUE TO  CHECKOUT");
		}
		//activationPhoneFlow.pressSubmitButton(properties.getString("continueToCheckout"));
		if(activationPhoneFlow.h3Visible("ADD ADDITIONAL PLAN")){
			if (promo.equalsIgnoreCase("True")) {
				activationPhoneFlow.pressSubmitButton(properties.getString("add"));
			} else {
				activationPhoneFlow.pressSubmitButton(properties.getString("skip"));
			}
		}
	} 

	private String getLinkIdFromCard(String card) {
		//String result = properties.getString("add");
		if ("1500 Minutes One Year".equalsIgnoreCase(card)) {
			return "+";
		} else if ("1000 Minutes One Year".equalsIgnoreCase(card)) {
			return "+[1]";
//		} else if ("800 Minutes One Year".equalsIgnoreCase(card)) {
//			return result+"[2]";
//		} else if ("400 Minutes One Year".equalsIgnoreCase(card)) {
//			return "+[2]";
		} else if ("400 Minutes".equalsIgnoreCase(card)) {
			return "+[2]";
//		} else if ("Double Minutes".equalsIgnoreCase(card)) {
//			return result+"[4]";
		} else if ("450 Minutes".equalsIgnoreCase(card)) {
			return "+[3]";
		} else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "+[4]";
		} else if ("120 Minutes".equalsIgnoreCase(card)) {
			return "+[5]";
		} else if ("60 Minutes".equalsIgnoreCase(card)) {
			return "+[6]";
		} else if ("30 Minutes".equalsIgnoreCase(card)) {
			return "+[7]";
//		} else if ("3000 Minutes".equalsIgnoreCase(card)) {
		} /*else if ("90 Minutes".equalsIgnoreCase(card)) {
			return "+[6]";
//		} else if ("2000 Minutes".equalsIgnoreCase(card)) {
		} */else if ("200 MinutesSP".equalsIgnoreCase(card)) {
			return "+[8]";
//		} else if ("1000 Minutes".equalsIgnoreCase(card)) {
		} else if ("300 Minutes".equalsIgnoreCase(card)) {
			return "+[9]";
//		} else if ("500 Minutes".equalsIgnoreCase(card)) {
		} else if ("500 Minutes".equalsIgnoreCase(card)) {
			return "+[10]";
		} else if ("750 Minutes".equalsIgnoreCase(card)) {
			return "+[11]";	
		} else if ("1500 Minutes".equalsIgnoreCase(card)) {
			return "+[12]";
		}else {
			throw new IllegalArgumentException("Could not find plan: " + card +	" on page");
		}
	}


	public void enterSimForReactivation() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		if (activationPhoneFlow.textboxVisible("iccid")) {
		activationPhoneFlow.typeInTextField("iccid", esn.getSim());
		System.out.println(activationPhoneFlow.buttonVisible((properties.getString("continue"))));
			if (activationPhoneFlow.buttonVisible(properties.getString("continue"))) {
				activationPhoneFlow.pressButton(properties.getString("continue"));
			} else if (activationPhoneFlow.submitButtonVisible(properties.getString("continue"))) {
				activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			}
		}
	
	}

	// real handset //
	public void goToActivateMyTracFoneForRealESN(String partNumber) throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("TF_URL")); //$NON-NLS-1$
		activationPhoneFlow.clickLink("ACTIVATE");
		if (partNumber.matches("PH(TF).*")) {
			activationPhoneFlow.clickLink("ACTIVATE[1]");
		} else {
			activationPhoneFlow.clickLink("ACTIVATE");
		}
		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.WithUsRadio.name);
		activationPhoneFlow.pressButton(properties.getString("submit"));
	}

	public void completeTheProcessForRealphone(String cellTech) throws Exception {
		if (activationPhoneFlow.buttonVisible(properties.getString("continueCompleteActivation"))) {
			activationPhoneFlow.pressButton(properties.getString("continueCompleteActivation"));
		} else if ("GSM".equalsIgnoreCase(cellTech)) {
			activationPhoneFlow.pressButton(properties.getString("continue"));
		}
		// ESN esn = esnUtil.getCurrentESN();
		if (activationPhoneFlow.buttonVisible(properties.getString("noThanks"))) {
			activationPhoneFlow.pressButton(properties.getString("noThanks"));
		}
	}

	// Wi-fi Calling registration//
	public void registerForWificalling() throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("TF_WIFI"));
		//activationPhoneFlow.pressSubmitButton("ENROLL");
		String Esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(Esn);
		logger.info(" CurrentMin: " + min);
		activationPhoneFlow.typeInTextField("input_phone", min);
		String last4digits = Esn.substring(Esn.length() - 4);
		activationPhoneFlow.typeInTextField("input_esn", last4digits);
		logger.info("Last4Digits:" + last4digits);
		activationPhoneFlow.pressButton("enroll"); //enroll//CHECK ELIGIBILITY
		activationPhoneFlow.typeInTextField("address1","1295 Charleston Road");
		activationPhoneFlow.typeInTextField("address2","Road 36");
		activationPhoneFlow.chooseFromSelect("state","CA");
		activationPhoneFlow.typeInTextField("txtzip_code","33178");
		activationPhoneFlow.typeInTextField("city","Mountain View");
		//activationPhoneFlow.getBrowser().div("CONTINUE");
		activationPhoneFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(10);
		activationPhoneFlow.getBrowser().paragraph("Congratulations!! We have all the information we need to enable your Wi-Fi calling in our system. This process may take up to 1 hour to complete. To enable Wi-Fi calling on your phone:").isVisible();
	}

	// WARP//
	public void goToActivate(String partNumber) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.setCurrentESN(esn);
		esnUtil.getCurrentESN().getPartNumber();
		goToActivateMyTracFoneFor(partNumber);

	}

	public void activatePhoneDependingOnStatusOfCellTechZip(String status, String cellTech, String zipCode) throws Exception {
		ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.getCurrentESN();
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		System.out.println("new ESN: " + esnUtil.getCurrentESN());

		String esnText;
		if (activationPhoneFlow.textboxVisible("esnExis")) {
			esnText = "esnExis";
		} else {
			esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
		}
		activationPhoneFlow.typeInTextField(esnText, esn.getEsn());
		activationPhoneFlow.pressButton(properties.getString("submit"));

		if (!activationPhoneFlow.textboxVisible("zipCode") && !activationPhoneFlow.buttonVisible("Cancel")) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		}

		if (NEW_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);
			if (activationPhoneFlow.submitButtonVisible(properties.getString("continue"))) {
				activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			} else if (activationPhoneFlow.buttonVisible(properties.getString("continue"))) {
				activationPhoneFlow.pressButton(properties.getString("continue"));
			}
		}
	}
 
 	public void enterESNForReactivation() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		
		String esnText;
		if (activationPhoneFlow.textboxVisible("esnNew")) {
			esnText = "esnNew";
		} else {
			esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
		}
		activationPhoneFlow.typeInTextField(esnText, esn.getEsn());
		activationPhoneFlow.pressButton(properties.getString("continue"));
		submitform("continue2");
        submitform("continue3");
	}

	public void enterPinCardNumberForPortIn(String pinCard1) throws Exception {
		enterPin(pinCard1, "input_pin1[1]");
		activationPhoneFlow.pressSubmitButton("Add PIN"); 		
	}
	
	public void selectThePlanToPurchaseAndDevice(String plan ,String Device) throws Exception {
		activationPhoneFlow.pressSubmitButton("Buy Now"); 
		activationPhoneFlow.clickDivMessage("close");
		String linkId = getLinkIdFromCard(plan);
		activationPhoneFlow.clickDivMessage(linkId);
		if (Device.equalsIgnoreCase("Enroll in Auto Refill")) {
			activationPhoneFlow.selectCheckBox("autorefillchkbox");
		}
		if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[1]")) {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[1]");
		} else if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[2]")) {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[2]");
		} else {
			activationPhoneFlow.pressButton("ADD PLAN(S) TO CART");
		}
		if (activationPhoneFlow.buttonVisible("Continue to Checkout")) {
			activationPhoneFlow.pressButton("Continue to Checkout");
		} else if (activationPhoneFlow.submitButtonVisible("Continue to Checkout")) {
			activationPhoneFlow.pressSubmitButton("Continue to Checkout");
		} else if (activationPhoneFlow.buttonVisible("CONTINUE TO  CHECKOUT")) {
			activationPhoneFlow.pressButton("CONTINUE TO  CHECKOUT");
		} else {
			activationPhoneFlow.pressSubmitButton("CONTINUE TO  CHECKOUT");
		}
		
		if (activationPhoneFlow.spanVisible("$49.99")){
			activationPhoneFlow.pressSubmitButton(properties.getString("skip"));
		}
	}

	public void checkForTheErrorMessageForDevice(String Device) throws Exception {		
		activationPhoneFlow.clickLink("Manage Auto-Refill");
		activationPhoneFlow.clickLink("Enroll");
		TwistUtils.setDelay(30);
		String errorTxt = activationPhoneFlow.getBrowser().div("error").text();
		if (Device.equalsIgnoreCase("Enroll in Auto Refill")) {
			Assert.assertTrue(errorTxt.contains("You have all Active phones enrolled in Auto-Refill"));
		} else {
			Assert.assertTrue(errorTxt.contains("You need to have an Active phone to enroll in Auto-Refill."));
		}
	
	}

	public void enterPinOrPurchasePlanPromoPlanWithPromoCode(String pinCard1,
			String pinCard2, String pinCard3, String plan, String promo,
			String promocode) throws Exception {
		
		if (!pinCard1.isEmpty()) {			
			enterPin(pinCard1, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard1Text.name);
			activationPhoneFlow.clickSpanMessage("Enter more");
			enterPin(pinCard2, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard2Text.name);
			activationPhoneFlow.clickSpanMessage("Enter more");
			enterPin(pinCard3, ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard3Text.name);
			activationPhoneFlow.typeInTextField("promo", promocode);
			activationPhoneFlow.pressButton("Add PIN"); 
			
		} else {
			activationPhoneFlow.pressSubmitButton("Buy Now"); 
			activationPhoneFlow.clickDivMessage("close");
			String linkId = getLinkIdFromCard(plan);
			//activationPhoneFlow.clickLink(linkId);
			activationPhoneFlow.clickDivMessage(linkId);
			
			if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[1]")) {
				activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[1]");
			} else if (activationPhoneFlow.buttonVisible("ADD PLAN(S) TO CART[2]")) {
				activationPhoneFlow.pressButton("ADD PLAN(S) TO CART[2]");
			} else {
				activationPhoneFlow.pressButton("ADD PLAN(S) TO CART");
			}
			
			activationPhoneFlow.typeInTextField("promo_code", promocode);
			if (activationPhoneFlow.buttonVisible("Continue to Checkout")) {
				activationPhoneFlow.pressButton("Continue to Checkout");
			} else if (activationPhoneFlow.submitButtonVisible("Continue to Checkout")) {
				activationPhoneFlow.pressSubmitButton("Continue to Checkout");
			} else if (activationPhoneFlow.buttonVisible("CONTINUE TO  CHECKOUT")) {
				activationPhoneFlow.pressButton("CONTINUE TO  CHECKOUT");
			} else {
				activationPhoneFlow.pressSubmitButton("CONTINUE TO  CHECKOUT");
			}
			//activationPhoneFlow.pressSubmitButton(properties.getString("continueToCheckout"));
			activationPhoneFlow.h3Visible("$49.99 ONLINE ONLY");
			if (promo.equalsIgnoreCase("True")) {
				activationPhoneFlow.pressSubmitButton(properties.getString("add"));
			} else {
				activationPhoneFlow.pressSubmitButton(properties.getString("skip"));
			}
		}
	}
	
	public void removeDeviceForESNStatus(String ESNStatus) throws Exception {		
		if(!ESNStatus.equalsIgnoreCase("Active")){
			deactivatePhone.stDeactivateEsn(esnUtil.getCurrentESN().getEsn(), "PASTDUE");
			activationPhoneFlow.clickLink("Logout");
			activationPhoneFlow.navigateTo(properties.getString("TF_URL"));			
			if (activationPhoneFlow.linkVisible("Go to Account Summary")) {
				activationPhoneFlow.clickLink("Go to Account Summary");
			} else if (activationPhoneFlow.linkVisible("My Account")) {
				activationPhoneFlow.clickLink("My Account");
			}
		}

		TwistUtils.setDelay(20);
		activationPhoneFlow.clickLink("Remove Device From Account");
		activationPhoneFlow.clickLink("Continue");
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("success_msg").text().contains("The selected device has been successfully removed from your account."));	
	}
	
	public void validatePromo(String promo) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String objid = phoneUtil.validatePromo(esnStr, promo);
		System.out.println(objid);
	}
	private String getAutoRefillIDFromCard(String card) {
		if ("450 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox";
		} else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[1]";
		} else if ("120 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[2]";
		} else if ("60 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[3]";
		} else if ("30 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[4]";
		}else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[5]";
		} else if ("500 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[6]";
		} else if ("750 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[7]";
		} else if ("1500 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[8]";
		}
		throw new IllegalArgumentException("Could not find Auto refill plan: " + card +	" on page");
	}
}