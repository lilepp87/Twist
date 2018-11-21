package com.tracfone.twist.byop;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.junit.Assert;
import com.tracfone.twist.byop.CreateContact;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RegisterBYOP extends CreateContact {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	public String simPartNumber;
	private static final String HEX_VALUE = "HEX" ;
	private static final String DECIMAL_VALUE = "DECIMAL";
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");
	private String error_msg;
	String fromEsn;
	public RegisterBYOP() {
		
	}
	
	public void goToBYOPCDMAVerizonRegistration() throws Exception {
		activationPhoneFlow.clickLink("BYOP Registration");
	}

	public void enterNetworkAccessCode(String accessPin) throws Exception {
		if(!"Free".equalsIgnoreCase(accessPin)){
			clickButton("Register & Activate");
			activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Customer has a Network Access Code");
			String pin = phoneUtil.getNewPinByPartNumber(accessPin);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it35/", pin);
		}
	//	clickButton("Validate Eligibility Coverage");
	}

	public void purchaseAndCheckForConfirmation() throws Exception {
		clickButton("Submit");
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb9/");
		TwistUtils.setDelay(15);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").isVisible()){
			error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.h2Visible("Transaction Summary"));
	}
	
	public void clickButton(String buttonName) {
		if (activationPhoneFlow.buttonVisible(buttonName)) {
			activationPhoneFlow.pressButton(buttonName);
		} else {
			activationPhoneFlow.pressSubmitButton(buttonName);
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

	public void getWorkforcePinForRegistration() throws Exception {
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "Customer has a Network Access Code");
		clickButton("Workforce Pin");
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/", "Redemption Failures");
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue("ITQ-WORKFORCE PIN-TEST");
		clickButton("Get Pin");
	}
	private boolean buttonVisible(String button) {
		return activationPhoneFlow.buttonVisible(button) || activationPhoneFlow.submitButtonVisible(button);
	}

	public void checkForFromEsn() throws Exception {

		this.fromEsn = esnUtil.getCurrentESN().getEsn();

	}
	private boolean isTextFieldEnabled(String buttonType) {
		if (activationPhoneFlow.getBrowser().textbox(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
			return false;
		} else {
			return true;
		}
	}
	public void enterEsnZipAndCheckForEligibiltyWithCarrier(String esnType, String zipCode,String Carrier) throws Exception {
		ESN esn ;
	    if(Carrier.equalsIgnoreCase("RSS") || Carrier.equalsIgnoreCase("verizon") ){
			activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2::content/").choose("Verizon");
		}else if (Carrier.equalsIgnoreCase("SPRINT")){
			activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2::content/").choose("Sprint");
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			esn = new ESN(esnType, phoneUtil.getNewByopCDMAEsn());
		}else{
			esn = new ESN("BYOP", phoneUtil.getNewByopCDMAEsn());
		}
		esnUtil.setCurrentESN(esn);
		esn.setZipCode(zipCode);
		if (HEX_VALUE.equalsIgnoreCase(esnType) || esnType.matches(".*HEX")) {
			String hex_esn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6::content/", hex_esn);
		} else {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6::content/", esn.getEsn());
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it11::content/", zipCode);
		clickButton("Validate Eligibility");
		
	}

	public void enterYourDeviceTypeInfoLTEIphone5AndCheckCustomerHaveSimForTrio(
			String isActive,String deviceType, String isLTE, String isIphone, String custHaveSim,String carrier ,String IsTrio, String IsHd) throws Exception {
	    TwistUtils.setDelay(8);
		phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(), carrier, isActive,isLTE,isIphone,IsHd);
		TwistUtils.setDelay(15);
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			clickButton("Register & Activate");
		}else{
			clickButton("Validate Eligibility"); 
		}
		TwistUtils.setDelay(5);
		if(buttonVisible("OK")){
			clickButton("OK");
		}
		
		if(buttonVisible("ACCEPT")){
			clickButton("ACCEPT");
		}
		TwistUtils.setDelay(15);
		
		if(activationPhoneFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1/")){ 
			activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:selectOneChoice1::content/", deviceType );
		}
		//if(carrier.equalsIgnoreCase("Sprint")){
		//	activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", isLTE);
		//	activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", isIphone);
		//	if (isIphone.equalsIgnoreCase("Yes")) {
		//		String newSim = TwistUtils.generateRandomSim();
		//		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it28/", newSim);
		//	}
		if(isLTE.equalsIgnoreCase("YES")){
			if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") &&  !isIphone.equalsIgnoreCase("Yes")&& IsTrio.equalsIgnoreCase("No") ){
				String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9DD");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
				
			}else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("Yes")&& IsTrio.equalsIgnoreCase("No") ){
				String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			}
			else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("Yes") && IsTrio.equalsIgnoreCase("Yes") ){
				String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			}
			else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && !isIphone.equalsIgnoreCase("Yes") && IsTrio.equalsIgnoreCase("Yes")){
				String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
				activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			}
	
		/*if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS")){
			String newSim = TwistUtils.generateRandomSim();
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", newSim);
		//	clickButton("Register & Activate");
		}else */else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") &&  !isIphone.equalsIgnoreCase("Yes")&& IsTrio.equalsIgnoreCase("No") ){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9DD");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			
		}else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("Yes")&& IsTrio.equalsIgnoreCase("No") ){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
		}
		else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("Yes") && IsTrio.equalsIgnoreCase("Yes") ){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
		}
		else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && !isIphone.equalsIgnoreCase("Yes") && IsTrio.equalsIgnoreCase("Yes")){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
		}
		
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			clickButton("Register & Activate");
		}
		}		
	}

	
	public void enterYourPhoneTotalWirelessInfoLTEIphone5AndCheckCustomerHaveSimFor(
			String isActive,String isLTE, String isIphone, String custHaveSim,String carrier, String IsHd) throws Exception {
		TwistUtils.setDelay(8);
		phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(), carrier, isActive,isLTE,isIphone,IsHd);
		TwistUtils.setDelay(6);
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")) {
			if (buttonVisible("Register & Activate")) {
				clickButton("Register & Activate");
			} else if (buttonVisible("Register Free Upgrade")) {
				clickButton("Register Free Upgrade");
			}

		}
		TwistUtils.setDelay(5);
		
		if(buttonVisible("OK")){
			clickButton("OK");
			}
		else if(buttonVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:dlg12::ok/")){
	     	
			clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:dlg12::ok/");
		}
		if(carrier.equalsIgnoreCase("Sprint")){
		//	activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", isLTE);
		//	activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", isIphone);
		//	if (isIphone.equalsIgnoreCase("Yes")) {
		//		String newSim = TwistUtils.generateRandomSim();
		//		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it28/", newSim);
		//	}
		}/*else if(carrier.equalsIgnoreCase("RSS") && custHaveSim.equalsIgnoreCase("No") && esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			
			/*if(isLTE.equalsIgnoreCase("yes") && isIphone.equalsIgnoreCase("yes")){
				activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
			}else if(isLTE.equalsIgnoreCase("yes") && isIphone.equalsIgnoreCase("no")){
				activationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
				//Ship Dual SIM
				activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4::content/", "DUAL");
			
		}
		*/
		/*else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			
		}
		*/
		else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("Yes") && esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			if(activationPhoneFlow.selectionboxVisible("/r2:\\d:r\\d:\\d:soc4/")){
			activationPhoneFlow.getBrowser().select("/r2:\\d:r\\d:\\d:soc4/").choose(("Upgrade from BYOP CDMA"));
			}
	//		if(activationPhoneFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it50/")){
	//		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it50/", fromEsn );
	//		}
			if (buttonVisible("Register & Activate")) {
				clickButton("Register & Activate");
			} else if (buttonVisible("Register Free Upgrade")) {                                  
				clickButton("Register Free Upgrade");
				TwistUtils.setDelay(7);
			}
			
		}	
		else if (custHaveSim.equalsIgnoreCase("yes") && carrier.equalsIgnoreCase("RSS") && isIphone.equalsIgnoreCase("yes")){
			String lteSim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it7/", lteSim);
			clickButton("Register & Activate");
		}
		}
        
	public void returnToUpgradeFlowWithMintransfer(String minTransfer) throws Exception {
		String minTransfer_button;
		clickButton("Return to Upgrade Flow");
		if(minTransfer.equalsIgnoreCase("Yes")){
			minTransfer_button = "Process Transaction (min)";
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_0/");
		}else {
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor2:_1/");
			minTransfer_button = "Transfer Service (no min)";
		}
		
		clickButton("Continue");
		clickButton(minTransfer_button);
	}
	
	public void submitForRegistration() throws Exception {
		if(buttonVisible("OK")){
			clickButton("OK");
		}else if(buttonVisible("OK[1]")){
			clickButton("OK[1]");
		}
		clickButton("Submit");
	}

	public void checkConfirmation() throws Exception {
		TwistUtils.setDelay(5);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").isVisible()){
			error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").getText();
		}
		if(activationPhoneFlow.cellVisible("/.*DEVICE HAS BEEN SUCCESSFULLY REGISTERED*/")){
			
		}else if(activationPhoneFlow.cellVisible("/.*CREATE A SIM EXCHANGE CASE*/")){
			createSimCaseIfNeeded();
		}else{
			Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, false);
		}
	
	}

	public void selectPurchaseNACOption() throws Exception {
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Purchase a Network Access Code");
		clickButton("Add New Credit Card");
	}

	public void submitForRegistrationWithPurchase() throws Exception {
		//clickButton("Validate Eligibility Coverage");
		//submitForRegistration();
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb13/");
	}

	public void chooseUpgradeOptionWithDeviceTypeLTEIphoneForEsnAndCustHaveSIMTrio(String deviceType, String isLTE, String isIphone, String carrier, String esnType,String custHaveSim, String trio, String IsHD) throws Exception {
		String fromEsn = esnUtil.getCurrentESN().getEsn();
		activationPhoneFlow.clickLink("Credit Cards");
		goToBYOPCDMAVerizonRegistration();
		enterEsnZipAndCheckForEligibiltyWithCarrier(esnType, "33178", carrier);
		enterYourDeviceTypeInfoLTEIphone5AndCheckCustomerHaveSimForTrio( "Inactive", isLTE,  isIphone, "No" , carrier, deviceType,trio,IsHD);
	//	clickButton("Validate Eligibility Coverage");
		if(buttonVisible("OK")){
			clickButton("OK");
		}
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Upgrade from BYOP CDMA");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it50/", fromEsn);
		submitForRegistration();
	}

	public void enterEsnZipAndCheckForEligibilty(String string1, Integer integer2,
			String string3) throws Exception {
	
	}

	public void createSimCaseIfNeeded() throws Exception {
		clickButton("Create SIM Case");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it18::content/").setValue("TwistFirstName");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it19/").setValue("TwistLastName");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/").setValue("1295 Charleston road");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it6/").setValue(TwistUtils.createRandomEmail());
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it5/").setValue("94043");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").setValue("Mountain view");
		activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1/").choose("CA");
		activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it23/").setValue("9999999999");
		activationPhoneFlow.getBrowser().submit("Save & Continue").click();
		Assert.assertTrue(activationPhoneFlow.labelVisible("Id Number"));
		
		String domain="SIM CARDS";
		
		activationPhoneFlow.clickLink("Part Request");
		activationPhoneFlow.clickCellMessage(domain);
		
		clickButton("Ship");
		String partNum = activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:r\\d:\\d)?:xReplPartNumId::content/").value();
		if(domain.equalsIgnoreCase("PHONES")){
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", phoneUtil.getNewEsnByPartNumber(partNum));
		}else if (domain.equalsIgnoreCase("SIM CARDS")){
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32/", simUtil.getNewSimCardByPartNumber(partNum));
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31/", "1234567890");
		clickButton("Ship[1]");
		Assert.assertTrue("PART REQUEST SHIPMENT FAILED",activationPhoneFlow.cellVisible("SUCCESS"));
		activationPhoneFlow.clickLink("Close[1]");
	}

	public void activateTheESNForCarrierAndZipAndPin(String carrier, String zip, String pinPart)
			throws Exception {
		
		String newPin;
		clickButton("New Customer");
		activationPhoneFlow.typeInTextField("/r\\d:\\d:s12:it1/", esnUtil.getCurrentESN().getEsn());
		clickButton("Search Service");
		activationPhoneFlow.clickLink("Transactions");
		activationPhoneFlow.clickLink("Activation");
		activationPhoneFlow.typeInTextField("/r2:\\d:r1:\\d:it2/", zip);
		
		if(!pinPart.equalsIgnoreCase("Free Trial")){
			newPin = phoneUtil.getNewPinByPartNumber(pinPart);
			esnUtil.getCurrentESN().setPin(newPin);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
			clickButton("Validate Card");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1/", newPin);
		}
		
		clickButton("Validate Card");
		clickButton("Activate");
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error in activation", activationPhoneFlow.h2Visible("Transaction Summary"));
		esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), "CDMA", "New", "TAS Activation with PIN ["+esnUtil.getCurrentBrand()+"]");
		clickButton("Refresh");
	}

	public void goToBYOPEligibility() throws Exception {
		activationPhoneFlow.clickLink("Support");
		activationPhoneFlow.clickLink("BYOP Eligibility");
	}
	
	public void enterBrandEsnZipAndCheckForEligibiltyWithCarrier(String brand, String esnType, String zipCode,String Carrier) throws Exception {
		ESN esn ;
	    activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/").choose(brand);
	    if(Carrier.equalsIgnoreCase("RSS") || Carrier.equalsIgnoreCase("Verizon")){
	    	activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2::content/").choose("Verizon");
	    }else{
	    	activationPhoneFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2::content/").choose(Carrier);
	    }
	    
	
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")){
			esn = new ESN(esnType, phoneUtil.getNewByopCDMAEsn());
		}else{
			esn = new ESN("BYOP", phoneUtil.getNewByopCDMAEsn());
		}
		esnUtil.setCurrentESN(esn);
		esn.setZipCode(zipCode);
		if (HEX_VALUE.equalsIgnoreCase(esnType) || esnType.matches(".*HEX")) {
			String hex_esn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", hex_esn);
		} else {
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", esn.getEsn());
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", zipCode);
		clickButton("Validate Eligibility");
		TwistUtils.setDelay(5);
		if(Carrier.equalsIgnoreCase("AT&T") || Carrier.equalsIgnoreCase("T-Mobile")){
			System.out.println("INSIDE 1");
			Assert.assertTrue(activationPhoneFlow.cellVisible("/.*YOUR DEVICE IS COMPATIBLE WITH OUR BYOP PROGRAM. WE NEED TO VERIFY COVERAGE IN YOUR ZIP CODE.*/"));
		}else if((!(brand.equalsIgnoreCase("NET10") || brand.equalsIgnoreCase("STRAIGHT_TALK"))) && Carrier.equalsIgnoreCase("Sprint")){
			System.out.println("INSIDE 2");
			Assert.assertTrue(activationPhoneFlow.cellVisible("SELECTED CARRIER IS NOT CURRENTLY SUPPORTED"));
		}else if(((brand.equalsIgnoreCase("SIMPLE_MOBILE") || brand.equalsIgnoreCase("TELCEL"))) && Carrier.equalsIgnoreCase("RSS")){
			System.out.println("INSIDE 3");
			Assert.assertTrue(activationPhoneFlow.cellVisible("NO COVERAGE IN THE ZIP CODE ENTERED"));
		}
		
		
	}

	public void updateYourPhoneInfoLTEIphone5AndCheckCustomerHaveSimFor(
			String isActive,String isLTE, String isIphone, String custHaveSim,String carrier, String IsHD) throws Exception {
		
		if(activationPhoneFlow.cellVisible("PENDING")){
			System.out.println("INSIDE 3");
			TwistUtils.setDelay(8);
			phoneUtil.finishCdmaByopIgate(esnUtil.getCurrentESN().getEsn(), carrier, isActive,isLTE,isIphone,IsHD);
			TwistUtils.setDelay(15);
			clickButton("Validate Eligibility"); 
			Assert.assertTrue(activationPhoneFlow.cellVisible("/.*ELIGIBLE.*/"));
		}
				
	}
	
	public void pressButton(String btnName){
		if(activationPhoneFlow.buttonVisible(btnName)){
			activationPhoneFlow.pressButton(btnName);
		}else if(activationPhoneFlow.submitButtonVisible(btnName)){
			activationPhoneFlow.pressSubmitButton(btnName);
		}
	}
	
	public void goToSafelinkHomePage() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("SL.HomeUrl")); 
		//Click link apn Settings
		activationPhoneFlow.clickLink("APN Settings");
	}

	public void goToSafelinkBYOPAPNSettingsPage() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("SLAPN.Url"));
		Assert.assertTrue(activationPhoneFlow.labelVisible("How to Update Data Settings"));
		Assert.assertTrue(activationPhoneFlow.getBrowser().paragraph("You will need to update these settings in order to send MMS and access the internet.").isVisible());
	}

	public void enterEsnAndOtherDetails(String type, String model, String version) throws Exception {
		//System.out.println(esnUtil.getCurrentESN());
		TwistUtils.setDelay(10);
		//String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());

		activationPhoneFlow.typeInTextField("phone_num", "9548742386");
		clickButton("ENTER ");
		if (type.equalsIgnoreCase("OPERATING SYSTEM")) {
			activationPhoneFlow.pressButton("OPERATING SYSTEM");
			activationPhoneFlow.chooseFromSelect("os_type", model);
			activationPhoneFlow.chooseFromSelect("select_os_version", version);
		} else {
			activationPhoneFlow.pressButton("HANDSET MANUFACTURING");
			activationPhoneFlow.chooseFromSelect("hs_type", model);
			activationPhoneFlow.chooseFromSelect("select_hs_version", version);
		}
		clickButton("SEARCH");	
	}

	public void checkForSettings() throws Exception {
		TwistUtils.setDelay(5);
		Assert.assertTrue(activationPhoneFlow.labelVisible("APN Settings"));
		Assert.assertTrue(activationPhoneFlow.labelVisible("APN Instructions"));
		Assert.assertTrue(activationPhoneFlow.buttonVisible("SEND SMS"));
		activationPhoneFlow.typeInTextField("input_email",TwistUtils.createRandomEmail());
		clickButton("SEND BY EMAIL");		
		Assert.assertTrue(activationPhoneFlow.divVisible("Email was sent successfully"));
	
	}
	
}