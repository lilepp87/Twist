package com.tracfone.twist.account;

// JUnit Assert framework can be used for verification

import org.junit.Assert;


import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	private Properties properties = new Properties();
	
	public CreateAccount() {
		
	}
	
	public void createNewAccount() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";
		TwistUtils.setDelay(20);
		System.out.println("Email:" + randomEmail);
		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		
		myAccountFlow.typeInTextField("first_name", "TwistFirstName");
		myAccountFlow.typeInTextField("last_name", "TwistLastName");
		
/*		if (myAccountFlow.submitButtonVisible("Continuar")) {
			myAccountFlow.pressSubmitButton("Continuar");
		}*/
		
		myAccountFlow.chooseFromSelect("dob_day", "01");

		if (myAccountFlow.linkVisible("Track Your Order")) {
			myAccountFlow.chooseFromSelect("dob_month", "February");
		} else {
			myAccountFlow.chooseFromSelect("dob_month", "Febrero");
		}
		
		myAccountFlow.chooseFromSelect("dob_year", "1982");
		//myAccountFlow.typeInTextField("address1", "1295 Charleston Road");
		//myAccountFlow.typeInTextField("zip", "94043");
		//myAccountFlow.typeInTextField("city", "Mountain View");
		//myAccountFlow.chooseFromSelect("state", "CA");

		myAccountFlow.typeInTextField("email", randomEmail);
		//myAccountFlow.typeInTextField("confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("password", password);
		myAccountFlow.typeInPasswordField("confirm_password", password);
		myAccountFlow.typeInTextField("four_pin", "12345");
		myAccountFlow.typeInPasswordField("sec_ans", "Robert");
		myAccountFlow.typeInTextField("nickname", "Twist Nickname");
		myAccountFlow.pressSubmitButton("default_submit_btn");
//		if (myAccountFlow.linkVisible("Track Your Order")) {
//			myAccountFlow.pressSubmitButton("default_submit_btn");
//		} else {
//			myAccountFlow.pressSubmitButton("Continuar");
//		}
	}
	
	public void createNewAccountEnglish() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";
		System.out.println("Email:" + randomEmail);
		
		myAccountFlow.typeInTextField("first_name", "TwistFirstName");
		myAccountFlow.typeInTextField("last_name", "TwistLastName");
		myAccountFlow.chooseFromSelect("dob_day", "01");
		myAccountFlow.chooseFromSelect("dob_month", properties.getString("june"));
		myAccountFlow.chooseFromSelect("dob_year", "1982");
		//myAccountFlow.typeInTextField("address1", "1295 Charleston Road");
		//myAccountFlow.typeInTextField("zip", "94043");
		//myAccountFlow.typeInTextField("city", "Mountain View");
		//myAccountFlow.chooseFromSelect("state", "CA");

		myAccountFlow.typeInTextField("email", randomEmail);
		myAccountFlow.typeInTextField("confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("password", password);
		myAccountFlow.typeInPasswordField("confirm_password", password);
		myAccountFlow.typeInTextField("four_pin", "12345");
		myAccountFlow.typeInPasswordField("sec_ans", "Robert");
		myAccountFlow.typeInTextField("nickname", "Twist Nickname");
		myAccountFlow.pressSubmitButton("default_submit_btn");
//		myAccountFlow.pressSubmitButton("Submit");
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void tryToResetEmail() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String currentMin = esnUtil.getCurrentESN().getMin();
		myAccountFlow.clickLink("Mi Cuenta");
		myAccountFlow.clickLink("Olvidé mi Correo Electrónico");
		myAccountFlow.typeInTextField("user_min", currentMin);
		myAccountFlow.pressSubmitButton("Continuar");
		Assert.assertFalse(myAccountFlow.labelVisible("*Zona Postal de Activación:"));
	}

}