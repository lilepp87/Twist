package com.tracfone.twist.utils;

import java.util.ArrayList;
import java.util.List;

public class ORG {
	
	private static PhoneUtil phoneUtil;
	private  String parentChildfFlag;
	private  String ecommOrgId;
	private  String clfyOrgId;
	private  String ofsOrgid;
	private  String clfyOrderId; 
	private  String ofsOrderId;
	private  String orgName;
	private  String parentOrgName;
	private  String childOrgName;
	private  String shipLocId;
	private  String email;
	private  String password;
	private  String parentEmail;
	private  String parentPassword;
	private  String env;
	private  String database;
	private  String project;

	public String getParentChildfFlag() {
		return parentChildfFlag;
	}

	public void setParentChildfFlag(String parentChildfFlag) {
		this.parentChildfFlag = parentChildfFlag;
	}

	public String getEcommOrgId() {
		return ecommOrgId;
	}

	public void setEcommOrgId(String ecommOrgId) {
		this.ecommOrgId = ecommOrgId;
	}

	public String getOfsOrgid() {
		return ofsOrgid;
	}

	public void setOfsOrgid(String ofsOrgid) {
		this.ofsOrgid = ofsOrgid;
	}

	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	public String getChildOrgName() {
		return childOrgName;
	}

	public void setChildOrgName(String childOrgName) {
		this.childOrgName = childOrgName;
	}
	
	public String getShipLocId() {
		return shipLocId;
	}

	public void setShipLocId(String shipLocId) {
		this.shipLocId = shipLocId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentPassword() {
		return parentPassword;
	}

	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public static PhoneUtil getPhoneUtil() {
		return phoneUtil;
	}

	public void insertoB2BTable(){
		List<Object> b2bdata = new ArrayList();
		b2bdata.add(parentChildfFlag);
		b2bdata.add(ecommOrgId);
		b2bdata.add(clfyOrgId);
		b2bdata.add(ofsOrgid);
		b2bdata.add(clfyOrderId); 
		b2bdata.add(ofsOrderId); 
		b2bdata.add(orgName);
		b2bdata.add(parentOrgName);
		b2bdata.add(childOrgName);
		b2bdata.add(shipLocId);
		b2bdata.add(email);
		b2bdata.add(password);
		b2bdata.add(env);
		b2bdata.add(database);
		b2bdata.add(project);
		
		phoneUtil.insertIntob2bTable(b2bdata);
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public String getClfyOrderId() {
		return clfyOrderId;
	}

	public void setClfyOrderId(String clfyOrderId) {
		this.clfyOrderId = clfyOrderId;
	}
	
	public String getOfsOrderId() {
		return ofsOrderId;
	}

	public void setOfsOrderId(String ofsOrderId) {
		this.ofsOrderId = ofsOrderId;
	}

}
