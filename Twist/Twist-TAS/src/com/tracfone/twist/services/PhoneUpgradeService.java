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

public class PhoneUpgradeService {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String ServiceURL = props.getString("PhoneUpgradeUrl");
	private static Logger logger = LogManager.getLogger(PhoneUpgradeService.class.getName());

	public PhoneUpgradeService() {

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

	public void callPhoneUpgradeService(String flash, String plan) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/PhoneUpgrade.xml");
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
		System.out.println("From ESN - " + esn.getFromEsn().getEsn());
		request = request.replaceAll("@fromESN", esn.getFromEsn().getEsn());
		request = request.replaceAll("@toESN", esn.getEsn());
		request = request.replaceAll("@iccid", esn.getSim());
		request = request.replaceAll("@zipcode", esn.getZipCode());
		request = request.replaceAll("@plan", plan);

		logger.info("PhoneUpgrade call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, ServiceURL,"SOA-PhoneUpgrade");
		
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String status = validateOutput(output);
		
		if (flash.equalsIgnoreCase("cold") || flash.equalsIgnoreCase("")){
			Assert.assertTrue("Upgrade failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("success"));
		}else if(flash.equalsIgnoreCase("hot")){
			Assert.assertTrue("ESN has a HOT FLASH - Cannot continue for esn - "+esnUtil.getCurrentESN().getEsn(),status.equalsIgnoreCase("ESN has a HOT FLASH - Cannot continue"));
		}
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
