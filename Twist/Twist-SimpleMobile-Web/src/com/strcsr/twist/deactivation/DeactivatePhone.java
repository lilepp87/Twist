package com.strcsr.twist.deactivation;

import java.util.ResourceBundle;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.DeactivationPhoneFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class DeactivatePhone {

	private DeactivationPhoneFlow deactivationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static ESNUtil esnUtil;

	private ResourceBundle rb = ResourceBundle.getBundle("SM");

	public DeactivatePhone() {

	}

	public void enterEsnAndMinInformation(String partNumber) throws Exception {
		String activeEsn = phoneUtil.getActiveEsnByPartNumber(partNumber);
		enterActiveEsnAndMin(activeEsn);
	}

	public void enterActiveEsnAndMin(String activeEsn) {
		TwistUtils.setDelay(15);
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("ActiveESN: " + activeEsn + " ActiveMin: " + activeMin);
		
		deactivationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", activeEsn);
		if (deactivationPhoneFlow.buttonVisible("Search Service")) {
			deactivationPhoneFlow.pressButton("Search Service");
		} else {
			deactivationPhoneFlow.pressSubmitButton("Search Service");
		}
	}

	public void deactivateEsn(String esn, String reason) throws Exception {
		if (reason != null && reason.equalsIgnoreCase("REFURBISHED")) {
			reason = "REFURBISHED Line Status : RETURNED";
		} else if (reason != null && reason.equalsIgnoreCase("USED")) {
			reason = "NO NEED OF PHONE Line Status : RESERVED USED";
		} else if (reason != null && reason.equalsIgnoreCase("USED")) {
			reason = "NO NEED OF PHONE Line Status : RESERVED USED";
		} else if (reason != null && reason.equalsIgnoreCase("PORT CANCEL")) {
			reason = "PORT CANCEL Line Status : RETURNED";
		} else {
			reason = "PASTDUE Line Status : RESERVED USED";
		}
		TwistUtils.setDelay(5);
		if (deactivationPhoneFlow.passwordVisible("it2")) {
			deactivationPhoneFlow.typeInTextField("it1", "itquser");
			deactivationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
			
			if (deactivationPhoneFlow.buttonVisible("cb1")) {
				deactivationPhoneFlow.pressButton("cb1");
			} else {
				deactivationPhoneFlow.pressSubmitButton("Login");
			}
		}
		TwistUtils.setDelay(9);
		deactivationPhoneFlow.clickLink("Incoming Call");
        String email = TwistUtils.createRandomEmail();
		enterActiveEsnAndMin(esn);

		TwistUtils.setDelay(10);
		if (deactivationPhoneFlow.buttonVisible("Continue to Service Profile")) {
			deactivationPhoneFlow.pressButton("Continue to Service Profile");
		} else if (deactivationPhoneFlow.submitButtonVisible("Continue to Service Profile")) {
			deactivationPhoneFlow.pressSubmitButton("Continue to Service Profile");
		}
		TwistUtils.setDelay(5);
		deactivationPhoneFlow.clickLink("Transactions");
		deactivationPhoneFlow.clickLink("Deactivation");
		TwistUtils.setDelay(5);
		
		if (deactivationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/")) {
			deactivationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:0:sbc1::content/");
		}
		if (deactivationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/")) {
			deactivationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:1:sbc1::content/");
		} 
		if (deactivationPhoneFlow.checkboxVisible("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:sbc1::content/")){
			deactivationPhoneFlow.selectCheckBox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:2:sbc1::content/");
		} 

		if (deactivationPhoneFlow.buttonVisible("Continue")) {
			deactivationPhoneFlow.pressButton("Continue");
		} else {
			deactivationPhoneFlow.pressSubmitButton("Continue");
		}
		TwistUtils.setDelay(12);
		deactivationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc1::content/", reason);
		if (deactivationPhoneFlow.buttonVisible("Deactivate")) {
			deactivationPhoneFlow.pressButton("Deactivate");
		} else {
			deactivationPhoneFlow.pressSubmitButton("Deactivate");
		}
		TwistUtils.setDelay(5);
		
		if (deactivationPhoneFlow.textboxVisible("r2:1:r1:2:it2::content")) {
			deactivationPhoneFlow.typeInTextField("r2:1:r1:2:it2::content", email);
			if (deactivationPhoneFlow.buttonVisible("Save Email")) {
				deactivationPhoneFlow.pressButton("Save Email");
			} else {
				deactivationPhoneFlow.pressSubmitButton("Save Email");
			}
		}
		TwistUtils.setDelay(10);
		Assert.assertTrue("Failed to complete deactivtion", deactivationPhoneFlow.h2Visible("Transaction Summary")
				|| deactivationPhoneFlow.divVisible("Your phone has been deactivated."));
	}
	
	public void stDeactivateEsn(String esn, String reason) throws Exception {
		deactivationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
		deactivateEsn(esn, reason);
	}	
	
	public void twDeactivateEsn(String esn, String reason) throws Exception {
		deactivationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
		deactivateChildEsn(esn, reason);
	}
	
	private void deactivateChildEsn(String esn, String reason) {
		String email = TwistUtils.createRandomEmail();
		if (reason == null || reason.isEmpty()) {
			reason = "PASTDUE"; 
		}
		if (deactivationPhoneFlow.textboxVisible("it1")) {
			deactivationPhoneFlow.typeInTextField("it1", "itquser");
			deactivationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
			
			if (deactivationPhoneFlow.buttonVisible("Login")){
				deactivationPhoneFlow.pressButton("Login");
			} else {
				//deactivationPhoneFlow.pressSubmitButton("Login");
				deactivationPhoneFlow.pressSubmitButton("cb1");
			}
		}
		deactivationPhoneFlow.clickLink("Incoming Call");

		enterActiveEsnAndMin(esn);
		if (deactivationPhoneFlow.buttonVisible("Contact Profile")) {
			deactivationPhoneFlow.pressButton("Contact Profile");
		} else {
			deactivationPhoneFlow.pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:commandButton1202/");
		}

		
		if (deactivationPhoneFlow.checkboxVisible("r2:2:r1:0:t1:0:sbc1::content")) {
			deactivationPhoneFlow.selectCheckBox("r2:2:r1:0:t1:0:sbc1::content");
			deactivationPhoneFlow.selectCheckBox("r2:2:r1:0:t1:1:sbc1::content");
		}
		
		deactivationPhoneFlow.pressButton("r2:2:r1:0:cb1");
		deactivationPhoneFlow.clickLink("GROUP 1");
		
		deactivationPhoneFlow.clickLink(esn);
		
		/*if (deactivationPhoneFlow.buttonVisible("Continue to Service Profile")) {
			deactivationPhoneFlow.pressButton("Continue to Service Profile");
		}*/
		
		deactivationPhoneFlow.clickLink("Transactions");
		deactivationPhoneFlow.clickLink("Deactivation");
		if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:0:t1:0:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:0:t1:0:sbc1::content");
		} else if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:1:t1:0:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:0:sbc1::content");
		}
		if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:0:t1:1:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:0:t1:1:sbc1::content");
		} else if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:1:t1:1:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:1:sbc1::content");
		}
		if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:0:t1:2:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:0:t1:2:sbc1::content");
		} else if (deactivationPhoneFlow.checkboxVisible("r2:1:r1:1:t1:2:sbc1::content")){
			deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:2:sbc1::content");
		}
		//deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:0:sbc1::content");
		//deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:1:sbc1::content");
		//deactivationPhoneFlow.selectCheckBox("r2:1:r1:1:t1:2:sbc1::content");
		if (deactivationPhoneFlow.buttonVisible("Continue")) {
			deactivationPhoneFlow.pressButton("Continue");
		} else if(deactivationPhoneFlow.submitButtonVisible("Continue")){
			deactivationPhoneFlow.pressSubmitButton("Continue");
		}
		deactivationPhoneFlow.chooseFromSelect("/r2:\\d:r\\d:\\d:soc1/", reason);
		if (deactivationPhoneFlow.buttonVisible("Deactivate")) {
			deactivationPhoneFlow.pressButton("Deactivate");
		} else {
			deactivationPhoneFlow.pressSubmitButton("Deactivate");
		}
		
		if (deactivationPhoneFlow.textboxVisible("r2:1:r1:2:it2::content")) {
			deactivationPhoneFlow.typeInTextField("r2:1:r1:2:it2::content", email);
			if (deactivationPhoneFlow.buttonVisible("Save Email")) {
				deactivationPhoneFlow.pressButton("Save Email");
			} else {
				deactivationPhoneFlow.pressSubmitButton("Save Email");
			}
		}
		
		Assert.assertTrue("Failed to complete deactivtion", deactivationPhoneFlow.h2Visible("Transaction Summary")
				|| deactivationPhoneFlow.divVisible("Your phone has been deactivated."));
		
	}
	
    public void ntDeactivateEsn(String esn, String reason) throws Exception {
		deactivationPhoneFlow.navigateTo(rb.getString("sm.csrhome"));
		deactivateEsn(esn, reason);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		deactivationPhoneFlow = tracfoneFlows.deactivationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public static ESNUtil getEsnUtil() {
		return esnUtil;
	}

	public static void setEsnUtil(ESNUtil esnUtil) {
		DeactivatePhone.esnUtil = esnUtil;
	}
	
}