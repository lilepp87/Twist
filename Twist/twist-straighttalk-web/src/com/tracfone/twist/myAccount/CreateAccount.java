package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.context.OnStraighttalkHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
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
	private ResourceBundle props = ResourceBundle.getBundle("straighttalk");
	
	public CreateAccount() {

	}
//
	public void createNewAccount(String dob) throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";
		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);
		TwistUtils.setDelay(15);

		if (!myAccountFlow.linkVisible("activate-create-btn")) {
			System.out.println("'activate-create-btn' link not found, reloading the page & waiting 20 sec");
			myAccountFlow.getBrowser().execute("location.reload()");
			TwistUtils.setDelay(20);
		}
		if (!myAccountFlow.linkVisible("activate-create-btn")) {
			System.out.println("'activate-create-btn' link not found, reloading the page & waiting 20 sec");
			myAccountFlow.getBrowser().execute("location.reload()");
			TwistUtils.setDelay(20);
		}
		
		if (myAccountFlow.linkVisible("activate-create-btn"))
			myAccountFlow.clickLink("activate-create-btn");
		else if (myAccountFlow.linkVisible("CREATE"))
			myAccountFlow.clickLink("CREATE");
		else
			myAccountFlow.getBrowser().byId("activate-create-btn").click();

		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.EmailText.name, randomEmail);
		myAccountFlow.typeInPasswordField(ActivationReactivationFlow.ActivationStraighttalkWebFields.PasswordPswd.name, password);
		myAccountFlow.typeInPasswordField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ConfirmPasswordPswd.name, password);
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.SecurityPinText.name, "12345");
		myAccountFlow.typeInTextField("input_dob", dob);
		myAccountFlow.pressButton("create-account-submit");
	}
//
	public void createNewAccountDependingOnStatus(String status) throws Exception {
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			createNewAccount("01/01/1980");
		}
	}
//	
	public void logOut() {
		if (myAccountFlow.linkVisible("sign_in_out_link[1]")) {
			myAccountFlow.clickLink("sign_in_out_link[1]");
		} else if (myAccountFlow.linkVisible("sign_in_out_link")) {
			myAccountFlow.clickLink("sign_in_out_link");
		} else if (myAccountFlow.linkVisible("Logout")) {
			myAccountFlow.clickLink("Logout");
		} else if (myAccountFlow.linkVisible("logout")) {
			myAccountFlow.clickLink("logout");
		} else {
			myAccountFlow.navigateTo(OnStraighttalkHomePage.URL);
			if (myAccountFlow.linkVisible("sign_in_out_link[1]")) {
				myAccountFlow.clickLink("sign_in_out_link[1]");
			} else if (myAccountFlow.linkVisible("sign_in_out_link")) {
				myAccountFlow.clickLink("sign_in_out_link");
			} else if (myAccountFlow.linkVisible("Logout")) {
				myAccountFlow.clickLink("Logout");
			} else if (myAccountFlow.linkVisible("logout")) {
				myAccountFlow.clickLink("logout");
			}
		}
	}
//
	public void logInDependingOnStatus(String status) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status)/* && myAccountFlow.textboxVisible("userID")*/) {
			if (!myAccountFlow.linkVisible("activate-login-btn")) {
				TwistUtils.setDelay(15);
			}
			myAccountFlow.clickLink("activate-login-btn");
			ESN currEsn = esnUtil.getCurrentESN();
			myAccountFlow.typeInTextField("userID", currEsn.getEmail());
			myAccountFlow.typeInPasswordField("password", currEsn.getPassword());
			myAccountFlow.pressButton("default_login_btn");
		}
	}
//
	public void createNewAccountAsNegativeCase(Integer dobYear) throws Exception {
		createNewAccount("01/01/" + dobYear);
		TwistUtils.setDelay(15);
		if (COPPA_DOB.compareTo(dobYear) < 0) {
			Assert.assertTrue(myAccountFlow.divVisible("We cannot process your transaction with the information " +
					"provided. Please contact our Customer Care Center at 1-877-430-CELL (2355) for further assistance."));
			Assert.assertTrue(!myAccountFlow.divVisible("YOUR SERVICE WAS ACTIVATED. WE'RE ALMOST DONE!"));
		} else {
			Assert.assertTrue(myAccountFlow.divVisible("YOUR SERVICE WAS ACTIVATED. WE'RE ALMOST DONE!"));
		}
	}

	public void tryToReset(String whatToReset) throws Exception {
		myAccountFlow.clickLink("my account");

		if ("Password".equalsIgnoreCase(whatToReset)) {
			String dummyEmail = esnUtil.getCurrentESN().getEsn() + "@straighttalk.com";
			myAccountFlow.clickLink("Password");
			myAccountFlow.typeInTextField("input_email", dummyEmail);
			myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		} else {
			myAccountFlow.clickLink("Email");
		}
	}
	
	public void clickForgotEmail(String zip) throws Exception {
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		//String verificationCode = esnUtil.getCurrentESN().getLastFourDigits();
		String esn= esnUtil.getCurrentESN().getEsn();
		System.out.println("esn1:"+esn);
		String last6digits = esn.substring(esn.length()-6);
        System.out.println("last6digits:"+last6digits);
        String email = TwistUtils.createRandomEmail();
        String password = "tracfone";
        ESN currEsn = esnUtil.getCurrentESN();
		//esn.setLastFourDigits(last4digits);

//		myAccountFlow.clickLink("Forgot Email");
		myAccountFlow.typeInTextField("forgot_username_input_phone", min);
		//myAccountFlow.pressSubmitButton("default_submit_btn");
		myAccountFlow.pressButton("Continue");
		TwistUtils.setDelay(8);
		//myAccountFlow.typeInTextField("verificationCodeEntered", last6digits);
		//myAccountFlow.pressButton("default_submit_btn[1]");
		myAccountFlow.typeInTextField("input_email", email);
		myAccountFlow.typeInPasswordField("input_password", password);
		myAccountFlow.typeInTextField("input_pin", "12345");
		myAccountFlow.chooseFromSelect("input_security_question", "What is the name of the street you grew up on?");
		myAccountFlow.typeInTextField("input_security_question_answer", "xyz");

		currEsn.setEmail(email);
		currEsn.setPassword(password);
		myAccountFlow.pressSubmitButton("default_submit_btn");
		//myAccountFlow.pressButton("default_login_btn");
		//myAccountFlow.typeInTextField("zip", zip);
		//myAccountFlow.pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
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

	public void tryToResetPassword() throws Exception {
		myAccountFlow.clickLink("my account");
		//myAccountFlow.clickLink("Forgot password");
		myAccountFlow.clickLink("Password");
		String email = esnUtil.getCurrentESN().getEmail();
		myAccountFlow.typeInTextField("input_email", email);
		myAccountFlow.pressSubmitButton("Submit");
		TwistUtils.setDelay(15);
		Assert.assertTrue(myAccountFlow.getBrowser().div("tracfone_login_resetpassword").text().contains("Your Username was successfully verified."));
		myAccountFlow.pressSubmitButton("Back to Sign In");
	}
//
	public void addPhoneToMyAccount() throws Exception {
		ESN esn = esnUtil.getCurrentESN().getFromEsn();
		TwistUtils.setDelay(20);
		pressButton("Add device to My Account");
		TwistUtils.setDelay(10);
		if (!myAccountFlow.textboxVisible("esn")) {
			TwistUtils.setDelay(45);
		}
		myAccountFlow.typeInTextField("esn", esn.getEsn());
		pressButton("continueButton");
		TwistUtils.setDelay(10);
		myAccountFlow.typeInTextField("phone_number", esn.getMin());
		pressButton("Add");
		TwistUtils.setDelay(20);
		Assert.assertTrue("No added device message", myAccountFlow.divVisible("Your device has been successfully added to your account!"));
	}
	
	public void pressButton(String btnName){
		if (myAccountFlow.buttonVisible(btnName)) {
			myAccountFlow.pressButton(btnName);
		} else if (myAccountFlow.submitButtonVisible(btnName)) {
			myAccountFlow.pressSubmitButton(btnName);
		}
	}

	public void enrollInLRPOutsideAccount() throws Exception {
		String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		myAccountFlow.typeInTextField("min", min);	
		myAccountFlow.selectCheckBox("termOfUse");
		myAccountFlow.pressButton("Continue");
		
		String email = esnUtil.getCurrentESN().getEmail();
		String pwd = esnUtil.getCurrentESN().getPassword();
		myAccountFlow.typeInTextField("userID", email);
		myAccountFlow.typeInPasswordField("password", pwd);
		myAccountFlow.pressButton("default_login_btn");
		TwistUtils.setDelay(25);
		String points= phoneUtil.getLRPPointsbyEmailforTranstype("PROGRAM_ENROLLMENT",esnUtil.getCurrentESN().getEmail());
		Assert.assertTrue(myAccountFlow.getBrowser().div("col-md-12 col-sm-12").text().contains("You will now accumulate points every time you refill. YOU'VE JUST EARNED "+points+" POINTS!"));
		myAccountFlow.clickLink("Back to My Account");
	}

	public void goToTAS() throws Exception {
		myAccountFlow.navigateTo(props.getString("TAS_URL"));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "itquser");
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abcd1234!");
		myAccountFlow.pressSubmitButton("Login");
	}

	//TODO: Delete tas
	public void createAccountForWithNoEmail(String brand) throws Exception {
		
		esnUtil.setCurrentBrand(brand);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it14/", "33178");
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:sbc3::content/");
		//myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it15/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it18/", "05/15/1951");
		if (esnUtil.getCurrentESN().getPartNumber().contains("API4")) {
			String hexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it24/", hexEsn);
		} else if("byop".equalsIgnoreCase(esnUtil.getCurrentESN().getPartNumber())){

		} else {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it24/", esnUtil.getCurrentESN().getEsn());
		}
		if(myAccountFlow.linkVisible("Expand Expanded")){
			myAccountFlow.clickLink("Expand Expanded");
			}
		/*myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it8/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it10/", "TwistLastName");*/

		if(brand.equalsIgnoreCase("total_wireless")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it22/", "1234");
		}
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}
//
	public void enterPersonalInfoForDOB(Integer dobYear) throws Exception {
		enterPersonalInformation(dobYear.toString());
		myAccountFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		Assert.assertTrue(myAccountFlow.h1Visible("My Account Summary"));
		if (COPPA_DOB.compareTo(dobYear) < 0) {
			Assert.assertTrue(myAccountFlow.divVisible("User is COPPA age restricted") || myAccountFlow.divVisible("User is not 13 years old per COPPA"));
			//Assert.assertFalse(myAccountFlow.divVisible("/Welcome " + email));
			Assert.assertFalse(myAccountFlow.h1Visible("My Account Summary"));
		} else {
			//Assert.assertTrue(myAccountFlow.divVisible("/Welcome " + email));
			Assert.assertTrue(myAccountFlow.h1Visible("My Account Summary"));
		}
	}

	private void enterPersonalInformation(String dobYear) {
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.Address1Text.name, "1295 Charleston Road");
		myAccountFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CityText.name, "Mountain View");
		myAccountFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.StateSelect.name, "CA");
		myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.AddressZipCodeText.name, "94043");
		myAccountFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobMonthSelect.name, "February");
		myAccountFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobYearSelect.name, dobYear);
	}
//
	public void tryToResetEmail() throws Exception {
		myAccountFlow.clickLink("my account");
		//myAccountFlow.clickLink("Forgot Email");
		myAccountFlow.clickLink("Email");
		String esn = esnUtil.getCurrentESN().getEsn();
		myAccountFlow.typeInTextField("forgot_username_input_phone", esn);
		myAccountFlow.pressButton("Continue");
		TwistUtils.setDelay(14);
		myAccountFlow.typeInTextField("pinEntered", "1234");
		myAccountFlow.pressButton("Continue");
		TwistUtils.setDelay(7);
		Assert.assertTrue(myAccountFlow.getBrowser().div("tracfone_login_reset_username").text()
				.contains("Your account was successfully verified."));
		myAccountFlow.pressButton("Back to Sign In");
	}

}