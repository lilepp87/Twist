package com.tracfone.twist.priviledge;

// JUnit Assert framework can be used for verification

import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;

public class ChangePermissionAndPriviledges {
	
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	public void goToAdminTab() throws Exception {
		myAccountFlow.clickLink("Admin");
		myAccountFlow.clickLink("User Priviledge Classes");
	}
	
	public void searchForUserAndChangePriviledgesTo(String privClass) throws Exception {
		pressButton("Advanced");
		myAccountFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId1:value00/").setValue("itquser3");
		pressButton("Basic");
		pressButton("Search");
		myAccountFlow.getBrowser().cell("ITQUSER3").click();
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:resId1:\\d:soc\\d/", privClass);
		pressButton("Update");
	}

	public void goToPermissionAndChangeStatusToRevokedForClass(String permissionName, String privClass) 
			throws Exception {
		myAccountFlow.clickLink("Permissions");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId1:value10/", privClass.substring(0, 1));
		pressButton("Search");
		myAccountFlow.getBrowser().cell(privClass).click();
		myAccountFlow.getBrowser().cell(permissionName).click();
		String checkDisabled = myAccountFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot7/").getText();
		if (!checkDisabled.equalsIgnoreCase("Revoked")) {
			myAccountFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot7/");
		}
	}

	public void loginAsOtherUser() throws Exception {
		myAccountFlow.clickLink("Logout");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "itquser3");
		myAccountFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abc123");
		pressButton("Login");
	}
	
	public void checkForActivationFlow() throws Exception {
		myAccountFlow.getBrowser().cell("Transactions").click();
		myAccountFlow.clickLink("Activation");
		Assert.assertTrue(myAccountFlow.h2Visible("Access Restricted"));
	}
	
	public void resetSettings() throws Exception {
		myAccountFlow.clickLink("Admin");
		myAccountFlow.clickLink("Permissions");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:qryId1:value10/", "P");
		pressButton("Search");
		myAccountFlow.getBrowser().cell("Portability - Port In").click();
		myAccountFlow.getBrowser().cell("ACTIVATION_PG").click();
		String checkDisabled = myAccountFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot7/").getText();
		if (!checkDisabled.equalsIgnoreCase("Granted")) {
			myAccountFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot7/");
		}
		goToAdminTab();
		searchForUserAndChangePriviledgesTo("System Administrator");
	}
	
	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}
