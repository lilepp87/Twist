package com.tracfone.twist.myAccount;

import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class ManageReserve {

	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;

	public ManageReserve() {

	}

	public void addCardToReserve() throws Exception {
		TwistUtils.setDelay(5);
		ESN esn = esnUtil.getCurrentESN();
		if (buttonVisible("Redeem")) {
			pressButton("Redeem");
			esn.setActionType(401);
		} else if (buttonVisible("Redeem Later") && isEnabled("Redeem Later")) {
			pressButton("Redeem Later");
			esn.setActionType(401);
		} else if (buttonVisible("Add Later") && isEnabled("Add Later")) {
			pressButton("Add Later");
			esn.setActionType(401);
		} else if (buttonVisible("Add to Reserve") && isEnabled("Add to Reserve")) {
			pressButton("Add to Reserve");
			esn.setActionType(401);
		} else if (buttonVisible("Add Later[1]") && isEnabled("Add Later[1]")) {
			pressButton("Add Later[1]");
			esn.setActionType(401);
		} else {
			throw new AssertionError("Could not find redeem button");
		}
		TwistUtils.setDelay(15);
		pressButton("Refresh");
		phoneUtil.checkRedemption(esn);
		esnUtil.addBackActiveEsn(esn);

	}

	private boolean buttonVisible(String button) {
		return redemptionFlow.buttonVisible(button) || redemptionFlow.submitButtonVisible(button);
	}

	private boolean isEnabled(String buttonType) {
		if (redemptionFlow.getBrowser().submit(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
			return false;
		} else {
			return true;
		}
	}

	public void redeemQueuedCardFromReserve() throws Exception {
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
		pressButton("Refresh");
		redemptionFlow.clickLink("Redemption");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
		pressButton("Reserved Pins");
		String queuePin = redemptionFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot5/").getText();
		redemptionFlow.clickLink(queuePin);
		pressButton("Validate");
		TwistUtils.setDelay(5);
		Assert.assertTrue(redemptionFlow.cellVisible("Valid Pin") || redemptionFlow.cellVisible("Valid PIN"));
		if (buttonVisible("Add Now") && isEnabled("Add Now"))
			pressButton("Add Now");
		else if (buttonVisible("Add Now[1]") && isEnabled("Add Now[1]")) 
			pressButton("Add Now[1]");
	}

	private void pressButton(String buttonType) {
		if (redemptionFlow.submitButtonVisible(buttonType)) {
			redemptionFlow.pressSubmitButton(buttonType);
		} else {
			redemptionFlow.pressButton(buttonType);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
}
