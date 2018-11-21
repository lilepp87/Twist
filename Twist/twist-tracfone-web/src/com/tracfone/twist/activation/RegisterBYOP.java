package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

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
	
	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ESNUtil esnUtil;
	
	private Properties properties = new Properties();

	public RegisterBYOP() {

	}

	public void goToRegisterBYOPCDMAPage() throws Exception {
		String byopUrl = properties.getString("byopUrl");
		activationPhoneFlow.navigateTo(byopUrl);
	}
	
	public void goToRegisterBYOPPage() throws Exception {
		activationPhoneFlow.clickLink("ACTIVATE");
		if (activationPhoneFlow.submitButtonVisible("activate_byop")) {
			activationPhoneFlow.pressSubmitButton("activate_byop");
		} else {
			activationPhoneFlow.pressButton("activate_byop");
		}
		
	}
	
	public void enterNetworkAccessCode(String accessPin) throws Exception {
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);
		activationPhoneFlow.selectRadioButton("haveaccess");
		activationPhoneFlow.typeInTextField("my_access_code", pin);
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		esnUtil.getCurrentESN().setTransType("TF BYOP Register with Pin");
		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
	}

	public void finishRegistration() throws Exception {
		TwistUtils.setDelay(20);
		Assert.assertTrue(activationPhoneFlow.h4Visible(properties.getString("phoneRegisteredCongratulations")));
		activationPhoneFlow.pressSubmitButton(properties.getString("activateNow"));
		phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}

	public void chooseToPurchaseRegistrationCode() throws Exception {
		activationPhoneFlow.selectRadioButton("needaccess");
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		activationPhoneFlow.clickLink(properties.getString("clickHere"));
		
		esnUtil.getCurrentESN().setTransType("TF BYOP Register with Purchase");
		esnUtil.getCurrentESN().setBuyFlag(true);
	}

	public void chooseToUpgrade() throws Exception {
		activationPhoneFlow.selectRadioButton("upgrade");
		ESN oldEsn = esnUtil.popRecentActiveEsn("TFBYOPC");
		activationPhoneFlow.typeInTextField("old_meid", oldEsn.getEsn());
		activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
		
		esnUtil.getCurrentESN().setTransType("TF BYOP Register with Upgrade");
		esnUtil.getCurrentESN().setFromEsn(oldEsn);
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

	public void enterEsnSimAndCarrierForLteIphoneWithZip(String status,String simPart, String carrier, 
			String isLTE, String iPhone,String zipCode) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn = new ESN("TFBYOPC", esnStr);
		esnUtil.setCurrentESN(esn);
		if (activationPhoneFlow.textboxVisible("phone_meid")) {
			activationPhoneFlow.typeInTextField("zip", zipCode);
			activationPhoneFlow.typeInTextField("phone_meid", esnStr);
			//System.out.println(esnStr);
			
			//activationPhoneFlow.pressSubmitButton(properties.getString("continue"));//default_submit_btn
			//activationPhoneFlow.pressSubmitButton("default_submit_btn");//redirects to add airtime page error
			activationPhoneFlow.clickStrongMessage("CONTINUE");
			} else {
				activationPhoneFlow.typeInTextField("esnNew", esnStr);
				activationPhoneFlow.pressButton("CONTINUE");			
				activationPhoneFlow.typeInTextField("zipcode",zipCode);
				activationPhoneFlow.clickLink("CONTINUE");
			}
			
			System.out.println(esnStr);
		
	
		int edited = 0;
		TwistUtils.setDelay(3);
		for (int i = 0; i < 15 && edited == 0; i++) {
			TwistUtils.setDelay(4+i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, status, isLTE, iPhone);
			System.out.println("edit: " + edited);
		}

		TwistUtils.setDelay(5);
	/*	if(isLTE.equals("Yes") ){
		if(!simPart.equals("null")){
			String vzSimStr = simUtil.getNewSimCardByPartNumber(simPart);
			activationPhoneFlow.typeInTextField("lte_sim_number", vzSimStr);
			activationPhoneFlow.pressSubmitButton("Continue");
		} else {
			activationPhoneFlow.selectCheckBox("vrz_sim_option");
			if(iPhone.equalsIgnoreCase("Yes")){
			activationPhoneFlow.selectRadioButton("NANO");
			}else {
			activationPhoneFlow.selectRadioButton("MICRO");
			}
		}
		} */
		
		/*if (status.equalsIgnoreCase("Active")) {
			activationPhoneFlow.chooseFromSelect("phone_active", "Yes");
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			TwistUtils.setDelay(5);
			
		} else {
			activationPhoneFlow.chooseFromSelect("phone_active", "No");
			activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
			TwistUtils.setDelay(5);
		}
		if(activationPhoneFlow.checkboxVisible("accept_rules")){
			activationPhoneFlow.selectCheckBox("accept_rules");
		}*/
		//activationPhoneFlow.pressSubmitButton(properties.getString("continue"));
	}
	
	public void chooseTheCarrier(String string1) throws Exception {
		//othersClick
		activationPhoneFlow.clickLink("Verizon Compatible Phone");
	}
	
	public void chooseForESNStatusIfLteAndSimAndNAC(String kit, String status, String isLte, String sim, String NACcode) throws Exception {
		TwistUtils.setDelay(25);
		Assert.assertTrue(activationPhoneFlow.strongVisible("CONGRATULATIONS! YOUR PHONE IS COMPATIBLE"));

		if (kit.equalsIgnoreCase("BUY A KIT") || kit.equalsIgnoreCase("REGISTER AND ACTIVATE")) {
			if (kit.equals("BUY A KIT")) {
				activationPhoneFlow.pressButton("default_submit_btn3");
			} else if (kit.equals("REGISTER AND ACTIVATE")) {
				activationPhoneFlow.pressButton("have-access-code");
			}

			if (status.equalsIgnoreCase("Active")) {
				activationPhoneFlow.pressButton("YES");
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("AGREE");
			} else {
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressButton("NO");
				TwistUtils.setDelay(5);
			}
			if (isLte.equalsIgnoreCase("Yes")) {
				if (sim.equalsIgnoreCase("null")) { // LTE_NO SIM
					activationPhoneFlow.selectCheckBox("simcheckbox");
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					// delay.
					TwistUtils.setDelay(5);
					if (activationPhoneFlow.divVisible("Select[1]")) {
						activationPhoneFlow.clickDivMessage("Select[1]");
					}
				} else {// LTE _SIM
					String Simstr = simUtil.getNewSimCardByPartNumber(sim);
					activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
					activationPhoneFlow.pressSubmitButton("CONTINUE");
				}
			}

		} else if (kit.equals("I HAVE KIT")) {
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
			if (isLte.equals("Yes")) {
				if (sim.equals("null")) { // LTE_NO SIM
					String NACstr = phoneUtil.getNewPinByPartNumber(NACcode);
					System.out.println("NAC Pin: " + NACstr);
					activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
					activationPhoneFlow.selectCheckBox("simcheckbox");
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					if (activationPhoneFlow.linkVisible("nanoSimCard")) {
						activationPhoneFlow.clickLink("nanoSimCard");
					}
				} else {// LTE _SIM
					String Simstr = simUtil.getNewSimCardByPartNumber(sim);
					esnUtil.getCurrentESN().setSim(Simstr);
					activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
					String NACstr = phoneUtil.getNewPinByPartNumber(NACcode);
					System.out.println("NAC Pin: " + NACstr);
					activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					activationPhoneFlow.pressSubmitButton("default_submit_btn4");
				}
			} else {// NON LTE
					// TFBYOPC
				String NACstr = phoneUtil.getNewPinByPartNumber("TFAPPBYOCR80");
				activationPhoneFlow.typeInTextField("network_access_code_input", NACstr);
				activationPhoneFlow.pressSubmitButton("CONTINUE");
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressSubmitButton("default_submit_btn4");

			}

		} else if (kit.equals("TRANSFER REGISTRATION")) {
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
			if (isLte.equals("Yes")) {
				if (sim.equals("null")) { // LTE_NO SIM
					ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
					activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
					activationPhoneFlow.selectCheckBox("simcheckbox");
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					if (activationPhoneFlow.linkVisible("nanoSimCard")) {
						activationPhoneFlow.clickLink("nanoSimCard");
					}

				} else {// LTE _SIM
					String Simstr = simUtil.getNewSimCardByPartNumber(sim);
					activationPhoneFlow.typeInTextField("lte_capable_input", Simstr);
					ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
					activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
					activationPhoneFlow.pressSubmitButton("CONTINUE");
					TwistUtils.setDelay(5);
					activationPhoneFlow.pressSubmitButton("default_submit_btn4");
				}
			} else {// NON LTE
					// find the oldESN eligible for transfer registration
				ESN Esn = esnUtil.popRecentActiveEsn("TFBYOPC");
				activationPhoneFlow.typeInTextField("old_phone_serial_number_input", Esn.getEsn());
				activationPhoneFlow.pressSubmitButton("CONTINUE");
				TwistUtils.setDelay(5);
				activationPhoneFlow.pressSubmitButton("default_submit_btn4");
			}
		}
	}
}