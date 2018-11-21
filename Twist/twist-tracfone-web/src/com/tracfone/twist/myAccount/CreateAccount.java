package com.tracfone.twist.myAccount;

/*
 * Created by: Hannia Leiva 10/11/2011
 * Test Case ID:
 * Create New Account
 * Email: random Email
 *
 * */

import org.junit.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private static final String NEW_STATUS = "New";
	private Properties properties = new Properties();
	private DeactivatePhone deactivatePhone;
	
	public CreateAccount() {

	}

	public void goToMyAccount() throws Exception {
		myAccountFlow.clickLink(properties.getString("myAccount"));
	}

	public void selectRegister() throws Exception {
		if (myAccountFlow.buttonVisible(properties.getString("createaccount"))){
		    myAccountFlow.pressButton(properties.getString("createaccount"));
		    enterAccountInformation();
		} else if (myAccountFlow.buttonVisible(properties.getString("exisitngaccount"))) {
			myAccountFlow.pressButton(properties.getString("exisitngaccount"));
			myAccountFlow.pressSubmitButton(properties.getString("create"));
			enterUserInformation();
		}
	}

	public void goToCheckBalance() throws Exception {
		myAccountFlow.clickLink("CHECK YOUR BALANCE");
	}

	public void enterEsnAndNickname(String partNumber) throws Exception {
		String newEsn = phoneUtil.getNewEsnByPartNumber(partNumber);
		esnUtil.setCurrentESN(new ESN(partNumber, newEsn));

		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EsnText.name, newEsn);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.NickNameText.name, "Twist Nickname");
	}
	public void enterAccountInformation() throws Exception {
		
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);
		ESN esn = esnUtil.getCurrentESN();
		System.out.println(esn.getEsn());
		esn.setEmail(randomEmail);
		esn.setPassword("tracfone");
		if (myAccountFlow.textboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name)){
            myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
            myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
            }
            myAccountFlow.clickSpanMessage("dob_icon");
            myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
            myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
            myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
            myAccountFlow.pressButton(properties.getString("add"));;
            myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EmailText.name,randomEmail);
            if (myAccountFlow.textboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name)){
            myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name, randomEmail);
            }
            myAccountFlow.typeInPasswordField("password_reg", "tracfone");
            myAccountFlow.pressButton("show_password");
            if (myAccountFlow.selectionboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.SecurityQues.name)){
            myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.SecurityQues.name, "What is your father's middle name");
            myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityAswerPswd.name, "Robert");
            }
            myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityPin.name,"12345");
         if (myAccountFlow.buttonVisible("Create"))
               myAccountFlow.pressButton("Create");
            else
                  myAccountFlow.pressSubmitButton("Create");
			
		/*String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.Address1Text.name, "1295 Charleston Road");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ZipCodeText.name, "94043");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityAswerPswd.name, "Robert");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EmailText.name, randomEmail);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name, randomEmail);
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.PasswordPswd.name, "tracfone");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmPasswordPswd.name, "tracfone");
		myAccountFlow.pressSubmitButton(properties.getString("submit"));*/
	}

	private void enterUserInformation() {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);
		ESN esn = esnUtil.getCurrentESN();
		System.out.println(esnUtil.getCurrentESN());
		esn.setEmail(randomEmail);
		esn.setPassword("tracfone");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.clickSpanMessage("dob_icon");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
		myAccountFlow.pressButton("add_dob");
		//myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.sec_ans.name, "Robert");
		myAccountFlow.typeInTextField(properties.getString("email"), randomEmail);
		myAccountFlow.typeInPasswordField(properties.getString("password"), "tracfone");
		//myAccountFlow.typeInPasswordField(properties.getString("confirmpassword"), "tracfone");;
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.SecurityQues.name, "");
		//myAccountFlow.typeInTextField("sec_ans", "Robert");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityAswerPswd.name, "Robert");
		myAccountFlow.typeInTextField(properties.getString("SecurityPin"), "12345");
	    myAccountFlow.pressSubmitButton(properties.getString("Create"));
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
		myAccountFlow.pressSubmitButton(properties.getString("continue3"));
	}

	public void enterSurveyAnswers() throws Exception {
		if (myAccountFlow.radioVisible(MyAccountFlow.MyAccountTracfoneWebFields.Answer1Radio.name)) {
			myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountTracfoneWebFields.Answer1Radio.name);
			myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountTracfoneWebFields.Answer2Radio.name);
			myAccountFlow.selectRadioButton(MyAccountFlow.MyAccountTracfoneWebFields.Answer3Radio.name);
			myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.ContinueButton.name);
		}
	}

	public void verifyNewPhone() throws Exception {
		// Not being used because number is inactive
		// TODO use recently active number
		Assert.assertTrue(myAccountFlow.divVisible(MyAccountFlow.MyAccountTracfoneWebFields.AddNewPhoneMessage.name));
	}

	public void enterMinAndEsn(String partNum) throws Exception {
		String esn = phoneUtil.getActiveEsnByPartNumber(partNum);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		String newEsn = esn.substring(esn.length() - 4);
		System.out.println("ESN :" + esn + "\n" + "MIN :" + min);

		myAccountFlow.typeInTextField("input_phone", min);
		myAccountFlow.typeInTextField("input_esn", newEsn);
		//myAccountFlow.pressSubmitButton("Submit");
		myAccountFlow.pressSubmitButton("Submit[2]");
	}

	public void checkBalanceDetails() throws Exception {
		TwistUtils.setDelay(120);
		TwistUtils.setDelay(120);
		Assert.assertTrue(myAccountFlow.h2Visible("Check Your Balance / Service End Date"));
		myAccountFlow.pressSubmitButton("HOME");
	}

	public void registerWithEsnSimAndZip(String partNumber, String simCard, String zipCode) throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);  
		
		ESN esn;
		if (partNumber.matches("PH(TF).*")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simCard));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		}else{
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		}
		esnUtil.setCurrentESN(esn);

		esn.setEmail(randomEmail);
		esn.setPassword("tracfone"); 
		System.out.println("new ESN: " + esn.getEsn());
		
		if (!simCard.isEmpty() && !partNumber.matches("PH(TF).*")) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			phoneUtil.addSimToEsn(newSim, esn);
		}

		if (!partNumber.startsWith("PHTF")) {
			String esnText;
			if (myAccountFlow.textboxVisible("esnNew")) {
				esnText = "esnNew";
			} else {
				esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
			}
			//activationPhoneFlow.typeInTextField("esnNew", esn.getEsn());
			myAccountFlow.typeInTextField(esnText, esn.getEsn());
			myAccountFlow.pressButton(properties.getString("continue"));
			submitform("continue1");
		} else {
			myAccountFlow.typeInTextField("simNew", esn.getSim());
			myAccountFlow.pressButton(properties.getString("submit"));
		}
		/*myAccountFlow.typeInTextField("emailreg", randomEmail);
		myAccountFlow.typeInTextField("confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("passwordreg", "tracfone");
		myAccountFlow.typeInPasswordField("confirm_password", "tracfone");
		myAccountFlow.typeInPasswordField("sec_ans", "Robert");*/
		
		myAccountFlow.pressButton(properties.getString("getanewphonenumber"));
		
		/*if (!simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status)) {
			myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);				
			submitform("continue2");
			
			
		} else if(simCard.isEmpty() && NEW_STATUS.equalsIgnoreCase(status)){
			myAccountFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ZipCodeText.name, zipCode);
			submitform("continue2");
			submitform("continue1");
		}*/	
		
		/*if (partNumber.matches("PH(TF).*")) {
			myAccountFlow.typeInTextField("simNew", esn.getSim());
		}else{
			myAccountFlow.typeInTextField("esn", esnStr);
		}*/
		/*myAccountFlow.chooseFromSelect("dob_month", "01");
		myAccountFlow.chooseFromSelect("dob_day", "1");
		myAccountFlow.chooseFromSelect("dob_year", "1982"); 
		myAccountFlow.pressSubmitButton(properties.getString("CREATE")); */
		
		/*if(!myAccountFlow.textboxVisible("zipCode") && !myAccountFlow.buttonVisible("Cancel")){
			myAccountFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		myAccountFlow.typeInTextField("zipCode", zipCode);
		if (myAccountFlow.buttonVisible(properties.getString("continue"))) {
			myAccountFlow.pressButton(properties.getString("continue"));
		} else {
			myAccountFlow.pressSubmitButton(properties.getString("continue"));
		}*/
	}

	private void submitform(String buttonName) {
		if(myAccountFlow.buttonVisible(properties.getString(buttonName))){
			myAccountFlow.pressButton(properties.getString(buttonName));
		}else if(myAccountFlow.submitButtonVisible(properties.getString(buttonName))){
			myAccountFlow.pressSubmitButton(properties.getString(buttonName));
		}
	}
	
	public void loginToMyAccount() throws Exception {
		String email = esnUtil.getCurrentESN().getEmail();
		String pwd = esnUtil.getCurrentESN().getPassword();
//		myAccountFlow.navigateTo(properties.getString("TF_URL"));
//		myAccountFlow.clickLink(properties.getString("myAccount"));
		
		if (myAccountFlow.linkVisible("Go to Account Summary")) {
			myAccountFlow.clickLink("Go to Account Summary");
		} else if (myAccountFlow.linkVisible("My Account")) {
			myAccountFlow.clickLink("My Account");
		}
		
		TwistUtils.setDelay(15);
//		myAccountFlow.pressButton("Login to Existing Account");
//		myAccountFlow.typeInTextField("email[1]", email);
//		myAccountFlow.typeInPasswordField("password", pwd);
		//myAccountFlow.pressButton("LogIn");
//		if (myAccountFlow.submitButtonVisible("Sign In")){
//			myAccountFlow.pressSubmitButton("Sign In");//login_form_button
//			}else{
//				myAccountFlow.pressSubmitButton("login_form_button");
//				}
	}

	public void verifyPhoneNumber() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		myAccountFlow.clickLink(properties.getString("viewPhoneOptions"));
		myAccountFlow.typeInTextField("verify_min1", min);
		myAccountFlow.pressSubmitButton(properties.getString("viewPhoneOptions"));
//		myAccountFlow.clickLink(properties.getString("signOut"));
		//loginToMyAccount();
	}

	public void addAPhone(String esnPart) throws Exception {
		String esn = phoneUtil.getActiveEsnByPartWithNoAccount(esnPart);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		esnUtil.setCurrentESN(new ESN(esnPart, esn));
        
		//myAccountFlow.clickLink(properties.getString("addNewPhone"));
		myAccountFlow.clickImage("add_phone.gif");
		System.out.println(esn + " IS ATTACHED TO MIN " + min);
		myAccountFlow.typeInTextField("cb-serial", esn);
		myAccountFlow.clickStrongMessage("CONTINUE");//myAccountFlow.pressButton("add-phone-modal-button");
		myAccountFlow.typeInTextField("cb-phone", min);
		myAccountFlow.pressSubmitButton("Add");
		if (myAccountFlow.textboxVisible("smsPhoneCode")){
			String last6digits = esn.substring(esn.length() - 6);
			myAccountFlow.typeInTextField("smsPhoneCode",last6digits);
			myAccountFlow.getBrowser().strong("CONTINUE[1]").click();
			}
		if (myAccountFlow.textboxVisible("verifyPhoneCode")){
			myAccountFlow.typeInTextField("verifyPhoneCode", phoneUtil.getZipcode(esn));
			myAccountFlow.getBrowser().strong("CONTINUE[2]").click();
			}
		String SuccessMsg= "Phone Successfully Added To Account"; 
		Assert.assertTrue(myAccountFlow.divVisible(SuccessMsg));
		phoneUtil.clearOTAforEsn(esn);
		TwistUtils.setDelay(10);
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

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setDeactivatePhone(DeactivatePhone deactivatePhone) {
		this.deactivatePhone = deactivatePhone;
	}
	
	public void tryToResetPassword() throws Exception {
		myAccountFlow.clickLink(properties.getString("myAccount"));
		myAccountFlow.pressButton("Login to Existing Account");
		myAccountFlow.clickLink(properties.getString("forgotPassword"));
		String email = esnUtil.getCurrentESN().getEmail();
		//myAccountFlow.getBrowser().byId("email").setValue(email);
		myAccountFlow.typeInTextField("email[2]", email);
		myAccountFlow.pressSubmitButton("Submit[2]");
		String successMsg = properties.getString("succesMessage");
		String msg = myAccountFlow.getBrowser().div("page_content").getText();
		Assert.assertTrue(msg.contains(successMsg));
		myAccountFlow.pressSubmitButton(properties.getString("backSignIn"));
	}

	public void logOut() throws Exception {
         //myAccountFlow.clickLink(properties.getString("signOut"));
         //myAccountFlow.getBrowser().link("Sign Out").click();
         myAccountFlow.getBrowser().link("Sign Out").click();

         myAccountFlow.clickImage("Tracfone");


		//myAccountFlow.navigateTo(properties.getString("TF_URL"));
	}

	public void updatePersonalInfo () throws Exception {
		String email = TwistUtils.createRandomEmail();
		myAccountFlow.clickLink(properties.getString("updatePersonalProfile"));
		myAccountFlow.typeInTextField("first_name", "Twist FirstName");
		myAccountFlow.typeInTextField("last_name", "Twist LastName");
		myAccountFlow.typeInTextField("address1", "9700 NW 112th Avenue");
		
		myAccountFlow.chooseFromSelect("dob_month", "01");
		myAccountFlow.chooseFromSelect("dob_day","20");
		myAccountFlow.chooseFromSelect("dob_year", "1980");
		myAccountFlow.typeInTextField("zip", "33178");
		
		myAccountFlow.typeInTextField("email[2]",email );
		myAccountFlow.typeInTextField("confirm_email",email);
		myAccountFlow.typeInPasswordField("new_password","123456a");
		myAccountFlow.typeInPasswordField("confirm_password","123456a");
		myAccountFlow.chooseFromSelect("sec_qstn", "What is your fathers middle name");
		myAccountFlow.typeInTextField("sec_ans", "Robert");
		if(myAccountFlow.buttonVisible("default_submit_btn"))
			myAccountFlow.pressButton("default_submit_btn");
		else
			myAccountFlow.pressSubmitButton("default_submit_btn");
		
	      myAccountFlow.typeInPasswordField("current_password", "tracfone");
	      myAccountFlow.pressSubmitButton("Save");
	      Assert.assertTrue(myAccountFlow.divVisible("success_msg"));
		
		myAccountFlow.pressSubmitButton(properties.getString("signIn"));
	}

	public void enterPersonalInformationIncludingBirthYear(String birthyear)
			throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);

		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
		myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, birthyear);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.Address1Text.name, "1295 Charleston Road");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ZipCodeText.name, "94043");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityAswerPswd.name, "Robert");
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EmailText.name, randomEmail);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name, randomEmail);
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.PasswordPswd.name, "tracfone");
		myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmPasswordPswd.name, "tracfone");
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.SubmitButton.name);
	}
	
	public void clickOnCreateAccount() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String currentMin = esnUtil.getCurrentESN().getMin();
		myAccountFlow.typeInTextField("user_min", currentMin);
		myAccountFlow.pressSubmitButton(properties.getString("createAccount"));
		myAccountFlow.typeInTextField("user_esn",esn.getEsn());
		myAccountFlow.pressSubmitButton(properties.getString("CREATE"));
		myAccountFlow.typeInTextField("user_zip", "33178");
		myAccountFlow.pressSubmitButton(properties.getString("submit"));
	}
	
	public void verifyTheAccount() throws Exception {
		String currentMin = esnUtil.getCurrentESN().getMin();
		myAccountFlow.typeInTextField("verify_min1", currentMin);
		myAccountFlow.pressSubmitButton(properties.getString("continue"));
		myAccountFlow.clickLink(properties.getString("signOut"));
	}

	public void tryToResetEmail() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String currentMin = esnUtil.getCurrentESN().getMin();
		System.out.println("Min::"+currentMin);
		myAccountFlow.clickLink(properties.getString("myAccount"));
		myAccountFlow.pressButton("Login to Existing Account");
		myAccountFlow.clickLink(properties.getString("forgotEmail"));
		//myAccountFlow.typeInTextField("user_min",currentMin);
		myAccountFlow.getBrowser().textbox("user_min[1]").setValue(currentMin);
		myAccountFlow.pressSubmitButton("Submit[2]");
		//myAccountFlow.pressSubmitButton(properties.getString("submit"));
		//myAccountFlow.typeInTextField("user_security_answer", "Robert");
		myAccountFlow.chooseFromSelect("dob_month", "01");
		myAccountFlow.chooseFromSelect("dob_day", "1");
		myAccountFlow.chooseFromSelect("dob_year", "1982");
		myAccountFlow.typeInTextField("user_zip", "33178");
		myAccountFlow.pressSubmitButton("Submit[2]");
		Assert.assertTrue(myAccountFlow.submitButtonVisible(properties.getString("backSignIn")));
		myAccountFlow.pressSubmitButton(properties.getString("backSignIn"));
	}
	public void tryToDoAMinChangeForPhoneInZipcode(String cellTech, String zipCode) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		//String currentMin = esnUtil.getCurrentESN().getMin();
		myAccountFlow.clickLink("Change Your Phone Number");
		myAccountFlow.typeInTextField("seri_or_sim_input", esn.getEsn());
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.ContinueButton.name);
		myAccountFlow.typeInTextField("zip_input", zipCode);
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.ContinueButton.name);
//		myAccountFlow.pressSubmitButton("default_submit_btn");
		myAccountFlow.h2Visible("Steps to complete your number change");
		myAccountFlow.pressSubmitButton("Done");
		myAccountFlow.h1Visible("My Account Summary");
		esnUtil.addRecentActiveEsn(esn, cellTech, NEW_STATUS, "Tracfone Activate");
		esnUtil.getCurrentESN().setMin(phoneUtil.getMinOfActiveEsn(esn.getEsn()));
		phoneUtil.clearOTAforEsn(esn.getEsn());
		if (!esnUtil.getCurrentESN().getPin().isEmpty()) {
//			phoneUtil.checkRedemption(esn);
		}
	}

// Real Handset //

	public void registerWithRealesnRealsimAndZip(String partNumber, String simCard, String zipCode) throws Exception {
		String randomEmail = TwistUtils.createRandomEmail();
		System.out.println("Email: " + randomEmail);  
		
		ESN esn;
		if (partNumber.matches("PH(TF).*")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simCard));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		}else{
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		}
		esnUtil.setCurrentESN(esn);
//		String esnStr = esn.getEsn();
		String esnStr = partNumber;
		esn.setEmail(randomEmail);
		esn.setPassword("tracfone"); 
		System.out.println("real ESN: " + partNumber);
		
		myAccountFlow.typeInTextField("emailreg", randomEmail);
		myAccountFlow.typeInTextField("confirm_email", randomEmail);
		myAccountFlow.typeInPasswordField("passwordreg", "tracfone");
		myAccountFlow.typeInPasswordField("confirm_password", "tracfone");
		myAccountFlow.typeInPasswordField("sec_ans", "Robert");
		if (partNumber.matches("PH(TF).*")) {
			myAccountFlow.typeInTextField("simNew", simCard);
		}else{
			myAccountFlow.typeInTextField("esn", esnStr);
		}
		myAccountFlow.chooseFromSelect("dob_month", "01");
		myAccountFlow.chooseFromSelect("dob_day", "1");
		myAccountFlow.chooseFromSelect("dob_year", "1982"); 
		myAccountFlow.pressSubmitButton(properties.getString("CREATE")); 
		
		if (!simCard.isEmpty() && !partNumber.matches("PH(TF).*")) {
//			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			String newSim = simCard;
			System.out.println("real SIM: " + simCard);
			myAccountFlow.typeInTextField("iccid", simCard);
			phoneUtil.addSimToEsn(newSim, esn);
		}

		if(!myAccountFlow.textboxVisible("zipCode") && !myAccountFlow.buttonVisible("Cancel")){
			myAccountFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		myAccountFlow.typeInTextField("zipCode", zipCode);
		if (myAccountFlow.buttonVisible(properties.getString("continue"))) {
			myAccountFlow.pressButton(properties.getString("continue"));
		} else {
			myAccountFlow.pressSubmitButton(properties.getString("continue"));
		}
	}

	public void addAPhoneWithInactiveESNStatus(String esnPart) throws Exception {
		
		String esn = phoneUtil.getActiveEsnByPartWithNoAccount(esnPart);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		//esnUtil.setCurrentESN(new ESN(esnPart, esn));
		System.out.println(esn);
		TwistUtils.setDelay(25);
		deactivatePhone.stDeactivateEsn(esn, "PASTDUE");
		myAccountFlow.navigateTo(properties.getString("TF_URL")); //
		/*if (myAccountFlow.linkVisible("Sign Out")) {
			myAccountFlow.clickLink("Sign Out");
		}*/
		loginToMyAccount();		;
		//myAccountFlow.clickLink(properties.getString("addNewPhone"));
		myAccountFlow.clickImage("add_phone.gif");
		System.out.println(esn + " IS ATTACHED TO MIN " + min);
		myAccountFlow.typeInTextField("cb-serial", esn);
		myAccountFlow.pressSubmitButton("CONTINUE");
		if (myAccountFlow.textboxVisible("cb-phone")){
		myAccountFlow.typeInTextField("cb-phone", min);
		myAccountFlow.pressSubmitButton("Add");
		}
		if (myAccountFlow.textboxVisible("verifyPhoneCode")){
			myAccountFlow.typeInTextField("verifyPhoneCode", phoneUtil.getZipcode(esn));
		myAccountFlow.getBrowser().strong("CONTINUE[2]").click();
		}
		String SuccessMsg= "Phone Successfully Added To Account"; 
		Assert.assertTrue(myAccountFlow.divVisible(SuccessMsg));
		///Phone Successfully Added To Account
		//myAccountFlow.typeInTextField("esn1", esn);
		//myAccountFlow.typeInTextField("min1", min);
		//myAccountFlow.pressSubmitButton(properties.getString("add"));
		phoneUtil.clearOTAforEsn(esn);
		TwistUtils.setDelay(10);
	
	}
	
		public void getESNWithNoAccountForPartnumber(String esnPart) throws Exception {
		String esn = phoneUtil.getActiveEsnByPartWithNoAccount(esnPart);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		esnUtil.setCurrentESN(new ESN(esnPart, esn));
		String esnText;
		
		if (myAccountFlow.textboxVisible("esnNew")) {
			esnText = "esnNew";
		} else {
			esnText = ActivationReactivationFlow.ActivationTracfoneWebFields.EsnText.name;
		}
		//activationPhoneFlow.typeInTextField("esnNew", esn.getEsn());
		myAccountFlow.typeInTextField(esnText, esn);
		myAccountFlow.pressButton(properties.getString("continue"));
		submitform("continue1");
        myAccountFlow.clickLink("Go to My Account");
	}

	public void verifyAccountCreation() throws Exception {
		Assert.assertTrue(myAccountFlow.h1Visible("My Account Summary"));
	    if (myAccountFlow.linkVisible(properties.getString("signOut"))) {
		      myAccountFlow.clickLink(properties.getString("signOut"));
	   }
	}
	
	public void delay10Seconds() throws Exception {
		TwistUtils.setDelay(10);
	
	}

	public void loginToExistingAccount() throws Exception {
		String email = "hdycspqp@tracfone.com";
		String pwd = "tracfone";
        // myAccountFlow.navigateTo(properties.getString("TF_URL"));
        // myAccountFlow.clickLink(properties.getString("myAccount"));
        myAccountFlow.pressButton("Login to Existing Account");
        myAccountFlow.typeInTextField("email[1]", email);
        myAccountFlow.typeInPasswordField("password", pwd);
        // myAccountFlow.pressButton("LogIn");
        if (myAccountFlow.submitButtonVisible("Sign In")) {
          	myAccountFlow.pressSubmitButton("Sign In");// login_form_button
         } else {
        	myAccountFlow.pressSubmitButton("login_form_button");
           }
	}

	public void logout() throws Exception {
		myAccountFlow.clickLink("account_logoff");
		//myAccountFlow.getBrowser().link("Sign Out").click();
        myAccountFlow.clickImage("Tracfone");
     }

	public void verifyPhoneNumberIfDeviceIs(String Device) throws Exception {
		if (Device.equalsIgnoreCase("Enroll in Auto Refill")) {
			String esn = esnUtil.getCurrentESN().getEsn();
			String min = phoneUtil.getMinOfActiveEsn(esn);
			myAccountFlow.clickLink(properties.getString("viewPhoneOptions"));
			myAccountFlow.typeInTextField("verify_min1", min);
			myAccountFlow.pressSubmitButton(properties.getString("viewPhoneOptions"));
		} else if (Device.equalsIgnoreCase("In Active")) {
			String esn = esnUtil.getCurrentESN().getEsn();
			deactivatePhone.stDeactivateEsn(esn, "PASTDUE");
			myAccountFlow.navigateTo(properties.getString("TF_URL")); //
			if (myAccountFlow.linkVisible("Sign Out")) {
				myAccountFlow.clickLink("Sign Out");
			}
			loginToMyAccount();
		}
	}

	public void selectRegisterAndEnterInvalidValuesFor(String email, String password, String secPin , String DOB) throws Exception {
		myAccountFlow.pressButton(properties.getString("createaccount"));

		if (email.equalsIgnoreCase("Random")) {
			email = TwistUtils.createRandomEmail();
		}

		if (myAccountFlow.textboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name)) {
			myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.FirstNameText.name, "TwistFirstName");
			myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.LastNameText.name, "TwistLastName");
		}

		if (!DOB.isEmpty()) {
			myAccountFlow.clickSpanMessage("dob_icon");
			myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobDaySelect.name, "01");
			myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobMonthSelect.name, "01");
			myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.DobYearSelect.name, "1982");
			myAccountFlow.pressButton(properties.getString("add"));
		}
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EmailText.name, email);
		if (myAccountFlow.textboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name)) {
			myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ConfirmEmailText.name, email);
		}
		myAccountFlow.typeInPasswordField("password_reg", password);
		if (myAccountFlow.selectionboxVisible(MyAccountFlow.MyAccountTracfoneWebFields.SecurityQues.name)) {
			myAccountFlow.chooseFromSelect(MyAccountFlow.MyAccountTracfoneWebFields.SecurityQues.name, "What is your father's middle name");
			myAccountFlow.typeInPasswordField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityAswerPswd.name, "Robert");
		}
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.SecurityPin.name, secPin);
		if (myAccountFlow.buttonVisible("Create")) {
			myAccountFlow.pressButton("Create");
		} else {
			myAccountFlow.pressSubmitButton("Create");
		}

		checksforCreateAccount(email, password, secPin, DOB);
	}

	private void checksforCreateAccount(String email, String password, String secPin, String DOB) {
		if (!secPin.matches("\\d{4}")) {
			Assert.assertTrue(myAccountFlow.getBrowser().div("error").text().contains("A valid Security PIN is required")
					|| myAccountFlow.getBrowser().div("error").text().contains("Security PIN is required"));
		}

		String splChrs = "-/@#$%^&_+=()";
		if (password.length() < 6 || password.matches("[" + splChrs + "]+")) {
			Assert.assertTrue(myAccountFlow.getBrowser().div("error").text().contains("Please enter Your Password.")
					|| myAccountFlow.getBrowser().div("error").text().contains("Your password must be at least 6 characters and free of special characters (&%$#@...)"));
		}
		if (!email.matches("(?i:^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$)")) {
			Assert.assertTrue(myAccountFlow.getBrowser().div("error").text().contains("Please enter Email")
					|| myAccountFlow.getBrowser().div("error").text().contains("you have entered an incorrect Username/Email"));
		}
		if (DOB.isEmpty()) {
			Assert.assertTrue(myAccountFlow.getBrowser().div("error").text().contains("Date of Birth is required"));
		}
	}

	public void enterMinToCreateAccount() throws Exception {
		myAccountFlow.clickLink("My Account");
		TwistUtils.setDelay(15);
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		myAccountFlow.typeInTextField("user_min", min);
		myAccountFlow.pressButton("Create Account[1]");
	}

}