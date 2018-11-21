package com.tracfone.twist.b2c;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
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
	private static final ResourceBundle props = ResourceBundle.getBundle("TotalWeb");

	public PhonePurchase() {
		
	}

	public void addToCart(String model) throws Exception {
		activationPhoneFlow.clickLink(model);
		TwistUtils.setDelay(8);
		activationPhoneFlow.clickLink("select_phone_bundles_btn_nozip");
		activationPhoneFlow.typeInTextField("zipPopup_zc", "33178");
		activationPhoneFlow.pressButton("Continue");
	}
	
	
	public void addPhoneAndPlanToCart(String servicePlan) throws Exception {
		
//		System.out.println(activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[0]").getText());
		
		if (activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[0]").isVisible()){
			activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[2]").click();
		}
		activationPhoneFlow.clickLink("ADD TO CART");
		TwistUtils.setDelay(2);
		selectServicePlan(servicePlan);
		activationPhoneFlow.clickLink("Continue");
		TwistUtils.setDelay(2);
		activationPhoneFlow.clickLink("CHECK OUT");

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
	
	
	public void goToCartAndProvideDetails(String card) throws Exception {
		String email = TwistUtils.createRandomEmail();
		String cardNum = TwistUtils.generateCreditCard(card);
		
		activationPhoneFlow.pressButton("Ok");
		TwistUtils.setDelay(2);
		
		System.out.println(email);
		activationPhoneFlow.typeInTextField("firstName", "Cyber");
		activationPhoneFlow.typeInTextField("lastName", "Source");
		activationPhoneFlow.typeInTextField("phone1", "9876543210");
		activationPhoneFlow.typeInTextField("email1", email);
		activationPhoneFlow.typeInTextField("company", email);
		activationPhoneFlow.typeInTextField("address1", "1295 charleston Road");
		activationPhoneFlow.typeInTextField("city", "Mountainview");
		activationPhoneFlow.chooseFromSelect("state", "California");
//		activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx 3-Day $4.95");
		
		if(card.equalsIgnoreCase("Master card")){
			activationPhoneFlow.chooseFromSelect("payMethodId_1","MasterCard Credit Card");
		}else if(card.equalsIgnoreCase("Visa")){
			activationPhoneFlow.chooseFromSelect("payMethodId_1","VISA Credit Card");
		}else if(card.equalsIgnoreCase("Discover")){
			activationPhoneFlow.chooseFromSelect("payMethodId_1","Discover Credit Card");
		}else if(card.equalsIgnoreCase("American Express")){
			activationPhoneFlow.chooseFromSelect("payMethodId_1","American Express Credit Card");
		}
		TwistUtils.setDelay(2);
		
		activationPhoneFlow.typeInTextField("account1_1", cardNum);
		activationPhoneFlow.typeInTextField("cc_cvc_1", "456");
		activationPhoneFlow.chooseFromSelect("expire_month_1", "07");
		activationPhoneFlow.chooseFromSelect("expire_year_1", "2021");
		
		activationPhoneFlow.clickLink("CONTINUE");		

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
	
	
public void reviewAndFinishOrder() throws Exception {
		
		TwistUtils.setDelay(8);
		activationPhoneFlow.pressSubmitButton("Submit");
		
		TwistUtils.setDelay(8);
		
		String orderId = activationPhoneFlow.getBrowser().heading2("panel-title title-panel-box number-order").getText();
		System.out.println(orderId); 
		orderId = orderId.substring(13,25);
		System.out.println(orderId);
		ESN esn = new ESN("Order Number", orderId);
        esnUtil.setCurrentESN(esn);

		Assert.assertTrue(activationPhoneFlow.getBrowser().heading2("small-top ").getText().contains("Thank you for your order"));
	
	}

	public void finishOrder() throws Exception {
		activationPhoneFlow.clickLink("CONTINUE TO ORDER SUMMARY");
		if (activationPhoneFlow.buttonVisible("Use suggested address")) {
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
	
	public void addPhoneAndPlanToCartWithCredit(String servicePlan, String creditScore) throws Exception {
		
		if(activationPhoneFlow.linkVisible("ADD TO CART")){
		
			if(creditScore.equalsIgnoreCase("Excellent")){
				if (activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[0]").isVisible()){
					activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[0]").click();
					activationPhoneFlow.clickLink("ADD TO CART");
				}else {
					throw new IllegalArgumentException("We don't have lease offer for selected phone.");
				}
			}
			else if(creditScore.equalsIgnoreCase("Average")){
				if (activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[1]").isVisible()){
					activationPhoneFlow.getBrowser().paragraph("top-push-4 text-black[1]").click();
					activationPhoneFlow.clickLink("ADD TO CART");
				}else {
					throw new IllegalArgumentException("We don't have lease offer for selected phone.");
				}	
			}
			else {
				throw new IllegalArgumentException("Please provide your credit score.");
			}	
		} else{
			throw new IllegalArgumentException("Slected Phone is out of stock.");
		}
		
		TwistUtils.setDelay(2);
		selectServicePlan(servicePlan);
		activationPhoneFlow.clickLink("Continue");
		TwistUtils.setDelay(2);
		activationPhoneFlow.clickLink("CHECK OUT");

	}
	
	public void goToCartAndProvideInformation() throws Exception {
		String email = TwistUtils.createRandomEmail();
		activationPhoneFlow.pressButton("Ok");
		TwistUtils.setDelay(2);
		
		System.out.println(email);
		activationPhoneFlow.typeInTextField("firstName", "Cyber");
		activationPhoneFlow.typeInTextField("lastName", "Source");
		activationPhoneFlow.typeInTextField("phone1", "9876543210");
		activationPhoneFlow.typeInTextField("email1", email);
		activationPhoneFlow.typeInTextField("company", email);
		activationPhoneFlow.typeInTextField("address1", "1295 charleston Road");
		activationPhoneFlow.typeInTextField("city", "Mountainview");
		activationPhoneFlow.chooseFromSelect("state", "California");
	//	activationPhoneFlow.chooseFromSelect("singleShipmentShippingMode", "FedEx 3-Day $4.95");
		activationPhoneFlow.clickLink("CONTINUE");		

	}
	
	public void applyForSmartpay(){
		TwistUtils.setDelay(2);
		activationPhoneFlow.clickLink("Apply online");
		TwistUtils.setDelay(2);
		Browser smartpay = activationPhoneFlow.getBrowser().domain("applysandbox.billfloat.com");
	
		TwistUtils.setDelay(3);
		smartpay.byId("mobile").setValue("9876543210"); 
		smartpay.select("birth_month").choose("May");
		smartpay.select("birth_day").choose("12");
		smartpay.select("birth_year").choose("1990");
		smartpay.byId("ssn").setValue("5778");
		smartpay.submit("form-submit-button[0]").click(); 
		TwistUtils.setDelay(3);
		
		//activationPhoneFlow.pressSubmitButton("Continue");
		
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
	
	public void selectServicePlan(String servicePlan){
		
		if(servicePlan.equalsIgnoreCase("$25")){
	           activationPhoneFlow.getBrowser().span("choose_plan_lto_price[0]").click();
        }
        else if(servicePlan.equalsIgnoreCase("$35")){
        	activationPhoneFlow.getBrowser().span("choose_plan_lto_price[1]").click();
        }
        else if(servicePlan.equalsIgnoreCase("$60")){
        	activationPhoneFlow.getBrowser().span("choose_plan_lto_price[2]").click();
        }
        else if(servicePlan.equalsIgnoreCase("$85")){
        	activationPhoneFlow.getBrowser().span("choose_plan_lto_price[3]").click();
        	System.out.println("ABCD"+activationPhoneFlow.getBrowser().span("choose_plan_lto_price[3]").getText());
        }
        else if(servicePlan.equalsIgnoreCase("$100")){
        	activationPhoneFlow.getBrowser().span("choose_plan_lto_price[4]").click();
        }
        else if(servicePlan.equalsIgnoreCase("NONE")){
        	activationPhoneFlow.getBrowser().span("choose_plan_lto_price[5]").click();
        }
        else {
        	throw new IllegalArgumentException("Selected service plan is not available.");
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