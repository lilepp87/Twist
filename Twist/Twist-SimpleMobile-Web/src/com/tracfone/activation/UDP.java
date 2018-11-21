package com.tracfone.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;
import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class UDP {

	private Browser browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	
	public UDP() {

	}

	//TODO: Use UDP Code
	public void goTo(String brand) throws Exception {
		activationPhoneFlow.navigateTo(rb.getString("sm.udp"));
		activationPhoneFlow.typeInTextField("j_username", "admin@aol.com");
		activationPhoneFlow.typeInPasswordField("j_password", "abc123");
		activationPhoneFlow.pressSubmitButton("Login");	
		if (brand.equals("tracfone")) {
			activationPhoneFlow.clickLink("Tracfone");
		} else {
			activationPhoneFlow.clickLink(brand.toLowerCase());
		}
	}

	public void goToActivationFor(String brand) throws Exception {
		if (brand.equals("tracfone")) {
			activationPhoneFlow.clickLink("Phone Activation");
		} else {
			activationPhoneFlow.clickLink("SIM Activation (GSM)");
		}
	}

	public void enterEsnSimZipPin(String esnPart, String sim, String zip, String pin) throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		ESN newEsn;
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		
		if (esnPart.startsWith("PHSM") || esnPart.startsWith("SM"))  {
			String esn = phoneUtil.getNewByopEsn(esnPart, sim);
			newEsn = new ESN(esnPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			newEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("sim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			newEsn = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("esn", esn);
			
			if (!sim.isEmpty()) {
				String newsim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newsim, newEsn);
				activationPhoneFlow.typeInTextField("sim", newsim);
			}
		}
		
		esnUtil.setCurrentESN(newEsn);
		if (fromEsn != null) {
			esnUtil.getCurrentESN().setEmail(fromEsn.getEmail());
			esnUtil.getCurrentESN().setPassword(fromEsn.getPassword());
			System.out.println("Email:" + newEsn.getEmail() );
			System.out.println("Password:" + newEsn.getPassword());
		}
		System.out.println("ESN: " + newEsn.getEsn());
		System.out.println("PIN: " + newPin);
		System.out.println("SIM:" + newEsn.getSim());

		newEsn.setPin(newPin);
		
		activationPhoneFlow.typeInTextField("actZip", zip);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.getBrowser().emailbox("createEmail").setValue(randomEmail);
	} 

	public void confirmActivationForCellTech(String cellTech) throws Exception {
		activationPhoneFlow.pressButton("Continue");
		TwistUtils.setDelay(10);
		if (activationPhoneFlow.buttonVisible("yes")) {
	//		This will click 'OK' on the Mexico roaming popup
			activationPhoneFlow.pressButton("yes");
		}
		Assert.assertTrue(activationPhoneFlow.h2Visible("Steps to Complete your Activation"));
		ESN esn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(esn, cellTech, "New", "UDP Activation with PIN");
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
}
