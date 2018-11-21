package com.tracfone.twist.purchase;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AutoRefill {

	private RedemptionFlow redemptionFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static final String ADD_NOW = "Now";
	
	private Properties properties = new Properties();

	public AutoRefill() {
		
	}

	public void selectEnrollInAutoRefillFor(String howToAdd, String servicePlan) throws Exception {
		redemptionFlow.clickLink(properties.getString("setUpAuto-Refill"));
		selectPlan(howToAdd, servicePlan);
	}
	
	public void switchAutoRefillFor(String howToAdd, String servicePlan) throws Exception {
		redemptionFlow.clickLink(properties.getString("manageAutorefillEnrollment"));
		redemptionFlow.clickLink(properties.getString("switchPlan"));
		selectPlan(howToAdd, servicePlan);
	}

	private void selectPlan(String howToAdd, String servicePlan) {
		String planStr = getPlanString(servicePlan);
		TwistUtils.setDelay(5);
		redemptionFlow.chooseFromSelect("service_plan_id", planStr);
		TwistUtils.setDelay(5);
		if (ADD_NOW.equalsIgnoreCase(howToAdd)) {
			redemptionFlow.selectRadioButton("radioParam2");
		} else {
			redemptionFlow.selectRadioButton("radioParam1");
		}
	}
	
	private String getPlanString(String servicePlan) {
		String planStr;
		if (servicePlan.contains("60")) {
			planStr = "Plan Extended Nation con Datos Ilimitados  a Alta Velocidad";
		} else if (servicePlan.contains("55")) {
			planStr = "Plan Extended Nation con 10 GB de Datos a alta velocidad";
		} else if (servicePlan.contains("40")) {
			planStr = "Plan Extended Nation con 4 GB de Datos a Alta Velocidad";
		} else if (servicePlan.contains("30")) {
			planStr = "Plan Extended Nation con Llamadas y Mensajes de Texto Internacionales con una oferta limitada de 1 GB de Datos a alta velocidad, luego a 2G";
		}else if (servicePlan.contains("25")) {
			planStr = "Plan Extended Nation con  llamadas y mensajes de texto ilimitados";
		} else if (servicePlan.contains("20")) {
			planStr = "Plan Extended Nation de una semana con 2 GB de Datos a alta velocidad";
		} else {
			throw new IllegalArgumentException("Unknown service plan: " + servicePlan);
		}
		return planStr;
	}
	
	public void checkAutoRefillFor(String howToAdd, String plan) throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(redemptionFlow.h2Visible(properties.getString("ORDERSUMARY")) ||
				redemptionFlow.h2Visible(properties.getString("orderSummary")));
		redemptionFlow.pressSubmitButton("default_submit_btn");
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
		if (redemptionFlow.submitButtonVisible("default_cancel_btn")) {
			redemptionFlow.pressSubmitButton("default_cancel_btn");
		}
		
		if (ADD_NOW.equalsIgnoreCase(howToAdd)) {
			Assert.assertTrue(redemptionFlow.linkVisible(properties.getString("manageAutorefillEnrollment")));
			String phoneInfo = redemptionFlow.getBrowser().div("phone_item").getText();		
			Assert.assertTrue(phoneInfo.contains(properties.getString("enrolledinAutoRefill")));
			String planStr = getPlanString(plan);
			Assert.assertTrue(phoneInfo.contains(planStr));	
		}

		ESN esn = esnUtil.getCurrentESN();
		storeRedeemData(esn);
		phoneUtil.checkRedemption(esn);
		phoneUtil.clearOTAforEsn(esn.getEsn());
/*		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));*/
	}

	public void deEnrollInAutoRefill() throws Exception {
		redemptionFlow.clickLink(properties.getString("manageAutorefillEnrollment"));
		redemptionFlow.clickLink(properties.getString("cancelEnrollment"));
		redemptionFlow.pressSubmitButton("default_submit_btn");
		String phoneInfo = redemptionFlow.getBrowser().div("phone_item").getText();		
		Assert.assertTrue(phoneInfo.contains("Inscrito en Auto-Recarga: No"));
		Assert.assertFalse(redemptionFlow.linkVisible(properties.getString("manageAutorefillEnrollment")));
		Assert.assertTrue(redemptionFlow.divVisible("Has cancelado tu suscripción en Auto-Recarga."));
	}
	
	public void selectApplyRefillNow() {
		TwistUtils.setDelay(80);
		String esn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.setDateInFuture(esn);
		Assert.assertTrue(redemptionFlow.linkVisible(properties.getString("manageAutorefillEnrollment")));
		redemptionFlow.clickLink(properties.getString("manageAutorefillEnrollment"));
		Assert.assertTrue(redemptionFlow.linkVisible(properties.getString("applyEnrollmentNow")));
		redemptionFlow.clickLink(properties.getString("applyEnrollmentNow"));
		TwistUtils.setDelay(80);
	}

	public void confirmApplyNow() throws Exception {
		TwistUtils.setDelay(15);
		Assert.assertTrue(redemptionFlow.h2Visible(properties.getString("orderSummary")));
		redemptionFlow.pressSubmitButton("default_submit_btn");
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));	
	}

	private void storeRedeemData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(true);
		esn.setTransType("TC Enroll in Auto-refill");
		esn.setActionType(401);
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

}