package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnGoSmartHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");

	public OnGoSmartHomePage() {
	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(props.getString("GS.HomePage"));
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
