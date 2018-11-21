package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
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
	private ResourceBundle rb = ResourceBundle.getBundle("SM");
	private Properties properties = new Properties();
	
	public CompleteShipping() {
		
	}
	
	public void completeShippingDetailsForDomainIfLteAndAlreadyHaveSIM(String domain, String islte , String havesim) throws Exception {
		if (islte.equals("Yes")){
			if (havesim.equals("null")){
				TwistUtils.setDelay(5);
					Shippingdetails();
					
					activationPhoneFlow.navigateTo(properties.getString("TAS_URL"));
					if (activationPhoneFlow.textboxVisible("it1")) {
						activationPhoneFlow.typeInTextField("it1", "Itquser");
						activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
						
						if (activationPhoneFlow.buttonVisible("Login")){
							activationPhoneFlow.pressButton("Login");
						} else {
							activationPhoneFlow.pressSubmitButton("cb1");
						}
					}
					activationPhoneFlow.clickLink("Incoming Call");
					ESN esn=esnUtil.getCurrentESN();
					System.out.println(esn.getEsn());
					enterActiveEsnAndMin(esn.getEsn());
					if (activationPhoneFlow.buttonVisible("Continue to Service Profile")) {
						activationPhoneFlow.pressButton("Continue to Service Profile");
					}
					
					CompleteShipping(esn.getEsn(),domain);
					activationPhoneFlow.clickLink("Logout");
					activationPhoneFlow.navigateTo(properties.getString("TF_URL"));
					activationPhoneFlow.clickLink("ACTIVATE");
					activationPhoneFlow.clickLink("ACTIVATE");

			}
			
		}
	}
	
	public void CompleteShipping(String esn, String domain){
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
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", activeEsn);
		if (activationPhoneFlow.buttonVisible("Search Service")) {
			activationPhoneFlow.pressButton("Search Service");
		} else {
			activationPhoneFlow.pressSubmitButton("Search Service");
		}
	}

	
	public void Shippingdetails() throws Exception{
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.typeInTextField("firstName", "TwistFirstName");
		activationPhoneFlow.typeInTextField("lastName", "TwistLastName"); //email
		activationPhoneFlow.getBrowser().byId("email").setValue(randomEmail);
		activationPhoneFlow.typeInTextField("confirmEmail", randomEmail);
		activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("zip", "33178");
		activationPhoneFlow.typeInTextField("city", "Miami");
		activationPhoneFlow.chooseFromSelect("state", "FL");
		activationPhoneFlow.typeInTextField("phone", TwistUtils.generateRandomMin());
		activationPhoneFlow.pressSubmitButton("CONTINUE"); //default_submit_btn
		TwistUtils.setDelay(5);
		Assert.assertTrue(activationPhoneFlow.labelVisible("Ticket Number:")); 
		TwistUtils.setDelay(20);
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
