package com.tracfone.twist.byop;

// JUnit Assert framework can be used for verification

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateContact {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;

	public CreateContact() {

	}

	public void clickOnNewContact() throws Exception {
		myAccountFlow.clickLink("Incoming Call");
		clickButton("New Contact Account");
	}

	public void createNewContactForBrandZipcode(String brand, String zipCode) throws Exception {
		String email = TwistUtils.createRandomEmail();
		esnUtil.setCurrentBrand(brand);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:soc3/", brand);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it14/", zipCode);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it17/", email);
		if(brand.equalsIgnoreCase("total_wireless")){
			myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it22/", "1234");
		}
		clickButton("Create Contact");
	}

	private void clickButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}