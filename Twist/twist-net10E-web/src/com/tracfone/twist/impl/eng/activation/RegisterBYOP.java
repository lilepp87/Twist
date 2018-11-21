package com.tracfone.twist.impl.eng.activation;

// JUnit Assert framework can be used for verification

import static junit.framework.Assert.assertTrue;
import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.util.Properties;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RegisterBYOP {

	private static final String NET10E_PROPS_NAME = "Net10";
	private ActivationReactivationFlow activationPhoneFlow;
	private Properties props;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	private final Properties properties = new Properties("Net10");
	private static final String INVALID_ESN_ERR = "The MEID/Serial Number you entered is not compatible with this program. " +
			"Only Serial Numbers/IMEI/MEID HEX/MEID DEC with 11, 14, 15, or 18 numbers are eligible to be registered in this " +
			"program. Please verify the number and try again.";

	public RegisterBYOP() {
		props = new Properties(NET10E_PROPS_NAME);
	}

	public void goToRegisterBYOPCDMAPage() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("Twist.BYOPURL"));
	}

	public void enterEsnSimAndCarrierForLteIphoneWithZip(String active, String esnType, String simPart,
			String carrier, String isLTE, String iPhone, String zip , String IsHD) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn;
		if (props.getString("BYOP.HexEsn").equalsIgnoreCase(esnType)) {
			esn = new ESN("NTBYOPHEX", esnStr);
			String hexEsn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField(props.getString("BYOP.MEIDText"), hexEsn);
		} else {
			esn = new ESN("NTBYOPDEC", esnStr);
			activationPhoneFlow.typeInTextField(props.getString("BYOP.MEIDText"), esnStr);
		}
		esnUtil.setCurrentESN(esn);
		
		activationPhoneFlow.typeInTextField("zip", zip);
		activationPhoneFlow.pressSubmitButton("default_submit_btn");
		int edited = 0;
		TwistUtils.setDelay(3);
		for (int i = 0; i < 10 && edited == 0; i++) {
			TwistUtils.setDelay(3+i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, active, isLTE, iPhone,IsHD);
			System.out.println("edit: " + edited);
		}
		TwistUtils.setDelay(5);
		
		/*if ("Sprint".equalsIgnoreCase(carrier)) {
			
			activationPhoneFlow.chooseFromSelect("lte_tech", isLTE);
			activationPhoneFlow.chooseFromSelect("iphone5", iPhone);
			activationPhoneFlow.pressSubmitButton("Continue");
			
			if ("Yes".equalsIgnoreCase(iPhone)) {
				//If Sprint and iPhone enter remove lte sim 
				String simStr = TwistUtils.generateRandomSim();
				activationPhoneFlow.typeInTextField("lte_sim_number", simStr);
				activationPhoneFlow.pressButton(props.getString("BYOP.continue"));	
			}
			if (props.getString("BYOP.Active").equalsIgnoreCase(active) ) {
				activationPhoneFlow.selectCheckBox(props.getString("BYOP.AgreeCheckbox"));
			}
		} else {
			// If Verizon needs a new sim
			if(!simPart.equals("null")){
				String vzSimStr = simUtil.getNewSimCardByPartNumber(simPart);
				activationPhoneFlow.typeInTextField("lte_sim_number", vzSimStr);
				activationPhoneFlow.pressSubmitButton("Continue");
			}
			//If Verizon enter if active, Sprint has its own API
			if (props.getString("BYOP.Active").equalsIgnoreCase(active) ) {
				activationPhoneFlow.chooseFromSelect("phone_active", "Yes");
				activationPhoneFlow.selectCheckBox(props.getString("BYOP.AgreeCheckbox"));
			} else {
				activationPhoneFlow.chooseFromSelect("phone_active", "No");
			}
		}*/
	}
	
	public void enterNetworkAccessCode(String accessPin) throws Exception {
		activationPhoneFlow.selectRadioButton("REGISTRATIONCODE");
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);		
		activationPhoneFlow.typeInTextField("registration_code", pin);
		activationPhoneFlow.pressSubmitButton(props.getString("Redemption.ContinueButton"));
		
		esnUtil.getCurrentESN().setTransType("NT BYOP Register with Pin");
		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
	}

	public void chooseToUpgrade() throws Exception {
		activationPhoneFlow.selectRadioButton("UPGRADEBYOPCDMA");
		
		ESN oldEsn;
		if (esnUtil.peekRecentActiveEsn("NTBYOPHEX")) {
			oldEsn = esnUtil.popRecentActiveEsn("NTBYOPHEX");
		} else {
			oldEsn = esnUtil.popRecentActiveEsn("NTBYOPDEC");
		}
		activationPhoneFlow.typeInTextField("old_meid", oldEsn.getEsn());
		activationPhoneFlow.pressSubmitButton(props.getString("Redemption.ContinueButton"));
		
		esnUtil.getCurrentESN().setTransType("NT BYOP Register with Upgrade");
		esnUtil.getCurrentESN().setFromEsn(oldEsn);
	}
	
	public void chooseToPurchaseRegistrationCode() throws Exception {
		activationPhoneFlow.selectRadioButton("NEEDREGISTRATIONCODE");
		activationPhoneFlow.pressSubmitButton(props.getString("Redemption.ContinueButton"));
		
		esnUtil.getCurrentESN().setTransType("NT BYOP Register with Purchase");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}
	
	public void finishRegistration() throws Exception {
		//Assert.assertTrue(activationPhoneFlow.spanVisible(props.getString("BYOP.Congratulations")));
		//activationPhoneFlow.pressSubmitButton(props.getString("BYOP.ActivateNow"));
		//phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}
	
	public void enterAnInvalidESN(String esn) throws Exception {
		activationPhoneFlow.typeInTextField("phone_meid", esn);
		activationPhoneFlow.typeInTextField("zipcode", "33178");
		activationPhoneFlow.chooseFromSelect("lte_tech", "Yes");
		activationPhoneFlow.chooseFromSelect("iphone5", "Yes");
		activationPhoneFlow.pressSubmitButton("Continue");
		Assert.assertTrue(activationPhoneFlow.divVisible(INVALID_ESN_ERR));
		activationPhoneFlow.pressSubmitButton("Continue");
		Assert.assertTrue(activationPhoneFlow.divVisible(INVALID_ESN_ERR));
		activationPhoneFlow.pressSubmitButton("Continue");
		Assert.assertFalse(activationPhoneFlow.divVisible(INVALID_ESN_ERR));
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}
	
	public void setPhoneUtil(PhoneUtil phone) {
		phoneUtil = phone;
	}
	
	public void setSimUtil(SimUtil newSimUtil) {
		simUtil = newSimUtil;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

	public void goToBYOPFinder() throws Exception {
		activationPhoneFlow.navigateTo(props.getString("Twist.ByopFinder"));
	}

	public void enterEsnAndCarrierForLteIphoneWithZip(String status, String esnType, String carrier, String isLTE, String iPhone,String zipCode, String IsHD) throws Exception {
		
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		
		ESN esn;
		if (props.getString("BYOP.HexEsn").equalsIgnoreCase(esnType)) {
			esn = new ESN("NTBYOPHEX", esnStr);
			String hexEsn = phoneUtil.convertMeidDecToHex(esn);
			//activationPhoneFlow.typeInTextField(props.getString("BYOP.MEIDText"), hexEsn);
		} else {
			esn = new ESN("NTBYOPDEC", esnStr);
			//activationPhoneFlow.typeInTextField(props.getString("BYOP.MEIDText"), esnStr);
		}
		esnUtil.setCurrentESN(esn);
		
		activationPhoneFlow.clickLink("othersClick");
		activationPhoneFlow.typeInTextField("zip",zipCode);
		activationPhoneFlow.typeInTextField("phone_meid",esnStr);
		activationPhoneFlow.pressSubmitButton(props.getString("BYOP.FinderContinue"));
		int edited = 0;
		TwistUtils.setDelay(3);
		for (int i = 0; i < 10 && edited == 0; i++) {
			TwistUtils.setDelay(4+i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, status, isLTE, iPhone,IsHD);
			System.out.println("edit: " + edited);
		}
		TwistUtils.setDelay(5);
	}

	public void enterSimAndNAC(String isActive, String sim, String pin)	throws Exception {
		// String nac = phoneUtil.getNewPinByPartNumber(pin);
		activationPhoneFlow.pressButton("REGISTER AND ACTIVATE");
		TwistUtils.setDelay(5);
		if (isActive.equalsIgnoreCase("Active")) {
			activationPhoneFlow.pressButton("YES[1]");
			TwistUtils.setDelay(5);
			activationPhoneFlow.pressButton("AGREE");
		} else {
			activationPhoneFlow.pressButton("NO[1]");
		}
		TwistUtils.setDelay(5);
		if (activationPhoneFlow.textboxVisible("lte_capable_input")) {
			String simStr = simUtil.getNewSimCardByPartNumber(sim);
			activationPhoneFlow.typeInTextField("lte_capable_input", simStr);
			activationPhoneFlow.pressSubmitButton("CONTINUE");
		}
		// if(activationPhoneFlow.textboxVisible("network_access_code_input")){
		// activationPhoneFlow.typeInTextField("network_access_code_input",nac);
		// }
	}

	public void enterSimAndPurchaseNAC(String isActive, String sim)
			throws Exception {
		activationPhoneFlow.pressButton("default_submit_btn3");
		if(isActive.equalsIgnoreCase("Active")){
			activationPhoneFlow.pressButton("YES[1]");
			TwistUtils.setDelay(5);
			activationPhoneFlow.pressButton("AGREE");
		}else{
			activationPhoneFlow.pressButton("NO[1]");			
		}
		TwistUtils.setDelay(5);
		if(activationPhoneFlow.textboxVisible("lte_capable_input")){
			String simStr = simUtil.getNewSimCardByPartNumber(sim);
			activationPhoneFlow.typeInTextField("lte_capable_input",simStr);	
		}
		activationPhoneFlow.pressSubmitButton("CONTINUE");
	}

	public void chooseCarrier(String carrier) throws Exception {
		if("RSS".equalsIgnoreCase(carrier)){
			activationPhoneFlow.clickLink("othersClick");
		}
	}

	public void chooseForESNStatusIfLteAndSimAndNAC(String KIT, String status,
			String islte , String sim, String NACcode) throws Exception {

		
		TwistUtils.setDelay(10);
		
		Assert.assertTrue(activationPhoneFlow.strongVisible("CONGRATULATIONS! YOUR DEVICE IS COMPATIBLE"));
		
		if (KIT.equals("BUY A KIT")||KIT.equals("REGISTER AND ACTIVATE")){
			/*if (KIT.equals("BUY A KIT")){
			activationPhoneFlow.pressButton("default_submit_btn3");
			}else if(KIT.equals("REGISTER AND ACTIVATE")){
			activationPhoneFlow.pressButton("have-access-code");	
			}*/
		
	//added
	activationPhoneFlow.pressButton("REGISTER AND ACTIVATE");
	
	if (status.equalsIgnoreCase("Active")) {
		activationPhoneFlow.pressButton("YES");
		TwistUtils.setDelay(5);
		activationPhoneFlow.pressButton("AGREE");
	} else {
		TwistUtils.setDelay(5);
		activationPhoneFlow.pressButton("NO[1]");
		//TwistUtils.setDelay(5);
	}
		if(islte.equals("Yes")){
			if(sim.equals("null")){ //LTE_NO SIM
			
				activationPhoneFlow.selectCheckBox("simcheckbox");
				activationPhoneFlow.pressSubmitButton("CONTINUE");
				//delay.
				TwistUtils.setDelay(5);
				 if(activationPhoneFlow.imageVisible("MICRO_SIM_CARD_CT.PNG")){
					 activationPhoneFlow.clickImage("MICRO_SIM_CARD_CT.PNG"); 
				 }
			
			}else {//LTE _SIM
				String Simstr= simUtil.getNewSimCardByPartNumber(sim);
				activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
				activationPhoneFlow.clickStrongMessage("CONTINUE");						
			}						
	    }
			
		}else if(KIT.equals("I HAVE KIT")){
			activationPhoneFlow.pressButton("have-access-code");
			if (status.equalsIgnoreCase("Active")) {
				activationPhoneFlow.pressButton("YES");
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("AGREE");
			} else {
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("NO");
				TwistUtils.setDelay(5);
			}
				if(islte.equals("Yes")){
					if(sim.equals("null")){ //LTE_NO SIM
						String NACstr= phoneUtil.getNewPinByPartNumber(NACcode);
						System.out.println("NAC Pin: " + NACstr);
						activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
						activationPhoneFlow.selectCheckBox("simcheckbox");
						activationPhoneFlow.pressSubmitButton("CONTINUE");
						TwistUtils.setDelay(5);
						 if(activationPhoneFlow.linkVisible("nanoSimCard")){
							 activationPhoneFlow.clickLink("nanoSimCard");
						 }
					}else {//LTE _SIM
						String Simstr= simUtil.getNewSimCardByPartNumber(sim);
						esnUtil.getCurrentESN().setSim(Simstr);
						activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
						String NACstr= phoneUtil.getNewPinByPartNumber(NACcode);
						System.out.println("NAC Pin: " + NACstr);
						activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
						activationPhoneFlow.pressSubmitButton("CONTINUE");
						TwistUtils.setDelay(5);
						activationPhoneFlow.pressSubmitButton("default_submit_btn4");
					}
				}
				else {//NON LTE
					//TFBYOPC
					String NACstr= phoneUtil.getNewPinByPartNumber("TFAPPBYOCR80");
					activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					activationPhoneFlow.pressSubmitButton("default_submit_btn4");
					
				}
											
		}else if(KIT.equals("TRANSFER REGISTRATION")){
			activationPhoneFlow.pressButton("default_submit_btn6");
			if (status.equalsIgnoreCase("Active")) {
				activationPhoneFlow.pressButton("YES");
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("AGREE");
			} else {
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("NO");
				TwistUtils.setDelay(5);
			}
				if(islte.equals("Yes")){
					if(sim.equals("null")){ //LTE_NO SIM
						ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
						activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
						activationPhoneFlow.selectCheckBox("simcheckbox");
						activationPhoneFlow.pressSubmitButton("CONTINUE");
						TwistUtils.setDelay(5);
						 if(activationPhoneFlow.linkVisible("nanoSimCard")){
							 activationPhoneFlow.clickLink("nanoSimCard");
						 }
						
					}else {//LTE _SIM
						String Simstr= simUtil.getNewSimCardByPartNumber(sim);
						activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
						ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
						activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
						activationPhoneFlow.pressSubmitButton("CONTINUE");
						TwistUtils.setDelay(5);
						activationPhoneFlow.pressSubmitButton("default_submit_btn4");
					}
				}
				else {//NON LTE
					//find the oldESN eligible for transfer registration
					ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
					activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					activationPhoneFlow.pressSubmitButton("default_submit_btn4");
					
				}
			
		}
	
	}

	public void finishActivation() throws Exception {
		/*TwistUtils.setDelay(5);
		activationPhoneFlow.clickImage("nothanks");*/
	}

	public void completeShippingDetailsForDomainIfLteAndAlreadyHaveSIM(String domain, String islte , String havesim) throws Exception {
		if (islte.equals("Yes")){
			if (havesim.equals("null")){
				TwistUtils.setDelay(5);
					Shippingdetails();
					
					activationPhoneFlow.navigateTo(props.getString("Twist.TASURL"));
					System.out.println(props.getString("Twist.TASURL"));
					if (activationPhoneFlow.textboxVisible("it1")) {
						activationPhoneFlow.typeInTextField("it1", "itquser");
						activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");
						
						if (activationPhoneFlow.buttonVisible("Login")){
							activationPhoneFlow.pressButton("Login");
						} else {
							activationPhoneFlow.pressSubmitButton("cb1");
						}
					}
					activationPhoneFlow.clickLink("Incoming Call");
					ESN esn=esnUtil.getCurrentESN();
					System.out.println(esn.getEsn());
					enterActiveEsnAndMin(esn.getEsn());
					if (activationPhoneFlow.buttonVisible("Continue to Service Profile")) {
						activationPhoneFlow.pressButton("Continue to Service Profile");
					}
					
					CompleteShipping(esn.getEsn(),domain);
					activationPhoneFlow.navigateTo(properties.getString("Twist.Net10URL"));
					
			}
		}
	}
		
		public void enterActiveEsnAndMin(String activeEsn) {
			String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
			System.out.println("ActiveESN: " + activeEsn + " ActiveMin: " + activeMin);
			activationPhoneFlow.typeInTextField("/r2:\\d:it1/", activeEsn);
			if (activationPhoneFlow.buttonVisible("Search Service")) {
				activationPhoneFlow.pressButton("Search Service");
			} else {
				activationPhoneFlow.pressSubmitButton("Search Service");
			}
		}
		
		public void CompleteShipping(String esn, String domain){
			activationPhoneFlow.clickLink("History");
            activationPhoneFlow.clickLink("Ticket History");
            activationPhoneFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot2/");
            if (activationPhoneFlow.buttonVisible("Yank")||activationPhoneFlow.submitButtonVisible("Yank")){
            	pressButton("Yank");
            }else{
	            pressButton("Accept");
	            pressButton("Accept[1]");
            }
            activationPhoneFlow.clickLink("Part Request");
            //Assert.assertTrue(activationPhoneFlow.spanVisible("PENDING")); 
            //Assertion as pending
            /*activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7::content/", "BP_IO");//r2:1:r1:2:soc7
            TwistUtils.setDelay(5);
            activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5::content/", "FEDEX");
            activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6::content/", "2nd DAY");*/
            activationPhoneFlow.pressSubmitButton("Save");
            pressButton("OK");
            String partNum = activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:xReplPartNumId::content/").value();
            activationPhoneFlow.pressSubmitButton("Ship");        
            if(domain.equalsIgnoreCase("SIM CARDS")){
                  String sim= simUtil.getNewSimCardByPartNumber(partNum);
                  activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32::content/", sim);
                  esnUtil.getCurrentESN().setSim(sim);
            }
            activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31::content/", "1234567890");
            pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb44/");//Ship[1]
            TwistUtils.setDelay(10); //10
            activationPhoneFlow.pressButton("Cancel");
		}
		
		public void Shippingdetails() throws Exception{
			String randomEmail = TwistUtils.createRandomEmail();
			activationPhoneFlow.typeInTextField("firstName", "TwistFirstName");
			activationPhoneFlow.typeInTextField("lastName", "TwistLastName"); //email
			activationPhoneFlow.getBrowser().byId("email").setValue(randomEmail);
			activationPhoneFlow.typeInTextField("confirmEmail", randomEmail);
			activationPhoneFlow.typeInTextField("address1", "1295 Charleston Road");
			activationPhoneFlow.typeInTextField("zip", "33178");
			activationPhoneFlow.typeInTextField("city", "Miami");
			activationPhoneFlow.chooseFromSelect("state", "FL");
			activationPhoneFlow.typeInTextField("phone", TwistUtils.generateRandomMin());
			activationPhoneFlow.clickStrongMessage("CONTINUE");
			TwistUtils.setDelay(5);
			Assert.assertTrue(activationPhoneFlow.strongVisible("Ticket Number:")); 
			TwistUtils.setDelay(20);
		}
		
		public void pressButton(String btnName){
			if(activationPhoneFlow.buttonVisible(btnName)){
				activationPhoneFlow.pressButton(btnName);
			}else if(activationPhoneFlow.submitButtonVisible(btnName)){
				activationPhoneFlow.pressSubmitButton(btnName);
			}
		}

		public void chooseTheCarrier(String option) throws Exception {
			activationPhoneFlow.clickLink("othersClick");
		}

		public void enterEsnSimAndCarrierForLteIphoneWithZip(String status,String simPart, String carrier, 
				String isLTE, String iPhone,String IsHD, String zipCode) throws Exception {
			String esnStr = phoneUtil.getNewByopCDMAEsn();
			ESN esn = new ESN("TFBYOPC", esnStr);
			esnUtil.setCurrentESN(esn);
			activationPhoneFlow.typeInTextField("zip", zipCode);
			activationPhoneFlow.typeInTextField("phone_meid", esnStr);
			//System.out.println(esnStr);
			
			//activationPhoneFlow.pressSubmitButton(properties.getString("continue"));//default_submit_btn
			//activationPhoneFlow.pressSubmitButton("default_submit_btn");//redirects to add airtime page error
			activationPhoneFlow.clickStrongMessage("CONTINUE");
			System.out.println(esnStr);
			
		
			int edited = 0;
			TwistUtils.setDelay(3);
			for (int i = 0; i < 8 && edited == 0; i++) {
				TwistUtils.setDelay(4+i);
				edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, status, isLTE, iPhone,IsHD);
				System.out.println("edit: " + edited);
			}

			TwistUtils.setDelay(5);
		}

		public void goToTAS() throws Exception {
			activationPhoneFlow.navigateTo(properties.getString("Twist.TASURL"));
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it1/", "itquser");
			activationPhoneFlow.typeInPasswordField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?it2/", "abcd1234!");
			activationPhoneFlow.pressSubmitButton("Login");
		}

		public void checkForSED() throws Exception {
			String byopEsn = esnUtil.getCurrentESN().getEsn();
			activationPhoneFlow.clickLink("Incoming Call");
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:it1/",byopEsn);
			pressButton("Search Service");
			TwistUtils.setDelay(3);
			String date = activationPhoneFlow.getBrowser().byId("r2:1:panelLabelAndMessage26").getValue();
			assertTrue(date.equalsIgnoreCase("Undefined"));
		}

}