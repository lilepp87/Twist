package com.tracfone.twist.activation;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
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
	private final String CDMA = "CDMA";
	private final String GSM = "GSM";
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();

	public PhoneUpgrade() {

	}

	public void goToActivatePhone() throws Exception {
		activationPhoneFlow.navigateTo("http://sit1.tracfone.com");
//		activationPhoneFlow.clickLink("Remove Phone");
		activationPhoneFlow.clickLink("ACTIVATE");
		activationPhoneFlow.clickLink("ACTIVATE");
	}

	public void selectTransferMyNumberAndServiceFromOneTracFoneToAnother() throws Exception {
		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.TransferRadio.name);
		activationPhoneFlow.pressButton(properties.getString("submit"));
	}
	

	public void selectTransferMyNumberFromAnotherCompany() throws Exception {
		activationPhoneFlow.selectRadioButton(ActivationReactivationFlow.ActivationTracfoneWebFields.PortRadio.name);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationTracfoneWebFields.SubmitButton.name);
		activationPhoneFlow.selectCheckBox("terms_and_cond");
		activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationTracfoneWebFields.ContinueButton.name);
	}


	public void enterNewESNAndSim(String partNumber, String simCard) throws Exception {
		String newEsn = phoneUtil.getNewEsnByPartNumber(partNumber);
		System.out.println("newESN: " + newEsn);
		ESN esn = new ESN(partNumber, newEsn);
		esn.setFromEsn(esnUtil.getCurrentESN());
		esnUtil.setCurrentESN(esn);
		
		if (simCard != null && !simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			phoneUtil.addSimToEsn(newSim, esnUtil.getCurrentESN());
		}
		
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.NewEsnText.name, newEsn);
		//activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.NewNickNameText.name, "Twist Nickname");
		submitform("continue");
	}
	
	public void enterCurrentESNAndPhoneNumber(String oldPartNum) throws Exception {
		String oldEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(oldEsn);
		String min = phoneUtil.getMinOfActiveEsn(oldEsn);
		System.out.println("currentESN: " + oldEsn + " CurrentMin: " + min);

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.CurrentEsnText.name, oldEsn);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.CurrentMinText.name, min);
		activationPhoneFlow.pressButton(properties.getString("submit"));
	}

	public void enterAndConfirmCodeNumber() throws Exception {
		if (activationPhoneFlow.textboxVisible(ActivationReactivationFlow.ActivationTracfoneWebFields.TimeTankText.name)) {
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.TimeTankText.name, "12345");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ConfirmTimeTankText.name, "12345");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.TTestText.name, "10");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.ConfirmTTestText.name, "10");
			activationPhoneFlow.pressButton(properties.getString("submit"));
		}
	}

	public void completeTheProcessWithPin(String technology, String pin) throws Exception {
		if (activationPhoneFlow.buttonVisible(properties.getString("submit"))) {
			activationPhoneFlow.pressButton(properties.getString("submit"));
		} else if (activationPhoneFlow.buttonVisible(properties.getString("yes"))) {
			activationPhoneFlow.pressButton(properties.getString("yes"));
		} else {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		}
		
		if (!pin.isEmpty()) {
			String newPin = phoneUtil.getNewPinByPartNumber(pin);
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.PinCard1Text.name, newPin);
		} else if (activationPhoneFlow.buttonVisible(properties.getString("skipStep"))) {
			activationPhoneFlow.pressButton(properties.getString("skipStep"));
		}
		
		if (activationPhoneFlow.h2Visible(properties.getString("orderSummary"))) {
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));	
		} else {
			TwistUtils.setDelay(13);
			if (!activationPhoneFlow.submitButtonVisible(properties.getString("continue"))) {
				TwistUtils.setDelay(45);
				while (activationPhoneFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
					activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
					TwistUtils.setDelay(2);
				}
			}
			
//			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			Assert.assertTrue(activationPhoneFlow.spanVisible(properties.getString("activationMessage")));
			activationPhoneFlow.pressButton(properties.getString("continue"));
			if (activationPhoneFlow.buttonVisible("Continue to Complete Activation")) {
				activationPhoneFlow.pressButton("Continue to Complete Activation");	
			} else {
				activationPhoneFlow.pressButton(properties.getString("continue"));
			}
			if (activationPhoneFlow.buttonVisible(properties.getString("noThanks"))) {
				activationPhoneFlow.pressButton(properties.getString("noThanks"));	
			}
		}
		
		phoneUtil.runIGateIn();
		ESN esn = esnUtil.getCurrentESN();
		esn.setTransType("TF Phone Upgrade");
		phoneUtil.checkUpgrade(esn.getFromEsn(), esn);
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
	
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	public void completeUpgradeFromESNWithPin(String oldPartNum,String PinPart) throws Exception {
		activationPhoneFlow.pressButton(properties.getString("keepcurrentphonebutton"));//to transfer
		ESN oldEsn = esnUtil.popRecentActiveEsn(oldPartNum);
		phoneUtil.clearOTAforEsn(oldEsn.getEsn());
		System.out.println(oldEsn.getEsn());
		String min = phoneUtil.getMinOfActiveEsn(oldEsn.getEsn());
		System.out.println("FROMESN: " + oldEsn.getEsn() + " FromMin: " + min);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.CurrentMinText.name, min);
		submitform("continue2");
		TwistUtils.setDelay(10);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationTracfoneWebFields.CurrentEsnText.name, oldEsn.getEsn());
	
		if (activationPhoneFlow.buttonVisible(properties.getString("submit"))) {
			activationPhoneFlow.pressButton(properties.getString("submit"));
		}else {
			activationPhoneFlow.pressSubmitButton(properties.getString("submit"));
		}
		
		TwistUtils.setDelay(10);
		//to check for the warning message
		if (activationPhoneFlow.divVisible("phone-upg-warn-modal")) {
			Assert.assertTrue(activationPhoneFlow.getBrowser().div("phone-upg-warn-modal").text().contains("Coverage Warning Message"));
			submitform("continue2");
		}
		//BI pop up
		TwistUtils.setDelay(30);
		if (activationPhoneFlow.divVisible("time-tank-warn-modal")) {
			Assert.assertTrue(activationPhoneFlow.getBrowser().div("time-tank-warn-modal").text().contains("Coverage Warning Message"));
			if (activationPhoneFlow.submitButtonVisible("Try Again")){
				activationPhoneFlow.pressSubmitButton("Try Again");
			}else{
				activationPhoneFlow.pressButton("Try Again");
			}
		}
		TwistUtils.setDelay(5);
		simulateBalanceInquiry(oldEsn.getEsn());
		/*String DeviceType= phoneUtil.getDevicetype(oldEsn.getEsn());
		if (DeviceType.equals("SMARTPHONE")||DeviceType.equals("BYOP")) {
			int inserted = 0;
			TwistUtils.setDelay(3);
			for (int i = 0; i < 5; i++) {
				TwistUtils.setDelay(6+i);
				//DAO for Smartphone
				inserted=phoneUtil.getInsertSmartphoneEsnforUpgrade(oldEsn.getEsn());
				System.out.println("insert: " + inserted);
			}
		} else if(DeviceType.equals("FEATURE_PHONE")) {	
			int insertedPPE = 0;
			String ACK_MSG ="223560630010302230170228201700141000223001" + oldEsn.getEsn() + "01130000046";
			TwistUtils.setDelay(3);
			for (int i = 0; i < 5; i++) {
				TwistUtils.setDelay(6+i);
				//DAO for FeaturePhone
				insertedPPE=phoneUtil.getInsertPPEphoneEsnforUpgrade(ACK_MSG,oldEsn.getEsn());
				System.out.println("insert: " + insertedPPE);
			}
		}*/
		TwistUtils.setDelay(15);
		if (activationPhoneFlow.submitButtonVisible("YES")) {
			activationPhoneFlow.pressSubmitButton("YES");
			String newPin = phoneUtil.getNewPinByPartNumber(PinPart);
			System.out.println("PIN CARD: " + newPin);
			StringBuilder spacedPin = new StringBuilder(newPin.length() + 4);
			for (int i = 0; i < newPin.length(); i += 3) {
				spacedPin.append(newPin.substring(i, i + 3));
				spacedPin.append(' ');
			}
			activationPhoneFlow.typeInTextField("redcard1", spacedPin.toString());
			activationPhoneFlow.pressButton("Add PIN");
			
			activationPhoneFlow.clickLink(properties.getString("skipstep"));
			TwistUtils.setDelay(65);
			for (int i = 0; i < 9; i++) {
				if (activationPhoneFlow.submitButtonVisible(properties.getString("codeAccepted"))) {
					activationPhoneFlow.pressSubmitButton(properties.getString("codeAccepted"));
				}
			}
			if (activationPhoneFlow.buttonVisible(properties.getString("continue2"))) {
				activationPhoneFlow.pressButton(properties.getString("continue2"));
			} else if (activationPhoneFlow.submitButtonVisible(properties.getString("continue2"))) {
				activationPhoneFlow.pressSubmitButton(properties.getString("continue2"));
			}
			
			if (activationPhoneFlow.listItemVisible(properties.getString("phoneProgrammedSuccessfulMessage"))) {
				activationPhoneFlow.pressButton(properties.getString("continue"));
			}
			
			Assert.assertTrue(activationPhoneFlow.spanVisible(properties.getString("activationMessage")));
			submitform("continue2");
			submitform("continue");
			if (activationPhoneFlow.buttonVisible(properties.getString("noThanks"))) {
				activationPhoneFlow.pressButton(properties.getString("noThanks"));
			}
		} else {
			Assert.assertTrue(activationPhoneFlow.h1Visible("Transaction Summary"));
			submitform("continue2");
			submitform("continue");
			phoneUtil.runIGateIn();
			ESN esn = esnUtil.getCurrentESN();
			phoneUtil.clearOTAforEsn(esn.getEsn());
			esn.setTransType("TF Phone Upgrade");
			phoneUtil.checkUpgrade(esn.getFromEsn(), esn);
			//String record = phoneUtil.checkBI(oldEsn);
			//Assert.assertNotNull(phoneUtil.checkBI(oldEsn.getEsn()));
		}
	}
	
	private void submitform(String buttonName) {
		if (activationPhoneFlow.buttonVisible(properties.getString(buttonName))) {
			activationPhoneFlow.pressButton(properties.getString(buttonName));
		} else if (activationPhoneFlow.submitButtonVisible(properties.getString(buttonName))) {
			activationPhoneFlow.pressSubmitButton(properties.getString(buttonName));
		}
	}
	
	private void simulateBalanceInquiry(String fromEsn) throws Exception{
		try {
			File file = new File("../twist-tracfone-web/cborequestfiles/simulatebalance.xml");  
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
	}

}