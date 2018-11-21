package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.TwistUtils;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;

	private Properties properties = new Properties();
	
	public CoverageCheck() {

	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void goToCoverageAndEnterZip(String zip) throws Exception {
		myAccountFlow.clickLink(properties.getString("coverage"));
		myAccountFlow.typeInTextField("zip", zip);
		//myAccountFlow.pressSubmitButton(properties.getString("continue"));
		//myAccountFlow.pressSubmitButton("zip_btn");
		myAccountFlow.pressSubmitButton("CONTINUE");
	}

	public void selectAPhoneAndCheckForMaps() throws Exception {
		myAccountFlow.pressButton("SELECT A DEVICE");
		myAccountFlow.clickImage("small.gif");
		TwistUtils.setDelay(20);
		Assert.assertFalse("something problem with maps. It's Still loading",myAccountFlow.cellVisible("Loading"));
		myAccountFlow.getBrowser().domain("tracfone.cellmaps.com").image("CellMaps_Control_CMZoomBar_12_OpenLayers_Map_16_innerImage").click();
		Assert.assertTrue(myAccountFlow.linkVisible("CHANGE ZIP CODE"));
		Assert.assertTrue(myAccountFlow.linkVisible("REDO SEARCH"));
	}

}