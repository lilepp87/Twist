package com.tracfone.twist.impl.eng.activation;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

// JUnit Assert framework can be used for verification

public class BYOPFinder {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private Properties props;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;

	public BYOPFinder() {

	}

	public void goToBYOPFinderPage() throws Exception {
		activationPhoneFlow.navigateTo("http://sit1.net10.com/byop_finder_regis/index.jsp");
	}

	public void enterESNAndZip(String status, String partNumber, String zipCode) throws Exception {
		if (partNumber.equalsIgnoreCase("GSM") && status.equalsIgnoreCase("Active")) {
			String esn = phoneUtil.getActiveEsnByPartNumber("NTZEZ660GP5");
			activationPhoneFlow.typeInTextField("phoneCode", esn);
			activationPhoneFlow.typeInTextField("zip", zipCode);
			activationPhoneFlow.pressSubmitButton("Continue");
			activationPhoneFlow.pressSubmitButton("Confirm");
			Assert.assertTrue(activationPhoneFlow.getBrowser().byText("Good news, your phone is a Net 10 phone " +
					"so you are already taking advantage of great savings. All you need to do is purchase an " +
					"Service Card and activate. A new SIM Card or Network Access Code is not required.", "P").isVisible());
		} else if (partNumber.equalsIgnoreCase("GSM") && status.equalsIgnoreCase("Inactive")) {
			String esn = phoneUtil.getNewByopCDMAEsn().replaceFirst("1000", "10");
			activationPhoneFlow.typeInTextField("phoneCode", esn);
			activationPhoneFlow.typeInTextField("zip", zipCode);
			activationPhoneFlow.pressSubmitButton("Continue");
			activationPhoneFlow.pressSubmitButton("Confirm");
			Assert.assertTrue(activationPhoneFlow.h2Visible("Good news, your phone is compatible to use on Net10."));
			Assert.assertTrue(activationPhoneFlow.h3Visible("For your phone, only the Service Plan Card and SIM Card are necessary. " +
					"The Network Access Code is not required. Due to packaging issues, the SIM Card you need may not be in the correct " +
					"place in your Activation Kit. Follow these simple directions to select the right SIM Card."));
		} else if (partNumber.equalsIgnoreCase("CDMA")) {
			String esn = "3400" + TwistUtils.generateRandomEsn();
			System.out.println(esn);
			activationPhoneFlow.typeInTextField("phoneCode", esn);
			activationPhoneFlow.typeInTextField("zip", zipCode);
			activationPhoneFlow.pressSubmitButton("Continue");
			activationPhoneFlow.pressSubmitButton("Confirm");
			
			int edited = 0;
			for (int i = 0; i < 8 && edited == 0; i++) {
				TwistUtils.setDelay(2+i);
				edited = phoneUtil.finishCdmaByopIgate(esn, "Sprint", status, "No", "No", "NO");
				System.out.println("edit: " + edited);
			}
			TwistUtils.setDelay(45);
			Assert.assertTrue(activationPhoneFlow.h2Visible("Good news, your phone is compatible to use on Net10."));
			Assert.assertTrue(activationPhoneFlow.h3Visible("For your particular phone only the Service Plan Card and Network Access " +
					"Code are needed for activation. The SIM Card is not needed. The Network Access Code is located in the white area."));
		} else {
			throw new IllegalArgumentException("Unsupported cell tech and status");
		}
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phone) {
		phoneUtil = phone;
	}
	
	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

}