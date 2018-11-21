package com.tracfone.twist.myAccount;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

public class CreateANewAccount {

	private MyAccountFlow myAccountFlow;

	public CreateANewAccount() {

	}

	public void goToMyAccount() throws Exception {
		myAccountFlow.clickLink(MyAccountFlow.MyAccountNet10SWebFields.MyAccountRegisterLink.name);
	}

	public void selectRegister() throws Exception {
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountNet10SWebFields.RegisterLink.name);
	}

	public void enterRequiredInformation() throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email : " + randomEmail);

		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10SWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10SWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountNet10SWebFields.DobYearSelect.name, "1982");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.Address1Text.name, "10050 NW Test");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.ZipCodeText.name, "33178");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10SWebFields.SecurityAswerPswd.name, "Robert");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.EmailText.name, randomEmail);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountNet10SWebFields.ConfirmEmailText.name, randomEmail);
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10SWebFields.PasswordPswd.name, "tracfone");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountNet10SWebFields.ConfirmPasswordPswd.name, "tracfone");
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountNet10SWebFields.SubmitButton.name);
	}

	public void enterSurveyAnswers() throws Exception {
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10SWebFields.Answer1Radio.name);
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10SWebFields.Answer2Radio.name);
		myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountNet10SWebFields.Answer3Radio.name);
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountNet10SWebFields.ContinueButton.name);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}