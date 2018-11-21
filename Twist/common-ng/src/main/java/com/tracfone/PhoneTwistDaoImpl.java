package com.tracfone;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.tracfone.core.persistence.dao.AbstractBaseDao;
import com.tracfone.ESN;
import com.tracfone.TwistUtils;

public class PhoneTwistDaoImpl extends AbstractBaseDao {

	private final NewEsnTestFunction newEsnTestFunction;
	private final UsedPinFunction usedPinFunction;
	private final NewEsnWithoutAccount newEsnWithoutAccount;
	private final DeactivateEsnFunction deactivateEsnFunction;
	private final RemoveEsnFromSTAccount removeEsnFromSTAccount;
	private final ActiveEsnTestFunction activeEsnTestFunction;
	private final EsnForPortFunction esnForPortFunction;
	private final StraighttalkActiveEsnTestFunction straighttalkActiveEsnTestFunction;
	private final IGateRtaInFunction iGateRtaInFunction;
	private final PinTestFunction pinTestFunction;
	private final GetReservedLine getReservedLine;
	private final GetMinOfActiveEsn getMinOfActiveEsn;
	private final IgTransactionCdmaPhoneActivationUpdate igTransactionCdmaPhoneActivationUpdate;
	private final IgTransactionGsmPhoneActivationUpdate igTransactionGsmPhoneActivationUpdate;
	private final PhoneActivationWithSameMinUpdate phoneActivationWithSameMinUpdate;
	private final UpdateOTATransactions updateOTATransactions;
	private final SetDueDateInPastSite setDueDateInPastSite;
	private final SetDueDateInFutureSite setDueDateInFutureSite;
	private final SetDueDateInPastPartInst setDueDateInPastPartInst;
	private final SetDueDateInFuturePartInst setDueDateInFuturePartInst;
	private final AddPinToQueue addPinToQueue;
	private final AddSimToEsn addSimToEsn;
	private final ClearOTA clearOTA;
	private final SetMinForPortTicket setMinForPortTicket;
	private final updateEsnforInactiveStatus esnUpdateForInactiveStatus;
	private final updateBIRecord esnUpdateBIRecord;
	private final GetHexFromESN getHexFromESN;
	private final ActiveEsnNoAccount activeEsnNoAccount;
	private final ActiveEsnWithLocalZip activeEsnWithLocalZip;
	private final PhoneActivationAfterPortIn phoneActivationAfterPortIn;
	private final VerifyPhoneUpgrade verifyPhoneUpgrade;
	private final GetICCIDAndZip getICCIDAndZip;
	private final CheckActivationProcedure checkActivationProcedure;
	private final ClearTNumberFunction clearTNumber;
	private final GetNextCmdaEsn getNextCmdaEsn;
	private final AddToTestIgateEsn addToTestIgateEsn;
	private final AddToTestOtaEsn addToTestOtaEsn;
	private final FinishCdmaByopIgate finishCdmaByopIgate;
	private final CompleteCdmaByopIgate completeCdmaByopIgate;
	private final ChangeTestIgateEsnType changeTestIgateEsnType;
	private final CheckRedemptionProcedure checkRedemptionProcedure;
	private final SMDeactivationProcedure checkDeactivationProcedure;
	private final GetSAObjid getSAObjid;
	private final NewByopEsnFunction newByopEsnFunction;
	private final GetSimFromEsn getSimFromEsn;
	private final CheckPhoneUpgrade checkPhoneUpgrade;
	private final GetESNForIMSI getEsnForIMSI;
	private final GetStateByZip getStateByZip;
	private final GetPastDueEsn getPastDueEsn;
	private final GetUsedEsn getUsedEsn;
	private final GetRefurbEsn getRefurbEsn;
	private final GetESNByBrandQueue getEsnByBrandQueue;
	private final MeidDecToHex meidDecToHex;
	private final FinishRedeemIgate finishRedeemIgate;
	private String ipAddress;
	private final CheckRegisterProcedure checkRegisterProcedure;
	private final FinishAllIgate finishAllIgate;
	private final ResetILDCounter resetILDCounter;
	private final GetStatusForStage  getStatusForStage;
	private final GetEnrollmnetStatus getEnrollmentStatus;
	private final InsertToB2BTable insertToB2BTable;
	private final B2BPromoORG b2bPromoORG;
	private JdbcTemplate ofsjdbcTemplate;
	private JdbcTemplate db2jdbcTemplate;
	private JdbcTemplate clfyjdbcTemplate;
	private final GetB2BNewEsns getB2BNewEsns;
	private final insertEsnIntoUnlockSpc insertEsnIntoUnlockSpc;
	private final InsertSmartphoneEsnforUpgrade InsertSmartphoneEsnforUpgrade;
	private final InsertPPEphoneEsnforUpgrade InsertPPEphoneEsnforUpgrade;
	private final AddEsntoPromoGroup AddEsntoPromoGroup;
	private final PaymentSourceForEmail paymentSourceForEmail;
	private final ThrottleEsn throttleEsn;
	//private final ChangeDealerForEsn changeDealerForEsn;
	private final RewardBatchUpdate rewardBatchUpdate;
	private SimpleDateFormat dateFormat;
	private Calendar cal ;
	
	public PhoneTwistDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		usedPinFunction = new UsedPinFunction();
		b2bPromoORG = new B2BPromoORG();
		insertToB2BTable = new InsertToB2BTable();
		newEsnTestFunction = new NewEsnTestFunction();
		newByopEsnFunction = new NewByopEsnFunction();
		newEsnWithoutAccount = new NewEsnWithoutAccount();
		deactivateEsnFunction = new DeactivateEsnFunction();
		removeEsnFromSTAccount = new RemoveEsnFromSTAccount();
		activeEsnTestFunction = new ActiveEsnTestFunction();
		activeEsnWithLocalZip = new ActiveEsnWithLocalZip();
		straighttalkActiveEsnTestFunction = new StraighttalkActiveEsnTestFunction();
		activeEsnNoAccount = new ActiveEsnNoAccount();
		pinTestFunction = new PinTestFunction();
		getReservedLine = new GetReservedLine();
		getMinOfActiveEsn = new GetMinOfActiveEsn();
		igTransactionCdmaPhoneActivationUpdate = new IgTransactionCdmaPhoneActivationUpdate();
		igTransactionGsmPhoneActivationUpdate = new IgTransactionGsmPhoneActivationUpdate();
		updateOTATransactions = new UpdateOTATransactions();
		setDueDateInPastSite = new SetDueDateInPastSite();
		setDueDateInPastPartInst = new SetDueDateInPastPartInst();
		setDueDateInFutureSite = new SetDueDateInFutureSite();
		setDueDateInFuturePartInst = new SetDueDateInFuturePartInst();
		addPinToQueue = new AddPinToQueue();
		clearOTA = new ClearOTA();
		setMinForPortTicket = new SetMinForPortTicket();
		esnUpdateForInactiveStatus= new updateEsnforInactiveStatus();
		esnUpdateBIRecord = new updateBIRecord();
		iGateRtaInFunction = new IGateRtaInFunction();
		phoneActivationWithSameMinUpdate = new PhoneActivationWithSameMinUpdate();
		phoneActivationAfterPortIn = new PhoneActivationAfterPortIn();
		getHexFromESN = new GetHexFromESN();
		verifyPhoneUpgrade = new VerifyPhoneUpgrade();
		getICCIDAndZip = new GetICCIDAndZip();
		checkActivationProcedure = new CheckActivationProcedure();
		addSimToEsn = new AddSimToEsn();
		clearTNumber = new ClearTNumberFunction();
		getNextCmdaEsn = new GetNextCmdaEsn();
		addToTestIgateEsn = new AddToTestIgateEsn();
		addToTestOtaEsn = new AddToTestOtaEsn();
		finishCdmaByopIgate = new FinishCdmaByopIgate();
		changeTestIgateEsnType = new ChangeTestIgateEsnType();
		getSAObjid = new GetSAObjid();
		getSimFromEsn = new GetSimFromEsn();
		checkRedemptionProcedure = new CheckRedemptionProcedure();
		checkDeactivationProcedure = new SMDeactivationProcedure();
		checkPhoneUpgrade = new CheckPhoneUpgrade();
		getEsnForIMSI = new GetESNForIMSI();
		getStateByZip = new GetStateByZip();
		getPastDueEsn = new GetPastDueEsn();
		getUsedEsn = new GetUsedEsn();
		getRefurbEsn = new GetRefurbEsn();
		getEsnByBrandQueue = new GetESNByBrandQueue();
		meidDecToHex = new MeidDecToHex();
		finishRedeemIgate = new FinishRedeemIgate();
		finishAllIgate = new FinishAllIgate();
		checkRegisterProcedure = new CheckRegisterProcedure();
		resetILDCounter = new ResetILDCounter();
		getB2BNewEsns = new GetB2BNewEsns();
	    completeCdmaByopIgate = new CompleteCdmaByopIgate();
	    getStatusForStage = new GetStatusForStage();
	    getEnrollmentStatus = new GetEnrollmnetStatus();
	    insertEsnIntoUnlockSpc = new insertEsnIntoUnlockSpc();
	    InsertSmartphoneEsnforUpgrade= new InsertSmartphoneEsnforUpgrade();
	    InsertPPEphoneEsnforUpgrade= new InsertPPEphoneEsnforUpgrade();
	    AddEsntoPromoGroup= new AddEsntoPromoGroup();
	    esnForPortFunction = new EsnForPortFunction();
	    paymentSourceForEmail = new PaymentSourceForEmail();
	    throttleEsn = new ThrottleEsn();
	    rewardBatchUpdate=new RewardBatchUpdate();
	    
	    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal = Calendar.getInstance();
		//changeDealerForEsn = new ChangeDealerForEsn();
	}
	
	public synchronized String getEnrollmentStatusforEsn(String esn) {
		List status = getEnrollmentStatus.execute(esn);
		logger.debug("		esn_number --> " + esn);
		logger.debug("		result --> " + status);

		if (status == null || status.isEmpty()) {
			throw new IllegalArgumentException("No reocrds found in enrollment table" + esn);
		} else {
			return (String) status.get(0);
		}
		
	}
	
	 public class GetEnrollmnetStatus extends MappingSqlQuery{
		 public GetEnrollmnetStatus(){
			 super(getDataSource(), GET_ENROLLMENT_STATUS);
			 declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		 }

		
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			return rs.getString("status");
		} 
	 }
	 

	 
	public synchronized void getInsertEsnIntoUnlockSpc(String esn) {
			//insertEsnIntoUnlockSpc.execute().get(0);
			insertEsnIntoUnlockSpc.update(esn);
			//addToTestIgateEsn.update(esn, "C");
			//return esn;
		}
	 
	 private class insertEsnIntoUnlockSpc extends SqlUpdate {
			public insertEsnIntoUnlockSpc() {
				super(getDataSource(),INSERT_INTO_UNLOCK_SPC );
				declareParameter(new SqlParameter("esn", Types.VARCHAR));
				compile();
			}

			
			public int update(String esn) {
				return update(new Object[] { esn });
			}
		}
		
	/*
	 
	 public synchronized boolean updateToRealSim(String sim) {
			int result = igTransactionGsmPhoneActivationUpdate.update(sim);

			logger.debug("Call to " + SQL_UPDATE_IG_TRANSACTION_GSM_PHONE_ACTIVATION);
			logger.debug("		sim --> " + sim);
			 catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			
			return result;
		}
	 
	 private class updateToRealSim extends SqlUpdate {
			public updateToRealSim() {
				super(getDataSource(), SET_DUE_DATE_IN_FUTURE_PART_INST);
				declareParameter(new SqlParameter("sim", Types.VARCHAR));
				compile();
			}

			
			public int update(String sim) {
				return update(new Object[] { sim });
			}
		}
	 
	 */
	 
	
	public synchronized String getStatusOfESNFromStage(String esn) {
		List status = getStatusForStage.execute(esn);
		logger.debug("		esn_number --> " + esn);
		logger.debug("		result --> " + status);

		if (status == null || status.isEmpty()) {
			throw new IllegalArgumentException("No records found for ESN in STAGE table" + esn);
		} else {
			return (String) status.get(0);
		}			
	}
		
	public class GetStatusForStage extends MappingSqlQuery{
			 public GetStatusForStage(){
				 super(getDataSource(), GET_STAGE_STATUS);
				 declareParameter(new SqlParameter("esn", Types.VARCHAR));
				compile();
			 }

			
			protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
				return rs.getString("status");
			} 
	}

	
	public synchronized String getESNForIMSI(String partNumber,String sim) {
		String newESN = getEsnForIMSI.execute(partNumber, sim);
		logger.debug("Call to " + GET_TEST_ESN_IMSI);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + newESN);

		if (newESN == null || newESN.isEmpty()) {
			throw new IllegalArgumentException("No new esns with part number: " + partNumber);
		} else {
			return newESN;
		}
	}

	
	public synchronized String getNewEsnByPartNumber(String partNumber) {
		if (partNumber.startsWith("STAPI4")) {
			throw new IllegalArgumentException("Can not find new ESN for ACME part number");
		}
		String newEsn = newEsnTestFunction.execute(partNumber);

		logger.debug("Call to " + GET_TEST_NEW_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + newEsn);

		if (newEsn == null || newEsn.isEmpty()) {
			throw new IllegalArgumentException("No new esns with part number: " + partNumber);
		} else if (partNumber.contains("VV10")) {
			String pin = getNewPinByPartNumber("STAPPCC0010D");
			addPinToQueue(newEsn, pin);
		}
		return newEsn;
	}
	
	
	public synchronized List<ESN> getNewB2BEsns() {
		List result = getB2BNewEsns.execute();
		logger.debug("Call to " + GET_NEW_B2B_ESNS);

		if (result == null || result.isEmpty()) {
			throw new IllegalArgumentException("No new b2b esns to activate");
		} else {
			return result;
		}
	}
	
	
	public synchronized String getNewByopEsn(String partNumber, String sim) {
		TwistUtils.setDelay(4);
		String newEsn = newByopEsnFunction.execute(partNumber, sim);
		TwistUtils.setDelay(4);
		logger.debug("Call to " + GET_TEST_BYOP_ESN);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + newEsn);

		if (newEsn == null || newEsn.isEmpty()) {
			throw new IllegalArgumentException("No new esns with part number: " + partNumber);
		} else {
			return newEsn;
		} 
	}
	
	
	public synchronized String getSimFromEsn(String esn) {
		List result = getSimFromEsn.execute(esn);

		if (result == null || result.isEmpty()) {
			throw new IllegalArgumentException("No sim for esn: " + esn);
		} else {
			return (String) result.get(0);
		}
	}

	/*
	 *  public synchronized String getNewEsnByPartWithoutAccount(String
	 * partNumber) { List result = newEsnWithoutAccount.execute(partNumber);
	 * 
	 * 
	 * logger.debug("Call to " + GET_NEW_ESN_BY_PART_NUMBER_NO_ACCOUNT);
	 * logger.debug("		part_number --> " + partNumber);
	 * logger.debug("		result --> " + result);
	 * 
	 * if (result.isEmpty()) { return this.getNewEsnByPartNumber(partNumber); }
	 * else { return (String) result.get(0); } }
	 */

	
	public synchronized boolean deactivateEsn(String esn) {
		try {
			deactivateEsnFunction.execute(esn);
			logger.debug("Call to " + DEACTIVATE_ESN_BY_PART_NUMBER);
			logger.debug("		part_number --> " + esn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public synchronized String getNewByopCDMAEsn() {
		String esn = (String) getNextCmdaEsn.execute().get(0);
		addToTestOtaEsn.update(esn);
		addToTestIgateEsn.update(esn, "C");
		return esn;
	}
	
	

	
	public synchronized int changeTestIgateEsnType(String esn) {
		return changeTestIgateEsnType.update(esn, "C");
	}

	
	
	public synchronized boolean setDateInFuture(String esn) {
		try {
			setDueDateInFutureSite.update(esn);
			logger.debug("Call to " + SET_DUE_DATE_IN_FUTURE_SITE);
			logger.debug("		esn --> " + esn);

			setDueDateInFuturePartInst.update(esn);
			logger.debug("Call to " + SET_DUE_DATE_IN_FUTURE_PART_INST);
			logger.debug("		esn --> " + esn);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public synchronized boolean setDateInPast(String esn) {
		try {
			setDueDateInPastSite.update(esn);
			logger.debug("Call to " + SET_DUE_DATE_IN_PAST_SITE);
			logger.debug("		esn --> " + esn);

			setDueDateInPastPartInst.update(esn);
			logger.debug("Call to " + SET_DUE_DATE_IN_PAST_PART_INST);
			logger.debug("		esn --> " + esn);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean addPinToQueue(String esn, String pin) {
		try {
			addPinToQueue.update(esn, pin);
			logger.debug("Call to " + ADD_PIN_TO_QUEUE);
			logger.debug("		esn --> " + esn);
			logger.debug("		pin --> " + pin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public synchronized boolean addSimToEsn(String sim, ESN esn) {
		try {
			addSimToEsn.update(sim, esn.getEsn());
			esn.setSim(sim);
			logger.debug("Call to " + ADD_SIM_TO_ESN);
			logger.debug("		esn --> " + esn);
			logger.debug("		sim --> " + sim);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public synchronized boolean clearOTAforEsn(String esn) {
		try {
			clearOTA.update(esn);
			clfyjdbcTemplate.update(CLEAR_SWITCH_BASED_TRANSACTION,new Object[] { esn });
			logger.debug("Call to " + CLEAR_OTA);
			logger.debug("		esn --> " + esn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public synchronized boolean setMinForPortTicket(String min, String ticket) {
		try {
			setMinForPortTicket.update(min, ticket);
			logger.debug("Call to " + SET_MIN_FOR_PORT_TICKET);
			logger.debug("		min --> " + min);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public synchronized void updateEsnStatustoInactive(String esn) {
		try {
			esnUpdateForInactiveStatus.update(esn);
			logger.debug("Call to " + ESNUPDATE_INACTIVE_STATUS);
			logger.debug("		esn --> " + esn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized void updateBIRecord(String esn) {
		try {
			esnUpdateBIRecord.update(esn);
			logger.debug("Call to " + ESNUPDATE_BI_RECORD);
			logger.debug("		esn --> " + esn);
			System.out.println("ESNUPDATE_BI_RECORD - " + esn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public synchronized boolean resetILDCounter(String esn) {
		try {
			resetILDCounter.update(esn);
			logger.debug("Call to " + RESET_ILD_COUNT);
			logger.debug("		esn --> " + esn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public synchronized boolean removeEsnFromSTAccount(String esn) {
		try {
			removeEsnFromSTAccount.execute(esn);
			logger.debug("Call to " + REMOVE_ESN_FROM_ST_ACCOUNT);
			logger.debug("		part_number --> " + esn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public synchronized String getActiveSTEsnByPartNumber(String partNumber) {
		Map outMap = straighttalkActiveEsnTestFunction.execute(partNumber);
		String result = (String) outMap.get("return");

		logger.debug("Call to " + GET_TEST_ACTIVE_ST_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);
		resetILDCounter(result);
		return result;
	}

	
	public void runIGateIn() {
		logger.debug("Call to " + RUN_IGATE_IN);
		TwistUtils.setDelay(30);
		// iGateRtaInFunction.execute();
	}

	
	public synchronized String getActiveEsnByPartNumber(String partNumber) {
		List result = activeEsnTestFunction.execute(partNumber);

		logger.debug("Call to " + GET_TEST_ACTIVE_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No active esns with part number: " + partNumber);
		} else {
			String esn = (String) result.get(0);
			resetILDCounter(esn);
			return esn;
		}
	}
	
	
	public synchronized String getEsnForPort(String partNumber) {
		List result = esnForPortFunction.execute(partNumber);

		logger.debug("Call to " + GET_PORT_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No active esns with part number: " + partNumber);
		} else {
			String esn = (String) result.get(0);
			resetILDCounter(esn);
			return esn;
		}
	}
	
	
	public synchronized String getPaymentSource(String lastFour, String email) {
		Object[] parms = new Object[2];
		parms[0] = "%" + lastFour;
		parms[1] = email.toUpperCase();
		
		List result = paymentSourceForEmail.execute(parms);

		logger.debug("Call to " + GET_PAYMENT_SOURCE_FOR_EMAIL);
		logger.debug("		part_number --> " + lastFour);
		logger.debug("		email--> " + email);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No payment source for: " + email + " last four cc: " + lastFour);
		} else {
			return (String) result.get(0);
		}
	}

	
	public synchronized void clearTNumber(String esn) {
		logger.debug("Call to " + CLEAR_TNUMBER);
		logger.debug("		esn --> " + esn);

		clearTNumber.execute(esn);
	}

	
	public synchronized String getActiveEsnByPartWithNoAccount(String partNumber) {
		List result = activeEsnNoAccount.execute(partNumber);

		logger.debug("Call to " + GET_ACTIVE_ESN_BY_PART_NUMBER_NO_ACCOUNT);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No active esns with part number: " + partNumber);
		} else {
			String esn = (String) result.get(0);
			resetILDCounter(esn);
			return esn;
		}
	}

	
	public synchronized boolean verifyPhoneUpgrade(String oldEsn, String newEsn) {
		List wrappers = getICCIDAndZip.execute(oldEsn);
		if (wrappers.isEmpty()) {
			throw new IllegalArgumentException("Old ESN is not active");
		}
		ICCIDAndZipWrapper wrapper = (ICCIDAndZipWrapper) wrappers.get(0);

		boolean canUpgrade = verifyPhoneUpgrade.execute(oldEsn, newEsn, wrapper.zip, wrapper.iccid);

		logger.debug("Call to " + VERIFY_PHONE_UPGRADE);
		logger.debug("		oldEsn --> " + oldEsn);
		logger.debug("		newEsn --> " + newEsn);
		logger.debug("		zip --> " + wrapper.zip);
		logger.debug("		iccid --> " + wrapper.iccid);
		logger.debug("		can upgrade --> " + canUpgrade);

		return canUpgrade;
	}

	
	public synchronized String getActiveEsnToUpgradeFrom(String oldPartNum, String newEsn) {
		List<String> possEsns = getActiveEsnFromLocalZip(oldPartNum);
		for (String esn : possEsns) {
			if (verifyPhoneUpgrade(esn, newEsn) && getMinOfActiveEsn(esn).length() == 10) {
				resetILDCounter(esn);
				return esn;
			}
		}
		throw new IllegalArgumentException("Could not find active esn with part " + oldPartNum + " to upgrade to " + newEsn);
	}

	private List<String> getActiveEsnFromLocalZip(String partNumber) {
		List<String> result = (List<String>) activeEsnWithLocalZip.execute(partNumber);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No active esns with part number: " + partNumber);
		} else {
			return result;
		}
	}

	
	public synchronized String getHexEsnFromDecimalEsn(String esn) {
		List result = getHexFromESN.execute(esn);

		logger.debug("Call to " + GET_HEX_ESN_BY_DEC_ESN);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			return "";
		} else {
			return (String) result.get(0);
		}
	}

	
	public synchronized String getNewPinByPartNumber(String partNumber) {
		if (partNumber == null || partNumber.isEmpty()) {
			throw new IllegalArgumentException("Pin Part number can not be blank");
		}

		TwistUtils.setDelay(3);
		String pin = pinTestFunction.execute(partNumber);

		logger.debug("Call to " + GET_TEST_PIN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + pin);

		return pin;
	}

	
	public synchronized void checkActivation(ESN esn, int actionType, String transType) {
		logger.debug("Call to " + CHECK_ACTIVATION);
		logger.debug("		esn --> " + esn);
		logger.debug("		actionType --> " + actionType);
		TwistUtils.setDelay(2);
		checkActivationProcedure.execute(esn, actionType, transType);
	}
	
	
	public synchronized void checkBYOPRegistration(ESN esn) {
		logger.debug("Call to " + CHECK_REGISTER);
		logger.debug("		esn --> " + esn.getEsn());
		TwistUtils.setDelay(2);
		checkRegisterProcedure.execute(esn);
		esn.clearTestState();
	}
	
	
	public synchronized void checkUpgrade(ESN fromEsn, ESN toEsn) {
		logger.debug("Call to " + CHECK_PHONE_UPGRADE);
		logger.debug("		from esn --> " + fromEsn.getEsn());
		logger.debug("		to esn --> " + toEsn.getEsn());
		finishAllIgate.update(toEsn.getEsn());
		TwistUtils.setDelay(9);
		checkPhoneUpgrade.execute(fromEsn, toEsn);
	}

	
	public synchronized void checkRedemption(ESN esn) {
		finishRedeemIgate.update(esn.getEsn());
		logger.debug("Call to " + CHECK_REDEMPTION);
		logger.debug("		esn --> " + esn.getEsn());
		TwistUtils.setDelay(7);
		checkRedemptionProcedure.execute(esn);
	}
	
	
	public synchronized int finishCdmaPhoneActivation(String msid, String esn) {
		int result = igTransactionCdmaPhoneActivationUpdate.update(msid, esn);

		logger.debug("Call to " + SQL_UPDATE_IG_TRANSACTION_CDMA_PHONE_ACTIVATION);
		logger.debug("		min --> " + msid);
		logger.debug("		msid --> " + msid);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}

	
	public synchronized int finishGsmPhoneActivation(String msid, String esn) {
		int result = igTransactionGsmPhoneActivationUpdate.update(msid, esn);

		logger.debug("Call to " + SQL_UPDATE_IG_TRANSACTION_GSM_PHONE_ACTIVATION);
		logger.debug("		min --> " + msid);
		logger.debug("		msid --> " + msid);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}

	
	public synchronized int finishPhoneActivationWithSameMin(String esn) {
		int result = phoneActivationWithSameMinUpdate.update(esn);

		logger.debug("Call to " + SQL_UPDATE_PHONE_ACTIVATION_SAME_MIN);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}

	
	public synchronized int finishPhoneActivationAfterPortIn(String esn) {
		int result = phoneActivationAfterPortIn.update(esn);

		logger.debug("Call to " + SQL_UPDATE_PHONE_ACTIVATION_PORT_MIN);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}

	
	public synchronized void deactivateSMEsn(String esn) {
		checkDeactivationProcedure.execute(esn);
	}
	
	public synchronized void createThrottleRecordforEsn(String esn,String policyName) {
		throttleEsn.execute(esn,policyName);
	}
	
	
	public synchronized void completePendingRewardBatchTrasanctions() {
		rewardBatchUpdate.execute();
	}

	
	public synchronized int finishCdmaByopIgate(String esn, String brand, String status, String isLTE, String isiPhone) {
		int result;
		if (isLTE.equalsIgnoreCase("YES") && isiPhone.equalsIgnoreCase("YES")) {
			isLTE = "4G";
			isiPhone = "APL";
		} else if (isLTE.equalsIgnoreCase("YES") && isiPhone.equalsIgnoreCase("NO")) {
			isLTE = "4G";
			isiPhone = "TESTPHONE";
		} else if (isLTE.equalsIgnoreCase("NO") && isiPhone.equalsIgnoreCase("YES")) {
			isLTE = "3G";
			isiPhone = "APL";
		} else if (isLTE.equalsIgnoreCase("NO") && isiPhone.equalsIgnoreCase("NO")) {
			isLTE = "3G";
			isiPhone = "TESTPHONE";
		} else {
			isLTE = "";
			isiPhone = "";
		}
		
		boolean isActive = "Active".equalsIgnoreCase(status);
		if ("SPRINT".equalsIgnoreCase(brand)) {
			TwistUtils.setDelay(1);
			result = finishCdmaByopIgate.update(isLTE, isiPhone, esn, "SPRINT", isActive);
			TwistUtils.setDelay(1);
			completeCdmaByopIgate.update(esn,"RSS");
		} else {
			logger.info(isiPhone+isLTE);
			result = finishCdmaByopIgate.update(isLTE, isiPhone, esn, "RSS", false);
			TwistUtils.setDelay(1);
			completeCdmaByopIgate.update(esn,"SPRINT");
		}
		 
		logger.debug("Call to " + SQL_FINISH_CDMA_BYOP_IGATE);
		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}

	
	public synchronized String getReservedLine(String esn) {
		List result = getReservedLine.execute(esn);

		logger.debug("Call to " + SQL_RESERVED_LINE_BY_ESN_QUERY);

		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return (!result.isEmpty()) ? (String) result.get(0) : "";
	}

	
	public synchronized String getMinOfActiveEsn(String esn) {
		List result = getMinOfActiveEsn.execute(esn);

		logger.debug("Call to " + SQL_MIN_OF_ACTIVE_ESN);

		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return (!result.isEmpty()) ? (String) result.get(0) : "";
	}

	
	public synchronized int getUpdateOTAResults(String esn) {
		int result = updateOTATransactions.update(esn);

		logger.debug("Call to " + SQL_UPDATE_OTA_PENDING_BY_ESN);

		logger.debug("		esn --> " + esn);
		logger.debug("		result --> " + result);

		return result;
	}
	
	/*private class ChangeDealerForEsn extends SqlUpdate {
		public ChangeDealerForEsn() {
			super(getDataSource(), CHAGNE_DEALER_FOR_ESN);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	}*/
	
	
	
	public synchronized String getStateByZip(String zipCode) {
		List result = getStateByZip.execute(zipCode);
		logger.debug("Call to " + GET_STATE_BY_ZIPCODE);
		logger.debug("		X_ZIP --> " + zipCode);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("Could not find state for zip: " + zipCode);
		 } else {
			 return (String) result.get(0);
		 }
	}
	
	
	public synchronized String convertMeidDecToHex(ESN esn) {
		logger.debug("Call to " + MEID_DEC_TO_HEX);
		logger.debug("		ESN --> " + esn.getEsn());
		return meidDecToHex.execute(esn);
	}

	
	public synchronized String getPastDueEsnByPartNumber(String partNumber) {
		List result = getPastDueEsn.execute(partNumber);

		logger.debug("Call to " + GET_TEST_PAST_DUE_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No past due esns with part number: " + partNumber);
		} else {
			return (String) result.get(0);
		}
	}

	
	public synchronized String getUsedEsnByPartNumber(String partNumber) {
		List result = getUsedEsn.execute(partNumber);

		logger.debug("Call to " + GET_TEST_USED_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No Used esns with part number: " + partNumber);
		} else {
			return (String) result.get(0);
		}
	}

	
	public synchronized String getRefurbEsnByPartNumber(String partNumber) {
		List result = getRefurbEsn.execute(partNumber);

		logger.debug("Call to " + GET_TEST_REFURB_ESN_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No refurbished esns with part number: " + partNumber);
		} else {
			return (String) result.get(0);
		}
	}
	
	
	public synchronized String getEsnbyQueueandBrand(String queue, String brand) {
		List result = getEsnByBrandQueue.execute(new String[] { queue, brand });
		logger.debug("Call to " + GET_ESN_BY_BRAND_QUEUE);
		logger.debug("		queue --> " + queue);
		logger.debug("		brand --> " + brand);
		logger.debug("		result --> " + result);

		if (result.isEmpty()) {
			throw new IllegalArgumentException("No active esns in database for the queue provided : " + queue+","+brand);
		} else {
			return (String) result.get(0);
		}
	}
	
	public synchronized String getIpAddress() {
		if (ipAddress == null) {
			try {
				ipAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				ipAddress = "Could not get IP";
			}
		}
		return ipAddress;
	}

	private class GetESNForIMSI extends StoredProcedure {
		public GetESNForIMSI() {
			super(getDataSource(), GET_TEST_ESN_IMSI);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("esn_part_number", Types.VARCHAR));
			declareParameter(new SqlParameter("sim_part_num", Types.VARCHAR));
	
			compile();
		}

		public String execute(String partNumber,String sim) {
			Map inParameters = new Hashtable();
			inParameters.put("esn_part_number", partNumber);
			inParameters.put("sim_part_num", sim);
			
			Map outMap = super.execute(inParameters);
			
			return (String) outMap.get("return");
		}
	}

	private class NewEsnTestFunction extends StoredProcedure {
		public NewEsnTestFunction() {
			super(getDataSource(), GET_TEST_NEW_ESN_BY_PART_NUMBER);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("esn_part_number", Types.VARCHAR));

			compile();
		}

		public String execute(String partNumber) {
			Map inParameters = new Hashtable();
			inParameters.put("esn_part_number", partNumber);
			Map outMap = super.execute(inParameters);

			return (String) outMap.get("return");
		}
	}
	
	private class NewByopEsnFunction extends StoredProcedure {
		public NewByopEsnFunction() {
			super(getDataSource(), GET_TEST_BYOP_ESN);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("esn_part_number", Types.VARCHAR));
			declareParameter(new SqlParameter("sim_part_num", Types.VARCHAR));

			compile();
		}

		public String execute(String esnPart, String simPart) {
			Map inParameters = new Hashtable();
			inParameters.put("esn_part_number", esnPart);
			inParameters.put("sim_part_num", simPart);
			
			Map outMap = super.execute(inParameters);

			return (String) outMap.get("return");
		}
	}

	private class CheckActivationProcedure extends StoredProcedure {
		public CheckActivationProcedure() {
			super(getDataSource(), CHECK_ACTIVATION);
//			setFunction(true);
			declareParameter(new SqlParameter("V_TRANSTYPE", Types.VARCHAR));
			declareParameter(new SqlParameter("inesn", Types.VARCHAR));
			declareParameter(new SqlParameter("actiontype", Types.VARCHAR));
			declareParameter(new SqlParameter("redeemflag", Types.VARCHAR));
			declareParameter(new SqlParameter("PIN", Types.VARCHAR));
			declareParameter(new SqlOutParameter("resultStr", Types.VARCHAR));
			declareParameter(new SqlOutParameter("returnCode", Types.INTEGER));
			declareParameter(new SqlParameter("v_ipaddress", Types.VARCHAR));

			compile();
		}
	
		public void execute(ESN esn, int actionType, String transType) {
			Map<String, Object> inParameters = new Hashtable<String, Object>();
			inParameters.put("V_TRANSTYPE", transType);
			inParameters.put("inesn", esn.getEsn());
			inParameters.put("actiontype", actionType);
			if (esn.getBuyFlag().equalsIgnoreCase("Y")) {
				inParameters.put("redeemflag", "B");
			} else if (!esn.getPin().isEmpty()) {
				inParameters.put("redeemflag", "R");
			} else {
				inParameters.put("redeemflag", "");
			}
			
			inParameters.put("PIN", esn.getPin());
			inParameters.put("v_ipaddress", getIpAddress());
			Map<String, Object> out = execute(inParameters);
			Integer returnCode = (Integer) out.get("returnCode");
			String message = (String) out.get("resultStr");
		
		//Assert.assertSame(message, 0, returnCode.intValue());
		}
	}
	
	private class B2BPromoORG extends StoredProcedure {
		public B2BPromoORG() {
			super(getDataSource(), Load_X_B2B_PROMO_ORG);
			// setFunction(true);

			//declareParameter(new SqlParameter("l_v_script_name", Types.VARCHAR));
			declareParameter(new SqlParameter("lv_org_name", Types.VARCHAR));
			//declareParameter(new SqlParameter("ip_commerceID", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_promo_code", Types.VARCHAR));
			//declareParameter(new SqlParameter("ip_x_start_date", Types.DATE));
			//declareParameter(new SqlParameter("ip_x_end_date", Types.DATE));
			//declareParameter(new SqlParameter("ip_user_created", Types.VARCHAR));

			declareParameter(new SqlOutParameter("op_return_code", Types.VARCHAR));
			declareParameter(new SqlOutParameter("op_error_msg", Types.VARCHAR));

			compile();
		}

		public void execute(String orgName, String promo) {

			Map<String, Object> inParameters = new Hashtable<String, Object>();

			inParameters.put("lv_org_name", orgName);
			inParameters.put("ip_promo_code", promo);

			Map<String, Object> out = execute(inParameters);
			String returnCode = (String) out.get("op_return_code");
			String message = (String) out.get("op_error_msg");
			System.out.println(message + "\n" + returnCode);
			logger.info(message);
			logger.info(returnCode);
			Assert.assertEquals(message, "Success" , message);
		}
		
	}
	
	
	public synchronized void b2bPromoORG(String orgName, String promo) {
		try {
			b2bPromoORG.execute(orgName, promo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private class InsertToB2BTable extends StoredProcedure{
		
		public InsertToB2BTable() {
			super(getDataSource(), INSERT_INTO_B2B_TABLE);
			declareParameter(new SqlParameter("V_PARENT_CHILD_FLAG", Types.VARCHAR));
			declareParameter(new SqlParameter("V_ECOMM_ORG_ID", Types.VARCHAR));
			declareParameter(new SqlParameter("V_CLFY_ORG_ID", Types.VARCHAR));
			declareParameter(new SqlParameter("V_OFS_ORG_ID", Types.VARCHAR));
			declareParameter(new SqlParameter("V_CLFY_ORDER_ID", Types.INTEGER));
			declareParameter(new SqlParameter("V_OFS_ORDER_ID", Types.INTEGER));
			declareParameter(new SqlParameter("V_ORG_NAME", Types.VARCHAR));
			declareParameter(new SqlParameter("V_PARENT_ORG_NAME", Types.VARCHAR));
			declareParameter(new SqlParameter("V_CHILD_ORG_NAME", Types.VARCHAR));
			declareParameter(new SqlParameter("V_SHIP_LOC_ID", Types.VARCHAR));
			declareParameter(new SqlParameter("V_USERNAME", Types.VARCHAR));
			declareParameter(new SqlParameter("V_PASSWORD", Types.VARCHAR));
		//	declareParameter(new SqlParameter("V_ORDER_DATE", Types.VARCHAR));
			declareParameter(new SqlParameter("V_ENVIRONMENT", Types.VARCHAR));
			declareParameter(new SqlParameter("V_DATABASE", Types.VARCHAR));
			declareParameter(new SqlParameter("V_PROJECT", Types.VARCHAR));
			compile();
		}
		
		
		public void execute(List b2bdata) {
			Map<String, Object> inParameters = new Hashtable<String, Object>();
		  
			inParameters.put("V_PARENT_CHILD_FLAG", b2bdata.get(0));
			inParameters.put("V_ECOMM_ORG_ID", b2bdata.get(1));
			inParameters.put("V_CLFY_ORG_ID", b2bdata.get(2));
			inParameters.put("V_OFS_ORG_ID", b2bdata.get(3));
			inParameters.put("V_CLFY_ORDER_ID", b2bdata.get(4));
			inParameters.put("V_OFS_ORDER_ID", b2bdata.get(5));
			inParameters.put("V_ORG_NAME", b2bdata.get(6));
			inParameters.put("V_PARENT_ORG_NAME", b2bdata.get(7));
			inParameters.put("V_CHILD_ORG_NAME", b2bdata.get(8));
			inParameters.put("V_SHIP_LOC_ID", b2bdata.get(9));
			inParameters.put("V_USERNAME", b2bdata.get(10));
			inParameters.put("V_PASSWORD", b2bdata.get(11));
		//	inParameters.put("V_ORDER_DATE", b2bdata.get(8));
			inParameters.put("V_ENVIRONMENT", b2bdata.get(12));
			inParameters.put("V_DATABASE", b2bdata.get(13));
			inParameters.put("V_PROJECT", b2bdata.get(14));
			
			execute(inParameters);
//			Assert.assertSame(message, 0, returnCode.intValue());
		}
	}
	
	
	
	public synchronized void insertIntob2bTable(List b2bdata) {
		try {
			insertToB2BTable.execute(b2bdata);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private class CheckRegisterProcedure extends StoredProcedure {
		public CheckRegisterProcedure() {
			super(getDataSource(), CHECK_REGISTER);
//			setFunction(true);
			declareParameter(new SqlParameter("V_TRANSTYPE", Types.VARCHAR));
			declareParameter(new SqlParameter("INESN", Types.VARCHAR));
			declareParameter(new SqlParameter("Oldesn", Types.VARCHAR));
			declareParameter(new SqlParameter("v_actiontype", Types.VARCHAR));
			declareParameter(new SqlParameter("PIN", Types.VARCHAR));
			declareParameter(new SqlParameter("REDEEMFLAG", Types.VARCHAR));
			declareParameter(new SqlParameter("Upgradeflag", Types.VARCHAR));	
			declareParameter(new SqlOutParameter("resultStr", Types.VARCHAR));
			declareParameter(new SqlOutParameter("returnCode", Types.INTEGER));
			declareParameter(new SqlParameter("v_ipaddress", Types.VARCHAR));

			compile();
		}
	
		public void execute(ESN esn) {
			Map<String, Object> inParameters = new Hashtable<String, Object>();
			inParameters.put("V_TRANSTYPE", esn.getTransType());
			inParameters.put("INESN", esn.getEsn());
			if (esn.getFromEsn() != null) {
				inParameters.put("v_actiontype", "1");
				inParameters.put("REDEEMFLAG", "");
				inParameters.put("Oldesn", esn.getFromEsn().getEsn());
				inParameters.put("Upgradeflag", "Y");
			} else if (esn.getBuyFlag().equalsIgnoreCase("Y")) {
				inParameters.put("v_actiontype", "6");
				inParameters.put("REDEEMFLAG", "B");
				inParameters.put("Oldesn", "");
				inParameters.put("Upgradeflag", "");
			} else {
				inParameters.put("v_actiontype", "6");
				inParameters.put("REDEEMFLAG", "R");
				inParameters.put("Oldesn", "");
				inParameters.put("Upgradeflag", "");
			}
			
			inParameters.put("PIN", esn.getPin());
			inParameters.put("v_ipaddress", getIpAddress());
			Map<String, Object> out = execute(inParameters);
			Integer returnCode = (Integer) out.get("returnCode");
			String message = (String) out.get("resultStr");
//			Assert.assertSame(message, 0, returnCode.intValue());
		}
	}
	
	private class SMDeactivationProcedure extends StoredProcedure {
		public SMDeactivationProcedure() {
			super(getDataSource(), SM_DEACTIVATION);
			// setFunction(true);

			declareParameter(new SqlParameter("ip_sourcesystem", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_userObjId", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_esn", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_min", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_DeactReason", Types.VARCHAR));
			declareParameter(new SqlParameter("intByPassOrderType", Types.INTEGER));
			declareParameter(new SqlParameter("ip_newESN", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_samemin", Types.VARCHAR));

			declareParameter(new SqlOutParameter("OP_RETURN", Types.VARCHAR));
			declareParameter(new SqlOutParameter("OP_RETURNMSG", Types.VARCHAR));

			compile();
		}

		public void execute(String esn) {
			String min = getMinOfActiveEsn(esn);
			String objid = (String) getSAObjid.execute().get(0);

			Map<String, Object> inParameters = new Hashtable<String, Object>();

			inParameters.put("ip_esn", esn);
			inParameters.put("ip_min", min);
			inParameters.put("ip_sourcesystem", "PAST_DUE_BATCH");
			inParameters.put("ip_userObjId", objid);
			inParameters.put("ip_DeactReason", "PASTDUE");
			inParameters.put("intByPassOrderType", 0);
			inParameters.put("ip_newESN", "0");
			inParameters.put("ip_samemin", "true");

			Map<String, Object> out = execute(inParameters);
			String returnCode = (String) out.get("OP_RETURN");
			String message = (String) out.get("OP_RETURNMSG");
			logger.info(message);
			logger.info(returnCode);
			Assert.assertEquals(message, "true" , returnCode);
		}

	}
	
	private class RewardBatchUpdate extends StoredProcedure{
		public RewardBatchUpdate(){
			super(getDataSource(), REWARDS_BATCH_COMPLETE_TRANSACTION);
			declareParameter(new SqlParameter("I_RUNDATE", Types.DATE));
			declareParameter(new SqlOutParameter("O_ERR_CODE", Types.VARCHAR));
			declareParameter(new SqlOutParameter("O_ERR_MSG", Types.VARCHAR));

		//	dateFormat.format(cal.getTime())
	
			compile();
			
		}
		
		public void execute() {
			Map<String, Object> inParameters = new Hashtable<String, Object>();
			inParameters.put("I_RUNDATE", dateFormat.format(cal.getTime()));
			
			Map<String, Object> out = execute(inParameters);

			String returnCode = (String) out.get("O_ERR_CODE");
			String message = (String) out.get("O_ERR_MSG");
			
			
		}
	}
	
	private class ThrottleEsn extends StoredProcedure {
		public ThrottleEsn() {
			super(getDataSource(), THROTTLE_ESN);
			// setFunction(true);

			declareParameter(new SqlParameter("P_MIN", Types.VARCHAR));
			declareParameter(new SqlParameter("P_ESN", Types.VARCHAR));
			declareParameter(new SqlParameter("P_POLICY_NAME", Types.VARCHAR));
			declareParameter(new SqlParameter("P_CREATION_DATE", Types.DATE));
			declareParameter(new SqlParameter("P_TRANSACTION_NUM", Types.VARCHAR));
			declareParameter(new SqlOutParameter("P_ERROR_CODE", Types.INTEGER));
			declareParameter(new SqlOutParameter("P_ERROR_MESSAGE", Types.VARCHAR));

			declareParameter(new SqlParameter("P_USAGE", Types.VARCHAR));
			declareParameter(new SqlParameter("P_BYPASS_OFF", Types.VARCHAR));
	
			compile();
		}

		public void execute(String esn,String policyName) {
			String min = getMinOfActiveEsn(esn);
	
			Map<String, Object> inParameters = new Hashtable<String, Object>();

			inParameters.put("P_MIN", min);
			inParameters.put("P_ESN", esn);
			inParameters.put("P_POLICY_NAME", policyName);
	//		inParameters.put("P_CREATION_DATE",java.sql.Date.valueOf("2016/09/06"));
			inParameters.put("P_CREATION_DATE",null);
			inParameters.put("P_TRANSACTION_NUM", null);
			inParameters.put("P_USAGE", null);
			inParameters.put("P_BYPASS_OFF", "YES");
			

			Map<String, Object> out = execute(inParameters);

			int returnCode =  (Integer) out.get("P_ERROR_CODE");
			String message = (String) out.get("P_ERROR_MESSAGE");
			logger.info(message);
			logger.info("ERROR CODE for throttle proc"+returnCode);
		
		}

	}
		
	
	public synchronized void updateThrottleTransactionStatus(String esn) {
		try{
			this.clfyjdbcTemplate.update(UPDATE_THROTTLING_TRANSACTION_STATUS,new Object[] {esn });
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	private class CheckRedemptionProcedure extends StoredProcedure {
		public CheckRedemptionProcedure() {
			super(getDataSource(), CHECK_REDEMPTION);
//			setFunction(true);
			declareParameter(new SqlParameter("V_TRANSTYPE", Types.VARCHAR));
			declareParameter(new SqlParameter("inesn", Types.VARCHAR));
			declareParameter(new SqlParameter("REDEEMTYPE", Types.VARCHAR));
			declareParameter(new SqlParameter("actiontype", Types.VARCHAR));
			declareParameter(new SqlParameter("BUYFLAG", Types.VARCHAR));
			declareParameter(new SqlParameter("PIN", Types.VARCHAR));
			declareParameter(new SqlParameter("ENROLLED_DAYS", Types.INTEGER));
			declareParameter(new SqlOutParameter("resultStr", Types.VARCHAR));
			declareParameter(new SqlOutParameter("returnCode", Types.INTEGER));
			declareParameter(new SqlParameter("v_ipaddress", Types.VARCHAR));
			
			compile();
		}
	
		public void execute(ESN esn) {
			if (!esn.hasValidRedeemData()) {
				throw new IllegalArgumentException("Cannot check redemption due to missing data");
			}
			
			Map<String, Object> inParameters = new Hashtable<String, Object>();
			if (esn.getActionType() == 6) {
				inParameters.put("V_TRANSTYPE", esn.getTransType() + " Add Now");
			} else {
				inParameters.put("V_TRANSTYPE", esn.getTransType() + " Add Later");
			}

			inParameters.put("inesn", esn.getEsn());
			inParameters.put("REDEEMTYPE", esn.getRedeemType());
			inParameters.put("actiontype", esn.getActionType());
			inParameters.put("BUYFLAG", esn.getBuyFlag());
			inParameters.put("PIN", esn.getPin());
			inParameters.put("ENROLLED_DAYS", esn.getEnrolledDays());
			inParameters.put("v_ipaddress", getIpAddress());

			Map<String, Object> out = execute(inParameters);
			Integer returnCode = (Integer) out.get("returnCode");
			String message = (String) out.get("resultStr");
//			Assert.assertSame(message, 0, returnCode.intValue());
		}
	}

	private class GetStateByZip extends MappingSqlQuery{
		public GetStateByZip(){
			super(getDataSource(),GET_STATE_BY_ZIPCODE);
			declareParameter(new SqlParameter("x_zip", Types.VARCHAR));
			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("x_state");
		}
	}

	private class NewEsnWithoutAccount extends MappingSqlQuery {
		public NewEsnWithoutAccount() {
			super(getDataSource(), GET_NEW_ESN_BY_PART_NUMBER_NO_ACCOUNT);
			declareParameter(new SqlParameter("part_number", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}
	
	private class EsnForPortFunction extends MappingSqlQuery {
		public EsnForPortFunction() {
			super(getDataSource(), GET_PORT_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("part_number", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}
	
	private class PaymentSourceForEmail extends MappingSqlQuery {
		public PaymentSourceForEmail() {
			super(getDataSource(), GET_PAYMENT_SOURCE_FOR_EMAIL);
			declareParameter(new SqlParameter("X_CUSTOMER_CC_NUMBER", Types.VARCHAR));
			declareParameter(new SqlParameter("S_LOGIN_NAME", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("OBJID");
		}
	}
	
	private class ActiveEsnTestFunction extends MappingSqlQuery {
		public ActiveEsnTestFunction() {
			super(getDataSource(), GET_TEST_ACTIVE_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("part_number", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}

	private class ActiveEsnWithLocalZip extends MappingSqlQuery {
		public ActiveEsnWithLocalZip() {
			super(getDataSource(), GET_ACTIVE_33178_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("part_number", Types.VARCHAR));

			compile();
		}

		
		protected String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}

	private class ActiveEsnNoAccount extends MappingSqlQuery {
		public ActiveEsnNoAccount() {
			super(getDataSource(), GET_ACTIVE_ESN_BY_PART_NUMBER_NO_ACCOUNT);
			declareParameter(new SqlParameter("part_number", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}

	private class ActiveEsnFromBrand extends MappingSqlQuery {
		public ActiveEsnFromBrand() {
			super(getDataSource(), GET_ACTIVE_ESN_BY_BRAND);
			declareParameter(new SqlParameter("s_org_id", Types.VARCHAR));

			compile();
		}

		
		protected String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String test = rs.getString("part_serial_no");
			return test;
		}
	}

	private class GetHexFromESN extends MappingSqlQuery {
		public GetHexFromESN() {
			super(getDataSource(), GET_HEX_ESN_BY_DEC_ESN);
			declareParameter(new SqlParameter("part_serial_no", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("x_hex_serial_no");
		}
	}
	
	public class GetPastDueEsn extends MappingSqlQuery{
		public GetPastDueEsn() {
			super(getDataSource(), GET_TEST_PAST_DUE_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("s_part_number", Types.VARCHAR));

			compile();
	}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}

	public class GetUsedEsn extends MappingSqlQuery{
		public GetUsedEsn() {
			super(getDataSource(), GET_TEST_USED_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("s_part_number", Types.VARCHAR));

			compile();
	}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}

	public class GetRefurbEsn extends MappingSqlQuery{
		public GetRefurbEsn() {
			super(getDataSource(), GET_TEST_REFURB_ESN_BY_PART_NUMBER);
			declareParameter(new SqlParameter("s_part_number", Types.VARCHAR));

			compile();
		}
		
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("part_serial_no");
		}
	}
	
	 public class GetESNByBrandQueue extends MappingSqlQuery{
		 public GetESNByBrandQueue(){
			 super(getDataSource(), GET_ESN_BY_BRAND_QUEUE);
			 
			 declareParameter(new SqlParameter("queue_name", Types.VARCHAR));
			 declareParameter(new SqlParameter("brand", Types.VARCHAR));
			compile();
		 }

		
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			return rs.getString("esn");
		} 
	 }

	private class StraighttalkActiveEsnTestFunction extends StoredProcedure {
		public StraighttalkActiveEsnTestFunction() {
			super(getDataSource(), GET_TEST_ACTIVE_ST_ESN_BY_PART_NUMBER);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("esn_part_number", Types.VARCHAR));

			compile();
		}

		public Map execute(String partNumber) {
			Map inParameters = new Hashtable();
			inParameters.put("esn_part_number", partNumber);

			return execute(inParameters);
		}
	}

	private class IGateRtaInFunction extends StoredProcedure {
		public IGateRtaInFunction() {
			super(getDataSource(), RUN_IGATE_IN);
			// setFunction(true);
			// declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			compile();
		}

		public Map execute() {
			return execute(new Hashtable());
		}

	}

	private class DeactivateEsnFunction extends StoredProcedure {
		public DeactivateEsnFunction() {
			super(getDataSource(), DEACTIVATE_ESN_BY_PART_NUMBER);
			// setFunction(true);
			declareParameter(new SqlParameter("P_ESN", Types.VARCHAR));

			compile();
		}

		public Map execute(String partNumber) {
			Map inParameters = new Hashtable();
			inParameters.put("P_ESN", partNumber);

			return execute(inParameters);
		}
	}

	private class RemoveEsnFromSTAccount extends StoredProcedure {
		public RemoveEsnFromSTAccount() {
			super(getDataSource(), REMOVE_ESN_FROM_ST_ACCOUNT);
			// setFunction(true);
			declareParameter(new SqlParameter("p_esn", Types.VARCHAR));

			compile();
		}

		public Map execute(String esn) {
			Map inParameters = new Hashtable();
			inParameters.put("p_esn", esn);

			return execute(inParameters);
		}
	}

	private class PinTestFunction extends StoredProcedure {
		public PinTestFunction() {
			super(getDataSource(), GET_TEST_PIN_BY_PART_NUMBER);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("pin_part_number", Types.VARCHAR));

			compile();
		}

		public String execute(String partNumber) {
			Map inParameters = new Hashtable();
			inParameters.put("pin_part_number", partNumber);
			Map outMap = execute(inParameters);
			String result = (String) outMap.get("return");

			return result;
		}
	}

	private class ClearTNumberFunction extends StoredProcedure {
		public ClearTNumberFunction() {
			super(getDataSource(), CLEAR_TNUMBER);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("V_ESN", Types.VARCHAR));
			declareParameter(new SqlParameter("P_AC", Types.VARCHAR));

			compile();
		}

		public void execute(String esn) {
			Map inParameters = new Hashtable();
			inParameters.put("V_ESN", esn);
			inParameters.put("P_AC", "954");
			Map outMap = execute(inParameters);
			String result = (String) outMap.get("return");
			logger.info(result);
			System.out.println(result);

		}
	}

	private class VerifyPhoneUpgrade extends StoredProcedure {
		public VerifyPhoneUpgrade() {
			super(getDataSource(), VERIFY_PHONE_UPGRADE);
			declareParameter(new SqlParameter("ip_str_old_esn", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_str_new_esn", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_str_zip", Types.VARCHAR));
			declareParameter(new SqlParameter("ip_str_iccid", Types.VARCHAR));
			declareParameter(new SqlOutParameter("op_carrier_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("op_error_text", Types.VARCHAR));
			declareParameter(new SqlOutParameter("op_error_num", Types.VARCHAR));

			compile();
		}

		public boolean execute(String oldEsn, String newEsn, String zip, String iccid) {
			Hashtable<String, String> in = new Hashtable<String, String>();
			in.put("ip_str_old_esn", oldEsn);
			in.put("ip_str_new_esn", newEsn);
			in.put("ip_str_zip", zip);
			in.put("ip_str_iccid", iccid);
			Map out = execute(in);
			logger.info(oldEsn);
			if (!out.isEmpty()) {
				String op_error_num = out.get("op_error_num").toString();
				logger.info(op_error_num);

				if ("0".equals(op_error_num) || "9991".equals(op_error_num)) {
					return true;
				}
			}
			return false;
		}
	}

	private class CheckPhoneUpgrade extends StoredProcedure {
		public CheckPhoneUpgrade() {
			super(getDataSource(), CHECK_PHONE_UPGRADE);
			declareParameter(new SqlParameter("v_transtype", Types.VARCHAR));
			declareParameter(new SqlParameter("v_fromESN", Types.VARCHAR));
			declareParameter(new SqlParameter("v_toESN", Types.VARCHAR));
			declareParameter(new SqlParameter("v_fromSIM", Types.VARCHAR));
			declareParameter(new SqlParameter("v_toSIM", Types.VARCHAR));
			declareParameter(new SqlParameter("v_fromPIN", Types.VARCHAR));
			declareParameter(new SqlParameter("v_toPIN", Types.VARCHAR));
			declareParameter(new SqlOutParameter("returnCode", Types.NUMERIC));
			declareParameter(new SqlOutParameter("returnStr", Types.VARCHAR));
			declareParameter(new SqlParameter("v_ipaddress", Types.VARCHAR));

			compile();
		}

		public void execute(ESN fromEsn, ESN toEsn) {
			Hashtable<String, String> in = new Hashtable<String, String>();
			in.put("v_transtype", toEsn.getTransType());
			in.put("v_fromESN", fromEsn.getEsn());
		    in.put("v_toESN", toEsn.getEsn());
		    in.put("v_fromSIM", fromEsn.getSim());
		    in.put("v_toSIM", toEsn.getSim());
		    in.put("v_fromPIN", fromEsn.getPin());
		    in.put("v_toPIN", toEsn.getPin());
		    in.put("v_ipaddress", getIpAddress());
		    
		    Map out = execute(in);
		    if(!out.isEmpty()) {
				String returnCode = out.get("returnCode").toString();
				String returnStr = out.get("returnStr").toString();
				logger.info(returnStr);
				
//				Assert.assertEquals(returnStr, "0", returnCode);
		    } else {
		    	throw new IllegalArgumentException("Check Upgrade not returning result");
		    }
		}
	}
	
	private class MeidDecToHex extends StoredProcedure {
		public MeidDecToHex() {
			super(getDataSource(), MEID_DEC_TO_HEX);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("P_DECNUM", Types.VARCHAR));
	
			compile();
		}

		public String execute(ESN esn) {
			Hashtable<String, String> in = new Hashtable<String, String>();
			in.put("P_DECNUM", esn.getEsn());

		    Map out = execute(in);
		    if(!out.isEmpty()) {
				String returnHex = out.get("return").toString();
				esn.setHexESN(returnHex);
				return returnHex;
		    } else {
		    	throw new IllegalArgumentException("Could not convert CDMA dec esn to hex esn");
		    }
		}
	}
	
	private class GetICCIDAndZip extends MappingSqlQuery {
		public GetICCIDAndZip() {
			super(getDataSource(), GET_ACTIVE_ICCID);
			declareParameter(new SqlParameter("x_service_id", Types.VARCHAR));
			/*
			 * declareParameter(new SqlOutParameter("x_zipcode",
			 * Types.VARCHAR)); declareParameter(new SqlOutParameter("",
			 * Types.VARCHAR));
			 */

			compile();
		}

		
		protected ICCIDAndZipWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
			String iccid = rs.getString("x_iccid");
			String zip = rs.getString("x_zipcode");
			return new ICCIDAndZipWrapper(iccid, zip);
		}
	}
	

	private class ICCIDAndZipWrapper {
		public String zip;
		public String iccid;

		public ICCIDAndZipWrapper(String iccid, String zip) {
			this.iccid = iccid;
			this.zip = zip;
		}
	}
	
	private class GetSAObjid extends MappingSqlQuery {
		public GetSAObjid() {
			super(getDataSource(), GET_OBJID);
//			declareParameter(new SqlOutParameter("objid", Types.VARCHAR));

			compile();
		}

		
		protected String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return  rs.getString("objid");
		}
	}
	
	private class GetB2BNewEsns extends MappingSqlQuery {
		public GetB2BNewEsns() {
			super(getDataSource(), GET_NEW_B2B_ESNS);
			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ESN esn = new ESN("B2B", rs.getString("part_serial_no"));
			esn.setEmail(rs.getString("login_name"));
			esn.setPassword(rs.getString("password"));
			esn.setCommerceId(rs.getString("x_commerce_id"));
			esn.setOrderId(rs.getString("clfy_order_id"));
			esn.setSim(rs.getString("X_ICCID"));
			return esn;
		}
	}

	private class GetSimFromEsn extends MappingSqlQuery {
		public GetSimFromEsn() {
			super(getDataSource(), GET_SIM_FROM_ESN);
			declareParameter(new SqlParameter("part_serial_no", Types.VARCHAR));

			compile();
		}

		
		protected String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return  rs.getString("x_iccid");
		}
	}
	

	private class GetReservedLine extends MappingSqlQuery {
		public GetReservedLine() {
			super(getDataSource(), SQL_RESERVED_LINE_BY_ESN_QUERY);
			declareParameter(new SqlParameter("pin_part_number", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("min");
		}
	}

	private class GetNextCmdaEsn extends MappingSqlQuery {
		public GetNextCmdaEsn() {
			super(getDataSource(), SQL_GET_NEXT_CDMA);
			// declareParameter(new SqlOutParameter("pin_part_number",
			// Types.VARCHAR));

			compile();
		}

		
		protected String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("ESN");
		}
	}

	private class GetMinOfActiveEsn extends MappingSqlQuery {
		public GetMinOfActiveEsn() {
			super(getDataSource(), SQL_MIN_OF_ACTIVE_ESN);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));

			compile();
		}

		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("x_min");
		}
	}

	private class IgTransactionCdmaPhoneActivationUpdate extends SqlUpdate {
		public IgTransactionCdmaPhoneActivationUpdate() {
			super(getDataSource(), SQL_UPDATE_IG_TRANSACTION_CDMA_PHONE_ACTIVATION);
			declareParameter(new SqlParameter("min", Types.VARCHAR));
			declareParameter(new SqlParameter("msid", Types.VARCHAR));
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String msid, String esn) {
			return update(new Object[] { msid, msid, esn });
		}
	}

	private class UpdateOTATransactions extends SqlUpdate {
		public UpdateOTATransactions() {
			super(getDataSource(), SQL_UPDATE_OTA_PENDING_BY_ESN);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class AddToTestOtaEsn extends SqlUpdate {
		public AddToTestOtaEsn() {
			super(getDataSource(), SQL_ADD_TO_TEST_OTA_ESN);
			declareParameter(new SqlParameter("ESN", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class AddToTestIgateEsn extends SqlUpdate {
		public AddToTestIgateEsn() {
			super(getDataSource(), SQL_ADD_TO_TEST_IGATE_ESN);
			declareParameter(new SqlParameter("ESN", Types.VARCHAR));
			declareParameter(new SqlParameter("ESN_TYPE", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn, String type) {
			return update(new Object[] { esn, type });
		}
	}

	private class ChangeTestIgateEsnType extends SqlUpdate {
		public ChangeTestIgateEsnType() {
			super(getDataSource(), SQL_CHANGE_TEST_IGATE_ESNTYPE);
			declareParameter(new SqlParameter("ESN_TYPE", Types.VARCHAR));
			declareParameter(new SqlParameter("ESN", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn, String type) {
			return update(new Object[] { type, esn });
		}
	}

	private class IgTransactionGsmPhoneActivationUpdate extends SqlUpdate {
		public IgTransactionGsmPhoneActivationUpdate() {
			super(getDataSource(), SQL_UPDATE_IG_TRANSACTION_GSM_PHONE_ACTIVATION);
			declareParameter(new SqlParameter("msid", Types.VARCHAR));
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String msid, String esn) {
			return update(new Object[] { msid, esn });
		}
	}

	private class PhoneActivationWithSameMinUpdate extends SqlUpdate {
		public PhoneActivationWithSameMinUpdate() {
			super(getDataSource(), SQL_UPDATE_PHONE_ACTIVATION_SAME_MIN);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class PhoneActivationAfterPortIn extends SqlUpdate {
		public PhoneActivationAfterPortIn() {
			super(getDataSource(), SQL_UPDATE_PHONE_ACTIVATION_PORT_MIN);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class FinishCdmaByopIgate extends SqlUpdate {
		public FinishCdmaByopIgate() {
			super(getDataSource(), SQL_FINISH_CDMA_BYOP_IGATE);
			declareParameter(new SqlParameter("x_pool_name", Types.VARCHAR));
			declareParameter(new SqlParameter("x_MPN", Types.VARCHAR));
			declareParameter(new SqlParameter("STATUS_MESSAGE", Types.VARCHAR));
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			declareParameter(new SqlParameter("template", Types.VARCHAR));
			compile();
		}

		public int update(String x_pool_name, String x_MPN, String esn, String template, boolean isActive) {
			if (isActive) {
				return update(new Object[] {x_pool_name, x_MPN, "ESN_IN_USE: Device is in use", esn, template  });
			} else {
				return update(new Object[] {x_pool_name, x_MPN, "isPIBLock=N,isLostorStolen=N,isBYOPEligible=Y,returnCode=S0048,returnMessage=EQUIPMENT IS ELIGIBLE,validWithCarrier=Y", esn, template });
			}
		}
	}
	
	private class CompleteCdmaByopIgate extends SqlUpdate {
		public CompleteCdmaByopIgate() {
			super(getDataSource(), SQL_COMPLETE_CDMA_BYOP_IGATE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			declareParameter(new SqlParameter("template", Types.VARCHAR));
			compile();
		}

		public int update(String esn, String template) {
				 return update(new Object[] { esn, template});
		}
	}

	private class FinishRedeemIgate extends SqlUpdate {
		public FinishRedeemIgate() {
			super(getDataSource(), SQL_FINISH_REDEEM_IGATE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	
	private class FinishAllIgate extends SqlUpdate {
		public FinishAllIgate() {
			super(getDataSource(), SQL_FINISH_ALL_IGATE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class SetDueDateInFutureSite extends SqlUpdate {
		public SetDueDateInFutureSite() {
			super(getDataSource(), SET_DUE_DATE_IN_FUTURE_SITE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	
	private class SetDueDateInPastSite extends SqlUpdate {
		public SetDueDateInPastSite() {
			super(getDataSource(), SET_DUE_DATE_IN_PAST_SITE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class SetDueDateInFuturePartInst extends SqlUpdate {
		public SetDueDateInFuturePartInst() {
			super(getDataSource(), SET_DUE_DATE_IN_FUTURE_PART_INST);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	
	private class SetDueDateInPastPartInst extends SqlUpdate {
		public SetDueDateInPastPartInst() {
			super(getDataSource(), SET_DUE_DATE_IN_PAST_PART_INST);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	private class AddSimToEsn extends SqlUpdate {
		public AddSimToEsn() {
			super(getDataSource(), ADD_SIM_TO_ESN);
			declareParameter(new SqlParameter("x_iccid", Types.VARCHAR));
			declareParameter(new SqlParameter("part_serial_no", Types.VARCHAR));
			compile();
		}

		
		public int update(String sim, String esn) {
			return update(new Object[] { sim, esn });
		}
	}

	private class AddPinToQueue extends SqlUpdate {
		public AddPinToQueue() {
			super(getDataSource(), ADD_PIN_TO_QUEUE);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			declareParameter(new SqlParameter("pin", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn, String pin) {
			return update(new Object[] { esn, pin });
		}
	}

	private class ClearOTA extends SqlUpdate {
		public ClearOTA() {
			super(getDataSource(), CLEAR_OTA);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	
	private class SetMinForPortTicket extends SqlUpdate {
		public SetMinForPortTicket() {
			super(getDataSource(), SET_MIN_FOR_PORT_TICKET);
			declareParameter(new SqlParameter("min", Types.VARCHAR));
			declareParameter(new SqlParameter("ticket", Types.VARCHAR));
			compile();
		}

		
		public int update(String min, String ticket) {
			return update(new Object[] { min, ticket });
		}
	}
	
	private class updateEsnforInactiveStatus extends SqlUpdate {
		public updateEsnforInactiveStatus() {
			super(getDataSource(), ESNUPDATE_INACTIVE_STATUS);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	
	private class updateBIRecord extends SqlUpdate {
		public updateBIRecord() {
			super(getDataSource(), ESNUPDATE_BI_RECORD);
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}
	
	private class ResetILDCounter extends SqlUpdate {
		public ResetILDCounter() {
			super(getDataSource(), RESET_ILD_COUNT);
			declareParameter(new SqlParameter("x_service_id", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	
	public synchronized String getOrgIdByOrgName(String orgName) {
		String orgId = (String) this.clfyjdbcTemplate.queryForObject(DB2_GET_ORGID, new Object[] { orgName }, String.class);
		if (orgId == null || orgId.isEmpty()) {
			throw new IllegalArgumentException("No organization cust with org provided: "+ orgName);
		} else {
			return orgId;
		}
	}
	
	
	public synchronized String getParentCarrierName(String esn) {
	//	logger.info(GET_PARENT_CARRIER_NAME);
		String carrierName = (String) this.clfyjdbcTemplate.queryForObject(GET_PARENT_CARRIER_NAME, new Object[] { esn }, String.class);
		if (carrierName == null || carrierName.isEmpty()) {
			throw new IllegalArgumentException("No Carreir name found for  "+ esn);
		} else {
			return carrierName;
		}
	}
	
	
	public synchronized void setEsnInstallDatetoPast(String esn, int days) {
		try{
			this.clfyjdbcTemplate.update(SET_INSTALL_DATE_TO_PAST,new Object[] { days , esn });
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public synchronized void setEsnServiceEndDatetoPast(String esn, int days) {
		try {
			this.clfyjdbcTemplate.update(SET_SERVICE_END_DATE_TO_PAST, new Object[] { days, esn });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public synchronized String getPhonePartNumberbySimandModel(String model, String simPart, String brand) {
		String partNumber = (String)clfyjdbcTemplate.queryForObject(GET_PHONE_PART_BASED_ON_SIM_AND_MODEL, new Object[] { model , simPart, brand }, String.class);
		if (partNumber == null || partNumber.isEmpty()) {
			throw new IllegalArgumentException("No part numberfound for  "+ model);
		} else {
			return partNumber;
		}
	}

	
	public synchronized void changeDealerForEsn(String esn, String dealerId) {
		try {
			this.clfyjdbcTemplate.update(CHANGE_DELAER_ESN, new Object[] { dealerId, esn });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	public synchronized void setFlashforEsn(String esn,int flashType,String flashId) {
		if (flashId.equalsIgnoreCase("50")) {
			if (flashType == 0) {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1083988680", esn });
			} else {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1083988681", esn });
			}
		}

		if (flashId.equalsIgnoreCase("52")) {
			if (flashType == 0) {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1090347659", esn });
			} else {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1090347657", esn });
			}
		}

		if (flashId.equalsIgnoreCase("51")) {
			if (flashType == 0) {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1083988705", esn });
			} else {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1090347647", esn });
			}
		}
		
		if (flashId.equalsIgnoreCase("54")) {
			if (flashType == 0) {
				clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW, new Object[] { "1083988727", esn });
			} else {
				// clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN_NEW,new Object[]
				// { "1083988681",esn });
			}
		}
	}

	
	public synchronized void insertIntoServiceResultsTable(String operationName, String request, StringBuffer response, String elapsedTime, String url) {
		clfyjdbcTemplate.update(INSERT_INTO_CBO_RESULTS_TABLE, new Object[] { operationName , request, response, elapsedTime, url });
	}
	
	
	public synchronized String getAccountIdbyEsn(String esn) {
		String accountId = null;
		try {
			accountId = (String) clfyjdbcTemplate.queryForObject(GET_ACCOUNT_ID_BY_ESN, new Object[] { esn }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No records found for ESN");
		}
		return accountId;	
	}
	
	
	public synchronized String getEsnAttribute(String reqValue,String esn) {
		String requiredValue = null;
		try{
			Map<String, Object> resMap = clfyjdbcTemplate.queryForMap(GET_PROFILE_VALUE_BY_ESN, new Object[] { esn });
			requiredValue = resMap.get(reqValue).toString();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new IllegalArgumentException("No reocords found");
		}
		return requiredValue;	
	}
		
	public synchronized String getWebobjidByEmail(String email) {
		String webobjid = null;
		try {
			webobjid = (String) clfyjdbcTemplate.queryForObject(GET_WEB_OBJID_BY_EMAIL, new Object[] { email }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No reocords found for provided email");
		}
		return webobjid;	
	}
	
	private final static String  GET_WEB_OBJID_BY_EMAIL="select objid from table_web_user where login_name= ?";
	
	
	public synchronized String getPinPartNumberfromPin(String pin) {
		String accountId = null;
		try {
			accountId = (String) clfyjdbcTemplate.queryForObject(GET_PIN_PART_FROM_PIN_NUMBER, new Object[] { pin }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No records found for given pin");
		}
		return accountId;	
	}
	
	
	
	public synchronized ArrayList<Object> getRewardBenefitDetails(String category, String subCategory, String benefitsEarned) {
		ArrayList<Object> benefitDetails = new ArrayList<Object>();
		try {
			SqlRowSet  rs = clfyjdbcTemplate.queryForRowSet(GET_REWARD_BENEFIT_DETAILS, new Object[] { category, subCategory, benefitsEarned });
			while (rs.next()) {
				benefitDetails.add(rs.getString("objid"));
				benefitDetails.add(rs.getString("transaction_type"));
				benefitDetails.add(rs.getInt("benefits_earned"));
				benefitDetails.add(rs.getString("category"));
				benefitDetails.add(rs.getString("sub_category"));
				benefitDetails.add(rs.getString("transaction_description"));
			}
			return benefitDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No records found for provided benefit objid");
		}
	}
	
	
	public synchronized void updateMaturirtyDateforAcc(String email) {
		String webobjid = getWebobjidByEmail(email);
		try {
			if (webobjid != null && !webobjid.isEmpty()) {
				clfyjdbcTemplate.update(UPDATE_MATURITY_DATE_FOR_ACC, new Object[] { webobjid });
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("No records Updated");
		}
	}

	
	public synchronized String getEsnLeasedStaus(String esn) {
		String leaseStatus;
		try {
			leaseStatus = (String) clfyjdbcTemplate.queryForObject(GET_ESN_LEASED_STATUS, new Object[] { esn }, String.class);
		} catch (Exception e) {
			leaseStatus = "NOLEASE";
		}
		return leaseStatus;
	}
	
	public synchronized int getProgramIdforServicePlan(String sp,boolean isReccuring) {
		int progId;
		String reccuringVal=isReccuring ? "1": "0";
		try {
			progId = (int) clfyjdbcTemplate.queryForObject(GET_SP_PARAM_OBJID_FOR_PINPART, new Object[] { sp,reccuringVal }, Integer.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("No records Found");
		}
		return progId;
	}
	
	private final static String GET_ESN_LEASED_STATUS = "select lease_status from sa.x_customer_lease where x_esn = ? and rownum <2 order by objid desc ";
	
	private final static String REWARDS_BATCH_COMPLETE_TRANSACTION = "SA.REWARDS_BATCH_PKG.P_COMPLETE_TRANSACTION";
	
	private final static String UPDATE_MATURITY_DATE_FOR_ACC="update sa.x_reward_benefit_transaction set maturity_date = (sysdate - 1) where 1 = 1 and  web_account_id = ?  and  transaction_status = 'PENDING' ";
	
	private final static String GET_REWARD_BENEFIT_DETAILS="select objid,transaction_type,benefits_earned,category,SUB_CATEGORY,transaction_description from sa.X_REWARD_BENEFIT_EARNING where 1=1 "+
															"  AND CATEGORY= ? AND SUB_CATEGORY= ? AND BENEFITS_EARNED= ? and END_DATE > SYSDATE";
	
	private final static String  GET_PIN_PART_FROM_PIN_NUMBER="SELECT  pn.part_number from TABLE_MOD_LEVEL ML,TABLE_PART_NUM PN,"
															  +"table_part_class pc,table_x_red_card rc WHERE 1=1and rc.x_red_code = ?"
															  +"and rc.x_red_card2part_mod = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID AND pc.objid =pn.part_num2part_class";
	
	private final static String  GET_PROFILE_VALUE_BY_ESN="select * from  table(sa.adfcrm_vo.get_service_profile( ? ,null))";

	private final static String  GET_ACCOUNT_ID_BY_ESN ="select w.objid FROM TABLE_PART_INST pi, TABLE_X_CONTACT_PART_INST cp, table_contact c,table_web_user w WHERE"
														+ "  pi.objid = cp.x_contact_part_inst2part_inst and cp.x_contact_part_inst2contact = c.objid "
														+ "  AND w.web_user2contact =  c.objid AND pi.part_serial_no = ? ";
	
	private final static String INSERT_INTO_CBO_RESULTS_TABLE = "insert into ITQUSER_DATA.ITQ_SERVICE_RESULTS (OPERATION,REQUEST,RESPONSE,CREATION_TIME,ELAPSED_TIME,URL) values ( ?,?,?,sysdate,?,?)";
	
	private final static String INSERT_FLASH_FOR_ESN_NEW ="insert into esn_to_alert (alert_objid,x_esn)values(?, ?)";
			
	private final static String INSERT_FLASH_FOR_ESN = "INSERT INTO table_alert( objid, alert_text, start_date,end_date,active, title,last_update2user,"+
                                          " alert2contract,  hot,  type, X_CANCEL_SQL,X_WEB_TEXT_ENGLISH,   X_WEB_TEXT_SPANISH,X_IVR_SCRIPT_ID, X_TTS_ENGLISH, X_TTS_SPANISH  )"+
                                          "VALUES(sa.seq ('alert'),'Flash',sysdate - 1,  sysdate + 1825,1,'ITQ Testing', 268435556,(select objid from table_part_inst where part_serial_no = ?),"+
                                          " ?, 'GENERIC', '', NULL, NULL, NULL, '', '')";
	
	private final static String GET_PHONE_PART_BASED_ON_SIM_AND_MODEL = "select phone_part_num from Simoutconfrules where vendor_model = ? and sim_profile= ? and org_id= ? ";
	
	private final static String GET_PARENT_CARRIER_NAME = "SELECT p.x_parent_name parent_name FROM table_part_inst pi_esn,table_part_inst pi_min,table_x_parent p,"
			+"table_x_carrier_group cg,table_x_carrier c WHERE pi_esn.part_serial_no = ? AND pi_esn.x_domain = 'PHONES'" 
			+"AND pi_min.part_to_esn2part_inst = pi_esn.objid AND pi_min.x_domain = 'LINES' AND pi_min.part_inst2carrier_mkt = c.objid " 
			+"AND c.carrier2carrier_group = cg.objid  AND cg.x_carrier_group2x_parent = p.objid AND rownum <2";
	
	private final static String SET_INSTALL_DATE_TO_PAST = "update table_site_part   set install_date =trunc(sysdate)- ?    where x_service_id=?";
	
	private final static String SET_SERVICE_END_DATE_TO_PAST = "update table_site_part   set service_end_dt =trunc(sysdate)- ?    where x_service_id=?";
	
	private final static String GET_STAGE_STATUS = "select status from X_SERVICE_ORDER_STAGE where esn = ?";
	
	private final static String GET_TEST_ESN_IMSI="SA.get_test_esn_IMSI";

	private final static String GET_TEST_NEW_ESN_BY_PART_NUMBER = "sa.get_test_esn";
	
	private final static String GET_TEST_BYOP_ESN = "sa.get_test_esn_byop";

	private final static String CHECK_ACTIVATION = "check_activation";
	
	private final static String CHECK_REGISTER = "check_register";

	private final static String INSERT_INTO_B2B_TABLE = "ITQ_B2B_RESULT";
	
	private final static String Load_X_B2B_PROMO_ORG = "ITQ_B2B_PROMO_ORG";
	
	private final static String SM_DEACTIVATION = "SA.SERVICE_DEACTIVATION_CODE.DEACTSERVICE";

	private final static String THROTTLE_ESN = "W3CI.THROTTLING.SP_THROTTLING_VALVE";
	
	private final static String VERIFY_PHONE_UPGRADE = "sa.verify_phone_upgrade_pkg.verify";

	private final static String CHECK_PHONE_UPGRADE = "CHECK_PHONE_UPGRADE";
	
	private final static String GET_ACTIVE_ICCID = "select x_zipcode, X_ICCID from table_site_part where part_status='Active' and x_service_id= ?";

	private final static String GET_TEST_ACTIVE_ESN_BY_PART_NUMBER = "SELECT  pi.part_serial_no FROM TABLE_PART_INST PI, TABLE_MOD_LEVEL ML, TABLE_PART_NUM PN, table_part_class pc,"
			+ " TABLE_SITE_PART sp, SA.TEST_IGATE_ESN ig WHERE PI.N_PART_INST2PART_MOD = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID AND pi.x_part_inst2site_part = sp.objid and pc.objid=pn.part_num2part_class "
			+ "and sp.x_min not like 'T%' and sp.part_status='Active' and pi.x_part_inst_status = '52' and pi.X_DOMAIN||''='PHONES' and pn.part_number = ? and (x_model_number not in ('STAPI4C', 'STAPI4SC', 'STAPI5C') OR "
			+ "pi.x_hex_serial_no is not null) and sp.x_expire_dt > sysdate and ig.ESN=pi.part_serial_no and ig.ESN_TYPE='C' and ROWNUM < 2";

	private final static String GET_PORT_ESN_BY_PART_NUMBER = "SELECT pi.part_serial_no FROM TABLE_PART_INST PI, TABLE_MOD_LEVEL ML, TABLE_PART_NUM PN, table_part_class pc,"
			+ " TABLE_SITE_PART sp, SA.TEST_IGATE_ESN ig WHERE PI.N_PART_INST2PART_MOD = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID AND pi.x_part_inst2site_part = sp.objid and pc.objid=pn.part_num2part_class "
			+ "and sp.x_min not like 'T%' and sp.part_status='Active' and pi.x_part_inst_status = '52' and pi.X_DOMAIN||''='PHONES' and pn.part_number = ? and ig.ESN=pi.part_serial_no and ig.ESN_TYPE='C' and ROWNUM < 2";

	private final static String GET_ACTIVE_33178_ESN_BY_PART_NUMBER= "SELECT  pi.part_serial_no FROM TABLE_PART_INST PI, TABLE_MOD_LEVEL ML, TABLE_PART_NUM PN, table_part_class pc,"
			+ " TABLE_SITE_PART sp, SA.TEST_IGATE_ESN ig WHERE PI.N_PART_INST2PART_MOD = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID AND pi.x_part_inst2site_part = sp.objid and pc.objid=pn.part_num2part_class "
			+ "and sp.x_min not like 'T%' and sp.part_status='Active' and pi.x_part_inst_status = '52' and pi.X_DOMAIN||''='PHONES' and pn.part_number = ? and (x_model_number not in ('STAPI4C', 'STAPI4SC', 'STAPI5C') OR "
			+ "pi.x_hex_serial_no is not null) and sp.x_expire_dt > sysdate and sp.x_actual_expire_dt > sysdate and ig.ESN=pi.part_serial_no and ig.ESN_TYPE='C' and sp.x_zipcode = '33178' and ROWNUM < 5";

	private final static String GET_ACTIVE_ESN_BY_PART_NUMBER_NO_ACCOUNT = "SELECT  pi.part_serial_no FROM TABLE_PART_INST PI, TABLE_MOD_LEVEL ML, TABLE_PART_NUM PN, table_part_class pc, "
			+ "TABLE_SITE_PART sp WHERE PI.N_PART_INST2PART_MOD = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID AND pi.x_part_inst2site_part = sp.objid and pc.objid=pn.part_num2part_class and "
			+ "sp.x_min not like 'T%' and sp.part_status='Active' and pi.x_part_inst_status='52' and pi.X_DOMAIN||''='PHONES' and pn.part_number like ? and (x_model_number not in ('STAPI4C', 'STAPI4SC', 'STAPI5C') OR "
			+ "pi.x_hex_serial_no is not null)  and not exists (select null from TABLE_X_CONTACT_PART_INST cp, table_contact c, table_web_user w WHERE pi.objid = cp.x_contact_part_inst2part_inst "
			+ "and w.web_user2contact =  c.objid and cp.x_contact_part_inst2contact = c.objid) and ROWNUM < 2";

	private final static String GET_ACTIVE_ESN_BY_BRAND = "SELECT pi.part_serial_no FROM table_part_inst pi, table_mod_level ml, table_part_num pn, table_bus_org org, table_part_inst line, TABLE_SITE_PART sp "
			+ "WHERE pi.x_domain || '' = 'PHONES' AND pi.n_part_inst2part_mod = ml.objid AND ml.part_info2part_num = pn.objid and pn.part_num2bus_org=org.objid "
			+ "AND pi.x_part_inst2site_part = sp.objid and line.part_to_esn2part_inst = pi.objid AND line.x_domain || '' = 'LINES'  and sp.x_zipcode = '33178' and "
			+ "sp.x_min not like 'T%' and pi.x_part_inst_status='52' and sp.part_status='Active' and org.s_org_id=? and ROWNUM < 30";

	private final static String GET_NEW_ESN_BY_PART_NUMBER_NO_ACCOUNT = "SELECT  pi.part_serial_no FROM TABLE_PART_INST PI, TABLE_MOD_LEVEL ML, TABLE_PART_NUM PN, "
			+ "table_part_class pc WHERE PI.N_PART_INST2PART_MOD = ML.OBJID AND ML.PART_INFO2PART_NUM = PN.OBJID and pc.objid=pn.part_num2part_class "
			+ "AND pi.x_part_inst_status = '50' and pi.X_DOMAIN||''='PHONES' and pn.part_number = ? and (x_model_number not in ('STAPI4C', 'STAPI4SC', 'STAPI5C') OR "
			+ "pi.x_hex_serial_no is not null) and ROWNUM < 2 and not exists (select null from TABLE_X_CONTACT_PART_INST cp, table_contact c, table_web_user w WHERE pi.objid ="
			+ " cp.x_contact_part_inst2part_inst and w.web_user2contact =  c.objid and cp.x_contact_part_inst2contact = c.objid)";

	private final static String GET_HEX_ESN_BY_DEC_ESN = "SELECT pi.x_hex_serial_no, x_model_number, pi.part_serial_no FROM TABLE_PART_INST pi, TABLE_MOD_LEVEL ml, "
			+ "TABLE_PART_NUM pn, table_part_class pc WHERE pi.N_PART_INST2PART_MOD = ml.OBJID AND ml.PART_INFO2PART_NUM = pn.OBJID and pc.objid=pn.part_num2part_class "
			+ "and X_DOMAIN||''='PHONES' and pi.part_serial_no = ? and pi.x_hex_serial_no is not null and rownum < 2";
//			"and X_DOMAIN||''='PHONES' and x_model_number in ('STAPI4C', 'STAPI4SC', 'STAPI5C') and pi.part_serial_no = ? and pi.x_hex_serial_no is not null and rownum < 2";

	private final static String GET_TEST_PAST_DUE_ESN_BY_PART_NUMBER = "SELECT part_serial_no FROM table_part_inst pi, table_mod_level ml, table_part_num pn WHERE 1=1 AND x_domain || '' = 'PHONES' "
			+ "AND pi.n_part_inst2part_mod = ml.objid AND ml.part_info2part_num = pn.objid AND pn.s_part_number = ? "
			+ "AND x_part_inst_status=54 AND rownum < 2 " ;
	
	private final static String GET_TEST_USED_ESN_BY_PART_NUMBER = "SELECT part_serial_no FROM table_part_inst pi, table_mod_level ml, table_part_num pn WHERE 1=1 AND x_domain || '' = 'PHONES' "
			+ "AND pi.n_part_inst2part_mod = ml.objid AND ml.part_info2part_num = pn.objid AND pn.s_part_number = ? "
			+ "AND x_part_inst_status=51 AND rownum < 2 " ;
	
	private final static String GET_TEST_REFURB_ESN_BY_PART_NUMBER = "SELECT part_serial_no FROM table_part_inst pi, table_mod_level ml, table_part_num pn WHERE 1=1 AND x_domain || '' = 'PHONES' "
			+ "AND pi.n_part_inst2part_mod = ml.objid AND ml.part_info2part_num = pn.objid AND pn.s_part_number = ? "
			+ "AND x_part_inst_status=150 AND rownum < 2 " ;

	private final static String GET_TEST_PIN_BY_PART_NUMBER = "sa.get_test_pin";
	private final static String CLEAR_TNUMBER = "SA.clear_tnumber";
	private final static String DEACTIVATE_ESN_BY_PART_NUMBER = "SA.deactivate_past_due_esn";
	private final static String GET_TEST_ACTIVE_ST_ESN_BY_PART_NUMBER = "sa.get_active_esn_pn1";
	private final static String RUN_IGATE_IN = "sa.igate_in3.rta_in";
	private final static String REMOVE_ESN_FROM_ST_ACCOUNT = "remove_myaccount_st";

	private final static String SQL_UPDATE_IG_TRANSACTION_CDMA_PHONE_ACTIVATION = "update ig_transaction set STATUS = 'W', min = ?, msid = ?, NEW_MSID_FLAG='Y' "
			+ " where ACTION_ITEM_ID = (select action_item_id from gw1.IG_TRANSACTION where esn = ? and status = 'L' and STATUS <> 'S' and rownum = 1 )";

	private final static String SQL_UPDATE_IG_TRANSACTION_GSM_PHONE_ACTIVATION = "update ig_transaction set STATUS = 'W', msid = ?, NEW_MSID_FLAG='Y' "
			+ " where ACTION_ITEM_ID = (select action_item_id from gw1.IG_TRANSACTION where esn = ? and status = 'L' and STATUS <> 'S' and rownum = 1 )";

	private final static String SQL_UPDATE_PHONE_ACTIVATION_SAME_MIN = "update ig_transaction set STATUS = 'W' where ACTION_ITEM_ID = "
			+ "(select action_item_id from gw1.IG_TRANSACTION where esn = ? and status = 'L' and order_type <> 'S' and rownum = 1 )";

	private final static String SQL_UPDATE_PHONE_ACTIVATION_PORT_MIN = "update ig_transaction set STATUS = 'W' where ACTION_ITEM_ID = "
			+ "(select action_item_id from gw1.IG_TRANSACTION where esn = ? and (order_type = 'PIR' or order_type = 'IPI' or order_type = 'E')  and rownum = 1 )";

	private final static String SQL_FINISH_CDMA_BYOP_IGATE = "update ig_transaction set STATUS = 'S', APPLICATION_SYSTEM='IG_RWK', x_pool_name= ?, X_MPN= ?,  STATUS_MESSAGE= ? where ESN = ? and ORDER_TYPE='VD' and TEMPLATE = ?";
	//"update ig_transaction set STATUS = 'W', APPLICATION_SYSTEM='IG_RWK', X_MPN='APL', X_MPN_CODE='IPHONE 4 BLACK 8GB', STATUS_MESSAGE='TracFone: Validate Device completed succesfully' where ESN = ? and ORDER_TYPE='VD' and TEMPLATE = ?
	//'ESN_IN_USE: Device is in use'
	
	private final static String SQL_COMPLETE_CDMA_BYOP_IGATE = "update ig_transaction set STATUS = 'F' where ESN = ? and ORDER_TYPE='VD' and TEMPLATE = ?";
	
	private final static String SQL_FINISH_REDEEM_IGATE = "update ig_transaction set STATUS = 'W' where ESN = ? and ORDER_TYPE='CR'";
	
	private final static String SQL_FINISH_ALL_IGATE = "update ig_transaction set STATUS = 'W' where ESN = ? and STATUS='L'";

	private final static String SQL_RESERVED_LINE_BY_ESN_QUERY = "SELECT " + "TABLE_PART_INST.OBJID as id"
			+ ", TABLE_PART_INST.PART_SERIAL_NO as min" + ", TABLE_PART_INST.X_MSID as msid" + ", TABLE_PART_INST.X_NXX as nxx"
			+ ", TABLE_PART_INST.X_NPA as npa" + ", TABLE_PART_INST.X_EXT as ext"
			+ ", TABLE_PART_INST.X_PART_INST_STATUS as status" + ", TABLE_PART_INST.PART_INST2X_PERS as personalityObjid"
			+ ", TABLE_PART_INST.PART_INST2X_NEW_PERS as newPersonalityObjid"
			+ ", TABLE_PART_INST.PART_INST2CARRIER_MKT as marketObjid" + ", TABLE_PART_INST.X_PORT_IN as linePortInStatus"
			+ ", TABLE_PART_INST.PART_TO_ESN2PART_INST as phoneObjid" + " FROM TABLE_X_PARENT pa," + " TABLE_X_CARRIER_GROUP gr,"
			+ " TABLE_X_CARRIER ca," + " TABLE_PART_INST," + " table_part_inst pi2"
			+ " WHERE pa.objid = x_carrier_group2x_parent" + " AND carrier2carrier_group = gr.objid"
			+ " AND TABLE_PART_INST.part_inst2carrier_mkt = ca.objid" + " and TABLE_PART_INST.part_to_esn2part_inst = pi2.objid"
			+ " AND TABLE_PART_INST.x_part_inst_status IN ('37', '39', '73')"
			+ " AND TABLE_PART_INST.part_serial_no IN (SELECT part_serial_no FROM TABLE_PART_INST"
			+ " WHERE part_to_esn2part_inst IN (SELECT objid FROM TABLE_PART_INST" + " where part_serial_no in (?)))";

	private final static String SQL_MIN_OF_ACTIVE_ESN = "SELECT X_MIN FROM TABLE_SITE_PART WHERE X_SERVICE_ID = ? AND PART_STATUS IN ('Active', 'CarrierPending')";

	private final static String SQL_GET_NEXT_CDMA = "select to_char(sa.cdma_serial_no_seq.nextval) as ESN from dual";

	private final static String SQL_UPDATE_OTA_PENDING_BY_ESN = "update table_x_OTA_TRANSACTION " + " set X_STATUS='Completed' "
			+ " WHERE X_STATUS='OTA PENDING' " + " AND X_ESN= ?";

	private final static String SQL_ADD_TO_TEST_OTA_ESN = "insert into GW1.TEST_OTA_ESN values (?)";

	private final static String SQL_ADD_TO_TEST_IGATE_ESN = "insert into SA.TEST_IGATE_ESN values (?, ?)";

	private final static String SQL_CHANGE_TEST_IGATE_ESNTYPE = "update SA.TEST_IGATE_ESN set ESN_TYPE= ? where ESN= ?";

	private final static String ADD_SIM_TO_ESN = "UPDATE table_part_inst SET x_iccid=? WHERE part_serial_no=? AND x_part_inst_status='50'";
	
	private final static String GET_SIM_FROM_ESN = "select x_iccid from table_part_inst WHERE part_serial_no= ? ";

	private final static String SET_DUE_DATE_IN_FUTURE_SITE = "UPDATE table_site_part " + "SET x_expire_dt = trunc(sysdate+32), "
			+ "warranty_date = trunc(sysdate+31) " + "WHERE x_service_id = ?";

	private final static String SET_DUE_DATE_IN_FUTURE_PART_INST = "UPDATE table_part_inst "
			+ "SET warr_end_date =trunc(sysdate+31) " + "WHERE part_serial_no = ?";
	
	private final static String SET_DUE_DATE_IN_PAST_SITE = "UPDATE table_site_part " + "SET x_expire_dt = trunc(sysdate-10), "
			+ "warranty_date = trunc(sysdate-10) " + "WHERE x_service_id = ?";

	private final static String SET_DUE_DATE_IN_PAST_PART_INST = "UPDATE table_part_inst "
			+ "SET warr_end_date =trunc(sysdate-10) " + "WHERE part_serial_no = ?";

	private final static String ADD_PIN_TO_QUEUE = "update table_part_inst set x_part_inst_status = '400', "
			+ "x_ext = '1', part_to_esn2part_inst = (select objid from table_part_inst "
			+ "where part_serial_no = ?) " + "where x_red_code = ?";

	private final static String CLEAR_OTA = "update table_x_ota_transaction set x_status = 'Completed' where x_esn = ?";
	
	private final static String ESNUPDATE_INACTIVE_STATUS = "update table_part_inst set part_status='INACTIVE', x_part_inst_status='59' WHERE part_serial_no= ?";
	
	private final static String ESNUPDATE_BI_RECORD = "update x_pcrf_transaction set pcrf_status_code = 'S', data_usage = 725008934 where objid = (select max(objid) from x_pcrf_transaction where esn = ? and order_type = 'BI')";
	
	private final static String RESET_ILD_COUNT = "update x_vas_event_counter set objectvalue_event_counter=1 " 
			+ "where vas_objectvalue = ( select x_min from table_site_part where x_service_id= ? "
			+ "and part_status ='Active' and x_actual_expire_dt > sysdate)";

	private final static String GET_OBJID = "SELECT objid FROM TABLE_USER WHERE s_login_name ='SA'";
	
	private final static String CHECK_REDEMPTION = "CHECK_REDEMPTION";
	
	private final static String GET_STATE_BY_ZIPCODE="select x_state from table_x_zip_code where x_zip = ?";
	
	private final static String MEID_DEC_TO_HEX = "sa.meiddectohex"; 

	private final static String GET_ESN_BY_BRAND_QUEUE = "select ESN from itq_gen_cases where QUEUE_NAME = ? and BRAND = ?";
	
	private final static String GET_ENROLLMENT_STATUS = "select x_enrollment_status from  x_program_enrolled where  x_esn= ?";
	
	private final static String DB2_GET_ORGID = "select x_commerce_id from table_site where s_name = ?";
	
	private final static String GET_ORDERID_BY_ESN = "select X_PRODUCT_ID from table_x_ild_transaction where x_esn = ?";
	
//  private final static String GET_LRP_POINTS_BY_EMAIL = "SELECT AMOUNT FROM X_REWARD_BENEFIT_TRANSACTION where WEB_ACCOUNT_ID in (SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = ? )";
//	SELECT AMOUNT FROM X_REWARD_BENEFIT_TRANSACTION where TRANS_TYPE = 'REDEMPTION' AND WEB_ACCOUNT_ID in 
//			(SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = 'cbwuixzz@tracfone.com');
	
	private final static String GET_LRP_POINTS_BY_EMAIL = "SELECT AMOUNT FROM X_REWARD_BENEFIT_TRANSACTION where TRANS_TYPE = 'REDEMPTION' AND WEB_ACCOUNT_ID in (SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = ? )";

	private final static String GET_LRP_POINTS_BY_EMAIL_FOR_TRANSTYPE = "SELECT AMOUNT FROM X_REWARD_BENEFIT_TRANSACTION where TRANS_TYPE = ? AND WEB_ACCOUNT_ID in (SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = ? )";
	
	private final static String GET_ACTIVATION_POINTS_BY_ESN = "SELECT mtm.REWARD_POINT FROM table_part_inst pi_esn, table_site_part sp, x_service_plan_site_part spsp, MTM_SP_REWARD_PROGRAM mtm WHERE pi_esn.part_serial_no = ? AND pi_esn.x_domain = 'PHONES' AND pi_esn.x_part_inst2site_part = sp.objid AND sp.objid = spsp.table_site_part_id AND spsp.X_SERVICE_PLAN_ID = mtm.SERVICE_PLAN_OBJID AND mtm.END_DATE >= trunc(sysdate)";
	
	private final static String GET_TRANS_DESC_FOR_LRP_TRANSTYPE="SELECT TRANS_DESC FROM X_REWARD_BENEFIT_TRANSACTION where TRANS_TYPE = ? AND WEB_ACCOUNT_ID in (SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = ? )";
	
	private final static String GET_TOTAL_LRP_POINTS_BY_EMAIL = "SELECT SUM(AMOUNT) FROM X_REWARD_BENEFIT_TRANSACTION where WEB_ACCOUNT_ID in (SELECT OBJID FROM table_web_user WHERE LOGIN_NAME = ? )";

	private final static String GET_LRP_POINTS_BY_OBJID="SELECT REWARD_POINT FROM MTM_SP_REWARD_PROGRAM WHERE END_DATE >= trunc(sysdate) and SERVICE_PLAN_OBJID = ? ";
	private final static String GET_LRP_POINTS_FOR_PINPART = "SELECT REWARD_POINT FROM MTM_SP_REWARD_PROGRAM WHERE END_DATE >= trunc(sysdate) and SERVICE_PLAN_OBJID IN (SELECT spl.objid esn_plan_objid "         
			+ "FROM table_part_num pn,table_part_class pc,mtm_partclass_x_spf_value_def mtmspfv,x_serviceplanfeature_value spfv,x_service_plan_feature spf,x_service_plan spl,table_bus_org bo "
			+ "WHERE     1 = 1 "
			+ "AND pc.objid = pn.part_num2part_class AND mtmspfv.part_class_id(+) = pc.objid AND mtmspfv.spfeaturevalue_def_id = spfv.value_ref(+) "
			+ "AND spfv.spf_value2spf = spf.objid(+) AND spf.sp_feature2service_plan = spl.objid(+) AND pn.domain = 'REDEMPTION CARDS' "
			+ "AND pn.part_number = ? and pn.part_num2bus_org = bo.objid and rownum <2) ";
	
	private final static String GET_SP_PARAM_OBJID_FOR_PINPART= "SELECT x_sp2program_param FROM table_part_num pn,table_part_class pc,mtm_partclass_x_spf_value_def mtmspfv,x_serviceplanfeature_value spfv,"
																+" x_service_plan_feature spf,x_service_plan spl,table_bus_org bo ,MTM_SP_X_PROGRAM_PARAM mtm ,x_program_parameters ppm WHERE 1= 1  AND pc.objid= pn.part_num2part_class"
																+" AND mtmspfv.part_class_id(+)= pc.objid AND mtmspfv.spfeaturevalue_def_id = spfv.value_ref(+) AND spfv.spf_value2spf= spf.objid(+) " 
																+" AND spf.sp_feature2service_plan= spl.objid(+) AND pn.domain='REDEMPTION CARDS' AND pn.part_number= ? "
																+" AND mtm.X_SP2PROGRAM_PARAM = (select OBJID from X_PROGRAM_PARAMETERS where x_program_name=spl.mkt_name)"
																+" AND ppm.X_IS_RECURRING= ? AND ppm.objid=mtm.X_SP2PROGRAM_PARAM  AND mtm.program_para2x_sp = spl.objid"
																+" AND pn.part_num2bus_org= bo.objid";
	
	private final static String GET_LRP_POINTS_FOR_TRANSTYPE = "select BENEFITS_EARNED from X_REWARD_BENEFIT_EARNING WHERE TRANSACTION_TYPE = ? and END_DATE >= trunc(sysdate)";
	
	private final static String GET_TICKETID_FOR_LID = "Select X_CURRENT_TICKET_ID from X_Sl_Currentvals where LID = ? ";
//	private final static String GET_NEW_B2B_ESNS = "SELECT pi.part_serial_no, ts.x_commerce_id, wu.login_name, pi.X_ICCID, wu.login_name as order_id, pi.X_ICCID as password FROM table_x_contact_part_inst cpi, table_web_user wu, x_site_web_accounts swa, table_site ts, table_part_inst pi WHERE 1=1 and pi.x_insert_date > (sysdate-1) AND wu.objid = swa.site_web_acct2web_user AND x_contact_part_inst2contact = wu.web_user2contact and  site_web_acct2site = ts.objid and x_contact_part_inst2part_inst = pi.objid and  ts.x_commerce_id is not null and pi.x_part_inst_status = '50'";

	private final static String GET_NEW_B2B_ESNS = "SELECT pi.part_serial_no, ts.x_commerce_id, wu.login_name, itq.password, itq.clfy_order_id, pi.X_ICCID "
			+ "FROM table_x_contact_part_inst cpi, table_web_user wu, x_site_web_accounts swa, table_site ts, table_part_inst pi, ITQ_B2B_DATA itq WHERE 1=1 "
			+ "AND wu.objid = swa.site_web_acct2web_user AND x_contact_part_inst2contact = wu.web_user2contact and ts.x_commerce_id = itq.ecomm_org_id and " 
			+ "site_web_acct2site = ts.objid and x_contact_part_inst2part_inst = pi.objid and itq.clfy_order_id is not null and wu.login_name = itq.USERNAME and order_date > '18-MAY-14'" 
			+ " and pi.x_part_inst_status = '50'";

    private final static String INSERT_INTO_UNLOCK_SPC = "insert into unlock_spc_encrypt values ( ? ,null,null,'34CigdXXFSLHHLEMnIPiSPxrmAsQ7VNU6eglX8m37PHIBB0+UQMS77R8nthTxRrY','ZzpoQ9CjdDCZ93JUmdy5X/VgQg7mnb2Z76z0+iZrDQzMxQDNRnoVH892iDNCQmuM',null,'0UR+NntXN1HbiUTLVIyiaqy9dEfbSmv2VoX1kHB9ObFUHv75VizeQJ+WQW7Sx8G06uZYo+WyC3kE26n+qCGQ0/XCd/Ct3hcDNkUCqzsn/VVHnmybv7zA0QGupPGJKOVCBnTnJU+J5yQ/0uv/UEQtK4d6iLL0mmR2FLASO8sdkb8=','dp-oem-tst-01182017.cert','http://www.w3.org/2001/04/xmlenc#rsa-1_5','http://www.w3.org/2001/04/xmlenc#aes256-cbc','LOCKED')";

	private final static String INSERT_INTO_BALANCE_BUCKET="insert into X_SWB_TX_BALANCE_BUCKET select SEQU_X_BALANCE_BUCKET.nextval,(select min(objid) from x_switchbased_transaction where X_SB_TRANS2X_CALL_TRANS =(select objid from TABLE_X_CALL_TRANS where x_service_id= ? and x_action_type='7')),null, x_type, EXPIRATION_DATE, x_value,null from X_SWB_TX_BALANCE_BUCKET where BALANCE_BUCKET2X_SWB_TX = (select BALANCE_BUCKET2X_SWB_TX from X_SWB_TX_BALANCE_BUCKET where rownum='1')";
    
	private final static String INSERT_INTO_BALANCE_BUCKET_PPE="Insert into  SA.TABLE_X_OTA_ACK (OBJID,X_OTA_ERROR_CODE,X_OTA_ERROR_MESSAGE,X_OTA_NUMBER_OF_CODES,X_OTA_CODES_ACCEPTED,X_UNITS,X_PHONE_SEQUENCE,X_PSMS_ACK_MSG,X_OTA_ACK2X_OTA_TRANS_DTL,X_SERVICE_END_DT,X_SMS_UNITS,X_DATA_UNITS,X_PRE_UNITS) values (SA.SEQU_X_OTA_ACK.nextval,null,null,4,1,200,null, ? ,(SELECT objid FROM table_x_ota_trans_dtl WHERE x_ota_trans_dtl2X_ota_trans in (SELECT max(objid) FROM TABLE_X_OTA_TRANSACTION WHERE X_ESN= ? )),null,200,200,NULL)";
	
	private final static String GET_DEVICETYPE_FOR_ESN="select VAL from table(sa.adfcrm_ret_esn_info( ? )) where COL='PC_PARAMS DEVICE_TYPE'";
	
	private final static String INSERT_INTO_PROMO_GROUP="INSERT INTO x_loyalty_rewards (objId, esn, MIN, cust_id, load_date) values (sa.SEQ_X_LOYALTY_REWARDS.nextval, ? , ? ,(select X_CUST_ID from ( select X_CUST_ID from table_contact where E_MAIL= ? order by X_CUST_ID) where rownum=1), sysdate)";

	private final static String CLEAR_SWITCH_BASED_TRANSACTION = " UPDATE SA.X_SWITCHBASED_TRANSACTION SWBTX SET SWBTX.STATUS = 'COMPLETED' WHERE SWBTX.X_SB_TRANS2X_CALL_TRANS IN (SELECT OBJID FROM TABLE_X_CALL_TRANS WHERE X_SERVICE_ID= ?) ";

	private final static String GET_ESN_DETAILS="select VAL from table(sa.adfcrm_ret_esn_info( ? )) where COL=?";
	
	private final static String GET_CONTACT_DETAILS="select first_name from table_contact where objid=?";
	
	private final static String SET_MIN_FOR_PORT_TICKET="update table_x_case_detail set x_value = ? where detail2case in (select objid from table_case where id_number=?) and x_name in ('NEW_MSID','MSID','CURRENT_MIN')";

	private final static String GET_ENROLL_STATUS_FOR_ESN="SELECT pe.X_ENROLLMENT_STATUS FROM X_Program_Enrolled pe,X_PROGRAM_PARAMETERS pp WHERE 1=1 AND pe.x_esn = ? AND pe.pgm_enroll2pgm_parameter=pp.objid AND pp.x_prog_class <> 'WARRANTY' AND pe.x_insert_date > sysdate-(5/1440)";
	
	private final static String GET_BI_RECORD_FOR_ESN = "select TRANSACTION_ID from IG_TRANSACTION where ESN = ? and ORDER_TYPE = 'BI'";
	
	private final static String GET_PAYMENT_SOURCE_FOR_EMAIL="SELECT PS.OBJID FROM X_PAYMENT_SOURCE PS, TABLE_WEB_USER WU, TABLE_X_CREDIT_CARD cc WHERE PS.PYMT_SRC2WEB_USER = WU.OBJID AND PS.PYMT_SRC2X_CREDIT_CARD=CC.OBJID AND X_CUSTOMER_CC_NUMBER like ? AND S_LOGIN_NAME= ? ";

	private final static String UPDATE_THROTTLING_TRANSACTION_STATUS="update W3CI.TABLE_X_THROTTLING_TRANSACTION Set x_status = 'W' where x_ESN =  ? ";
	
	private final static String CHANGE_DELAER_ESN = "UPDATE table_part_inst SET PART_INST2INV_BIN = (SELECT objid FROM table_inv_bin WHERE bin_name=?) WHERE part_serial_no= ? ";

	private final static String GET_PART_INST_STATUS = "select X_PART_INST_STATUS  from table_part_inst where part_serial_no= ? ";
	
	private final static String GET_PROMO_HIST_FOR_ESN = "select promo_hist2x_call_trans from table_x_promo_hist where 1=1 and "
			 + "promo_hist2x_promotion in (select objid from table_x_promotion where x_promo_code = ? ) "
			 + "and promo_hist2x_call_trans in (select max(objid) from table_x_call_trans where x_service_id = ? and x_action_type in ('1','3','6'))";
	
	private final static String GET_USED_PIN_OF_ESN = "select X_RED_CODE from table_x_red_card where RED_CARD2CALL_TRANS IN (select OBJID from table_x_call_trans a where x_service_id = ?)";
	
	private final static String GET_ORDER_TYPE_OF_ESN = "SELECT ORDER_TYPE FROM (select * from ig_transaction where esn= ? ORDER BY ACTION_ITEM_ID DESC) WHERE ROWNUM = 1";
	
	
	public void setOfsjdbcTemplate(JdbcTemplate ofsjdbcTemplate) {
		this.ofsjdbcTemplate = ofsjdbcTemplate;
	}

	public void setDb2jdbcTemplate(JdbcTemplate db2jdbcTemplate) {
		this.db2jdbcTemplate = db2jdbcTemplate;
	}
	
	public void setClfyjdbcTemplate(JdbcTemplate clfyjdbcTemplate) {
		this.clfyjdbcTemplate = clfyjdbcTemplate;
	}

	
	public synchronized List<String> getProductIDbyESN(String esn) {
		ArrayList<String> productIdList = new ArrayList<String>();
		SqlRowSet rs =this.clfyjdbcTemplate.queryForRowSet(GET_ORDERID_BY_ESN,new Object[] { esn });
		while(rs.next()){

			productIdList.add(rs.getString("X_PRODUCT_ID"));

		}
		return productIdList;
	}

	
	public synchronized String getLRPPointsbyEmail(String email) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_LRP_POINTS_BY_EMAIL, new Object[] { email }, String.class);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ email);
		} else {
			return objid;
		}
	}

	
	public synchronized String getLRPPointsbyEmailforTranstype(String Transtype, String email) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_LRP_POINTS_BY_EMAIL_FOR_TRANSTYPE, new Object[] { Transtype,email }, String.class);
		System.out.println(objid);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ email);
		} else {
			return objid;
		}
	}
	
	
	public synchronized String getActivationPointsByEsn(String esn) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_ACTIVATION_POINTS_BY_ESN, new Object[] { esn }, String.class);
		System.out.println(objid);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ esn);
		} else {
			return objid;
		}
	}
	
	
	public synchronized String getTotalLRPPointsbyEmail(String email) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_TOTAL_LRP_POINTS_BY_EMAIL, new Object[] { email }, String.class);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ email);
		} else {
			return objid;
		}
	}

	
	public synchronized String getTicketIDforLID(String lid) {
		String TicketID = (String) this.clfyjdbcTemplate.queryForObject(GET_TICKETID_FOR_LID, new Object[] { lid }, String.class);
		if (TicketID == null || TicketID.isEmpty()) {
			throw new IllegalArgumentException("TicketID not found for : "+ lid);
		} else {
			return TicketID;
		}
	}
	
	
	public synchronized int getInsertSmartphoneEsnforUpgrade(String esn) {
		return InsertSmartphoneEsnforUpgrade.update(esn);
		
	}
	
	private class InsertSmartphoneEsnforUpgrade extends SqlUpdate {
		public InsertSmartphoneEsnforUpgrade() {
			super(getDataSource(),INSERT_INTO_BALANCE_BUCKET );
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			compile();
		}

		
		public int update(String esn) {
			return update(new Object[] { esn });
		}
	}

	
	public synchronized int getInsertPPEphoneEsnforUpgrade(String ACK_MSG,String esn) {
		return InsertPPEphoneEsnforUpgrade.update(ACK_MSG,esn);
	}
	
	private class InsertPPEphoneEsnforUpgrade extends SqlUpdate {
		public InsertPPEphoneEsnforUpgrade() {
			super(getDataSource(),INSERT_INTO_BALANCE_BUCKET_PPE );
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			declareParameter(new SqlParameter("X_PSMS_ACK_MSG", Types.VARCHAR));
			compile();
		}

		
		public int update(String ACK_MSG,String esn) {									
			return update(new Object[] {ACK_MSG,esn });
		}
	}

	
	public synchronized String getDevicetype(String esn) {
		String Device = (String) this.clfyjdbcTemplate.queryForObject(GET_DEVICETYPE_FOR_ESN, new Object[] { esn }, String.class);
		if (Device == null || Device.isEmpty()) {
			throw new IllegalArgumentException("DeviceType not found for : "+ esn);
		} else {
			return Device;
		}
	}
	
	
	public synchronized String getEsnDetails(String esn, String col) {
		String data = (String) this.clfyjdbcTemplate.queryForObject(GET_ESN_DETAILS, new Object[] { esn, col }, String.class);
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException("Data not found for : "+ esn + col);
		} else {
			return data;
		}
	}
	
	
	public synchronized String getContactDetails(String objid) {
		String data = (String) this.clfyjdbcTemplate.queryForObject(GET_CONTACT_DETAILS, new Object[] { objid }, String.class);
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException("Data not found for : "+ objid);
		} else {
			return data;
		}
	}
	
	
	public synchronized String getLRPPointsforPinPart(String PinPart) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_LRP_POINTS_FOR_PINPART, new Object[] { PinPart }, String.class);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ PinPart);
		} else {
			return objid;
		}
	}
	
	
	public synchronized String getLRPPointsbyObjid(String Objid) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_LRP_POINTS_BY_OBJID, new Object[] { Objid }, String.class);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ Objid);
		} else {
			return objid;
		}
	}
	
	public synchronized String getTransdescforLRPTransType(String Transtype, String email) {
		String objid = (String) this.clfyjdbcTemplate.queryForObject(GET_TRANS_DESC_FOR_LRP_TRANSTYPE, new Object[] { Transtype,email }, String.class);
		if (objid == null || objid.isEmpty()) {
			throw new IllegalArgumentException("Objid not found for : "+ email);
		} else {
			return objid;
		}
	}
	
	
	public synchronized String getLRPPointsForTransType(String transType) {
		String lrpPoints = (String) this.clfyjdbcTemplate.queryForObject(GET_LRP_POINTS_FOR_TRANSTYPE, new Object[] { transType }, String.class);
		if (lrpPoints == null || lrpPoints.isEmpty()) {
			throw new IllegalArgumentException("LRP Points not found for : "+ transType);
		} else {
			return lrpPoints;
		}
	}
	
	public synchronized int clearCarrierPending(String esn) {
		return finishRedeemIgate.update(esn);
	}
	
	
	public synchronized int LRPAddEsntoPromoGroup(String esn,String min, String email) {
		return AddEsntoPromoGroup.update(esn , min, email);
		
	}
	
	private class AddEsntoPromoGroup extends SqlUpdate {
		public AddEsntoPromoGroup() {
			super(getDataSource(),INSERT_INTO_PROMO_GROUP );
			declareParameter(new SqlParameter("esn", Types.VARCHAR));
			declareParameter(new SqlParameter("min", Types.VARCHAR));
			declareParameter(new SqlParameter("cust_id", Types.VARCHAR));
			compile();
		}

		public int update(String esn,String min, String email) {
			return update(new Object[] {esn , min, email});
		}	
	}
	
	
	public synchronized String checkEnrollment(String esn) {
		String accountId = null;
		try {
			accountId = (String) clfyjdbcTemplate.queryForObject(GET_ENROLL_STATUS_FOR_ESN, new Object[] { esn }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No status found");
		}
		
		return accountId;	
	}
	
	
	public synchronized String checkBI(String esn) {
		String objId = null;
		try {
			objId = (String) clfyjdbcTemplate.queryForObject(GET_BI_RECORD_FOR_ESN, new Object[] { esn }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No status found");
		}
		
		return objId;	
	}

	
	public String validatePromo(String esn, String promo) {
		String objId = null;
		try {
			objId = (String) clfyjdbcTemplate.queryForObject(GET_PROMO_HIST_FOR_ESN, new Object[] { promo, esn }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No status found");
		}
		
		return objId;	
	}
	
	
	public String checkpartinststatus(String esn) {
		String Status = (String) this.clfyjdbcTemplate.queryForObject(GET_PART_INST_STATUS, new Object[] { esn }, String.class);
		if (Status == null || Status.isEmpty()) {
			throw new IllegalArgumentException("X_PART_INST_STATUS not found for : "+ esn);
		} else {
			return Status;
		}
	}
	
		private class UsedPinFunction extends MappingSqlQuery {
			public UsedPinFunction() {
				super(getDataSource(), GET_USED_PIN_OF_ESN);
				declareParameter(new SqlParameter("esn", Types.VARCHAR));

				compile();
			}

			
			protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("x_red_code");
			}
		}
		
		
		public synchronized String getUsedPinOfEsn(String esn) {
			List result = usedPinFunction.execute(esn);

			logger.debug("Call to " + GET_USED_PIN_OF_ESN);
			logger.debug("		ESN --> " + esn);
			logger.debug("		result --> " + result);

			if (result.isEmpty()) {
				throw new IllegalArgumentException("No pin found for ESN: " + esn);
			} else {
				String pin = (String) result.get(0);
				return pin;
			}
		}
		
		public String getOrderType(String esn) {
			String orderType = (String) this.clfyjdbcTemplate.queryForObject(GET_ORDER_TYPE_OF_ESN, new Object[] { esn }, String.class);
			if (orderType == null || orderType.isEmpty()) {
				throw new IllegalArgumentException("Order type not found for : "+ esn);
			} else {
				return orderType;
			}
		}
		
		
		public void insertFlash(String esn,String flashType) {
			clfyjdbcTemplate.update(INSERT_FLASH_FOR_ESN,new Object[] { esn ,flashType });	
		}
}