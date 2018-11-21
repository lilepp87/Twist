package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.impl.eng.activation.Plan;
import com.tracfone.twist.purchases.AbstractEnterPaymentInformation;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public abstract class AbstractFinishActivationWithServicePlan {

	private final Properties properties;
	private final String ANDROID;
	private final String MEGACARD;
	private final String STATUS_PAST_DUE;
	private final String ERR_NOT_MEGACARD = "Android phones can only use Megacard"; //$NON-NLS-1$
	private AbstractEnterPaymentInformation enterPaymentInfo;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private planUtil planUtil;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;

	public AbstractFinishActivationWithServicePlan(String propsName) {
		planUtil = new planUtil();
		properties = new Properties(propsName);
		ANDROID = properties.getString("Twist.Android");
		MEGACARD = properties.getString("Twist.Megacard");
		STATUS_PAST_DUE = properties.getString("Twist.PastDueStatus");
	}

	public void selectINeedAnAirtimeCard() throws Exception {
		//Assert.assertTrue(activationPhoneFlow.radioVisible(properties.getString("Activation.BuyPinActivateRadio")));
		//activationPhoneFlow.selectRadioButton(properties.getString("Activation.BuyPinActivateRadio"));
		activationPhoneFlow.getBrowser().submit(properties.getString("Activation.BuyAPlanNow")).click();
		if(activationPhoneFlow.divVisible("error_msg_id")){
			if(activationPhoneFlow.getBrowser().div("error_msg_id").text().contains("Unfortunately, your local calling area does not provide data services.Therefore, you will only be able to use your NET10 phone to make/receive calls and send/receive text messages. Please click on the 'Continue' button to continue the Activation process.")){
			activationPhoneFlow.clickLink(properties.getString("Activation.ContinueLink"));
			activationPhoneFlow.selectRadioButton(properties.getString("Activation.BuyPinActivateRadio"));
			activationPhoneFlow.pressSubmitButton(properties.getString("Activation.Continue2Submit"));
			}
		}
//	    if(activationPhoneFlow.tableHeaderVisible("span-10 prepend-top")){
//	    	activationPhoneFlow.pressSubmitButton("CONTINUE");
//	    }
	}

	public void enrollInRecurringPlan(String planStr, String phoneType) throws Exception {
		esnUtil.getCurrentESN().setRedeemType(true);
		esnUtil.getCurrentESN().setTransType("Net10 Activation with Enroll");
		planUtil.clearPlanFound();
		Plan plan = Plan.getPlan(properties, planStr, phoneType);
//		TwistUtils.setDelay(30);
		if (activationPhoneFlow.checkboxVisible(plan.getEnrollCheck(properties))) {
			activationPhoneFlow.selectRadioButton(plan.getRadioLabel(properties));
			activationPhoneFlow.selectCheckBox(plan.getEnrollCheck(properties));
			planUtil.setPlanFound(true);
		} else {
			planUtil.setPlanFound(false);
		}
	}

	public void selectPlan(String planStr, String phoneType) throws Exception {
		Plan plan = Plan.getPlan(properties, planStr, phoneType);
		// TODO Change to h2 once the site is fixed
		// Site is currently using bold instead of h2 for some titles of plans
		boolean titleVisible = activationPhoneFlow.strongVisible(plan.getPlanTitle(properties))
				|| activationPhoneFlow.h2Visible(plan.getPlanTitle(properties))
				|| activationPhoneFlow.divVisible(plan.getPlanTitle(properties));
		titleVisible = true;
		TwistUtils.setDelay(45);
		if (titleVisible) {
			activationPhoneFlow.selectRadioButton(plan.getRadioLabel(properties));
			activationPhoneFlow.pressSubmitButton(properties.getString("Activation.SelectServiceButton")); //$NON-NLS-1$
			planUtil.setPlanFound(true);
		} else {
			planUtil.setPlanFound(false);
		}
		if (esnUtil.getCurrentESN().getRedeemType().isEmpty()) {
			esnUtil.getCurrentESN().setRedeemType(false);
			esnUtil.getCurrentESN().setTransType("Net10 Activation with Purchase");
		}
	}

	public void checkResultForPhoneWithStatusAndPlan(String cellTech, String phoneType, String status, String planType)
			throws Exception {
		if (ANDROID.equalsIgnoreCase(phoneType) && !MEGACARD.equalsIgnoreCase(planType)) {
			boolean pinRedeemPassed = activationPhoneFlow.linkVisible(properties.getString("Activation.FinishButton")); //$NON-NLS-1$
			Assert.assertFalse(ERR_NOT_MEGACARD, pinRedeemPassed);
			Assert.assertFalse(planUtil.wasPlanFound());
		} else {
			activationPhoneFlow.pressSubmitButton(properties.getString("Payment.ContinueSubmit")); //$NON-NLS-1$
			TwistUtils.setDelay(45);
			if (activationPhoneFlow.tableHeaderVisible("span-10 prepend-top")) {
				activationPhoneFlow.pressSubmitButton("CONTINUE");
			}
			activationPhoneFlow.clickLink(properties.getString("Activation.FinishButton")); //$NON-NLS-1$
			Assert.assertTrue(planUtil.wasPlanFound());
			esnUtil.addRecentActiveEsn(esnUtil.getCurrentESN(), cellTech, status, "Activate Net10 with purchase");
//			phoneUtil.checkRedemption(esnUtil.getCurrentESN());
		}
		esnUtil.getCurrentESN().setDeviceType(phoneType);
	}

	public void enterPaymentInformationWithZipCodeCardTypeAndStatus(String zipCode, String cardType, String status)
			throws Exception {
		boolean planFound = planUtil.wasPlanFound();
		esnUtil.getCurrentESN().setBuyFlag(true);
		esnUtil.getCurrentESN().setActionType(6);
		if (activationPhoneFlow.selectionboxVisible("paymentSource") && planFound) {
			activationPhoneFlow.typeInTextField(properties.getString("Payment.CvvText"), //$NON-NLS-1
					properties.getString("Payment.DefaultCvv")); //$NON-NLS-1$
			activationPhoneFlow.selectCheckBox("terms");
		} else if (planFound) {
			enterPaymentInfo.enterPaymentWithCardType(cardType);
		}
	}

	public void checkForDiscount(String disAmount) throws Exception {
		String charges = activationPhoneFlow.getBrowser().div("info_container charge_breakdown clear").getText();
		boolean discountVisible = charges.contains("Discount Amount-" + disAmount)
				|| charges.contains("Discount Amount- " + disAmount);
		Assert.assertTrue(discountVisible);
	}

	public void addMembersPartSIMAndZipCode(int familySize, String partNumber, String simPart, String zip) throws Exception {
		activationPhoneFlow.typeInTextField("input_name_1", "TwistFamilyPhoneNickname1");
		ESN mainEsn = esnUtil.getCurrentESN();

		for (int i = 2; i <= familySize; i++) {
			if (i > 2) {
				activationPhoneFlow.pressButton("ADD LINE");
			}
			String esnStr = phoneUtil.getNewEsnByPartNumber(partNumber);
			ESN esn = new ESN(partNumber, esnStr);
			
			if (!simPart.isEmpty()) {
				String sim = simUtil.getNewSimCardByPartNumber(simPart);
				phoneUtil.addSimToEsn(sim, esn);
			}
			mainEsn.addFamilyEsn(esn);
			activationPhoneFlow.typeInTextField("input_min_esn_" + i, esnStr);
			activationPhoneFlow.typeInTextField("input_name_" + i, "TwistFamilyPhoneNickname" + i);
			if (activationPhoneFlow.submitButtonVisible("Accept")) {
				activationPhoneFlow.pressSubmitButton("Accept");
			}
		}
		activationPhoneFlow.pressSubmitButton("CONTINUE");

		for (int i = 2; i <= familySize; i++) {
			activationPhoneFlow.typeInTextField("input_zip_" + i, zip);
		}
		activationPhoneFlow.pressSubmitButton("CONTINUE");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setEnterPaymentInfo(AbstractEnterPaymentInformation enterPaymentInfo) {
		this.enterPaymentInfo = enterPaymentInfo;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	private class planUtil {
		private Boolean planFound = null;

		public void clearPlanFound() {
			planFound = null;
		}

		public boolean wasPlanFound() {
			if (planFound == null) {
				throw new IllegalArgumentException("Plan found status not available"); //$NON-NLS-1$
			}
			return planFound.booleanValue();
		}

		public void setPlanFound(boolean wasPlanFound) {
			if (planFound == null) {
				planFound = Boolean.valueOf(wasPlanFound);
			} else if (planFound.booleanValue() == wasPlanFound) {
				// Do nothing
			} else {
				throw new RuntimeException("\'Plan found\' status set more than once"); //$NON-NLS-1$
			}
		}
	}

	public void goToMyAccount()throws Exception{
		activationPhoneFlow.getBrowser().image("No Thanks").click();
		activationPhoneFlow.clickLink("My Account/Register");
		TwistUtils.setDelay(20);
		activationPhoneFlow.clickLink("Payment Information");
		activationPhoneFlow.clickLink("Manage Payment Information");
	}
	
	public void addNewCreditCard(String cardType) throws Exception{
		Assert.assertTrue(activationPhoneFlow.h1Visible("VIEW/CHANGE PAYMENT INFORMATION"));
		activationPhoneFlow.pressButton("ADD A NEW CREDIT CARD");
		String card = TwistUtils.generateCreditCard(cardType);
		activationPhoneFlow.typeInTextField("account_number", card);
		activationPhoneFlow.chooseFromSelect("credit_expiry_mo", "07");
		activationPhoneFlow.chooseFromSelect("credit_expiry_yr", "2021");
		activationPhoneFlow.typeInTextField("fname", "testfirst");
		activationPhoneFlow.typeInTextField("lname", "testlast");
		activationPhoneFlow.typeInTextField("address1", "1295 Charlestn Rd");
		activationPhoneFlow.typeInTextField("city", "Mountain View");
		activationPhoneFlow.typeInTextField("zip", "94043");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.chooseFromSelect("country_list", "USA");	
		activationPhoneFlow.pressSubmitButton("save_btn");
	}

	public void verifyPopUp() throws Exception{
		TwistUtils.setDelay(10);
		Assert.assertTrue(activationPhoneFlow.divVisible("Your Payment Method has successfully been added to your account."));
		activationPhoneFlow.pressButton("Cancel");
		
	}

	public void removeCCNotAssociatedWithAutoRefill() throws Exception{
		String value = esnUtil.getCurrentESN().getLastFourDigits();
	      
		if(activationPhoneFlow.divVisible("cc-last4[1]")){
			activationPhoneFlow.clickDivMessage("cc-last4[1]");	
			activationPhoneFlow.pressButton("REMOVE");//remove_btn
		}
		Assert.assertTrue(activationPhoneFlow.divVisible("success_msg"));
		activationPhoneFlow.clickLink("Sign Out");
		
	}

	public void removeCCAssociatedWithAutoRefill() throws Exception {
		TwistUtils.setDelay(10);
		String value = esnUtil.getCurrentESN().getLastFourDigits();
	      
		if(activationPhoneFlow.divVisible("cc-last4[0]")){
			activationPhoneFlow.clickDivMessage("cc-last4[0]");	
			activationPhoneFlow.pressButton("REMOVE");
		}
		if(activationPhoneFlow.divVisible("ADD A NEW CREDIT CARD")){
			activationPhoneFlow.getBrowser().div("ADD A NEW CREDIT CARD").click();
		}
	}

	public void selectPlanFor(String plan, String deviceType, String purchaseType) throws Exception {
		String planToSelect = null;
		String planType = null;
		if(deviceType.equalsIgnoreCase("Android") || deviceType.equalsIgnoreCase("Non-Android")){
			/*if(plan.equalsIgnoreCase("Unlimited $75")){
				planToSelect = "/.*?NTAPP6U075_en\\.png/";
				planType = "MONTHLY PLANS";
			}else*/ if(plan.equalsIgnoreCase("Unlimited International Monthly")){
				planToSelect = "/.*?NTAPPU0065ILD_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited $60")){
				planToSelect = "/.*?NTAPP6U060_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited Monthly")){
				planToSelect = "/.*?NTAPPU0001_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited $40")){
				planToSelect = "/.*?NTAPP6U040_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited $35")){
				planToSelect = "/.*?NTAPP6U035_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited $80 3 Point Upgrade Plan")){
				planToSelect = "/.*?NTAPP60080ILDUP_en\\.png/";
				planType = "Phone Upgrade Plans";
			}else if(plan.equalsIgnoreCase("Unlimited $70 1.5 Point Upgrade Plan")){
				planToSelect = "/.*?NTAPP60070ILDUP_en\\.png/";
				planType = "Phone Upgrade Plans";
			}else if(plan.equalsIgnoreCase("Unlimited $60 1 Point Upgrade Plan")){
				planToSelect = "/.*?NTAPP60060ILDUP_en\\.png/";
				planType = "Phone Upgrade Plans";
			}else if(plan.equalsIgnoreCase("1500 Minutes Nondata")){
				planToSelect = "/.*?NTAPP21500_en\\.png/";
				planType = "Pay-As-You-Go-Plans";
			}else if(plan.equalsIgnoreCase("900 Minutes Nondata")){
				planToSelect = "/.*?NTAPP30900_en\\.png/";
				planType = "Pay-As-You-Go-Plans";
			}else if(plan.equalsIgnoreCase("600 Minutes Nondata")){
				planToSelect = "/.*?NTAPP30600_en\\.png/";
				planType = "Pay-As-You-Go-Plans";
			}else if(plan.equalsIgnoreCase("300 Minutes Nondata")){
				planToSelect = "/.*?NTAPP20300_en\\.png/";
				planType = "Pay-As-You-Go-Plans";
			}else if(plan.equalsIgnoreCase("750 Minutes Nondata")){
				planToSelect = "/.*?NTAPP30750_en\\.png/";
				planType = "Pay-As-You-Go-Plans";
			}else if(plan.equalsIgnoreCase("200 Minutes Nondata")){
				planToSelect = "/.*?NTAPP20200_en\\.png/";
				planType = "PAY-AS-YOU-GO AUTO-REFILL PLANS";
			}else if(plan.equalsIgnoreCase("500 Minutes Monthly Nondata")){
				planToSelect = "/.*?5800662_en\\.png/";
				planType = "PAY-AS-YOU-GO AUTO-REFILL PLANS";
			}else if(plan.equalsIgnoreCase("750 Minutes Monthly Nondata")){
				planToSelect = "/.*?NTAPP30750_en\\.png/";
				planType = "PAY-AS-YOU-GO AUTO-REFILL PLANS";
			}else if(plan.equalsIgnoreCase("200 Minutes Monthly Nondata")){
				planToSelect = "/.*?5800661_en\\.png/";
				planType = "PAY-AS-YOU-GO AUTO-REFILL PLANS";
			}
			
		}

		if(deviceType.equalsIgnoreCase("HomePhone")){
			if(plan.equalsIgnoreCase("Home Unlimited International")){
				planToSelect = "/.*?NTAPPHP6U001ILD_en\\.png/";
			}else if(plan.equalsIgnoreCase("Home Unlimited")){
				planToSelect = "/.*?NTAPPHP6U001_en\\.png/";
			}
		}
		
		if(deviceType.equalsIgnoreCase("HotSpot")){
			if (plan.equalsIgnoreCase("5GB Data")) {
				planToSelect = "/.*?NTAPPMB6050_en\\.png/";
			} else if (plan.equalsIgnoreCase("2.5GB Data")) {
				planToSelect = "/.*?NTAPPMB6030_en\\.png/";
			} else if (plan.equalsIgnoreCase("1GB Data")) {
				planToSelect = "/.*?NTAPPMB6020_en\\.png/";
			} else if (plan.equalsIgnoreCase("500MB Data")) {
				planToSelect = "/.*?NTAPPMB6010_en\\.png/";
			}
		}
		
		planUtil.setPlanFound(true);
		
		activationPhoneFlow.clickImage(planToSelect);
		
		if( purchaseType.equalsIgnoreCase("One-Time")){
			if (activationPhoneFlow.submitButtonVisible("ONE-TIME PURCHASE")){
				activationPhoneFlow.pressSubmitButton("ONE-TIME PURCHASE");
			} if (activationPhoneFlow.submitButtonVisible("Buy")){ //for pay as you go plans
				activationPhoneFlow.pressSubmitButton("Buy");
			}
			else if(activationPhoneFlow.submitButtonVisible("ONE-TIME PURCHASE[1]")){
				activationPhoneFlow.pressSubmitButton("ONE-TIME PURCHASE[1]"); //for port flow
			}
		}else{
			if (activationPhoneFlow.submitButtonVisible("BUY PLAN WITH AUTO-REFILL")){
				activationPhoneFlow.pressSubmitButton("BUY PLAN WITH AUTO-REFILL");
			}
		}
		
		
		if (esnUtil.getCurrentESN().getRedeemType().isEmpty()) {
			esnUtil.getCurrentESN().setRedeemType(false);
			esnUtil.getCurrentESN().setTransType("Net10 Activation with Purchase");
		}
	}


}