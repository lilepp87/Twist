package com.tracfone.twist.portin;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

//import net.sf.sahi.client.Browser;

public class PortIn {

	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public PortIn() {

	}

	public void goToPort() throws Exception {
		activationPhoneFlow.clickLink("PORT");
	}

	public void enterEsnSimProviderAndPin(String toPart, String sim, String fromProvider, String pin) 
			throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		String fromPhone = phoneUtil.getMinOfActiveEsn(fromEsn.getEsn());
		
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		String providerStr = getFromProviderStr(fromProvider);
		String esn = esnUtil.getCurrentESN().getEsn();
		
		ESN toEsn = enterToEsnAndSim(toPart, sim, fromEsn);
		toEsn.setPin(newPin);
		toEsn.setTransType("UDP Port In");
		
		activationPhoneFlow.typeInTextField("displayPhoneNumber", fromPhone);
		activationPhoneFlow.typeInTextField("zipCode", "33178");
		activationPhoneFlow.chooseFromSelect("currentProvider", providerStr);		
		activationPhoneFlow.typeInTextField("currentEsnOrSim", esn);
		if (activationPhoneFlow.textboxVisible("redemptionPin")) {
			activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		}

		if (activationPhoneFlow.buttonVisible("Process Activation")) {
			activationPhoneFlow.pressButton("Process Activation");
		} else if (activationPhoneFlow.buttonVisible("btnContinue")) {
			activationPhoneFlow.getBrowser().button("btnContinue").focus();
			activationPhoneFlow.pressButton("btnContinue");
		}
	}

	private ESN enterToEsnAndSim(String toPart, String sim, ESN fromEsn) {
		ESN toEsn;
		if (toPart.startsWith("PH")) {
			String esn = phoneUtil.getNewByopEsn(toPart, sim);
			toEsn = new ESN(toPart, esn);
			String newSim = phoneUtil.getSimFromEsn(esn);
			toEsn.setSim(newSim);
			
			activationPhoneFlow.typeInTextField("portSim", newSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(toPart);
			toEsn = new ESN(toPart, esn);
			if (activationPhoneFlow.textboxVisible("portEsn")) {
				activationPhoneFlow.typeInTextField("portEsn", esn);
			}
			
			if (!sim.isEmpty()) {
				String newSim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(newSim, toEsn);
				activationPhoneFlow.typeInTextField("portSim", newSim);
			}
		}
		
		toEsn.setFromEsn(fromEsn);
		esnUtil.setCurrentESN(toEsn);
		
		return toEsn;
	}

	private String getFromProviderStr(String fromprovider) {
		String providerStr;

		if ("simple mobile".equalsIgnoreCase(fromprovider)) {
			providerStr = "SIMPLE_MOBILE";
		} else if ("net10".equalsIgnoreCase(fromprovider)) {
			providerStr = "NET10";
		} else if ("telcel".equalsIgnoreCase(fromprovider)) {
			providerStr = "TELCEL_AMERICA";
		} else {
			throw new IllegalArgumentException("Could not find from provider");
		}
		
		return providerStr;
	}

	public void enterDetails() throws Exception {
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.textboxVisible("currentProviderAccount")) {
			TwistUtils.setDelay(45);
		}
		activationPhoneFlow.typeInTextField("currentProviderAccount", "12345678-12345");
		activationPhoneFlow.typeInTextField("currentProviderPassword", "1234");
		activationPhoneFlow.typeInTextField("firstName", "TwistFirstname");
		activationPhoneFlow.typeInTextField("lastName", "TwistLastname");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("city", "MOUNTAIN VIEW");
		activationPhoneFlow.typeInTextField("phoneNumberDisplay", TwistUtils.generateRandomMin());
		activationPhoneFlow.chooseFromSelect("state", "California");
		activationPhoneFlow.typeInTextField("zip", "94043");
		activationPhoneFlow.typeInTextField("email", "abc@aol.com");
		activationPhoneFlow.typeInTextField("ssn", "1111");

		activationPhoneFlow.getBrowser().button("btnContinue").focus();
		activationPhoneFlow.pressSubmitButton("btnContinue");
	}

	public void checkTransactionSummary() throws Exception {
		TwistUtils.setDelay(7);
		Assert.assertTrue(activationPhoneFlow.h3Visible("Number Portability: Steps to complete your port"));
		phoneUtil.runIGateIn();
		activationPhoneFlow.pressButton("Continue");
		
		//ESN toEsn = esnUtil.getCurrentESN();
		//phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
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

	public void selectPortANumber() throws Exception {
		activationPhoneFlow.selectRadioButton("actProcessType1");
	}

	public void enterEsnSimProviderAndPinForExternalPort(String toPart,
			String sim, String fromProvider, String pin) throws Exception {
		ESN toEsn;
		String newPin = phoneUtil.getNewPinByPartNumber(pin);
		
		if (toPart.startsWith("PH"))  {
			String esn = phoneUtil.getNewByopEsn(toPart, sim);
			toEsn = new ESN(toPart, esn);
			String toSim = phoneUtil.getSimFromEsn(esn);
			toEsn.setSim(toSim);
			if (activationPhoneFlow.textboxVisible("portEsn")) {
				activationPhoneFlow.typeInTextField("portEsn", esn);
			}
			activationPhoneFlow.typeInTextField("portSim", toSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(toPart);
			toEsn = new ESN(toPart, esn);
			if (activationPhoneFlow.textboxVisible("portEsn")) {
				activationPhoneFlow.typeInTextField("portEsn", esn);
			}
			
			if (!sim.isEmpty()) {
				String toSim = simUtil.getNewSimCardByPartNumber(sim);
				phoneUtil.addSimToEsn(toSim, toEsn);
				activationPhoneFlow.typeInTextField("portSim", toSim);
			}
		}
		
		esnUtil.setCurrentESN(toEsn);
		
		System.out.println("ESN: " + toEsn.getEsn());
		System.out.println("PIN: " + newPin);
				
		toEsn.setPin(newPin);
		toEsn.setTransType("UDP Port In");
		
		String min = TwistUtils.generateRandomMin();
		esnUtil.getCurrentESN().setMin(min);
		
		activationPhoneFlow.typeInTextField("displayPhoneNumber",min);
		activationPhoneFlow.chooseFromSelect("currentProvider" , fromProvider);
		activationPhoneFlow.typeInTextField("redemptionPin",newPin);

		if (activationPhoneFlow.buttonVisible("Process Activation")) {
			activationPhoneFlow.pressButton("Process Activation");
		} else if (activationPhoneFlow.buttonVisible("btnContinue")) {
			activationPhoneFlow.getBrowser().button("btnContinue").focus();
			activationPhoneFlow.pressButton("btnContinue");
		}
	}

}