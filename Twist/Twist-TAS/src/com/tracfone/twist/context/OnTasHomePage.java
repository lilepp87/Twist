package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnTasHomePage {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");

	public OnTasHomePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		if (myAccountFlow.linkVisible("Logout")) {
			myAccountFlow.clickLink("Logout");
			myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		}
		if(myAccountFlow.getBrowser().password("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/").isVisible()){
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", props.getString("TAS.UserName"));
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", props.getString("TAS.Password"));
		}
		if (myAccountFlow.submitButtonVisible(props.getString("TAS.Login"))) {
			myAccountFlow.pressSubmitButton(props.getString("TAS.Login"));
		} else if(myAccountFlow.buttonVisible(props.getString("TAS.Login"))){
			myAccountFlow.pressButton(props.getString("TAS.Login"));
		}
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