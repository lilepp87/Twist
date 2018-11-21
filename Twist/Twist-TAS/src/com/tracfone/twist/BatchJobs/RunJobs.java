package com.tracfone.twist.BatchJobs;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

import net.sf.sahi.client.Browser;

public class RunJobs {

	private MyAccountFlow myAccountFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("TAS");
	public RunJobs() {
	
	}

	public void chooseInputsAsJobNameAndEnv(String brand, String jobName,
			String env) throws Exception {
		myAccountFlow.chooseFromSelect("BRAND", brand);
		myAccountFlow.chooseFromSelect("JOB[1]", jobName);
		myAccountFlow.chooseFromSelect("ENV[1]", env);
	}

	public void submitRequest() throws Exception {
		myAccountFlow.pressSubmitButton("Submit Query[1]");
	}

	public void checkForConfiramtion() throws Exception {
		Assert.assertTrue("JOB FAILED TO COMPLETE.PLEASE RE RUN AGAIN",myAccountFlow.linkVisible("Click here to go Back to Index "));
	}
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}

	public void selectJobForEnv(String jobName, String env) throws Exception {
		myAccountFlow.chooseFromSelect("JOB", jobName);
		myAccountFlow.chooseFromSelect("ENV", env);
	}

	public void submitQuery() throws Exception {
		myAccountFlow.pressSubmitButton("Submit Query");
	}

	public void sendEmailAndCheckConfirmationMessage() throws Exception {
		 myAccountFlow.typeInTextField("SUBJECT", "Completed Payemnt jobs  from TWIST");
		 myAccountFlow.typeInTextField("EMAIL", "mkankanala@tracfone.com");
		 myAccountFlow.pressSubmitButton("Send email");
		 Assert.assertTrue("JOB FAILED TO COMPLETE.PLEASE RE RUN AGAIN",myAccountFlow.linkVisible("Click here to go Back to Index "));
	}

}
