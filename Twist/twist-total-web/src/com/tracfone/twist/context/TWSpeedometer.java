package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class TWSpeedometer extends Speedometer {
	
	public TWSpeedometer() throws Exception{
		this.brandName = "Total Wireless";
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