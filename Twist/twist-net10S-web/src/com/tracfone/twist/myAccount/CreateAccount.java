package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

public class CreateAccount extends AbstractCreateAccount {

	public CreateAccount() {
		super("Net10S");
	}

	@Override
	public void createNewAccountForEsn() 
			throws Exception {
		super.createNewAccountForEsn();
	}

	@Override
	public void skipAccountCreation() throws Exception {
		super.skipAccountCreation();
	}
}
