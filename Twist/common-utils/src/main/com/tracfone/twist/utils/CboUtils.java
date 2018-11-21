package com.tracfone.twist.utils;

import static junit.framework.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CboUtils {

	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private ServiceUtil serviceUtil;
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	public void callAddUser(ESN esn, String city, String state, String lastname, String dob) throws Exception {
		try {
			File file = new File("../common-utils/cborequestfiles/addUser.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@brand", esnUtil.getCurrentBrand());
			content = content.replaceAll("@zip", esn.getZipCode());
			content = content.replaceAll("@city", city);
			content = content.replaceAll("@state", state);
			content = content.replaceAll("@lastname", lastname);
			content = content.replaceAll("@email", esn.getEmail());
			content = content.replaceAll("@password", esn.getPassword());
			content = content.replaceAll("@esn", esn.getEsn());
			content = content.replaceAll("@dob", dob);
			content = content.replaceAll("@pin", esn.getFromMap("securityPin"));

			System.out.println(content);

			StringBuffer response = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String webobjid = parseResponse(response, "AddUser");
			esnUtil.getCurrentESN().setEmail(webobjid);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void callSTActivate(ESN esn, String actionType, String redcount, String redCard,	String refillType) throws Exception {
		try {
			File file = new File("../common-utils/cborequestfiles/STActivate.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn.getEsn());
			content = content.replaceAll("@zip", esn.getZipCode());
			content = content.replaceAll("@actiontype", actionType);
			content = content.replaceAll("@brand", esnUtil.getCurrentBrand());
			content = content.replaceAll("@redcount", redcount);
			content = content.replaceAll("@redcard", redCard);
			content = content.replaceAll("@sim", esn.getSim());
			content = content.replaceAll("@refilltype", refillType);
			content = content.replaceAll("@webobjid", esn.getEmail());
			content = content.replaceAll("@pin", esn.getFromMap("securityPin"));

			System.out.println(content);
			StringBuffer response = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			parseResponse(response, "STActivate");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void callDeactivatePhone(String esn, String min, String reason, String brand) throws Exception {
		try {
			File file = new File("../common-utils/cborequestfiles/deactivatePhone.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@min", min);
			content = content.replaceAll("@reason", reason);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			parseResponse(response, "DeactivatePhone");

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void completePhoneActivationForAndStatus(String cellTech, String status)
			throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		System.out.println(currEsn.getEsn());
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "TAS Activation["+esnUtil.getCurrentBrand()+"]");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}
	
	public String parseResponse(StringBuffer sbf, String serviceType) {
		String message = null;
		String result = null;
		String errorNum = null;
		try {
			byte[] bytes = sbf.toString().getBytes();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			String rootNode = document.getDocumentElement().getNodeName();
			NodeList nlist = document.getElementsByTagName(rootNode);
			try {
				message = (String) ((Element) nlist.item(0)).getElementsByTagName("ERROR_STRING").item(0).getChildNodes().item(0).getNodeValue();
				System.out.println(message);
					if(message.contains("Success")  || message.contains("Sucess") || message.contains("SUCCESS") || message.equalsIgnoreCase("PhoneUpgrade Request has been scheduled to be processed in 2 minutes - Pls. disconnect") || message.equalsIgnoreCase("")){
					result = "success";
				} else {
					result = "unsuccessful";
				}
			} catch (NullPointerException e) {
				result = "success";
			}
			if (serviceType.equalsIgnoreCase("VerifyTechnology")) {
				errorNum = (String) ((Element) nlist.item(0)).getElementsByTagName("ERROR_NUM").item(0).getChildNodes().item(0).getNodeValue();
				assertEquals(errorNum, "0");
				String carrierId = (String) ((Element) nlist.item(0)).getElementsByTagName("PREFERRED_CARRIER_MARKET_ID").item(0).getChildNodes()
						.item(0).getNodeValue();
				return carrierId;
			} else if (serviceType.equalsIgnoreCase("AddUser")) {
				String webobjid = (String) ((Element) nlist.item(0)).getElementsByTagName("WEBUSER_OBJID").item(0).getChildNodes().item(0)
						.getNodeValue();
				return webobjid;
			} else if (serviceType.equalsIgnoreCase("CreateContact")) {
				String contactobjid = (String) ((Element) nlist.item(0)).getElementsByTagName("CONTACT_OBJID").item(0).getChildNodes().item(0)
						.getNodeValue();
				return contactobjid;
			} else if (serviceType.equalsIgnoreCase("ValidateRedCardBatch")) {
				String lines = (String) ((Element) nlist.item(0)).getElementsByTagName("AVAILABLE_CAPACITY_0").item(0).getChildNodes().item(0)
						.getNodeValue();
				String pinPart = esnUtil.getCurrentESN().getFromMap("pinPart");
				String status = null;
				if((pinPart.equalsIgnoreCase("TWAPP00025") || pinPart.equalsIgnoreCase("TWAPP00035")) && lines.equalsIgnoreCase("1")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00060") && lines.equalsIgnoreCase("2")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00085") && lines.equalsIgnoreCase("3")){
					status = "Success";
				}else if(pinPart.equalsIgnoreCase("TWAPP00110") && lines.equalsIgnoreCase("4")){
					status = "Success";
				}else status = "Failure";
				return status;
			}else if (serviceType.equalsIgnoreCase("updateTicketAndSendMessage")){
				message = (String) ((Element) nlist.item(0)).getElementsByTagName("TICKET_ID").item(0).getChildNodes().item(0).getNodeValue();
				System.out.println("TicketID:" + message);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";

	}
	
	public void callcompleteAutomatedPortins(String Actionitemid , String Min) throws Exception {
		try {
			File file = new File("../common-utils/cborequestfiles/completeAutomatedPortins.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@actionitemid", Actionitemid);
			content = content.replaceAll("@min", Min);
						
			System.out.println(content);

			StringBuffer response = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = serviceUtil.parseXml(response.toString(), "/completeAutomatedPortins/ERROR_STRING").get(0);
			assertEquals("completeAutomatedPortins CBO call failed with error : ", "Success", output); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	public String callGetPassword(String email,String brand) throws Exception {
		String output="";
		try {
			if(brand.equalsIgnoreCase("go_smart")){
				brand= "simplemobile";
			}
			File file = new File("../common-utils/cborequestfiles/getPassword.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@email", email);
			content = content.replaceAll("@brand", brand);
						
			System.out.println(content);

			StringBuffer response = serviceUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			output = serviceUtil.parseXml(response.toString(), "/getPassword/PASSWORD").get(0);
			
			System.out.println("Account Password : "+output);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
		
		
		return output;
	}
}
