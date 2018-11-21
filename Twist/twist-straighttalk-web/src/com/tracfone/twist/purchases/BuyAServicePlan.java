package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAServicePlan {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private AutoRefill autoRefill;
	private static PhoneUtil phoneUtil;
	private static final String REOCCURING = "Reoccuring";
	private static final String QUEUED = "QUEUED";
	
	public BuyAServicePlan() {

	}
//
	public void selectBuyAServicePlan() throws Exception {
		TwistUtils.setDelay(10);
		if (!paymentFlow.linkVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.BuyServicePlanLink.name)) {
			TwistUtils.setDelay(35);
		}
		paymentFlow.clickLink(PaymentFlow.BuyAirtimeStraighttalkWebFields.BuyServicePlanLink.name);
	}
//	
	public void goToServicePlans() throws Exception {
		TwistUtils.setDelay(10);
		if (!paymentFlow.linkVisible("Service Plans")) {
			TwistUtils.setDelay(35);
		}
		paymentFlow.clickLink("Service Plans");
	}
//	
	public void enterMinAndClickOnCheckAvailablePlans(String esnPart) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		System.out.println(esn);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		paymentFlow.typeInTextField("see-available-plans-input", min);
		paymentFlow.pressSubmitButton("SEE AVAILABLE PLANS");
	}
//
	public void selectPlanForActivation(String purchaseType, String servicePlan) {
		TwistUtils.setDelay(8);
		paymentFlow.clickLink("ServicePlanInputBtn");
		selectPlan(purchaseType, servicePlan);
	}
	
	public void selectPlan(String purchaseType, String servicePlan) {
		onlySelectPlan(purchaseType, servicePlan);
		
		if (QUEUED.equalsIgnoreCase(purchaseType) && paymentFlow.submitButtonVisible("END OF SERVICE")) {
			paymentFlow.pressSubmitButton("END OF SERVICE");
			esnUtil.getCurrentESN().setActionType(401);
		} else if (paymentFlow.submitButtonVisible("RIGHT NOW")) {
			paymentFlow.pressSubmitButton("RIGHT NOW");
			esnUtil.getCurrentESN().setActionType(6);
		} else {
			esnUtil.getCurrentESN().setActionType(401);
		}
	}
	
	public void onlySelectPlan(String purchaseType, String servicePlan) {
		String option = getPlanOrder(servicePlan);
		TwistUtils.setDelay(15);
		paymentFlow.clickLink("PURCHASE" + option);
//		browser.radio("82-85").click();
		if (AutoRefill.UNLIMITED_130.equalsIgnoreCase(servicePlan) || "3 Months Unlimited Plan".equalsIgnoreCase(servicePlan)) {
			paymentFlow.getBrowser().radio("extendedPlanRadio[2]").click();	
		} else if (AutoRefill.UNLIMITED_255.equalsIgnoreCase(servicePlan) || "6 Months Unlimited Plan".equalsIgnoreCase(servicePlan)) {
			paymentFlow.getBrowser().radio("extendedPlanRadio[1]").click();
		} else if (AutoRefill.UNLIMITED_495.equalsIgnoreCase(servicePlan) || "1 Year Unlimited Plan".equalsIgnoreCase(servicePlan)) {
			paymentFlow.getBrowser().radio("extendedPlanRadio").click();
		}
		paymentFlow.clickLink("PURCHASE PLAN");
		

		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			paymentFlow.clickLink("submit-auto-refill");
		}else if (servicePlan.contains("Data Add On")){
			//Do Nothing
		}else{
			paymentFlow.clickLink("submit-one-time-purchase");
		}
		
		storeRedeemData(esnUtil.getCurrentESN());
		esnUtil.getCurrentESN().setActionType(6);
		TwistUtils.setDelay(8);
	}
//
	private String getPlanOrder(String plan) {
		String option;
		if (AutoRefill.UNLIMITED_130.equalsIgnoreCase(plan) || "3 Months Unlimited Plan".equalsIgnoreCase(plan)) {
			//No top level option for 3 months. Now picking $45 plan and choosing the number of months later 
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.UnlimitedPlan.name;
		} else if (AutoRefill.UNLIMITED_255.equalsIgnoreCase(plan) || "6 Months Unlimited Plan".equalsIgnoreCase(plan)) {
			//No top level option for 3 months. Now picking $55 plan and choosing the number of months later 
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Unlimited55Plan.name;
		} else if (AutoRefill.UNLIMITED_495.equalsIgnoreCase(plan) || "1 Year Unlimited Plan".equalsIgnoreCase(plan)) {
			//No top level option for 3 months. Now picking $60 plan and choosing the number of months later 
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.UnlimitedInternationalPlan.name;
		} else if (AutoRefill.UNLIMITED.equalsIgnoreCase(plan) || AutoRefill.UNLIMITED_45.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.UnlimitedPlan.name;
		} else if (AutoRefill.AYN.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.AllYouNeedPlanRadio.name;
		} else if (AutoRefill.UNLIMITED_55.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Unlimited55Plan.name;
		} else if (AutoRefill.ILD.equalsIgnoreCase(plan) || AutoRefill.UNLIMITED_60.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.UnlimitedInternationalPlan.name;
		} else if (AutoRefill.HOME_UNL_15.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HomePhone15Radio.name;
		} else if (AutoRefill.HOME_ILD_30.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HomePhone30Radio.name;
		} else if (AutoRefill.HOTSPOT_7.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HotSpot7GBRadio.name;
		} else if (AutoRefill.HOTSPOT_5.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HotSpot5GBRadio.name;
		} else if (AutoRefill.HOTSPOT_4.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HotSpot4GBRadio.name;
		} else if (AutoRefill.HOTSPOT_2.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HotSpot2GBRadio.name;
		} else if (AutoRefill.HOTSPOT_1.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HotSpot1GBRadio.name;
		} else if (AutoRefill.BYOT_7.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Byot7GBRadio.name;
		} else if (AutoRefill.BYOT_5.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Byot5GBRadio.name;
		} else if (AutoRefill.BYOT_4.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Byot4GBRadio.name;
		} else if (AutoRefill.BYOT_2.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Byot2GBRadio.name;
		} else if (AutoRefill.BYOT_1.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.Byot1GBRadio.name;
		} else if (AutoRefill.ALERT_10.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.RemoteAlertMonthlyPlanRadio.name;
		} else if (AutoRefill.ALERT_100.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.RemoteAlertAnnualPlanRadio.name;
		} else if (AutoRefill.AudioVox_10.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.CarConnectionMonthlyPlanRadio.name;
		} else if (AutoRefill.AudioVox_100.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.CarConnectionAnnualPlanRadio.name;
		} else if (AutoRefill.HOME_60.equalsIgnoreCase(plan) || "$45 Home Center".equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HomeCenter60Radio.name;
		} else if (AutoRefill.HOME_40.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HomeCenter40Radio.name;
		} else if (AutoRefill.HOME_30.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.HomeCenter30Radio.name;
		} else if (AutoRefill.ADDON_5.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.DataAddON5.name;
		} else if (AutoRefill.ADDON_10.equalsIgnoreCase(plan)) {
			option = PaymentFlow.BuyAirtimeStraighttalkWebFields.DataAddON10.name;
		} else {
			throw new IllegalArgumentException("Plan " + plan + " not found" );
		}
		return option;
	}
//
/*	public void selectServicePlanForCellTech(String purchaseType, String servicePlan, String celltech)
			throws Exception {
		String option = autoRefill.getPlanIdByTech(servicePlan, celltech);
		TwistUtils.setDelay(15);
		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			paymentFlow.clickLink("auto_" + option);
		} else {
			paymentFlow.clickLink("buy_" + option);
		}

		TwistUtils.setDelay(5);
		if (paymentFlow.submitButtonVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddToReserveButton.name)) {
			paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.AddToReserveButton.name);
		}
		TwistUtils.setDelay(5);
		 if (paymentFlow.linkVisible("SKIP[1]")) {
			paymentFlow.clickLink("SKIP[1]");
		} else if (paymentFlow.linkVisible("SKIP")) {
			paymentFlow.clickLink("SKIP");
		}
		storeRedeemData(esnUtil.getCurrentESN());
	}*/
//
	public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(20);
		String orderSummary = PaymentFlow.BuyAirtimeStraighttalkWebFields.OrderSummaryMessage.name;
		if (!paymentFlow.strongVisible(orderSummary) && !paymentFlow.h2Visible(orderSummary)) {
			TwistUtils.setDelay(70);
		}
		Assert.assertTrue(paymentFlow.strongVisible(orderSummary) || paymentFlow.h2Visible(orderSummary));
		paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);

		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}

	private void storeRedeemData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("Straight Talk Buy a Plan");
		esn.setActionType(401);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setAutoRefill(AutoRefill autoRefill) {
		this.autoRefill = autoRefill;
	}
	
	public void addToCart(String plan) throws Exception {
		paymentFlow.getBrowser().paragraph("top-push-4[2]").click(); 			
		paymentFlow.clickLink("ADD TO CART");
		TwistUtils.setDelay(15);
		selectPlan(plan);
//		paymentFlow.getBrowser().paragraph("").click();
		TwistUtils.setDelay(25);
		paymentFlow.clickLink("CHECK OUT");
		TwistUtils.setDelay(5);
	}
	
	public void buySimWithPlan(String plan){
		selectPlan(plan);
		TwistUtils.setDelay(15);
		paymentFlow.clickLink("CHECK OUT");
	}
	
	public void selectPlan(String servicePlan) {
		String planDiv = "";
		if (servicePlan.equalsIgnoreCase("$35")) {
			planDiv = "choose_your_plan_section_122001";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[0]");
		}else if (servicePlan.equalsIgnoreCase("$45")) {
			planDiv = "choose_your_plan_section_135001";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[1]");
		} else if (servicePlan.equalsIgnoreCase("$55")) {
			planDiv = "choose_your_plan_section_62501";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[2]");
		} else if (servicePlan.equalsIgnoreCase("$60")) {
			planDiv = "choose_your_plan_section_31134";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[3]");
		} else if (servicePlan.equalsIgnoreCase("$130")) {
			planDiv = "choose_your_plan_section_31132";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[4]");
		} else if (servicePlan.equalsIgnoreCase("$255")) {
			planDiv = "choose_your_plan_section_31348";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[5]");
		} else if (servicePlan.equalsIgnoreCase("$495")) {
			planDiv = "choose_your_plan_section_31133";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[6]");
		}else if (servicePlan.equalsIgnoreCase("$75")) {
			planDiv = "choose_your_plan_section_31363";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[0]");
		}else if (servicePlan.equalsIgnoreCase("$50")) {
			planDiv = "choose_your_plan_section_31362";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[1]");
		}else if (servicePlan.equalsIgnoreCase("$40")) {
			planDiv = "choose_your_plan_section_31361";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[2]");
		}else if (servicePlan.equalsIgnoreCase("$25")) {
			planDiv = "choose_your_plan_section_31360";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[3]");
		}else if (servicePlan.equalsIgnoreCase("$15")) {
			planDiv = "choose_your_plan_section_31144";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[4]");
		}	else { 
			// No thanks to the plan
			planDiv = "choose_your_plan_section_31040";
			TwistUtils.setDelay(5);
			paymentFlow.clickLink("Continue[7]");
		}
		/*paymentFlow.clickDivMessage(planDiv);
//		add2CartBtn_31132
		TwistUtils.setDelay(5);
		paymentFlow.clickLink("Continue[7]");*/
	}

}