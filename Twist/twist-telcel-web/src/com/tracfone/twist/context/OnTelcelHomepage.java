package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;

public class OnTelcelHomepage {
	
	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	public static String TC_URL;
	
	private Properties properties = new Properties();

	public OnTelcelHomepage() {
		TC_URL = properties.getString("TC_URL");
	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(properties.getString("TC_URL"));
		if (myAccountFlow.linkVisible("Español")) {
			myAccountFlow.clickLink("Español");			
		}
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
