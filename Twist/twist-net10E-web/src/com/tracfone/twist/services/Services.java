package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Services {
	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private static Logger logger = LogManager.getLogger(Services.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("Net10");
	private MyAccountFlow myAccountFlow;
	public Services() {
	}

	@Autowired
	private TwistScenarioDataStore scenarioStore;
		
	public void setPhoneUtil(PhoneUtil newPhoneUtil) {
		this.phoneUtil = newPhoneUtil;
	}
	
	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	public void checkCoverageForZipCode(String zipCode) throws Exception {
		String token = serviceUtil.genAuth();
		String output = serviceUtil.checkCoverage(token,zipCode);
		
		JSONObject result = new JSONObject(output);
		JSONObject status = result.getJSONObject("status");
		String message = (String) status.get("message");
		assertTrue(message.equalsIgnoreCase("SUCCESS"));
	}

	public void generateAPinForPartNumber(String pinPart) throws Exception {
		String token = serviceUtil.genAuth();
		serviceUtil.generatePin(token,pinPart);
	}

	public void generateAPinForPartNumberAndVoidThePin(String pinPart)
			throws Exception {
		String token = serviceUtil.genAuth();
		ArrayList<String> gpin = serviceUtil.generatePin(token,pinPart);
		String pin = gpin.get(0);
		String orderID= gpin.get(1);
		String output = serviceUtil.voidPin(token, pin, orderID);
		JSONObject result = new JSONObject(output);
		JSONObject status = result.getJSONObject("status");
		String message = (String) status.get("message");
		assertTrue(message.equalsIgnoreCase("SUCCESS"));
	}

	public void genrateAndVoidAPinForPinPartNumberAndGetStatusOfPin(String pinPart)
			throws Exception {
	
		String token = serviceUtil.genAuth();
		ArrayList<String> gpin = serviceUtil.generatePin(token,pinPart);
		String pin = gpin.get(0);
		String orderID = gpin.get(1);
		String output = serviceUtil.getStatus(serviceUtil.genAuthfor("ETAILER", "resource management"), pin);
		
		JSONObject result = new JSONObject(output);
		JSONObject status = result.getJSONObject("status");
		String message = (String) status.get("message");
		assertTrue(message.equalsIgnoreCase("SUCCESS"));
		JSONObject resourceSpecification = result.getJSONObject("resource").getJSONObject("logicalResource").getJSONObject("resourceSpecification");
		String lifecycleStatus = (String) resourceSpecification.get("lifecycleStatus");
		assertTrue(lifecycleStatus.equalsIgnoreCase("NOT REDEEMED"));
		
		serviceUtil.voidPin(token, pin, orderID);
		String output1 = serviceUtil.getStatus(serviceUtil.genAuthfor("ETAILER", "resource management"), pin);
		
		JSONObject result1 = new JSONObject(output1);
		JSONObject status1 = result1.getJSONObject("status");
		String message1 = (String) status1.get("message");
		assertTrue(message1.equalsIgnoreCase("SUCCESS"));
		JSONObject resourceSpecification1 = result1.getJSONObject("resource").getJSONObject("logicalResource").getJSONObject("resourceSpecification");
		String lifecycleStatus1 = (String) resourceSpecification1.get("lifecycleStatus");
		assertTrue(lifecycleStatus1.equalsIgnoreCase("INVALID"));
		
	}

	public void generateOAuth() throws Exception {
		serviceUtil.genAuth();
	}

	public void registerESNAndSimWithPin(String esnPart, String simPart,String pinPart) throws Exception {
		String token = serviceUtil.genAuth();
		ArrayList<String> gpin = serviceUtil.generatePin(token,pinPart);
		String pin = gpin.get(0);
		String esnStr = phoneUtil.getNewByopEsn(esnPart, simPart);
		System.out.println("ESN : " +esnStr+ " IS ATTACHED TO PIN : " + pin);
		String output = serviceUtil.RegisterEsnWithPin(serviceUtil.genAuthfor("ETAILER", "resource management"),esnStr,pin);

		JSONObject result = new JSONObject(output);
		JSONObject status1 = result.getJSONObject("status");
		String message = (String) status1.get("message");
		assertTrue(message.equalsIgnoreCase("SUCCESS"));
	
	}

	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}

}


