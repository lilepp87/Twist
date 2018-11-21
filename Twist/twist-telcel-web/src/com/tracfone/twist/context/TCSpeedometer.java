package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class TCSpeedometer extends Speedometer {
	
	public TCSpeedometer() throws Exception{
		this.brandName = "Telcel";
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
