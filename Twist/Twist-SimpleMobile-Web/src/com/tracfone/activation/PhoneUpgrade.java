package com.tracfone.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

import net.sf.sahi.client.Browser;

public class PhoneUpgrade {
	
	private static SimUtil simUtil;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	public ActivationReactivationFlow activationPhoneFlow;
	private static final String NEW_STATUS = "New";
	private static final String ACTIVE_STATUS = "Active";
	private ResourceBundle rb = ResourceBundle.getBundle("SM");

	public PhoneUpgrade() {

	}

	public void selectTransferMyNumber() throws Exception {
		activationPhoneFlow.clickLink("Activate/Reactivate");
		activationPhoneFlow.selectRadioButton("activation_option[1]");
		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void enterNewEsnWithSim(String partNumber, String sim)
			throws Exception {
		String toEsn = phoneUtil.getNewEsnByPartNumber(partNumber);
		ESN esn = new ESN(partNumber, toEsn);
		phoneUtil.addSimToEsn(sim, esn);
		System.out.println("New ESN2: " + toEsn);

		activationPhoneFlow.typeInTextField("new_esn",toEsn);
		activationPhoneFlow.typeInTextField("new_phone_nickname","nickname target");
		activationPhoneFlow.selectRadioButton("current_esn");
		activationPhoneFlow.pressSubmitButton("Submit");
	}

	public void enterSimCard(String targetSimCard) throws Exception {
		// Confirm that I have both phones for a sim swap
		if (activationPhoneFlow.submitButtonVisible("Yes")) {
			activationPhoneFlow.pressSubmitButton("Yes");
		} else {
			String newTargetSim = simUtil.getNewSimCardByPartNumber(targetSimCard);
			System.out.println("New SIM target: " + newTargetSim);
			activationPhoneFlow.typeInTextField("new_phone_sim",newTargetSim);
			activationPhoneFlow.pressSubmitButton("Submit");
		}
		
	}

	public void completeUpgradeProcess() throws Exception {
		/*if (activationPhoneFlow.submitButtonVisible("Yes")) {
			activationPhoneFlow.pressSubmitButton("Yes");
		}
		activationPhoneFlow.pressSubmitButton("Proceed with upgrade");
		
//		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY"));
		activationPhoneFlow.pressSubmitButton("Continue");
		if (activationPhoneFlow.submitButtonVisible("No Thanks")) {
			activationPhoneFlow.pressSubmitButton("No Thanks");
		}*/
		TwistUtils.setDelay(30);
		Assert.assertTrue(activationPhoneFlow.h4Visible("ORDER SUMMARY") ||
				activationPhoneFlow.h2Visible("ORDER SUMMARY") ||
				activationPhoneFlow.h4Visible("Billing Summary"));
		ESN toEsn = esnUtil.getCurrentESN();
		toEsn.setTransType("SM Phone Upgrade");
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(toEsn.getEsn());
		phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
		//activationPhoneFlow.pressSubmitButton(rb.getString("sm.DoneButton"));
		TwistUtils.setDelay(15);
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
		if (activationPhoneFlow.submitButtonVisible(rb.getString("sm.NoThanksText"))) {
			activationPhoneFlow.pressSubmitButton(rb.getString("sm.NoThanksText"));
		}
	}

	public void enterToEsn(String partNumber) throws Exception {
		String toEsn = esnUtil.getCurrentESN().getEsn();
		String fromMin = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getFromEsn().getEsn());

		activationPhoneFlow.typeInTextField("new_esn",toEsn);
		activationPhoneFlow.typeInTextField("new_phone_nickname","nickname target");
		activationPhoneFlow.getBrowser().radio("current_esn").near(activationPhoneFlow.getBrowser().span(fromMin)).click();
		activationPhoneFlow.pressSubmitButton("Submit");
	}

	public void enterFromMin() throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN().getFromEsn();
		activationPhoneFlow.typeInTextField("portPhoneNumber", fromEsn.getMin());
		activationPhoneFlow.clickLink(rb.getString("sm.continue"));
	}
	
	public void enterNewSimAndMinDependingOnAndSim(String status, String sim)
			throws Exception {
		if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.pressButton("active_phone_confirm_div_submit");
//			String toMin = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
//			activationPhoneFlow.typeInTextField("new_phone", toMin);
//			activationPhoneFlow.pressSubmitButton("Submit");
		}

		if (!NEW_STATUS.equalsIgnoreCase(status) && !sim.isEmpty()) {
			ESN esn = esnUtil.getCurrentESN().getFromEsn();
			String sim1 = phoneUtil.getSimFromEsn(esn.getEsn());

			// String newSim = simUtil.getNewSimCardByPartNumber(sim);
			activationPhoneFlow.typeInTextField("new_phone_sim",sim1);
			activationPhoneFlow.pressSubmitButton("Submit");
		}
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

	public void goToUpgradeOption() throws Exception {
		activationPhoneFlow.clickLink("Upgrade/Transfer current Service");
	}

	public void enterSimToUpgrade(String partNumber, String sim) throws Exception {
		ESN esn;
		if (partNumber.startsWith("PH")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			ActivatePhone.joinSimCard(sim, esn);
		}
		ESN fromEsn = esnUtil.getCurrentESN();
		if (fromEsn != null) {
			esn.setFromEsn(fromEsn);
		}
		esnUtil.setCurrentESN(esn);
		
		activationPhoneFlow.typeInTextField("sim_num", esn.getSim());
		activationPhoneFlow.selectRadioButton("current_group_esn");
		TwistUtils.setDelay(5);
		pressButton("Submit");
		TwistUtils.setDelay(20);
		
	}
	
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
	}

}