package com.tracfone.twist.byop;

//JUnit Assert framework can be used for verification

import junit.framework.Assert;
import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BYOPAPN {
	
	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public BYOPAPN() {

	}

	public void goToAPNSettings() throws Exception {
		TwistUtils.setDelay(15);
		myAccountFlow.clickLink("APN Settings");
	}

	public void selectOperatingSystem(String operatingSystem) throws Exception {
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc\\d/", operatingSystem);
	}

	public void checkAPNSettingsTable() throws Exception {
		String apnSettings = myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:[a-zA-Z]{2}\\d/").getText();
		String apnTable = myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d::db/").getText();
		Assert.assertTrue(!apnSettings.contains("SCRIPT MISSING") && myAccountFlow.cellVisible("Find your APN Settings")
				&& myAccountFlow.cellVisible("Apn Settings Table") && !apnSettings.isEmpty() && apnTable.contains("MMS"));
		
		myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/").uncheck();
		myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2/").uncheck();
		myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbcE1/").uncheck();
		myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbcE2/").uncheck();
		
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/");
		pressButton("Send SMS");
		Assert.assertTrue(myAccountFlow.cellVisible("/SMS sent to \\d/"));
		
		if(myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2/").fetch("disabled").equalsIgnoreCase("false")){
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2/");
		pressButton("Send SMS");
		Assert.assertTrue(myAccountFlow.cellVisible("Both the APN settings link and the APN settings information were sent to the phone number provided."));
		}
		
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11/", "test@tracfone.com");
		
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbcE1/");
		pressButton("Send Email");
		Assert.assertTrue(myAccountFlow.cellVisible("Email sent to test@tracfone.com"));
		
		if(myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbcE2/").fetch("disabled").equalsIgnoreCase("false")){
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbcE2/");
		pressButton("Send Email");
		Assert.assertTrue(myAccountFlow.cellVisible("Both the APN settings link and the APN settings information were sent to the email address provided."));
		}
		
	}

	public void pressButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void selectOperatingSystemAndOSVersions(String operatingSystem, String osVersion)
				throws Exception {
	//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", operatingSystem);
	//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", osVersion);
	}

	public void selectHandsetManufacturerAndHandsetModels(String handsetManufacturer,
			String handsetModel) throws Exception {
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", handsetManufacturer);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", handsetModel);
	}

	public void sendAPNInfoAndCheckForAPNSettingsTable() throws Exception {
		Assert.assertTrue(myAccountFlow.h2Visible("Find your APN Settings") && myAccountFlow.h2Visible("APN Settings Table") );
		//		&& myAccountFlow.spanVisible("APN") && myAccountFlow.spanVisible("MMS_APN"));
		myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/").check();
	//	myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2/").check();
	//	myAccountFlow.pressSubmitButton("Send SMS");
	//	Assert.assertFalse("SMS SENT FAILED",myAccountFlow.cellVisible("Failed."));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "test@tracfone.com");
		pressButton("Send Email");
		Assert.assertTrue(myAccountFlow.cellVisible("Email Sent."));
	}

}