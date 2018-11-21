package com.tracfone.twist.context;

//JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;

public class OnCommercePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private final Properties properties = new Properties("Net10");
	
	public OnCommercePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(properties.getString("NT.Commerce"));
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