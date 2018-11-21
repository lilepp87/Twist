package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import org.junit.Assert;

import com.tracfone.twist.activation.ActivatePhone;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RefillUsingAServiceCard {

	private RedemptionFlow redemptionFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String NOW = "Now";
	
	private Properties properties = new Properties();

	public RefillUsingAServiceCard() {

	}
	
	public void goToRefill() throws Exception {
		redemptionFlow.clickLink(properties.getString("refillNow"));
	}

	public void enterPhoneNumberAndPinCard(String partNumber, String pinCard)
			throws Exception {		
		ESN activeEsn = new ESN(partNumber, phoneUtil.getActiveEsnByPartNumber(partNumber));// esnUtil.popRecentActiveEsn(partNumber);
		esnUtil.setCurrentESN(activeEsn);
		enterPhoneAndPinCard(pinCard);
	}
	
	public void enterPhoneAndPinCard(String pinCard) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		phoneUtil.clearOTAforEsn(esn.getEsn());
		String minNumber = phoneUtil.getMinOfActiveEsn(esn.getEsn());
		String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
		
		redemptionFlow.typeInTextField("mobilenmbr", minNumber);
		redemptionFlow.typeInTextField("airtime_pin", newPin);
		redemptionFlow.pressSubmitButton("default_submit_btn");
		storeRedeemData(esn, newPin);
		TwistUtils.setDelay(10);
		dismissCdmaRoaming(pinCard);
	}
	
	public void dismissCdmaRoaming(String pinPart) {
		boolean isCDMA = esnUtil.getCurrentESN().getEsn().length() != 15;
		if (isCDMA && (pinPart.endsWith("60ILD") || pinPart.endsWith("ILD60"))) {
			String warningMess = redemptionFlow.getBrowser().div("window[1]").getText();
			String warningMess2 = redemptionFlow.getBrowser().div("window").getText();
			String warningMess3 = redemptionFlow.getBrowser().div("window[2]").getText();

			boolean foundWarning = warningMess.contains(properties.getString("foundWarning"));
			boolean foundWarning2 = warningMess2.contains(properties.getString("foundWarning"));
			boolean foundWarning3 = warningMess3.contains(properties.getString("foundWarning"));
			Assert.assertTrue("Didn't find warning for $60 plus for cdma", foundWarning || foundWarning2 || foundWarning3);
			redemptionFlow.pressSubmitButton(properties.getString("continue") + "[1]");
		}
	}
	
	public void confirmRefill(String enrollType) throws Exception {
//		Assert.assertTrue(redemptionFlow.h2Visible("Información Importante"));
		ESN esn = esnUtil.getCurrentESN();
		TwistUtils.setDelay(15);
		if (NOW.equalsIgnoreCase(enrollType)) {
			if (redemptionFlow.submitButtonVisible(properties.getString("addNow"))) {
				redemptionFlow.pressSubmitButton(properties.getString("addNow"));
			}
			esn.setActionType(6);
		} else {
			if (redemptionFlow.submitButtonVisible("add_queue_submit")) {
				redemptionFlow.pressSubmitButton("add_queue_submit");
			}
			esn.setActionType(401);
		}
		esn.setActionType(401);
		Assert.assertTrue(redemptionFlow.h2Visible(properties.getString("ORDERSUMARY")));
		redemptionFlow.pressSubmitButton(properties.getString("continue"));
		phoneUtil.checkRedemption(esn);
	}
	
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("TC Refill with PIN");
		esn.setPin(pin);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void clickOnRefill() throws Exception {
		//This is inside 'My Account' used for $10 ILD Redemption
		redemptionFlow.clickLink(properties.getString("refill"));
	}

	public void clickOnAddAServiceCardNowSpanish() throws Exception {
		redemptionFlow.clickLink(properties.getString("addServicePlan"));
	}

	public void enterPinCardFor10ILD(String pinCard) throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
		String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
		
		System.out.println("Active ESN: " + activeEsn + " minNumber: " + minNumber + " newPin: " + newPin);
		redemptionFlow.typeInTextField("airtime_pin", newPin);
		redemptionFlow.pressSubmitButton(properties.getString("go"));
		storeRedeemData(activeEsn, newPin);
	}

	public void confirmRefillFor10ILD() throws Exception {
		TwistUtils.setDelay(35);
		Assert.assertTrue(redemptionFlow.h2Visible(properties.getString("ORDERSUMARY")));
		redemptionFlow.pressSubmitButton(properties.getString("continue"));
		ESN esn = esnUtil.getCurrentESN();
		esn.setActionType(401);
		phoneUtil.checkRedemption(esn);
	}

	public void clickOnAddAServiceCardToReserveSpanish() throws Exception {
		redemptionFlow.clickLink("Añadir un Plan de Servicio a Mi Reserva™"); 
		}

	public void checkForManageMyReserveLink() throws Exception {
		//For CR26966
		Assert.assertTrue(redemptionFlow.linkVisible("Administrar los planes que tienes actualmente en Mi Reserva™"));
	}

	public void enterPinCardForILDReserve(String pinCard) throws Exception {
		ESN activeEsn = esnUtil.getCurrentESN();
		String minNumber = phoneUtil.getMinOfActiveEsn(activeEsn.getEsn());
		String newPin = phoneUtil.getNewPinByPartNumber(pinCard);
		
		System.out.println("Active ESN: " + activeEsn + " minNumber: " + minNumber + " newPin: " + newPin);
		redemptionFlow.typeInTextField("airtime_pin_queue", newPin);
		redemptionFlow.pressSubmitButton("Ir");
		storeRedeemData(activeEsn, newPin);
	}
	
}