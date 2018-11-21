package com.tracfone.twist.purchases;

import java.util.List;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.impl.eng.activation.Plan;
import com.tracfone.twist.myAccount.AbstractCreateAccount;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AbstractAddAirtime {

	private static final String REOCCURING = "Reoccuring";
	private static final String NOW = "Now";
	private static PhoneUtil phoneUtil;
	private RedemptionFlow redemptionFlow;
	private final Properties properties;
	private ESNUtil esnUtil;
	private AbstractCreateAccount createAccount;

	protected AbstractAddAirtime(String propsName) {
		properties = new Properties(propsName);
	}

	public void goToAddCardOnline() throws Exception {
		redemptionFlow.navigateTo("http://sit1.net10.com/existing_customer.jsp");
		redemptionFlow.clickLink(properties.getString("Redemption.AddorBuyAirtimeLink")); //$NON-NLS-1$
	}
	
	public void goToBuyAirtime() throws Exception {
		redemptionFlow.navigateTo(properties.getString("Twist.Net10URL"));
		redemptionFlow.clickLink(properties.getString("Redemption.AddorBuyAirtimeLink"));
	}
	
	public void enterPhoneNumberFromPart(String partNumber) throws Exception {
		enterPhoneNumberWithPartAnd(partNumber, "One-Time");
	}

	public void enterPhoneNumberWithPartAnd(String partNumber, String purchaseType) throws Exception {
	/*	String activeEsn;
		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			activeEsn = phoneUtil.getActiveEsnByPartWithNoAccount(partNumber);
		} else {
			activeEsn = phoneUtil.getActiveEsnByPartNumber(partNumber); 
		}
		esnUtil.setCurrentESN(new ESN(partNumber, activeEsn));*/
		//enterPhoneNumber();
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());

		redemptionFlow.typeInTextField(properties.getString("Redemption.PhoneTextPurchase"), min);
	}

	public void enterPhoneNumber() throws Exception {
		TwistUtils.setDelay(10);
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());

		redemptionFlow.typeInTextField(properties.getString("Redemption.PhoneText"), min); //$NON-NLS-1$
	}
	
	public void enterPinCard(String pinPart) throws Exception {
		TwistUtils.setDelay(15);
		String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		esnUtil.getCurrentESN().setPin(newPin);
		esnUtil.getCurrentESN().setRedeemType(false);
		
		/*redemptionFlow.clickLink(properties.getString("Activation.EnterServicePin"));
		redemptionFlow.typeInTextField(properties.getString("Activation.PinCardText"), newPin);
		System.out.println(newPin);
	    redemptionFlow.pressSubmitButton(properties.getString("Activation.Continue1Submit"));*/
		
		redemptionFlow.typeInTextField(properties.getString("Redemption.PinText"), newPin); //$NON-NLS-1$
		//redemptionFlow.clickLink(properties.getString("Redemption.AirtimeContinueButton")); //$NON-NLS-1$
		redemptionFlow.getBrowser().button(properties.getString("Redemption.AirtimeContinueButton")).click();
		TwistUtils.setDelay(20);
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))){//for home phone pop up
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		}
		TwistUtils.setDelay(15);
	}	

	public void chooseToPurchaseAirtime() throws Exception {
		//redemptionFlow.clickLink(properties.getString("Redemption.PurchaseContinue")); //$NON-NLS-1$
		//redemptionFlow.clickLink(properties.getString("switchPlan.continue"));
		redemptionFlow.getBrowser().button(properties.getString("switchPlan.continue")).click();
	}
	
	/*public void selectPlanWithILD(String purchaseType, String planStr, String phoneType, Integer numILD)
			throws Exception {
		Plan plan = Plan.getPlan(properties, planStr, phoneType);
		// TODO Change to h2 once the site is fixed
		// Site is currently using bold instead of h2 for some titles of plans
		boolean titleVisible = redemptionFlow.strongVisible(plan.getPlanTitle(properties))|| redemptionFlow.h2Visible(plan.getPlanTitle(properties));
		titleVisible = true;
		Assert.assertTrue("Could not find plan: " + planStr, titleVisible);
		redemptionFlow.selectRadioButton(plan.getRadioLabel(properties));

		if (REOCCURING.equalsIgnoreCase(purchaseType) && !plan.getPlanName(properties).contains("Easy Minutes")
			&& !plan.getPlanName(properties).contains("Family")) {
			redemptionFlow.selectCheckBox(plan.getEnrollCheck(properties));
		}
		
		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			esnUtil.getCurrentESN().setRedeemType(true);
		} else {
			esnUtil.getCurrentESN().setRedeemType(false);
		}
			
		redemptionFlow.pressSubmitButton(properties.getString("Activation.SelectServiceButton")); //$NON-NLS-1$
		
		//Confirm add to reserve
		if (redemptionFlow.submitButtonVisible(properties.getString("Redemption.AddReserve"))) {
			redemptionFlow.pressSubmitButton(properties.getString("Redemption.AddReserve"));
			esnUtil.getCurrentESN().setActionType(401);
		} else {
			esnUtil.getCurrentESN().setActionType(6);
		}
		
		//Confirm replace old minutes
		if (redemptionFlow.submitButtonVisible(properties.getString("Redemption.YesSubmit"))) {
			redemptionFlow.pressSubmitButton(properties.getString("Redemption.YesSubmit"));
		}
		
		if (redemptionFlow.submitButtonVisible("skip_service_plan_button_selection")) {

			if (numILD > 0) {
				redemptionFlow.typeInTextField(properties.getString("Redemption.ILDQty"),numILD.toString());
				redemptionFlow.pressSubmitButton("add_service_plan_button_selection");
			} else {
				redemptionFlow.pressSubmitButton("skip_service_plan_button_selection");
			}
		}
	}*/
	
	public void createNewAccountForEsnDependingOn(String purchaseType) throws Exception {
		if (!REOCCURING.equalsIgnoreCase(purchaseType) && 
				redemptionFlow.radioVisible(properties.getString("Account.SkipRadio"))) {
			redemptionFlow.selectRadioButton(properties.getString("Account.SkipRadio"));
			redemptionFlow.clickLink(properties.getString("Account.ContinueLink"));
		} else {
			createAccount.createNewAccountForEsn();
		}
	}
	
	public void submitPayment() throws Exception {
		esnUtil.getCurrentESN().setBuyFlag(true);
		redemptionFlow.h3Visible("CHARGE BREAKDOWN");
		redemptionFlow.pressSubmitButton(properties.getString("Redemption.AirtimeContinueButton")); //$NON-NLS-1$
	}
	
	public void addPin(String addNow) {
		TwistUtils.setDelay(20);
		ESN esn = esnUtil.getCurrentESN();
		//Dismiss Alerts about adding the pin
		if (redemptionFlow.submitButtonVisible(properties.getString("Redemption.YesSubmit"))) { //$NON-NLS-1$
			redemptionFlow.pressSubmitButton(properties.getString("Redemption.YesSubmit")); //$NON-NLS-1$
		}
		
		if (redemptionFlow.submitButtonVisible(properties.getString("Redemption.MegaCardSubmit"))) { //$NON-NLS-1$
			redemptionFlow.pressSubmitButton(properties.getString("Redemption.MegaCardSubmit")); //$NON-NLS-1$
		}
		
		if (NOW.equalsIgnoreCase(addNow)) {
			if (redemptionFlow.buttonVisible(properties.getString("Redemption.AddNow"))) {
				redemptionFlow.pressButton(properties.getString("Redemption.AddNow"));
			}
			
			esn.setActionType(6);
		} else {
			if (redemptionFlow.submitButtonVisible(properties.getString("Redemption.AddReserve"))) {
				redemptionFlow.pressSubmitButton(properties.getString("Redemption.AddReserve"));
			}
			esn.setActionType(401);
		}
		TwistUtils.setDelay(30);
	}

	public void completeTheProcess() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		//Check for success message
		TwistUtils.setDelay(8);
		Assert.assertTrue(redemptionFlow.h3Visible(properties.getString("Redemption.SuccessTitle"))); //$NON-NLS-1$
		redemptionFlow.clickLink(properties.getString("Redemption.FinishButton")); //$NON-NLS-1$
		esn.setTransType("Net10 Add Airtime");
		//phoneUtil.checkRedemption(esn);
	}

	public void redeemPINDependingOnStatusPhoneTypeAndPin(String status, String phone, String phoneType, String pin)
			throws Exception {
		if (properties.getString("Twist.StatusPastDuePinInQueue").equalsIgnoreCase(status)) { //$NON-NLS-1$
			ESN esn = esnUtil.popRecentActiveEsn(phone);
			esnUtil.setCurrentESN(esn);
			goToAddCardOnline();			
			enterPhoneNumber();
			enterPinCard(pin);
			completeTheProcess();
			boolean isNotAndroid = !properties.getString("Twist.Android").equalsIgnoreCase(phoneType); //$NON-NLS-1$
			if (isNotAndroid) {
				// Repeat redeem pin if not android
				// Activated with no pin so it will not be added to queue the first time
				goToAddCardOnline();
				enterPhoneNumber();
				enterPinCard(pin);
				completeTheProcess();
			}
			esnUtil.addRecentEsnWithPin(esn);
		}
	}

	public void addMinsToThePlanForParts(int familySize, String partNum2, String partNum3, String partNum4) throws Exception {
		addActivateEsnAsFamily(partNum2);
		addActivateEsnAsFamily(partNum3);
		addActivateEsnAsFamily(partNum4);
		
		enterRemainingFamilyPlanPhoneNumbersAndChooseAddNow(familySize);
	}

	private void addActivateEsnAsFamily(String partNum) {
		if (!partNum.isEmpty()) {
			ESN esn = new ESN(partNum, phoneUtil.getActiveEsnByPartWithNoAccount(partNum));
			esnUtil.getCurrentESN().addFamilyEsn(esn);
		}
	}

	public void enterRemainingFamilyPlanPhoneNumbersAndChooseAddNow(int familySize) throws Exception {
		List<ESN> familyEsns = esnUtil.getCurrentESN().getFamilyEsns();
		redemptionFlow.typeInTextField("input_name_1", "TwistFPNickName1");
		// fills active min number for family plans depends on number of members
		for (int i = 2; i <= familySize; i++) {
			if (i > 2) {
				redemptionFlow.pressButton("ADD LINE");
			}
			//Minus two because familyEsns does not include Account owner and is zero based
			ESN esn = familyEsns.get(i-2);
			String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
			
			redemptionFlow.typeInTextField("input_min_esn_" + i, min);
			redemptionFlow.typeInTextField("input_name_" + i, "TwistFPNickName" + i);
		}
		redemptionFlow.pressSubmitButton("continue_button_selection");
		for (int i = 1; i <= familySize; i++) {
			if (redemptionFlow.radioVisible("input_isEnrollLater_" + i + "[1]")) {
				redemptionFlow.selectRadioButton("input_isEnrollLater_" + i + "[1]");
			} else {
				redemptionFlow.selectRadioButton("input_isEnrollLater_" + i);
			}
		}
		redemptionFlow.selectCheckBox("apply_airtime_now_checkBox");
		redemptionFlow.pressSubmitButton("continue_addinfo_button");
	}
	
	public void enterPastDuePhoneNumber() throws Exception{
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		//String esnStr=esnUtil.getCurrentESN().getEsn();
		String min = esnUtil.getCurrentESN().getMin();
		//phoneUtil.deactivateSMEsn(esnStr);
		//phoneUtil.setDateInPast(esnStr);
		redemptionFlow.typeInTextField(properties.getString("Redemption.PhoneText"), min); //$NON-NLS-1$
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setCreateAccount(AbstractCreateAccount createAccount) {
		this.createAccount = createAccount;
	}

	public void addServicePlan() throws Exception {
		if(!redemptionFlow.linkVisible(properties.getString("Redemption.AddorBuyAirtimeLink"))){
			redemptionFlow.navigateTo(properties.getString("Twist.Net10URL"));
		}
		redemptionFlow.clickLink(properties.getString("Redemption.AddorBuyAirtimeLink"));
	}
	
	public void addServicePlanInsideAccount() throws Exception {
		redemptionFlow.clickLink(properties.getString("Redemption.AddAirtimeLink"));
	}

	public void reactivateAndCheckSummary() throws Exception {
		if (!redemptionFlow.h3Visible(properties.getString("Redemption.SuccessTitle"))) {
			TwistUtils.setDelay(60);
		}
		Assert.assertTrue(redemptionFlow.h3Visible(properties.getString("Redemption.SuccessTitle")));
		/*redemptionFlow.clickLink(properties.getString("Redemption.AirtimeContinueButton"));
		redemptionFlow.selectRadioButton(properties.getString("Activation.PinForActivateRadio"));
		redemptionFlow.pressSubmitButton(properties.getString("Redemption.AirtimeContinueButton"));*/
		redemptionFlow.clickLink(properties.getString("Activation.FinishButton"));
		phoneUtil.finishPhoneActivationWithSameMin(esnUtil.getCurrentESN().getEsn());
	
	}

	public void goToServicePlan() throws Exception {
		redemptionFlow.clickLink(properties.getString("Redemption.PurchaseContinue"));
		redemptionFlow.clickLink(properties.getString("Redemption.AirtimeContinueButton"));
		redemptionFlow.selectRadioButton(properties.getString("Activation.BuyPinActivateRadio"));
		redemptionFlow.pressSubmitButton(properties.getString("Activation.Continue1Submit"));
	}
	
	public void enrollInAutorefillForPlan(String plan) throws Exception {
		TwistUtils.setDelay(10);
		redemptionFlow.clickLink(properties.getString("enrollAutoRefillDiscount"));
		if(plan.equalsIgnoreCase("$65")){
			redemptionFlow.selectRadioButton(properties.getString("Plans.AndroidUnlimitedRadio"));
		}else if(plan.equalsIgnoreCase("$50")){
			redemptionFlow.selectRadioButton(properties.getString("Plans.AndroidUnlimitedRadio"));
		}else if(plan.equalsIgnoreCase("$40")){
			redemptionFlow.selectRadioButton(properties.getString("Plans.AndroidUnlimited40Radio"));
		}else if(plan.equalsIgnoreCase("$35")){
			redemptionFlow.selectRadioButton(properties.getString("Plans.AndroidUnlimited35Radio"));
		}
		redemptionFlow.selectCheckBox(properties.getString("Plans.NonData200EnrollCheck"));
		redemptionFlow.pressSubmitButton(properties.getString("Activation.SelectServiceButton"));
		redemptionFlow.pressSubmitButton(properties.getString("Redemption.AddNow"));
		TwistUtils.setDelay(5);
		//redemptionFlow.pressSubmitButton(properties.getString("Account.SkipLink"));
	}
	
	public void checkForRACEmployeeDiscountForPlan(String plan) throws Exception {
		TwistUtils.setDelay(5);
		if(plan.equalsIgnoreCase("$65")){
			//Assert.assertTrue(redemptionFlow.divVisible("-$9.75"));
			//Assert.assertTrue(redemptionFlow.listItemVisible(properties.getString("Discount Amount")));
			//Assert.assertTrue(redemptionFlow.listItemVisible(properties.getString("-$9.75")));
			Assert.assertTrue(redemptionFlow.getBrowser().byClassName("span-14 prepend-1 right last").getText().contains("-$9.75"));
		}else if(plan.equalsIgnoreCase("$50")){
			Assert.assertTrue(redemptionFlow.divVisible("-$7.50"));
		}else if(plan.equalsIgnoreCase("$40")){
			Assert.assertTrue(redemptionFlow.divVisible("-$6.00"));
		}else if(plan.equalsIgnoreCase("$35")){
			Assert.assertTrue(redemptionFlow.divVisible("-$5.25"));
		}
	}

	public void enterPastDueESN() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		//String esnStr=esnUtil.getCurrentESN().getEsn();
		String esnStr = esnUtil.getCurrentESN().getEsn();
		//phoneUtil.deactivateSMEsn(esnStr);
		//phoneUtil.setDateInPast(esnStr);
		redemptionFlow.typeInTextField(properties.getString("Activation.EsnText"), esnStr); //$NON-NLS-1$
		redemptionFlow.selectCheckBox("agree_terms");
		redemptionFlow.clickLink(properties.getString("Activation.ContinueLink"));
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))){//for home phone pop up
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		}
		if (redemptionFlow.textboxVisible(properties.getString("Activation.ZipCodeText"))){
            redemptionFlow.typeInTextField(properties.getString("Activation.ZipCodeText"), esn.getZipCode()); //$NON-NLS-1$
            }

	}

}