package com.tracfone.redemption;

import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RedeemCard {

	private static PhoneUtil phoneUtil;
	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private Properties properties = new Properties();

	public RedeemCard() {

	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		RedeemCard.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void goToAddCardOnline() throws Exception {
		redemptionFlow.navigateTo(properties.getString("TF_HOME"));
		redemptionFlow.clickLink(properties.getString("addCardOnline"));
	}

	public void enterPinNumberAndMinFromPart(String pinNumber, String partNumber) {
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(activeEsn);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("ESN: " + activeEsn + " MIN: " + activeMin + " Pin: " + newPin);
		storeRedeemData(newPin);

		redemptionFlow.typeInTextField("input_pin1[1]", newPin);
		redemptionFlow.typeInTextField("input_min[1]", activeMin);
		redemptionFlow.pressButton("Submit");
	}
	
/*	public void enterPinCardNumberForPortIn(String pinCard1) throws Exception {
		enterPin(pinCard1, "input_pin1[1]");
		redemptionFlow.pressSubmitButton("Add PIN");
	}*/

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

/*	public void enterPinNumber(String pinNumber) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("Pin: " + newPin);
		storeRedeemData(newPin);
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PinText.name, newPin);
		redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionTracfoneWebFields.SubmitButton.name);
	}*/

	public void enterPastDuePhoneNumber() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());

		String currentMin = esnUtil.getCurrentESN().getMin();
		System.out.println("ESN: " + esn.getEsn() + " MIN: " + currentMin);
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionTracfoneWebFields.PhoneText.name, currentMin);
	}

	public void reviewOrderSummary() throws Exception {
		Assert.assertTrue(redemptionFlow.h2Visible(PaymentFlow.BuyAirtimeTracfoneWebFields.BuyAirtimeMessage.name));
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))) {
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		} else {
			redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		}
		Assert.assertTrue(redemptionFlow.h1Visible("My Account Summary"));
	}

	public void reviewSummary() throws Exception {
		Assert.assertTrue(redemptionFlow.h2Visible(PaymentFlow.BuyAirtimeTracfoneWebFields.BuyAirtimeMessage1.name));
		if (redemptionFlow.submitButtonVisible(properties.getString("continue"))) {
			redemptionFlow.pressSubmitButton(properties.getString("continue"));
		} else {
			redemptionFlow.pressSubmitButton(properties.getString("continue2"));
		}
		System.out.println("Redemption Successful with PIN");
	}

	public void goToRedeemCardOnline() throws Exception {
		redemptionFlow.navigateTo(properties.getString("TF_HOME") + "direct/AddTracfoneAirtime");
	}

	public void enterPinNumberPromoCodeAndMinFromPart(String pinNumber, String Promocode, String partNumber) throws Exception {
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(activeEsn);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		System.out.println("ESN: " + activeEsn + " MIN: " + activeMin + " Pin: " + newPin);
		storeRedeemData(newPin);
		if (!Promocode.isEmpty()) {
			redemptionFlow.typeInTextField("input_pin1", newPin);
			redemptionFlow.typeInTextField("input_min", activeMin);
			redemptionFlow.typeInTextField("input_promo_code", Promocode);
			redemptionFlow.pressButton("default_submit_btn");

			int edited = 0;
			for (int i = 0; i < 3 && edited == 0; i++) {
				TwistUtils.setDelay(10);
				if (redemptionFlow.getBrowser().div("error").text().contains("We're sorry, but Promo Codes are not valid")) {
					if (redemptionFlow.textboxVisible("input_promo_code")) {
					} else {
						redemptionFlow.pressButton("default_submit_btn");
						System.out.println(edited);
					}
				}
			}
		} else {
			redemptionFlow.typeInTextField("input_pin1", newPin);
			redemptionFlow.typeInTextField("input_min", activeMin);
			redemptionFlow.pressButton("default_submit_btn");
		}
	}

//	private void pressButton(String buttonType) {
//		if (redemptionFlow.submitButtonVisible(buttonType)) {
//			redemptionFlow.pressSubmitButton(buttonType);
//		} else {
//			redemptionFlow.pressButton(buttonType);
//		}
//	}

	public void validatePromo(String promoCode) throws Exception {
		if (!promoCode.isEmpty()) {
			String esnStr = esnUtil.getCurrentESN().getEsn();
			String objid = phoneUtil.validatePromo(esnStr, promoCode);
			System.out.println(objid);
		} 
	}
	
	public void enterPinCardNumber(String pinCard) throws Exception {
		if (!pinCard.isEmpty()) {
			String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
			storeRedeemData(newPin);

			System.out.println("PIN CARD: " + newPin);
			redemptionFlow.typeInTextField("", newPin);
			redemptionFlow.pressButton("Add PIN");

		} else {
			redemptionFlow.pressSubmitButton("skipDiv");
		}
	}

	public void enterPinCard(String pinPart) throws Exception {
	
	}

}
