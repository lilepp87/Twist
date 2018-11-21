package com.tracfone.twist.impl.eng.myAccount;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.*;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class AppsAndMore {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private Properties properties;

	public AppsAndMore() {
		properties = new Properties("Net10");
	}

	public void checkForAppsAndMoreInsideMyAccount() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.clickLink(properties.getString("Account.apps&more"));
		activationPhoneFlow.typeInTextField("phoneNumber", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("Account.apps&moreContinue"));
		
	}

	public void checkForAppsAndMoreOutsideMyAccount() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.clickLink(properties.getString("Account.MyAccountLinkUp"));
		activationPhoneFlow.clickLink(properties.getString("Account.SignOut"));
		activationPhoneFlow.clickLink(properties.getString("Account.apps&more"));
		activationPhoneFlow.typeInTextField("phoneNumber", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("Account.apps&moreContinue"));
		activationPhoneFlow.clickLink(properties.getString("Apps.Back"));
		activationPhoneFlow.clickLink(properties.getString("Apps.Home"));
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

}