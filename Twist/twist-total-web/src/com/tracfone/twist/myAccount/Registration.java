package com.tracfone.twist.myAccount;

import java.util.ResourceBundle;



import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;

import junit.framework.Assert;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.tracfone.b2b.inventoryservices.CreateResourceRequestType;
import com.tracfone.b2b.inventoryservices.CreateResourceResponseType;
import com.tracfone.b2b.inventoryservices.ExtensionType;
import com.tracfone.b2b.inventoryservices.InventoryServices;
import com.tracfone.b2b.inventoryservices.InventoryServices_Service;
import com.tracfone.b2b.inventoryservices.ResourceOrderItemType;
import com.tracfone.commontypes.LanguageType;
import com.tracfone.commontypes.RequestorIdType;
import com.tracfone.commontypes.TracfoneBrandType;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;


public class Registration {
	
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TotalWeb");

	public Registration() {

	}

	public void registerIphoneModelForCarrier(String model, String simPart, String brand)
			throws Exception {
		String imei = registeriphone(model, simPart, brand);
		
		/*if(simPart.equalsIgnoreCase("TF256PSIMV9N")){
			registeriphoneForCdmaRetry(model, esnUtil.getCurrentESN().getSim() ,esnUtil.getCurrentESN().getEsn());
		}
*/
	}

	public String registeriphone(String model, String simPart, String brand) {
		InventoryServices_Service webServiceImpl = new InventoryServices_Service();
		InventoryServices serviceInterface = webServiceImpl
				.getInventoryServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				props.getString("inventoryservice_url"));
		CreateResourceResponseType oResp;
		CreateResourceRequestType oRequest = new CreateResourceRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientId("123");
		reqToken.setClientTransactionId("123");
		oRequest.setRequestToken(reqToken);
		oRequest.setBrandName(TracfoneBrandType.TOTAL_WIRELESS);
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setSourceSystem("WARP");

		ExtensionType conInfo1, conInfo2, conInfo3;

		conInfo1 = new ExtensionType();
		conInfo1.setName("store_number");
		conInfo1.setValue("9988774455");
		oRequest.getConsumerInfo().add(conInfo1);

		conInfo2 = new ExtensionType();
		conInfo2.setName("terminal_id");
		conInfo2.setValue("0002222");
		oRequest.getConsumerInfo().add(conInfo2);

		conInfo3 = new ExtensionType();
		conInfo3.setName("purchase_type");
		conInfo3.setValue("new_purchase");
		oRequest.getConsumerInfo().add(conInfo3);

		String imei = TwistUtils.generate15DigitImei();
		String newSim = simUtil.getNewSimCardByPartNumber(simPart);
		System.out.println("SIM:"+ newSim);
		ResourceOrderItemType resOrdItem = new ResourceOrderItemType();
		resOrdItem.setEsn(imei);
		resOrdItem.setSim(newSim);

		ExtensionType resItemExtn1, resItemExtn2, resItemExtn3;

		resItemExtn1 = new ExtensionType();
		resItemExtn1.setName("phone_make");
		resItemExtn1.setValue("apple");
		resOrdItem.getResourceItemExtension().add(resItemExtn1);

		resItemExtn2 = new ExtensionType();
		resItemExtn2.setName("phone_model");
		resItemExtn2.setValue(model);
		resOrdItem.getResourceItemExtension().add(resItemExtn2);

		resItemExtn3 = new ExtensionType();
		resItemExtn3.setName("transaction_timestamp");
		resItemExtn3.setValue("2015-05-12T19:07:50");
		resOrdItem.getResourceItemExtension().add(resItemExtn3);

		oRequest.getResourceOrderItem().add(resOrdItem);
		oResp = serviceInterface.createResource(oRequest);
		
		/*if(simPart.equalsIgnoreCase("TF256PSIMV9N")){
			Assert.assertEquals(oResp.getResult().getMessage(), "VD Pending"+imei, oResp.getResult().getMessage());
			phoneUtil.finishCdmaByopIgate(imei, "RSS", "NEW", "YES", "YES");
			 
		}else{
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
		}*/
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
	//	String phonePartNum = phoneUtil.getPhonePartNumberbySimandModel(model,simPart, brand);
		ESN currentEsn = new ESN("TEST", imei);
		esnUtil.setCurrentESN(currentEsn);
		currentEsn.setSim(newSim);
		// System.out.println("status code is :"+oResp.getResult().getCode());
		// System.out.println("status message is:"+oResp.getResult().getMessage());
		return imei;

	}
	
	
	public void registeriphoneForCdmaRetry(String model, String sim,String esn) {
		InventoryServices_Service webServiceImpl = new InventoryServices_Service();
		InventoryServices serviceInterface = webServiceImpl
				.getInventoryServicesSOAP();

		addUsernameTokenProfile(serviceInterface);
		// to change the End point during runtime
		BindingProvider bindingProvider = (BindingProvider) serviceInterface;
		bindingProvider.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				props.getString("inventoryservice_url"));
		CreateResourceResponseType oResp;
		CreateResourceRequestType oRequest = new CreateResourceRequestType();

		RequestorIdType reqToken = new RequestorIdType();
		reqToken.setClientId("123");
		reqToken.setClientTransactionId("123");
		oRequest.setRequestToken(reqToken);
		oRequest.setBrandName(TracfoneBrandType.STRAIGHT_TALK);
		oRequest.setLanguage(LanguageType.ENG);
		oRequest.setSourceSystem("WARP");

		ExtensionType conInfo1, conInfo2, conInfo3;

		conInfo1 = new ExtensionType();
		conInfo1.setName("store_number");
		conInfo1.setValue("9988774455");
		oRequest.getConsumerInfo().add(conInfo1);

		conInfo2 = new ExtensionType();
		conInfo2.setName("terminal_id");
		conInfo2.setValue("0002222");
		oRequest.getConsumerInfo().add(conInfo2);

		conInfo3 = new ExtensionType();
		conInfo3.setName("purchase_type");
		conInfo3.setValue("new_purchase");
		oRequest.getConsumerInfo().add(conInfo3);

		
		ResourceOrderItemType resOrdItem = new ResourceOrderItemType();
		resOrdItem.setEsn(esn);
		resOrdItem.setSim(sim);

		ExtensionType resItemExtn1, resItemExtn2, resItemExtn3;

		resItemExtn1 = new ExtensionType();
		resItemExtn1.setName("phone_make");
		resItemExtn1.setValue("apple");
		resOrdItem.getResourceItemExtension().add(resItemExtn1);

		resItemExtn2 = new ExtensionType();
		resItemExtn2.setName("phone_model");
		resItemExtn2.setValue(model);
		resOrdItem.getResourceItemExtension().add(resItemExtn2);

		resItemExtn3 = new ExtensionType();
		resItemExtn3.setName("transaction_timestamp");
		resItemExtn3.setValue("2015-05-12T19:07:50");
		resOrdItem.getResourceItemExtension().add(resItemExtn3);

		oRequest.getResourceOrderItem().add(resOrdItem);
		oResp = serviceInterface.createResource(oRequest);
		Assert.assertEquals(oResp.getResult().getMessage(), "SUCCESS", oResp.getResult().getMessage());
		// System.out.println("status code is :"+oResp.getResult().getCode());
		// System.out.println("status message is:"+oResp.getResult().getMessage());

	}


	private void addUsernameTokenProfile(InventoryServices serviceInterface) {
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
			username.addTextNode(props.getString("service_username"));// your username 
			QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
			SOAPElement password = soapFactory.createElement(passwordQName);
			password.addTextNode(props.getString("service_password"));// your password
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

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}
