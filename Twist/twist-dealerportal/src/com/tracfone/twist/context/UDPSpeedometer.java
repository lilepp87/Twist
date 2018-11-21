package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class UDPSpeedometer extends Speedometer {
	
	public UDPSpeedometer() throws Exception{
		this.brandName = "UDP";
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
