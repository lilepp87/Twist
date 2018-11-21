package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.tracfone.twist.context.OnDealerportal;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

import net.sf.sahi.client.Browser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tracfone.twist.utils.ServiceUtil;


public class RedeemPin {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String STOLEN_STATUS = "Stolen";
	private static final String GSM = "GSM";
	private static final String CDMA = "CDMA";
	private DeactivatePhone deactivatePhone;
	private MyAccountFlow myAccountFlow;
	private ServiceUtil cboUtil;

	@Autowired
	public void setCboUtil(ServiceUtil cboUtil) {
		this.cboUtil = cboUtil;
	}

	public RedeemPin() {

	}

	public void goToReUp() throws Exception {
		activationPhoneFlow.clickLink("ReUp");
	}

	public void enterPhoneNumberAndPin(String pin) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String phone = phoneUtil.getMinOfActiveEsn(esn);
		phoneUtil.clearOTAforEsn(esn);
		String newPin = phoneUtil.getNewPinByPartNumber(pin);

		activationPhoneFlow.typeInTextField("phoneNumber", phone);
		//activationPhoneFlow.getBrowser().accessor("document.frmRedemption.phoneNumber").setValue(phone);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);

		activationPhoneFlow.getBrowser().textbox("phoneNumber").focus();
		activationPhoneFlow.getBrowser().button("btnContinue").hover();
		activationPhoneFlow.getBrowser().button("btnContinue").focus();
		activationPhoneFlow.pressButton("btnContinue");
		
		
		
		storeRedeemData(newPin);
	}

	public void forStatusEnterPhoneNumberAndPin(String status, String pin, String action) throws Exception {
//		String esn = esnUtil.getCurrentESN().getEsn();
		//String phone = esnUtil.getCurrentESN().getMin();
//		String phone = phoneUtil.getMinOfActiveEsn(esn);
//		System.out.println("**************MIN:**********" +phone);
		
		
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		//String esnStr=esnUtil.getCurrentESN().getEsn();
		String min = esnUtil.getCurrentESN().getMin();

//		if (status.equalsIgnoreCase(PAST_DUE_STATUS)) {
////			phoneUtil.deactivateSMEsn(esn);
//			deactivatePhone.ntDeactivateEsn(esn, "PASTDUE");
//			
//			
//		}
//		phoneUtil.clearOTAforEsn(esn);
		String newPin = phoneUtil.getNewPinByPartNumber(pin);

		
		//activationPhoneFlow.getBrowser().accessor("document.frmRedemption.phoneNumber").setValue(phone);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);		
		System.out.println("**************MIN:**********" +min);
//		
//		activationPhoneFlow.typeInTextField("phoneNumber", min);	
//		activationPhoneFlow.getBrowser().textbox("phoneNumber").focus();
//		activationPhoneFlow.typeInTextField("phoneNumber", min);
		//phoneNumberDisplay
		activationPhoneFlow.typeInTextField("phoneNumberDisplay", min);
		if(activationPhoneFlow.buttonVisible("btnNow")){
			activationPhoneFlow.pressButton("btnNow");
		}
		else if(activationPhoneFlow.buttonVisible("btnContinue")){
			activationPhoneFlow.pressButton("btnContinue");
		}
//		
//		if (action.equalsIgnoreCase("Reup")) {			
//			activationPhoneFlow.getBrowser().button("btnContinue").hover();
//			activationPhoneFlow.getBrowser().button("btnContinue").focus();
//			activationPhoneFlow.pressButton("btnContinue");
//		} else {
//			activationPhoneFlow.getBrowser().button("btnNow").hover();
//			activationPhoneFlow.getBrowser().button("btnNow").focus();
//			activationPhoneFlow.pressButton("btnNow");
//		}
		
		storeRedeemData(newPin);
	}

	private void storeRedeemData(String pin) {
		ESN esn = esnUtil.getCurrentESN();
		esn.setPin(pin);
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setActionType(6);
		esn.setTransType("UDP Redeem PIN");
	}

	public void checkForRedemptionSuccess() throws Exception {
		TwistUtils.setDelay(16);
		if (activationPhoneFlow.buttonVisible("Continue")) {
			//This will click 'OK' on the Mexico roaming popup
			activationPhoneFlow.pressButton("Continue");
		} else if (activationPhoneFlow.buttonVisible("Continue[1]")) {
			//This will click 'OK' on the Mexico roaming popup
			activationPhoneFlow.pressButton("Continue[1]");
		}
		TwistUtils.setDelay(15);
		if (!activationPhoneFlow.h4Visible("Your redemption was succesful")) {
			TwistUtils.setDelay(45);
		}
		Assert.assertTrue(activationPhoneFlow.h4Visible("Your redemption was succesful"));
		esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), "", PAST_DUE_STATUS, "UDP Reup and Change Plan");
		activationPhoneFlow.pressSubmitButton("Continue");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setDeactivatePhone(DeactivatePhone deactivatePhone) {
		this.deactivatePhone = deactivatePhone;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void goToRACPage() throws Exception {
		activationPhoneFlow.navigateTo(OnDealerportal.UDP_URL + "/OneStepRefill/");
	}

	public void enterPhoneNumberAndPinForRACForAndAdd(String pin,
			String brand, String add) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String phone = phoneUtil.getMinOfActiveEsn(esn);
		phoneUtil.clearOTAforEsn(esn);
		String newPin = phoneUtil.getNewPinByPartNumber(pin);

		activationPhoneFlow.typeInTextField("inputPhoneNumber", phone);
		activationPhoneFlow.typeInTextField("inputPinNumber", newPin);

		if (brand.equalsIgnoreCase("net10")) {
			activationPhoneFlow.selectRadioButton("optionsRadios1");
		} else if (brand.equalsIgnoreCase("simple mobile")) {
			activationPhoneFlow.selectRadioButton("optionsRadios2");
		} else if (brand.equalsIgnoreCase("telcel")) {
			activationPhoneFlow.selectRadioButton("optionsRadios3");
		} else {
			activationPhoneFlow.selectRadioButton("optionsRadios1");
		}
		
		if (add.equalsIgnoreCase("later")) {
			activationPhoneFlow.selectRadioButton("applyPin1");
		} else if (add.equalsIgnoreCase("now")) {
			activationPhoneFlow.selectRadioButton("applyPin2");
		}

		activationPhoneFlow.pressSubmitButton("Submit");
		storeRedeemData(newPin);
	}

	public void checkForRACRedemptionSuccess() throws Exception {
		TwistUtils.setDelay(7);
		Assert.assertTrue(activationPhoneFlow.h3Visible("Refill Confirmation"));
	}

	public void makeBasedOnStatus(String status) throws Exception {
//		if (status.equalsIgnoreCase(PAST_DUE_STATUS)) {
//			String esn = esnUtil.getCurrentESN().getEsn();
//			deactivatePhone.ntDeactivateEsn(esn, "PASTDUE");
//		}
		
//		public void callDeactivatePhone(String esn, String min, String reason, String brand) throws Exception 
			try {
				File file = new File("../Twist-TAS/cborequestfiles/deactivatePhone.xml");
				String content = FileUtils.readFileToString(file, "UTF-8");
				
				//added 
				String esn = esnUtil.getCurrentESN().getEsn();
				String min = phoneUtil.getMinOfActiveEsn(esn);
				//
				
				content = content.replaceAll("@esn", esn);
				content = content.replaceAll("@min", min);
				content = content.replaceAll("@reason", "PASTDUE");
				content = content.replaceAll("@brand", "net10");

				System.out.println(content);

								
				StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
				parseResponse(response, "DeactivatePhone");

			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Generating file failed", e);
			}
	 
	}

	public String parseResponse(StringBuffer sbf, String serviceType) {
		String message = null;
		String result = null;
		String errorNum = null;
		try {
			byte[] bytes = sbf.toString().getBytes();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			String rootNode = document.getDocumentElement().getNodeName();
			NodeList nlist = document.getElementsByTagName(rootNode);
			try {
				message = (String) ((Element) nlist.item(0)).getElementsByTagName("ERROR_STRING").item(0).getChildNodes().item(0).getNodeValue();
				System.out.println(message);
					if(message.contains("Success")  || message.contains("Sucess") || message.contains("SUCCESS") || message.equalsIgnoreCase("PhoneUpgrade Request has been scheduled to be processed in 2 minutes - Pls. disconnect") || message.equalsIgnoreCase("")){
					result = "success";
				} else {
					result = "unsuccessful";
				}
			} catch (NullPointerException e) {
				result = "success";
			}
			if (serviceType.equalsIgnoreCase("VerifyTechnology")) {
				errorNum = (String) ((Element) nlist.item(0)).getElementsByTagName("ERROR_NUM").item(0).getChildNodes().item(0).getNodeValue();
				assertEquals(errorNum, "0");
				String carrierId = (String) ((Element) nlist.item(0)).getElementsByTagName("PREFERRED_CARRIER_MARKET_ID").item(0).getChildNodes()
						.item(0).getNodeValue();
				return carrierId;
			} else if (serviceType.equalsIgnoreCase("AddUser")) {
				String webobjid = (String) ((Element) nlist.item(0)).getElementsByTagName("WEBUSER_OBJID").item(0).getChildNodes().item(0)
						.getNodeValue();
				return webobjid;
			} else if (serviceType.equalsIgnoreCase("CreateContact")) {
				String contactobjid = (String) ((Element) nlist.item(0)).getElementsByTagName("CONTACT_OBJID").item(0).getChildNodes().item(0)
						.getNodeValue();
				return contactobjid;
			} else if (serviceType.equalsIgnoreCase("ValidateRedCardBatch")) {
				String lines = (String) ((Element) nlist.item(0)).getElementsByTagName("AVAILABLE_CAPACITY_0").item(0).getChildNodes().item(0)
						.getNodeValue();
				String pinPart = esnUtil.getCurrentESN().getFromMap("pinPart");
				String status = null;
				if((pinPart.equalsIgnoreCase("TWAPP00025") || pinPart.equalsIgnoreCase("TWAPP00035")) && lines.equalsIgnoreCase("1")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00060") && lines.equalsIgnoreCase("2")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00085") && lines.equalsIgnoreCase("3")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00110") && lines.equalsIgnoreCase("4")){
					status = "Success";
				}else status = "Failure";
				return status;
			}else if (serviceType.equalsIgnoreCase("updateTicketAndSendMessage")){
				message = (String) ((Element) nlist.item(0)).getElementsByTagName("TICKET_ID").item(0).getChildNodes().item(0).getNodeValue();
				System.out.println("TicketID:" + message);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";

	}
	
	
	
	
	public void goBackToUDP() throws Exception {
//		activationPhoneFlow.getBrowser().close();
//		activationPhoneFlow.getBrowser().open();
		activationPhoneFlow.getBrowser().navigateTo(OnDealerportal.UDP_URL + "/login");
		activationPhoneFlow.typeInTextField("j_username", "admin@aol.com");
		activationPhoneFlow.typeInPasswordField("j_password", "abc123");
		activationPhoneFlow.pressSubmitButton("Login");
	}

	public void enterPhoneNumberAndPinPromo(String pin, String promo)
			throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String phone = phoneUtil.getMinOfActiveEsn(esn);
		phoneUtil.clearOTAforEsn(esn);
		String newPin = phoneUtil.getNewPinByPartNumber(pin);

		activationPhoneFlow.typeInTextField("phoneNumber", phone);
		activationPhoneFlow.typeInTextField("redemptionPin", newPin);
		if (!promo.isEmpty()) {
			activationPhoneFlow.typeInTextField("promoCode", promo);
		}
		TwistUtils.setDelay(30);
		activationPhoneFlow.pressButton("Add PIN Now");
	}

}