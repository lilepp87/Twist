package com.tracfone.twist.redemption;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RefillUsingAServiceCard {

	private static PhoneUtil phoneUtil;
	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private static final String NOW = "Now";

	public RefillUsingAServiceCard() {

	}
//
	public void goToRefill() throws Exception {
		redemptionFlow.clickLink("REFILL");
	}

	public void enterPhoneNumberAndPinCard(String pinCard) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		enterPhoneAndPinCard(pinCard);
	}
//	
	public void enterPhoneForPurchase() throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		System.out.println("ESN:" + activeEsn);
		if (activeEsn.getMin().isEmpty()) {
			String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
			System.out.println("MIN:" + minNumber);
			activeEsn.setMin(minNumber);
		}

		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionStraighttalkWebFields.MinPurchaseText.name, activeEsn.getMin());
		redemptionFlow.pressButton("Continue[1]");
	}
	
	public void enterPhoneAndPinCard(String pinCard) throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		System.out.println("ESN:" + activeEsn);
		if (activeEsn.getMin().isEmpty()) {
			String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
			System.out.println("MIN:" + minNumber);
			activeEsn.setMin(minNumber);
		}
		
		String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
		
		storeRedeemData(activeEsn, newPin);
		System.out.println("Active ESN: " + activeEsn.getEsn() + " minNumber: " + activeEsn.getMin() + " newPin: " + newPin);

		if (activeEsn.getMin().isEmpty()) {
			redemptionFlow.typeInTextField(RedemptionFlow.RedemptionStraighttalkWebFields.MinNumberText.name, activeEsn.getEsn());
		} else {
			redemptionFlow.typeInTextField(RedemptionFlow.RedemptionStraighttalkWebFields.MinNumberText.name, activeEsn.getMin());
		}
		redemptionFlow.typeInTextField(RedemptionFlow.RedemptionStraighttalkWebFields.PinNumberText.name, newPin);
		redemptionFlow.pressButton(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name);
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Straight Talk refill with pin");
		esn.setPin(pin);
		esn.setActionType(6);
	}
	
	public void addPin() {
		if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.AddToReserveButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.AddToReserveButton.name);
			esnUtil.getCurrentESN().setActionType(401);
		} else if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.AddNowButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.AddNowButton.name);
		 	esnUtil.getCurrentESN().setActionType(6);
		} else {
			esnUtil.getCurrentESN().setActionType(401);
		}
	}
//
	public void addPin(String addType) {
		if (NOW.equalsIgnoreCase(addType)) {
			if (redemptionFlow.submitButtonVisible("RIGHT NOW")) {
				redemptionFlow.pressSubmitButton("RIGHT NOW");				
			}
			esnUtil.getCurrentESN().setActionType(6);
		} else {
			if (redemptionFlow.submitButtonVisible("END OF SERVICE")) {
				redemptionFlow.pressSubmitButton("END OF SERVICE");
			}
			esnUtil.getCurrentESN().setActionType(401);
		}		
	}
//
	public void completeTheProcess() throws Exception {
		TwistUtils.setDelay(18);
		if (!redemptionFlow.h2Visible(RedemptionFlow.RedemptionStraighttalkWebFields.OrderSummaryMessage.name) &&
				!redemptionFlow.h1Visible("Refill Summary") && !redemptionFlow.h2Visible("ORDER SUMMARY")) {
			TwistUtils.setDelay(55);	
		}
		Assert.assertTrue(redemptionFlow.h2Visible(RedemptionFlow.RedemptionStraighttalkWebFields.OrderSummaryMessage.name) ||
				redemptionFlow.h1Visible("Refill Summary") || redemptionFlow.h2Visible("ORDER SUMMARY"));
		if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name);
		} else if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.DoneButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.DoneButton.name);
		}
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
//		phoneUtil.removeEsnFromSTAccount(esnUtil.getCurrentESN().getEsn());
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}
	
	public void goToAddAServiceCard() throws Exception {
		redemptionFlow.clickLink("Add a Service Card");
	}

	public void checkLRPAndCompleteTheProcess() throws Exception {
		TwistUtils.setDelay(4);
		Assert.assertTrue(redemptionFlow.getBrowser().table("form_fields_ild").text().contains("Reward Points earned"));
		if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name);
		} else if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.DoneButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.DoneButton.name);
		}
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}

	public void refillWithPin(String pinCard) throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		if (activeEsn.getMin().isEmpty()) {
			String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
			activeEsn.setMin(minNumber);
		}
		String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
		storeRedeemData(activeEsn, newPin);
		System.out.println("Active ESN: " + activeEsn.getEsn() + " minNumber: " + activeEsn.getMin() + " newPin: " + newPin);
		redemptionFlow.selectRadioButton("selected_serial_number");
		redemptionFlow.pressButton("Submit");
		redemptionFlow.selectRadioButton("use_service_card");
		redemptionFlow.typeInTextField("service_card_input", newPin);
		/*if (redemptionFlow.submitButtonVisible(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name)) {
			redemptionFlow.pressSubmitButton(RedemptionFlow.RedemptionStraighttalkWebFields.ContinueButton.name);
		}
		redemptionFlow.pressSubmitButton("Continue");*/
	}


	public void refillWithPurchase() throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		if (activeEsn.getMin().isEmpty()) {
			String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
			activeEsn.setMin(minNumber);
		}
		redemptionFlow.selectRadioButton("selected_serial_number");
		redemptionFlow.pressButton("Submit");
		redemptionFlow.selectRadioButton("use_service_card_no");
		redemptionFlow.pressButton("Continue");
	}

	public void addAServicePlanForLRP(String pinCard, Integer redoFlag) throws Exception {
		for (int i = 0; i < redoFlag; i++) {
			redemptionFlow.clickLink("Add a Service Plan");
			ESN activeEsn = esnUtil.getCurrentESN();
			System.out.println("ESN::" + activeEsn);
			if (activeEsn.getMin().isEmpty()) {
				String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
				System.out.println("MIN:" + minNumber);
				activeEsn.setMin(minNumber);
			}
	
			String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
			storeRedeemData(activeEsn, newPin);
			System.out.println("Active ESN: " + activeEsn.getEsn() + " minNumber: " + activeEsn.getMin() + " newPin: " + newPin);
			redemptionFlow.typeInTextField("new_airtime_pin", newPin);
			redemptionFlow.pressButton("add_srvc_card_btn");
			redemptionFlow.pressSubmitButton("Continue");
			
			redemptionFlow.clickLink("Manage existing plans in your Straight Talk Reserve™");
			redemptionFlow.selectCheckBox("add_now_warning");
			redemptionFlow.pressSubmitButton("Add Now");
			redemptionFlow.pressButton("Confirm");
			TwistUtils.setDelay(30); //Continue-submit
			redemptionFlow.pressSubmitButton("Continue");
			TwistUtils.setDelay(20);			
		}
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
}