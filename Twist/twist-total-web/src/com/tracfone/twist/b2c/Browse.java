package com.tracfone.twist.b2c;

import java.util.ResourceBundle;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import org.junit.Assert;

import com.tracfone.twist.context.OnTotalWirelessHomePage;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class Browse {
	
	private static PhoneUtil phoneUtil;
	private static ESNUtil esnUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("TotalWeb");

	public Browse() {

	}
	
	public static ESNUtil getEsnUtil() {
		return esnUtil;
	}
	public static void setEsnUtil(ESNUtil esnUtil) {
		Browse.esnUtil = esnUtil;
	}
	public static PhoneUtil getPhoneUtil() {
		return phoneUtil;
	}
	public static void setPhoneUtil(PhoneUtil phoneUtil) {
		Browse.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void goToPhones(String zipCode) throws Exception {
		activationPhoneFlow.clickLink("Phones[2]");
		TwistUtils.setDelay(8);
		activationPhoneFlow.typeInTextField("zip_code", zipCode);
		activationPhoneFlow.pressSubmitButton("Continue");
		activationPhoneFlow.pressSubmitButton("button_yes");
	}

	public void goToPhonesPage(String zipCode) throws Exception {
		
		activationPhoneFlow.clickLink("Phones");
		TwistUtils.setDelay(8);
		ElementStub e = activationPhoneFlow.getBrowser().byId("zipPopup_zc");
		e.setValue(zipCode);
		activationPhoneFlow.pressSubmitButton("Continue[0]");

	}
	
	
	public void goToPhonesPageExisting(String zipCode) throws Exception {
		
//		String EsnStr = phoneUtil.getEsnForPort("TWZEZ717VCP");
//		String Min = phoneUtil.getMinOfActiveEsn(EsnStr);
//		System.out.println("Customer's Number" +Min);
		
		String Min =phoneUtil.getminforESN(esnUtil.getCurrentESN().getEsn());
		
		activationPhoneFlow.clickLink("Phones");
		TwistUtils.setDelay(8);
		ElementStub e = activationPhoneFlow.getBrowser().byId("phoneNumber");
		e.setValue(Min);
		activationPhoneFlow.pressSubmitButton("Continue[1]");

	}
	
	public void selectPhonesAndCompare() throws Exception {
		TwistUtils.setDelay(30);
		activationPhoneFlow.selectCheckBox("/comparebox_\\d{5}/");
		activationPhoneFlow.selectCheckBox("/comparebox_\\d{5}/[1]");

		activationPhoneFlow.clickLink("COMPARE[1]");
		Assert.assertTrue(activationPhoneFlow.linkVisible("VIEW DETAILS"));
	}

	public void browsePhonesBasedOn(String phone) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("Samsung")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Samsung"));
		} else if (phone.equalsIgnoreCase("Apple")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Apple"));
		} else if (phone.equalsIgnoreCase("Bar")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Bar"));
		} else if (phone.equalsIgnoreCase("Flip")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Flip"));
		} else if (phone.equalsIgnoreCase("Slide")) {
			activationPhoneFlow.selectCheckBox(props.getString("PhoneType.Slide"));
		} else if (phone.equalsIgnoreCase("Black")) {
			activationPhoneFlow.selectCheckBox(props.getString("Color.Black"));
		} else if (phone.equalsIgnoreCase("Silver")) {
			activationPhoneFlow.selectCheckBox(props.getString("Color.Silver"));
		} else if (phone.equalsIgnoreCase("Grey")) {
			activationPhoneFlow.selectCheckBox(props.getString("Color.Grey"));
		} else {
			throw new IllegalArgumentException("Unknown phone type: " + phone);
		}
	}
	
	
	public void browsePhonesBasedOnPhoneAndModel(String phone, String phoneModel) throws Exception {
		TwistUtils.setDelay(8);
		if (phone.equalsIgnoreCase("APPLE")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Apple"));
			activationPhoneFlow.getBrowser().link(phoneModel).click();
		} else if (phone.equalsIgnoreCase("Samsung")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Samsung"));
			activationPhoneFlow.getBrowser().link(phoneModel).click();
		} else if (phone.equalsIgnoreCase("ZTE")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.ZTE"));
			activationPhoneFlow.getBrowser().link(phoneModel).click();
		} else if (phone.equalsIgnoreCase("LG")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.LG"));
			activationPhoneFlow.getBrowser().link(phoneModel).click();
		}  else if (phone.equalsIgnoreCase("Huawei")) {
			activationPhoneFlow.selectCheckBox(props.getString("Brand.Huawei"));
			activationPhoneFlow.getBrowser().link(phoneModel).click();
		} else {
			throw new IllegalArgumentException("Unknown phone type: " + phone);
		}
	}
	

	public void selectSortType(String sortBy) throws Exception {
		
		TwistUtils.setDelay(4);
		activationPhoneFlow.chooseFromSelect("orderBy", sortBy);
		
	}

	public void sortByPriceRange(String price) throws Exception {
		
		if (price.equalsIgnoreCase("Less than $50")) {
			activationPhoneFlow.selectCheckBox("facet_checkbox401234232534812532534841");
		} else if (price.equalsIgnoreCase("Between $50 and $100")) {
			activationPhoneFlow.selectCheckBox("facet_checkbox4012353483249484812532575741");
		} else if (price.equalsIgnoreCase("Between $100 and $300")) {
			activationPhoneFlow.selectCheckBox("facet_checkbox40123494848325148481253250575741");
		} else if (price.equalsIgnoreCase("More than $300")) {
			activationPhoneFlow.selectCheckBox("facet_checkbox40123514848324212541");
		}
		
//		Browser browser = activationPhoneFlow.getBrowser();
//		browser.byId("low_price_input").setValue(low);
//		browser.byId("high_price_input").setValue(high);
//		activationPhoneFlow.pressButton("GO");
	}

	public void checkIsShown(String expectedPhone) throws Exception {
				
		String actualPhone = activationPhoneFlow.getBrowser().heading2("h1 hidden-xs").getText();
		boolean success = expectedPhone.equalsIgnoreCase(actualPhone);
		String errMess = "Found phone: \"" + actualPhone + "\" instead of expected phone: " + expectedPhone;
		Assert.assertTrue(errMess, success);
	
		
//		Browser browser = activationPhoneFlow.getBrowser();
//		String foundPhone = browser.link("/WC_CatalogEntryDBThumbnailDisplayJSPF_\\d{5}_link_9b/").getText();
//		boolean success = expectedPhone.equalsIgnoreCase(foundPhone);
//		String errMess = "Found phone: \"" + foundPhone + "\" instead of expected phone: " + expectedPhone;
//		Assert.assertTrue(errMess, success);
	}
	
	public void checkPhoneIsShown(String expectedPhone) throws Exception {
		
		Browser browser = activationPhoneFlow.getBrowser();
		String foundPhone = browser.link("/WC_CatalogEntryDBThumbnailDisplayJSPF_\\d{6}_link_9b_name/").getText();
		System.out.println(foundPhone);
		boolean success = foundPhone.contains(expectedPhone);
		System.out.println(success);
		String errMess = "Found phone: \"" + foundPhone + "\" instead of expected phone: " + expectedPhone;
		Assert.assertTrue(errMess, success);
		
	}

	public void goToPhonesPage() throws Exception {
		activationPhoneFlow.navigateTo(OnTotalWirelessHomePage.PHONES_URL);
	}
	
	public void goToHotspotPage() throws Exception {
		activationPhoneFlow.navigateTo(OnTotalWirelessHomePage.HOTSPOT_URL);
	}

}