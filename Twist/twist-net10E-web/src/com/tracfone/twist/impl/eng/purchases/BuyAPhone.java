package com.tracfone.twist.impl.eng.purchases;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.activation.PortIn;
import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAPhone {

	private PurchasesFlow purchasesFlow;
	private static ESNUtil esnUtil;
	
	public static ESNUtil getEsnUtil() {
		return esnUtil;
	}

	public static void setEsnUtil(ESNUtil esnUtil) {
		BuyAPhone.esnUtil = esnUtil;
	}
	private ResourceBundle nt = ResourceBundle.getBundle("Net10");
	private PortIn portIn;

	public PortIn getPortIn() {
		return portIn;
	}

	public void setPortIn(PortIn portIn) {
		this.portIn = portIn;
	}

	public BuyAPhone() {

	}

	public void selectBuyPhones() throws Exception {
		purchasesFlow.clickLink(PurchasesFlow.PurchasesNet10EFields.ComprarTelefonosLink.name);
	}

	public void enterZipCode(String zipCode) throws Exception {
		purchasesFlow.typeInTextField(PurchasesFlow.PurchasesNet10EFields.ZipCodeText.name, zipCode);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10EFields.BuscarSubmitButton.name);
	}

	public void confirmAndAcceptZipCode() throws Exception {
		purchasesFlow.clickImageSubmitButton(PurchasesFlow.PurchasesNet10EFields.ConfirmZipCodeImageButton.name);
		purchasesFlow.clickImageSubmitButton(PurchasesFlow.PurchasesNet10EFields.AcceptZipCodeImageButton.name);
	}

	public void validateBrightpointWebpage() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10EFields.VerCarritoImage.name);
		Assert.assertTrue(purchasesFlow.listItemVisible(PurchasesFlow.PurchasesNet10EFields.CarritoListItem.name));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		purchasesFlow = tracfoneFlows.purchasesFlow();
	}
	
	public void goToShopPhones(String customer, String zipCode) throws Exception {
		TwistUtils.setDelay(5);
		if (customer.equalsIgnoreCase("new")){
		ElementStub e = purchasesFlow.getBrowser().byId("zipPopup_zc");
		e.setValue(zipCode);
		purchasesFlow.pressSubmitButton("Continue[0]");
		}else{
			portIn.enterPhoneOfBrand(nt.getString("OldEsn"));
		}
	}
	
	public void selectPhone(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("Samsung")) {
			purchasesFlow.selectCheckBox(nt.getString("Brand.Samsung"));
			TwistUtils.setDelay(10);
			purchasesFlow.clickLink(nt.getString("NT.Samsung.1"));			
		}else if(phone.equalsIgnoreCase("zte")){
			purchasesFlow.selectCheckBox(nt.getString("Brand.Zte"));
			TwistUtils.setDelay(10);
			purchasesFlow.clickLink(nt.getString("NT.zte.1"));
					
		}else if(phone.equalsIgnoreCase("alcatel")){
			purchasesFlow.selectCheckBox(nt.getString("Brand.Alcatel"));
			TwistUtils.setDelay(10);
			purchasesFlow.clickLink(nt.getString("NT.alcatel.1"));
			
		}else if(phone.equalsIgnoreCase("huawei")){
			purchasesFlow.selectCheckBox(nt.getString("Brand.Huawei"));
			TwistUtils.setDelay(10);
			purchasesFlow.clickLink(nt.getString("NT.Huawei.1"));
		}
	}
	
	public void addPhoneToCart() throws Exception {
		if (purchasesFlow.getBrowser().paragraph("top-push-4 text-black[2]").isVisible()){
			purchasesFlow.getBrowser().paragraph("top-push-4 text-black[2]").click();
			//myAccountFlow.getBrowser().byId("lto_smartpay_option_2").click();
		}
		//myAccountFlow.getBrowser().
		//byId("price top-push-6").click();
		if(purchasesFlow.linkVisible("ADD TO CART")){
			purchasesFlow.clickLink("ADD TO CART");
		}
		else {
			System.out.println("Phone Out Of Stock");
			purchasesFlow.clickLink("CONTINUE SHOPPING");			
		}
	}
	
	public void selectCarrierSimKit(String carrier){
		if(carrier.equalsIgnoreCase("ATT")){
			purchasesFlow.getBrowser().byId(nt.getString("NT.ATT")).click();			
		}else if (carrier.equalsIgnoreCase("TMO")){
			purchasesFlow.getBrowser().byId(nt.getString("NT.TMO")).click();	
		}else if (carrier.equalsIgnoreCase("VZW")){
			purchasesFlow.getBrowser().byId(nt.getString("NT.VZW")).click();	
		}
		TwistUtils.setDelay(10);
		purchasesFlow.getBrowser().byId("select_phone_bundles_btn").click();
	}
		
	public void selectServicePlan(String servicePlan) {
			String planDiv = "";
			if (servicePlan.equalsIgnoreCase("$35")) {
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[0]");
			} else if (servicePlan.equalsIgnoreCase("$40")) {
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[1]");
			} else if (servicePlan.equalsIgnoreCase("$50")) {
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[2]");
			} else if (servicePlan.equalsIgnoreCase("$65")) {
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[3]");
			} else if (servicePlan.equalsIgnoreCase("$75")) {
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[4]");
			} else if (servicePlan.equalsIgnoreCase("No Plan")) { 
				// No thanks to the plan
				TwistUtils.setDelay(10);
				purchasesFlow.clickLink("Continue[5]");
			}
			
			TwistUtils.setDelay(5);
			purchasesFlow.clickLink("CHECK OUT");
			TwistUtils.setDelay(5);
		
	}
		
	public void enterShippingAddressAndWhiteListedCard(String card) throws Exception {
		String cardNum = TwistUtils.generateCreditCard(card);		
		enterShippingInfo();
		purchasesFlow.chooseFromSelect("payMethodId_1", card + " Credit Card");
		purchasesFlow.typeInTextField("account1_1", cardNum);
		if(card.equalsIgnoreCase("American Express")){
			purchasesFlow.typeInTextField("cc_cvc_1", "6718");
		}else {
			purchasesFlow.typeInTextField("cc_cvc_1", "671");
		}
		purchasesFlow.chooseFromSelect("expire_month_1", "07");
		purchasesFlow.chooseFromSelect("expire_year_1", "2021");
		
		purchasesFlow.clickLink("CONTINUE");		
		TwistUtils.setDelay(15);
		purchasesFlow.pressSubmitButton("Submit[0]");
			
	}
	
	public void enterShippingInfo() throws Exception{
		TwistUtils.setDelay(20);
		if(purchasesFlow.buttonVisible("Ok")){
			purchasesFlow.pressButton("Ok");
		}
		String email = "itq_st_" + TwistUtils.createRandomEmail();
		purchasesFlow.typeInTextField("firstName", "Cyber");
		purchasesFlow.typeInTextField("lastName", "Source");
		purchasesFlow.typeInTextField("phone1", TwistUtils.generateRandomMin());
		purchasesFlow.typeInTextField("email1", email);
		purchasesFlow.typeInTextField("address1", "1295 charleston road");
		purchasesFlow.typeInTextField("address2", "");
		purchasesFlow.typeInTextField("city", "Mountain view");
		purchasesFlow.chooseFromSelect("state", "California");
		
		if(!purchasesFlow.getBrowser().byId("zipCode").containsText("94043")){
			if(purchasesFlow.linkVisible("btn btn-link pull-left top-push-2 zip-code")){
				purchasesFlow.clickLink("btn btn-link pull-left top-push-2 zip-code");
			TwistUtils.setDelay(5);
			purchasesFlow.clickLink("Change ZIP");
			TwistUtils.setDelay(3);
			purchasesFlow.typeInTextField("zipCode", "94043");
			}else{
				purchasesFlow.typeInTextField("zipCode", "94043");
			}
		}			
	}	
	
	public void verifyOrderSummary() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(purchasesFlow.h3Visible("ORDER SUMMARY"));		
		
	}
	public void printOrderNumber(){
		ElementStub el = purchasesFlow.getBrowser().heading2("panel-title title-panel-box number-order");
		String orderId = purchasesFlow.getBrowser().getText(el);
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

}