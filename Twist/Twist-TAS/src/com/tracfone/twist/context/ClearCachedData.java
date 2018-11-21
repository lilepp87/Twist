package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class ClearCachedData {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public ClearCachedData() {
		
	}

	public void setUp() throws Exception {
		//This method is executed before the scenario execution starts.
		esnUtil.clearCurrentESN();
	}

	public void tearDown() throws Exception {
		//This method is executed after the scenario execution finishes.
		esnUtil.clearCurrentESN();
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
}
