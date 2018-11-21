package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class STSpeedometer extends Speedometer {

	public STSpeedometer() throws Exception {
		this.brandName = "Straight Talk";
	}

	@Override
	public void setUp(String scenario) throws Exception {
		super.setUp(scenario);
	}

	@Override
	public void tearDown(String scenario) throws Exception {
		super.tearDown(scenario);
	}
}