package com.tracfone.twist.services;

// JUnit Assert framework can be used for verification

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;

public class BHNServices {

	private static PhoneUtil phoneUtil;
	private ServiceUtil serviceUtil;
	private ESNUtil esnUtil;
	

	private static Logger logger = LogManager.getLogger(BHNServices.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("TAS");
	
	private BHN bhn;
	

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public BHNServices() {
		
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}
	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	

	public void setServiceUtil(ServiceUtil newServiceUtil) {
		this.serviceUtil = newServiceUtil;
	}
	/*@Autowired
	public BHN getBhn() {
		return bhn;
	}
	*/
	public void setBhn(BHN bhn) {
		this.bhn = bhn;
	}
	public void callBHNServiceForPinRegistrationWithPartNumberAndBrand(String partNumber,String brand)
			throws Exception {
		

		String wsdlURL = props.getString("bhnserviceUrl");
		
		try {
			File file = new File("../common-utils/ServiceRequestFiles/BHNRequest.xml");  
			String request = FileUtils.readFileToString(file, "UTF-8");
			
			
			String snp= phoneUtil.getPOSAUnregisteredPin(brand);
			String upc= phoneUtil.getUPCByPartNumber(partNumber);
			bhn = new BHN();
			bhn.setSnp(snp);
			bhn.setUpc(upc);
			
			request = request.replaceAll("@snp", snp );
			request = request.replaceAll("@upc", upc);
			
			scenarioStore.put("SNP", snp);
			scenarioStore.put("UPC", upc);
			
			logger.info(request);
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"BHN_PinRegistration");
			logger.info(response);
			bhn.setRedCode(phoneUtil.getRedCodefromSnp(snp));
			ESN esn = new ESN("TEST", "TESTESN");
			esnUtil.setCurrentESN(esn);
			esnUtil.getCurrentESN().setPin(phoneUtil.getRedCodefromSnp(snp));
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	
	
	}

	public void callBHNServiceForPinUnswipe() throws Exception {
String wsdlURL = props.getString("bhnserviceUrl");
		
		try {
			File file = new File("../common-utils/ServiceRequestFiles/BHNUnSwipeRequest.xml");  
			String request = FileUtils.readFileToString(file, "UTF-8");
			
			request = request.replaceAll("@snp", bhn.getSnp() );
			request = request.replaceAll("@upc", bhn.getUpc());
			
			scenarioStore.put("SNP-UNSWIPE", bhn.getSnp() );
			scenarioStore.put("UPC-UNSWIPE",  bhn.getUpc());
			
			logger.info(request);
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"BHN_unSwipePinRegistration");
			logger.info(response);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	
	}

}
