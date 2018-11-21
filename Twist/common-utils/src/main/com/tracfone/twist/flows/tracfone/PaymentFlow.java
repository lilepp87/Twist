package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class PaymentFlow extends Flow {

	public PaymentFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum PurchaseAirtimeWebcsrFields {
		PurchaseAirtimeButton("Purchase Airtime"),
		AddCardButton("Add Card"),
		FirstNameText("txt_FName"),
		LastNameText("txt_LName"),
		PhoneNumberText("txt_phone"),
		EmailText("txt_email"),
		AccountNumberText("account_number"),
		CreditCardTypeSelect("lst_credCard"),
		ExpMonthSelect("lst_expMonth"),
		ExpYearSelect("lst_expYear"),
		SaveButton("Save"),
		CreditCardNumberSelect("lst_credCard"),
		CvvText("txt_cvv"),
		QuantityText("txt_quantity0"),
		CalcTotalButton("Calc Total"),
		PurchaseButton("Purchase"),
		NorefundsMessage("We do not issue refunds for airtime purchases.  Do you accept the total charge of 130.43 to be processed on this credit card?");

		public final String name;

		private PurchaseAirtimeWebcsrFields(String id) { this.name = id; }
	}

	public static enum CreditCardPaymentWebcsrFields {
		PaymentSourceText("paymentsrcname"),
		ExpMonthSelect("month"),
		ExpYearSelect("year"),
		AccountNumberText("account_number"),
		AccountTypeSelect("accounttype"),
		CvvText("cvvnumber"),
		NpaText("txtNpa"),
		NxxText("txtNxx"),
		PhoneText("txtPh");

		public final String name;

		private CreditCardPaymentWebcsrFields(String id) { this.name = id; }
	}

	public static enum BuyAirtimeTracfoneWebFields {
		//Monthly Plans
		Link50MinutesPlan("5800020:"),
		Link125MinutesPlan("5800561:"),
		Link200MinutesPlan("5800562:"),
		Link50MinutesFamilyPlan("5800005:"),
		AdditionalPhonesPlanLink("Select[4]"),
		ServiceProtectionPlanLink("5800100:"),
		FamilyPlanLink("5800005:"),
		EnrollButton("Enroll"),

		BuyOnlineLink("Buy Online"),
		EsnText("min_or_esn"),
		CheckOutButton("Continue to Checkout"),
		BuyAirtimeMessage("THANK YOU FOR BUYING AIRTIME!"),
		BuyAirtimeMessage1("THANK YOU FOR ADDING AIRTIME WITH US!"),
		BuyPlanMessage("THANK YOU FOR YOUR PURCHASE!"),
		ContinueButton("Continue"),
		QuantityLink("Add[7]"),
		GoButton("GO"),
		GoMontlyPlanButton("GO[1]"),
		SkipButton("Skip"),

		CloseAlertMessage("close");

		public final String name;

		private BuyAirtimeTracfoneWebFields(String id) { this.name = id; }
	}


	public static enum BuyAirtimeNet10EWebFields {
		//Monthly Plans
		Link750MinutesMonthlyPlan("qty_0"),
		UnlimitedMonthlyPlanLink("qty_1"),
		UnlimitedInternationalPlan("qty_2"),
		Link900MinutesPlan("qty_3"),
		Link600MinutesPlan("qty_4"),
		Link300MinutesPlan("qty_5"),
		Link1500MinutesPlan("qty_6"),
		EnrollButton("Enroll"),
		Net10MonthlyPlansLink("NET10 Monthly Plans"),
		LearnMoreLink("Learn More"),
		SetUpAutoRefillLink("SET UP AUTO REFILL NOW"),
		AddAMinutesBundleButton("ADD A MINUTES BUNDLE!"),
		MyAccountRegisterLink("My Account / Register"),
		YesButton("Yes"),

		BuyAirtimeLink("Add or Buy Airtime"),
		EsnText("esn"),
		CheckOutButton("Continue to Checkout"),
		BuyAirtimeMessage("THANK YOU FOR BUYING AIRTIME!"),
		BuyAirtimeMessage1("THANK YOU FOR ADDING AIRTIME WITH US!"),
		ContinueButton("Continue"),
		NewContinueButton("CONTINUE"),
		QuantityLink("Add[3]"),
		GoButton("GO"),
		GoMontlyPlanButton("GO[1]"),
		SkipButton("Skip");

		public final String name;

		private BuyAirtimeNet10EWebFields(String id) { this.name = id; }
	}

	public static enum BuyAirtimeNet10SWebFields {
		//Monthly Plans
		Link750MinutesMonthlyPlan("qty_0"),
		UnlimitedMonthlyPlanLink("qty_1"),
		UnlimitedInternationalPlan("qty_2"),
		Link900MinutesPlan("qty_3"),
		Link600MinutesPlan("qty_4"),
		Link300MinutesPlan("qty_5"),
		Link1500MinutesPlan("qty_6"),
		EnrollButton("Enroll"),
		Net10MonthlyPlansLink("Inscribirse en Planes Mensuales"),
		LearnMoreLink("Learn More"),
		SetUpAutoRefillLink("SET UP AUTO REFILL NOW"),
		AddAMinutesBundleButton("ADD A MINUTES BUNDLE!"),
		MyAccountRegisterLink("Acceder Cuenta / Registrarse"),
		YesButton("SI"),


		BuyAirtimeLink("Comprar Minutos"),
		EsnText("esn"),
		CheckOutButton("Continue to Checkout"),
		BuyAirtimeMessage("iGRACIAS POR COMPRAR MINUTOS CON NOSOTROS!"),
		ContinueButton("Continuar"),
		QuantityLink("Add[3]"),
		GoButton("IR"),
		GoMontlyPlanButton("GO[1]"),
		SkipButton("Omitir");

		public final String name;

		private BuyAirtimeNet10SWebFields(String id) { this.name = id; }
	}

	public static enum CreditCardPaymentTracfoneWebFields {
		PaymentSrcNameText("payment_src_name"),
		ExpMonthSelect("credit_expiry_mo"),
		ExpYearSelect("credit_expiry_yr"),
		AccountNumberText("account_number"),
		//AccountTypeSelect("account_type"),
		AccountTypeSelect("cc_account_type"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		Address1Text("address1"),
		CityText("city"),
		ZipCodeText("zip"),
		StateSelect("state"),
		PhoneText("phone"),
		DefaultBillingCheck("defaultbilling"),
		CvvText("cvvnumber");

		public final String name;

		private CreditCardPaymentTracfoneWebFields(String id) { this.name = id; }
	}

	public static enum CreditCardPaymentNet10EWebFields {
		PaymentSrcNameText("payment_src_name"),
		ExpMonthSelect("credit_expiry_mo"),
		ExpYearSelect("credit_expiry_yr"),
		AccountNumberText("account_number"),
		AccountTypeSelect("account_type"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		Address1Text("address1"),
		CityText("city"),
		ZipCodeText("zip"),
		StateSelect("state"),
		PhoneText("phone"),
		DefaultBillingCheck("defaultbilling"),
		CvvText("cvvnumber");

		public final String name;

		private CreditCardPaymentNet10EWebFields(String id) { this.name = id; }
	}

	public static enum CreditCardPaymentStraighttalkWebcsrFields {
		PaymentSrcNameText("payment_src_name"),
		ExpMonthSelect("credit_expiry_mo"),
		ExpYearSelect("credit_expiry_yr"),
		AccountNumberText("account_number"),
		AccountTypeSelect("account_type"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		Address1Text("address1"),
		CityText("city"),
		ZipCodeText("zip"),
		StateSelect("state"),
		CvvText("cvvnumber");


		public final String name;

		private CreditCardPaymentStraighttalkWebcsrFields(String id) { this.name = id; }
	}

	public static enum CreditCardPaymentStraighttalkWebFields {
		PaymentSrcNameText("payment_src_name"),
		ExpMonthSelect1("expMonth"),
		ExpMonthSelect("credit_expiry_mo"),
		ExpYearSelect1("expYear"),
		ExpYearSelect("credit_expiry_yr"),
		AccountNumberText("account_number"),
		AccountTypeSelect("account_type"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		Address1Text("address1"),
		CityText("input_city"),
		ZipCodeText("input_zip_code"),
		StateSelect("state"),
		CvvText("manage_cvvnumber"), CvvText1("cvv");
		//CvvText("cvv");

		public final String name;

		private CreditCardPaymentStraighttalkWebFields(String id) { this.name = id; }
	}

	
	public static enum CreditCardPaymentSimpleMobileWebFields {
		PaymentSrcNameText("payment_src_name"),
		ExpMonthSelect("credit_expiry_mo"),
		ExpYearSelect("credit_expiry_yr"),
		AccountNumberText("account_number"),
		AccountTypeSelect("account_type"),
		FirstNameText("first_name"),
		LastNameText("last_name"),
		Address1Text("address1"),
		CityText("input_city"),
		ZipCodeText("input_zip_code"),
		StateSelect("state"),
		CvvText("cvvnumber");

		public final String name;

		private CreditCardPaymentSimpleMobileWebFields(String id) { this.name = id; }
	}
	
	public static enum BuyAirtimeStraighttalkWebcsrFields {
		BuyAirtimeOption("radAction[2]"),
		NewCallerButton("New Caller"),
		EsnText("txtEsn"),
		SearchButton("Search"),
		SearchByEsnButton("Search[2]"),
		ValidResponseButton("Valid"),
		BuyServicePlanLink("Buy a Service Plan"),
		MinPinOption("radAction2"),
		Next2Button("   Next   "),

		//Service Plans
		Plan3MonthsUnlimitedRadio("service_plans_id_selected"),
		Plan6MonthsUnlimitedRadio("service_plans_id_selected[1]"),
		Plan1YearUnlimitedRadio("service_plans_id_selected[2]"),
		UnlimitedPlanRadio("service_plans_id_selected[3]"),
		AllYouNeedPlanRadio("service_plans_id_selected[4]"),
		ServicePlanRadio("service_plans_id_selected"),

		UseAccountInformationCheck("ckbox"),
		TermsCheck("terms"),
		SubmitButton("Submit"),
		NextButton("Next"),
		ContinueButton("Continue"),
		AddToReserveButton("Add to Reserve"),
		OrderSummaryMessage("ORDER SUMMARY (Give ONLY if customer requests)"),
		AddNowButton("Add Now"),
		OneTimePurchase("One-Time Purchase");

		public final String name;

		private BuyAirtimeStraighttalkWebcsrFields(String id) { this.name = id; }
	}

	public static enum BuyAirtimeStraighttalkWebFields {
		BuyServicePlanLink("Buy a Service Plan"),
		AddServicePlanReserveLink("Add a Service Plan"),
		AddServicePlanNowLink("Add a Service Plan"),
		ReturnToMyAccountLink("Return to Account Summary"),
		ProtectYourPlanLink("Protect your Phone"),
		
		//Add Plan Now
		AirtimePinText("new_airtime_pin"),
		GoSubmit("Go"),
		AddNowSubmit("Add Now"),
		//Add Plan Reserve
		AirtimePinQueueText("new_airtime_pin"),
		GoQueueSubmit("Go"),
		AddReserveSubmit("Add to Reserve"),

		//Service Plans
		Plan1YearUnlimitedRadio("[0]"),
		Plan6MonthsUnlimitedRadio("[1]"),
		Plan3MonthsUnlimitedRadio("[2]"),
		
		UnlimitedPlan("[0]"),
		Unlimited55Plan("[1]"),
		UnlimitedInternationalPlan("[2]"),
		AllYouNeedPlanRadio("[3]"),
		UnlimitedPlanMonthly("buy_21"),
		UnlimitedInternational("buy_214"),
		Plan3MonthsUnlimited("buy_83"),
		Plan6MonthsUnlimited("buy_84"),
		Plan1YearUnlimited("buy_85"),
		UnlimitedPlanFortyfiveRadio("[4]"),
		
		//ACME Service Plan
		AcmeUnlimitedPlanRadio("[1]"),
		AcmeUnlimitedIntRadio("[0]"),
		
		//Hotspot
		HotSpot7GBRadio("[4]"),
		HotSpot5GBRadio("[3]"),
		HotSpot4GBRadio("[2]"),
		HotSpot2GBRadio("[1]"),
		HotSpot1GBRadio("[0]"),
		HotSpot7GB("buy_259"),
		HotSpot5GB("buy_258"),
		HotSpot4GB("buy_257"),
		HotSpot2GB("buy_256"),
		HotSpot1GB("buy_255"),
		
		//Byot
		Byot7GBRadio("[4]"),
		Byot5GBRadio("[3]"),
		Byot4GBRadio("[2]"),
		Byot2GBRadio("[1]"),
		Byot1GBRadio("[0]"),
		Byot7GB("buy_292"),
		Byot5GB("buy_291"),
		Byot4GB("buy_290"),
		Byot2GB("buy_289"),
		Byot1GB("buy_288"),
		
		//Home Center with wifi
		HomeCenter60Radio("[2]"),
		HomeCenter40Radio("[1]"),
		HomeCenter30Radio("[0]"),
		HomeCenter40("buy_362"),
		HomeCenter30("buy_361"),
		HomeCenter60("buy_363"),
		//Data ADD ON
		DataAddON5("[6]"),
		DataAddON10("[7]"),		
		//HomePhone
		HomePhone30Radio("[1]"),
		HomePhone15Radio("[0]"),
		
		RemoteAlertMonthlyPlanRadio("[0]"),
		RemoteAlertAnnualPlanRadio("[1]"),
		RemoteAlertMonthlyPlan("buy_277"),
		RemoteAlertAnnualPlan("buy_278"),
		
		CarConnectionMonthlyPlanRadio("[1]"),
		CarConnectionAnnualPlanRadio("[0]"),
		CarConnectionMonthlyPlan("buy_293"),
		CarConnectionAnnualPlan("buy_294"),
		
		AutoRefillRadio("isRecurring"),
		RefillOnDemandRadio("isRecurring[1]"),

		UseAccountInformationCheck("ckbox"),
		TermsCheck("terms"),
		SubmitButton("Submit"),
		NextButton("Next"),
		AddToReserveButton("Straight Talk Reserve ™"),
		ContinueButton("Continue"),
		OrderSummaryMessage("ORDER SUMMARY"),
		AddNowButton("Add Now"), 
		OneTimePurchase("One-Time Purchase");

		public final String name;

		private BuyAirtimeStraighttalkWebFields(String id) { this.name = id; }
	}

	
	public static enum BuyAirtimeSimpleMobileWebFields {
		BuyServicePlanLink("Buy a Service Plan"),
		AddServicePlanReserveLink("Add a Service Plan to RESERVE"),
		AddServicePlanNowLink("Add a Service Plan NOW"),
		ReturnToMyAccountLink("Return to Account Summary"),
		
		//Add Plan Now
		AirtimePinText("new_airtime_pin"),
		GoSubmit("Go"),
		AddNowSubmit("Add Now"),
		//Add Plan Reserve
		AirtimePinQueueText("new_airtime_pin"),
		GoQueueSubmit("Go"),
		AddReserveSubmit("Add to Reserve"),

		//Service Plans
		Plan1YearUnlimitedRadio("service_plans_id_selected"),
		Plan6MonthsUnlimitedRadio("service_plans_id_selected[1]"),
		Plan3MonthsUnlimitedRadio("service_plans_id_selected[2]"),
		
		UnlimitedInternationalRadio("service_plans_id_selected[3]"),
		UnlimitedPlanRadio("service_plans_id_selected[4]"),
		AllYouNeedPlanRadio("service_plans_id_selected[5]"),
		
		
		
		//ACME Service Plan
		AcmeUnlimitedPlanRadio("21"),
		AcmeUnlimitedIntRadio("213"),
		
		AutoRefillRadio("isRecurring"),
		RefillOnDemandRadio("isRecurring[1]"),

		UseAccountInformationCheck("ckbox"),
		TermsCheck("terms"),
		SubmitButton("Submit"),
		NextButton("Next"),
		AddToReserveButton("Straight Talk Reserve ™"),
		ContinueButton("Continue"),
		OrderSummaryMessage("ORDER SUMMARY"),
		AddNowButton("Add Now");

		public final String name;

		private BuyAirtimeSimpleMobileWebFields(String id) { this.name = id; }
	}

}