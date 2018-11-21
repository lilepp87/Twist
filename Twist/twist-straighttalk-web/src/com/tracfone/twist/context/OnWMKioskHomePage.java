package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class OnWMKioskHomePage {

	private MyAccountFlow myAccountFlow;
	public static final String URL = "https://sit1walmart.straighttalk.com";

	public OnWMKioskHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(URL);
	}

	public void tearDown() throws Exception {
		if (myAccountFlow.imageVisible("main_menu.png")) {
			myAccountFlow.clickImage("main_menu.png");	
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}