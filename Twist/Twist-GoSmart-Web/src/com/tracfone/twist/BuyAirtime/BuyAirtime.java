package com.tracfone.twist.BuyAirtime;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAirtime {
	
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public BuyAirtime() {
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void goToRefillAndBuyNow() throws Exception {
		activationPhoneFlow.clickLink(props.getString("Redeem.RefillInsideAccount"));
		activationPhoneFlow.clickLink(props.getString("BuyAirtime.BuyPlan"));
	}
	
	public static String getPlanStr(String plan) {
		String planStr;
		if (plan.contains("55")) {
			planStr = "455";
		} else if (plan.contains("45")) {
			planStr = "453";
		} else if (plan.contains("35")) {
			planStr = "451";
		} else if (plan.contains("25")){
			planStr = "449";
		} else {
			throw new IllegalArgumentException("Plan '" + plan + "' not found");
		}
		return planStr;
	}
	
	public void selectPlanWithILDAndWithCurrentPlan(String planToBuy, String ild, String redeemType, String currentPlan, String option) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String planStr = getPlanStr(planToBuy);
		TwistUtils.setDelay(10);
		if(planToBuy.equalsIgnoreCase(currentPlan)){
			if (ild.equalsIgnoreCase("Yes")) {
				activationPhoneFlow.selectCheckBox(props.getString("BuyAirtime.ReUpMyildCheckbox")+ planStr );
			}
			activationPhoneFlow.clickLink("Refill This Plan");
		}else{
			
			if (ild.equalsIgnoreCase("Yes")) {
				activationPhoneFlow.selectCheckBox(props.getString("BuyAirtime.ReUpildCheckbox")+ planStr );
			}
			
			if(planToBuy.equalsIgnoreCase("$25")){
				activationPhoneFlow.clickLink(option+"[0]");
			}else if(planToBuy.equalsIgnoreCase("$55")){
				activationPhoneFlow.clickLink(option+"[3]");
			}else if(planToBuy.equalsIgnoreCase("$35")){
				if(currentPlan.equalsIgnoreCase("$25")){
					activationPhoneFlow.clickLink(option+"[0]");
				}else{
					activationPhoneFlow.clickLink(option+"[1]");
				}
			}else if(planToBuy.equalsIgnoreCase("$45")){
				if(currentPlan.equalsIgnoreCase("$55")){
					activationPhoneFlow.clickLink(option+"[3]");
				}else{
					activationPhoneFlow.clickLink(option+"[2]");
				}
			}
		}
		
		if(activationPhoneFlow.submitButtonVisible(props.getString("Redeem.StashNow"))){
			if(redeemType.equalsIgnoreCase("Add Now")){
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.RefillNow"));
			esn.setActionType(6);
		}else{
			activationPhoneFlow.pressSubmitButton(props.getString("Redeem.StashNow"));
			esn.setActionType(401);
		}
	  }
		
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("GoSmart Buy Service");
	}

	public void enterMin() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		
		activationPhoneFlow.typeInTextField(props.getString("BuyAirtime.InputMin"), min);
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
	}

	public void checkPurchaseSummaryAndProcess() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		Assert.assertTrue(activationPhoneFlow.h2Visible(props.getString("Activate.OrderSummary")));
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
		phoneUtil.checkRedemption(esn);
	}

	public void goToBuy10ILD() throws Exception {
		activationPhoneFlow.clickLink(props.getString("ILD.Inside$10ILD"));
	}

	public void enterMinAndQuanity(String quantity) throws Exception {
		if (activationPhoneFlow.textboxVisible(props.getString("BuyAirtime.InputMin"))){
		activationPhoneFlow.typeInTextField(props.getString("BuyAirtime.InputMin"), esnUtil.getCurrentESN().getMin());
		}
		activationPhoneFlow.typeInTextField(props.getString("ILD.quantity"), quantity);
		pressButton(props.getString("ILD.Purchase"));
	}
	
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
	}

	public void buy10ILD() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("GS.HomePage"));
		activationPhoneFlow.clickLink(props.getString("ILD.Outside$10ILD"));
		activationPhoneFlow.clickLink(props.getString("ILD.Buynow1"));
	}

	public void manageReserve() throws Exception {
		activationPhoneFlow.clickLink(props.getString("Redeem.ManageStash"));
		activationPhoneFlow.selectCheckBox(props.getString("Redeem.AddNowWarning"));
		activationPhoneFlow.pressSubmitButton(props.getString("Redeem.AddNow"));
		activationPhoneFlow.pressButton(props.getString("Redeem.Confirm"));
		
		ESN esn = esnUtil.getCurrentESN();
		esn.setActionType(6);
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("GoSmart Manage Stash");
	}

	public void goToSetupAutoPay() throws Exception {
		activationPhoneFlow.clickLink(props.getString("BuyAirtime.AutoRefill"));
	}
}
