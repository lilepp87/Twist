package com.tracfone.twist.purchases;

/*
 * Created by: Hannia Leiva 10/12/2011
 * Test Case ID:
 * Add Card Online
 * Phone Model: Tracfone Kyocera 126 C
 * ESN Status:50
 */

import junit.framework.Assert;


import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AddCardOnline {

	private static PhoneUtil phoneUtil;
	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private PaymentFlow paymentFlow;
	
	private Properties properties = new Properties();
	
	public AddCardOnline() {

	}

	public void goToAddCardOnline() throws Exception {
		redemptionFlow.navigateTo(properties.getString("TF_URL"));
		redemptionFlow.clickLink(properties.getString("addCardOnline"));
	}
	
	public void enterPinNumberAndMinFromPart(String pinNumber, String partNumber) {
//		String activeEsn = phoneUtil.getActiveEsnByPartNumber(partNumber);
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(activeEsn);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("ESN: " + activeEsn + " MIN: " + activeMin + " Pin: " + newPin);
		storeRedeemData(newPin);

		redemptionFlow.typeInTextField("input_pin1[1]", newPin);
		redemptionFlow.typeInTextField("input_min[1]", activeMin);
		redemptionFlow.pressButton("Submit");
		/*redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PinText.name, newPin);
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PhoneText.name, activeMin);
		redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionTracfoneWebFields.SubmitButton.name);*/
	}

	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setActionType(401);
		esn.setRedeemType(false);
		esn.setTransType("Tracfone redeem with PIN");
	}

	public void completeTheProcess() throws Exception {
		if (redemptionFlow.h2Visible(properties.getString("pleaseEnterAirtimePIN"))) {
			redemptionFlow.pressSubmitButton("default_submit_btn[1]");
		}
		
		TwistUtils.setDelay(20);
		
		if (redemptionFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
			redemptionFlow.pressSubmitButton(properties.getString("codeAccepted"));
		}

		Assert.assertTrue(redemptionFlow.h2Visible(properties.getString("addAirtimeMessage")));
		redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		if (redemptionFlow.textboxVisible("time_tank_value")) {
			redemptionFlow.typeInTextField("time_tank_value", "12345");
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}

	public void enterPIN(String pinPart) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		redemptionFlow.clickLink(properties.getString("addAirtime"));
		redemptionFlow.typeInTextField("input_pin1[1]", pin);
		storeRedeemData(pin);
		redemptionFlow.pressSubmitButton(properties.getString("submit2"));
		redemptionFlow.clickLink(properties.getString("signOut"));
	}

	public void enterPinNumber(String pinNumber) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("Pin: " + newPin);
		storeRedeemData(newPin);
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PinText.name, newPin);
		redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionTracfoneWebFields.SubmitButton.name);
	}

	public void enterPastDuePhoneNumber() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());

		String currentMin = esnUtil.getCurrentESN().getMin();
		System.out.println("ESN: " + esn.getEsn() + " MIN: " + currentMin);
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PhoneText.name, currentMin);
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

	public void buyILDWith(Integer numIld, String purchaseType) throws Exception {
		//Assert.assertTrue(redemptionFlow.h2Visible(RedemptionFlow.RedemptionTracfoneWebFields.AddAirtimeMessage.name));
		//redemptionFlow.h2Visible("$10 ILD Card Important Information");
		//redemptionFlow.pressSubmitButton("default_submit_btn");
		//redemptionFlow.h3Visible("International Long Distance Global Card");
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		System.out.println(min);
		redemptionFlow.typeInTextField("ild_min", min);
		if (numIld > 0) {
			redemptionFlow.typeInTextField("ild_quantity", numIld.toString());
			if(purchaseType.equalsIgnoreCase("Enroll in Auto Refill")){
				pressButton("default_enroll_btn");
			}
			else if(purchaseType.equalsIgnoreCase("One Time Purchase")){
				pressButton("default_ildadd_btn");
			}
//			redemptionFlow.pressSubmitButton("default_ildadd_btn");
//		} else {
//			redemptionFlow.pressSubmitButton("default_ILDsubmit_btn");
		}
	}

	public void reviewOrderSummary() throws Exception {
		Assert.assertTrue(redemptionFlow.h2Visible(PaymentFlow.BuyAirtimeTracfoneWebFields.BuyAirtimeMessage.name));
//		Assert.assertTrue(redemptionFlow.tableHeaderVisible("/.*911.*/"));
//		redemptionFlow.pressSubmitButton(PaymentFlow.BuyAirtimeTracfoneWebFields.ContinueButton.name);
		//redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))){
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		}else{
			redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		}
		Assert.assertTrue(redemptionFlow.h1Visible("My Account Summary"));
	}
	
	public void reviewSummary() throws Exception {
		TwistUtils.setDelay(5);
		Assert.assertTrue(redemptionFlow.h2Visible(PaymentFlow.BuyAirtimeTracfoneWebFields.BuyAirtimeMessage1.name));
		//redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))){
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		}else{
			redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		}
		System.out.println("Redemption Successful with PIN");
	}

	public void goToRedeemCardOnline() throws Exception {
		redemptionFlow.navigateTo(properties.getString("TF_URL") +"direct/AddTracfoneAirtime");
	}

	public void enterPinNumberPromoCodeAndMinFromPart(String pinNumber,String Promocode, String partNumber) throws Exception {
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(activeEsn);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("ESN: " + activeEsn + " MIN: " + activeMin + " Pin: " + newPin);
		storeRedeemData(newPin);
		if (!Promocode.isEmpty()){
		redemptionFlow.typeInTextField("input_pin1", newPin);
		redemptionFlow.typeInTextField("input_min", activeMin);
		redemptionFlow.typeInTextField("input_promo_code", Promocode);
		redemptionFlow.pressButton("default_submit_btn");

		int edited = 0;
		for (int i = 0; i < 3 && edited == 0; i++) {
			TwistUtils.setDelay(10);
			if (redemptionFlow.getBrowser().div("error").text().contains("We're sorry, but Promo Codes are not valid")){
				if (redemptionFlow.textboxVisible("input_promo_code")){
			   }else{
				redemptionFlow.pressButton("default_submit_btn");
				System.out.println(edited);
				}
			}
		}
		}else{
		redemptionFlow.typeInTextField("input_pin1", newPin);
		redemptionFlow.typeInTextField("input_min", activeMin);
		redemptionFlow.pressButton("default_submit_btn");
		}
	}

	private void pressButton(String buttonType) {
		if (redemptionFlow.submitButtonVisible(buttonType)) {
			redemptionFlow.pressSubmitButton(buttonType);
		} else {
			redemptionFlow.pressButton(buttonType);
		}
	}

	public void validatePromo(String Promocode) throws Exception {
		if (!Promocode.isEmpty()){
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String objid = phoneUtil.validatePromo(esnStr, Promocode);
		System.out.println(objid);
		}else{
			System.out.println("Promo field is empty");
		}
	}

}