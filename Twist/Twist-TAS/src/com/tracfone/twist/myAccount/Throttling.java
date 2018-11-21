package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.Activation.ActivatePhone;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class Throttling {

	
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public Throttling() {
		
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void throttleEsnOnPolicy(String policyName) throws Exception {
		phoneUtil.createThrottleRecordforEsn(esnUtil.getCurrentESN().getEsn(),policyName);
		phoneUtil.updateThrottleTransactionStatus(esnUtil.getCurrentESN().getEsn());
	}

}
