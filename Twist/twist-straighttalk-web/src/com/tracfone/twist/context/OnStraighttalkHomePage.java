package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnStraighttalkHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	public static final String URL = "https://sit1.straighttalk.com";

	public OnStraighttalkHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(URL);
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
		try {
			myAccountFlow.getBrowser().close();
			myAccountFlow.getBrowser().open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}