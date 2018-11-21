package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class MyAccountFlow extends Flow {

	public MyAccountFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum MyAccountWEBCSRFields {
		MyAccountOption("radAction[11]"),
		ExistingAccountOption("radResponse[1]"),
		NewCallerButton("New Caller"),
		FirstNameText("txtFName"),
		LastNameText("txtLName"),
		ZipCodeText("txtZIP"),
		PinText("txtPIN"),
		Esn("txtESN"),
		NextButton("Next"),
		EmailText("txtEmail1"),
		Email2Text("txtEmail2"),
		Email3Text("txtEmail3"),
		MonthText("txtMonth"),
		DateText("txtDate"),
		YearText("txtYear"),
		StartRecordingCheckBox("startRecording"),
		StopRecordingCheckBox("stopRecording"),
		NpaText("txtNpa"),
		NxxText("txtNxx"),
		PhoneText("txtPh"),
		SecurityQuestionSelect("selSecurityQstn"),
		SecurityAnswerText("txtSecurityAns"),
		SaveButton("   Save   "),
		Answer1Radio("radSendSMS[1]"),
		Answer2Radio("radResponse[1]"),
		CellText("Account Summary"),
		LogOutLink("Log Out"),
		LogOutConfirmMessage("Are you sure you want to Log Out?"),
		Continue2Button(" Continue "),
		AcceptRadio("acceptSubProcess[1]"),
		NoInteractionRadio("radAction[1]"),
		ContinueButton("Continue");

		public final String name;

		private MyAccountWEBCSRFields(String id) {
			this.name = id;
		}

	}

	public static enum ValuePlansWEBCSRFields {
		// Enroll in Value Plan
		EnrollInValuePlanRadio("radSendSMS[1]"),
		IsStraighttalkRadio("scriptQuestion[1]"),
		SelectESNCheckBox("chkESN"),
		SelectPlansLink("SelectPrograms"),

		// Enroll in Value Plan for Net10
		AcceptEnrollmentRadio("YN10[1]");

		public final String name;

		private ValuePlansWEBCSRFields(String id) {
			this.name = id;
		}
	}

	public static enum BuyNowWEBCSRFields {
		// Register in Buy Now
		AccountServicesTabImage("tab_account_services.jpg"),
		RegisterInBuyNowRadio("radSelect[3]"),
		AgreeButton(" Agree "),
		NewPaymentSourceRadio("paySource"),
		EsnRadio("chkESN"),
		CvvText("cvvNum"),
		PinNumberText("pinNumber");

		public final String name;

		private BuyNowWEBCSRFields(String id) {
			this.name = id;
		}
	}

	public static enum AccountProfileWEBCSRFields {
		// Remove ESN from my Account
		RemoveEsnFromMyAccountRadio("radActProfile[8]"),

		// Transfer ESN
		TransferEsnRadio("radActProfile[10]"),
		NewAccountRadio("radSelect[1]"),
		ApproveTransferRadio("radSelect[2]"),
		EsnCheckBox("checkESN"),

		// Add ESN to my Account
		AddEsnToMyAccountRadio("radActProfile[7]"),
		AccountProfileTabImage("tab_account_profile.jpg"),
		AddEsnButton("Add ESN"),
		NickNameText("nickName"),
		AccountEsnText("accEsn"),
		EsnAddedMessage("has successfully been added to your account");

		public final String name;

		private AccountProfileWEBCSRFields(String id) {
			this.name = id;
		}
	}

	public static enum MyAccountTracfoneWebFields {
		// Survery Aswers
		Answer1Radio("answer1"),
		Answer2Radio("answer2"),
		Answer3Radio("answer3"),

		MyAccountLink("My Account"),
		RegisterLink("Register"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		DobMonthSelect("dob_month"),
		DobDaySelect("dob_day"),
		DobYearSelect("dob_year"),
		Address1Text("address1"),
		ZipCodeText("zip"),
		simCardText("sim"),
		EmailText("email_reg"),
		//ConfirmEmailText("confirm_email"),
		ConfirmEmailText("confirm_email_reg"),
		PasswordPswd("password_reg"),
		ConfirmPasswordPswd("confirm_password"),
		SecurityPin("security_pin"),
		SecurityQues("sec_qstn"),
		SecurityAswerPswd("sec_ans"),
		ContinueButton("Continue"),
		SignOutLink("Sign Out"),
		EsnText("esn1"),
		NickNameText("nickname1"),
		NextButton("Next"),
		AddButton("Add"),
		AddPhoneLink("Add New Phone"),
        Create("Create"),
		AddNewPhoneMessage("Phone Successfully Added To Account"),
		SubmitButton("Submit");

		public final String name;

		private MyAccountTracfoneWebFields(String id) {
			this.name = id;
		}

	}

	public static enum MyAccountNet10SWebFields {
		// Survery Aswers
		Answer1Radio("answer1"),
		Answer2Radio("answer2"),
		Answer3Radio("answer3"),

		MyAccountLink("Mi Cuenta"),
		MyAccountRegisterLink("Acceder a su cuenta / Registrarse ahora"),
		RegisterLink("INSCRIBIRSE"),
		EnrollInEasyMinutesLink("Inscríbase en Easy Minutes"),
		Net10EMonthlyPlansLink("NET10 Monthly Plans"),
		EnrollInMonthlyPlan("Inscrï¿½base en Auto Refill"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		DobMonthSelect("dob_month"),
		DobDaySelect("dob_day"),
		DobYearSelect("dob_year"),
		Address1Text("address1"),
		ZipCodeText("zip"),
		simCardText("sim"),
		EmailText("email"),
		ConfirmEmailText("confirm_email"),
		PasswordPswd("password"),
		ConfirmPasswordPswd("confirm_password"),
		SecurityAswerPswd("sec_ans"),
		ContinueButton("Continuar"),
		Continue2Button("Continuar[1]"),
		SignOutLink("Salir"),
		EsnText("esn1"),
		NickNameText("nickname1"),
		NextButton("Prï¿½ximo"),
		AddButton("Agregar"),
		IrLink("IR"),
		AddPhoneLink("Add New Phone"),
		AddNewPhoneMessage("Teléfono Añadido Exitosamente a Su Cuenta"),
		SubmitButton("Enviar");

		public final String name;

		private MyAccountNet10SWebFields(String id) {
			this.name = id;
		}
	}

	public static enum MyAccountNet10EWebFields {
		// Survery Aswers
		Answer1Radio("answer1"),
		Answer2Radio("answer2"),
		Answer3Radio("answer3"),

		Net10EMonthlyPlansLink("NET10 Monthly Plans[2]"),
		Net10EEasyMinutesLink("Easy Minutes[1]"),

		EnrollInEasyMinutesLink("Enroll in Easy Minutes"),
		EnrollInMonthlyPlan("Enroll in Auto Refill"),
		Continue2Button("Continue[1]"),
		MyAccountRegisterLink("My Account/Register"),
		RegisterLink("REGISTER"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		DobMonthSelect("dob_month"),
		DobDaySelect("dob_day"),
		DobYearSelect("dob_year"),
		Address1Text("address1"),
		ZipCodeText("zip"),
		simCardText("sim"),
		EmailText("email"),
		NewEmailText("new_email"),
		NewAccountRadio("option_account_id[1]"),
		LoginRadio("option_account_id"),
		ConfirmEmailText("confirm_email"),
		PasswordPswd("password"),
		ConfirmPasswordPswd("confirm_password"),
		NewPasswordPswd("new_password"),
		SecurityAswerPswd("sec_ans"),
		SecurityPinText("four_pin"),
		ContinueButton("Continue"),
		ContinueLink("CONTINUE"),
		SignOutLink("Sign Out"),
		EsnText("esn1"),
		NickNameText("nickname1"),
		NextButton("Next"),
		AddButton("Add"),
		AddPhoneLink("Add New Phone"),
		AddNewPhoneMessage("Phone Successfully Added To Account"),
		SubmitButton("Submit"),
		BirthdateText("dateofbirth"),
		DatePickerYearDrop("ui-datepicker-year"),
		DatePickerMonthDrop("ui-datepicker-month"),
		CreateAccountSubmit("create_button_selection"),
		SkipLink("SKIP"),
		SignInSubmit("SIGN IN");

		public final String name;

		private MyAccountNet10EWebFields(String id) {
			this.name = id;
		}
	}

}