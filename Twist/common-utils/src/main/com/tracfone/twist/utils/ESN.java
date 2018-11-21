package com.tracfone.twist.utils;

import java.util.*;

public class ESN {

	private final String partNumber;
	private final String esn;
	private String hexESN;
	private String email;
	private String min;
	private String password;
	private String sim;
	private ESN fromESN;
	private String transType;
	private String redeemType;
	private int actionType;
	private String buyFlag;
	private String pin;
	private int enrolledDays = 30;
	private static final String NO_HEX_ESN = "No Hex ESN";
	private List<ESN> familyEsns = new ArrayList<ESN>();
    private Map famiyESNMap = new Hashtable();
    private String zipCode;
    private String lastFourDigits;
    private String lastSixDigits;
    private String accountNumber;
	private String commerceId;
	private String orderId;
	private String deviceType;
	private Map<String, String> map = new Hashtable<String, String>();
	private String paymentMethod;
	private String lineAction;
	private int servicePlanId;
	private String pinPartNumber;

//	public ESN() {
//		partNumber = null;
//		esn = null;
//	}

	public Map getFamiyESNMap() {
		return famiyESNMap;
	}

	public void setFamiyESNMap(Map famiyESNMap) {
		this.famiyESNMap = famiyESNMap;
	}

	public ESN(String part, String esn) {
		if (esn == null || part == null || part.isEmpty() || esn.isEmpty()) {
			throw new IllegalArgumentException("Part number and ESN cannot be empty");
		}
		this.partNumber = part;
		this.esn = esn;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public String getEsn() {
		return esn;
	}
	
	public String getCommerceId() {
		return (commerceId == null) ? "" : commerceId;
	}

	public void setCommerceId(String commerceId) {
		this.commerceId = commerceId;
	}
	
	public String getOrderId() {
		return (orderId == null) ? "" : orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getEmail() {
		return (email == null) ? "" : email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMin() {
		return (min == null) ? "" : min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getPassword() {
		return (password == null) ? "" : password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSim() {
		return (sim == null) ? "" : sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}
	
	public String getHexESN() {
		return (hexESN == null) ? "" : hexESN;
	}

	public void setHexESN(String hexESN) {
		this.hexESN = hexESN;
	}
	
	public void setFromEsn(ESN esn) {
		fromESN = esn;
		if (esn != null && esn.email != null) {
			this.setEmail(esn.email);
			this.setPassword(esn.password);
		}
	}
	
	public ESN getFromEsn() {
		return fromESN;
	}

	public void clearTestState() {
		redeemType = null;
		actionType = 0;
		buyFlag = null;
		pin = null;
		transType = null;
	}
	
	public String getRedeemType() {
		return (redeemType == null) ? "" : redeemType;
	}

	public void setRedeemType(boolean isRecurring) {
		if (isRecurring) {
			this.redeemType = "RECURRING";
		} else {
			this.redeemType = "PAYASYOUGO";
		}
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		if (6 == actionType || 401 == actionType) {
			this.actionType = actionType;	
		} else {
			throw new IllegalArgumentException("Unknown action type: " + actionType);
		}
	}

	public String getBuyFlag() {
		return (buyFlag == null) ? "" : buyFlag;
	}

	public void setBuyFlag(boolean isBuy) {
		if (isBuy) {
			this.buyFlag = "Y";
			this.pin = null;
		} else {
			this.buyFlag = "N";
		}
	}

	public String getPin() {
		return (pin == null) ? "" : pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
		setBuyFlag(false);
	}
	
	public String getTransType() {
		return (transType == null) ? "" : transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public int getEnrolledDays() {
		return enrolledDays;
	}

	public void setEnrolledDays(int enrolledDays) {
		this.enrolledDays = enrolledDays;
	}
	
	public boolean hasValidRedeemData() {
		if (getBuyFlag().isEmpty() || getActionType() == 0
				|| getRedeemType().isEmpty() || getTransType().isEmpty()) {
			return false;
		}
		
		if (getBuyFlag().equals("Y") == !getPin().isEmpty()) {
			return false;
		}
		
		return true;
	}

	public String tryToEnterHexEsn(PhoneUtil phoneUtil) {
		enterHexEsn(phoneUtil);
		
		if (NO_HEX_ESN.equalsIgnoreCase(hexESN)) {
			return getEsn();
		} else {
			return getHexESN();
		}
	}
	
	public String enterHexEsn(PhoneUtil phoneUtil) {
		if (getHexESN().isEmpty()) {
			String possHex = phoneUtil.getHexEsnFromDecimalEsn(esn);
			if(possHex.isEmpty()) {
				System.out.println("Could not enter hex esn for " + partNumber + " " + esn);
				hexESN = NO_HEX_ESN ;
			} else {
				hexESN = possHex;
			}
		}
		
		return hexESN;
	}
	
	public List<ESN> getFamilyEsns() {
		return familyEsns;
	}
	
	public List<ESN> getAllEsns() {
		ArrayList<ESN> all = new ArrayList<ESN>();
		all.add(this);
		all.addAll(familyEsns);
		return all;
	}

	public void setFamilyEsns(List<ESN> familyEsns) {
		this.familyEsns = familyEsns;
	}
	
	public void addFamilyEsn(ESN esn) {
		this.familyEsns.add(esn);
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}
	
	public void setLastSixDigits(String lastSixDigits) {
		this.lastSixDigits = lastSixDigits;
	}
	
	public String getLastSixDigits() {
		return lastSixDigits;
	}

	public synchronized String getFromMap(String key) {
		return this.map.get(key);
	}

	public synchronized String putInMap(String key, String value) {
		return this.map.put(key, value);
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod=paymentMethod;
		
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public String getLineAction() {
		return lineAction;
	}

	public void setLineAction(String lineAction) {
		this.lineAction = lineAction;
	}

	public int getServicePlanId() {
		return servicePlanId;
	}

	public void setServicePlanId(int servicePlanId) {
		this.servicePlanId = servicePlanId;
	}

	public void setPinPartNumber(String pinPart) {
		this.pinPartNumber=pinPart;
		
	}
	
	public String getPinPartNumber() {
		return pinPartNumber;
		
	}
}