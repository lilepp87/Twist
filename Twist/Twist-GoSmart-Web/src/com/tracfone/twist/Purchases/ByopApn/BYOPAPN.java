package com.tracfone.twist.Purchases.ByopApn;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class BYOPAPN {

	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public BYOPAPN() {
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

	public void goToAPNSettings() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		
		activationPhoneFlow.clickLink("Update Data / APN Settings");
		activationPhoneFlow.typeInTextField("phone_num", min);
		activationPhoneFlow.pressButton("Operating System");
		activationPhoneFlow.chooseFromSelect("os_type","IOS");
		activationPhoneFlow.chooseFromSelect("select_os_version","iPhone or iPad (iOS 6 or later)");
		activationPhoneFlow.pressSubmitButton("SEARCH");
	}
}
