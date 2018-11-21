package com.tracfone.twist.purchase;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;


import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class EnterPayment {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	private Properties properties = new Properties();
	
	public void useStoredCard(String cardType) throws Exception {
		String cvv;
		if (cardType.equalsIgnoreCase("American Express")) {
			cvv = "6715";
		} else {
			cvv = "671";
		}
		//paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CvvText.name, cvv);
		paymentFlow.typeInTextField("cvvnumber", cvv);
		paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.TermsCheck.name);
		paymentFlow.pressSubmitButton("default_submit_btn");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}

	public void enterPaymentWithCardType(String cardType) throws Exception {
		String creditCard = TwistUtils.generateCreditCard(cardType);
		TwistUtils.setDelay(18);
		String cvv;
		if (cardType.equalsIgnoreCase("American Express")) {
			cvv = "6715";
		} else {
			cvv = "671";
		}

		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.AccountTypeSelect.name, cardType);
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.AccountNumberText.name, creditCard);
		System.out.println("CC: " + creditCard);
		//paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CvvText.name, cvv);
		//paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.ExpMonthSelect.name, "07");
		//paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.ExpYearSelect.name, "2021");
		//paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.StateSelect.name, "CA");
		// paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.UseAccountInformationCheck.name);
		paymentFlow.typeInTextField("cvvnumber", cvv);
		paymentFlow.chooseFromSelect("credit_expiry_mo", "07");
		paymentFlow.chooseFromSelect("credit_expiry_yr", "2021");
		paymentFlow.chooseFromSelect("state_list", "CA");
		paymentFlow.typeInTextField("first_name", "TwistFirstName");
		paymentFlow.typeInTextField("last_name", "TwistLastName");
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("zip", "94043");
		paymentFlow.typeInTextField("city", "Mountain View");
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.StateSelect.name, "CA");
		paymentFlow.chooseFromSelect("country", "USA");
		if (paymentFlow.checkboxVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.TermsCheck.name)) {
			paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.TermsCheck.name);
		}
		// paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);
		paymentFlow.pressSubmitButton("default_submit_btn");
		esnUtil.getCurrentESN().setBuyFlag(true);		
	}

	public void addProtectionPlanDependsOnZipWithForPart(String planType, String zipCode, String cardType,String partnumber) throws Exception {
		String state = phoneUtil.getStateByZip(zipCode);
		ESN activeEsn = esnUtil.getCurrentESN();
		String randomEmail = esnUtil.getCurrentESN().getEmail();
		System.out.println("Email:" + randomEmail);
		if (!state.equalsIgnoreCase("FL") && !state.equalsIgnoreCase("CA")) {
			int option = 0;
			if (partnumber.startsWith("TCAPBYOP")) {
				option = 0;
			} else if (planType.equalsIgnoreCase("Monthly Easy Exchnage Plus")) {
				option = 0;
			} else if (planType.equalsIgnoreCase("Annual Easy Exchnage Plus")) {
				option = 1;
			} else if (planType.equalsIgnoreCase("Annual Easy Exchnage")) {
				option = 2;
			}
			paymentFlow.pressSubmitButton("default_submit_btn[" + option + "]");
		}

		if (partnumber.startsWith("PHTC")) {
			paymentFlow.typeInTextField("useremail", randomEmail);
			paymentFlow.typeInTextField("userserial", activeEsn.getEsn());
			paymentFlow.chooseFromSelect("mfgtype", "APPLE");
			paymentFlow.chooseFromSelect("phmodeltype", "IPHONE 5S");
		} else if(partnumber.startsWith("TCAPBYOP")) {
			paymentFlow.typeInTextField("useremail", randomEmail);
		}
		enterPaymentWithCardType(cardType);

		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("transSummary")));
		paymentFlow.pressSubmitButton("default_submit_btn");
		if (paymentFlow.submitButtonVisible(properties.getString("noThanks"))) {
			paymentFlow.pressSubmitButton(properties.getString("noThanks"));
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
}

