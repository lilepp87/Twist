package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class UniversalPurchase {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String wsURL = props.getString("ivrcommercepurchaseservicewsdl");
	private static Logger logger = LogManager.getLogger(UniversalPurchase.class.getName());
//	private String identity = "";
//	private String signature = "";
//	private String price = "";
//	private String itemPrice = "";
//	private String orderId = "";
//	private String orderItemId = "";
//	private String planid = "";
//	private String planname = "";
//	private String plantype = "";
//	private String sourceplan = "";
//	private String planpart = "";
	private SimpleDateFormat dateFormat;
	private TimeZone tz;

	public UniversalPurchase() {
		tz = TimeZone.getTimeZone("UTC");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		dateFormat.setTimeZone(tz);
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setCboUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	public void callGetProductOfferingServicePlan(String plan) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/retrieveProductOffering.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		ESN esn = esnUtil.getCurrentESN();
//		TwistScenarioDataStore store = esnUtil.getScenarioStore();
		request = request.replaceAll("@esn", esn.getEsn());
		request = request.replaceAll("@brandname", esnUtil.getCurrentBrand());
		request = request.replaceAll("@channel", "IVR");
		request = request.replaceAll("@mercassociation", "DEVICE_TO_PLAN");
		logger.info("retrieveProductOffering call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"RetrieveProductOffering");
		logger.info(serviceUtil.parseXml(response, "//ns9:ProductSpecification/ns10:name"));
		String identity = serviceUtil.parseXml(response, "//soapenv:Header/wsse:Security/ns8:IdentityToken/ns8:IdentityIdentifier").get(0);
		put("identity", identity);
		String signature = serviceUtil.parseXml(response, "//soapenv:Header/wsse:Security/ns8:IdentityToken/ns8:Signature").get(0);
		put("signature", signature);
		put("planid", serviceUtil.parseXml(response, "//Payload/ns9:Product[starts-with(ns9:ProductSpecification/ns10:id,'" + plan + "')]/ns9:ID/ns10:ID").get(0));
		put("planname", serviceUtil.parseXml(response, "//Payload/ns9:Product[starts-with(ns9:ProductSpecification/ns10:id,'" + plan + "')]/ns9:ID/ns10:name").get(0));
		put("plantype", serviceUtil.parseXml(response, "//Payload/ns9:Product[starts-with(ns9:ProductSpecification/ns10:id,'" + plan + "')]/ns9:ID/ns10:type").get(0));
		put("planpart", plan);
		logger.info(serviceUtil.parseXml(response, "//Payload/ns9:Product[starts-with(ns9:ProductSpecification/ns10:id,'" + plan + "')]"));
		ArrayList<String> list = serviceUtil.parseXml(response, "//Payload/ns9:Product[ns9:ProductAttributes[ns10:name='LAST_REEDEEMED'][ns10:Value/ns10:value='true']]/ns9:ProductSpecification/ns10:id");
		String sourceplan;
		if (list.isEmpty()) {
			sourceplan = "";
		} else {
			sourceplan = list.get(0);
		}
		put("sourceplan", sourceplan);
	}

	private static void printNote(NodeList nodeList) {

		    for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				logger.info("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				logger.info("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						logger.info("attr name : " + node.getNodeName());
						logger.info("attr value : " + node.getNodeValue());

					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					printNote(tempNode.getChildNodes());

				}

				logger.info("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		    }
	 }

	public void callEstimateCustomerOrderForZip(String zip) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/estimateCustomerOrderRequest.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		
		ESN esn = esnUtil.getCurrentESN();
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@channel", "IVR");
		request = request.replaceAll("@timestamp", dateFormat.format(new Date()));
		request = request.replaceAll("@promo", "");

		request = request.replaceAll("@identity", get("identity"));
		request = request.replaceAll("@signature", get("signature"));
		request = request.replaceAll("@planid", get("planid"));
		request = request.replaceAll("@pinpart", get("planpart"));
		request = request.replace("@planname", get("planname"));
		request = request.replace("@plantype", get("plantype"));
		//Optional: Can be NOW,LATER,""
		request = request.replaceAll("@refilltype", get("refilltype"));
		request = request.replaceAll("@autorefill", get("autorefill"));
		//Try with a different price.
		request = request.replaceAll("@price", "");
		request = request.replaceAll("@zip", zip);
		request = request.replaceAll("@email", esn.getEmail());
		logger.info(request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"EstimateCustomerOrder");
	
		put("orderId", serviceUtil.parseXml(response, "//ns1:Payload/ns3:ID/com:ID").get(0));
		put("price", serviceUtil.parseXml(response, "//ns1:Payload/ns3:CustomerOrderExtension/ns15:orderPrice").get(0));
		//Can be multiple?
		put("itemPrice", serviceUtil.parseXml(response, "//ns1:Payload/ns3:CustomerOrderExtension/ns15:orderTotals/ns15:totalProductPrice").get(0));
		put("orderItemId", serviceUtil.parseXml(response, "//ns1:Payload/ns3:CustomerOrderItem/ns3:ID/com:ID").get(0));
//		logger.info(orderId + " " + price + " " + orderItemId);

	}

	public void callSubmitCustomerOrderForNewCard(String zip, String creditCard) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/submitCustomerOrderRequest_addCC.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String cc = TwistUtils.generateCreditCard(creditCard);
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@creditcard", cc);
		if("American Express".equalsIgnoreCase(creditCard)){
			request = request.replaceAll("@creditcard", "1234");
		}else{
			request = request.replaceAll("@cvv", "123");
		}
		submitOrder(zip, request, creditCard);
	}

	public void callSubmitCustomerOrderForExistingCard(String zip, String creditCard) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/submitCustomerOrderRequest_GetCC.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		ESN esn = esnUtil.getCurrentESN();
		String lastFour = esn.getLastFourDigits();
		
		String paymentSource = phoneUtil.getPaymentSource(lastFour, esn.getEmail());
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@creditlastfour", esn.getLastFourDigits());
		request = request.replaceAll("@paymentsource", paymentSource);
		submitOrder(zip, request, creditCard);
	}

	private void submitOrder(String zip, String request, String creditCard) throws Exception {
		String wsURL = props.getString("ivrdporderservice");
		ESN esn = esnUtil.getCurrentESN();
		request = request.replaceAll("@esn", esn.getEsn());
		request = request.replaceAll("@min", esn.getMin());
		request = request.replaceAll("@sim", esn.getSim());
		request = request.replaceAll("@itemprice", get("itemPrice"));
		request = request.replaceAll("@cardtype", creditCard);
		
		request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@channel", "IVR");
		//try with future date and if it extends the expire date
		request = request.replaceAll("@timestamp", dateFormat.format(new Date()));
		request = request.replaceAll("@promo", "");

		request = request.replaceAll("@identity", get("identity"));
		request = request.replaceAll("@signature", get("signature"));
		request = request.replaceAll("@planid", get("planid"));
		request = request.replaceAll("@pinpart", get("planpart"));
		request = request.replace("@planname", get("planname"));
		request = request.replace("@plantype", get("plantype"));
		//Optional: Can be NOW,LATER,AUTOREFILL, or ""
		request = request.replaceAll("@refilltype", get("refilltype"));
		request = request.replaceAll("@autorefill", get("autorefill"));
		//Try with a different price.
		request = request.replaceAll("@price", get("price"));
		request = request.replaceAll("@zip", zip);
		request = request.replaceAll("@email", esn.getEmail());
		request = request.replaceAll("@orderid", get("orderId"));
		//See what happens with wrong due date
//		Calendar c = new GregorianCalendar();
//		c.setTimeZone(tz);
//		c.add(Calendar.DATE, 30);
//		request = request.replaceAll("@duedate", dateFormat.format(c.getTime()));
		
		//Can be multiple?
		request = request.replaceAll("@orderitemid", get("orderItemId"));
		logger.info("Request: \n" + request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"SubmitCustomerOrder");
		String status = serviceUtil.parseXml(response, "/soapenv:Envelope/env:Body/ivrapi:submitCustomerOrderOutput/ivrapi:Payload/ns9:CustomerOrderItem/" +
				"ns9:CharacteristicSpecification[ns10:name='FULFILLMENT_STATUS']/ns10:Value/ns10:value").get(0);
		Assert.assertTrue("SUCCESS".equalsIgnoreCase(status));
		logger.info(get("price"));
	
	}

	public void callCheckEligibility() throws Exception {
		
		String wsURL = props.getString("ivrcommercepurchaseservicewsdl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/checkEligibility.xml");  
		     String request = FileUtils.readFileToString(file, "UTF-8");
		     String TASURL=props.getString("TAS.HomeUrl");
		     if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
		     	request = request.replaceAll("@secusername", props.getString("services.tstusername"));
		     	request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		     }else{
		     	request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
		     	request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		     }
		     request = request.replaceAll("@min", esnUtil.getCurrentESN().getMin());
		     request = request.replaceAll("@brandname", esnUtil.getCurrentBrand());
		     request = request.replaceAll("@channel", "IVR");
		 	String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"CheckEligibility");
		  	logger.info(serviceUtil.parseXml(response,"//Payload/com:name | //Payload/com:Value"));
		 	
		 	String isEligible = serviceUtil.parseXml(response, "/soapenv:Envelope/env:Body/ns1:ruleEngineResponse/Payload[com:name='IS_ELIGIBLE_FOR_IVR_PURCHASE']/com:Value/com:value").get(0);
		 	Assert.assertTrue("IS_ELIGIBLE_FOR_IVR_PURCHASE returned as FALSE for esn:"+esnUtil.getCurrentESN().getEsn(),isEligible.equalsIgnoreCase("ELIGIBLE"));
			
		 	put("isEnrolled", serviceUtil.parseXml(response, "/soapenv:Envelope/env:Body/ns1:ruleEngineResponse/Payload[com:name='ESN_AUTOREFILL_ENROLLED']/com:Value/com:value").get(0));
		 	
		  } catch (IOException e) {
			  e.printStackTrace();
		     throw(e);
		  }
	}
	
	public void callGetRetentionDecisionService(String refillType) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getRetentionDecisionRequest.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
		request = request.replaceAll("@brandname", esnUtil.getCurrentBrand());
		request = request.replaceAll("@channel", "IVR");
		request = request.replaceAll("@sourceserviceplan", get("sourceplan"));
		request = request.replaceAll("@destinationserviceplan",get("planpart"));
		
		if(get("isEnrolled").equalsIgnoreCase("TRUE")){
			request = request.replaceAll("@isenrolled","YES");
		}else {
			request = request.replaceAll("@isenrolled","NO");
		}
		
		logger.info("retention request:\n" + request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"GetRetentionDecision");
		serviceUtil.parseXml(response, "//Payload/com:Value/com:value");
		
		ArrayList<String> list = serviceUtil.parseXml(response, "/soapenv:Envelope/env:Body/ns1:ruleEngineResponse/Payload[com:name='FLOW_SCENARIO']/com:Value/com:value");
		if(! phoneUtil.getEsnDetails(esnUtil.getCurrentESN().getEsn(), "PART_STATUS").equalsIgnoreCase("52")){
			put("refilltype", "NOW");
		}else{
		if ("Now".equalsIgnoreCase(refillType) && list.contains("ADD_NOW")) {
			put("refilltype", "NOW");
		} else if ("Later".equalsIgnoreCase(refillType) && list.contains("ADD_TO_RESERVE")) {
			put("refilltype", "LATER");
		} else if (list.contains("BOTH")) {
			put("refilltype", refillType.toUpperCase());
		} else  {
			//Fall back to another option when done testing
			throw new IllegalArgumentException("Unable to buy expected plan refill type");
		}
		}
	}

	public void callGetProductOfferingServicePlanWithAutorefillOption(String autoRefill)
			throws Exception {
//		TwistScenarioDataStore ds = esnUtil.getScenarioStore();
		if ("yes".equalsIgnoreCase(autoRefill)) {
			File file = new File("../Twist-TAS/servicerequestfiles/retrieveProductOffering_upsell.xml");
			String request = FileUtils.readFileToString(file, "UTF-8");
			request = request.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
			request = request.replaceAll("@brandname", esnUtil.getCurrentBrand());
			request = request.replaceAll("@channel", "IVR");
			request = request.replaceAll("@mercassociation", "UPSELL");
			request = request.replaceAll("@identityidentifier",get("identity"));
			request = request.replaceAll("@signaturevalue", get("signature"));
			request = request.replaceAll("DEVICE_TO_PLAN", "UPSELL");
			request = request.replaceAll("@productpartnum", get("planpart"));
			
			logger.info("retrieveProductOffering call2 Request: "+request);
			String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"RetrieveProductOfferingforUpsell");
			
			put("identity", serviceUtil.parseXml(response, "//soapenv:Header/wsse:Security/ns8:IdentityToken/ns8:IdentityIdentifier").get(0));
			put("signature", serviceUtil.parseXml(response, "//soapenv:Header/wsse:Security/ns8:IdentityToken/ns8:Signature").get(0));
			put("planpart", serviceUtil.parseXml(response, "//Payload/ns9:Product/ns9:ProductSpecification/ns10:id").get(0));
			put("planname", serviceUtil.parseXml(response, "//Payload/ns9:Product/ns9:ProductSpecification/ns10:name").get(0));
			put("planid",  serviceUtil.parseXml(response, "//Payload/ns9:Product/ns9:ID/ns10:ID").get(0));
		//	ds.put("plantype", serviceUtil.parseXml(response, "//Payload/ns9:Product/ns9:ID/ns10:type").get(0));
//			logger.info(planid);
//			phoneUtil.insertIntoServiceResultsTable("retrieveProductOffering2", request, new StringBuffer(response),esnUtil.getCurrentESN().getFromMap("elpasedtime"), wsURL);
			put("autorefill", "TRUE");
		} else {
			put("autorefill", "FALSE");
		}
	}

	public String put(String key, String value) {
		return esnUtil.getCurrentESN().putInMap(key, value);
	}
	
	public String get(String key) {
		return esnUtil.getCurrentESN().getFromMap(key);
	}

	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}
	
}
