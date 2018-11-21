package com.tracfone.twist.buyfromshop;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.activation.PortNumber;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;


public class Shop {

	private MyAccountFlow myAccountFlow;
	private static ESNUtil esnUtil;
	private PortNumber portNumber;
	
	//private String ccZipcode = myAccountFlow.
	
	private ResourceBundle rb = ResourceBundle.getBundle("SM");

	public Shop() {

	}
	

	public PortNumber getPortNumber() {
		return portNumber;
	}


	public void setPortNumber(PortNumber portNumber) {
		this.portNumber = portNumber;
	}


	public static ESNUtil getEsnUtil() {
		return esnUtil;
	}

	public static void setEsnUtil(ESNUtil esnUtil) {
		Shop.esnUtil = esnUtil;
	}

	public void goToShopSimCards() throws Exception {
		myAccountFlow.clickLink("Bring Your Own Phone");
	}

	public void selectZipSIMType(String phoneType, String zip, String simType) throws Exception {
		if (phoneType.equalsIgnoreCase("Unlocked GSM Phone")) {
				myAccountFlow.pressButton("tmoCompBtn1");			
		}
		TwistUtils.setDelay(5);
		myAccountFlow.pressButton("tmoCompBtn");
		TwistUtils.setDelay(8);			
		myAccountFlow.getBrowser().link("btn btn-greenSolid").click();
		ElementStub e4 = myAccountFlow.getBrowser().byId("zipPopup_zc");
		e4.setValue(zip);
		myAccountFlow.getBrowser().button("pdp_zipbutton").click();
		if (simType.equalsIgnoreCase("Nano")) {
			//myAccountFlow.clickLink("BUY");
			myAccountFlow.clickLink("SELECT");
		} else if (simType.equalsIgnoreCase("Dual")) {
			//myAccountFlow.clickLink("BUY[1]");
			myAccountFlow.clickLink("SELECT[1]");
		}

	}

	public void checkForShopPhonesRedirectingPage() throws Exception {
		myAccountFlow.clickLink("Phones");
		Assert.assertTrue(myAccountFlow.h1Visible("YOU ARE NOW LEAVING SIMPLE MOBILE"));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void buttonClick(String buttonName) {
		if (myAccountFlow.buttonVisible(buttonName)) {
			myAccountFlow.pressButton(buttonName);
		} else {
			myAccountFlow.pressSubmitButton(buttonName);
		}
	}

	public void selectPlan(String planType) throws Exception {
		if (planType.equalsIgnoreCase("SMNAPP0040UNL")) {
			myAccountFlow.clickLink("Add to Cart[1]");
		}
	}

	public void addToCart() throws Exception {
		myAccountFlow.clickLink("Proceed to checkout");
	}

	public void checkoutWithCard(String cardType) throws Exception {
		String email = TwistUtils.createRandomEmail();
		myAccountFlow.typeInTextField("shipFName", "TwistFirstName");
		myAccountFlow.typeInTextField("shipLName", "TwistLastName");
		myAccountFlow.typeInTextField("shipPhone", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("shipEmail", email);
		myAccountFlow.typeInTextField("shipAddress1", "1295 Charleston Road");
		myAccountFlow.typeInTextField("shipCity", "Mountain View");
		myAccountFlow.chooseFromSelect("shipState", "CA");
		myAccountFlow.typeInTextField("shipZip", "94043");
		myAccountFlow.selectCheckBox("useShipping");
		myAccountFlow.chooseFromSelect("shipViaExtensionId", "FedEx 3 Day - $4.95");
		paymentWithCard(cardType);
	}

	public void paymentWithCard(String cardType) throws Exception {
		String cardNumber = null;
		if (cardType.equalsIgnoreCase("AMEX")) {
			cardNumber = TwistUtils.generateCreditCard("American Express");
		} else if (cardType.equalsIgnoreCase("MASTERCARD")) {
			cardNumber = TwistUtils.generateCreditCard("Master Card");
		} else {
			cardNumber = TwistUtils.generateCreditCard(cardType);
		}

		myAccountFlow.chooseFromSelect("paymentMethodExtensionId", cardType);
		myAccountFlow.typeInTextField("CCNumber", cardNumber);
		myAccountFlow.chooseFromSelect("CCExpirationMonth", "07");
		myAccountFlow.chooseFromSelect("CCExpirationYear", "2021");
		if (cardType.equalsIgnoreCase("AMEX")) {
			myAccountFlow.typeInTextField("CCSecurity", "6715");
		} else {
			myAccountFlow.typeInTextField("CCSecurity", "671");
		}

	}

	public void submitOrder() throws Exception {
		myAccountFlow.clickLink("Next Step");
		myAccountFlow.clickLink("Submit Order");
		Assert.assertTrue(myAccountFlow.listItemVisible("Order Complete"));
	}

	public void goToShopPhones(String customer, String zipCode) throws Exception {
		TwistUtils.setDelay(5);
		if (customer.equalsIgnoreCase("new")){
		ElementStub e = myAccountFlow.getBrowser().byId("zipPopup_zc");
		e.setValue(zipCode);
		myAccountFlow.pressSubmitButton("Continue[0]");
		}else{
			portNumber.enterPhoneOfBrand(rb.getString("OldEsn"));
		}
	}
	
	public void goToTFStore(String customer, String zipCode) throws Exception {
		TwistUtils.setDelay(5);
		if (customer.equalsIgnoreCase("new")){
		ElementStub e = myAccountFlow.getBrowser().byId("zipPopup_zc");
		e.setValue(zipCode);
		myAccountFlow.pressSubmitButton("Continue[0]");
		}else{
			portNumber.enterPhoneOfBrand(rb.getString("TfEsn"));
		}
	}
	
	/*public void enterExistingCustomerMin() throws Exception {
		TwistUtils.setDelay(2);
		portNumber.enterPhoneOfBrand(oldPart);
			}*/
	

	public void selectAPhoneAndPlanToCheckout() throws Exception {
		//myAccountFlow.clickLink("BUY[3]");
		//myAccountFlow.clickLink("Buy Now");
		myAccountFlow.clickLink("SELECT");
		myAccountFlow.clickLink("Buy PHONE Only");
		/*myAccountFlow.clickLink("WC_CatalogEntryDBThumbnailDisplayJSPF_28002_link_9b_buy");
		myAccountFlow.clickLink("add2CartBtn_33003");*/
	}
	
	/*public void selectPhoneBasedOnPhone(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("APPLE")) {
			myAccountFlow.clickLink("catalogEntry_img124508");
		} else if (phone.equalsIgnoreCase("Samsung")) {
			myAccountFlow.clickLink("catalogEntry_img1133002");			
		
		} else {
			throw new IllegalArgumentException("Unknown phone type: " + phone);
		}
	}*/
	
	public void selectPhoneBasedOnPhone(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("Samsung")) {
			myAccountFlow.selectCheckBox(rb.getString("Brand.Samsung"));
			TwistUtils.setDelay(8);
			myAccountFlow.clickLink(rb.getString("Samsung.1"));
			TwistUtils.setDelay(5);
			if(!myAccountFlow.linkVisible("ADD TO CART")){
				myAccountFlow.clickLink("CONTINUE SHOPPING");
				TwistUtils.setDelay(10);
				myAccountFlow.clickLink(rb.getString("Samsung.1"));
				TwistUtils.setDelay(5);
				
			}else if(!myAccountFlow.linkVisible("ADD TO CART")){
				myAccountFlow.clickLink("CONTINUE SHOPPING");
				System.out.println("Phones are out of Stock");
			}
			
		} else if (phone.equalsIgnoreCase("Apple")) {
			myAccountFlow.selectCheckBox(rb.getString("Brand.Apple"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("Apple.1"));
			TwistUtils.setDelay(5);
			if(!myAccountFlow.linkVisible("ADD TO CART")){
				myAccountFlow.clickLink("CONTINUE SHOPPING");
				TwistUtils.setDelay(10);
				myAccountFlow.clickLink(rb.getString("Apple.2"));
				TwistUtils.setDelay(10);				
			}else if(!myAccountFlow.linkVisible("ADD TO CART")){
				myAccountFlow.clickLink("CONTINUE SHOPPING");
				System.out.println("Phones are out of Stock");
			}	
	
		} else {
			throw new IllegalArgumentException("Unknown phone type: " + phone);
		}
		
	}
	
	public void selectTfPhone(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("Samsung")) {
			myAccountFlow.selectCheckBox(rb.getString("TF.Samsung"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("TF.Samsung.1"));				
		}else if(phone.equalsIgnoreCase("zte")){
			myAccountFlow.selectCheckBox(rb.getString("TF.Zte"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("TF.zte.1"));
		}else if(phone.equalsIgnoreCase("lg")){
			myAccountFlow.selectCheckBox(rb.getString("TF.Lg"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("TF.lg.1"));	
		}else if(phone.equalsIgnoreCase("alcatel")){
			myAccountFlow.selectCheckBox(rb.getString("TF.Alcatel"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("TF.alcatel.1"));
		}else if(phone.equalsIgnoreCase("unimax")){
			myAccountFlow.selectCheckBox(rb.getString("TF.Unimax"));
			TwistUtils.setDelay(10);
			myAccountFlow.clickLink(rb.getString("TF.unimax.1"));
		}
	}

	public void goToCartAndCheckout() throws Exception {	
		String email = TwistUtils.createRandomEmail();
		System.out.println(email);
		TwistUtils.setDelay(10);
		//myAccountFlow.clickLink("GO TO SECURE CHECKOUT[1]");
		if (myAccountFlow.linkVisible("GO TO SECURE CHECKOUT[1]")) {
			myAccountFlow.clickLink("GO TO SECURE CHECKOUT[1]");
		} else if (myAccountFlow.linkVisible("GO TO SECURE CHECKOUT")) {
			myAccountFlow.clickLink("GO TO SECURE CHECKOUT");
		}
		myAccountFlow.typeInTextField("firstName", "Twist FirstName");
		myAccountFlow.typeInTextField("lastName", "Twist LastName");
		myAccountFlow.typeInTextField("address1", "1295 Charleston Road");
		myAccountFlow.chooseFromSelect("state", "California");
		myAccountFlow.typeInTextField("city", "Mountain View");
		myAccountFlow.typeInTextField("zipCode", "94043"); //-- it is auto populated
		myAccountFlow.typeInTextField("phone1", "3059999999");
		myAccountFlow.typeInTextField("email1", email);
		//myAccountFlow.getBrowser().textarea("giftMessageText").setValue("Twist Gift Message");
	}
	
	public void goToCartAndCheckoutFromShop() throws Exception {	
		String email = TwistUtils.createRandomEmail();
		System.out.println(email);
		//myAccountFlow.clickLink("GO TO SECURE CHECKOUT[1]");
		if(myAccountFlow.linkVisible("GO TO SECURE CHECKOUT[1]")){
			myAccountFlow.clickLink("GO TO SECURE CHECKOUT[1]");
		}else if(myAccountFlow.linkVisible("GO TO SECURE CHECKOUT")){
			myAccountFlow.clickLink("GO TO SECURE CHECKOUT");
		}
		myAccountFlow.typeInTextField("firstName", "Twist FirstName");
		myAccountFlow.typeInTextField("lastName", "Twist LastName");
		myAccountFlow.typeInTextField("address1", "1295 Charleston Road");
		myAccountFlow.chooseFromSelect("state", "California");
		myAccountFlow.typeInTextField("city", "Mountain View");
		//myAccountFlow.typeInTextField("zipCode", "94043"); //-- it is auto populated
		myAccountFlow.typeInTextField("phone1", "3059999999");
		myAccountFlow.typeInTextField("email1", email);
		//myAccountFlow.getBrowser().textarea("giftMessageText").setValue("Twist Gift Message");
	}

	public void enterCreditCardDetailsAndPurchase(String card) throws Exception {
		String cardNum = TwistUtils.generateCreditCard(card);
		myAccountFlow.chooseFromSelect("payMethodId", card + " Credit Card");
		System.out.println(cardNum);
		myAccountFlow.typeInTextField("account1", cardNum);
		if (card.equalsIgnoreCase("American Express")) {
			myAccountFlow.typeInTextField("cc_cvc", "6715");
		} else {
			myAccountFlow.typeInTextField("cc_cvc", "671");
		}
		myAccountFlow.chooseFromSelect("expire_month", "07");
		myAccountFlow.chooseFromSelect("expire_year", "2021");
	}

	public void confirmOrder() {
		myAccountFlow.clickLink("CONTINUE TO ORDER SUMMARY");
		if(myAccountFlow.buttonVisible("Use suggested address")){
			myAccountFlow.pressButton("Use suggested address");
		}
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("Order");
		TwistUtils.setDelay(5);
		Assert.assertTrue(myAccountFlow.h2Visible("Thank you for your order!"));
		ElementStub el = myAccountFlow.getBrowser().byId("WC_OrderShippingBillingConfirmationPage_div_4");
		String orderId = myAccountFlow.getBrowser().getText(el);
		System.out.println(orderId);
		ESN esn = new ESN("Order Number", orderId.substring(orderId.indexOf(":")+1,orderId.lastIndexOf("Order")-1));
        esnUtil.setCurrentESN(esn);

		String[] possOrder = orderId.split("13,24");
		int orderNum =  -1;
		for (int i = 2; i < possOrder.length; i++)	{
			try {
				orderNum = Integer.valueOf(possOrder[i]);
				break;
			} catch (NumberFormatException ex) {}			
		}
		//System.out.println("Order number:" + orderNum);
	}

	public void selectPlan() throws Exception {
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("Buy Now[4]");
	}

	public void selectOnlySIM() throws Exception {
		myAccountFlow.clickLink("Buy SIM Only");
	}
	
	public void loginWithAmazonCredentialsAndGoto(String gotoLink) throws Exception {
		TwistUtils.setDelay(10);
		myAccountFlow.navigateTo(rb.getString("sm.amazonpage"));
		TwistUtils.setDelay(5);
		try{
			myAccountFlow.clickLink("Login with Amazon");
		Browser popup1 = myAccountFlow.getBrowser().popup("Amazon.com Sign In");
		popup1.emailbox("email").setValue("itquser@yopmail.com");
		popup1.password("password").setValue("tracfone");
		popup1.submit("Sign in using our secure server").click();
		TwistUtils.setDelay(10);
			Browser popup2 = myAccountFlow.getBrowser().popup("Amazon.com Consent");
			if(popup2.isVisible(popup1.submit("consentApproved"))){
				popup2.submit("consentApproved").click();
			}
		}catch(Exception e){
			System.out.println("Already login or popup came up");
		}
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink(gotoLink);
		TwistUtils.setDelay(10);
	}
	
	public void enterZipCodeAndShopSim() throws Exception {
		TwistUtils.setDelay(5);
		ElementStub e5 = myAccountFlow.getBrowser().byId("zipPopup_zc");
		e5.setValue("33178");
		myAccountFlow.getBrowser().button("pdp_zipbutton").click();
	}
	
	public void selectSim() throws Exception {
		myAccountFlow.clickLink("SELECT");
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("Buy SIM Only");
	}

	public void addPhoneToCart() throws Exception {
		if (myAccountFlow.getBrowser().paragraph("top-push-4 text-black[2]").isVisible()){
			myAccountFlow.getBrowser().paragraph("top-push-4 text-black[2]").click();
			//myAccountFlow.getBrowser().byId("lto_smartpay_option_2").click();
		}
		//myAccountFlow.getBrowser().
		//byId("price top-push-6").click();
		if(myAccountFlow.linkVisible("ADD TO CART")){
		myAccountFlow.clickLink("ADD TO CART");
		}
		else {
			System.out.println("Phone Out Of Stock");
			myAccountFlow.clickLink("CONTINUE SHOPPING");			
		}			
	}
	
	public void chooseMonthlyPaymentPlanOnCreditScore(String creditScore) throws Exception {
		TwistUtils.setDelay(5);
		if(myAccountFlow.linkVisible("ADD TO CART")){
					
		if(creditScore.equalsIgnoreCase("Excellent")){
			myAccountFlow.getBrowser().paragraph("top-push-4 text-black[0]").isVisible();
			}
		else {
			myAccountFlow.getBrowser().paragraph("top-push-4 text-black[1]").isVisible();
			myAccountFlow.getBrowser().paragraph("top-push-4 text-black[1]").click();
		}
		}else{
			System.out.println("Phone Out Of Stock");
			myAccountFlow.clickLink("CONTINUE SHOPPING");	
		}
		TwistUtils.setDelay(3);
		myAccountFlow.clickLink("ADD TO CART");
	}
	public void selectPlanforSmartPayPurchase(String customer, String plan){
		if(customer.equalsIgnoreCase("NEW")){
			selectServicePlan(plan);
		}else {
			TwistUtils.setDelay(3);			
			myAccountFlow.getBrowser().link("add2CartBtn_73089").click();
			TwistUtils.setDelay(3);
			myAccountFlow.clickLink("CHECK OUT");
			
		}
			
		
	}
	
	
	public void selectServicePlan(String servicePlan) {
		String planDiv = "";
		if (servicePlan.equalsIgnoreCase("$25")) {
			planDiv = rb.getString("sm.25");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[0]");
		} else if (servicePlan.equalsIgnoreCase("$30")) {
			planDiv = rb.getString("sm.30");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[1]");
		} else if (servicePlan.equalsIgnoreCase("$40")) {
			planDiv = rb.getString("sm.40");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[2]");
		} else if (servicePlan.equalsIgnoreCase("$50")) {
			planDiv = rb.getString("sm.50");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[3]");
		} else if (servicePlan.equalsIgnoreCase("$60")) {
			planDiv = rb.getString("sm.60");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[4]");
		} else if (servicePlan.equalsIgnoreCase("No Plan")) { 
			// No thanks to the plan
			planDiv = rb.getString("sm.NoPlan");
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[5]");
		}
		
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("CHECK OUT");
		TwistUtils.setDelay(5);
	
	}
	
	public void selectTfServicePlan(String servicePlan) {
		String planDiv = "";
		if (servicePlan.equalsIgnoreCase("$15")) {
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[0]");
		} else if (servicePlan.equalsIgnoreCase("$25")) {
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[1]");
		} else if (servicePlan.equalsIgnoreCase("$35")) {
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[2]");
		} else if (servicePlan.equalsIgnoreCase("$125")) {
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[3]");
		} else if (servicePlan.equalsIgnoreCase("No Plan")) { 
			// No thanks to the plan
			TwistUtils.setDelay(25);
			myAccountFlow.clickLink("Continue[4]");
		}
		
		TwistUtils.setDelay(5);
		myAccountFlow.clickLink("CHECK OUT");
		TwistUtils.setDelay(5);
	
	}

	public void enterShippingAddressAndWhiteListedCard(String card) throws Exception {
		String cardNum = TwistUtils.generateCreditCard(card);		
		enterShippingInfo();
		myAccountFlow.chooseFromSelect("payMethodId_1", card + " Credit Card");
		myAccountFlow.typeInTextField("account1_1", cardNum);
		if(card.equalsIgnoreCase("American Express")){
			myAccountFlow.typeInTextField("cc_cvc_1", "6718");
		}else {
			myAccountFlow.typeInTextField("cc_cvc_1", "671");
		}
		myAccountFlow.chooseFromSelect("expire_month_1", "07");
		myAccountFlow.chooseFromSelect("expire_year_1", "2021");
		
		myAccountFlow.clickLink("CONTINUE");		
		TwistUtils.setDelay(15);
		myAccountFlow.pressSubmitButton("Submit[0]");
			
	}
	
	public void enterShippingInfo() throws Exception{
		TwistUtils.setDelay(20);
		if(myAccountFlow.buttonVisible("Ok")){
		myAccountFlow.pressButton("Ok");
		}
		String email = "itq_st_" + TwistUtils.createRandomEmail();
		myAccountFlow.typeInTextField("firstName", "Cyber");
		myAccountFlow.typeInTextField("lastName", "Source");
		myAccountFlow.typeInTextField("phone1", TwistUtils.generateRandomMin());
		myAccountFlow.typeInTextField("email1", email);
		myAccountFlow.typeInTextField("address1", "1295 charleston road");
		myAccountFlow.typeInTextField("address2", "");
		myAccountFlow.typeInTextField("city", "Mountain view");
		myAccountFlow.chooseFromSelect("state", "California");
		
		if(!myAccountFlow.getBrowser().byId("zipCode").containsText("94043")){
			if(myAccountFlow.linkVisible("btn btn-link pull-left top-push-2 zip-code")){
			myAccountFlow.clickLink("btn btn-link pull-left top-push-2 zip-code");
			TwistUtils.setDelay(5);
			myAccountFlow.clickLink("Change ZIP");
			TwistUtils.setDelay(3);
			myAccountFlow.typeInTextField("zipCode", "94043");
			}else{
			myAccountFlow.typeInTextField("zipCode", "94043");
			}
		}			
	}
	
	public void applyOnlineForSmartPay(){
		myAccountFlow.clickLink("CONTINUE");		
		TwistUtils.setDelay(50);
		//Assert.assertTrue(myAccountFlow.h1Visible("Monthly payment application"));
		myAccountFlow.clickLink("Apply online");	
		
	}
	
	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(myAccountFlow.h3Visible("ORDER SUMMARY"));		
		
	}
	public void printOrderNumber(){
		ElementStub el = myAccountFlow.getBrowser().heading2("panel-title title-panel-box number-order");
		String orderId = myAccountFlow.getBrowser().getText(el);
		System.out.println(orderId);
		ESN esn = new ESN("Order Number", orderId.substring(orderId.indexOf(":")+1,orderId.lastIndexOf("Order")-1));
        esnUtil.setCurrentESN(esn);

		String[] possOrder = orderId.split("13,24");
		int orderNum =  -1;
		for (int i = 2; i < possOrder.length; i++)	{
			try {
				orderNum = Integer.valueOf(possOrder[i]);
				break;
			} catch (NumberFormatException ex) {}			
		}
		
	}
	public void selectSimKit(){
		myAccountFlow.getBrowser().byId("WC_CatalogEntryDBThumbnailDisplayJSPF_124547_link_9b").click();
		TwistUtils.setDelay(10);
		myAccountFlow.getBrowser().byId("select_phone_bundles_btn").click();
		
	}
	public void selectCarrierSimKit(String carrier){
		if(carrier.equalsIgnoreCase("ATT")){
			myAccountFlow.getBrowser().byId(rb.getString("TF.ATT")).click();			
		}else if (carrier.equalsIgnoreCase("TMO")){
			myAccountFlow.getBrowser().byId(rb.getString("TF.TMO")).click();	
		}else if (carrier.equalsIgnoreCase("VZW")){
			myAccountFlow.getBrowser().byId(rb.getString("TF.VZW")).click();	
		}
		TwistUtils.setDelay(10);
		myAccountFlow.getBrowser().byId("select_phone_bundles_btn").click();
	}
	
	public void completeSmartPayApplication(){
		
		 System.out.println(myAccountFlow.getBrowser().iframe("smartPayIFrame").isVisible());
		 //myAccountFlow.getBrowser().sw
			
	}
	public void applythroughSmartPay() throws Exception {
	        Browser smartpay = myAccountFlow.getBrowser().domain("applysandbox.billfloat.com");
	         
	        smartpay.select("birth_month").choose("May");
	        smartpay.select("birth_day").choose("2");
	        smartpay.select("birth_year").choose("1985");
	       // smartpay.byId("ssn").setValue("5698");
	        
	       //smartpay.submit("form-submit-button").click();
	       myAccountFlow.getBrowser().domain("applysandbox.billfloat.com").submit("Continue").click();
	       // smartpay.submit("CONTINUE[1]").click();
	       // smartpay.submit("CONTINUE[2]").click();
	        
	        /*ElementStub dsf = smartpay.byId("form-submit-button");
	        System.out.println(dsf.getText());
	        dsf.click();*/
	
	}
}