package com.tracfone.twist.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
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

public class EnrollmentServices {

	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static SimUtil simUtil;
	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String ESURL = props.getString("EnrollmentServicesUrl");
	private static Logger logger = LogManager.getLogger(RedemptionServices.class.getName());
	
	public EnrollmentServices()
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
	

	public void callAREnrollment(String flash) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/AREnrollment.xml");
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
		request = request.replaceAll("@min", esn.getMin());
		request = request.replaceAll("@card", esn.getLastFourDigits());

		logger.info("SMS Enroll : "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, ESURL, "SOA-SmsEnroll");
		
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String status = validateOutput(output);
		
		if (flash.equalsIgnoreCase("cold") || flash.equalsIgnoreCase("")){
			Assert.assertTrue("Enrollment failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Inserts are Successful"));
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




 