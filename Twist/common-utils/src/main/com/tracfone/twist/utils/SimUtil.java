package com.tracfone.twist.utils;

import com.tracfone.twist.api.phone.SimTwistDao;

public class SimUtil {

	private SimTwistDao simTwistDao;

	public SimTwistDao getSimTwistDao() {
		return simTwistDao;
	}

	public void setSimTwistDao(SimTwistDao simTwistDao) {
		this.simTwistDao = simTwistDao;
	}

	public String getNewSimCardByPartNumber(String partClass) {
		return simTwistDao.getNewSim(partClass);
	}
	
	public String getSimPartFromSimNumber(String sim) {
		return simTwistDao.getSimPartFromSimNumber(sim);
	}

}