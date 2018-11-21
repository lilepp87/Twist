package com.tracfone.twist.activation;

import com.tracfone.twist.flows.tracfone.ActivationReactivationFlow;
import com.tracfone.twist.flows.tracfone.TracfoneFlows;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.SimUtil;

public class PhoneUpgrade {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private ActivationReactivationFlow activationPhoneFlow;

	public PhoneUpgrade() {

	}

	public void goToActivatePhone() throws Exception {
		activationPhoneFlow.clickLink(ActivationReactivationFlow.ActivationNet10SWebFields.ActivateLink.name);
	}

	public void selectTransferMyNumberAndServiceFromOneNet10ToAnother() throws Exception {
		activationPhoneFlow.selectRadioButton("optionsRadios[2]").clickLink("CONTINUAR[2]");
	}

	public void selectTheNewPhoneImage(String brand, String phoneModel) throws Exception {
		activationPhoneFlow.clickLink(brand);
		activationPhoneFlow.clickImage(phoneModel);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);
	}

	public void selectTheCurrentPhoneImage() throws Exception {
		activationPhoneFlow.clickLink(ActivationReactivationFlow.ActivationNet10SWebFields.KyoceraLink.name);
		activationPhoneFlow.clickImage(ActivationReactivationFlow.ActivationNet10SWebFields.KyoceraImage.name);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);
	}

	public void enterNewESNAndNickname(String partNumber) throws Exception {
		String newEsn = phoneUtil.getNewEsnByPartNumber(partNumber);

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.NewEsnText.name, newEsn);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.NewNickNameText.name,
				"Twist Nickname");
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);

	}

	public void enterSimNumber(String simCard) throws Exception {
		if (!simCard.isEmpty()) {
			String newSim = simUtil.getNewSimCardByPartNumber(simCard);
			System.out.println("SimCard: " + newSim);
			System.out.println("IN");
			activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.NewSimText.name, newSim);
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);
		}
	}

	public void enterCurrentESNAndPhoneNumber(String partNumber) throws Exception {
		String currentEsn = phoneUtil.getActiveEsnByPartNumber(partNumber);
		String currentMin = phoneUtil.getMinOfActiveEsn(currentEsn);
		System.out.println("currentESN: " + currentEsn + " CurrentMin: " + currentMin);

		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.CurrentEsnText.name, currentEsn);
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.CurrentMinText.name, currentMin);
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);
	}

	public void enterCodeNumberAndConfirmCodeNumber() throws Exception {
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.TimeTankText.name, "12345");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.ConfirmTimeTankText.name, "12345");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.TTestText.name, "12345");
		activationPhoneFlow.typeInTextField(ActivationReactivationFlow.ActivationNet10SWebFields.ConfirmTTestText.name, "12345");
		activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.SubmitButton.name);
	}

	public void completeTheProcess(String technology) throws Exception {
		if ("GSM".equals(technology)) {
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.ContinueButton.name);

			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.ContinueButton.name);
			activationPhoneFlow.clickHeadingMessage(ActivationReactivationFlow.ActivationNet10SWebFields.ActivationMessage.name);
			activationPhoneFlow.pressButton(ActivationReactivationFlow.ActivationNet10SWebFields.ContinueButton.name);
			activationPhoneFlow.clickImage(ActivationReactivationFlow.ActivationNet10SWebFields.ContinueButton.name);
			activationPhoneFlow.clickImage(ActivationReactivationFlow.ActivationNet10SWebFields.NoThanksButton.name);
		} else {
			activationPhoneFlow.pressSubmitButton(ActivationReactivationFlow.ActivationNet10SWebFields.ContinueButton.name);
		}
	}

	public void setTracfoneFlows(TracfoneFlows tracfoneFlows) {
		activationPhoneFlow = tracfoneFlows.activationPhoneFlow();
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

}