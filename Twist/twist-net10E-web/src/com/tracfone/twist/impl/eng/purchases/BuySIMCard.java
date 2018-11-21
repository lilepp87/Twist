package com.tracfone.twist.impl.eng.purchases;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

// JUnit Assert framework can be used for verification

public class BuySIMCard {

	private PurchasesFlow purchasesFlow;

	public BuySIMCard() {

	}

	public void clickSIMCardsLink() throws Exception {
		purchasesFlow.clickLink(PurchasesFlow.PurchasesNet10EFields.TarjetasSIMLink.name);
	}

	public void selectUnlockedOption() throws Exception {
		purchasesFlow.selectRadioButton(PurchasesFlow.PurchasesNet10EFields.UnlockedRadioButton.name);
	}

	public void selectSIMCard() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10EFields.SimCardImage.name);
	}

	public void enterZipCode(String zipCode) throws Exception {
		purchasesFlow.typeInTextField(PurchasesFlow.PurchasesNet10EFields.ZipCodeText4Sim.name, zipCode);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10EFields.ContinueButton.name);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10EFields.ContinueButton.name);
	}

	public void validateBrightpointWebpage() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10EFields.VerCarritoImage.name);
		Assert.assertTrue(purchasesFlow.listItemVisible(PurchasesFlow.PurchasesNet10EFields.CarritoListItem.name));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		purchasesFlow = tracfoneFlows.purchasesFlow();
	}

}