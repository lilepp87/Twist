package com.tracfone.twist.purchases;

import org.junit.Assert;

import net.sf.sahi.client.ElementStub;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.RandomACHGenerator;
import com.tracfone.twist.utils.TwistUtils;

public abstract class AbstractEnterPaymentInformation {

	private PaymentFlow paymentFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private final Properties properties;

	protected AbstractEnterPaymentInformation(String propsName) {
		properties = new Properties(propsName);
	}

	public void enterPaymentWithCardType(String cardType) {
		if(cardType.equalsIgnoreCase("ACH")){
			  paymentFlow.selectRadioButton(properties.getString("Payment.SelectBankAccount"));
			  paymentFlow.chooseFromSelect(properties.getString("Payment.SelectAccountType"), properties.getString("Payment.DefaultAccountType"));
			  paymentFlow.typeInTextField(properties.getString("Payment.RoutingNum"), RandomACHGenerator.getRoutingNumber());
			  paymentFlow.typeInTextField(properties.getString("Payment.AccountNum"), RandomACHGenerator.getAccountNumber());
			}else{
				if(paymentFlow.selectionboxVisible(properties.getString("Payment.AccountTypeSelect"))){
					paymentFlow.chooseFromSelect(properties.getString("Payment.AccountTypeSelect"), cardType); //$NON-NLS-1$
				}
				paymentFlow.typeInTextField(properties.getString("Payment.AccountNumberText"), TwistUtils.generateCreditCard(cardType)); //$NON-NLS-1$
			
				if ("American Express".equalsIgnoreCase(cardType)){
					paymentFlow.typeInTextField(properties.getString("Payment.CvvText"), "6712");
				}
				else {
					paymentFlow.typeInTextField(properties.getString("Payment.CvvText"), properties.getString("Payment.DefaultCvv")); //$NON-NLS-1$ //$NON-NLS-2$
				 }
				paymentFlow.chooseFromSelect(properties.getString("Payment.ExpMonthSelect"), properties.getString("Payment.DefaultMonth")); //$NON-NLS-1$ //$NON-NLS-2$
				paymentFlow.chooseFromSelect(properties.getString("Payment.ExpYearSelect"), properties.getString("Payment.DefaultYear")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			paymentFlow.typeInTextField(properties.getString("Payment.FirstNameText"), properties.getString("Payment.DefaultFirstName")); //$NON-NLS-1$ //$NON-NLS-2$
			paymentFlow.typeInTextField(properties.getString("Payment.LastNameText"), properties.getString("Payment.DefaultLastName")); //$NON-NLS-1$ //$NON-NLS-2$
			paymentFlow.typeInTextField(properties.getString("Payment.Address1Text"), properties.getString("Payment.DefaultAddress")); //$NON-NLS-1$ //$NON-NLS-2$
			paymentFlow.typeInTextField(properties.getString("Payment.CityText"), properties.getString("Payment.DefaultCity")); //$NON-NLS-1$ //$NON-NLS-2$
			paymentFlow.chooseFromSelect(properties.getString("Payment.StateSelect"), properties.getString("Payment.DefaultState")); //$NON-NLS-1$ //$NON-NLS-2$
			paymentFlow.typeInTextField(properties.getString("Payment.ZipCodeText"), "94043"); //$NON-NLS-1$
			if (paymentFlow.textboxVisible(properties.getString("Payment.PhoneText"))) {
				paymentFlow.typeInTextField(properties.getString("Payment.PhoneText"), TwistUtils.generateRandomMin());
			}
			if (paymentFlow.checkboxVisible(properties.getString("Payment.Checkbox"))){
			paymentFlow.selectCheckBox(properties.getString("Payment.Checkbox"));
			}
			if (paymentFlow.checkboxVisible("terms")){
			paymentFlow.selectCheckBox("terms");
			}
		}

	public void submitPaymentWithCardType(String cardType) {
		enterPaymentWithCardType(cardType);
		ElementStub b = paymentFlow.getBrowser().byId("default_submit_btn");
		b.click();
	}
	
	public void buyProtectionPlanWith(String plan, String cardtype) throws Exception {
		
		if (plan.equalsIgnoreCase("Monthly Easy Exchange Plus")) {
			paymentFlow.pressSubmitButton("Enroll Now[2]");			
		} else if (plan.equalsIgnoreCase("Annual Easy Exchange Plus")) {
			paymentFlow.pressSubmitButton("Enroll Now");			
		} else if (plan.equalsIgnoreCase("Annual Easy Exchange")) {
			paymentFlow.pressSubmitButton("Enroll Now[1]");			
		} else if (plan.equalsIgnoreCase("BYOP Annual Easy Exchange")) {
			paymentFlow.pressSubmitButton("Enroll Now");			
		}else {
			throw new IllegalArgumentException("Unknown easy exchange plan: " + plan);
		}
		
//		paymentFlow.pressSubmitButton(properties.getString("Redemption.ContinueButton"));
		enterPaymentWithCardType(cardtype);
		
		if(paymentFlow.textboxVisible("useremail")){
			paymentFlow.typeInTextField("useremail", esnUtil.getCurrentESN().getEmail());
		}
		if(paymentFlow.selectionboxVisible("mfgtype")){
			paymentFlow.typeInTextField("userserial", esnUtil.getCurrentESN().getEsn());
			paymentFlow.chooseFromSelect("mfgtype", "SAMSUNG");
			paymentFlow.chooseFromSelect("phmodeltype", "GALAXY S III");
		}
		paymentFlow.pressSubmitButton(properties.getString("Redemption.SubmitButton"));
		TwistUtils.setDelay(25);
		paymentFlow.pressSubmitButton(properties.getString("Redemption.FinishButton"));
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

	public void checkSummaryAndProcess() throws Exception {
		Assert.assertTrue(paymentFlow.h3Visible(properties.getString("Redemption.SuccessTitle"))); 
		paymentFlow.clickLink(properties.getString("Activation.FinishButton"));
		phoneUtil.finishPhoneActivationWithSameMin(esnUtil.getCurrentESN().getEsn());
	}

}