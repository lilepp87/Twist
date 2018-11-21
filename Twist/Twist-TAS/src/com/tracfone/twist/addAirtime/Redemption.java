
package com.tracfone.twist.addAirtime;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;

import java.util.*;
import net.sf.sahi.client.Browser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.tracfone.twist.flows.tracfone.RedemptionFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.tossutilops.TossUtil;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.RandomACHGenerator;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.utils.ServiceUtil.ResourceType;

public class Redemption {

	private RedemptionFlow redemptionFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private static final String CONFIRM_MSG = "Your Plan was successfully added. You must keep your phone "
			+ "turned ON to receive your benefits.If you are enrolled in AutoReUp, your AutoReUp service will"
			+ " not go into effect until your phone reaches its Last Day of Service indicated below. Otherwise,"
			+ " please make sure to ReUp your service before this date. As a reminder, we will send you a text message "
			+ "or email prior to your AutoReUp charge.";
	private String error_msg;
	public String planAdding;
	private String last4digits;
	private String currentBrand;
	private String accountNum;
	private ServiceUtil serviceUtil;
	private static Logger logger = LogManager.getLogger(Redemption.class.getName());
	
	public Redemption() {

	}
	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}
	private void storeRedeemData(ESN esn, String pin) {
		esn.setBuyFlag(false);
		esn.setRedeemType(false);
		esn.setTransType("TAS refill with pin");
		esn.setPin(pin);
		esn.setActionType(401);
	}

	private void storePurchaseData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("TAS Buy a Plan");
		esn.setActionType(401);
	}

	public void enterPin(String pinPart) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		logger.info("PIN:"+newPin);
		enterNewPin(newPin);
		esnUtil.getCurrentESN().setPin(newPin);
	}

	private void enterNewPin(String newPin) {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
		if(newPin.equalsIgnoreCase("reserved")){
			pressButton("Reserved Pins");
			String queuePin = redemptionFlow.getBrowser().link("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot5/").getText();
			redemptionFlow.clickLink(queuePin);
		}else{
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("net10") || esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone") || esnUtil.getCurrentBrand().equalsIgnoreCase("safelink")){
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:rit1::content/", newPin);
			}else{
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
			}
		}
		pressButton("Validate");
	    String error_msg1=redemptionFlow.getBrowser().div("d1::msgDlg::_cnt").getText() ;
		if(redemptionFlow.linkVisible("Close[1]")){
			redemptionFlow.clickLink("Close[1]");
		}
		if(redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").isVisible()){
			error_msg= redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg+";"+error_msg1, (redemptionFlow.cellVisible("Valid PIN") || redemptionFlow.cellVisible("Valid Pin")));
		storeRedeemData(esnUtil.getCurrentESN(), newPin);
	}

	public void goToRedemption() throws Exception {
		TwistUtils.setDelay(5);
		//pressButton("Refresh");
		redemptionFlow.clickLink("Transactions");
		redemptionFlow.clickLink("Redemption");
	}

	public void selectPinAndAddCreditCard(String pinPart, String cardType) throws Exception {
		String brandname = esnUtil.getCurrentBrand();
		if(brandname.equalsIgnoreCase("wfm") || brandname.equalsIgnoreCase("simple_mobile")){
			redemptionFlow.clickLink("Purchase Airtime");
			redemptionFlow.typeInTextField("/r2:\\d:r\\d:\\d:it2/", esnUtil.getCurrentESN().getZipCode());
			TwistUtils.setDelay(2);
			pressButton("Refresh");
			TwistUtils.setDelay(2);
		}
		
		pressButton("Refresh");
		if(brandname.equalsIgnoreCase("total_wireless") || brandname.equalsIgnoreCase("go_smart")){
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
	
		} else {
			redemptionFlow.clickLink("Purchase Airtime");
			
			
		}
		
		redemptionFlow.clickCellMessage(pinPart);
		esnUtil.getCurrentESN().setPinPartNumber(pinPart);
		if (brandname.equalsIgnoreCase("total_wireless") || brandname.equalsIgnoreCase("go_smart")) {
			pressButton("Add Plan");
		} else {
			pressButton("Add");
		}
		if (brandname.equalsIgnoreCase("net10") && cardType.equalsIgnoreCase("ACH")) {
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		}
		if (brandname.equalsIgnoreCase("total_wireless") || brandname.equalsIgnoreCase("go_smart")) {
		//	pressButton("Add New Payment Method");
		} else if (brandname.equalsIgnoreCase("wfm") || brandname.equalsIgnoreCase("simple_mobile")) {
		//	TwistUtils.setDelay(3);
			pressButton("Add");
			pressButton("Checkout");
		//	pressButton("Add New Payment");
			
		}  else {
			//pressButton("Add New Payment");
		}
		enterCard(cardType);
		if (buttonVisible("Refresh[1]")) {
			pressButton("Refresh[1]");
		} else {
			pressButton("Refresh");
		}
	}

	public void addCreditCard(String cardType)  throws Exception {
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("net10") && cardType.equalsIgnoreCase("ACH")) {
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		}
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) {
		//	pressButton("Add New Payment Method");
		} else if (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") ) {
			TwistUtils.setDelay(3);
		//	pressButton("Add");
		//	pressButton("Checkout");
		//	pressButton("Add New Payment");
			
		}  else {
		//	pressButton("Add New Payment");
		}
		enterCard(cardType);
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")) {
			pressButton("Refresh[1]");
		} else {
			pressButton("Refresh");
		}
	}
	public void enterCard(String cardType) throws Exception {
		TwistUtils.setDelay(5);
		ESN esn= esnUtil.getCurrentESN();
		String cardNumber = null;
		Browser popup2 = redemptionFlow.getBrowser().popup("/.*/");
		
			if(true){
			/*if (popup2.checkbox("t1:0:sbc1::content").isVisible()) {
				popup2.checkbox("t1:0:sbc1::content").click();
			}
			if (popup2.checkbox("t1:1:sbc1::content").isVisible()) {
				popup2.checkbox("t1:1:sbc1::content").click();
			}
			if (popup2.checkbox("t1:2:sbc1::content").isVisible()) {
				popup2.checkbox("t1:2:sbc1::content").click();
			}
			TwistUtils.setDelay(5);
			if (popup2.button("Continue").isVisible()) {
				popup2.button("Continue").click();
			} else if (popup2.submit("Continue").isVisible()){
				popup2.submit("Continue").click();
			}
			TwistUtils.setDelay(2);
			if (popup2.button("Close").isVisible()) {
				popup2.button("Close").click();
			} else {
				popup2.submit("Close").click();
			}*/
			
			serviceUtil.addPaymentSourceToAccountforTAS(cardType,phoneUtil.getEsnAttribute("web_user_objid", esnUtil.getCurrentESN().getEsn()));
			storePurchaseData(esnUtil.getCurrentESN());
		
		}else{
			
		
		
		
		
		//CANDA credit cards
	/*	  String country ="USA";
		if(cardType.startsWith("Can")){
			country = cardType.substring(6);
			cardType= cardType.substring(7, cardType.length());
			logger.info(country);
			logger.info(cardType);
			
		}*/
//		cardNumber = TwistUtils.generateCreditCard(cardType);
//		last4digits = cardNumber.substring(cardNumber.length()-4);
//		esn.setLastFourDigits(last4digits);
//		popup2 = redemptionFlow.getBrowser().popup("/.*/");
//		TwistUtils.setDelay(5);
//		String name = popup.fetch("window.name");
//		while (name.endsWith("1")) {
//			TwistUtils.setDelay(2);
//			popup = redemptionFlow.getBrowser().popup("/.*/");
//			name = popup.fetch("window.name");
//			logger.info("!" + name);
//		}*/
//
//		Browser popup2 = redemptionFlow.getBrowser().popup(name);
				if (popup2.checkbox("t1:0:sbc1::content").isVisible()) {
			popup2.checkbox("t1:0:sbc1::content").click();
		}
		if (popup2.checkbox("t1:1:sbc1::content").isVisible()) {
			popup2.checkbox("t1:1:sbc1::content").click();
		}
		if (popup2.checkbox("t1:2:sbc1::content").isVisible()) {
			popup2.checkbox("t1:2:sbc1::content").click();
		}
		TwistUtils.setDelay(5);
		if (popup2.button("Continue").isVisible()) {
			popup2.button("Continue").click();
		} else if (popup2.submit("Continue").isVisible()){
			popup2.submit("Continue").click();
		}
		TwistUtils.setDelay(2);
		if (!cardType.equalsIgnoreCase("ACH")) {
			cardNumber = TwistUtils.generateCreditCard(cardType);
			last4digits = cardNumber.substring(cardNumber.length() - 4);
			esn.setLastFourDigits(last4digits);
			esn.setPaymentMethod("CC");
			TwistUtils.setDelay(5);
			popup2.textbox("account_number::content").setValue(cardNumber);
			TwistUtils.setDelay(5);
			popup2.div("pfl2").click();
			TwistUtils.setDelay(5);
			popup2.div("pfl2").doubleClick();
			popup2.select("soc2").choose("07");
			popup2.select("soc3").choose("2021");
		} else {
			// ACH details
			esn.setPaymentMethod("ACH");
			String routingNum = RandomACHGenerator.getRoutingNumber();
			String accountNum = RandomACHGenerator.getAccountNumber();
			esn.setAccountNumber(accountNum);
			popup2.radio("sor1:_1").click();
			popup2.select("soc6::content").choose("Savings");
			popup2.textbox("routing_number::content").setValue(routingNum);
			popup2.textbox("bank_account_number::content").setValue(accountNum);
			TwistUtils.setDelay(2);

		}
		popup2.textbox("it7").setValue("TwistFirstName");
		popup2.textbox("it8").setValue("TwistLastName");
		popup2.select("soc4").choose("USA");
		TwistUtils.setDelay(2);
		popup2.textbox("it5").setValue("1295 charleston road");
		popup2.textbox("it4").setValue("94043");
		popup2.textbox("it9").setValue("2345678900");
		TwistUtils.setDelay(2);
		popup2.textbox("it9").setValue("2345678900");
		popup2.checkbox("sbc1::content").check();
		if (popup2.button("Register Payment").isVisible()) {
			popup2.button("Register Payment").click();
		} else {
			popup2.submit("Register Payment").click();
		}
		TwistUtils.setDelay(3);
		storePurchaseData(esnUtil.getCurrentESN());
		if(popup2.span("Required value missing").isVisible()){
			
			popup2.textbox("account_number::content").setValue(cardNumber);
			TwistUtils.setDelay(3);
			if (popup2.button("Register Payment").isVisible()) {
				popup2.button("Register Payment").click();
			} else {
				popup2.submit("Register Payment").click();
			}
			
		}
		}
	}

	public void selectCardEnterCvv(String cvv) throws Exception {
		if (buttonVisible("Refresh")) {
			pressButton("Refresh");
		} 
		
		if (buttonVisible("Refresh Credit Card")) {
			pressButton("Refresh Credit Card");
		} else if (buttonVisible("Refresh Payment Methods")) {
			pressButton("Refresh Payment Methods");
		} else if (buttonVisible("Refresh Payment")) {
			pressButton("Refresh Payment");
		}else if (buttonVisible("Refresh Payment[1]")) {
			pressButton("Refresh Payment[1]");
		}
		
		if(esnUtil.getCurrentESN().getPaymentMethod().equalsIgnoreCase("CC")){
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
				redemptionFlow.getBrowser().select("/r2:\\d:r\\d:\\d:soc3::content/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
				redemptionFlow.typeInTextField("/r2:\\d:r\\d:\\d:it4::content/", cvv);
			}else{
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?(r\\d:\\d:)?r\\d:\\d:soc1/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r2:\\d:r\\d:\\d((:r\\d:\\d:it1)|(:it1a)|(:it30))/", cvv);
			}
		}else{
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc1/").choose(("/."+esnUtil.getCurrentESN().getAccountNumber()+".*/"));
		}
		storePurchaseData(esnUtil.getCurrentESN());
	}

	public void checkRedemption() throws Exception {
		TwistUtils.setDelay(4);
		ESN esn = esnUtil.getCurrentESN();
		if (buttonVisible("Redeem")) {
			pressButton("Redeem");
			esn.setActionType(401);
		} else if (buttonVisible("Redeem Now") && isEnabled("Redeem Now")) {
			pressButton("Redeem Now");
			esn.setActionType(6);
		} else if (buttonVisible("Redeem Later") && isEnabled("Redeem Later")) {
			pressButton("Redeem Later");
			esn.setActionType(401);
		} else if (buttonVisible("Add Now") && isEnabled("Add Now")) {
			pressButton("Add Now");
			esn.setActionType(6);
		} else if (buttonVisible("Add to Reserve") && isEnabled("Add to Reserve")) {
			pressButton("Add to Reserve");
			esn.setActionType(401);
		} else if (buttonVisible("Add Later") && isEnabled("Add Later")) {
			pressButton("Add Later");
			esn.setActionType(401);
		} else if (buttonVisible("Apply Now") && isEnabled("Apply Now")){
			pressButton("Apply Now");
			esn.setActionType(6);
		} else if (buttonVisible("Apply on Due Date") && isEnabled("Apply on Due Date")) {
			pressButton("Apply on Due Date");
			esn.setActionType(401);
		} else if (buttonVisible("Enroll Now") && isEnabled("Enroll Now")) {
			pressButton("Enroll Now");
			esn.setActionType(6);
		} else if (buttonVisible("Redeem All") && isEnabled("Redeem All")) {
			pressButton("Redeem All");
			esn.setActionType(401);
		} else if (buttonVisible("Enroll All") && isEnabled("Enroll All")) {
			pressButton("Enroll All");
			esn.setActionType(401);
		} else if (buttonVisible("Enroll VAS") && isEnabled("Enroll VAS")) {
			pressButton("Enroll VAS");
			esn.setActionType(6);
		} else if (buttonVisible("Add Now[1]") && isEnabled("Add Now[1]")) {
			pressButton("Add Now[1]");
			esn.setActionType(6);
		} else if (buttonVisible("Add Later[1]") && isEnabled("Add Later[1]")) {
			pressButton("Add Later[1]");
			esn.setActionType(401);
		} else {
			throw new AssertionError("Could not find redeem button");
		}
		
		TwistUtils.setDelay(10);
		if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, redemptionFlow.divVisible(CONFIRM_MSG) || redemptionFlow.h2Visible("Transaction Summary") || redemptionFlow.h2Visible("Transaction Summary (Individual/Multiple Devices)") ||  redemptionFlow.h2Visible("Our records indicate you have a very recent transaction for this same item. In order to avoid duplicate charges, please wait awhile and then try to purchase it again later.") ||  redemptionFlow.cellVisible("Our records indicate you have a very recent transaction for this same item. In order to avoid duplicate charges, please wait awhile and then try to purchase it again later."));
		phoneUtil.checkRedemption(esn);
		esnUtil.addBackActiveEsn(esn);
		phoneUtil.updateLatestIGRecordforEsn(esn.getEsn());
	}
	
	
	public String checkRedemptionType() throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		String redeemType;
		if (buttonVisible("Redeem")) {
			pressButton("Redeem");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";			
		} else if (buttonVisible("Redeem Now") && isEnabled("Redeem Now")) {
			pressButton("Redeem Now");
			esn.setActionType(6);
			redeemType= "ADDNOW";
		} else if (buttonVisible("Redeem Later") && isEnabled("Redeem Later")) {
			pressButton("Redeem Later");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";
		} else if (buttonVisible("Add Now") && isEnabled("Add Now")) {
			pressButton("Add Now");
			esn.setActionType(6);
			redeemType= "ADDNOW";
		} else if (buttonVisible("Add to Reserve") && isEnabled("Add to Reserve")) {
			pressButton("Add to Reserve");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";
		} else if (buttonVisible("Add Later") && isEnabled("Add Later")) {
			pressButton("Add Later");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";
		} else if (buttonVisible("Apply Now") && isEnabled("Apply Now")) {
			pressButton("Apply Now");
			esn.setActionType(6);
			redeemType=  "ADDNOW";
		} else if (buttonVisible("Apply on Due Date") && isEnabled("Apply on Due Date")) {
			pressButton("Apply on Due Date");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";
		} else if (buttonVisible("Enroll VAS") && isEnabled("Enroll VAS")) {
			pressButton("Enroll VAS");
			esn.setActionType(6);
			redeemType = "ADDNOW";
		} else if (buttonVisible("Add Now[1]") && isEnabled("Add Now[1]")) {
			pressButton("Add Now[1]");
			esn.setActionType(6);
			redeemType= "ADDNOW";
		} else if (buttonVisible("Add Later[1]") && isEnabled("Add Later[1]")) {
			pressButton("Add Later[1]");
			esn.setActionType(401);
			redeemType= "ADDRESERVE";
		} else {
			throw new AssertionError("Could not find redeem button");
		}
		
		TwistUtils.setDelay(05);
		if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl10/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl3/").getText();
		} else if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, redemptionFlow.divVisible(CONFIRM_MSG) || redemptionFlow.h2Visible("Transaction Summary") || redemptionFlow.h2Visible("Transaction Summary (Individual/Multiple Devices)")  );
		phoneUtil.checkRedemption(esn);
		esnUtil.addBackActiveEsn(esn);
		return redeemType;
	}
	
	public void redeemCard (String pinPart) throws Exception{
		TwistUtils.setDelay(5);
		enterPin(pinPart);
		String redemption = checkRedemptionType();
		
		String points = phoneUtil.getLRPPointsforPinPart(pinPart);
		System.out.println("points for pin A" + points);
		Assert.assertTrue(redemptionFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl7/").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
		
		if("ADDRESERVE".equalsIgnoreCase(redemption)){
			AddCardFromReserve(redemption);
		}	
	}

	private boolean isEnabled(String buttonType) {
		if (redemptionFlow.submitButtonVisible(buttonType)) {
			if (redemptionFlow.getBrowser().submit(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
				return false;
			} else {
				return true;
			}
		} else {
			if (redemptionFlow.getBrowser().button(buttonType).fetch("disabled").equalsIgnoreCase("true")) {
				return false;
			} else {
				return true;
			}
		}
	
	}
	
	private boolean isTextFieldEnabled(String textFieldType) {
		if (redemptionFlow.textboxVisible(textFieldType)) {
			if (redemptionFlow.getBrowser().textbox(textFieldType).fetch("disabled").equalsIgnoreCase("true")) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	
	}
	
	private void pressButton(String buttonType) {
		if (redemptionFlow.submitButtonVisible(buttonType)) {
			redemptionFlow.pressSubmitButton(buttonType);
		} else {
			redemptionFlow.pressButton(buttonType);
		}
	}

	private boolean buttonVisible(String button) {
		return redemptionFlow.buttonVisible(button) || redemptionFlow.submitButtonVisible(button);
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		redemptionFlow = tracfoneFlows.redemptionFlow();
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void selectQuantityToAddFor(String quantity,String brand) throws Exception {
		esnUtil.setCurrentBrand(brand);
		this.currentBrand= brand;
		
		if("total_wireless".equalsIgnoreCase(brand) || "GO_SMART".equalsIgnoreCase(brand)){
			//IF TOTAL WIRELESS OT SIMPLE MOBILE
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
			redemptionFlow.clickLink("ILD");
			
		}else {  
			redemptionFlow.clickLink("Purchase Airtime");
		}
		
		add10ILDQuantity(quantity);
	}

	public void addNewCreditCard() throws Exception {
		/*if(redemptionFlow.buttonVisible("Add Payment") || redemptionFlow.submitButtonVisible("Add Payment")){
			pressButton("Add Payment");
		}else if(redemptionFlow.buttonVisible("Add New Payment") || redemptionFlow.submitButtonVisible("Add New Payment")){
			pressButton("Add New Payment");
		}else if(redemptionFlow.buttonVisible("Add New Payment Method") || redemptionFlow.submitButtonVisible("Add New Payment Method")){
			pressButton("Add New Payment Method");
		}else if (redemptionFlow.buttonVisible("Add New Credit Card") || redemptionFlow.submitButtonVisible("Add New Credit Card")){
			pressButton("Add New Credit Card");
		}else{
		//	pressButton("Add Credit Card");
		}
	*/
		} 
	

	public void goToEnrollInAutoRefill() throws Exception {
		redemptionFlow.clickLink("Transactions");
		redemptionFlow.clickLink("Enrollments");
	}

	public void enrollIntoPlan(String Plan) throws Exception {
		checkRedemption();
		Assert.assertTrue(redemptionFlow.cellVisible("Transaction Summary") 
				|| redemptionFlow.divVisible("Thank you for Enrolling in Auto-Refill!"));
		if (!Plan.equalsIgnoreCase("Exchange") && !esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone") && !(esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")|| esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")))  {
			String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
			System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
			Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
		}
		phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
	}

	public void selectCardAndEnterCvv(String cvv) throws Exception {

			if (buttonVisible("Refresh")) {
				pressButton("Refresh");
			}
			if (buttonVisible("Refresh Payment")) {
				pressButton("Refresh Payment");
			}else if (buttonVisible("Refresh Credit Cards")) {
				pressButton("Refresh Credit Cards");
			}
			if(!cvv.isEmpty()){
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc\\d/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
				//redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc2/").choose(("/."+last4digits+".*/"));
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:1:r1:\\d:it1::content/", cvv);
			}else{
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc\\d/").choose(("/."+esnUtil.getCurrentESN().getAccountNumber()+".*/"));
			}
	}

	public void chooseCardAndEnterCvv(String cvv) throws Exception {
		if (buttonVisible("Refresh[1]")) {
			pressButton("Refresh[1]");
		}else{
			pressButton("Refresh Payment");
		}
		//redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d(:soc1)/").choose(("/."+last4digits+".*/"));
		//redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:1:r1:\\d:it1a/", cvv);
		if(esnUtil.getCurrentESN().getPaymentMethod().equalsIgnoreCase("CC")){
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc2/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:1:r1:\\d:it1a::content/", cvv);
		}else{
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc2/").choose(("/."+esnUtil.getCurrentESN().getAccountNumber()+".*/"));
		}
	}

	public void add10ILDQuantity(String quantity) throws Exception {
		if (!quantity.equalsIgnoreCase("")) {

			if ("total_wireless".equalsIgnoreCase(currentBrand) ) {
				// IF TOTAL WIRELESS OR SIIMPLE MOBILE
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:it5::content/", quantity);
				pressButton("Add ILD");
			} else if ("wfm".equalsIgnoreCase(currentBrand) || "simple_mobile".equalsIgnoreCase(currentBrand)) {
				
				redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
				pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:t\\d:\\d:cb7/");
			if( "simple_mobile".equalsIgnoreCase(currentBrand)){
	
				redemptionFlow.clickCellMessage("SMNAPP0010MILD");
			}else{
	
				redemptionFlow.clickCellMessage(quantity);
			}
			
				pressButton("Add");
				pressButton("Checkout");
			} else {
				redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc1/");
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:t\\d:\\d:it2/", quantity);
			}

		}
	}

	public void checkConfirmation() throws Exception {
		TwistUtils.setDelay(10);
		pressButton("Refresh");
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, redemptionFlow.h2Visible("Transaction Summary") || redemptionFlow.h2Visible("Transaction Summary (Individual/Multiple Devices)"));
	}

	public void addUnits(){
		//Units soc7
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1/")){
			if(redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/").choose("100");
			}
		}
		
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/")){
				if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/").fetch("disabled").equalsIgnoreCase("false")){
					redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1/").setValue("100");
				}
		}
		
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/")){
			if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/").setValue("100");
			}
	}
		}
	
	public void addSms(){
		//SMS	
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc6/")){
			if(redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc6/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc6/").choose("20");
			}
		}
		
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2")){
				if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2").fetch("disabled").equalsIgnoreCase("false")){
					redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/").setValue("100");
				}		
	
		}
	}
	public void addData(){
		//DATA
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc5/")){
			if(redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc5/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc5/").choose("20");
			}
		}
		
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it10/")){
				if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it10/").fetch("disabled").equalsIgnoreCase("false")){
					redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it10/").setValue("100");
				}		
	
		}
	}
	
	public void addServiceDays(){
		//service days	soc7			
		if (redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/")) {
			if (redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/").fetch("disabled").equalsIgnoreCase("false")) {
				redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4::content/").choose("20");
			}
		}

		if (redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it10/")) {
			if (redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/").fetch("disabled").equalsIgnoreCase("false")) {
				redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/").setValue("30");
			}

		}
	}
	
	public void addServicePlan() {
		if (isSelectionEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/")) {
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/").choose("/.*?(UNL|TALK|DATA|TEXT|4G|DAY|YEAR).*?/i");
			planAdding = redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/").selectedText().toString();
			logger.info("Plan adding to phone:" + planAdding);
		}
	}

	private void addServiceDaysSurepay() {
		//Units 
				if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4a::content/")){
						if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4a::content/").fetch("disabled").equalsIgnoreCase("false")){
							redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4a::content/").setValue("20");
						}
				}
	}

	private void addDataSurepay() {
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4::content/")){
			if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4::content/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4::content/").setValue("100");
			}
	}
	}

	private void addSmsSurepay() {
		if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3::content/")){
			if(redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3::content/").fetch("disabled").equalsIgnoreCase("false")){
				redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3::content/").setValue("100");
			}
	}
	}

	private void addUnitsSurepay() {
		if (redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/")) {
			if (redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/").fetch("disabled")
					.equalsIgnoreCase("false")) {
				redemptionFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2::content/").setValue("100");
			}
		}
	}
	
	public void checkAndAddUnits( String comprepltype,String group)  {
		if (comprepltype.equalsIgnoreCase("compensation")) {
			if (group.equalsIgnoreCase("tfnonsurepay")) {
				addUnits();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("tfsurepay") || group.equalsIgnoreCase("attswitch") || group.equalsIgnoreCase("tmoswitch")) {
				addUnitsSurepay();
				addSmsSurepay();
				addDataSurepay();
				addServiceDaysSurepay();
			}
			/*if (group.equalsIgnoreCase("tfsurepay") || group.equalsIgnoreCase("attswitch") || group.equalsIgnoreCase("tmoswitch")) {
				addUnitsSurepay();
				addSmsSurepay();
				addDataSurepay();
				addServiceDaysSurepay();
			}*/
			if (group.equalsIgnoreCase("unlimited")) {
				addServiceDays();
			}
			if (group.equalsIgnoreCase("paygo")) {
				addUnits();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("staynvz")) {
				addUnits();
				addSms();
				addData();
			}
			if (group.equalsIgnoreCase("staynnonvz")) {
				addUnits();
				addSms();
				addData();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("hotspot")) {
				addData();
				addServiceDays();
			}
		} else if (comprepltype.equalsIgnoreCase("replacement")) {
			if (group.equalsIgnoreCase("tfnonsurepay")) {
				addUnits();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("tfsurepay")) {
				addUnitsSurepay();
				addSmsSurepay();
				addDataSurepay();
				addServiceDaysSurepay();
			}
			if (group.equalsIgnoreCase("unlimited")) {
				addServicePlan();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("paygo")) {
				addServicePlan();
				addUnits();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("staynvz")) {
				addServicePlan();
				addUnits();
				addSms();
				addData();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("staynnonvz")) {
				addServicePlan();
				addUnits();
				addSms();
				addData();
				addServiceDays();
			}
			if (group.equalsIgnoreCase("hotspot")) {
				addServicePlan();
				addData();
				addServiceDays();
			}
		}
	}

	public void selectReasonTypeWithForGroup(String reason, String type,String replType,String group) throws Exception {
		if( esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_6/");
		}else {
			redemptionFlow.clickLink("Compensation/Replacement");
		}		
		redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc2/", type);
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8/", reason);
		}else if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", reason);
		}
		
		if (type.equalsIgnoreCase("compensation")) {
			checkAndAddUnits(type, group);
		} else if (type.equalsIgnoreCase("Replacement")) {
			
			if(replType.equalsIgnoreCase("reference esn")){
			  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_0/");
			  String refEsn=esnUtil.getCurrentESN().getEsn();
			  redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it7/", refEsn);
			  pressButton("Validate Esn");
			  TwistUtils.setDelay(15);
			  if( esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){		    	 
			    	 pressButton("Refresh[1]");			     
			  }else {
			    	 if (buttonVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/")){
				    	 pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb6/");
				     }
			  }			  
			  
			}else if(replType.equalsIgnoreCase("reference pin")){
				  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_1/");
				  String refpin =phoneUtil.getUsedPinOfEsn(esnUtil.getCurrentESN().getEsn());
				  redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it9/", refpin);
				  pressButton("Validate Pin");
				  TwistUtils.setDelay(15);
				  if (buttonVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:commandButton1/")){
				    	 pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:commandButton1/");
				    	 
				  }
				  
			}else if(replType.equalsIgnoreCase("Open access")){
				  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
			}
			checkAndAddUnits(type,group);
		}
		
		if (redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/") && isTextFieldEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/")) {
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/", "15");
		}
		if (redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/").isVisible()) {
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/", "1");
		}
		
		redemptionFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it\\d/").setValue("ITQ-TEST");
		
		pressButton("Process");
	}

	public void enterPinAndRedeem() throws Exception {
		String pin = esnUtil.getCurrentESN().getPin();
		goToRedemption();
		enterNewPin(pin);
	}

	public void choosePlan(String plan) throws Exception {
		if (isSelectionEnabled("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/")) {
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/").choose("/.*?(UNL|TALK|DATA|TEXT|4G|DAY|YEAR).*?/i");
		    planAdding = redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/").selectedText().toString();
			logger.info("Plan adding to phone:" + planAdding);
		}
		pressButton("Process");
		if(redemptionFlow.cellVisible("You need to issue at least one bucket of SMS or Units or Data")){
			if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/")){
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it2/", "500");
			}
			if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3/")){
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3/", "500");
			}
			if(redemptionFlow.textboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/")){
				redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/", "500");
			}
			if (redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/").isVisible()) {
				redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/","30");
			}
			pressButton("Process");
		}
		if(redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl2/").isVisible()){
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:pgl2/").getText();
		}
	}
	
	private boolean isSelectionEnabled(String selection) {
		if (redemptionFlow.getBrowser().select(selection).isVisible()) {
			boolean isDisabled = redemptionFlow.getBrowser().select(selection).fetch("disabled").equalsIgnoreCase("true");
			if (!isDisabled) {
				return true;
			}
		}
		return false;
	}
	
	public void goToPurchaseHistory() throws Exception {
		
		
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
			pressButton("Contact Profile");
			redemptionFlow.clickLink("Credit Cards");
			redemptionFlow.clickLink("Edit");
		}else{
			redemptionFlow.clickLink("History");
			redemptionFlow.clickLink("Purchase History by ESN");	
		}
		
	}
	
	public void goToCreditCardDeatils() throws Exception {
		
		if(! esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
		redemptionFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot14/");
		redemptionFlow.clickLink("Credit Card History");
		}
		
	}
	
	public void issueRefundWithReasonOfAmount(String refundReason, String refundAmount)
			throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
			redemptionFlow.clickLink("Initiate Refund");
			redemptionFlow.selectCheckBox("/r\\d:\\d:r\\d:\\d:t\\d:\\d:t\\d:0:sbc3/");
			pressButton("Process Refund");
			
		}else{
			while(!redemptionFlow.submitButtonVisible("Ok")){
				redemptionFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot48/");
			}
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:soc8/", refundReason);
			redemptionFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:inputText3/", "ITQ-REFUND-TEST");
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:it17/", refundAmount);
			pressButton("Ok");
		}
		
		
	}
	
	public void confirmRefundSummary() throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")){
			Assert.assertTrue(redemptionFlow.spanVisible("/.*Success.*/"));
		}else{
			Assert.assertTrue(redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:ot17/").containsText("SUCCESS"));
		}
		
	}
	
	public void switchPaymentToDifferentCardAndCheckForConfirmation(String paymentType)
			throws Exception {
		redemptionFlow.clickLink("Enrollments");
		redemptionFlow.clickLink("Change Payment");
		String activeCard = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot40/").getText();
		logger.info(activeCard);
		redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:soc9::content/").choose(("/."+paymentType.toUpperCase()+".*/"));
		pressButton("Update");
		Assert.assertTrue(redemptionFlow.spanVisible("Success"));
		redemptionFlow.clickLink("Close[2]");
		String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
		System.out.println(esnUtil.getCurrentESN().getEsn() + " is " + enrollStatus);
		Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
	}
	
	public void chooseEnrollmentTypeAs(String string1) throws Exception {
		  checkRedemption();
		//pressButton("Apply on Due Date");
	}
	
	public void clearOTA() throws Exception {
	   redemptionFlow.clickLink("Toss Util");
	   redemptionFlow.clickLink("PPE Phone Programming");
	   pressButton("Code Accepted");
	}

	public void makeSelectionOfPayment(String purchaseType) throws Exception {
	
		if("Reoccuring".equalsIgnoreCase(purchaseType)  && (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile"))){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
			pressButton("Add Plan");
		}else if("Reoccuring".equalsIgnoreCase(purchaseType) && (esnUtil.getCurrentBrand().equalsIgnoreCase("straight_talk") || esnUtil.getCurrentBrand().equalsIgnoreCase("net10") )){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		}else if("Reoccuring".equalsIgnoreCase(purchaseType) || esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
		
		}
	}

	public void selectESNForRedemption(String transFor) throws Exception {
		if( (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  )&& transFor.equalsIgnoreCase("child")){
		pressButton("Contact Profile");	
		redemptionFlow.clickCellMessage("GROUP 1");
		Map familyEsns = esnUtil.getCurrentESN().getFamiyESNMap();
		Set keys = familyEsns.keySet();
		Iterator itr = keys.iterator();
		String childKey = (String) itr.next();
		String childEsn = (String) familyEsns.get(childKey);
		redemptionFlow.clickLink(childEsn);
		}
	}

	
	public void selectESNForEnrollment(String transFor) throws Exception {
		selectESNForRedemption(transFor);
	}

	public void selectESNForUpgrade(String transFor) throws Exception {
		selectESNForRedemption(transFor);
	}

	public void addCardEnterCvv(String cvv) throws Exception {
		if (buttonVisible("Refresh Credit Cards")) {
			pressButton("Refresh Credit Cards");
		} else if (buttonVisible("Refresh Credit Card")) {
			pressButton("Refresh Credit Card");
		}
		if (buttonVisible("Refresh Payment")) {
			pressButton("Refresh Payment");
		} 
		if (buttonVisible("Refresh Payment Methods")) {
			pressButton("Refresh Payment Methods");
		} 
		
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			redemptionFlow.getBrowser().select("/r2:\\d:r\\d:\\d:soc3::content/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
			redemptionFlow.typeInTextField("/r2:\\d:r\\d:\\d:it4::content/", cvv);
			
		}else{
			redemptionFlow.getBrowser().select("/r2:\\d:r\\d:\\d((:r\\d:\\d:soc1)|(:soc2)|(:soc3))/").choose(("/."+esnUtil.getCurrentESN().getLastFourDigits()+".*/"));
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d((:r\\d:\\d:it1)|(:it14)|(:it1a)|(:it30))/", cvv);
		}
	}

	public void addPaymentForNACPurchase(String cardType, String nacCode) throws Exception {
		if(!nacCode.equalsIgnoreCase("Free")){
			pressButton("Add New Credit Card");
			enterCard(cardType);
			pressButton("Refresh");
			selectCardEnterCvv("123");
			pressButton("Continue");
		}
	}
	public void addToCartForPurchase() throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			pressButton("Add Plan");
		}else if(esnUtil.getCurrentBrand().equalsIgnoreCase("net10") || esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone") || esnUtil.getCurrentBrand().equalsIgnoreCase("safelink")){
			pressButton("+");
		}else{
			pressButton("Add");
		}
	}
	
	public void addPlansToCartForPurchase(String quantity) throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")  || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			pressButton("Add Plan");
		}else if(esnUtil.getCurrentBrand().equalsIgnoreCase("telcel") ||esnUtil.getCurrentBrand().equalsIgnoreCase("straighttalk") ||esnUtil.getCurrentBrand().equalsIgnoreCase("net10") || esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone") || esnUtil.getCurrentBrand().equalsIgnoreCase("safelink")){
			int count=1;
			if(!quantity.equalsIgnoreCase("")){
				count = Integer.parseInt(quantity);
			}
			for(int i=1;i<=count;i++){
				pressButton("+");
			}
		}else if (esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")|| esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")){
		}
			else{
			if(buttonVisible("Add")){
				pressButton("Add");
			}
				
		}
	}

	public void addILDCardToCart() throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("go_smart")){
			pressButton("Add ILD");
		}
	}

	public void addCardEnterCvvFotByop(String cvv) throws Exception {
		if (buttonVisible("Refresh Credit Cards")) {
			pressButton("Refresh Credit Cards");
		} else if (buttonVisible("Refresh Credit Card")) {
			pressButton("Refresh Credit Card");
		}
			redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/").choose(("/."+last4digits+".*/"));
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it46/", cvv);
	}

	public void AddCardFromReserve(String redemptionType) throws Exception {
		redemptionFlow.clickLink("Transactions");
		redemptionFlow.clickLink("Redemption");
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile") || esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
		String fullPin= esnUtil.getCurrentESN().getPin();
		pressButton("Reserved Pins");
		redemptionFlow.clickLink("***********"+fullPin.substring(fullPin.length()-4));
		pressButton("Validate");
		checkRedemption();
	}
	public void addPinCardToReach365PaidDaysRequirment(String pinPart)
			throws Exception {
		int startValue = 0;
		if (pinPart.equalsIgnoreCase("NTAPPMP000451Y") || pinPart.equalsIgnoreCase("NTNMP00045Y1") || pinPart.equalsIgnoreCase("TSAPP4001Y")) {
			startValue = 12;
		} else if (pinPart.equalsIgnoreCase("NTAPP21500")) {
			startValue = 9;
		}
		
		for(int i=startValue ;i<=11;i++){
			pressButton("Refresh");
			redemptionFlow.clickLink("Transactions");
			redemptionFlow.clickLink("Redemption");
			
			enterPin(pinPart);
			String redemption = checkRedemptionType();
			if("ADDRESERVE".equalsIgnoreCase(redemption)){
				AddCardFromReserve(redemption);
			}
		//	phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
			redemptionFlow.clickLink("Toss Util");
			redemptionFlow.clickLink("PPE Phone Programming");
			pressButton("Code Accepted");
			TwistUtils.setDelay(3);
			redemptionFlow.clickLink("Fix ESN");
			pressButton("Fix ESN");
		}
	}

	public void addPinCardToReach365PaidDaysRequirmentSl(String pinPart)  //new
			throws Exception {
	
			redemptionFlow.clickLink("Transactions");
			redemptionFlow.clickLink("Redemption");
			enterPin(pinPart);
			String redemption = checkRedemptionType();
			
		//	phoneUtil.clearOTAforEsn(esnUtil.getCurrentESN().getEsn());
			redemptionFlow.clickLink("Toss Util");
			redemptionFlow.clickLink("PPE Phone Programming");
			pressButton("Code Accepted");
			TwistUtils.setDelay(3);
			redemptionFlow.clickLink("Fix ESN");
			pressButton("Fix ESN");
		}
	//new
	

//	public void checkFrontendForPointAllocation() throws Exception {
//		Assert.assertTrue(redemptionFlow.getBrowser().div("r2:1:r1:2:pfl7").text().contains("You will gain 225 Loyalty Reward points as part of this transaction"));
//	}

	public void checkForPointAllocationForPin(String pinPart)
			throws Exception {
	
		String points = phoneUtil.getLRPPointsforPinPart(pinPart);
		System.out.println("points for pin B" + points);

//		Assert.assertTrue(redemptionFlow.getBrowser().div("r2:1:r1:2:pfl7").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
		phoneUtil.getLRPPointsbyEmail(esnUtil.getCurrentESN().getEmail());
		String dbpoints = phoneUtil.getLRPPointsbyEmail(esnUtil.getCurrentESN().getEmail());
		Assert.assertTrue(points.equals(dbpoints));
	}
	
	public void cancleEnrollment() throws Exception {
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM") || esnUtil.getCurrentBrand().equalsIgnoreCase("SIMPLE_MOBILE")){
			TwistUtils.setDelay(45);
			pressButton("Refresh");
			redemptionFlow.clickLink("Enrollments");
			pressButton("De-Enrollment");
			TwistUtils.setDelay(5);
			pressButton("De-Enroll");
		}else{
			redemptionFlow.clickLink("Cancel");
		}
		
	}
	
	public void checkDeEnrolledStatusInServiceProfile() throws Exception {
		pressButton("Refresh");
		Assert.assertTrue(redemptionFlow.cellVisible("Transaction Summary"));
		//String autoRefillStatus = redemptionFlow.getBrowser().cell("r2:1:panelLabelAndMessage66").text();
		//System.out.println(autoRefillStatus);
		//Assert.assertTrue(autoRefillSta);
	}
	
	public void checkForPointAllocationForPin(String pinPart,String plan)
			throws Exception {
	
		String points = phoneUtil.getLRPPointsforPinPart(pinPart);

		Assert.assertTrue(redemptionFlow.getBrowser().div("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pfl7/").text().contains("You will gain " + points + " Loyalty Reward points as part of this transaction"));
		TwistUtils.setDelay(120);
		String dbpoints = phoneUtil.getLRPPointsbyEmailforTranstype(plan,esnUtil.getCurrentESN().getEmail());
		Assert.assertTrue(points.equals(dbpoints));
	}

	public void selectReasonTypeWithServicePlanAndServiceDays(String reason,
			String type, String replType, String servicePlan, Integer serviceDays)
			throws Exception {
		redemptionFlow.clickLink("Compensation/Replacement");
		redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc2/", type);
		
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8/", reason);
		}else if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", reason);
		}
		
		if(replType.equalsIgnoreCase("Open Access")){
				  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
		}
		
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/", servicePlan);
		}
		if(redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/").isVisible()) {
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/", "1");
		}
		redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/", serviceDays.toString());
		redemptionFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3::content/", "test");
		pressButton("Process");
		
	}


	public void purchaseAirtimeForPINWithCard(String pin, String cardType)
			throws Exception {
		redemptionFlow.clickLink("Purchase Airtime");
		redemptionFlow.clickCellMessage(pin);
		pressButton("Add");
		pressButton("Add New Credit Card");
		esnUtil.getCurrentESN().setPin(pin);
		enterCard(cardType);
		pressButton("Refresh");
		TwistUtils.setDelay(20);
		selectCardEnterCvv("671");
		storeRedeemData(esnUtil.getCurrentESN(), pin);
		//redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc1::content/").choose(("/."+last4digits+".*/"));
		//redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it1::content/", "671");
		//redemptionFlow.pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:cb2/");
		checkRedemption();
		//pressButton("Add Now");
	}

	public void selectReasonTypeWithServicePlanAndServiceDaysOldESN(
			String reason, String type, String replType, String servicePlan, Integer serviceDays, String oldPart) throws Exception {
		ESN oldEsn = esnUtil.popRecentEsnWithPin(oldPart);
		redemptionFlow.clickLink("Compensation/Replacement");
		redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc2/", type);
		
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc8/", reason);
		}else if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", reason);
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc4/", reason);
		}
		
		if(replType.equalsIgnoreCase("Open Access")){
				  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_2/");
		}else if(replType.equalsIgnoreCase("Reference Pin")){
			  redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sor1:_1/");
			  //redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it9", esnUtil.getCurrentESN().getPin());
			  redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it9::content/", oldEsn.getPin());
			  pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:cb4/");
		}
		
		if(redemptionFlow.selectionboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/")){
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc3::content/", servicePlan);
		}
		if(redemptionFlow.getBrowser().select("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/").isVisible()) {
			redemptionFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:soc11/", "1");
		}
		redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it4/", serviceDays.toString());
		redemptionFlow.typeInTextArea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:it3::content/", "test");
		pressButton("Process");
		
	}

	public void enterPromo(String promo) throws Exception {
		redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r2:\\d:r\\d:\\d((:r\\d:\\d:it3)|(:it3a)|(:it3))/", promo);
		pressButton("Validate Promo");
		TwistUtils.setDelay(10);
				String error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r2:\\d:r\\d:\\d:r\\d:\\d:pgl10/").getText();
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, redemptionFlow.cellVisible("Promo VALID for purchase"));
	}

	public void enterPinAndPromo(String pinPart, String promo) throws Exception {
		String newPin = phoneUtil.getNewPinByPartNumber(pinPart);
		logger.info("PIN:" + newPin);
		enterNewPinAndPromo(newPin, promo);
		esnUtil.getCurrentESN().setPin(newPin);
	}

	private void enterNewPinAndPromo(String newPin, String promo) {
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile")) {
			redemptionFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("net10") || esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone")){
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:rit1::content/", newPin);
		}else{
			redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1::content/", newPin);
		}
		pressButton("Validate");
		String error_msg1 = redemptionFlow.getBrowser().div("d1::msgDlg::_cnt").getText();
		if (redemptionFlow.linkVisible("Close[1]")) {
			redemptionFlow.clickLink("Close[1]");
		}
		if (redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").isVisible()) {
			error_msg = redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
		}
		Assert.assertTrue("ESN:" + esnUtil.getCurrentESN().getEsn() + ";" + "Error " + error_msg + ";" + error_msg1,
				(redemptionFlow.cellVisible("Valid PIN") || redemptionFlow.cellVisible("Valid Pin")));
		storeRedeemData(esnUtil.getCurrentESN(), newPin);
		redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2::content/", promo);
	}

	public void makeSelectionOfPaymentWithCardType(String purchaseType, String cardType)
			throws Exception {
		String brand = esnUtil.getCurrentBrand();
		if("Reoccuring".equalsIgnoreCase(purchaseType)  && (brand.equalsIgnoreCase("total_wireless") || brand.equalsIgnoreCase("go_smart"))){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
			pressButton("Add Plan");
		}else if("Reoccuring".equalsIgnoreCase(purchaseType) && (brand.equalsIgnoreCase("wfm") || (brand.equalsIgnoreCase("simple_mobile")))){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
			
		}else if("Reoccuring".equalsIgnoreCase(purchaseType) && (brand.equalsIgnoreCase("straight_talk") || (brand.equalsIgnoreCase("net10") &&  !cardType.equalsIgnoreCase("ACH")))){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		}else if("Reoccuring".equalsIgnoreCase(purchaseType) && (brand.equalsIgnoreCase("net10") && cardType.equalsIgnoreCase("ACH"))){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:r\\d:\\d:sbc2::content/");
		}
		//redemptionFlow.clickLink("Purchase Airtime");
	}

	
	public void enterReservedPinAndRedeem() throws Exception {
		goToRedemption();
		enterNewPin("reserved");
	}
	
	public void enterPinTimes(String pinPart, String redeemCount) throws Exception {
		int count=1;
		String newPin=null;
		int i=1;
		if(!redeemCount.equalsIgnoreCase("")){
			count = Integer.parseInt(redeemCount); 
		}
		while(i<=count){
			newPin = phoneUtil.getNewPinByPartNumber(pinPart);		
			logger.info("PIN "+ i +": " + newPin);
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("net10") || esnUtil.getCurrentBrand().equalsIgnoreCase("tracfone") || esnUtil.getCurrentBrand().equalsIgnoreCase("safelink")){
				enterMultiplePins(newPin,i);				
			}else{
				enterNewPin(newPin);
			}
			i++;
		}	
		esnUtil.getCurrentESN().setPin(newPin);
	}
	private void enterMultiplePins(String newPin, int index) {
		
		if(index>1){
			pressButton("+");
		}
		redemptionFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:rit" + index + "::content/", newPin);
		pressButton("Validate");
	    String error_msg1=redemptionFlow.getBrowser().div("d1::msgDlg::_cnt").getText() ;
		if(redemptionFlow.linkVisible("Close[1]")){
			redemptionFlow.clickLink("Close[1]");
		}
		if(redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").isVisible()){
			error_msg= redemptionFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:panelGroupLayout1/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg+";"+error_msg1, (redemptionFlow.cellVisible("Valid PIN") || redemptionFlow.cellVisible("Valid Pin")));
		storeRedeemData(esnUtil.getCurrentESN(), newPin);
	}
	
	public void selectOption(String purchaseType) throws Exception {
		if("Reoccuring".equalsIgnoreCase(purchaseType) || esnUtil.getCurrentBrand().equalsIgnoreCase("wfm")){
			redemptionFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sbc1::content/");
		}
	}

	public void goToPurchaseAirtimeFlow() throws Exception {
		redemptionFlow.clickLink("Purchase Airtime");
	}
	
}