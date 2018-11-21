package com.tracfone.twist.speedometer;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Speedometer implements SpeedometerMBean{

	protected boolean mode;
	protected String scenarioName;
	protected String brandName;
	protected String date;
	protected String initialTime;
	protected String endTime;
	private Long initialTimeLong;
	private Long endTimeLong;
	protected double duration;
	public boolean done;
	protected DateFormat dateFormat;
	
	
	protected MBeanServer mbs;
	protected ObjectName on;
	
	public Speedometer() throws Exception{
		dateFormat = new SimpleDateFormat("MM-dd-yy HH-mm-ss");
		done = false;
	}
	
	public void setUp(String scenario) throws Exception {
		date = dateFormat.format(new Date());
		initialTime = date;
		System.out.println("Starting watch at: " + date);
		
		this.scenarioName = scenario;	
		mbs = ManagementFactory.getPlatformMBeanServer();
		on = new ObjectName("tracfone:type=" + brandName +",Scenario=" + scenarioName +",id=" + dateFormat.format(new Date()) + " " + UUID.randomUUID().toString());
		mbs.registerMBean(this, on);
		initialTimeLong = System.currentTimeMillis();	
	}

	public void tearDown(String scenario) throws Exception {
		endTimeLong = System.currentTimeMillis();
		done = true;
		endTime = dateFormat.format(new Date());
		System.out.println("Stopping watch at: " + endTime);
		
		duration = (double) (endTimeLong - initialTimeLong);
		duration = duration / 60000;
		System.out.println("Execution time: " + duration + " minutes");
	}
	
	
	public boolean isMode() {
		return mode;
	}
	
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	
	@Override
	public String getScenarioName() {
		return scenarioName;
	}
	
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	
	@Override
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	@Override
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(String initialTime) {
		this.initialTime = initialTime;
	}

	@Override
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public double getDuration() {
		return duration;
	}
	
	public void setDuration(double duration) {
		this.duration = duration;
	}

	@Override
	public Long getInitialTimeLong() {
		return initialTimeLong;
	}

	public void setInitialTimeLong(Long initialTimeLong) {
		this.initialTimeLong = initialTimeLong;
	}

	@Override
	public Long getEndTimeLong() {
		return endTimeLong;
	}

	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong = endTimeLong;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}		
}
