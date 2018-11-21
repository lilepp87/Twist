package com.tracfone.twist.purchases;

public class AddAirtime extends AbstractAddAirtime {

	public AddAirtime() {
		super("Net10S");
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
		
	@Override
	public void selectPlan(String purchaseType, String planStr, String phoneType) throws Exception {
		super.selectPlan(purchaseType, planStr, phoneType);
	}
		
	@Override
	public void enterPhoneNumberFromPart(String partNumber) throws Exception {
		super.enterPhoneNumberFromPart(partNumber);
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
	public void completeTheProcess(String action) throws Exception {
		super.completeTheProcess(action);
	}


}
