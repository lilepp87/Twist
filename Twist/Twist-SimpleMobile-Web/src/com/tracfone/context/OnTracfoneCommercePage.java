package com.tracfone.context;

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class OnTracfoneCommercePage {
	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private final static ResourceBundle rb = ResourceBundle.getBundle("SM");
	public final static String TF_COM_URL = rb.getString("tf.commerce");

	public OnTracfoneCommercePage() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(TF_COM_URL);
	}

	public void tearDown() throws Exception {
		esnUtil.clearCurrentESN();
		try {
			myAccountFlow.getBrowser().close();
			myAccountFlow.getBrowser().open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
}
