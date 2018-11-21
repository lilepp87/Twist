package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class LogOut {

	private MyAccountFlow myAccountFlow;
	private static ResourceBundle rb = ResourceBundle.getBundle("B2B");

	public LogOut() {

	}

	public void setUp() throws Exception {
		if (myAccountFlow.linkVisible("Sign Out[1]")) {
			myAccountFlow.clickLink("Sign Out[1]");
		} else if (myAccountFlow.linkVisible("Sign Out")) {
			myAccountFlow.clickLink("Sign Out");	
		} else {
			myAccountFlow.navigateTo(OnB2BHomePage.URL);;
			if (myAccountFlow.linkVisible("Sign Out")) {
				myAccountFlow.clickLink("Sign Out");
			}
		}
	}

	public void tearDown() throws Exception {
		if (myAccountFlow.linkVisible("Sign Out[1]")) {
			myAccountFlow.clickLink("Sign Out[1]");
		} else if (myAccountFlow.linkVisible("Sign Out")) {
			myAccountFlow.clickLink("Sign Out");	
		} else {
			myAccountFlow.navigateTo(rb.getString("B2B.HomeURL"));
			if (myAccountFlow.linkVisible("Sign Out")) {
				myAccountFlow.clickLink("Sign Out");
			}
		}
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}