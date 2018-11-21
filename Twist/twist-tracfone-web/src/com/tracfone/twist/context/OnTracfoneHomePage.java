package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;

public class OnTracfoneHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private Properties properties = new Properties();

	public OnTracfoneHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(properties.getString("TF_HOME"));
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
	}

	public void signOut() throws Exception {
		myAccountFlow.clickLink(properties.getString("buyAirtimeFromPhone"));

		if (myAccountFlow.linkVisible(properties.getString("signOut"))) {
			myAccountFlow.clickLink(properties.getString("signOut"));
		}
		myAccountFlow.navigateTo(properties.getString("TF_HOME"));
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}
