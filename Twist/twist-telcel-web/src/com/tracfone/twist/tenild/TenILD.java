package com.tracfone.twist.tenild;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.context.OnTelcelHomepage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class TenILD {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();

	public TenILD() {

	}

	public void goToInternationalCalling() throws Exception {
		activationPhoneFlow.clickLink("Llamadas Internacionales");
	}

	public void buy10DollarIldCard() throws Exception {
//		activationPhoneFlow.clickLink("buynow_2");
		activationPhoneFlow.navigateTo(OnTelcelHomepage.TC_URL + "/secure/controller.block?__blockname=" +
				"telcel.dollar10.ild_plan_landing&isdollar10ild=true&collect_min=true&dollar10ILDPurchase" +
				"=DOLLAR10_ILD_PURCHASE_ONLY&language=es");
	}

	public void enterPhoneNumber() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		System.out.println(min);
		activationPhoneFlow.typeInTextField("min", min);
		activationPhoneFlow.typeInTextField("quantity", "1");
		activationPhoneFlow.pressSubmitButton("default_add_btn");
	}

	public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(activationPhoneFlow.h2Visible(properties.getString("orderSummary")));
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		ESN esn = esnUtil.getCurrentESN();
		storeRedeemData(esn);
		phoneUtil.checkRedemption(esn);
	}

	private void storeRedeemData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
	
		esn.setTransType("TC Buy 10ILD");
		//What type is ILD being added?
		esn.setActionType(6);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}