package com.tracfone.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnAmazonHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private final static ResourceBundle rb = ResourceBundle.getBundle("SM");
	public final static String SM_AMZ_URL = rb.getString("sm.amazonpage");

	public OnAmazonHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(SM_AMZ_URL);
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