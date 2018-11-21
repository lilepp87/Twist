package com.tracfone.twist.csr;

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class CSR {

	private ActivationReactivationFlow activationPhoneFlow;
	private ResourceBundle rb = ResourceBundle.getBundle("B2B");
	
	public CSR() {
		
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void searchUserBasedOn(String searchBy, String searchValue) throws Exception {
		activationPhoneFlow.chooseFromSelect("searchoption", searchBy);
		activationPhoneFlow.typeInTextField("search_value", searchValue);
		activationPhoneFlow.clickLink("userSearchSection");
		Assert.assertTrue(activationPhoneFlow.linkVisible("Twist FirstName"));
	}

	public void selectAndVerifyUser() throws Exception {
		activationPhoneFlow.clickLink("Twist LastName");
/*		activationPhoneFlow.clickLink("Correct answer"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.CorrectAns"));
		TwistUtils.setDelay(10);
		/*browser.link("Payment Information").click();
		browser.select("expMonth").choose("06");
		browser.select("expYear").choose("2021");
		browser.label("Make this my preferred payment method").click();
		browser.checkbox("ccIsDefault").click();
		browser.div("row checkBox[1]").click();
		browser.checkbox("ccMemberId").click();
		browser.textbox("nickName").setValue("Twist NickName");
		browser.textbox("address1").setValue("1295 Charleston Road");
		browser.div("WC_AddressBookAddressEntryFormf_div_6a").click();
		browser.select("phone1Type").choose("Mobile");
		browser.div("Alternate Phone Phone Type SelectOfficeMobileHome").click();
		browser.checkbox("preferredShippingCheckbox").click();
		browser.checkbox("preferredBillingCheckbox").click();
		browser.checkbox("memberId").click();
		browser.image("shadow-createUserProfile.png[2]").click();
		browser.link("SAVE").click();*/
	}

}