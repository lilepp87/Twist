package com.tracfone.twist.api.phone;

public interface SimTwistDao {

	public String getNewSimCardByPartNumber(String partClass);

	public String getNewSim(String partClass);

	public String getSimPartFromSimNumber(String sim);

}