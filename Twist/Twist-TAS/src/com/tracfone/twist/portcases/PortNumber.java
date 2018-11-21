package com.tracfone.twist.portcases;

// JUnit Assert framework can be used for verification

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.sahi.client.Browser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.tracfone.twist.addAirtime.Redemption;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;
public class PortNumber {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private static ServiceUtil serviceUtil ;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private CboUtils cboUtils;
	private String error_msg;
	public ESN old_esn;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static Logger logger = LogManager.getLogger(PortNumber.class.getName());
	public PortNumber() {

	}

	public void enterNumberFromWithPartZip(String portBrand, String oldPart,String zip) throws Exception {
		checkForTransferButton();
		checkForContinuePortButton();
		String oldEsn;
		String min;
		TwistUtils.setDelay(5);
		pressButton("Refresh");
		if((!esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || !esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) && oldPart.equalsIgnoreCase("external")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Portability - Port In");
		}
		
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", zip);
	
		String newSim = esnUtil.getCurrentESN().getSim();
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/", newSim);
		}	
		if((esnUtil.getCurrentESN().getPartNumber().matches("BYO.*") && esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")) ||  (esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") )){
			
			
			
		}else{
			pressButton("Validate Esn");
			Assert.assertTrue(myAccountFlow.cellVisible("Valid ESN"));
			pressButton("Validate NAP");
			Assert.assertTrue(myAccountFlow.cellVisible("Coverage is available"));
		}
		//String newEsn = esnUtil.getCurrentESN().getEsn();
		//String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(oldPart, newEsn);
		//String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
		if(!oldPart.equalsIgnoreCase("external") ){
			this.old_esn= esnUtil.popRecentActiveEsn(oldPart);
			oldEsn = old_esn.getEsn();
			String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
			logger.info("Old ESN:"+oldEsn+";Old MIN"+oldMin+";Old Brand:"+portBrand);
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5/", portBrand);
			TwistUtils.setDelay(5);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", oldMin);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", oldEsn);
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", "FL");
		}else{
			
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5/", portBrand);
			if("other".equalsIgnoreCase(portBrand)){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it26/", "TEST CARRIER");
			}
			min = TwistUtils.generateRandomMin();
			esnUtil.getCurrentESN().setMin(min);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", min);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", "12345678" );
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10/", "12345");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it14/", "3480");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/", "TwistFirstname");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/","TwistlastName" );
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", TwistUtils.generateRandomMin());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19/", TwistUtils.createRandomEmail() );
			pressButton("Enter/Update Address");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "1295 Charleston Road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Mountain View");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "CA");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/","94043" );
			pressButton("Validate");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/", "1295 Charleston Road");
		//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", "CA");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/","94043" );
			
		}
		
		//Asha
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15::content/", "33178");
		//Asha ends
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Wireless");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "33178");
		
	}

	public void addAirtimeCard(String pinPart) {
		ESN esn  = esnUtil.getCurrentESN();
		if((esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")|| esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) && !pinPart.isEmpty()){
			String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			String partNumber = esnUtil.getCurrentESN().getPartNumber();
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
			pressButton("Validate");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "33178");
			}
			if(partNumber.equalsIgnoreCase("byop") || partNumber.equalsIgnoreCase("byophex")){
			//	myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_0/");
			 }else if(partNumber.equalsIgnoreCase("byod") || partNumber.equalsIgnoreCase("byodhex")){
				// myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_1/");
			 }
			pressButton("Add Port Information");
			//pressButton("Continue");			
		}else{
		if(!pinPart.isEmpty() && !"AWOP".equalsIgnoreCase(pinPart)){
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		pressButton("Add Airtime Card");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", pin);
		pressButton("Validate Card");
		TwistUtils.setDelay(5);
		Assert.assertTrue("Error in validating PIN",myAccountFlow.cellVisible("Valid PIN"));
		logger.info("PIN:"+pin);
		pressButton("OK[1]");
		} else if("AWOP".equalsIgnoreCase(pinPart)){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", "33178");
			pressButton(props.getString("TAS.AWOP"));
			//select supervisor approval
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_2/");
			pressButton("Validate Plan");
			if (myAccountFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/").isVisible()) {
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "20");
			}
			
			if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/")) {
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "20");
			}
			
			if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11/")) {
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11/", "20");
			}
			if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/")) {
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", "20");
			}
			
			if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10/")) {
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10/", "20");
			}
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Activation/Reactivation Failure");
			myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/").setValue("ITQ-AWOP TEST");
			if(myAccountFlow.buttonVisible("Code Accepted") || myAccountFlow.submitButtonVisible("Code Accepted")){
				//Activation with awop flow
				pressButton("Activate");
			}else{
				//Port with awop flow
				pressButton("Continue");
			}
				//	;
			if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21/")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21/", "ITQUSER");
				pressButton("Accept");
			}
		}
		TwistUtils.setDelay(5);
	//	pressButton("Process");
		}
		if(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").isVisible()){
			error_msg= myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").getText();
		}
	}
	private boolean isTextFieldEnabled(String buttonType) {
		if (myAccountFlow.getBrowser().textbox(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
			return false;
		} else {
			return true;
		}
	}
	public void finishPort() throws Exception {
		String currESN = esnUtil.getCurrentESN().getEsn();
		phoneUtil.finishPhoneActivationAfterPortIn(currESN);
		phoneUtil.runIGateIn();
		phoneUtil.clearOTAforEsn(currESN);
	}
	
	public void completePort() throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			
				pressButton("Save");
				
				if(buttonVisible("Continue")){
					pressButton("Continue");
				}
				
				if(myAccountFlow.linkVisible("/Validate and Add/")){
					myAccountFlow.clickLink("/Validate and Add/");
				}
				
				
			
			//pressButtonIfVisible("Continue Purchase");
			if (myAccountFlow.submitButtonVisible("Process Transaction")) { //for SM
				myAccountFlow.pressSubmitButton("Process Transaction");
			} else if (myAccountFlow.buttonVisible("Process Transaction")){
				myAccountFlow.pressButton("Process Transaction");
			}
		}else {
			if(buttonVisible(props.getString("TAS.process"))){
				pressButton(props.getString("TAS.process"));
			}
			
			if(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").isVisible()){
				error_msg= myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").getText();
			}
			TwistUtils.setDelay(10);
			Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg,myAccountFlow.cellVisible("Transaction Summary"));
		//	Assert.assertTrue("NO TICKET NUMBER FOUND IN TRANSACTION SUMMARY :"+esnUtil.getCurrentESN().getEsn(),myAccountFlow.labelVisible("/.*Ticket Number.*/"));
			esnUtil.getCurrentESN().setTransType("TAS PORT IN["+esnUtil.getCurrentBrand()+"]");
			//keeping to & from same esn numbers since ext. port doesn't contain any esn 
			phoneUtil.checkUpgrade(esnUtil.getCurrentESN(),esnUtil.getCurrentESN());
			
			// To Auto Complete Port Ticket Using CBO
			
			if (!(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") ||  esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile"))){
				String Ordertype=phoneUtil.getOrderType(esnUtil.getCurrentESN().getEsn());
				if( Ordertype.equals("EPIR")||Ordertype.equals("PIR")||Ordertype.equals("IPI")){
					String Actionitemid=phoneUtil.getactionitemidbyESN(esnUtil.getCurrentESN().getEsn());
					String Min =phoneUtil.getminforESN(esnUtil.getCurrentESN().getEsn());
					cboUtils.callcompleteAutomatedPortins(Actionitemid, Min);					
					}
				}
		}
	}

	public void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}
	private void checkForTransferButton() {
		if(getButtonType("Continue to Service Profile")  ){
			pressButton("Continue to Service Profile");
		}
		
	}
	
	private void checkForContinuePortButton() {
		if(getButtonType("Continue with Port Request")  ){
			pressButton("Continue with Port Request");
		}
		
	}

	public boolean getButtonType(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName) || myAccountFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
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
	
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}
	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}
	

	public void deactivatePhone() throws Exception {
	pressButton("Deactivate");
	
	}

	public void createSimExchangeCaseIfNecessaryFor(String simPart) throws Exception {
		checkForTransferButton();
		pressButton("Refresh");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Activation");
	}else if( esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")|| esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") ){
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Activation/ Port");
		pressButton("Transfer Number");
	}else{
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Portability - Port In");
		if(getButtonType("SIM Exchange Case")){
		pressButton("SIM Exchange Case");
		myAccountFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", "ITQ_PORT_TEST");
		pressButton("Load Part Number");
	/**@author mkankanala  Revert sim exchange case scenario changes */
	//	myAccountFlow.clickLink("Select Part Number");
	//	pressButton("Search");
		pressButton("Save & Continue");
	/*	if(myAccountFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/").isVisible()){
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7/", "4");
			}
			if(myAccountFlow.linkVisible("Update Address")){
				myAccountFlow.clickLink("Update Address");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it9::content/", "TwistFirstName");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it8::content/", "TwistLastname");
				myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it2::content/").hover();
				myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it2::content/").focus();
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it2::content/", "4600 NW 107th ave");
				
				myAccountFlow.getBrowser().keyDown(myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it2::content/"), 13, 8629);
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it3::content/", "94043");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it5::content/", "Mountain View");
				myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:soc1::content/", "CA");
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sf\\d:r\\d:\\d:it6::content/", "3059999999");
				clickButton(props.getString("TAS.Save&Continue"));
			}*/
		pressButton("Save & Continue"); 
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21::content/", "TwistFirstName");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it22::content/", "TwistLastName");
			if(buttonVisible("Save & Continue[1]")){
				pressButton("Save & Continue[1]");
			}else{
				pressButton("Save & Continue");
			}
	
			
		}
		
	//	pressButton("Save & Continue");
		pressButton("Save & Continue");
		if(myAccountFlow.cellVisible("Miami, FL 33122, Please confirm address. This is used for default accounts")){
			
			pressButton("Save & Continue");
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "9700 NW 112th ave");
			pressButton("Save & Continue");
		}
	
		myAccountFlow.clickLink("Portability - Port In");
		String newSim = simUtil.getNewSimCardByPartNumber(simPart);	
		phoneUtil.addSimToEsn(newSim, esnUtil.getCurrentESN());
		deactivatePhone();
		}
	}
	}

	public void deactivatePhoneIfNecessary() throws Exception {
		if(getButtonType("Deactivate")){
		deactivatePhone();
		}
	}

	public void completeActivationForPort() throws Exception {
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) {
			if (myAccountFlow.buttonVisible("Refresh Table")){
				pressButton("Refresh Table");
				pressButton("Process Transaction");
			}					
			TwistUtils.setDelay(40);
			if(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").isVisible()){
				error_msg= myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").getText();
			}
			Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg,myAccountFlow.cellVisible("Transaction Summary (Individual/Multiple Devices)"));
			esnUtil.getCurrentESN().setTransType("TAS PORT IN["+esnUtil.getCurrentBrand()+"]");
			phoneUtil.checkUpgrade(old_esn,esnUtil.getCurrentESN());
			
			}
			pressButton("Refresh");
	}

	public void completePortForChild() throws Exception {
		pressButton("Save");
		pressButton("Refresh Table");
		myAccountFlow.clickLink("Validate and Add Port");
		pressButton("Process Transaction");
	}

	public void addPortInformation() throws Exception {
//	pressButton("Port Number (Add Port Information)");
	}

	public void enterNumberFromWithPart(String portBrand, String oldPart) throws Exception {
		checkForTransferButton();
		String oldEsn;
		TwistUtils.setDelay(5);
		pressButton("Refresh");
		if(!esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") && oldPart.equalsIgnoreCase("external")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Portability - Port In");
		}
		if(simUtil.getSimPartFromSimNumber(esnUtil.getCurrentESN().getSim()).contains("CL7")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", "00692");
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", "33178");
		}
		
		String newSim = esnUtil.getCurrentESN().getSim();
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/", newSim);
		}	
		if(!esnUtil.getCurrentESN().getPartNumber().matches("BYO.*") || !esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") ){
			pressButton("Validate Esn");
			Assert.assertTrue(myAccountFlow.cellVisible("Valid ESN"));
			pressButton("Validate NAP");
			Assert.assertTrue(myAccountFlow.cellVisible("Coverage is available"));
		}
		//String newEsn = esnUtil.getCurrentESN().getEsn();
		//String oldEsn = phoneUtil.getActiveEsnToUpgradeFrom(oldPart, newEsn);
		//String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
		if(!oldPart.equalsIgnoreCase("external") ){
			this.old_esn= esnUtil.popRecentActiveEsn(oldPart);
			oldEsn = old_esn.getEsn();
			String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
			logger.info("Old ESN:"+oldEsn+";Old MIN"+oldMin+";Old Brand:"+portBrand);
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5/", portBrand);
			TwistUtils.setDelay(5);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", oldMin);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", oldEsn);
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", "FL");
		}else{
			
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5/", portBrand);
			if("other".equalsIgnoreCase(portBrand)){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it26/", "TEST CARRIER");
			}
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", TwistUtils.generateRandomMin());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", "12345678" );
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10/", "12345");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it14/", "3480");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/", "TwistFirstname");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/","TwistlastName" );
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", TwistUtils.generateRandomMin());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19/", TwistUtils.createRandomEmail() );
			pressButton("Enter/Update Address");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "1295 Charleston Road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Mountain View");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "CA");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/","94043" );
			pressButton("Validate");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/", "1295 Charleston Road");
		//	myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6/", "CA");
		//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/","94043" );
			
		}
		
		//Asha
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15::content/", "33178");
		//Asha ends
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Wireless");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "33178");
		
	}
	
	public void enterMINFromWithPartZip(String portBrand, String oldPart,String zip) throws Exception {
		String[] externalCarriers = {"WINDSTREAM","BELLSOUTH","CENTURY LINK/EMBARQ","COMCAST","LOCUS COMMUNICATIONS(H20)","QWEST"};
		Set<String> localSet = new HashSet<String>(Arrays.asList(externalCarriers));
		
		checkForTransferButton();
		checkForContinuePortButton();
		String oldEsn;
		String min;
		TwistUtils.setDelay(5);
		pressButton("Refresh");
		
		if(!(esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) && oldPart.equalsIgnoreCase("external")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Portability - Port In");
		} /*else{//for add airtime
			String newPin;
			if("NO PIN".equalsIgnoreCase(pinPart)){
				newPin = pinPart;
			}else{
				 newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			}
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Activation");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
			pressButton("Validate");
			TwistUtils.setDelay(5);
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			if(!zip.equalsIgnoreCase("") ){
				if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
					myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", zip);
				}
			}
			pressButton("Add Port Information");
			
		}*/
		
		if(!oldPart.equalsIgnoreCase("external") ){
			this.old_esn= esnUtil.popRecentActiveEsn(oldPart);
			oldEsn = old_esn.getEsn();
			String oldMin = phoneUtil.getMinOfActiveEsn(oldEsn);
			logger.info("Old ESN:"+oldEsn+";Old MIN"+oldMin+";Old Brand:"+portBrand);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", oldMin);
			enterSimZip(zip, oldPart);
		}else{
			min = TwistUtils.generateRandomMin();
			esnUtil.getCurrentESN().setMin(min);
			System.out.println("Random External MIN for "+portBrand+" : "+min);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", min);
			enterSimZip(zip, oldPart);
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", portBrand);
			if(portBrand.equalsIgnoreCase("OTHER")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it26::content/", "TEST");
			}
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Wireless");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", "12345678" );
			if (!localSet.contains((Object)portBrand)){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText2/", "12345890000");
			}
			//myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it14/", "3480");
			if(portBrand.equalsIgnoreCase("VIRGIN MOBILE")){
				myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11/", "3480");
			}
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/", "TwistFirstname");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/","TwistlastName" );
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", TwistUtils.generateRandomMin());
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19/", TwistUtils.createRandomEmail() );
			pressButton("Enter/Update Address");
			/*myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "1295 Charleston Road");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Mountain View");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "CA");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/","94043" );
			pressButton("Validate");*/
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", "33178");
			myAccountFlow.typeInTextField( "/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "9700 NW 112th Ave, Medley");
		
			/*if (myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").isVisible()) {
				TwistUtils.setDelay(7);
				myAccountFlow.getBrowser().listItem("9700 NW 112th Ave, Medley FL 33178-1353").click();
			}*/
			TwistUtils.setDelay(5);
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/", "Medley");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "FL");
			pressButton("Validate DPV");
		//	TwistUtils.setDelay(20);
			if(myAccountFlow.cellVisible("DPV validation failed, Please re-enter the address and select from suggested values.")){
				pressButton("Override DPV And Save");
			}
		}
		
	}
	
	public void enterSimZip(String zip, String oldPart) throws Exception {
		pressButtonIfVisible("Continue");
		
	
		String newSim = phoneUtil.getSimFromEsn(esnUtil.getCurrentESN().getEsn());
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it17/", newSim);
		}		
		if(oldPart.equalsIgnoreCase("External") && myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it15/", zip);
		}
		pressButtonIfVisible("Continue");
		
		pressButtonIfVisible("Security verified");
	}
	
	public void pressButtonIfVisible(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else if(myAccountFlow.buttonVisible(buttonType)){
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void performOptionWithPlanAndWorkforcePIN(String option, String pinPart, String workforcePIN)
			throws Exception {
		if(option.equalsIgnoreCase("Add Airtime")){
			addAirtime(pinPart);
		}else if(option.contains("AWOP")){
			performAwop(option, pinPart);
		}else if(option.contains("Workforce")){
			getWorkforcePins(pinPart, workforcePIN);
		}else if(option.contains("Purchase")){
			purchasePlan(pinPart);
		}
	}
	
	public void performAwop(String option, String pinPart)
			throws Exception {
		pressButton("AWOP");
		if (option.equalsIgnoreCase("AWOP-Reference ESN")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:sor1:_0/");
			 String brand = esnUtil.getCurrentBrand();
			  String refEsn=null;
			 if(brand.equalsIgnoreCase("SIMPLE_MOBILE")){
				 refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
					} else if(brand.equalsIgnoreCase("NET10")){
						refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
						}else if(brand.equalsIgnoreCase("TRACFONE")){
							refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
						}else if(brand.equalsIgnoreCase("TELCEL")){
							refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
						}else if(brand.equalsIgnoreCase("STRAIGHT_TALK")){
							refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
						}
			 logger.info("Reference ESN:"+refEsn);
			 phoneUtil.clearOTAforEsn(refEsn);
			 myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it7::content/", refEsn);
		     pressButton("Validate ESN");	
		     TwistUtils.setDelay(15);
		     if (myAccountFlow.submitButtonVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/")){
		    	 pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/");
		     }
		} else if (option.equalsIgnoreCase("AWOP-SupervisorApproval")) {			
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d(:r\\d:\\d)?:sor1:_2/");
			pressButton("Validate Plan");
		}

		if (myAccountFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d(:r\\d:\\d)?:soc4/").isVisible()) {
			myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc4/", "1");
		}

		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1::content/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1::content/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1::content/", "1");
		}
		
		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it2::content/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it2::content/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it2::content/", "1");
		}

		if (myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it11/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it11/")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it11/", "1");
		}
		
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc3/", "Port Request(Active with new number and now requesting a Port)");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it8/").setValue("ITQ-AWOP TEST");
		pressButton("Continue");
		//r2:1:r1:2:it21::content
		if(myAccountFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it21/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it21::content/", "ITQUSER");
			pressButton("Accept");
		}
		
		if (myAccountFlow.buttonVisible("Code Accepted") || myAccountFlow.submitButtonVisible("Code Accepted")) {
			pressButton("Code Accepted");
		}
		
	}
	
	public void getWorkforcePins(String pinPart, String workforcePIN) throws Exception {
		String workForcePinText=null;
		pressButton("Get Workforce PIN");
		TwistUtils.setDelay(4);
		myAccountFlow.clickCellMessage(workforcePIN);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "Port Request Cancellation (Requested a Port and is now requesting a new number)");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue("ITQ-WORKFORCE PIN-TEST");
		pressButton("Get Pin");
	}
	
	public void purchasePlan(String pinPart) throws Exception {
		ESN esn=esnUtil.getCurrentESN();
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
		}else{
			pressButton("Purchase Airtime");
		}
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) {
			TwistUtils.setDelay(2);
			pressButton("Refresh");
			myAccountFlow.clickCellMessage(pinPart);
			pressButton("Add");
			pressButton("Checkout");
		}
		
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")) {
			pressButton("Add New Credit Card");
		} else {
			//pressButton("Add New Payment");
		}
		
		enterCard("Visa");
	if(buttonVisible("Refresh Payment")){
		pressButton("Refresh Payment");
	}
		
		if (!(esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")  || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") )) {
			pressButton("Refresh");
			myAccountFlow.clickCellMessage(pinPart);
			
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
				pressButton("Add Plan");
			}
			else {
				pressButton("Add");
			}
			
		}
	
		
		if (buttonVisible("Refresh Credit Cards")) {
			pressButton("Refresh Credit Cards");
		} else if (buttonVisible("Refresh Credit Card")) {
			pressButton("Refresh Credit Card");
		} else if(buttonVisible("Refresh Payment")) {
			pressButton("Refresh Payment");
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			myAccountFlow.getBrowser().select("/r2:\\d:r\\d:\\d:soc3::content/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
			myAccountFlow.typeInTextField("/r2:\\d:r\\d:\\d:it4::content/", "123");
		}else{
			myAccountFlow.getBrowser().select("/r2:\\d:r\\d:\\d:soc1/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r2:\\d:r\\d:\\d:it1/", "123");
		}
		
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")  || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) {
			pressButton("Ready to Pay");
			
		}else {
			pressButton("Purchase");
		}

		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("TAS Buy a Plan");
		esn.setActionType(401);
	}
	
	private boolean buttonVisible(String button) {
		return myAccountFlow.buttonVisible(button) || myAccountFlow.submitButtonVisible(button);
	}
	
	public void addAirtimeCardIfNecessary(String pinPart) throws Exception {
		if((esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) && !pinPart.isEmpty()){
			String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
			pressButton("Validate");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "33178");
			}
			pressButton("Add Port Information");		
		}
	}
	
	public void addAirtimeCardIfNecessaryBasedOnBrand(String pinPart) throws Exception {
		/*if((esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) && !pinPart.isEmpty()){
			String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
			pressButton("Validate");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "33178");
			}
			pressButton("Add Port Information");		
		}else*/ if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  ||esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")  ){
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_2/");
			pressButton("Continue");
			pressButton("Queue Port");
			
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){
			pressButton("Transfer Number");
		}
	}
	
	public void addAirtime(String pinPart) throws Exception {
		
		ESN esn  = esnUtil.getCurrentESN();
		if((esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) && !pinPart.isEmpty()){
			//do nothing			
		}else
		if(!pinPart.isEmpty()){
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			pressButton("Add Airtime Card");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", pin);
			TwistUtils.setDelay(5);		
			logger.info("PIN:"+pin);
			if(getButtonType("OK")){
				pressButton("OK");
			}else if (getButtonType("OK[1]")){
				pressButton("OK[1]");
			}
			Assert.assertTrue("Error in validating PIN",(myAccountFlow.cellVisible("Valid PIN") || myAccountFlow.cellVisible("SUCCESS") ));
		} 
	}
	
	public void enterCard(String cardType) throws Exception {
		TwistUtils.setDelay(5);
		ESN esn= esnUtil.getCurrentESN();		
		String cardNumber = TwistUtils.generateCreditCard(cardType);
		String last4digits = cardNumber.substring(cardNumber.length()-4);
		esn.setLastFourDigits(last4digits);
		//Browser popup2 = myAccountFlow.getBrowser().popup("/.*/");
		/*TwistUtils.setDelay(5);
		if(popup2.checkbox("t1:0:sbc1::content").isVisible()){
			popup2.checkbox("t1:0:sbc1::content").click();
		}
		if(popup2.checkbox("t1:1:sbc1::content").isVisible()){
			popup2.checkbox("t1:1:sbc1::content").click();
		}
		if(popup2.checkbox("t1:2:sbc1::content").isVisible()){
			popup2.checkbox("t1:2:sbc1::content").click();
		}
		TwistUtils.setDelay(2);
		if (popup2.button("Continue").isVisible()) {
			popup2.button("Continue").click();
		} else if (popup2.submit("Continue").isVisible()){
			popup2.submit("Continue").click();
		}
		TwistUtils.setDelay(5);*/
		
		serviceUtil.addPaymentSourceToAccountforTAS(cardType,phoneUtil.getEsnAttribute("web_user_objid", esnUtil.getCurrentESN().getEsn()));
		TwistUtils.setDelay(2);
		/*if (popup2.button("Close").isVisible()) {
			popup2.button("Close").click();
		} else {
			popup2.submit("Close").click();
		}*/
		
		/*popup2.textbox("account_number::content").setValue(cardNumber);
		TwistUtils.setDelay(2);
		popup2.select("soc2").choose("07");
		popup2.select("soc3").choose("2021");
		popup2.textbox("it7").setValue("TwistFirstName");
		popup2.textbox("it8").setValue("TwistLastName");
		popup2.select("soc4").choose("USA");
		TwistUtils.setDelay(2);
		popup2.textbox("it5").setValue("1295 Charleston Road");
		popup2.textbox("it4").setValue("94043");
		popup2.textbox("it9").setValue("2345678900");
		TwistUtils.setDelay(2);
		popup2.textbox("it9").setValue("2345678900");
		if (popup2.button("Register Payment").isVisible()) {
			popup2.button("Register Payment").click();
		} else {
			popup2.submit("Register Payment").click();
		}*/
		TwistUtils.setDelay(3);
	}
	
	public void goToPortInformationErrorFor(String Brand) throws Exception {
		myAccountFlow.navigateTo(props.getString(Brand+"PortUrl"));
		if(Brand.equalsIgnoreCase("TRACFONE")||Brand.equalsIgnoreCase("NET10")||Brand.equalsIgnoreCase("TELCEL")){
			Assert.assertTrue(myAccountFlow.getBrowser().paragraph("lead").text().contains("Please enter the information below:"));
			myAccountFlow.typeInTextField("esn",esnUtil.getCurrentESN().getEsn());
			myAccountFlow.typeInTextField("ticketid",esnUtil.getCurrentESN().getFromMap("TicketID"));
		}else if (Brand.equalsIgnoreCase("STRAIGHT_TALK")||Brand.equalsIgnoreCase("SIMPLE_MOBILE")||Brand.equalsIgnoreCase("TOTAL_WIRELESS")){
			Assert.assertTrue(myAccountFlow.getBrowser().paragraph("Please enter the information below:").isVisible());
			myAccountFlow.typeInTextField("esn",esnUtil.getCurrentESN().getEsn());
			myAccountFlow.typeInTextField("ticketno",esnUtil.getCurrentESN().getFromMap("TicketID"));
		}
		pressButton("NEXT");
		
	}

	public void updatePortDetailsForFrom(String Brand,String oldBrand) throws Exception {
		if(Brand.equalsIgnoreCase("TRACFONE")||Brand.equalsIgnoreCase("NET10")||Brand.equalsIgnoreCase("TELCEL")){
			Assert.assertTrue(myAccountFlow.h1Visible("TRANSFER INFORMATION"));
			myAccountFlow.chooseFromSelect("current_provider",oldBrand);
			myAccountFlow.typeInTextField("input_account_number","123456789");
			myAccountFlow.typeInPasswordField("input_account_password","123456");
			myAccountFlow.typeInTextField("__first_name","TwistFirstnamePort");
			myAccountFlow.typeInTextField("__last_name","TwistlastNamePort");
			myAccountFlow.typeInTextField("__address1","9700 NW 112TH AVE");
			myAccountFlow.typeInTextField("__address2","Address2");
			myAccountFlow.typeInTextField("__city", "Miami");
			myAccountFlow.chooseFromSelect("__state","FL");
			myAccountFlow.typeInTextField("__zip_code","33178");
			if (myAccountFlow.textboxVisible("input_ssn")){
				myAccountFlow.typeInTextField("input_ssn","1234");
			}
			pressButton("SAVE");
			if( myAccountFlow.textboxVisible("userParsedHouseNumber")){  
				myAccountFlow.typeInTextField("userParsedHouseNumber","9700 NW 112TH AVE");
				myAccountFlow.chooseFromSelect("userParsedDirection","NE");
				myAccountFlow.typeInTextField("userParsedStreetName","Miami");
				myAccountFlow.chooseFromSelect("userParsedStreetType","BES");
				pressButton("user_entered_continue_button");
			   }
			Assert.assertTrue(myAccountFlow.getBrowser().paragraph("lead").text().contains("THANK YOU FOR USING OUR SERVICE!"));
			
		}else if (Brand.equalsIgnoreCase("STRAIGHT_TALK")||Brand.equalsIgnoreCase("SIMPLE_MOBILE")){
			Assert.assertTrue(myAccountFlow.getBrowser().paragraph("TRANSFER INFORMATION").isVisible());
			//update invalid fields
			myAccountFlow.chooseFromSelect("/(port_)?isp/",oldBrand);
			TwistUtils.setDelay(5);
			myAccountFlow.typeInTextField("/(port_)?ian/","123456789");
			myAccountFlow.typeInPasswordField("/(port_)?accpwd/","123456");
			myAccountFlow.typeInTextField("/(port_)?txtfirstname/","TwistFirstnamePort");
			myAccountFlow.typeInTextField("/(port_)?txtlastname/","TwistlastNamePort");
		//	myAccountFlow.typeInTextField("/(port_)?accpwd/","1234");
			myAccountFlow.typeInPasswordField("/(port_)?accpwd/","1234");
			myAccountFlow.typeInTextField("/(port_)?txtaddress1/","9700 NW 112TH AVE");
			myAccountFlow.typeInTextField("/(port_)?txtaddress2/","Address2");
			//city is missing
			myAccountFlow.chooseFromSelect("/(port_)?state/","FL");
			myAccountFlow.typeInTextField("/(port_)?zipcode/","33178");
			pressButton("SAVE");
			Assert.assertTrue(myAccountFlow.strongVisible("THANK YOU FOR USING OUR SERVICE!"));
		}
		else if (Brand.equalsIgnoreCase("TOTAL_WIRELESS")){
			Assert.assertTrue(myAccountFlow.getBrowser().paragraph("TRANSFER INFORMATION").isVisible());
			//update invalid fields
			myAccountFlow.chooseFromSelect("port_isp",oldBrand);
			myAccountFlow.typeInTextField("port_ian","123456789");
			myAccountFlow.typeInPasswordField("port_accpwd","123456");
			myAccountFlow.typeInTextField("port_txtfirstname","TwistFirstnamePort");
			myAccountFlow.typeInTextField("port_txtlastname","TwistlastNamePort");
			//myAccountFlow.typeInTextField("last4ssn","1234");
			myAccountFlow.typeInTextField("port_txtaddress1","9700 NW 112TH AVE");
			myAccountFlow.typeInTextField("port_txtaddress2","Address2");
			//city is missing
			myAccountFlow.chooseFromSelect("port_state","FL");
			myAccountFlow.typeInTextField("port_zipcode","33178");
			pressButton("SAVE");
			Assert.assertTrue(myAccountFlow.strongVisible("THANK YOU FOR USING OUR SERVICE!"));
		}
	}

	public void enterPINFor(String pin, String Brand) throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){
			myAccountFlow.clickLink("Transactions");
			myAccountFlow.clickLink("Activation");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/",phoneUtil.getNewPinByPartNumber(pin));
			pressButton("Validate");
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/","33178");
			pressButton("Add Port Information");
		}
	}

	public void updatePortCaseAndCompletePort() throws Exception {
		String[] brands = {"TOTAL_WIRELESS","GO_SMART"};
		
		if(!Arrays.asList(brands).contains(esnUtil.getCurrentBrand().toUpperCase())){
			System.out.println("entered complete port for "+esnUtil.getCurrentBrand());
			TwistUtils.setDelay(20);
			pressButton("Contact Profile");
		//	myAccountFlow.clickLink("History");
			myAccountFlow.clickLink("Ticket History");
			myAccountFlow.clickLink(phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn()));
			myAccountFlow.clickLink("Ticket Detail");
			TwistUtils.setDelay(5);
			phoneUtil.setMinForPortTicket(esnUtil.getCurrentESN().getMin(), phoneUtil.getRecentTicketbyESN(esnUtil.getCurrentESN().getEsn()));
			
			myAccountFlow.clickLink("Complete Port");
			pressButton("Complete Port");
			myAccountFlow.h2Visible("Transaction Summary");
			
			phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
		}			
	}
	
	public void completePortAfterServiceRequest() throws Exception {
		
		myAccountFlow.typeInTextField("it1::content",esnUtil.getCurrentESN().getEsn());
		myAccountFlow.clickImage("cil1::icon");
		TwistUtils.setDelay(10);
		myAccountFlow.pressSubmitButton("Yank");
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("Port In / Out");
		TwistUtils.setDelay(4);
		myAccountFlow.chooseFromSelect("r2:0:r1:0:soc4::content", "Port Successful");
		TwistUtils.setDelay(5);
		myAccountFlow.pressSubmitButton("Update Port Status");
		TwistUtils.setDelay(4);
		myAccountFlow.pressButton("d1::msgDlg::cancel");
		TwistUtils.setDelay(2);
		myAccountFlow.clickLink("Complete Port");
		TwistUtils.setDelay(3);
		myAccountFlow.pressSubmitButton("Complete Port");
		TwistUtils.setDelay(5);
		Assert.assertTrue(myAccountFlow.divVisible("Transaction completed successfully."));
		
	}

	public void completePortWithoutCbo() throws Exception {
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){	
					pressButton("Save");
					if(buttonVisible("Continue")){
						pressButton("Continue");
					}					
					if(myAccountFlow.linkVisible("/Validate and Add/")){
						myAccountFlow.clickLink("/Validate and Add/");
					}					
				//pressButtonIfVisible("Continue Purchase");
				if (myAccountFlow.submitButtonVisible("Process Transaction")) { //for SM
					myAccountFlow.pressSubmitButton("Process Transaction");
				} else if (myAccountFlow.buttonVisible("Process Transaction")){
					myAccountFlow.pressButton("Process Transaction");
				}
			}else {
				if(buttonVisible(props.getString("TAS.process"))){
					pressButton(props.getString("TAS.process"));
				}
				
				if(myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").isVisible()){
					error_msg= myAccountFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:psl\\d::t/").getText();
				}
				TwistUtils.setDelay(10);
				Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg,myAccountFlow.cellVisible("Transaction Summary"));
			//	Assert.assertTrue("NO TICKET NUMBER FOUND IN TRANSACTION SUMMARY :"+esnUtil.getCurrentESN().getEsn(),myAccountFlow.labelVisible("/.*Ticket Number.*/"));
				esnUtil.getCurrentESN().setTransType("TAS PORT IN["+esnUtil.getCurrentBrand()+"]");
				//keeping to & from same esn numbers since ext. port doesn't contain any esn 
				phoneUtil.checkUpgrade(esnUtil.getCurrentESN(),esnUtil.getCurrentESN());
			}
		
	}

	public void addAnotherLinePartWithSimForPortWithMINFromWithPartZip(String partnumber, String simPartNumber ,String minnumber,
			String porttype, String zip) throws Exception {
		
		if (buttonVisible("Add Additional Line")) {
			pressButton("Add Additional Line");
		}	
		
		String new_sim ="";
		ESN childEsn= new ESN(partnumber, phoneUtil.getNewEsnByPartNumber(partnumber));
		if (!simPartNumber.isEmpty()) {
			new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber);
			phoneUtil.addSimToEsn(new_sim, childEsn);
			TwistUtils.setDelay(5);
		}	
		
		if (buttonVisible("Add Additional Line")) {
			pressButton("Add Additional Line");
		}					
				
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", childEsn.getEsn());
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", new_sim);
		pressButton("Transfer Number");
		TwistUtils.setDelay(5);
		if (buttonVisible("New Line/ Reactivate")) {
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "1234");
			pressButton("Transfer Number");
		}
	
	}


	
}