package com.tracfone.twist.apps;

// JUnit Assert framework can be used for verification

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.Account;
import com.tracfone.twist.utils.Account.DeviceType;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.WfmUtil;
import com.tracfone.twist.utils.ServiceUtil.Flow;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.utils.ServiceUtil.ResourceType;
import com.tracfone.twist.utils.SimUtil;

public class MyAccountApp {

	@Autowired
	private TwistScenarioDataStore scenarioStore;
	@Autowired
	private WfmUtil wfmUtil;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private MyAccountFlow myAccountFlow;
	private ServiceUtil serviceUtil;
	private Account account;
	private static Logger logger = LogManager.getLogger(MyAccountApp.class
			.getName());
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	public String simPartNumber;

	public MyAccountApp() {

	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void getSecurityQuestionsFor(String brand) throws Exception {
		serviceUtil.getSecQuestions(brand);
	}

	public void addCCToAccountBy(String paymentType, String resType) throws Exception {
		// resType = MIN or Account ID
		account.setMin(esnUtil.getCurrentESN().getMin());
		account.setPaymentSrc(paymentType);
		serviceUtil.addPaymentSourceToAccount(paymentType, ResourceType.valueOf(resType.toUpperCase()));
	}

	public void updatePaymentSrc() {
		String ccLastFour = account.getCreditCardNumber().substring(account.getCreditCardNumber().length() - 4);
//		String pymysrcid = phoneUtil.getPaymentSource(ccLastFour, account.getEmail());
//		account.setPaymentSourceId(Integer.parseInt(pymysrcid));
		serviceUtil.updatePaymentSrc();
	}

	public void deletePaymentSourceBy(String resType) throws Exception {
		// account.setEmail("dakh9l62@tracfone.com");
		// account.setPassword("tracfone");
		// account.setBrand("STRAIGHT_TALK");
		// String accountId = phoneUtil.getEsnAttribute("WEB_USER_OBJID",
		// "100000088358626");
		// account.setAccountId(Integer.parseInt(accountId));
		serviceUtil.deletePaymentSrc(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void createAccountForPartSimBrand(String partNumber, String simPart, String brand) throws Exception {
		account.setBrand(brand);
		String email=TwistUtils.createRandomEmail();
		account.setEmail(email);
		account.setPassword("tracfone");
		ESN esn = generateData(partNumber, simPart);
		esn.setEmail(email);
		account.setEsn(esn.getEsn());
		account.setSim(esn.getSim());
		account.setBrand(brand);
		esnUtil.setCurrentBrand(brand);
		serviceUtil.createAccount();
	//	phoneUtil.enrollintoAffiliatedPartnerDiscountProgram(email, "KMART");
	}

	public void generateDataAndSetToCurrentESNForUpgrade(String partNumber, String simPart, String brand) throws Exception {
		ESN esn = generateData(partNumber, simPart);
		account.setEsn(esn.getEsn());
		account.setSim(esn.getSim());
		account.setBrand(brand);
		esnUtil.setCurrentBrand(brand);
	}

	public ESN generateData(String partNumber, String simPart) {
		ESN esn;
		if (partNumber.matches("PH(SM|ST|TC|NT|TF).*")) {
			esn = new ESN(partNumber, phoneUtil.getNewByopEsn(partNumber,
					simPart));
			esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
		} else if ("byop".equalsIgnoreCase(partNumber)) {
			esn = new ESN(partNumber, phoneUtil.getNewByopCDMAEsn());
		} else {
			esn = new ESN(partNumber,
					phoneUtil.getNewEsnByPartNumber(partNumber));
			if (!simPart.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simPart);
				if (sim.equalsIgnoreCase("0") || sim.isEmpty()) {
					sim = simUtil.getNewSimCardByPartNumber(simPart);
					esn.setSim(phoneUtil.getSimFromEsn(esn.getEsn()));
				}
				phoneUtil.addSimToEsn(sim, esn);
			}
		}
		esnUtil.setCurrentESN(esn);
		return esn;

	}

	public void callAPNSettingsForPhone(String deviceModel, String brand)
			throws Exception {
		account.setBrand(brand);
		serviceUtil.callAPNSettingsForPhone(deviceModel);
	}

	public void forgotPassword() throws Exception {
		serviceUtil.forgotPassword();
	}

	public void calculateEnrollmentTaxForPlan(String serviceplan)
			throws Exception {
		serviceUtil.calculateEnrollmentTaxForPlan(serviceplan);
	}

	public void calculatePurchaseTaxForPlan(String serviceplan) throws Exception {
		serviceUtil.calculatePurchaseTaxForPlan(serviceplan);
	}

	/*
	 * public void getPaymentSrcListFor(String accountEmail) throws Exception {
	 * serviceUtil.getPaymentSrcListFor(accountEmail); }
	 */

	public void setupInitialData() throws Exception {

		// account.setEsn(esnUtil.getCurrentESN().getEsn());
		// account.setEmail(esnUtil.getCurrentESN().getEmail());
		// account.setAccountId(Integer.parseInt(phoneUtil.getEsnAttribute("WEB_USER_OBJID",
		// esnUtil.getCurrentESN().getEsn())));
		// account.setBrand(esnUtil.getCurrentBrand());
	}

	public void getListOfAllPaymentSrcBy(String resType) throws Exception {
		// getPaymentSrcListFor(account.getEmail());
		serviceUtil.getPaymentSrcListFor(/* accountEmail, */ResourceType
				.valueOf(resType.toUpperCase()));
	}

	public void enrollIntoServicePlanWithOption(String serviePlan,
			String refillType) throws Exception {
		account.setDestPlan(serviePlan);

		int programId = phoneUtil.getProgramIdforServicePlan(serviePlan, true);
		account.setProgramId(programId);

		serviceUtil.enrollIntoServicePlanWithOption(serviePlan, refillType,
				ResourceType.ESN);
	}

	public void getRetailersInfoForInAroundMileRadius(String brand,
			String zipCode, String radius) throws Exception {
		serviceUtil.getRetailersInfoForInAroundMileRadius(brand, zipCode,
				radius);
	}

	public void getAccountDetails(String resType) throws Exception {
		// account.setBrand("STRAIGHT_TALK");
		account.setMin(esnUtil.getCurrentESN().getMin());
		serviceUtil.getAccountDetails(ResourceType.valueOf(resType
				.toUpperCase()));
	}

	public void getTransactionHistoryFor(String resType) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		serviceUtil.getTransactionHistory(ResourceType.valueOf(resType
				.toUpperCase()));
	}

	public void validateUserDevice(String type) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		// account.setEsn(esn.getEsn());
		// account.setSim(esn.getSim());
		account.setMin(esn.getMin());
		serviceUtil.validateUserDevice(account.getBrand(),
				ResourceType.valueOf(type.toUpperCase()));
	}

	public void getBalanceInquiry(String type) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		account.setMin(esn.getMin());
		serviceUtil.getBalanceInquiry(ResourceType.valueOf(type.toUpperCase()));
	}

	public void requestBenefitsBalance(String type) throws Exception {
		// String transId = phoneUtil.checkBI(account.getEsn());
		serviceUtil.requestBenefitsBalance(
				ResourceType.valueOf(type.toUpperCase()), account.getTransId());
	}

	public void forgotUsername() throws Exception {
		serviceUtil.forgotUsername();
	}

	public void viewListOfFeaturesSupportedBy(String brand) throws Exception {
		account.setBrand(brand);
		serviceUtil.viewListOfFeaturesSupportedBy(brand);
	}

	public void getCurrentEnrollmentInformation() throws Exception {
		serviceUtil.getCurrentEnrollmentInformation(ResourceType.ESN);
	}

	public void getAllTicketsInformation() throws Exception {
		serviceUtil.getAllTicketsInformation(ResourceType.ESN);
	}

	public void deenrollEsnFromCurrentEnrollment() throws Exception {
		serviceUtil.deenrollEsnFromCurrentEnrollment(ResourceType.ESN);
	}

	public void getDisenrollmentReason() throws Exception {
		serviceUtil.getDisenrollmentReason();
	}

	public void updateNickNameToFor(String nickName, String resType)
			throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String accountId = phoneUtil.getEsnAttribute("WEB_USER_OBJID",
				esn.getEsn());
		account.setAccountId(Integer.parseInt(accountId));
		String groupId = phoneUtil.getEsnAttribute("GROUPID", esn.getEsn());
		account.setGroupId(groupId);
		account.setMin(esn.getMin());
		serviceUtil.updateNickName(ResourceType.valueOf(resType.toUpperCase()),
				nickName);
	}

	public void addADeviceToAccount(String esnPart) throws Exception {
		/*
		 * account.setEmail("dakh9l62@tracfone.com");
		 * account.setPassword("tracfone"); account.setBrand("STRAIGHT_TALK");
		 * String accountId = phoneUtil.getEsnAttribute("WEB_USER_OBJID",
		 * "100000088358626");
		 * account.setAccountId(Integer.parseInt(accountId));
		 */

		String esnStr = phoneUtil.getNewEsnByPartNumber(esnPart);
		account.setEsn(esnStr);
		ESN esn = new ESN(esnPart, esnStr);
		esnUtil.getCurrentESN().addFamilyEsn(esn);

		serviceUtil.addADeviceToAccount(ServiceUtil.ResourceType.ESN);
	}

	public void deleteDeviceFromAccountFor(String resType) throws Exception {
		serviceUtil.deleteDeviceFromAccount(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void getPaymentHistory() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		String accountId = phoneUtil.getEsnAttribute("WEB_USER_OBJID",
				"100000088358626");
		account.setAccountId(Integer.parseInt(accountId));
		serviceUtil.getPaymentHistory(ServiceUtil.ResourceType.ESN);
	}

	public void getTransactionHistory() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		serviceUtil.getTransactionHistory(ServiceUtil.ResourceType.ESN);
	}

	public void enumeratePin() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");
		serviceUtil.enumeratePin(ServiceUtil.ResourceType.MIN);
	}

	public void movePinToTheTop() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");

		serviceUtil.movePinToTheTop(ServiceUtil.ResourceType.MIN, "119761260098149");
	}

	public void redeemPINFromReserve() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");

		serviceUtil.redeemPINFromReserve(ServiceUtil.ResourceType.MIN);
	}

	public void getRetentionMatrix() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");

		serviceUtil.getRetentionMatrix(ServiceUtil.ResourceType.MIN, ServiceUtil.Flow.REDEMPTION, "999999937643732", 0);
	}

	public void getManufacturerModel() throws Exception {
		account.setEmail("dakh9l62@tracfone.com");
		account.setPassword("tracfone");
		account.setBrand("STRAIGHT_TALK");
		serviceUtil.getManufacturerModel();
	}

	public void getScripts() throws Exception {
		// account.setEmail("dakh9l62@tracfone.com");
		// account.setPassword("tracfone");
		account.setBrand("SIMPLE_MOBILE");
		serviceUtil.getScripts("PLAN_5223,PLAN_5226");
	}

	public void getRewards() throws Exception {
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");
		serviceUtil.getRewards(ResourceType.ESN);
	}

	public void getServicePlans(String deviceType, String resType, String targetPart) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		DeviceType dType = DeviceType.valueOf(deviceType);
		account.setDeviceType(dType);
		ResourceType resource = ResourceType.valueOf(resType.toUpperCase());
		JSONObject response = serviceUtil.getServicePlans(resource);
		JSONArray plans = response.getJSONObject("response").getJSONArray("servicePlans");
		
		ESN esn = esnUtil.getCurrentESN();
		//Find Service Plan ID based on target PIN part
		for (int i = 0; i < plans.length(); i++) {
			JSONObject plan = plans.getJSONObject(i);
			if (targetPart.equalsIgnoreCase(plan.getString("partNumber"))) {
				esn.setServicePlanId(plan.getInt("servicePlanId"));
			}
		}
	}

	public void getHPP() throws Exception {
		account.setBrand("STRAIGHT_TALK");
		account.setEsn("100000088358626");
		account.setMin("9543085237");
		serviceUtil.getHandsetProtectionPrograms(ResourceType.ESN);
	}
	
	public void updateCustomerProfile(String resType) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		serviceUtil.updateProfileContact(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void updateProfileLoginInformation() throws Exception {
		serviceUtil.updateProfileLoginInformation();
	}

	public void redeemPinCard(String pinPart) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		String pin = phoneUtil.getNewPinByPartNumber(pinPart);
		serviceUtil.redeemPinCard(pin);
	}

	public void clearOTA(String resType) throws Exception {
		TwistUtils.setDelay(5);
		serviceUtil.clearOTA(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void confirmPurchase(String pinPart) throws Exception {
		serviceUtil.confirmPurchase(pinPart);
	}

	public void getPaymentHistory(String resType) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		serviceUtil.getPaymentHistory(ResourceType.valueOf(resType.toUpperCase()));
	}

	public void getPaymentDetails() throws Exception {
		serviceUtil.getPaymentDetails();
	}

	public void validatePromoWithPromoFor(String pinPart, String price,
			String promo, String flow) throws Exception {
		if (Flow.PURCHASE.toString().equalsIgnoreCase(flow)) {
			serviceUtil.validatePromo(pinPart, "", price, promo, Flow.PURCHASE);
		} else {
			String pin = phoneUtil.getNewPinByPartNumber(pinPart);
			serviceUtil.validatePromo(pinPart, pin, price, promo, Flow.REDEMPTION);
		}
	}

	public void validateAddressFor(String address1, String city, String state,
			String country, String zip, String brand) throws Exception {
		account.setBrand(brand);
		serviceUtil.validateAddress(address1, city, state, country, zip);
	}

	public void completePhoneBranding() throws Exception {
		serviceUtil.completePhonebranding();
	}

	public void getConfigInformation(String type) throws Exception {
		serviceUtil.getlistofall(type);
	}

	public void linkAccount() {
		ESN esn = esnUtil.getCurrentESN();
		String accountId = phoneUtil.getEsnAttribute("WEB_USER_OBJID",
				esn.getEsn());
		account.setAccountId(Integer.parseInt(accountId));
		serviceUtil.linkAccount();
	}

	public void getCoverageForZipcodeDevicetypeCarrierBrand(String zipcode,
			String deviceType, String carrier, String brand) {
		account.setBrand(brand);
		account.setDeviceType(DeviceType.valueOf(deviceType.toUpperCase()));
		serviceUtil.getCoverage(zipcode, carrier);
	}

	public void getServiceQualificationForZipcodeCarrier(String zipcode, String carrier) {
		serviceUtil.getServiceQualification(zipcode, carrier);
	}

	public void recoverAccount(String resType) throws Exception {
		ResourceType type = null;
		account.setMin(esnUtil.getCurrentESN().getMin());
		if (resType != null && !resType.isEmpty()) {
			type = ResourceType.valueOf(resType.toUpperCase());
			serviceUtil.accountRecoveryCheckpoints(type, "33178"); // type =
																	// ESN/MIN/SIM
			serviceUtil.accountRecoveryValidate(type, account.getSecurityPin(),
					"33178", "", "", account.getPaymentSrc()); // type =
																// ESN/MIN/SIM
		}
		serviceUtil.accountRecoveryStatus(type); // type =
													// ESN/MIN/SIM/EMAIL/ACC/NULL

	}

	public void getAccountProfile(String resType) throws Exception {
		account.setMin(esnUtil.getCurrentESN().getMin());
		serviceUtil.retrieveCustomerAccountProfile(ResourceType.valueOf(resType
				.toUpperCase()));
	}

	public void getRenewalEnquiry(String type) throws Exception {
		serviceUtil.renewalEnquiry(ResourceType.valueOf(type.toUpperCase()));
	}

	public void getESNAttributes(String type) throws Exception {
		serviceUtil.getESNAttributes(ResourceType.valueOf(type.toUpperCase()));
	}

	public void addESNSToAccountESN(String numLines, String esn1, String sim1, String esn2, String sim2, String esn3, String sim3) throws Exception {
		String[] esnPartNumber_list = { esn1, esn2, esn3 };
		String[] simPartNumber_list = { sim1, sim2, sim3 };
		int lines = Integer.parseInt(numLines);
		ESN esn = esnUtil.getCurrentESN();
		List<ESN> familyEsns = new ArrayList<ESN>();
		if(lines > 0){
			for (int i=0; i<=lines; i++){
				if(!esnPartNumber_list[i].isEmpty()){
					ESN aal = generateData(esnPartNumber_list[i], simPartNumber_list[i]);
					familyEsns.add(aal);	
				}
			}
		}
		esn.setFamilyEsns(familyEsns);
	}

	public void setCurrentBrandTo(String brand) throws Exception {
		account.setBrand(brand);
	
	}

}