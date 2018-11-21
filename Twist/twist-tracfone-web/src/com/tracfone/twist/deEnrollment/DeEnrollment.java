/* Created for release 0310 - Auto Refill option in Pay As You Go Plans */
// JUnit Assert framework can be used for verification

package com.tracfone.twist.deEnrollment;
import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.deEnrollment.DeEnrollment;
import org.junit.Assert;
import com.tracfone.twist.context.OnTracfoneHomePage;
import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;

import net.sf.sahi.client.Browser;

public class DeEnrollment {

	private Browser browser;
	
	public DeEnrollment(Browser browser) {
		this.browser = browser;
	}
	
	public void deEnrollment() throws Exception {
		browser.link("Enroll in Pay-As-You-Go Auto-Refill Plans").click();
		browser.link("Cancel").click();
		browser.radio("program_enroll_objid").click();
		browser.submit("Continue").click();
		browser.submit("Yes").click();
		browser.link("Go to Account Summary").click();
		browser.span("Details").click();
		browser.link("Sign Out").click();
	
	}
}

/*Working on changing the parameters. */
/*	private DeEnrollmentFlow denrollmentFlow;
	
	DenrollmentFlow denrollmentFlow = new denrollmentFlow(this);
	
	public DeEnrollment(DenrollmentFlow denrollmentFlow) {
		this.denrollmentFlow = denrollmentFlow;
	}
	
	public void deEnrollment() throws Exception {
		denrollmentFlow.clickLink("Enroll in Pay-As-You-Go Auto-Refill Plans").click();
		denrollmentFlow.clickLink("Cancel").click();
		denrollmentFlow.radio("program_enroll_objid").click();
		denrollmentFlow.pressSubmitButton(properties.getString("continue"));
		denrollmentFlow.submit("Yes").click();
		denrollmentFlow.clickLink("Go to Account Summary").click();
		denrollmentFlow.clickLink("Sign Out").click();
}*/