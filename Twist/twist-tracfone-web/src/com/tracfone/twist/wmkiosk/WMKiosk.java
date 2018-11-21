package com.tracfone.twist.wmkiosk;

// JUnit Assert framework can be used for verification

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class WMKiosk {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static Logger logger = Logger.getLogger(WMKiosk.class);
	
	private Properties properties = new Properties();
	
	public WMKiosk() {
		
	}

	public void goToWalmartKiosk(String brand) throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("TF_URL") + "/walmart");
		activationPhoneFlow.clickLink(brand);
		activationPhoneFlow.pressButton(properties.getString("continue2"));
	}
	
	public void goToRadioshackKiosk(String brand) throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("TF_URL") + "/radioshack");
		activationPhoneFlow.clickLink(brand);
	}

	public void goToSimplyWirelessKiosk(String brand) throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("TF_URL") + "/simplywireless");
		activationPhoneFlow.clickLink(brand);
	}

	public void enterEsnSimZipAndPin(String esnpart, String simpart, String zip, String pinpart) throws Exception {
		String esn = phoneUtil.getNewEsnByPartNumber(esnpart);
		logger.warn(esn);
		ESN esn1 = new ESN(esnpart, esn);
		esnUtil.setCurrentESN(esn1);
		activationPhoneFlow.typeInTextField("esn", esn);
		activationPhoneFlow.typeInTextField("zip", zip);
		
		if (!pinpart.isEmpty()) {
			String pin = phoneUtil.getNewPinByPartNumber(pinpart);
			activationPhoneFlow.typeInTextField("pin", pin);
		}
		
		if (!simpart.isEmpty()) {
			String sim = simUtil.getNewSimCardByPartNumber(simpart);
			phoneUtil.addSimToEsn(sim, esn1);	
		}

		activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
	}
	
	public void completeTheActivation(String cellTech) throws Exception {
		Assert.assertTrue(activationPhoneFlow.h1Visible(properties.getString("orderSummary")));
		activationPhoneFlow.pressButton(properties.getString("continue2"));
		
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, cellTech, NEW_STATUS, "WM Kiosk Activate");
		phoneUtil.clearOTAforEsn(esn.getEsn());
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
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

	public void goToGamestopKiosk(String brand) throws Exception {
			activationPhoneFlow.navigateTo(properties.getString("TF_URL") + "/gamestop");
			activationPhoneFlow.clickLink(brand);	
	}

	
}
