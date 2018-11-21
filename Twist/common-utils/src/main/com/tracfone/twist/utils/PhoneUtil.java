package com.tracfone.twist.utils;

import java.util.ArrayList;
import java.util.List;

import com.tracfone.twist.api.phone.PhoneTwistDao;

public class PhoneUtil {

	private PhoneTwistDao phoneTwistDao;
	
	public void setEsnInstallDatetoPast(String esn, int days){
		phoneTwistDao.setEsnInstallDatetoPast(esn,days);
	}
	
	public void setEsnServiceEndDatetoPast(String esn, int days){
		phoneTwistDao.setEsnServiceEndDatetoPast(esn,days);
	}
	
	public String getParentCarrierName(String esn) {
		 return phoneTwistDao.getParentCarrierName(esn);
	}

	public void insertIntob2bTable(List b2bdata) {
		 phoneTwistDao.insertIntob2bTable(b2bdata);
	}
	
	public void b2bPromoORG(String orgName, String promo) {
		 phoneTwistDao.b2bPromoORG(orgName, promo);
	}
	
	public String getOrgIdByOrgName(String orgName) {  
		return phoneTwistDao.getOrgIdByOrgName(orgName);
	}
	
	public String getStatusOfESNFromStage(String esn){
		return phoneTwistDao.getStatusOfESNFromStage(esn);
	}
	
	public String getEnrollmentStatusforEsn(String esn){
		return phoneTwistDao.getEnrollmentStatusforEsn(esn);
	}
	public String getESNForIMSI(String partNumber, String sim) {
		return phoneTwistDao.getESNForIMSI(partNumber, sim);
	}
	
	public String getNewEsnByPartNumber(String partNumber) {
		return phoneTwistDao.getNewEsnByPartNumber(partNumber);
	}

	public String getActiveEsnByPartNumber(String partNumber) {
		return phoneTwistDao.getActiveEsnByPartNumber(partNumber);
	}
	public String getActiveEsnByBrand(String brand) {
		return phoneTwistDao.getActiveEsnByBrand(brand);
	}

	public String getEsnForPort(String partNumber) {
		return phoneTwistDao.getEsnForPort(partNumber);
	}
	
	public String getActiveSTEsnByPartNumber(String partNumber) {
		return phoneTwistDao.getActiveSTEsnByPartNumber(partNumber);
	}

	public String getNewPinByPartNumber(String partNumber) {
		return phoneTwistDao.getNewPinByPartNumber(partNumber);
	}

	public int finishCdmaPhoneActivation(String min, String esn) {
		return phoneTwistDao.finishCdmaPhoneActivation(min, esn);
	}

	public int finishGsmPhoneActivation(String min, String esn) {
		return phoneTwistDao.finishGsmPhoneActivation(min, esn);
	}

	public int updateOTAPending(String esn) {
		return phoneTwistDao.getUpdateOTAResults(esn);
	}

	public String getMinOfActiveEsn(String esn) {
		return phoneTwistDao.getMinOfActiveEsn(esn);
	}

	public boolean deactivateEsn(String esn) {
		return phoneTwistDao.deactivateEsn(esn);
	}

	public boolean removeEsnFromSTAccount(String esn) {
		return phoneTwistDao.removeEsnFromSTAccount(esn);
	}

	public String getReservedLine(String esn) {
		return phoneTwistDao.getReservedLine(esn);
	}

	public boolean addPinToQueue(String esn, String pin) {
		return phoneTwistDao.addPinToQueue(esn, pin);
	}
	
	public boolean addSimToEsn(String sim, ESN toEsn) {
		return phoneTwistDao.addSimToEsn(sim, toEsn);
	}
	
	public boolean setDateInFuture(String esn) {
		return phoneTwistDao.setDateInFuture(esn);
	}

	public boolean setDateInPast(String esn) {
		return phoneTwistDao.setDateInPast(esn);
	}

	public boolean clearOTAforEsn(String esn) {
		return phoneTwistDao.clearOTAforEsn(esn);
	}
	
	public void updateEsnStatustoInactive(String esn) {
		phoneTwistDao.updateEsnStatustoInactive(esn);
	}
	
	public void updateBIRecord(String esn) {
		phoneTwistDao.updateBIRecord(esn);
	}
	
	public void setPhoneTwistDao(PhoneTwistDao phoneTwistDao) {
		this.phoneTwistDao = phoneTwistDao;
	}

	public void deactivateSMEsn(String esn) {
		phoneTwistDao.deactivateSMEsn(esn);
	}

	public void runIGateIn() {
		this.phoneTwistDao.runIGateIn();
	}

	public void finishPhoneActivationWithSameMin(String esn) {
		this.phoneTwistDao.finishPhoneActivationWithSameMin(esn);
	}

	public String getHexEsnFromDecimalEsn(String esn) {
		return this.phoneTwistDao.getHexEsnFromDecimalEsn(esn);
	}

	public String getActiveEsnByPartWithNoAccount(String partNumber) {
		return phoneTwistDao.getActiveEsnByPartWithNoAccount(partNumber);
	}

	public String getActiveEsnToUpgradeFrom(String oldPartNum, String newEsnToUpgrade) {
		return phoneTwistDao.getActiveEsnToUpgradeFrom(oldPartNum, newEsnToUpgrade);
	}

	public void finishPhoneActivationAfterPortIn(String esn) {
		phoneTwistDao.finishPhoneActivationAfterPortIn(esn);
	}

	public boolean verifyPhoneUpgrade(String oldEsn, String newEsn) {
		return phoneTwistDao.verifyPhoneUpgrade(oldEsn, newEsn);
	}
	
	public void checkActivation(ESN esn, int actionType, String transType) {
		phoneTwistDao.checkActivation(esn, actionType, transType);
	}
	
	public void clearTNumber(String esn) {
		phoneTwistDao.clearTNumber(esn);
	}

	public String getNewByopCDMAEsn() {
		return phoneTwistDao.getNewByopCDMAEsn();
	}

	public int finishCdmaByopIgate(String esn, String carrier, String status, String isLTE, String isIphone, String isHd){
		return phoneTwistDao.finishCdmaByopIgate(esn, carrier, status, isLTE, isIphone, isHd);
	}

	public int changeTestIgateEsnType(String esn) {
		return phoneTwistDao.changeTestIgateEsnType(esn);
	}
	
	public String getSimFromEsn(String esn) {
		return phoneTwistDao.getSimFromEsn(esn);
	}
	
	public String getNewByopEsn(String esn, String sim) {
		return phoneTwistDao.getNewByopEsn(esn, sim);
	}
	
	public void checkRedemption(ESN esn) {
		phoneTwistDao.checkRedemption(esn);
	}
	
	public String getLRPPointsbyEmail(String email) {
		return phoneTwistDao.getLRPPointsbyEmail(email);
	}
	
	public String getLRPPointsbyEmailforTranstype(String Transtype ,String email) {
		return phoneTwistDao.getLRPPointsbyEmailforTranstype(Transtype ,email);
	}
	
	public String getActivationPointsByEsn(String esn) {
		return phoneTwistDao.getActivationPointsByEsn(esn);
	}
	public String getTotalLRPPointsbyEmail(String email) {
		return phoneTwistDao.getTotalLRPPointsbyEmail(email);
	}

	public String getLRPPointsforPinPart(String PinPart) {
		return phoneTwistDao.getLRPPointsforPinPart(PinPart);
	}
	
	public String getLRPPointsbyObjid(String Objid) {
		return phoneTwistDao.getLRPPointsbyObjid(Objid);
	}
	
	public String getLRPPointsForTransType(String transType) {
		return phoneTwistDao.getLRPPointsForTransType(transType);
	}
	
	public String getTransdescforLRPTransType(String Transtype, String email){
		return phoneTwistDao.getTransdescforLRPTransType(Transtype ,email);
	}
	
	public void checkUpgrade(ESN fromEsn, ESN toEsn) {
		phoneTwistDao.checkUpgrade(fromEsn, toEsn);
	}

	public String getStateByZip(String zipCode){
		return phoneTwistDao.getStateByZip(zipCode);
	}
	
	public String getPastDueEsnByPartNumber(String partNumber){
		return phoneTwistDao.getPastDueEsnByPartNumber(partNumber);
	}
	
	public String getUsedEsnByPartNumber(String partNumber){
		return phoneTwistDao.getUsedEsnByPartNumber(partNumber);
	}
	
	public String getRefurbEsnByPartNumber(String partNumber){
		return phoneTwistDao.getRefurbEsnByPartNumber(partNumber);
	}

	public String getEsnbyQueueandBrand(String queue, String brand) {
		return phoneTwistDao.getEsnbyQueueandBrand(queue, brand);
	}
	
	public String convertMeidDecToHex(ESN esn) {
		return phoneTwistDao.convertMeidDecToHex(esn);
	}
	
	public void checkBYOPRegistration(ESN esn) {
		phoneTwistDao.checkBYOPRegistration(esn);
	}
	
	public void resetILDCounter(String esn) {
		phoneTwistDao.resetILDCounter(esn);
	}
	
	public List<ESN> getNewB2BEsns() {
		return phoneTwistDao.getNewB2BEsns();
	}

	public String getPhonePartNumberbySimandModel(String model, String simPart,String brand) {
		return phoneTwistDao.getPhonePartNumberbySimandModel(model, simPart, brand);
	}
	
	public List<String> getProductIDbyESN(String esn) {
		return phoneTwistDao.getProductIDbyESN(esn);
	}
	
	public void getInsertEsnIntoUnlockSpc(String esn) {
		phoneTwistDao.getInsertEsnIntoUnlockSpc(esn);
	}

	public String getTicketIDforLID(String lid) {
		return phoneTwistDao.getTicketIDforLID(lid);
	}

	public int getInsertSmartphoneEsnforUpgrade(String esn) {
		return phoneTwistDao.getInsertSmartphoneEsnforUpgrade(esn);
	}

	public int getInsertPPEphoneEsnforUpgrade(String ACK_MSG,String esn) {
		return phoneTwistDao.getInsertPPEphoneEsnforUpgrade(ACK_MSG,esn);
	}

	public String getDevicetype(String esn) {
		return phoneTwistDao.getDevicetype(esn);
	}
	
	public String getZipcode(String esn) {
		return phoneTwistDao.getZipcode(esn);
	}
	
	public String getEsnDetails(String esn, String col) {
		return phoneTwistDao.getEsnDetails(esn, col);
	}
	
	public String getContactDetails(String objid) {
		return phoneTwistDao.getContactDetails(objid);
	}
	
	public boolean setMinForPortTicket(String min, String ticket) {
		return phoneTwistDao.setMinForPortTicket(min,ticket);
	}

	public void setFlashforEsn(String esn, int flashType, String status) {
		phoneTwistDao.setFlashforEsn(esn, flashType, status);
	}
	
	public void insertIntoServiceResultsTable(String operationName, String request, StringBuffer response, String elapsedTime, String url) {
		phoneTwistDao.insertIntoServiceResultsTable(operationName, request,	response, elapsedTime, url);
	}
	
	public int clearCarrierPending(String esn) {
		return phoneTwistDao.clearCarrierPending(esn);
	}

	public String getAccountIdbyEsn(String esn) {
		return phoneTwistDao.getAccountIdbyEsn(esn);
	}
	
	public int LRPAddEsntoPromoGroup(String esn, String min, String email) {
		return phoneTwistDao.LRPAddEsntoPromoGroup(esn, min, email);
	}

	public String getEsnAttribute(String reqValue, String esn) {
		return phoneTwistDao.getEsnAttribute(reqValue, esn);
	}
	
	public String checkEnrollment(String esn) {
		return phoneTwistDao.checkEnrollment(esn);
	}

	public String getPinPartNumberfromPin(String pin) {
		return phoneTwistDao.getPinPartNumberfromPin(pin);
	}
		
	public String getWebobjidByEmail(String email) {
		return phoneTwistDao.getWebobjidByEmail(email);
	}
	
	public String checkBI(String esn) {
		return phoneTwistDao.checkBI(esn);
	}
	
	public String validatePromo(String esn, String promo) {
		return phoneTwistDao.validatePromo(esn, promo);
	}
	
	public String getPaymentSource(String lastFour, String email) {
		return phoneTwistDao.getPaymentSource(lastFour, email);
	}

	public void createThrottleRecordforEsn(String esn, String policyName) {
		phoneTwistDao.createThrottleRecordforEsn(esn, policyName);
	}

	public void updateThrottleTransactionStatus(String esn) {
		phoneTwistDao.updateThrottleTransactionStatus(esn);
	}

	public ArrayList<Object> getRewardBenefitDetails(String transaction_type,String category,String sub_category,String benefits_earned) {
		return phoneTwistDao.getRewardBenefitDetails(transaction_type,category, sub_category, benefits_earned);
	}

	public void updateMaturirtyDateforAcc(String email) {
		phoneTwistDao.updateMaturirtyDateforAcc(email);
	}

	public void completePendingRewardBatchTrasanctions() {
		phoneTwistDao.completePendingRewardBatchTrasanctions();
	}
	
	public void changeDealerForEsn(String esn , String dealerID) {
		phoneTwistDao.changeDealerForEsn(esn, dealerID);
	}

	public String getEsnLeasedStaus(String esn) {
		return phoneTwistDao.getEsnLeasedStaus(esn);
	}

	public int getProgramIdforServicePlan(String servicePlan, boolean isReccuring) {
		return phoneTwistDao.getProgramIdforServicePlan(servicePlan, isReccuring);
	}
	
	public String checkpartinststatus(String esn) {
		return phoneTwistDao.checkpartinststatus(esn);
	}
	
	public String getUsedPinOfEsn(String esn) {
		return phoneTwistDao.getUsedPinOfEsn(esn);
	}
	
	public String getOrderType(String esn) {
		return phoneTwistDao.getOrderType(esn);
	}
	
	public void insertFlash(String esn, String flashType){
		phoneTwistDao.insertFlash(esn, flashType);
	}
	
	public String getBANFromWebObj(String webObj) {
		return phoneTwistDao.getBANFromWebObj(webObj);
	}
	
	public String getCurrentActiveObjID(String esn){
		return phoneTwistDao.getCurrentActiveObjID(esn);
	}
	
	public int updateBRMCheck(List<String> insertData){
		return phoneTwistDao.updateBRMCheck(insertData);
	}

	public String getRecentTicketbyESN(String esn) {
		return phoneTwistDao.getRecentTicketbyESN(esn);
	}

	public void updateLatestIGRecordforEsn(String esn) {
		phoneTwistDao.updateLatestIGRecordforEsn(esn);
	}

	public void ChangeDealer(String dealerID , String esn) {
		phoneTwistDao.ChangeDealer(dealerID,esn);
	}
	
	public String getactionitemidbyESN(String esn) {
		return phoneTwistDao.getactionitemidbyESN(esn);
	}
	
	public String getminforESN(String esn) {
		return phoneTwistDao.getminforESN(esn);
	}

	public String getUPCByPartNumber(String partNumber) {
		return phoneTwistDao.getUPCByPartNumber(partNumber);
		
	}

	public String getPOSAUnregisteredPin(String brand) {
		return phoneTwistDao.getPOSAUnregisteredPin(brand);
		
	}

	public String getRedCodefromSnp(String snp) {
		return phoneTwistDao.getRedCodefromSnp(snp);
	}
	
	public String getSNPfromRedCode(String redCode) {
		return phoneTwistDao.getSNPfromRedCode(redCode);
	}

	public void enrollintoAffiliatedPartnerDiscountProgram(String email,String partner) {
	 phoneTwistDao.enrollintoAffiliatedPartnerDiscountProgram(email,partner);
		
	}


}