package com.tracfone.twist.numberchange;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.cbo.CboUtility;
import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class MinChange {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private String error_msg;
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	private static CboUtility cboUtility;
	public MinChange() {

	}

	public void minChangeForActivationZipStatusCellTech(String zip, String status, String cellTech) throws Exception {
		if(zip.equals("692"))
			zip="00"+zip;
		
		TwistUtils.setDelay(15);
		pressButton("Refresh");
		activationPhoneFlow.clickLink("MIN Change");
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
		pressButton("Process Min Change");
		if (activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl5/").isVisible()) {
			error_msg = activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl5/").getText();
		}
		Assert.assertTrue("ESN:" + esnUtil.getCurrentESN().getEsn() + ";" + "Error " + error_msg,
				activationPhoneFlow.h2Visible("Transaction Summary"));
		finishPhoneActivation(cellTech, "Past Due");
	}

	private void finishPhoneActivation(String cellTech, String status) throws Exception {
		TwistUtils.setDelay(5);
		ESN currEsn = esnUtil.getCurrentESN();
		esnUtil.addRecentActiveEsn(currEsn, cellTech, status, "TAS SIM/MIN CHANGE["+esnUtil.getCurrentBrand()+"]");
	}

	public void simChangeZipWithNumber(String simPart, String zip, String minType, String cellTech) 
			throws Exception {
		pressButton("Refresh");
		if(zip.equals("692"))
			zip="00"+zip;
		
		activationPhoneFlow.clickLink("SIM Change");
		String newSim = simUtil.getNewSimCardByPartNumber(simPart);
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", newSim);
		if (minType.equalsIgnoreCase("New")) {
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_0/");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", zip);
		} else {
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
			activationPhoneFlow.selectRadioButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:sor1:_1/");
		}
		if(buttonVisible("Balance Inquiry")){
			pressButton("Balance Inquiry");
			cboUtility.simulateBalanceInquiry(esnUtil.getCurrentESN().getEsn());
			TwistUtils.setDelay(3);
			pressButton("Get Balance");
			cboUtility.simulateBalanceInquiry(esnUtil.getCurrentESN().getEsn());
			pressButton("Get Balance");
			//simulate BI CBO call ends here
			pressButton("Done");
			
		}
		pressButton("Process");
		TwistUtils.setDelay(10);
		if(activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").isVisible()){
			error_msg= activationPhoneFlow.getBrowser().span("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:pgl2/").getText();
		}
		Assert.assertTrue("ESN:"+esnUtil.getCurrentESN().getEsn()+";"+"Error "+error_msg, activationPhoneFlow.h2Visible("Transaction Summary"));
		finishPhoneActivation(cellTech, minType);
	}

	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}
	public void setCboUtility(CboUtility cboUtility) {
		this.cboUtility = cboUtility;
	}
	
	public void createSimExchangeCaseWithZipCode(String zip) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		activationPhoneFlow.clickLink("Transactions");
		activationPhoneFlow.clickLink("Activation");
		activationPhoneFlow.typeInTextField("/r2:\\d:r1:\\d:it2/", zip);
		pressButton("Activate");
		if (activationPhoneFlow.buttonVisible("Exchange")) {
			// pressButton("Exchange");
		}
		activationPhoneFlow.clickLink("ESN Support");
		activationPhoneFlow.clickLink("Solutions");
		activationPhoneFlow.clickCellMessage("Phone/SIM Exchanges Non-Defective");
		activationPhoneFlow.clickLink("Solution: 4001");
		activationPhoneFlow.clickLink("Ticket: Technology Exchange SIM Card Exchange");
		activationPhoneFlow.clickLink("Ticket: Technology Exchange SIM Card Exchange");
		activationPhoneFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/").setValue(props.getString("TAS.Reason"));
		activationPhoneFlow.typeInTextField("/r2:\\d:r1:\\d:r1:\\d:it8/", zip);
		pressButton(props.getString("TAS.LoadPart"));
		//pressButton(props.getString("TAS.Search"));
		pressButton(props.getString("TAS.Save&Continue"));
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:6:it1::content/","1000");
		pressButton(props.getString("TAS.Save&Continue"));
		pressButton(props.getString("TAS.Save&Continue"));
	}

	public void checkAutoRefillStatus() throws Exception {
		
		if(!esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
			String enrollStatus=phoneUtil.checkEnrollment(esnUtil.getCurrentESN().getEsn());
			Assert.assertTrue(enrollStatus.equalsIgnoreCase("ENROLLED"));
		}
		
	}
	private boolean buttonVisible(String button) {
		return activationPhoneFlow.buttonVisible(button) || activationPhoneFlow.submitButtonVisible(button);
	}

}