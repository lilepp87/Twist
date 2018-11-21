package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PhoneUpgrade {

	private static SimUtil simUtil;
	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private CboUtils cboUtils;

	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private ResourceBundle props = ResourceBundle.getBundle("straighttalk");
	private TwistScenarioDataStore scenarioStore;
	
	public PhoneUpgrade() {

	}
//
	public void enterCurrentNumber() throws Exception {
		ESN currentEsn = esnUtil.getCurrentESN();
		String fromEsn = currentEsn.getFromEsn().getEsn();
		String fromMin = phoneUtil.getMinOfActiveEsn(fromEsn);
		currentEsn.setMin(fromMin);
		activationPhoneFlow.clickLink("PhoneInputBtn");
		activationPhoneFlow.typeInTextField("input_phone_number", fromMin);
		activationPhoneFlow.pressButton("enter-actvn-phone-submit");
		TwistUtils.setDelay(30);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
	}
	//
	public void enterSimCard(String targetSimCard) throws Exception {
		//Confirm that I have both phones for a sim swap		
		if (activationPhoneFlow.submitButtonVisible("Yes")) {
			activationPhoneFlow.pressSubmitButton("Yes");
		} else {
			String newTargetSim = simUtil.getNewSimCardByPartNumber(targetSimCard);
			System.out.println("New SIM target: " + newTargetSim);
	
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationStraighttalkWebFields.TargetSimText.name, newTargetSim);
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		}
	}

	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(30);
		if (!activationPhoneFlow.strongVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.OrderSummaryText.name)) {
			TwistUtils.setDelay(35);
		}
		Assert.assertTrue(activationPhoneFlow.strongVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.OrderSummaryText.name));
	}

	public void completeUpgradeProcess() throws Exception {
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.ContinueButton.name);
		} else {
			activationPhoneFlow.pressSubmitButton("default_submit_btn");
		}
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.NoThanksSubmit.name);
		}
//		ESN fromEsn = esnUtil.getCurrentESN().getFromEsn();
		ESN esn = esnUtil.getCurrentESN();
		esn.setTransType("ST Phone Upgrade");
		phoneUtil.checkUpgrade(esn.getFromEsn(), esn);
		// To Auto Complete Port Ticket Using CBO for Phone Upgrade between diff carriers	
		String orderType = phoneUtil.getOrderType(esnUtil.getCurrentESN().getEsn());
		if (orderType.equals("EPIR") || orderType.equals("PIR") || orderType.equals("IPI")) {
			String actionItemId = phoneUtil.getactionitemidbyESN(esnUtil.getCurrentESN().getEsn());
//			String Min = phoneUtil.getminforESN(esnUtil.getCurrentESN().getEsn());
			String min = esn.getMin();
			//TODO: Does this work for EPIR? I Doubt this actually does anything 
			cboUtils.callcompleteAutomatedPortins(actionItemId, min);
		}
//		scenarioStore.put("ESN0", esnUtil.getCurrentESN().getFromEsn().getEsn());
		//Assert.assertNotNull(phoneUtil.checkBI(fromEsn));
		
	}

	public void completeUpgradeProcessInLRPAccount() throws Exception {
		ESN toEsn = esnUtil.getCurrentESN();
		toEsn.setTransType("ST Phone Upgrade");
		phoneUtil.finishPhoneActivationAfterPortIn(toEsn.getEsn());
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(toEsn.getEsn());
		// phoneUtil.checkUpgrade(toEsn.getFromEsn(), toEsn);
		phoneUtil.checkUpgrade(esnUtil.getCurrentESN(), esnUtil.getCurrentESN());
	}

	public void enterNewSimPinAndMinDependingOn(String sim, String pin, String status) throws Exception {
		System.out.println(esnUtil.getCurrentESN().getPartNumber());
		if (esnUtil.getCurrentESN().getPartNumber().startsWith("GP") && NEW_STATUS.equalsIgnoreCase(status)) {
			//activationPhoneFlow.pressButton("phone_branding_submit");
		}
			
		if (!NEW_STATUS.equalsIgnoreCase(status)) {
			//activationPhoneFlow.pressButton("active_phone_confirm_div_submit");
//			String toMin = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
//			activationPhoneFlow.typeInTextField("new_phone", toMin);
//			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationStraighttalkWebFields.SubmitButton.name);
		}
		
		TwistUtils.setDelay(45);
		if (activationPhoneFlow.submitButtonVisible("smartphone_confirm_div_submit")) {
			activationPhoneFlow.pressSubmitButton("smartphone_confirm_div_submit");
		}
		
		if (activationPhoneFlow.divVisible("switch_carrier_modal_header")) {
			activationPhoneFlow.pressButton("Continue");
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

		//to check for different carrier pop up
		if (activationPhoneFlow.h4Visible("myModalLabel")) {
			Assert.assertTrue(activationPhoneFlow.getBrowser().heading4("myModalLabel").text().contains("Coverage Warning Message"));
			if (activationPhoneFlow.buttonVisible("Continue")) {
				activationPhoneFlow.pressButton("Continue");
			} else {
				activationPhoneFlow.pressSubmitButton("Continue");
			}
		}
		
		if (activationPhoneFlow.divVisible("window[2]")) {
			activationPhoneFlow.pressSubmitButton("Yes");
		}
		
		String fromEsn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		String toEsn = esnUtil.getCurrentESN().getEsn();
		String fromdeviceType = phoneUtil.getDevicetype(fromEsn);
		String todeviceType = phoneUtil.getDevicetype(toEsn);
		//System.out.println(deviceType);
		if (fromdeviceType.equalsIgnoreCase("FEATURE_PHONE")
				&& todeviceType.equalsIgnoreCase("FEATURE_PHONE")) {
			simulateBalanceInquiry(esnUtil.getCurrentESN().getEsn());
		}
	}
	
	private void simulateBalanceInquiry(String fromEsn) throws Exception{
		try {
			File file = new File("../twist-straighttalk-web/cborequestfiles/simulatebalance.xml");  
		     String content = FileUtils.readFileToString(file, "UTF-8");
		     content = content.replaceAll("@fromesn", fromEsn);
		    System.out.println(content);
		    //String url = props.getString("simulate_balance_cbo_url");
		    StringBuffer respBuffer = new StringBuffer();
			for(int i=0 ; i<=4 ; i++){
				TwistUtils.setDelay(1);
				respBuffer = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content,"UTF-8"));
			}
			Assert.assertTrue(respBuffer.toString().contains("Success") || respBuffer.toString().contains("success"));
		
		  } catch (IOException e) {
			  e.printStackTrace();
		     throw new RuntimeException("Generating file failed", e);
		  }
		//String record = phoneUtil.checkBI(oldEsn);
		//Assert.assertNotNull(record);
		//Assert.assertNotNull(phoneUtil.checkBI(fromEsn));
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
	
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}
	public void setScenarioStore(TwistScenarioDataStore scenarioStore) {
		this.scenarioStore = scenarioStore;
	}
}