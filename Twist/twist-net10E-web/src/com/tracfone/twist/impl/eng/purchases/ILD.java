package com.tracfone.twist.impl.eng.purchases;

// JUnit Assert framework can be used for verification

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.MyAccountFlow;
import com.tracfone.twist.flows.tracfone.PurchasesFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.purchases.AbstractAddAirtime;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

import net.sf.sahi.client.Browser;

public class ILD {

	private static final String NET10E_PROPS_NAME = "Net10";
	private Properties properties = new Properties(NET10E_PROPS_NAME);
	private MyAccountFlow myAccountFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	
	public ILD() {

	}

	public void goToNET10ProgramsILD() throws Exception {
		myAccountFlow.clickLink(properties.getString("ILD.ILD"));
//		myAccountFlow.getBrowser().heading2("Unlimited International""UNLIMITED INTERNATIONAL").click();
		myAccountFlow.getBrowser().heading2(properties.getString("ILD.10Global")).click();
		myAccountFlow.clickLink(properties.getString("ILD.BuyNow"));
	}
	
	public void net10ILDPrograms() throws Exception {
		myAccountFlow.getBrowser().heading2(properties.getString("ILD.10Global")).click();
		myAccountFlow.clickLink(properties.getString("ILD.BuyNow"));
	
	}
	
	public void enterPhoneNumberFromPart(String partNumber) throws Exception {
		String activeEsn = phoneUtil.getActiveEsnByPartNumber(partNumber);
		phoneUtil.clearOTAforEsn(activeEsn);
		esnUtil.setCurrentESN(new ESN(partNumber, activeEsn));
	}

	public void add10ILD() throws Exception {
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("activeMIN:"+min);
		myAccountFlow.typeInTextField("min", min);
		myAccountFlow.pressSubmitButton("ONE-TIME PURCHASE");
	}
	
	public void add10ILDWithEnroll() throws Exception {
		String activeEsn = esnUtil.getCurrentESN().getEsn();
		String min = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("activeMIN:"+min);
		myAccountFlow.typeInTextField("min", min);
		myAccountFlow.pressSubmitButton("ENROLL IN INSTANT LOW BALANCE REFILL");
		//myAccountFlow.pressSubmitButton("REGISTER");
	}

    public void loginIntoMyAccount() throws Exception {
    	String email = esnUtil.getCurrentESN().getEmail();
    	String password = esnUtil.getCurrentESN().getPassword();
    	if(myAccountFlow.textboxVisible("email")){
    	myAccountFlow.typeInTextField("email", email);
    	myAccountFlow.typeInPasswordField("password", password);
    	myAccountFlow.pressSubmitButton("LOG IN");
    	}
    }

	public void orderSummary() throws Exception {
		TwistUtils.setDelay(16);
		myAccountFlow.pressSubmitButton(properties.getString("ILD.continue"));
		TwistUtils.setDelay(8);
		Assert.assertTrue(myAccountFlow.h3Visible(properties.getString("ILD.transactionsummary")));
		if(myAccountFlow.linkVisible(properties.getString("Activation.FinishButton"))){
		myAccountFlow.clickLink(properties.getString("Activation.FinishButton"));
		}
		if(myAccountFlow.strongVisible(properties.getString("Redemption.FinishButton"))){
            myAccountFlow.clickStrongMessage(properties.getString("Redemption.FinishButton"));
      }

	}

	public void goToMyAccountPage() throws Exception {
		if(myAccountFlow.linkVisible(properties.getString("Account.MyAccountLink"))){
			myAccountFlow.clickLink(properties.getString("Account.MyAccountLink"));	
		}else{
			myAccountFlow.clickLink(properties.getString("Account.MyAccountSummary"));
		}
		myAccountFlow.clickLink(properties.getString("Apps.ILD"));
	}
	
	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		myAccountFlow = tracfoneFlows.myAccountFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phone) {
		phoneUtil = phone;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

	public void createMyAccount () throws Exception {
		//String activeEsn = esnUtil.getCurrentESN().getEsn();
		//String min = phoneUtil.getMinOfActiveEsn(activeEsn);
		
		String email = esnUtil.getCurrentESN().getEmail();
		System.out.println("EMAIL:"+email);
		String password = esnUtil.getCurrentESN().getPassword();
		System.out.println("EMAIL:"+email);
		
	}
	
}

