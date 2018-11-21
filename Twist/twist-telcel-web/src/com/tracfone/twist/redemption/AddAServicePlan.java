package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

//import net.sf.sahi.client.Browser;

public class AddAServicePlan {

	private static final String QUEUED = "QUEUED";
	private static PhoneUtil phoneUtil;
	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();

	public AddAServicePlan() {

	}

	public void selectAddAServicePlan(String howToAdd) throws Exception {
		TwistUtils.setDelay(30);
		/*if (QUEUED.equalsIgnoreCase(howToAdd)) {
			paymentFlow.clickLink("Añadir un Plan de Servicio a Mi Reserva™");
		} else {
			paymentFlow.clickLink("Añadir un Plan de Servicio AHORA");
		}*/
		
		paymentFlow.clickLink(properties.getString("addServicePlan"));
	}

	public void enterPinAndAdd(String pinNumber, String howToAdd) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		ESN esn = esnUtil.getCurrentESN();
		System.out.println("Pin2: "+ newPin);
		
		paymentFlow.typeInTextField("airtime_pin", newPin);
		paymentFlow.pressSubmitButton("add_srvc_card_btn");
		TwistUtils.setDelay(15);
		if (paymentFlow.submitButtonVisible("add_now_submit")) {
			paymentFlow.pressSubmitButton("add_now_submit");
			esn.setActionType(6);
		} else if (paymentFlow.submitButtonVisible("add_queue_submit")) {
			paymentFlow.pressSubmitButton("add_queue_submit");
			esn.setActionType(401);
		}
//		TwistUtils.setDelay(15);
		/*if (QUEUED.equalsIgnoreCase(howToAdd)) {
			paymentFlow.typeInTextField("airtime_pin_queue", newPin);
			paymentFlow.pressSubmitButton("Ir[1]");
			
			if (paymentFlow.h2Visible("Información Importante")) {
				paymentFlow.pressSubmitButton("add_queue_submit");
			}
			esn.setActionType(401);
		} else if (howToAdd.equalsIgnoreCase("Now")){
			paymentFlow.typeInTextField("airtime_pin", newPin);
			paymentFlow.pressSubmitButton("add_srvc_card_btn");
			if (paymentFlow.h2Visible("Información Importante")) {
				paymentFlow.pressSubmitButton("add_now_submit");
			}
			
			esn.setActionType(6);
		} else if (howToAdd.equalsIgnoreCase("addon")){
			paymentFlow.typeInTextField("airtime_pin", newPin);
			paymentFlow.pressSubmitButton("add_srvc_card_btn");
			esn.setActionType(6);
		}*/
		if (paymentFlow.submitButtonVisible("Continuar")) {
			paymentFlow.pressSubmitButton("Continuar");
		}
		TwistUtils.setDelay(20);
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("ORDERSUMARY")));
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(esn.getEsn());
		TwistUtils.setDelay(18);
		paymentFlow.pressSubmitButton(properties.getString("continue"));	
		
		storeRedeemData(esn, newPin);
		phoneUtil.checkRedemption(esn);
	}
	
	public void selectManageReserve() throws Exception {
		paymentFlow.clickLink(properties.getString("adminPlans"));
	}
	
	public void addCardInReserveNow() throws Exception {
		paymentFlow.selectCheckBox("check_service_plan");
		paymentFlow.pressSubmitButton(properties.getString("addNow"));
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("importantInformation")));
		paymentFlow.pressSubmitButton(properties.getString("confirm"));
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("ORDERSUMARY")));
		paymentFlow.pressSubmitButton(properties.getString("continue"));

		ESN esn = esnUtil.getCurrentESN();
		esn.setActionType(6);
		esn.setTransType("TC Manage Reserve");
		phoneUtil.checkRedemption(esn);
	}

	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("TC Add a Service Plan");
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