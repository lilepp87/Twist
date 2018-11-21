package com.tracfone.twist.flows.tracfone;

import net.sf.sahi.client.Browser;

import com.tracfone.twist.flows.Flow;

public class PurchasesFlow extends Flow {

	public PurchasesFlow(Browser browser) {
		this.browser = browser;
	}

	public static enum PurchasesNet10SFields {
		EnviarSubmitButton("Enviar"),
		EsnText("esn"),
		PhoneNumberText("param_min"),
		ComprarTelefonosLink("Teléfonos NET10"),
		ZipCodeText("zip"),
		BuscarSubmitButton("Buscar[1]"),
		ConfirmZipCodeImageButton("submitBtn"),
		AcceptZipCodeImageButton("Yes! This is my area."),
		VerCarritoImage("Ver carrito"),
		CarritoListItem("Carro De Compras"),
		TarjetasSIMLink("Tarjetas SIM"),
		SimCardImage("sim.gif"),
		ZipCodeText4Sim("display_location_zip"),
		ContinueButton("Continuamos."),
		UnlockedRadioButton("phone_option[1]"),
		DownloadsLink("Downloads / Descargas");

		public final String name;

		private PurchasesNet10SFields(String id) { this.name = id; }
	}

	public static enum PurchasesNet10EFields {
		EnviarSubmitButton("Continue"),
		EsnText("esn"),
		PhoneNumberText("param_min"),
		ComprarTelefonosLink("NET10 Phones"),
		ZipCodeText("zip"),
		BuscarSubmitButton("Search[1]"),
		ConfirmZipCodeImageButton("submitBtn"),
		AcceptZipCodeImageButton("Yes! This is my area."),
		VerCarritoImage("View Cart"),
		CarritoListItem("Shopping Cart"),
		TarjetasSIMLink("SIM Cards"),
		SimCardImage("sim.gif"),
		ZipCodeText4Sim("display_location_zip"),
		ContinueButton("Continue"),
		UnlockedRadioButton("phone_option[1]"),
		DownloadsLink("Downloads and More");

		public final String name;

		private PurchasesNet10EFields(String id) { this.name = id; }
	}

	public static enum PurchasesTracfoneWFields {
		EnviarSubmitButton("Continue"),
		EsnText("esn"),
		PhoneNumberText("param_min"),
		ComprarTelefonosLink("Shop Phones"),
		ZipCodeText("zip"),
		BuscarSubmitButton("Continue"),
		ConfirmZipCodeImageButton("submitBtn"),
		AcceptZipCodeImageButton("Yes"),
		VerCarritoImage("View Cart"),
		CarritoListItem("Shopping Cart"),
		TarjetasSIMLink("SIM Cards"),
		SimCardImage("sim.gif"),
		ZipCodeText4Sim("display_location_zip"),
		ContinueButton("Continue"),
		UnlockedRadioButton("phone_option[1]"),
		TonesLink("Tones"),
		YesButton("Yes"),
		DownloadsLink("Downloads and More");

		public final String name;

		private PurchasesTracfoneWFields(String id) { this.name = id; }
	}

}