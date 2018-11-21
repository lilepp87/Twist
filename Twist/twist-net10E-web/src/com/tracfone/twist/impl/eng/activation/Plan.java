package com.tracfone.twist.impl.eng.activation;

import com.tracfone.twist.util.Properties;

public enum Plan {
	// Monthly
	Normal750Monthly(false, true),
	NormalUnlimited(false, true),
	NormalUnlimitedInt(false, true),
	// Pay as you go
	Normal200(false, true),
	Normal300(false, true),
	Normal600(false, true),
	Normal900(false, true),
	Normal1500(false, true),
	
	// Non Data
	NonData750Monthly(false, true),
	NonData200(false, true),
	NonData300(false, true),
	NonData600(false, true),
	NonData900(false, true),
	NonData1500(false, true),
	NonData30Easy(false, true),
	NonData15Easy(false, true),

	// Hypothetical normal plans that could be used be for Android
	Android750Monthly(true, true),

	Android200(true, false),
	Android300(true, false),
	Android600(true, false),
	Android900(true, false),
	Android1500(true, false),

	// Megacard
	AndroidUnlimited(true, true),
	AndroidUnlimitedInt(true, true),
	
	//Family plans
	AndroidUnlimitedIntFamily(true,true),
	AndroidUnlimitedFamily(true,true),
	
	//New Megacard
	AndroidUnlimited75(true,true),
	AndroidUnlimited60(true,true),
	AndroidUnlimited40(true,true),
	AndroidUnlimited35(true,true),

	// Home
	HomeUnlimited(true, true),
	HomeUnlimitedInt(true, true),

	// Easy minutes
	Normal30Easy(false, true),
	Normal15Easy(false, true),
	
	//Data Only
	AndroidData5GB(true, true),
	AndroidData2GB(true, true),
	AndroidData1GB(true, true),
	AndroidData500MB(true, true),
	
	//Upgrade plans
	Unlimited1PointUpgradePlan60(true, true),
	Unlimited1halfPointUpgradePlan70(true, true),
	Unlimited3PointUpgradePlan80(true, true);
	

	private static final String PROPS_KEY = "Plans."; //$NON-NLS-1$
	private String planName;
	private String phoneType;
	private String planTitle;
	private String radioLabel;
	private String enrollCheck;
	private String enrollRadio;

	private Plan(boolean isAndroid, boolean monthly) {
		this.planName = PROPS_KEY + this.name() + "Name"; //$NON-NLS-1$
		if (isAndroid) {
			this.phoneType = "Twist.Android"; //$NON-NLS-1$
		} else {
			this.phoneType = "Twist.NonAndroid"; //$NON-NLS-1$
		}
		this.planTitle = PROPS_KEY + this.name() + "Title"; //$NON-NLS-1$
		this.radioLabel = PROPS_KEY + this.name() + "Radio"; //$NON-NLS-1$
		if (monthly) {
			this.enrollCheck = PROPS_KEY + this.name() + "EnrollCheck"; //$NON-NLS-1$
		} else {
			this.enrollCheck = PROPS_KEY + "NA"; //$NON-NLS-1$
		}
		this.enrollRadio = PROPS_KEY + this.name() + "EnrollRadioPrefix";
	}

	public static Plan getPlan(Properties props, String plan, String phoneType) {
		for (Plan p : Plan.values()) {
			if (p.getPlanName(props).equalsIgnoreCase(plan) && p.getPhoneType(props).equalsIgnoreCase(phoneType)) {
				return p;
			}
		}
		throw new IllegalArgumentException("No plan called " + plan); //$NON-NLS-1$
	}

	public String getPlanName(Properties props) {
		return props.getString(planName);
	}

	public String getPhoneType(Properties props) {
		return props.getString(phoneType);
	}

	public String getPlanTitle(Properties props) {
		return props.getString(planTitle);
	}

	public String getRadioLabel(Properties props) {
		return props.getString(radioLabel);
	}

	public String getEnrollCheck(Properties props) {
		return props.getString(enrollCheck);
	}

	public String getEnrollRadioString(Properties props, String postfix) {
		return props.getString(enrollRadio) + postfix;
	}
}