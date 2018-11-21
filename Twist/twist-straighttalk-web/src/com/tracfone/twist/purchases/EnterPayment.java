package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class EnterPayment {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	public EnterPayment() {

	}
//
	public void enterCreditCardOrUseStoredCard(String cardType) throws Exception {
		TwistUtils.setDelay(18);
		if (paymentFlow.selectionboxVisible("paymentSrcs")) {
			if ("American Express".equalsIgnoreCase(cardType)) {
				paymentFlow.typeInTextField("cvv", "6712");
			} else {
				paymentFlow.typeInTextField("cvv", "671");
			}
			paymentFlow.clickLink("paymentSubmit");
			TwistUtils.setDelay(10);
		} else {
			enterCreditCard(cardType);
			TwistUtils.setDelay(30);
			if (paymentFlow.textboxVisible("account_number")) {
				enterCreditCard(cardType);
			}
		}
	}
//
	public void enterCreditCard(String cardType) throws Exception {
		TwistUtils.setDelay(13);
		String card = TwistUtils.generateCreditCard(cardType);
		paymentFlow.getBrowser().setSpeed(600);
		//Try to work around an issue where the text is not getting filled out the first time
		paymentFlow.clickLink("paymentSubmit");
		
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.AccountNumberText.name, card);
		// paymentFlow.getBrowser().execute("$(\"#account_number\").val(\"" +
		// card + "\").change()");
		TwistUtils.setDelay(1);
		paymentFlow.getBrowser().execute("$(\"#account_number\").trigger(\"input\")");
		if ("American Express".equalsIgnoreCase(cardType)) {
			paymentFlow.typeInTextField("cvv", "6712");
		} else {
			paymentFlow.typeInTextField("cvv", "671");
		}
		paymentFlow.chooseFromSelect("expMonth", "07");
		paymentFlow.chooseFromSelect("expYear", "2021");
		paymentFlow.typeInTextField("fname", "TwistFirstName");
		paymentFlow.typeInTextField("lname", "TwistLastName");
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("address2", "");
		paymentFlow.typeInTextField("city", "Mountain View");
		paymentFlow.typeInTextField("zip", "94043");
		paymentFlow.chooseFromSelect("state", "CA");
		if (paymentFlow.checkboxVisible("saveCardToAccount")) {
			paymentFlow.selectCheckBox("saveCardToAccount");
		}
		TwistUtils.setDelay(3);
		paymentFlow.clickLink("paymentSubmit");
		paymentFlow.getBrowser().setSpeed(200);
		TwistUtils.setDelay(10);
		esnUtil.getCurrentESN().setBuyFlag(true);
	}
//	
	public void addProtectionPlanDependsOnZipWithForPart(String planType, String zipCode, String cardType, String partnumber) throws Exception {
		if (paymentFlow.linkVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.ProtectYourPlanLink.name)) {
		     paymentFlow.clickLink(PaymentFlow.BuyAirtimeStraighttalkWebFields.ProtectYourPlanLink.name);
		}
		//Assertion to check if flow is in Protection Plan page, else fail the scenario
		Assert.assertTrue(paymentFlow.getBrowser().exists(paymentFlow.getBrowser().byId("valueadded_program_id")));
		
		String state = phoneUtil.getStateByZip(zipCode);
		String randomEmail = esnUtil.getCurrentESN().getEmail();
		if (!state.equalsIgnoreCase("FL") && !state.equalsIgnoreCase("CA")) {
			if (partnumber.startsWith("STBYO") || partnumber.startsWith("PHST")) {
				if (planType.equalsIgnoreCase("Annual Easy Exchange PLUS for $200 BYOP")) {
					paymentFlow.clickLink("valueadded_program_id");
				} else {
					//Should be Annual Easy Exchange PLUS for $200 BYOP
					paymentFlow.clickLink("valueadded_program_id[1]");
				}
			} else {
				if (planType.equalsIgnoreCase("Annual Easy Exchange PLUS")) {
					paymentFlow.clickLink("valueadded_program_id");
				} else if (planType.equalsIgnoreCase("Annual Easy Exchange")) {
					paymentFlow.clickLink("valueadded_program_id[1]");
				} else {
					//Should be Monthly Easy Exchange PLUS"
					paymentFlow.clickLink("valueadded_program_id[2]");
				}
			}

			if (partnumber.startsWith("PHST") || partnumber.startsWith("STBYOC")) {	
				paymentFlow.typeInTextField("hpp_email", randomEmail);
			}
			enterCreditCard(cardType);
			Assert.assertTrue(paymentFlow.h2Visible("Transaction Summary"));
			paymentFlow.pressSubmitButton("Continue");
			TwistUtils.setDelay(10);
			if (paymentFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
				paymentFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);
			}
		}
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void useAnExistingCardForLogin(String cardType, String login) throws Exception {
		if (login.equalsIgnoreCase("Yes")) {
			// use existing CC card
			ESN esn = esnUtil.getCurrentESN();
			paymentFlow.clickLink("USE AN EXISTING CREDIT CARD");
			paymentFlow.clickLink("LOG IN");
			paymentFlow.typeInTextField("userid", esn.getEmail());
			paymentFlow.typeInPasswordField("password", esn.getPassword());
			paymentFlow.pressButton("Log In to My Account");
		}		
		enterCreditCardOrUseStoredCard(cardType);
	}

	public void enterShippingAddressAndWhiteListedCard(String card) throws Exception {
		TwistUtils.setDelay(20);
		
		
		paymentFlow.pressButton("Ok");
		String email = "itq_st_" + TwistUtils.createRandomEmail();
		String cardNum = TwistUtils.generateCreditCard(card);
		System.out.println("CC NUM" +cardNum);
		paymentFlow.typeInTextField("firstName", "Cyber");
		paymentFlow.typeInTextField("lastName", "Source");
		paymentFlow.typeInTextField("phone1", TwistUtils.generateRandomMin());
		paymentFlow.typeInTextField("email1", email);
		paymentFlow.typeInTextField("address1", "1295 charleston road");
		paymentFlow.typeInTextField("address2", "");
		paymentFlow.typeInTextField("city", "Mountain view");
		paymentFlow.chooseFromSelect("state", "California");
		if(paymentFlow.getBrowser().byId("zipCode").getValue().isEmpty()){
			paymentFlow.typeInTextField("zipCode", "94043");
		}
		else{
			paymentFlow.clickLink("btn btn-link pull-left top-push-2 zip-code");
			TwistUtils.setDelay(3);
			paymentFlow.clickLink("Change ZIP");
			paymentFlow.typeInTextField("zipCode", "94043");
		}
		paymentFlow.chooseFromSelect("payMethodId_1", card + " Credit Card");
		paymentFlow.typeInTextField("account1_1", cardNum);
		if(card.equalsIgnoreCase("American Express")){
			paymentFlow.typeInTextField("cc_cvc_1", "6718");
		}else {
		paymentFlow.typeInTextField("cc_cvc_1", "671");
		}
		paymentFlow.chooseFromSelect("expire_month_1", "05");
		paymentFlow.chooseFromSelect("expire_year_1", "2019");
		paymentFlow.clickLink("shippingBillingPageNext");
		TwistUtils.setDelay(15);
		paymentFlow.pressSubmitButton("singleOrderSummary");
	}
	
	public void enterShippingAddressAndCc(String cardType, String card) throws Exception {
		TwistUtils.setDelay(20);
		String email = "itq_st_" + TwistUtils.createRandomEmail();
		paymentFlow.typeInTextField("firstName", "TwistFirstName");
		paymentFlow.typeInTextField("lastName", "TwistLastName");
		paymentFlow.typeInTextField("phone1", TwistUtils.generateRandomMin());
		paymentFlow.typeInTextField("email1", email);
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("address2", "");
		paymentFlow.typeInTextField("city", "Miami");
		paymentFlow.chooseFromSelect("state", "Florida");
		paymentFlow.chooseFromSelect("payMethodId_1", card);
		paymentFlow.typeInTextField("account1_1", TwistUtils.generateCreditCard(cardType));
		if (cardType.equalsIgnoreCase("American Express")) {
			paymentFlow.typeInTextField("cc_cvc_1", "6712");
		} else {
			paymentFlow.typeInTextField("cc_cvc_1", "671");
		}
		paymentFlow.chooseFromSelect("expire_month_1", "05");
		paymentFlow.chooseFromSelect("expire_year_1", "2020");
		paymentFlow.clickLink("shippingBillingPageNext");
		TwistUtils.setDelay(15);
		paymentFlow.pressSubmitButton("singleOrderSummary");
		TwistUtils.setDelay(15);
	}	

}