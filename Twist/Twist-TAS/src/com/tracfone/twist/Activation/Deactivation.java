package com.tracfone.twist.Activation;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.DeactivationPhoneFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Deactivation {

	private DeactivationPhoneFlow deactivationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	public Deactivation() {

	}

	public void selectDeactivationOption() throws Exception {
		TwistUtils.setDelay(5);
		deactivationPhoneFlow.clickLink("Transactions");
		deactivationPhoneFlow.clickLink("Deactivation");
		TwistUtils.setDelay(5);
	}

	public void selectADeactivationReason(String reason) throws Exception {
	
		if(deactivationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/")){
			deactivationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/");
			if(deactivationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/")){
				deactivationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/");
			}
			if (deactivationPhoneFlow.buttonVisible("Continue")) {
				deactivationPhoneFlow.pressButton("Continue");
			} else if (deactivationPhoneFlow.submitButtonVisible("Continue")){
				deactivationPhoneFlow.pressSubmitButton("Continue");
			}
		}
		
		String[] reasonPartial =  reason.split(" ");
		deactivationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/","/"+reasonPartial[0]+".*?/");
		
		if (deactivationPhoneFlow.buttonVisible("Deactivate")) {
			deactivationPhoneFlow.pressButton("Deactivate");
		} else {
			deactivationPhoneFlow.pressSubmitButton("Deactivate");
		}
	}

	public void verifyMessageThatLineHasBeenSuccessfullyDeactivated() throws Exception {
		Assert.assertTrue(deactivationPhoneFlow.h2Visible("Transaction Summary")||deactivationPhoneFlow.divVisible("Your phone has been deactivated."));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		deactivationPhoneFlow = tracfoneFlows.deactivationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void deactivatePhoneIfPhoneNeedsToBeTested(String reason)
			throws Exception {
		if(!reason.equalsIgnoreCase("active")){
			selectDeactivationOption();
			selectADeactivationReason(reason);
		}	
	}

	public void updateServiceEndDateToPast() throws Exception {
		phoneUtil.setDateInPast(esnUtil.getCurrentESN().getEsn());
		phoneUtil.setEsnServiceEndDatetoPast(esnUtil.getCurrentESN().getEsn(), 10);
	}

	public void deactivateEsnForBrandsUsesActFlowForPort() throws Exception {
		deactivationPhoneFlow.clickLink("Deactivation");
		selectADeactivationReason("PORT CANCEL Line Status : RETURNED");
		verifyMessageThatLineHasBeenSuccessfullyDeactivated();
		pressButton("Refresh");
		deactivationPhoneFlow.clickLink("/Activation.*/");
	}
	
	public void pressButton(String buttonType) {
		if (deactivationPhoneFlow.submitButtonVisible(buttonType)) {
			deactivationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			deactivationPhoneFlow.pressButton(buttonType);
		}
	}
}