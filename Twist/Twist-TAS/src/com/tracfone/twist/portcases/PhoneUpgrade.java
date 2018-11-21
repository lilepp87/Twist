package com.tracfone.twist.portcases;

// JUnit Assert framework can be used for verification

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.services.InquiryServices;
import com.tracfone.twist.utils.CboUtils;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class PhoneUpgrade {

	private MyAccountFlow myAccountFlow;
	private String error_msg;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private CboUtils cboUtils;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	
	
	public PhoneUpgrade() {

	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void goToUpgradeOption() throws Exception {
		myAccountFlow.clickCellMessage("Transactions");
		myAccountFlow.clickLink("Upgrade");
		if (esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless")) {
			myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
	}

	public void goToByopUpgradeOption() throws Exception {
		myAccountFlow.clickCellMessage("Transactions");
		myAccountFlow.clickLink("Upgrade");
		myAccountFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
		//myAccountFlow.selectRadioButton("r2:1:r1:2:sor2:_0");
	}
	
	public void confirmPhoneUpgrade() throws Exception {
		if (myAccountFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1a/").isVisible()) {
			error_msg = myAccountFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1a/").getText();
		}
		Assert.assertTrue("Error "+error_msg, myAccountFlow.h2Visible("Transaction Summary"));
		TwistUtils.setDelay(5);
		phoneUtil.updateLatestIGRecordforEsn(esnUtil.getCurrentESN().getEsn());
//		Assert.assertTrue(myAccountFlow.labelVisible("/.*Ticket Number.*/"));
//		Assert.assertTrue(myAccountFlow.labelVisible("Phone Upgrade Ticket Number")|| myAccountFlow.labelVisible("Auto Internal Ticket Number"));
		// To Auto Complete Port Ticket Using CBO for phone upgrade between diff carriers
		
		if (!(esnUtil.getCurrentBrand().equalsIgnoreCase("total_wireless") ||  esnUtil.getCurrentBrand().equalsIgnoreCase("wfm") || esnUtil.getCurrentBrand().equalsIgnoreCase("simple_mobile"))){
			String Ordertype=phoneUtil.getOrderType(esnUtil.getCurrentESN().getEsn());
			if( Ordertype.equals("EPIR")||Ordertype.equals("PIR")||Ordertype.equals("IPI")){
				String Actionitemid=phoneUtil.getactionitemidbyESN(esnUtil.getCurrentESN().getEsn());
				String Min =phoneUtil.getminforESN(esnUtil.getCurrentESN().getEsn());
				cboUtils.callcompleteAutomatedPortins(Actionitemid, Min);					
				}
			}
	
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}

	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}

	public void confirmIdentityChallenge() throws Exception {
		if (myAccountFlow.checkboxVisible("r2:1:r1:0:t1:0:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:0:t1:0:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:1:r1:1:t1:0:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:1:t1:0:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:2:r1:0:t1:0:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:2:r1:0:t1:0:sbc1::content");
		}
		
		if (myAccountFlow.checkboxVisible("r2:1:r1:0:t1:1:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:0:t1:1:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:1:r1:1:t1:1:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:1:t1:1:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:2:r1:0:t1:1:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:2:r1:0:t1:1:sbc1::content");
		}
		
		if (myAccountFlow.checkboxVisible("r2:1:r1:0:t1:2:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:0:t1:2:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:1:r1:1:t1:2:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:1:r1:1:t1:2:sbc1::content");
		} else if (myAccountFlow.checkboxVisible("r2:2:r1:0:t1:2:sbc1::content")) {
			myAccountFlow.selectCheckBox("r2:2:r1:0:t1:2:sbc1::content");
		}
		
		if (myAccountFlow.buttonVisible("Continue")) {
			myAccountFlow.pressButton("Continue");
		} else {
			myAccountFlow.pressSubmitButton("Continue");
		}
	}

	public void confirmPhoneUpgradeForSL() throws Exception {
		if (myAccountFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1a/").isVisible()) {
			error_msg = myAccountFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl1a/").getText();
		}
		Assert.assertTrue("Error "+error_msg, myAccountFlow.h2Visible("Transaction Summary"));
	}	
}