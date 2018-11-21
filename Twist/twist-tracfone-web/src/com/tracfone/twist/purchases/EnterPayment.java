package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class EnterPayment {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	private Properties properties = new Properties();
	
	public EnterPayment() {

	}

	public void enterCreditCard(String cardType) throws Exception {
		String card = TwistUtils.generateCreditCard(cardType);
		//Assert.assertTrue(paymentFlow.h1Visible("CHECK-OUT"));
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentTracfoneWebFields.AccountTypeSelect.name, cardType);
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.AccountNumberText.name, card);
		if ("American Express".equalsIgnoreCase(cardType)){
			paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.CvvText.name, "6712");
		} else {
			paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.CvvText.name, "671");	
		}
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentTracfoneWebFields.ExpMonthSelect.name, "07");
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentTracfoneWebFields.ExpYearSelect.name, "2021");
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.FirstNameText.name, "TwistFirstName");
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.LastNameText.name, "TwistLastName");
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.Address1Text.name, "1295 Charleston Road");
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.CityText.name, "Mountain View");
		paymentFlow.chooseFromSelect(PaymentFlow.CreditCardPaymentTracfoneWebFields.StateSelect.name, "CA");
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.ZipCodeText.name, "94043");
		if (paymentFlow.labelVisible(properties.getString("billingPhoneNumber"))) {
			paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.PhoneText.name, TwistUtils.generateRandomMin());
			//paymentFlow.typeInTextField("payment_src_name", "Twist Nickname");
		}
		
		if (paymentFlow.textboxVisible("payment_src_name")) {
			paymentFlow.typeInTextField("payment_src_name", "Twist Nickname");
		}
		paymentFlow.selectCheckBox("terms");
		paymentFlow.pressSubmitButton(properties.getString("continue2"));
		esnUtil.getCurrentESN().setBuyFlag(true);
		TwistUtils.setDelay(15);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void verifyPhoneNumberForESN() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		paymentFlow.typeInTextField("verify_min1", min);
		phoneUtil.clearOTAforEsn(esn);
		paymentFlow.pressSubmitButton("Continue");
	}
	
	public void finishPurchaseForByop(String cardType)throws Exception {
		String card = TwistUtils.generateCreditCard(cardType);
		paymentFlow.chooseFromSelect("account_type", cardType);
		paymentFlow.typeInTextField("account_number", card);
		if ("American Express".equalsIgnoreCase(cardType)){
			paymentFlow.typeInTextField("cvvnumber", "6712");
		} else {
			paymentFlow.typeInTextField("cvvnumber", "671");	
		}
		paymentFlow.chooseFromSelect("credit_expiry_mo", "07");
		paymentFlow.chooseFromSelect("credit_expiry_yr", "2021");
		paymentFlow.typeInTextField("first_name", "TwistFirstName");
		paymentFlow.typeInTextField("last_name", "TwistLastName");
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("city", "Mountain View");//city
		paymentFlow.chooseFromSelect("state_list", "CA");
		paymentFlow.typeInTextField("zip", "94043");//zip
		paymentFlow.pressSubmitButton("Continue");;//redirects to Add Airtime page error
		TwistUtils.setDelay(15);
		if(paymentFlow.submitButtonVisible("default_submit_btn4")){//correct
			paymentFlow.pressSubmitButton("default_submit_btn4");
		}
	}

	public void enterCvvAndContinue() throws Exception {
		paymentFlow.typeInTextField(PaymentFlow.CreditCardPaymentTracfoneWebFields.CvvText.name, "671");	
		paymentFlow.selectCheckBox("terms");
		paymentFlow.pressSubmitButton(properties.getString("continue2"));
		esnUtil.getCurrentESN().setBuyFlag(true);
	}

	public void enterCreditCardForPurchaseOnly(String cardType, String flowType) throws Exception {
		if(flowType.equalsIgnoreCase("purchase")){
			enterCreditCard(cardType);	
		}
	}

	public void addACHPaymentDetails() throws Exception {
	  paymentFlow.clickLink("Payment Information");
	  paymentFlow.clickLink("Manage Payment Information");
	  Assert.assertTrue(paymentFlow.h1Visible("VIEW/CHANGE PAYMENT INFORMATION"));
	  paymentFlow.clickSpanMessage("ACH");
	  paymentFlow.pressButton("ADD A NEW BANK ACCOUNT");//ach_add_cc_head
	  paymentFlow.chooseFromSelect("ach_account_type", "Checking");//Saving
	  paymentFlow.typeInTextField("routing_number", "121107882");
	  paymentFlow.typeInTextField("bank_account_number", "4100");
	  paymentFlow.typeInTextField("fname", "testfirst");
	  paymentFlow.typeInTextField("lname", "testlast");
	  paymentFlow.typeInTextField("address1", "1295 charlestn Road");
	  paymentFlow.typeInTextField("city", "Mountain View");
	  paymentFlow.typeInTextField("zip", "94043");
	  paymentFlow.chooseFromSelect("state", "CA");
	  paymentFlow.pressSubmitButton("SAVE");//save_btn
	}

	public void enterACHPaymentDetails() throws Exception {
	
	}
}