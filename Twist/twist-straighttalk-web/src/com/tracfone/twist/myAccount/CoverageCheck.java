package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;

	public CoverageCheck() {
		
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void goToCoverageAndEnterZip(String zip) throws Exception {
		//myAccountFlow.clickLink("Service Area Map");
		myAccountFlow.clickLink("Coverage Map");
		myAccountFlow.typeInTextField("zip", zip);
		//myAccountFlow.pressSubmitButton("Continue");
		myAccountFlow.pressButton("zip_btn");
	}

	public void selectAPhoneAndCheckForMaps() throws Exception {
		TwistUtils.setDelay(80);
		myAccountFlow.clickLink("purchasingPhone");
		TwistUtils.setDelay(20);
		myAccountFlow.clickImage("small.gif");
		TwistUtils.setDelay(15);
		Assert.assertFalse("Map is still loading", myAccountFlow.cellVisible("Loading"));
		myAccountFlow.getBrowser().domain("tracfone.cellmaps.com").image("CellMaps_Control_CMZoomBar_12_OpenLayers_Map_16_innerImage").click();
		Assert.assertTrue(myAccountFlow.linkVisible("changeZipCode"));
		Assert.assertTrue(myAccountFlow.linkVisible("redoSearch"));
	}

}