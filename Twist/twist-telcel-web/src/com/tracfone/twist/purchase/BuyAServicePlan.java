package com.tracfone.twist.purchase;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.context.OnTelcelHomepage;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAServicePlan {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String REOCCURRING = "Reoccurring";
	private static final String INT_UNL_PLAN = "Unlimited International Plan";
	private static final String ADD_NOW = "Now";
	
	private Properties properties = new Properties();

	public BuyAServicePlan() {

	}

	public void selectBuyAServicePlan() throws Exception {
		paymentFlow.clickLink(properties.getString("buyServicePlan"));
	}

	public void goToUnlimitedInternational(String servicePlan) {
		ESN currEsn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(currEsn.getEsn());
//		paymentFlow.clickLink("/Servicio Internacional Ilimitado\\*?/");

		if (servicePlan.isEmpty()) {
			paymentFlow.navigateTo(OnTelcelHomepage.TC_URL + "/secure/controller.block?__blockname=" +
					"telcel.dollar10.ild_plan_landing&isdollar10ild=true&collect_min=true&dollar10ILDPurchase" +
					"=DOLLAR10_ILD_PURCHASE_ONLY&language=es");
			paymentFlow.typeInTextField("min", min);
		} else {
			paymentFlow.clickLink("Comprar un Plan de Servicio ");
			paymentFlow.typeInTextField("min", min);
			paymentFlow.pressSubmitButton("submitMin");
		}
	}
	
	public void selectPlan(String servicePlan) throws Exception{
		if (servicePlan.contains("60")) {
			paymentFlow.selectRadioButton("service_plans_id_selected");
		} else if (servicePlan.contains("55")) {
			paymentFlow.selectRadioButton("service_plans_id_selected[1]");
		} else if (servicePlan.contains("40")) {
			paymentFlow.selectRadioButton("service_plans_id_selected[2]");
		} else if (servicePlan.contains("30")) {
			paymentFlow.selectRadioButton("service_plans_id_selected[3]");
		} else if (servicePlan.contains("25")) {
			paymentFlow.selectRadioButton("service_plans_id_selected[4]");
		} else if (servicePlan.contains("20")) {
			paymentFlow.selectRadioButton("service_plans_id_selected[5]");
		} else {
			throw new IllegalArgumentException("Unknown service plan: " + servicePlan);
		}
	}

	public void selectServicePlan(String planType, String servicePlan) throws Exception {
		TwistUtils.setDelay(10);
		if (!paymentFlow.radioVisible("isRecurring")) {
			TwistUtils.setDelay(60);
		}
		selectPlan(servicePlan);
		
		if (REOCCURRING.equalsIgnoreCase(planType)) {
			paymentFlow.selectRadioButton("isRecurring");
		} else {
			paymentFlow.selectRadioButton("isRecurring[1]");
		}

		paymentFlow.pressSubmitButton("default_submit_btn");
	}

	public void selectServicePlanAndAddIld(String servicePlan, Integer numIld, String howToAdd)
			throws Exception {
		selectPlan(servicePlan);
		
		paymentFlow.pressSubmitButton("default_submit_btn");
		
		if (ADD_NOW.equalsIgnoreCase(howToAdd)) {
			if (paymentFlow.submitButtonVisible(properties.getString("addNow"))) {
				paymentFlow.pressSubmitButton(properties.getString("addNow"));
			}
		} else {
			if (paymentFlow.submitButtonVisible(properties.getString("myReserve"))) {
				paymentFlow.pressSubmitButton(properties.getString("myReserve"));
			}
		}

		if (numIld > 0) {
			paymentFlow.typeInTextField("quantity", numIld.toString());
			paymentFlow.pressSubmitButton("default_add_btn");
		} else {
			paymentFlow.pressSubmitButton("default_submit_btn");
		}
	}

	public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("orderSummary")));
		paymentFlow.pressSubmitButton(properties.getString("continue"));

		ESN esn = esnUtil.getCurrentESN();
		storeRedeemData(esn);
		phoneUtil.checkRedemption(esn);
	}

	private void storeRedeemData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("TC Buy a Service Plan");
		esn.setActionType(401);
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

}