package com.tracfone.twist.impl.eng.myAccount;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Login {

	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private Properties properties = new Properties("Net10");

	public Login() {

	}

	public void logoutAndLoginAgain() throws Exception {
		String email = esnUtil.getCurrentESN().getEmail();
		String password = esnUtil.getCurrentESN().getPassword();
		activationPhoneFlow.clickLink(properties.getString("Account.SignOut"));
		activationPhoneFlow.typeInTextField("email", email);
		activationPhoneFlow.typeInPasswordField("password", password);
		activationPhoneFlow.pressSubmitButton(properties.getString("Account.LogIn"));
		Assert.assertTrue(activationPhoneFlow.linkVisible(properties.getString("Account.SignOut")));
	}
	
	public void logout() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("Account.SignOut"));
	}
	
	public void goToInternationalCallingPage() throws Exception {
		activationPhoneFlow.clickLink("NET10 Programs");
		activationPhoneFlow.clickLink("International long distance");
		
		
	}
	
	

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

	public void loginToMyAccount(String email) throws Exception {
		activationPhoneFlow.clickLink(properties.getString("Account.MyAccountLink"));
		TwistUtils.setDelay(10);
		activationPhoneFlow.typeInTextField(properties.getString("Account.EmailText"), email);
		activationPhoneFlow.typeInPasswordField(properties.getString("Account.PasswordPswd"), properties.getString("Account.DefaultPassword"));
		activationPhoneFlow.pressSubmitButton(properties.getString("Account.LogIn"));
	}

}