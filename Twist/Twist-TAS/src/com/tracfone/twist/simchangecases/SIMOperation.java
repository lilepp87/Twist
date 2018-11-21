package com.tracfone.twist.simchangecases;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class SIMOperation {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;

	public SIMOperation() {

	}

	public void goToSimMarriage() throws Exception {
		myAccountFlow.clickCellMessage("LTE");
		myAccountFlow.clickLink("SIM Marriage");
	}

	public void enterSimNumberAndMarryWithEsnAndZip(String sim , String zipCode) throws Exception {
		String newSim = simUtil.getNewSimCardByPartNumber(sim);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", newSim);
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zipCode);
		}
		pressButton("Marry");
		Assert.assertTrue(myAccountFlow.cellVisible("ESN AND SIM HAVE BEEN SUCCESSFULLY MARRIED") || myAccountFlow.cellVisible("SIM MARRIED SUCCESSFULLY"));
		pressButton("Refresh");
		String simNumber = myAccountFlow.getBrowser().cell(newSim).getText();
		Assert.assertTrue(newSim.equalsIgnoreCase(simNumber));
	}

	public void goToSimChange() throws Exception {
		TwistUtils.setDelay(12);
		myAccountFlow.clickCellMessage("LTE");
		myAccountFlow.clickLink("SIM Change");
	}

	public void simChangeZip(String sim, String zipCode) throws Exception {
		String newSim = simUtil.getNewSimCardByPartNumber(sim);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", newSim);
		myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zipCode);
		pressButton("Process");
		TwistUtils.setDelay(25);
		Assert.assertTrue(myAccountFlow.h2Visible("Transaction Summary"));
		pressButton("Refresh");
		String changeSim = myAccountFlow.getBrowser().cell(newSim).getText();
		Assert.assertTrue(newSim.equalsIgnoreCase(changeSim));
	}

	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

}