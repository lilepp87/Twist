package com.tracfone.twist.redemption;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.TwistUtils;

public class EnterPayment {

	private PaymentFlow paymentFlow;
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	private ESNUtil esnUtil;

	public EnterPayment() {

	}

	public void enterCreditCard(String cardType) throws Exception {
		String card;
		if (cardType.equalsIgnoreCase("Master Card")) {
			card = TwistUtils.generateCreditCard("Mastercard");
		} else {
			card = TwistUtils.generateCreditCard(cardType);
		}
		TwistUtils.setDelay(20);
		if (!paymentFlow.textboxVisible(rb.getString("sm.AccountNumberText"))) {
			TwistUtils.setDelay(100);
		}
		paymentFlow.typeInTextField(rb.getString("sm.AccountNumberText"), card);

		if (cardType.equalsIgnoreCase("American Express")) {
			paymentFlow.typeInTextField(rb.getString("sm.AccCVVText"), rb.getString("sm.amexcvvnumber"));
		} else {
			paymentFlow.typeInTextField(rb.getString("sm.AccCVVText"), rb.getString("sm.cvvnumer"));
		}

		paymentFlow.chooseFromSelect(rb.getString("sm.AccExpMonthSelectText"), rb.getString("sm.expmonth"));
		paymentFlow.chooseFromSelect(rb.getString("sm.AccExpYearSelectText"), rb.getString("sm.expyear"));
		paymentFlow.chooseFromSelect(rb.getString("sm.StateSelectText"), rb.getString("sm.state"));
		// paymentFlow.selectCheckBox(PaymentFlow.BuyAirtimeStraighttalkWebFields.UseAccountInformationCheck.name);
		paymentFlow.typeInTextField("firstName", "TwistFirstName");
		paymentFlow.typeInTextField("lastName", "TwistLastName");
		paymentFlow.typeInTextField("address1", "1295 Charleston Road");
		paymentFlow.typeInTextField("zip", "94043");
		paymentFlow.typeInTextField("city", "Mountain View");
		paymentFlow.chooseFromSelect(rb.getString("sm.StateSelectText"), rb.getString("sm.state"));
		if (paymentFlow.checkboxVisible("optIn") && !paymentFlow.getBrowser().checkbox("optIn").checked()) {
			paymentFlow.selectCheckBox("optIn");
		}
		paymentFlow.pressSubmitButton("default_submit_btn_first");
		
		TwistUtils.setDelay(25);
		
		esnUtil.getCurrentESN().setBuyFlag(true);
	}

	public void enterCreditCardDependsOnStatus(String cardType, String status) throws Exception {
		if (paymentFlow.selectionboxVisible(rb.getString("sm.CCDropdown"))) {
			if (cardType.equalsIgnoreCase("American Express")) {
				paymentFlow.typeInTextField(rb.getString("sm.AccCVVText"), rb.getString("sm.amexcvvnumber"));
			} else {
				paymentFlow.typeInTextField(rb.getString("sm.AccCVVText"), rb.getString("sm.cvvnumer"));
			}

			paymentFlow.selectCheckBox("optIn");
			paymentFlow.pressSubmitButton(rb.getString("sm.continue"));
			esnUtil.getCurrentESN().setBuyFlag(true);
		} else {
			enterCreditCard(cardType);
			TwistUtils.setDelay(3);
			if (paymentFlow.textboxVisible(rb.getString("sm.AccountNumberText"))) {
				enterCreditCard(cardType);
			}
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

}