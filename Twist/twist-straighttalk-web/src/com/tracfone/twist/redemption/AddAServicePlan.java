package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AddAServicePlan {

	private static final String QUEUED = "QUEUED";
	private static PhoneUtil phoneUtil;
	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;

	public AddAServicePlan() {

	}
//
	public void selectAddAServicePlan() throws Exception {
		TwistUtils.setDelay(18);
		paymentFlow.clickLink(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddServicePlanReserveLink.name);
	}
//
	public void enterPinAndAdd(String pinNumber, String howToAdd) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		ESN esn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(10);
		boolean flashVisible = paymentFlow.getBrowser().paragraph("flash_div_text").isVisible();
		if (flashVisible) {
			paymentFlow.pressButton("Continue");
		}
		paymentFlow.typeInTextField(PaymentFlow.BuyAirtimeStraighttalkWebFields.AirtimePinQueueText.name, newPin);
		paymentFlow.pressButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.GoQueueSubmit.name);
		TwistUtils.setDelay(12);
		if (QUEUED.equalsIgnoreCase(howToAdd) && paymentFlow.submitButtonVisible("END OF SERVICE")) {
			paymentFlow.pressSubmitButton("END OF SERVICE");
			esn.setActionType(401);
		} else if (paymentFlow.submitButtonVisible("RIGHT NOW")) {
			paymentFlow.pressSubmitButton("RIGHT NOW ");
			esn.setActionType(6);
		} else {
			esn.setActionType(401);
		}
		TwistUtils.setDelay(15);
		if (!paymentFlow.h2Visible(RedemptionFlow.RedemptionStraighttalkWebFields.OrderSummaryMessage.name)) {
			TwistUtils.setDelay(70);
		}
		Assert.assertTrue(paymentFlow.h2Visible(RedemptionFlow.RedemptionStraighttalkWebFields.OrderSummaryMessage.name));
		phoneUtil.clearOTAforEsn(esn.getEsn());
		storeRedeemData(esn, newPin);
		phoneUtil.checkRedemption(esn);
		paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);	
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Straight Talk Add Plan");
		esn.setPin(pin);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}