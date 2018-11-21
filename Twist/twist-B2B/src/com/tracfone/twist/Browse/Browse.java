package com.tracfone.twist.Browse;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;

import org.junit.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.TwistUtils;

public class Browse {

	private ActivationReactivationFlow activationPhoneFlow;

	public Browse() {
		
	}

	public void goToPhones() throws Exception {
		activationPhoneFlow.clickLink("PHONES");
		activationPhoneFlow.clickLink("PHONES[1]");
		TwistUtils.setDelay(10);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void browsePhonesBasedOn(String orderBy) throws Exception {
		if (orderBy.equalsIgnoreCase("SAMSUNG")) {
			activationPhoneFlow.clickLink("SBN_facet_SAMSUNG");
			Assert.assertTrue(activationPhoneFlow.divVisible("Add to compare list")); 
		} else if (orderBy.equalsIgnoreCase("LG INC")) {
			activationPhoneFlow.clickLink("SBN_facet_LG INC");
			Assert.assertTrue(activationPhoneFlow.divVisible("Add to compare list"));
		}else if (orderBy.equalsIgnoreCase("ZTE")) {
			activationPhoneFlow.clickLink("SBN_facet_ZTE");
			Assert.assertTrue(activationPhoneFlow.divVisible("Add to compare list"));
		}
	}

	public void sortPhonesBy(String sortBy, String priceRange) throws Exception {
		activationPhoneFlow.chooseFromSelect("orderBy",sortBy);
		if(!priceRange.isEmpty()){
			activationPhoneFlow.getBrowser().heading2("Price").click();
			activationPhoneFlow.clickLink("SBN_facet_$100 to $300");
		}
	}

}