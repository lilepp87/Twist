package com.tracfone.twist.impl.eng.purchases;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.purchases.AbstractEnrollInPlan;

public class EnrollInPlan extends AbstractEnrollInPlan {

	public EnrollInPlan() {
		super("Net10");
	}

	@Override
	public void goToEnrollInEasyMinutes() throws Exception {
		super.goToEnrollInEasyMinutes();
	}

	@Override
	public void chooseToRegister() throws Exception {
		super.chooseToRegister();
	}

	@Override
	public void checkThatThePurchaseWasSuccessful() throws Exception {
		super.checkThatThePurchaseWasSuccessful();
	}

	@Override
	public void selectThePlanForPhoneType(String plan, String phoneType)
			throws Exception {
		super.selectThePlanForPhoneType(plan, phoneType);
	}

	@Override
	public void registerOrLogInForModelAndZip(String model, String zip)
			throws Exception {
		super.registerOrLogInForModelAndZip(model, zip);
	}

	@Override
	public void goToEnrollInMonthlyPlans() throws Exception {
		super.goToEnrollInMonthlyPlans();
	}
	
}