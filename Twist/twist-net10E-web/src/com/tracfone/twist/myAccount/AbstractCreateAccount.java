package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AbstractCreateAccount {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private final String DEFAULT_DAY;
	private final String DEFAULT_MONTH;
	private final String DEFAULT_YEAR;
	private final String DEFAULT_PASS;
	private final String DEFAULT_PIN;
	private final String DEFAULT_ANSWER;
	private final String DEFAULT_FIRSTNAME;
	private final String DEFAULT_LASTNAME;
	private final String DEFAULT_DOB;
	private final Properties properties;

	protected AbstractCreateAccount(String propsName) {
		properties = new Properties(propsName);
		DEFAULT_DAY = properties.getString("Account.DefaultDay"); //$NON-NLS-1$
		DEFAULT_MONTH = properties.getString("Account.DefaultMonth"); //$NON-NLS-1$
		DEFAULT_YEAR = properties.getString("Account.DefaultYear"); //$NON-NLS-1$
		DEFAULT_PASS = properties.getString("Account.DefaultPassword"); //$NON-NLS-1$
		DEFAULT_PIN = properties.getString("Account.DefaultPin"); //$NON-NLS-1$
		DEFAULT_ANSWER = properties.getString("Account.DefaultSecurityAnswer"); //$NON-NLS-1$
		DEFAULT_FIRSTNAME = properties.getString("Account.DefaultFirstName");
		DEFAULT_LASTNAME = properties.getString("Account.DefaultLastName");
		DEFAULT_DOB =properties.getString("Account.DefaultDateOfBirth");
	}

	public void createNewAccountForEsn() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		/*if (esn.getEmail() == null || esn.getEmail().isEmpty()) {
			myAccountFlow.selectRadioButton(properties.getString("Account.NewAccountRadio")); //$NON-NLS-1$
			String email = TwistUtils.createRandomEmail();
			System.out.println("EMAIL:"+email);
			myAccountFlow.typeInTextField("first_name",DEFAULT_FIRSTNAME);
			myAccountFlow.typeInTextField("last_name", DEFAULT_LASTNAME);
			myAccountFlow.typeInTextField(properties.getString("Account.NewEmailText"), email); //$NON-NLS-1$
			//myAccountFlow.typeInTextField(properties.getString("Account.ConfirmEmailText"), email); //$NON-NLS-1$
			myAccountFlow.focusOnText(properties.getString("Account.BirthdateText")); //$NON-NLS-1$
			myAccountFlow.chooseFromSelect(properties.getString("Account.DatePickerMonthDrop"), DEFAULT_MONTH); //$NON-NLS-1$
			myAccountFlow.chooseFromSelect(properties.getString("Account.DatePickerYearDrop"), DEFAULT_YEAR); //$NON-NLS-1$
			myAccountFlow.clickLink(DEFAULT_DAY);
			myAccountFlow.typeInPasswordField(properties.getString("Account.NewPasswordPswd"), DEFAULT_PASS); //$NON-NLS-1$
			myAccountFlow.typeInPasswordField(properties.getString("Account.ConfirmPasswordPswd"), DEFAULT_PASS); //$NON-NLS-1$
			myAccountFlow.typeInPasswordField(properties.getString("Account.SecurityAswerPswd"), DEFAULT_ANSWER); //$NON-NLS-1$
			myAccountFlow.typeInTextField(properties.getString("Account.SecurityPinText"), DEFAULT_PIN); //$NON-NLS-1$
			myAccountFlow.pressSubmitButton(properties.getString("Account.CreateAccountSubmit")); //$NON-NLS-1$

			esn.setEmail(email);
			esn.setPassword(DEFAULT_PASS);
		} else {
			myAccountFlow.selectRadioButton(properties.getString("Account.LoginRadio")); //$NON-NLS-1$
			myAccountFlow.typeInTextField(properties.getString("Account.EmailText"), esn.getEmail()); //$NON-NLS-1$
			myAccountFlow.typeInPasswordField(properties.getString("Account.PasswordPswd"), esn.getPassword()); //$NON-NLS-1$
			myAccountFlow.pressSubmitButton(properties.getString("Account.SignInSubmit")); //$NON-NLS-1$
		}*/
		
		if (esn.getEmail() == null || esn.getEmail().isEmpty()) {
			myAccountFlow.pressButton(properties.getString("Account.NewAccountButton")); //$NON-NLS-1$
			String email = TwistUtils.createRandomEmail();
			System.out.println("EMAIL:"+email);
			myAccountFlow.getBrowser().emailbox(properties.getString("Account.NewEmailText")).setValue(email);
			myAccountFlow.typeInPasswordField(properties.getString("Account.NewPasswordPswd"), DEFAULT_PASS); 
			myAccountFlow.typeInPasswordField(properties.getString("Account.ConfirmPasswordPswd"), DEFAULT_PASS);
			myAccountFlow.getBrowser().datebox(properties.getString("Account.BirthdateText")).setValue(DEFAULT_DOB);
			//myAccountFlow.getBrowser().datebox("dateofbirthformat").setValue("06/02/1985");
			/*myAccountFlow.focusOnText(properties.getString("Account.BirthdateText")); 
			//id="datepicker-calendar-dateofbirth"
			myAccountFlow.getBrowser().span("glyphicon glyphicon-calendar").click();
			for(int i=0; i<25;i++){
			      myAccountFlow.getBrowser().span("glyphicon glyphicon-backward").click();
			}
			myAccountFlow.getBrowser().cell(DEFAULT_DAY).click();*/
			/*myAccountFlow.chooseFromSelect(properties.getString("Account.DatePickerMonthDrop"), DEFAULT_MONTH); 
			myAccountFlow.chooseFromSelect(properties.getString("Account.DatePickerYearDrop"), DEFAULT_YEAR); 
			myAccountFlow.clickLink(DEFAULT_DAY);*/
			myAccountFlow.typeInTextField(properties.getString("Account.SecurityPinText"), DEFAULT_PIN); 
			myAccountFlow.pressSubmitButton(properties.getString("Account.CreateAccountSubmit"));

			esn.setEmail(email);
			esn.setPassword(DEFAULT_PASS);
		} else {
			myAccountFlow.pressButton(properties.getString("Account.LoginButton"));
			myAccountFlow.typeInTextField(properties.getString("Account.EmailText"), esn.getEmail());
			myAccountFlow.typeInPasswordField(properties.getString("Account.PasswordPswd"), esn.getPassword());
			myAccountFlow.pressSubmitButton(properties.getString("Account.SignInSubmit"));
		}
	}

	public void skipAccountCreation() throws Exception {
		myAccountFlow.pressButton(properties.getString("Account.NewAccountButton")); //$NON-NLS-1$
		//myAccountFlow.selectRadioButton(properties.getString("Account.NewAccountRadio")); //$NON-NLS-1$
		myAccountFlow.clickLink(properties.getString("Account.SkipLink")); //$NON-NLS-1$
		//myAccountFlow.clickLink(properties.getString("Account.ContinueLink")); //$NON-NLS-1$
	}

	public void fillOutOriginalRegistrationFormWithZip(String zip) {
		String randomEmail = TwistUtils.createRandomEmail();
		fillOutOriginalFormWithEmailPasswordAndZip(randomEmail, properties.getString("Account.DefaultPassword"), zip);
	}

	public void fillOutOriginalFormWithEmailPasswordAndZip(String email, String password, String zip) {
		myAccountFlow.typeInTextField(properties.getString("Payment.FirstNameText"),
				properties.getString("Payment.DefaultFirstName"));
		myAccountFlow.typeInTextField(properties.getString("Payment.LastNameText"),
				properties.getString("Payment.DefaultLastName"));
		myAccountFlow.typeInTextField(properties.getString("Payment.Address1Text"),
				properties.getString("Payment.DefaultAddress"));

		myAccountFlow.typeInTextField(properties.getString("Payment.ZipCodeText"), zip);
		myAccountFlow.typeInTextField(properties.getString("Account.EmailText"), email);
		myAccountFlow.typeInTextField(properties.getString("Account.ConfirmEmailText"), email);

		myAccountFlow.typeInPasswordField(properties.getString("Account.PasswordPswd"), password);
		myAccountFlow.typeInPasswordField(properties.getString("Account.ConfirmPasswordPswd"), password);
		myAccountFlow.typeInPasswordField(properties.getString("Account.SecurityAswerPswd"),
				properties.getString("Account.DefaultSecurityAnswer"));
	}
	
	public void goToMyAccount() throws Exception{
		myAccountFlow.clickLink(properties.getString("Account.MyAccountLink"));
		myAccountFlow.clickLink(properties.getString("Account.ActivateLink"));
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

	
	public void changeDealerTo(String dealerId) throws Exception {
		String esn =esnUtil.getCurrentESN().getEsn();
		System.out.println(esn + " @@@" + dealerId +"3333"+ phoneUtil);
		myAccountFlow.navigateTo(properties.getString("Twist.TASURL")); 
		 if (myAccountFlow.linkVisible("Logout")) { 
		      myAccountFlow.clickLink("Logout"); 
		      myAccountFlow.navigateTo(properties.getString("Twist.TASURL")); 
		} 
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "itquser"); 
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abcd1234!"); 
		if (myAccountFlow.submitButtonVisible("Login")) { 
		 myAccountFlow.pressSubmitButton("Login"); 
		} else { 
		 myAccountFlow.pressButton("Login"); 
		 } 
		myAccountFlow.clickLink("Incoming Call"); 
		myAccountFlow.typeInTextField("r2:0:s12:it1", esnUtil.getCurrentESN().getEsn()); 
		clickButton("Search Service"); 
		if(myAccountFlow.submitButtonVisible("Continue to Service Profile")){
			clickButton("Continue to Service Profile");
		}
		myAccountFlow.clickCellMessage("Toss Util"); 
		 myAccountFlow.clickLink("Change Dealer"); 
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5::content/", dealerId); 
		myAccountFlow.pressSubmitButton("Search"); 
		clickButton("Change Dealer"); 
		//Assert.assertTrue(activationPhoneFlow.cellVisible("Dealer Changed - New Dealer (" + newDealer + ")") || myAccountFlow.cellVisible("Dealer Changed - New Dealer (0)")  ); 
		myAccountFlow.pressSubmitButton("Refresh"); 
		phoneUtil.changeDealerForEsn(esn, dealerId);
	}
	public void clickButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
		
	}

}