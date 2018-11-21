package com.tracfone.twist.ShopSims;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class ShopSims {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;
	private ESNUtil esnUtil;
	private ResourceBundle rb = ResourceBundle.getBundle("B2B");

	public ShopSims() {

	}

	public void goToSims() throws Exception {
		activationPhoneFlow.clickLink("BYOD");
	}

	public void selectSIMAndCheckAvailabilityIn(String sim , String zipCode) throws Exception {
		//sim ="http://dpisitcist.tracfone.com:10039/wps/contenthandler/dav/content/libraries/wcm.library.phones/components/"+sim+"/wcm/comps/image/cw_ecom_med1.png";
		activationPhoneFlow.clickLink(sim);
		activationPhoneFlow.clickLink("Check Availability");
		activationPhoneFlow.typeInTextField("zip_code", zipCode);
		activationPhoneFlow.clickLink("Check");
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void selectPlanAndQuantity(String plan, String qty) throws Exception {
		activationPhoneFlow.chooseFromSelect("secondCatEntryDropDown", plan);
		activationPhoneFlow.typeInTextField("left marginLeft5 marginTop5 txt-center", qty);
		activationPhoneFlow.clickLink("Add to Cart");
	}

	public void selectSIMAndSIMTypeAndCheckAvailabilityIn(String carrier, String simType, String zipCode) throws Exception {
		if ("T-Mobile".equalsIgnoreCase(carrier)) {
			activationPhoneFlow.selectRadioButton(rb.getString("B2B.TmoSIMRadio"));
		} else {
			activationPhoneFlow.selectRadioButton(rb.getString("B2B.AttSIMRadio"));
		}
		activationPhoneFlow.clickLink(simType);
		activationPhoneFlow.clickLink("Check Availability");
		activationPhoneFlow.typeInTextField("zip_code", zipCode);
		activationPhoneFlow.clickLink("Check");
	}

}