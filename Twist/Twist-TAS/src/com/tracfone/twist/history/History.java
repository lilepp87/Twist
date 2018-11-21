package com.tracfone.twist.history;

// JUnit Assert framework can be used for verification

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class History {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;

	public History() {

	}

	public void checkActivationHistory() throws Exception {
		String currentDate = dateFormat.format(new Date());
		myAccountFlow.clickLink("History");
		myAccountFlow.clickLink("Action Item History by ESN");
		// Assert.assertTrue(myAccountFlow.cellVisible(currentDate));
		Assert.assertTrue(myAccountFlow.cellVisible("Activation"));
		Assert.assertTrue(myAccountFlow.cellVisible("Succeeded"));
		myAccountFlow.clickLink("Transaction History");
		Assert.assertTrue(myAccountFlow.cellVisible("ACTIVATION"));
		Assert.assertTrue(myAccountFlow.cellVisible("Completed"));
		myAccountFlow.clickLink("Line History");
		pressButton("Search");
		Assert.assertTrue(myAccountFlow.cellVisible("LINE_BATCH"));
		Assert.assertTrue(myAccountFlow.cellVisible("MSID UPDATE"));
		Assert.assertTrue(myAccountFlow.cellVisible("ACTIVATE"));
		pressButton("Search");
		myAccountFlow.clickLink("Redemption Summary");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:id1::content/", currentDate);
		pressButton("Search");
		Assert.assertTrue(myAccountFlow.labelVisible("PAID REDEMPTIONS"));
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Redemption");
	}

	public void pressButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void checkForCompensationUnitsHistory() throws Exception {
		pressButton("Process");
		Assert.assertTrue("Add compensation failed", myAccountFlow.h2Visible("Transaction Summary"));
		myAccountFlow.clickLink("History");
		myAccountFlow.clickLink("Comp/Repl History");
		Assert.assertTrue(myAccountFlow.cellVisible("COMPENSATION"));
		myAccountFlow.clickLink("Transactions");
	}

	public void checkForMinChangeHistory() throws Exception {
		myAccountFlow.clickLink("History");
		myAccountFlow.clickLink("Line History");
		pressButton("Search");
		Assert.assertTrue(myAccountFlow.cellVisible("DEACTIVATE"));
		myAccountFlow.clickLink("Phone History");
		pressButton("Search");
		Assert.assertTrue(myAccountFlow.cellVisible("DEACTIVATE"));
	}

	public void clickRecentInteractions(String reason, String detail, String channel) throws Exception {
		myAccountFlow.clickLink("Recent History");
		myAccountFlow.clickLink("Interactions");
		pressButton("Create Interaction");
		if(myAccountFlow.submitButtonVisible("NO")){
			myAccountFlow.pressSubmitButton("NO");
			TwistUtils.setDelay(5);
		}
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/", reason);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc2::content/", detail);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4::content/", channel);
		myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1::content/", "ITQ Testing 123");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/", "Call Completed");
		pressButton("Create Interaction");
		
	}

	public void validateInteractions() throws Exception {
		Assert.assertTrue(myAccountFlow.spanVisible("ITQ Testing 123"));
		// Assert.assertTrue(myAccountFlow.cellVisible(esnUtil.getCurrentESN().toString()));
	}
}
