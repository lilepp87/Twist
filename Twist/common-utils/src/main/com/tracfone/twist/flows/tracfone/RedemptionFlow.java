package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class RedemptionFlow extends Flow {

	public RedemptionFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum RedemptionWebcsrFields {
		RedemptionOption("radAction[1]"),
		NewCallerButton("New Caller"),
		Esn("esn"),
		CustomerFailedOnHandsetCheckBox("redemptionAssessment[4]"),
		RegularRedemptionButton("Regular Redemption"),
		NextButton("Next"),
		SaveButton("   Save   "),
		GenerateCodesButton("Generate Codes"),
		LogOutLink("Log Out"),
		YesButton("    Yes   "),
		LogOutConfirmMessage("Are you sure you want to Log Out?"),

		// Redemption with PIN number
		PinText("pins[0]}"),
		PinNumberText("pin1"),
		MinNumberText("textMIN"),
		AddAirtimeButton("Add Airtime"),
		PurchaseAirtimeButton("Purchase Airtime"),
		TransactionSummaryMessage("Transaction Summary");

		public final String name;

		private RedemptionWebcsrFields(String id) {
			this.name = id;
		}
	}

	public static enum RedemptionTracfoneWebFields {
		PinText("input_pin1"),
		PhoneText("input_min"),
		PromoCodeText("input_promo_code"),
		SubmitButton("default_submit_btn"),
		AddOnlineCardLink("Add Card Online"),
		ContinueButton("Continue"),
		AddAirtimeMessage("THANK YOU FOR ADDING AIRTIME WITH US!");
		public final String name;

		private RedemptionTracfoneWebFields(String id) {
			this.name = id;
		}
	}

	public static enum RedemptionNet10EWebFields {
		PinText("input_pin1"),
		PhoneText("input_min"),
		PromoCodeText("input_promo_code"),
		SubmitButton("default_submit_btn"),
		Submit2Button("Submit"),
		AddAirtimeLink("Add Airtime"),
		AddOnlineCardLink("Add Card Online"),
		ContinueButton("Continue"),
		GoButton("GO"),
		AddAirtimeMessage("THANK YOU FOR ADDING AIRTIME WITH US!"),
		YesSubmit("Yes");

		public final String name;

		private RedemptionNet10EWebFields(String id) {
			this.name = id;
		}
	}

	public static enum RedemptionNet10SWebFields {
		PinText("input_pin1"),
		PhoneText("input_min"),
		PromoCodeText("input_promo_code"),
		SubmitButton("default_submit_btn"),
		Submit2Button("Enviar"),
		AddAirtimeLink("Añadir Tiempo Celular"),
		// AddOnlineCardLink("A�ADA MINUTOS EN EL INTERNET"),
		ContinueButton("Continuar"),
		GoButton("IR"),
		AddAirtimeMessage("iGRACIAS POR AÑADIR MINUTOS CON NOSOTROS!");

		public final String name;

		private RedemptionNet10SWebFields(String id) {
			this.name = id;
		}
	}

	public static enum RedemptionStraighttalkWebcsrFields {
		NewCallerButton("New Caller"),
		MinNumberText("mobilenmbr"),
		PinNumberText("airtime_pin"),
		AddButton("Add"),
		AddToSTReserveButton("Add to Straight Talk Reserve�"),
		OrderSummaryMessage("ORDER SUMMARY (Give ONLY if customer requests)"),
		ContinueButton("Continue"),
		AddAirtimeOptionST("radAction[1]");

		public final String name;

		private RedemptionStraighttalkWebcsrFields(String id) {
			this.name = id;
		}
	}

	public static enum RedemptionStraighttalkWebFields {
		RefillOption("Refill Now"),
		MinNumberText("inputMin"),
		MinPurchaseText("minInputRefill"),
		PinNumberText("inputAirtimePin"),
		ContinueButton("Continue"),
		AddNowButton("Add Now"),
		AddToReserveButton("Add to Straight Talk Reserve�"),
		OrderSummaryMessage("ORDER SUMMARY"),
		DoneButton("Done");

		public final String name;

		private RedemptionStraighttalkWebFields(String id) {
			this.name = id;
		}
	}

}