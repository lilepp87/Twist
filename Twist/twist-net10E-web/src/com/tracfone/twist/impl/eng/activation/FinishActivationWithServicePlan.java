package com.tracfone.twist.impl.eng.activation;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.activation.AbstractFinishActivationWithServicePlan;

public class FinishActivationWithServicePlan extends AbstractFinishActivationWithServicePlan {

	public FinishActivationWithServicePlan() {
		super("Net10");
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
	public void enterPaymentInformationWithZipCodeCardTypeAndStatus(String zipCode,
			String cardType, String status) throws Exception {
		super.enterPaymentInformationWithZipCodeCardTypeAndStatus(zipCode, cardType, status);
	}
	
	@Override
	public void checkForDiscount(String disAmount) throws Exception {
	   super.checkForDiscount(disAmount);
	}

	@Override
	public void addMembersPartSIMAndZipCode(int noOfFamilyMembers, String partNum, String simPart, String zip)
			throws Exception {
		super.addMembersPartSIMAndZipCode(noOfFamilyMembers, partNum, simPart, zip);
	}

	public void goToMyAccount() throws Exception {
	       super.goToMyAccount();
	}

	public void addNewCreditCard(String cardType) throws Exception {
	       super.addNewCreditCard(cardType);
	}

	public void verifyPopUp() throws Exception {
	     super.verifyPopUp();
	}

	public void removeCCNotAssociatedWithAutoRefill() throws Exception {
	   super.removeCCNotAssociatedWithAutoRefill();
	}

	public void removeCCAssociatedWithAutoRefill() throws Exception {
	   super.removeCCAssociatedWithAutoRefill();
	}

	public void selectPlanFor(String plan, String deviceType, String purchaseType) throws Exception {
		super.selectPlanFor(plan,deviceType, purchaseType);
	}

}