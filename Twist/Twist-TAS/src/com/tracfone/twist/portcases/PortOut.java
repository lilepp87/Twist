package com.tracfone.twist.portcases;

// JUnit Assert framework can be used for verification

import java.util.Random;
import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.Activation.ActivatePhone;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.supportOperations.CaseOperations;
import com.tracfone.twist.utils.TwistUtils;

public class PortOut {
		
	private ActivatePhone activatePhone ;
	private MyAccountFlow myAccountFlow;
	private CaseOperations caseOps = 	new CaseOperations();
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	
	public ActivatePhone getActivatePhone() {
		return activatePhone;
	}

	public void setActivatePhone(ActivatePhone activatePhone) {
		this.activatePhone = activatePhone;
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void findESNForPartNumber(String status, String partNumber, String simPart) throws Exception {
		activatePhone.enterEsnForPartSim(status, partNumber, simPart);
	}
	
	public void goToPortOut() throws Exception {
		myAccountFlow.clickLink("Transactions");
		myAccountFlow.clickLink("Portability - Port Out");
	}
	
	public void createTicketForPortOut() throws Exception {
		myAccountFlow.chooseFromSelect    ("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Medium");
		myAccountFlow.chooseFromSelect    ("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc4/", "Pending Approval");
		myAccountFlow.typeInTextField     ("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it1/", "itq portout ticket test");
		myAccountFlow.typeInTextArea      ("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it2/", "itq-test");
		
		clickButton("Save & Continue");
		
		String extCarrier[] = {"AT&T","SPRINT","VERIZON"};
		int index = new Random().nextInt(extCarrier.length);
		
		String extCarrierValue = extCarrier[index];
		System.out.println(extCarrierValue);

//		myAccountFlow.typeInTextField ("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:5:it1::content/", "158");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:soc1/", extCarrierValue);
		TwistUtils.setDelay(2);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:soc1/", extCarrierValue);
		TwistUtils.setDelay(2);
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:soc1/", extCarrierValue);
		//TwistUtils.setDelay(10);
		
		clickButton("Save & Continue");
		
		TwistUtils.setDelay(5);
		
		clickButton("Assign");
		
		TwistUtils.setDelay(2);
		
		clickButton("Assign");
		TwistUtils.setDelay(2);
	}
	
	public void addNotesChangeStatusShippingDetails() throws Exception {
		myAccountFlow.clickLink("Add Notes");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc2/", "Tech");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it3/").setValue(props.getString("TAS.Reason"));

		clickButton(props.getString("TAS.LogButton"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
		myAccountFlow.clickLink("Status Change");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc3/", "Address Updated");
		myAccountFlow.getBrowser().textarea("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it4/").setValue("ITQ Test-address update status changed");
		clickButton(props.getString("TAS.ChangeStatus"));
		Assert.assertTrue(myAccountFlow.divVisible("SUCCESS"));
		clickButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?d\\d::msgDlg::cancel/");
		myAccountFlow.clickLink("Shipping Address");
	//	myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12/", "94043");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it8/", "TwistFirstName");
		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it9/", "TwistLastName");
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:inputText1::content/", "1295 Charleston road");
//		myAccountFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it12::content/", "94043");
		clickButton("Save / Verify");
		TwistUtils.setDelay(2);
	}
	
	public void checkForConfirmation() throws Exception {
		
		Assert.assertTrue(myAccountFlow.cellVisible("Id Number"));
	}
	
	public void clickButton(String buttonType) {
		if (myAccountFlow.buttonVisible(buttonType)) {
			myAccountFlow.pressButton(buttonType);
		} else {
			myAccountFlow.pressSubmitButton(buttonType);
		}
	}

	public void completePortout() throws Exception {
		clickButton("Close Ticket");
		myAccountFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:soc8/", "Closed");
		clickButton("Close Ticket[1]");
	}

	

}