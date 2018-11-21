package com.tracfone.twist.byopapn;

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

public class BYOPAPN {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	
	private Properties properties = new Properties();

	public BYOPAPN() {

	}

	public void goToBYOPAPNSettingPage() throws Exception {
		TwistUtils.setDelay(15);
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, "GSM", "New", "TC Activate for APN");
//		activationPhoneFlow.clickLink(properties.getString("clickHere"));
		activationPhoneFlow.navigateTo(OnTelcelHomepage.TC_URL + 
				"/secure/controller.block?__blockname=telcel.byop_apn.landing&app=TELCEL&language=es");
	}

	public void enterEsnAndOtherDetails(String model) throws Exception {
		activationPhoneFlow.chooseFromSelect("operatingSystem", model);
		activationPhoneFlow.pressSubmitButton("search_apn");
	}

	public void checkForSettings() throws Exception {
		String apnSettings = activationPhoneFlow.getBrowser().div("phone_results").getText();
		TwistUtils.setDelay(8);
		Assert.assertTrue(activationPhoneFlow.h3Visible(properties.getString("apnSettings")) && 
				!apnSettings.isEmpty() && !apnSettings.contains("SCRIPT MISSING"));
		// || (activationPhoneFlow.divVisible("The Phone Number/Serial Number you have entered does not produce APN Settings information.")));
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