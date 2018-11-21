package com.tracfone.twist.impl.eng.purchases;

import com.tracfone.twist.purchases.AbstractAddAirtime;

public class AddAirtime extends AbstractAddAirtime {

	public AddAirtime() {
		super("Net10");
	}
		
	@Override
	public void goToAddCardOnline() throws Exception {
		super.goToAddCardOnline();
	}
		
	@Override
	public void goToBuyAirtime() throws Exception {
		super.goToBuyAirtime();
	}
		
	@Override
	public void enterPhoneNumberWithPartAnd(String partNumber, String purchaseType) throws Exception {
		super.enterPhoneNumberWithPartAnd(partNumber, purchaseType);
	}
		
	@Override
	public void enterPinCard(String pinPart) throws Exception {
		super.enterPinCard(pinPart);
	}
		
	@Override
	public void chooseToPurchaseAirtime() throws Exception {
		super.chooseToPurchaseAirtime();
	}
		
	@Override
	public void createNewAccountForEsnDependingOn(String purchaseType) throws Exception {
		super.createNewAccountForEsnDependingOn(purchaseType);
	}
		
	/*@Override
	public void selectPlanWithILD(String purchaseType, String planStr, String phoneType, Integer numILD)
			throws Exception {
		super.selectPlanWithILD(purchaseType, planStr, phoneType, numILD);
	}*/
		
	@Override
	public void enterPhoneNumberFromPart(String partNumber) throws Exception {
		super.enterPhoneNumberFromPart(partNumber);
	}
	
	@Override
	public void enterPhoneNumber() throws Exception {
		super.enterPhoneNumber();
	}
		
	@Override
	public void submitPayment() throws Exception {
		super.submitPayment();
	}
		
	@Override
	public void redeemPINDependingOnStatusPhoneTypeAndPin(String status,
			String phone, String phoneType, String pin)	throws Exception {
		super.redeemPINDependingOnStatusPhoneTypeAndPin(status, phone, phoneType, pin);
	}
	
	@Override
	public void addPin(String addNow) {
		super.addPin(addNow);
	}

	@Override
	public void addServicePlan() throws Exception {
		super.addServicePlan();
	}
	
	@Override
	public void addServicePlanInsideAccount() throws Exception {
		super.addServicePlanInsideAccount();
	}
	
	@Override
	public void completeTheProcess() throws Exception {
		super.completeTheProcess();
	}
	
	@Override
	public void addMinsToThePlanForParts(int noOfMembers, String partNumber2, String partNumber3, String partNumber4) throws Exception {
		super.addMinsToThePlanForParts(noOfMembers, partNumber2, partNumber3, partNumber4);
	}

	@Override
	public void enterRemainingFamilyPlanPhoneNumbersAndChooseAddNow(int noOfMembers) throws Exception {
		super.enterRemainingFamilyPlanPhoneNumbersAndChooseAddNow(noOfMembers);
	}
	@Override
	public void enterPastDuePhoneNumber() throws Exception {
		super.enterPastDuePhoneNumber();
	}
	@Override
	public void reactivateAndCheckSummary() throws Exception {
		super.reactivateAndCheckSummary();
	}
	@Override
	public void goToServicePlan() throws Exception {
		super.goToServicePlan();
	}

	public void enrollInAutorefillForPlan(String plan) throws Exception {
		super.enrollInAutorefillForPlan(plan);
	}

	public void checkForRACEmployeeDiscountForPlan(String plan) throws Exception {
		super.checkForRACEmployeeDiscountForPlan(plan);
	}
	
	public void enterPastDueESN() throws Exception {
	    super.enterPastDueESN();
	}

	

}