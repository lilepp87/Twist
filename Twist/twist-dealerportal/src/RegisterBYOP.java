//package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;


public class RegisterBYOP {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private final String ACTIVE = "Active";
	private final String YES = "Yes"; 
	private final String NO = "No";
	private final String HEX_ESN = "Hex";

	public RegisterBYOP() {
		
	}

	public void goToRegisterBYOPCDMAPage() throws Exception {
		activationPhoneFlow.clickLink("CDMA Registration");
	}

	public void enterNetworkAccessCode(String accessPin) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);
		activationPhoneFlow.typeInTextField("byopAccessCode", pin);
		TwistUtils.setDelay(7);
		activationPhoneFlow.pressSubmitButton("btnContinue");
		
		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Pin");
	}

	public void finishRegistration(String status) throws Exception {
		TwistUtils.setDelay(25);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("content_pod").containsText("Congratulations! Your phone has been registered in our system using your network access code."));
		
		if (ACTIVE.equalsIgnoreCase(status)) {
			activationPhoneFlow.pressSubmitButton("PORT NOW");
		} else {
			activationPhoneFlow.pressSubmitButton("ACTIVATE NOW");
		}

		phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}

	public void enterEsnAndCarrierForLteIphoneWithZip(String isActive, String esnType, String carrier,
			String isLTE, String iPhone5, String zip) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn;
		if (HEX_ESN.equalsIgnoreCase(esnType)) {
			esn = new ESN("NTBYOPHEX", esnStr);
			String hexEsn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField("byopEsn", hexEsn.toLowerCase());
		} else {
			esn = new ESN("NTBYOPDEC", esnStr);
			activationPhoneFlow.typeInTextField("byopEsn", esnStr);
		}
		esnUtil.setCurrentESN(esn);
		activationPhoneFlow.typeInTextField("byopZipCode", zip);
		activationPhoneFlow.chooseFromSelect("byopLteTech", isLTE);
		activationPhoneFlow.chooseFromSelect("byopIphone5", iPhone5);
		activationPhoneFlow.pressButton("Continue");

		//Try at most 10 times to edit ig_trans to W
		int edited = 0;
		for (int i = 0; i < 10 && edited == 0; i++) {
			TwistUtils.setDelay(3);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, isActive, isLTE, iPhone5, "No");
			System.out.println("edit: " + edited);
		}
		
		if (activationPhoneFlow.divVisible("ESN status is PENDING, please try again after some time")) {
            activationPhoneFlow.pressButton("Continue");
            TwistUtils.setDelay(16);
		}
		
		if ("Sprint".equalsIgnoreCase(carrier)) {
			if ("Yes".equalsIgnoreCase(iPhone5)) {
				//If Sprint and iPhone enter remove lte sim
				String simStr = TwistUtils.generateRandomSim();
				activationPhoneFlow.typeInTextField("input_sim", simStr);
			}
			if ("Active".equalsIgnoreCase(isActive)) {
				activationPhoneFlow.selectCheckBox("agree");
				activationPhoneFlow.getBrowser().button("yes").hover();
				activationPhoneFlow.getBrowser().button("yes").focus();
				activationPhoneFlow.pressButton("yes");
			}
		} else {
			//If Verizon enter if active, Sprint has its own API
			if ("Active".equalsIgnoreCase(isActive)) {
				activationPhoneFlow.chooseFromSelect("byopActive", "YES");
				activationPhoneFlow.selectCheckBox("byopPort");
			} else {
				activationPhoneFlow.chooseFromSelect("byopActive", "NO");
			}
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}
}