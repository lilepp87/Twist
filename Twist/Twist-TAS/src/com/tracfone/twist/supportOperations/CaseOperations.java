package com.tracfone.twist.supportOperations;

//JUnit Assert framework can be used for verification

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.*;

import junit.framework.Assert;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CaseOperations {
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private String brand;
	private String caseType;
	private static final String ATT = "AT&T";
	private static final String TMOBILE = "T-MOBILE";
	private static SimUtil simUtil;
	private static Logger logger = LogManager.getLogger(CaseOperations.class.getName());
	public CaseOperations() {

	}

	public void getEsnForQueueOfBrand(String queue, String brand) throws Exception {
		String esn = null;
		this.brand = brand;
		ESN currEsn=null;
		try{
			currEsn= new ESN("TESTESN", phoneUtil.getEsnbyQueueandBrand(queue, brand));
		}catch(Exception e){
			if(brand.equalsIgnoreCase("SIMPLE_MOBILE")){
				currEsn= new ESN("PHSM64PSIMT5BLC", phoneUtil.getActiveEsnByPartNumber("PHSM64PSIMT5BLC"));
			} else if(brand.equalsIgnoreCase("NET10")){
				currEsn= new ESN("NTLG236CP", phoneUtil.getActiveEsnByPartNumber("NTLG236CP"));
				}else if(brand.equalsIgnoreCase("TRACFONE")){
				currEsn= new ESN("TFLG220CP", phoneUtil.getActiveEsnByPartNumber("TFLG220CP"));
				}else if(brand.equalsIgnoreCase("TELCEL")){
				currEsn= new ESN("TCHUH867GWHP4", phoneUtil.getActiveEsnByPartNumber("TCHUH867GWHP4"));
				}else if(brand.equalsIgnoreCase("STRAIGHT_TALK")){
					currEsn= new ESN("STZEZ930GP5", phoneUtil.getActiveEsnByPartNumber("STZEZ930GP5"));
				}else if(brand.equalsIgnoreCase("TOTAL_WIRELESS")){
					currEsn= new ESN("TWMTXT830CP", phoneUtil.getActiveEsnByPartNumber("TWMTXT830CP"));
				}else if(brand.equalsIgnoreCase("GO_SMART")){
					currEsn= new ESN("PHGS128PSIMT5ND", phoneUtil.getActiveEsnByPartNumber("PHGS128PSIMT5ND"));
				}
		}
		esn=currEsn.getEsn();
		esnUtil.setCurrentESN(currEsn);
		logger.info(esn);
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", esn);
		clickButton("Search Service");
		//Continue to profile page.
		checkForTransferButton();
		//myAccountFlow.clickLink(esn);
		myAccountFlow.clickLink(props.getString("TAS.CreateCase"));
	}

	private void checkForTransferButton() {
		if(getButtonType("Continue to Service Profile")){
			clickButton("Continue to Service Profile");
		}
		
	}

	public boolean getButtonType(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName) || myAccountFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void selectCaseTypeTitleStatusSource(String caseType, String title, String status, String source) throws Exception {
		this.caseType = caseType;
 		if(myAccountFlow.h2Visible("Balance Inquiry")){
			clickButton("Manual Balance Entry");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "10");
			clickButton("Submit");
			clickButton("Done");
		}
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc8/", caseType);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc10/", title);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", status);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", source);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "test");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue(props.getString("TAS.Reason"));
		if (caseType.equalsIgnoreCase("Technology Exchange") || caseType.equalsIgnoreCase("Warehouse")|| caseType.equalsIgnoreCase("Warranty")) {
			clickButton(props.getString("TAS.LoadPart"));
			//clickButton(props.getString("TAS.Search"));
			if(myAccountFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/").isVisible()){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "4");
			}
			
		}
		clickButton(props.getString("TAS.Save&Continue"));
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TiestlastNmae");
			clickButton(props.getString("TAS.Save&Continue"));
		}
		if(myAccountFlow.h2Visible("Balance Inquiry")){
			clickButton("Manual Balance Entry");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "10");
			clickButton("Submit");
			clickButton("Done");
		}
	
		if("Air Time Cards".equalsIgnoreCase(caseType) ){
			 if("CDMA".equalsIgnoreCase(myAccountFlow.getBrowser().cell("CDMA").getText())){
				 myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:it1/", "TEST DEALER");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:3:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:it1/", "3059999999");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:4:it1/", "TEST_RATE_PLAN");
			 }else{
				 	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:3:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:4:it1/", "TEST DEALER");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:5:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1/", "3059999999");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:10:it1/", "TEST_RATE_PLAN");
			 }
			
		}
		if("Airtime".equalsIgnoreCase(caseType)  && "Credit Card Issues".equalsIgnoreCase(title)){
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:it1/", esnUtil.getCurrentESN().getEsn());
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:it1/", "REFID");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:it1/", "TEST_MODEL");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:3:it1/", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
					if("CDMA".equalsIgnoreCase(myAccountFlow.getBrowser().cell("CDMA").getText())){
				 	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:7:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:8:it1/", "TEST_FLOW");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:9:it1/", "ERROR_TEST");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:10:it1/", "ERROR_FROM_WEBCSR");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:11:it1/", "1111");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:12:it1/", "TwistFirstName");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:13:it1/", "TRACFONE");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:14:it1/", "ITQ_TEST_COMMENTS");
				 
			 }else{
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:8:it1/", "12345");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:9:it1/", "TEST_FLOW");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:10:it1/", "ERROR_TEST");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:11:it1/", "ERROR_FROM_WEBCSR");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:12:it1/", "1111");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:13:it1/", "TwistFirstName");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:14:it1/", "TRACFONE");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:15:it1/", "ITQ_TEST_COMMENTS");
			 }
		}
		
		if("coverage".equalsIgnoreCase(caseType)){
			 if("Customer needs GSM".equalsIgnoreCase(title) || "-34 Call Restriction".equalsIgnoreCase(title)){
				 if("CDMA".equalsIgnoreCase(myAccountFlow.getBrowser().cell("CDMA").getText())){
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:it1/", "33178");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:4:it1/", "TRACFONE");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:5:it1/", "YES");
				 }
				 else{
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:3:it1/", "33178");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:5:it1/", "TRACFONE");
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1/", "YES");
			 }
			 } 
		}
		if("inventory".equalsIgnoreCase(caseType)){
				   myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:it1/", "TEST1");
				   myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:it1/", "3059999999");
				   myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:it1/", "TRACFONE");
				   myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:4:it1/", "WRONG DELAER");
			
		}
		if("Technology Exchange".equalsIgnoreCase(caseType)){
			 myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1/","1000");
			      clickButton(props.getString("TAS.Save&Continue"));
		}
		if("Port In".equalsIgnoreCase(caseType)){
			 myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:7:it1/","10");
		}
		if("ILD".equalsIgnoreCase(caseType)){ 
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:t\\d:2:it1/","30");
		}
		
				  clickButton(props.getString("TAS.Save&Continue"));
		
		if(myAccountFlow.h2Visible("Validate Address") || myAccountFlow.h2Visible("Ticket Address")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3::content/", "1295 charleston Road");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6::content/", "MOUNTAIN VIEW");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7::content/", "CA");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8::content/", "94043");
			clickButton("Validate");
			clickButton(props.getString("TAS.Save&Continue"));
		}
		if(myAccountFlow.h2Visible("Ticket Address")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7::content/", "1295 charleston Road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", "94043");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", "Mountain View");
		//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/", "CA");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17::content/", "9999999999");
			clickButton(props.getString("TAS.Save&Continue"));
		}
		
		Assert.assertTrue(myAccountFlow.cellVisible("Id Number"));
	}

	public void addNotesChangeStatusShippingDetails() throws Exception {
		myAccountFlow.clickLink("Add Notes");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Tech");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").setValue(props.getString("TAS.Reason"));

		clickButton(props.getString("TAS.LogButton"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
		myAccountFlow.clickLink("Status Change");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Address Updated");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/").setValue("ITQ Test-address update status changed");
		clickButton(props.getString("TAS.ChangeStatus"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
		myAccountFlow.clickLink("Shipping Address");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", "TwistLastName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it23/", "1295 Charleston road");
		clickButton("Save / Verify");
		TwistUtils.setDelay(2);
	}

	public void goToSupportAndChooseCaseMaintainanceAndSearchForCaseId() throws Exception {
		Calendar calObject = Calendar.getInstance();
        calObject.add(Calendar.DAY_OF_YEAR, 1);
		String caseId = myAccountFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:1:r\\d:\\d:ot3/").getText();
		
		myAccountFlow.clickLink("Support");
		myAccountFlow.clickLink("Ticket Maintenance");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId1:value30/", caseId);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId\\d:value70::content/", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId\\d:value80::content/", new SimpleDateFormat("MM/dd/yyyy").format(calObject.getTime()));
		clickButton(props.getString("TAS.Search"));
		myAccountFlow.clickLink(caseId);
	}

	public void modifyCaseDetails() throws Exception {
		myAccountFlow.clickLink("Add Notes");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Tech");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").setValue("ITQ-test-Case Maintainanace");
		clickButton(props.getString("TAS.LogButton"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
		myAccountFlow.clickLink("Status Change");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Address Updated");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/")
				.setValue("ITQ Test-address update status changed-Case Maintainanace");
		clickButton(props.getString("TAS.ChangeStatus"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
	}

	public void closeCase() throws Exception {
		clickButton(props.getString("TAS.CaseClose"));
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc8/", "Closed");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/").setValue("ITQ-TEST-CLOSE CASE");
		clickButton(props.getString("TAS.CaseClose1"));
	}

	public void clickButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
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
	public void goToEsnSupport() throws Exception {
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Accessory Ticket");
	}

	public void selectAccessoriesAndCreateTicket() throws Exception {
		if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1/")){
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1/");
		}else{
			throw new AssertionError("Could not find any accessory items");
		}
		if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1:_1/")){
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1:_1/");
			if(myAccountFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1:_2/")){
				myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:smc1:_2/");
				
			}
		}
		clickButton("Save & Continue");
		
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Medium");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Pending Approval");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "itq accessory case ticket");
		myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", "itq-test");
		clickButton("Continue");
		
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8::content/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3::content/", "TiestlastNmae");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17::content/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", TwistUtils.createRandomEmail() );
		clickButton("Enter/Update Address");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", "33178");
		myAccountFlow.typeInTextField( "/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "9700 NW 112th Ave");
		/*TwistUtils.setDelay(15);
		if (myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").isVisible()) {
			TwistUtils.setDelay(7);
			myAccountFlow.getBrowser().listItem("9700 NW 112th Ave, Medley FL 33178-1353").click();
		}
		TwistUtils.setDelay(5);*/
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Medley");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "FL");
		clickButton("Validate DPV");
//		TwistUtils.setDelay(20);
				if(myAccountFlow.cellVisible("DPV validation failed, Please re-enter the address and select from suggested values.")){
					clickButton("Override DPV And Save");
				}
				TwistUtils.setDelay(5);
			clickButton("Save & Continue");
		clickButton("Save & Continue");
		TwistUtils.setDelay(5);
//		clickButton("Save & Continue");
		
//		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TiestlastNmae");
//			clickButton(props.getString("TAS.Save&Continue"));
//		}
//		clickButton("Save & Continue");
//
//		if(myAccountFlow.h2Visible("Validate Address") || myAccountFlow.h2Visible("Ticket Address")){
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7::content/", "1295 charleston Road");
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", "94043");
//			clickButton("Save & Continue");
//		}
		
	}

	public void checkForConfirmation() throws Exception {
		
		Assert.assertTrue(myAccountFlow.cellVisible("Id Number"));
	}

	public void checkForExchangeWithZip(String zip) throws Exception {
		ESN esn  = esnUtil.getCurrentESN();
		String partNumber= esn.getPartNumber();
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Activation");
		myAccountFlow.typeInTextField("/r2:\\d:r1:\\d:it2/", zip);
		clickButton("Activate");
		clickButton("Exchange");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue(props.getString("TAS.Reason"));
		//clickButton(props.getString("TAS.SelectPart"));
		clickButton(props.getString("TAS.LoadPart"));
		clickButton(props.getString("TAS.ChangePart"));
		clickButton(props.getString("TAS.Search"));
		clickButton(props.getString("TAS.Save&Continue"));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1::content/", "1000");
		clickButton(props.getString("TAS.Save&Continue"));
		clickButton(props.getString("TAS.Save&Continue"));
	}

	public void enterNumberFromWithPartZipCheckForExchange(String portBrand,String oldPart, String zip) throws Exception {
		checkForTransferButton();
		String oldEsn;
		TwistUtils.setDelay(5);
		clickButton("Refresh");
		if(!esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") && oldPart.equalsIgnoreCase("external")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Portability - Port In");
		}
		oldEsn = esnUtil.popRecentActiveEsn(oldPart).getEsn();
		String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
		logger.info("Old ESN:"+oldEsn+";Old MIN"+oldMin+";Old Brand:"+portBrand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", oldMin);
		pressButtonIfVisible("Continue");
		
		String newSim = esnUtil.getCurrentESN().getSim();
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/", newSim);
		}		
		if(oldPart.equalsIgnoreCase("External") && myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", zip);
		}
		pressButtonIfVisible("Continue");		
		pressButtonIfVisible("Security verified");
		
		if(!esnUtil.getCurrentESN().getPartNumber().matches("BYO.*") || !esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") ){
			clickButton("Exchange");
			myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue(props.getString("TAS.Reason"));
			clickButton(props.getString("TAS.LoadPart"));
			clickButton(props.getString("TAS.Save&Continue"));
			if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TwistLastName");
				pressButtonIfVisible(props.getString("TAS.Save&Continue"));
			}
			TwistUtils.setDelay(10);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1::content/", "1000");
			clickButton(props.getString("TAS.Save&Continue"));
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it7::content/", "1295 charleston road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it4::content/", "94043");
			clickButton(props.getString("TAS.Save&Continue"));
	}
	
	}
	
	public void pressButtonIfVisible(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else if(myAccountFlow.buttonVisible(buttonType)){
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void goToCreateTicketFlow() throws Exception {
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Create Ticket");
	}

	public void selectTicketTypeAndTicketTitle(String caseType, String title)
			throws Exception {
		this.caseType = caseType;
 		if(myAccountFlow.h2Visible("Balance Inquiry")){
			clickButton("Manual Balance Entry");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "10");
			clickButton("Submit");
			clickButton("Done");
		}
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc8/", caseType);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc10/", title);
	//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Pending");
	//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", source);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "test");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue(props.getString("TAS.Reason"));
	
	}

	public void selectPartNumberForExchangeOfType(String replPartNumber, String typeOfCase)
			throws Exception {
		clickButton(props.getString("TAS.LoadPart"));
		/*
		simUtil.getSimPartFromSimNumber(esnUtil.getCurrentESN().getSim());
		if(typeOfCase.equalsIgnoreCase("phone exchange")){
			String partNumber = esnUtil.getCurrentESN().getPartNumber();
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/", partNumber.substring(0, 7));
		}else if(typeOfCase.equalsIgnoreCase("sim exchange")){
			
		}
		*/
		clickButton(props.getString("TAS.Save&Continue"));
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TiestlastNmae");
			clickButton(props.getString("TAS.Save&Continue"));
		}
		
	//	String carrier = phoneUtil.getParentCarrierName(esnUtil.getCurrentESN().getEsn());
	//	logger.info(carrier);
	}

	public void selectPartNumberForExchangeOfTypes(String simNumber, String typeOfCase)
			throws Exception {
		clickButton(props.getString("TAS.LoadPart"));
		
		if(typeOfCase.equalsIgnoreCase("digital phone exchange")){
		
			if(simNumber.contains("T5")){
			clickButton("Same carrier exchange [T-MOBILE]");
			}else if(simNumber.contains("C4")){
			clickButton("Same carrier exchange [ATT]");
			}else if(simNumber.contains("CL7")){
			clickButton("Same carrier exchange [CLARO]");
			}else if(simNumber.contains("V9")){
			clickButton("Same carrier exchange [VERIZON]");
			}
		}
		
		clickButton(props.getString("TAS.Save&Continue"));
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TiestlastNmae");
			clickButton(props.getString("TAS.Save&Continue"));
		}
		
	}
		
	public void selectPartNumberForExchangeOfTypesForSolution(String simNumber, String typeOfCase, String solutionName)
			throws Exception {
		clickButton(props.getString("TAS.LoadPart"));
		
		if(solutionName.equalsIgnoreCase("TMO Band 2")){
			if(simNumber.contains("T5")){
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "5");
			}else if(simNumber.contains("C4")){
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "4");
			}else if(simNumber.contains("CL7")){
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "7");
			}else if(simNumber.contains("V9")){
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "9");
			}
		}
		
		if(typeOfCase.equalsIgnoreCase("digital phone exchange")){
		
			if(simNumber.contains("T5")){
			clickButton("Same carrier exchange [T-MOBILE]");
			}else if(simNumber.contains("C4")){
			clickButton("Same carrier exchange [ATT]");
			}else if(simNumber.contains("CL7")){
			clickButton("Same carrier exchange [CLARO]");
			}else if(simNumber.contains("V9")){
			clickButton("Same carrier exchange [VERIZON]");
			}
		}
		
		clickButton("Continue");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8::content/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3::content/", "TiestlastNmae");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17::content/", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", TwistUtils.createRandomEmail() );
		clickButton("Enter/Update Address");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", "33178");
		myAccountFlow.typeInTextField( "/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "9700 NW 112th Ave, Medley");
		/*TwistUtils.setDelay(15);
		if (myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").isVisible()) {
			TwistUtils.setDelay(7);
			myAccountFlow.getBrowser().listItem("9700 NW 112th Ave, Medley FL 33178-1353").click();
		}
		TwistUtils.setDelay(5);*/
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Medley");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "FL");
		clickButton("Validate DPV");
		//	TwistUtils.setDelay(20);
			if(myAccountFlow.cellVisible("DPV validation failed, Please re-enter the address and select from suggested values.")){
				clickButton("Override DPV And Save");
			}
		clickButton("Save & Continue");
		
//		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
//			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TiestlastNmae");
//			clickButton(props.getString("TAS.Save&Continue"));
//		}
		
	}
	
	public void selectPartNumberForExchangeOfTypeForLine(String replPartNumber, String typeOfCase, String status) throws Exception {
		if("new".equalsIgnoreCase(status)){
			if(myAccountFlow.getBrowser().checkbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:sbc1::content/").checked()){
				
			}else{
				
			myAccountFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:sbc1::content/");
		}
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:it8::content/", "33178");
			}else if("active".equalsIgnoreCase(status)){
				
		}
		selectPartNumberForExchangeOfType(replPartNumber, typeOfCase);
	}
	
	public void chooseSimProfileBasedOnSimType(String simNumber) throws Exception {
	/*	if(simNumber.contains("T5")){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "5");
		}else if(simNumber.contains("C4")){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "4");
		}else if(simNumber.contains("CL7")){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "7");
		}else if(simNumber.contains("V9")){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "9");
		}*/
		
	}

	public void completeShipConfirmFor(String string1) throws Exception {
		String domain;
		
		if(string1.equalsIgnoreCase("digital phone exchange")){
			 domain = "SIM CARDS";
			 completeShipConfirmForDevice(domain);
		}else if(string1.equalsIgnoreCase("phone exchange")){
			domain = "PHONES";
			completeShipConfirmForTicket();
			completeShipConfirmForDevice(domain);
		}else if(string1.equalsIgnoreCase("sim exchange")){
			 domain = "SIM CARDS";
			 completeShipConfirmForDevice(domain);
		}
		
		
	}

	public void continueToNextSteps() throws Exception {
		if (myAccountFlow.cellVisible("Balance Inquiry")) {
			
			clickButton("Manual Balance Entry");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "1");
			
			if (myAccountFlow.cellVisible("Sms")) 
			{
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18::content/", "1");
			}
			if (myAccountFlow.cellVisible("Data")) 
			{
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19::content/", "1");
			}
			clickButton("Submit");
			clickButton("Done");
		}
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1/","1000");
		
		if(myAccountFlow.h2Visible("Validate Address") || myAccountFlow.h2Visible("Ticket Address")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7::content/", "1295 charleston Road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", "94043");	
			clickButton(props.getString("TAS.Save&Continue"));
		}
		clickButton(props.getString("TAS.Save&Continue"));
		//clickButton(props.getString("TAS.Save&Continue"));
	}

	public void goToCreateTicketFlowUsingSolutions() throws Exception {
		myAccountFlow.clickLink("ESN Support");
		myAccountFlow.clickLink("Solutions");
	}
	public void completeShipConfirmForTicket() throws Exception {
		if(buttonVisible("Accept")){
			clickButton("Accept");
			clickButton("Accept[1]");
		}else if(buttonVisible("Yank")){
			clickButton("Yank");
		}
		myAccountFlow.clickLink("Part Request");
		
		if (myAccountFlow.cellVisible("ACC")) {
			
			myAccountFlow.clickCellMessage("ACC");
			/*if(airbillShipType.equalsIgnoreCase("ELECTRONIC")){
				myAccountFlow.clickCellMessage("/.*?BB-EX.*?/");
			}else {
				myAccountFlow.clickCellMessage("/.*?BP-EX.*?/");
			}*/
	//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc7::content/", "BP_IO");
	//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc5::content/", "FEDEX");
	//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc6::content/", "2nd DAY");
			clickButton("Ship");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", "654EERRWWWTTW");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
			clickButton("Ship[1]");
			Assert.assertTrue("AIRBILL SHIPMENT FAILE",myAccountFlow.cellVisible("SUCCESS"));
			myAccountFlow.clickLink("Close[1]");
			myAccountFlow.clickLink("Status Change");
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3::content/", "ESN Received");
			myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", "ITQ_ESN RECEIVED STATUS UPDATE");
			clickButton("Change Status");
			clickButton("OK[1]");
		}	
	}
	
	public void completeShipConfirmForDevice(String domain) throws Exception {
		
		if(buttonVisible("Accept")){
			clickButton("Accept");
			clickButton("Accept[1]");
		}else if(buttonVisible("Yank")){
			clickButton("Yank");
		}
		
		myAccountFlow.clickLink("Part Request");
		System.out.println("phone domain");
		if (myAccountFlow.cellVisible(domain)) {
			myAccountFlow.clickCellMessage(domain);
		}
		
//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc7::content/", "BP_IO");
//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc5::content/", "FEDEX");
//		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:soc6::content/", "2nd DAY");
		clickButton("Ship");
		String partNum = myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:xReplPartNumId::content/").value();
		if(domain.equalsIgnoreCase("PHONES")){
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", phoneUtil.getNewEsnByPartNumber(partNum));
		}else if (domain.equalsIgnoreCase("SIM CARDS")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", simUtil.getNewSimCardByPartNumber(partNum));
		}
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
		clickButton("Ship[1]");
		Assert.assertTrue("PART REQUEST SHIPMENT FAILED",myAccountFlow.cellVisible("SUCCESS"));
 		myAccountFlow.clickLink("Close[1]");
	}
	private boolean buttonVisible(String button) {
		return myAccountFlow.buttonVisible(button) || myAccountFlow.submitButtonVisible(button);
	}

	public void createPhoneExchangeCaseUsingMicrosite() throws Exception {
		
		myAccountFlow.navigateTo(props.getString("2gmicrosite"));
		myAccountFlow.typeInTextField("phoneNumber", esnUtil.getCurrentESN().getMin());
		String esn = esnUtil.getCurrentESN().getEsn();
		myAccountFlow.typeInTextField("serialNumber", esn.substring(esn.length() - 4 ));
		clickButton("VERIFY ELIGIBILITY");
		clickButton("CONTINUE");
		
		myAccountFlow.typeInTextField("firstname", "TwistFirstNameMicrosite");
		myAccountFlow.typeInTextField("lastname", "TwistLasNameMicrosite");
		myAccountFlow.typeInTextField("address1", "1295 charleston rd");
		myAccountFlow.typeInTextField("city", "Mountain view");
		myAccountFlow.chooseFromSelect("state", "CA");
		myAccountFlow.typeInTextField("zipCode", "94043");
		myAccountFlow.typeInTextField("contactnumber", "9999999999");
		myAccountFlow.typeInTextField("email", "akumarkoripalli@tracfone.com");
		
		clickButton("Submit");
		
		myAccountFlow.h2Visible("Your Phone Exchange case was created successfully ");
		
		clickButton("Finish");
		
		
	
	}
	
	public void completeShipConfirmForFCCTicketWithType(String refundType) throws Exception {
		if(buttonVisible("Accept")){
			clickButton("Accept");
			clickButton("Accept[1]");
		}else if(buttonVisible("Yank")){
			clickButton("Yank");
		}
		myAccountFlow.clickLink("Part Request");
		myAccountFlow.clickCellMessage("ACC");
		clickButton("Ship");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", "654EERRWWWTTW");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
		clickButton("Ship[1]");
		Assert.assertTrue("AIRBILL SHIPMENT FAILE",myAccountFlow.cellVisible("SUCCESS"));
		myAccountFlow.clickLink("Close[1]");
		if(refundType.equalsIgnoreCase("CashBack")){
			myAccountFlow.clickLink("Status Change");
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3::content/", "ESN Received");
			myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", "ITQ_ESN RECEIVED STATUS UPDATE");
			clickButton("Change Status");
			if(buttonVisible("OK")){
				clickButton("OK");
			}else if(buttonVisible("OK[1]")){
			clickButton("OK[1]");
			}
		}
	}

	public void escalateWithOptionForAndUpdateAddressOrPartNumber(String option, String domain, String partNumber) throws Exception {
		clickButton("Escalate Case");
		if(option.equalsIgnoreCase("Bad address")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:sbr1/");
			myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it16/", "ITQ");
			clickButton("OK[1]");
			myAccountFlow.clickLink("Shipping Address");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it12/","33178");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it23/","9700 NW 112 ave");
			clickButton("Save / Verify");
			
			myAccountFlow.clickLink("Part Request");
			myAccountFlow.clickCellMessage("PENDING");
			clickButton("Save");
			myAccountFlow.clickLink("Close[1]");
			if(myAccountFlow.cellVisible("PENDING")){
				myAccountFlow.clickCellMessage("PENDING");
			}else if(myAccountFlow.cellVisible("INCOMPLETE")){
				myAccountFlow.clickCellMessage("INCOMPLETE");
			}else {
				myAccountFlow.clickCellMessage("ONHOLD");
			}

			clickButton("Ship");
			String partNum = myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xReplPartNumId::content/").value();
			if(domain.equalsIgnoreCase("Phone Exchanges-Non Defective")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", phoneUtil.getNewEsnByPartNumber(partNum));
			}else if (domain.equalsIgnoreCase("SIM Exchanges")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", simUtil.getNewSimCardByPartNumber(partNum));
			}
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
			clickButton("Ship[1]");
			myAccountFlow.clickLink("Close[1]");
			
		}else if(option.equalsIgnoreCase("Change part number")){
			myAccountFlow.selectRadioButton("r2:1:r1:0:sbr2::content");
			myAccountFlow.typeInTextArea("r2:1:r1:0:it16::content", "ITQ");
			clickButton("OK[1]");
			
			myAccountFlow.clickLink("Part Request");
			myAccountFlow.clickCellMessage("ONHOLD");
			clickButton("Save");
			
			myAccountFlow.clickLink("Part Request");
			myAccountFlow.clickCellMessage("PENDING");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xReplPartNumId/",partNumber);
			clickButton("Save");			
			myAccountFlow.clickLink("Close[1]");
			
			myAccountFlow.clickLink("Part Request");
			myAccountFlow.clickCellMessage("PENDING");
			clickButton("Ship");
			String partNum = myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:xReplPartNumId::content/").value();
			if(domain.equalsIgnoreCase("Phone Exchanges-Non Defective")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", phoneUtil.getNewEsnByPartNumber(partNum));
			}else if (domain.equalsIgnoreCase("SIM Exchanges")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", simUtil.getNewSimCardByPartNumber(partNum));
			}
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
			clickButton("Ship[1]");
			myAccountFlow.clickLink("Close[1]");

		}else {
			myAccountFlow.selectRadioButton("r2:1:r1:0:sbr3::content");
			myAccountFlow.typeInTextArea("r2:1:r1:0:it16::content", "ITQ");
			clickButton("OK[1]");
		}
		
	
	}

	public void loginWithItquser2() throws Exception {
		if (myAccountFlow.linkVisible("Logout")) {
			myAccountFlow.clickLink("Logout");
			myAccountFlow.navigateTo(props.getString("TAS.HomeUrl"));
		}
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "Itquser2");
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abcd1234!");
		if (myAccountFlow.submitButtonVisible(props.getString("TAS.Login"))) {
			myAccountFlow.pressSubmitButton(props.getString("TAS.Login"));
		} else {
			myAccountFlow.pressButton(props.getString("TAS.Login"));
		}
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esnUtil.getCurrentESN().getEsn());
		clickButton("Search Service");
		clickButton("Yank");
	}



}
