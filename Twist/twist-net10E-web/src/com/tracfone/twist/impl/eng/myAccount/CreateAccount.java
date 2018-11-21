package com.tracfone.twist.impl.eng.myAccount;

import com.tracfone.twist.myAccount.AbstractCreateAccount;

// JUnit Assert framework can be used for verification

public class CreateAccount extends AbstractCreateAccount {

	public CreateAccount() {
		super("Net10");
	}

	@Override
	public void createNewAccountForEsn() throws Exception {
		super.createNewAccountForEsn();
	}

	@Override
	public void skipAccountCreation() throws Exception {
		super.skipAccountCreation();
	}

	@Override
	public void fillOutOriginalRegistrationFormWithZip(String zip) {
		super.fillOutOriginalRegistrationFormWithZip(zip);
	}
	
	@Override
	public void goToMyAccount() throws Exception {
		super.goToMyAccount();	
	}


	public void changeDealerTo(String dealer) throws Exception {
	     super.changeDealerTo(dealer);
	}
}