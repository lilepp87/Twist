package com.tracfone.twist.speedometer;


public interface SpeedometerMBean {

	public String getScenarioName();
	public String getBrandName();
	public String getDate();
	public String getInitialTime();
	public String getEndTime();
	public double getDuration();
	public Long getInitialTimeLong();
	public Long getEndTimeLong();
	public boolean isDone();
	
}
