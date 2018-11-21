package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class LogOut {

	private MyAccountFlow myAccountFlow;

	public LogOut() {

	}

	public void setUp() throws Exception {

	}

	public void tearDown() throws Exception {
		try {
			myAccountFlow.clickLink("Logout");
	//		myAccountFlow.getBrowser().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}