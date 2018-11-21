package com.tracfone.twist.BuyAirtime;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class SwitchPlan {

	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public SwitchPlan() {
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
	
	public void switchPlan(String planToBuy, String ILD, String currentPlan, String redeemType) throws Exception {
		String planStr = getPlanStr(planToBuy);
		TwistUtils.setDelay(20);
		if(planToBuy.equalsIgnoreCase(currentPlan)){
			if (ILD.equalsIgnoreCase("Yes")) {
				activationPhoneFlow.selectCheckBox(props.getString("BuyAirtime.ReUpMyildCheckbox")+ planStr );
			}
			activationPhoneFlow.clickLink("Refill This Plan");
		}else{
			
			if (ILD.equalsIgnoreCase("Yes")) {
				activationPhoneFlow.selectCheckBox(props.getString("BuyAirtime.ReUpildCheckbox")+ planStr );
			}
			
			if(planToBuy.equalsIgnoreCase("$25")){
				activationPhoneFlow.clickLink("PURCHASE");
			}else if(planToBuy.equalsIgnoreCase("$55")){
				activationPhoneFlow.clickLink("PURCHASE[2]");
			}else if(planToBuy.equalsIgnoreCase("$35")){
				if(currentPlan.equalsIgnoreCase("$25")){
					activationPhoneFlow.clickLink("PURCHASE");
				}else{
					activationPhoneFlow.clickLink("PURCHASE[1]");
				}
			}else if(planToBuy.equalsIgnoreCase("$45")){
				if(currentPlan.equalsIgnoreCase("$55")){
					activationPhoneFlow.clickLink("PURCHASE[2]");
				}else{
					activationPhoneFlow.clickLink("PURCHASE[1]");
				}
			}
		}
		
		if(activationPhoneFlow.strongVisible(props.getString("Switch.StashNow"))){
			if(redeemType.equalsIgnoreCase("Add Now")){
			activationPhoneFlow.clickStrongMessage(props.getString("Switch.RefillNow"));
		}else{
			activationPhoneFlow.clickStrongMessage(props.getString("Switch.StashNow"));
		}
	  }
		
	}
	
	public void switchEnrollmentToPlanWithILDFrom(String planToSwitch, String ild, String currentPlan, String redeemType)
			throws Exception {
		activationPhoneFlow.clickLink("Manage Refill Enrollment");
		activationPhoneFlow.clickLink("Switch Plan");
		switchPlan(planToSwitch, ild, currentPlan, redeemType);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
}
