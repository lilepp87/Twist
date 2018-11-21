package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;

public abstract class AbstractFinishActivationWithoutPin {

	private final Properties props;
	private final String ANDROID;
	private final String STATUS_NEW;
	private final String ERR_ANDROID_NO_PLAN = "Invalid PIN. Please enter a valid PIN (numbers only)."; //$NON-NLS-1$
	private ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;

	protected AbstractFinishActivationWithoutPin(String propsName) {
		props = new Properties(propsName);
		ANDROID = props.getString("Twist.Android"); //$NON-NLS-1$
		STATUS_NEW = props.getString("Twist.StatusNew"); //$NON-NLS-1$
	}

	public void attemptToActivatePhoneWithStatus(String cellTech, String phoneType, String status)
			throws Exception {
		if (ANDROID.equalsIgnoreCase(phoneType) && STATUS_NEW.equalsIgnoreCase(status)) {
			verifyPhoneCannotActivate();
		} else {
			completeActivation();
			esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "Activate Net10 without PIN");
		}
	}

	public void activateWithoutPinFor(String cellTech, String phoneType) throws Exception {
		if (!ANDROID.equalsIgnoreCase(phoneType)) {
			completeActivation();
			esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, STATUS_NEW, "Activate Net10 without PIN");
		}
	}

	private void completeActivation() {
		if (activationPhoneFlow.radioVisible(props.getString("Activation.OnlyActivate"))) { //$NON-NLS-1$
			activationPhoneFlow.selectRadioButton(props.getString("Activation.OnlyActivate"));	 //$NON-NLS-1$
		}
		activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue2Submit"));  //$NON-NLS-1$
		Assert.assertTrue(activationPhoneFlow.linkVisible(props.getString("Activation.FinishButton"))); //$NON-NLS-1$
		activationPhoneFlow.clickLink(props.getString("Activation.FinishButton")); //$NON-NLS-1$
	}

	private void verifyPhoneCannotActivate() {
		//New Android phones should not be able to activated without a service
		//Check that option to activate without any plan is invisible
		//boolean noPlanOption = activationPhoneFlow.radioVisible(props.getString("Activation.OnlyActivateRadio")); //$NON-NLS-1$
		//activationPhoneFlow.clickLink(props.getString("Activation.EnterServicePin"));
		activationPhoneFlow.pressButton(props.getString("Activation.EnterServicePin"));
		//Assert.assertFalse(ERR_ANDROID_NO_PLAN, noPlanOption);
		boolean canContinue = activationPhoneFlow.submitButtonVisible(props.getString("Activation.Continue1Submit")); //$NON-NLS-1$
		activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue2Submit"));
		//Assert.assertTrue(ERR_ANDROID_NO_PLAN, canContinue);
		Assert.assertTrue(activationPhoneFlow.labelVisible(ERR_ANDROID_NO_PLAN));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}