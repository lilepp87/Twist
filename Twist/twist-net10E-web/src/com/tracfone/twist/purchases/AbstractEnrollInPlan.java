package com.tracfone.twist.purchases;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.impl.eng.activation.Plan;
import com.tracfone.twist.myAccount.AbstractCreateAccount;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public abstract class AbstractEnrollInPlan {

	private static PhoneUtil phoneUtil;
	private PaymentFlow paymentFlow;
	private MyAccountFlow myAccountFlow;
	private ActivationReactivationFlow activationReactivationFlow;
	private AbstractCreateAccount accountUtil;
	private ESNUtil esnUtil;
	private final Properties properties;

	public AbstractEnrollInPlan(String propsName) {
		properties = new Properties(propsName);
	}

	private void enterEsn(String esn) throws Exception {
		String min = phoneUtil.getMinOfActiveEsn(esn);

		myAccountFlow.typeInTextField(properties.getString("Redemption.EsnText"), esn); //$NON-NLS-1$
		myAccountFlow.typeInTextField(properties.getString("Redemption.MinText"), min); //$NON-NLS-1$
		myAccountFlow.pressSubmitButton(properties.getString("Redemption.SubmitButton")); //$NON-NLS-1$

		if (myAccountFlow.textboxVisible(properties.getString("Redemption.VerifyMin"))) { //$NON-NLS-1$
			myAccountFlow.typeInTextField(properties.getString("Redemption.VerifyMin"), min); //$NON-NLS-1$
			myAccountFlow.pressSubmitButton(properties.getString("Redemption.ContinueButton")); //$NON-NLS-1$
		}
	}

	public void selectEnrollInMonthlyPlan() throws Exception {
		myAccountFlow.clickLink(MyAccountFlow.MyAccountNet10EWebFields.EnrollInMonthlyPlan.name);
	}

	public void selectThePlanForPhoneType(String plan, String phoneType) throws Exception {
		Plan currPlan = Plan.getPlan(properties, plan, phoneType);
		String esn = esnUtil.getCurrentESN().getEsn();

		myAccountFlow.selectRadioButton(currPlan.getEnrollRadioString(properties, esn));
		myAccountFlow.pressSubmitButton(properties.getString("Redemption.ContinueButton")); //$NON-NLS-1$

		if (myAccountFlow.submitButtonVisible(properties.getString("Redemption.Continue1Button"))) { //$NON-NLS-1$
			myAccountFlow.pressSubmitButton(properties.getString("Redemption.Continue1Button")); //$NON-NLS-1$
		}
	}

	public void goToEnrollInEasyMinutes() throws Exception {
		paymentFlow.clickLink(properties.getString("Redemption.MonthlyPlansLink")); //$NON-NLS-1$
		paymentFlow.clickLink(properties.getString("Redemption.EnrollInEasyMinutesLink")); //$NON-NLS-1$
	}

	public void goToEnrollInMonthlyPlans() throws Exception {
		
		paymentFlow.clickLink(properties.getString("Redemption.MonthlyPlansLink")); //$NON-NLS-1$
		paymentFlow.clickLink(properties.getString("Redemption.EnrollInMonthlyPlanLink")); //$NON-NLS-1$
	}

	public void chooseToRegister() throws Exception {
		myAccountFlow.pressSubmitButton(properties.getString("Redemption.RegisterSubmit")); //$NON-NLS-1$
	}

	public void checkThatThePurchaseWasSuccessful() throws Exception {
		activationReactivationFlow.pressSubmitButton(properties.getString("Redemption.ContinueButton")); //$NON-NLS-1$
		TwistUtils.setDelay(5);
		Assert.assertTrue(activationReactivationFlow.h2Visible(properties.getString("Redemption.ThankYouMessage"))); //$NON-NLS-1$
		activationReactivationFlow.pressSubmitButton(properties.getString("Redemption.ContinueButton")); //$NON-NLS-1$
	}

	public void registerOrLogInForModelAndZip(String model, String zip) throws Exception {
		ESN esn = new ESN(model, phoneUtil.getActiveEsnByPartWithNoAccount(model));//esnUtil.popRecentEsnWithoutPlan(model);
		phoneUtil.clearOTAforEsn(esn.getEsn());
		esnUtil.setCurrentESN(esn);

		if (esn.getEmail().isEmpty()) {
			chooseToRegister();
			String email = TwistUtils.createRandomEmail();
			String password = properties.getString("Account.DefaultPassword");
			esn.setEmail(email);
			esn.setPassword(password);
			accountUtil.fillOutOriginalFormWithEmailPasswordAndZip(email, password, zip);
			enterEsn(esn.getEsn());
		} else {
			myAccountFlow.typeInTextField(properties.getString("Account.EmailText"), esn.getEmail());
			myAccountFlow.typeInPasswordField(properties.getString("Account.PasswordPswd"), esn.getPassword());
			myAccountFlow.pressSubmitButton(properties.getString("Account.SignIn"));
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
		myAccountFlow = tracfoneFlows.myAccountFlow();
		activationReactivationFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setAccountUtil(AbstractCreateAccount accountUtil) {
		this.accountUtil = accountUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}