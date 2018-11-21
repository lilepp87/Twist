package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BYOPAPN {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;

	public BYOPAPN() {

	}
//	
	public void enterEsnAndOtherDetails(String type, String model, String version) throws Exception {
		System.out.println(esnUtil.getCurrentESN());
		TwistUtils.setDelay(10);
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());

		activationPhoneFlow.typeInTextField("phone_num", min);
		if (type.equalsIgnoreCase("Operating System")) {
			activationPhoneFlow.pressButton("Operating System");
			activationPhoneFlow.chooseFromSelect("os_type", model);
			activationPhoneFlow.chooseFromSelect("select_os_version", version);
		} else {
			activationPhoneFlow.pressButton("Handset Manufacturer");
			activationPhoneFlow.chooseFromSelect("hs_type", model);
			activationPhoneFlow.chooseFromSelect("select_hs_version", version);
		}
		activationPhoneFlow.pressSubmitButton("SEARCH");
	}
//
	public void goToBYOPAPNSettingPage() throws Exception {
		TwistUtils.setDelay(15);
		activationPhoneFlow.clickLink("Update Data / APN Settings");
	}
//
	public void checkForSettings() throws Exception {
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.divVisible("APN Settings")) {
			TwistUtils.setDelay(55);
		}
		String apnSettings = activationPhoneFlow.getBrowser().div("apn_results").getText();
//		System.out.println(apnSettings);
		Assert.assertTrue(!apnSettings.contains("SCRIPT MISSING") && !apnSettings.isEmpty() && activationPhoneFlow.divVisible("APN Settings"));
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

}