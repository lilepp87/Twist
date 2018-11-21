package com.tracfone.twist.supportOperations;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.apache.ecs.xhtml.pre;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;


public class Registration {

	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	public Registration() {
		
	}
	
	
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}


	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}



	public void registerIphoneModelForCarrierWithResponseForSource(String model, String simPart,String response, String brand, String WARP)
			throws Exception {
		myAccountFlow.clickLink("Support");
		myAccountFlow.clickLink("SIM Out Registration");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", brand);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", model);
		String imei = TwistUtils.generate15DigitImei();
		System.out.println(imei);
		String newSim = simUtil.getNewSimCardByPartNumber(simPart);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?\\d:\\d:r\\d:\\d:it2/", newSim);
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/",imei);
		pressButton("Register Serial Number");
		if (simPart.equalsIgnoreCase("TF256PSIMV9N")) {
			if (response.equalsIgnoreCase("YES")) {
				TwistUtils.setDelay(5);
				phoneUtil.finishCdmaByopIgate(imei, "RSS", "NEW", "YES", "YES", "YES");
			}
			if (response.equalsIgnoreCase("NO")) {
				TwistUtils.setDelay(30);
			}

		}
		Assert.assertTrue(myAccountFlow.cellVisible("/SUCCESS.*?/"));
		myAccountFlow.clickLink("Incoming Call");
		pressButton("New Contact Account");
		ESN currentEsn = new ESN("TEST", imei);
		esnUtil.setCurrentESN(currentEsn);
		currentEsn.setSim(newSim);
		
	}
	
	private void pressButton(String buttonType) {
		if (myAccountFlow.submitButtonVisible(buttonType)) {
			myAccountFlow.pressSubmitButton(buttonType);
		} else {
			myAccountFlow.pressButton(buttonType);
		}
	}


}
