package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class BuyAPhone {

	private PurchasesFlow purchasesFlow;

	public BuyAPhone() {

	}

	public void selectBuyPhones() throws Exception {
		purchasesFlow.clickLink(PurchasesFlow.PurchasesTracfoneWFields.ComprarTelefonosLink.name);
	}

	public void enterZipCode(String zipCode) throws Exception {
		purchasesFlow.typeInTextField(PurchasesFlow.PurchasesTracfoneWFields.ZipCodeText.name, zipCode);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesTracfoneWFields.BuscarSubmitButton.name);
	}

	public void confirmAndAcceptZipCode() throws Exception {
		//not currently used, test brightpoint site doesn't seem to work consistently
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesTracfoneWFields.AcceptZipCodeImageButton.name);
	}

	public void checkoutPhoneAndVerify() throws Exception {
		//not currently used, test brightpoint site doesn't seem to work consistently
		purchasesFlow.clickLink("checkout");
		purchasesFlow.clickLink("Feeling Lazy");
		purchasesFlow.selectCheckBox("useShipping");
		purchasesFlow.clickLink("submit");
		purchasesFlow.clickLink("Submit Order");
		Assert.assertTrue(purchasesFlow.divVisible("Order Complete"));
	}

	public void addPhoneToCart() throws Exception {
		purchasesFlow.clickLink("Add to Cart");
	}

	public void validateBrightpointWebpage() throws Exception {
		//Only checks if you can get to the brightpoint shopping cart and does not test
		//a purchase. Brightpoint does not seem to work currently.
//		purchasesFlow.clickLink(PurchasesFlow.PurchasesTracfoneWFields.VerCarritoImage.name);
//		Assert.assertTrue(purchasesFlow.divVisible(PurchasesFlow.PurchasesTracfoneWFields.CarritoListItem.name));
		purchasesFlow.clickLink("viewCartNav");
		purchasesFlow.clickDivMessage("Continue");
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		purchasesFlow = tracfoneFlows.purchasesFlow();
	}

}