package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;
import org.mortbay.log.Log;

import junit.framework.Assert;

import com.tracfone.twist.context.OnTotalWirelessHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final Integer COPPA_DOB = Integer.valueOf(2000);
	private static Logger logger = LogManager.getLogger(CreateAccount.class.getName());

	public CreateAccount() {

	}

	public synchronized void createNewAccount() throws Exception {
		//TwistUtils.setDelay(8);
		/*if (myAccountFlow.buttonVisible("default_continue_with_create_account")) {
			myAccountFlow.pressButton("default_continue_with_create_account");
			TwistUtils.setDelay(8);
		}*/
		
		if (myAccountFlow.getBrowser().div("create an account").isVisible()) {
			myAccountFlow.getBrowser().div("create an account").click();
			TwistUtils.setDelay(5);
		}
		//enterPersonalInfo("1982");
		//TwistUtils.setDelay(5);
		enterEmailInfo();
		//TwistUtils.setDelay(5);
		synchronized (phoneUtil) {
			Log.info("!!!! Start Create account");
			//TwistUtils.setDelay(6);
			//myAccountFlow.pressSubmitButton("default_submit_btn");
			myAccountFlow.getBrowser().div("createaccount_submit").click();
			TwistUtils.setDelay(2);
			Log.info("!!!! Exit Create account");
		}
//		String successMsg = "We apologize but there seems to be an issue. Please contact our Customer Care Center at 1-866-663-3633 for further assistance.";
//		if(myAccountFlow.getBrowser().paragraph(successMsg).isVisible()){
//			TwistUtils.setDelay(8);
//			if (myAccountFlow.getBrowser().div("create an account").isVisible()) {
//				myAccountFlow.getBrowser().div("create an account").click();
//				TwistUtils.setDelay(8);
//			}
//			//enterPersonalInfo("1982");
//			//TwistUtils.setDelay(8);
//			enterEmailInfo();
//			TwistUtils.setDelay(7);
//			synchronized (phoneUtil) {
//				Log.info("!!!! Start Create account");
//				TwistUtils.setDelay(6);
//				myAccountFlow.pressSubmitButton("default_submit_btn");
//				TwistUtils.setDelay(6);
//				Log.info("!!!! Exit Create account");
//			}
//			
//		}
	
	}
		

	public synchronized void createNewAccountDependingOnStatus(String status) throws Exception {
		if (NEW_STATUS.equalsIgnoreCase(status) || status.equalsIgnoreCase("Login")) {
			createNewAccount();
		}
	}
	
	public void logOut() {
		if (myAccountFlow.linkVisible("sign_in_out_link")) {
			myAccountFlow.clickLink("sign_in_out_link");
		} else {
			myAccountFlow.navigateTo(OnTotalWirelessHomePage.URL);
			if (myAccountFlow.linkVisible("sign_in_out_link")) {
				myAccountFlow.clickLink("sign_in_out_link");
			}
		}
	}

	public void logInDependingOnStatus(String status) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status) || "Existing".equalsIgnoreCase(status)) {
			ESN curresn = esnUtil.getCurrentESN();
			System.out.println(curresn);
			System.out.println(curresn.getEmail());
			System.out.println(curresn.getPassword());
			myAccountFlow.getBrowser().div("log into my account").click();
			myAccountFlow.typeInTextField("userID", curresn.getEmail());
			myAccountFlow.typeInPasswordField("password", curresn.getPassword());
			//myAccountFlow.pressButton("default_login_btn");
			myAccountFlow.getBrowser().div("loginaccount_submit").click();
		}
	}
	

	public void createNewAccountAsNegativeCase(Integer dobYear) throws Exception {
		enterEmailInfo();
		enterPersonalInfo(dobYear.toString());
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.NickNameText.name, "Twist Nickname");
	
		if (COPPA_DOB.compareTo(dobYear) < 0) {
			myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
			Assert.assertTrue(myAccountFlow.divVisible("User is COPPA age restricted"));
		} else {
			myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ConfirmEmailText.name, "someotheremail@msn.com");
			myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		}
	
		TwistUtils.setDelay(10);
		Assert.assertTrue(!myAccountFlow.h2Visible("ORDER SUMMARY"));
		//TODO Add error message asset
	}

	public void tryToReset(String whatToReset) throws Exception {
		myAccountFlow.clickLink("sign_in_out_link");

		if ("Password".equalsIgnoreCase(whatToReset)) {
			String dummyEmail = esnUtil.getCurrentESN().getEsn() + "@totalwireless.com";
			myAccountFlow.clickLink("Forgot password");
			myAccountFlow.typeInTextField("input_email", dummyEmail);
			//myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		} else {
			myAccountFlow.clickLink("Forgot Email");
		}
//		Assert.assertTrue("Dummy email used to reset password",
//				myAccountFlow.divVisible("Email not registered to My Account and not found in database"));
//		myAccountFlow.clickSpanMessage("dijitDialogCloseIcon dijitDialogCloseIconHover");
//		myAccountFlow.clickImage("Signin");
	}
	
	public void clickForgotEmail(String zip) throws Exception {
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
//		myAccountFlow.clickLink("Forgot Email");
		myAccountFlow.typeInTextField("forgot_username_input_phone", min);
		myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		myAccountFlow.typeInTextField("zip", zip);
		myAccountFlow.pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
	}

	public void enterAccountInformationForActivePhone(Integer dobYear) throws Exception {
		enterEmailInfo();
		myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);

		enterPersonalInfo(dobYear.toString());
		myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);

		if (COPPA_DOB.compareTo(dobYear) < 0) {
			Assert.assertTrue(myAccountFlow.divVisible("User is COPPA age restricted") || 
					myAccountFlow.divVisible("User is not 13 years old per COPPA"));
			Assert.assertFalse(myAccountFlow.divVisible("/Welcome TwistFirstName TwistLastName.*/"));
		} else {
			Assert.assertTrue(myAccountFlow.divVisible("/Welcome TwistFirstName TwistLastName.*/"));
		}
	}

	private synchronized void enterEmailInfo() {
		TwistUtils.setDelay(8);
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";
		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);
		logger.info("Email:" + randomEmail);
		myAccountFlow.typeInTextField("input_txt_email", randomEmail);
		//myAccountFlow.typeInTextField("input_confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("input_txt_password", password);
		myAccountFlow.typeInPasswordField("input_txt_confirm_password", password);
		myAccountFlow.getBrowser().textbox("input_txt_dob").setValue("03/09/1985");
		myAccountFlow.typeInTextField("input_txt_pin", "12345");
		myAccountFlow.typeInTextField("input_txt_zip_code", "33178");
	//	myAccountFlow.clickLink("input_txt_dob");
		/*myAccountFlow.chooseFromSelect("ui-datepicker-year", "1987");
		myAccountFlow.chooseFromSelect("ui-datepicker-month", "March");
		myAccountFlow.clickLink("23");
		browser.div("Please enter Date of Birth SUBMIT").click();*/
		
		//myAccountFlow.chooseFromSelect("input_dob_year", dobYear);
		/*if (myAccountFlow.textboxVisible("input_security_question_answer")) {
			myAccountFlow.typeInTextField("input_security_question_answer", "Robert");
		} else {
			myAccountFlow.typeInPasswordField("input_security_question_answer", "Robert");
		}*/
	}
	
	private synchronized void enterPersonalInfo(String dobYear) {
		TwistUtils.setDelay(10);
		if (!myAccountFlow.textboxVisible("input_txt_email")) {
			TwistUtils.setDelay(90);
		}
		myAccountFlow.typeInTextField("input_txt_email", "TwistFirstName");
		myAccountFlow.typeInTextField("input_last_name", "TwistLastName");
		myAccountFlow.typeInTextField("input_zip_code", "94043");
		myAccountFlow.chooseFromSelect("input_dob_month", "March");
		myAccountFlow.chooseFromSelect("input_dob_day", "16");
		myAccountFlow.chooseFromSelect("input_dob_year", dobYear);
	}

	public void tryToResetPassword() throws Exception {
		myAccountFlow.clickLink("sign_in_out_link");
		myAccountFlow.clickLink("Forgot Password?");
		String email = esnUtil.getCurrentESN().getEmail();
		TwistUtils.setDelay(6);
		myAccountFlow.typeInTextField("input_email", email);
		myAccountFlow.pressButton("SUBMIT");
		TwistUtils.setDelay(6);
		String successMsg = "A link to reset your password will be sent to the email on file. If" +
				" we do not have your email address on file, we will send you a text message. If" +
				" you need further assistance, please contact us at 1-866-663-3633.";
	
		Assert.assertTrue(myAccountFlow.getBrowser().byText(successMsg, "p").isVisible());
		myAccountFlow.clickLink("BACK TO SIGN IN");
	}
	
	public void addPhoneToMyAccount() throws Exception {
		ESN esn = esnUtil.getCurrentESN().getFromEsn();
		myAccountFlow.clickLink("Add device to My Account");
		myAccountFlow.typeInTextField("esn", esn.getEsn());
		myAccountFlow.typeInTextField("phone_number", esn.getMin());
		myAccountFlow.pressSubmitButton("Add");
		TwistUtils.setDelay(10);
		Assert.assertTrue("No added device message", myAccountFlow.divVisible("Your device has been successfully added to your account!"));
	}

//	public void selectCreateNewAccount(String esnPart) throws Exception {
//		activationPhoneFlow.clickLink("First-time Log in?");
//		String esnStr = phoneUtil.getActiveEsnByPartNumber(esnPart);
//		phoneUtil.clearOTAforEsn(esnStr);
//		String min = phoneUtil.getMinOfActiveEsn(esnStr);
//		ESN esn = new ESN(esnPart, esnStr);
//		storeRedeemInfo(esn);
//		esnUtil.setCurrentESN(esn);
//		TwistUtils.setDelay(10);
//		logger.info(esn);
//		logger.info(min);
//		activationPhoneFlow.typeInTextField("forgot_username_input_phone", min);
//		activationPhoneFlow.pressSubmitButton("CONTINUE TO NEXT STEP");
//		activationPhoneFlow.typeInTextField("user_pin", "1234");
//		activationPhoneFlow.typeInTextField("user_security_answer", "Robert");
//		activationPhoneFlow.pressButton("SUBMIT");
//		String userName=activationPhoneFlow.getBrowser().hidden("user_name").value();
//		logger.info("userName:"+userName);
//		activationPhoneFlow.clickLink("BACK TO SIGN IN");
//		activationPhoneFlow.typeInTextField("userid", userName);
//		activationPhoneFlow.typeInPasswordField("password","tracfone");
//		activationPhoneFlow.pressButton("log in to my account");
//	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

}