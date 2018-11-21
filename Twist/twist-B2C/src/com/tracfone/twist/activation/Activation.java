package com.tracfone.twist.activation;

import com.tracfone.twist.activation.ActivatePhone;

// JUnit Assert framework can be used for verification


public class Activation {
	
	private ActivatePhone activateSTPhone;

	public Activation() {
		
	}

//	public void activatePhoneWithZipSimPin(String status, String part, String zip, String sim, String pin) throws Exception {
//		activateSTPhone.activateSTPhoneWithZipSimPin(status, part, zip, sim, pin);
//	}
	
	public void setActivateSTPhone(ActivatePhone activateSTPhone) {
		this.activateSTPhone = activateSTPhone;
	}


}
