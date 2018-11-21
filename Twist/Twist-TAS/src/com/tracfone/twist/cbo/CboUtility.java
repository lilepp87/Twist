package com.tracfone.twist.cbo;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CboUtility {

	private static Logger logger = LogManager.getLogger(CboUtility.class.getName());
	private ServiceUtil cboUtil;
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	public CboUtility() {
		
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setCboUtil(ServiceUtil cboUtil) {
		this.cboUtil = cboUtil;
	}	
	
	//GET FLASH BY ESN  REQUIRES  #ESN
	public void callGetFlashbyEsn(String esn) throws Exception {		
		try {
			File file = new File("../Twist-TAS/cborequestfiles/getFlashbyEsn.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			System.out.println(content);
			cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));

		} catch (IOException e) {
			// Simple exception handling, replace with what's necessary for your
			// use case!
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	// Add User REQUIRES #BRAND, #ZIPCODE, #CITY, #STATE, #LASTNAME, #EMAIL, #PASSWORD, #ESN, #DOB
	public void callAddUser(ESN esn, String city, String state, String lastname,	String dob) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/addUser.xml");
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

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String webobjid = cboUtil.parseXml(response.toString(), "/addUser/WEBUSER_OBJID").get(0);
			String output = cboUtil.parseXml(response.toString(), "/addUser/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("AddUser CBO call failed with error : ", "success", message);

			esnUtil.getCurrentESN().setEmail(webobjid);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	//ST Activation  REQUIRES  #ESN, #ZIPCODE, #ACTIONTYPE, #BRAND, #REDEMPTIONCARD, #REDEMPTIONCARD, #SIM, #REFILLTYPE
	public void callSTActivate(ESN esn, String actionType, String redcount, String redCard,	String refillType) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/STActivate.xml");
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
			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/STActivate/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("STActivate CBO call failed with error : ", "success", message);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
		//ST Refill  REQUIRES  #ESN, #BRAND, #REDEMPTIONCOUNT, #REDEMPTIONCARD, #REFILLTYPE
	public void callSTRefill(ESN esn, String redcount, String redCard, String refillType) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/STRefill.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn.getEsn());
			content = content.replaceAll("@brand", esnUtil.getCurrentBrand());
			content = content.replaceAll("@redcount", redcount);
			content = content.replaceAll("@redcard", redCard);
			content = content.replaceAll("@refilltype", refillType);
			content = content.replaceAll("@pin", esnUtil.getCurrentESN().getFromMap("securityPin"));

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			
			String output = cboUtil.parseXml(response.toString(), "/STRefill/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("STRefill CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
		//ST Refill  REQUIRES  #MIN, #BRAND, #REDEMPTIONCOUNT, #REDEMPTIONCARD, #REFILLTYPE
	public void callSTRefillForMin(String min, String brand, String redcount, String redCard, String refillType) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/STRefillForMin.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@min", min);
			content = content.replaceAll("@brand", brand);
			content = content.replaceAll("@redcount", redcount);
			content = content.replaceAll("@redcard", redCard);
			content = content.replaceAll("@refilltype", refillType);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/STRefillForMin/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("STRefillForMin CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		

		//ST RefillCC  REQUIRES  #ESN, #ZIP, #ACTIONTYPE, #REDEMPTIONCOUNT, #PLAN, #REFILLTYPE, #AUTOREFILL, #BRAND
	public void callSTRefillCC(String esn, String zip, String actiontype, String redcount, String plan, String refilltype, String autorefill,
			String brand, String pin, String ARflag, String cardType, String cvv) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/STRefillCC.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@zip", zip);
			content = content.replaceAll("@actiontype", actiontype);
			content = content.replaceAll("@redcount", redcount);
			content = content.replaceAll("@plan", plan);
			content = content.replaceAll("@refilltype", refilltype);
			content = content.replaceAll("@autorefill", autorefill);
			content = content.replaceAll("@brand", brand);
			content = content.replaceAll("@pin", pin);
			content = content.replaceAll("@ARflag", ARflag);
			content = content.replaceAll("@cardType", cardType);
			content = content.replaceAll("@cvv", cvv);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/STRefillCC/DTL_ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("STRefillCC CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
		
		//Deactivate Phone  REQUIRES  #ESN, #MIN, #REASON, #BRAND
	public void callDeactivatePhone(String esn, String min, String reason, String brand) throws Exception {
		try {
			File file = new File("../Twist-TAS/cborequestfiles/deactivatePhone.xml");
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@min", min);
			content = content.replaceAll("@reason", reason);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
						
			String output = cboUtil.parseXml(response.toString(), "/deactivatePhone/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("deactivatePhone CBO call failed with error : ", "success", message);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callValidateMINorESN(String esnOrMinFlag, String esnOrMin, String brand) throws Exception {
		try {
			File file = null;
			if (esnOrMinFlag.equalsIgnoreCase("ESN")) {
				file = new File("../Twist-TAS/cborequestfiles/ValidateMINorESNwithESN.xml");
			} else if (esnOrMinFlag.equalsIgnoreCase("MIN")) {
				file = new File("../Twist-TAS/cborequestfiles/ValidateMINorESNwithMIN.xml");
			}

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esnOrMin", esnOrMin);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			
			String output = cboUtil.parseXml(response.toString(), "/ValidateMINorESN/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("ValidateMINorESN CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callValidateESN(String esn, String brand) throws Exception {
		try {
			File file = null;
			file = new File("../Twist-TAS/cborequestfiles/ValidateESN.xml");

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/ValidateESN/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("ValidateESN CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callValidateMINCase(String esn, String brand) throws Exception {
		try {
			File file = null;
			file = new File("../Twist-TAS/cborequestfiles/ValidateMINCase.xml");

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/ValidateMINCase/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("ValidateMINCase CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callVerifyTechnology(String esn, String zip, String brand, String sim) throws Exception {
		try {
			File file = null;
			file = new File("../Twist-TAS/cborequestfiles/VerifyTechnology.xml");

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@zip", zip);
			content = content.replaceAll("@brand", brand);
			content = content.replaceAll("@sim", sim);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			
			String errorNum = cboUtil.parseXml(response.toString(), "/VerifyTechnology/ERROR_NUM").get(0);
			String message = validateOutput(errorNum);
			assertEquals("VerifyTechnology CBO call failed with error : ", "success", message);
			
			String carrierID = cboUtil.parseXml(response.toString(), "/VerifyTechnology/PREFERRED_CARRIER_MARKET_ID").get(0);
			esnUtil.getCurrentESN().setCommerceId(carrierID);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callCreateContact(String esn, String zip, String brand) throws Exception {
		try {
			File file = null;
			file = new File("../Twist-TAS/cborequestfiles/CreateContact.xml");

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@zip", zip);
			content = content.replaceAll("@brand", brand);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			
			String output = cboUtil.parseXml(response.toString(), "/CreateContact/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("CreateContact CBO call failed with error : ", "success", message);
			
			String contactobjid = cboUtil.parseXml(response.toString(), "/CreateContact/CONTACT_OBJID").get(0);
			esnUtil.getCurrentESN().setEmail(contactobjid);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
	public void callGenCodes(String esn, String contactobjid, String zip, String brand, String redcard, String sim, String carrierid)
			throws Exception {
		try {
			File file = null;
			file = new File("../Twist-TAS/cborequestfiles/GenCodes.xml");

			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("@esn", esn);
			content = content.replaceAll("@contactobjid", contactobjid);
			content = content.replaceAll("@zip", zip);
			content = content.replaceAll("@brand", brand);
			content = content.replaceAll("@redcard", redcard);
			content = content.replaceAll("@sim", sim);
			content = content.replaceAll("@carrierid", carrierid);

			System.out.println(content);

			StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
			String output = cboUtil.parseXml(response.toString(), "/GenCodes/ERROR_STRING").get(0);
			String message = validateOutput(output);
			assertEquals("GenCodes CBO call failed with error : ", "success", message);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
		
		public void callValidateUpgrade(String fromESN, String toESN, String toSIM, String zip, String brand) throws Exception {
			
			try {
				File file = null;
				file = new File("../Twist-TAS/cborequestfiles/ValidatePhoneUpgrade.xml");
				
				String content = FileUtils.readFileToString(file, "UTF-8");
				content = content.replaceAll("@fromESN", fromESN);
				content = content.replaceAll("@toESN", toESN);
				content = content.replaceAll("@toSIM", toSIM);
				content = content.replaceAll("@zip", zip);
				content = content.replaceAll("@brand", brand);
										
				System.out.println(content);
						    
				StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content,"UTF-8"));
				
				String output = cboUtil.parseXml(response.toString(), "/ValidatePhoneUpgrade/ERROR_STRING").get(0);
				String message = validateOutput(output);
				assertEquals("ValidatePhoneUpgrade CBO call failed with error : ", "success", message);
													
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Generating file failed", e);
				}
		}
		
		public void callPhoneUpgrade(String fromESN, String toESN, String toSIM, String zip, String brand, String pin) throws Exception {
			
			try {
				File file = null;
				file = new File("../Twist-TAS/cborequestfiles/PhoneUpgradeReq.xml");
				
				String content = FileUtils.readFileToString(file, "UTF-8");
				content = content.replaceAll("@fromESN", fromESN);
				content = content.replaceAll("@toESN", toESN);
				content = content.replaceAll("@toSIM", toSIM);
				content = content.replaceAll("@pin", pin);
				content = content.replaceAll("@zipcode", zip);
				content = content.replaceAll("@brand", brand);
										
				System.out.println(content);
						    
				StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content,"UTF-8"));
				String output = cboUtil.parseXml(response.toString(), "/PhoneUpgradeReq/ERROR_NUM").get(0);
				String message = validateOutput(output);
				assertEquals("PhoneUpgradeReq CBO call failed with error : ", "success", message);
													
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Generating file failed", e);
				}
		}
		
		public void callValidateRedCard(String esn) throws Exception {
			
			try {
				File file = null;
				file = new File("../Twist-TAS/cborequestfiles/ValidateRedCardBatch.xml");
				
				String content = FileUtils.readFileToString(file, "UTF-8");
				content = content.replaceAll("@esn", esn);
				content = content.replaceAll("@pin", esnUtil.getCurrentESN().getFromMap("redCard"));
										
				System.out.println(content);
						    
				StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content,"UTF-8"));
				
				String lines = cboUtil.parseXml(response.toString(), "/ValidateRedCardBatch/AVAILABLE_CAPACITY_0").get(0);
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
				
				assertEquals("ValidateRedCardBatch failed : ", "Success", status);
															
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Generating file failed", e);
				}
		}
		
		public void simulateBalanceInquiry(String fromEsn) throws Exception{
			try {
				File file = new File("../Twist-TAS/cborequestfiles/simulatebalance.xml");  
			     String content = FileUtils.readFileToString(file, "UTF-8");
			     content = content.replaceAll("@fromesn", fromEsn);
			    System.out.println(content);
			    //String url = props.getString("cbourl");
			    StringBuffer respBuffer = new StringBuffer();
			    for(int i=0 ; i<=2 ; i++){
					TwistUtils.setDelay(1);
					respBuffer = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content,"UTF-8"));
				}
			    Assert.assertTrue(respBuffer.toString().contains("Success") || respBuffer.toString().contains("success"));
			
			  } catch (IOException e) {
				  e.printStackTrace();
			     throw new RuntimeException("Generating file failed", e);
			  }
			//String record = phoneUtil.checkBI(fromEsn);
			//Assert.assertNotNull(record);
		}
		
		public void callServiceUpdateTicketAndSendMessage() throws Exception {
			try {
				
				File file = new File("../Twist-TAS/cborequestfiles/updateTicketAndSendMessage.xml");

				String content = FileUtils.readFileToString(file, "UTF-8");
				content = content.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
				
				System.out.println(content);

				StringBuffer response = cboUtil.callCboMethodWithRequest(URLEncoder.encode(content, "UTF-8"));
				
				String output = cboUtil.parseXml(response.toString(), "/updateTicketAndSendMessage/ERROR_STRING").get(0);
				String message = validateOutput(output);
				assertEquals("updateTicketAndSendMessage CBO call failed with error : ", "success", message);
				String TicketID = cboUtil.parseXml(response.toString(), "/updateTicketAndSendMessage/TICKET_ID").get(0);
				esnUtil.getCurrentESN().putInMap("TicketID", TicketID);

			} catch (IOException e) {
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