package com.tracfone.twist.services;

import java.io.File;
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

public class RedemptionServices {
	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private static SimUtil simUtil;
	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private static ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final String RSURL = props.getString("RedemptionServicesUrl");
	private static Logger logger = LogManager.getLogger(RedemptionServices.class.getName());
	
	public RedemptionServices() {

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
	

	public void callAddPinToReserveForPin(String pin , String flash) throws Exception  {
		
		File file = new File("../Twist-TAS/servicerequestfiles/AddPinToReserve.xml");
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
		String reservePin = phoneUtil.getNewPinByPartNumber(pin);
		
		request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@min", esn.getMin());
		request = request.replaceAll("@pin", reservePin);
		
		logger.info("Add Pin To Reserve call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, RSURL,"SOA-AddPinToReserve");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String message = validateOutput(output);
		
		if (flash.equalsIgnoreCase("cold") || flash.equalsIgnoreCase("")){
			Assert.assertTrue("Add PIN to reserve failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("success"));
		}else if(flash.equalsIgnoreCase("hot")){
			Assert.assertTrue(message+esnUtil.getCurrentESN().getEsn(),message.equalsIgnoreCase("ESN has a HOT FLASH - Cannot continue"));
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

	public void redeemServiceWithPIN(String pin) throws Exception {
		
		File file = new File("../Twist-TAS/servicerequestfiles/redeemPINCard.xml");
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
		String redeemPin = phoneUtil.getNewPinByPartNumber(pin);
		
		request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
		request = request.replaceAll("@pin", redeemPin);
		
		logger.info("Redeem PIN Card Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, RSURL,"SOA-Redemption");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String message = validateOutput(output);
		Assert.assertTrue("Redemption Services failed for esn - "+esnUtil.getCurrentESN().getEsn()+ " with status " + message,message.equalsIgnoreCase("success"));	
	}

	public void callQueuePinRequest3ci(String pin) throws Exception  {
		
		File file = new File("../Twist-TAS/servicerequestfiles/queuePINRequest3ci.xml");
		String wsdl = props.getString("3ciRedemQueueService");
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
		String reservePin = phoneUtil.getNewPinByPartNumber(pin);
		
		request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
		request = request.replaceAll("@min", esn.getMin());
		request = request.replaceAll("@pin", reservePin);
		
		logger.info("queuePINRequest(3ci) call Request: "+request);
		String response = serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdl,"SOA-queuePINRequest");
		String output = serviceUtil.parseXml(response, "//com:result/com:message").get(0);		
		String message = validateOutput(output);
		
		Assert.assertEquals("SUCCESS", message.toUpperCase());

	}

}

