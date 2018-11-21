package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;

public class OAuth {

	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public OAuth() {
		
	}
	
	public void setPhoneUtil(PhoneUtil newPhoneUtil) {
		this.phoneUtil = newPhoneUtil;
	}
	
	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}

	public void generateOAuthFor(String app, String orderType) throws Exception {
		String AT =serviceUtil.genAuthfor(app, orderType);
		System.out.println(AT);
	}

}
