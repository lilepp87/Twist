package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

import java.util.ResourceBundle;

import junit.framework.Assert;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RegisterBYOP {

	private ActivationReactivationFlow activationPhoneFlow;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private final String HEX_ESN = "Hex";
	private ResourceBundle rb1 = ResourceBundle.getBundle("straighttalk");

	public RegisterBYOP() {
		
	}
		
	public void enterEsnWithZipLteCarrierAndIPhone5IsHd(String isActive, String esnType, String zip, 
			String isLTE, String carrier, String iPhone5, String isHd) throws Exception {
		String esnStr = phoneUtil.getNewByopCDMAEsn();
		ESN esn;
		if ("Sprint".equalsIgnoreCase(carrier)) {
			activationPhoneFlow.clickLink("PhoneInputBtn");
		}
		if (HEX_ESN.equalsIgnoreCase(esnType)) {
			esn = new ESN("STBYOPHEX", esnStr);
			String hexEsn = phoneUtil.convertMeidDecToHex(esn);
			activationPhoneFlow.typeInTextField("byop_esn_number", hexEsn.toLowerCase());
		} else {
			esn = new ESN("STBYOPDEC", esnStr);
			activationPhoneFlow.typeInTextField("byop_esn_number", esnStr);
		}
		activationPhoneFlow.selectCheckBox("byop-ein-meid-number-check");
		activationPhoneFlow.pressSubmitButton("CONTINUE[1]");
		esnUtil.setCurrentESN(esn);

		int edited = 0;
		for (int i = 0; i < 8 && edited == 0; i++) {
			TwistUtils.setDelay(4 + i);
			edited = phoneUtil.finishCdmaByopIgate(esnStr, carrier, isActive, isLTE, iPhone5, isHd);
			System.out.println("edit: " + edited);
			if (edited==1 && activationPhoneFlow.divVisible("error")){
				activationPhoneFlow.pressSubmitButton("byop-ein-meid-submit");
			}
		}
		//activationPhoneFlow.selectCheckBox("byop-ein-meid-number-activate");
		activationPhoneFlow.clickLink("ACTIVATE[1]");
	}

	public void enterCdmaLteIphoneSimIsTrio(String isActive, String carrier, String isLte, String isIphone, String hasSim, String isTrio) throws Exception {
	if ("Yes".equalsIgnoreCase(isLte) && "Verizon".equalsIgnoreCase(carrier)) {
			String sim=null;
			if ("Yes".equalsIgnoreCase(hasSim)) {
				if ("Yes".equalsIgnoreCase(isIphone)) {
					if("Yes".equalsIgnoreCase(isTrio))
						sim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
					else
						sim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
				} else {
					if("Yes".equalsIgnoreCase(isTrio))
						sim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9TD");
					else
						sim = simUtil.getNewSimCardByPartNumber("TF256PSIMV9DD");
				}
				System.out.println("SIM# "+sim);
				ESN esn = esnUtil.getCurrentESN();
				esn.setSim(sim);

				activationPhoneFlow.typeInTextField("byop_sim_number[4]", esn.getSim());
				//activationPhoneFlow.selectCheckBox("byop-vrz-sim-number-check");
				activationPhoneFlow.pressSubmitButton("byop-vrz-sim-submit");
				//TwistUtils.setDelay(20);
			} else {
				//Customer need a SIM to be shipped
				activationPhoneFlow.typeInTextField("byop_zip_code[3]", "33178");
				activationPhoneFlow.pressSubmitButton("FIND THE RIGHT SIM[3]");
			}
		}
		TwistUtils.setDelay(35);
		
		if (activationPhoneFlow.linkVisible("PhoneInputBtn")) {
			// I Have a Phone
			activationPhoneFlow.clickLink("PhoneInputBtn");
			TwistUtils.setDelay(10);
		}
		phoneUtil.addSimToEsn(esnUtil.getCurrentESN().getSim(), esnUtil.getCurrentESN());//to avoid entering SIM (it asks to enter SIM if isHD=YES)
		TwistUtils.setDelay(5);
		if ("Active".equalsIgnoreCase(isActive) && "Verizon".equalsIgnoreCase(carrier)) {
			activationPhoneFlow.clickLink("byop-already-active-yes");
//			activationPhoneFlow.clickLink("AGREE");
		} else if ("Verizon".equalsIgnoreCase(carrier)) {
			activationPhoneFlow.clickLink("byop-already-active-no");
		} else if ("Active".equalsIgnoreCase(isActive) && "Sprint".equalsIgnoreCase(carrier)) {
			TwistUtils.setDelay(10);
			activationPhoneFlow.clickLink("AGREE");
		}
		TwistUtils.setDelay(55);
		if (activationPhoneFlow.linkVisible("SELECT[13]")) {
			// I Have a Phone For some reason if you don't have a sim it asks 
			// after active question instead of before
			activationPhoneFlow.clickLink("SELECT[13]");
			TwistUtils.setDelay(10);
		}
		if (activationPhoneFlow.linkVisible("SELECT[11]")) {
			// Choose SIM Size if it is not a iPhone
			activationPhoneFlow.clickLink("SELECT[11]");
		}
		if ("Yes".equalsIgnoreCase(isLte) && "Verizon".equalsIgnoreCase(carrier) && "No".equalsIgnoreCase(hasSim)) {
			completeShippingDetailsForShippedSim();		
		}
	}
	
	public void chooseSimByDeviceWithZip(String device, String Zip) throws Exception{
		activationPhoneFlow.typeInTextField("byop_zip_code[3]", "33178");
		activationPhoneFlow.pressSubmitButton("FIND THE RIGHT SIM[3]");
		TwistUtils.setDelay(10);
		activationPhoneFlow.clickLink("byop-already-active-no");
		TwistUtils.setDelay(3);
		
		if (device.equalsIgnoreCase("Phone")) {
			activationPhoneFlow.linkVisible("PhoneInputBtn");
			activationPhoneFlow.clickLink("PhoneInputBtn");
			TwistUtils.setDelay(5);
			activationPhoneFlow.getBrowser().byId("WC_CatalogEntryDBThumbnailDisplayJSPF_111008_link_9b_buy").click();
		} else if(device.equalsIgnoreCase("Tablet")){
			//activationPhoneFlow.linkVisible("ZipInputBtn");
			activationPhoneFlow.clickLink("ZipInputBtn");
			TwistUtils.setDelay(5);
			activationPhoneFlow.getBrowser().byId("WC_CatalogEntryDBThumbnailDisplayJSPF_71006_link_9b_buy").click();
		}
		
	}
	
	
	public void enterEsnBrandModelStatusZip(String esn, String brand, String model, String status, String zip)
			throws Exception {
		activationPhoneFlow.typeInTextField("phone_meid", esn);
		activationPhoneFlow.chooseFromSelect("phone_brand", brand);
		activationPhoneFlow.chooseFromSelect("phone_model", model);
		activationPhoneFlow.chooseFromSelect("phone_active_verizon", status);
		activationPhoneFlow.typeInTextField("phone_zip", zip);
		activationPhoneFlow.pressButton("Continue");
		TwistUtils.setDelay(3);
		Assert.assertTrue(activationPhoneFlow.divVisible("NOT ELIGIBLE"));
	}

	public void enterNetworkAccessCode(String accessPin) throws Exception {
		activationPhoneFlow.selectRadioButton("registration_code_option");
		String pin = phoneUtil.getNewPinByPartNumber(accessPin);
		activationPhoneFlow.typeInTextField("registration_code", pin);
		TwistUtils.setDelay(7);
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setBuyFlag(false);
		esnUtil.getCurrentESN().setPin(pin);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Pin");
	}

	public void chooseToUpgrade() throws Exception {
		TwistUtils.setDelay(7);
		activationPhoneFlow.selectRadioButton("registration_code_option[2]");
		ESN oldEsn;
		if (esnUtil.peekRecentActiveEsn("STBYOPHEX")) {
			oldEsn = esnUtil.popRecentActiveEsn("STBYOPHEX");
		} else {
			oldEsn = esnUtil.popRecentActiveEsn("STBYOPDEC");
		}
		activationPhoneFlow.typeInTextField("old_phone_meid", oldEsn.getEsn());
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setFromEsn(oldEsn);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Upgrade");
	}

	public void finishRegistration() throws Exception {
		TwistUtils.setDelay(9);
		Assert.assertTrue(activationPhoneFlow.divVisible("Congratulations! Your phone has been registered in"
						+ " our system using your Network Access Code. You can now Activate your phone with Straight Talk Service. ACTIVATE NOW"));
		activationPhoneFlow.pressSubmitButton("ACTIVATE NOW");
		phoneUtil.checkBYOPRegistration(esnUtil.getCurrentESN());
	}

	public void chooseToPurchaseRegistrationCode() throws Exception {
		activationPhoneFlow.selectRadioButton("registration_code_option[1]");
		TwistUtils.setDelay(6);
		activationPhoneFlow.pressButton("continue_btn_section");

		esnUtil.getCurrentESN().setBuyFlag(true);
		esnUtil.getCurrentESN().setTransType("ST BYOP Register with Purchase");
	}

	public void checkForFieldEnforcement() throws Exception {
		activationPhoneFlow.pressButton("continue_btn_section");
		Assert.assertTrue(activationPhoneFlow.divVisible("All the fields on this page are required. Please make"
						+ " sure you have entered all the required information before continuing."));
	}

	public void enterNetworkAccessCodeDeselectAgreementAndCheckForError(
			String accessPin) throws Exception {
		activationPhoneFlow.selectCheckBox("agreement");
		enterNetworkAccessCode(accessPin);
		Assert.assertTrue(activationPhoneFlow.divVisible("You need to select the check box agreeing to transfer your"
						+ " current number in order to continue with the process."));
	}

	public void selectCarrier() throws Exception {
		activationPhoneFlow.clickLink("Other Carrier / Don't know");
	
	}

	public void enterEsnWithZip(String esn, String zip) throws Exception {
		activationPhoneFlow.typeInTextField("others_zipcode", zip);
		activationPhoneFlow.typeInTextField("others_esn", "145698569865732569");
		activationPhoneFlow.pressButton("CONTINUE[3]");
	
	}
//
	public void chooseTheCarrier(String carrier) throws Exception {
		activationPhoneFlow.clickLink("SELECT[1]");
		activationPhoneFlow.clickLink("SELECT[2]");
		if (carrier.equals("Verizon")) {
			activationPhoneFlow.clickLink("SELECT[7]");
		} else if (carrier.equals("Sprint")) {
			activationPhoneFlow.clickLink("SELECT[8]");
		}
	}
//TODO:Split up
	public void completeTicketCreationIfLteAndAlreadyHaveSIM(String islte, String havesim) throws Exception {
		if (islte.equals("Yes")) {
			Assert.assertTrue(activationPhoneFlow.divVisible("Is the phone you are trying to bring to Straight Talk currently active?"));
			TwistUtils.setDelay(15);
			if (activationPhoneFlow.buttonVisible("active_no")) {
				activationPhoneFlow.pressButton("active_no");
				TwistUtils.setDelay(15);
				if (islte.equals("Yes")) {
					// check box to create ticket for SIM
					if (havesim.equals("Yes")) {
						String Simstr = simUtil.getNewSimCardByPartNumber("TF256PSIMV9ND");
						activationPhoneFlow.typeInTextField("sim_last4_redeem_lte", Simstr);
						activationPhoneFlow.pressButton("redeem_lte_continue");
						if (activationPhoneFlow.linkVisible("ns_Z7_D0JM3QAH23E8A0I7V3NMMG1042__register_activate_phone")) {
							activationPhoneFlow.clickLink("ns_Z7_D0JM3QAH23E8A0I7V3NMMG1042__register_activate_phone");
						}
					} else {
						// Assert.assertTrue(activationPhoneFlow.labelVisible("Your Phone is LTE compatible. Please enter the CDMA LTE SIM Card number that came in your kit."));
						activationPhoneFlow.selectCheckBox("redeem_lte_check");

						// redeem_lte_continue
						if (activationPhoneFlow.buttonVisible("redeem_lte_continue")) {// correct
							activationPhoneFlow.pressButton("redeem_lte_continue");
						} else if (activationPhoneFlow.submitButtonVisible("redeem_lte_continue")) {
							activationPhoneFlow.pressSubmitButton("redeem_lte_continue");
						}
						String randomEmail = TwistUtils.createRandomEmail();
						activationPhoneFlow.typeInTextField("input_first_name", "TwistFirstName");
						activationPhoneFlow.typeInTextField("input_last_name", "TwistLastName");
						activationPhoneFlow.getBrowser().emailbox("input_email").setValue(randomEmail);
						activationPhoneFlow.getBrowser().emailbox("input_confirm_email").setValue(randomEmail);
						activationPhoneFlow.typeInTextField("input_address1", "1295 Charleston Road");
						activationPhoneFlow.typeInTextField("input_zip_code", "94043");
						activationPhoneFlow.typeInTextField("input_city", "Mountain View");
						activationPhoneFlow.chooseFromSelect("input_state", "CA");
						activationPhoneFlow.typeInTextField("input_phone", TwistUtils.generateRandomMin());

						if (activationPhoneFlow.buttonVisible("CONTINUE")) {
							activationPhoneFlow.pressButton("CONTINUE");
						} else if (activationPhoneFlow.submitButtonVisible("CONTINUE")) {
							activationPhoneFlow.pressSubmitButton("CONTINUE");
						} else if (activationPhoneFlow.buttonVisible("default_submit_btn")) {
							activationPhoneFlow.pressButton("default_submit_btn");
						} else if (activationPhoneFlow.submitButtonVisible("default_submit_btn")) {
							activationPhoneFlow.pressSubmitButton("default_submit_btn");
						}

						TwistUtils.setDelay(20);
						// Confirmation message for ticket creation
						Assert.assertTrue(activationPhoneFlow.divVisible("Ticket Number:"));
						Assert.assertTrue(activationPhoneFlow.divVisible("col-md-9"));
					}
				} else if (islte.equals("No")) {
					if (activationPhoneFlow.linkVisible("ns_Z7_D0JM3QAH23E8A0I7V3NMMG1042__register_activate_phone")) {
						activationPhoneFlow.clickLink("ns_Z7_D0JM3QAH23E8A0I7V3NMMG1042__register_activate_phone");
					}
				}
			}
		}
	}

/*	public void confirmCompatiblyFor(String string1) throws Exception {
		TwistUtils.setDelay(15);
		// Congratulation page
		Assert.assertTrue(activationPhoneFlow.h2Visible("CONGRATULATIONS! YOUR PHONE IS COMPATIBLE"));
		// Click on START FREE 5 DAY TRIAL
		activationPhoneFlow.clickLink("START FREE 5 DAY TRIAL");
		TwistUtils.setDelay(10);
	}*/

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setEsnUtil(ESNUtil esn) {
		esnUtil = esn;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void completeShippingDetailsForShippedSim() throws Exception {
		TwistUtils.setDelay(5);
		shippingDetails();
		activationPhoneFlow.navigateTo(rb1.getString("TAS_URL"));
		if (activationPhoneFlow.textboxVisible("it1") && activationPhoneFlow.passwordVisible("it2")) {
			activationPhoneFlow.typeInTextField("it1", "Itquser");
			activationPhoneFlow.typeInPasswordField("it2", "abcd1234!");

			if (activationPhoneFlow.buttonVisible("Login")) {
				activationPhoneFlow.pressButton("Login");
			} else {
				activationPhoneFlow.pressSubmitButton("cb1");
			}
		}
		activationPhoneFlow.clickLink("Incoming Call");
		ESN esn = esnUtil.getCurrentESN();
		System.out.println(esn.getEsn());
		enterActiveEsnAndMin(esn.getEsn());
		TwistUtils.setDelay(8);
		if (activationPhoneFlow.buttonVisible("Continue to Service Profile")) {
			activationPhoneFlow.pressButton("Continue to Service Profile");
		}

		completeShipping(esn.getEsn(), "SIM CARDS");
		activationPhoneFlow.navigateTo(rb1.getString("ST_URL"));
		activationPhoneFlow.clickLink("ACTIVATE");
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("ACTIVATE");
		TwistUtils.setDelay(5);
		
		activationPhoneFlow.typeInTextField("input_esn", esn.getEsn());
		activationPhoneFlow.selectCheckBox("activation-check");
		activationPhoneFlow.pressButton("CONTINUE");
		
		if(activationPhoneFlow.textboxVisible("input_sim")){
			activationPhoneFlow.typeInTextField("input_sim", esn.getSim());
			activationPhoneFlow.selectCheckBox("activation-check-sim");
			activationPhoneFlow.pressButton("CONTINUE");
		}
	}
	
	public void completeShipping(String esn, String domain) {
		activationPhoneFlow.clickLink("History");
		activationPhoneFlow.clickLink("Ticket History");
		activationPhoneFlow.clickLink("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:t\\d:\\d:ot2/");
		if (activationPhoneFlow.buttonVisible("Yank") || activationPhoneFlow.submitButtonVisible("Yank")) {
			pressButton("Yank");
		} else {
			pressButton("Accept");
			pressButton("Accept[1]");
		}
		activationPhoneFlow.clickLink("Part Request");
		//Assert.assertTrue(activationPhoneFlow.spanVisible("PENDING"));
		/*activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc7::content/", "BP_IO");//r2:1:r1:2:soc7
		TwistUtils.setDelay(5);
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc5::content/", "FEDEX");
		activationPhoneFlow.chooseFromSelect("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:soc6::content/", "2nd DAY");*/
		activationPhoneFlow.pressSubmitButton("Save");
		pressButton("OK");
		String partNum = activationPhoneFlow.getBrowser().textbox("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:xReplPartNumId::content/").value();
		System.out.println("simPartNum : "+partNum);
		activationPhoneFlow.pressSubmitButton("Ship");		
		if (domain.equalsIgnoreCase("SIM CARDS")) {
			String sim= simUtil.getNewSimCardByPartNumber(partNum);
			System.out.println("SIM : "+sim);
			activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it32::content/", sim);
			esnUtil.getCurrentESN().setSim(sim);
		}
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:it31::content/", "1234567890");
		pressButton("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:r\\d:\\d:cb44/");//Ship[1]
		TwistUtils.setDelay(10);
		pressButton("Cancel");
	}
	
	public void enterActiveEsnAndMin(String activeEsn) {
		String activeMin = phoneUtil.getMinOfActiveEsn(activeEsn);
		System.out.println("ActiveESN: " + activeEsn + " ActiveMin: " + activeMin);
		activationPhoneFlow.typeInTextField("/((pt\\d:) | (pt\\d:r\\d:\\d:))?r\\d:\\d:s12:it1/", activeEsn);///r2:\\d:it1/
		if (activationPhoneFlow.buttonVisible("Search Service")) {
			activationPhoneFlow.pressButton("Search Service");
		} else {
			activationPhoneFlow.pressSubmitButton("Search Service");
		}
	}
	
	public void shippingDetails() throws Exception{
		String randomEmail = TwistUtils.createRandomEmail();
		activationPhoneFlow.typeInTextField("fName", "TwistFirstName");
		activationPhoneFlow.typeInTextField("lName", "TwistLastName");
		activationPhoneFlow.getBrowser().byId("email").setValue(randomEmail);
		activationPhoneFlow.typeInTextField("emailConfirm", randomEmail);
		activationPhoneFlow.typeInTextField("addressOne", "1295 Charleston Road");
		activationPhoneFlow.typeInTextField("zip", "94043");
		activationPhoneFlow.typeInTextField("city", "Mountain View");
		activationPhoneFlow.chooseFromSelect("state", "CA");
		activationPhoneFlow.typeInTextField("phone", TwistUtils.generateRandomMin());
		activationPhoneFlow.pressSubmitButton("SUBMIT");
		TwistUtils.setDelay(5);
		Assert.assertTrue(activationPhoneFlow.getBrowser().div("content padded").text().contains("Ticket Number:")); 
		TwistUtils.setDelay(5);
		activationPhoneFlow.clickLink("FINISH");
	}
	
	private void pressButton(String buttonType) {
		if (activationPhoneFlow.submitButtonVisible(buttonType)) {
			activationPhoneFlow.pressSubmitButton(buttonType);
		} else {
			activationPhoneFlow.pressButton(buttonType);
		}
	}
	public void changeDealer(String Dealer) throws Exception {
		phoneUtil.ChangeDealer(Dealer,esnUtil.getCurrentESN().getEsn());
	}


}