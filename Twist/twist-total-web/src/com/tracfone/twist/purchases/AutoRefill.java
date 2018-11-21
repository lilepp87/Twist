package com.tracfone.twist.purchases;

import org.apache.log4j.*;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class AutoRefill {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String AYN = "All You Need Plan";
	private static final String UNLIMITED = "Unlimited Plan";
	private static final String ILD = "Unlimited International Plan";
	private static final String ALERT_10 = "Remote Alert Monthly Plan";
	private static final String ALERT_100 = "Remote Alert Annual Plan";
	private static final String NOW = "Now";
	private static final String REOCCURING = "Reoccuring";
	private static final String HOTSPOT_7 = "Hotspot 7 GB";
	private static final String HOTSPOT_5 = "Hotspot 5 GB";
	private static final String HOTSPOT_4 = "Hotspot 4 GB";
	private static final String HOTSPOT_2 = "Hotspot 2 GB";
	private static final String HOTSPOT_1 = "Hotspot 1 GB";
	private static final String BYOT_7 = "BYOT 7 GB";
	private static final String BYOT_5 = "BYOT 5 GB";
	private static final String BYOT_4 = "BYOT 4 GB";
	private static final String BYOT_2 = "BYOT 2 GB";
	private static final String BYOT_1 = "BYOT 1 GB";
	private static final String AudioVox_10 = "Car Connection Monthly Plan";
	private static final String AudioVox_100 = "Car Connection Annual Plan";
	private static Logger logger = LogManager.getLogger(AutoRefill.class.getName());
	public AutoRefill() {
		
	}

	public void selectSetUpAutoRefill() {
		paymentFlow.clickLink("Set Auto-Refill");
	}
	
	public void checkPlanSelected(String oldPlan) {
		String defaultPlan = paymentFlow.getBrowser().select("plans_select").getSelectedText();
		logger.info(defaultPlan);
		String oldPlanStr = getPlanStr(oldPlan);
		logger.info(oldPlanStr);
		Assert.assertTrue(defaultPlan.startsWith(oldPlanStr));
	}
	
	public void selectEnroll(String enrollType) throws Exception {
		if (NOW.equalsIgnoreCase(enrollType)) {
			//paymentFlow.selectRadioButton("apply_now");
			paymentFlow.selectRadioButton("apply now");
		} else {
			//paymentFlow.selectRadioButton("apply_on_due_date");
			paymentFlow.selectRadioButton("end of service");
		}
	}

	public void selectEnrollInFrom(String newPlan, String enrollType, String oldPlan)
			throws Exception {
		String selectPlan = getPlanStr(newPlan);
		
		if (oldPlan.equalsIgnoreCase(newPlan)) {
			selectPlan += "  **Current Plan**";
		}
		
		paymentFlow.chooseFromSelect("plans_select", selectPlan);
		selectEnroll(enrollType);
	}
	
	public void checkAutoRefill() throws Exception {
		TwistUtils.setDelay(8);
		if (!paymentFlow.h3Visible("service summary") && !paymentFlow.h3Visible("SERVICE SUMMARY")) {
			TwistUtils.setDelay(70);	
		}
		Assert.assertTrue(paymentFlow.h3Visible("service summary") || paymentFlow.h3Visible("SERVICE SUMMARY"));
		pressButton("GO TO MY ACCOUNT");
		Assert.assertTrue(paymentFlow.getBrowser().listItem("Auto-Refill Enrollment: Yes").isVisible());
		Assert.assertTrue(paymentFlow.linkVisible("Cancel Auto-Refill"));
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
	}
	
	public void checkEnrolledInAutoRefill(String autoRefill, String plan) throws Exception {
		if (REOCCURING.equalsIgnoreCase(autoRefill)) {
			TwistUtils.setDelay(8);
			Assert.assertTrue(paymentFlow.getBrowser().listItem("Auto-Refill Enrollment: Yes").isVisible());
			Assert.assertTrue(paymentFlow.linkVisible("Cancel Auto-Refill"));
		}
	}

	private String getPlanStr(String plan) {
		String planStr;

		if (AYN.equalsIgnoreCase(plan)) {
			planStr = "$30.00 ALL YOU NEED&trade;";
		} else if (ILD.equalsIgnoreCase(plan)) {
			planStr = "$60.00 UNLIMITED INTERNATIONAL 1-MONTH";
		} else if (UNLIMITED.equalsIgnoreCase(plan)) {
			planStr = "$45.00 UNLIMITED 1-MONTH";
		} else if (ALERT_10.equalsIgnoreCase(plan)) {
			planStr = "$10.00 1 Month Remote Alert";
		} else if (ALERT_100.equalsIgnoreCase(plan)) {
			planStr = "$100.00 1 Year Remote Alert";
		} else if (HOTSPOT_7.equalsIgnoreCase(plan)) {
			planStr = "$75.00 7GB Hotspot";
		} else if (HOTSPOT_5.equalsIgnoreCase(plan)) {
			planStr = "$50.00 5GB Hotspot";
		} else if (HOTSPOT_4.equalsIgnoreCase(plan)) {
			planStr = "$40.00 4GB Hotspot";
		} else if (HOTSPOT_2.equalsIgnoreCase(plan)) {
			planStr = "$25.00 2GB Hotspot";
		} else if (HOTSPOT_1.equalsIgnoreCase(plan)) {
			planStr = "$15.00 1GB Hotspot";
		} else if (BYOT_7.equalsIgnoreCase(plan)) {
			planStr = "$75.00 7GB Data Plan";
		} else if (BYOT_5.equalsIgnoreCase(plan)) {
			planStr = "$50.00 5GB Data Plan";
		} else if (BYOT_4.equalsIgnoreCase(plan)) {
			planStr = "$40.00 4GB Data Plan";
		} else if (BYOT_2.equalsIgnoreCase(plan)) {
			planStr = "$25.00 2GB Data Plan";
		} else if (BYOT_1.equalsIgnoreCase(plan)) {
			planStr = "$15.00 1GB Data Plan";
		} else if (AudioVox_10.equalsIgnoreCase(plan)) {
			planStr = "$10.00 1 Month Car Connection";
		} else if (AudioVox_100.equalsIgnoreCase(plan)) {
			planStr = "$100.00 1 Year Car Connection ";
		} else {
			throw new IllegalArgumentException("Could not find Plan: " + plan);
		}
		return planStr;
	}
	
	public void cancelEnrollment() throws Exception {
		/*String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));*/
		paymentFlow.clickLink("Cancel Auto-Refill");
		TwistUtils.setDelay(3);
		if (paymentFlow.buttonVisible("continue")) {
			paymentFlow.pressButton("continue");
		} else {
			paymentFlow.pressSubmitButton("CONTINUE");
		}
		TwistUtils.setDelay(3);
		Assert.assertTrue(paymentFlow.getBrowser().paragraph("You have successfully canceled the enrollment in Auto Refill").isVisible());
		
	}
	public void addReserveCardNow() throws Exception {
		paymentFlow.clickLink("Manage Reserve");
		pressButton("ADD NOW");
		//pressButton("ADD NOW[1]");
		pressButton("apply now");
		TwistUtils.setDelay(3);
		//paymentFlow.getBrowser().submit("continue").click();
		//pressButton("continue");
	}
	
	public void goToSwitchPlans() throws Exception {
		paymentFlow.clickLink("Manage Auto-Refill Enrollment");
		paymentFlow.clickLink("Switch Plan");
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
	private void pressButton(String button) {
		if (paymentFlow.buttonVisible(button)) {
			paymentFlow.pressButton(button);
		} else if (paymentFlow.submitButtonVisible(button)) {
			paymentFlow.pressSubmitButton(button);
		} 
	}
	private boolean buttonVisible(String button) {
		return paymentFlow.buttonVisible(button) || paymentFlow.submitButtonVisible(button);
	}
}