package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import junit.framework.Assert;
import net.sf.sahi.client.Browser;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.utils.Account;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BRMOperations {

	private Browser browser;
	private ServiceUtil serviceUtil;
	private PhoneUtil phoneutil;
	@Autowired
	private Account account;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	
	@Autowired
	public void setPhoneutil(PhoneUtil phoneutil) {
		this.phoneutil = phoneutil;
	}

	@Autowired
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public BRMOperations() {
		
	}

	public void callSubscriptionRedeemCard(String deal) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/subscriptionRedeemCard.xml");
		String wsdlurl= props.getString("redemptioncardmgmgservice");
		String request = FileUtils.readFileToString(file, "UTF-8");
		

		request = request.replaceAll("@ban", account.getBanID());
		request = request.replaceAll("@login", account.getMin());
		request = request.replaceAll("@deal", deal);
	
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionRedeemCard");	
		
	}

	public void createABillingAccount(Integer numberOfDeals) throws Exception {
		File file = null;
		if(numberOfDeals==0){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount.xml");
		} else if(numberOfDeals==1){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount1.xml");	
		} else if(numberOfDeals==2){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount2.xml");	
		} else if(numberOfDeals==3){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount3.xml");	
		} else if(numberOfDeals==4){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount4.xml");	
		} else if(numberOfDeals==5){
			file = new File("../Twist-TAS/servicerequestfiles/CreateBillingAccount5.xml");	
		}
		
		String wsdlurl= props.getString("BrmAccountMgmtServices");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		String emailStr = TwistUtils.createRandomEmail();

		request = request.replaceAll("@webObjid", TwistUtils.generateRandomMin());
		request = request.replaceAll("@randomEmail", emailStr);
		request = request.replaceAll("@orderID", TwistUtils.generateACHAccountNumber());
		
		if(numberOfDeals > 0){
			request = request.replaceAll("@app1", "WFMU0029");
			request = request.replaceAll("@login1", TwistUtils.generateRandomMin());
			request = request.replaceAll("@orderID1", TwistUtils.generateACHAccountNumber());
			if(numberOfDeals > 1){
				request = request.replaceAll("@app2", "WFMU0039");
				request = request.replaceAll("@login2", TwistUtils.generateRandomMin());
				request = request.replaceAll("@orderID2", TwistUtils.generateACHAccountNumber());
			}if(numberOfDeals > 2){
				request = request.replaceAll("@app3", "WFMU0049");
				request = request.replaceAll("@login3", TwistUtils.generateRandomMin());
				request = request.replaceAll("@orderID3", TwistUtils.generateACHAccountNumber());
			}if(numberOfDeals > 3){
				request = request.replaceAll("@app4", "WFMU0024");
				request = request.replaceAll("@login4", TwistUtils.generateRandomMin());
				request = request.replaceAll("@orderID4", TwistUtils.generateACHAccountNumber());
			}if(numberOfDeals > 4){
				request = request.replaceAll("@app5", "WFMU0039");
				request = request.replaceAll("@login5", TwistUtils.generateRandomMin());
				request = request.replaceAll("@orderID5", TwistUtils.generateACHAccountNumber());
			}
		}
		
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl, "BRM_tfOpCreateBillingAccount");
		String ban = serviceUtil.parseXml(response, "//brm:ACCOUNT_NO").get(0);
		String login = serviceUtil.parseXml(response, "//brm:PROVISIONING_INFO/brm:LOGIN").get(0);
		String groupId = serviceUtil.parseXml(response, "//brm:GROUP_NAME").get(0);
		account.setBanID(ban);
		account.setMin(login);
		account.setGroupId(groupId);
	}

	public void callSubscriptionPurchaseRedemptionCard(String deal) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/subscriptionPurchaseRedeemCard.xml");
		String wsdlurl= props.getString("redemptioncardmgmgservice");
		String request = FileUtils.readFileToString(file, "UTF-8");
		
		request = request.replaceAll("@ban", account.getBanID());
		request = request.replaceAll("@login",account.getMin());
		request = request.replaceAll("@deal",deal);
		request = request.replaceAll("@subsID", account.getGroupId());
		request = request.replaceAll("@orderID", TwistUtils.generateACHAccountNumber());
	
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionPurchaseRedeemCard");
	}

	public void callRecordPaymentSettlement() throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/recordPaymentSettlement.xml");
		String wsdlurl= props.getString("brmpaymentservices");
		String request = FileUtils.readFileToString(file, "UTF-8");
		

		request = request.replaceAll("@accnum", account.getBanID());
		request = request.replaceAll("@loginnum", account.getMin());
		
	
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request,wsdlurl ,"BRM_tfRecordPaymentSettlement");
	
	}

	public void getBillingAccountSummary() throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/BillingAccountsummary.xml");
		String wsdlurl= props.getString("BrmAccountEnquiryServices");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		request = request.replaceAll("@account_no", account.getBanID());
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpRetrieveBillingAccountSummary");
	
	}
	
	public void callSubscriptionEnroll() throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/SubscriptionEnroll.xml");
		String wsdlurl= props.getString("BrmServicePlanMgmtServices");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		request = request.replaceAll("@account_no", account.getBanID());
		request = request.replaceAll("@deal", "WFMAPPU0029");
		request = request.replaceAll("@login", account.getMin());
		request = request.replaceAll("@paymentSourceID", "1367082341");
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionEnroll");
	}
	
	public void callSubscriptionUnEnroll(String banID) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/SubscriptionUnEnroll.xml");
		String wsdlurl= props.getString("BrmServicePlanMgmtServices");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		request = request.replaceAll("@account_no", banID);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionUnEnroll");
	}

	public void getBAN() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/GetBAN.xml");
		String wsdlurl= props.getString("BrmAccountEnquiryServices");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		request = request.replaceAll("@login", account.getMin());
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpGETBAN");
	}
	
	public void soapServiceCallFISERV(String option) throws Exception{
		
		String banID = account.getBanID();
		String receiptNumber = "" + TwistUtils.createRandomLong(100000000, 999999999);
		String amount = "29.88";
		String retrivalResponse = retriveRenewalQuote(banID);
		String retrivalResponseCode = serviceUtil.parseXml(retrivalResponse, "//com:code").get(0);
		if(retrivalResponseCode.equals("0")){
			amount = getSoapServicesStatusCode(retrivalResponse,"com:totalAmountsWithDiscountAndTaxes");
		}
		
		String paymentResponse = paymentProcess(banID,amount,receiptNumber);
		String paymentResponseCode = serviceUtil.parseXml(paymentResponse, "//com:code").get(0);
		Assert.assertEquals("0", paymentResponseCode);
		
		if(option.toLowerCase().equals("cancel")){
			String cancelPaymentResponse = cancelPaymentRequest(banID,amount,receiptNumber);
			String cancelPaymentCode = serviceUtil.parseXml(cancelPaymentResponse, "//com:code").get(0);
			Assert.assertEquals("0", cancelPaymentCode);
		}
	}
	
	public String getSoapServicesStatusCode(String response, String tag ) throws SOAPException, IOException{
		
		MessageFactory factory = MessageFactory.newInstance();
		
		SOAPMessage message = factory.createMessage(
	            new MimeHeaders(),
	            new ByteArrayInputStream(response.getBytes(Charset
	                    .forName("UTF-8"))));
		
		SOAPBody body = message.getSOAPBody();
		return getTextValuesByTagName(body,tag);
	}
	
	public static String getTextValuesByTagName(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			return getTextValue(nodeList.item(i));
		}
		return null;
	}

	public static String getTextValue(Node node) {
		StringBuffer textValue = new StringBuffer();
		int length = node.getChildNodes().getLength();
		for (int i = 0; i < length; i++) {
			Node c = node.getChildNodes().item(i);
			if (c.getNodeType() == Node.TEXT_NODE) {
				textValue.append(c.getNodeValue());
			}
		}
		return textValue.toString().trim();
	}
	
	public String retriveRenewalQuote(String ban) throws Exception{
		
		String url= props.getString("FiservPaymentprocess");
		
		File file = new File("../Twist-TAS/servicerequestfiles/RetriveRenewalService_FISERV.xml");
		
		String request = FileUtils.readFileToString(file, "UTF-8");
		
		request = request.replaceAll("@ban", ban);
		
		return serviceUtil.callSOAServicewithRequestandEndpoint(request, url,"FISERV-RetriveRenewal");
		
	}
	
	public String paymentProcess(String ban , String amount ,String receiptNumber) throws Exception{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date dateObj = new Date();
		String formatedDate = formatter.format(dateObj);
		String url= props.getString("FiservPaymentprocess");
		
		File file = new File("../Twist-TAS/servicerequestfiles/ProcessPaymentRequest_FISERV.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		
		request = request.replaceAll("@ban", ban);
		request = request.replaceAll("@amount", amount);
		request = request.replaceAll("@date",formatedDate);
		request = request.replaceAll("@receipt",receiptNumber);
	
		return serviceUtil.callSOAServicewithRequestandEndpoint(request, url,"FISERV-PaymentProcess");
	}
	
	public String cancelPaymentRequest(String ban , String amount ,String receiptNumber) throws Exception {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date dateObj = new Date();
		String formatedDate = formatter.format(dateObj);
		String retrivalBAN = account.getBanID();
		
		File file = new File("../Twist-TAS/servicerequestfiles/CancelPaymentRequest_FISERV.xml");
		
		String wsdlurl= props.getString("FiservPaymentprocess");

		String request = FileUtils.readFileToString(file, "UTF-8");
		request = request.replace("@ban", ban);
		request = request.replace("@amount", amount);
        request = request.replace("@reciept",receiptNumber );
        request = request.replace("@date",formatedDate);
        
        return serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"FISERV-CancelPayment");
	}

	
	public void callSubscriptionQuotePrice() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/SubscriptionQuotePrice.xml");
		String wsdlurl= props.getString("BrmServicePlanMgmtServices");
		String request = FileUtils.readFileToString(file, "UTF-8");
		
		request = request.replaceAll("@zip", "33178");
		request = request.replaceAll("@ban", account.getBanID());
	
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionQuotePrice");
	}
	
	public void callSubscriptionUpdateService() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/SubscriptionUpdateService.xml");
		String wsdlurl = props.getString("BrmServicePlanMgmtServices");
		String request = FileUtils.readFileToString(file, "UTF-8");
        
		request = request.replaceAll("@login", "2225665882");
		request = request.replaceAll("@newvalue", TwistUtils.generateRandomMin());
		request = request.replaceAll("@valuetype", "MIN");
	
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlurl,"BRM_tfOpSubscriptionPurchaseRedeemCard");
	}

}
