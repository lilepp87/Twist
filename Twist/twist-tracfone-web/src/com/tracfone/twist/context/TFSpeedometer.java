package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class TFSpeedometer extends Speedometer {
	
	public TFSpeedometer() throws Exception{
		this.brandName = "Tracfone";
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
