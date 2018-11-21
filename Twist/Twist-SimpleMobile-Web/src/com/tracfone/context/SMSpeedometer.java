package com.tracfone.context;

import com.tracfone.twist.speedometer.Speedometer;

public class SMSpeedometer extends Speedometer {
	
	public SMSpeedometer() throws Exception{
		this.brandName = "Simple Mobile";
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
