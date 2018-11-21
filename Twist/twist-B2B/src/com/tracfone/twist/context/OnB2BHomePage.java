package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnB2BHomePage {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static ResourceBundle rb = ResourceBundle.getBundle("B2B");
	public static final String URL = rb.getString("B2B.HomeURL");

	public OnB2BHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(URL);
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}