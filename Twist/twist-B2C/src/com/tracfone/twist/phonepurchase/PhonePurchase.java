package com.tracfone.twist.phonepurchase;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.ElementStub;

import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;
import java.util.ResourceBundle;

public class PhonePurchase {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private static final ResourceBundle props = ResourceBundle.getBundle("B2C");

	public PhonePurchase() {
		
	}

	public void addToCart(String model) throws Exception {
		activationPhoneFlow.clickLink(model);
		activationPhoneFlow.clickLink("SELECT");
		activationPhoneFlow.clickLink("SELECT[1]");
	}
	
	public void goToCartAndCheckout() throws Exception {
		String email = TwistUtils.createRandomEmail();
		System.out.println(email);
		activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx 3-Day - $4.95");
		activationPhoneFlow.clickLink("GO TO SECURE CHECKOUT");
		//activationPhoneFlow.typeInTextField("nickName", "TwistNickname");
		activationPhoneFlow.typeInTextField("firstName", "Twist FirstName");
		activationPhoneFlow.typeInTextField("lastName", "Twist LastName");
		activationPhoneFlow.typeInTextField("address1", "9700 NW 112th avenue");
		//activationPhoneFlow.selectCheckBox("pobox");
		//activationPhoneFlow.chooseFromSelect("country", "United States");
		activationPhoneFlow.chooseFromSelect("state", "Florida");
		activationPhoneFlow.typeInTextField("city", "Miami");
		//activationPhoneFlow.typeInTextField("zipCode", "94043");
		activationPhoneFlow.typeInTextField("phone1", "9876543210");
		activationPhoneFlow.typeInTextField("email1", email);
		activationPhoneFlow.selectCheckBox("on");
		activationPhoneFlow.getBrowser().textarea("giftMessageText").setValue("Twist Gift Message");
		
		activationPhoneFlow.selectCheckBox("sam_ship");
		activationPhoneFlow.typeInTextField("firstName[1]", "Twist FirstName");
		activationPhoneFlow.typeInTextField("lastName[1]", "Twist LastName");
		activationPhoneFlow.typeInTextField("address1[1]", "1295 Charleston Rd");
		activationPhoneFlow.chooseFromSelect("state[1]", "California");
		activationPhoneFlow.typeInTextField("city[1]", "MOUNTAIN VIEW");
		activationPhoneFlow.typeInTextField("phone1[1]", "3059999999");
		activationPhoneFlow.typeInTextField("email1[1]", email);
		activationPhoneFlow.typeInTextField("zipCode[1]", "94043");
	}

	public void enterCreditCardDetails(String card) throws Exception {
		String cardNum = TwistUtils.generateCreditCard(card);
		activationPhoneFlow.chooseFromSelect("payMethodId", card + " Credit Card");
		System.out.println(cardNum);
		activationPhoneFlow.typeInTextField("account1", cardNum);
		activationPhoneFlow.typeInTextField("cc_cvc", "123");
		activationPhoneFlow.chooseFromSelect("expire_month", "07");
		activationPhoneFlow.chooseFromSelect("expire_year", "2021");
	}

	public void finishOrder() throws Exception {
		activationPhoneFlow.clickLink("CONTINUE TO ORDER SUMMARY");
		if(activationPhoneFlow.buttonVisible("Use suggested address")){
			activationPhoneFlow.pressButton("Use suggested address");
		}
		activationPhoneFlow.clickLink("Order");
		Assert.assertTrue(activationPhoneFlow.h2Visible("Thank you for your order!"));
		ElementStub el = activationPhoneFlow.getBrowser().byId("WC_OrderShippingBillingConfirmationPage_div_4");
		String orderId = activationPhoneFlow.getBrowser().getText(el);
		orderId = orderId.substring(13,19);
		System.out.println(orderId);
	}
	
	public void selectPhone(String cellTech , String carrier) throws Exception {
		activationPhoneFlow.clickLink("BRING YOUR PHONE");
		if (cellTech.equalsIgnoreCase("GSM")) {
			if (carrier.equalsIgnoreCase("T-Mobile")) {
				activationPhoneFlow.selectRadioButton("byop_radio2");
			} else if (carrier.equalsIgnoreCase("ATT")) {
				activationPhoneFlow.selectRadioButton("byop_radio3");
			} else {
				activationPhoneFlow.selectRadioButton("byop_radio1");
			}
			activationPhoneFlow.clickLink("FIND MY SIM CARD");
		} else {
			activationPhoneFlow.clickLink("CHECK CDMA COMPATIBILITY");
		}
		activationPhoneFlow.clickLink("SELECT");
		activationPhoneFlow.clickLink("SELECT");
	}
	
	public void selectHomePhone() throws Exception {
		activationPhoneFlow.clickLink("HOME SERVICE");
		activationPhoneFlow.clickLink("SELECT");
		activationPhoneFlow.clickLink("SELECT");
	}

	public void goToCartAndCheckoutAsCSR() throws Exception {
		String email = TwistUtils.createRandomEmail();
		activationPhoneFlow.clickLink("GO TO SECURE CHECKOUT");
		//activationPhoneFlow.typeInTextField("nickName", "TwistNickname");
		activationPhoneFlow.typeInTextField("firstName", "Twist FirstName");
		activationPhoneFlow.typeInTextField("lastName", "Twist LastName");
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Rd");
		activationPhoneFlow.selectCheckBox("pobox");
		activationPhoneFlow.typeInTextField("city", "MOUNTAIN VIEW");
		//activationPhoneFlow.chooseFromSelect("country", "United States");
		activationPhoneFlow.chooseFromSelect("state", "California");
		activationPhoneFlow.typeInTextField("city", "MOUNTAIN VIEW");
		activationPhoneFlow.clickLink("Change ZIP CODE");
		activationPhoneFlow.clickLink("Change ZIP");
		activationPhoneFlow.typeInTextField("zipCode", "94043");
		activationPhoneFlow.typeInTextField("phone1", "9876543210");
		activationPhoneFlow.typeInTextField("email1", email);
		activationPhoneFlow.selectCheckBox("on");
		activationPhoneFlow.getBrowser().textarea("giftMessageText").setValue("Twist Gift Message");
		activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx 3-Day - $4.95");
	}

	public void goToCartAndApplyCoupon(String couponCode) throws Exception {
		activationPhoneFlow.typeInTextField("promoCode", couponCode);
		activationPhoneFlow.clickLink("APPLY");
		
		if (couponCode.equalsIgnoreCase("HWPC-1")) {
			Assert.assertTrue(activationPhoneFlow.spanVisible("Promotion Details Buy anyHUAWEI Ascend Y package and get 5% off"));
			activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx 3-Day - $4.95");
		} else {
			Assert.assertTrue(activationPhoneFlow.spanVisible("Promotion Details Free shipping using FedEx Overnight for orders over $70.00"));
			activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx Overnight - $14.95 NOW FREE");
		}
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

}