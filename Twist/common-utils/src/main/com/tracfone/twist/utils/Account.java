package com.tracfone.twist.utils;

public class Account {

	private String esn;
	private String sim;
	private String min;
	private String username;
	private String password;
	private String securityPin;
	private int    accountId;
	private String groupId;
	private int    customerId;
	private String paymentSrc;
	private String paymentSrcNickName;
	private String creditCardNumber;
	private int    paymentSourceId;
	private String brand;
	private String email;
	private String currentPlan;
	private String destPlan;
	private int    programId;
	private DeviceType deviceType;
	private String transId;
	private String flowName;
	private String banID;
	
	public String getBanID() {
		return banID;
	}
	public void setBanID(String banID) {
		this.banID = banID;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecurityPin() {
		return securityPin;
	}
	public void setSecurityPin(String securityPin) {
		this.securityPin = securityPin;
	}
	public String getPaymentSrc() {
		return paymentSrc;
	}
	public void setPaymentSrc(String paymentSrc) {
		this.paymentSrc = paymentSrc;
	}
	public String getPaymentSrcNickName() {
		return paymentSrcNickName;
	}
	public void setPaymentSrcNickName(String paymentSrcNickName) {
		this.paymentSrcNickName = paymentSrcNickName;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public int getPaymentSourceId() {
		return paymentSourceId;
	}
	public void setPaymentSourceId(int paymentSourceId) {
		this.paymentSourceId = paymentSourceId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getCurrentPlan() {
		return currentPlan;
	}
	public void setCurrentPlan(String currentPlan) {
		this.currentPlan = currentPlan;
	}
	public String getDestPlan() {
		return destPlan;
	}
	public void setDestPlan(String destPlan) {
		this.destPlan = destPlan;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getDeviceType() {
		return deviceType.toString();
	}
	public void setDeviceType(DeviceType dt) {
		deviceType = dt;
	}
	public void setAccount(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public enum DeviceType {
		SMARTPHONE, FEATURE_PHONE, HOMEPHONE, HOME_ALERT, HOTSPOT, CAR_CONNECT, BYOP, BYOT, HOME_CENTER, M2M, MOBILE_BROADBAND, WIRELESS_HOME_PHONE
	}
	public void setTransID(String transId) {
		this.transId = transId;
	}
	public String getTransId() {
		return transId;
	}
	
}