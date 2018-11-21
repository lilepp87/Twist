package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;

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
	private static Logger logger = LogManager.getLogger(EnterPayment.class.getName());
	
	public EnterPayment() {

	}

	public void enterCreditCardOrUseStoredCard(String cardType) throws Exception {
		TwistUtils.setDelay(10);
		paymentFlow.pressSubmitButton("continue to payment");
		if (paymentFlow.linkVisible("exitingCCTab")) {
			paymentFlow.typeInTextField("cvv", "123");
			paymentFlow.pressSubmitButton("complete transaction");
		} else {
			enterCreditCard(cardType);
		}
	}
	
	public void enterCreditCard(String cardType) throws Exception {
		TwistUtils.setDelay(15);
		if (paymentFlow.submitButtonVisible("continue to payment")) {
			paymentFlow.pressSubmitButton("continue to payment");
		}
		if (paymentFlow.submitButtonVisible("GUEST CHECKOUT")) {
			paymentFlow.pressSubmitButton("GUEST CHECKOUT");
		}
		TwistUtils.setDelay(20);
		String card = TwistUtils.generateCreditCard(cardType);
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.AccountNumberText.name, card);
		if (cardType.equalsIgnoreCase("American Express")) {
			paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CvvText1.name, "6712");	
		} else {
			paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentStraighttalkWebFields.CvvText1.name, "123");	
		}
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.ExpMonthSelect1.name, "07");
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.ExpYearSelect1.name, "2021");
		// paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.UseAccountInformationCheck.name);
		paymentFlow.typeInTextField("fname", "Cyber");
		paymentFlow.typeInTextField("lname", "Source");
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("zip", "94043");
		paymentFlow.typeInTextField("city", "Mountain View");
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentStraighttalkWebFields.StateSelect.name, "CA");
		//paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.TermsCheck.name);
		//paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.ContinueButton.name);
		//paymentFlow.pressSubmitButton("default_submit_btn");
		paymentFlow.pressSubmitButton("complete transaction");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}

	public void addProtectionPlanDependsOnZipWithForPart(String planType, String zipCode, String cardType, String partnumber) throws Exception {
		String state = phoneUtil.getStateByZip(zipCode);
		ESN activeEsn = esnUtil.getCurrentESN();
		String randomEmail = esnUtil.getCurrentESN().getEmail();
		if (!state.equalsIgnoreCase("FL") && !state.equalsIgnoreCase("CA")) {
			if (partnumber.startsWith("TWBYOC") || partnumber.startsWith("PH") && planType.equalsIgnoreCase("Annual Easy Exchnage")) {
				paymentFlow.selectRadioButton("valueadded_program_id");
			} else {
				if (planType.equalsIgnoreCase("Monthly Easy Exchnage Plus")) {
					paymentFlow.selectRadioButton("valueadded_program_id");
				} else if (planType.equalsIgnoreCase("Annual Easy Exchnage Plus")) {
					paymentFlow.selectRadioButton("valueadded_program_id[1]");
				} else if (planType.equalsIgnoreCase("Annual Easy Exchnage")) {
					paymentFlow.selectRadioButton("valueadded_program_id[2]");
				}
			}
			paymentFlow.pressSubmitButton("default_submit_btn");
			if (partnumber.startsWith("PH")) {
				paymentFlow.typeInTextField("hpp_email", randomEmail);
				paymentFlow.typeInTextField("hpp_serial", activeEsn.getEsn());
				paymentFlow.chooseFromSelect("hppmanufacturer", "APPLE");
				paymentFlow.chooseFromSelect("hppphonemodel", "IPHONE 5S");
			} else if (partnumber.startsWith("STBYOC")) {
				paymentFlow.typeInTextField("hpp_email", randomEmail);
			}
			enterCreditCard(cardType);
			Assert.assertTrue(paymentFlow.h2Visible("Transaction Summary"));
			paymentFlow.pressSubmitButton("Continue");

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

}