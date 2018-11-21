package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnNet10SpanishHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;

	public OnNet10SpanishHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo("https://env6.spanish.net10.dev");
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}
