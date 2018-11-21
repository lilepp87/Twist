package com.tracfone.twist.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.utils.Account;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.RandomACHGenerator;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.utils.WfmUtil;
import com.tracfone.twist.utils.ServiceUtil.ResourceType;
import com.tracfone.twist.utils.gson.BankAccount;
import com.tracfone.twist.utils.gson.CreditCard;

public class FamilyMobileOperations {

	@Autowired
	private TwistScenarioDataStore scenarioStore;
	@Autowired
	private WfmUtil wfmUtil;
	@Autowired
	private ServiceUtil serviceUtil;
	@Autowired
	private ESNUtil esnUtil;
	@Autowired
	private PhoneUtil phoneUtil;
	@Autowired
	private Account account;
	@Autowired
	private  SimUtil simUtil;

	private static Logger logger = LogManager
			.getLogger(FamilyMobileOperations.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("TAS");

	public FamilyMobileOperations() {

	}

	public void addEmployeeDiscount() throws Exception {
		account.setBrand("WFM");
		wfmUtil.addBillingDiscount("3059268228","adarsh@082217f.com");
	}

	public void callServiceOrderWithPin(String action, String pinPart) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		wfmUtil.serviceOrder(pin,action);
		
	}
	
	public void callProductOrder() throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setServicePlanId(484);
		currEsn.setLineAction("ACTIVATION");
		account.setSecurityPin("1234");
		
		CreditCard credit = new CreditCard("2020", "01", "123", TwistUtils.generateCreditCard("Visa"), "VISA");
//		BankAccount bank = new BankAccount("000123456789", "110000000", "CHECKING");
		wfmUtil.activateBANWithCreditCard(currEsn.getAllEsns(), "33178", true, true, credit);
	}
	
	public void callProductOrderWithPlanAutorefillUsing(String servicePlan,boolean planType, String paymentType) throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setServicePlanId(Integer.parseInt(props.getString(servicePlan)));
		currEsn.setLineAction("ACTIVATION");
		account.setSecurityPin("1234");
		boolean autoRefill = planType;
		if(paymentType.equalsIgnoreCase("ACH")){
			BankAccount bank = new BankAccount(RandomACHGenerator.getAccountNumber(),RandomACHGenerator.getRoutingNumber(), "CHECKING");
			wfmUtil.activateBANWithACH(currEsn.getAllEsns(), "33178", autoRefill, true, bank);
		}else{
			CreditCard credit = new CreditCard("2020", "01", (paymentType.equalsIgnoreCase("American Express"))? "1234":"123", TwistUtils.generateCreditCard(paymentType), paymentType);
			wfmUtil.activateBANWithCreditCard(currEsn.getAllEsns(), "33178", autoRefill, true, credit);
		}
	
	}



	public void addRemainingToOrderUsingPartSimPinAndCellTech(Integer numofLines,
			String partNumber, String simPart, String pinPart, String cellTech)
			throws Exception {
		ESN currEsn = esnUtil.getCurrentESN();
		String servicePlan = pinPart;
		
		ESN newE;
		ArrayList al  = new ArrayList<Object>();
		for (int i=0 ; i < numofLines; i++)
		{
			if(pinPart.equalsIgnoreCase("random")){
				String ServicePlanList[] = { "WFMAPPU0024", "WFMAPPU0029", "WFMAPPU0039", "WFMAPPU0049" };
				int index = new Random().nextInt(ServicePlanList.length);
				servicePlan= ServicePlanList[index];
			}
			if(pinPart.equalsIgnoreCase("sm_random")){
				String ServicePlanList[] = { "SMNAPP0025TT", "SMNAPP40030ILD", "SMNAPP0040UNL", "SMNAPP0050BBUNL","SMNAPP30060","SMNAPP20050ILDUP","SMNAPP20060ILDUP","SMNAPP20070ILDUP" };
				//String ServicePlanList[] = {  "SMNAPP40030ILD"};
				int index = new Random().nextInt(ServicePlanList.length);
				servicePlan= ServicePlanList[index];
			}
			/*if(partNumber.startsWith("PH")){
				newE = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber, simPart));
				newE.setSim(phoneUtil.getSimFromEsn(newE.getEsn()));
			}else{*/
				newE = new ESN(partNumber, phoneUtil.getNewEsnByPartNumber(partNumber));
				String newSim = simUtil.getNewSimCardByPartNumber(simPart);
				newE.setSim(phoneUtil.getSimFromEsn(newE.getEsn()));
				phoneUtil.addSimToEsn(newSim, newE);
		//	}
			newE.setServicePlanId(Integer.parseInt(props.getString(servicePlan)));
			newE.setLineAction("ACTIVATION");
			al.add(newE);
		}
		
		currEsn.setFamilyEsns(al);
	
	}
	public void completePhoneActivationForAndStatus(String cellTech,
			String status) throws Exception {
		TwistUtils.setDelay(60);
		ESN currEsn = esnUtil.getCurrentESN();
		System.out.println(currEsn.getEsn());
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "Services[" + esnUtil.getCurrentBrand() + "]");
		TwistUtils.setDelay(5);
		phoneUtil.clearOTAforEsn(currEsn.getEsn());
		String min = phoneUtil.getMinOfActiveEsn(currEsn.getEsn());
		account.setMin(min);
	if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM") ||esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){	
		String banID=phoneUtil.getBANFromWebObj(Integer.toString(account.getAccountId()));
		account.setBanID(banID);
		System.out.println(banID);
		String objID = phoneUtil.getCurrentActiveObjID(currEsn.getEsn());
		String[] insertData= {currEsn.getEsn(),min,banID,objID};
		phoneUtil.updateBRMCheck(Arrays.asList(insertData));
	}

	}
	
	public void getCatalog(String resType) throws Exception {
		wfmUtil.getCatalog(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void estimateOrder() throws Exception {
		wfmUtil.estimateOrder();
	}

	public void serviceQualificationByNapAndPin(String action, String Zipcode,
			String pinNumber) throws Exception {
		String newPin;
		// ESN esn = esnUtil.getCurrentESN();
		// account.setEsn(esn.getEsn());
		// account.setSim(esn.getSim());
		if ("NAP".equalsIgnoreCase(action)) {
			wfmUtil.serviceQualificationByNapAndPin(action, Zipcode, pinNumber);
		}
		if ("AIRTIMEPIN".equalsIgnoreCase(action)) {
			if ("NO PIN".equalsIgnoreCase(pinNumber)) {
				newPin = pinNumber;
			} else {
				newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
			}
			wfmUtil.serviceQualificationByNapAndPin(action, Zipcode, newPin);
		}
	}

	public void serviceOrderPortsAndUpgrade(String pinNumber, String flowname) {
		// String flowName ;
		String newPin;
		if (flowname.equalsIgnoreCase("InternalPort")
				|| flowname.equalsIgnoreCase("ExternalPort")) {
			if ("NO PIN".equalsIgnoreCase(pinNumber)) {
				newPin = pinNumber;
			} else {
				newPin = phoneUtil.getNewPinByPartNumber(pinNumber);
			}
			wfmUtil.serviceOrderPotrsAndUpgrade(newPin, flowname);
		}

		else if (flowname.equalsIgnoreCase("PhoneUpgrade")) {
			wfmUtil.serviceOrderPotrsAndUpgrade(pinNumber, flowname);
		}

	}

	public void requestForRefill() throws Exception {
		String banID = account.getBanID();
		wfmUtil.getAccountDetails(banID);
		wfmUtil.requestRefill(banID);
		wfmUtil.requestForCancelRefill(banID);
	}

	public void registerCustomerIntoEmployeeDiscountProgram(boolean enrollment) throws Exception {
	
		try{
			if(account.getBrand().equalsIgnoreCase("")){
				
			}
		}
		catch(Exception e){
			account.setBrand(esnUtil.getCurrentBrand());
		}
		if(enrollment){
			 wfmUtil.addBillingDiscount(esnUtil.getCurrentESN().getMin(),account.getEmail());
		}
		
	}
	
}
