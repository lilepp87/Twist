package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;


public class CompleteShipping {
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private ResourceBundle props = ResourceBundle.getBundle("TotalWEB");
	
	public CompleteShipping() {
		
	}
	
	public void completeShippingDetailsForDomainIfLteAndAlreadyHaveSIM(String domain, String isLte , String haveSim) throws Exception {
		if (isLte.equalsIgnoreCase("Yes") && haveSim.isEmpty()) {
			TwistUtils.setDelay(5);
			shippingDetails();

			activationPhoneFlow.navigateTo(props.getString("TW_TASURL"));
			if (activationPhoneFlow.textboxVisible("it1")) {
				activationPhoneFlow.typeInTextField("it1", "itquser");
				activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
				pressButton("Login");
			}
			activationPhoneFlow.clickLink("Incoming Call");
			ESN esn = esnUtil.getCurrentESN();
			System.out.println(esn.getEsn());
			enterActiveEsnAndMin(esn.getEsn());
			if (activationPhoneFlow
					.buttonVisible("Continue to Service Profile")) {
				activationPhoneFlow.pressButton("Continue to Service Profile");
			}

			completeShipping(esn.getEsn(), domain);
			activationPhoneFlow.navigateTo(props.getString("TW_HOME"));
			activationPhoneFlow.clickLink("Return to Account Summary");
			TwistUtils.setDelay(5);
			activationPhoneFlow.clickLink("Complete Activation");
			activationPhoneFlow.selectCheckBox("esnList");
			pressButton("Activate");
		}
	}
	
	public void completeShipping(String esn, String domain){
		activationPhoneFlow.clickLink("History");
		activationPhoneFlow.clickLink("Ticket History");
		activationPhoneFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot2/");
		//pressButton("Yank");
		if (activationPhoneFlow.buttonVisible("Yank")||activationPhoneFlow.submitButtonVisible("Yank")){
        	pressButton("Yank");
        }else{
            pressButton("Accept");
            pressButton("Accept[1]");
        }
		activationPhoneFlow.clickLink("Part Request");
		//Assert.assertTrue(activationPhoneFlow.spanVisible("PENDING"));
		/*activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7::content/", "BP_IO");//r2:1:r1:2:soc7
		TwistUtils.setDelay(5);
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5::content/", "FEDEX");
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6::content/", "2nd DAY");*/
		activationPhoneFlow.pressSubmitButton("Save");
		pressButton("OK");
		String partNum = activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:xReplPartNumId::content/").value();
		activationPhoneFlow.pressSubmitButton("Ship");		
		if(domain.equalsIgnoreCase("SIM CARDS")){
			String sim= simUtil.getNewSimCardByPartNumber(partNum);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32::content/", sim);
			esnUtil.getCurrentESN().setSim(sim);
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31::content/", "1234567890");
		pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb44/");//Ship[1]
		TwistUtils.setDelay(10); //10
		activationPhoneFlow.pressButton("Cancel");//activationPhoneFlow.pressButton("r2:1:r1:2:d1::cancel");//Cancel
		}
	
	public void enterActiveEsnAndMin(String activeEsn) {
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("ActiveESN: " + activeEsn + " ActiveMin: " + activeMin);
		activationPhoneFlow.typeInTextField("/r2:\\d:it1/", activeEsn);
		pressButton("Search Service");
	}

	//r2:0:it1
	public void shippingDetails() throws Exception{
		activationPhoneFlow.typeInTextField("ct_firstName", "TwistFirstName");
		activationPhoneFlow.typeInTextField("ct_lastName", "TwistLastName");
		activationPhoneFlow.typeInTextField("ct_address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("ct_zip", "33178");
		activationPhoneFlow.typeInTextField("ct_city", "Miami");
		activationPhoneFlow.chooseFromSelect("ct_state", "FL");
		activationPhoneFlow.typeInTextField("ct_phoneNumber", TwistUtils.generateRandomMin());
		pressButton("CONTINUE[2]");
		pressButton("Select");
		TwistUtils.setDelay(15);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("panel-body").text().contains("Your SIM order has been created with case number:"));
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	
	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}
	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}

}