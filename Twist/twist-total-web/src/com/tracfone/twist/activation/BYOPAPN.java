package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class BYOPAPN {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static Logger logger = LogManager.getLogger(BYOPAPN.class.getName());
	public BYOPAPN() {

	}

	public void goToBYOPAPNSettingPage() throws Exception {
		activationPhoneFlow.clickLink("click here");
	}

	public void enterEsnAndOtherDetails(String model) throws Exception {
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.typeInTextField("phone_num", min);
		activationPhoneFlow.chooseFromSelect("phone_type", model);
		activationPhoneFlow.pressSubmitButton("SEARCH");
	}

	public void checkForSettings() throws Exception {
		String apnSettings = activationPhoneFlow.getBrowser().div("apn_mms").getText();
		logger.info(apnSettings);
		Assert.assertTrue(!apnSettings.contains("SCRIPT MISSING") && activationPhoneFlow.h4Visible("APN Settings"));
		// Assert.assertTrue((activationPhoneFlow.h4Visible("APN Settings")) ||
		// (activationPhoneFlow.divVisible("The Phone Number/Serial Number you have entered does not produce APN Settings information.")));
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