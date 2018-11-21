package com.tracfone.twist.tossutilops;

// JUnit Assert framework can be used for verification

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.*;
import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class TossUtil {
	
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private static final String NEW_STATUS = "New";
	private static final String ACTIVE_STATUS = "Active";
	private static final String USED_STATUS = "Used";
	private static final String REFURB_STATUS = "Refurbished";
	private static final String PAST_DUE_STATUS = "Past Due";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static Logger logger = LogManager.getLogger(TossUtil.class.getName());
	public TossUtil() {

	}

	public void goToTossUtil() throws Exception {
		myAccountFlow.clickLink(props.getString("TAS.Support"));
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
	}

	public void searchForEsn() throws Exception {
		myAccountFlow.clickLink("Incoming Call");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", esnUtil.getCurrentESN().getEsn());
		clickButton("Search Service");
		checkForTransferButton();
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
//CHNAGE MODEL
	public void clickOnChangeModel() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ChangeModel"));
	}

	public void selectNewTechnologyBrandPartNumber(String cellTech, String brand, String newpart) throws Exception {
		clickButton("Refresh");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r1:\\d:soc2/", cellTech);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r1:\\d:soc1/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4::content/", newpart);
		clickButton(props.getString("TAS.Search"));
		myAccountFlow.clickCellMessage(newpart);
		clickButton(props.getString("TAS.ChangeModel"));
	}

	public void confirmChangedModelTo(String newpart) throws Exception {
		boolean confirmMessage = (myAccountFlow.cellVisible("Model Updated - New Model (" + newpart + ")") || myAccountFlow
				.cellVisible("Updated Model and Removed Line - New Model (" + newpart + ")"));
		Assert.assertTrue(confirmMessage);
	}

//CHANGE DEALER
	public synchronized void getEsnForPartOfStatus(String partNumber, String status) throws Exception {
		
		ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getPastDueEsnByPartNumber(partNumber));
		} else if (REFURB_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getRefurbEsnByPartNumber(partNumber));
		} else if (USED_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getUsedEsnByPartNumber(partNumber));
		} else if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));
		} else {
			throw new IllegalArgumentException("Unknown status: " + status);
		}
		esnUtil.setCurrentESN(esn);
	}
	
	public synchronized void getEsnForPartSimOfStatus(String partNumber,String simPart, String status) throws Exception {
				
		ESN esn;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			if (partNumber.matches("PH(SM|ST|TC|NT|TF).*")) {
				esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
				esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
			} else if ("byop".equalsIgnoreCase(partNumber)) {
				esn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
			} else {
				esn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
				if (!simPart.isEmpty()) {
					String sim = simUtil.getNewSimCardByPartNumber(simPart);
					if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
						sim = simUtil.getNewSimCardByPartNumber(simPart);
						esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
					}
					phoneUtil.addSimToEsn(sim, esn);
				}
			}
		} else if (PAST_DUE_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getPastDueEsnByPartNumber(partNumber));
		} else if (REFURB_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getRefurbEsnByPartNumber(partNumber));
		} else if (USED_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getUsedEsnByPartNumber(partNumber));
		} else if (ACTIVE_STATUS.equalsIgnoreCase(status)) {
			esn = new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));
		} else {
			throw new IllegalArgumentException("Unknown status: " + status);
		}
		esnUtil.setCurrentESN(esn);
	}

	public void clickOnChangeDealer() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ChangeDealer"));
	}

	
	public void changeNewDealerFor(String Dealer) throws Exception {		
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ChangeDealer"));
		if(Dealer.equals("USAC-DEALER")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5::content/", "24920");// USAC-DEALER
		}else{
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5::content/", "61374");// USAC-SL_FIELD
		}
		myAccountFlow.pressSubmitButton("Search");
		myAccountFlow.pressSubmitButton("Change Dealer");
		myAccountFlow.pressSubmitButton("Refresh");
		esnUtil.setCurrentBrand("SAFELINK");

	}

	
	public void activationForPartSimLidForDealer(String simPart, String lid, String Dealer) throws Exception {
		//simPartNumber = simPart; 
		String esnStr =  esnUtil.getCurrentESN().getEsn();
		if (Dealer.equals("USAC-DEALER")){
			String SLURL=props.getString("TAS.HomeUrl");
			myAccountFlow.navigateTo(props.getString("SL.BrightpointURL"));			
			if (SLURL.contains("testtas")||SLURL.contains("TESTTAS")){
				myAccountFlow.typeInTextField("username", "itquser");
				myAccountFlow.typeInPasswordField("password", "abcd1234!");	
			}else{
				myAccountFlow.typeInTextField("username", "cbo");
				myAccountFlow.typeInPasswordField("password", "abc123");
			}

			myAccountFlow.pressSubmitButton("login");
			String ticketid= phoneUtil.getTicketIDforLID(lid);
			System.out.println(ticketid);
			myAccountFlow.typeInTextField("ticketid", ticketid);
			myAccountFlow.typeInTextField("esn",esnStr);
			
	    	//esn = new ESN(partNumber,phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simPart.isEmpty()) {
				String sim =  esnUtil.getCurrentESN().getSim();
				myAccountFlow.typeInTextField("sim", sim );
			}				
			myAccountFlow.pressSubmitButton("activate");
			TwistUtils.setDelay(10);
			String actStr = myAccountFlow.getBrowser().div("msgBoxes").getText();
			Assert.assertTrue(actStr.contains("Activation request sent successfully.")||actStr.contains("complete your activation"));
		}else {
			myAccountFlow.navigateTo(props.getString("SL.FieldActivationURL"));
			myAccountFlow.typeInTextField("i_lifelineId", lid);
			myAccountFlow.typeInTextField("i_esn",esnStr);
			
			System.out.println("simPart" + simPart);
			if (!simPart.isEmpty()) {
				String sim =  esnUtil.getCurrentESN().getSim();;
				myAccountFlow.typeInTextField("i_sim", sim );
			}				
			System.out.println("Press Button Activate :" + myAccountFlow.buttonVisible("Activate"));
			System.out.println("Press SubmitButton Activate :" + myAccountFlow.submitButtonVisible("Activate"));
			System.out.println("Press Button b_submit :" + myAccountFlow.buttonVisible("b_submit"));
			myAccountFlow.selectCheckBox("i_agreement");
			myAccountFlow.pressButton("b_submit");
			TwistUtils.setDelay(10);
		}
			
	}


	
	
	public void selectNewDealer(String newDealer) throws Exception {
//		clickButton(props.getString("TAS.Refresh"));
		//myAccountFlow.clickCellMessage(newDealer);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5::content/", newDealer);
		clickButton("Search");
		clickButton(props.getString("TAS.ChangeDealer"));
		Assert.assertTrue(myAccountFlow.cellVisible("Dealer Changed - New Dealer (" + newDealer + ")") || myAccountFlow.cellVisible("Dealer Changed - New Dealer (0)")  );
		clickButton("Refresh");
	}

//POSA REPORT
	public void clickOnPOSAReport() throws Exception {
		myAccountFlow.clickLink(props.getString("TAS.PosaReport"));
	}

	public void enterStartDateEndDateAsCurrentDateForReport(String startDate, String reportType) throws Exception {
		String currentDate = dateFormat.format(new Date());
		logger.info(currentDate);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:0:r\\d:\\d:id1/", startDate);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:0:r\\d:\\d:id2/", currentDate);
		if (reportType.equalsIgnoreCase("Detailed")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
		} else if (reportType.equalsIgnoreCase("Summary")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
	}

	public void runAndCheckReport() throws Exception {
		if(getButtonType(props.getString("TAS.Run"))){
			clickButton(props.getString("TAS.Run"));
		}else if(getButtonType(props.getString("TAS.Run[1]"))){
			clickButton(props.getString("TAS.Run[1]"));
		}
		
		TwistUtils.setDelay(14);
		Assert.assertTrue(myAccountFlow.divVisible("Log Date"));
	}

//AIRTIME
	public void clickOnAirtime() throws Exception {
		myAccountFlow.clickLink(props.getString("TAS.AirTime"));
	}

	public void enterAirtimeCardNumberAndCheckForRetrievedInfo(String partNumber) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(partNumber);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it29::content/", newPin);
		clickButton(props.getString("TAS.Search"));
		Assert.assertTrue(myAccountFlow.cellVisible(newPin));
	}

//RESET POSA
	public void clickOnResetPOSAPhone() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ResetPOSA"));
	}

//reset phone
	public void goToResetPhone() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ResetPhone"));
	}

	public void resetStatusToAndConfirmChangeInStatus(String newStatus) throws Exception {
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", newStatus);
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/").setValue("test");
		clickButton("Reset");
		Assert.assertTrue(myAccountFlow.cellVisible("Phone has been reset to " + newStatus));
	}

//RESET COUNTER
	public void goToResetCounter() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ResetCounter"));
	}

	public void enterNewSequence(String newSequence) throws Exception {
		clickButton(props.getString("TAS.Refresh"));
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/", newSequence);
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it\\d/").setValue("test");
	}

	public void updateCounterAndCheckForSequenceChangeTo(String newSequence) throws Exception {
		clickButton(props.getString("TAS.UpdateCounter"));
		boolean confirmMessage = (myAccountFlow.cellVisible("Sequence has been raised. New value (" + newSequence + ")")
				|| myAccountFlow.cellVisible("	ESN Sequence is the same as the existing sequence."));
		Assert.assertTrue(confirmMessage);
	}

//  Change Expiration Date
	public void goToChangeExpirationDate() throws Exception {
		myAccountFlow.clickCellMessage(props.getString("TAS.TossUtil"));
		myAccountFlow.clickLink(props.getString("TAS.ChangeExpDate"));
	}

	public void enterExpirationDate(String NewExpDate) throws Exception {
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", NewExpDate);
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/").setValue("test");
		clickButton(props.getString("TAS.ChangeExpDate"));
	}

	public void checkForConfirmationMessage() throws Exception {
		Assert.assertTrue(myAccountFlow.getBrowser().table("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:plam1/").containsText("CHANGED DATE FROM:"));
	}

	public void clickButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
	}

	// RESET POSA PHONE
	public void enterSerialNumberAndCheckForResetStatus() throws Exception {
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").setValue("Reset POSA Phones ITQ Testing");
		clickButton("Reset POSA");
	    Assert.assertTrue(myAccountFlow.cellVisible("RESET POSA : ESN Update Complete"));
	}
	
	// Reset ESN Status for POSA Phone
	public void resetTheESNStatusForPOSAPhone() throws Exception {
		String currEsn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.updateEsnStatustoInactive(currEsn);
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

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}


}