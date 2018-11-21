package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ArrayList;

import org.apache.log4j.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;

import org.junit.Assert;

import com.strcsr.twist.deactivation.DeactivatePhone;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.tracfone.b2b.inventoryservices.CreateResourceRequestType;
import com.tracfone.b2b.inventoryservices.CreateResourceResponseType;
import com.tracfone.b2b.inventoryservices.ExtensionType;
import com.tracfone.b2b.inventoryservices.InventoryServices;
import com.tracfone.b2b.inventoryservices.InventoryServices_Service;
import com.tracfone.b2b.inventoryservices.ResourceOrderItemType;
import com.tracfone.b2b.phoneservices.GetEsnAttributesRequestType;
import com.tracfone.b2b.phoneservices.GetEsnAttributesResponseType;
import com.tracfone.b2b.phoneservices.PhoneInformationRequestType;
import com.tracfone.b2b.phoneservices.PhoneInformationResponseType;
import com.tracfone.b2b.phoneservices.PhoneServices;
import com.tracfone.b2b.phoneservices.PhoneServices_Service;
import com.tracfone.b2b.redemptionservices.PINCardRedemptionRequestType;
import com.tracfone.b2b.redemptionservices.PINCardRedemptionResponseType;
import com.tracfone.b2b.redemptionservices.RedemptionServices;
import com.tracfone.b2b.redemptionservices.RedemptionServices_Service;
import com.tracfone.commontypes.EsnAttributesType;
import com.tracfone.commontypes.LanguageType;
import com.tracfone.commontypes.RequestorIdType;
import com.tracfone.commontypes.TracfoneBrandType;
import com.tracfone.phonecommontypes.DeviceIdType;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.impl.eng.activation.FinishActivationWithPIN;
import com.tracfone.twist.impl.eng.activation.FinishActivationWithoutPin;
import com.tracfone.twist.impl.eng.myAccount.CreateAccount;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public abstract class AbstractStartActivation {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private final Properties properties;
	private final String newStatus;
	private final String pastDueStatus;
	private final String GSM;
	private final String pastDueFutureStatus;
	private final String pastDuePinInQueueStatus;
	private final String stolenStatus;
	private DeactivatePhone deactivatePhone;
	
	private FinishActivationWithPIN finishWithPin;
	private CreateAccount createAccount;
	private FinishActivationWithoutPin finishWithoutPin;
	static Logger log = Logger.getLogger(AbstractStartActivation.class.getName());

	public AbstractStartActivation(String propsName) {
		properties = new Properties(propsName);
		newStatus = properties.getString("Twist.StatusNew"); //$NON-NLS-1$
		pastDueStatus = properties.getString("Twist.StatusPastDue"); //$NON-NLS-1$
		GSM = properties.getString("Twist.CellTechGSM"); //$NON-NLS-1$
		pastDueFutureStatus = properties.getString("Twist.StatusPastDueFuture"); //$NON-NLS-1$
		pastDuePinInQueueStatus = properties.getString("Twist.StatusPastDuePinInQueue"); //$NON-NLS-1$
		stolenStatus = properties.getString("Twist.StatusStolen"); //$NON-NLS-1$
	}

	public void goToActivatePhone() throws Exception {
		/*if(activationPhoneFlow.linkVisible(properties.getString("Redemption.AddorBuyAirtimeLink"))){
			activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL"));
		}*/
		activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL"));
		activationPhoneFlow.clickLink(properties.getString("Activation.ActivateLink")); //$NON-NLS-1$
		//activationPhoneFlow.clickLink(properties.getString("Activation.ActivatePhone"));
	}

	public void selectActivateMyNet10Phone() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("Activation.ActivatePhone"));
		activationPhoneFlow.clickLink("NEW NUMBER");
		/*activationPhoneFlow.selectRadioButton(properties.getString("Activation.ActivateRadio")); 
		activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink")); */
	}

	public void selectActivateMyNet10PhoneForByop() throws Exception {
		//activationPhoneFlow.clickLink(properties.getString("Activation.ActivatePhone"));
		activationPhoneFlow.clickLink("NEW NUMBER");
		/*activationPhoneFlow.selectRadioButton(properties.getString("Activation.ActivateRadio")); 
		activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink")); */
	}

	
	public void enterEsnForModelWithStatusAndZipCode(String partNumber, String status, String zipCode)
			throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		esn.setActionType(6);
		String esnStr;
		esn.setZipCode(zipCode);
		
		//Enter hex ESN for iPhone 
		if (partNumber.contains("API") || esn.getPartNumber().contains("HEX")) {
			esnStr = esn.tryToEnterHexEsn(phoneUtil);
		} else {
			esnStr = esn.getEsn();
		}
		
		activationPhoneFlow.typeInTextField(properties.getString("Activation.EsnText"), esnStr);
		activationPhoneFlow.selectCheckBox("agree_terms");
		activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink")); 
		
//		if (newStatus.equalsIgnoreCase(status) && partNumber.startsWith("GP")) {
//			activationPhoneFlow.pressSubmitButton("Accept");
//		}
		
		if (activationPhoneFlow.submitButtonVisible(properties.getString("continue"))){
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			if(activationPhoneFlow.linkVisible(properties.getString("Activation.ContinueLink"))){
                activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink")); 
          }
          
		}
		
		if (activationPhoneFlow.textboxVisible(properties.getString("Activation.ZipCodeText"))){
		activationPhoneFlow.typeInTextField(properties.getString("Activation.ZipCodeText"), zipCode); //$NON-NLS-1$
		}
		if (stolenStatus.equalsIgnoreCase(status)) {
			esnUtil.setShouldPass(false);
		}
		activationPhoneFlow.pressSubmitButton(properties.getString("Activation.Continue1Submit"));
	}

	private ESN findESNForStatusandSim(String partNumber, String status, String sim) {
		ESN esn;
		if (newStatus.equalsIgnoreCase(status)) {
			String  esnStr;
			if (partNumber.startsWith("NTSAS960")) {
				esnStr = phoneUtil.getESNForIMSI(partNumber,"TF64PSIMT5");
				esn = new ESN(partNumber, esnStr);
				esn.setSim(phoneUtil.getSimFromEsn(esnStr));
			} else if (partNumber.startsWith("PH")) {
				esnStr = phoneUtil.getNewByopEsn(partNumber, sim);
				esn = new ESN(partNumber, esnStr);
				esn.setSim(phoneUtil.getSimFromEsn(esnStr));
				String pin = phoneUtil.getNewPinByPartNumber("NTAPP6U040FREE");
				phoneUtil.addPinToQueue(esnStr, pin);
			} else {
				esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
				esn = new ESN(partNumber, esnStr);
			}
			log.warn(partNumber +":"+ esnStr);
		} else if (pastDueFutureStatus.equalsIgnoreCase(status)) {
			esn = esnUtil.popPastDueInFuture(partNumber);
		} else if (pastDueStatus.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentPastDueEsn(partNumber);
		} else if (pastDuePinInQueueStatus.equalsIgnoreCase(status)) {
			esn = esnUtil.popPastDueEsnWithPin(partNumber);
		} else if (stolenStatus.equalsIgnoreCase(status)) {
			esn = esnUtil.popRecentStolenEsn(partNumber);
		} else {
			throw new IllegalArgumentException("Invalid status of ESN"); //$NON-NLS-1$
		}
		esnUtil.setCurrentESN(esn);
		return esn;
	}

	public void possiblyJoinSIMForCellTechAndStatus(String simNumber, String cellTech,
			String status) throws Exception {
		String phonePart = esnUtil.getCurrentESN().getPartNumber();
		if (!simNumber.isEmpty() && esnUtil.shouldPass() && !phonePart.startsWith("PH")) {
			String sim = esnUtil.getCurrentESN().getSim();
			if (sim == null || sim.isEmpty()) {
				ESN esn = esnUtil.getCurrentESN();
				sim = simUtil.getNewSimCardByPartNumber(simNumber);
				log.warn(sim);
				phoneUtil.addSimToEsn(sim, esn);
				TwistUtils.setDelay(5);
			}
		}
	}
	
	public void makePastDueDependingOnStatusForModelAndSim(String status, String model, String sim) throws Exception {
		if (!newStatus.equalsIgnoreCase(status)) {
			ESN esn;
			String deactiveReason;

			if (pastDuePinInQueueStatus.equalsIgnoreCase(status)) {
				esn = esnUtil.popRecentEsnWithPin(model);
			} else {
				esn = esnUtil.popRecentActiveEsn(model);
			}

			if (stolenStatus.equalsIgnoreCase(status)) {
				deactiveReason = "STOLEN";
			} else {
				deactiveReason = "PASTDUE";
			}

			deactivatePhone.ntDeactivateEsn(esn.getEsn(), deactiveReason);
			activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL")); //$NON-NLS-1$

			if (pastDueFutureStatus.equalsIgnoreCase(status)) {
				esnUtil.addPastDueInFuture(esn);
			} else if (pastDuePinInQueueStatus.equalsIgnoreCase(status)) {
				phoneUtil.setDateInPast(esn.getEsn());
				esnUtil.addPastDueEsnWithPin(esn);
			} else if (stolenStatus.equalsIgnoreCase(status)) {
				phoneUtil.setDateInPast(esn.getEsn());
				esnUtil.addRecentStolenEsn(esn);
			} else {
				phoneUtil.setDateInPast(esn.getEsn());
				esnUtil.addRecentPastDueEsn(esn);
			}
		}
		findESNForStatusandSim(model, status, sim);
	}
	
	public void deactivateToEsn() throws Exception {
			deactivatePhone.ntDeactivateEsn(esnUtil.getCurrentESN().getEsn(), "PASTDUE");
			activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL")); //$NON-NLS-1$
		}
	
	public void deactivateWithReason(String Status) throws Exception {
		deactivatePhone.ntDeactivateEsn(esnUtil.getCurrentESN().getEsn(), "PORT CANCEL");
		activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL")); //$NON-NLS-1$
	}
	
	public void activateFromPhoneWithPartTypeSimTechPinAndCarrier(String part, String phoneType, 
			String sim, String tech, String pinType, String pin, String carrier) throws Exception {
		makePastDueDependingOnStatusForModelAndSim(newStatus, part, sim);
		goToActivatePhone();
		selectActivateMyNet10Phone();
		createAccount.createNewAccountForEsn();
		possiblyJoinSIMForCellTechAndStatus(sim, tech, newStatus);
		enterEsnForModelWithStatusAndZipCode(part, newStatus, "33178");	
		if (pin != null && !pin.isEmpty()) {
			finishWithPin.activateWithPinFor(pinType, pin, tech, phoneType);	
		} else {
			finishWithoutPin.attemptToActivatePhoneWithStatus(tech, phoneType, newStatus);	
		}	
	}
	
	public void activateLeasedFromPhoneWithPartTypeSimTechPinAndCarrier(String part, String phoneType, 
			String sim, String tech, String pinType, String pin, String carrier) throws Exception {
		goToActivatePhone();
		selectActivateMyNet10Phone();
		createAccount.createNewAccountForEsn();
		possiblyJoinSIMForCellTechAndStatus(sim, tech, newStatus);
		enterEsnForModelWithStatusAndZipCode(part, newStatus, "33178");	
		if (pin != null && !pin.isEmpty()) {
			finishWithPin.activateWithPinFor(pinType, pin, tech, phoneType);	
		} else {
			finishWithoutPin.attemptToActivatePhoneWithStatus(tech, phoneType, newStatus);	
		}	
	}

	public void activateToPhoneWithPartStatusTypeSimTechPinAndCarrier(String part, String status, String phoneType, String sim,
			String tech, String pinType, String pin, String carrier) throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN();
		String toEsnStr;
		if (newStatus.equalsIgnoreCase(status)) {
			if (part.startsWith("PH")) {
				toEsnStr = phoneUtil.getNewByopEsn(part, sim);
				ESN toEsn = new ESN(part, toEsnStr);
				toEsn.setSim(phoneUtil.getSimFromEsn(toEsnStr));
				esnUtil.setCurrentESN(toEsn);
			} else {
				toEsnStr = phoneUtil.getNewEsnByPartNumber(part);
				log.warn(part + ":" + toEsnStr);
				ESN toEsn = new ESN(part, toEsnStr);
				esnUtil.setCurrentESN(toEsn);
				if (GSM.equalsIgnoreCase(tech) || !sim.isEmpty()) {
					String newSim = simUtil.getNewSimCardByPartNumber(sim);
					phoneUtil.addSimToEsn(newSim, toEsn);
				}
			}		
		} else {
			activateFromPhoneWithPartTypeSimTechPinAndCarrier(part, phoneType, sim, tech, pinType, pin, carrier);
		}
		
		if (pastDueStatus.equalsIgnoreCase(status)) {
			deactivatePhone.ntDeactivateEsn(esnUtil.getCurrentESN().getEsn(), "PASTDUE");
			activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL")); //$NON-NLS-1$

		}
		
		ESN toESN = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(toESN.getEsn());
		toESN.setFromEsn(fromEsn);
	}

	public void setPhoneUtil(PhoneUtil newPhoneUtil) {
		phoneUtil = newPhoneUtil;
	}

	public void setEsnUtil(ESNUtil newEsnUtil) {
		esnUtil = newEsnUtil;
	}
	
	public void setDeactivatePhone(DeactivatePhone deactivatePhone) {
		this.deactivatePhone = deactivatePhone;
	}

	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	
	public void setCreateAccount(CreateAccount createAccount) {
		this.createAccount = createAccount;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setFinishActivationWithPIN(FinishActivationWithPIN finishWithPin) {
		this.finishWithPin = finishWithPin;
	}
	
	public void setFinishActivationWithoutPin(FinishActivationWithoutPin finishWithoutPin) {
		this.finishWithoutPin = finishWithoutPin;
	}
	
	static ESN AccountEsn=null;
	public void setAccountEsnForEsn(String status) throws Exception {
		AccountEsn = esnUtil.getCurrentESN();
		String esn=AccountEsn.getEsn();
		System.out.println("AccountEsn Value : "+esn);
		if (status.equals("In Active")){
			System.out.println(esn);
			deactivatePhone.stDeactivateEsn(esn, "PASTDUE");
		}
	}
	public void getAccountEsn() {
		activationPhoneFlow.clickLink(properties.getString("Account.MyAccountLink"));
		String esnStr= AccountEsn.getEsn();
		//ESN esn = esnUtil.getCurrentESN();
		String lastSixDigits = esnStr.substring(esnStr.length()-6) ;
		AccountEsn.setLastSixDigits(lastSixDigits);
		String min= AccountEsn.getMin();
		String zip = AccountEsn.getZipCode();
		System.out.println(zip);
		activationPhoneFlow.clickImage("add_phone.gif");
		activationPhoneFlow.typeInTextField("cb-serial", esnStr);
		activationPhoneFlow.pressSubmitButton("CONTINUE");
		if(activationPhoneFlow.textboxVisible("cb-phone")){
			activationPhoneFlow.typeInTextField("cb-phone", min);
		}
		activationPhoneFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.AddButton.name);
		if(activationPhoneFlow.spanVisible("Please enter the Verification Code:")){
			activationPhoneFlow.typeInTextField("smsPhoneCode", lastSixDigits);
		}
		activationPhoneFlow.pressSubmitButton("CONTINUE[1]");
		if(activationPhoneFlow.spanVisible("Please enter your Activation Zip Code")){
			activationPhoneFlow.typeInTextField("verifyPhoneCode", zip);
		}
		activationPhoneFlow.pressSubmitButton("CONTINUE[2]");
		String SuccessMsg= "Phone Successfully Added To Account"; 
		Assert.assertTrue(activationPhoneFlow.divVisible(SuccessMsg));
	}

	public void enterSimForModelWithStatusAndZipCode(String partNumber,String status, String zipCode)
			throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		esn.setActionType(6);
		String esnStr;
		String sim = null;
		
		//Enter hex ESN for iPhone 
		if (partNumber.contains("API") || esn.getPartNumber().contains("HEX")) {
			esnStr = esn.tryToEnterHexEsn(phoneUtil);
			System.out.println("Hex ESN ::"+esnStr);
			sim = esn.getSim();
		} else {
			esnStr = esn.getEsn();
			sim = esn.getSim();
		}
		
		activationPhoneFlow.typeInTextField(properties.getString("Activation.EsnText"), sim); //$NON-NLS-1$
		activationPhoneFlow.selectCheckBox("agree_terms");
		activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink")); //$NON-NLS-1$
		
		if (newStatus.equalsIgnoreCase(status) && partNumber.startsWith("GP")) {
			activationPhoneFlow.pressSubmitButton("Accept");
		}
		
		activationPhoneFlow.typeInTextField(properties.getString("Activation.ZipCodeText"), zipCode); //$NON-NLS-1$

		if (stolenStatus.equalsIgnoreCase(status)) {
			esnUtil.setShouldPass(false);
		}
		activationPhoneFlow.pressSubmitButton(properties.getString("Activation.Continue1Submit"));
	}

	public void setTheStatusToForEsnAndSim(String status, String esnPart, String simPart) throws Exception {
		findESNForStatusandSim(esnPart, "NEW", simPart);
		String esnStr =  esnUtil.getCurrentESN().getEsn();
		setLeaseStatus(status, esnStr);
	}
	
	
	public void setLeaseStatus(String status, String esn) {
		PhoneServices_Service webServiceImpl = new PhoneServices_Service();
		PhoneServices serviceInterface = webServiceImpl.getPhoneServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,properties.getString("phoneservice_url"));
		PhoneInformationResponseType oResp;
		PhoneInformationRequestType oRequest = new PhoneInformationRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientId("SmartPay");
		reqToken.setClientTransactionId("132456");
		oRequest.setRequestToken(reqToken);
		oRequest.setBrandName(TracfoneBrandType.NET_10);
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setSourceSystem("API");
		oRequest.setApplicationId("546445784");
		oRequest.setEsn(esn);
		oRequest.setLeaseStatus(status);
		
		oResp = serviceInterface.updatePhoneInformation(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
	}
	
	private void addUsernameTokenProfile(PhoneServices serviceInterface) {
		// TODO Auto-generated method stub
		try {
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
			QName securityQName = new QName(SECURITY_NAMESPACE, "Security");
			SOAPElement security = soapFactory.createElement(securityQName);
			QName tokenQName = new QName(SECURITY_NAMESPACE, "UsernameToken");
			SOAPElement token = soapFactory.createElement(tokenQName);
			QName userQName = new QName(SECURITY_NAMESPACE, "Username");
			SOAPElement username = soapFactory.createElement(userQName);
			username.addTextNode(properties.getString("service_username"));// your username 
			QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
			SOAPElement password = soapFactory.createElement(passwordQName);
			password.addTextNode(properties.getString("service_password"));// your password
			token.addChildElement(username);
			token.addChildElement(password);
			security.addChildElement(token);
			com.sun.xml.ws.api.message.Header header = Headers.create(security);
			((WSBindingProvider) serviceInterface).setOutboundHeaders(header);
		} catch (SOAPException e) {
			System.out
					.println("Could not configure Username Token Profile authentication");
			e.printStackTrace();
		}
	}

	public void redeemLeasedEsnWith(String pinPart) throws Exception{
		String esn = esnUtil.getCurrentESN().getEsn();
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		redeemLeasedEsn(pin, esn);
	}
	
	public void redeemLeasedEsn(String pin, String esn){
		RedemptionServices_Service webServiceImpl = new RedemptionServices_Service();
		RedemptionServices serviceInterface = webServiceImpl.getRedemptionServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,properties.getString("redeemservice_url"));
		PINCardRedemptionResponseType oResp;
		PINCardRedemptionRequestType oRequest = new PINCardRedemptionRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientTransactionId("132456");
		reqToken.setClientId("SmartPay");
		oRequest.setRequestToken(reqToken);
		
		oRequest.setBrandName(TracfoneBrandType.NET_10);
		oRequest.setSourceSystem("API");
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setPinCardNum(pin);
		oRequest.setRedeemNow(true);
		
		DeviceIdType phone = new DeviceIdType();
		phone.setEsn(esn);
		oRequest.setDeviceId(phone);
		
		oResp = serviceInterface.redeemPinCard(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
	
	}

	private void addUsernameTokenProfile(RedemptionServices serviceInterface) {
		try {
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
			QName securityQName = new QName(SECURITY_NAMESPACE, "Security");
			SOAPElement security = soapFactory.createElement(securityQName);
			QName tokenQName = new QName(SECURITY_NAMESPACE, "UsernameToken");
			SOAPElement token = soapFactory.createElement(tokenQName);
			QName userQName = new QName(SECURITY_NAMESPACE, "Username");
			SOAPElement username = soapFactory.createElement(userQName);
			username.addTextNode(properties.getString("service_username"));// your username 
			QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
			SOAPElement password = soapFactory.createElement(passwordQName);
			password.addTextNode(properties.getString("service_password"));// your password
			token.addChildElement(username);
			token.addChildElement(password);
			security.addChildElement(token);
			com.sun.xml.ws.api.message.Header header = Headers.create(security);
			((WSBindingProvider) serviceInterface).setOutboundHeaders(header);
		} catch (SOAPException e) {
			System.out.println("Could not configure Username Token Profile authentication");
			e.printStackTrace();
		}
	}

	public void getTheUpdatedESNAttributesForLeasedEsn() throws Exception{
		
		String esn = esnUtil.getCurrentESN().getFromEsn().getEsn();
		
		PhoneServices_Service webServiceImpl = new PhoneServices_Service();
		PhoneServices serviceInterface = webServiceImpl.getPhoneServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,properties.getString("phoneservice_url"));
		GetEsnAttributesResponseType oResp;
		GetEsnAttributesRequestType oRequest = new GetEsnAttributesRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientTransactionId("132456");
		reqToken.setClientId("SmartPay");
		oRequest.setRequestToken(reqToken);
		
		oRequest.setBrandName(TracfoneBrandType.NET_10);
		oRequest.setSourceSystem("API");
		oRequest.setLanguage(LanguageType.ENG);
		
		DeviceIdType phone = new DeviceIdType();
		phone.setEsn(esn);
		oRequest.setDevice(phone);
			
		
		ArrayList<EsnAttributesType> alist = new ArrayList<EsnAttributesType>();
		alist.add(EsnAttributesType.BRAND);
		alist.add(EsnAttributesType.SIM);
		alist.add(EsnAttributesType.STATUS);
		alist.add(EsnAttributesType.TECHNOLOGY);
		alist.add(EsnAttributesType.MIN);
		alist.add(EsnAttributesType.QUEUESIZE);
		alist.add(EsnAttributesType.ENDOFSERVICEDATE);
		alist.add(EsnAttributesType.FORECASTDATE);
		alist.add(EsnAttributesType.ESN);
		alist.add(EsnAttributesType.ISAUTOREFILL);
		alist.add(EsnAttributesType.ISUNLIMITED);
		alist.add(EsnAttributesType.CURRENT_SERV_PLAN_NAME);
		alist.add(EsnAttributesType.CURRENT_SERV_PLAN_ID);
		alist.add(EsnAttributesType.SERVICE_PARTNUMBER);
		alist.add(EsnAttributesType.CARRIER);
		alist.add(EsnAttributesType.LEASESTATUS);
		alist.add(EsnAttributesType.OLD_ESN);
		alist.add(EsnAttributesType.GROUPID);
		alist.add(EsnAttributesType.AVAILABLE_LINES);
		alist.add(EsnAttributesType.TOTAL_LINES);
		alist.add(EsnAttributesType.ESN_CHANGE_TYPE);
		alist.add(EsnAttributesType.PIN_PART_NUMBER);
		
		oRequest.getAttributeList().addAll(alist);
		
		oResp = serviceInterface.getUpdatedEsnAttributes(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
		
	}

	public void selectActivateMyNet10SIM() throws Exception {
		activationPhoneFlow.clickLink(properties.getString("Activation.ActivateSIM"));
		activationPhoneFlow.clickLink("NEW NUMBER");
		
	}
	public void goToReactivatePhone() throws Exception {
				activationPhoneFlow.clickLink(properties.getString("Activation.ActivatePhone"));
				activationPhoneFlow.clickLink(properties.getString("Activation.KeepMyNumber"));
				activationPhoneFlow.clickLink(properties.getString("Activation.Reactivate"));
		}

	public void selectNewNumberForBYOPNAC() throws Exception {
		activationPhoneFlow.clickLink("NEW NUMBER");
	}

	public void wIFIRegistration() throws Exception{
		activationPhoneFlow.navigateTo(properties.getString("Twist.WIFIURL"));
		String min = esnUtil.getCurrentESN().getMin();
		String sim = esnUtil.getCurrentESN().getSim();
		String simLastFour = sim.substring(sim.length() - 4);
		activationPhoneFlow.typeInTextField("input_phone", min);
		activationPhoneFlow.typeInTextField("input_esn", simLastFour);
		activationPhoneFlow.pressButton("enroll");
		activationPhoneFlow.typeInTextField("address1", "1200 charleston town");
		activationPhoneFlow.typeInTextField("city", "Mountain view");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("txtzip_code", "94043"); 
		activationPhoneFlow.pressButton("CONTINUE");
		Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("Congratulations!! We have all the information we need to enable your Wi-Fi calling in our system. This process may take up to 1 hour to complete. To enable Wi-Fi calling on your phone:").isVisible());	
	}
}