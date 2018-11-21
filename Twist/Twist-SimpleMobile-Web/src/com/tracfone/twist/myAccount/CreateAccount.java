package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.junit.Assert;

import com.tracfone.context.LogOut;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private ResourceBundle rb = ResourceBundle.getBundle("SM");

	public CreateAccount() {

	}

	public void createNewAccount() throws Exception {
		TwistUtils.setDelay(25);
		pressButton("CREATE");
		TwistUtils.setDelay(10);
		if (!myAccountFlow.textboxVisible(rb.getString("sm.EmailText"))) {
			TwistUtils.setDelay(75);
		}
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";

		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);

		/*myAccountFlow.typeInTextField(rb.getString("sm.FirstNameText"), rb.getString("sm.firstname"));
		myAccountFlow.typeInTextField(rb.getString("sm.LastNameText"), rb.getString("sm.lastname"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobDayText"), rb.getString("sm.DobDaySelect"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobMonthText"), rb.getString("sm.DobMonthSelect"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobYearText"), rb.getString("sm.DobYearSelect"));*/
		//myAccountFlow.typeInTextField(rb.getString("sm.Address1Text"), rb.getString("sm.address1"));
		//myAccountFlow.typeInTextField(rb.getString("sm.cityText"), rb.getString("sm.city"));
		//myAccountFlow.chooseFromSelect(rb.getString("sm.StateSelectText"), rb.getString("sm.state"));

		if (myAccountFlow.textboxVisible(rb.getString("sm.AddressZipCodeText"))) {
			myAccountFlow.typeInTextField(rb.getString("sm.AddressZipCodeText"), rb.getString("sm.zipcode"));
		}

		myAccountFlow.typeInTextField(rb.getString("sm.EmailText"), randomEmail);
		//myAccountFlow.typeInTextField(rb.getString("sm.ConfirmEmailText"), randomEmail);
		myAccountFlow.typeInPasswordField(rb.getString("sm.PassText"), password);
		myAccountFlow.typeInPasswordField(rb.getString("sm.ConfirmPassText"), password);
		myAccountFlow.typeInTextField(rb.getString("sm.SecPinText"), "1234");
		//Need to change this hard coded value once dob format is fixed
		myAccountFlow.typeInTextField(rb.getString("sm.Dob"), "02/05/1991");
		//myAccountFlow.typeInPasswordField(rb.getString("sm.SecAnswerText"), rb.getString("sm.securityanswer"));
		//myAccountFlow.typeInTextField(rb.getString("sm.NicknameText"), rb.getString("sm.nickname"));
		myAccountFlow.pressSubmitButton("SUBMIT");
	}

	public void createNewAccountDependingOnStatus(String status) throws Exception {
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			createNewAccount();
		}
	}

	public void logInDependingOnStatus(String status) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status) || "Active".equalsIgnoreCase(status)) {
			pressButton("LOG IN");
			ESN currEsn = esnUtil.getCurrentESN();
			myAccountFlow.typeInTextField("userID", currEsn.getEmail());
			myAccountFlow.typeInPasswordField("password", currEsn.getPassword());
			myAccountFlow.pressSubmitButton("LOG IN");
			TwistUtils.setDelay(3);
			/*if(myAccountFlow.buttonVisible("LOG IN[1]")){
			pressButton("LOG IN[1]");
			}*/
		}
	}

	public void logintoReactivateTheEsn(String status) throws Exception {
		if (PAST_DUE_STATUS.equalsIgnoreCase(status) || "Active".equalsIgnoreCase(status)) {
			//pressButton("LOG IN");
			myAccountFlow.clickSpanMessage("Sign Out");
			TwistUtils.setDelay(5);
			myAccountFlow.clickSpanMessage("My Account");
			TwistUtils.setDelay(5);
			ESN currEsn = esnUtil.getCurrentESN();
			myAccountFlow.typeInTextField("userID", currEsn.getEmail());
			myAccountFlow.typeInPasswordField("password", currEsn.getPassword());
			pressButton("default_login_btn");
		}
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	private void pressButton(String button) {
		if (myAccountFlow.buttonVisible(button)) {
			myAccountFlow.pressButton(button);
		} else {
			myAccountFlow.pressSubmitButton(button);
		}
	}
	
	public void checkForDisabledBirthDateControls() throws Exception {
		TwistUtils.setDelay(10);
		myAccountFlow.clickLink("Update Personal Profile");
		TwistUtils.setDelay(10);
		Assert.assertFalse(Boolean.valueOf(myAccountFlow.getBrowser().select("input_dob_month").fetch("disabled")));
		Assert.assertFalse(Boolean.valueOf(myAccountFlow.getBrowser().select("input_dob_day").fetch("disabled")));
		Assert.assertFalse(Boolean.valueOf(myAccountFlow.getBrowser().select("input_dob_year").fetch("disabled")));		
		//Assert.assertTrue(Boolean.valueOf(myAccountFlow.getBrowser().select("input_dob_year").fetch("disabled")));
	}

	public void createNewAccountDependingOnStatusAndTryToSelect2013BirthDate(String status) throws Exception {
		TwistUtils.setDelay(10);
		pressButton("CREATE");
		TwistUtils.setDelay(10);
		
	
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";

		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);
		
		myAccountFlow.typeInTextField(rb.getString("sm.EmailText"),randomEmail);
		myAccountFlow.typeInPasswordField(rb.getString("sm.PassText"), password);
		myAccountFlow.typeInPasswordField(rb.getString("sm.ConfirmPassText"), password);
		myAccountFlow.typeInTextField(rb.getString("sm.Dob"),"02/01/2013");
		myAccountFlow.typeInTextField(rb.getString("sm.SecPinText"),"1234");
		myAccountFlow.pressSubmitButton("SUBMIT");

		/*myAccountFlow.typeInTextField(rb.getString("sm.FirstNameText"), rb.getString("sm.firstname"));
		myAccountFlow.typeInTextField(rb.getString("sm.LastNameText"), rb.getString("sm.lastname"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobDayText"), rb.getString("sm.DobDaySelect"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobMonthText"), rb.getString("sm.DobMonthSelect"));
		myAccountFlow.chooseFromSelect(rb.getString("sm.DobYearText"), "2013");*/
	}

	public void createAccountFromCheckOut() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";
		System.out.println("userid:: "+randomEmail);
		myAccountFlow.pressButton("CREATE ACCOUNT");
		myAccountFlow.typeInTextField("userid", randomEmail);
		myAccountFlow.typeInPasswordField("password", password);
		myAccountFlow.typeInPasswordField("confirmpassword", password);
		myAccountFlow.pressButton("Save");
		myAccountFlow.pressButton("Close");
	}

	public void logOut() throws Exception {
		LogOut.logout();
	}
		
}