package com.tracfone.twist.Activation;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.cbo.CboUtility;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.services.BHN;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ActivatePhone {

	private ActivationReactivationFlow activationPhoneFlow;
	//private RedemptionFlow redemptionFlow;
	private static PhoneUtil phoneUtil;
	private static CboUtility cboUtility;
	private  CboUtils cboUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private static final String NEW_STATUS = "New";
	private static final String ACTIVE_STATUS = "Active";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final String COMPLETED = "completed";
	private ResourceBundle rb1 = ResourceBundle.getBundle("TAS");
	private String error_msg;
	public static final String UPGRADE_MESSAGE = "Please verify / enter a new SIM OR select the create a case button to send the customer a new SIM.";
	public static final String UPGRADE_MESSAGE1 = "If customer already received a new SIM, please verify or enter the new SIM number. If the customer does not have a new SIM, select the create ticket button to send the customer a new SIM card";
	public 	Map<String, String> familyEsns;
	public List<ESN> familyEsnsList;
	public String childEsn;
	public String cellTechtype;
	public String memberType;
	public String memberValue;
	public String simPartNumber;
	public String simNumber;
	private MyAccountFlow myAccountFlow;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static Logger logger = LogManager.getLogger(ActivatePhone.class.getName());
	@Autowired
	private TwistScenarioDataStore scenarioStore;
	
	public ActivatePhone() {

	}
	
	public synchronized void enterEsnForPartSim(String status, String partNumber, String simPart) 
			throws Exception {
		activationPhoneFlow.clickLink("Incoming Call");
		simPartNumber = simPart; 
		ESN esn;

		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.matches("PH(SM|ST|TC|NT|TF|GS|WF).*")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if ("byop".equalsIgnoreCase(partNumber)) {
				esn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
			} else {
				esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
						sim = simUtil.getNewSimCardByPartNumber(simPart);
						esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
					}
					phoneUtil.addSimToEsn(sim, esn);
				}
			}
			
			/* NT BOGO CR4
			
			if (partNumber.matches("PHNT(64|128)PSIM(C4|T5).*")) {
				String pin = phoneUtil.getNewPinByPartNumber("NTAPP6U040FREE");
				phoneUtil.addPinToQueue(esn.getEsn(), pin);
			}
			
			*/
			pressButton("New Contact Account");
		} else if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
			esn = // esnUtil.popRecentActiveEsn(partNumber);//
			new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));
			phoneUtil.clearOTAforEsn(esn.getEsn());
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esn.getEsn());
			pressButton("Search Service");
			// Continue to profile page.
			checkForTransferButton();
		}
		// else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
		// esn = esnUtil.popRecentPastDueEsn(partNumber);
		// }
		else {
			throw new IllegalArgumentException("Phone Status not found");
		}
		try {		
			if (!esnUtil.getCurrentESN().getPin().equalsIgnoreCase("")) {
				String pin = esnUtil.getCurrentESN().getPin();
				esnUtil.setCurrentESN(esn);
				esnUtil.getCurrentESN().setPin(pin);
			} else {
				esnUtil.setCurrentESN(esn);
			}
		} catch(Exception e){
			esnUtil.setCurrentESN(esn);
		}		
	}
	
    public void goToUpgradeOption() throws Exception {
    	activationPhoneFlow.clickCellMessage("Transactions");
    	activationPhoneFlow.clickLink("Upgrade");
    	
    	//Identity Challenge 
    	if (activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1/")){
    		activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1/");
    		if (activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1/")){
    			activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1/");
    		}
    		pressButton("Continue");
    	}
    		
		TwistUtils.setDelay(5);
		activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
    }

    public void completePortingForDifferentCarrierWithPart(String partnum)
			throws Exception {
		pressButton("Refresh");
		pressButton("Contact Profile");
		System.out.println(esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.clickLink(esnUtil.getCurrentESN().getEsn());
		//phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.clickLink("History");
		activationPhoneFlow.clickLink("Ticket History");
		if (activationPhoneFlow.linkVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot2/")){
			activationPhoneFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot2/");
			completeport();
		}
	}

    public void completeport()throws Exception {
    	if (activationPhoneFlow.buttonVisible("Yank")||activationPhoneFlow.submitButtonVisible("Yank")){
        	pressButton("Yank");
        }else{
            pressButton("Accept");
            pressButton("Accept[1]");
        }
    	activationPhoneFlow.clickLink("Complete Port");
		pressButton("Complete Port");
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.divVisible("Transaction completed successfully."));
		pressButton("Refresh");
		TwistUtils.setDelay(10);
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
	}

	
	public synchronized void enterEsn(String status, String esnactive) throws Exception {  //New
		activationPhoneFlow.clickLink("Incoming Call");
		 
		ESN esn = null;
		if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
            
            esn = new ESN("TFALA845G3P4", esnactive);
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esn.getEsn());
		pressButton("Search Service");
		//if (activationPhoneFlow.buttonVisible("r2:1:commandButton1")) {
		pressButton("r2:1:commandButton1");
		
		esnUtil.setCurrentESN(esn);
	}
	
	
	public synchronized void enterEsnForPartZipCodeSimBrandWithNoAccount(String status, String partNumber, String zipCode, String partClass, String brand) throws Exception {
		ESN esn;
		activationPhoneFlow.clickLink("Incoming Call");
		if(ACTIVE_STATUS.equalsIgnoreCase(status)){
			esn = new ESN(partNumber, phoneUtil.getActiveEsnByPartWithNoAccount(partNumber));
			esnUtil.setCurrentBrand(brand);
			esnUtil.setCurrentESN(esn);
			phoneUtil.clearOTAforEsn(esn.getEsn());
		}
		pressButton("New Contact Account");
	}
	
	public void joinSimCard(String simCard, ESN esn) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			esn.setSim(newSim);
			phoneUtil.addSimToEsn(newSim, esn);
		}
	}

	public synchronized void activatePhoneByUsingPinDependingOnStatusOfCellTechZip(String pinPart, String status, String cellTech, String zip) throws Exception {
		//Continue to profile page.
		this.cellTechtype = cellTech;
		checkForTransferButton();
		String newPin;
		if ("NO PIN".equalsIgnoreCase(pinPart)) {
			newPin = pinPart;
		} else if("bhn".equalsIgnoreCase(pinPart)){
			System.out.println(esnUtil.getCurrentESN().getPin());
			newPin =esnUtil.getCurrentESN().getPin();
		}else{
			 newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		}
		
		if (zip.equals("692")) {
			zip = "00" + zip;
		}
		if(simUtil.getSimPartFromSimNumber(esnUtil.getCurrentESN().getSim()).contains("CL7")){
			zip="00692";
		}
		
		esnUtil.getCurrentESN().setPin(newPin);
		esnUtil.getCurrentESN().setZipCode(zip);
		activateWithNewPin(newPin, status, cellTech, zip);
	}

	private synchronized void activateWithNewPin(String newPin, String status, String cellTech, String zip) throws Exception {
		
		String brand = esnUtil.getCurrentBrand();
		ESN esn  = esnUtil.getCurrentESN();
		String partNumber= esn.getPartNumber();
		
		if(!partNumber.matches("BYO.*")){
			activationPhoneFlow.clickLink("Transactions");
			if(brand.equalsIgnoreCase("wfm") || brand.equalsIgnoreCase("simple_mobile"))
				activationPhoneFlow.clickLink("Activation/ Port");
			else
				activationPhoneFlow.clickLink("Activation");
		}
		if(brand.equalsIgnoreCase("total_wireless")  || brand.equalsIgnoreCase("GO_SMART")){
			esn.setZipCode(zip);
			if(!"reactivation".equalsIgnoreCase(status)){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
				if(newPin.equalsIgnoreCase("reserved")){
					pressButton("Reserved Pins");
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", esnUtil.getCurrentESN().getEsn());
					pressButton("Search");
					String queuePin = activationPhoneFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot5/").getText();
					activationPhoneFlow.clickLink(queuePin);
				}else{
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
				}
				pressButton("Validate");
				TwistUtils.setDelay(5);
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
				if(!zip.equalsIgnoreCase("") ){
					if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", zip);
					}
				}
				/*
				if (partNumber.equalsIgnoreCase("byop")
						|| partNumber.equalsIgnoreCase("byophex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_0/");
				} else if (partNumber.equalsIgnoreCase("byod")
						|| partNumber.equalsIgnoreCase("byodhex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_1/");
				}
				*/
				pressButton("Continue");
			} else{
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_2/");
				pressButton("Continue");
				if(activationPhoneFlow.textboxVisible("r2:1:r1:2:it16:0:it10::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:2:it16:0:it10::content",esn.getZipCode());
				}else if (activationPhoneFlow.textboxVisible("r2:1:r1:4:it16:0:it10::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:4:it16:0:it10::content",esn.getZipCode());
				}
				pressButton("Queue Activation");
				if(activationPhoneFlow.textboxVisible("r2:1:r1:2:it16:0:it15::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:2:it16:0:it15::content",esn.getSim());
				} else if(activationPhoneFlow.textboxVisible("r2:1:r1:4:it16:0:it15::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:4:it16:0:it15::content",esn.getSim());
				}
				pressButton("Queue Activation");
				activationPhoneFlow.clickLink("Validate and Add");
			}
						
		} else {
			if(brand.equalsIgnoreCase("wfm") ||brand.equalsIgnoreCase("simple_mobile") ){
				if(activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").getText().equalsIgnoreCase("")){

					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", esnUtil.getCurrentESN().getEsn());
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", esnUtil.getCurrentESN().getSim());
					activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
					TwistUtils.setDelay(5);
					if(buttonVisible("New Line/ Reactivate")){
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "1234");
						activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
															 
					}
					
					
				}else{
					activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
				}
				
			}
//		 else{
			//Check for SIM null
		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/") && activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").getValue().equalsIgnoreCase("")){
			String new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/",new_sim);
			esn.setSim(new_sim);
		}
		if((!"reactivation".equalsIgnoreCase(status)) && !(brand.equalsIgnoreCase("total_wireless") || brand.equalsIgnoreCase("GO_SMART"))){
			
			if( simPartNumber != null && simPartNumber.contains("CL7") && zip.equalsIgnoreCase("33178") ) {
				// CLARO
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/","00692");
			} else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
			}
		}
		logger.info("PIN:" + newPin);
		esnUtil.getCurrentESN().setPin(newPin);
		if("NO PIN".equalsIgnoreCase(newPin)){
			
		}else{
		if(newPin.equalsIgnoreCase("reserved")){
			pressButton("Reserved Pins");
			String queuePin = activationPhoneFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot5/").getText();
			activationPhoneFlow.clickLink(queuePin);
		}else{
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
		}
		pressButton("Validate Card");
		TwistUtils.setDelay(10);
		String error_msg1=activationPhoneFlow.getBrowser().div("d1::msgDlg::_cnt").getText() ;
		if (activationPhoneFlow.linkVisible("Close[1]")) {
			activationPhoneFlow.clickLink("Close[1]");
		}
		String error_msg2 = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg2+":"+error_msg1, activationPhoneFlow.cellVisible("Valid PIN"));
		}
		pressButton("Activate");
		TwistUtils.setDelay(10);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()){
		error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
		} 
		if(activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").isVisible()){
			error_msg = error_msg + activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").getText();
		}
		if(activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")){
			pressButton("Code Accepted");
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.h2Visible("Transaction Summary"));
	    finishPhoneActivation(cellTech, status);
		esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "TAS Activation with PIN ["+esnUtil.getCurrentBrand()+"]");
		pressButton("Refresh");
//		Assert.assertFalse("Please check for T number for ESN: " + esnUtil.getCurrentESN().getEsn(),
//				activationPhoneFlow.getBrowser().span("/r2:\\d:ot26/").getText().startsWith("T"));
		}
	}

	
	private void finishPhoneActivationForTotalWirelss(ESN childESN,String cellTech, String status) throws Exception {
//		TwistUtils.setDelay(13);
		esnUtil.addRecentActiveEsn(childESN, cellTech, status, "TAS Activation["+esnUtil.getCurrentBrand()+"]");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(childESN.getEsn());
	}

	public void delay() throws Exception {
		TwistUtils.setDelay(5);
	}

	public void enterZip(String zip) throws Exception {
		if(zip.equals("692"))
			zip="00"+zip;
			
		activationPhoneFlow.clickLink("Transactions");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){
			activationPhoneFlow.clickLink("Activation/ Port");
			pressButton("New Line/ Reactivate");
			activationPhoneFlow.clickLink("Purchase Airtime");
			TwistUtils.setDelay(3);
			}
		else{
			activationPhoneFlow.clickLink("Activation");}
		
		//Check for SIM null
		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/") && activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").getValue().equalsIgnoreCase("")){
			String new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/",new_sim);
			esnUtil.getCurrentESN().setSim(new_sim);
		}
		String partNumber= esnUtil.getCurrentESN().getPartNumber();
		checkForTransferButton();
		esnUtil.getCurrentESN().setZipCode(zip);
		if(!partNumber.matches("BYO.*") && !( esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")|| esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile"))){
		activationPhoneFlow.clickLink("Transactions");
		activationPhoneFlow.clickLink("Activation");
		}
		if( (!esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  && !esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart"))){
		//	activationPhoneFlow.clickLink("Purchase Airtime");
			activationPhoneFlow.typeInTextField("/r2:\\d:r\\d:\\d:it2/", zip);
			}
	}

	public void checkActivationForCellTechAndStatus(String cellTech, String status) throws Exception {
		TwistUtils.setDelay(4);
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			String partNumber = esnUtil.getCurrentESN().getPartNumber();
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
			
			if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", esnUtil.getCurrentESN().getZipCode());
			}
			
			if(partNumber.equalsIgnoreCase("byop") || partNumber.equalsIgnoreCase("byophex")){
				 activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_0/");
			 }else if(partNumber.equalsIgnoreCase("byod") || partNumber.equalsIgnoreCase("byodhex")){
				 activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_1/");
			 }
			 
			pressButton("Continue Purchase");			
		}else if(! (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") )){
		if (activationPhoneFlow.buttonVisible("Activate") || activationPhoneFlow.submitButtonVisible("Activate")) {
			pressButton("Activate");
		} else if (activationPhoneFlow.buttonVisible("Ready to Pay") || activationPhoneFlow.submitButtonVisible("Ready to Pay")) {
			pressButton("Ready to Pay");
		} else{
			pressButton("Process (min)");
		}
		TwistUtils.setDelay(40);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").isVisible()){
			error_msg= activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").getText();
		}
		if(activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")){
			pressButton("Code Accepted");
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.cellVisible("Transaction Summary"));
		finishPhoneActivation(cellTech, status);
		pressButton("Refresh");
		}
	}

	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}

	public void addPartNumbersToAccountWithSIMCellTech(Integer noOfMembers, String status, String partNumber, String simPart, String cellTech)
			throws Exception {
		ESN mainEsn = esnUtil.getCurrentESN();
		
		for (int i=1; i<=noOfMembers; i++) {
			if(NEW_STATUS.equalsIgnoreCase(status)) {
				ESN esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				mainEsn.addFamilyEsn(esn);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", esn.getEsn());
				if(!simPart.isEmpty()){
					String newSim = simUtil.getNewSimCardByPartNumber(simPart);
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", newSim);
					//phoneUtil.addSimToEsn(newSim, esn);
				}
			} else if(ACTIVE_STATUS.equalsIgnoreCase(status)) {
				ESN activeEsn = new ESN(partNumber, phoneUtil.getActiveEsnByPartWithNoAccount(partNumber));
				phoneUtil.clearOTAforEsn(activeEsn.getEsn());
				mainEsn.addFamilyEsn(activeEsn);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", activeEsn.getEsn());
			} else {
				throw new IllegalArgumentException("Invalid status: " + status);
			}
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", "33178");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", "TwistNickName"+i);
			pressButton("Add");
			Assert.assertFalse("ESN not added.Error message :null",activationPhoneFlow.cellVisible("-1:null"));
			pressButton("Clear");
		}
//		String plansAvailable = activationPhoneFlow.getBrowser().div("((pt\\d:) | (pt\\d:r\\d:\\d:))?1:r1:1:t2::db").text();
//		Assert.assertTrue("ESN's are not adding to list/Check ADD tool", !plansAvailable.equalsIgnoreCase("No data to display."));
	}

	public void selectPlan(String planType) throws Exception {
		if((esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) && activationPhoneFlow.radioVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/")){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
				activationPhoneFlow.clickCellMessage(planType);
		}else if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") ){
			// ("WFMAPP0010D".equalsIgnoreCase(planType)) || ("WFMAPP0010EP".equalsIgnoreCase(planType)) 
			if(activationPhoneFlow.linkVisible("Purchase Airtime")){
				activationPhoneFlow.clickLink("Purchase Airtime");
				pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:t\\d:\\d:cb7/");
				activationPhoneFlow.clickCellMessage(planType);
				if(buttonVisible("Add")){
						pressButton("Add");
			}else {//pressButton("+");
			
			}
				pressButton("Checkout");
			}else if("".equalsIgnoreCase(planType)){
			//	activationPhoneFlow.clickLink("Purchase Airtime");
			}else{
			//	activationPhoneFlow.clickLink("Purchase Airtime");
			}
			
		}else{
			if(activationPhoneFlow.buttonVisible("Refresh[1]") || activationPhoneFlow.submitButtonVisible("Refresh[1]")){
				pressButton("Refresh[1]");
			}
			if(activationPhoneFlow.linkVisible("Purchase Airtime")){
				activationPhoneFlow.clickLink("Purchase Airtime");
			}
			
			
				activationPhoneFlow.clickCellMessage(planType);
			
				if(buttonVisible("Add")){
					pressButton("Add");
		}else if(buttonVisible("+")){pressButton("+");
		
		}
				if(buttonVisible("ok")){
					pressButton("ok");
		}	
		}
		if (planType.equalsIgnoreCase("BUY NOW")) {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/","1234");
		}
		/*
		 * Temp fix for contact details missing after activation for NT,TF
		 * if(activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it13/").isVisible()){
		 * activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it13/", "TwistFirstname");
		 *  pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb15/");
		 *  }
		 */
	}
	
	public void checkActivationOfFamilyMembersForPartCellTechAndStatus(int noOfMembers, String partNumber, String cellTech, String status) throws Exception {
		finishPhoneActivation(cellTech, status);
		pressButton("Refresh");		
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
	}

	public void checkConfirmation() throws Exception {
		pressButton("Process");
		TwistUtils.setDelay(15);
		Assert.assertTrue( activationPhoneFlow.cellVisible("Transaction Summary (Family Plan)"));
		Assert.assertFalse("Problem with purchase services/purchases failing", activationPhoneFlow.cellVisible("BEA-380001"));
	}
	private boolean isSelectionEnabled(String selection) {
		if (activationPhoneFlow.getBrowser().select(selection).isVisible()) {
			boolean isDisabled = activationPhoneFlow.getBrowser().select(selection).fetch("disabled").equalsIgnoreCase("true");
			if (!isDisabled) {
				return true;
			}
		}
		return false;
	}
	public void selectAWOPWtihOptionReasonAndServiceDaysDataForZipCode(String option, String reason, String ServiceDays, String data, String zip) throws Exception {
		checkForTransferButton();
		ServiceDays="1";
		activationPhoneFlow.clickLink("Transactions");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM") ||esnUtil.getCurrentBrand().equalsIgnoreCase("SIMPLE_MOBILE")){
			activationPhoneFlow.clickLink("Activation/ Port");
			activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
		}else{
			activationPhoneFlow.clickLink("Activation");
		}
		
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_5/");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it21/", zip);
		}else{
			activationPhoneFlow.clickLink("AWOP");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
		}		
		pressButton("Refresh");
		if (option.equalsIgnoreCase("Reference Esn")) {
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_0/");
			 String refEsn	= phoneUtil.getActiveEsnByPartNumber(esnUtil.getCurrentESN().getPartNumber());
			 logger.info("Reference ESN:"+refEsn);
			 phoneUtil.clearOTAforEsn(refEsn);
			 activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it7::content/", refEsn);
		     pressButton("Validate ESN");
		     TwistUtils.setDelay(15);
		     
		     if(esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){		    	 
		    	 pressButton("Refresh[1]");			     
		     }else {
		    	 if (activationPhoneFlow.submitButtonVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/")){
			    	 pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/");
			     }
		     }
		     		     
		} else if (option.equalsIgnoreCase("Supervisor Approval")) {
			
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
			
			if (isSelectionEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/") && (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile"))) {
				activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc5::content/").choose("/.*?(UNL|TALK|TEXT|4G|DAY|YEAR).*?/i");
				String planAdding = activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc5::content/").selectedText().toString();
				logger.info("Plan adding to phone:" + planAdding);
			}
						
			pressButton("Validate Plan");
		}
		
		if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/")) {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/", ServiceDays);
		}
		
		if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1::content/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1::content/")) {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1::content/", "20");
		}
		
		if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/")) {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/", "20");
		}
		
		if (activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/").isVisible()) {
			activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", data);
		}
		
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3/", reason);
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it8/").setValue("ITQ-AWOP TEST");
		pressButton("Activate");
		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it21/")){
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it21/", "ITQUSER");
			pressButton("Accept");
		}
		
		if (activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")) {
			pressButton("Code Accepted");
		}
		
		TwistUtils.setDelay(5);
		assertTrue(activationPhoneFlow.h2Visible("Transaction Summary"));

	}
	
	private void checkForTransferButton() {
		if (getButtonType("Continue to Service Profile")) {
			pressButton("Continue to Service Profile");
		}		
	}

	public void completeActivationForStatusCellTech(String status, String cellTech)
			throws Exception {
		/*if(activationPhoneFlow.spanVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/")){
			error_msg= activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.cellVisible("Transaction Summary"));*/
		if (!esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
			TwistUtils.setDelay(30);	
		} else{
			TwistUtils.setDelay(30);
		}
		
		finishPhoneActivation(cellTech, status);
	//	pressButton("Refresh");
	}
	
	
	public void completeDeviceActivationForStatusCellTech(String status,String cellTech)
			throws Exception {
		
		finishPhoneActivation(cellTech, status);
	
	}
	
	public void activateDependsOnStatusPinAndCellTechZip(String status,String pinPart, String cellTech,String zip) throws Exception {
		if(ACTIVE_STATUS.equalsIgnoreCase(status)){
			activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart, status, cellTech, zip);
		}
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setCboUtility(CboUtility cboUtility) {
		this.cboUtility = cboUtility;
	}
	
	public void setCboUtil(CboUtils cboUtil) {
		this.cboUtil = cboUtil;
	}
	
	private boolean buttonVisible(String button) {
		return activationPhoneFlow.buttonVisible(button) || activationPhoneFlow.submitButtonVisible(button);
	}
	public void selectOptionPartNumberSimPinOfCellTechZipWithMinTransfer(String status, String partNumber, String simPart, String pinPart,
			String cellTech, String zip,String minTransfer) throws Exception {
		String minTransfer_button;
		//Browser popup2 = redemptionFlow.getBrowser().popup("/.*/");
		String brand = esnUtil.getCurrentBrand();
		if(minTransfer.equalsIgnoreCase("Yes")){
			minTransfer_button = "Process Transaction (min)";
			//activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
			
			if(activationPhoneFlow.checkboxVisible("r2:1:r1:1:t1:0:sbc1::content")){  //New
				activationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:0:sbc1::content");
			
			
			//(activationPhoneFlow.checkboxVisible(check)("r2:1:r1:1:t1:0:sbc1::content");  New
			//activationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:1:sbc1::content");
		}
			if(activationPhoneFlow.checkboxVisible("r2:1:r1:1:t1:1:sbc1::content")){
				activationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:1:sbc1::content");
			}  //new
			
			if(activationPhoneFlow.checkboxVisible("r2:2:r1:1:t1:0:sbc1::content")){
				activationPhoneFlow.selectCheckBox("r2:2:r1:1:t1:0:sbc1::content");
			}
			
			pressButton("Continue");
		}
			else {
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			minTransfer_button = "Transfer Service (no min)";
		}
		ESN toEsn = null;
		ESN fromEsn = esnUtil.getCurrentESN();
		esnUtil.getCurrentESN().setFromEsn(fromEsn);
	
		if (status.equalsIgnoreCase("Active")) {
			pressButton("Contact Profile");
			pressButton("Add ESN to Account");
		   toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			esnUtil.setCurrentESN(toEsn);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
			pressButton("Add");
			Assert.assertTrue("ESN not added to account", activationPhoneFlow.cellVisible("ESN added to account, Successfully"));
			activationPhoneFlow.clickLink(toEsn.getEsn());
			checkForTransferButton();
			activationPhoneFlow.clickLink("Transactions");
			if(brand.equalsIgnoreCase("wfm") || brand.equalsIgnoreCase("simple_mobile"))
				activationPhoneFlow.clickLink("Activation/ Port");
			else{
				activationPhoneFlow.clickLink("Activation");}
			if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					phoneUtil.addSimToEsn(sim, toEsn);
					pressButton("Refresh");
				}
				activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink("Account Summary");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
			} else {
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
				fromEsn = new ESN(partNumber,
						phoneUtil.getActiveEsnByPartNumber(partNumber));
				phoneUtil.clearOTAforEsn(fromEsn.getEsn());
			//  esnUtil.setCurrentESN();
			}
			checkForTransferButton();
			activationPhoneFlow.clickLink("Upgrade");
			if(minTransfer.equalsIgnoreCase("Yes")){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
			}else {
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			}
			if (activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/").isVisible()) {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
				pressButton("Continue");
			}

		} else if(status.equalsIgnoreCase("PastDue")){
			pressButton("Contact Profile");
			 toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
				esnUtil.setCurrentESN(toEsn);
				
				if(esnUtil.getCurrentBrand().equalsIgnoreCase("TOTAL_WIRELESS") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
					pressButton("Add Group Member");
					activationPhoneFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it2::content/", toEsn.getEsn());
					  activationPhoneFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it3::content/", "ITQ_ADD_MEMBER_TEST");

			}else{
					pressButton("Add ESN to Account");
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
					
				}
				
			pressButton("Add");
			Assert.assertTrue("ESN not added to account", (activationPhoneFlow.cellVisible("ESN added to account, Successfully") || activationPhoneFlow.cellVisible("ESN added to group")));
			activationPhoneFlow.clickLink(toEsn.getEsn());
			checkForTransferButton();
			activationPhoneFlow.clickLink("Transactions");
			if(brand.equalsIgnoreCase("wfm") || brand.equalsIgnoreCase("simple_mobile"))
				activationPhoneFlow.clickLink("Activation/ Port");
			else{
				activationPhoneFlow.clickLink("Activation");}
			if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					phoneUtil.addSimToEsn(sim, toEsn);
					pressButton("Refresh");
				}
				activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
				pressButton("Refresh");
				activationPhoneFlow.clickLink("Deactivation");
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "/.*PASTDUE.*/");
				pressButton("Deactivate");
				Assert.assertTrue(activationPhoneFlow.h2Visible("Transaction Summary")||activationPhoneFlow.divVisible("Your phone has been deactivated."));
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
			
			} else {
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
				fromEsn = new ESN(partNumber,phoneUtil.getActiveEsnByPartNumber(partNumber));
				phoneUtil.clearOTAforEsn(fromEsn.getEsn());
			//  esnUtil.setCurrentESN();
			}
			activationPhoneFlow.clickLink("Upgrade");
			if(minTransfer.equalsIgnoreCase("Yes")){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
			}else {
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			}
			if (activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/").isVisible()) {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
				pressButton("Continue");
				
		}}
			else if(status.equalsIgnoreCase("New")){
			toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			if(partNumber.equalsIgnoreCase("NTLGL41CWHP")){
				String pin = phoneUtil.getNewPinByPartNumber("NTAPP6U040FREE");
				phoneUtil.addPinToQueue(toEsn.getEsn(), pin);
			}
			logger.info("TO ESN (NEW):" + toEsn.getEsn());
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
			if (partNumber.matches("PH(SM|ST|TC|NT|TF).*")) {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toEsn.getSim());
			}
			pressButton("Continue");
			if(activationPhoneFlow.cellVisible("/.*SIM SWAP.*/") && simPart.equalsIgnoreCase("SWAP")){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
				logger.info("RUNNING SIM SWAP");
				pressButton("Continue");
			}else if(activationPhoneFlow.cellVisible("/.*SIM SWAP.*/") && !simPart.equalsIgnoreCase("SWAP") && !simPart.isEmpty()){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
				String toSim = simUtil.getNewSimCardByPartNumber(simPart);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toSim);
				pressButton("Continue");
			}else if(!simPart.isEmpty() ) {
				String toSim = simUtil.getNewSimCardByPartNumber(simPart);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toSim);
				pressButton("Continue");
			}
			
			if (activationPhoneFlow.cellVisible("Pin is Required.") ){
				String pin = phoneUtil.getNewPinByPartNumber(pinPart);
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", pin);
				pressButton("Continue");
				pressButton(minTransfer_button);

			}
			if(activationPhoneFlow.buttonVisible("Continue")){
				activationPhoneFlow.pressButton("Continue");
			}
			
			
		}
		if (checkCellMessage("In order to upgrade from an active TF SurePay to an active TF SurePay, " +"you must deactivate the TO Phone first") || 
				checkCellMessage("In order to upgrade from an active Non PPE to an active Non PPE, " +"you must deactivate the TO Phone first")   ||
				checkCellMessage("In order to upgrade to an active, you must deactivate the TO Phone first") ||
				 checkCellMessage("/.*customer agrees to deactivate the new phone.*/")) {
			pressButton("Deactivate");
			logger.info("Deactivation of TO PHONE  required for this flow FROM PART:"+fromEsn.getPartNumber()+";TO PART: "+toEsn.getPartNumber());
			Assert.assertTrue(activationPhoneFlow.cellVisible("TO Phone was successfully deactivated. You may proceed with the transaction"));
			pressButton("Continue");
		}
		
		if (activationPhoneFlow.cellVisible("Min Entered does not match our records. Please try again.") 
				|| activationPhoneFlow.cellVisible("Please enter MIN.")) {
			String toMin = phoneUtil.getMinOfActiveEsn(toEsn.getEsn());
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/", toMin);
			pressButton("Continue");
		}

		if (activationPhoneFlow.cellVisible("Pin is Required.")) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", pin);
			pressButton("Continue");

		}
		if (activationPhoneFlow.cellVisible(UPGRADE_MESSAGE) 
				|| activationPhoneFlow.cellVisible("The SIM card entered cannot be used for the upgrade. " + UPGRADE_MESSAGE) || checkCellMessage("/.*customer already received a new SIM, please verify or enter the new SIM number.*/")||
				 checkCellMessage("/.*DO NOT continue with the phone upgrade. Please, ask customer to buy a new SIM card.*/")) {
			String toNewSim = simUtil.getNewSimCardByPartNumber(simPart);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toNewSim);
			pressButton("Continue");
		}
		if (checkCellMessage("In order to upgrade from an active TF SurePay to an active TF SurePay, " +"you must deactivate the TO Phone first") || 
				checkCellMessage("In order to upgrade from an active Non PPE to an active Non PPE, " +"you must deactivate the TO Phone first")   ||
				checkCellMessage("In order to upgrade to an active, you must deactivate the TO Phone first") || checkCellMessage("/.*customer agrees to deactivate the new phone.*/")) {
			pressButton("Deactivate");
			logger.info("Deactivation of TO PHONE  required for this flow FROM PART:"+fromEsn.getPartNumber()+";TO PART: "+toEsn.getPartNumber());
			Assert.assertTrue(activationPhoneFlow.cellVisible("TO Phone was successfully deactivated. You may proceed with the transaction"));
			pressButton("Continue");
		}
		if (activationPhoneFlow.cellVisible("/*.Pin is Required.*/")) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", pin);
			pressButton("Continue");

		}
		
		if (activationPhoneFlow.buttonVisible(minTransfer_button)
				|| activationPhoneFlow.submitButtonVisible(minTransfer_button)) {
			pressButton(minTransfer_button);
		}
		
		if (activationPhoneFlow.cellVisible("Please provide the balance information.")) {
			if (getButtonType("Get Balance Inquiry")) {
				pressButton("Get Balance Inquiry");
			}else if (getButtonType("Balance Inquiry")) {
				pressButton("Balance Inquiry");
			}else if (getButtonType("Balance Inquiry[1]")) {
				pressButton("Balance Inquiry[1]");
			}
			/*pressButton("Manual Balance Entry");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "1");
			
			if (activationPhoneFlow.cellVisible("Sms")) 
			{
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18::content/", "1");
			}
			if (activationPhoneFlow.cellVisible("Data")) 
			{
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19::content/", "1");
			}
			pressButton("Submit");*/
			
			//simulate BI CBO call starts here
			cboUtility.simulateBalanceInquiry(fromEsn.getEsn());
			pressButton("Get Balance");
			cboUtility.simulateBalanceInquiry(fromEsn.getEsn());
			pressButton("Get Balance");
			//simulate BI CBO call ends here
			pressButton("Done");
			 pressButton(minTransfer_button);
		}
		/*
		if (activationPhoneFlow.cellVisible("Pin is Required.")) {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:1:r1:\\d:it26/", pin);
			pressButton("Process Transaction");
		}
		*/
		
		toEsn.setTransType("TAS Phone Upgrade["+esnUtil.getCurrentBrand()+"]");
		phoneUtil.checkUpgrade(esnUtil.getCurrentESN(), toEsn);
		scenarioStore.put("ESN0", esnUtil.getCurrentESN().getEsn());
		esnUtil.setCurrentESN(toEsn);
		}
	
		
	
	
	
	public void getWorkforcePinForReason(String wfPin,String reason) throws Exception {
		checkForTransferButton();
		String workForcePin;
		activationPhoneFlow.clickLink("ESN Support");
		activationPhoneFlow.clickLink("Workforce Pins");
		activationPhoneFlow.clickCellMessage(wfPin);
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", reason);
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", "123456");
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/").setValue("ITQ WORKFORCE PIN TEST A");
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it10/").setValue("ITQ WORKFORCE PIN TEST B");
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue("ITQ WORKFORCE PIN TEST C");
		pressButton("Get Pin");
		/*CR49706
		String pinText = activationPhoneFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl1/").getText();
		int i = pinText.lastIndexOf("Get Pin");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			workForcePin = pinText.substring(i-16, i).trim();
		}else if( esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")){
			workForcePin = pinText.substring(i-16, i).trim();
		}else{
			workForcePin = pinText.substring(i-16, i).trim();
			if(workForcePin.startsWith("n")){
				workForcePin = pinText.substring(i-14, i).trim();
			}
		}
		esnUtil.getCurrentESN().setPin(workForcePin);*/
	}

	public void activateWithPinDependingOnStatusOfCellTech(String status, String cellTech) throws Exception {
		String workForcePin = esnUtil.getCurrentESN().getPin();
		activateWithNewPin(workForcePin, status, cellTech, "33178");
	}

	public void addProtectionPlanIfApplicable(String planType) throws Exception {
		if (esnUtil.getCurrentESN().getZipCode().equalsIgnoreCase("80130"))	{
			// choose protection plan
			activationPhoneFlow.clickCellMessage("/."+planType+".*/");
			pressButton("Add[1]");
		}
	}
	
	public boolean getButtonType(String buttonName) {
		if (activationPhoneFlow.buttonVisible(buttonName) || activationPhoneFlow.submitButtonVisible(buttonName)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkCellMessage(String cellText) {
		if (activationPhoneFlow.cellVisible(cellText)) {
			return true;
		} else {
			return false;
		}}

	public void comeBackAsNewCustomer() throws Exception {
		checkForTransferButton();
		pressButton("New Customer");
		pressButton("New Contact Account");
	}
	

	public void comeBackAsNewCustomerForNewEsn() throws Exception {
		checkForTransferButton();
		pressButton("New Customer");
		
	}

	public void makePhoneToByUsingPinAndCelltech(String Status, String pinPart,
			String cellTech) throws Exception {
		//Continue to profile page.
		checkForTransferButton();
		if(Status.equalsIgnoreCase(ACTIVE_STATUS)){
			activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,"New",cellTech,"33178");
		}
		if(Status.equalsIgnoreCase("pastdue")){
			activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,"New",cellTech,"33178");
			pressButton("Refresh");
			activationPhoneFlow.clickLink("Deactivation");
			activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", Status);
			pressButton("Deactivate");
			pressButton("Refresh");
		}
	
	}

	public void verifyByopDevice(){
		
	}
	
	public void addRemainingPhonesForLinePlanOfPartWithSim(String numLines, String line1,
			String line2, String line3, String childSim1, String childSim2, String childSim3) throws Exception {
		TwistUtils.setDelay(15);
		String newLine_Esn;
		ESN childEsn = null;
		String partNumber = null;
		String simPartNumber = null;
		int actualNumofChildLines;
		ESN esn;
		int numberOfLines ;
		if ((esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) && !numLines.equalsIgnoreCase("")) {
			numberOfLines = Integer.parseInt(numLines);
			if (numberOfLines > 1) {
				actualNumofChildLines = numberOfLines-1;
				
				esn = esnUtil.getCurrentESN();
				familyEsns = new Hashtable();
				familyEsnsList = new ArrayList<ESN>();
				for (int i = 0; i <= actualNumofChildLines; i++) {
					String[] partNumber_list = { line1, line2, line3,line3 };
					String[] simPartNumber_list = { childSim1, childSim2, childSim3,childSim3 };
					
					String new_sim = null;
					partNumber=partNumber_list[i];
					simPartNumber=simPartNumber_list[i];
					
					childEsn= new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber_list[i]));
					if (!simPartNumber.isEmpty()) {
						new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber_list[i]);
						phoneUtil.addSimToEsn(new_sim, childEsn);
						TwistUtils.setDelay(5);
					}					
					
					newLine_Esn = childEsn.getEsn();
					familyEsns.put(partNumber_list[i]+"_"+i, newLine_Esn);
					esn.setFamiyESNMap(familyEsns);
					familyEsnsList.add(childEsn);
					esn.setFamilyEsns(familyEsnsList);
					
					//alwyas true for multiline activation in single purchase
					if (buttonVisible("Add Additional Line")) {
						pressButton("Add Additional Line");
					}					
							
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", newLine_Esn);
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", new_sim);
					activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
					TwistUtils.setDelay(5);
					if (buttonVisible("New Line/ Reactivate")) {
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "1234");
						activationPhoneFlow.pressSubmitButton("New Line/ Reactivate");
					}
					int index ;
					String ServicePlanRandom;
					if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
						String servicePlanListWFM[] = { "WFMAPPU0024", "WFMAPPU0029", "WFMAPPU0039", "WFMAPPU0049" };
						index= new Random().nextInt(servicePlanListWFM.length);
						ServicePlanRandom = servicePlanListWFM[index];
					}else {
						String servicePlanListSM[] = { "SMNAPP0025TT", "SMNAPP40030ILD", "SMNAPP0040UNL", "SMNAPP0050BBUNL","SMNAPP30060","SMNAPP20050ILDUP","SMNAPP20060ILDUP","SMNAPP20070ILDUP" };
						index= new Random().nextInt(servicePlanListSM.length);
						ServicePlanRandom = servicePlanListSM[index];
					}
				
					
					activationPhoneFlow.clickLink("Purchase Airtime");
					activationPhoneFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it2/", esnUtil.getCurrentESN().getZipCode());
					TwistUtils.setDelay(10);
					pressButton("Refresh");
					TwistUtils.setDelay(2);
					System.out.println(ServicePlanRandom);
					activationPhoneFlow.getBrowser().cell(ServicePlanRandom).doubleClick();
					activationPhoneFlow.clickCellMessage(ServicePlanRandom);
					pressButton("Add");
				}	
				pressButton("Checkout");
			}
			
		}
	
		if ((esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || 
				esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART"))
				&& !numLines.equalsIgnoreCase("")) {			
			
			numberOfLines = Integer.parseInt(numLines);
			if (numberOfLines > 1) {
			
					//if(numberOfLines==4){
						//actualNumofChildLines = numberOfLines;
					//}else{
				actualNumofChildLines = numberOfLines-1;
					
			esn = esnUtil.getCurrentESN();
			familyEsns = new Hashtable();
			familyEsnsList = new ArrayList<ESN>();
			for (int i = 0; i < (actualNumofChildLines); i++) {
				String[] partNumber_list = { line1, line2, line3 };
				String[] simPartNumber_list = { childSim1, childSim2, childSim3 };
			if(partNumber_list[i].equalsIgnoreCase("byop") || partNumber_list[i].equalsIgnoreCase("byophex")  ){
				partNumber = "byop" ;
				childEsn= new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
				newLine_Esn = childEsn.getEsn();
				familyEsns.put(partNumber_list[i]+"_"+i, newLine_Esn);
				familyEsnsList.add(childEsn);
				esn.setFamiyESNMap(familyEsns);
				esn.setFamilyEsns(familyEsnsList);
				 if(partNumber_list[i].equalsIgnoreCase("byophex")  ){
					 partNumber="byophex";
					phoneUtil.convertMeidDecToHex(childEsn);
					newLine_Esn= childEsn.getHexESN();
				 }
			}else if(partNumber_list[i].equalsIgnoreCase("byod") || partNumber_list[i].equalsIgnoreCase("byodhex") ){
				partNumber = "byod" ;
				childEsn= new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
				newLine_Esn = childEsn.getEsn();
				familyEsns.put(partNumber_list[i]+"_"+i, newLine_Esn);
				familyEsnsList.add(childEsn);
				esn.setFamiyESNMap(familyEsns);
				esn.setFamilyEsns(familyEsnsList);
				 if(partNumber_list[i].equalsIgnoreCase("byodhex")  ){
						partNumber="byodhex";
						phoneUtil.convertMeidDecToHex(childEsn);
						newLine_Esn= childEsn.getHexESN();
					 }
			}else{
				partNumber=partNumber_list[i];
				simPartNumber=simPartNumber_list[i];
				if(partNumber_list[i].startsWith("PHSM")){
					childEsn= new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber_list[i],simPartNumber));
				}else{
					childEsn= new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber_list[i]));
					if (!simPartNumber.isEmpty()) {
						String new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber_list[i]);
						phoneUtil.addSimToEsn(new_sim, childEsn);
						TwistUtils.setDelay(5);
					}
				
				}
				
				newLine_Esn = childEsn.getEsn();
				//	 newLine_Esn = phoneUtil.getNewEsnByPartNumber(partNumber_list[i]);
				 familyEsns.put(partNumber_list[i]+"_"+i, newLine_Esn);
					esn.setFamiyESNMap(familyEsns);
					familyEsnsList.add(childEsn);
					esn.setFamilyEsns(familyEsnsList);
			
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
								+ i + ":it9/", newLine_Esn);
			
			if(simPartNumber != null && simPartNumber.contains("CL7") && esn.getZipCode().equalsIgnoreCase("33178") ){
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
								+ i + ":it10/", "00692");
				TwistUtils.setDelay(2);
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
								+ i + ":it10/", "00692");
				pressButton("Queue Activation[" + i + "]");
				
			}else{
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
								+ i + ":it10/", esn.getZipCode());
				TwistUtils.setDelay(2);
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
								+ i + ":it10/", esn.getZipCode());
				pressButton("Queue Activation[" + i + "]");
			}
			/*	TwistUtils.setDelay(2);
				activationPhoneFlow.typeInTextField(
						"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:j_id__ctru50pc5:"
								+ i + ":it10/", esn.getZipCode());
				pressButton("Queue Activation[" + i + "]");*/
				
				TwistUtils.setDelay(5);
				
						try {

							activationPhoneFlow.typeInTextField(
									"/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:"
											+ i + ":it15::content/",
									childEsn.getSim());
							logger.info(childEsn.getSim());
						} catch (Exception ex) {
							logger.info(" SIM not found");
						}
			}
			//	familyEsns.put(partNumber_list[i]+"_"+i, newLine_Esn);
			//	esn.setFamiyESNMap(familyEsns);
				//actualNumofChildLines--;
				logger.info(familyEsns.size());
				
				
				switch (i) {
				case 0:
					pressButton("Queue Activation");
				    if("byop".equalsIgnoreCase(partNumber) || "byophex".equalsIgnoreCase(partNumber)){
					TwistUtils.setDelay(7);
					newLine_Esn = childEsn.getEsn();
					activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:j_id__ctru51pc5:0:sor1:_0/");
					phoneUtil.finishCdmaByopIgate(newLine_Esn, "RSS", "Yes","No","No","Yes");
					TwistUtils.setDelay(3);
					pressButton("Queue Activation");
					}else if("byod".equalsIgnoreCase(partNumber) || "byodhex".equalsIgnoreCase(partNumber)){
						TwistUtils.setDelay(5);
						newLine_Esn = childEsn.getEsn();
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:j_id__ctru51pc5:0:sor1:_1/");
						phoneUtil.finishCdmaByopIgate(newLine_Esn, "RSS", "Yes","No","No","Yes");
						TwistUtils.setDelay(3);
						pressButton("Queue Activation");
					}
					TwistUtils.setDelay(10);
					activationPhoneFlow.clickLink("Validate and Add");
					TwistUtils.setDelay(10);
					break;

				default:
					pressButton("Queue Activation[" + i + "]");
					if("byop".equalsIgnoreCase(partNumber) || "byophex".equalsIgnoreCase(partNumber)){
						TwistUtils.setDelay(5);
						newLine_Esn = childEsn.getEsn();
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:j_id__ctru51pc5:"+i+":sor1:_0/");
						phoneUtil.finishCdmaByopIgate(newLine_Esn, "RSS", "Yes","No","No","Yes");
						TwistUtils.setDelay(3);
						pressButton("Queue Activation[" + i + "]");
						}else if("byod".equalsIgnoreCase(partNumber) || "byodhex".equalsIgnoreCase(partNumber)){
							TwistUtils.setDelay(5);
							newLine_Esn = childEsn.getEsn();
							activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:j_id__ctru51pc5:"+i+":sor1:_1/");
							phoneUtil.finishCdmaByopIgate(newLine_Esn, "RSS", "Yes","No","No","Yes");
							TwistUtils.setDelay(5);
							pressButton("Queue Activation[" + i + "]");
						}
					TwistUtils.setDelay(5);
					if(activationPhoneFlow.linkVisible("Validate and Add["+ i +"]")){
					
						activationPhoneFlow.clickLink("Validate and Add["+ i +"]");
						TwistUtils.setDelay(3);
						activationPhoneFlow.clickLink("Validate and Add");
					}else{ activationPhoneFlow.clickLink("Validate and Add");}
					if(i==2){
						activationPhoneFlow.clickLink("Validate and Add[1]");
					}
					
					TwistUtils.setDelay(10);
					break;
				}
			}
			
			}
		
		}
	}
	
	public void checkoutAndProcessTransaction() throws Exception {
			String brand = esnUtil.getCurrentBrand();
		  String currentesn= esnUtil.getCurrentESN().getEsn();
		if (brand.equalsIgnoreCase("total_wireless") || brand.equalsIgnoreCase("simple_mobile") || brand.equalsIgnoreCase("WFM") || brand.equalsIgnoreCase("GO_SMART")) {
			TwistUtils.setDelay(5);
		if(!(brand.equalsIgnoreCase("WFM") || brand.equalsIgnoreCase("simple_mobile") )){
			pressButton("Refresh Table");
			pressButton("Process Transaction");
		//	TwistUtils.setDelay(45);
		}else{
			//click below must for purchase flow. no need for pin flow
			if(buttonVisible("Ready to Pay")){
				pressButton("Ready to Pay");
				
			}
		}
			
		
			Assert.assertTrue( activationPhoneFlow.cellVisible("/Transaction Summary.*/"));
			logger.info("Processing Activation of master ESN:"
					+currentesn );
			TwistUtils.setDelay(30);
		//	String stageStatus = phoneUtil.getStatusOfESNFromStage(currentesn);
		//	Assert.assertTrue("STAGE TABLE STATUS NOT COMPLETED FOR MASTER:"+currentesn,stageStatus.equalsIgnoreCase(COMPLETED));
			finishPhoneActivationForTotalWirelss(esnUtil.getCurrentESN(), "CDMA", "New");
			phoneUtil.clearOTAforEsn(currentesn);
			if(familyEsns != null){
			Set keys = familyEsns.keySet();
			Iterator itr = keys.iterator();
			String child_partnum;
			String child_esn;
			logger.info(keys.size());
			while (itr.hasNext()) {
				child_partnum = (String) itr.next();
				child_esn = (String) familyEsns.get(child_partnum);
			//	String childStageStatus = phoneUtil.getStatusOfESNFromStage(child_esn);
			//	Assert.assertTrue("STAGE TABLE STATUS NOT COMPLETED FOR CHILD:"+currentesn,childStageStatus.equalsIgnoreCase(COMPLETED));
				logger.info("Processing Activation of:" + child_partnum+ " - " + child_esn);
			ESN childESN2= new ESN(child_partnum, child_esn);
				finishPhoneActivationForTotalWirelss(childESN2, cellTechtype, "New");
				phoneUtil.clearOTAforEsn(child_esn);

			}}
			pressButton("Refresh");
			checkForTransferButton();
		}
		else{
			phoneUtil.clearOTAforEsn(currentesn);
			pressButton("Refresh");
		}
		cboUtil.callGetPassword(esnUtil.getCurrentESN().getEmail(), esnUtil.getCurrentBrand());
	}

	public void goToAccountSummary() throws Exception {
		if( esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") ||esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			pressButton("Contact Profile");	
			activationPhoneFlow.clickCellMessage("INACTIVE GROUP");
			}
	}

	public void addEsnToGroupForPartSimOfCellTechUsingPin(String status, String partNumber,String simPart,String cellTech,String pin)
			throws Exception {
//		pressButton("Contact Profile");
		pressButton("Add Group Member");
		String currentZip= esnUtil.getCurrentESN().getZipCode();
		ESN group2Esn;
		if(partNumber.startsWith("PHSM")){
			group2Esn= new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
		}else{
			group2Esn= new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simPart.isEmpty()) {
				String new_sim = simUtil.getNewSimCardByPartNumber(simPart);
				phoneUtil.addSimToEsn(new_sim, group2Esn);
				TwistUtils.setDelay(5);
			}
		}
		  activationPhoneFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it2::content/", group2Esn.getEsn());
		  activationPhoneFlow.typeInTextField("/r\\d:\\d:r\\d:\\d:it3::content/", "ITQ_ADD_MEMBER_TEST");
		  pressButton("Add");
		  activationPhoneFlow.clickLink(group2Esn.getEsn());
		  activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pin, status, cellTech, currentZip);
	}

	public void selectPortOptionForChildPartZip(String partNumber, String zipCode)
			throws Exception {
		String simPart= "SM64PSIMT5B";
		ESN group2Esn;
		if(partNumber.startsWith("PHSM")){
			group2Esn= new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
		}else{
			group2Esn= new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simPart.isEmpty()) {
				String new_sim = simUtil.getNewSimCardByPartNumber(simPart);
				phoneUtil.addSimToEsn(new_sim, group2Esn);
				TwistUtils.setDelay(5);
			}
		}
		childEsn = group2Esn.getEsn();
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:0:it9/", childEsn);
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:0:it10/", zipCode);
		pressButton("Queue Port");
		TwistUtils.setDelay(5);
		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:0:it10/")){
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it16:0:it10/", zipCode);
			pressButton("Queue Port");
		}
	}

	public void checkForConfirmationAndCompleteActivation() throws Exception {
	
	}

	public void checkForConfirmationAndCompleteActivationForTech(String cellTech)
			throws Exception {
		Assert.assertTrue( activationPhoneFlow.cellVisible("Transaction Summary (Individual/Multiple Devices)"));
		TwistUtils.setDelay(40);
		phoneUtil.getStatusOfESNFromStage(esnUtil.getCurrentESN().getEsn());
		phoneUtil.getStatusOfESNFromStage(childEsn);
		finishPhoneActivationForTotalWirelss(esnUtil.getCurrentESN(), cellTech, "New");
	}

	public void selectAndDeleteESNFromGroup(String memberType) throws Exception {
		pressButton("Contact Profile");
		this.memberType = memberType;
		activationPhoneFlow.clickCellMessage("GROUP 1");
		if ("master".equalsIgnoreCase(memberType)) {
			activationPhoneFlow.clickCellMessage("Y");
			memberValue = activationPhoneFlow.getBrowser()
					.span("/r\\d:\\d:r\\d:\\d:ot38/").getText();
		} else if ("child".equalsIgnoreCase(memberType)) {
			activationPhoneFlow.clickCellMessage("N");
			memberValue = activationPhoneFlow.getBrowser()
					.span("/r\\d:\\d:r\\d:\\d:ot38/").getText();
		}
		pressButton("Delete Group Member");
		pressButton("Delete");
	}
	public boolean checkByop(){
		String partNumber =esnUtil.getCurrentESN().getPartNumber();
		if(partNumber.equalsIgnoreCase("byop") || partNumber.equalsIgnoreCase("byod")|| partNumber.equalsIgnoreCase("byophex") || partNumber.equalsIgnoreCase("byodhex")){
			return true;
		}
		
		return false;
	
	}
	public void completeAllChecks() throws Exception {
		Assert.assertTrue("DELETE MEMBER NOT SUCCESSFUL",
				activationPhoneFlow.cellVisible("/SUCCESS.*/"));
		pressButton("Refresh");
		Assert.assertTrue("CURRENT GROUP INVISIBLE IN ACCOUNT",
				activationPhoneFlow.cellVisible("GROUP 1"));
		activationPhoneFlow.clickCellMessage("INACTIVE GROUP");
		if (memberType.equalsIgnoreCase("master")) {
			Assert.assertTrue("MASTER MEMBER NOT AVAILABLE IN INACTIVE GROUP",
					activationPhoneFlow.cellVisible(memberValue));
		} else if (memberType.equalsIgnoreCase("child")) {
			Assert.assertTrue("CHILD MEMBER NOT AVAILABLE IN INACTIVE GROUP",
					activationPhoneFlow.cellVisible(memberValue));
		}
	}

	public void continuteToActivation() throws Exception {
	       pressButton("Register & Activate");
	}

	public void reactivatePhoneWithPinOnZipOfCellTech(String pinPart, String zip, String cellTech) throws Exception {
		activatePhoneWithStatusPinOnZipOfCellTech("Active", pinPart, zip, cellTech);
	}
	
	public void activatePhoneWithStatusPinOnZipOfCellTech(String status, String pinPart, String zip, String cellTech) throws Exception {
		pressButton("Refresh");
		activationPhoneFlow.clickLink("Transactions");
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) {
			activationPhoneFlow.clickLink("Activation/ Port");
		} else {
			activationPhoneFlow.clickLink("Activation");
		}
		String activationType;
		if ("New".equalsIgnoreCase(status)) {
			activationType = "ACTIVATION";
		} else {
			activationType = "REACTIVATION";
		}
		activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart, activationType, cellTech, zip);		
	}

	public void selectESNForLineForReactivation(String esnType, String esnNbr, String numlines) throws Exception {
		int pos = 1;
		if( esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") && esnType.equalsIgnoreCase("child") && !numlines.equalsIgnoreCase("") && !numlines.equalsIgnoreCase("1")){
			pressButton("Contact Profile");	
			activationPhoneFlow.clickCellMessage("GROUP 1");
			List familyEsns = esnUtil.getCurrentESN().getFamilyEsns();
			Iterator itr = familyEsns.iterator();
			ESN childEsn = null;
			int esnCount = 0;
			if(!esnNbr.equalsIgnoreCase("")){
				esnCount = Integer.parseInt(esnNbr);
			}					
			while(pos<esnCount){
				childEsn = (ESN)itr.next();
				pos++;
			}	
			esnUtil.setCurrentESN(childEsn);
			String childEsnStr = esnUtil.getCurrentESN().getEsn();
			activationPhoneFlow.clickLink(childEsnStr);			
		}
	}

	public void registerForWificalling() throws Exception {
            activationPhoneFlow.navigateTo(props.getString("wificallingurl"));
   //         activationPhoneFlow.pressSubmitButton("ENROLL");
            String Esn = esnUtil.getCurrentESN().getEsn();
         String min = phoneUtil.getMinOfActiveEsn(Esn);
            logger.info(" CurrentMin: " + min);
         activationPhoneFlow.typeInTextField("input_phone", min);
         String last4digits = Esn.substring(Esn.length()-4);
         activationPhoneFlow.typeInTextField("input_esn", last4digits);
         logger.info("Last4Digits:" + last4digits);
         activationPhoneFlow.pressButton("enroll");
         activationPhoneFlow.getBrowser().textbox("address1").setValue("1295 Charleston Rd");
       //  activationPhoneFlow.getBrowser().textbox("address2").setValue("Road 36");
         activationPhoneFlow.getBrowser().select("state").choose("CA");
         activationPhoneFlow.getBrowser().textbox("txtzip_code").setValue("33178");
         activationPhoneFlow.getBrowser().textbox("city").setValue("Mountain View");
       //  activationPhoneFlow.getBrowser().div("CONTINUE");
       if(buttonVisible("CONTINUE")){
    	   activationPhoneFlow.pressButton("CONTINUE");
       }else if(buttonVisible("Continue")){
    	   activationPhoneFlow.pressButton("Continue");
       }
       
       
         
            
     }
		public void addANewESNToAccountForPartSim(String esnPart, String simPart) throws Exception {
			ESN esn = null;
			if (esnPart.matches("PH(SM|ST|TC|NT|TF).*")) {
				esn = new ESN(esnPart, phoneUtil.getNewByopEsn(esnPart,simPart));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if ("byop".equalsIgnoreCase(esnPart)) {
				esn = new ESN(esnPart, phoneUtil.getNewByopCDMAEsn());
			} else {
				esn = new ESN(esnPart,phoneUtil.getNewEsnByPartNumber(esnPart));
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
						sim = simUtil.getNewSimCardByPartNumber(simPart);
						esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
					}
					phoneUtil.addSimToEsn(sim, esn);
				}
			}
			esn.setEmail(esnUtil.getCurrentESN().getEmail());
			esnUtil.setCurrentESN(esn);
			activationPhoneFlow.clickLink("Account Summary");
			activationPhoneFlow.pressSubmitButton("Add ESN to Account");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it18/", esnUtil.getCurrentESN().getEsn());
			activationPhoneFlow.pressSubmitButton("Add");
		}

		public void checkFrontendForPointAllocationForPin(String transType, String pinPart)
				throws Exception {
			if (!pinPart.isEmpty()){	
				TwistUtils.setDelay(20);
				
				//Get points for TransType from DB	
				String dbActivationPoints = phoneUtil.getLRPPointsbyEmailforTranstype(transType,esnUtil.getCurrentESN().getEmail());
				
				//Get points from DB for PinPart
				String points = phoneUtil.getLRPPointsforPinPart(pinPart);
				System.out.println("points::"+points);
				
				//Verify the points displayed in transaction summary
				if(!transType.equals("REACTIVATION")){
					if (activationPhoneFlow.divVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl7/")){
						Assert.assertTrue(activationPhoneFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl7/").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
					}
				}
				//Check whether the bonus points for activation is same as in DB 
				Assert.assertTrue(points.equals(dbActivationPoints));
			}
		}

	
		public void enterRealEsnForPartSim(String status, String esnnumber,
				String simPart) throws Exception {
			activationPhoneFlow.clickLink("Incoming Call");
			simPartNumber = simPart; 
			ESN esn = null;
			if (NEW_STATUS.equalsIgnoreCase(status)) {
				
				esn = new ESN("TFALA845G3P4", esnnumber);
				
				esnUtil.setCurrentESN(esn);
				logger.info(esn);
				if (!simPart.isEmpty()) {
					
					phoneUtil.addSimToEsn(simPart, esn );
				}
				pressButton("New Contact Account");
			}
		
		}
		private boolean isTextFieldEnabled(String buttonType) {
			if (activationPhoneFlow.getBrowser().textbox(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
				return false;
			} else {
				return true;
			}
		}
		
		public void activateRealhandsetByUsingPinDependingOnStatusOfCellTechZip(
				String pinPart, String status, String cellTech, String zip)
				throws Exception {
			
			this.cellTechtype = cellTech;
			checkForTransferButton();
			String newPin;
			if("NO PIN".equalsIgnoreCase(pinPart)){
				newPin = pinPart;
			}else{
				 newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			}
			
			esnUtil.getCurrentESN().setPin(newPin);
			activateRealHandsetWithNewPin(newPin, status, cellTech, zip);
		}	
			
		private synchronized void activateRealHandsetWithNewPin(String newPin, String status, String cellTech, String zip) throws Exception {	
			ESN esn  = esnUtil.getCurrentESN();
			String partNumber= esn.getPartNumber();
			if(!partNumber.matches("BYO.*")){
				activationPhoneFlow.clickLink("Transactions");
				activationPhoneFlow.clickLink("Activation");
			}
			
			if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/") && activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").getValue().equalsIgnoreCase("")){
				String new_sim = simPartNumber;
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/",new_sim);
				esn.setSim(new_sim);
			}
			
			activationPhoneFlow.typeInTextField("/r2:\\d:r1:\\d:it2/", zip);
			logger.info("PIN:" + newPin);
			esnUtil.getCurrentESN().setPin(newPin);
			if("NO PIN".equalsIgnoreCase(newPin)){
				
			}else{
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
			pressButton("Validate Card");
			TwistUtils.setDelay(10);
			String error_msg1=activationPhoneFlow.getBrowser().div("d1::msgDlg::_cnt").getText() ;
			if (activationPhoneFlow.linkVisible("Close[1]")) {
				activationPhoneFlow.clickLink("Close[1]");
			}
			String error_msg2 = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
			Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg2+":"+error_msg1, activationPhoneFlow.cellVisible("Valid PIN"));
			}
			pressButton("Activate");
			TwistUtils.setDelay(10);
			if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()){
			error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
			} 
			if(activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").isVisible()){
				error_msg = error_msg + activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").getText();
			}
			if(activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")){
				pressButton("Code Accepted");
			}
			Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.h2Visible("Transaction Summary"));
//			finishPhoneActivation(cellTech, status);
			//esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "TAS Activation with PIN ["+esnUtil.getCurrentBrand()+"]");
			pressButton("Refresh");
			TwistUtils.setDelay(30);
			pressButton("Refresh");
		
		}

			private void finishPhoneActivation(String cellTech, String status) throws Exception {
//				TwistUtils.setDelay(13);
				ESN currEsn = esnUtil.getCurrentESN();
				System.out.println(currEsn.getEsn());
				esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "TAS Activation["+esnUtil.getCurrentBrand()+"]");
				TwistUtils.setDelay(5);
				phoneUtil.clearOTAforEsn(currEsn.getEsn());
			}

			public void finishPhoneActivationForCellTypeAndStatus(String cellTech,
					String status) throws Exception {
				TwistUtils.setDelay(10);
				ESN currEsn = esnUtil.getCurrentESN();
				phoneUtil.clearOTAforEsn(currEsn.getEsn());
				if (currEsn.getTransType().isEmpty()) {
					currEsn.setTransType("SL Activate Phone");
				}
				esnUtil.addRecentActiveEsn(currEsn, cellTech, status, currEsn.getTransType());
				currEsn.clearTestState();
				phoneUtil.clearOTAforEsn(currEsn.getEsn());
			}
			
			public void enterEsnForPart(String status, String partNumber) throws Exception {  //New
				activationPhoneFlow.navigateTo(props.getString("TAS.HomeUrl"));
				activationPhoneFlow.clickLink("Incoming Call");	
				ESN esn = null;	
				if (NEW_STATUS.equalsIgnoreCase(status)) {
					esn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
					System.out.println(esn.getEsn());
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esn.getEsn());
					pressButton("Search Service");
					//pressButton("Continue to Service Profile");
					esnUtil.setCurrentESN(esn);
				}
				else{
					esn = esnUtil.popRecentActiveEsn(partNumber);
					System.out.println(esn.getEsn());
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/",esn.getEsn());
					pressButton("Search Service");
					//pressButton("Continue to Service Profile");
					esnUtil.setCurrentESN(esn);
				}
				
			}

			public void gxxdgfxh() throws Exception {
			
			}
			
						
		public void checkBackendForPointAllocationForAndReasonLRPEnrollment(String transType, String reason) throws Exception {
			TwistUtils.setDelay(20);
			
			//Reward Points for transType Auto Refill				
			//String points="250";
			String points= phoneUtil.getLRPPointsForTransType(transType);
			
			if (reason.equals("After")){
				//Trans_desc = Bonus: Enrollment in Auto-Refill
					String Reason1=phoneUtil.getTransdescforLRPTransType(transType,esnUtil.getCurrentESN().getEmail());
					Assert.assertTrue(Reason1.equals("Bonus: Enrollment in Auto-Refill"));
					//Get points for TransType from DB	
					String dbActivationPoints = phoneUtil.getLRPPointsbyEmailforTranstype(transType,esnUtil.getCurrentESN().getEmail());						
					//Check whether the bonus points for auto refill is same as in DB 
					Assert.assertTrue(points.equals(dbActivationPoints));
			}else {
				//Trans_desc = Note: Already enrolled in Auto-Refill
					String Reason1=phoneUtil.getTransdescforLRPTransType(transType,esnUtil.getCurrentESN().getEmail());
					Assert.assertTrue(Reason1.equals("Note: Already enrolled in Auto-Refill"));				
			}
			
		}

		public void checkFrontendForPointAllocationForEnrollPlan(String Transtype,
				String Plan) throws Exception {
				
			//Get points for TransType from DB	
			String dbActivationPoints = phoneUtil.getLRPPointsbyEmailforTranstype(Transtype,esnUtil.getCurrentESN().getEmail());
			
			//Get points from DB for Enrollment Plan
				String Plan1=Plan.replace(' ','_');		
				String Objid = rb1.getString(Plan1);
			String points = phoneUtil.getLRPPointsbyObjid(Objid);
		
			System.out.println("points::"+points);
			
			//Verify the points displayed in transaction summary
			
			if (activationPhoneFlow.divVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:2:pfl7/")){
			Assert.assertTrue(activationPhoneFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:2:pfl7/").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
			//}else if (activationPhoneFlow.divVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\:4:pfl7/")){
			//Assert.assertTrue(activationPhoneFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:4:pfl7/").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
			}
			//Check whether the bonus points for activation is same as in DB 
			Assert.assertTrue(points.equals(dbActivationPoints));
		
		}

		public void aWOPWithSupervisorApprovalForZipReasonPlan(String zip, String reason,String plan) throws Exception {					
			ESN esn  = esnUtil.getCurrentESN();
			String partNumber= esn.getPartNumber();
			
			if(!partNumber.matches("BYO.*")){
				activationPhoneFlow.clickLink("Transactions");
				activationPhoneFlow.clickLink("Activation");
			}
			
			activationPhoneFlow.clickLink("AWOP");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", zip);
			TwistUtils.setDelay(5);
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
			
			if(activationPhoneFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/")){
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/", reason);
			}
			
			if(activationPhoneFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/")){
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/", plan);
				pressButton("Validate Plan");
			}
			if(activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/").isVisible()) {
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", "1");
			}
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/", "15");
			activationPhoneFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it8::content/", "test");
			pressButton("Activate");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it21::content/",props.getString("TAS.UserName"));
			pressButton("Accept");
			TwistUtils.setDelay(10);
			
//			activationPhoneFlow.selectRadioButton("r2:1:r1:1:r2:1:sor1:_2");
//			activationPhoneFlow.chooseFromSelect("r2:1:r1:1:r2:1:soc3::content", reason);
//			activationPhoneFlow.typeInTextArea("r2:1:r1:1:r2:1:it8::content","TestTwist");
//			activationPhoneFlow.chooseFromSelect("r2:1:r1:1:r2:1:soc1::content", plan);			
//			pressButton("Validate Plan");
//			activationPhoneFlow.typeInTextField("r2:1:r1:1:r2:1:it11::content","25");
//			pressButton("Activate");
//			activationPhoneFlow.typeInTextField("r2:1:r1:1:r2:1:it21::content",props.getString("TAS.UserName"));
//			pressButton("Accept");
		}

		public void checkFrontendForPointAllocationFor(String transType) throws Exception {
			
			TwistUtils.setDelay(20);
			String Points = phoneUtil.getActivationPointsByEsn(esnUtil.getCurrentESN().getEsn());
			TwistUtils.setDelay(120);
			String dbAwopPoints = phoneUtil.getLRPPointsbyEmailforTranstype(transType,esnUtil.getCurrentESN().getEmail());
			
			if (activationPhoneFlow.divVisible("r2:1:r1:2:pfl7")){
				Assert.assertTrue(activationPhoneFlow.getBrowser().div("r2:1:r1:2:pfl7").text().contains("You will gain " + Points + " Loyalty Reward points as part of this transaction"));
			}else if (activationPhoneFlow.divVisible("r2:1:r1:4:pfl7")){
				Assert.assertTrue(activationPhoneFlow.getBrowser().div("r2:1:r1:4:pfl7").text().contains("You will gain " + Points + " Loyalty Reward points as part of this transaction"));
			}
			
			Assert.assertTrue(Points.equals(dbAwopPoints));
			
		}

		public void goToMainScreen() throws Exception {
			pressButton("New Customer");				
		}

		public void lRPAWOPWithReferenceEsnForZipCodeAndServiceDaysAndReason(String oldPart, String zip, String ServiceDays, String reason) throws Exception {
			
			activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation");
			activationPhoneFlow.clickLink("AWOP");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
			pressButton("Refresh");
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_0/");
			String refEsn = esnUtil.popRecentActiveEsn(oldPart).getEsn();
			logger.info("Reference ESN:"+refEsn);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it7::content/", refEsn);
			pressButton("Validate ESN");
					
			if (activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/").isVisible()) {
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", ServiceDays);
			}
			
			if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/")) {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/", ServiceDays);
			}
			
			if (activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/")) {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it11/", "20");
			}
			
			activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3/", reason);
			activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it8/").setValue("ITQ-AWOP TEST");
			pressButton("Activate");
			if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it21/")){
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it21/", "ITQUSER");
				pressButton("Accept");
			}
			
			if (activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")) {
				pressButton("Code Accepted");
			}
		}
		public void reactivatePhoneByUsingPinSimDependingOnStatusOfCellTechZip(
				String pinPart, String simPart, String status, String cellTech, String zip) throws Exception {
			simPartNumber = simPart;
			activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status,cellTech,zip);
		}
		
		public void selectOptionPartNumberSimPinOfCellTechZipWithMinTransferForSL(String status, String partNumber, String simPart, String pinPart,
					String cellTech, String zip,String minTransfer) throws Exception {
				String minTransfer_button;
				if(minTransfer.equalsIgnoreCase("Yes")){
					minTransfer_button = "Process Transaction (min)";			
					if(activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:sbc1::content/")){
						activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:sbc1::content/");
					}
					if(activationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:sbc1::content/")){
						activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:sbc1::content/");
					}
					
					pressButton("Continue");
				}
					else {
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
					minTransfer_button = "Transfer Service (no min)";
				}
				ESN toEsn = null;
				ESN fromEsn = esnUtil.getCurrentESN();
			
				if (status.equalsIgnoreCase("Active")) {
					pressButton("Contact Profile");
					pressButton("Add ESN to Account");
				   toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
					esnUtil.setCurrentESN(toEsn);
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
					pressButton("Add");
					Assert.assertTrue("ESN not added to account", activationPhoneFlow.cellVisible("ESN added to account, Successfully"));
					activationPhoneFlow.clickLink(toEsn.getEsn());
					checkForTransferButton();
					activationPhoneFlow.clickLink("Transactions");
					activationPhoneFlow.clickLink("Activation");
					if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
						if (!simPart.isEmpty()) {
							String sim = simUtil.getNewSimCardByPartNumber(simPart);
							phoneUtil.addSimToEsn(sim, toEsn);
							pressButton("Refresh");
						}
						activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
						pressButton("Contact Profile");
						activationPhoneFlow.clickLink("Account Summary");
						activationPhoneFlow.clickLink(fromEsn.getEsn());
					} else {
						pressButton("Contact Profile");
						activationPhoneFlow.clickLink(fromEsn.getEsn());
						fromEsn = new ESN(partNumber,
								phoneUtil.getActiveEsnByPartNumber(partNumber));
						phoneUtil.clearOTAforEsn(fromEsn.getEsn());
					//  esnUtil.setCurrentESN();
					}
					checkForTransferButton();
					activationPhoneFlow.clickLink("Upgrade");
					if(minTransfer.equalsIgnoreCase("Yes")){
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
					}else {
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
					}
					if (activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/").isVisible()) {
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
						pressButton("Continue");
					}

				} else if(status.equalsIgnoreCase("PastDue")){
					pressButton("Contact Profile");
					pressButton("Add ESN to Account");
				   toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
					esnUtil.setCurrentESN(toEsn);
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
					pressButton("Add");
					Assert.assertTrue("ESN not added to account", activationPhoneFlow.cellVisible("ESN added to account, Successfully"));
					activationPhoneFlow.clickLink(toEsn.getEsn());
					checkForTransferButton();
					activationPhoneFlow.clickLink("Transactions");
					activationPhoneFlow.clickLink("Activation");
					if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
						if (!simPart.isEmpty()) {
							String sim = simUtil.getNewSimCardByPartNumber(simPart);
							phoneUtil.addSimToEsn(sim, toEsn);
							pressButton("Refresh");
						}
						activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
						pressButton("Refresh");
						activationPhoneFlow.clickLink("Deactivation");
						activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "/.*PASTDUE.*/");
						pressButton("Deactivate");
						Assert.assertTrue(activationPhoneFlow.h2Visible("Transaction Summary")||activationPhoneFlow.divVisible("Your phone has been deactivated."));
						pressButton("Contact Profile");
						activationPhoneFlow.clickLink(fromEsn.getEsn());
					
					} else {
						pressButton("Contact Profile");
						activationPhoneFlow.clickLink(fromEsn.getEsn());
						fromEsn = new ESN(partNumber,phoneUtil.getActiveEsnByPartNumber(partNumber));
						phoneUtil.clearOTAforEsn(fromEsn.getEsn());
					//  esnUtil.setCurrentESN();
					}
					activationPhoneFlow.clickLink("Upgrade");
					if (minTransfer.equalsIgnoreCase("Yes")) {
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
					} else {
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
					}
					if (activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/").isVisible()) {
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
						pressButton("Continue");
					}
				} else if(status.equalsIgnoreCase("New")){
					toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
					if(partNumber.equalsIgnoreCase("NTLGL41CWHP")){
						String pin = phoneUtil.getNewPinByPartNumber("NTAPP6U040FREE");
						phoneUtil.addPinToQueue(toEsn.getEsn(), pin);
					}
					logger.info("TO ESN (NEW):" + toEsn.getEsn());
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",toEsn.getEsn());
					if (partNumber.matches("PH(SM|ST|TC|NT|TF).*")) {
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toEsn.getSim());
					}
					pressButton("Continue");
					if(activationPhoneFlow.cellVisible("/.*SIM SWAP.*/") && simPart.equalsIgnoreCase("SWAP")){
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
						logger.info("RUNNING SIM SWAP");
						pressButton("Continue");
					}else if(activationPhoneFlow.cellVisible("/.*SIM SWAP.*/") && !simPart.equalsIgnoreCase("SWAP") && !simPart.isEmpty()){
						activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
						String toSim = simUtil.getNewSimCardByPartNumber(simPart);
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toSim);
						pressButton("Continue");
					}else if(!simPart.isEmpty() ) {
						String toSim = simUtil.getNewSimCardByPartNumber(simPart);
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", toSim);
						pressButton("Continue");
					}
					
					if (activationPhoneFlow.cellVisible("Pin is Required.")) {
						String pin = phoneUtil.getNewPinByPartNumber(pinPart);
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", pin);
						pressButton("Continue");

					}
					pressButton(minTransfer_button);
				}
				
				if (activationPhoneFlow.cellVisible("Please provide the balance information.")) {
					if (getButtonType("Get Balance Inquiry")) {
						pressButton("Get Balance Inquiry");
					}else if (getButtonType("Balance Inquiry")) {
						pressButton("Balance Inquiry");
					}else if (getButtonType("Balance Inquiry[1]")) {
						pressButton("Balance Inquiry[1]");
					}
					pressButton("Manual Balance Entry");
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", "1");
					
					if (activationPhoneFlow.cellVisible("Sms")) 
					{
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18::content/", "1");
					}
					if (activationPhoneFlow.cellVisible("Data")) 
					{
						activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19::content/", "1");
						activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1::content/", "MB");
					}
					pressButton("Submit");
					pressButton("Done");
					pressButton(minTransfer_button);
					 
					TwistUtils.setDelay(60);//
					
				}
			
				phoneUtil.clearOTAforEsn(toEsn.getEsn());//
				toEsn.setTransType("TAS Phone Upgrade["+esnUtil.getCurrentBrand()+"]");
				phoneUtil.checkUpgrade(esnUtil.getCurrentESN(), toEsn);
				
				//SL
				esnUtil.setCurrentESN(toEsn);//
			}

		public void clickOnNewCustomer() throws Exception {
			esnUtil.addRecentEsnWithPin(esnUtil.getCurrentESN());
			pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1200/");
		}

		public void addESNToPromoGroup() throws Exception {
			int PromoAdded=0;
			String esn= esnUtil.getCurrentESN().getEsn();
			String min= phoneUtil.getMinOfActiveEsn(esn);
			String email=esnUtil.getCurrentESN().getEmail();		
			PromoAdded = phoneUtil.LRPAddEsntoPromoGroup(esn, min, email);		
			System.out.println("PromeAdded : " +PromoAdded );
		
		}

	public void addRemainingPhonesForLinePlanOfPartWithSimForWarp(
			String numLines, String line1, String line2, String line3, String line4,
			String childSim1, String childSim2, String childSim3, String childSim4)
			throws Exception {

		if (!numLines.equalsIgnoreCase("1") && !numLines.equalsIgnoreCase("")  ) {
			String newLine_Esn;
			ESN childEsn = null;
			String partNumber = null;
			String simPartNumber = null;
			int actualNumofChildLines;
			ESN esn;
			int numberOfLines = Integer.parseInt(numLines);
			if (numberOfLines > 1) {
				actualNumofChildLines = numberOfLines - 1;

				esn = esnUtil.getCurrentESN();
				familyEsns = new Hashtable<String, String>();
				familyEsnsList = new ArrayList<ESN>();
				for (int i = 0; i < (actualNumofChildLines); i++) {
					String[] partNumber_list = { line1, line2, line3 ,line4};
					String[] simPartNumber_list = { childSim1, childSim2,
							childSim3,childSim4 };
					if (partNumber_list[i].equalsIgnoreCase("byop")
							|| partNumber_list[i].equalsIgnoreCase("byophex")) {
						partNumber = "byop";
						childEsn = new ESN(partNumber,
								phoneUtil.getNewByopCDMAEsn());
						childEsn.setDeviceType("BYOP");
						newLine_Esn = childEsn.getEsn();
						familyEsns.put(partNumber_list[i] + "_" + i,
								newLine_Esn);
						familyEsnsList.add(childEsn);
						esn.setFamiyESNMap(familyEsns);
						esn.setFamilyEsns(familyEsnsList);
						if (partNumber_list[i].equalsIgnoreCase("byophex")) {
							partNumber = "byophex";
							phoneUtil.convertMeidDecToHex(childEsn);
							newLine_Esn = childEsn.getHexESN();
						}
					} else if (partNumber_list[i].equalsIgnoreCase("byod")
							|| partNumber_list[i].equalsIgnoreCase("byodhex")) {
						partNumber = "byod";
						childEsn = new ESN(partNumber,
								phoneUtil.getNewByopCDMAEsn());
						childEsn.setDeviceType("BYOD");
						newLine_Esn = childEsn.getEsn();
						familyEsns.put(partNumber_list[i] + "_" + i,
								newLine_Esn);
						familyEsnsList.add(childEsn);
						esn.setFamiyESNMap(familyEsns);
						esn.setFamilyEsns(familyEsnsList);
						if (partNumber_list[i].equalsIgnoreCase("byodhex")) {
							partNumber = "byodhex";
							phoneUtil.convertMeidDecToHex(childEsn);
							newLine_Esn = childEsn.getHexESN();
						}
					} else {
						partNumber = partNumber_list[i];
						simPartNumber = simPartNumber_list[i];
//						if (partNumber_list[i].startsWith("PH")) {
//							childEsn = new ESN(partNumber,
//									phoneUtil.getNewByopEsn(partNumber_list[i],
//											simPartNumber));
//							childEsn.setDeviceType("BYOP");
//						} 
		
					    childEsn = new ESN(
									partNumber,
									phoneUtil
											.getNewEsnByPartNumber(partNumber_list[i]));
							childEsn.setDeviceType("BRANDED");
							if (!simPartNumber.isEmpty()) {
								String new_sim = simUtil
										.getNewSimCardByPartNumber(simPartNumber_list[i]);
								phoneUtil.addSimToEsn(new_sim, childEsn);
								TwistUtils.setDelay(5);
							}
							newLine_Esn = childEsn.getEsn();
							familyEsns.put(partNumber_list[i] + "_" + i,
									newLine_Esn);
							familyEsnsList.add(childEsn);
							esn.setFamiyESNMap(familyEsns);
							esn.setFamilyEsns(familyEsnsList);
							System.out.println(familyEsns);
						}

					}
				}

			}

		}

	
	
	public void completePhoneActivationForCellTechAndStatus(String cellTech, String status) throws Exception {
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") ) {
			finishPhoneActivationForTotalWirelss(esnUtil.getCurrentESN(), cellTech, status);
		}
		finishPhoneActivation(cellTech, status);
	}

	public synchronized void activatePhoneByUsingPinAndPromoDependingOnStatusOfCellTechZip(
			String pinPart, String promo, String status, String cellTech,
			String zip) throws Exception {
		
		this.cellTechtype = cellTech;
		checkForTransferButton();
		String newPin;
		if("NO PIN".equalsIgnoreCase(pinPart)){
			newPin = pinPart;
		}else{
			 newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		}
		
		esnUtil.getCurrentESN().setPin(newPin);
		activateWithNewPinAndPromo(newPin, promo, status, cellTech, zip);
	}

	private synchronized void activateWithNewPinAndPromo(String newPin, String promo, String status, String cellTech, String zip) throws Exception {
		ESN esn  = esnUtil.getCurrentESN();
		String partNumber= esn.getPartNumber();
		
		if(!partNumber.matches("BYO.*")){
			activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation");
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){
			esn.setZipCode(zip);
			if(!"reactivation".equalsIgnoreCase(status)){
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
				pressButton("Validate");
				TwistUtils.setDelay(5);
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
				if(!zip.equalsIgnoreCase("") ){
					if(isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/")){
					activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", zip);
					}
				}
				/*
				if (partNumber.equalsIgnoreCase("byop")
						|| partNumber.equalsIgnoreCase("byophex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_0/");
				} else if (partNumber.equalsIgnoreCase("byod")
						|| partNumber.equalsIgnoreCase("byodhex")) {
					activationPhoneFlow
							.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor3:_1/");
				}
				*/
				pressButton("Continue");
			} else{
				activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_2/");
				pressButton("Continue");
				if(activationPhoneFlow.textboxVisible("r2:1:r1:2:it16:0:it10::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:2:it16:0:it10::content",esn.getZipCode());
				}else if (activationPhoneFlow.textboxVisible("r2:1:r1:4:it16:0:it10::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:4:it16:0:it10::content",esn.getZipCode());
				}
				pressButton("Queue Activation");
				if(activationPhoneFlow.textboxVisible("r2:1:r1:2:it16:0:it15::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:2:it16:0:it15::content",esn.getSim());
				} else if(activationPhoneFlow.textboxVisible("r2:1:r1:4:it16:0:it15::content")){
					activationPhoneFlow.typeInTextField("r2:1:r1:4:it16:0:it15::content",esn.getSim());
				}
				pressButton("Queue Activation");
				activationPhoneFlow.clickLink("Validate and Add");
			}
						
		} else  {	
			//Check for SIM null
		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/") && activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").getValue().equalsIgnoreCase("")){
			String new_sim = simUtil.getNewSimCardByPartNumber(simPartNumber);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/",new_sim);
			esn.setSim(new_sim);
		}
		if(!"reactivation".equalsIgnoreCase(status) ){
			
			if( simPartNumber != null && simPartNumber.contains("CL7") && zip.equalsIgnoreCase("33178") ) {
				// CLARO
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/","00692");
			} else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
			}
		}
		logger.info("PIN:" + newPin);
		esnUtil.getCurrentESN().setPin(newPin);
		if("NO PIN".equalsIgnoreCase(newPin)){
			
		}else{
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
		pressButton("Validate Card");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it23/", promo);
		TwistUtils.setDelay(10);
		String error_msg1=activationPhoneFlow.getBrowser().div("d1::msgDlg::_cnt").getText() ;
		if (activationPhoneFlow.linkVisible("Close[1]")) {
			activationPhoneFlow.clickLink("Close[1]");
		}
		String error_msg2 = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg2+":"+error_msg1, activationPhoneFlow.cellVisible("Valid PIN"));
		}
		pressButton("Activate");
		TwistUtils.setDelay(10);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()){
		error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
		} 
		if(activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").isVisible()){
			error_msg = error_msg + activationPhoneFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelLabelAndMessage1/").getText();
		}
		if(activationPhoneFlow.buttonVisible("Code Accepted") || activationPhoneFlow.submitButtonVisible("Code Accepted")){
			pressButton("Code Accepted");
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.h2Visible("Transaction Summary"));
//		finishPhoneActivation(cellTech, status);
		esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "TAS Activation with PIN ["+esnUtil.getCurrentBrand()+"]");
		pressButton("Refresh");
//		Assert.assertFalse("Please check for T number for ESN: " + esnUtil.getCurrentESN().getEsn(),
//				activationPhoneFlow.getBrowser().span("/r2:\\d:ot26/").getText().startsWith("T"));
		}
	}

	public void validatePromo(String promo) throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String objid = phoneUtil.validatePromo(esnStr, promo);
		System.out.println(objid);
	}
	
	public void addESNStatusPartNumberSimPinOfCellTechZip(String status, String partNumber, String simPart, String pinPart,
			String cellTech, String zip) throws Exception {
		
		ESN toEsn = null;
		ESN fromEsn = esnUtil.getCurrentESN();
	
		if (status.equalsIgnoreCase("Active")) {
			pressButton("Contact Profile");
			pressButton("Add ESN to Account");
			toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			esnUtil.setCurrentESN(toEsn);
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("SIMPLE_MOBILE") || esnUtil.getCurrentBrand().equalsIgnoreCase("TOTAL_WIRELESS")){
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/",toEsn.getEsn());
			}else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
			}
			pressButton("Add");
			activationPhoneFlow.clickLink(toEsn.getEsn());
			checkForTransferButton();
			activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation");
			if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					phoneUtil.addSimToEsn(sim, toEsn);
					pressButton("Refresh");
				}
				activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink("Account Summary");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
			} else {
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
				fromEsn = new ESN(partNumber,
						phoneUtil.getActiveEsnByPartNumber(partNumber));
				phoneUtil.clearOTAforEsn(fromEsn.getEsn());
			}
			checkForTransferButton();
		}else if(status.equalsIgnoreCase("PastDue")){
			pressButton("Contact Profile");
			pressButton("Add ESN to Account");
		    toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			esnUtil.setCurrentESN(toEsn);
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("SIMPLE_MOBILE") || esnUtil.getCurrentBrand().equalsIgnoreCase("TOTAL_WIRELESS")){
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/",toEsn.getEsn());
			}else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
			}
			pressButton("Add");
			activationPhoneFlow.clickLink(toEsn.getEsn());
			checkForTransferButton();
			activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation");
			if (!activationPhoneFlow.spanVisible("Transaction not available for Brand / Model in session. Please use WEBCSR.")) {
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					phoneUtil.addSimToEsn(sim, toEsn);
					pressButton("Refresh");
				}
				activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart,status, cellTech, zip);
				pressButton("Refresh");
				activationPhoneFlow.clickLink("Deactivation");
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "/.*PASTDUE.*/");
				pressButton("Deactivate");
				Assert.assertTrue(activationPhoneFlow.h2Visible("Transaction Summary")||activationPhoneFlow.divVisible("Your phone has been deactivated."));
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
			
			} else {
				pressButton("Contact Profile");
				activationPhoneFlow.clickLink(fromEsn.getEsn());
				fromEsn = new ESN(partNumber,phoneUtil.getActiveEsnByPartNumber(partNumber));
				phoneUtil.clearOTAforEsn(fromEsn.getEsn());
			}
		}else if(status.equalsIgnoreCase("New")){		
			pressButton("Contact Profile");
			if (activationPhoneFlow.submitButtonVisible("Add ESN to Account")){
				pressButton("Add ESN to Account");
	        }else{
	            pressButton("Add Group Member");
	        }		
			toEsn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			esnUtil.setCurrentESN(toEsn);
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("SIMPLE_MOBILE") || esnUtil.getCurrentBrand().equalsIgnoreCase("TOTAL_WIRELESS")){
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/",toEsn.getEsn());
			}else {
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18/",toEsn.getEsn());
			}
			pressButton("Add");
			activationPhoneFlow.clickLink(toEsn.getEsn());
			checkForTransferButton();
		}
		esnUtil.getCurrentESN().setFromEsn(fromEsn);
		esnUtil.getCurrentESN().setZipCode(zip);
	}

	public void addAnotherEsnSimPinAndActivateOnCellTechZip(
			String partNumber, String simPart, String pinPart,
			String cellTech, String zipCode) throws Exception {


			ESN esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
			esnUtil.setCurrentESN(esn);
			
			String sim = simUtil.getNewSimCardByPartNumber(simPart);
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			esnUtil.getCurrentESN().setSim(sim);
			phoneUtil.addSimToEsn(sim, esn);
			
			esnUtil.getCurrentESN().setZipCode(zipCode);
			
			activatePhoneByUsingPinDependingOnStatusOfCellTechZip(pinPart, "new", cellTech, zipCode);
			
		//	activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/", esnUtil.getCurrentESN().getEsn());
		//	activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", esnUtil.getCurrentESN().getSim());
		//	activationPhoneFlow.pressSubmitButton("New Line");
		//	TwistUtils.setDelay(5);
			
			
		//	activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "1234");
	
	
	}
	
	public void activateWithWorkforcePinDependingOnStatusOfCellTech(String status, String cellTech) throws Exception {
		activateWithNewPin("Reserved", status, cellTech, "33178");
	}
	
	public void addRemainingPhonesForLinePlanOfPartWithSimForWarp(
			String numLines, String line1, String line2, String line3,
			String childSim1, String childSim2, String childSim3)
			throws Exception {

		if (!numLines.equalsIgnoreCase("1")) {
			String newLine_Esn;
			ESN childEsn = null;
			String partNumber = null;
			String simPartNumber = null;
			int actualNumofChildLines;
			ESN esn;
			int numberOfLines = Integer.parseInt(numLines);
			if (numberOfLines > 1) {
				actualNumofChildLines = numberOfLines - 1;

				esn = esnUtil.getCurrentESN();
				familyEsns = new Hashtable<String, String>();
				familyEsnsList = new ArrayList<ESN>();
				for (int i = 0; i < (actualNumofChildLines); i++) {
					String[] partNumber_list = { line1, line2, line3 };
					String[] simPartNumber_list = { childSim1, childSim2,
							childSim3 };
					if (partNumber_list[i].equalsIgnoreCase("byop")
							|| partNumber_list[i].equalsIgnoreCase("byophex")) {
						partNumber = "byop";
						childEsn = new ESN(partNumber,
								phoneUtil.getNewByopCDMAEsn());
						newLine_Esn = childEsn.getEsn();
						familyEsns.put(partNumber_list[i] + "_" + i,
								newLine_Esn);
						familyEsnsList.add(childEsn);
						esn.setFamiyESNMap(familyEsns);
						esn.setFamilyEsns(familyEsnsList);
						if (partNumber_list[i].equalsIgnoreCase("byophex")) {
							partNumber = "byophex";
							phoneUtil.convertMeidDecToHex(childEsn);
							newLine_Esn = childEsn.getHexESN();
						}
					} else if (partNumber_list[i].equalsIgnoreCase("byod")
							|| partNumber_list[i].equalsIgnoreCase("byodhex")) {
						partNumber = "byod";
						childEsn = new ESN(partNumber,
								phoneUtil.getNewByopCDMAEsn());
						newLine_Esn = childEsn.getEsn();
						familyEsns.put(partNumber_list[i] + "_" + i,
								newLine_Esn);
						familyEsnsList.add(childEsn);
						esn.setFamiyESNMap(familyEsns);
						esn.setFamilyEsns(familyEsnsList);
						if (partNumber_list[i].equalsIgnoreCase("byodhex")) {
							partNumber = "byodhex";
							phoneUtil.convertMeidDecToHex(childEsn);
							newLine_Esn = childEsn.getHexESN();
						}
					} else {
						partNumber = partNumber_list[i];
						simPartNumber = simPartNumber_list[i];
						if (partNumber_list[i].startsWith("PH")) {
							childEsn = new ESN(partNumber,
									phoneUtil.getNewByopEsn(partNumber_list[i],
											simPartNumber));
						} else {
							childEsn = new ESN(
									partNumber,
									phoneUtil
											.getNewEsnByPartNumber(partNumber_list[i]));
							if (!simPartNumber.isEmpty()) {
								String new_sim = simUtil
										.getNewSimCardByPartNumber(simPartNumber_list[i]);
								phoneUtil.addSimToEsn(new_sim, childEsn);
								TwistUtils.setDelay(5);
							}
							newLine_Esn = childEsn.getEsn();
							familyEsns.put(partNumber_list[i] + "_" + i,
									newLine_Esn);
							familyEsnsList.add(childEsn);
							esn.setFamiyESNMap(familyEsns);
							esn.setFamilyEsns(familyEsnsList);
						}

					}
				}

			}

		}

	}

	public void goBackToActivationFlow() throws Exception {
		activationPhoneFlow.clickLink("Transactions");
			activationPhoneFlow.clickLink("Activation/ Port");
	}

	public void addAnotherLinesForEsnSimPinWithCellTechZip(Integer numofLines,
			String esnPart, String simPart, String pinPart, String cellTech,
			String zipCode) throws Exception {
		String servicePlan  = pinPart;
		for(int i=1;i<=numofLines;i++){
			if(pinPart.equalsIgnoreCase("wfm_random")){
				String ServicePlanList[] = { "WFMAPPU0024", "WFMAPPU0029", "WFMAPPU0039", "WFMAPPU0049" };
				int index = new Random().nextInt(ServicePlanList.length);
				servicePlan= ServicePlanList[index];
			}
			if(pinPart.equalsIgnoreCase("sm_random")){
				String ServicePlanList[] = { "SMNAPP0025TT", "SMNAPP40030ILD", "SMNAPP0040UNL", "SMNAPP0050BBUNL","SMNAPP30060","SMNAPP20050ILDUP","SMNAPP20060ILDUP","SMNAPP20070ILDUP" };
				int index = new Random().nextInt(ServicePlanList.length);
				servicePlan= ServicePlanList[index];
			}
			addAnotherEsnSimPinAndActivateOnCellTechZip(esnPart, simPart, servicePlan, cellTech, zipCode);
		}
	}

	public void addSIMToESNForStatus(String SimPart, String status)
				throws Exception {
		if (status.equalsIgnoreCase("New")){
				String sim = simUtil.getNewSimCardByPartNumber(SimPart);
				phoneUtil.addSimToEsn(sim, esnUtil.getCurrentESN());
		}
	}

	
}