package com.tracfone.context;

import com.tracfone.twist.util.Properties;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ExecutionException;

public class LogOut {

	private final Browser browser;
	private Properties properties = new Properties();

	public LogOut(Browser browser) {
		this.browser = browser;
	}

	public void setUp() throws Exception {

	}

	public void tearDown() throws Exception {
		boolean loggedOut = tryToLogout();
		if (!loggedOut) {
			try {
				browser.navigateTo(properties.getString("TF_HOME"));
				browser.link(properties.getString("buyAirtimeFromPhone")).click();
				tryToLogout();
			} catch (ExecutionException ex) {
			}
		}
	}

	private boolean tryToLogout() {
		boolean loggedOut = false;
		if (browser.exists(browser.link(properties.getString("signOut")))) {
			browser.link(properties.getString("signOut")).click();
			loggedOut = true;
		} else if (browser.exists(browser.link(properties.getString("signOut") + " TwistFirst TwistLast"))) {
			browser.link(properties.getString("signOut") + " TwistFirst TwistLast").click();
			loggedOut = true;
		}
		return loggedOut;
	}

}
