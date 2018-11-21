package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

public class TracfoneFlows {

	private Browser browser;

	public TracfoneFlows() {

	}

	public SignInFlow signInFlow() {
		return new SignInFlow(browser);
	}

	public MyAccountFlow myAccountFlow() {
		return new MyAccountFlow(browser);
	}

	public DeactivationPhoneFlow deactivationPhoneFlow() {
		return new DeactivationPhoneFlow(browser);
	}

	public ActivationReactivationFlow activationPhoneFlow() {
		return new ActivationReactivationFlow(browser);
	}

	public RedemptionFlow redemptionFlow() {
		return new RedemptionFlow(browser);
	}

	public PaymentFlow paymentFlow() {
		return new PaymentFlow(browser);
	}

	public PurchasesFlow purchasesFlow() {
		return new PurchasesFlow(browser);
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

}