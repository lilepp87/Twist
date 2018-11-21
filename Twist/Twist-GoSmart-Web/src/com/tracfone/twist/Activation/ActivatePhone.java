package com.tracfone.twist.Activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;
import junit.framework.Assert;
import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.Activation.ActivatePhone;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;


public class ActivatePhone {
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static PhoneUtil phoneUtil;
	private static CboUtils cboUtils;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private DeactivatePhone deactivatePhone;
	public ActivatePhone() {
	
	}

	public void goToActivateOption() throws Exception {
		activationPhoneFlow.clickLink(props.getString("Activate.Activate"));
	}
	
	public void goToActivateOptionToPort() throws Exception {
		//activationPhoneFlow.clickLink(rb.getString("sm.activate"));
		activationPhoneFlow.clickSpanMessage("Sign Out");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink(props.getString("Activate.Activate"));
//		activationPhoneFlow.clickLink("Activate an Individual Plan");
	}

	public void enterEsnForPartSim(String status, String partNumber, String sim)throws Exception {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.matches("PH(GS|SMGS).*")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
				esnUtil.setCurrentESN(esn);
				System.out.println(esnUtil.getCurrentESN().getEsn());
			} 
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
			esnUtil.setCurrentESN(esn);
		} else {
			throw new IllegalArgumentException("Phone Status not found");
		}	
		if (esnUtil.getCurrentESN().getFromEsn() == null) {
			esnUtil.getCurrentESN().setFromEsn(fromEsn);
		}
		activationPhoneFlow.typeInTextField(props.getString("Activate.ESNText"), esnUtil.getCurrentESN().getSim());
		pressButton(props.getString("Activate.continue"));
		TwistUtils.setDelay(10);
		
	}

	public void enterEsnForPort(String partNumber, String sim) throws Exception {
		ESN esn;
		ESN fromEsn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField(props.getString("Activate.ESNText"), esnUtil.getCurrentESN().getSim());
		pressButton(props.getString("Activate.continue"));
		TwistUtils.setDelay(10);
		//activationPhoneFlow.clickLink("showPortNumberLink");
	
}
	
	public void enterZipForNewPhoneNumber(String zip) throws Exception {
		activationPhoneFlow.clickLink(props.getString("Activate.NewPhoneLink"));
		activationPhoneFlow.typeInTextField(props.getString("Activate.zipcodeText"), zip);
		activationPhoneFlow.clickLink(props.getString("Activate.continue1"));
		TwistUtils.setDelay(10);
	}

	public void enterPin(String PinPart) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String newPin = phoneUtil.getNewPinByPartNumber(PinPart);
		System.out.println("pin is " + newPin);
		storeRedeemData(newPin);
		activationPhoneFlow.clickLink(props.getString("Activate.PINcard"));
		activationPhoneFlow.typeInTextField(props.getString("Activate.PINcardText"), newPin);
		pressButton(props.getString("Activate.continue"));
	}

	public void completeActivationProcessDependingOnStatusAndCellTech(String status, String cellTech)throws Exception {
			TwistUtils.setDelay(20);
			Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || 
					activationPhoneFlow.h2Visible("Billing Summary"));
			finishPhoneActivation(cellTech, status);
			activationPhoneFlow.pressSubmitButton(props.getString("Activate.DoneButton"));
			if (activationPhoneFlow.submitButtonVisible(props.getString("Activate.NoThanksText"))) {
				activationPhoneFlow.pressSubmitButton(props.getString("Activate.NoThanksText"));
			}
			TwistUtils.setDelay(10);
	}
	
	public void deactivateESNWithReason(String Partnumber , String Reason) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		deactivatePhone.stDeactivateEsn(esnStr, Reason);
		phoneUtil.setDateInPast(esnStr);
		activationPhoneFlow.navigateTo(props.getString("GS.HomePage"));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}
	
	/*public void deactivateWithReason(String Reason) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		deactivatePhone.stDeactivateEsn(esnStr, "");
		phoneUtil.setDateInPast(esnStr);
		activationPhoneFlow.navigateTo(props.getString("GS.HomePage"));
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}*/
	public void deactivateWithReason(String reason) throws Exception{
		esnUtil.setCurrentBrand("GO_SMART");
		String esn = esnUtil.getCurrentESN().getEsn();
		System.out.println("cbo :"+cboUtils);
		cboUtils.callDeactivatePhone(esn, phoneUtil.getMinOfActiveEsn(esn),reason, esnUtil.getCurrentBrand());
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		
	}
	
	public void enterSIM() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField(props.getString("Activate.ESNText"), esnUtil.getCurrentESN().getSim());
		pressButton(props.getString("Activate.continue"));
	}
	public void completeProcessFor(String cellTech) throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.h2Visible("ORDER SUMMARY") || 
				activationPhoneFlow.h2Visible("Billing Summary"));
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.DoneButton"));
		if (activationPhoneFlow.submitButtonVisible(props.getString("Activate.NoThanksText"))) {
			activationPhoneFlow.pressSubmitButton(props.getString("Activate.NoThanksText"));
		}
		ESN esn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(10);
		esnUtil.addRecentActiveEsn(esn, cellTech, NEW_STATUS, "Go Smart Activate");
		esnUtil.getCurrentESN().setMin(phoneUtil.getMinOfActiveEsn(esn.getEsn()));
		phoneUtil.clearOTAforEsn(esn.getEsn());
		
	}
	private void pressButton(String button) {
		if (activationPhoneFlow.buttonVisible(button)) {
			activationPhoneFlow.pressButton(button);
		} else {
			activationPhoneFlow.pressSubmitButton(button);
		}
	}
	
	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
	}
	
	private void finishPhoneActivation(String cellTech, String status) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(10);
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "Go Smart Activation");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
		TwistUtils.setDelay(15);
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
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
	
	public void setDeactivatePhone(DeactivatePhone deactivation) {
		this.deactivatePhone = deactivation;
	}

	public static void setCboUtils(CboUtils cboUtils) {
		ActivatePhone.cboUtils = cboUtils;
	}
	
	public void logOut() throws Exception {
		activationPhoneFlow.clickLink(props.getString("GS.Logout"));
		Assert.assertTrue(activationPhoneFlow.linkVisible(props.getString("Activate.MyAccount")));
	}

	public void goToCheckBalance() throws Exception {
		activationPhoneFlow.clickLink("Check Balance");
		activationPhoneFlow.typeInTextField(props.getString("Activate.CheckBalanceMin"),esnUtil.getCurrentESN().getMin());
		pressButton(props.getString("Account.Submit"));
	
	}

	public void checkBalanceDetails() throws Exception {
		
		Assert.assertTrue(activationPhoneFlow.h1Visible("Check Your Balance"));
		Assert.assertTrue(activationPhoneFlow.strongVisible("REMAINING BALANCE"));
		activationPhoneFlow.clickStrongMessage("HOME");
	}

}
