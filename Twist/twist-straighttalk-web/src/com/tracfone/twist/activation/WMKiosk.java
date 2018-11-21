package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class WMKiosk {

	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";

	public WMKiosk() {

	}
//
	public void goToWMActivationWithCard() throws Exception {
		 activationPhoneFlow.clickLink("Activation with a Service Card");
	}

	public void goToWMActivationDependingOn(String status) throws Exception {
		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.navigateTo("https://sit1walmart.straighttalk.com");
		}
		
		activationPhoneFlow.clickLink("Activation with Enrollment, Reactivation, or Porting");
		activationPhoneFlow.pressSubmitButton("Continue");
	}
	
	public void goToWMTransferPhoneNumber() throws Exception {
		activationPhoneFlow.clickLink("Activation with Enrollment, Reactivation, or Porting");	
		activationPhoneFlow.selectRadioButton("activation_option[3]");
		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void createNewAccount() throws Exception {
		enterEmailInfo();
		enterPersonalInfo("1982");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.NickNameText.name, "Twist Nickname");
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
	}

	public void createNewAccountDependingOnStatusInWMKiosk(String status)
			throws Exception {
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			createNewAccount();
		}
	}
	
	private void enterEmailInfo() {
		TwistUtils.setDelay(20);
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";

		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.EmailText.name, randomEmail);
		//myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ConfirmEmailText.name, randomEmail);
		activationPhoneFlow.typeInPasswordField(ActivationReactivationFlow.ActivationStraighttalkWebFields.PasswordPswd.name, password);
		activationPhoneFlow.typeInPasswordField(ActivationReactivationFlow.ActivationStraighttalkWebFields.ConfirmPasswordPswd.name, password);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.SecurityPinText.name, "12345");
		if (activationPhoneFlow.textboxVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.SecurityAswerText.name)) {
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.SecurityAswerText.name, "Robert");
		} else {
			activationPhoneFlow.typeInPasswordField(ActivationReactivationFlow.ActivationStraighttalkWebFields.SecurityAswerText.name, "Robert");
		}
	}
	
	public void enterPersonalInfo(String dobYear) {
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.FirstNameText.name, "TwistFirstName");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.LastNameText.name, "TwistLastName");
		//myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.Address1Text.name, "1295 Charleston Road");
		//myAccountFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CityText.name, "Mountain View");
		//myAccountFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.StateSelect.name, "CA");
		//myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.AddressZipCodeText.name, "94043");
		activationPhoneFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobDaySelect.name, "01");
		activationPhoneFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobMonthSelect.name, "February");
		activationPhoneFlow.chooseFromSelect(ActivationReactivationFlow.ActivationStraighttalkWebFields.DobYearSelect.name, dobYear);
	}

}