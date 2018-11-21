package com.tracfone.twist.impl.eng.myAccount;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.PhoneUtil;

public class AddPhoneToMyNewAccount {

	private static PhoneUtil phoneUtil;
	private MyAccountFlow myAccountFlow;

	public AddPhoneToMyNewAccount() {

	}

	public void enterEsnAndNickname(String partNumber) throws Exception {
		String newEsn = phoneUtil.getNewEsnByPartNumber(partNumber);
		System.out.println("NEW ESN:");

		myAccountFlow.clickLink(MyAccountFlow.MyAccountTracfoneWebFields.AddPhoneLink.name);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.EsnText.name, newEsn);
		myAccountFlow.typeInTextField(MyAccountFlow.MyAccountTracfoneWebFields.NickNameText.name, "Twist Nickname");
		myAccountFlow.pressSubmitButton(MyAccountFlow.MyAccountTracfoneWebFields.AddButton.name);
	}

	public void completeTheProcess() throws Exception {
		Assert.assertTrue(myAccountFlow.divVisible(MyAccountFlow.MyAccountTracfoneWebFields.AddNewPhoneMessage.name));
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

}