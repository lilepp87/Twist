package com.tracfone.twist.context;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;

import com.tracfone.twist.flows.tracfone.TracfoneFlows;

// JUnit Assert framework can be used for verification

public class OnNet10Web {

	private MyAccountFlow myAccountFlow;
	
	public OnNet10Web() {
	
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
		myAccountFlow.navigateTo("http://sit1.net10.com");
	}
	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}