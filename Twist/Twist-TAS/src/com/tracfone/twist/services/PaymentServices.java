package com.tracfone.twist.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class PaymentServices {
	@Autowired
	private TwistScenarioDataStore scenarioStore;
	private static PhoneUtil phoneUtil;
	private ESNUtil esnUtil;
	private static SimUtil simUtil;
	private ServiceUtil serviceUtil;
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

	public void addCreditCardToAccount(String ccType) throws Exception {
		String wsdlURL = props.getString("PaymentMethodServicesUrl");
		try {
			File file = new File("../Twist-TAS/servicerequestfiles/addCreditCard.xml");  
			ESN currentESN = esnUtil.getCurrentESN();
			String cardNbr = TwistUtils.generateCreditCard(ccType);
			String last4digits = cardNbr.substring(cardNbr.length()-4);
			currentESN.setLastFourDigits(last4digits);

			String request = FileUtils.readFileToString(file, "UTF-8");
			String TASURL=props.getString("TAS.HomeUrl");
			if (TASURL.contains("testtas")||TASURL.contains("TESTTAS")){
				request = request.replaceAll("@secusername", props.getString("services.tstusername"));
				request = request.replaceAll("@secpassword", props.getString("services.tstpassword"));	
			}else{
				request = request.replaceAll("@secusername", props.getString("services.sitxusername"));
				request = request.replaceAll("@secpassword", props.getString("services.sitxpassword"));
			}
			request = request.replaceAll("@brand", esnUtil.getCurrentBrand());
			request = request.replaceAll("@email", currentESN.getEmail());
			request = request.replaceAll("@password", currentESN.getPassword());
			request = request.replaceAll("@firstname", "TwistFirstName");
			request = request.replaceAll("@lastname", "TwistLastName");
			request = request.replaceAll("@phonenumber", "7894561230");
			request = request.replaceAll("@city", "Mountain view");
			request = request.replaceAll("@address", "1295 charleston rd");
			request = request.replaceAll("@state", "CA");
			request = request.replaceAll("@zip", "94043");
			request = request.replaceAll("@accnumber", cardNbr);
			request = request.replaceAll("@cardtype", ccType);
			request = request.replaceAll("@expyear", "2021");
			request = request.replaceAll("@expmonth", "07");

			String response=serviceUtil.callSOAServicewithRequestandEndpoint(request, wsdlURL,"AddCreditCard");
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
			message = (String) ((Element) nlist.item(0)).getElementsByTagName("commonTypes:message").item(0).getChildNodes().item(0).getNodeValue();
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
}



