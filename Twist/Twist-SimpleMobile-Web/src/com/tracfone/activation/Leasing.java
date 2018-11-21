package com.tracfone.activation;

// JUnit Assert framework can be used for verification

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;

import net.sf.sahi.client.Browser;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
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
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Leasing {

	private static ResourceBundle rb = ResourceBundle.getBundle("SM");
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public Leasing() {

	}

	public static void setLeaseStatus(String status, String esn) {
		PhoneServices_Service webServiceImpl = new PhoneServices_Service();
		PhoneServices serviceInterface = webServiceImpl.getPhoneServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, rb.getString("sm.phoneservice_url"));
		PhoneInformationResponseType oResp;
		PhoneInformationRequestType oRequest = new PhoneInformationRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientId("SmartPay");
		reqToken.setClientTransactionId(Long.toString(TwistUtils.createRandomLong(10000, 99999)));
		oRequest.setRequestToken(reqToken);
		oRequest.setBrandName(TracfoneBrandType.SIMPLE_MOBILE);
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setSourceSystem("API");
		oRequest.setApplicationId(Long.toString(TwistUtils.createRandomLong(1000000, 9999999)));
		oRequest.setEsn(esn);
		oRequest.setLeaseStatus(status);
		System.out.println(oRequest.toString());
		oResp = serviceInterface.updatePhoneInformation(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
	}
	
	private static void addUsernameTokenProfile(Object serviceInterface) {
		try {
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
			QName securityQName = new QName(SECURITY_NAMESPACE, "Security");
			SOAPElement security = soapFactory.createElement(securityQName);
			QName tokenQName = new QName(SECURITY_NAMESPACE, "UsernameToken");
			SOAPElement token = soapFactory.createElement(tokenQName);
			QName userQName = new QName(SECURITY_NAMESPACE, "Username");
			SOAPElement username = soapFactory.createElement(userQName);
			username.addTextNode(rb.getString("sm.service_username"));// your username 
			QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
			SOAPElement password = soapFactory.createElement(passwordQName);
			password.addTextNode(rb.getString("sm.service_password"));// your password
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

	public void redeemLeasedEsnWith(String pinPart) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		redeemLeasedEsn(pin, esn);
	}
	
	public static void redeemLeasedEsn(String pin, String esn){
		RedemptionServices_Service webServiceImpl = new RedemptionServices_Service();
		RedemptionServices serviceInterface = webServiceImpl.getRedemptionServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, rb.getString("sm.redeemservice_url"));
		PINCardRedemptionResponseType oResp;
		PINCardRedemptionRequestType oRequest = new PINCardRedemptionRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientTransactionId("132456");
		reqToken.setClientId("SMARTPAYLEASE");
		oRequest.setRequestToken(reqToken);
		
		oRequest.setBrandName(TracfoneBrandType.SIMPLE_MOBILE);
		oRequest.setSourceSystem("WEB");
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setPinCardNum(pin);
		oRequest.setRedeemNow(false);
		
		DeviceIdType phone = new DeviceIdType();
		phone.setEsn(esn);
		oRequest.setDeviceId(phone);
		
		oResp = serviceInterface.redeemPinCard(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "Success.", oResp.getResult().getMessage());
	}

	public void getTheUpdatedESNAttributesForLeasedEsn() throws Exception {		
//		String esn = esnUtil.getCurrentESN().getEsn();
		
		PhoneServices_Service webServiceImpl = new PhoneServices_Service();
		PhoneServices serviceInterface = webServiceImpl.getPhoneServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, rb.getString("sm.phoneservice_url"));
		GetEsnAttributesResponseType oResp;
		GetEsnAttributesRequestType oRequest = new GetEsnAttributesRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientTransactionId("132456");
		reqToken.setClientId("SmartPay");
		oRequest.setRequestToken(reqToken);
		
		oRequest.setBrandName(TracfoneBrandType.SIMPLE_MOBILE);
		oRequest.setSourceSystem("API");
		oRequest.setLanguage(LanguageType.ENG);
		
		DeviceIdType phone = new DeviceIdType();
		phone.setEsn("100000003727129");
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

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

}