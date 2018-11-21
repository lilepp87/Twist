package com.tracfone.twist.simpurchase;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;


public class SimPurchase {
	
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;

	public SimPurchase() {

	}

	public void goToSims() throws Exception {
		//selecting phones under sim cards
		activationPhoneFlow.clickLink("Phones[2]");
	}
	
	public void chooseSimType(String carrier) throws Exception {
		if(carrier.equalsIgnoreCase("ATT")){
			activationPhoneFlow.selectRadioButton("phone_option[2]");
		}else if(carrier.equalsIgnoreCase("TMO")){
			activationPhoneFlow.selectRadioButton("phone_option[1]");
		}else{
			activationPhoneFlow.selectRadioButton("phone_option");	
		}
		activationPhoneFlow.typeInTextField("input_zip", "33178");
		activationPhoneFlow.pressSubmitButton("CHECK AVAILABILITY");
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
	}

	public void enterPaymentDetailsAndSubmitOrder(String cardType) throws Exception {
		String cc =TwistUtils.generateCreditCard(cardType);
		activationPhoneFlow.typeInTextField("shipFName","Twist FirstName");
		activationPhoneFlow.typeInTextField("shipLName","Twist LastName");
		activationPhoneFlow.typeInTextField("shipPhone","3053053055");
		activationPhoneFlow.typeInTextField("shipEmail","test@b2ctest.com");
		activationPhoneFlow.typeInTextField("shipAddress1","9700 TRACFONE");
		activationPhoneFlow.typeInTextField("shipCity","Miami");
		activationPhoneFlow.chooseFromSelect("shipState","FL");
		activationPhoneFlow.typeInTextField("shipZip","33178");
		activationPhoneFlow.selectCheckBox("useShipping");
		activationPhoneFlow.chooseFromSelect("shipViaExtensionId","FedEx 3 Days - $4.95");
		activationPhoneFlow.chooseFromSelect("paymentMethodExtensionId",cardType);
		activationPhoneFlow.typeInTextField("CCNumber",cc);
		activationPhoneFlow.chooseFromSelect("CCExpirationMonth","07");
		activationPhoneFlow.chooseFromSelect("CCExpirationYear","2021");
		activationPhoneFlow.typeInTextField("CCSecurity","123");
		activationPhoneFlow.clickLink("continue");
		activationPhoneFlow.getBrowser().listItem("Submit Order").click();
		activationPhoneFlow.clickLink("Submit Order");
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

	public void selectSimAndPlan(String simType) throws Exception {
		activationPhoneFlow.clickLink(simType);
		activationPhoneFlow.clickLink("SELECT");
		activationPhoneFlow.clickLink("SELECT[1]");
	}

}