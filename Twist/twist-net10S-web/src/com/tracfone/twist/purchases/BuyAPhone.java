package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class BuyAPhone {

	private PurchasesFlow purchasesFlow;

	public BuyAPhone() {

	}

	public void selectBuyPhones() throws Exception {
		purchasesFlow.clickLink(PurchasesFlow.PurchasesNet10SFields.ComprarTelefonosLink.name);
	}

	public void enterZipCode(String zipCode) throws Exception {
		purchasesFlow.typeInTextField(PurchasesFlow.PurchasesNet10SFields.ZipCodeText.name, zipCode);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10SFields.BuscarSubmitButton.name);
	}

	public void confirmAndAcceptZipCode() throws Exception {
		purchasesFlow.clickImageSubmitButton(PurchasesFlow.PurchasesNet10SFields.ConfirmZipCodeImageButton.name);
		purchasesFlow.clickImageSubmitButton(PurchasesFlow.PurchasesNet10SFields.AcceptZipCodeImageButton.name);
	}

	public void validateBrightpointWebpage() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10SFields.VerCarritoImage.name);
		Assert.assertTrue(purchasesFlow.listItemVisible(PurchasesFlow.PurchasesNet10SFields.CarritoListItem.name));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		purchasesFlow = tracfoneFlows.purchasesFlow();
	}

}