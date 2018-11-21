package com.tracfone.twist.ShopPhones;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.ElementStub;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.ORG;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ShopPhones {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private static ORG org;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private ResourceBundle rb = ResourceBundle.getBundle("B2B");

	public ShopPhones() {
		
	}

	public void goToPhones() throws Exception {
		activationPhoneFlow.clickLink("PHONES[1]");
		TwistUtils.setDelay(10);
	}
	
	public void goToBusinessLines() throws Exception {
		activationPhoneFlow.clickLink("PHONES");
		activationPhoneFlow.clickLink("WIRELESS BUSINESS LINE");
		TwistUtils.setDelay(10);
	}

	public void submitOrder(String ccTYPE) throws Exception {
		activationPhoneFlow.clickLink("VIEW CART");
/*		activationPhoneFlow.clickLink("Checkout"); */
		TwistUtils.setDelay(6);
		
		activationPhoneFlow.clickLink(rb.getString("B2B.CheckOut"));
//		activationPhoneFlow.getBrowser().select("singleShipmentShippingMode").choose("FedEx 3-Day");
		TwistUtils.setDelay(10);
		activationPhoneFlow.pressSubmitButton("Continue");
		TwistUtils.setDelay(3);
		activationPhoneFlow.getBrowser().radio("creditCard").click();
		if (activationPhoneFlow.textboxVisible("cvvNumber1")) {
			String cc = TwistUtils.generateCreditCard(ccTYPE);
			activationPhoneFlow.typeInTextField("accountNumber", cc);
			activationPhoneFlow.chooseFromSelect("expirationMonth", "07");
			activationPhoneFlow.chooseFromSelect("expirationYear", "2021");
			/*ElementStub e1 = activationPhoneFlow.getBrowser().byId("cvvNumber1");
			e1.setValue("378");*/
			activationPhoneFlow.typeInTextField("cvvNumber1", "346");
			activationPhoneFlow.selectCheckBox("preferredCC");
			activationPhoneFlow.pressSubmitButton("Continue");
			//activationPhoneFlow.chooseFromSelect("addressId", "Default");
		}
		/*if (activationPhoneFlow.textboxVisible("cvv2")) {
			String cc = TwistUtils.generateCreditCard(ccTYPE);
			activationPhoneFlow.typeInTextField("account_number", cc);
			activationPhoneFlow.typeInTextField("cvv2", "123");
			activationPhoneFlow.chooseFromSelect("expMonth", "07");
			activationPhoneFlow.chooseFromSelect("expYear", "2021");
			activationPhoneFlow.selectCheckBox("ccIsDefault");
			activationPhoneFlow.chooseFromSelect("addressId", "Default");
		}*/ 
		else if (activationPhoneFlow.textboxVisible("cvv2_saved")) {
			activationPhoneFlow.typeInTextField("cvv2_saved", "123");
		}
/*		activationPhoneFlow.clickLink("CONTINUE TO REVIEW & SUBMITCONTINUE TO REVIEW & SUBMIT"); */
		//activationPhoneFlow.clickLink(rb.getString("B2B.ContinueCheckout")); 
		TwistUtils.setDelay(3);
		activationPhoneFlow.getBrowser().radio("shippingOptions[0]").click();
		//Assert.assertTrue(activationPhoneFlow.divVisible("row3"));
		activationPhoneFlow.selectCheckBox("acceptTerms");
/*		activationPhoneFlow.clickLink("Submit order"); */
		activationPhoneFlow.pressSubmitButton("Place Order");
		//activationPhoneFlow.clickLink(rb.getString("B2B.SubmitOrder")); 
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.h1Visible("Thank you for your order!"));
		ElementStub el = activationPhoneFlow.getBrowser().byId("WC_OrderShippingBillingConfirmationPage_span_1");
		String orderId = activationPhoneFlow.getBrowser().getText(el);
		org.setClfyOrderId(orderId);
		System.out.println(orderId);
/*		activationPhoneFlow.clickLink("Continue Shopping"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.ContinueShopping")); 
		insertdata();
	}
	
	public void insertdata(){
		String ORGName = org.getOrgName();
		if (!(ORGName == null || ORGName.isEmpty())) {
			org.setEcommOrgId(phoneUtil.getOrgIdByOrgName(ORGName));			
		}
		org.setProject("B2B");
		org.insertoB2BTable();
	}
	
	public void setORG(ORG newORG) {
		org = newORG;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void selectPhoneAndCheckAvailabilityIn(String model, String zipCode)	throws Exception {
		if (activationPhoneFlow.linkVisible("View all")) {
			activationPhoneFlow.clickLink("View all");
		}
		activationPhoneFlow.clickLink(model);
		TwistUtils.setDelay(20);
		activationPhoneFlow.clickLink("Check Availability");
		activationPhoneFlow.typeInTextField("zip_code", zipCode);
		activationPhoneFlow.clickLink("Check");
	}

//	public void selectPlanAndQuantity(String plan, String qty) throws Exception {
////		activationPhoneFlow.chooseFromSelect("secondCatEntryDropDown", plan); 
//		activationPhoneFlow.typeInTextField("left marginLeft5 marginTop5 txt-center", qty);
///*		activationPhoneFlow.clickLink("Add to Cart"); */
//		activationPhoneFlow.clickLink(rb.getString("B2B.AddToCart")); 
//	}

	public void goToPlans() throws Exception {
		TwistUtils.setDelay(10);
		activationPhoneFlow.clickLink("PLANS");
		activationPhoneFlow.clickLink("TALK, TEXT & DATA");
	}
	
	public void goToBusinessLinePlans() throws Exception {
		  TwistUtils.setDelay(10);
		  activationPhoneFlow.clickLink("PLANS");
		  activationPhoneFlow.clickLink("WIRELESS BUSINESS LINE[1]");
	}
	
	public void goToDataClubPlans() throws Exception {
		  TwistUtils.setDelay(10);
//		  activationPhoneFlow.clickLink("PLANS");
		  activationPhoneFlow.clickLink("DATA PLANS");
	}

	public void selectPlanAndCheckAvailabilityIn(String plan, String zipCode)
			throws Exception {
//		activationPhoneFlow.clickLink("BUSINESS LINE[1]");
		TwistUtils.setDelay(10);
		activationPhoneFlow.clickLink(("/."+plan+".*?/"));
		activationPhoneFlow.clickLink("Check Availability");
		activationPhoneFlow.typeInTextField("zip_code", zipCode);
		activationPhoneFlow.clickLink("Check");
	}

	public void selectPhoneQuantityAndEnroll(String phone, String qty, String lowBalance)
			throws Exception {
		selectPlanQuantityAndEnroll(phone, qty, lowBalance);
	}

	public void selectPlanQuantityAndEnroll(String plan, String qty, String lowBalance) throws Exception {
		if (plan.contains("SIM")) {
			activationPhoneFlow.selectCheckBox("bringingOwnPhone");
		}
		activationPhoneFlow.chooseFromSelect("secondCatEntryDropDown", ("/.*"+plan+".*?/")); 
	    activationPhoneFlow.typeInTextField("left marginLeft5 marginTop5 txt-center", qty);
	    if ("Yes".equalsIgnoreCase(lowBalance)) {
	    	activationPhoneFlow.selectCheckBox("autoDataRefillId");
	    	activationPhoneFlow.clickLink("ENROLL NOW");
	    }
	    activationPhoneFlow.clickLink(rb.getString("B2B.AddToCart"));
	    if (activationPhoneFlow.linkVisible("order_cancel_submit")) {
	    	activationPhoneFlow.clickLink("order_cancel_submit");
	    }
	}
	
}