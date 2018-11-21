package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.Activation.ActivatePhone;
import com.tracfone.twist.cbo.InvokeCBO;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Operations {

	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private MyAccountFlow myAccountFlow;
	private ServiceUtil serviceUtil;
	private InvokeCBO invokeCBO;
	private String named_userId;
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	ESN fromESN;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public Operations() {
		
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setInvokeCBO(InvokeCBO invokeCBO) {
		this.invokeCBO = invokeCBO;
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}

	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}
	
	public void generateEsnForPartSimAndCellTech(String partNumber, String simPart, String cellTech) throws Exception {
		myAccountFlow.getBrowser().close();
//		esnUtil.setDeviceType(partNumber);
		ESN currentEsn;
		fromESN = esnUtil.getCurrentESN();
		if (fromESN != null) {
			String min = phoneUtil.getMinOfActiveEsn(fromESN.getEsn());
			fromESN.setMin(min);
		}
		if (partNumber.matches("SIMOUT.*.LEASED")) {
			System.out.println("SIMOUT_LEASED");
			currentEsn = new ESN(partNumber, TwistUtils.generate15DigitImei());
			esnUtil.setCurrentESN(currentEsn);
			String sim = simUtil.getNewSimCardByPartNumber(simPart);
			esnUtil.getCurrentESN().setSim(sim);
			currentEsn.setDeviceType("SIMOUT_LEASED");

		} else if (partNumber.matches("SIMOUT.*")) {
			System.out.println("SIMOUT");
			currentEsn = new ESN(partNumber, TwistUtils.generate15DigitImei());
			esnUtil.setCurrentESN(currentEsn);
			String sim = simUtil.getNewSimCardByPartNumber(simPart);
			esnUtil.getCurrentESN().setSim(sim);
			currentEsn.setDeviceType("SIMOUT");

		} else if (partNumber.matches("BYO.*")) {
			System.out.println("BYOP/T");
			currentEsn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
			esnUtil.setCurrentESN(currentEsn);
			if (!simPart.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simPart);
				esnUtil.getCurrentESN().setSim(sim);
			}
			currentEsn.setDeviceType("BYOP");

		} else if (partNumber.matches(".*.LEASED")) {
			System.out.println("LEASED_NORMAL");
			String[] partNumberSplit = partNumber.split("_");
			System.out.println(partNumberSplit);
			String phonePartNumber_leased = partNumberSplit[0];
			String techType = partNumberSplit[1];
			currentEsn = new ESN(phonePartNumber_leased, phoneUtil.getNewEsnByPartNumber(phonePartNumber_leased));
			esnUtil.setCurrentESN(currentEsn);
			if (!simPart.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simPart);
				esnUtil.getCurrentESN().setSim(sim);
			}
			currentEsn.setDeviceType("LEASED");
		} else {
			if (partNumber.startsWith("PH")) {
				currentEsn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, simPart));
				esnUtil.setCurrentESN(currentEsn);
				esnUtil.getCurrentESN().setSim(phoneUtil.getSimFromEsn(currentEsn.getEsn()));

			} else {
				currentEsn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				phoneUtil.convertMeidDecToHex(currentEsn);
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					phoneUtil.addSimToEsn(sim, currentEsn);
				}

			}
			currentEsn.setDeviceType("BRANDED");
		}
		currentEsn.setFromEsn(fromESN);
		esnUtil.setCurrentESN(currentEsn);
	}
	
	public synchronized void generateEsnForPartSimAndPin(String partNumber, String simPart, String pinPart){
		ESN currentESN = null;
		
		currentESN = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		currentESN.setDeviceType(partNumber);
		esnUtil.setCurrentESN(currentESN);
		if (!simPart.isEmpty()) {
			String sim = simUtil.getNewSimCardByPartNumber(simPart);
			phoneUtil.addSimToEsn(sim, currentESN);
		}
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		currentESN.setPin(pin);
	}
	
	public synchronized void createAccountForUsingService(String brandName) throws Exception {
		esnUtil.setCurrentBrand(brandName);
		String access_token = serviceUtil.genAuthfor(esnUtil.getSourceSystem(), "customer management");
		serviceUtil.createAccount(access_token, brandName);
	}
	
	
	public synchronized void callGetAccountSummary(String brand) throws Exception {
		//partyID = WALMART
		TwistUtils.setDelay(60);
		String partyId;
		if(esnUtil.getSourceSystem().equalsIgnoreCase("API")){
			partyId="TCETRA";
		}else{
			partyId= "WALMART";
		}
		serviceUtil.callGetAccountSummary(esnUtil.getCurrentESN().getEsn(),brand,partyId);
	}

	public synchronized void validateCustomerOrderWithPinZipForFlow(String pinPart , String zipCode,String flowName) throws Exception {
		//Get Group for last param in case of TW  for existing group and reactivation
		String partyId;
		if(esnUtil.getSourceSystem().equalsIgnoreCase("API")){
			partyId="TCETRA";
		}else{
			partyId= "WALMART";
		}
		serviceUtil.validateCustomerOrder(pinPart,zipCode,flowName,partyId,"validate","");
	}

	public synchronized void submitCustomerOrderFor(String flowName) throws Exception {
		serviceUtil.submitCustomerOrderFor(flowName,"");
	}

	public void checkByopCoverageForIn(String carrier, String zipCode)
			throws Exception {
		System.out.println(TwistUtils.generate15DigitImei());
		serviceUtil.CheckByopCoverage(carrier,zipCode);
	}

	public void checkByopEligibilty() throws Exception {
		ESN currByopEsn = new ESN("BYOP", phoneUtil.getNewByopCDMAEsn());

	//	String hexEsn=phoneUtil.convertMeidDecToHex(currByopEsn);
	//	currByopEsn = new ESN("BYOP", hexEsn);
		esnUtil.setCurrentESN(currByopEsn);
		esnUtil.getCurrentESN().putInMap("deviceType", "BYOP");
		serviceUtil.CheckByopCoverage("VZW","33178","TOTAL_WIRELESS",esnUtil.getCurrentESN().getEsn());
		TwistUtils.setDelay(5);
		phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(), "RSS", "ACTIVE","NO","NO", "NO");
		serviceUtil.CheckByopCoverage("VZW","33178","TOTAL_WIRELESS",esnUtil.getCurrentESN().getEsn());
		
	}

	public void selectESNToReactivate() throws Exception {
		/*ESN childEsn = null;
		List<ESN> allEsns = new ArrayList<ESN>();
		List<ESN> familyesns = esnUtil.getCurrentESN().getFamilyEsns();
		allEsns.add(esnUtil.getCurrentESN());
		allEsns.addAll(familyesns);
		TwistUtils.setDelay(40);
		for (ESN currentEsn : allEsns) {
			//esnUtil.setCurrentESN(currentEsn);
			invokecbo.callDeactivatePhoneMethodWithReason("PASTDUE");
		}*/
		invokeCBO.callDeactivatePhoneMethodWithReason("PASTDUE");
	}

	public void goToTASAndSearchForESN() throws Exception {
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		pressButton("Search Service");
	}
	
	public void identitycahllenge(){
		if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/")){
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/");
			if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/")){
				myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/");
			}
			if (myAccountFlow.buttonVisible("Continue")) {
				myAccountFlow.pressButton("Continue");
			} else if (myAccountFlow.submitButtonVisible("Continue")){
				myAccountFlow.pressSubmitButton("Continue");
			}
		}
	}
	
	public void generatePinForActivationWithZip(String pinPart, String zip)
			throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		esnUtil.getCurrentESN().setPin(pin);
		esnUtil.getCurrentESN().setZipCode(zip);
		serviceUtil.activatePhone();
	}
	
	public void genrateAuth() throws Exception {
	serviceUtil.genAuthfor("LRP", "Ext");
	}

	public  synchronized void getEncryptedWebuserObjid() throws Exception {
		String wsdlURL = props.getString("customermanagementserviceurl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/retrieveCustomerDetails.xml");  
			
			String request = FileUtils.readFileToString(file, "UTF-8");
			String TASURL=props.getString("TAS.HomeUrl");
			 if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
			     	request = request.replaceAll("@secusername", props.getString("services.tstusername"));
			     	request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
			     }else{
			     	request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
			     	request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
			     }
			request = request.replaceAll("@partner", "augeo");
			request = request.replaceAll("@webobjid", phoneUtil.getWebobjidByEmail(esnUtil.getCurrentESN().getEmail()));
			
			logger.info(request);
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"RetrieveCustomerDetails");
			logger.info(response);
			Assert.assertEquals("SUCCESS", serviceUtil.parseXml(response, "//com:message").get(0).toUpperCase());
		
			
			named_userId = serviceUtil.parseXml(response, "//com:CustomerAccount/com:ID").get(0);
			put("named_userId", named_userId);
			logger.info(named_userId);
			
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void callCustomerManagementService() throws Exception {
		serviceUtil.getCustomerMgmtDetails(esnUtil.getCurrentESN().getFromMap("named_userId"));		
	}

	public void callReportManagementService() throws Exception {		
		serviceUtil.getReportManagementDetails(esnUtil.getCurrentESN().getFromMap("named_userId"));
	}

	public void callUsageManaementServiceForRewardTypeAndRunTime(String transacton_type,String category,String sub_category,String benefits_earned, int numberofTimes) throws Exception {
		if (numberofTimes > 1) {
			for (int i = 1; i <= numberofTimes; i++) {
				serviceUtil.getUsageManagementDetails(esnUtil.getCurrentESN().getFromMap("named_userId"),transacton_type, category, sub_category, benefits_earned);
			}
		} else {
			serviceUtil.getUsageManagementDetails(esnUtil.getCurrentESN().getFromMap("named_userId"),transacton_type,category, sub_category, benefits_earned);
		}

		TwistUtils.setDelay(10);
		logger.info("Updating maturity date.......");
		phoneUtil.updateMaturirtyDateforAcc(esnUtil.getCurrentESN().getEmail());
		logger.info("Updating maturity date : Completed");

		TwistUtils.setDelay(10);
		logger.info("Running complete reward proc.......");
		//phoneUtil.completePendingRewardBatchTrasanctions();
		logger.info("Running complete reward proc: Completed");
	}

	public void callCatalogManagementService() throws Exception {
		serviceUtil.getCatalogManagementDetails();
	}

	public void callSubmitProductorderServiceForRewardTypeAndRunTime(String category,String sub_category,String benefits_earned, int numberofTimes) throws Exception {
		if (numberofTimes > 1) {
			for (int i = 1; i <= numberofTimes; i++) {
				serviceUtil.submitProductOrder(esnUtil.getCurrentESN().getFromMap("named_userId"), category, sub_category, benefits_earned);
			}
		} else {
			serviceUtil.submitProductOrder(esnUtil.getCurrentESN().getFromMap("named_userId"), category, sub_category, benefits_earned);
		}

		TwistUtils.setDelay(10);
		logger.info("Updating maturity date.......");
		phoneUtil.updateMaturirtyDateforAcc(esnUtil.getCurrentESN().getEmail());
		logger.info("Updating maturity date : Completed");
		TwistUtils.setDelay(10);
		logger.info("Running complete reward proc.......");
		//phoneUtil.completePendingRewardBatchTrasanctions();
		logger.info("Running complete reward proc: Completed");
	}
	
	public String put(String key, String value) {
		return esnUtil.getCurrentESN().putInMap(key, value);
	}
	
	public String get(String key) {
		return esnUtil.getCurrentESN().getFromMap(key);
	}

	public void closeBrowser() throws Exception {
		myAccountFlow.clickLink("Logout");
		myAccountFlow.getBrowser().close();
	}

	public void generateNewModelEsnForSimoutWithSIM(String model,String simPart) throws Exception {
		ESN simoutESn= new ESN("SIMOUT", TwistUtils.generate15DigitImei());
		esnUtil.setCurrentESN(simoutESn);
		String sim = simUtil.getNewSimCardByPartNumber(simPart);
		esnUtil.getCurrentESN().setSim(sim);
		esnUtil.getCurrentESN().setSim(sim);
		esnUtil.getCurrentESN().putInMap("deviceType", "");
		esnUtil.getCurrentESN().putInMap("carrierPreloaded", "true");
		esnUtil.getCurrentESN().putInMap("phoneModel", model);	
	}

	public void setEsnLeaseStatusToWithPin(String status,String pinPart ) throws Exception {
		String wsdlURL = props.getString("PhoneServices");
		
		try {
			File file = new File("../common-utils/ServiceRequestFiles/updatePhoneInformation.xml");  
			String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			
			
			String request = FileUtils.readFileToString(file, "UTF-8");
			String TASURL=props.getString("TAS.HomeUrl");
			if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
				request = request.replaceAll("@secusername", props.getString("services.tstusername"));
				request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
			}else{
				request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
				request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
			}
		//	request = request.replaceAll("@secusername", props.getString("services.username"));
		//	request = request.replaceAll("@secpassword", props.getString("services.password"));
			request = request.replaceAll("@clientname", "smartpaylease");
			request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
			request = request.replaceAll("@sourcesystem", "API");
			request = request.replaceAll("@applicationid", Long.toString(TwistUtils.createRandomLong(1000000, 9999999)));
			request = request.replaceAll("@esn", esnUtil.getCurrentESN().getEsn());
			request = request.replaceAll("@snp",	phoneUtil.getSNPfromRedCode(newPin));
			request = request.replaceAll("@leasestatus", status);
			
			logger.info(request);
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"UpdatePhoneInformation");
			logger.info(response);
			Assert.assertEquals("SUCCESS", serviceUtil.parseXml(response, "//com:message").get(0).toUpperCase());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void checkCCOnFile() throws Exception {
		String wsdlURL = props.getString("PhoneServices");
		
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/hasCConFile.xml");  
			ESN esn = esnUtil.getCurrentESN();
			
			String request = FileUtils.readFileToString(file, "UTF-8");
			String TASURL=props.getString("TAS.HomeUrl");
			if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
				request = request.replaceAll("@secusername", props.getString("services.tstusername"));
				request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
			}else{
				request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
				request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
			}
			request = request.replaceAll("@min",phoneUtil.getMinOfActiveEsn(esn.getEsn()));
			
			logger.info(request);
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"hasCConFile");
			logger.info(response);
			Assert.assertEquals("SUCCESS", serviceUtil.parseXml(response, "//com:message").get(0).toUpperCase());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}
	
	public void addRequiredDetailsIfDeviceRequiresProcessWithModel() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String[] partNumberSplit = esnUtil.getCurrentESN().getPartNumber().split("_");
		System.out.println(partNumberSplit);
		String phonetype =partNumberSplit[0];
		String modelType =partNumberSplit[1];
		
		esn.putInMap("deviceType", "");
		esn.putInMap("carrierPreloaded", "true");
		esn.putInMap("phoneModel", modelType);
		esn.setDeviceType("");
	}
	
	public void addRequiredDetailsIfDeviceRequiresSimoutLeasedProcessWithModel(){
		ESN esn = esnUtil.getCurrentESN();
		String[] partNumberSplit = esn.getPartNumber().split("_");
		System.out.println(partNumberSplit);
		String phonetype =partNumberSplit[0];
		String modelType =partNumberSplit[1];
		String isleased =partNumberSplit[2];
		
		esn.putInMap("deviceType", "");
		esn.putInMap("carrierPreloaded", "true");
		esn.putInMap("phoneModel", modelType);	
		esn.setDeviceType("");		
	}

	public void addRequiredDetilsIfDeviceRequiresBYOPRegistrationWith() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		if (esn.getDeviceType().matches("BYO.*")) {
			
			String[] partNumberSplit = esnUtil.getCurrentESN().getPartNumber().split("_");
			System.out.println(partNumberSplit);
			String phonetype = partNumberSplit[0];
			String techType = partNumberSplit[1];
			String manufType = partNumberSplit[2];
			String fromCarrierType = partNumberSplit[3];
			
			String currentHexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			String lte;
			String iPhone;
			String newEsnVal = esnUtil.getCurrentESN().getEsn();
			String phonePart = esnUtil.getCurrentESN().getPartNumber();
			
			if (phonetype.equalsIgnoreCase("BYOP")) {
				esn.setDeviceType("BYOP");
			} else if (phonetype.equalsIgnoreCase("BYOPHEX")) {
				newEsnVal = currentHexEsn;
				esn.setDeviceType("BYOP");
			} else if (phonetype.equalsIgnoreCase("BYOT")) {
				esn.setDeviceType("BYOT");
			} else if (phonetype.equalsIgnoreCase("BYOHEX")) {
				esn.setDeviceType("BYOT");
				newEsnVal = currentHexEsn;
			}
			
			if (techType.equalsIgnoreCase("LTE")) {
				lte = "YES";
			} else {
				lte = "NO";
			}
			if (manufType.equalsIgnoreCase("APL")) {
				iPhone = "YES";
			} else {
				iPhone = "NO";
			}
			String newCarriername = null;
			if (fromCarrierType.equalsIgnoreCase("VZW")) {
				newCarriername = "RSS";

			} else if (fromCarrierType.equalsIgnoreCase("SPT")) {
				newCarriername = "SPRINT";
			}

			esnUtil.getCurrentESN().putInMap("carrierType", fromCarrierType);
			serviceUtil.CheckByopCoverage(fromCarrierType, "33178", esnUtil.getCurrentBrand(),newEsnVal);
			if(esnUtil.getCurrentESN().getSim().isEmpty()){
				phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(),newCarriername, "INACTIVE", lte, iPhone, "No");	
			}else{
				phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(),newCarriername, "INACTIVE", lte, iPhone, "Yes");	
			}
			
			serviceUtil.CheckByopCoverage(fromCarrierType, "33178", esnUtil.getCurrentBrand(),newEsnVal);

		}
	}

//Pin requires only if we are updating phone info for leased phone
	public void completeRegistrationProcessIfDeviceIsBYOPOrSIMOUTFlow(String pinPart, String flowName) 
			throws Exception {
		// String dType = "BYOP";
		 String dType= esnUtil.getCurrentESN().getDeviceType();
		if (dType.matches("SIMOUT")) {
			addRequiredDetailsIfDeviceRequiresProcessWithModel();
		}

		if (dType.matches("SIMOUT*.*LEASED")) {
			esnUtil.getCurrentESN().setPin(phoneUtil.getNewPinByPartNumber(pinPart));
			addRequiredDetailsIfDeviceRequiresSimoutLeasedProcessWithModel();
			validateCustomerOrderWithPinZipForFlow("", "33178", flowName);
			setEsnLeaseStatusToWithPin("1001", pinPart);
			esnUtil.getCurrentESN().setPin("nopin");
		}

		if (dType.matches("LEASED")) {
			esnUtil.getCurrentESN().setPin(phoneUtil.getNewPinByPartNumber(pinPart));
			setEsnLeaseStatusToWithPin("1001", pinPart);
			esnUtil.getCurrentESN().setDeviceType("BRANDED");
			validateCustomerOrderWithPinZipForFlow("", "33178", flowName);
			esnUtil.getCurrentESN().setPin("nopin");
		}
		if (dType.matches("BYO.*")) {
			addRequiredDetilsIfDeviceRequiresBYOPRegistrationWith();
		}
	}

	public void enrollIntoAutoRefillWith(String cardType) throws Exception {
		serviceUtil.submitCustomerOrderFor("ENROLLMENT", cardType);
	}
	
	public void refillWithPin(String newPin) throws Exception {
		TwistUtils.setDelay(30);
		String pin = phoneUtil.getNewPinByPartNumber(newPin);
		esnUtil.getCurrentESN().setPin(pin);
		serviceUtil.refillPhonewithPin();
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void transferNumberToPartSimPin(String Newphone, String NewSim,String NewPin)
			throws Exception {
		generateEsnForPartSimAndCellTech(Newphone, NewSim, "");
		serviceUtil.transferMin(fromESN.getEsn(), esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentESN().getSim(), fromESN.getSim());
	}
	public void sendIVRServiceRequestForExternalPortInFromTo(String oldBrandName ,String newBrandName) throws Exception {
		
		String esn = esnUtil.getCurrentESN().getEsn();
		String sim = esnUtil.getCurrentESN().getSim();
		String min = TwistUtils.generateRandomMin();
		String email = TwistUtils.createRandomEmail();
		String pin = esnUtil.getCurrentESN().getPin();
	
		File file = new File("../Twist-TAS/servicerequestfiles/IVR_External_PortIn.xml");
		
		String request = FileUtils.readFileToString(file,"UTF-8");
		request = request.replaceAll("@newBrand",newBrandName);
		request = request.replaceAll("@min",min);
		request = request.replaceAll("@email",email);
		request = request.replaceAll("@oldBrand",oldBrandName);
		request = request.replaceAll("@esn",esn);
		request = request.replaceAll("@sim",sim);
		request = request.replaceAll("@pin",pin);
		System.out.println("Request : "+request);
		
		serviceUtil.callSOAServicewithRequestandEndpoint(request,"http://sit1esbgateway/B2B/PortabilityServices","IVR EXTERNAL PORTIN");
	}
	
	public void updateLatestIGTransaction() throws Exception {
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void setSourcesystemAs(String sourceSystem) throws Exception {
		esnUtil.setSourceSystem(sourceSystem);
	}

	public void addPaymentsource() throws Exception {
		serviceUtil.addPaymentSourceforWARP();
	}

}