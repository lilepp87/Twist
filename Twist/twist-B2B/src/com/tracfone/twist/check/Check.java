package com.tracfone.twist.check;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

public class Check {

	private ActivationReactivationFlow activationPhoneFlow;
	private static final String parent_Admin = "znlp526ia9g5@tracfone.com";
	private static final String child_Admin = "u3feyqmoi5pm@tracfone.com";
	private static final String DEFAULT_PASS_OLD = "123456";
	private static final String DEFAULT_PASS = "123456a";
	private ResourceBundle rb = ResourceBundle.getBundle("B2B");

	public Check() {

	}

	public void checkOrders(String type) throws Exception {
		if (type.equalsIgnoreCase("ORG")) {
			activationPhoneFlow.clickLink("Organization Orders");
			activationPhoneFlow.clickLink(rb.getString("B2B.OrderORG")); 
			Assert.assertTrue(activationPhoneFlow.cellVisible("Twist FirstName Twist LastName"));
		} else {
			activationPhoneFlow.clickLink("My Orders");	
			Assert.assertTrue(activationPhoneFlow.cellVisible("Ready for remote fulfillment")); 
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void goToPhones() throws Exception {
		activationPhoneFlow.clickLink("PHONES[1]");
	}

	public void comparePhones() throws Exception {
		activationPhoneFlow.clickLink("SBN_facet_LG INC");
		activationPhoneFlow.clickLink("Add to compare list");
		activationPhoneFlow.clickLink("Add to compare list[1]");
		activationPhoneFlow.clickLink("Compare");
		Assert.assertTrue(activationPhoneFlow.linkVisible("View Full Details"));
	}

	public void checkCSRPendingRequests(String approvalType) throws Exception {
		int approvalCount = 0;
//		activationPhoneFlow.clickLink("headerLogin");
		
		activationPhoneFlow.typeInTextField("logonId", "test@b2b9.com");
		activationPhoneFlow.typeInPasswordField("logonPassword", "password1");
/* 		activationPhoneFlow.clickLink("WC_AccountDisplay_links_2"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));
/*		activationPhoneFlow.clickLink("Approval Requests"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.ApproveReq"));
		if (approvalType.equalsIgnoreCase("Profile")) {
			activationPhoneFlow.chooseFromSelect("arSelectUserType", "Requests for user Registration");
		} else {
			activationPhoneFlow.chooseFromSelect("arSelectUserType","Requests for Organization Registration");
		}
		while (activationPhoneFlow.linkVisible("Twist FirstName Twist LastName")) {
			activationPhoneFlow.clickLink("Twist FirstName Twist LastName");
			activationPhoneFlow.clickLink("APPROVE");
			approvalCount++ ;
			System.out.println(approvalCount);
		}

	}

	public void loginAs(String accountType) throws Exception {
		if (accountType.equalsIgnoreCase("ParentAdmin")) {
			activationPhoneFlow.typeInTextField("logonId", parent_Admin);
			activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS_OLD);	
		} else if (accountType.equalsIgnoreCase("ChildAdmin")) {
			activationPhoneFlow.typeInTextField("logonId", child_Admin);
			activationPhoneFlow.typeInPasswordField("logonPassword", DEFAULT_PASS);
		}
/*		activationPhoneFlow.clickLink("WC_AccountDisplay_links_2"); */
		activationPhoneFlow.clickLink(rb.getString("B2B.SignIn"));  /* Link for Sign in _ Mayuri*/
	}
}

