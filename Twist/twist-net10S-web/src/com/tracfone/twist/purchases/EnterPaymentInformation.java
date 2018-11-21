package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

public class EnterPaymentInformation extends AbstractEnterPaymentInformation {

	public EnterPaymentInformation() {
		super("Net10S");
	}

	@Override
	public void enterPaymentWithCardType(String cardType) {
		super.enterPaymentWithCardType(cardType);
	}
}
