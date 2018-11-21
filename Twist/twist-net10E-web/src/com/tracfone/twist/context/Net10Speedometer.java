package com.tracfone.twist.context;

import com.tracfone.twist.speedometer.Speedometer;

public class Net10Speedometer extends Speedometer {
	
	public Net10Speedometer() throws Exception{
		this.brandName = "Net10";
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
