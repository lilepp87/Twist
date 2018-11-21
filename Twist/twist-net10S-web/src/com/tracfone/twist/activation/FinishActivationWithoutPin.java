package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

public class FinishActivationWithoutPin extends AbstractFinishActivationWithoutPin {

	public FinishActivationWithoutPin() {
		super("Net10S");
	}

	@Override
	public void attemptToActivatePhoneWithStatus(String cellType, String phoneType, String status) throws Exception {
		super.attemptToActivatePhoneWithStatus(cellType, phoneType, status);
	}

	@Override
	public void activateWithoutPinFor(String cellTech, String phoneType) throws Exception {
		super.activateWithoutPinFor(cellTech, phoneType);
	}

}