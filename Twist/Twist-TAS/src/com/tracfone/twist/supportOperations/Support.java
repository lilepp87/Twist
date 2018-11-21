package com.tracfone.twist.supportOperations;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.sahi.client.Browser;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Support {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;

	private ServiceUtil cboUtil;
	private static final String NEW_STATUS = "New";
	private static final String ACTIVE_STATUS = "Active";
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");

	public Support() {

	}

	public void goToDealerPasswordReset() throws Exception {
		myAccountFlow.clickLink("Support");
		myAccountFlow.clickLink("Dealer Password Reset");
	}

	public void fillTheDetailsOnFormAndEmail(String dealerEmail)
			throws Exception {
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", "TwistLastName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", dealerEmail);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "Test Store");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").setValue("Test notes by ITQ");
		clickButton("Create Ticket");
	}

	public void checkForConfirmation() throws Exception {
		Assert.assertTrue(myAccountFlow.cellVisible("/.*Case Created Id.*/"));
	}

	private void clickButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setCboUtil(ServiceUtil cboUtil) {
		this.cboUtil = cboUtil;
	}

	public void getActiveEsnForPart(String partNumber) throws Exception {
		ESN esn = new ESN(partNumber,
				phoneUtil.getActiveEsnByPartNumber(partNumber));
		esnUtil.setCurrentESN(esn);
		phoneUtil.clearOTAforEsn(esn.getEsn());
	}

	public void enterCurrentEsn() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField(
				"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", esn);
		clickButton("Search Service");
		// Continue to profile page.
		checkForTransferButton();
	}

	private void checkForTransferButton() {
		if (getButtonType("Continue to Service Profile")) {
			pressButton("Continue to Service Profile");
		}

	}

	public boolean getButtonType(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName)
				|| myAccountFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}

	public void passUrlFromIVRToTASForTask(String taskID) throws Exception {
		String ivrUrl = props.getString("TAS.HomeUrl")
				+ "faces/adf.task-flow?adf.tfId=console-login-flow&adf.tfDoc=/WEB-INF/"
				+ "console-login-flow.xml&esn="
				+ esnUtil.getCurrentESN().getEsn() + "&task_id=" + taskID;
		// System.out.println(ivrUrl);
		myAccountFlow.navigateTo(ivrUrl);
	}

	public void checkPageFor(String taskName) throws Exception {
		// Assert.assertTrue(myAccountFlow.spanVisible(esnUtil.getCurrentESN().getEsn()));
		Assert.assertTrue(myAccountFlow.h2Visible(taskName));
	}

	public void goToFlashContact() throws Exception {
		pressButton("Contact Profile");
		myAccountFlow.clickLink("Flash Contact");
	}

	public void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void createNewFlashMessage() throws Exception {
		String currentDate = dateFormat.format(new Date());
		pressButton("New Flash");
		myAccountFlow
				.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/",
						"ITQ_TEST_FLASH_MESSAGE");
		myAccountFlow
				.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:id2::content/",
						currentDate);
		myAccountFlow
				.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:id1::content/",
						currentDate);
		myAccountFlow
				.chooseFromSelect(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3::content/",
						"Active");
		myAccountFlow
				.chooseFromSelect(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/",
						"Yes");
		// myAccountFlow.typeInTextArea("/r2:\\d:r\\d:\\d:rte1::src/",
		// "DISPLAY FLASH MESSAGE TEST BY ITQ");
		pressButton("Save");
	}

	public void callValidateEsnMinUpgradeChangeMinMethod() throws Exception {
		String payload = "<INPUT TASK=\"ValidateEsnMinUpgradeChangeMin\"><ESN>"
				+ esnUtil.getCurrentESN().getEsn()
				+ "</ESN><VALIDATION_TYPE>1</VALIDATION_TYPE><SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
				+ esnUtil.getCurrentBrand() + "</BRAND_NAME></INPUT>";

		StringBuffer sbf = cboUtil.callCboMethodWithRequest(payload);
		
		String output = cboUtil.parseXml(sbf.toString(), "/ValidateEsnMinUpgradeChangeMin/ERROR_STRING").get(0);
		assertEquals("ValidateEsnMinUpgradeChangeMin CBO call failed with error : ", "Success", output);
		/*
		 * <INPUT TASK="ValidateEsnMinUpgradeChangeMin">
		 * <ESN>100000002179157</ESN> <VALIDATION_TYPE>1</VALIDATION_TYPE>
		 * <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM> <BRAND_NAME>NET10</BRAND_NAME>
		 * </INPUT>
		 */

		/*
		 * <INPUT TASK="ValidateMINCase"> <ESN>100000002178605</ESN>
		 * <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM> <BRAND_NAME>NET10</BRAND_NAME>
		 * </INPUT>
		 */
	}

	public void insertAlertForEsnWithTypeAndId(int flashType, String status)
			throws Exception {
		phoneUtil.setFlashforEsn(esnUtil.getCurrentESN().getEsn(), flashType,
				status);
	}

	public void callValidateMINCaseMethodForPartStatusBrand(String part,
			String status, String brand) throws Exception {
		if (status.equalsIgnoreCase("new")) {
			ESN currentEsn = new ESN(part,
					phoneUtil.getNewEsnByPartNumber(part));
			esnUtil.setCurrentESN(currentEsn);
			esnUtil.setCurrentBrand(brand);
			String payload = "<INPUT TASK=\"ValidateMINCase\"><ESN>"
					+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN> <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ brand + "</BRAND_NAME></INPUT>";

			cboUtil.callCboMethodWithRequest(payload);
		} else if (status.equalsIgnoreCase("active")) {
			String payload = "<INPUT TASK=\"ValidateMINCase\"><ESN>"
					+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN> <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ brand + "</BRAND_NAME></INPUT>";
			cboUtil.callCboMethodWithRequest(payload);
		}
	}

	public void callValidateMINCaseMethod() throws Exception {
		String payload = "<INPUT TASK=\"ValidateMINCase\"><ESN>"
				+ esnUtil.getCurrentESN().getEsn()
				+ "</ESN> <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
				+ esnUtil.getCurrentBrand() + "</BRAND_NAME></INPUT>";
		cboUtil.callCboMethodWithRequest(payload);
	}

	public void callGetflashbyesnMethodForPart() throws Exception {
		String payload = "<INPUT TASK=\"getFlashByESN\"><ESN>"
				+ esnUtil.getCurrentESN().getEsn() + "</ESN></INPUT>";
		cboUtil.callCboMethodWithRequest(payload);

	}

	public void callGetflashbyesnMethodForPart(String part) throws Exception {

		ESN currentEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
		esnUtil.setCurrentESN(currentEsn);
		String payload = "<INPUT TASK=\"getFlashByESN\"><ESN>"
				+ esnUtil.getCurrentESN().getEsn() + "</ESN></INPUT>";
		StringBuffer sb = cboUtil.callCboMethodWithRequest(payload);
		String output = cboUtil.parseXml(sb.toString(), "/getFlashByESN/ERROR_STRING").get(0);
		assertEquals("getFlashByESN CBO call failed with error : ", "Success", output);		 
	}
	
	
	public void getEsnForPartOfStatusBrand(String part, String status,
			String brand) throws Exception {
String payload = null;
		if(status.equalsIgnoreCase("new")){
			ESN currentEsn = new ESN(part,
					phoneUtil.getNewEsnByPartNumber(part));
			esnUtil.setCurrentESN(currentEsn);
			esnUtil.setCurrentBrand(brand);
		/*	 payload = "<INPUT TASK=\"ValidateMINCase\"><ESN>"
					+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN> <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ esnUtil.getCurrentBrand() + "</BRAND_NAME></INPUT>";*/
		}else if (status.equalsIgnoreCase("active")){
			ESN currentEsn = new ESN(part,phoneUtil.getActiveEsnByPartNumber(part));
					
			esnUtil.setCurrentESN(currentEsn);
			/*		payload = "<INPUT TASK=\"ValidateMINCase\"><ESN>"
				+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN> <SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ esnUtil.getCurrentBrand() + "</BRAND_NAME></INPUT>";*/
		}
		

		//cboUtil.callCboMethodWithRequest(payload);
	}

	public void callValidateEsnMinUpgradeChangeMinMethodForPartStatusBrand(
			String part, String status, String brand) throws Exception {
		if (status.equalsIgnoreCase("new")) {
			ESN currentEsn = new ESN(part,
					phoneUtil.getNewEsnByPartNumber(part));
			esnUtil.setCurrentESN(currentEsn);
			esnUtil.setCurrentBrand(brand);
			String payload = "<INPUT TASK=\"ValidateEsnMinUpgradeChangeMin\"><ESN>"
					+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN><VALIDATION_TYPE>1</VALIDATION_TYPE><SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ esnUtil.getCurrentBrand() + "</BRAND_NAME></INPUT>";

			cboUtil.callCboMethodWithRequest(payload);
		} else if (status.equalsIgnoreCase("active")) {
			String payload = "<INPUT TASK=\"ValidateEsnMinUpgradeChangeMin\"><ESN>"
					+ esnUtil.getCurrentESN().getEsn()
					+ "</ESN><VALIDATION_TYPE>2</VALIDATION_TYPE><SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><BRAND_NAME>"
					+ brand + "</BRAND_NAME></INPUT>";
			cboUtil.callCboMethodWithRequest(payload);
		}

	}
	
	public void generateESNForAndSIMForBrandZip(String EsnPart, String SimPart, String Brand, String zip) throws Exception {
		ESN esn = null;
		String sim = null;
		
		if (EsnPart.matches("PH(SM|ST|TC|NT|TF).*")) {
			esn = new ESN(EsnPart, phoneUtil.getNewByopEsn(EsnPart,SimPart));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else if ("byop".equalsIgnoreCase(EsnPart)) {
			esn = new ESN(EsnPart, phoneUtil.getNewByopCDMAEsn());
		} else {
			esn = new ESN(EsnPart,phoneUtil.getNewEsnByPartNumber(EsnPart));
		}

		if ((!SimPart.isEmpty()) && (!EsnPart.matches("PH(SM|ST|TC|NT|TF).*"))) {
			sim = simUtil.getNewSimCardByPartNumber(SimPart);
			if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
				sim = simUtil.getNewSimCardByPartNumber(SimPart);
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			}
			phoneUtil.addSimToEsn(sim, esn);
		}
		
		esnUtil.setCurrentESN(esn);
		esnUtil.setCurrentBrand(Brand);
		esnUtil.getCurrentESN().setZipCode(zip);
	}
	
	public void createAStagingTicket() throws Exception {
		
		String min=TwistUtils.generateRandomMin();
		esnUtil.getCurrentESN().setMin(min);
		String payload="<INPUT TASK=\"createTicket\"><ESN>" 
				+ esnUtil.getCurrentESN().getEsn() 
				+ "</ESN><ATTRIBUTE_CURRENT_MIN>" 
				+ min 
				+ "</ATTRIBUTE_CURRENT_MIN><ATTRIBUTE_CUSTOMER_FIRST_NAME>" + "Test123" +  "</ATTRIBUTE_CUSTOMER_FIRST_NAME>"
				+ "<TYPE>TRANSACTION</TYPE><SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><TITLE>EXTERNAL</TITLE></INPUT>";
		
		StringBuffer sbf = cboUtil.callCboMethodWithRequest(payload);	
		String output = cboUtil.parseXml(sbf.toString(), "/createTicket/ERROR_STRING").get(0);
		assertEquals("createTicket CBO call failed with error : ", "Success", output);		
	}

	public void checkPortCoverage() throws Exception {
	
		String payload="<INPUT TASK=\"checkPortCoverage\"><ZIPCODE>" + esnUtil.getCurrentESN().getZipCode() + "</ZIPCODE>"
				+ "<SOURCE_SYSTEM>IVR</SOURCE_SYSTEM><SIM>" + esnUtil.getCurrentESN().getSim() + "</SIM>"
				+ "<BRAND_NAME>" + esnUtil.getCurrentBrand() + "</BRAND_NAME><ESN>" 
				+ esnUtil.getCurrentESN().getEsn() + "</ESN></INPUT>";
		
		StringBuffer sbf = cboUtil.callCboMethodWithRequest(payload);
		String output = cboUtil.parseXml(sbf.toString(), "/checkPortCoverage/ERROR_STRING").get(0);
		assertEquals("checkPortCoverage CBO call failed with error : ", "SUCCESS", output);
		
	}

	public void getPortServiceConfigurationForTypeAndCarrier(String type, String carrier) throws Exception {
		
		String payload="<INPUT TASK=\"getPortServiceConfig\"><PHONE_TYPE>" + type + "</PHONE_TYPE>"
				+ "<CURRENT_PROVIDER>" + carrier + "</CURRENT_PROVIDER></INPUT>";
		
		StringBuffer sbf = cboUtil.callCboMethodWithRequest(payload);		
		
		String output = cboUtil.parseXml(sbf.toString(), "/getPortServiceConfig/ERROR_STRING").get(0);
		assertEquals("getPortServiceConfig CBO call failed with error : ", "Success", output);
	}

	public void processExternalPort() throws Exception {
		
		String esn = esnUtil.getCurrentESN().getEsn();
		String tech = phoneUtil.getEsnDetails(esn, "PC_PARAMS TECHNOLOGY");
		String objid = phoneUtil.getEsnDetails(esn, "CONTACT_OBJID");
		String lastname = phoneUtil.getContactDetails(objid);
		String name = lastname + " " + lastname;
		
		String payload="<INPUT TASK=\"processExternalPort\"><CONTACT_OBJECT_ID>" + objid + "</CONTACT_OBJECT_ID>"
				+ "<PHONE_TECHNOLOGY>" + tech + "</PHONE_TECHNOLOGY><AGENT_LOGIN>CBO</AGENT_LOGIN>"
				+ "<BRAND_NAME>" + esnUtil.getCurrentBrand() +"</BRAND_NAME>"
				+ "<ACTIVATION_ZIP_CODE>" + esnUtil.getCurrentESN().getZipCode() +"</ACTIVATION_ZIP_CODE>"
				+ "<ACCOUNT>1256789</ACCOUNT><CURR_ADDR_HOUSE_NUMBER></CURR_ADDR_HOUSE_NUMBER>" 
				+ "<CURR_ADDR_DIRECTION></CURR_ADDR_DIRECTION><SOURCE_SYSTEM>IVR</SOURCE_SYSTEM>" 
				+ "<RESULT>Pending</RESULT><PIN_CARDS></PIN_CARDS>"
				+ "<ESN>" + esn + "</ESN><SIM_ID></SIM_ID><PIN>1939</PIN>"
				+ "<ASSIGNED_CARRIER>VERIZON</ASSIGNED_CARRIER>"
				+ "<ASSIGNED_CARRIER_ID>104152</ASSIGNED_CARRIER_ID>"
				+ "<ASSIGNED_CARRIER_OBJID>268447737</ASSIGNED_CARRIER_OBJID>"
				+ "<CURR_PROVIDER_PHONE_TYPE>WIRELESS</CURR_PROVIDER_PHONE_TYPE>"
				+ "<ZIP_CODE>" + esnUtil.getCurrentESN().getZipCode() + "</ZIP_CODE><NAME>" + name + "</NAME>"
				+ "<REPL_SIM_ID></REPL_SIM_ID><REASON>Activation</REASON>"
				+ "<CUSTOMER_OBJID></CUSTOMER_OBJID><ACTION_TYPE>1</ACTION_TYPE>"
				+ "<ASSIGNED_CARRIER_PARENT_ID>5</ASSIGNED_CARRIER_PARENT_ID>"
				+ "<ADDRESS_1>9700 NW 112th Ave</ADDRESS_1><ADDRESS_2></ADDRESS_2>"
				+ "<TOTAL_UNITS>0</TOTAL_UNITS><CURR_ADDR_STREET_NAME></CURR_ADDR_STREET_NAME>"
				+ "<CURRENT_MIN>" + esnUtil.getCurrentESN().getMin() + "</CURRENT_MIN><LAST_NAME>" + lastname + "</LAST_NAME>"
				+ "<IS_PORT_IN>true</IS_PORT_In></INPUT>";
		
		StringBuffer sbf = cboUtil.callCboMethodWithRequest(payload);		
		
		String output = cboUtil.parseXml(sbf.toString(), "/processExternalPort/ERROR_STRING").get(0);
		assertEquals("processExternalPort CBO call failed with error : ", "Success", output);
		
		String ticketNbr = cboUtil.parseXml(sbf.toString(), "/processExternalPort/TICKET_NO").get(0);
		completePort(ticketNbr);
		
	}

	public void completePort(String ticketNbr) throws Exception {
		boolean a = phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getMin(), ticketNbr);
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		pressButton("Search Service");
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Complete Ports");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1/",ticketNbr);
		pressButton("Complete Port");
		Assert.assertTrue(myAccountFlow.divVisible("Transaction completed successfully."));
	}

	public void getBalanceAndUpdateBIRecord() throws Exception {
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Balance Inquiry");
		Browser popup2 = myAccountFlow.getBrowser().popup("/.*/");
		if (popup2.button("Get Balance").isVisible()) {
			popup2.button("Get Balance").click();
		} else if (popup2.submit("Get Balance").isVisible()){
			popup2.submit("Get Balance").click();
		}
		//pressButton("Get Balance");
		TwistUtils.setDelay(60);
		phoneUtil.updateBIRecord(esnUtil.getCurrentESN().getEsn());
		pressButton("Refresh");
	}

	public void verifyBalanceResults() throws Exception {
		myAccountFlow.cellVisible("See the balance for the given Transaction Id or click Refresh again after a few seconds if the balance doesn't show.");
	}
	
	public void createFlashWithType(String flashType) throws Exception {
		if(flashType.equalsIgnoreCase("Hot")){
			flashType = "1";
		}else if(flashType.equalsIgnoreCase("Cold")){
			flashType = "0";
		}else{
			return;
		}
		phoneUtil.insertFlash(esnUtil.getCurrentESN().getEsn(), flashType);
	}

	public void enrollIntoEmployeeDiscount() throws Exception {
		
			phoneUtil.enrollintoAffiliatedPartnerDiscountProgram(esnUtil.getCurrentESN().getEmail(),"RENT-A-CENTER");
	
	}

}