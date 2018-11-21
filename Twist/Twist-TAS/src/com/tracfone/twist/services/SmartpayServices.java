package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import java.io.*;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;

public class SmartpayServices {

	private MyAccountFlow myAccountFlow;
	private PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String SPURL = props.getString("SmartPayUrl");
	private static Logger logger = LogManager.getLogger(SmartpayServices.class.getName());

	public SmartpayServices() {

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

	public void closeBrowser() throws Exception {
		myAccountFlow.getBrowser().close();
	}

	public void callGetHandSetInfo() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getHandSetInfo.xml");
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
	
		request = request.replaceAll("@esn", esn.getEsn());
		logger.info("HandsetInfo call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SPURL, "SOA-GetHandsetInfo"); 	

		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String status = validateOutput(output);
		Assert.assertTrue("GetHandsetInfo failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Success"));
	}
	
	public void callGetEsnFromMin() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getEsnFromMin.xml");
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
		request = request.replaceAll("@min", phoneUtil.getMinOfActiveEsn(esn.getEsn()));
		
		logger.info("GetEsnFromMin call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SPURL, "SOA-GetEsnFromMin");
	 	
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String status = validateOutput(output);
		Assert.assertTrue("GetEsnFromMin failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Success"));
	}
	
	public void callGetEsnFromSim() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getEsnFromSim.xml");
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
		request = request.replaceAll("@sim", esn.getSim());
		
		logger.info("GetEsnFromSim call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SPURL, "SOA-GetEsnFromSim");

		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String status = validateOutput(output);
		Assert.assertTrue("GetEsnFromSim failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("Success"));
	}

	public void callGetEsnAttributes() throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getEsnAttributes.xml");
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
		request = request.replaceAll("@esn", esn.getEsn());
		logger.info("GetEsnAttributes call Request: "+ request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SPURL, "SOA-GetEsnAttributes");

		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String status = validateOutput(output);
		Assert.assertTrue("GetEsnAttributes failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + status,status.equalsIgnoreCase("success"));
	}

	public void callGetInformationOfPIN(String pinPart) throws Exception {
		File file = new File("../Twist-TAS/servicerequestfiles/getInfoOfPin.xml");
		String request = FileUtils.readFileToString(file, "UTF-8");
		String pin = phoneUtil.getUsedPinOfEsn(esnUtil.getCurrentESN().getEsn());
		System.out.println(" PIN - "+ pin);
		String TASURL=props.getString("TAS.HomeUrl");
		if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
		}else{
			request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
		}
	    request = request.replaceAll("@pin", pin);
		logger.info("GetInfoForPin call Request: "+ request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, SPURL, "SOA-GetInfoForPin");
		String redemptionStatus = serviceUtil.parseXml(response, "//ns2:getInfoForPinResponse/ns2:isRedeemed").get(0);				
		Assert.assertTrue("GetInfoForPin failed for pin - "+ pin,redemptionStatus.equalsIgnoreCase("Y"));
		String reservedStatus = serviceUtil.parseXml(response, "//ns2:getInfoForPinResponse/ns2:isReserved").get(0);
		String redeemDate = serviceUtil.parseXml(response, "//ns2:getInfoForPinResponse/ns2:redeemDate").get(0);
		System.out.println("Redemption status : " + redemptionStatus);
		System.out.println("Reserved status : " + reservedStatus);
		System.out.println("Redemption date : " + redeemDate);	
		
	}
	
	public String validateOutput(String output) throws Exception {
		
		String result=output;
		if(output.equalsIgnoreCase("Success")  || output.equalsIgnoreCase("Successful.") || output.equalsIgnoreCase("Sucess") || output.equalsIgnoreCase("Success.") || output.equalsIgnoreCase("") || output.equalsIgnoreCase("0")){
			result = "success";
		} 
		return result;
	}

	
}
