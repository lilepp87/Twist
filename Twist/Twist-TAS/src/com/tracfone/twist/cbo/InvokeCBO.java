package com.tracfone.twist.cbo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.cbo.CboUtility;
public class InvokeCBO  {

	
	private static Logger logger = LogManager.getLogger(InvokeCBO.class.getName());
	
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil cboUtil;
	private CboUtility cboUtility;
	
	public void InvokeCbo(){
		
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setCboUtility(CboUtility cboUtility) {
		this.cboUtility = cboUtility;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	public void setCboUtil(ServiceUtil cboUtil) {
		this.cboUtil = cboUtil;
	}

	public void callGetflashbyesnMethodForPart(String part) throws Exception {
		ESN currentEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
		esnUtil.setCurrentESN(currentEsn);
	//	cboUtil.callCboMethodWithRequest("test");
		cboUtility.callGetFlashbyEsn(currentEsn.getEsn());
	}
	
	public void callAddUserMethodForPartZipBrand(String part, String zip, String brand) throws Exception {
		ESN currentEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));	
		String email = "tas" + TwistUtils.createRandomEmail();
		String password = "123456";
		
		currentEsn.setZipCode(zip);
		currentEsn.setEmail(email);
		currentEsn.setZipCode(zip);
		currentEsn.setPassword(password);
		currentEsn.putInMap("securityPin", "8910");
		
		esnUtil.setCurrentESN(currentEsn);
		esnUtil.setCurrentBrand(brand);
		cboUtility.callAddUser(currentEsn, "MIAMI", "FL", "TwistLastName", "05/15/1951");
	}

	public void callSTActivateMethodForActionPinSimRefilltype(String actionType, String pinPart, String simPart, String refillType) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		if (actionType.equalsIgnoreCase("1") && !simPart.isEmpty()) {
			String sim = simUtil.getNewSimCardByPartNumber(simPart);
			esnUtil.getCurrentESN().setSim(sim);
		} 
		esnUtil.getCurrentESN().putInMap("redCard", pin);
		cboUtility.callSTActivate(esnUtil.getCurrentESN(), actionType, "1", pin, refillType);
	}
	
	public void callSTRefillMethodForPinRefilltype(String redPinPart, String refillType) throws Exception {
		String redPin = phoneUtil.getNewPinByPartNumber(redPinPart);
		cboUtility.callSTRefill(esnUtil.getCurrentESN(), "1", redPin, refillType);
		esnUtil.getCurrentESN().setPin(redPin);
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
		
		esnUtil.getCurrentESN().setTransType("CBO Redemption ["+esnUtil.getCurrentBrand()+"]");
		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setRedeemType(false);
		esnUtil.getCurrentESN().setActionType(6);
		
		esnUtil.getCurrentESN().setEnrolledDays(30);
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}
	
	public void callSTRefillForMinMethodForPinRefilltype(String redPinPart, String refillType) throws Exception {
		String redPin = phoneUtil.getNewPinByPartNumber(redPinPart);
		cboUtility.callSTRefillForMin(esnUtil.getCurrentESN().getMin(), esnUtil.getCurrentBrand(), "1", redPin, refillType);
	}
	
	public void callSTRefillCCMethodForPlanActionRefilltypeAutorefillEnrollwithpurchaseWithCardtypeCvv(String plan, String actionType, String refillType, String autoRefillType, String AREnroll, String cardType, String cvv) throws Exception {
		ESN currentEsn = esnUtil.getCurrentESN();
		String autoRefill = null;
		if(autoRefillType.equalsIgnoreCase("Yes")){
			autoRefill = "1";
		}else {
			autoRefill = "0";
		}
		String ARflag = null;
		if(AREnroll.equalsIgnoreCase("Yes")){
			ARflag = "0";
		}else {
			ARflag = "1";
		}
		cboUtility.callSTRefillCC(currentEsn.getEsn(), currentEsn.getZipCode(), actionType, "1", plan, refillType, autoRefill, esnUtil.getCurrentBrand(), esnUtil.getCurrentESN().getFromMap("securityPin"), ARflag, cardType, cvv);
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void callDeactivatePhoneMethodWithReason(String reason)throws Exception {
		
		List<ESN> allEsns = new ArrayList<ESN>();
		List<ESN> familyesns = esnUtil.getCurrentESN().getFamilyEsns();
		allEsns.add(esnUtil.getCurrentESN());
		allEsns.addAll(familyesns);
		TwistUtils.setDelay(40);
		for (ESN currentEsn : allEsns) {
			cboUtility.callDeactivatePhone(currentEsn.getEsn(), currentEsn.getMin(), reason, esnUtil.getCurrentBrand());
		}
		//String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
		//cboUtility.callDeactivatePhone(esnUtil.getCurrentESN().getEsn(), min, reason, esnUtil.getCurrentBrand());
	}
	
	public void callValidateMINOrESNWith(String esnOrMinFlag)
			throws Exception {
		if (esnOrMinFlag.equalsIgnoreCase("ESN")) {
			String esn = esnUtil.getCurrentESN().getEsn();
			cboUtility.callValidateMINorESN(esnOrMinFlag, esn, esnUtil.getCurrentBrand());
		} else if (esnOrMinFlag.equalsIgnoreCase("MIN")) {
			String min = phoneUtil.getMinOfActiveEsn(esnUtil.getCurrentESN().getEsn());
			cboUtility.callValidateMINorESN(esnOrMinFlag, min, esnUtil.getCurrentBrand());
		} 	
	}
	
	public void callValidateESN() throws Exception {
		cboUtility.callValidateESN(esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentBrand()); 
	}
	
	public void callValidateMINCase() throws Exception {
		cboUtility.callValidateMINCase(esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentBrand()); 
	}

	public void completePhoneActivationForAndStatus(String cellTech, String status)
			throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		System.out.println(currEsn.getEsn());
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "CBO Activation["+esnUtil.getCurrentBrand()+"]");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
	}
	
	public void callVerifyTechnologyForPartSIMBrandZipcode(String part, String simPart, String brand, String zip) throws Exception {
		ESN currentEsn = new ESN(part, phoneUtil.getNewEsnByPartNumber(part));
		String sim = simUtil.getNewSimCardByPartNumber(simPart);
		esnUtil.setCurrentESN(currentEsn);
		esnUtil.setCurrentBrand(brand);
		esnUtil.getCurrentESN().setSim(sim);
		esnUtil.getCurrentESN().setZipCode(zip);
		cboUtility.callVerifyTechnology(esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentESN().getZipCode(), esnUtil.getCurrentBrand(), esnUtil.getCurrentESN().getSim()); 
	}

	public void callCreateContact() throws Exception {		
		cboUtility.callCreateContact(esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentESN().getZipCode(), esnUtil.getCurrentBrand());
	}

	public void callGenCodesForPin(String pinPart) throws Exception {
		String redcard = phoneUtil.getNewPinByPartNumber(pinPart);
		cboUtility.callGenCodes(esnUtil.getCurrentESN().getEsn(), esnUtil.getCurrentESN().getEmail(), esnUtil.getCurrentESN().getZipCode(), esnUtil.getCurrentBrand(), redcard, esnUtil.getCurrentESN().getSim(), esnUtil.getCurrentESN().getCommerceId());
	}

	public void callValidatePhoneUpgradeToPartSimBrand(String partNumber, String simPart, String brand) throws Exception {
		ESN fromESN = esnUtil.getCurrentESN();
		String zipcode = esnUtil.getCurrentESN().getZipCode();
		ESN toESN = null;
		if (partNumber.matches("PH(SM|ST|TC|NT|TF|GS).*")) {
			toESN = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,simPart));
			toESN.setSim(phoneUtil.getSimFromEsn(toESN.getEsn()));
		} else if ("byop".equalsIgnoreCase(partNumber)) {
			toESN = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
		} else{
			toESN = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
		}		 
		String toSIM = simUtil.getNewSimCardByPartNumber(simPart);
		esnUtil.setCurrentESN(toESN);
		esnUtil.getCurrentESN().setFromEsn(fromESN);
		esnUtil.getCurrentESN().setSim(toSIM);
		esnUtil.getCurrentESN().setZipCode(zipcode);
		esnUtil.setCurrentBrand(brand);
		cboUtility.callValidateUpgrade(fromESN.getEsn().toString(), toESN.getEsn().toString(), toSIM, zipcode, brand);
	}

	public void callPhoneUpgradeReqWithPin(String pinPart) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		ESN currentESN = esnUtil.getCurrentESN();
		cboUtility.callPhoneUpgrade(currentESN.getFromEsn().getEsn().toString(), currentESN.getEsn().toString(), currentESN.getSim(), currentESN.getZipCode(), esnUtil.getCurrentBrand(), pin);
	}
	
	public void callCBOForBI() throws Exception {
		ESN fromEsn = esnUtil.getCurrentESN().getFromEsn();
		//System.out.println(fromEsn.getEsn());
		cboUtility.simulateBalanceInquiry(fromEsn.getEsn());		
	}
	
	public void callValidateRedCardBatch(String pinPart) throws Exception {
		esnUtil.getCurrentESN().putInMap("pinPart", pinPart);
		cboUtility.callValidateRedCard(esnUtil.getCurrentESN().getEsn());
	}
	public void callServiceUpdateTicketAndSendMessage() throws Exception {
		TwistUtils.setDelay(10);
		cboUtility.callServiceUpdateTicketAndSendMessage();
	}

}