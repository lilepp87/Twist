package com.tracfone.twist.solutions;

// JUnit Assert framework can be used for verification

import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.*;
import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class Solutions {

	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	public String airbillShipType;
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static Logger logger = LogManager.getLogger(Solutions.class.getName());
	public Solutions() {
		
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void goToSolutions() throws Exception {
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Solutions");
	}
	
	public void  goToUnlockPhoneEligibiltyPage()throws Exception {
		myAccountFlow.clickLink("Loss Prevention");
		myAccountFlow.clickLink("Unlock Phone Eligibility");
	}
	
	public void clickOnPromoValidationTool() throws Exception {
		myAccountFlow.clickCellMessage("Test Data");
		myAccountFlow.clickLink("Solution: 1264");
	  myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t1/").hover();
	  myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t1/").focus();
	  myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t1/").click();
	  for(int i=0 ; i<=15;i++){
		  myAccountFlow.getBrowser().keyDown(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t1/"), 40, 8595);
	  }
	  myAccountFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:26:ot20/").click();
	  myAccountFlow.clickLink("Promo Pin Tool"); 
	}
	
	public void enterPromoCodeAndCheckForConfirmation(String promoPin)
			throws Exception {
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/", esnUtil.getCurrentESN().getPin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/", promoPin);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1/", "WEB");
		pressButton("Submit");
		Assert.assertTrue(myAccountFlow.cellVisible("Success"));
		pressButton("Continue to Redemption");
	}
	
	public void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void clickOnUnlockDevice() throws Exception {
		myAccountFlow.clickCellMessage("Unlock Device");
		myAccountFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot20/");
		myAccountFlow.clickLink("Unlock Phone Eligibility");
		myAccountFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sdi\\d::disAcr/");
	}

	public void validateUnlockEligibility() throws Exception {
		pressButton("Verify Eligibility");
		Assert.assertTrue("Eligigbility FAILED",myAccountFlow.cellVisible("SUCCESS (0)"));
	}

	public void fillUpAddressForm() throws Exception {
		String[] airbillType = {"PHYSICAL","ELECTRONIC"};
		Random random = new Random();
		int index = random.nextInt(airbillType.length);
		airbillShipType = airbillType[index];
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/", airbillShipType);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", "TwistFirstname"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5::content/", "TwistLastname"+TwistUtils.generateName(9));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6::content/", "9700 NW 112th ave");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8::content/", "Miami");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2::content/", "FL");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9::content/", "33178");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10::content/", TwistUtils.createRandomEmail());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11::content/", "3055555555");
		pressButton("Create BuyBack Request");
	}
	
	
	public void fillUpAddressFormSl() throws Exception {  //new
		//String[] airbillType = {"PHYSICAL","ELECTRONIC"};
		//int index = random.nextInt(airbillType.length);
		//airbillShipType = airbillType[index];
		//myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/", airbillShipType);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4::content/", "TwistFirstname"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it5::content/", "TwistLastname"+TwistUtils.generateName(9));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it6::content/", "9700 NW 112th ave");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it8::content/", "Miami");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc2::content/", "FL");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it9::content/", "33178");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it10::content/", TwistUtils.createRandomEmail());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11::content/", "3055555555");
		myAccountFlow.chooseFromSelect("/((pt\\d:)| (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/", "YES");
		myAccountFlow.selectCheckBox("/((pt\\d:)| (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		pressButton("Create Unlock Exchange Request");
		//pressButton("Create BuyBack Request");
	}
//new
	public void fillUpAddressFormAndCreateUnlockRequestByOverrideType(String partNumber , String overideType) throws Exception {
		if (partNumber.startsWith("STAPI5SG") || partNumber.startsWith("NTAPI5SG") || partNumber.startsWith("SMZEZ828T") || partNumber.startsWith("TFAPI6")) {
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1\\d::content/", overideType);
			pressButton("Verify Eligibility");

		} else {
			phoneUtil.getInsertEsnIntoUnlockSpc(esnUtil.getCurrentESN().getEsn());
			logger.info("Unlocking codes inserted");
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1::content/", overideType);
			pressButton("Verify Eligibility");
		}
	    
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:it4::content/", "TwistFirstname"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:it5::content/", "TwistLastname"+TwistUtils.generateName(9));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10::content/", "akumarkoirpalli@tracfone.com");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11::content/", "3055555555");
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2::content/");
		pressButton("Create Unlock Phone Request");
	    
	}
	
	public void fillUpAddressFormAndCreateUnlockRequest(String partNumber) throws Exception {
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:it4::content/", "TwistFirstname"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:it5::content/", "TwistLastname"+TwistUtils.generateName(9));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10::content/", TwistUtils.createRandomEmail());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11::content/", "3055555555");
		myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc2::content/");
	    if (partNumber.startsWith("STAPI5SG")||partNumber.startsWith("NTAPI5SG") || partNumber.startsWith("SMZEZ828T")|| partNumber.startsWith("TFAPI6")) {
	    	pressButton("Create Unlock Phone Request");
		}
	   
	    else {
		    phoneUtil.getInsertEsnIntoUnlockSpc(esnUtil.getCurrentESN().getEsn());
		    logger.info("Unlocking codes inserted");
			pressButton("Create Unlock Phone Request");
	    }
	}
	
	public void checkForTicketNumber() throws Exception {
		Assert.assertTrue(myAccountFlow.cellVisible("Id Number"));
	}

	public void applyMilitaryOverride() throws Exception {
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1::content/","Military");
	}

	public void setInstallationDateTo365DaysPastToReach365DaysActiveRequirement()
			throws Exception {
		phoneUtil.setEsnInstallDatetoPast(esnUtil.getCurrentESN().getEsn(), 365);
	}

	public void setPastDueDateBackToDaysOld(Integer days) throws Exception {
		TwistUtils.setDelay(5);
		phoneUtil.setEsnServiceEndDatetoPast(esnUtil.getCurrentESN().getEsn(), days);
	}

	public void validateUnlockEligibilityToCheckFailure() throws Exception {
		pressButton("Verify Eligibility");
		Assert.assertTrue(myAccountFlow.cellVisible("/.*?ERROR: Not active recently.*?/"));
	}

	public void validateUnlockEligibilityForFailureMessage() throws Exception {
		pressButton("Verify Eligibility");
		Assert.assertTrue(myAccountFlow.cellVisible("NOT ELEGIBLE") || myAccountFlow.cellVisible("NOT ELIGIBLE"));
	}

	public void movePastDateBackToDaysOld(Integer days) throws Exception {
		phoneUtil.setEsnServiceEndDatetoPast(esnUtil.getCurrentESN().getEsn(), days);
	}

	public void setInstallationDateToDaysPastToReachActiveDaysRequirement(
			Integer days) throws Exception {
		phoneUtil.setEsnInstallDatetoPast(esnUtil.getCurrentESN().getEsn(), days);
	}
	public void goToFCCWeb() throws Exception {
		if(myAccountFlow.linkVisible("Logout")){
			myAccountFlow.clickLink("Logout");
		}
		myAccountFlow.navigateTo(props.getString("buybackweburl"));
	}

	public void enterEsnOrMinAndCheckEligibilty(String esnOrMin) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();

		if(esnOrMin.equalsIgnoreCase("ESN")){
			myAccountFlow.typeInTextField("serialNumber",esn);		
		}else if(esnOrMin.equalsIgnoreCase("MIN")){
			String min = phoneUtil.getMinOfActiveEsn(esn);
			myAccountFlow.typeInTextField("phoneNumber",min);
		}
		
		myAccountFlow.pressButton("Verify Eligibility");
	}

	public void enterAddressAndCheckTicketNumber() throws Exception {
		String email = esnUtil.getCurrentESN().getEmail();
		myAccountFlow.selectCheckBox("eligible");
		pressButton("Submit");
		myAccountFlow.typeInTextField("firstName","TwistFirstNamer"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("lastName","TwistLastName"+TwistUtils.generateName(6));
		myAccountFlow.typeInTextField("address1","1295 charleston Rd");
		myAccountFlow.typeInTextField("city","MountianView");
		myAccountFlow.typeInTextField("zipCode","94043");
		myAccountFlow.chooseFromSelect("state","CA");
		myAccountFlow.typeInTextField("email",email);
		myAccountFlow.selectRadioButton("eb1");
		myAccountFlow.pressButton("Submit[1]");
		Assert.assertTrue(myAccountFlow.h2Visible("Buy Back Request Submitted"));
		/*ElementStub el = myAccountFlow.getBrowser().table("table");
		String ticketID = myAccountFlow.getBrowser().getText(el);
		logger.info(ticketID);*/
		
	}

	public void openTicket() throws Exception {
		myAccountFlow.clickLink("History");
		myAccountFlow.clickLink("Ticket History");
	}

	public void goToTAS() throws Exception {
		
		myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", props.getString("TAS.UserName"));
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", props.getString("TAS.Password"));
		if (myAccountFlow.submitButtonVisible(props.getString("TAS.Login"))) {
			myAccountFlow.pressSubmitButton(props.getString("TAS.Login"));
		} else {
			myAccountFlow.pressButton(props.getString("TAS.Login"));
		}
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		pressButton("Search Service");
	}

	public void verifyEligibility() throws Exception {
		Assert.assertTrue(myAccountFlow.checkboxVisible("eligible"));
	}

	public void verifyNotEligible() throws Exception {
		Assert.assertFalse(myAccountFlow.checkboxVisible("eligible"));
	}
	
	private boolean buttonVisible(String button) {
		return myAccountFlow.buttonVisible(button) || myAccountFlow.submitButtonVisible(button);
	}

	public void selectParentSolutionAnd(String parentSolution, String solution)throws Exception {
		myAccountFlow.clickCellMessage(parentSolution);
		if(solution.equalsIgnoreCase("Phone Exchanges-Non Defective")){
			myAccountFlow.clickLink("Solution: 1804");
		} 
		if (solution.equalsIgnoreCase("SIM Exchanges")){
			myAccountFlow.clickLink("Solution: 4001");
		}		
		if (solution.equalsIgnoreCase("2G Migration")) {
			myAccountFlow.clickLink("Solution: 9001");
		}
		if (solution.equalsIgnoreCase("CDMA LTE SIM Exchanges")) {
			myAccountFlow.clickLink("Solution: 15701");
		}
		if (solution.equalsIgnoreCase("TMO Band 2")){
			myAccountFlow.clickLink("Solution: 2105");
		}
		
	}

	public void selectTicketType(String solName) throws Exception {
		myAccountFlow.clickLink(solName);
		myAccountFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sdi\\d::disAcr/");
	}
	
	public void selectCaseForExpiredWarranty(String caseType) throws Exception {
		
		if(caseType.equalsIgnoreCase("Lost or Stolen")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_0/");
			
		}else if (caseType.equalsIgnoreCase("Physical Damage")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_1/");
			
		}else if (caseType.equalsIgnoreCase("Bright Point Order Not Received")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
			
		}else if (caseType.equalsIgnoreCase("System Error")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_3/");
			
		}else if (caseType.equalsIgnoreCase("Different Phone Technology")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_4/");	
		}
		
		pressButton("Accept");
		System.out.println(caseType);
		
	}

	public void checkForUnlockDeviceOptionAvailablility() throws Exception {
		pressButton("Refresh");
		if(buttonVisible("Continue to Service Profile")){			
			pressButton("Continue to Service Profile");
		}
		Assert.assertFalse(myAccountFlow.cellVisible("UNLOCK DEVICE"));
	}

	public void validateUnlockEligibilityForErrorMessage() throws Exception {
		pressButton("Verify Eligibility");
		Assert.assertTrue(myAccountFlow.cellVisible("ERROR: Case already exists (1027)"));
	}

	public void validateEligibilityForNotEligibleMessage() throws Exception {
		pressButton("Verify Eligibility");
		Assert.assertTrue(myAccountFlow.cellVisible("ERROR: Device not elegible (1030)") || myAccountFlow.cellVisible("/ERROR.*?/"));
	}

	public void checkForConfirmation() throws Exception {
		Assert.assertTrue(esnUtil.getCurrentESN().getEsn(),myAccountFlow.h2Visible("Transaction Summary"));
	}
	
	public void goToUnlockPhoneEligibiltyPageAfterBuybackRequest() throws Exception {
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Redemption");
		myAccountFlow.clickLink("Loss Prevention");
		myAccountFlow.clickLink("Unlock Phone Eligibility");
	}
	
	public void selectRefundType(String RefundType) throws Exception {
		
		if(RefundType.equalsIgnoreCase("CashBack")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:sor1:_0/");
		}else if(RefundType.equalsIgnoreCase("UpgradeCredit")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:sor1:_1/");
		}
			
	}
}
