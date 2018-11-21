package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.tracfone.twist.autoReup.Enrollment;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AddServicePlan {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private RedemptionFlow redeemFlow;
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	private static Logger logger = Logger.getLogger(AddServicePlan.class);
	private static final String QUEUED = "QUEUED";
	public AddServicePlan() {

	}

	public void goToShopAndClickOnServicePlans() throws Exception {
		redeemFlow.clickLink("Plans");		
	}

	public void clickOnReUp() throws Exception {
		if (redeemFlow.linkVisible("ReUp")) {
			redeemFlow.clickLink("ReUp");
			redeemFlow.clickLink("ReUp[1]");
		} else {
			redeemFlow.clickLink("Auto-ReUp");
		}
	}

	public void clickOnAddToSimpleMobileStashOrAddNow() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		if (isButtonVisible("switch_plan_div_submit_now")) {
			clickButton("switch_plan_div_submit_now");
			System.out.println("Adding to Simple Mobile stash");
			esn.setActionType(401);
		} else if (isButtonVisible("switch_plan_div_submit_now[1]")) {
			clickButton("switch_plan_div_submit_now[1]");
			System.out.println("Adding now");
			esn.setActionType(6);
		} else {
			System.out.println("Adding to Simple Mobile stash");
			esn.setActionType(401);
		}		
	}

	public void clickOnValidateMyPhoneNumber() throws Exception {
		redeemFlow.pressSubmitButton("Go");
	}
	
	public void selectServicePlan(String purchaseType, String servicePlan) throws Exception {
		TwistUtils.setDelay(30);
		if (isButtonVisible("Buy plan")) {
			pressButton("Buy plan");
		} else if (isButtonVisible("Buy a Service Plan")) {
			pressButton("Buy a Service Plan");
		}
		Enrollment.selectPlan(servicePlan, redeemFlow.getBrowser());
		
		if (purchaseType.equalsIgnoreCase("Reoccuring")) {
			redeemFlow.pressButton("BUY PLAN WITH AUTO REUP");
		} else {
			redeemFlow.pressButton("ONE-TIME PURCHASE");
		}	
		storeRedeemData();
	}

	private void storeRedeemData() {
		ESN esn = esnUtil.getCurrentESN();
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("SM Buy Service");
	}

	public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(20);
		if (!redeemFlow.h2Visible(rb.getString("sm.OrderSummaryText"))) {
			TwistUtils.setDelay(70);
		}
		Assert.assertTrue(redeemFlow.h2Visible(rb.getString("sm.OrderSummaryText")));
		redeemFlow.pressSubmitButton("Continue");
		ESN currESN = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(currESN.getEsn());
		phoneUtil.checkRedemption(currESN);
		esnUtil.addBackActiveEsn(currESN);
	}

	private void storeILDRedeemData() {
		ESN esn = esnUtil.getCurrentESN();
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		if (esn.getTransType().isEmpty()) {
			esn.setTransType("SM Buy $10 ILD");
			esn.setActionType(6);
		}
	}

	public void goToInternationalCalling() throws Exception {
		redeemFlow.clickLink(rb.getString("sm.ILD"));
		clickButton(rb.getString("sm.Buy10ILD"));
	}

	public void goTo10ILD(String numIld) throws Exception {
		redeemFlow.clickLink(rb.getString("sm.ILD"));
		clickButton(rb.getString("sm.Buy10ILD_ReUp"));
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		redeemFlow.typeInTextField("qty", numIld);
		redeemFlow.typeInTextField(rb.getString("sm.ILDMinText"), min);
		clickButton("Submit");
	}
	
/*	public void enterPhoneNumber(String partNumber) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());

		redeemFlow.typeInTextField(rb.getString("sm.PurchaseMinText"), min);
		clickButton(rb.getString("sm.continue[1]"));
		redeemFlow.typeInTextField("min_", min);
		redeemFlow.pressSubmitButton(rb.getString("sm.continue"));
		TwistUtils.setDelay(15);
	}*/

	public void enterCountryCode(String country) throws Exception {
		redeemFlow.clickLink(rb.getString("sm.ILD"));
		redeemFlow.typeInTextField("input_country_code", "011"+ country);
		redeemFlow.typeInTextField("input_number", "9985425336");
		redeemFlow.pressSubmitButton("submit");
		//TwistUtils.setDelay(5);
		boolean genericSuccess = redeemFlow.spanVisible("Congratulations, the destination you've entered is available on this plan.");
		if ("51".equalsIgnoreCase(country)) {
			Assert.assertFalse("Mexico number need custom message", genericSuccess);
			//TODO check for custom message
		} else {
			Assert.assertTrue("Country code not found for ILD", genericSuccess);
		}
	}

	public void clickButton(String buttonType) {
		if (redeemFlow.buttonVisible(buttonType)) {
			redeemFlow.pressButton(buttonType);
		} else {
			redeemFlow.pressSubmitButton(buttonType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redeemFlow = tracfoneFlows.redemptionFlow();
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

	public void enterPhoneNumberForPastDueEsn() throws Exception {
		String esn  = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		phoneUtil.deactivateSMEsn(esn);
		redeemFlow.typeInTextField(rb.getString("sm.ILDMinText"), min);
		redeemFlow.typeInTextField("qty", "1");
		redeemFlow.pressSubmitButton("BUY");
		Assert.assertTrue(redeemFlow.divVisible("This phone number is currently inactive. To " +
				"activate your phone please select the “Activate/Reactivate” option from the Home Page."));
	}

	public void enterNewPin(String partNumber) throws Exception {
		TwistUtils.setDelay(15);
		redeemFlow.clickLink("ReUp My Service Plan");
		TwistUtils.setDelay(15);
		redeemFlow.clickLink("Add Plan");
		String pin = phoneUtil.getNewPinByPartNumber(partNumber);
		redeemFlow.typeInTextField("new_airtime_pin", pin);
		redeemFlow.pressButton("add_srvc_card_btn");
		storeRedeemData(pin);
	}
	
	public void addAPhone(String partNumber) throws Exception {
		ESN esn = new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));
		esnUtil.setCurrentESN(esn);
		String sim = phoneUtil.getSimFromEsn(esn.getEsn());
		//String activeMin = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		redeemFlow.pressSubmitButton("Add device to My Account");
		redeemFlow.typeInTextField("esn", sim);
		redeemFlow.pressButton("Continue[1]");
		//redeemFlow.typeInTextField("phone_number", activeMin);
		//redeemFlow.pressSubmitButton("Add");
		//String failureMsg = "The Phone Number you entered does not belong to Simple Mobile. Please check the number and try again.";
		String failureMsg = "The SIM Number you entered does not belong to a Simple Mobile device. Please review the number and try again.";
		Assert.assertTrue(redeemFlow.divVisible(failureMsg));
		//Assert.assertTrue(redeemFlow.h1Visible("My Account Summary"));
	}
	
	public void addOrSkip10ILD(String numberOfCards) throws Exception {
		if (numberOfCards.equalsIgnoreCase("0")) {
			//redeemFlow.pressSubmitButton("skip_label");
			redeemFlow.pressButton("SKIP");
		} else {
			redeemFlow.typeInTextField("qty", numberOfCards);
			redeemFlow.pressButton("One-time Purchase");
			TwistUtils.setDelay(15);
			storeILDRedeemData();
		}
	}
	
	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
		esn.setTransType("SM Redeem PIN");
	}

	public void addNowAndReviewSummary() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		if (isButtonVisible("switch_plan_div_submit_now")) {
			clickButton("switch_plan_div_submit_now");
			System.out.println("Adding to Simple Mobile stash");
			esn.setActionType(401);
		}	
		Assert.assertTrue(redeemFlow.h2Visible("ORDER SUMMARY"));
		redeemFlow.pressSubmitButton("Continue");
	}
	
	//finishPhoneActivation(cellTech, status);
	
	public boolean isButtonVisible(String buttonName) {
		if (redeemFlow.buttonVisible(buttonName) || redeemFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}

	public void selectManageReserve() throws Exception {
		redeemFlow.clickLink("Manage Stash");
	}

	public void addCardInReserveNow() throws Exception {
		redeemFlow.selectCheckBox("add_now_warning");
		redeemFlow.pressSubmitButton("Add Now");
		redeemFlow.pressButton("Confirm");
		TwistUtils.setDelay(16);
		Assert.assertTrue(redeemFlow.h2Visible("ORDER SUMMARY"));
		redeemFlow.pressSubmitButton("default_submit_btn");
	}

	public void enrollIn10ILD() throws Exception {
		redeemFlow.pressSubmitButton("Enroll in Instant Low Balance Refill");
		storeILDRedeemData();
	}

	public void clickMyAccountBuyPlan() throws Exception {
		redeemFlow.clickLink("ReUp My Service Plan");
		redeemFlow.clickLink("Buy Plan");
		//TODO should not be need
		TwistUtils.setDelay(20);
		//redeemFlow.clickH1("Switch Plan");
		redeemFlow.clickSpanMessage("All Plans");
	}
	
	private void pressButton(String button) {
		if (redeemFlow.buttonVisible(button)) {
			redeemFlow.pressButton(button);
		} else {
			redeemFlow.pressSubmitButton(button);
		}
	}
	
	public void enterPhoneNumberAndQuantity (String esnPart , String numIld ){
		String esnStr = phoneUtil.getActiveEsnByPartNumber(esnPart);
		phoneUtil.clearOTAforEsn(esnStr);
		String min = phoneUtil.getMinOfActiveEsn(esnStr);
		ESN esn = new ESN(esnPart, esnStr);
		//storeRedeemData(esn);
		esnUtil.setCurrentESN(esn);
		TwistUtils.setDelay(10);
		logger.info(esn);
		logger.info(min);
		redeemFlow.typeInTextField("qty", numIld);
		redeemFlow.typeInTextField("ild_input_phone", min);
		//redeemFlow.typeInTextField("qty", numIld);
//		activationPhoneFlow.pressSubmitButton("BUY");
		redeemFlow.pressButton("Submit");
		TwistUtils.setDelay(10);
	}
	
	public void chooseAServicePlan(String servicePlan){
		if(servicePlan.equalsIgnoreCase("$60")){
			redeemFlow.clickLink("Buy Now[0]");
		}
		else if(servicePlan.equalsIgnoreCase("$50")){
			redeemFlow.clickLink("Buy Now[1]");
		}
		else if(servicePlan.equalsIgnoreCase("$40")){
			redeemFlow.clickLink("Buy Now[2]");
		}
		else if(servicePlan.equalsIgnoreCase("$25")){
			redeemFlow.clickLink("Buy Now[3]");
		}
	}

}