package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class DeactivationPhoneFlow extends Flow {

	public DeactivationPhoneFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum DeactivationWebcsrFields {
		DeactivationOption("radAction[2]"),
		DeactivationOptionST("radAction[3]"),
		NewCallerButton("New Caller"),
		Esn("txtESN"),
		NextButton("Next"),
		ContinueButton("Continue"),
		Min("txtMIN"),
		YesButton("Yes"),
		NoOption("radResponse[1]"),
		ReasonOption("strReason"),
		ReasonOptionNoNeedForPhone("NO NEED OF PHONE"),
		DeactivateButton("Deactivate"),
		LineSuccessfullyDeactivated("REP: The Line has been successfully deactivated."),
		LineSuccessfullyDeactivatedST("REP: The line has been successfully deactivated."),
		CompleteProcessOption("radAction[1]"),
		LogOutLink("Log Out"),
		LogOutConfirmMessage("Are you sure you want to Log Out?"),

		// Deactive Phone with Phone number change
		PinText("pins[0]}"),
		GenerateCodesButton("Generate Codes"),
		CompleteProcessOption2("radAction2"),
		TransactionSummaryMessage("Transaction Summary"),
		NextButton2("   Next   ");

		public final String name;

		private DeactivationWebcsrFields(String id) {
			this.name = id;
		}
	}

}