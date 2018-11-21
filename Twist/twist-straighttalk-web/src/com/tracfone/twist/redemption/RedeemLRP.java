package com.tracfone.twist.redemption;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class RedeemLRP {
	
	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	public void goToRedeemOption() throws Exception {
		redemptionFlow.pressSubmitButton("USE THEM NOW!");
	}

	public void useLRPPoints() throws Exception {
		if (redemptionFlow.buttonVisible("USE THEM NOW!")) {
			redemptionFlow.pressButton("USE THEM NOW!");
		} else {
			redemptionFlow.pressSubmitButton("USE THEM NOW!");
		}
	}

	public void clickRedeemPlan(String plan) throws Exception {
		//TODO Pick Plan
		if (plan.equalsIgnoreCase("10ILD")) {
			redemptionFlow.clickLink("REDEEM PLAN[4]");
		} else if (plan.equalsIgnoreCase("45")) {
			redemptionFlow.clickLink("REDEEM PLAN[5]");
		}else if (plan.equalsIgnoreCase("55")) {
			redemptionFlow.clickLink("REDEEM PLAN[6]");
		}else if (plan.equalsIgnoreCase("60")) {
			redemptionFlow.clickLink("REDEEM PLAN[7]");
		}
		//redemptionFlow.pressSubmitButton("Add Now");
		redemptionFlow.pressSubmitButton("fullfillmentType2");
	}

	public void submitTheOrder() throws Exception {
		redemptionFlow.clickLink("Order[1]");		
		Assert.assertTrue(redemptionFlow.spanVisible("THANK YOU!"));
		Assert.assertTrue(redemptionFlow.h4Visible("ORDER SUMMARY"));
		Assert.assertTrue(redemptionFlow.h4Visible("REWARDS POINTS SUMMARY"));
		redemptionFlow.clickLink("Back to My Account");							
	}

	public void verifyRemainingLRP() throws Exception {
		redemptionFlow.clickLink("Back to My Account");
		String email = esnUtil.getCurrentESN().getEmail();
		// To validate points from DB to UI for total reward Points
		String points = phoneUtil.getTotalLRPPointsbyEmail(email);
		System.out.println("Reward Points " + points);
		Assert.assertTrue(redemptionFlow.getBrowser().div("rewardpoints_available").text()
				.contains("You Have" + points + " Reward Points available Member since Feb,2016"));
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

}