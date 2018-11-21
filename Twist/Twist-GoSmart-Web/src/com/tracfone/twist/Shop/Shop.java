package com.tracfone.twist.Shop;

import java.util.ResourceBundle;

import junit.framework.Assert;
import net.sf.sahi.client.ElementStub;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification


public class Shop {
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	private static ESNUtil esnUtil;


	public static ESNUtil getEsnUtil() {
		return esnUtil;
	}

	public static void setEsnUtil(ESNUtil esnUtil) {
		Shop.esnUtil = esnUtil;
	}

	public Shop() {

	}

	public void goToShopSimCardsOrBYOP() throws Exception {
		activationPhoneFlow.clickLink(props.getString("Shop.SIM"));
	}

	public void selectZipSIMType(String PhoneType, String Zip, String SIMType)throws Exception {
		
		if (PhoneType.equalsIgnoreCase("T-Mobile Compatible Phone")) {
			activationPhoneFlow.selectRadioButton(props.getString("Shop.TMobile"));
		}else if(PhoneType.equalsIgnoreCase("Unlocked GSM Phone")) {
			activationPhoneFlow.selectRadioButton(props.getString("Shop.GSMPhone"));
		}
		activationPhoneFlow.typeInTextField(props.getString("Shop.Zip"), Zip);
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
		activationPhoneFlow.pressSubmitButton(props.getString("Activate.continue"));
		if (SIMType.equalsIgnoreCase("Nano")) {
			activationPhoneFlow.clickLink(props.getString("Shop.Nano"));
		} else if (SIMType.equalsIgnoreCase("Dual")) {
			activationPhoneFlow.clickLink(props.getString("Shop.Dual"));
		}
	
	}

	public void selectPlanWithILDAndCheckout(String Plan, String withILD) throws Exception {
		
		if (Plan.contains("55") && withILD.equalsIgnoreCase("No")) {
			activationPhoneFlow.clickLink("Buy Now");
		} else if (Plan.contains("45")&& withILD.equalsIgnoreCase("No")) {
			activationPhoneFlow.clickLink("Buy Now[1]");
		} else if (Plan.contains("35")&& withILD.equalsIgnoreCase("No")) {
			activationPhoneFlow.clickLink("Buy Now[2]");
		} else if (Plan.contains("25")&& withILD.equalsIgnoreCase("No")){
			activationPhoneFlow.clickLink("Buy Now[3]");
		} else if (Plan.contains("55") && withILD.equalsIgnoreCase("Yes")) {
			activationPhoneFlow.clickLink("Buy Now[4]");
		} else if (Plan.contains("45")&& withILD.equalsIgnoreCase("Yes")) {
			activationPhoneFlow.clickLink("Buy Now[5]");
		} else if (Plan.contains("35")&& withILD.equalsIgnoreCase("Yes")) {
			activationPhoneFlow.clickLink("Buy Now[6]");
		} else if (Plan.contains("25")&& withILD.equalsIgnoreCase("Yes")){
			activationPhoneFlow.clickLink("Buy Now[7]");
		} else {
			throw new IllegalArgumentException("Plan '" + Plan + "' not found");
		}
		
		if(activationPhoneFlow.linkVisible("GO TO SECURE CHECKOUT[1]")){
			activationPhoneFlow.clickLink("GO TO SECURE CHECKOUT[1]");
		}else{
			activationPhoneFlow.clickLink("GO TO SECURE CHECKOUT");
		}
		
	}


	public void enterCreditCard(String card) throws Exception {
		String email = TwistUtils.createRandomEmail();
		System.out.println(email);
		String cardNum = TwistUtils.generateCreditCard(card);
		activationPhoneFlow.typeInTextField(props.getString("Payment.AccFirstNameText"), "Twist FirstName");
		activationPhoneFlow.typeInTextField(props.getString("Payment.AccLastnameText"), "Twist LastName");
		activationPhoneFlow.typeInTextField(props.getString("Payment.AccAddress1Text"), "1295 Charleston Road");
		activationPhoneFlow.chooseFromSelect(props.getString("Port.ExtState"), "California");
		activationPhoneFlow.typeInTextField(props.getString("Payment.city"), "Mountain View");	
		activationPhoneFlow.typeInTextField(props.getString("Shop.Phone"), TwistUtils.generateRandomMin());
		activationPhoneFlow.typeInTextField(props.getString("Shop.Email"), email);
		
		activationPhoneFlow.chooseFromSelect("payMethodId", card + " Credit Card");
		System.out.println(cardNum);
		activationPhoneFlow.typeInTextField(props.getString("Shop.Account"), cardNum);
		if (card.equalsIgnoreCase("American Express")) {
			activationPhoneFlow.typeInTextField(props.getString("Shop.CVV"), "6715");
		} else {
			activationPhoneFlow.typeInTextField(props.getString("Shop.CVV"), "671");
		}
		activationPhoneFlow.chooseFromSelect(props.getString("Shop.CCMonth"), "07");
		activationPhoneFlow.chooseFromSelect(props.getString("Shop.CCYear"), "2021");
	
		activationPhoneFlow.clickLink("CONTINUE TO ORDER SUMMARY");
		if(activationPhoneFlow.buttonVisible("OK")){
			activationPhoneFlow.pressButton("OK");
		}
		TwistUtils.setDelay(5);
	}

	public void confirmOrder() throws Exception {
		
		activationPhoneFlow.clickDivMessage(props.getString("Shop.Order"));
		TwistUtils.setDelay(5);
		Assert.assertTrue(activationPhoneFlow.h2Visible("Thank you for your order!"));
		Assert.assertTrue(activationPhoneFlow.h4Visible("ORDER SUMMARY"));
		ElementStub el = activationPhoneFlow.getBrowser().byId("WC_OrderShippingBillingConfirmationPage_div_4");
		String orderId = activationPhoneFlow.getBrowser().getText(el);
		System.out.println(orderId);
		//ESN esn = new ESN("Order Number", orderId.substring(orderId.indexOf(":")+1,orderId.lastIndexOf("Order")-1));
		orderId =orderId.substring(13, 25);
		System.out.println(orderId);
		ESN esn = new ESN( "Order Number", orderId);
        esnUtil.setCurrentESN(esn);
        
		/*String[] possOrder = orderId.split(" ");
		int orderNum =  -1;
		for (int i = 2; i < possOrder.length; i++)	{
			try {
				orderNum = Integer.valueOf(possOrder[i]);
				break;
			} catch (NumberFormatException ex) {}			
		}*/
		
	
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

}
