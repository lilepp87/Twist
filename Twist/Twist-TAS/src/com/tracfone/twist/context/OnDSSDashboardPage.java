package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class OnDSSDashboardPage {

	private MyAccountFlow myAccountFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");

	public OnDSSDashboardPage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(props.getString("DSS.dashboard_url"));
		myAccountFlow.typeInTextField("j_username", props.getString("DSS.dashboard_username"));
		myAccountFlow.typeInPasswordField("j_password", props.getString("DSS.dashboard_password"));
		myAccountFlow.pressSubmitButton("Submit");

	}

	public void tearDown() throws Exception {

	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}
