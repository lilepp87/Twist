package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

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
		//This method is executed before the scenario execution starts.
	}

	/*public void tearDown() throws Exception {
		boolean loggedOut = tryToLogout();
		if (!loggedOut) {
			try {
				browser.navigateTo(properties.getString("TF_URL"));
				browser.link(properties.getString("buyAirtimeFromPhone")).click();
				tryToLogout();
			} catch (ExecutionException ex) {}
		}
	}

	private boolean tryToLogout() {
		boolean loggedOut = false;
		if (browser.exists(browser.link(properties.getString("signOut")))){
			browser.link(properties.getString("signOut")).click();
			loggedOut = true;
		} else if (browser.exists(browser.link(properties.getString("signOut") + " TwistFirst TwistLast"))) {
			browser.link(properties.getString("signOut") + " TwistFirst TwistLast").click();
			loggedOut = true;
		}
		return loggedOut;
	}*/

	public void tearDown() throws Exception {
		try {
			tryToLogout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		browser.close();
		browser.open();
	}
	private void tryToLogout() {
		if (browser.isVisible(browser.link(properties.getString("signOut")))) {
			browser.link(properties.getString("signOut")).click();
		} else {
			browser.navigateTo(properties.getString("TF_URL"));
			if (browser.isVisible(browser.link(properties.getString("signOut")))) {
				browser.link(properties.getString("signOut")).click();
			}
		}
	}

}


