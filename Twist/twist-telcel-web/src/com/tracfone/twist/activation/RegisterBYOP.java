package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
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
	
	private Properties properties = new Properties();

	public RegisterBYOP() {
		
	}

	public void goToRegisterBYOPCDMAPage() throws Exception {
		activationPhoneFlow.navigateTo(properties.getString("byopUrl"));
	}

	public void enterWithZipAndEsn(String active, String zip, String esnType) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn;
		
		String activeAnswer;
		boolean isActive = ACTIVE.equalsIgnoreCase(active);
		if (isActive) {
			activeAnswer = YES;
		} else {
			activeAnswer = NO;
		}
		
		if (HEX_ESN.equalsIgnoreCase(esnType)) {
			esn = new ESN("TCBYOPHEX", esnStr);
			String hexEsn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField("phone_meid", hexEsn);
		} else {
			esn = new ESN("TCBYOPDEC", esnStr);
			activationPhoneFlow.typeInTextField("phone_meid", esnStr);
		}
		esnUtil.setCurrentESN(esn);
		
		activationPhoneFlow.chooseFromSelect("phone_active", activeAnswer);
		activationPhoneFlow.typeInTextField("zipcode", zip);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		//Try at most 8 times to edit ig_trans to W
		TwistUtils.setDelay(3);
		int edited = 0;
		for (int i = 0; i < 8 && edited == 0; i++) {
			TwistUtils.setDelay(4+i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, "Verizon", active, "No", "No", "No");
			System.out.println("ESN: " + esnStr + " i: " + i +" edit: " + edited);
		}
		
		if (isActive) {
			TwistUtils.setDelay(8);
			activationPhoneFlow.selectCheckBox("agree");
		}
	}
	
	public void enterNetworkAccessCode(String accessPin) throws Exception {
		activationPhoneFlow.selectRadioButton("REGISTRATIONCODE");
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);
		activationPhoneFlow.typeInTextField("registration_code", pin);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		esnUtil.getCurrentESN().setTransType("TC BYOP Register with Pin");
		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
	}

	public void finishRegistration() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h4Visible(properties.getString("phoneRegisteredCongratulations")));
		activationPhoneFlow.pressSubmitButton(properties.getString("activateNow"));
		phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}

	public void chooseToUpgrade() throws Exception {
		activationPhoneFlow.selectRadioButton("UPGRADEBYOPCDMA");
	
		ESN oldEsn;
		if (esnUtil.peekRecentActiveEsn("TCBYOPHEX")) {
			oldEsn = esnUtil.popRecentActiveEsn("TCBYOPHEX");
		} else {
			oldEsn = esnUtil.popRecentActiveEsn("TCBYOPDEC");
		}
		activationPhoneFlow.typeInTextField("old_meid", oldEsn.getEsn());
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		esnUtil.getCurrentESN().setTransType("TC BYOP Register with Upgrade");
		esnUtil.getCurrentESN().setFromEsn(oldEsn);
	}

	public void chooseToPurchaseRegistrationCode() throws Exception {
		activationPhoneFlow.selectRadioButton("NEEDREGISTRATIONCODE");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		esnUtil.getCurrentESN().setTransType("TC BYOP Register with Purchase");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}
	
	public void enterAnInvalidESN(String esn) throws Exception {
		activationPhoneFlow.typeInTextField("phone_meid", esn);
		activationPhoneFlow.chooseFromSelect("phone_active", "yes");
		activationPhoneFlow.typeInTextField("zipcode", "33178");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		Assert.assertTrue(activationPhoneFlow.divVisible("error"));
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		Assert.assertTrue(activationPhoneFlow.divVisible("error"));
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		Assert.assertFalse(activationPhoneFlow.divVisible("error"));
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