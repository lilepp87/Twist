package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;
import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.utils.SimUtil;

public class PhoneUpgrade {

	private static PhoneUtil phoneUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private static final String NEW_STATUS = "New";
	private static final String ACTIVE_STATUS = "Active";
	private static final String BYOP = "TWBYO";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static Logger logger = LogManager.getLogger(PhoneUpgrade.class.getName());
	public PhoneUpgrade() {

	}

	public void selectTransferMyNumber() throws Exception {
		activationPhoneFlow.clickLink("Activate/Reactivate");
		activationPhoneFlow.clickLink("ACTIVATE");
		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.TransferMyNumberRadio.name);
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}

	public void enterToEsn(String partNumber) throws Exception {
		String toEsn = esnUtil.getCurrentESN().getEsn();

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.TargetEsnText.name, toEsn);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.TargetNickName.name, "nickname target");
		activationPhoneFlow.getBrowser().radio("current_esn").click();
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);

		if (partNumber.startsWith("GP")) {
			activationPhoneFlow.pressButton("phone_branding_submit");
		}
	}
	
	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(30);
		if (!activationPhoneFlow.strongVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.OrderSummaryText.name)) {
			TwistUtils.setDelay(45);
		}
		Assert.assertTrue(activationPhoneFlow.strongVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.OrderSummaryText.name));
	}

	public void completeUpgradeProcess() throws Exception {
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);
		}

		ESN esn = esnUtil.getCurrentESN();
		esn.setTransType("ST Phone Upgrade");
		phoneUtil.checkUpgrade(esn.getFromEsn(), esn);
	}

	public void enterNewPinAndMinDependingOn(String pin, String status) throws Exception {
		logger.info(esnUtil.getCurrentESN().getPartNumber());
		if (esnUtil.getCurrentESN().getPartNumber().startsWith("GP") && NEW_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.pressButton("phone_branding_submit");
		}

		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			activationPhoneFlow.pressButton("active_phone_confirm_div_submit");
		}

		if (activationPhoneFlow.submitButtonVisible("smartphone_confirm_div_submit")) {
			activationPhoneFlow.pressSubmitButton("smartphone_confirm_div_submit");
		}

		if (activationPhoneFlow.buttonVisible("flash_continue_modal_btn")) {
			activationPhoneFlow.pressButton("flash_continue_modal_btn");
		}

		if (!pin.isEmpty()) {
			activationPhoneFlow.typeInTextField("service_plan", phoneUtil.getNewPinByPartNumber(pin));
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		} else if (activationPhoneFlow.h2Visible("You can continue with no Service Plan")) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		}

		if (activationPhoneFlow.divVisible("window[2]")) {
			activationPhoneFlow.pressSubmitButton("Yes");
		}
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
	
	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	public void completeUpgradeProcessForTW() throws Exception {
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.h1Visible("Activate Your Phone"));
		activationPhoneFlow.getBrowser().submit("GO TO ACCOUNT SUMMARY").click();
		Assert.assertTrue(activationPhoneFlow.h1Visible("My Account Summary"));
		phoneUtil.runIGateIn();
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		esn.setTransType("TW Phone Upgrade");
		phoneUtil.finishPhoneActivationAfterPortIn(esn.getEsn());
		
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esnStr);
		esn.setMin(min);
	}

	public void enterEsnSimAndCarrierForLteIphoneWithZip(String phoneStatus, String partNumber, String simPart,
			String carrier, String isLTE, String iPhone, String zip, String isHD) throws Exception {
		ESN esn;
		String status;
		if (phoneStatus.equalsIgnoreCase("New")) {
			status = "Not Active";
		} else {
			status = "Active";
		}
		if (NEW_STATUS.equalsIgnoreCase(phoneStatus) && partNumber.startsWith(BYOP)) {
			// String byopEsn = phoneUtil.getNewByopCDMAEsn();
			String byopEsn = "3400" + TwistUtils.generateRandomEsn();
			esn = new ESN(partNumber, byopEsn);
		} else if (NEW_STATUS.equalsIgnoreCase(phoneStatus)) {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simPart.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simPart);
	            phoneUtil.addSimToEsn(sim, esn);
				TwistUtils.setDelay(5);
			}
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}

		String email = esnUtil.getCurrentESN().getEmail();
		String password = esnUtil.getCurrentESN().getPassword();
		esnUtil.setCurrentESN(esn);
		esn.setEmail(email);
		esn.setPassword(password);

		if (partNumber.startsWith(BYOP)) {
			activationPhoneFlow.typeInTextField("serial_num", esn.getEsn());
			activationPhoneFlow.pressButton("CONTINUE TO NEXT STEP");
			int edited = 0;
		     for (int i = 0; i < 8 && edited == 0; i++) {
				TwistUtils.setDelay(2 + i);
				edited = phoneUtil.finishCdmaByopIgate(esn.getEsn(), carrier, status, isLTE, iPhone, isHD);
				logger.info("edit: " + edited);
		     }
			TwistUtils.setDelay(5);
			activationPhoneFlow.pressButton("CONTINUE TO NEXT STEP");
			if (phoneStatus.equals("New")) {
				TwistUtils.setDelay(10);
				activationPhoneFlow.getBrowser().submit("currently inactive").click();
				TwistUtils.setDelay(5);
			} else {
				TwistUtils.setDelay(5);
				activationPhoneFlow.getBrowser().submit("currently active").click();
				TwistUtils.setDelay(5);
			}

			if (isLTE.equalsIgnoreCase("Yes")) {
				if (!simPart.isEmpty()) {
					String simStr = simUtil.getNewSimCardByPartNumber(simPart);
					activationPhoneFlow.getBrowser().textbox("sim_num").setValue(simStr);
					activationPhoneFlow.getBrowser().button("Continue").click();
				}
			}
		} else {
			activationPhoneFlow.typeInTextField("serial_num", esn.getEsn());
			activationPhoneFlow.pressButton("CONTINUE TO NEXT STEP");
		}
	}

	public void upgradeMyDeviceWithPartNumber(String partNumber) throws Exception {
		Assert.assertTrue(activationPhoneFlow.h1Visible("My Account Summary"));
		activationPhoneFlow.clickLink("Upgrade to New Device");
		Assert.assertTrue(activationPhoneFlow.h1Visible("Phone Upgrade"));
		if (partNumber.startsWith(BYOP)) {
			activationPhoneFlow.clickImage("byop_phone.png");
		} else {
			activationPhoneFlow.clickImage("branded_phone.png");
		}
	}

}