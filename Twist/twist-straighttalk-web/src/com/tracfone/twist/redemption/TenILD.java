package com.tracfone.twist.redemption;

import org.apache.log4j.Logger;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class TenILD {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static Logger logger = Logger.getLogger(TenILD.class);
	
	public TenILD() {
	
	}
//
	public void goToInternationalCalling() throws Exception {
		activationPhoneFlow.clickLink("International Calling[1]");
	}
//
	public void buy10DollarIldCard() throws Exception {
		activationPhoneFlow.pressSubmitButton("Buy Now[1]");
	}
//
	public void enterPhoneNumber() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String esnStr = esn.getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esnStr);
		storeRedeemInfo(esn);
		
		activationPhoneFlow.typeInTextField("min", min);
		activationPhoneFlow.typeInTextField("qty", "1");
		activationPhoneFlow.clickLink("No");
	}

	private void storeRedeemInfo(ESN esn) {
		esn.setTransType("Straight Talk Buy $10");
		esn.setActionType(6);
		esn.setRedeemType(false);
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