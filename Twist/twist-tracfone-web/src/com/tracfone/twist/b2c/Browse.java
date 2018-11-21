package com.tracfone.twist.b2c;

import java.util.ResourceBundle;
import net.sf.sahi.client.Browser;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class Browse {
	
	private static PhoneUtil phoneUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("B2C");

	public Browse() {

	}

	public void goToPhones(String zipCode) throws Exception {
		activationPhoneFlow.clickLink("Phones[1]");
		activationPhoneFlow.pressButton("New Customer");
		activationPhoneFlow.typeInTextField("zip_code[1]", zipCode);
		activationPhoneFlow.pressSubmitButton("Continue[1]");
		activationPhoneFlow.pressSubmitButton("Yes[1]");
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void selectPhonesAndCompare() throws Exception {
		activationPhoneFlow.selectCheckBox("/comparebox_\\d{5}/");
		activationPhoneFlow.selectCheckBox("/comparebox_\\d{5}/[1]");

		activationPhoneFlow.clickLink("COMPARE[1]");
		Assert.assertTrue(activationPhoneFlow.linkVisible("VIEW DETAILS"));
	}

	public void browsePhonesBasedOn(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("Samsung")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Samsung"));
		} else if (phone.equalsIgnoreCase("LG")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.LG"));
			Assert.assertTrue(activationPhoneFlow.linkVisible("LG Optimus Zip™"));
		} else if (phone.equalsIgnoreCase("LG Inc")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.LgInc"));
		} else if (phone.equalsIgnoreCase("MotorolaInc")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.MotorolaInc"));
		} else if (phone.equalsIgnoreCase("Huawei")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Huawei"));
			Assert.assertTrue(activationPhoneFlow.linkVisible("HUAWEI Ascend® Y"));
		} else if(phone.equalsIgnoreCase("Alcatel")){
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Alcatel"));
		} else if(phone.equalsIgnoreCase("Apple")){
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Apple"));
		} else if(phone.equalsIgnoreCase("BlackBerry")){
			activationPhoneFlow.selectCheckBox(props.getString("Brand.BlackBerry"));
		} else if(phone.equalsIgnoreCase("Unimax")){
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Unimax"));
		} else if(phone.equalsIgnoreCase("ZTE")){
			activationPhoneFlow.selectCheckBox(props.getString("Brand.ZTE"));
		} else if (phone.equalsIgnoreCase("Bar")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Bar"));
		} else if (phone.equalsIgnoreCase("Qwerty")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Qwerty"));
		} else if (phone.equalsIgnoreCase("Flip")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Flip"));
		} else if (phone.equalsIgnoreCase("Slide")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Slide"));
		} else if (phone.equalsIgnoreCase("Swype")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Swype"));
		} else {
			throw new IllegalArgumentException("Unknown phone type: " + phone);
		}
	}

	public void selectSortType(String sortBy) throws Exception {
		activationPhoneFlow.chooseFromSelect("orderBy", sortBy);
	}

	public void chooseRefurbished(String refurb) throws Exception {
		if (refurb.equalsIgnoreCase("Yes")) {
			activationPhoneFlow.clickSpanMessage("SHOW ME REFURBISHED ITEMS");
		}
	}

	public void sortByPriceRangeTo(String low, String high) throws Exception {
		Browser browser = activationPhoneFlow.getBrowser();
		browser.byId("low_price_input").setValue(low);
		browser.byId("high_price_input").setValue(high);
		activationPhoneFlow.pressButton("GO");
	}

	public void checkIsShown(String expectedPhone) throws Exception {
		Browser browser = activationPhoneFlow.getBrowser();
		String foundPhone = browser.link("/WC_CatalogEntryDBThumbnailDisplayJSPF_\\d{5}_link_9b/").getText();
		boolean success = expectedPhone.equalsIgnoreCase(foundPhone);
		String errMess = "Found phone: \"" + foundPhone + "\" instead of expected phone: " + expectedPhone;
		Assert.assertTrue(errMess, success);
	}

}