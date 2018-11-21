package com.tracfone.twist.impl.eng.myAccount;

/*
 * Created by: Hannia Leiva 10/11/2011
 * Test Case ID:
 * Create New Account
 * Email: random Email
 *
 * */

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateANewAccount {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private Properties properties = new Properties("Net10");
	
	public CreateANewAccount() {

	}

	public void goToMyAccount() throws Exception {
		myAccountFlow.clickLink(MyAccountFlow.MyAccountNet10EWebFields.MyAccountRegisterLink.name);
	}

	public void selectRegisterWithMin() throws Exception {
		/*String esn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		myAccountFlow.typeInTextField("user_min", min);*/   /* Changed for CR34421*/
		myAccountFlow.pressSubmitButton(properties.getString("REGISTER"));
	}

	public void enterRequiredInformation() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email : " + randomEmail);

		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10EWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10EWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10EWebFields.DobYearSelect.name, "1982");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.Address1Text.name, "10050 NW Test");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.ZipCodeText.name, "33178");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10EWebFields.SecurityAswerPswd.name, "Robert");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.EmailText.name, randomEmail);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10EWebFields.ConfirmEmailText.name, randomEmail);
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10EWebFields.PasswordPswd.name, "tracfone");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10EWebFields.ConfirmPasswordPswd.name, "tracfone");
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountNet10EWebFields.SubmitButton.name);
	}
	
	public void skipAccountCreation() throws Exception {
		myAccountFlow.clickLink(MyAccountFlow.MyAccountNet10EWebFields.RegisterLink.name);
		myAccountFlow.clickLink("click here");
	}
	
	public void skipAccountForFromEsn() throws Exception {
		String fromEsn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(fromEsn);
		
		myAccountFlow.typeInTextField("user_min", min);
		myAccountFlow.pressSubmitButton(properties.getString("REGISTER"));
		myAccountFlow.typeInTextField("user_esn", fromEsn);
		myAccountFlow.pressSubmitButton(properties.getString("REGISTER"));
		myAccountFlow.typeInTextField("user_zip", "33178");
		myAccountFlow.pressSubmitButton("default_submit_btn");
		myAccountFlow.clickLink(properties.getString("Account.SkipLink"));
	}

	public void enterSurveyAnswers() throws Exception {
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10EWebFields.Answer1Radio.name);
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10EWebFields.Answer2Radio.name);
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10EWebFields.Answer3Radio.name);
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountNet10EWebFields.ContinueButton.name);
	}
	
	public void enterPersonalInformation() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);

		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.Address1Text.name, "1295 Charleston Road");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ZipCodeText.name, "94043");
		myAccountFlow.typeInTextField("phone", TwistUtils.generateRandomMin());
		myAccountFlow.selectCheckBox("create_user_account");
		myAccountFlow.typeInTextField("email", randomEmail);
		myAccountFlow.typeInTextField("confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("password", "123456");
		myAccountFlow.typeInPasswordField("confirm_password", "123456");
		myAccountFlow.chooseFromSelect("sec_qstn", "What is your father's middle name");
		myAccountFlow.typeInPasswordField("sec_ans", "Robert");
		myAccountFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void enterInformation() throws Exception {
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.Address1Text.name, "1295 Charleston Road");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ZipCodeText.name, "94043");
		myAccountFlow.typeInTextField("phone", TwistUtils.generateRandomMin());
		myAccountFlow.pressSubmitButton(properties.getString("continue"));
	}
	public void selectRegisterWithMinFromPart(String string1) throws Exception {
		throw new IllegalArgumentException("Not Implemented Yet");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}