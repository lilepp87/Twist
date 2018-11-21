package com.tracfone.twist.purchases;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

// JUnit Assert framework can be used for verification

public class BuySIMCard {

	private PurchasesFlow purchasesFlow;

	public BuySIMCard() {

	}

	public void clickSIMCardsLink() throws Exception {
		purchasesFlow.clickLink(PurchasesFlow.PurchasesNet10SFields.TarjetasSIMLink.name);
	}

	public void selectUnlockedOption() throws Exception {
		purchasesFlow.selectRadioButton(PurchasesFlow.PurchasesNet10SFields.UnlockedRadioButton.name);
	}

	public void selectSIMCard() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10SFields.SimCardImage.name);
	}

	public void enterZipCode(String zipCode) throws Exception {
		purchasesFlow.typeInTextField(PurchasesFlow.PurchasesNet10SFields.ZipCodeText4Sim.name, zipCode);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10SFields.ContinueButton.name);
		purchasesFlow.pressSubmitButton(PurchasesFlow.PurchasesNet10SFields.ContinueButton.name);
	}

	public void validateBrightpointWebpage() throws Exception {
		purchasesFlow.clickImage(PurchasesFlow.PurchasesNet10SFields.VerCarritoImage.name);
		Assert.assertTrue(purchasesFlow.listItemVisible(PurchasesFlow.PurchasesNet10SFields.CarritoListItem.name));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		purchasesFlow = tracfoneFlows.purchasesFlow();
	}

}