package com.tracfone.twist.autoReup;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Enrollment {

	private MyAccountFlow myAccountFlow;
	private static final String $60_BROADBAND = "6GB Broadband Plan";
	private static final String $40_BROADBAND = "/\\$40(\\.00)? 4GB Wireless Broadband/";
	private static final String $20_BROADBAND = "/\\$20(\\.00)? 1\\.5GB Wireless Broadband/";
	
	private static final String $70_UPGRADE = "/\\$70(\\.00)? Unlimited Talk and Text, 2GB high-speed data, 3 Points Upgrade Plan/";
	private static final String $60_UPGRADE = "/\\$60(\\.00)? Unlimited Talk and Text, 2GB high-speed data, 1\\.5 Points Upgrade Plan/";
	private static final String $50_UPGRADE = "/\\$50(\\.00)? Unlimited Talk and Text, 2GB high-speed data, 1 Point Upgrade Plan/";
	
	private static final String $60_UNLIMITED = "/\\$60(\\.00)? Unlimited 4G LTE\\*\\* Data/";
	private static final String $55_UNLIMITED = "/\\$55(\\.00)? First 10GB Data up to 4G LTE\\*\\* Speed, then 2G\\*/";
	private static final String $50_UNLIMITED = "/\\$50(\\.00)? First 5GB Data up to 4G LTE\\*\\* Speed, then 2G\\*/";
	private static final String $40_UNLIMITED = "/\\$40(\\.00)? First 4GB Data up to 4G LTE\\*\\* Speed, then 2G\\*/";
	private static final String $25_UNLIMITED = "/\\$25(\\.00)? Unlimited Talk & Text/";
	
	private static final String NOW = "Now";
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	public Enrollment() {

	}

	public void goToEnrollmentPage() throws Exception {
		TwistUtils.setDelay(15);
		myAccountFlow.clickLink("Set Up Auto ReUp");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void checkForConfirmation() throws Exception {
		Assert.assertTrue("ORDER SUMMARY not Visible", myAccountFlow.h2Visible("ORDER SUMMARY"));
		myAccountFlow.pressSubmitButton("Continue");
		// Check for Auto refill YES
		/*Assert.assertTrue("NOT ENROLLED IN AUTO REFILL/CHECK FOR PORTLET ERROR", 
				myAccountFlow.getBrowser().div("phone_item").getText().contains("Enrolled in Auto-ReUp: Yes"));*/
		Assert.assertTrue(myAccountFlow.getBrowser().listItem("Auto ReUp Enrollment:	Yes").isVisible());
/*		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));*/	
	}

	public static void selectPlan(String plan, Browser browser1) {
		TwistUtils.setDelay(38);
		String planId;

		if (browser1.italic("fa[1]").isVisible()) {
			browser1.italic("fa[1]").click();
		}
		
		if (plan.toLowerCase().contains("upgrade")) {
			browser1.div("upGradeSectionTab").click();
			if (plan.contains("70")) {
				planId = "360";
			} else if (plan.contains("60")) {
				planId = "359";
			} else if (plan.contains("50")) {
				planId = "358";
			} else {
				throw new IllegalArgumentException("buy '" + plan + "' not found");
			}
		} else if (plan.toLowerCase().contains("wireless")) {
			browser1.div("wirelessSectionTab").click();
			if (plan.contains("40")) {
				planId = "286";
			} else if (plan.contains("20")) {
				planId = "285";
			} else {
				throw new IllegalArgumentException("buy '" + plan + "' not found");
			}
		} else {
			browser1.div("unlimitedSectionTab").click();
			if (plan.contains("60")) {
				planId = "416";
			} else if (plan.contains("50")) {
				planId = "240";
			} else if (plan.contains("40")) {
				planId = "238";
			} else if (plan.contains("30")){
				planId = "461";
			} else if (plan.contains("25")){
				planId = "235";
			} else {
				throw new IllegalArgumentException("buy '" + plan + "' not found");
			}
		}

		TwistUtils.setDelay(15);

		for (int i = 0; i < 10; i ++) {
			ElementStub button = browser1.submit("buyPlanModal["+ i +"]");
			try {
				String value = button.getAttribute("value");
				if (planId.equals(value)) {
					button.click();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public void selectEnrollIn(String newPlan, String enrollType) throws Exception {
		selectPlan(newPlan, myAccountFlow.getBrowser());
	}
	
}