package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class TASSpeedometer extends Speedometer {

	public TASSpeedometer() throws Exception {
		this.brandName = "TAS";
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
