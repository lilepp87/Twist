package com.tracfone.twist.impl.eng.activation;

import com.tracfone.twist.activation.AbstractStartActivation;

// JUnit Assert framework can be used for verification

public class StartActivation extends AbstractStartActivation {

	public StartActivation() {
		super("Net10");
	}

	@Override
	public void enterEsnForModelWithStatusAndZipCode(String partNumber, String status,
			String zipCode) throws Exception {
		super.enterEsnForModelWithStatusAndZipCode(partNumber, status, zipCode);
	}

	@Override
	public void goToActivatePhone() throws Exception {
		super.goToActivatePhone();
	}

	@Override
	public void possiblyJoinSIMForCellTechAndStatus(String partNumber, String cellTech,
			String status) throws Exception {
		super.possiblyJoinSIMForCellTechAndStatus(partNumber, cellTech, status);
	}

	@Override
	public void selectActivateMyNet10Phone() throws Exception {
		super.selectActivateMyNet10Phone();
	}
	@Override
	public void selectActivateMyNet10PhoneForByop() throws Exception {
		super.selectActivateMyNet10PhoneForByop();
	}

	@Override
	public void activateFromPhoneWithPartTypeSimTechPinAndCarrier(String part, String phoneType, String sim, String tech,
			String pinType, String pin, String carrier) throws Exception {
		super.activateFromPhoneWithPartTypeSimTechPinAndCarrier(part, phoneType, sim, tech, pinType, pin, carrier);
	}
	
	@Override
	public void activateLeasedFromPhoneWithPartTypeSimTechPinAndCarrier(String part, String phoneType, String sim, String tech,
			String pinType, String pin, String carrier) throws Exception {
		super.activateLeasedFromPhoneWithPartTypeSimTechPinAndCarrier(part, phoneType, sim, tech, pinType, pin, carrier);
	}
	
	@Override
	public void activateToPhoneWithPartStatusTypeSimTechPinAndCarrier(String part, String status, String phoneType, String sim, String tech,
			String pinType, String pin, String carrier) throws Exception {
		super.activateToPhoneWithPartStatusTypeSimTechPinAndCarrier(part, status, phoneType, sim, tech, pinType, pin, carrier);
	}
	
	@Override
	public void makePastDueDependingOnStatusForModelAndSim(String status, String model, String sim) throws Exception {
		super.makePastDueDependingOnStatusForModelAndSim(status, model, sim);
	}
	
	@Override
	public void deactivateToEsn() throws Exception {
		super.deactivateToEsn();
	}
	
	@Override
	public void deactivateWithReason(String status) throws Exception {
		super.deactivateWithReason(status);
	}
	
	@Override
	public void enterSimForModelWithStatusAndZipCode(String partNumber,String status, String zipCode) throws Exception {
		super.enterSimForModelWithStatusAndZipCode(partNumber, status, zipCode);
	}

	@Override
	public void setTheStatusToForEsnAndSim(String status, String esnPart,String simPart) throws Exception {
		super.setTheStatusToForEsnAndSim(status, esnPart, simPart);
	}
	
	@Override
	public void redeemLeasedEsnWith(String pinPart) throws Exception {
		super.redeemLeasedEsnWith(pinPart);
	}

	@Override
	public void getTheUpdatedESNAttributesForLeasedEsn() throws Exception {
		super.getTheUpdatedESNAttributesForLeasedEsn();
	}
	
	@Override
	public void setAccountEsnForEsn(String status) throws Exception {
		super.setAccountEsnForEsn(status);
	}

	@Override
	public void getAccountEsn(){
		super.getAccountEsn();
	}

	@Override
	public void selectActivateMyNet10SIM() throws Exception {
	   super.selectActivateMyNet10SIM();
	}

	@Override
	public void goToReactivatePhone() throws Exception {
		super.goToReactivatePhone();
	}

	@Override
	public void selectNewNumberForBYOPNAC() throws Exception {
		super.selectNewNumberForBYOPNAC();
	}

	@Override
	public void wIFIRegistration() throws Exception {
		super.wIFIRegistration();
	}

}