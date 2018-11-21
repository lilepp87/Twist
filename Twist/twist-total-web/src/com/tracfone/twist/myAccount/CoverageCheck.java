package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;

	public CoverageCheck() {
		
	}
	
	public void goToCoverage() throws Exception {
		myAccountFlow.clickLink("Coverage Maps[1]");
		Assert.assertTrue(myAccountFlow.imageVisible("exifviewer-img-1"));
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}