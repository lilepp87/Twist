package com.tracfone.twist.context;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import net.sf.sahi.client.ExecutionException;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;

public class Logout {

	private MyAccountFlow myAccountFlow;
	private static final ResourceBundle props = ResourceBundle.getBundle("GS");
	public Logout() {
		
	}

	public void setUp() throws Exception {
		logout();
	}

	public void tearDown() throws Exception {
		logout();
	}
	
	public void logout() {
		try {
			myAccountFlow.navigateTo(props.getString("GS.HomePage"));
			if (myAccountFlow.linkVisible(props.getString("GS.Logout"))) {
				myAccountFlow.clickLink(props.getString("GS.Logout"));
			}
		} catch (ExecutionException ex){}
		}
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
}
