package com.tracfone.twist.myAccount;

// JUnit Assert framework can be used for verification

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.tracfone.twist.addAirtime.Redemption;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static Logger logger = LogManager.getLogger(CreateAccount.class.getName());
	
	public CreateAccount() {

	}
	
	public void createAccountDependingOnStatusFor(String status, String brand) throws Exception {
		if ("NEW".equalsIgnoreCase(status)) {
			createAccountFor(brand);
		}
	}


	public void createAccountFor(String brand) throws Exception {
		String email = "itq_tas_"+TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
        logger.info("Email:" + email);
		esnUtil.getCurrentESN().setEmail(email);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it17/", email);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
		myAccountFlow.typeInTextField("r2:0:s12:it22", "1234");
		if (esnUtil.getCurrentESN().getPartNumber().contains("API4")) {
			String hexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", hexEsn);
		} else if("byop".equalsIgnoreCase(esnUtil.getCurrentESN().getPartNumber())){
			
		} else {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		}
		if (myAccountFlow.linkVisible("Expand Expanded")) {
			myAccountFlow.clickLink("Expand Expanded");
		}
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		pressButton("Create Contact");
	
		checkForTransferButton();
		
		if(brand.equalsIgnoreCase("simple_mobile")){
		//	phoneUtil.enrollintoAffiliatedPartnerDiscountProgram(email, "TRACFONE");
		}
	
	}

	private void checkForTransferButton() {
		if (getButtonType("Continue to Service Profile")) {
			pressButton("Continue to Service Profile");
		}
	}
	
	public boolean getButtonType(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName) || myAccountFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}
	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}
	
	public void clickOnFamilyPlan() throws Exception {
		myAccountFlow.clickCellMessage("Transactions");
		myAccountFlow.clickLink("Family Plans");
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

	public void createAccountForWithNoEmail(String brand) throws Exception {
		
		esnUtil.setCurrentBrand(brand);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:sbc3::content/");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
		if (esnUtil.getCurrentESN().getPartNumber().contains("API4")) {
			String hexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", hexEsn);
		} else if("byop".equalsIgnoreCase(esnUtil.getCurrentESN().getPartNumber())){
			
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		}
		
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}

	public void testingPhone() throws Exception {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("C:\\Users\\mkankanala\\Desktop\\filetest.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				logger.info(sCurrentLine);
				/*HttpURLConnection  httpUrlConn = (HttpURLConnection) new URL(sCurrentLine).openConnection(); 
				httpUrlConn.setRequestMethod("HEAD");
				  httpUrlConn.setConnectTimeout(30000);
		            httpUrlConn.setReadTimeout(30000);
		            logger.info("Response Code: "
		                    + httpUrlConn.getResponseCode());
		            logger.info("Response Message: "
		                    + httpUrlConn.getResponseMessage());
		            myAccountFlow.navigateTo(sCurrentLine);
		            */
		            String s = "http://tracfone.deviceanywhere.com/tracfone/home.seam?custId=TFZEZ793C&crd=webcsr:webcsr&locale=en_US";

		            s = s.substring(s.indexOf("=") + 1);
		            s = s.substring(2, s.indexOf("&"));

		            logger.info(s);
		       
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void createAccountForLRP(String brand) throws Exception {
		String email = TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
		logger.info("Email:" + email);
		esnUtil.getCurrentESN().setEmail(email);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		myAccountFlow.selectCheckBox("r2:0:s12:sbc3");
		myAccountFlow.typeInTextField("r2:0:s12:it22", "1234");
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it15/", TwistUtils.generateRandomMin());
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it17/", email);
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it18/", "05/15/1951");
		if (esnUtil.getCurrentESN().getPartNumber().contains("API4")) {
			String hexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", hexEsn);
		} else if("byop".equalsIgnoreCase(esnUtil.getCurrentESN().getPartNumber())){
			
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		}
		if(myAccountFlow.linkVisible("Expand Expanded")){
			myAccountFlow.clickLink("Expand Expanded");
			}
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it8/", "TwistFirstName");
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it10/", "TwistLastName");
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it11/", "1295 Charleston Road");
	/*	myAccountFlow.typeInTextField("r2:0:it13", "Mountain View");
		myAccountFlow.chooseFromSelect("r2:0:soc1", "USA");
		myAccountFlow.chooseFromSelect("r2:0:soc2", "CA");
		myAccountFlow.typeInTextField("r2:0:it22", "12345");
		myAccountFlow.chooseFromSelect("r2:0:soc4", "What is your mother's maiden name");
		myAccountFlow.typeInTextField("r2:0:it23", "Robert");*/
		
		if(brand.equalsIgnoreCase("total_wireless")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		}
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}

	public void lRPCreateAccountFor(String brand) throws Exception {
		String email = TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
		logger.info("Email:" + email);
		esnUtil.getCurrentESN().setEmail(email);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		//myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it15/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it17/", email);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
		if (esnUtil.getCurrentESN().getPartNumber().contains("API4")) {
			String hexEsn = phoneUtil.convertMeidDecToHex(esnUtil.getCurrentESN());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", hexEsn);
		} else if("byop".equalsIgnoreCase(esnUtil.getCurrentESN().getPartNumber())){
			
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		}
		if(myAccountFlow.linkVisible("Expand Expanded")){
			myAccountFlow.clickLink("Expand Expanded");
			}
	
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}

	public void completeTASLRPEnrollment() throws Exception {
		//myAccountFlow.pressButton("Contact Profile "); 
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
		
		clickButton("Contact Details");		
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/", "What is your mother's maiden name");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it19/", "Robert");
			clickButton("Update");
		}else {
			clickButton("Switch to Main Account Contact");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/", "What is your mother's maiden name");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it19/", "Robert");
			clickButton("Update");		
		}
		
		if(myAccountFlow.linkVisible("Logout")){
			myAccountFlow.clickLink("Logout");
			//myAccountFlow.navigateTo("TAS.HomeUrl");
		}	
		if(myAccountFlow.linkVisible("here")){
		//	myAccountFlow.clickLink("here");
		}
		else{
			myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		}
		myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", props.getString("TAS.UserName"));
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/",props.getString("TAS.Password"));
		if (myAccountFlow.submitButtonVisible(props.getString("TAS.Login"))) {
			myAccountFlow.pressSubmitButton(props.getString("TAS.Login"));
		} else {
			myAccountFlow.pressButton(props.getString("TAS.Login"));
		}
		
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", esnUtil.getCurrentESN().getEsn());
		clickButton("Search Service");
		
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
		
		myAccountFlow.clickLink("Loyalty Rewards");
		
		//Identity Challenge 
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1/");
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1/");
		//Continue Button
		clickButton("Continue");
		
		//Terms and Conditions
		if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/")){
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/");
		}
		//Enroll button
		
		clickButton("Enroll");
		
	
		//clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r2:2:r1:2:cb1");
		Assert.assertTrue(myAccountFlow.divVisible("This account is currently enrolled in the Loyalty Rewards Program.")); 
		//Check for 500 points awarded for LRP Enrollment
		String points= phoneUtil.getLRPPointsbyEmailforTranstype("PROGRAM_ENROLLMENT",esnUtil.getCurrentESN().getEmail());
		Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").text().contains(points));

		
	}
	
	public void updateContactDetail() throws Exception {
		 clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
			
			clickButton("Contact Details");		
			if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/", "What is your mother's maiden name");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it19/", "Robert");
				clickButton("Update");
			}else {
				clickButton("Switch to Main Account Contact");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xSecretQuestnId/", "What is your mother's maiden name");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it19/", "Robert");
				clickButton("Update");		
			}
		}
	
	public void returnToMainScreenFromLRP() throws Exception {
		clickButton("Same Customer");
		clickButton("Search Service");
		//myAccountFlow.clickLink("r2:0:t2:0:ot15");
	}
	
	public void returnToMainScreenForNewESNFromLRP() throws Exception {
		clickButton("New Customer");
		myAccountFlow.typeInTextField("r2:0:s12:it1", esnUtil.getCurrentESN().getEsn());
		clickButton("Search Service");
	}
	
	public void clickButton(String buttonName){
		if (myAccountFlow.submitButtonVisible(buttonName)) {
			myAccountFlow.pressSubmitButton(buttonName);
		} else {
			myAccountFlow.pressButton(buttonName);
		}
	}

	public void deenrollFromLRP() throws Exception {
		clickButton("De-Enroll");
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb2/");
		TwistUtils.setDelay(10);
		Assert.assertTrue(myAccountFlow.divVisible("This account is not currently enrolled in the Loyalty Rewards Program. Upon customer request, please select the option below to enroll."));
	}

	public void pointsWithReason(String type, Integer points, String reason)
			throws Exception {
		
		String device=null;
		int pointsEarned=0;
		if(esnUtil.getCurrentESN().getPartNumber().contains("BYOP"))
			device = "BYOP: " + esnUtil.getCurrentESN().getMin() + " (MIN)";
		else
			device = "SMARTPHONE: " + esnUtil.getCurrentESN().getMin() + " (MIN)";
		if(type.equals("Add")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
			myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "ADD LRP POINTS");
			pointsEarned = Integer.parseInt(phoneUtil.getLRPPointsForTransType("PROGRAM_ENROLLMENT")) + points;
		}
		else{
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "DEDUCT LRP POINTS");
			pointsEarned = Integer.parseInt(phoneUtil.getLRPPointsForTransType("PROGRAM_ENROLLMENT"))  - points;
		}
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", points.toString());
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", reason);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:devices/", "/.*?"+esnUtil.getCurrentESN().getEsn()+".*?/i");
		
		clickButton("Process");
		TwistUtils.setDelay(10);
		String dbTotalPoints = phoneUtil.getTotalLRPPointsbyEmail(esnUtil.getCurrentESN().getEmail());
		
		if(points == 0)
			Assert.assertTrue(myAccountFlow.cellVisible("/Max number of points to be issue by an agent must be greater than zero.*/"));
		else if(points > 6000)
			Assert.assertTrue(myAccountFlow.cellVisible("/Max number of points to be issue by an agent in a single transaction is 6000.*/"));
		else{
			Assert.assertTrue(myAccountFlow.cellVisible("/SUCCESS.*/"));
			Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl2/").text().contains(String.valueOf(pointsEarned))); //r2:1:r1:2:plam8
			Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl2/").text().contains(dbTotalPoints));		
		} 
			
			
	}
	
	public void createAccountForRealHandset(String brand) throws Exception {
		String email = TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
		logger.info("Email:" + email);
		esnUtil.getCurrentESN().setEmail(email);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		//myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it15/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it17/", email);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
	/*	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it8/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it10/", "TwistLastName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it11/", "1295 Charleston Road");*/
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it24/", esnUtil.getCurrentESN().getEsn());
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}
	
	public void completeTASLRPEnrollmentAfterAutoRefill() throws Exception {
		
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
		myAccountFlow.clickLink("Loyalty Rewards");
		//Terms and Conditions

		if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/")){
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/");
		}
		//Enroll button
		clickButton("Enroll");
		//clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r2:2:r1:2:cb1");
		Assert.assertTrue(myAccountFlow.divVisible("This account is currently enrolled in the Loyalty Rewards Program.")); 
		//Check for 500 points awarded for LRP Enrollment
		TwistUtils.setDelay(10);
		String points= phoneUtil.getTotalLRPPointsbyEmail(esnUtil.getCurrentESN().getEmail());
		Assert.assertTrue(myAccountFlow.getBrowser().div("r2:2:r1:2:pgl3").text().contains(points));			
	
	}

	public void completeTASLRPEnrollmentIfNecessaryForType(String refEsnType)
			throws Exception { 
		if(refEsnType.equalsIgnoreCase("Enrolled")){
			clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
			myAccountFlow.clickLink("Loyalty Rewards");
				
			//Terms and Conditions
			if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/")){
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1/");
			}
		
			//Enroll button
			clickButton("Enroll");
				
			//clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r2:2:r1:2:cb1");
			Assert.assertTrue(myAccountFlow.divVisible("This account is currently enrolled in the Loyalty Rewards Program.")); 
				
			//Check for points awarded for LRP Enrollment
			String points= phoneUtil.getLRPPointsbyEmailforTranstype("PROGRAM_ENROLLMENT",esnUtil.getCurrentESN().getEmail());
			Assert.assertTrue(myAccountFlow.getBrowser().div("r2:2:r1:2:pgl3").text().contains(points));
			
			//Return to main screen
			clickButton("New Customer");
			myAccountFlow.typeInTextField("r2:0:s12:it1", esnUtil.getCurrentESN().getEsn());
			clickButton("Search Service");
		}
	}

	public void completeTASLRPEnrollment(String enrollStatus) throws Exception {
		if("YES".equalsIgnoreCase(enrollStatus)){
			//myAccountFlow.pressButton("Contact Profile "); 
			clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
			myAccountFlow.clickLink("Loyalty Rewards");
			
//			Identity Challenge
//			myAccountFlow.selectCheckBox("r2:2:r1:0:t1:0:sbc1::content");
//			myAccountFlow.selectCheckBox("r2:2:r1:0:t1:1:sbc1::content");
//			//Continue Button
//			myAccountFlow.pressButton("r2:2:r1:0:cb1");
			
			//Terms and Conditions
			if(myAccountFlow.checkboxVisible("r2:2:r1:1:sbc1")){
			myAccountFlow.selectCheckBox("r2:2:r1:1:sbc1");
			}
			//Enroll button
			
			clickButton("Enroll");
			//clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r2:2:r1:2:cb1");
			Assert.assertTrue(myAccountFlow.divVisible("This account is currently enrolled in the Loyalty Rewards Program.")); 
			//Check for 500 points awarded for LRP Enrollment
			String points= phoneUtil.getLRPPointsbyEmailforTranstype("PROGRAM_ENROLLMENT",esnUtil.getCurrentESN().getEmail());
			Assert.assertTrue(myAccountFlow.getBrowser().div("r2:2:r1:2:pgl3").text().contains(points));
		}
	
	}

	public void clickOnNewCustomer() throws Exception {
	
	}

	public void addCurrentEsnToAccount(String brand) throws Exception {
		if (brand.equalsIgnoreCase("total_wireless") || brand.equalsIgnoreCase("simple_mobile")) {
			clickButton("Add Group Member");
			myAccountFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it2::content/", esnUtil.getCurrentESN().getEsn());
			myAccountFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it3::content/", "ITQ_ADD_MEMBER_TEST");
			clickButton("Add");
			myAccountFlow.clickLink(esnUtil.getCurrentESN().getEsn());
		} else {
			clickButton("Add ESN to Account");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18::content/", esnUtil.getCurrentESN().getEsn());
			clickButton("Add");
			//Assert.assertTrue("ESN not added to account", myAccountFlow.cellVisible("ESN added to account, Successfully"));
			myAccountFlow.clickLink(esnUtil.getCurrentESN().getEsn());
		}
	}

	public void createAccountForWithNoEmailAndEsn(String brand) throws Exception {
		String email = TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
		logger.info("Email:" + email);
		esnUtil.getCurrentESN().setEmail(email);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", "33178");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it17/", email);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it18/", "05/15/1951");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		if (myAccountFlow.submitButtonVisible("Create Contact")) {
			myAccountFlow.pressSubmitButton("Create Contact");
		} else {
			myAccountFlow.pressButton("Create Contact");
		}
	}

	public void brandEsnToForStatusZip(String toBrand, String status,String zip) throws Exception {
		clickButton("Refresh");
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Universal Branding");
		if (myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1/")) {
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1/");			
		}
		if (myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1/")) {
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1/");
		}
		if (myAccountFlow.submitButtonVisible("Continue") || myAccountFlow.buttonVisible("Continue")) {
			clickButton("Continue");
		}
		if (status.equalsIgnoreCase("Active")) {
			clickButton("Deactivate");
		} else if (status.equalsIgnoreCase("Past Due")) {
			clickButton("Continue");
		}
		if (toBrand.equalsIgnoreCase("TOTAL_WIRELESS")) {
			toBrand = "Total Wireless";
		} else if (toBrand.equalsIgnoreCase("NET10")) {
			toBrand = "Net10";
		} else if (toBrand.equalsIgnoreCase("TRACFONE")) {
			toBrand = "Tracfone";
		} else if (toBrand.equalsIgnoreCase("STRAIGHT_TALK")) {
			toBrand = "Straight Talk";
		}
		
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", toBrand);
		if("new".equalsIgnoreCase(status)){
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", zip);
		}
		clickButton("Brand Serial Number");
		Assert.assertTrue("UNIVERSAL BRANDING FAILED", myAccountFlow.cellVisible("ESN Branded"));
 	}

	public void redeemLRPPointsForPlan(Integer Plan) throws Exception {
		Integer Addpoints = 0;
		Addpoints = Plan * 100;
		pointsWithReason("Add",Addpoints,"Other");
		clickButton("Redeem Points");
		//myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:devices/","SMARTPHONE: 9549351020 (MIN)");
		String points = Integer.toString(Addpoints);
		String DeviceType= phoneUtil.getDevicetype(esnUtil.getCurrentESN().getEsn());
		String Device=DeviceType + ": " + esnUtil.getCurrentESN().getMin()+ " (MIN)";
		System.out.println(Device);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:devices::content/","/.*?"+esnUtil.getCurrentESN().getEsn()+".*?/i");
		TwistUtils.setDelay(5);
		myAccountFlow.clickSpanMessage(points);
	}

	public void addPlanNow(String AddNow, String Email) throws Exception {
		if (AddNow.equalsIgnoreCase("Yes")) {
			clickButton("Add Now");
		} else {
			clickButton("Add to Reserve");// r2:2:r1:4:pb1::content
		}
		TwistUtils.setDelay(30);
		Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pb1::content/").text().contains("REWARDS POINTS SUMMARY")); //REWARDS POINTS SUMMARY Redeemed//REWARDS POINTS SUMMARYRedeemed
		Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pb1::content/").text().contains("Send Email"));
		Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pb1::content/").text().contains("Update Email"));
		if(Email.equalsIgnoreCase("Send Email")){
			clickButton("Send Email");
		    Assert.assertTrue(myAccountFlow.cellVisible("EMAIL HAS BEEN SENT"));
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/",TwistUtils.createRandomEmail());
			clickButton("Update Email");
			 Assert.assertTrue(myAccountFlow.cellVisible("CUSTOMER EMAIL HAS BEEN UPDATED"));
		}
		String dbpoints= phoneUtil.getLRPPointsbyEmailforTranstype("CHARGE",esnUtil.getCurrentESN().getEmail());
		System.out.println("CHARGE" + dbpoints);
		Integer points= Integer.parseInt(dbpoints) * (-1);
		System.out.println(points);
		Assert.assertTrue(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pb1::content/").text().contains(Integer.toString(points)));
	}

	
}