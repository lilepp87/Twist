package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

public class FinishActivationWithPIN extends AbstractFinishActivationWithPIN {

	public FinishActivationWithPIN() {
		super("Net10S");
	}

	@Override
	public void enterPINForPhone(String pinType, String partForPin) throws Exception {
		super.enterPINForPhone(pinType, partForPin);
	}

	@Override
	public void activateWithPinFor(String cardType, String pin, String cellTech, String phoneType) 
			throws Exception {
		super.activateWithPinFor(cardType, pin, cellTech, phoneType);
	}

	@Override
	public void tryToActivateAndCheckMessageForPhoneAndPIN(String status, String cellTech, String phoneType, String pinType)
			throws Exception {
		super.tryToActivateAndCheckMessageForPhoneAndPIN(status, cellTech, phoneType, pinType);
	}
}