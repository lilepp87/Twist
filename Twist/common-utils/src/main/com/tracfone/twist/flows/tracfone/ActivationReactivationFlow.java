package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class ActivationReactivationFlow extends Flow {

	public ActivationReactivationFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum ActivationWebcsrFields {
		NewCallerButton("New Caller"),
		Esn("esn"),
		NextButton("Next"),
		ZipCode("code"),
		NewCustomer("newCustomer"),
		GenerateCodesButton("Generate Codes"),
		EmailAddress("emailAddress"),
		Next2Button("   Next   "),
		ContinueButton("Continue"),
		Min("txtMIN"),
		SaveButton("   Save   "),
		NameCheckBox("noNameRequired"),
		PhoneCheckBox("noPhoneRequired"),
		SimCardOption("radResponse"),
		SimCardNumberText("iccid"),
		TransactionSummaryMessage("Transaction Summary"),
		CompleteProcessOption("radAction[1]"),
		LogOutLink("Log Out"),
		YesButton("    Yes   "),
		StartRecordingCheck("startRecording"),
		StopRecordingCheck("stopRecording"),
		Phone1Text("phone1"),
		Phone2Text("phone2"),
		Phone3Text("phone3"),
		LogOutConfirmMessage("Are you sure you want to Log Out?");

		public final String name;

		private ActivationWebcsrFields(String id) {
			this.name = id;
		}
	}

	public static enum ActivationTracfoneWebFields {
		ActivateLink("Activate Phone"),
		TransferRadio("Transfer"),
		PortRadio("With_Another"),
		WithUsRadio("With_Us"),

		// Phone Models
		KyoceraLink("KYOCERA"),
		KyoceraImage("Kyocera K126C"),
		LGLink("LG"),
		LG800GImage("LG800G"),
		ContinueImage("Continue"),
		NoThanksButton("No Thanks"),
		NewSimText("simNew"),

		// Phone Upgrade
		NewEsnText("esnNew"),
		NewNickNameText("nickNew"),
		CurrentEsnText("esnCurrentNoAcct"),
		CurrentMinText("currentMIN"),
		TimeTankText("timeTank"),
		ConfirmTimeTankText("confirmTimeTank"),
		TTestText("tTest"),
		ConfirmTTestText("confirmTTest"),

		RegisterButton("REGISTER"),
		SubmitButton("Submit"),
		EmailText("email"),
		PasswordPswd("password"),
		SignInButton("Sign In"),
		ClickHereLink("click here."),
		OtherEsnText("esnOther"),
		EsnText("esnNew"),
		NickNameText("nicknameExis"),
		OtherNickName("nicknameOther"),
		ZipCodeText("zipCode"),
		SimText("iccid"),
		PinCard1Text("redcard1"),
		PinCard2Text("redcard2"),
		PinCard3Text("redcard3"),
		SkipButton("Skip Step"),
		ContinueButton("Continue"),
		ActivationMessage("You have successfully programmed your TracFone."),
		NoThanksLink("No Thanks"),
		AddMinutesLink("Add minutes"),
		BuyOnlineLink("Buy Online"),
		SignOut2Link("Sign Out"),
		SignOutLink("Sign Out TwistFirst TwistLast");

		public final String name;

		private ActivationTracfoneWebFields(String id) {
			this.name = id;
		}
	}

	public static enum ActivationStraighttalkWebcsrFields {
		// Phone Upgrade
		NewEsnText("new_esn"),
		NickNameText("new_phone_nickname"),
		TimeTankVoiceText("time_tank_voice_value"),
		ConfirmTimeTankVoiceText("time_tank_voice_confirm"),
		TimeTankSeqText("time_tank_seq_value"),
		ConfirmTimeTankSeqText("time_tank_seq_confirm"),
		TimeTankMinutesText("time_tank_minutes_when_phone_not_available"),
		MessagesText("time_tank_mt_value"),
		DataText("time_tank_dt_value"),
		OrderSummaryMessage("ORDER SUMMARY (Give ONLY if customer requests)"),

		NewCallerButton("New Caller"),
		PhoneUgradeOption("radAction[12]"),
		NoExistingAccountButton("No"),
		ContinueButton("Continue"),
		NextButton("Next"),
		Next2Button("   Next   "),
		InteractionRadio("radAction2"),
		EsnText("userinputesn"),
		ZipCodeText("userinputzipcode"),
		PinCardText("userredcards1"),
		DobDaySelect("dob_day"),
		DobMonthSelect("dob_month"),
		DobYearSelect("dob_year"),
		EmailText("email"),
		SimCardText("sim"),
		EnrollButton("Enroll Now"),
		TermsCheckBox("terms"),
		AutoRefillMessage("Thank you for setting up Auto-Refill!"),
		SearchEsnText("txtEsn"),
		SearchButton("Search"),
		ValidResponseButton("Valid"),
		SearchByEsnButton("Search[2]"),
		SubmitButton("Submit");

		public final String name;

		private ActivationStraighttalkWebcsrFields(String id) {
			this.name = id;
		}
	}

	public static enum ActivationStraighttalkWebFields {
		ActivateReactivateLink("Activate/Reactivate"),
		TransferMyNumberRadio("activation_option[2]"),
		TargetEsnText("new_esn"),
		TargetSimText("new_phone_sim"),
		TargetNickName("new_phone_nickname"),
		ContinueButton("Continue"),
		CreateAccountButton("create_acct_continue_btn"),
		OrderSummaryText("ORDER SUMMARY"),
		SubmitButton("Submit"),
		EsnText("input_esn"),
		ZipCodeText("input_zip"),
		AddressZipCodeText("input_zip_code"),
		SimCardText("input_sim"),
		PinCardText("service_card_input"),
		FirstNameText("input_first_name"),
		LastNameText("input_last_name"),
		DobDaySelect("input_dob_day"),
		DobMonthSelect("input_dob_month"),
		DobYearSelect("input_dob_year"),
		Address1Text("input_address1"),
		EmailText("input_email"),
		ConfirmEmailText("input_confirm_email"),
		PasswordPswd("input_password"),
		ConfirmPasswordPswd("input_confirm_password"),
		SecurityPinText("input_pin"),
		SecurityAswerText("input_security_question_answer"),
		NickNameText("input_nickname"),
		NoThanksSubmit("No Thanks"),
		AutoRefillMessage("Thank you for setting up Auto-Refill!"),

		EnrollButton("Enroll Now"),
		DoneButton("Done"),
		TermsCheckBox("terms"),
		UseAccountInfoCheck("ckbox"),
		SignOutImage("Signout");

		public final String name;

		private ActivationStraighttalkWebFields(String id) {
			this.name = id;
		}
	}

	public static enum ActivationNet10EWebFields {
		ActivateLink("Activate/ Reactivate"),
		TransferRadio("Transfer"),
		ActivateRadio("optionsRadios"),
		WithUsRadio("With_Us"),
		ContinueImage("Continue"),
		NoThanksButton("No Thanks"),
		NewAccountRadio("option_account_id[1]"),

		// Phone Models
		KyoceraLink("KYOCERA"),
		KyoceraImage("Kyocera K126C"),
		LGLink("LG"),
		LG800GImage("LG800G"),

		// Phone Upgrade
		NewEsnText("esnNew"),
		NewNickNameText("nickNew"),
		CurrentEsnText("esnCurrentNoEsn"),
		CurrentMinText("minCurrentNoEsn"),
		TimeTankText("timeTank"),
		ConfirmTimeTankText("confirmTimeTank"),
		TTestText("tTest"),
		ConfirmTTestText("confirmTTest"),

		RegisterButton("REGISTER"),
		SubmitButton("Submit"),
		EmailText("email"),
		PasswordPswd("password"),
		SignInButton("Sign In"),
		ClickHereLink("click here."),
		OtherEsnText("esnOther"),
		EsnText("userinputesn"),
		NickNameText("nicknameExis"),
		OtherNickName("nicknameOther"),
		// ZipCodeText("zipCode"),
		ZipCodeText("userinputzipcode"),
		SimText("userinputsim"),
		OnlyActivateRadio("payment_option[2]"),
		BuyPinActivateRadio("payment_option[1]"),
		OnlyActivateLabel("I'm all set, thanks"),
		PinForActivateRadio("payment_option"),
		NewSimText("simNew"),
		PinCard1Text("redcard1"),
		PinCardText("userredcards1"),
		SkipButton("Skip"),
		SkipLink("SKIP"),
		ContinueButton("Continue"),
		ContinueLink("CONTINUE"),
		Continue1Submit("CONTINUE[1]"),
		Continue2Submit("CONTINUE[2]"),
		SelectServiceButton("service_plan_button_selection"),
		FinishButton("FINISHED"),
		ActivationMessage("You have successfully programmed your phone."),
		NoThanksLink("No Thanks"),
		AddMinutesLink("Add minutes"),
		BuyOnlineLink("Buy Online"),
		BuyAirtimeLink("Buy Airtime"),
		BuyAirtime2Link("Buy Airtime Online"),
		SignOut2Link("Sign Out"),
		SignOutLink("Sign Out TwistFirst TwistLast");

		public final String name;

		private ActivationNet10EWebFields(String id) {
			this.name = id;
		}
	}

	public static enum ActivationNet10SWebFields {
		ActivateLink("Activar / Reactivar Teléfono[1]"),
		TransferRadio("Transfer"),
		WithUsRadio("With_Us"),
		ContinueImage("Continue"),
		ContinuarImage("Continuar"),
		NoThanksButton("No Thanks"),

		// Phone Models
		KyoceraLink("KYOCERA"),
		KyoceraImage("Kyocera K126C"),
		LGLink("LG"),
		LG800GImage("LG800G"),

		// Phone Upgrade
		NewEsnText("esnNew"),
		NewNickNameText("nickNew"),
		CurrentEsnText("esnCurrentNoEsn"),
		CurrentMinText("minCurrentNoEsn"),
		TimeTankText("timeTank"),
		ConfirmTimeTankText("confirmTimeTank"),
		TTestText("tTest"),
		ConfirmTTestText("confirmTTest"),

		RegisterButton("INSCRIBIRSE"),
		SubmitButton("Enviar"),
		EmailText("email"),
		PasswordPswd("password"),
		SignInButton("Sign In"),
		ClickHereLink("click here."),
		OtherEsnText("esnOther"),
		EsnText("esnExis"),
		NickNameText("nicknameExis"),
		OtherNickName("nicknameOther"),
		ZipCodeText("zipCode"),
		SimText("iccid"),
		NewSimText("simNew"),
		PinCard1Text("redcard1"),
		SkipButton("Saltar Este Paso"),
		ContinueButton("Continuar"),
		ActivationMessage("Usted ha exitosamente programado su teléfono Net10"),
		NoThanksLink("No Thanks"),
		AddMinutesLink("Añadir Tiempo Celular"),
		BuyOnlineLink("Buy Online"),
		BuyAirtimeLink("Buy Airtime"),
		BuyAirtime2Link("Comprar Minutos en la Página Web"),
		SignOut2Link("Salir"),
		SignOutLink("Salir TwistFirst TwistLast");

		public final String name;

		private ActivationNet10SWebFields(String id) {
			this.name = id;
		}
	}

}