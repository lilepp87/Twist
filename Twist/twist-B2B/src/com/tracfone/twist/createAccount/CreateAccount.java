package com.tracfone.twist.createAccount;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ORG;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

import java.util.Hashtable;
import java.util.ResourceBundle;

// JUnit Assert framework can be used for verification

public class CreateAccount {


	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static ORG org;
	private static final String DEFAULT_PASS = "123456a";
	private static String setEmail;
	private ResourceBundle rb = ResourceBundle.getBundle("B2B");

	public CreateAccount() {
		
	}

	public void goToLogin() throws Exception {
		if (activationPhoneFlow.linkVisible("Sign In / Sign Up")) {
			activationPhoneFlow.clickLink("Sign In / Sign Up");
		} else {
			activationPhoneFlow.clickLink("Sign In / Sign Up");
		}
/*		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn/Up")); */
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil newPhoneUtil) {
		phoneUtil = newPhoneUtil;
	}
	
	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}
	
	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}
	
	public void setORG(ORG newORG) {
		org = newORG;
	}
	
	public void createOrganizationAccount(String orgType) throws Exception {
		String email = TwistUtils.createRandomEmail();
		setEmail = email;
		System.out.println("Email: " + email);
		String ORGName = "TwistORGName-" + TwistUtils.createRandomUserId();
		System.out.println("ORG Name: " + ORGName);
	
		activationPhoneFlow.clickLink(rb.getString("B2B.CreateORG"));
		//activationPhoneFlow.clickLink("WC_AccountDisplay_links_4");
		if (orgType.equalsIgnoreCase("Child")) {
			String parentOrgName = org.getOrgName();
			String parentEmail = org.getEmail();
			String parentPassword = org.getPassword();
			org.setParentOrgName(parentOrgName);
			org.setParentEmail(parentEmail);
			org.setParentPassword(parentPassword);
			activationPhoneFlow.selectRadioButton("orgAccount");
			activationPhoneFlow.typeInTextField("ancestorOrgs", phoneUtil.getOrgIdByOrgName(parentOrgName.toUpperCase()));
		}
		activationPhoneFlow.typeInTextField("org_orgEntityName", ORGName);
		activationPhoneFlow.typeInTextField("org_address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("org_city", "Mountain View");
		activationPhoneFlow.typeInTextField("org_state", "CA");
		activationPhoneFlow.typeInTextField("org_zipCode", "94043");
		activationPhoneFlow.typeInTextField("org_phone1", "3053053055");
		activationPhoneFlow.typeInTextField("usr_firstName", "Twist FirstName");
		activationPhoneFlow.typeInTextField("usr_lastName", "Twist LastName");
		activationPhoneFlow.typeInTextField("usr_logonId", email);
		activationPhoneFlow.typeInTextField("usr_logonIdVerify", email);
		activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS);
		activationPhoneFlow.typeInPasswordField("logonPasswordVerify", DEFAULT_PASS);
		activationPhoneFlow.chooseFromSelect("usr_challengeQuestion", "What is your father's middle name?");
		activationPhoneFlow.typeInTextField("usr_challengeAnswer", "Robert");
		activationPhoneFlow.selectCheckBox("WC_OrganizationRegistrationAddForm_Checkbox1");
		activationPhoneFlow.typeInTextField("usr_phone1", "3053053055");
		activationPhoneFlow.chooseFromSelect("usr_phone1Type", "MO1");
		activationPhoneFlow.typeInTextField("usr_state", "CA");
		activationPhoneFlow.typeInTextField("usr_zipCode", "94043");
		activationPhoneFlow.chooseFromSelect("usr_preferredCommunication", "Mobile Phone");
		activationPhoneFlow.clickLink(rb.getString("B2B.Save")); 
		TwistUtils.setDelay(23);
		Assert.assertTrue(activationPhoneFlow.h1Visible("My Account"));
		org.setOrgName(ORGName);
		org.setEmail(email);
		org.setPassword(DEFAULT_PASS);
		if (!orgType.equalsIgnoreCase("Child")) {
		phoneUtil.b2bPromoORG(ORGName, "B2B3RECP");
		}
		insertdata();
	}

	public void insertdata() {
		String ORGName = org.getOrgName();
		TwistUtils.setDelay(30);
		org.setEcommOrgId(phoneUtil.getOrgIdByOrgName(ORGName.toUpperCase()));
		org.setProject("B2B");
		org.insertoB2BTable();
	}

	public void logout() throws Exception {
		TwistUtils.setDelay(7);
		if (activationPhoneFlow.linkVisible("Sign Out")) {
			activationPhoneFlow.clickLink("Sign Out"); 
		}
	}

	public void createProfile(String type) throws Exception {
		String email = TwistUtils.createRandomEmail();
		setEmail = email;
		System.out.println("Email : " + email);
		String parentOrgName = org.getOrgName();
		String parentEmail = org.getEmail();
		String parentPassword = org.getPassword();
		org.setParentOrgName(parentOrgName);
		org.setParentEmail(parentEmail);
		org.setParentPassword(parentPassword);
/*		activationPhoneFlow.clickLink("WC_AccountDisplay_links_3"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.CreateBuyerAcctProfile")); 
		if (type.equalsIgnoreCase("CSR")) {
			activationPhoneFlow.typeInTextField("ancestorOrgs", "7000000000000003655");	
			org.setOrgName("");
			org.setEmail(email);
			org.setPassword(DEFAULT_PASS);
		} else {
			activationPhoneFlow.typeInTextField("ancestorOrgs", phoneUtil.getOrgIdByOrgName(parentOrgName.toUpperCase()));
			org.setOrgName("");
			org.setEmail(email);
			org.setPassword(DEFAULT_PASS);
		}
		activationPhoneFlow.typeInTextField("logonId", email);
		activationPhoneFlow.typeInTextField("email1", email);
		activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS);
		activationPhoneFlow.typeInPasswordField("logonPasswordVerify", DEFAULT_PASS);
		activationPhoneFlow.chooseFromSelect("challengeQuestion", "What is your father's middle name?");
		activationPhoneFlow.typeInTextField("challengeAnswer", "Robert");
		activationPhoneFlow.typeInTextField("firstName", "Twist FirstName");
		activationPhoneFlow.typeInTextField("lastName", "Twist LastName");
		activationPhoneFlow.selectCheckBox("TFCopyOrgAddressChkBox");
		activationPhoneFlow.chooseFromSelect("phone1Type", "Mobile");
/*		activationPhoneFlow.clickLink("Save"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.Save"));
	}

	public void checkForApproval() throws Exception {
		Assert.assertTrue(activationPhoneFlow.divVisible("Your registration request has been received. " +
				" Your account is waiting for approval. Until your account has been approved, you cannot log on."));
	}

	public void loginAsCSR() throws Exception {
		activationPhoneFlow.typeInTextField("logonId", setEmail);
		activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS);
/*		activationPhoneFlow.clickLink("WC_AccountDisplay_links_2"); */ 
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  
		activationPhoneFlow.chooseFromSelect("searchoption", "First Name");
		activationPhoneFlow.typeInTextField("search_value", "Twist FirstName");
		activationPhoneFlow.clickLink("userSearchSection");
		Assert.assertTrue(activationPhoneFlow.linkVisible("Twist FirstName"));
	}

	public void updateInfo() throws Exception {
		activationPhoneFlow.clickLink("Organization Information");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road Update");
		activationPhoneFlow.clickLink("SAVE"); 
/*		activationPhoneFlow.clickLink(rb.getString("B2B.Save")); */ 
		Assert.assertTrue(activationPhoneFlow.divVisible("Organization information updated successfully."));
	}

	public void updatePersonalInfo() throws Exception {
		activationPhoneFlow.clickLink("Personal Information");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road Update");
		activationPhoneFlow.clickLink("Save[1]");
		activationPhoneFlow.h1Visible("My Account");
	}

	public void updateAddressBook() throws Exception {
		activationPhoneFlow.clickLink("Address Book");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road Update");
		activationPhoneFlow.clickLink("Save[1]");
		Assert.assertTrue(activationPhoneFlow.divVisible("The address has been updated successfully"));
	}

	public void updatePaymentInfo() throws Exception {
		activationPhoneFlow.clickLink("Payment Information");
/*		activationPhoneFlow.clickLink("Add New Credit Card");*/ 
		String cc = TwistUtils.generateCreditCard("VISA");
		activationPhoneFlow.typeInTextField("account_number", cc);
		activationPhoneFlow.typeInTextField("cvv2", "123");
		activationPhoneFlow.chooseFromSelect("expMonth", "07");
		activationPhoneFlow.chooseFromSelect("expYear", "2021");
		activationPhoneFlow.selectCheckBox("ccIsDefault");
		activationPhoneFlow.typeInTextField("nickName", "Twist Billing Address");
		activationPhoneFlow.typeInTextField("firstName", "Twist FirstName");
		activationPhoneFlow.typeInTextField("lastName", "Twist LastName");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("city", "Moutain View");
		activationPhoneFlow.typeInTextField("state", "CA");
		activationPhoneFlow.typeInTextField("zipCode", "94043");
		activationPhoneFlow.typeInTextField("phone1", "3053053055");
		activationPhoneFlow.chooseFromSelect("phone1Type", "Mobile");
		activationPhoneFlow.selectCheckBox("preferredShippingCheckbox");
		activationPhoneFlow.selectCheckBox("preferredBillingCheckbox");
		activationPhoneFlow.clickLink("SAVE"); 
/*		activationPhoneFlow.clickLink(rb.getString("B2B.Save")); */ 
		TwistUtils.setDelay(3);
//		Assert.assertTrue(activationPhoneFlow.divVisible("Your payment information has been updated successfully."));
	}

	public void loginAsBuyerAdminAndApprove(String orgType, String approvalType) throws Exception {
		int approvalCount = 0;
		String loginId = org.getParentEmail();
		String pwd = org.getParentPassword();
		activationPhoneFlow.typeInTextField("logonId", loginId);
		activationPhoneFlow.typeInPasswordField("logonPassword", pwd);
		/*if(orgType.equalsIgnoreCase("Parent")){
			activationPhoneFlow.typeInTextField("logonId","znlp526ia9g5@tracfone.com");
			activationPhoneFlow.typeInPasswordField("logonPassword","123456");
		}else if(orgType.equalsIgnoreCase("Child")){
			activationPhoneFlow.typeInTextField("logonId","u3feyqmoi5pm@tracfone.com");
			activationPhoneFlow.typeInPasswordField("logonPassword","123456a");
		}*/
/*		activationPhoneFlow.clickLink("WC_AccountDisplay_links_2"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  
/*		activationPhoneFlow.clickLink("Approval Requests"); */
		TwistUtils.setDelay(2);
		activationPhoneFlow.clickLink(rb.getString("B2B.ApproveReq"));
		TwistUtils.setDelay(2);
		activationPhoneFlow.clickLink(rb.getString("B2B.ApproveReq"));
		if (approvalType.equalsIgnoreCase("Profile")) {
			activationPhoneFlow.chooseFromSelect("arSelectUserType", "Requests for user Registration");
		} else if (approvalType.equalsIgnoreCase("ORG")){
			activationPhoneFlow.chooseFromSelect("arSelectUserType", "Requests for Organization Registration");	
		}
		while (activationPhoneFlow.linkVisible("Twist FirstName Twist LastName")) {
			activationPhoneFlow.clickLink("Twist FirstName Twist LastName");
			activationPhoneFlow.clickLink("APPROVE");
			approvalCount++ ;
			System.out.println(approvalCount);
		}
	}

	public void loginAsRequestor() throws Exception {
		activationPhoneFlow.typeInTextField("logonId", setEmail);
		activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS);
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  
	}

	public void logoutAndLoginOrg() throws Exception {
		activationPhoneFlow.clickLink("Sign Out");
		String loginId = org.getEmail();
		String pwd = org.getPassword();
		activationPhoneFlow.typeInTextField("logonId", loginId);
		activationPhoneFlow.typeInPasswordField("logonPassword", pwd);
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  
	}

	public void addACHPayment() throws Exception {
		activationPhoneFlow.clickLink(rb.getString("B2B.PaymentInfo"));  
		if(activationPhoneFlow.linkVisible("Add New Account")){
			activationPhoneFlow.clickLink("Add New Account");	
		}
		activationPhoneFlow.typeInTextField("accountNickName","Twist AccountName");
		activationPhoneFlow.selectRadioButton("accountType");
		activationPhoneFlow.typeInTextField("routing_number","121042882");
		activationPhoneFlow.typeInTextField("bank_account_number","4100");
		activationPhoneFlow.selectCheckBox("achIsDefault");
		activationPhoneFlow.clickLink("WC_PaymentInformationFormSubmit_2"); 
		TwistUtils.setDelay(3);
		Assert.assertTrue(activationPhoneFlow.divVisible("Your payment information has been updated successfully."));
	}

	public void logInForFleet() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.typeInTextField("logonId", esn.getEmail());
		activationPhoneFlow.typeInPasswordField("logonPassword", esn.getPassword());
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  
		activationPhoneFlow.h1Visible("My Account");
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void getActiveESNForPart(String esnPart) throws Exception {
		String esn = phoneUtil.getActiveEsnByPartNumber(esnPart);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		esnUtil.setCurrentESN(new ESN(esnPart, esn));
	}

	public void goToClearwayAPNSettingPage() throws Exception {
		//System.out.println(esnUtil.getCurrentESN().getEsn());
		activationPhoneFlow.clickLink("Help & FAQs");
		activationPhoneFlow.clickLink("Enabling Data Access");
		TwistUtils.setDelay(2);
		Assert.assertTrue(activationPhoneFlow.h2Visible("Enabling Data Access"));
		activationPhoneFlow.getBrowser().link("Clearway APN Settings").click();
	}

	public void enterMINAndDeviceDetails(String model) throws Exception {
        String min = esnUtil.getCurrentESN().getMin();
		activationPhoneFlow.getBrowser().popup("Clearway | Update Data Settings").select("deviceTypeOrOS").choose(model);
		activationPhoneFlow.getBrowser().popup("Clearway | Update Data Settings").textbox("simOrPhoneNo").rteWrite(min);
	    activationPhoneFlow.getBrowser().popup("Clearway | Update Data Settings").link("Update").click();
	}

	public void checkForSettings() throws Exception {
		Assert.assertTrue(activationPhoneFlow.h1Visible("Settings"));
		Assert.assertTrue(activationPhoneFlow.h1Visible("Instructions"));
	}
	
	public void placeOrderWithAPIForSimAndPlanOnInFor(String carrier,String cellTech,String partnerID, String zipCode, String phoneModel) throws Exception {
		String simPart = "";
		String sim = "";
		String partyID = null;
		String partySignature = null;
		
		Hashtable<String, Object> offering = new Hashtable<String, Object> ();
		
		if (!cellTech.equalsIgnoreCase("GSM")) {
			String esnStr = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
			serviceUtil.registerBYOP(partnerID, zipCode, esnStr, carrier, cellTech, sim);
			ESN esn;
			esn = new ESN("NTBYOPDEC", esnStr);
			esnUtil.setCurrentESN(esn);
		}
		
		if(!cellTech.equalsIgnoreCase("CDMA")){
			Hashtable<String, Object> simOffering = serviceUtil.GetProductOffering(partnerID,"SIM_CARD", "BYOP", carrier, cellTech, zipCode, "");
			simPart = (String) simOffering.get("simPart");
			offering.putAll(simOffering);
		}
		
		Hashtable<String, Object> planOffering = serviceUtil.GetProductOffering(partnerID,"SERVICE", "BYOP", carrier, cellTech, zipCode, simPart);
		offering.putAll(planOffering);
		System.out.println(offering);
		
		partyID = (String) offering.get("partyID");
		partySignature = (String) offering.get("partySignature");
		
		offering = serviceUtil.estimateOrder(partnerID,offering,cellTech);
		String orderID = (String) offering.get("orderID");
		System.out.println(offering);
		System.out.println("orderID : " +orderID);
		
		Hashtable<String, String> account = serviceUtil.CreateCustomer(partnerID,offering);
		String emailStr = account.get("email");
		String orgName = account.get("orgName");
		
		serviceUtil.submitOrder(partnerID, offering, cellTech, emailStr, orgName);
		
		serviceUtil.checkOrderStatus(orderID, emailStr);
	}

	public void getProductOfferingForSimAndPlanOnInZipCodeFor(String carrier,String cellTech, String partnerID, String zipCode, String phoneModel) throws Exception {
		String simPart = "";
		String sim = "";
		
		Hashtable<String, Object> offering = new Hashtable<String, Object> ();
		
		if (!cellTech.equalsIgnoreCase("GSM")) {
			String esnStr = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
			serviceUtil.registerBYOP(partnerID, zipCode, esnStr, carrier, cellTech, sim);
			ESN esn;
			esn = new ESN("NTBYOPDEC", esnStr);
			esnUtil.setCurrentESN(esn);
		}
		
		if(!cellTech.equalsIgnoreCase("CDMA")){
			Hashtable<String, Object> simOffering = serviceUtil.GetProductOffering(partnerID,"SIM_CARD", "BYOP", carrier, cellTech, zipCode, "");
			simPart = (String) simOffering.get("simPart");
			offering.putAll(simOffering);
		}
		
		Hashtable<String, Object> planOffering = serviceUtil.GetProductOffering(partnerID,"SERVICE", "BYOP", carrier, cellTech, zipCode, simPart);
		offering.putAll(planOffering);
		System.out.println(offering);
	}

	public void estimateOrderForSimAndPlanOnInZipCodeFor(String carrier,String cellTech, String partnerID, String zipCode, String phoneModel) throws Exception {
		String simPart = "";
		String sim = "";
		
		Hashtable<String, Object> offering = new Hashtable<String, Object> ();
		
		if (!cellTech.equalsIgnoreCase("GSM")) {
			String esnStr = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
			serviceUtil.registerBYOP(partnerID, zipCode, esnStr, carrier, cellTech, sim);
			ESN esn;
			esn = new ESN("NTBYOPDEC", esnStr);
			esnUtil.setCurrentESN(esn);
		}
		
		if(!cellTech.equalsIgnoreCase("CDMA")){
			Hashtable<String, Object> simOffering = serviceUtil.GetProductOffering(partnerID,"SIM_CARD", "BYOP", carrier, cellTech, zipCode, "");
			simPart = (String) simOffering.get("simPart");
			offering.putAll(simOffering);
		}
		
		Hashtable<String, Object> planOffering = serviceUtil.GetProductOffering(partnerID,"SERVICE", "BYOP", carrier, cellTech, zipCode, simPart);
		offering.putAll(planOffering);
		System.out.println(offering);
		
		offering = serviceUtil.estimateOrder(partnerID,offering,cellTech);
		String orderID = (String) offering.get("orderID");
		System.out.println(offering);
		System.out.println(orderID);

	}

	public void checkEligibilityForInFor(String partnerID, String carrier, String cellTech, String zipCode, String phoneModel) throws Exception {
		String esn = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
	}

	public void registerBYOPForIn(String partnerID, String carrier, String cellTech, String zipCode, String phoneModel) throws Exception {
		String sim = "";
		String esn = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
		serviceUtil.registerBYOP(partnerID, zipCode, esn, carrier, cellTech, sim);
	}

	public void validateNAPForInFor(String partnerID, String carrier, String cellTech, String zipCode, String phoneModel, String simPart) throws Exception {
		String sim = "";
		if(!simPart.isEmpty()){
			sim = simUtil.getNewSimCardByPartNumber(simPart);
		}
		String esn = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
		serviceUtil.registerBYOP(partnerID, zipCode, esn, carrier, cellTech, sim);
		serviceUtil.checkNAPValidation(partnerID, zipCode, esn, sim);
	}

	public void placeOrderWithIFramesForSimAndPlanOnInFor(String carrier,String cellTech,String partnerID, String zipCode, String phoneModel) throws Exception {
		String simPart = "";
		String sim = "";
		String partyID = null;
		String partySignature = null;
		
		Hashtable<String, Object> offering = new Hashtable<String, Object> ();
		
		if (!cellTech.equalsIgnoreCase("GSM")) {
			String esnStr = serviceUtil.checkEligibility(partnerID, carrier, cellTech, zipCode, phoneModel);
			serviceUtil.registerBYOP(partnerID, zipCode, esnStr, carrier, cellTech, sim);
			ESN esn;
			esn = new ESN("NTBYOPDEC", esnStr);
			esnUtil.setCurrentESN(esn);
		}
		
		if(!cellTech.equalsIgnoreCase("CDMA")){
			Hashtable<String, Object> simOffering = serviceUtil.GetProductOffering(partnerID,"SIM_CARD", "BYOP", carrier, cellTech, zipCode, "");
			simPart = (String) simOffering.get("simPart");
			offering.putAll(simOffering);
		}
		
		Hashtable<String, Object> planOffering = serviceUtil.GetProductOffering(partnerID,"SERVICE", "BYOP", carrier, cellTech, zipCode, simPart);
		offering.putAll(planOffering);
		System.out.println(offering);
		
		partyID = (String) offering.get("partyID");
		partySignature = (String) offering.get("partySignature");
		
		offering = serviceUtil.estimateOrder(partnerID,offering,cellTech);
		String orderID = (String) offering.get("orderID");
		System.out.println(offering);
		System.out.println(orderID);
		
		Hashtable<String, String> account = serviceUtil.CreateCustomer(partnerID,offering);
		String emailStr = account.get("email");
		
		String creditCardPageURL = rb.getString("B2B.HomeURL")+"/shop/en/net10business/TFPaymentCaptureCmd?"
				+"iID="+partyID+"&"
				+"iIS="+partySignature+"&"
				+"orderId="+orderID+"&"
				+"address1=9700%20NW%20112th%20Ave&"
				+"city=Miami&"
				+"state=FL&"
				+"country=US&"
				+"zipCode=33178";
		
		activationPhoneFlow.navigateTo(creditCardPageURL);
		
		String cc = TwistUtils.generateCreditCard("VISA");
		activationPhoneFlow.typeInTextField("account_number", cc);
		activationPhoneFlow.chooseFromSelect("expMonth","07");
		activationPhoneFlow.chooseFromSelect("expYear","2021");
		activationPhoneFlow.typeInTextField("cvv2","671");
		activationPhoneFlow.pressSubmitButton("PAY");
		
		Assert.assertTrue(activationPhoneFlow.getBrowser().byText("Your Order "+orderID +" has been successfully placed", "div").isVisible());
		
		serviceUtil.checkOrderStatus(orderID, emailStr);
	}

	public void encryptPaymentFor(String cardType) throws Exception {
		Hashtable<String, String> cc = serviceUtil.encryptPayment(cardType);
		System.out.println("cryptoKey : " + cc.get("encryptedValue"));
		System.out.println("encryptedValue : " + cc.get("encryptedValue"));
	}
	
}

