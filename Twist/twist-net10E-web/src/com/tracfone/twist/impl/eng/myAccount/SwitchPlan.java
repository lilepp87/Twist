package com.tracfone.twist.impl.eng.myAccount;

import com.tracfone.twist.myAccount.AbstractSwitchPlan;

public class SwitchPlan extends AbstractSwitchPlan {

	public SwitchPlan() {
		super("Net10");
	}

	@Override
	public void verifyPhoneNumber() throws Exception {
		super.verifyPhoneNumber();
	}

	@Override
	public void switchPlanAndMakePaymentOr(String toPlan, String addILD, String action)
			throws Exception {
		super.switchPlanAndMakePaymentOr(toPlan, addILD, action);
	}

	@Override
	/*public void buyAirtimeAndMakePayment(String planToBuy, String addILD, String addNow) throws Exception {
		super.buyAirtimeAndMakePayment(planToBuy, addILD, addNow);
	}*/
	public void buyAirtimeAndMakePayment(String planToBuy, String addILD,String deviceType, String addNow) throws Exception {
		super.buyAirtimeAndMakePayment(planToBuy, addILD, deviceType,addNow);
	}

	@Override
	public void goToMyAccount() throws Exception {
		super.goToMyAccount();
	}

	@Override
	public void enrollInAutoRefill(String planToBuy, String addILD, String addNow) throws Exception {
		super.enrollInAutoRefill(planToBuy, addILD, addNow);
	}

	@Override
	public void manageReserve() throws Exception {
		super.manageReserve();
	}
	
	@Override
	public void updatePersonalInfo() throws Exception {
		super.updatePersonalInfo();
	}
	
	@Override
	public void selectPlanWithILD(String purchaseType, String planStr, String phoneType, String addILD)
			throws Exception {
		super.selectPlanWithILD(purchaseType, planStr, phoneType, addILD);
	}

}