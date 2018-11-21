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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class GetSubscriber {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String GSURL = props.getString("GetSubscriberUrl");
	private static Logger logger = LogManager.getLogger(GetSubscriber.class.getName());

	public GetSubscriber() {

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

	public void callGetSubscriberService() throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/GetSubscriber.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		ESN esn = esnUtil.getCurrentESN();
	
		request = request.replaceAll("@esn", esn.getEsn());

		logger.info("GetSubscriber call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, GSURL,"SOA-GetSubscriber");
	 	
		//String status = serviceUtil.parseXml(response, "//com:return/com:status").get(0);
		String status = parseSoaResponse(response);
		Assert.assertTrue("GetSubscriber failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Active"));
		
	}

	private static String parseSoaResponse(String response){
		String message = null;
		try {
			byte[] bytes = response.toString().getBytes(); 
			InputStream inputStream = new ByteArrayInputStream(bytes); 
			DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder =factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			String rootNode = doc.getDocumentElement().getNodeName();
			NodeList nlist = doc.getElementsByTagName(rootNode);
			if (doc.hasChildNodes()) {
				System.out.println("Response:");
				//printNote(doc.getChildNodes());
			}
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("safelink_flag").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("safelink_flag : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("activation_date").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("activation_date : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("auto_refill_flag").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("auto_refill_flag : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("sl_certification_flag").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("sl_certification_flag : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("zipcode").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("zipcode : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("imsi").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("imsi : "+ message);
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("status").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("status : "+ message);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}
	
	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}

	
}
