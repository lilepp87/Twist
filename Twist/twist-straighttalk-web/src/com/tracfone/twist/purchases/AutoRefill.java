package com.tracfone.twist.purchases;

import static junit.framework.Assert.assertEquals;

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class AutoRefill {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	public static final String AYN = "All You Need Plan";
	public static final String UNLIMITED = "Unlimited Plan";
	public static final String ILD = "Unlimited International Plan";
	public static final String ALERT_10 = "Remote Alert Monthly Plan";
	public static final String ALERT_100 = "Remote Alert Annual Plan";
	public static final String NOW = "Now";
	public static final String REOCCURING = "Reoccuring";
	public static final String HOTSPOT_7 = "Hotspot 7 GB";
	public static final String HOTSPOT_5 = "Hotspot 5 GB";
	public static final String HOTSPOT_4 = "Hotspot 4 GB";
	public static final String HOTSPOT_2 = "Hotspot 2 GB";
	public static final String HOTSPOT_1 = "Hotspot 1 GB";
	public static final String BYOT_7 = "BYOT 7 GB";
	public static final String BYOT_5 = "BYOT 5 GB";
	public static final String BYOT_4 = "BYOT 4 GB";
	public static final String BYOT_2 = "BYOT 2 GB";
	public static final String BYOT_1 = "BYOT 1 GB";
	public static final String AudioVox_10 = "Car Connection Monthly Plan";
	public static final String AudioVox_100 = "Car Connection Annual Plan";
	public static final String HOME_60 = "$60 Home Center";
	public static final String HOME_40 = "$40 Home Center";
	public static final String HOME_30 = "$30 Home Center";
	public static final String HOME_ILD_30 = "Home Unlimited International";
	public static final String HOME_UNL_15 = "Home Unlimited";
	public static final String UNLIMITED_130 = "$130 Unlimited Plan";
	public static final String UNLIMITED_255 = "$255 Unlimited Plan";
	public static final String UNLIMITED_495 = "$495 Unlimited Plan";
	public static final String UNLIMITED_45 = "$45 Unlimited Plan";
	public static final String UNLIMITED_55 = "$55 Unlimited Plan";
	public static final String UNLIMITED_60 = "$60 Unlimited Plan";
	public static final String ADDON_5 = "$5 Data Add On";
	public static final String ADDON_10 = "$10 Data Add On";
	private ResourceBundle rb1 = ResourceBundle.getBundle("straighttalk");
	
	public AutoRefill() {
		
	}

	public void selectSetUpAutoRefill() {
		paymentFlow.clickLink("Set Up Auto-Refill");
	}
	
	public void selectManageAutoRefill() {
		paymentFlow.clickLink("Manage Auto-Refill Enrollment");
		paymentFlow.clickLink("Switch Auto-Refill Credit Card Payment");
		paymentFlow.clickDivMessage("ADD A NEW CREDIT CARD");
	}
/*
	public void checkPlanSelected(String oldPlan) {
		String defaultPlan = paymentFlow.getBrowser().select("plans_select").getSelectedText();
		// System.out.println(defaultPlan);

		String oldPlanStr = getPlanStr(oldPlan);
		System.out.println(oldPlanStr);
		Assert.assertTrue(defaultPlan.startsWith(oldPlanStr));
	}*/

	public void selectEnrollInFrom(String newPlan, String enrollType, String oldPlan, String cellTech) throws Exception {
		String selectPlan = getPlanIdByTech(newPlan, cellTech);
		System.out.println(newPlan);
//		if (oldPlan.equalsIgnoreCase(newPlan)) {
//			selectPlan += "  **Current Plan**";
//		}
	
		System.out.println(selectPlan);
		TwistUtils.setDelay(8);
		paymentFlow.chooseFromSelect("plans_select", selectPlan);

		if (NOW.equalsIgnoreCase(enrollType)) {
			paymentFlow.pressButton("applyNow");
		} else {
			paymentFlow.pressButton("applyLater");
		}
	}
	
	public void checkAutoRefill() throws Exception {
		TwistUtils.setDelay(15);
		if (!paymentFlow.h2Visible("ORDER SUMMARY")) {
			TwistUtils.setDelay(65);
		}
		Assert.assertTrue(paymentFlow.h2Visible("ORDER SUMMARY"));
		paymentFlow.pressSubmitButton("default_submit_btn");
		//TODO: Fix the below call
		/*TwistUtils.setDelay(10);
		String enrollStatus = phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));*/
		//TODO check new plan if applyed now and using auto refill
	}
//	
	public void checkEnrolledInAutoRefill(String autoRefill, String plan) throws Exception {
		if (REOCCURING.equalsIgnoreCase(autoRefill)) {
			TwistUtils.setDelay(23);
			String phoneInfo = paymentFlow.getBrowser().div("phone_item").getText();
			Assert.assertTrue(phoneInfo.contains("Enrolled in Auto-Refill: Yes"));
//			Assert.assertTrue(paymentFlow.linkVisible("Manage Auto-Refill Enrollment"));
		}
		
		if (plan.toLowerCase().contains("hotspot")) {
			Assert.assertFalse(paymentFlow.linkVisible("Check Balance / Service End Date"));
		}
	}
//
	/*private String getPlanStr(String plan) {
		String planStr;

		if (AYN.equalsIgnoreCase(plan)) {
			planStr = "$30.00 All You Need?";
		} else if (ILD.equalsIgnoreCase(plan)) {
			planStr = "$60.00 30-Day Unlimited International Talk, Text & Data Plan with first 5 GB at High Speeds, and then at 2G*"; 
		} else if (UNLIMITED.equalsIgnoreCase(plan) || UNLIMITED_45.equalsIgnoreCase(plan)) {
			planStr = "$45.00 30-Day Unlimited Talk, Text & Data Plan With First 5 GB Data at High Speeds then 2G*";
		} else if (UNLIMITED_130.equalsIgnoreCase(plan) || "3 Months Unlimited Plan".equalsIgnoreCase(plan)) {
			planStr = "$130.00 90-Day Unlimited Talk, Text & Data Plan with first 5 GB at High Speeds, and then at 2G*";
		} else if (UNLIMITED_255.equalsIgnoreCase(plan) || "6 Months Unlimited Plan".equalsIgnoreCase(plan)) {
			planStr = "$255.00 180-Day Unlimited Talk, Text & Data Plan with first 5 GB at High Speeds, and then at 2G*";
		} else if (UNLIMITED_495.equalsIgnoreCase(plan) || "1 Year Unlimited Plan".equalsIgnoreCase(plan)) {
		    planStr = "$495.00 365-Day Unlimited Talk, Text & Data Plan with first 5 GB at High Speeds, and then at 2G*";
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
		} else if (HOME_60.equalsIgnoreCase(plan)) {
			planStr = "$60.00 Straight Talk Home Unl 5GB Data";
		} else if (HOME_40.equalsIgnoreCase(plan)) {
			planStr = "$40.00 Straight Talk Home Unl 2GB Data";
		} else if (HOME_30.equalsIgnoreCase(plan)) {
			planStr = "$30.00 Straight Talk Home Unl 1GB Data";
		} else {
			throw new IllegalArgumentException("Could not find Plan: " + plan);
		}
		return planStr;
	}*/
	
	protected String getPlanIdByTech(String plan, String celltech) {
		String part = esnUtil.getCurrentESN().getPartNumber();
		
		String planStr;
		if (BYOT_7.equalsIgnoreCase(plan)) {
			planStr = "292";
		} else if (BYOT_5.equalsIgnoreCase(plan)) {
			planStr = "291";
		} else if (BYOT_4.equalsIgnoreCase(plan)) {
			planStr = "290";
		} else if (BYOT_2.equalsIgnoreCase(plan)) {
			planStr = "289";
		} else if (BYOT_1.equalsIgnoreCase(plan)) {
			planStr = "288";
		} else if (ALERT_10.equalsIgnoreCase(plan)) {
			planStr = "277";
		} else if (ALERT_100.equalsIgnoreCase(plan)) {
			planStr = "278";
		} else if (HOTSPOT_7.equalsIgnoreCase(plan)) {
			planStr = "259";
		} else if (HOTSPOT_5.equalsIgnoreCase(plan)) {
			planStr = "258";
		} else if (HOTSPOT_4.equalsIgnoreCase(plan)) {
			planStr = "257";
		} else if (HOTSPOT_2.equalsIgnoreCase(plan)) {
			planStr = "256";
		} else if (HOTSPOT_1.equalsIgnoreCase(plan)) {
			planStr = "255";
		} else if (AudioVox_10.equalsIgnoreCase(plan)) {
			planStr = "293";
		} else if (AudioVox_100.equalsIgnoreCase(plan)) {
			planStr = "294";
		} else if (HOME_60.equalsIgnoreCase(plan)) {
			planStr = "363";
		} else if (HOME_40.equalsIgnoreCase(plan)) {
			planStr = "362";
		} else if (HOME_30.equalsIgnoreCase(plan)) {
			planStr = "361";
		} else if (HOME_ILD_30.equalsIgnoreCase(plan)) {
			planStr = "219";
		} else if (HOME_UNL_15.equalsIgnoreCase(plan)) {
			planStr = "218";
		} else if (part.startsWith("PH") || part.contains("BYO")) {
			if (UNLIMITED.equalsIgnoreCase(plan) || UNLIMITED_45.equalsIgnoreCase(plan)) {
				planStr = "208";
			} else if (UNLIMITED_55.equalsIgnoreCase(plan)) {
				planStr = "430";
			} else if (ILD.equalsIgnoreCase(plan) || UNLIMITED_60.equalsIgnoreCase(plan)) {
				planStr = "216";
			} else if (UNLIMITED_130.equalsIgnoreCase(plan) || "3 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "209";
			} else if (UNLIMITED_255.equalsIgnoreCase(plan) || "6 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "210";
			} else if (UNLIMITED_495.equalsIgnoreCase(plan) || "1 Year Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "211";
			} else {
				throw new IllegalArgumentException("Could not find Plan: " + plan);
			}
		} else if (celltech.equals("GSM")) {
			if (AYN.equalsIgnoreCase(plan)) {
				planStr = "81";
			} else if (UNLIMITED.equalsIgnoreCase(plan) || UNLIMITED_45.equalsIgnoreCase(plan)) {
				planStr = "82";
			} else if (UNLIMITED_55.equalsIgnoreCase(plan)) {
				planStr = "489";
			} else if (ILD.equalsIgnoreCase(plan) || UNLIMITED_60.equalsIgnoreCase(plan)) {
				planStr = "214";
			} else if (UNLIMITED_130.equalsIgnoreCase(plan) || "3 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "83";
			} else if (UNLIMITED_255.equalsIgnoreCase(plan) || "6 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "84";
			} else if (UNLIMITED_495.equalsIgnoreCase(plan) || "1 Year Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "85";
			} else {
				throw new IllegalArgumentException("Could not find Plan: " + plan);
			}
		} else if (celltech.equals("CDMA")) {
			if (AYN.equalsIgnoreCase(plan)) {
				planStr = "1";
			} else if (UNLIMITED.equalsIgnoreCase(plan) || UNLIMITED_45.equalsIgnoreCase(plan)) {
				planStr = "21";
			} else if (UNLIMITED_55.equalsIgnoreCase(plan)) {
				planStr = "489";
			} else if (ILD.equalsIgnoreCase(plan) || UNLIMITED_60.equalsIgnoreCase(plan)) {
				planStr = "213";
			} else if (UNLIMITED_130.equalsIgnoreCase(plan) || "3 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "41";
			} else if (UNLIMITED_255.equalsIgnoreCase(plan) || "6 Months Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "42";
			} else if (UNLIMITED_495.equalsIgnoreCase(plan) || "1 Year Unlimited Plan".equalsIgnoreCase(plan)) {
				planStr = "43";
			} else {
				throw new IllegalArgumentException("Could not find Plan: " + plan);
			}
		} else {
			throw new IllegalArgumentException("Could not find cell tech: " + celltech);
		}
		return planStr;
	}
	
	public void cancelEnrollment() throws Exception {
		String enrollStatus = phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
		TwistUtils.setDelay(15);
		paymentFlow.clickLink("Manage Auto-Refill Enrollment");
		paymentFlow.clickLink("Cancel Enrollment");
		TwistUtils.setDelay(15);
		paymentFlow.pressSubmitButton("submit_deenrollment_button");
		TwistUtils.setDelay(7);
		Assert.assertTrue(paymentFlow.divVisible("You have successfully cancelled enrollment in Auto-Refill."));
		String phoneInfo = paymentFlow.getBrowser().div("phone_item").getText();
		Assert.assertTrue(phoneInfo.contains("Enrolled in Auto-Refill: No"));
	}
	
	public void addReserveCardNow() throws Exception {
		paymentFlow.clickLink("Manage existing plans in your Straight Talk Reserve™");
		paymentFlow.selectCheckBox("add_now_warning");
		paymentFlow.pressSubmitButton("Add Now");
		paymentFlow.pressSubmitButton("apply-now-submit");
//		paymentFlow.pressButton("Confirm");
	}
	
	public void goToSwitchPlans() throws Exception {
		paymentFlow.clickLink("Manage Auto-Refill Enrollment");
		paymentFlow.clickLink("Switch Plan");
	}

	public void selectCreditCardAndApply() throws Exception {
		paymentFlow.chooseFromSelect("sourceId", "Visa_1111");
		paymentFlow.pressSubmitButton("Apply");
	}

	public void checkForRewardPointAllocationForAutoRefillWithPlan(String addType, String plan) throws Exception {
		TwistUtils.setDelay(10);
		String plan1;
		//TODO: Does not handle all plans or devices. Split out as a function
		if (UNLIMITED.equalsIgnoreCase(plan)) {
			plan1 = "Unlimited_Plan";
		} else if (UNLIMITED_45.equalsIgnoreCase(plan)) {
			plan1 = "UNLIMITED_45";
		} else if (UNLIMITED_55.equalsIgnoreCase(plan)) {
			plan1 = "UNLIMITED_55";
		} else if (UNLIMITED_60.equalsIgnoreCase(plan)) {
			plan1 = "UNLIMITED_60";
		} else if (UNLIMITED_255.equalsIgnoreCase(plan)) {
			plan1 = "UNLIMITED_255";
		} else if (UNLIMITED_495.equalsIgnoreCase(plan)) {
			plan1 = "UNLIMITED_495";
		} else if (AudioVox_10.equalsIgnoreCase(plan)) {
			plan1 = "AudioVox_10";
		} else if (HOME_60.equalsIgnoreCase(plan)) {
			plan1 = "HOME_60";
		} else {
			throw new IllegalArgumentException("Plan not found: '" + plan + "'");
		}
		String pin = rb1.getString(plan1);
		// LRP check for DB
		System.out.println(esnUtil.getCurrentESN().getEmail());
		int autoRefillpoints = Integer.parseInt(phoneUtil.getLRPPointsForTransType("AUTO_REFILL"));
		int autoRefillpointsDB = Integer.parseInt(phoneUtil.getLRPPointsbyEmailforTranstype("AUTO_REFILL", esnUtil.getCurrentESN().getEmail()));
		System.out.println("Auto-Refill points - " + autoRefillpoints);
		assertEquals(autoRefillpoints, autoRefillpointsDB);
		int pinPoints = 0;
		System.out.println(addType);
		if (NOW.equalsIgnoreCase(addType)) {
			pinPoints = Integer.parseInt(phoneUtil.getLRPPointsforPinPart(pin));
			int pinPointsDB = Integer.parseInt(phoneUtil.getLRPPointsbyEmailforTranstype("REDEMPTION", esnUtil.getCurrentESN().getEmail()));
			assertEquals(pinPoints, pinPointsDB);
			System.out.println(esnUtil.getCurrentESN().getEmail().toString() + " got PIN points - " + pinPoints);
		}
		// LRP check for UI
		int totalPoints = pinPoints + autoRefillpoints;
		Assert.assertTrue(paymentFlow.getBrowser().table("form_fields_ild").text().contains("Reward Points earned " + totalPoints));
		Assert.assertTrue(paymentFlow.getBrowser().table("rewards_txnsummary").text().contains("Note: Reward Points might not be immediately added to your account. Please allow a period of 30 days for our system to update."));
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));	
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
	
	//TODO: Can this reuse credit card function?
	public void enterNewCreditCardDetailsAndApplyForAutoRefill() throws Exception {
		String card = TwistUtils.generateCreditCard("Visa");
		String last4 = card.substring(12);
		paymentFlow.typeInTextField("account_number",card);
		paymentFlow.chooseFromSelect("expMonth","07");
		paymentFlow.chooseFromSelect("expYear","2021");
		paymentFlow.typeInTextField("fname","TwistFirstName");
		paymentFlow.typeInTextField("lname","TwistLastName");
		paymentFlow.typeInTextField("address1","1295 Charleston Road");
		paymentFlow.typeInTextField("city","Mountain View");
		paymentFlow.typeInTextField("zip","94043");
		paymentFlow.chooseFromSelect("state","CA");
		paymentFlow.pressSubmitButton("SAVE");
		paymentFlow.pressSubmitButton("Cancel");
		
		paymentFlow.pressButton("CHANGE YOUR AUTO-REFILL CREDIT CARD");
		paymentFlow.clickDivMessage(last4);
		paymentFlow.pressSubmitButton("Continue");
		TwistUtils.setDelay(1);
		paymentFlow.divVisible("success_msg");
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
	}

	public void goToAutoRefill() throws Exception {
		paymentFlow.clickLink("Auto-Refill");
	}

	public void enterPhoneNumber() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		paymentFlow.typeInTextField("minInputRefill", esn.getMin());
		paymentFlow.pressButton("CONTINUE");
		TwistUtils.setDelay(10);
		paymentFlow.pressButton("Apply Now");
	}

	public void goToPromotionLinkForAutoRefill() throws Exception {
		TwistUtils.setDelay(10);
		paymentFlow.clickLink("You are eligible for a promotion! Click for details.");
		paymentFlow.pressButton("CONTINUE");
	}

}