package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class OnCSRHomePage {

	private MyAccountFlow myAccountFlow;

	public OnCSRHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo("https://sitcommerce.straighttalk.com/webapp/wcs/stores/servlet/LogonForm?catalogId=10001&myAcctMain=1&langId=-1&storeId=10154");
	}

	public void tearDown() throws Exception {

	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}