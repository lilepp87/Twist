package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnTotalWirelessHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	public static final String URL = "http://sit1.totalwireless.com";
	public static final String PHONES_URL = "https://sit1shop.totalwireless.com/shop/en/totalwireless/phones";
	public static final String HOTSPOT_URL = "https://sit1shop.totalwireless.com/shop/en/totalwireless/mobilebroadbands";

	public OnTotalWirelessHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(URL);
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
		myAccountFlow.getBrowser().close();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}