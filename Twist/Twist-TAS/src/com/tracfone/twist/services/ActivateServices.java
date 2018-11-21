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

public class ActivateServices {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String wsURL = props.getString("ActivationServicesUrl");
	private static final String wsURL1 = props.getString("smActivateServiceUrl");
	private static final String PIURL = props.getString("callPlanInformationUrl");
	private static final String SURL = props.getString("callSimExchangeServiceUrl");
	private static Logger logger = LogManager.getLogger(ActivateServices.class.getName());

	public ActivateServices() {

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

	public void callActivateServiceWithPIN(String pinPart , String flash) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/activateService.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		ESN esn = esnUtil.getCurrentESN();
		String pin = phoneUtil.getNewPinByPartNumber(pinPart); 
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@pin", pin);
		request = request.replaceAll("@min", esn.getMin());

		logger.info("ReactivateService call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"SOA-activateService");
		
		String message = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String status = validateOutput(message);
		if (flash.equalsIgnoreCase("cold") || flash.equalsIgnoreCase("")){
			Assert.assertTrue("Reactivation failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("success")); 
		}else if(flash.equalsIgnoreCase("hot")) {
			Assert.assertTrue("ESN has a HOT FLASH - Cannot continue for esn - "+esnUtil.getCurrentESN().getEsn(),status.equalsIgnoreCase("ESN has a HOT FLASH - Cannot continue"));
		}
				
	}

	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}

	public String validateOutput(String output) throws Exception {
		
		String result=output;
		if(output.equalsIgnoreCase("Success")  || output.equalsIgnoreCase("Successful.") || output.equalsIgnoreCase("Sucess") || output.equalsIgnoreCase("Success.") || output.equalsIgnoreCase("") || output.equalsIgnoreCase("0") || output.equalsIgnoreCase("COMPLETE")){
			result = "success";
		} 
		return result;
	}
	public void callSMActivateServices(String partNumber,
			String sim, String pinNumber ,String zipCode , String tspId) throws Exception {
		ESN esn;
		esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, sim));
		esnUtil.setCurrentESN(esn);
		String newSim = phoneUtil.getSimFromEsn(esn.getEsn());
		String newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
		
		
		File file = new File("../Twist-TAS/servicerequestfiles/smActivateService.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
	
		
		
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		
		request = request.replaceAll("@pin",newPin );
		request = request.replaceAll("@esn",esn.getEsn());
		request = request.replaceAll("@sim", newSim);
		request = request.replaceAll("@zipcode", zipCode);
		request = request.replaceAll("@tspid" ,tspId);

		logger.info("ActivateService call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL1,"SOA-activateService");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String message = validateOutput(output);
		Assert.assertTrue("SmActivateService failed for esn - "+esn.getEsn()+ " with status " + message,message.equalsIgnoreCase("success"));
		
	}

	public void activateServiceWithPIN(String pinPart) throws Exception {

		File file = new File("../Twist-TAS/servicerequestfiles/soaActivateService.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
			
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		request = request.replaceAll("@brand",esnUtil.getCurrentBrand());
		request = request.replaceAll("@zip",esnUtil.getCurrentESN().getZipCode());
		request = request.replaceAll("@sim", esnUtil.getCurrentESN().getSim());
		request = request.replaceAll("@pin", pin);
		request = request.replaceAll("@esn" ,esnUtil.getCurrentESN().getEsn());

		logger.info("Activation call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsURL,"SOA-activateService");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String message = validateOutput(output);
		Assert.assertTrue("ActivateService failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("success"));
		
	}

	public void callTheBYOPRegistraionServiceForAndModelForIn(String isLTE,
			String isIphone, String brand, String zipcode, String isHD) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/soaRegisterBYOP.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date dateObj = new Date();
		String formatedDate = formatter.format(dateObj);
		
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
	
		ESN esn;
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		esn = new ESN("BYOP", esnStr);
		esnUtil.setCurrentESN(esn);
		request = request.replaceAll("@brand",brand);
		request = request.replaceAll("@zip",zipcode);
		request = request.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
		request = request.replaceAll("@date",formatedDate);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, props.getString("ServiceQualificationServices"),"SOA-RegisterBYOP");
		
		int edited = 0;
		for (int i = 0; i < 10 && edited == 0; i++) {
			TwistUtils.setDelay(3);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, "RSS", "NO", isLTE, isIphone, isHD);
			System.out.println("edit: " + edited);
		}
	}

	public void callPlanInformation() throws Exception {

		File file = new File("../Twist-TAS/servicerequestfiles/soaCallPlanInformation.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
			
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		request = request.replaceAll("@brand",esnUtil.getCurrentBrand());
		request = request.replaceAll("@min",esnUtil.getCurrentESN().getMin());

		logger.info("Call Plan Information: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, PIURL,"SOA-callPlanInformationThrough611611");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String message = validateOutput(output);
		Assert.assertTrue("ActivateService failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("SUCCESS"));
		
	}

	public void callSimExchangeService(String newSiM) throws Exception {
	
		File file = new File("../Twist-TAS/servicerequestfiles/soaCallSimExchangeService.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String TASURL=props.getString("TAS.HomeUrl");
			
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
		String sim = simUtil.getNewSimCardByPartNumber(newSiM);
		request = request.replaceAll("@brand",esnUtil.getCurrentBrand());
		request = request.replaceAll("@min",esnUtil.getCurrentESN().getMin());
		request = request.replaceAll("@esn",esnUtil.getCurrentESN().getEsn());
		request = request.replaceAll("@newsim",sim);

		logger.info("Call Sim Exchange Service: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SURL,"SOA-callSimExchangeService");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);
		String message = validateOutput(output);
		Assert.assertTrue("ActivateService failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("SUCCESS"));
		
	}

}
