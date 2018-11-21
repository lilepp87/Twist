package com.tracfone.twist.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;

public class ESNUtil {

	private static PhoneUtil phoneUtil;
	private ESN currentESN = null;
	private boolean shouldPass = true;
	private static final String GSM = "GSM";
	private static final String CDMA = "CDMA";
	private static final Map<String, Queue<ESN>> pastDueFutureEsns = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> pastDueWithPinEsns = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> recentActiveEsns = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> recentEsnsWithPin = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> recentPastDueEsns = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> recentEsnsWithoutPlan = new Hashtable<String, Queue<ESN>>();
	private static final Map<String, Queue<ESN>> stolenEsns = new Hashtable<String, Queue<ESN>>();
	private static final Queue<ESN> newB2bEsns = new ArrayDeque<ESN>();
	private static final Queue<ESN> newB2bByopEsns = new ArrayDeque<ESN>();
	private static final Queue<ESN> activeB2bEsns = new ArrayDeque<ESN>();
	private static final String NEW_STATUS = "New";
	private String brand = "";
	private String extCarrierName = "";
	private static Logger logger = LogManager.getLogger(ESNUtil.class.getName());
	private String flowName = "";
	private String deviceType;
	private String sourceSystem;
	
	@Autowired
	private TwistScenarioDataStore scenarioStore;
	
	public void addRecentActiveEsn(ESN esn, String cellTech, String status, String transType) {
		List<ESN> esns = new ArrayList<ESN>();
		esns.add(esn);
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			esns.addAll(esn.getFamilyEsns());
		}
		
		for (ESN currEsn : esns) {
			if (currEsn.getEsn().length() == 15) {
				finishGsmActivation(currEsn);
			} else {
				finishCdmaActivation(currEsn);
			}
			TwistUtils.setDelay(1);
		}

		int actionType;
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			actionType = 1;
		} else {
			actionType = 3;
		}
		phoneUtil.runIGateIn();
		
		for (ESN currEsn : esns) {
			TwistUtils.setDelay(1);
			phoneUtil.clearOTAforEsn(currEsn.getEsn());		
			phoneUtil.checkActivation(currEsn, actionType, transType+ " (" + status + ")");
			TwistUtils.setDelay(1);
			currEsn.setMin(phoneUtil.getMinOfActiveEsn(currEsn.getEsn()));
			currEsn.clearTestState();
			addEsnToQueue(currEsn, recentActiveEsns);
		}
	}
	
	public synchronized ESN getNewB2bEsn() {
		if (newB2bEsns.isEmpty()) {
			populateB2bEsns();
		}
		ESN esn = newB2bEsns.poll();
		if (esn == null) {
			throw new IllegalArgumentException("No B2B Esns for activation");
		} else {
			return esn;
		}
	}

	public synchronized ESN getNewB2bByopEsn() {
		if (newB2bByopEsns.isEmpty()) {
			populateB2bEsns();
		}
		ESN esn = newB2bByopEsns.poll();
		if (esn == null) {
			throw new IllegalArgumentException("No B2B Byop Esns for activation");
		} else {
			return esn;
		}
	}
	
	private synchronized void populateB2bEsns() {
		if (!newB2bByopEsns.isEmpty() || !newB2bEsns.isEmpty()) {
			return;
		}
		List<ESN> b2bEsns = phoneUtil.getNewB2BEsns();
		for (ESN esn: b2bEsns) {
			String esnStr = esn.getEsn();
			String sim = esn.getSim();
			if (sim != null && sim.endsWith(esnStr)) {
				newB2bByopEsns.add(esn);
			} else {
				newB2bEsns.add(esn);
			}
		}
	}

	public void addActiveB2bEsn(ESN esn) {
		addRecentActiveEsn(esn, "", "New", "B2B Activation");
		activeB2bEsns.add(esn);
	}
	
	public ESN popActiveB2bEsn() {
		ESN esn = activeB2bEsns.poll();
		if (esn == null) {
			throw new IllegalArgumentException("No recent B2B active esns");
		} else {
			return esn;
		}
	}
	
	public void addBackActiveEsn(ESN esn) {
		addEsnToQueue(esn, recentActiveEsns);
	}
	
	public boolean peekRecentActiveEsn(String partNumber) {
		Queue<ESN> esns = recentActiveEsns.get(partNumber);

		if (esns != null && !esns.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public ESN popRecentActiveEsn(String partNumber) {
		return popRecentEsnFromQueue(partNumber, recentActiveEsns);
	}

	public void addRecentEsnWithPin(ESN esn) {
		addEsnToQueue(esn, recentEsnsWithPin);
	}

	public ESN popRecentEsnWithPin(String partNumber) {
		return popRecentEsnFromQueue(partNumber, recentEsnsWithPin);
	}

	public void addRecentPastDueEsn(ESN esn) {
		addEsnToQueue(esn, recentPastDueEsns);
	}

	public ESN popRecentPastDueEsn(String partNumber) {
		return popRecentEsnFromQueue(partNumber, recentPastDueEsns);
	}

	public void addPastDueInFuture(ESN esn) {
		addEsnToQueue(esn, pastDueFutureEsns);
	}

	public ESN popPastDueInFuture(String partNumber) {
		return popRecentEsnFromQueue(partNumber, pastDueFutureEsns);
	}

	public void addPastDueEsnWithPin(ESN esn) {
		addEsnToQueue(esn, pastDueWithPinEsns);
	}

	public ESN popPastDueEsnWithPin(String partNumber) {
		return popRecentEsnFromQueue(partNumber, pastDueWithPinEsns);
	}

	public ESN popRecentEsnWithoutPlan(String partNumber) {
		return popRecentEsnFromQueue(partNumber, recentEsnsWithoutPlan);
	}

	public void addRecentEsnWithoutPlan(ESN esn, String cellTech) {
		addEsnToQueue(esn, recentEsnsWithoutPlan);
		if (GSM.equalsIgnoreCase(cellTech)) {
			finishGsmActivation(esn);
		} else {
			finishCdmaActivation(esn);
		}
	}

	public void addRecentStolenEsn(ESN esn) {
		addEsnToQueue(esn, stolenEsns);
	}

	public ESN popRecentStolenEsn(String partNumber) {
		return popRecentEsnFromQueue(partNumber, stolenEsns);
	}

	private synchronized void addEsnToQueue(ESN esn, Map<String, Queue<ESN>> map) {
		if (esn == null) {
			throw new IllegalArgumentException("Trying to add bad ESN to recent active esn");
		}

		Queue<ESN> esns = map.get(esn.getPartNumber());
		if (esns == null) {
			esns = new ArrayDeque<ESN>();
			map.put(esn.getPartNumber(), esns);
		}

		esns.add(esn);
	}

	private synchronized ESN popRecentEsnFromQueue(String partNumber, Map<String, Queue<ESN>> map) {
		Queue<ESN> esns = map.get(partNumber);

		if (esns != null && !esns.isEmpty()) {
			ESN esn = esns.poll();
			phoneUtil.clearOTAforEsn(esn.getEsn());
			phoneUtil.resetILDCounter(esn.getEsn());
			return esn;
		}

		throw new IllegalArgumentException("No recent ESNs for this part");
	}

	private void finishGsmActivation(ESN esn) {
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		if (min.isEmpty() || min.startsWith("T")) {
//			min = TwistUtils.generateRandomMin();
			phoneUtil.clearTNumber(esn.getEsn());
//			phoneUtil.finishGsmPhoneActivation(min, esn.getEsn());
		} else {
			phoneUtil.finishPhoneActivationWithSameMin(esn.getEsn());
		} 	
	}

	private void finishCdmaActivation(ESN esn) {
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		if (min.isEmpty() || min.startsWith("T")) {
//			min = TwistUtils.generateRandomMin();
			phoneUtil.clearTNumber(esn.getEsn());
//			phoneUtil.finishCdmaPhoneActivation(min, esn.getEsn());
		} else {
			phoneUtil.finishPhoneActivationWithSameMin(esn.getEsn());
		}
	}

	public synchronized ESN getCurrentESN() {
		return currentESN;
	}

	public synchronized void setCurrentESN(ESN currentESN) {
		System.out.println("Current Part: " + currentESN.getPartNumber() + " ESN: " + currentESN.getEsn());
		scenarioStore.put("PartNumber-", currentESN.getPartNumber());
		scenarioStore.put("ESN-", currentESN.getEsn());
		this.currentESN = currentESN;
	}

	public boolean shouldPass() {
		return shouldPass;
	}

	public void setShouldPass(boolean shouldPass) {
		this.shouldPass = shouldPass;
	}

	public synchronized void clearCurrentESN() {
		if (currentESN != null) {
			this.currentESN.clearTestState();			
		}
		
		this.currentESN = null;
		this.shouldPass = true;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public String getCurrentBrand() {
		return brand;
	}

	public void setCurrentBrand(String brand) {
		this.brand = brand;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getExtCarrierName() {
		return extCarrierName;
	}

	public void setExtCarrierName(String extCarrierName) {
		this.extCarrierName = extCarrierName;
	}
	
	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}


//	public String getDeviceType() {
//		return deviceType;
//	}
//
//	public void setDeviceType(String deviceType) {
//		this.deviceType = deviceType;
//	}

}