package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;

public class OnNet10EnglishHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private final Properties properties = new Properties("Net10");
	
	public OnNet10EnglishHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(properties.getString("Twist.Net10URL"));
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