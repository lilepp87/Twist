package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class AppsAndMore {
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();
	
	public AppsAndMore() {

	}

	public void checkForAppsAndMoreInsideAccount() throws Exception {
		String esn= esnUtil.getCurrentESN().getEsn();
		String min= phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.clickLink(properties.getString("appsMore"));

		String email = esnUtil.getCurrentESN().getEmail();
		String pwd = esnUtil.getCurrentESN().getPassword();
		
		activationPhoneFlow.navigateTo(properties.getString("TF_URL"));
		activationPhoneFlow.clickLink(properties.getString("myAccount"));
		activationPhoneFlow.typeInTextField("email[1]", email);
		activationPhoneFlow.typeInPasswordField("password", pwd);
		activationPhoneFlow.pressSubmitButton(properties.getString("signIn"));
		
		activationPhoneFlow.clickLink(properties.getString("appsMore"));
		activationPhoneFlow.typeInTextField("param_min", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		activationPhoneFlow.typeInTextField("param_min", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("yes"));
		activationPhoneFlow.navigateTo(properties.getString("TF_URL"));
		activationPhoneFlow.clickLink(properties.getString("gotoAccountSummary"));
		activationPhoneFlow.clickLink(properties.getString("signOut"));
	} 

	public void checkForAppsAndMoreOutsideAccount() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		activationPhoneFlow.clickLink(properties.getString("appsMore"));
		activationPhoneFlow.typeInTextField("param_min", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		activationPhoneFlow.typeInTextField("param_min", min);
		activationPhoneFlow.pressSubmitButton(properties.getString("yes"));
		activationPhoneFlow.navigateTo(properties.getString("TF_URL"));
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