package com.tracfone.twist.account;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.TwistUtils;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;
	
	private Properties properties = new Properties();
	
	public CoverageCheck() {
		
	}

	public void goToCoverageAndEnterZip(String zip) throws Exception {
		myAccountFlow.clickLink(properties.getString("serviceAreaMap"));
		myAccountFlow.typeInTextField("zip", zip);
		myAccountFlow.pressSubmitButton(properties.getString("continue"));
	}

	public void selectAPhoneAndCheckForMaps() throws Exception {
		myAccountFlow.clickImage("small.gif");
		TwistUtils.setDelay(10);
		Assert.assertFalse("something problem with maps. It's Still loading", myAccountFlow.cellVisible("Loading"));
		myAccountFlow.getBrowser().domain("demo.cellmaps.com")
		.image("CellMaps.Control.CMZoomBar_12_zoomin_innerImage")
		.click();
	}
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
}