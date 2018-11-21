package com.tracfone.twist.activation;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AbstractBYOPAPN {

	private final Properties props;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	protected AbstractBYOPAPN(String propsName) {
		props = new Properties(propsName);
	}

	public void goToBYOPAPNSettingPage() throws Exception {
		TwistUtils.setDelay(25);
		esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), "GSM", "New", "Activate Net10 with PIN");
		//activationPhoneFlow.clickLink(props.getString("apn.click"));
		activationPhoneFlow.navigateTo(props.getString("Twist.BYOPAPN"));
	}

	public void enterEsnAndOtherDetails(String model) throws Exception {
		activationPhoneFlow.chooseFromSelect("operatingSystem", model);
		activationPhoneFlow.pressSubmitButton(props.getString("search"));
	}

	public void checkForSettings() throws Exception {
		TwistUtils.setDelay(30);
		String apnSettings = activationPhoneFlow.getBrowser().div("phone_results").getText();
//		APN Settings (Access Point Name):
		Assert.assertTrue(!apnSettings.contains("SCRIPT MISSING") && activationPhoneFlow.h3Visible(props.getString("apnSettings")));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phone) {
		phoneUtil = phone;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

}