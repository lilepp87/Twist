package com.tracfone.twist.impl.eng.myAccount;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.TwistUtils;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;
	private Properties properties = new Properties("Net10");

	public CoverageCheck() {

	}

	public void goToCoverageAndEnterZip(String zip) throws Exception {
		myAccountFlow.clickLink(properties.getString("coverage"));
		myAccountFlow.typeInTextField("zip", zip);
		myAccountFlow.pressSubmitButton(properties.getString("coverageContinue"));
	}

	public void selectAPhoneAndCheckForMaps() throws Exception {
		myAccountFlow.selectRadioButton("phone_type");
		myAccountFlow.clickImage("small.gif[2]");
		TwistUtils.setDelay(10);
		Assert.assertFalse("something problem with maps. It's Still loading", myAccountFlow.cellVisible("Loading"));
		myAccountFlow.getBrowser().domain("tracfone.cellmaps.com")
				.image("CellMaps_Control_CMZoomBar_12_OpenLayers_Map_16_innerImage")
				.click();
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}