package com.tracfone.twist.MyAccount;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import net.sf.sahi.client.Browser;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class CreateAccount {
	private MyAccountFlow myAccountFlow;
	private ESNUtil esnUtil;
	private static final String NEW_STATUS = "New";
	private static final String PAST_DUE_STATUS = "Past Due";
	private ResourceBundle props = ResourceBundle.getBundle("GS");

	public CreateAccount() {

	}

	public void logInDependingOnStatus(String status) throws Exception {
		//need to add
	}

	public void createNewAccountDependingOnStatus(String status) throws Exception {
		if (NEW_STATUS.equalsIgnoreCase(status)) {
			createNewAccount();
		}	
	}
	public void createNewAccount() throws Exception {
		TwistUtils.setDelay(5);
		String randomEmail = TwistUtils.createRandomEmail();
		String password = "tracfone";

		ESN currEsn = esnUtil.getCurrentESN();
		currEsn.setEmail(randomEmail);
		currEsn.setPassword(password);
		System.out.println("Email:" + randomEmail);
		if (myAccountFlow.buttonVisible(props.getString("Account.Create")) || myAccountFlow.submitButtonVisible(props.getString("Account.Create"))){
			pressButton(props.getString("Account.Create"));
		}
		myAccountFlow.typeInTextField(props.getString("Account.EmailText"), randomEmail);
		myAccountFlow.typeInPasswordField(props.getString("Account.PassText"), password);
		myAccountFlow.typeInPasswordField(props.getString("Account.ConfirmPassText"), password);
		myAccountFlow.typeInTextField(props.getString("Account.Dob"), "01/01/1985");
		myAccountFlow.typeInTextField(props.getString("Account.SecPinText"), "1234");
		pressButton(props.getString("Account.Submit"));
		TwistUtils.setDelay(20);
	}
	
	private void pressButton(String button) {
		if (myAccountFlow.buttonVisible(button)) {
			myAccountFlow.pressButton(button);
		} else {
			myAccountFlow.pressSubmitButton(button);
		}
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void clickMyAccountSignIn() throws Exception {
		myAccountFlow.navigateTo(props.getString("GS.HomePage"));
		myAccountFlow.clickLink(props.getString("Account.MyAccount"));	
	}

	public void enterVerificationCode(String Code) throws Exception {
		myAccountFlow.typeInTextField("secCode",Code);
		myAccountFlow.clickStrongMessage(props.getString("Activate.continue1"));
	
	}
	
	public void enterMinAttachedWithNoEmail() throws Exception {
		myAccountFlow.typeInTextField(props.getString("BuyAirtime.InputMin"),esnUtil.getCurrentESN().getMin());
		myAccountFlow.clickStrongMessage(props.getString("Activate.continue"));
	}

	public void verifyAccountCreated() throws Exception {
		Assert.assertTrue(myAccountFlow.h1Visible("My Account Summary"));
	}
}
