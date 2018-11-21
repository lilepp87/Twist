package com.tracfone.twist.purchases;

/*
 * Created by: Hannia Leiva 10/12/2011
 * Test Case ID:
 * Buy Airtime - Monthly Plans
 * Phone Model: TFLG800
 * ESN Status:50
 */

import org.junit.Assert;
import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAirtime {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;

	private Properties properties = new Properties();
	
	public BuyAirtime() {

	}

	public void goToMonthlyPlans() throws Exception {
		TwistUtils.setDelay(10);
		Assert.assertTrue(paymentFlow.divVisible("Phone verified successfully"));
//		paymentFlow.clickLink(properties.getString("enrollMonthlyPlan"));
		paymentFlow.clickLink(properties.getString("enrollValuePlan"));
//		paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeTracfoneWebFields.GoMontlyPlanButton.name);
	}

	public void goToPayAsYouGo() throws Exception {
		paymentFlow.navigateTo(properties.getString("TF_URL"));
		paymentFlow.clickLink(properties.getString("buyOnline"));
//		paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeTracfoneWebFields.GoButton.name);
	}

	public void selectCard(String card) throws Exception {
		try {
			// Close possible alert message but ignore if it does not appear
			paymentFlow.clickDivMessage(properties.getString("close"));
		} catch (Exception ex) {}
		String linkId = getLinkIdFromCard(card);
		//paymentFlow.clickLink(linkId);
		paymentFlow.clickDivMessage(linkId);
		if(paymentFlow.buttonVisible("ADD PLAN(S) TO CART[1]")) {
			paymentFlow.pressButton("ADD PLAN(S) TO CART[1]");
		} else if(paymentFlow.buttonVisible("ADD PLAN(S) TO CART[2]")) {
			paymentFlow.pressButton("ADD PLAN(S) TO CART[2]");
		}else {
			paymentFlow.pressButton("ADD PLAN(S) TO CART");
		}
	}

	/* CLick Auto Refill checkbox option - 0310 release */
	public void selectAutoRefill(String card) throws Exception {
		try {
			// Close possible alert message but ignore if it does not appear
			paymentFlow.clickDivMessage(properties.getString("close"));
		} catch (Exception ex) {}
		String linkId = getLinkIdFromCard(card);
		//paymentFlow.clickLink(linkId);
		//paymentFlow.clickSpanMessage(linkId);
		String AutoRefillID = getAutoRefillIDFromCard(card);
		paymentFlow.selectCheckBox(AutoRefillID);
		if(paymentFlow.buttonVisible("ADD PLAN(S) TO CART[1]")) {
			paymentFlow.pressButton("ADD PLAN(S) TO CART[1]");
		} else if(paymentFlow.buttonVisible("ADD PLAN(S) TO CART[2]")) {
			paymentFlow.pressButton("ADD PLAN(S) TO CART[2]");
		}else {
			paymentFlow.pressButton("ADD PLAN(S) TO CART");
		}
		ESN activeEsn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(activeEsn.getEsn());
		System.out.println("ESN: " + activeEsn);
		paymentFlow.typeInTextField(PaymentFlow.BuyAirtimeTracfoneWebFields.EsnText.name, activeEsn.getEsn());		
		if(paymentFlow.buttonVisible("Continue to Checkout")) {
			paymentFlow.pressButton("Continue to Checkout");
		}else if(paymentFlow.submitButtonVisible("Continue to Checkout")) {
			paymentFlow.pressSubmitButton("Continue to Checkout");
		}else if(paymentFlow.buttonVisible("CONTINUE TO  CHECKOUT")) {
			paymentFlow.pressButton("CONTINUE TO  CHECKOUT");
		}else {
			paymentFlow.pressSubmitButton("CONTINUE TO  CHECKOUT");
		}		
	}

	
	private String getAutoRefillIDFromCard(String card) {
		if ("450 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox";
		} else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[1]";
		} else if ("120 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[2]";
		} else if ("60 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[3]";
		} else if ("30 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[4]";
		}else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[5]";
		} else if ("500 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[6]";
		} else if ("750 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[7]";
		} else if ("1500 Minutes".equalsIgnoreCase(card)) {
			return "autorefillchkbox[8]";
		}
		throw new IllegalArgumentException("Could not find Auto refill plan: " + card +	" on page");
	}

	private String getLinkIdFromCard(String card) {
		//String result = properties.getString("add");
		if ("1500 Minutes One Year".equalsIgnoreCase(card)) {
			return "+";
		} else if ("1000 Minutes One Year".equalsIgnoreCase(card)) {
			return "+[1]";
//		} else if ("800 Minutes One Year".equalsIgnoreCase(card)) {
//			return result+"[2]";
//		} else if ("400 Minutes One Year".equalsIgnoreCase(card)) {
//			return "+[2]";
		} else if ("400 Minutes".equalsIgnoreCase(card)) {
			return "+[2]";
//		} else if ("Double Minutes".equalsIgnoreCase(card)) {
//			return result+"[4]";
		} else if ("450 Minutes".equalsIgnoreCase(card)) {
			return "+[3]";
		} else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "+[4]";
		} else if ("120 Minutes".equalsIgnoreCase(card)) {
			return "+[5]";
		} else if ("60 Minutes".equalsIgnoreCase(card)) {
			return "+[6]";
		} else if ("30 Minutes".equalsIgnoreCase(card)) {
			return "+[7]";
//		} else if ("3000 Minutes".equalsIgnoreCase(card)) {
		} /*else if ("90 Minutes".equalsIgnoreCase(card)) {
			return "+[6]";
//		} else if ("2000 Minutes".equalsIgnoreCase(card)) {
		} */else if ("200 Minutes".equalsIgnoreCase(card)) {
			return "+[8]";
//		} else if ("1000 Minutes".equalsIgnoreCase(card)) {
		} else if ("300 Minutes".equalsIgnoreCase(card)) {
			return "+[9]";
//		} else if ("500 Minutes".equalsIgnoreCase(card)) {
		} else if ("500 Minutes".equalsIgnoreCase(card)) {
			return "+[10]";
		} else if ("750 Minutes".equalsIgnoreCase(card)) {
			return "+[11]";	
		} else if ("1500 Minutes".equalsIgnoreCase(card)) {
			return "+[12]";
		}else {
			throw new IllegalArgumentException("Could not find plan: " + card +	" on page");
		}
	}
	public void enterEsnAndPromo(String partNumber, String promo)
			throws Exception {
	
		ESN activeEsn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(activeEsn.getEsn());
		System.out.println("ESN: " + activeEsn);
//		esnUtil.popRecentActiveEsn(partNumber);
//		esnUtil.setCurrentESN(activeEsn);

		paymentFlow.typeInTextField(PaymentFlow.BuyAirtimeTracfoneWebFields.EsnText.name, activeEsn.getEsn());
		
		if(paymentFlow.buttonVisible("Continue to Checkout")) {
			paymentFlow.pressButton("Continue to Checkout");
		}else if(paymentFlow.submitButtonVisible("Continue to Checkout")) {
			paymentFlow.pressSubmitButton("Continue to Checkout");
		}else if(paymentFlow.buttonVisible("CONTINUE TO  CHECKOUT")) {
			paymentFlow.pressButton("CONTINUE TO  CHECKOUT");
		}else {
			paymentFlow.pressSubmitButton("CONTINUE TO  CHECKOUT");
		}	
		TwistUtils.setDelay(9);
		paymentFlow.h3Visible("$49.99 ONLINE ONLY");
		if (promo.equalsIgnoreCase("True")) {
			paymentFlow.pressSubmitButton(properties.getString("add"));
		} else {
			paymentFlow.pressSubmitButton(properties.getString("skip"));
		}
		
//		paymentFlow.h3Visible("International Long Distance Global Card");
//		if (numIld > 0) {
//			paymentFlow.typeInTextField("ild_quantity", numIld.toString());
//			paymentFlow.pressSubmitButton("default_ildadd_btn");
//		} else {
//			paymentFlow.pressSubmitButton("default_ILDsubmit_btn");
//		}
	} 

	public void selectThePlan(String monthlyPlan) throws Exception {
		String option;

		if ("50 Minutes Plan".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.Link50MinutesPlan.name;
		} else if ("125 Minutes Plan".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.Link125MinutesPlan.name;
		} else if ("200 Minutes Plan".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.Link200MinutesPlan.name;
		} else if ("50 Minutes Family".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.Link50MinutesFamilyPlan.name;
		} else if ("Additional Phones".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.AdditionalPhonesPlanLink.name;
		} else if ("Service Protection".equalsIgnoreCase(monthlyPlan)) {
			option = PaymentFlow.BuyAirtimeTracfoneWebFields.ServiceProtectionPlanLink.name;
		} else {
			throw new IllegalArgumentException("Could not find plan: " + monthlyPlan);
		}
		
		option = option + esnUtil.getCurrentESN().getEsn();

		paymentFlow.selectRadioButton(option);
		if(paymentFlow.submitButtonVisible(properties.getString("continue3")))
		     paymentFlow.pressSubmitButton(properties.getString("continue3"));
		else
		     paymentFlow.pressButton(properties.getString("continue3"));
	}

	public void enterZipCodeAndSimCard(String zipCode, String simCard) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			paymentFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.simCardText.name, newSim);
		}

		paymentFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.ZipCodeText.name, zipCode);
		paymentFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.NextButton.name);
	}

	public void verifyPurchaseOfPlan() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("thankYouForYouPurchase")));
		//paymentFlow.pressSubmitButton(properties.getString("continue2"));
		if (paymentFlow.submitButtonVisible(properties.getString("continue"))){
			paymentFlow.pressSubmitButton(properties.getString("continue"));
		}else{
			paymentFlow.pressSubmitButton(properties.getString("continue2"));
		}
		
		ESN esn = esnUtil.getCurrentESN();
		storeRedeemDataForPlan(esn);
		phoneUtil.checkRedemption(esn);
	}

	public void verifyPurchaseOfAirtime() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("thankYouBuyingAirtime")));
//		Assert.assertTrue(paymentFlow.tableHeaderVisible("/.*911.*/"));
		if (paymentFlow.h1Visible("Transaction Summary")) {
			//paymentFlow.pressSubmitButton(properties.getString("continue2"));
		} else{
			Assert.assertTrue(paymentFlow.h2Visible(PaymentFlow.BuyAirtimeTracfoneWebFields.BuyAirtimeMessage.name));
			//paymentFlow.pressSubmitButton(properties.getString("continue2"));
		}
		if (paymentFlow.submitButtonVisible(properties.getString("continue"))){
			paymentFlow.pressSubmitButton(properties.getString("continue"));
		}else{
			paymentFlow.pressSubmitButton(properties.getString("continue2"));
		}

		ESN esn = esnUtil.getCurrentESN();
		storeRedeemDataForPAYG(esn);
		phoneUtil.checkRedemption(esn);
		
	}
	
	/* Verification of auto refill 0310 release*/
	public void verifyPurchaseOfAutoRefillAirtime() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(paymentFlow.h2Visible(properties.getString("thankYouForYouPurchase"))); 
//		Assert.assertTrue(paymentFlow.tableHeaderVisible("/.*911.*/"));
//		paymentFlow.h3Visible("$10 Global Card");
//		if (numILD > 0) {
//			paymentFlow.typeInTextField("ild_quantity", numIld.toString());
//			paymentFlow.pressSubmitButton("default_ildadd_btn");
//		} else {
//			paymentFlow.pressSubmitButton("default_ILDsubmit_btn");
//		}
		//paymentFlow.pressSubmitButton(properties.getString("continue2"));
		if (paymentFlow.submitButtonVisible(properties.getString("continue"))){
			paymentFlow.pressSubmitButton(properties.getString("continue"));
		}else{
			paymentFlow.pressSubmitButton(properties.getString("continue2"));
		}
		ESN esn = esnUtil.getCurrentESN();
		storeRedeemDataForPAYG(esn);
		phoneUtil.checkRedemption(esn);
		String partinststatus  =phoneUtil.checkpartinststatus(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " Part inst status is " + partinststatus);
		Assert.assertFalse(partinststatus.equalsIgnoreCase("50"));
		/*String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));*/
	}
	
	private void storeRedeemDataForPAYG(ESN esn) {
		esn.setBuyFlag(true);
		esn.setActionType(6);
		esn.setRedeemType(false);
		esn.setTransType("Tracfone Purchase for PAYGO");
		phoneUtil.clearOTAforEsn(esn.getEsn());
	}
	
	private void storeRedeemDataForPlan(ESN esn) {
		esn.setBuyFlag(true);
		esn.setActionType(6);
		esn.setRedeemType(true);
		esn.setTransType("Tracfone Purchase with Monthly Plan");
		phoneUtil.clearOTAforEsn(esn.getEsn());
	}
	
	public void enterActiveESNAndNickname(String partNumber) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.clearOTAforEsn(esn);

		paymentFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EsnText.name, esn);
		paymentFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.NickNameText.name, "Twist Nickname");
	}

	public void enrollInFamilyPlan() throws Exception {
		paymentFlow.clickLink(properties.getString("enrollInValuePlans"));
		selectThePlan("50 Minutes Family");

		paymentFlow.typeInTextField("cvvnumber", "123");
		paymentFlow.selectCheckBox("terms"); // Check Terms and Conditions.
		paymentFlow.pressSubmitButton(properties.getString("continue"));
		
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
		verifyPurchaseOfPlan();
	}

	public void goToMonthlyValuePlans() throws Exception {
		paymentFlow.clickLink("Monthly Value Plans");
	}

	public void selectTheFamilyPlan() throws Exception {
		String option;
		option = PaymentFlow.BuyAirtimeTracfoneWebFields.FamilyPlanLink.name;
		option = option + esnUtil.getCurrentESN().getEsn();
		paymentFlow.selectRadioButton(option);
		if(paymentFlow.submitButtonVisible(properties.getString("continue3")))
		     paymentFlow.pressSubmitButton(properties.getString("continue3"));
		else
		     paymentFlow.pressButton(properties.getString("continue3"));
		/*paymentFlow.clickLink("Select[3]");
		paymentFlow.pressSubmitButton("Enroll");*/
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
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

	public void buy10DollarIldCard() throws Exception {
		paymentFlow.navigateTo(properties.getString("TF_URL") + "direct/controller.block?__blockname=" +
				"direct.airtime.dollar10.ild_plan_landing&tfdollar10ild=true&collecttfmin=true&dollar10ILDPurchase" +
				"=DOLLAR10_ILD_PURCHASE_ONLY&language=en");
	
	}
	public void enterPhoneNumber() throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		System.out.println(min);
		paymentFlow.typeInTextField("ild_min", min);
		paymentFlow.typeInTextField("ild_quantity", "1");
		paymentFlow.pressButton("default_ildadd_btn");
								       
	}
	
	public void enterPhoneNumberAndSelectPurchaseType(String purchaseType)  //New
			throws Exception {
 		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		System.out.println(min);
		System.out.println(min);
		paymentFlow.typeInTextField("ild_min", min);
		paymentFlow.typeInTextField("ild_quantity", "1");
		if(purchaseType.equalsIgnoreCase("Enroll in Auto Refill")){
			pressButton("default_enroll_btn");
		}
		else if(purchaseType.equalsIgnoreCase("One Time Purchase")){
			pressButton("default_ildadd_btn");
		}
	
	}
	
	private void pressButton(String buttonType) {
		if (paymentFlow.submitButtonVisible(buttonType)) {
			paymentFlow.pressSubmitButton(buttonType);
		} else {
			paymentFlow.pressButton(buttonType);
		}
	}

	public void addILD() throws Exception {
	
	}
	
	public void goToAutoRefillPlansAndEnterMin() throws Exception {
		paymentFlow.clickLink("Auto-Refill Plans");
		String esn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esn);
		paymentFlow.typeInTextField("eligibitlity_esn", min);
		paymentFlow.pressSubmitButton("SEE AVAILABLE PLANS");
		
	}

	public void enrollIntoAutoRefill(String Plan) throws Exception {
		String Option;
		if ("200 Minutes Only Smartphone".equalsIgnoreCase(Plan)) {
			Option="[1]";
		} else if ("500 Minutes".equalsIgnoreCase(Plan)) {
			Option="[2]";
		} else if ("750 Minutes".equalsIgnoreCase(Plan)) {
			Option="[3]";
		} else if ("1500 Minutes".equalsIgnoreCase(Plan)) {
			Option="[4]";
		} else if ("450 Minutes".equalsIgnoreCase(Plan)) {
			Option="[5]";
		} else if ("200 Minutes".equalsIgnoreCase(Plan)) {
			Option="[6]";
		} else if ("120 Minutes".equalsIgnoreCase(Plan)) {
			Option="[7]";
		} else if ("60 Minutes".equalsIgnoreCase(Plan)) {
			Option="[8]";
		} else if ("30 Minutes".equalsIgnoreCase(Plan)) {
			Option="[9]";
		}else {
		throw new IllegalArgumentException("Could not find plan: " + Plan +	" on page");
		}
	
		Option= "ENROLL NOW" + Option;
	
		paymentFlow.pressSubmitButton(Option);
	}

}