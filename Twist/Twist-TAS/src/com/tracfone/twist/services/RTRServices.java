package com.tracfone.twist.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ESNUtil;
import com.tracfone.twist.utils.PhoneUtil;
import com.tracfone.twist.utils.ServiceUtil;
import com.tracfone.twist.utils.SimUtil;
import com.tracfone.twist.utils.TwistUtils;

public class RTRServices {
	@Autowired
	private TwistScenarioDataStore scenarioStore;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
	private static Random random = new Random();
	private ResourceBundle props = ResourceBundle.getBundle("TAS");

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setPhoneUtil(PhoneUtil phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setCboUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	public static String generateRandomRefNbr() {
		int area = 200 + random.nextInt(789);
		int central = 200 + random.nextInt(799);
		int station = random.nextInt(99);
		return String.valueOf(area) + String.valueOf(central) + String.format("%04d", station);
	}

	public void activateUsingPinAndAmount(String planCode, String amount) throws Exception {
		String wsdlURL = props.getString("RTRServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/RTRActivation.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String brand=esnUtil.getCurrentBrand();
			if(esnUtil.getCurrentBrand().equalsIgnoreCase("GO_SMART")){
			brand="SIMPLE_MOBILE";	
			}
			
			String request = FileUtils.readFileToString(file, "UTF-8");
			request = request.replaceAll("@RefNbr", generateRandomRefNbr());
			request = request.replaceAll("@brand",brand );
			request = request.replaceAll("@zip", currentESN.getZipCode());
			request = request.replaceAll("@esn", currentESN.getEsn());
			request = request.replaceAll("@sim", currentESN.getSim());
			request = request.replaceAll("@amount", amount);
			request = request.replaceAll("@planCode", planCode);

			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"RTRActivaion");
			//System.out.println(response);
			String respMessage = parseSoaResponse(response);
			System.out.println("");
			Assert.assertTrue(respMessage.equalsIgnoreCase("success") || respMessage.equalsIgnoreCase("success."));
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	private static String parseSoaResponse(String response){
		String message = null;
		try {
			byte[] bytes = response.toString().getBytes(); 
			InputStream inputStream = new ByteArrayInputStream(bytes); 
			DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder =factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			String rootNode = doc.getDocumentElement().getNodeName();
			NodeList nlist = doc.getElementsByTagName(rootNode);
			if (doc.hasChildNodes()) {
				System.out.println("Response:");
				printNote(doc.getChildNodes());
			}
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("resultCode").item(0).getChildNodes().item(0).getNodeValue();
			System.out.println("\n\n SOA response : "+ message);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.print("\n<" + tempNode.getNodeName());
				//System.out.print(" " + tempNode.getTextContent()+ ">");

				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						//System.out.print("\n<" + node.getNodeName() + ">");
						System.out.print(" " + node.getNodeValue());
					}
				}
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes());
				}
				System.out.print("</" + tempNode.getNodeName() + ">");
			}
		}
	}

	public void getSubscriberinformation() throws Exception {
		String wsdlURL = props.getString("RTRServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/RTRGetSubscriberinfo.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String request = FileUtils.readFileToString(file, "UTF-8");
			request = request.replaceAll("@min", currentESN.getMin());
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"RTRGetSubscriber");
			System.out.println(response);
			String respMessage = parseSoaResponse(response);
			System.out.println("");
			Assert.assertTrue(respMessage.equalsIgnoreCase("success") || respMessage.equalsIgnoreCase("success."));
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	public void addFundsUsingPinAndAmount(String futureplanCode, String amount)
			throws Exception {
		String wsdlURL = props.getString("RTRServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/RTRAddFunds.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String request = FileUtils.readFileToString(file, "UTF-8");
			String refNbr = generateRandomRefNbr();
			esnUtil.getCurrentESN().setOrderId(refNbr);
			request = request.replaceAll("@RefNbr", refNbr);
			request = request.replaceAll("@amount", amount);
			request = request.replaceAll("@futureplanCode", futureplanCode);
			request = request.replaceAll("@min", currentESN.getMin());
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"RTRAddFunds");
			//System.out.println(response);
			String respMessage = parseSoaResponse(response);
			System.out.println("");
			Assert.assertTrue(respMessage.equalsIgnoreCase("success") || respMessage.equalsIgnoreCase("success."));
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
		}
	}

	public void cancelAddFunds() throws Exception {
		String wsdlURL = props.getString("RTRServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/RTRCancelAddFunds.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String request = FileUtils.readFileToString(file, "UTF-8");
			request = request.replaceAll("@RefNbr", generateRandomRefNbr());
			request = request.replaceAll("@min", currentESN.getMin());
			request = request.replaceAll("@AddRefNbr", currentESN.getOrderId());
			
			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"RTRCanceFunds");
			//System.out.println(response);
			String respMessage = parseSoaResponse(response);
			System.out.println("");
			Assert.assertTrue(respMessage.equalsIgnoreCase("success") || respMessage.equalsIgnoreCase("success."));
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Generating file failed", e);
	}

	}

}



