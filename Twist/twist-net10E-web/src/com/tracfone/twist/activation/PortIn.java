package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.ElementStub;

import org.junit.Assert;
import org.mortbay.log.Log;

import sun.util.logging.resources.logging;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PortIn {

	private static final String GSM = "GSM";
	private static final String ANDROID = "Android";
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private CboUtils cboUtils;
	private Properties properties = new Properties("Net10");
	private ResourceBundle rb = ResourceBundle.getBundle("Net10");
	public PortIn() {
		
	}
	
	public void selectTransferMyNumberFromAnotherCompany() throws Exception {
		/*activationPhoneFlow.selectRadioButton("optionsRadios[3]");

		activationPhoneFlow.clickLink(properties.getString("portInt.continue"));
		activationPhoneFlow.selectCheckBox("terms_and_cond");
		activationPhoneFlow.pressSubmitButton(properties.getString("termsAndCOnditions.continue"));*/
		activationPhoneFlow.clickLink(properties.getString("Activation.ActivatePhone"));
		activationPhoneFlow.clickLink(properties.getString("Activation.KeepMyNumber"));
		activationPhoneFlow.clickLink(properties.getString("Activation.Port"));
	}

	public void enterNewEsn(String partNumber, String simCard) throws Exception {
		String esnStr;
		ESN toEsn;
		if (partNumber.startsWith("PH")){
			esnStr = phoneUtil.getNewByopEsn(partNumber, simCard);
			toEsn = new ESN(partNumber, esnStr);
			toEsn.setSim(phoneUtil.getSimFromEsn(esnStr));
		} else if (partNumber.startsWith("NTSAS960")) {
			esnStr = phoneUtil.getESNForIMSI(partNumber, "TF64PSIMT5");
			toEsn = new ESN(partNumber, esnStr);
			toEsn.setSim(phoneUtil.getSimFromEsn(esnStr));
		} else {
			esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
			Log.warn(partNumber  + ":" + esnStr);
			toEsn = new ESN(partNumber, esnStr);
			if (simCard != null && !simCard.isEmpty()) {
				String newSim = simUtil.getNewSimCardByPartNumber(simCard);
				phoneUtil.addSimToEsn(newSim, toEsn);
				TwistUtils.setDelay(3);
			}
		}
		
		toEsn.setTransType("Net10 Port In");
		esnUtil.setCurrentESN(toEsn);

		activationPhoneFlow.typeInTextField("portEsn", esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.selectCheckBox("agree_terms");
		activationPhoneFlow.pressSubmitButton(properties.getString("phoneTransfer.continue"));
		if (activationPhoneFlow.submitButtonVisible(properties.getString("continue"))){//for home phone pop up
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		
		if (partNumber.startsWith("GP")) {
			activationPhoneFlow.pressSubmitButton(properties.getString("accept"));
		}
	}

	public void enterEsnAndNumberFromBrandWithPart(String brand, String oldPart) throws Exception {
		String newEsn = esnUtil.getCurrentESN().getEsn();
		//String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(oldPart, newEsn);
		String oldEsn = phoneUtil.getActiveEsnByPartNumber(oldPart);
		esnUtil.getCurrentESN().setFromEsn(new ESN(oldPart, oldEsn));
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		activationPhoneFlow.chooseFromSelect("current_provider", brand.toUpperCase());
		activationPhoneFlow.typeInTextField("old_esn", oldEsn);
		activationPhoneFlow.typeInTextField("old_esn_min", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void selectPurchase() throws Exception {
		if (activationPhoneFlow.submitButtonVisible("Buy Now")){
			activationPhoneFlow.pressSubmitButton("Buy Now");
		}
	}
	
	public void finishPortingNumberFor(String cellTech) throws Exception {
		activationPhoneFlow.pressSubmitButton(properties.getString("Payment.ContinueSubmit"));
		finishPortingNumberForWithPIN(cellTech,"Purchase");
	}

	public void finishPortingNumberForWithPIN(String cellTech, String pinPart) throws Exception {
		TwistUtils.setDelay(12);
		if(!pinPart.equalsIgnoreCase("Purchase")){
			enterPin(pinPart);	
		}
		TwistUtils.setDelay(35);

		if (activationPhoneFlow.buttonVisible(properties.getString("continue"))) {
			activationPhoneFlow.pressButton(properties.getString("continue"));
		} else if (activationPhoneFlow.h2Visible(properties.getString("unlimitAirtimePin"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		} else if (!activationPhoneFlow.h2Visible(properties.getString("orderSummary"))){
			TwistUtils.setDelay(40);
			//Assert.assertNotNull(phoneUtil.checkBI(esnUtil.getCurrentESN().getEsn()));
			if (activationPhoneFlow.imageVisible(properties.getString("codeAccepted"))) {
				while (activationPhoneFlow.imageVisible(properties.getString("codeAccepted"))) {
					activationPhoneFlow.clickImage(properties.getString("codeAccepted"));
					TwistUtils.setDelay(2);						
				}
				if(!(activationPhoneFlow.h2Visible(properties.getString("orderSummary")) || activationPhoneFlow.h3Visible(properties.getString("orderSummary")))){
					activationPhoneFlow.clickImage(properties.getString("continue"));					
				}
//					TwistUtils.setDelay(4);
			}
		}
		if (activationPhoneFlow.h2Visible(properties.getString("Stepstocompleteactivation"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		if (activationPhoneFlow.h2Visible(properties.getString("orderSummary")) || activationPhoneFlow.h3Visible(properties.getString("orderSummary"))) {//for upgrade flow
			if (activationPhoneFlow.imageVisible(properties.getString("continue"))) {
				activationPhoneFlow.clickImage(properties.getString("continue"));
			} else if(activationPhoneFlow.submitButtonVisible(properties.getString("continue"))) {
				activationPhoneFlow.pressSubmitButton(properties.getString("continue"));				
			}
			TwistUtils.setDelay(2);
			if (activationPhoneFlow.imageVisible(properties.getString("NoThanks"))) {
				activationPhoneFlow.clickImage(properties.getString("NoThanks"));
			}
		} else {
			Assert.assertTrue(activationPhoneFlow.h2Visible(properties.getString("ActivationMessage"))); //for port flow
			activationPhoneFlow.clickImage(properties.getString("continue"));
			
			if (activationPhoneFlow.imageVisible(properties.getString("continue"))) {
				activationPhoneFlow.clickImage(properties.getString("continue"));
			}
			if (activationPhoneFlow.imageVisible(properties.getString("NoThanks"))) {
				activationPhoneFlow.clickImage(properties.getString("NoThanks"));
			}
		}
//		}
		ESN toEsn = esnUtil.getCurrentESN();
		ESN fromEsn = toEsn.getFromEsn();
//		phoneUtil.finishPhoneActivationAfterPortIn(newEsn);
		phoneUtil.runIGateIn();
		if (fromEsn != null){
			phoneUtil.checkUpgrade(fromEsn, toEsn);//returns null pointer exception during external port flow
		}
		// To Auto Complete Port Ticket Using CBO	
		String orderType = phoneUtil.getOrderType(esnUtil.getCurrentESN().getEsn());
		if (orderType.equals("EPIR") || orderType.equals("PIR")	|| orderType.equals("IPI")) {
			String actionItemId = phoneUtil.getactionitemidbyESN(esnUtil.getCurrentESN().getEsn());
			String min = phoneUtil.getminforESN(esnUtil.getCurrentESN().getEsn());
			cboUtils.callcompleteAutomatedPortins(actionItemId, min);
		}
	}
	
	private void enterPin(String pinPart) {
		if (activationPhoneFlow.divVisible("ADD A PLAN")) {
			activationPhoneFlow.clickDivMessage("ADD A PLAN");
		}

		boolean field1Visible = activationPhoneFlow.textboxVisible("redcard1");
		boolean field2Visible = activationPhoneFlow.textboxVisible("input_pin1");
		boolean field3Visible = activationPhoneFlow.h2Visible(properties.getString("orderSummary")) || activationPhoneFlow.h3Visible(properties.getString("orderSummary"));
		boolean anyFieldVisible = field1Visible || field2Visible;
		boolean havePinPart = pinPart != null && !pinPart.isEmpty();
		
		if (havePinPart && field1Visible) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("redcard1", pin);
		} else if (havePinPart && field2Visible) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("input_pin1", pin);
		}/* else if (havePinPart) {
			Assert.fail("Could not enter pin for to esn");
		}*/ else {
			return;
		}
		
		 if (activationPhoneFlow.imageVisible(properties.getString("continue")) && !field3Visible) {
			activationPhoneFlow.clickImage(properties.getString("continue"));
		} else if (activationPhoneFlow.buttonVisible(properties.getString("continue")) && !field3Visible) {
			activationPhoneFlow.pressButton(properties.getString("continue"));
		} else if (havePinPart) {
			if(activationPhoneFlow.submitButtonVisible(properties.getString("addMyPin"))){//for port flow
				activationPhoneFlow.pressSubmitButton(properties.getString("addMyPin"));	
			}else if(activationPhoneFlow.submitButtonVisible(properties.getString("continue"))&& field2Visible){//for upgrade flow
				activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			}
		} else if (!havePinPart && anyFieldVisible) {
			activationPhoneFlow.pressSubmitButton("Skip");
		} 
		
	}

	public void enterPINDependingOnPhoneType(String pinPart, String phoneType) throws Exception {
		if (ANDROID.equalsIgnoreCase(phoneType)) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("input_pin1", pin);
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		} else {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name);
		}
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
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}

	public void chooseCarrierAndEnterDetailsForExternalNumber(String carrier)
			throws Exception {
		String min = TwistUtils.generateRandomMin();
		activationPhoneFlow.chooseFromSelect("current_provider", carrier);
		activationPhoneFlow.typeInTextField("input_phone_number", min);
		activationPhoneFlow.typeInTextField("input_account_number", "123456-7890");
		activationPhoneFlow.typeInPasswordField("input_account_password","1233");
		activationPhoneFlow.typeInTextField("__first_name","TwistFirstName");
		activationPhoneFlow.typeInTextField("__last_name","TwistLastName");
		activationPhoneFlow.typeInTextField("__address1","1295 Charleston Road");//userParsedHouseNumber  userParsedDirection  userParsedStreetName
		activationPhoneFlow.typeInTextField("__city","Mountain View"); //userParsedStreetType
		activationPhoneFlow.chooseFromSelect("__state","CA");
		activationPhoneFlow.typeInTextField("__zip_code","94043");
		activationPhoneFlow.typeInTextField("__country","US");
		activationPhoneFlow.typeInTextField("__confirm_email",TwistUtils.createRandomEmail());
		//activationPhoneFlow.typeInTextField("input_ssn","1234");
		activationPhoneFlow.pressSubmitButton("Continue");
		esnUtil.getCurrentESN().setMin(min);
		TwistUtils.setDelay(3);
		if(activationPhoneFlow.submitButtonVisible("user_entered_continue_button")){
			activationPhoneFlow.typeInTextField("userParsedHouseNumber","123");
			activationPhoneFlow.chooseFromSelect("userParsedDirection","N");
			activationPhoneFlow.typeInTextField("userParsedStreetName","Charleston Road");
			activationPhoneFlow.chooseFromSelect("userParsedStreetType","BLVD");
			activationPhoneFlow.pressSubmitButton("user_entered_continue_button");
		}
		else{
			//activationPhoneFlow.pressSubmitButton("user_entered_continue_button");
		}
	
	}
	
	public void enterEsnAndSim(String esnPart, String sim) throws Exception {
		ESN toEsn = null;
		String esnStr = esnUtil.getCurrentESN().getEsn();
		System.out.println("esnStr"+esnStr);
		toEsn = new ESN(esnPart, esnStr);
		toEsn.setTransType("Net10 Port In");
		esnUtil.setCurrentESN(toEsn);

		activationPhoneFlow.typeInTextField("portEsn", esnUtil.getCurrentESN().getEsn());
		//activationPhoneFlow.pressSubmitButton(properties.getString("phoneTransfer.continue"));
		activationPhoneFlow.selectCheckBox("agree_terms");
		activationPhoneFlow.pressSubmitButton("login_form_button");
		if(activationPhoneFlow.strongVisible("Special Announcement")){
			activationPhoneFlow.pressSubmitButton("Continue");
		}
		activationPhoneFlow.clickImage("close_button.gif[1]");
		
		if (esnPart.startsWith("GP")) {
			activationPhoneFlow.pressSubmitButton(properties.getString("accept"));
		}
	}
	
	public void enterPortOutESN() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		activationPhoneFlow.typeInTextField("portEsn", esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.selectCheckBox("agree_terms");
		activationPhoneFlow.pressSubmitButton(properties.getString("phoneTransfer.continue"));
	}
	
	public void updateTicketAndCompletePort() throws Exception {
		TwistUtils.setDelay(20);
		String ticket = phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn());
		System.out.println("New MIN (external) : "+esnUtil.getCurrentESN().getMin()+", Port ticket : "+ticket);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getMin(), ticket);
		loginToTas();
		activationPhoneFlow.clickLink("Incoming Call");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		pressButton("Search Service");
		activationPhoneFlow.clickLink("Transactions");
		TwistUtils.setDelay(15);
		phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getMin(), ticket);
		activationPhoneFlow.clickLink("Complete Ports");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1/",ticket);
		pressButton("Complete Port");
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}
	
	public void loginToTas(){
		activationPhoneFlow.navigateTo(rb.getString("Twist.TASURL"));
		if (activationPhoneFlow.passwordVisible("it2")) {
			activationPhoneFlow.typeInTextField("it1", "itquser");
			activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
			if (activationPhoneFlow.buttonVisible("cb1")){
				activationPhoneFlow.pressButton("cb1");
			} else {
				activationPhoneFlow.pressSubmitButton("Login");
			}
		}
	}
	
	public void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
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
}