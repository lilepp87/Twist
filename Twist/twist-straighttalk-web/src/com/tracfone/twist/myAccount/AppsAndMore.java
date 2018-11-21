package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
//TODO: delete
public class AppsAndMore {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	public AppsAndMore() {

	}

	public void checkForAppsAndMoreInsideMyAccount() throws Exception {
		activationPhoneFlow.clickLink("Apps & More[1]");
		enterMinAndCheckApps();
		activationPhoneFlow.clickLink("Apps & More");
		enterMinAndCheckApps();
	}

	public void checkForAppsAndMoreOutsideMyAccount() throws Exception {
		activationPhoneFlow.clickLink("Logout");
		activationPhoneFlow.clickLink("Apps & More");
		enterMinAndCheckApps();
	}

	private String enterMinAndCheckApps() {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);

		activationPhoneFlow.typeInTextField("input_phone", min);
		activationPhoneFlow.pressButton("Continue");
		activationPhoneFlow.pressSubmitButton("Continue");
		//TODO Check that you are on the right page
//		activationPhoneFlow.clickLink("Back");
		return min;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}