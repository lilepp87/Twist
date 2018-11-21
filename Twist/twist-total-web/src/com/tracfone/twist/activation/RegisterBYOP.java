package com.tracfone.twist.activation;
// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;
import org.apache.log4j.*;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RegisterBYOP {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private final String HEX_ESN = "Hex";
	private ResourceBundle props = ResourceBundle.getBundle("TotalWEB");
	private static Logger logger = LogManager.getLogger(RegisterBYOP.class.getName());
	public RegisterBYOP() {

	}

	public void goToRegisterBYOPCDMAPage() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("TW_BYOPURL"));		
	}

	public void enterEsnSimAndCarrierForLteIphoneWithZipAndIsHd(String status, String simPart, String carrier, 
			String isLTE, String iPhone, String zipCode, String isHd) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn = new ESN("TWBYOP", esnStr);
		esnUtil.setCurrentESN(esn);
		activationPhoneFlow.typeInTextField("input_zip", zipCode);
		activationPhoneFlow.typeInTextField("input_esn", esnStr);
		activationPhoneFlow.pressButton("CHECK COMPATIBILITY");
		TwistUtils.setDelay(8);
		System.out.println(esnStr);

		int edited = 0;
		TwistUtils.setDelay(3);
		for (int i = 0; i < 15 && edited == 0; i++) {
			TwistUtils.setDelay(5 + i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, status,	isLTE, iPhone, isHd);
			System.out.println("edit: " + edited);
			TwistUtils.setDelay(5);
			activationPhoneFlow.pressButton("CHECK COMPATIBILITY");
		}
	}
	
	public void goToActivatePage() throws Exception {
//		activationPhoneFlow.clickLink("Phones[3]");
//		activationPhoneFlow.navigateTo("https://sit1.totalwireless.com/wps/portal/home/h/a/phone");
		activationPhoneFlow.clickLink("Activate / Reactivate");
		activationPhoneFlow.clickLink("purchaseNewPlan");
//		activationPhoneFlow.clickImage("exifviewer-img-1");
//		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
	}

	
	
	public void enterEsnBrandModelStatusZip(String esn, String brand, String model, String status, String zip)
			throws Exception {
		activationPhoneFlow.typeInTextField("phone_meid", esn);
		activationPhoneFlow.chooseFromSelect("phone_brand", brand);
		activationPhoneFlow.chooseFromSelect("phone_model", model);
		activationPhoneFlow.chooseFromSelect("phone_active_verizon", status);
		activationPhoneFlow.typeInTextField("phone_zip", zip);
		activationPhoneFlow.pressButton("Continue");
		TwistUtils.setDelay(3);
		Assert.assertTrue(activationPhoneFlow.divVisible("NOT ELIGIBLE"));
	}

	public void enterNetworkAccessCode(String accessPin) throws Exception {
		activationPhoneFlow.selectRadioButton("registration_code_option");
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);
		activationPhoneFlow.typeInTextField("registration_code", pin);
		TwistUtils.setDelay(7);
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Pin");
	}

	public void chooseToUpgrade() throws Exception {
		TwistUtils.setDelay(7);
		activationPhoneFlow.selectRadioButton("registration_code_option[2]");
		ESN oldEsn;
		if (esnUtil.peekRecentActiveEsn("STBYOPHEX")) {
			oldEsn = esnUtil.popRecentActiveEsn("STBYOPHEX");
		} else {
			oldEsn = esnUtil.popRecentActiveEsn("STBYOPDEC");
		}
		activationPhoneFlow.typeInTextField("old_phone_meid", oldEsn.getEsn());
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setFromEsn(oldEsn);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Upgrade");
	}

	public void finishRegistration() throws Exception {
		TwistUtils.setDelay(9);
		Assert.assertTrue(activationPhoneFlow.divVisible("Congratulations! Your phone has been registered in"
				+ " our system using your Network Access Code. You can now Activate your phone with Total Wireless Service. ACTIVATE NOW"));
		activationPhoneFlow.pressSubmitButton("ACTIVATE NOW");
		phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}

	public void chooseToPurchaseRegistrationCode() throws Exception {
		activationPhoneFlow.selectRadioButton("registration_code_option[1]");
		TwistUtils.setDelay(6);
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setBuyFlag(true);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Purchase");
	}

	public void checkForFieldEnforcement() throws Exception {
		activationPhoneFlow.pressButton("continue_btn_section");
		Assert.assertTrue(activationPhoneFlow.divVisible("All the fields on this page are required. Please make" 
				+ " sure you have entered all the required information before continuing."));
	}

	public void enterNetworkAccessCodeDeselectAgreementAndCheckForError(String accessPin) throws Exception {
		activationPhoneFlow.selectCheckBox("agreement");
		enterNetworkAccessCode(accessPin);
		Assert.assertTrue(activationPhoneFlow.divVisible("You need to select the check box agreeing to transfer your"
				+ " current number in order to continue with the process."));
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
	
	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	public void enterPin(String pinNum) throws Exception {
		    activationPhoneFlow.clickLink("Activate / Reactivate");
			activationPhoneFlow.clickLink("haveServicePlan");
			String pin = phoneUtil.getNewPinByPartNumber(pinNum);
			activationPhoneFlow.typeInTextField("servicePin", " "+pin+" ");
			activationPhoneFlow.pressSubmitButton("servicePinSubmit");
			storeRedeemData(esnUtil.getCurrentESN(), pin);
	}
	
	public void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setActionType(6);
		esn.setRedeemType(false);
		esn.setTransType("Total Wireless Activation with PIN");
		esn.setPin(pin);
		System.out.println(pin);
	}

//	public void enterBYOPESNAndZipForLte(String zipCode) throws Exception {
//		activationPhoneFlow.clickH3("i'm bringing my own phone");
//		if(activationPhoneFlow.textboxVisible("deviceEsn")){
//		activationPhoneFlow.typeInTextField("deviceEsn", esnUtil.getCurrentESN().getEsn());
//		activationPhoneFlow.typeInTextField("zipCode", zipCode);
//		activationPhoneFlow.selectCheckBox("esn-byop-activation-check");
//		pressButton("ACTIVATE BRING-YOUR-OWN DEVICE");
//		}
//	}
	
	public void enterBYOPESNAndZipForCdma(String zipCode,String islete) throws Exception {
		activationPhoneFlow.clickH3("i'm bringing my own phone");
		if(islete.equalsIgnoreCase("Yes")){
		activationPhoneFlow.typeInTextField("deviceEsn", esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.typeInTextField("zipCode", zipCode);
		activationPhoneFlow.selectCheckBox("esn-byop-activation-check");
		}
		if(islete.equalsIgnoreCase("No")){
		activationPhoneFlow.selectCheckBox("esn-byop-activation-check");
		}
		pressButton("ACTIVATE BRING-YOUR-OWN DEVICE");
	}
	public void chooseStatusForSimAndIfLte(String status, String sim, String islte) throws Exception {
		if (status.equals("Not Active")) {
			TwistUtils.setDelay(20);
			pressButton("currently inactive");
			//pressButton("btnRegisterByopPhone");
			TwistUtils.setDelay(5);
		} else {
			TwistUtils.setDelay(5);
			pressButton("currently active");
			TwistUtils.setDelay(5);
			// check terms
		}

		if (islte.equalsIgnoreCase("Yes") && !sim.isEmpty()) {
			if (!sim.isEmpty()) {
				String Simstr = simUtil.getNewSimCardByPartNumber(sim);
				activationPhoneFlow.typeInTextField("deviceSim", Simstr);
				pressButton("CONTINUE[3]");// works manually
				TwistUtils.setDelay(5);
				activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				if (activationPhoneFlow.textboxVisible("zipCode")) {
					activationPhoneFlow.typeInTextField("zipCode", "33178");
				}
			
				if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
					activationPhoneFlow.pressButton("No more lines at this time");
				}
		   }
		}
		if (islte.equalsIgnoreCase("Yes") && sim.isEmpty()){
				activationPhoneFlow.pressButton("I don't have a SIM card");
				if (activationPhoneFlow.imageVisible("micro_sim.png[1]")) {
					activationPhoneFlow.clickImage("micro_sim.png[1]");// works
				}
				activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				}
		if (islte.equalsIgnoreCase("No") && sim.isEmpty()){
			activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
			TwistUtils.setDelay(10);
			if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
				// activationPhoneFlow.spanVisible("successfulAddDeviceMsg");
				activationPhoneFlow.pressButton("No more lines at this time");
			}
		}
	}
    
	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else if (activationPhoneFlow.buttonVisible(buttonType)) {
			activationPhoneFlow.pressButton(buttonType);
		}
	}

	public void chooseStatusForSimAndIfLteAndPlanlineAndClick(String status,
			String sim, String islte, String planLine, String click)
			throws Exception {
		if (status.equals("Not Active")) {
			TwistUtils.setDelay(20);
			pressButton("currently inactive");
			pressButton("btnRegisterByopPhone");
			TwistUtils.setDelay(5);
		} else {
			TwistUtils.setDelay(5);
			pressButton("currently active");
			TwistUtils.setDelay(5);
			activationPhoneFlow.selectCheckBox("agree_to_port");
			pressButton("btnRegisterByopPhone");
		}

		if (islte.equals("Yes")) {
			if (!sim.isEmpty()) {
				String simStr = simUtil.getNewSimCardByPartNumber(sim);
				activationPhoneFlow.typeInTextField("deviceSim", simStr);
				pressButton("CONTINUE TO NEXT STEP");// works manually
				// No more lines at this time
				activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				if (activationPhoneFlow.buttonVisible("No more lines at this time")) {
					activationPhoneFlow.pressButton("No more lines at this time");
				}
				if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
					activationPhoneFlow.pressButton("no more phones");
				}
			} else {
				activationPhoneFlow.pressButton("I don't have a SIM card");
				if (planLine.equalsIgnoreCase("Multi")) {
					if (click.equalsIgnoreCase("BUY SIM")) {
						pressButton("BUY A SIM");
					} else {
						pressButton("SKIP THIS PHONE");
						Assert.assertTrue(activationPhoneFlow.h3Visible("i'm bringing my own phone"));
					}
				}
				if (activationPhoneFlow.strongVisible("Nano SIM Card")) {
					activationPhoneFlow.clickStrongMessage("Nano SIM Card");// works
																			// manually
				}
				if (activationPhoneFlow.h3Visible("I WANT A NEW PHONE NUMBER")) {
					activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				}
			}
		} else {
			activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
			TwistUtils.setDelay(10);
			if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
				// activationPhoneFlow.spanVisible("successfulAddDeviceMsg");
				activationPhoneFlow.pressButton("no more phones");
			}
		}
	}

	public void changeDealerAndChooseStatusForSimAndIfLte(String Dealer, String status, String sim, String islte) throws Exception {
	      if (status.equals("Not Active")) {
			TwistUtils.setDelay(20);
			pressButton("currently inactive");
			//pressButton("btnRegisterByopPhone");
			TwistUtils.setDelay(5);
		} else {
			TwistUtils.setDelay(5);
			pressButton("currently active");
			TwistUtils.setDelay(5);
			// check terms
		}

		if (islte.equalsIgnoreCase("Yes") && !sim.isEmpty()) {
			if (!sim.isEmpty()) {
				String Simstr = simUtil.getNewSimCardByPartNumber(sim);
				activationPhoneFlow.typeInTextField("deviceSim", Simstr);
				pressButton("CONTINUE[3]");// works manually
				TwistUtils.setDelay(5);
				activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				if (activationPhoneFlow.textboxVisible("zipCode")) {
					activationPhoneFlow.typeInTextField("zipCode", "33178");
				}
			
				if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
					activationPhoneFlow.pressButton("no more phones");
				}
		   }
		}
		if (islte.equalsIgnoreCase("Yes") && sim.isEmpty()){
				activationPhoneFlow.pressButton("I don't have a SIM card");
				if (activationPhoneFlow.imageVisible("micro_sim.png[1]")) {
					activationPhoneFlow.clickImage("micro_sim.png[1]");// works
				}
				activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
				}
		if (islte.equalsIgnoreCase("No") && sim.isEmpty()){
			activationPhoneFlow.clickH3("I WANT A NEW PHONE NUMBER");
			TwistUtils.setDelay(10);
			if (activationPhoneFlow.spanVisible("successfulAddDeviceMsg")) {
				// activationPhoneFlow.spanVisible("successfulAddDeviceMsg");
				activationPhoneFlow.pressButton("no more phones");
			}
		}
	}
}