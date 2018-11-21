package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class SignInFlow extends Flow {

	public SignInFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum SignInWebcsrFields {
		Login("txtUsr"),
		Password("txtPsw"),
		LoginButton("Login"),
		LogOutLink("Log Out"),
		LogOutConfirmMessage("Are you sure you want to Log Out?"),

		// Switch to Straighttalk
		StraighttalkLink("Switch to StraightTalk"),
		// Switch to NET10 English
		Net10Link("Switch to Net10");

		public final String name;

		private SignInWebcsrFields(String id) {
			this.name = id;
		}
	}

	public void clickRegister() {
		browser.submit("REGISTER").click();
	}

}