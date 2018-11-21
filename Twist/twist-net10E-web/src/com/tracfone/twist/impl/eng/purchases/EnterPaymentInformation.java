package com.tracfone.twist.impl.eng.purchases;

import com.tracfone.twist.purchases.AbstractEnterPaymentInformation;

// JUnit Assert framework can be used for verification

public class EnterPaymentInformation extends AbstractEnterPaymentInformation {
	
	public EnterPaymentInformation() {
		super("Net10");
	}
	
	@Override
	public void enterPaymentWithCardType(String cardType) {
		super.enterPaymentWithCardType(cardType);
	}
	
	@Override 
	public void submitPaymentWithCardType(String cardType) {
		super.submitPaymentWithCardType(cardType);
	}
	
	@Override
	public void buyProtectionPlanWith(String plan, String cardtype) throws Exception {
		super.buyProtectionPlanWith(plan, cardtype);
	}
	@Override
	public void checkSummaryAndProcess() throws Exception {
		super.checkSummaryAndProcess();
	}

}