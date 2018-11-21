package com.tracfone.twist.phoneupgrade;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class PhoneUpgrade {
	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	
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

	public void goToPhoneUpgrade() throws Exception {
		activationPhoneFlow.clickLink("Phone Upgrade");
	}

	public void enterFromESNAndMIN() throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		String fromPhone = phoneUtil.getMinOfActiveEsn(fromEsn.getEsn());
		
		activationPhoneFlow.typeInTextField("fromEsnOrSim", fromEsn.getEsn());
		activationPhoneFlow.typeInTextField("fromPhoneNumber", fromPhone);
		activationPhoneFlow.pressSubmitButton("submitBtn");
	}

	public void enterToESNWithSIMAndStatus(String esnPart, String toSimPart, String status) throws Exception {
		ESN toESN;
		if (esnPart.startsWith("PHSM")) {
			String esn = phoneUtil.getNewByopEsn(esnPart, toSimPart);
			toESN = new ESN(esnPart, esn);
			String newToSim = phoneUtil.getSimFromEsn(esn);
			toESN.setSim(newToSim);
			
			activationPhoneFlow.typeInTextField("toEsnOrSim", newToSim);
		} else {
			String esn = phoneUtil.getNewEsnByPartNumber(esnPart);
			toESN = new ESN(esnPart, esn);
			activationPhoneFlow.typeInTextField("toEsnOrSim", esn );
			
			if (!toSimPart.isEmpty()) {
				String newToSim = simUtil.getNewSimCardByPartNumber(toSimPart);
				phoneUtil.addSimToEsn(newToSim, toESN);
				//activationPhoneFlow.pressSubmitButton("submitBtn");
//				activationPhoneFlow.typeInTextField("toSimNumber", newToSim);
			}
		}
		activationPhoneFlow.pressSubmitButton("submitBtn");
	}

	public void checkForSuccessfulPhoneUpgrade() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h3Visible("Phone Upgrade"));
		Assert.assertTrue(activationPhoneFlow.labelVisible("Transaction Summary"));
		activationPhoneFlow.pressSubmitButton("submitBtn");
	}
	
}
