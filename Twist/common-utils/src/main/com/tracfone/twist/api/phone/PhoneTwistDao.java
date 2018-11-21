package com.tracfone.twist.api.phone;

import java.util.ArrayList;
import java.util.List;

import com.tracfone.twist.utils.ESN;

public interface PhoneTwistDao {

	public String getParentCarrierName(String esn);
	
	public void insertIntob2bTable(List b2bdata);
	
	public String getOrgIdByOrgName(String orgName);
	
	public void deactivateSMEsn(String esn);

	public String getESNForIMSI(String partNumber,String sim);

	public String getNewEsnByPartNumber(String partNumber);

	public String getActiveEsnByPartNumber(String partNumber);
	
	public String getActiveEsnByBrand(String brand);
	
	public String getEsnForPort(String partNumber);

	public String getActiveSTEsnByPartNumber(String partNumber);

	public String getNewPinByPartNumber(String partNumber);

	public int finishCdmaPhoneActivation(String msid, String esn);

	public int finishGsmPhoneActivation(String msid, String esn);

	public int getUpdateOTAResults(String esn);

	public String getReservedLine(String esn);

	public String getMinOfActiveEsn(String esn);

	public boolean deactivateEsn(String esn);

	public boolean removeEsnFromSTAccount(String esn);

	public boolean setDateInFuture(String esn);
	
	public boolean setDateInPast(String esn);

	public boolean addPinToQueue(String esn, String pin);

	public boolean clearOTAforEsn(String esn);
	
	public void updateEsnStatustoInactive(String esn);
	
	public void updateBIRecord(String esn);

	public void runIGateIn();

	public int finishPhoneActivationWithSameMin(String esn);

	public String getHexEsnFromDecimalEsn(String esn);

	public String getActiveEsnByPartWithNoAccount(String partNumber);

	public int finishPhoneActivationAfterPortIn(String esn);

	public String getActiveEsnToUpgradeFrom(String oldPartNum, String newEsnToUpgrade);

	public boolean verifyPhoneUpgrade(String oldEsn, String newEsn);

	public void checkActivation(ESN esn, int actionType, String transType);
	
	public boolean addSimToEsn(String sim, ESN toEsn);

	public void clearTNumber(String esn);

	public String getNewByopCDMAEsn();
	
	public void getInsertEsnIntoUnlockSpc(String esn);

	public int finishCdmaByopIgate(String esn, String carrier, String status, String isLTE, String isIphone , String isHd);

	public int changeTestIgateEsnType(String esn);

	public String getNewByopEsn(String partNumber, String sim);

	public String getSimFromEsn(String esn);
	
	public void checkRedemption(ESN esn);
	
	public void checkUpgrade(ESN fromEsn, ESN toEsn);

	public String getStateByZip(String zipCode);
	
	public String getEsnbyQueueandBrand(String queue,String brand);
	
	public String getPastDueEsnByPartNumber(String partNumber);
	
	public String getUsedEsnByPartNumber(String partNumber);
	
	public String getRefurbEsnByPartNumber(String partNumber);
	
	public String convertMeidDecToHex(ESN esn);

	public void checkBYOPRegistration(ESN esn);

	public boolean resetILDCounter(String esn);
	
	public List<ESN> getNewB2BEsns();

	public String getStatusOfESNFromStage(String esn);
	
	public String getEnrollmentStatusforEsn(String esn);

	public void setEsnInstallDatetoPast(String esn, int days);
	
	public void setEsnServiceEndDatetoPast(String esn, int days);

	public String getLRPPointsbyEmail(String email);
	
	public String getLRPPointsbyEmailforTranstype(String Transtype ,String email);
	
	public String getActivationPointsByEsn(String esn);
	
	public String getTotalLRPPointsbyEmail(String email);

	public String getLRPPointsforPinPart(String PinPart);
	
	public String getLRPPointsbyObjid(String Objid);
	
	public String getLRPPointsForTransType(String transType);
	
	public String getTransdescforLRPTransType(String Transtype, String email);
	
	public String getPhonePartNumberbySimandModel(String model, String simPart, String brand);
	
	public List<String> getProductIDbyESN(String esn);

	public String getTicketIDforLID(String lid);

	//public void updateToRealSim(String sim);
	public int getInsertSmartphoneEsnforUpgrade(String esn);

	public int getInsertPPEphoneEsnforUpgrade(String ACK_MSG,String esn);

	public String getDevicetype(String esn);
	
	public String getZipcode(String esn);
	
	public String getEsnDetails(String esn, String col);
	
	public String getContactDetails(String objid);
	
	public boolean setMinForPortTicket(String min, String ticket);	
	
	public int clearCarrierPending(String esn);

	public void setFlashforEsn(String esn, int flashType, String status);
	
	public void insertIntoServiceResultsTable(String operationName, String request, StringBuffer response, String elapsedTime, String url);

	public int LRPAddEsntoPromoGroup(String esn, String min, String email);
	
	public String getAccountIdbyEsn(String esn);
	
	public String checkEnrollment(String esn);

	public String getEsnAttribute(String reqValue, String esn);

	public String checkBI(String esn);
	
	public String validatePromo(String esn, String promo);
	
	public String getPinPartNumberfromPin(String pin);

	public String getPaymentSource(String lastFour, String email);

	public void createThrottleRecordforEsn(String esn, String policyName);

	public void updateThrottleTransactionStatus(String esn);
	
	public String getWebobjidByEmail(String email);

	public ArrayList<Object> getRewardBenefitDetails(String transaction_type,String category, String sub_category, String benefits_earned);

	public void updateMaturirtyDateforAcc(String email);

	public void completePendingRewardBatchTrasanctions();

	public void changeDealerForEsn(String esn , String dealerID);

	public void b2bPromoORG(String orgName, String promo);

	public String getEsnLeasedStaus(String esn);
	
	public String checkpartinststatus(String esn);
	
	public String getUsedPinOfEsn(String esn);
	
	public String getOrderType(String esn);
	
	public void insertFlash(String esn, String flashType);
	
	public int getProgramIdforServicePlan(String servicePlan, boolean isReccuring);
	
	public void ChangeDealer(String dealerID , String esn);

	public String getRecentTicketbyESN(String esn);

	public void updateLatestIGRecordforEsn(String esn);
	
	public String getBANFromWebObj(String webObj);

	public String getCurrentActiveObjID(String esn);
	
	public int updateBRMCheck(List<String> insertData);
	
	public String getactionitemidbyESN(String esn);

	public String getminforESN(String esn);

	public String getUPCByPartNumber(String partNumber);

	public String getPOSAUnregisteredPin(String brand);

	public String getRedCodefromSnp(String snp);

	public String getSNPfromRedCode(String redCode);

	public void enrollintoAffiliatedPartnerDiscountProgram(String email,String partner);
	
	
	
}