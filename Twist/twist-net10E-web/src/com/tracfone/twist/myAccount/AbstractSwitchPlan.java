package com.tracfone.twist.myAccount;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.impl.eng.activation.Plan;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class AbstractSwitchPlan {

	private final Properties properties;
	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String REOCCURING = "Reoccuring";

	protected AbstractSwitchPlan(String propsName) {
		properties = new Properties(propsName);
	}

	public void verifyPhoneNumber() throws Exception {
		String esnStr = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(esnStr);
		String Devicetype =phoneUtil.getDevicetype(esnStr);
		if (!Devicetype.equalsIgnoreCase("MOBILE_BROADBAND")){
		myAccountFlow.clickLink(properties.getString("verifyPhone"));
		myAccountFlow.typeInTextField("verify_min1", min);
		myAccountFlow.pressSubmitButton(properties.getString("verifyPhone"));
		}
		ESN esn = esnUtil.getCurrentESN();
		esn.setMin(min);
		
	}

	public void switchPlanAndMakePaymentOr(String toPlan, String addILD, String action)
			throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		phoneUtil.setDateInFuture(esn);
		myAccountFlow.clickLink(properties.getString("manageAutoRefillEnrollment"));
		if (action.equalsIgnoreCase("cancel enroll")) {
			cancelEnroll();
//		} else if (action.equalsIgnoreCase("apply now")) {
//			applyNow();
		} else {
			switchPlan(toPlan, addILD, "Now");
		}
	}

	private void switchPlan(String toPlan, String addILD, String addNow) throws Exception {
		String deviceType = esnUtil.getCurrentESN().getDeviceType();
		myAccountFlow.clickLink(properties.getString("switchPlan"));
		myAccountFlow.pressButton("submit_switch_enroll_now");
		// have to select the plan to switch(currently there is only one
		// plan other than the current plan)
		/*myAccountFlow.pressButton("service_plans_id_selected");
		myAccountFlow.selectRadioButton("service_plans_id_selected");
		myAccountFlow.selectCheckBox("isRecurringCheckBox");
		myAccountFlow.pressSubmitButton("service_plan_button_selection");*/
		
		selectPlanFor(toPlan, deviceType);
		myAccountFlow.pressSubmitButton("BUY PLAN WITH AUTO-REFILL");
		
		if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.confirm"))){
			myAccountFlow.pressSubmitButton(properties.getString("switchPlan.confirm"));
		}else if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.rightnow"))){
			myAccountFlow.pressSubmitButton(properties.getString("switchPlan.rightnow"));
		}

		addILDAndPay(addILD, addNow);
	}

	/*public void buyAirtimeAndMakePayment(String planToBuy, String addILD, String addNow)
	throws Exception {
		myAccountFlow.clickLink(properties.getString("buyPlan"));
		myAccountFlow.clickLink(properties.getString("switchPlan.continue"));
		
		Plan plan = Plan.getPlan(properties, planToBuy, "Android");
		myAccountFlow.selectRadioButton(plan.getRadioLabel(properties));
		
		myAccountFlow.pressSubmitButton("service_plan_button_selection");
		addILDAndPay(addILD, addNow);
	}*/
	
	public void buyAirtimeAndMakePayment(String planToBuy, String addILD, String deviceType, String addNow)
			throws Exception {
		//myAccountFlow.clickLink(properties.getString("buyPlan"));
		
		//myAccountFlow.clickLink(properties.getString("Redemption.AddorBuyAirtimeLink"));
		TwistUtils.setDelay(30);
		myAccountFlow.clickLink(properties.getString("Redemption.BuyAirtime"));
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String min = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		myAccountFlow.typeInTextField(properties.getString("Redemption.PhoneTextPurchase"), min);
		//myAccountFlow.clickLink(properties.getString("switchPlan.continue"));
		TwistUtils.setDelay(5);
		myAccountFlow.getBrowser().button(properties.getString("switchPlan.continue")).click();
		TwistUtils.setDelay(60);
		if (myAccountFlow.submitButtonVisible(properties.getString("continue"))){//home phone pop up
			myAccountFlow.pressSubmitButton(properties.getString("continue"));
		}			
		selectPlanFor(planToBuy, deviceType);
	            
		if (myAccountFlow.submitButtonVisible("ONE-TIME PURCHASE")){
			myAccountFlow.pressSubmitButton("ONE-TIME PURCHASE");
		}if(myAccountFlow.submitButtonVisible(properties.getString("Redemption.Buy"))) { //for pay as you go plans
			myAccountFlow.pressSubmitButton(properties.getString("Redemption.Buy"));
		}
		TwistUtils.setDelay(60);		
		if (esnUtil.getCurrentESN().getRedeemType().isEmpty()) {
			esnUtil.getCurrentESN().setRedeemType(false);
			esnUtil.getCurrentESN().setTransType("Net10 Airtime Purchase");
		}
		addILDAndPay(addILD, addNow);
	}
	
	public void selectPlanWithILD(String purchaseType, String planStr, String phoneType, String addILD)
			throws Exception {
		selectPlanFor(planStr, phoneType);
		
		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			myAccountFlow.pressSubmitButton("BUY PLAN WITH AUTO-REFILL");
			esnUtil.getCurrentESN().setRedeemType(true);
		} else {
			myAccountFlow.pressSubmitButton("ONE-TIME PURCHASE");
			esnUtil.getCurrentESN().setRedeemType(false);
			esnUtil.getCurrentESN().setTransType("Net10 Buy Airtime");
		}
		
		if(myAccountFlow.submitButtonVisible(properties.getString("Redemption.Buy"))){
			myAccountFlow.pressSubmitButton(properties.getString("Redemption.Buy"));	
		}
		
		//Early Redemption Warning
			if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.confirm"))){
				myAccountFlow.pressSubmitButton(properties.getString("switchPlan.confirm"));
			}else if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.rightnow"))){
				myAccountFlow.pressSubmitButton(properties.getString("switchPlan.rightnow"));
			}
			esnUtil.getCurrentESN().setActionType(6);
		
		if (myAccountFlow.submitButtonVisible("skip_service_plan_button_selection")) {
			if (addILD.equalsIgnoreCase("0")) {
				myAccountFlow.pressSubmitButton("skip_service_plan_button_selection");
			} else {
				myAccountFlow.typeInTextField("quantity", addILD);
				myAccountFlow.pressSubmitButton("add_service_plan_button_selection");
			}
		}
	}

	public void enrollInAutoRefill(String planToBuy, String addILD, String addNow)
			throws Exception {
		String email = esnUtil.getCurrentESN().getEmail();
		String pwd = esnUtil.getCurrentESN().getPassword();
		myAccountFlow.clickLink(properties.getString("Account.SignOut"));
		myAccountFlow.typeInTextField("email", email);
		myAccountFlow.typeInPasswordField("password", pwd);
		myAccountFlow.pressSubmitButton(properties.getString("Account.LogIn"));
		myAccountFlow.clickLink(properties.getString("enrollAutoRefillDiscount"));
		
/*		Plan plan = Plan.getPlan(properties, planToBuy, "Android");
		myAccountFlow.selectRadioButton(plan.getRadioLabel(properties));
		myAccountFlow.selectCheckBox(plan.getEnrollCheck(properties));
		myAccountFlow.pressSubmitButton("service_plan_button_selection");  */
		
		selectPlanFor(planToBuy, esnUtil.getCurrentESN().getDeviceType());		
		myAccountFlow.pressSubmitButton("BUY PLAN WITH AUTO-REFILL");
		
		if (esnUtil.getCurrentESN().getRedeemType().isEmpty()) {
			esnUtil.getCurrentESN().setRedeemType(false);
			esnUtil.getCurrentESN().setTransType("Net10 Activation with Purchase");
		}
		//Early Redemption Warning
		if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.confirm"))){
			myAccountFlow.pressSubmitButton(properties.getString("switchPlan.confirm"));
		}else if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.rightnow"))){
			myAccountFlow.pressSubmitButton(properties.getString("switchPlan.rightnow"));
		}
		addILDAndPay(addILD, addNow);
		
/*		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));	*/	
	}

	private void addILDAndPay(String addILD, String addNow) {
		//Early Redemption Warning
		if ("Now".equalsIgnoreCase(addNow)) {
			/*if (myAccountFlow.submitButtonVisible(properties.getString("Redemption.AddNow"))) {
				myAccountFlow.pressSubmitButton(properties.getString("Redemption.AddNow"));
			}*/		
			if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.confirm"))){
				myAccountFlow.pressSubmitButton(properties.getString("switchPlan.confirm"));
			}else if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.rightnow"))){
				myAccountFlow.pressSubmitButton(properties.getString("switchPlan.rightnow"));
			}
			esnUtil.getCurrentESN().setActionType(6);
		} else {
			/*if (myAccountFlow.submitButtonVisible(properties.getString("Redemption.AddReserve"))) {
				myAccountFlow.pressSubmitButton(properties.getString("Redemption.AddReserve"));
			}*/		
			if (myAccountFlow.submitButtonVisible(properties.getString("switchPlan.later"))){ 
				myAccountFlow.pressSubmitButton(properties.getString("switchPlan.later"));
			}
			esnUtil.getCurrentESN().setActionType(401);
		}

		TwistUtils.setDelay(10);
		if (myAccountFlow.submitButtonVisible("skip_service_plan_button_selection")) {
			if (addILD.equalsIgnoreCase("0")) {
				myAccountFlow.pressSubmitButton("skip_service_plan_button_selection");
			} else {
				myAccountFlow.typeInTextField("quantity", addILD);
				myAccountFlow.pressSubmitButton("add_service_plan_button_selection");
			}
		}
		TwistUtils.setDelay(30);
		if (myAccountFlow.imageVisible("American Express")){
			myAccountFlow.typeInTextField("cvvnumber", "6712");
		}else{
			myAccountFlow.typeInTextField("cvvnumber", "671");
		}
			myAccountFlow.selectCheckBox("terms");
			myAccountFlow.pressButton(properties.getString("Payment.Continue1Submit"));
			TwistUtils.setDelay(40);
			//myAccountFlow.clickLink(properties.getString("Redemption.FinishButton"));
			myAccountFlow.clickStrongMessage(properties.getString("Redemption.FinishButton"));
			TwistUtils.setDelay(20);
	}

	private void cancelEnroll() {
		myAccountFlow.clickLink(properties.getString("cancelEnrollment"));
		myAccountFlow.chooseFromSelect("__reason", properties.getString("noNeeded"));
		myAccountFlow.pressSubmitButton(properties.getString("yes"));
		myAccountFlow.pressSubmitButton(properties.getString("continue"));
	}

	private void applyNow() {
		myAccountFlow.clickLink(properties.getString("applyEnrollmentNow"));
		myAccountFlow.pressButton(properties.getString("applyNow"));
		myAccountFlow.typeInTextField("cvvnumber", "671");
		myAccountFlow.selectCheckBox("terms");
		myAccountFlow.pressSubmitButton(properties.getString("Payment.ContinueSubmit"));
		myAccountFlow.clickLink(properties.getString("Redemption.FinishButton"));
	}

	public void manageReserve() throws Exception {
		goToMyAccount();
		TwistUtils.setDelay(60);
		myAccountFlow.clickLink(properties.getString("manageMonthlyPlans"));
		myAccountFlow.selectRadioButton("pin_card_id1");
		myAccountFlow.pressSubmitButton(properties.getString("addNow"));
		myAccountFlow.pressSubmitButton(properties.getString("yes"));
		myAccountFlow.clickLink(properties.getString("Redemption.FinishButton"));
	}
	
	public void updatePersonalInfo () throws Exception{
		String email = TwistUtils.createRandomEmail();
		System.out.println("Updated Email: " + email);
		myAccountFlow.clickLink(properties.getString("UpdatePersonalProfile"));
		myAccountFlow.typeInTextField("first_name","Twist FirstName");
		myAccountFlow.typeInTextField("last_name","Twist LastName");
		myAccountFlow.typeInTextField("address1","9700 NW 112th Avenue");
		myAccountFlow.typeInTextField("address2","");
		myAccountFlow.typeInTextField("email",email);
		myAccountFlow.typeInTextField("confirm_email",email);
		myAccountFlow.typeInPasswordField("new_password","123456a");
		myAccountFlow.typeInPasswordField("confirm_password","123456a");
		myAccountFlow.typeInTextField("sec_ans","123456");
		myAccountFlow.pressButton(properties.getString("save"));
		myAccountFlow.typeInPasswordField("current_password","123456");
		myAccountFlow.pressSubmitButton(properties.getString("save"));
		Assert.assertTrue(myAccountFlow.divVisible(properties.getString("updatedSuccessfully")));
		myAccountFlow.clickLink(properties.getString("Account.SignOut"));
		myAccountFlow.typeInTextField("email", email);
		myAccountFlow.typeInPasswordField("password", "123456a");
		myAccountFlow.pressSubmitButton(properties.getString("Account.LogIn"));
		Assert.assertTrue(myAccountFlow.linkVisible(properties.getString("Account.SignOut")));
	}

	public void goToMyAccount() throws Exception {
		if(myAccountFlow.linkVisible(properties.getString("Account.MyAccountLink"))){
			myAccountFlow.clickLink(properties.getString("Account.MyAccountLink"));
		}else{
			myAccountFlow.clickLink(properties.getString("Account.MyAccountSummary"));
		}
	}
	
	public void selectPlanFor(String plan, String deviceType) throws Exception {
		String planToSelect = null;
		String planType = null;
		if(deviceType.equalsIgnoreCase("Android") || deviceType.equalsIgnoreCase("Non-Android")){
			if(plan.equalsIgnoreCase("Unlimited $75")){
				planToSelect = "/.*?NTAPP6U075_en\\.png/";
				planType = "MONTHLY PLANS";
			}else if(plan.equalsIgnoreCase("Unlimited International Monthly")){
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
		
		//planUtil.setPlanFound(true);
		
		myAccountFlow.clickImage(planToSelect);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

}