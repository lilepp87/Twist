package com.tracfone.twist.impl.eng.activation;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.services.Services;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PhoneUpgrade {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;

	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private final Properties props;
	private final String GSM;
	private final String ACTIVE;

	public PhoneUpgrade() {
		props = new Properties("Net10");
		GSM = props.getString("Twist.CellTechGSM"); //$NON-NLS-1$
		ACTIVE = props.getString("Twist.StatusActive");
	}

	public void enterToESNWithStatus(String status) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		esn.setTransType("Net10 Upgrade");
		System.out.println("New ESN: " + esn);
		
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.NewEsnText.name, esn.getEsn());
		activationPhoneFlow.pressButton(props.getString("submit"));
		TwistUtils.setDelay(80);
		if (activationPhoneFlow.buttonVisible(props.getString("continue"))){//for home phone pop up
			activationPhoneFlow.pressButton(props.getString("continue"));
		} /*else {
			activationPhoneFlow.pressSubmitButton(props.getString("continue"));
		}*/
		TwistUtils.setDelay(10);
		//to check for the warning message
		if (activationPhoneFlow.divVisible("phone-upg-warn-modal")){
			Assert.assertTrue(activationPhoneFlow.getBrowser().div("phone-upg-warn-modal").text().contains("Coverage Warning Message"));
			if (activationPhoneFlow.buttonVisible(props.getString("continue"))){
				activationPhoneFlow.pressButton(props.getString("continue"));
			} else {
				activationPhoneFlow.pressSubmitButton(props.getString("continue"));	
			}
		}
		
		if (activationPhoneFlow.submitButtonVisible(props.getString("accept"))) {
			activationPhoneFlow.pressSubmitButton(props.getString("accept"));
		}
		
		if (activationPhoneFlow.radioVisible("radioSwapSlt")) {
			activationPhoneFlow.selectRadioButton("radioSwapSlt");
			activationPhoneFlow.pressButton(props.getString("submit"));
		}
		
		if (ACTIVE.equalsIgnoreCase(status)) {
			String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
			activationPhoneFlow.typeInTextField("minNew", min);
			activationPhoneFlow.pressButton(props.getString("submit"));
		}
	}

	public void enterSimNumber(String simCard) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			System.out.println("SimCard: " + newSim);
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.NewSimText.name, newSim);
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10EWebFields.SubmitButton.name);
		}
	}

	public void enterFromESNAndPhoneNumberWithPart(String oldPart) throws Exception {
		String fromEsn = esnUtil.getCurrentESN().getFromEsn().getEsn();
//		String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(oldPart, newEsn);
		phoneUtil.clearOTAforEsn(fromEsn);
		String currentMin = phoneUtil.getMinOfActiveEsn(fromEsn);
//		System.out.println("New ESN: " + fromESN);
		System.out.println("currentESN: " + fromEsn + " CurrentMin: " + currentMin);

		//Not required if login works.
	/*	String email = esnUtil.getCurrentESN().getFromEsn().getEmail();
		String pwd = esnUtil.getCurrentESN().getFromEsn().getPassword();
		activationPhoneFlow.typeInTextField("email", email);
		activationPhoneFlow.typeInPasswordField("password", pwd);
		activationPhoneFlow.pressSubmitButton("login_form_button");*/
		
		/*activationPhoneFlow.typeInTextField("esnCurrentNoAcct", fromEsn);
		activationPhoneFlow.typeInTextField("minCurrentNoAcct", currentMin);*/
	}

	public void enterCodeNumberAndConfirmCodeNumberDependingOnPhoneType(String phoneType) throws Exception {
		if ("ANDROID".equalsIgnoreCase(phoneType)) {
			if (activationPhoneFlow.buttonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.ContinueButton.name )) {
				activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10EWebFields.ContinueButton.name);
			}
		}

		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name);
		}
		
		if (activationPhoneFlow.textboxVisible(ActivationReactivationFlow.ActivationNet10EWebFields.TimeTankText.name)) {
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.TimeTankText.name, "12345");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.ConfirmTimeTankText.name, "12345");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.TTestText.name, "10");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10EWebFields.ConfirmTTestText.name, "10");
		}
		
		if (activationPhoneFlow.buttonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name)) {
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name);	
		}

		if (activationPhoneFlow.buttonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.SubmitButton.name)) {
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10EWebFields.SubmitButton.name);
		}	
		
		if (activationPhoneFlow.buttonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.SubmitButton.name)) {
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10EWebFields.SubmitButton.name);
		}
		
		if (activationPhoneFlow.submitButtonVisible(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name)) {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationNet10EWebFields.SkipButton.name);	
		}
		
		String fromEsn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		String toEsn = esnUtil.getCurrentESN().getEsn();
		String fromdeviceType= phoneUtil.getDevicetype(fromEsn);
		String todeviceType= phoneUtil.getDevicetype(toEsn);
		//System.out.println(deviceType);
		if(fromdeviceType.equalsIgnoreCase("FEATURE_PHONE") && todeviceType.equalsIgnoreCase("FEATURE_PHONE")){
			simulateBalanceInquiry(esnUtil.getCurrentESN().getEsn());
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
	
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	public void enterSIMOrSelectOldSIM() throws Exception {
		if (activationPhoneFlow.radioVisible("radioSwapSlt")){
			activationPhoneFlow.selectRadioButton("radioSwapSlt");
			activationPhoneFlow.pressButton("Submit");
		}
	}

	public void finishPhoneUpgrade() throws Exception {
		/*if((activationPhoneFlow.h2Visible(properties.getString("orderSummary")) || activationPhoneFlow.h3Visible(properties.getString("orderSummary")))){
			activationPhoneFlow.clickImage(properties.getString("continue"));					
		}*/
		if(activationPhoneFlow.h3Visible(props.getString("orderSummary"))){
			activationPhoneFlow.clickImage(props.getString("continue"));	
			activationPhoneFlow.clickImage(props.getString("NoThanks"));
		}
		
	}
	
	private void simulateBalanceInquiry(String fromEsn) throws Exception{
		try {
			File file = new File("../twist-net10E-web/cborequestfiles/simulatebalance.xml");  
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
	
	public void selectTransferMyNumberAndServiceFromOneNet10ToAnother() throws Exception {
				//activationPhoneFlow.selectRadioButton("optionsRadios[2]").clickLink(props.getString("CONTINUE2"));
				activationPhoneFlow.clickLink(props.getString("Activation.ActivatePhone"));
				activationPhoneFlow.clickLink(props.getString("Activation.KeepMyNumber"));
				activationPhoneFlow.clickLink(props.getString("Activation.Upgrade"));
		 	}

}