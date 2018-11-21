package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AbstractFinishActivationWithPIN {

	private final Properties props;
	private final String ANDROID;
	private final String MEGACARD;
	private final String ERR_NOT_MEGACARD = "Android phones can only use Megacard"; //$NON-NLS-1$
	private final String NOT_MEGACARD_ERROR;
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	protected AbstractFinishActivationWithPIN(String propsName) {
		props = new Properties(propsName);
		ANDROID = props.getString("Twist.Android"); //$NON-NLS-1$
		MEGACARD = props.getString("Twist.Megacard"); //$NON-NLS-1$
		NOT_MEGACARD_ERROR = props.getString("Activation.NotMegacardMessage"); //$NON-NLS-1$
	}

	public void enterPINForPhone(String pinType, String partForPin) throws Exception {
		if(activationPhoneFlow.buttonVisible(props.getString("Activation.EnterServicePin"))){
		if (esnUtil.shouldPass() && !partForPin.equalsIgnoreCase("null")) {
			String pin = phoneUtil.getNewPinByPartNumber(partForPin);
			storeRedeemData(pin);
			//Assert.assertTrue(activationPhoneFlow.radioVisible(props.getString("Activation.PinForActivateRadio")));
			//activationPhoneFlow.selectRadioButton(props.getString("Activation.PinForActivateRadio"));
			activationPhoneFlow.pressButton(props.getString("Activation.EnterServicePin"));
			activationPhoneFlow.typeInTextField(props.getString("Activation.PinCardText"), pin);
			System.out.println(pin);
		}
		//activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue1Submit"));
		
		/*if(activationPhoneFlow.linkVisible(props.getString("Activation.FinishButton"))){
            activationPhoneFlow.clickLink(props.getString("Activation.FinishButton"));
	      }else{
	            activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue2Submit"));
	      }*/

		activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue2Submit"));
		TwistUtils.setDelay(20);
		if(activationPhoneFlow.divVisible("error_msg_id")){
			if(activationPhoneFlow.getBrowser().div("error_msg_id").text().contains("Unfortunately, your local calling area does not provide data services.Therefore, you will only be able to use your NET10 phone to make/receive calls and send/receive text messages. Please click on the 'Continue' button to continue the Activation process.")){
			activationPhoneFlow.clickLink(props.getString("Activation.ContinueLink"));
			activationPhoneFlow.selectRadioButton(props.getString("Activation.PinForActivateRadio"));
			activationPhoneFlow.pressSubmitButton(props.getString("Activation.Continue2Submit"));
			}
		}
	    if(activationPhoneFlow.tableHeaderVisible("span-10 prepend-top")){
	    	activationPhoneFlow.pressSubmitButton("CONTINUE");
	    }
	}
	}
	
	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("Net10 Activation with PIN");
	}

	public void tryToActivateAndCheckMessageForPhoneAndPIN(String status, String cellTech, String phoneType, String pinType) throws Exception {
		if (ANDROID.equalsIgnoreCase(phoneType) && !MEGACARD.equalsIgnoreCase(pinType)) {
			boolean pinRedeemPassed = activationPhoneFlow.linkVisible(props.getString("Activation.FinishButton")); //$NON-NLS-1$
			Assert.assertFalse(ERR_NOT_MEGACARD, pinRedeemPassed);
			boolean foundErrMesg = activationPhoneFlow.divVisible(NOT_MEGACARD_ERROR);
			Assert.assertTrue(ERR_NOT_MEGACARD, foundErrMesg);
		} else if (!esnUtil.shouldPass()) {
			Assert.assertTrue(activationPhoneFlow.divVisible(props.getString("Activation.StolenMessage")));
		} else {
			TwistUtils.setDelay(35);
			if (!activationPhoneFlow.h3Visible(props.getString("Redemption.SuccessTitle"))) {
				TwistUtils.setDelay(60);
			}
			Assert.assertTrue(activationPhoneFlow.h3Visible(props.getString("Redemption.SuccessTitle")));
			activationPhoneFlow.clickLink(props.getString("Activation.FinishButton"));
			esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "Activate Net10 with PIN");
//			phoneUtil.checkRedemption(esnUtil.getCurrentESN());
		}
	}

	public void activateWithPinForBYOP(String pinPart) throws Exception {
		enterPINForPhone(MEGACARD, pinPart);
		tryToActivateAndCheckMessageForPhoneAndPIN(props.getString("Twist.StatusNew"), 
				props.getString("Twist.CellTechCDMA"), ANDROID, MEGACARD);		
	}

	public void activateWithPinFor(String cardType, String pin, String cellTech, String phoneType) throws Exception {
		if (!pin.isEmpty()) {
			enterPINForPhone(cardType, pin);
			tryToActivateAndCheckMessageForPhoneAndPIN(props.getString("Twist.StatusNew"), cellTech, phoneType, cardType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phone) {
		phoneUtil = phone;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}
}