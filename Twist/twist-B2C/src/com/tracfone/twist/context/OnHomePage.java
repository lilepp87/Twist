package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class OnHomePage {

	private MyAccountFlow myAccountFlow;

	public OnHomePage() {

	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
		myAccountFlow.navigateTo("http://sit1.straighttalk.com/wps/portal/home");
	}

	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}