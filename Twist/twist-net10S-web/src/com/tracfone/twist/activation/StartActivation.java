package com.tracfone.twist.activation;

// JUnit Assert framework can be used for verification

public class StartActivation extends AbstractStartActivation {

	public StartActivation() {
		super("Net10S");
	}

	@Override
	public void enterEsnForModelWithStatusAndZipCode(String partNumber, String status,
			Integer zipCode) throws Exception {
		super.enterEsnForModelWithStatusAndZipCode(partNumber, status, zipCode);
	}

	@Override
	public void makePastDueDependingOnStatusAndModel(String status, String model)
			throws Exception {
		super.makePastDueDependingOnStatusAndModel(status, model);
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

}