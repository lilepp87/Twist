package com.tracfone.twist.services;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;

public class LRPEnrollmentServices {
	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static SimUtil simUtil;
	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String RSURL = props.getString("LRPEnrollmentServicesUrl");
	private static Logger logger = LogManager.getLogger(LRPEnrollmentServices.class.getName());
	
	public LRPEnrollmentServices()
	{}
	

	
	
	
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
	

	public void callLRPEnrollment() throws Exception  {
		
		File file = new File("../Twist-TAS/servicerequestfiles/LRPEnrollment.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		ESN esn = esnUtil.getCurrentESN();
		//String reservePin = phoneUtil.getNewPinByPartNumber(pin);
		
		//request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@min", esn.getMin());
		//request = request.replaceAll("@pin", reservePin);
		
		logger.info("LRP Enrollment Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, RSURL,"SOA-LRPEnrollment");
		
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String message = validateOutput(output);
		Assert.assertTrue("LRPEnrollment failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("success"));
		
	}

	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}
	
	public String validateOutput(String output) throws Exception {
		
		String result=output;
		if(output.equalsIgnoreCase("Success")  || output.equalsIgnoreCase("Successful.") || output.equalsIgnoreCase("Sucess") || output.equalsIgnoreCase("Success.") || output.equalsIgnoreCase("") || output.equalsIgnoreCase("0")){
			result = "success";
		} 
		return result;
	}








}



