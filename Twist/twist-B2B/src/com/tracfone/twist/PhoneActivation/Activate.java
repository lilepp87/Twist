package com.tracfone.twist.PhoneActivation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Activate {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	public Activate() {

	}

	public void goToActivate() throws Exception {
		activationPhoneFlow.clickLink("Activate");
	}
	
	public void goToInactivePhone() throws Exception {
		activationPhoneFlow.clickLink("My Phones & Plans");
		activationPhoneFlow.clickLink("cancelled / inactive");
	}
	
	public void filterByEsn() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField("search", esn.getEsn());
		activationPhoneFlow.clickLink("buttonsearch arrow purple left");
	}
	
	public void activateInactivePhone() throws Exception {
		activationPhoneFlow.clickLink("Activate");
		activationPhoneFlow.clickLink("Yes, activate plan");
	}

	public void chooseActivatePhone() throws Exception {
		activationPhoneFlow.clickH1("Activate my phone");
	}

	public void chooseActivateSim() throws Exception {
		activationPhoneFlow.clickH1("Activate my SIM card");
	}

	public void choosePortPhone() throws Exception {
		activationPhoneFlow.clickH1("Transfer from another company");
	}
	
	public void chooseUpgradePhone() throws Exception {
		activationPhoneFlow.clickH1("Transfer my Clearway number");
	}
	
	public void getNewB2BEsn() throws Exception {
		ESN esn = esnUtil.getNewB2bEsn();
		esnUtil.setCurrentESN(esn);
	}

	public void getNewB2BByopEsn() throws Exception {
		ESN esn = esnUtil.getNewB2bByopEsn();
		esnUtil.setCurrentESN(esn);
	}
	
	public void getActiveB2BEsn() throws Exception {
		ESN esn = esnUtil.popActiveB2bEsn();
		esnUtil.setCurrentESN(esn);
	}

	public void getNewB2BEsn(String type) throws Exception {
		if ("BYOP".equalsIgnoreCase(type)) {
			 getNewB2BByopEsn();
		} else {
			getNewB2BEsn();
		}
	}

	public void fillOutEsnZip(String zip) throws Exception {
		activationPhoneFlow.typeInTextField("esn", esnUtil.getCurrentESN().getEsn());
		fillOutNameZip(zip);
	}
	
	public void fillOutSimZip(String zip) throws Exception {
		activationPhoneFlow.typeInTextField("sim", esnUtil.getCurrentESN().getEsn());
		fillOutNameZip(zip);
	}
	
	public void fillOutNameZip(String zip) throws Exception {
		activationPhoneFlow.typeInTextField("fname", TwistUtils.generateName(6));
		activationPhoneFlow.typeInTextField("lname", TwistUtils.generateName(8));
		activationPhoneFlow.typeInTextField("zipcode", zip);
		activationPhoneFlow.clickLink("continue");
	}
	
	public void fillOutFromPhoneAndZip(String brand, String partNum, String zip) throws Exception {
		activationPhoneFlow.typeInTextField("fname", TwistUtils.generateName(6));
		activationPhoneFlow.typeInTextField("lname", TwistUtils.generateName(8));
		
		ESN toEsn = esnUtil.getCurrentESN();
		String fromEsn = phoneUtil.getActiveEsnToUpgradeFrom(partNum, toEsn.getEsn());
		toEsn.setFromEsn(new ESN(partNum, fromEsn));
		
		String min = phoneUtil.getMinOfActiveEsn(fromEsn);
		activationPhoneFlow.typeInTextField("phonetransfer", min);
		activationPhoneFlow.typeInTextField("newsimsrno", toEsn.getEsn());
		activationPhoneFlow.typeInTextField("zipcode", zip);
		activationPhoneFlow.chooseFromSelect("currentProvider", brand.toUpperCase() + " (Wireless)");
		activationPhoneFlow.typeInTextField("email", toEsn.getEmail());
		activationPhoneFlow.typeInTextField("altphone", TwistUtils.generateRandomMin());
		activationPhoneFlow.clickLink("continue");
	}

	public void checkSuccessfulActivation() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.h2Visible("You have successfully completed activation of your Clearway Wireless phone."));
		esnUtil.addActiveB2bEsn(esnUtil.getCurrentESN());
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