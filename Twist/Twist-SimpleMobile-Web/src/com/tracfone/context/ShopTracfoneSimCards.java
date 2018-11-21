package com.tracfone.context;

import java.util.ResourceBundle;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESNUtil;

public class ShopTracfoneSimCards {
	private ESNUtil esnUtil;
	private MyAccountFlow myAccountFlow;
	private final static ResourceBundle rb = ResourceBundle.getBundle("SM");
	public final static String TF_SIM_URL = rb.getString("tf.sim");

	public ShopTracfoneSimCards() {

	}

	public void setUp() throws Exception {
		myAccountFlow.navigateTo(TF_SIM_URL);
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
