package com.tracfone.twist.Purchases;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.RandomACHGenerator;
import com.tracfone.twist.utils.TwistUtils;

public class EnterPayment {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	public EnterPayment() {
		
	}

	public void purchaseServicePlanWithILD(String purchaseType, String servicePlan,String ild) throws Exception {
		TwistUtils.setDelay(10);
		pressButton(props.getString("BuyAirtime.BuyPlanLink"));
		TwistUtils.setDelay(10);
		String planStr = getPlanStr(servicePlan);
		String Button = getPlanButton(servicePlan);
		//redeemFlow.clickLink(planStr);
		if (ild.equalsIgnoreCase("Yes")) {
			paymentFlow.selectCheckBox(props.getString("BuyAirtime.ReUpildCheckbox")+ planStr );
		}		
		if (purchaseType.contains(props.getString("BuyAirtime.ReUpStr"))) {
			paymentFlow.selectCheckBox(props.getString("BuyAirtime.ReUpCheckbox")+ planStr);
		}
		paymentFlow.clickLink("PURCHASE"+ Button);
		storeRedeemData();
	}

	public void enterCreditCardFor(String cardType, String purchaseType) throws Exception {
		if (!cardType.equalsIgnoreCase("ACH")){
			String card;
			card = TwistUtils.generateCreditCard(cardType);
			paymentFlow.typeInTextField(props.getString("Payment.AccountNumberText"), card);
			if (cardType.equalsIgnoreCase("American Express")) {
				paymentFlow.typeInTextField(props.getString("Payment.AccCVVText"), "6712");
			} else {
				paymentFlow.typeInTextField(props.getString("Payment.AccCVVText"), "671");
			}
			paymentFlow.chooseFromSelect(props.getString("Payment.AccExpMonthSelectText"), "07");
			paymentFlow.chooseFromSelect(props.getString("Payment.AccExpYearSelectText"), "2021");
			paymentFlow.chooseFromSelect(props.getString("Payment.StateSelectText"),"CA");			
		}else {
			String routingNum = RandomACHGenerator.getRoutingNumber();
			String accountNum = RandomACHGenerator.getAccountNumber(); 
			paymentFlow.clickLink("Bank Account");
			paymentFlow.chooseFromSelect("bank_account_type","Savings");
			paymentFlow.typeInTextField("routing_number",routingNum);
			paymentFlow.typeInTextField("bank_account_number",accountNum);
			paymentFlow.chooseFromSelect(props.getString("Port.ExtState"),"CA");	
		}
			paymentFlow.typeInTextField(props.getString("Payment.AccFirstNameText"), "TwistFirstName");
			paymentFlow.typeInTextField(props.getString("Payment.AccLastnameText"), "TwistLastName");
			paymentFlow.typeInTextField(props.getString("Payment.AccAddress1Text"), "1295 Charleston Road");
			paymentFlow.typeInTextField(props.getString("Payment.zipcode"), "94043");
			paymentFlow.typeInTextField(props.getString("Payment.city"), "Mountain View");
			//if(paymentFlow.checkboxVisible(props.getString("Payment.SaveCC"))){
			if (!purchaseType.equalsIgnoreCase("Reoccurring")){
			paymentFlow.selectCheckBox(props.getString("Payment.SaveCC"));
			}
			//}
			//if(paymentFlow.checkboxVisible(props.getString("Payment.CheckTerms"))){
			paymentFlow.selectCheckBox(props.getString("Payment.CheckTerms"));
			//}
			paymentFlow.pressSubmitButton(props.getString("Payment.ProcessTransaction"));
			TwistUtils.setDelay(10);
			esnUtil.getCurrentESN().setBuyFlag(true);
	}
	
	private void pressButton(String button) {
		if (paymentFlow.buttonVisible(button)) {
			paymentFlow.pressButton(button);
		} else {
			paymentFlow.pressSubmitButton(button);
		}
	}
	
	private void storeRedeemData() {
		ESN esn = esnUtil.getCurrentESN();
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("GS Buy Service");
	}
	
	public static String getPlanStr(String plan) {
		String planStr;
		if (plan.contains("55")) {
			planStr = "455";
		} else if (plan.contains("45")) {
			planStr = "453";
		} else if (plan.contains("35")) {
			planStr = "451";
		} else if (plan.contains("25")){
			planStr = "449";
		} else {
			throw new IllegalArgumentException("Plan '" + plan + "' not found");
		}
		return planStr;
	}
	public static String getPlanButton(String plan) {
		String Button = "";
		if (plan.contains("55")) {
			Button = "[3]";
		} else if (plan.contains("45")) {
			Button = "[2]";
		} else if (plan.contains("35")) {
			Button = "[1]";
		} else if (plan.contains("25")){
		}
		return Button;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void selectCreditCardToPay(String cardType) throws Exception {
		paymentFlow.clickStrongMessage("Use an Existing Payment Method");
		if (paymentFlow.textboxVisible("userid")){
			paymentFlow.typeInTextField("userid",esnUtil.getCurrentESN().getEmail());
			paymentFlow.typeInPasswordField("password",esnUtil.getCurrentESN().getPassword());
			pressButton("Login to My Account & Continue");
		}
		if (cardType.equalsIgnoreCase("American Express")) {
			paymentFlow.typeInTextField(props.getString("Payment.AccCVVText1"), "6712");
		} else {
			paymentFlow.typeInTextField(props.getString("Payment.AccCVVText1"), "671");
		}
		paymentFlow.selectCheckBox(props.getString("Payment.SavedCheckTerms"));
		paymentFlow.pressSubmitButton(props.getString("Payment.ProcessTransaction"));
	}

}
