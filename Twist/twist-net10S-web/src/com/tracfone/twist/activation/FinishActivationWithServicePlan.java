package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

public class FinishActivationWithServicePlan extends AbstractFinishActivationWithServicePlan {
	public FinishActivationWithServicePlan() {
		super("Net10S");
	}

	@Override
	public void checkResultForPhoneWithStatusAndPlan(String cellTech, String phoneType,
			String status, String planType) throws Exception {
		super.checkResultForPhoneWithStatusAndPlan(cellTech, phoneType, status, planType);
	}

	@Override
	public void enrollInRecurringPlan(String planStr, String phoneType) throws Exception {
		super.enrollInRecurringPlan(planStr, phoneType);
	}

	@Override
	public void selectINeedAnAirtimeCard() throws Exception {
		super.selectINeedAnAirtimeCard();
	}

	@Override
	public void selectPlan(String planStr, String phoneType) throws Exception {
		super.selectPlan(planStr, phoneType);
	}

	@Override
	public void enterPaymentInformationWithZipCodeCardTypeAndStatus(Integer zipCode,
			String cardType, String status) throws Exception {
		super.enterPaymentInformationWithZipCodeCardTypeAndStatus(zipCode, cardType, status);
	}
}