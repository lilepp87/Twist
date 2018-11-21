package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;
import net.sf.sahi.client.Browser;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

public class CoverageCheck {

	private MyAccountFlow myAccountFlow;

	public CoverageCheck() {

	}

	public void goToCoverage() throws Exception {
		myAccountFlow.clickLink("Service Area Map");
	}

	public void checkForMaps() throws Exception {
		Browser browser = myAccountFlow.getBrowser();
		browser.domain("maps.smsdealerportal.com").link("+").click();
		TwistUtils.setDelay(10);
		browser.domain("maps.smsdealerportal.com").textbox("geocomplete").setValue("33178");
		browser.domain("maps.smsdealerportal.com").button("Search").click();
		TwistUtils.setDelay(20);
		boolean strVisible = browser.div("Service Area Map The network is nationwide. With SIMPLE Mobile, " +
				"you're part of one of the country's largest 4G networks. Sign Up").isVisible();
		Assert.assertTrue(strVisible);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}



	public void checkMapChange() throws Exception {
		//Map takes a few seconds to load the first time
		TwistUtils.setDelay(20);
		assertTrue(myAccountFlow.labelVisible("Full Service Dealers"));
		assertTrue(myAccountFlow.getBrowser().checkbox("fullServiceCheck").checked());
		assertTrue(myAccountFlow.labelVisible("ReUp Locations"));
		assertTrue(myAccountFlow.labelVisible("National Retailers"));
		assertTrue(myAccountFlow.labelVisible("Simple Mobile Solutions"));
		
		myAccountFlow.typeInTextField("searchOrigin", "33178");
		myAccountFlow.pressSubmitButton("Search");
		assertTrue(myAccountFlow.spanVisible("MAP"));
//		Assert.assertFalse(myAccountFlow.imageVisible("resultIcon3"));
//		myAccountFlow.chooseFromSelect("searchRadius", "15 Miles");
//		myAccountFlow.clickDivMessage("SEARCH");
		//Make sure fourth result is now visible
//		TwistUtils.setDelay(3);
//		Assert.assertTrue(myAccountFlow.imageVisible("resultIcon3"));
	}

	public void goToRetailers() throws Exception {
		myAccountFlow.getBrowser().link("Retailers").click();
	}

}