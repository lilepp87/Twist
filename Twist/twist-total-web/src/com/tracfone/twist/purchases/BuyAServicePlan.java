package com.tracfone.twist.purchases;

// JUnit Assert framework can be used for verification

import org.apache.log4j.*;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.PaymentFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.TwistUtils;

public class BuyAServicePlan {

	private PaymentFlow paymentFlow;
	private ESNUtil esnUtil;
	private static PhoneUtil phoneUtil;
	private static final String REOCCURING = "Reoccuring";
	private static final String QUEUED = "QUEUED";
	private static Logger logger = LogManager.getLogger(BuyAServicePlan.class.getName());
	
	public BuyAServicePlan() {

	}

	public void selectBuyAServicePlan() throws Exception {
		paymentFlow.clickLink("Buy Plan");
		//paymentFlow.pressSubmitButton("PURCHASE");
		paymentFlow.clickLink("continue[1]");
	}
	
	public void goToServicePlans() throws Exception {
		paymentFlow.clickLink("Service Plans");
	}
	
	public void enterMinAndClickOnCheckAvailablePlans(String esnPart) throws Exception {
		String esn = esnUtil.getCurrentESN().getEsn();
		logger.info(esn);
		String min = phoneUtil.getMinOfActiveEsn(esn);
		//paymentFlow.typeInTextField("phone_number", min);
		//pressButton("Check Plan Availability");
		paymentFlow.typeInTextField("input_number", min);
		paymentFlow.getBrowser().button("see available plans").click(); 
	}
	
	public void selectServicePlanPlan1Plan2(String purchaseType,String planOne, String servicePlan, String howToAdd, int numOfDev) throws Exception {
        ESN esn = esnUtil.getCurrentESN();
        storeRedeemData(esn);
        TwistUtils.setDelay(10);
		if (!planOne.contains("Line")) {
			String oldPlanDesc = getPlanFromPin(planOne);
			selectNewPlan(purchaseType, oldPlanDesc, servicePlan, numOfDev);
		} else {
			selectNewPlan(purchaseType, planOne, servicePlan, numOfDev);
		}
		if(howToAdd.equalsIgnoreCase("Now") && purchaseType.equalsIgnoreCase("Reoccuring")){
			if (buttonVisible("apply now")) {
				paymentFlow.pressSubmitButton("apply now");
			}
           esn.setActionType(6);
          } 
		if(howToAdd.equalsIgnoreCase("Queued") && purchaseType.equalsIgnoreCase("Reoccuring")){
			if (buttonVisible("end of service")) {
				pressButton("end of service");
			}
		   esn.setActionType(401);
           
        }
		if(howToAdd.equalsIgnoreCase("Now") && purchaseType.equalsIgnoreCase("one time-purchase")){
					if (buttonVisible("apply now")) {
						paymentFlow.pressSubmitButton("apply now");
					}
		           esn.setActionType(6);
		          } 
				if(howToAdd.equalsIgnoreCase("Queued") && purchaseType.equalsIgnoreCase("one time-purchase")){
					if (buttonVisible("end of service")) {
						pressButton("end of service");
					}
				   esn.setActionType(401);
		          }
				else{
					
					if(buttonVisible("apply now[1]")) {
						paymentFlow.pressSubmitButton("apply now[1]");
					}
				}
				
        esnUtil.getCurrentESN().setTransType("TW Activate with purchase");
  }
	
	public String getPlanFromPin(String oldPlanPin){
		String planName = null;
		if(oldPlanPin.equalsIgnoreCase("TWP00025")|| oldPlanPin.equalsIgnoreCase("TWAPP00025")){
			planName = "TW 1 Line No Data";
		}else if(oldPlanPin.equalsIgnoreCase("TWPS00035")|| oldPlanPin.equalsIgnoreCase("TWAPP00035")){
			planName = "TW 1 Line 5 GB";
		}else if(oldPlanPin.equalsIgnoreCase("TWP00060") || oldPlanPin.equalsIgnoreCase("TWNS00060")|| oldPlanPin.equalsIgnoreCase("TWAPP00060")){
			planName = "TW 2 Line 8 GB";
		}else if(oldPlanPin.equalsIgnoreCase("TWP00085")){
			planName = "TW 3 Line 12 GB";
		}else if(oldPlanPin.equalsIgnoreCase("TWAPP00110")){
			planName = "TW 4 Line 15 GB";
		}else {
			throw new IllegalArgumentException("Plan PIN" + oldPlanPin
					+ " not found");
		}
		return planName;
	}

	public void selectNewPlan(String purchaseType, String oldPlan,
			String servicePlan, int numOfDev) {
		int option = 100;
		String buttonName;
		if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		}
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 2) {
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 2;
		}
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 3) {
			   if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 0;
			   else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 1;
				
		 }
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 4) {
				 if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 0;
			}	 
			
		// 4 line device end //
		  
		//#two line device start
		else if (oldPlan.equalsIgnoreCase("TW 2 Line 8 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		 }
		 else if(oldPlan.equalsIgnoreCase("TW 2 Line 8 GB") && numOfDev == 2 ){
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			    option = 2;
			} 
		//#two line device start end
	    
	   // three line device start
	   else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		 }
		 else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB")&& numOfDev == 2){
				
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 2;
			}
		 else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB")&& numOfDev == 3){
				 if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
					option = 1;
			}
	// 	three line device end
					
		 else  if (oldPlan.equalsIgnoreCase("TW 1 Line 5 GB")) {
			 if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
					option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				option = 6;
			else {
				throw new IllegalArgumentException("Plan " + servicePlan + " not found");
			}
		} 
		
		else if (oldPlan.equalsIgnoreCase("TW 1 Line No Data")) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				option = 6;
			else {
				throw new IllegalArgumentException("Plan " + servicePlan + " not found");
			}
		 }
		else {
			throw new IllegalArgumentException("Plan " + servicePlan
					+ " not found");
		}
		System.out.println("Option::" + option);
		paymentFlow.getBrowser().submit("buy[" + option + "]").click();
		
        if (REOCCURING.equalsIgnoreCase(purchaseType)) {
              //buttonName = "ENROLL IN AUTO-REFILL[" + option + "]";
              paymentFlow.getBrowser().button("save with Auto-Refill").click(); 
            //  paymentFlow.getBrowser().submit("add now").click(); 
        } else {
              //buttonName = "ONE-TIME PURCHASE[" + option + "]";
              paymentFlow.getBrowser().button("one time-purchase").click(); 
        }

        //pressButton(buttonName);
  }
	
	public void selectServicePlan(String purchaseType, String servicePlan, String howToAdd) throws Exception {
		ESN esn = esnUtil.getCurrentESN();
		storeRedeemData(esn);
		selectPlan(purchaseType, servicePlan);
		if (QUEUED.equalsIgnoreCase(howToAdd)) {
			if (buttonVisible("ADD TO RESERVE")) {
				pressButton("ADD TO RESERVE");
			}
			
			if (buttonVisible("add to reserve")) {
				pressButton("add to reserve");
			}
			esn.setActionType(401);
		} else {
			if (buttonVisible("ADD NOW")) {
				pressButton("ADD NOW");
			}
			
			if (buttonVisible("add now")) {
				pressButton("add now");
			}
			esn.setActionType(6);
		}
		esnUtil.getCurrentESN().setTransType("TW Activate with purchase");
	}

	public void selectPortServicePlan(String servicePlan) throws Exception {
		selectPlan("", servicePlan);
		paymentFlow.pressSubmitButton(PaymentFlow.BuyAirtimeStraighttalkWebFields.SubmitButton.name);
		esnUtil.getCurrentESN().setTransType("TW Port with purchase");
	}

	public void selectPlan(String purchaseType, String servicePlan) {
		int option;
		String buttonName;
		
 		if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan)) {
			option = 6;
		} else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan)) {
			option = 5;
		} else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan)) {
			option = 4;
		} else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan)) {
			option = 1;
		} else if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan)) {
			option = 0;
		} else if ("TW 1.5 GB Add-On Data".equalsIgnoreCase(servicePlan)) {
			option = 7;
		} else {
			throw new IllegalArgumentException("Plan " + servicePlan + " not found" );
		}
		
 		
 		System.out.println("Option::"+option);
 		paymentFlow.getBrowser().submit("buy[" + option + "]").click(); 
 		
		if (REOCCURING.equalsIgnoreCase(purchaseType)) {
			//buttonName = "ENROLL IN AUTO-REFILL[" + option + "]";
			paymentFlow.getBrowser().button("save with Auto-Refill").click(); 
		} else {
			//buttonName = "ONE-TIME PURCHASE[" + option + "]";
			paymentFlow.getBrowser().button("one time-purchase").click(); 
		}

		//pressButton(buttonName);
	}
	
	
	public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(25);
		//Assert.assertTrue(message, condition)(paymentFlow.strongVisible(PaymentFlow.BuyAirtimeStraighttalkWebFields.OrderSummaryMessage.name));
		pressButton("FINISH");
//		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}

	/*public void reviewOrderSummary() throws Exception {
		TwistUtils.setDelay(15);
		Assert.assertTrue("Could not find service summary", paymentFlow.h3Visible("service summary"));
		paymentFlow.pressSubmitButton("finish");
		esnUtil.getCurrentESN().setRedeemType(false);
		esnUtil.getCurrentESN().setTransType("Total Wireless Buy a Plan");
		esnUtil.getCurrentESN().setActionType(6);
		phoneUtil.checkRedemption(esnUtil.getCurrentESN());
	}*/
	
	private void storeRedeemData(ESN esn) {
		esn.setBuyFlag(true);
		esn.setRedeemType(false);
		esn.setTransType("Total Wireless Buy a Plan");
		esn.setActionType(401);
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		paymentFlow = tracfoneFlows.paymentFlow();
	}
	
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	
	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	private void pressButton(String button) {
		if (paymentFlow.buttonVisible(button)) {
			paymentFlow.pressButton(button);
		} else if (paymentFlow.submitButtonVisible(button)) {
			paymentFlow.pressSubmitButton(button);
		}
	}

	private boolean buttonVisible(String button) {
		return paymentFlow.buttonVisible(button) || paymentFlow.submitButtonVisible(button);
	}
	
	public void Login() throws Exception {
		ESN curresn = esnUtil.getCurrentESN();
		paymentFlow.getBrowser().div("log into my account").click();
		paymentFlow.typeInTextField("userID", curresn.getEmail());
		paymentFlow.typeInPasswordField("password", curresn.getPassword());
		paymentFlow.getBrowser().div("loginaccount_submit").click();
		/*paymentFlow.typeInTextField("userid", curresn.getEmail());
		paymentFlow.typeInPasswordField("password", curresn.getPassword());
		paymentFlow.pressButton("default_login_btn");*/
	}
	
	public void continueAsGuest() throws Exception {
		if (paymentFlow.getBrowser().div("guestcheckout").isVisible()) {
			// pressButton("GUEST CHECKOUT");
			paymentFlow.getBrowser().div("guestcheckout").click();
		} else {
			Login();
			paymentFlow.getBrowser().submit("Add Payment Method").click();
		}
		
		TwistUtils.setDelay(15);
	}
	public void selectServicePlanForAutorefill(String purchaseType,String planOne, String servicePlan, String howToAdd, int numOfDev) throws Exception {
		 ESN esn = esnUtil.getCurrentESN();
	        storeRedeemData(esn);
	        TwistUtils.setDelay(10);
			if (!planOne.contains("Line")) {
				String oldPlanDesc = getPlanFromPin(planOne);
				selectPlanAutorefill(purchaseType, oldPlanDesc, servicePlan,numOfDev);
			} else {
				selectPlanAutorefill(purchaseType, planOne, servicePlan, numOfDev);
			}
			if(howToAdd.equalsIgnoreCase("Now") && purchaseType.equalsIgnoreCase("Reoccuring")){
				if (buttonVisible("apply now")) {
					paymentFlow.pressSubmitButton("apply now");
				}
				else{
					if(buttonVisible("apply now[1]")) {
						paymentFlow.pressSubmitButton("apply now[1]");
					}
				}
	           esn.setActionType(6);
	          } 
			if(howToAdd.equalsIgnoreCase("Later") && purchaseType.equalsIgnoreCase("Reoccuring")){
				if (buttonVisible("end of service")) {
					pressButton("end of service");
				}
				else{
					if(buttonVisible("apply now[1]")) {
						paymentFlow.pressSubmitButton("apply now[1]");
					}
					
				}
			   esn.setActionType(401);
	           
	        }
			
			
	        esnUtil.getCurrentESN().setTransType("TW Activate with purchase");
	  }
	
	public void selectPlanAutorefill(String purchaseType, String oldPlan,
			String servicePlan, int numOfDev) {
		int option = 100;
		String buttonName;
		
		// 4 line device start //
		
		if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		}
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 2) {
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 2;
		}
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 3) {
			   if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 0;
			   else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 1;
				
		 }
		else if (oldPlan.equalsIgnoreCase("TW 4 Line 15 GB") && numOfDev == 4) {
				 if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 0;
			}	 
			
		// 4 line device end //
		  
		//#two line device start
		else if (oldPlan.equalsIgnoreCase("TW 2 Line 8 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		 }
		 else if(oldPlan.equalsIgnoreCase("TW 2 Line 8 GB") && numOfDev == 2 ){
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			    option = 2;
			} 
		//#two line device start end
	    
	   // three line device start
	   else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB") && numOfDev == 1) {
			if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
				option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
			     option = 6;
		 }
		 else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB")&& numOfDev == 2){
				
				if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 1;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				     option = 2;
			}
		 else if (oldPlan.equalsIgnoreCase("TW 3 Line 12 GB")&& numOfDev == 3){
				 if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
					option = 0;
				else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
					option = 1;
			}
	// 	three line device end
					
		 else  if (oldPlan.equalsIgnoreCase("TW 1 Line 5 GB")) {
			 if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
					option = 0;
			 else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				option = 6;
			else {
				throw new IllegalArgumentException("Plan " + servicePlan + " not found");
			}
		} 
		
		else if (oldPlan.equalsIgnoreCase("TW 1 Line No Data")) {
			 if ("TW 1 Line No Data".equalsIgnoreCase(servicePlan))
					option = 0;
			else if ("TW 1 Line 5 GB".equalsIgnoreCase(servicePlan))
				option = 1;
			else if ("TW 2 Line 8 GB".equalsIgnoreCase(servicePlan))
				option = 4;
			else if ("TW 3 Line 12 GB".equalsIgnoreCase(servicePlan))
				option = 5;
			else if ("TW 4 Line 15 GB".equalsIgnoreCase(servicePlan))
				option = 6;
			else {
				throw new IllegalArgumentException("Plan " + servicePlan + " not found");
			}
		 }
		else {
			throw new IllegalArgumentException("Plan " + servicePlan
					+ " not found");
		}
		System.out.println("Option::" + option);
		paymentFlow.getBrowser().submit("buy[" + option + "]").click();

     
  }


}