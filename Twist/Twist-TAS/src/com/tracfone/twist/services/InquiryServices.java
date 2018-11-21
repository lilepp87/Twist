package com.tracfone.twist.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.TwistUtils;

public class InquiryServices {
	@Autowired
	private TwistScenarioDataStore scenarioStore;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private ServiceUtil serviceUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private MyAccountFlow myAccountFlow;
	private static Logger logger = LogManager.getLogger(InquiryServices.class.getName());

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void inquireForBalance() throws Exception {
		String wsdlURL = props.getString("InquiryServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/getBalancedetails.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String brand=esnUtil.getCurrentBrand();
			String request = FileUtils.readFileToString(file, "UTF-8");
			String TASURL=props.getString("TAS.HomeUrl");
			if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
				request = request.replaceAll("@secusername", props.getString("services.tstusername"));
				request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
			}else{
				request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
				request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
			}
			if(brand.equalsIgnoreCase("GO_SMART")){
				request = request.replaceAll("@Brand", "SIMPLE_MOBILE");
			}else{
				request = request.replaceAll("@Brand", esnUtil.getCurrentBrand());
			}
			
			request = request.replaceAll("@MIN", currentESN.getMin());
			
			logger.info("Balance Inquiry Request: "+request);
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"SOA-GetBalance");
			String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
			String status = validateOutput(output);			
			Assert.assertTrue("Balance Inquiry failed for esn - "+ esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Success"));
						
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public String validateOutput(String output) throws Exception {
		
		String result=output;
		if(output.equalsIgnoreCase("Success")  || output.equalsIgnoreCase("Successful.") || output.equalsIgnoreCase("Sucess") || output.equalsIgnoreCase("Success.") || output.equalsIgnoreCase("") || output.equalsIgnoreCase("0")){
			result = "success";
		} 
		return result;
	}
}


