package com.tracfone.twist.utils;

import static junit.framework.Assert.assertTrue;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import junit.framework.Assert;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import java.io.ByteArrayInputStream;

public class ServiceUtil {

	private static PhoneUtil phoneUtil;
	private static SimUtil simUtil;
	private static Logger logger = LogManager.getLogger(ServiceUtil.class.getName());
	private ResourceBundle props = ResourceBundle.getBundle("Utils");
	private Account account;
	private ESNUtil esnUtil;
	private SimpleDateFormat dateFormat;
	private Calendar cal;
	public Client client;
	long lStartTime;
	long lEndTime ;
	long difference ;
	private CboUtils cboUtils;

	public ServiceUtil() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal = Calendar.getInstance();
	}
	
	public StringBuffer callCboMethodWithRequest(String request) throws Exception {
		BufferedReader in = null;
		String url = props.getString("cbourl");
		String payload = request;
		String finalUrl = url + payload;
		try {
			lStartTime = System.currentTimeMillis();

			URL site = new URL(finalUrl);
			URLConnection st = site.openConnection();
			InputStream is = st.getInputStream();

			lEndTime = System.currentTimeMillis();
			difference = lEndTime - lStartTime;
			logger.info("Elapsed milliseconds: " + difference);

			in = new BufferedReader(new InputStreamReader(is));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Unable to get response for URL = " + url);
			throw new Exception("Unable to get response for URL = " + url);
		}
		StringBuffer out = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null) {
			out.append(line);
		}

		logger.info(out.toString()); // Prints the string content read
										// from input stream
		byte[] bytes = out.toString().getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		phoneUtil.insertIntoServiceResultsTable("CBO-" + document.getDocumentElement().getNodeName(), URLDecoder.decode(payload, "UTF-8"), out,
				String.valueOf(difference), url);
		logger.info("Request:" + payload);
		logger.info("Response:" + out.toString());
		logger.info("\n");
		in.close();
		return out;
	}

	public synchronized String callSOAServicewithRequestandEndpoint(String request, String endpointwsdl,String operation) throws Exception {
		SOAPConnection soapConnection = null;
		InputStream input = null;
		SOAPMessage soapResponse = null;
		ESN esn = esnUtil.getCurrentESN();
		System.out.println("Request:"+request);
		try {
			input = new ByteArrayInputStream(request.getBytes());
			MessageFactory messageFactory = MessageFactory.newInstance();
			MimeHeaders headers = new MimeHeaders();

			SOAPMessage soapMessage = messageFactory.createMessage(headers, input);

			headers = soapMessage.getMimeHeaders();
			// fix
			headers.addHeader("SOAPAction", endpointwsdl);// + serviceName);
			soapMessage.saveChanges();
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			lStartTime = System.currentTimeMillis();
			soapResponse = soapConnection.call(soapMessage, endpointwsdl);
			lEndTime = System.currentTimeMillis();
			difference = lEndTime - lStartTime;

			logger.info("Elapsed milliseconds: " + difference);
//			esn.putInMap("elpasedtime", String.valueOf(difference));

		} catch (SOAPException e) {
			logger.info(e.getMessage());
			Assert.assertFalse(true);
			// throw(e);
		} finally {
			try {
				if (soapConnection != null) {
					soapConnection.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String soaResponseText= printSOAPResponse(soapResponse);
				phoneUtil.insertIntoServiceResultsTable("SOA_" + operation, request,new StringBuffer(soaResponseText),String.valueOf(difference), endpointwsdl);
		
		return soaResponseText;
	}

	private static String printSOAPResponse(SOAPMessage soapResponse) {
		String str = null;
		ByteArrayOutputStream os = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			logger.info("\nResponse SOAP Message = ");

			os = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(os);
			transformer.transform(sourceContent, result);
			str = os.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info(str);
		return str;
	}

	public void setPhoneUtil(PhoneUtil newPhoneUtil) {
		phoneUtil = newPhoneUtil;
	}

	public void setSimUtil(SimUtil simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}
	@Autowired 
	public void setAccount(Account account) {
		this.account = account;
	}
	@Autowired
	public void setCboUtils(CboUtils cboUtils) {
		this.cboUtils = cboUtils;
	}

	static {
		disableSslVerification();
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e2) {
			e2.printStackTrace();
		}
	}

	// parse url
	public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
		final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
		final String[] pairs = url.getQuery().split("&");
		for (String pair : pairs) {
			final int idx = pair.indexOf("=");
			final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
			if (!query_pairs.containsKey(key)) {
				query_pairs.put(key, new LinkedList<String>());
			}
			final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
			query_pairs.get(key).add(value);
		}
		return query_pairs;
	}

	public ArrayList<String> parseXml(String response, String expressionVal) {
		InputSource inputSource = new InputSource(new ByteArrayInputStream(response.getBytes()));
		XPath xpath = XPathFactory.newInstance().newXPath();
		String comns = null;
		if (response.contains("http://www.tracfone.com/rest/model/v1/common")) {
			comns = "http://www.tracfone.com/rest/model/v1/common";
		} else if (response.contains("http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/common")) {
			comns = "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/common";
		} else if (response.contains("http://www.tracfone.com/CommonTypes")) {
			comns = "http://www.tracfone.com/CommonTypes";
		}else if (response.contains("http://xmlns.oracle.com/BRM/schemas/BusinessOpcodes")) {
			comns = "http://xmlns.oracle.com/BRM/schemas/BusinessOpcodes";
		}else if (!response.contains("http://")) {
			comns = "cbo";
		}
		
		final String comns_temp = comns;
		Hashtable<String, String> prefMap = new Hashtable<String, String>() { 
			private static final long serialVersionUID = -8690860758281811435L;
			{
				// for com add your value in if statement
				put("com", comns_temp);
				put("ns9", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/systemview");
				put("ns10", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/common");
				put("ns5", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/tracfonecommon");
				put("ns1", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/messageview");
				put("ns3", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/systemview");
				put("ns15", "http://www.ibm.com/software/issw/telecom/pack/v8.5.0/lite/businessobject/system/sid-v12.5");
				put("ns2", "http://b2b.tracfone.com/PhoneServices");
				put("ns8", "http://www.ibm.com/xmlns/prod/WebSphereCommerce");
				put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
				put("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
				put("ivrapi", "http://www.ibm.com/telecom/v8.5.0/businessobject/system/sid-v12.5/messageview");
				put("brm", "http://xmlns.oracle.com/BRM/schemas/BusinessOpcodes");
				put("env","http://schemas.xmlsoap.org/soap/envelope/");
			}
		};
		SimpleNamespaceContext namespaces = new SimpleNamespaceContext(prefMap);
		xpath.setNamespaceContext(namespaces);

		// String expression1 = "/country/networks/network/@name";
		// String expression =
		// "//ns9:ProductPrice/ns9:regularPrice/ns9:amount|//ns9:ProductAttributes/ns10:name|//ns9:ProductAttributes/ns10:Value/ns10:value|//ns9:ProductSpecification/ns10:id";
		String expression = expressionVal;
		NodeList names = null;
		ArrayList<String> al = new ArrayList<String>();
		try {
			names = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);

		} catch (XPathExpressionException e) {

			e.printStackTrace();
		} finally {
			xpath.reset();
			try {
				inputSource.getByteStream().close();
			} catch (IOException e) {}
		}
		for (int i = 0; i < names.getLength(); i++) {
			logger.info(names.item(i).getTextContent());
			al.add(names.item(i).getTextContent());
		}
		return al;
	}

	// "CCP","grantType","scope","id","secret","brand"
	public String genAuthforApp(String authType, String grantType, String scope, String clientId, String clientSecret, String brand) {
		String userName = null;
		String password = null;
		String cId = null;
		String sourceSystem = null;
		
		if (brand.equalsIgnoreCase("WFM")) {
			cId = props.getString(brand) + "WebMyAcct_" + authType;
			sourceSystem = "WEB";
		} else {
			cId = props.getString(brand) + "AppMyAcct_" + authType;
			sourceSystem = "APP";
		}
		
		String cSecret = props.getString("APP.PASSWORD");
		String input = null;
		String url = null;
		if (authType.equalsIgnoreCase("CCU") || authType.equalsIgnoreCase("CCP")) {
			input = "scope=" + scope + "&grant_type=client_credentials&clientid=" + cId + "&client_secret=" + cSecret + "&brandName=" + brand
					+ "&sourceSystem="+sourceSystem;
			url = props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("MAPP_OAUTH_RESOURCE");
		}
		if (authType.equalsIgnoreCase("RO")) {
			userName = account.getEmail();
			password = account.getPassword();

			url = props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("MAPP_OAUTH_RESOURCE_RO");
			input = "grant_type=password&username=" + userName + "&password=" + password + "&scope=" + scope + "&client_id=" + cId
					+ "&client_secret=" + cSecret + "&brandName=" + brand + "&sourceSystem="+sourceSystem;
		}

		try {
			client = Client.create();
			if (authType.equalsIgnoreCase("RO")) {
				client.addFilter(new HTTPBasicAuthFilter(userName, password));
			} else {
				client.addFilter(new HTTPBasicAuthFilter(cId, cSecret));
			}

			client.addFilter(new LoggingFilter(System.out));
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Unable to generate oauth HTTP error code: " + response.getStatus());				
			}

			String output = response.getEntity(String.class);

			JSONObject json = new JSONObject(output);
			String access_token = (String) json.get("access_token");
			// token = URLEncoder.encode(token,"UTF-8");
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
//			Assert.assertTrue(false);
			throw new RuntimeException("Unable to generate oauth for app", e);
		}
	}
	
	//do not delete : @mkankanala
	public String genAuthforApp1(String authType, String grantType, String scope, String clientId, String clientSecret, String brandname) {
		String userName = null;
		String password = null;
		String cId = null;
		String sourceSystem = null;
		String brand = brandname;
		if(brand.equalsIgnoreCase("GO_SMART")){
			brand= "SIMPLE_MOBILE";
		}
		
		if (brand.equalsIgnoreCase("WFM")) {
			cId = props.getString(brand) + "WebMyAcct_" + authType;
			sourceSystem = "WEB";
		} else {
			cId = props.getString(brand) + "AppMyAcct_" + authType;
			sourceSystem = "APP";
		}
		
		String cSecret = props.getString("APP.PASSWORD");
		String input = null;
		String url = null;
		
		if (authType.equalsIgnoreCase("RO")) {
			userName = esnUtil.getCurrentESN().getEmail();
			
			try{
			password = cboUtils.callGetPassword(userName,brand);
			}catch(Exception e){
				e.printStackTrace();
			}
			url = props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("MAPP_OAUTH_RESOURCE_RO");
			input = "grant_type=password&username=" + userName + "&password=" + password + "&scope=" + scope + "&client_id=" + cId
					+ "&client_secret=" + cSecret + "&brandName=" + brand + "&sourceSystem="+sourceSystem;
		}

		try {
			client = Client.create();
			if (authType.equalsIgnoreCase("RO")) {
				client.addFilter(new HTTPBasicAuthFilter(userName, password));
			} else {
				client.addFilter(new HTTPBasicAuthFilter(cId, cSecret));
			}

			client.addFilter(new LoggingFilter(System.out));
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Unable to generate oauth HTTP error code: " + response.getStatus());				
			}

			String output = response.getEntity(String.class);

			JSONObject json = new JSONObject(output);
			String access_token = (String) json.get("access_token");
			// token = URLEncoder.encode(token,"UTF-8");
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
//			Assert.assertTrue(false);
			throw new RuntimeException("Unable to generate oauth for app", e);
		}
	}

	public String genAuthfor(String app, String orderType) {
		String url;
		String input;
		String userName;
		String password;

		if (app.equalsIgnoreCase("WARP")) {
			userName = props.getString(props.getString("ENV.") + "WARP_CLIENT_ID");
			password = props.getString(props.getString("ENV.") + "WARP_CLIENT_SECRET");
			if (orderType.equalsIgnoreCase("order management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_ORDER_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_ORDER_MANAGEMENT");
			} else if (orderType.equalsIgnoreCase("customer management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_CUSTOMER_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_CUSTOMER_MANAGEMENT");
			} else if (orderType.equalsIgnoreCase("service management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_SERVICE_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_SERVICE_MANAGEMENT");
			}else if (orderType.equalsIgnoreCase("billing management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_BILLING_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_BILLING_MANAGEMENT");
			} else {
				throw new IllegalArgumentException("Unsupported orderType for WARP: " + orderType);
			}
		}else if (app.equalsIgnoreCase("API")) {
			userName = props.getString(props.getString("ENV.") + "API_CLIENT_ID");
			password = props.getString(props.getString("ENV.") + "API_CLIENT_SECRET");
			if (orderType.equalsIgnoreCase("order management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_ORDER_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_ORDER_MANAGEMENT");
			} else if (orderType.equalsIgnoreCase("customer management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_CUSTOMER_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_CUSTOMER_MANAGEMENT");
			} else if (orderType.equalsIgnoreCase("service management")) {
				input = props.getString(props.getString("ENV.") + "WARP_AUTH_SERVICE_MANAGEMENT_INPUT");
				url = props.getString(props.getString("ENV.") + "WARP_AUTH_SERVICE_MANAGEMENT");
			} else {
				throw new IllegalArgumentException("Unsupported orderType for WARP: " + orderType);
			}
		} else if (app.equalsIgnoreCase("ETAILER")) {
			userName = props.getString(props.getString("ENV.") + "CLIENT_ID");
			password = props.getString(props.getString("ENV.") + "CLIENT_SECRET");
			if (orderType.equalsIgnoreCase("order management")) {
				input = props.getString(props.getString("ENV.") + "OAUTH_TOKEN_INPUT");
				url = props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "OAUTH_TOKEN");
			} else if (orderType.equalsIgnoreCase("resource management")) {
				input = props.getString(props.getString("ENV.") + "RESOURCE_TOKEN_INPUT");
				url = props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "RESOURCE_TOKEN");
			} else {
				throw new IllegalArgumentException("Unsupported orderType for ETAILER: " + orderType);
			}
		} else if (app.equalsIgnoreCase("LRP")) {
			userName = props.getString(props.getString("ENV.") + "LRP_CLIENT_ID");
			password = props.getString(props.getString("ENV.") + "LRP_CLIENT_SECRET");
			input = props.getString(props.getString("ENV.") + "LRP_AUTH_PARAM");
			url = props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "LRP_OAUTH_RESOURCE");
		} else if (app.equalsIgnoreCase("GOAPI")) {
			userName = props.getString(props.getString("ENV.")+"GOAPI_CLIENT_ID");
			password = props.getString(props.getString("ENV.")+"GOAPI_CLIENT_SECRET");
			if (orderType.equalsIgnoreCase("catalog management")) {
				input = props.getString(props.getString("ENV.")+"GOAPI_AUTH_CATALOG_MANAGEMENT_PARAM");
				url = props.getString(props.getString("ENV.")+"GOAPI_END_POINT") + props.getString(props.getString("ENV.")+"GOAPI_AUTH_CATALOG_MANAGEMENT");
				
			} else if (orderType.equalsIgnoreCase("customer management")) {
				input = props.getString(props.getString("ENV.")+"GOAPI_AUTH_CUSTOMER_MANAGEMENT_PARAM");
				url = props.getString(props.getString("ENV.")+"GOAPI_END_POINT") + props.getString(props.getString("ENV.")+"GOAPI_AUTH_CUSTOMER_MANAGEMENT");
				
			} else if (orderType.equalsIgnoreCase("order management")) {
				input = props.getString(props.getString("ENV.")+"GOAPI_AUTH_ORDER_MANAGEMENT_PARAM");
				url = props.getString(props.getString("ENV.")+"GOAPI_END_POINT") + props.getString(props.getString("ENV.")+"GOAPI_AUTH_ORDER_MANAGEMENT");
				
			} else if (orderType.equalsIgnoreCase("resource management")) {
				input = props.getString(props.getString("ENV.")+"GOAPI_AUTH_RESOURCE_MANAGEMENT_PARAM");
				url = props.getString(props.getString("ENV.")+"GOAPI_END_POINT") + props.getString(props.getString("ENV.")+"GOAPI_AUTH_RESOURCE_MANAGEMENT");			
			} else if (orderType.equalsIgnoreCase("service-qualification")) {
				input = props.getString(props.getString("ENV.")+"GOAPI_AUTH_SERVICE_QUALIFICTION_PARAM");
				url = props.getString(props.getString("ENV.")+"GOAPI_END_POINT") + props.getString(props.getString("ENV.")+"GOAPI_AUTH_SERVICE_QUALIFICTION");
			} else {
				throw new IllegalArgumentException("Unsupported orderType for GOAPI: " + orderType);
			}
		} else if (app.equalsIgnoreCase("FISERV")) {
			userName = props.getString(props.getString("ENV.") + "FISERV_CLIENT_ID");
			password = props.getString(props.getString("ENV.") + "FISERV_CLIENT_SECRET");
			input = props.getString(props.getString("ENV.") + "FISERV_AUTH_BILLING_MANAGEMENT_PARAM");
			url = props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "FISERV_AUTH_BILLING_MANAGEMENT_RESOURCE");
		} else {
			throw new IllegalArgumentException("Unsupported app for genAuthfor: " + app);
		}
		System.out.println(input);
		System.out.println(url);
		
		try {
			client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(userName, password));
			client.addFilter(new LoggingFilter(System.out));
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Unable to generate oauth HTTP error code: " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			JSONObject json = new JSONObject(output);
			String access_token = (String) json.get("access_token");
			return access_token;
			// token = URLEncoder.encode(token,"UTF-8");
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException("Unable to generate oauth for app: " + app);
		}
	}

	public String genAccessTokenUsingAuthCode(String app, String orderType, String authCode) {
		String userName = props.getString(props.getString("ENV.") + "LRP_CLIENT_ID");
		String password = props.getString(props.getString("ENV.") + "LRP_CLIENT_SECRET");
		String input = props.getString(props.getString("ENV.") + "LRP_AUTH_PARAM_1") + authCode;
		String url = props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "LRP_OAUTH_RESOURCE");

		try {
			client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(userName, password));
			client.addFilter(new LoggingFilter(System.out));
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Unable to generate oauth HTTP error code: " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			JSONObject json = new JSONObject(output);
			String access_token = (String) json.get("access_token");
			// token = URLEncoder.encode(token,"UTF-8");
			return access_token;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new IllegalArgumentException("Unable to generate oauth for app: " + app);
		}
	}

	public String genAuth() {
		return genAuthfor("ETAILER", "order management");
	}

	public synchronized String generateAuthCode() {
		List authCode = null;
		try {
			String named_userId = esnUtil.getCurrentESN().getFromMap("named_userId"); // "CUcmbFHdNyE";
			String sha256hex = sha256(named_userId);
			String app_name = props.getString(props.getString("ENV.") + "LRP_APP_NAME");
			String get_url = props.getString(props.getString("ENV.") + "END_POINT")
					+ props.getString(props.getString("ENV.") + "LRP_AUTH_CODE_RESOURCE") + "?response_type=code&redirect_uri="
					+ props.getString(props.getString("ENV.") + "LRP_REDIRECT_URI") + "&" + "client_id="
					+ props.getString(props.getString("ENV.") + "LRP_CLIENT_ID") + "&rstate="
					+ props.getString(props.getString("ENV.") + "LRP_RSTATE") + "&username=" + named_userId + "&" + "confirmation=" + sha256hex
					+ "&app-name=" + app_name;
			System.out.println(get_url);
			HttpURLConnection con = (HttpURLConnection) (new URL(get_url).openConnection());
			con.setInstanceFollowRedirects(false);
			con.connect();
			String location = con.getHeaderField("Location");
			logger.info("Redirect url" + location);
			authCode = splitQuery(new URL(location)).get("code");
			logger.info("Auth Code : " + authCode.get(0));

		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
		return (String) authCode.get(0);

	}

	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public ArrayList<String> generatePin(String token, String pinPart) {
		ArrayList<String> out = new ArrayList<String>();
		String pin = null;
		Date javaUtilDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String orderId = TwistUtils.generateRandomEsn();
		orderId = "Twist" + orderId;
		try {
			client = Client.create();
			WebResource webResource = client.resource(
					props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "GENERATE_PIN")).queryParam(
					"client_id", props.getString(props.getString("ENV.") + "CLIENT_ID"));

			client.addFilter(new LoggingFilter(System.out));

			JsonObject relatedPartiesobj = Json.createObjectBuilder()
					.add("party", Json.createObjectBuilder().add("partyID", "incomm").add("partyName", "E_TAILER NAME")).add("roleType", "partner")
					.build();

			JsonObject brandobj = Json.createObjectBuilder().add("brand", "NET10").build();

			JsonObject orderItemsobj = Json
					.createObjectBuilder()
					.add("productOffering",
							Json.createObjectBuilder().add("id", pinPart).add("productCategory", "AIRTIME_CARD")
									.add("productSpecification", brandobj))
					.add("action", "add")
					.add("orderItemPrice",
							Json.createObjectBuilder().add("price", Json.createObjectBuilder().add("amount", 35.99).add("currencyCode", "USD"))
									.add("quantity", "1")).add("id", "1").build();

			JsonObject input = Json.createObjectBuilder().add("id", "B2B201411175007665").add("externalID", orderId)
					.add("orderDate", formatter.format(javaUtilDate)).add("relatedParties", Json.createArrayBuilder().add(relatedPartiesobj))
					.add("orderItems", Json.createArrayBuilder().add(orderItemsobj))

					.build();

			JSONObject result = callPostService(webResource, input, "REST-ETAILER_GeneratePin", token);

			JSONObject status = result.getJSONObject("status");
			JSONObject product = result.getJSONArray("orderItems").getJSONObject(0).getJSONObject("product");
			String message = (String) status.get("message");
			assertTrue(message.equalsIgnoreCase("SUCCESS"));
			pin = (String) product.get("productSerialNumber");
			logger.info(pin);
			out.add(pin);
			out.add(orderId);

		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}

		return out;
	}

	public String voidPin(String token, String pin, String orderId) {
		client = Client.create();
		WebResource webResource = client.resource(
				props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "VOID_PIN")).queryParam(
				"client_id", props.getString(props.getString("ENV.") + "CLIENT_ID"));

		client.addFilter(new LoggingFilter(System.out));

		JsonObject relatedPartiesobj = Json.createObjectBuilder()
				.add("party", Json.createObjectBuilder().add("partyID", "incomm").add("partyName", "E_TAILER NAME")).add("roleType", "partner")
				.build();

		JsonObject orderItemsobj = Json
				.createObjectBuilder()
				.add("product",
						Json.createObjectBuilder().add("productSerialNumber", pin).add("productCategory", "AIRTIME_CARD")
								.add("productSpecification", Json.createObjectBuilder().add("brand", "NET10"))).add("action", "RETURN")
				.add("id", "1").build();

		JsonObject input = Json.createObjectBuilder().add("id", "B2B201411175007665").add("externalID", orderId)
				.add("orderDate", "2016-04-16T16:42:23-04:00").add("relatedParties", Json.createArrayBuilder().add(relatedPartiesobj))
				.add("orderItems", Json.createArrayBuilder().add(orderItemsobj))
				.build();

		return callPostService(webResource, input, "REST-ETAILER_VoidPin", token).toString();

	}

	public String checkCoverage(String token, String zipCode) throws Exception {
		client = Client.create();
		WebResource webResource = client
				.resource( props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "SERVICE_AVAILABILITY"))
				.queryParam("partyTransactionID", "123").queryParam("partyID", "INCOMM").queryParam("brand", "NET10").queryParam("sourceSystem", "ETEDS").queryParam("lang", "ENG")
				.queryParam("zipCode", zipCode).queryParam("client_id", props.getString(props.getString("ENV.") + "CLIENT_ID"));

		client.addFilter(new LoggingFilter(System.out));

		return callGetService(webResource, "REST-ETAILER_CheckCoverage", token).toString();
	}

	public String getStatus(String token, String pin) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "RESOURCE_STATUS"))
				.queryParam("partyTransactionID", "123").queryParam("partyID", "INCOMM").queryParam("brand", "NET10")
				.queryParam("sourceSystem", "ETEDS").queryParam("lang", "ENG").queryParam("resourceIdentifier", pin)
				.queryParam("resourceCategory", "AIRTIME_CARD").queryParam("client_id", props.getString(props.getString("ENV.") + "CLIENT_ID"));

		return callGetService(webResource, "REST-ETAILER_GetPinStatus", token).toString();
	}

	public String RegisterEsnWithPin(String token, String esn, String pin) {
		client = Client.create();
		WebResource webResource = client.resource(
				props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "RESOURCE_INVENTORY"))
				.queryParam("client_id", props.getString(props.getString("ENV.") + "CLIENT_ID"));

		client.addFilter(new LoggingFilter(System.out));

		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "12312333123").build();

		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "ETEDS").build();

		JsonObject relatedPartiesobj = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder().add("partyID", "INCOMM")
								.add("partyExtension", Json.createArrayBuilder().add(partyExtensionobj1).add(partyExtensionobj2)))
				.add("roleType", "partner").build();

		JsonObject supportingLogicalResourcesobj = Json.createObjectBuilder().add("resourceCategory", "AIRTIME_CARD").add("serialNumber", pin)
				.build();

		JsonObject input = Json.createObjectBuilder()
				.add("orderDate", "2016-04-16T16:42:23-04:00")
				.add("relatedParties", Json.createArrayBuilder()
						.add(relatedPartiesobj))
				.add("resource", Json.createObjectBuilder()
						.add("association", Json.createObjectBuilder()
								.add("role", "marriage"))
						.add("resourceSpecification", Json.createObjectBuilder()
								.add("brand", "NET10"))
						.add("physicalResource", Json.createObjectBuilder()
								.add("resourceCategory", "BYOP_HANDSET")
								.add("serialNumber", esn)
								.add("supportingLogicalResources", Json.createArrayBuilder()
										.add(supportingLogicalResourcesobj))))
				.build();

		return callPostService(webResource, input, "REST-ETAILER_RegisterPinwithEsn", token).toString();
	}

	public void createAccount(String auth, String brandName) {
		try {
			client = Client.create();
			WebResource webResource = client.resource(
					props.getString(props.getString("ENV.") + "END_POINT")
							+ props.getString(props.getString("ENV.") + "WARP_CUSTOMER_MANAGEMENT_RESOURCE")).queryParam("client_id",
					props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));

			client.addFilter(new LoggingFilter(System.out));

			JsonObject partyExtensionobj = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231234234424").build();

			JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", esnUtil.getSourceSystem()).build();
			
			JsonObject custProductobjid = Json
					.createObjectBuilder()
					.add("product",
							Json.createObjectBuilder().add("productSerialNumber", "").add("productCategory", "HANDSET")
									.add("productSpecification", Json.createObjectBuilder().add("brand", brandName))).build();

			JsonObject partyExtensioncred = Json.createObjectBuilder().add("name", "partySecret").add("value", "1234").build();

			JsonObject customerAccountsExtensionobj1 = Json.createObjectBuilder().add("name", "password").add("value", "tracfone").build();
			JsonObject customerAccountsExtensionsec = Json.createObjectBuilder().add("name", "What was the name of your first boyfriend/girlfriend?")
					.add("value", "John").add("type", "securityQuestion").build();

			JsonObject customerAccountsExtensioncont = Json.createObjectBuilder().add("name", "canTracfonePartnersContactYou").add("value", "true")
					.build();

			JsonObject relatedPartyobj1 = Json
					.createObjectBuilder()
					// .add("", Json.createObjectBuilder()
					.add("party",
							Json.createObjectBuilder().add("individual",
									Json.createObjectBuilder().add("firstName", "TwistFirstName").add("lastName", "TwistLastName")))
					.add("roleType", "customer")
					.build();
			String partyId;
			JsonObject relatedPartyobj2;
			if(esnUtil.getSourceSystem().equalsIgnoreCase("API")){
				partyId="TCETRA";
			 	
				relatedPartyobj2 = Json.createObjectBuilder()
    					.add("party",
    							Json.createObjectBuilder().add("partyID", partyId)
    									.add("partyExtension", Json.createArrayBuilder().add(partyExtensionobj).add(partyExtensionobj1))).add("roleType", "partner").build();
			}else{
				partyId= "WALMART";
			 	 relatedPartyobj2 = Json
    					.createObjectBuilder()
    					.add("party",
    							Json.createObjectBuilder().add("partyID", partyId)
    									.add("partyExtension", Json.createArrayBuilder().add(partyExtensionobj))).add("roleType", "partner").build();
			}
          
			JsonObject customerAccountsExtensionobj = Json
					.createObjectBuilder()
					.add("customerAccountExtension",
							Json.createArrayBuilder().add(partyExtensioncred).add(customerAccountsExtensionobj1).add(customerAccountsExtensionsec)
									.add(customerAccountsExtensioncont)).build();

			JsonObject customerAccountsobj = Json.createObjectBuilder()
					// .add("customerProducts",
					// Json.createArrayBuilder().add(custProductobjid))
					.add("brand", brandName)
					.add("customerAccountExtension",
							Json.createArrayBuilder().add(partyExtensioncred).add(customerAccountsExtensionobj1).add(customerAccountsExtensionsec)
									.add(customerAccountsExtensioncont)).build();

			String accEmail = "warp" + TwistUtils.createRandomEmail();
			esnUtil.getCurrentESN().setEmail(accEmail);

			JsonObject contactMediumobj = Json
					.createObjectBuilder()
					.add("contactDetails",
							Json.createArrayBuilder().add(
									Json.createObjectBuilder().add("city", "Mountain View").add("country", "USA").add("emailAddress", accEmail)
											.add("stateOrProvince", "CA").add("street1", "1295 charleston road").add("street2", "")
											.add("postcode", "94043")))
					.add("telephoneNumber", Json.createArrayBuilder().add(Json.createObjectBuilder().addNull("3057156800"))).build();

			JsonObject input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj1).add(relatedPartyobj2))
					.add("customerAccounts", Json.createArrayBuilder().add(customerAccountsobj))
					.add("contactMedium", Json.createArrayBuilder().add(contactMediumobj)).build();

			
			JSONObject jsonRes  = callPostService(webResource, input, "REST-"+esnUtil.getSourceSystem()+"_CreateAccount", auth);
			// logger.info(response.getEntity(String.class));

			JSONObject myResponse = jsonRes.getJSONObject("status");
			Assert.assertEquals("SUCCESS", myResponse.getString("message").toUpperCase());
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void callGetAccountSummary(String accountEmail, String brand, String partyID) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = null;
		if (partyID.equalsIgnoreCase("WALMART") ||partyID.equalsIgnoreCase("TCETRA")) {
			if(brand.equalsIgnoreCase("WFM")){
				webResource = client
						.resource(
								props.getString(props.getString("ENV.") + "END_POINT")
										+ props.getString(props.getString("ENV.") + "WARP_CUSTOMER_MANAGEMENT_RESOURCE"))
						.queryParam("partyTransactionID", "123").queryParam("partyID", partyID).queryParam("brand", brand)
						.queryParam("sourceSystem", "WARP").queryParam("lang", "ENG").queryParam("productCategory", "HANDSET")
						.queryParam("productSerialNumber", esnUtil.getCurrentESN().getEsn()).queryParam("partySecret", "1234")
						.queryParam("emailAddress", esnUtil.getCurrentESN().getEmail()).queryParam("password", "tracfone")
						.queryParam("client_id", props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));
			}else{
				webResource = client
						.resource(
								props.getString(props.getString("ENV.") + "END_POINT")
										+ props.getString(props.getString("ENV.") + "WARP_CUSTOMER_MANAGEMENT_RESOURCE"))
						.queryParam("partyTransactionID", "123").queryParam("partyID", partyID).queryParam("brand", brand)
						.queryParam("sourceSystem", "WARP").queryParam("lang", "ENG").queryParam("productCategory", "HANDSET")
						.queryParam("productSerialNumber", esnUtil.getCurrentESN().getEsn()).queryParam("partySecret", "1234")
						.queryParam("client_id", props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));
			}
			
		} else {
			throw new IllegalArgumentException("Unsupport partyID: " + partyID);
		}

		String token = genAuthfor(esnUtil.getSourceSystem(), "customer management");
		callGetService(webResource, "REST-"+esnUtil.getSourceSystem()+"_getAccountSummary", token);
	}

	public void validateCustomerOrder(String pinPart, String activationZip, String flowName, String partyID, String transType, String cardType)
			throws Exception {
		//String pin = "";
		List<String> pinsList = Arrays.asList("WFMAPPU0049", "WFMAPPU0029", "WFMAPPU0039", "WFMAPPU0024");
		String pinsListCommaSeparated = StringUtils.join(pinsList,",");
		
		if (transType.equalsIgnoreCase("validate")) {
				if (pinPart.equalsIgnoreCase("nopin") || pinPart.isEmpty()) {
					if(!esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
						pinsListCommaSeparated = "nopin";
					}
				} else {
					pinsListCommaSeparated = pinPart;
				}
	
			}

		if (flowName.equalsIgnoreCase("ACTIVATION") || flowName.equalsIgnoreCase("ACTIVATION_WENROLLMENT")) {
			validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, "", "");
		} else if (flowName.equalsIgnoreCase("ADDACTIVATION")) {
			String identifier = esnUtil.getCurrentESN().getMin();
			validateCustomerAct(pinsListCommaSeparated, activationZip, "ACTIVATION", partyID, transType, identifier, "");
		} else if (flowName.equalsIgnoreCase("PHONE_UPGRADE")) {
			String identifier = esnUtil.getCurrentESN().getFromEsn().getMin();
			validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, identifier, "");
		} else if (flowName.equalsIgnoreCase("REACTIVATION") || flowName.equalsIgnoreCase("REACTIVATION_WENROLLMENT")) {
			if (esnUtil.getCurrentBrand().equalsIgnoreCase("TOTAL_WIRELESS")) {
				String identifier = esnUtil.getCurrentESN().getMin();
				validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, identifier, "");
			} else {
				validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, "", "");
			}
		} else if (flowName.equalsIgnoreCase("external_port") || flowName.equalsIgnoreCase("external_port_WENROLLMENT")) {
			validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, "", "");
		} else if (flowName.equalsIgnoreCase("Internal_port") || flowName.equalsIgnoreCase("Internal_port_WENROLLMENT")) {
			validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, "", "");
		} else if (flowName.equalsIgnoreCase("ENROLLMENT")) {
			validateCustomerAct(pinsListCommaSeparated, activationZip, flowName, partyID, transType, "", cardType);
		} else {
			throw new Exception("WRONG/EMPTY FLOW NAME.PLEASE CHECK AND RETRY");
		}
	}

	public void validateCustomerAct(String pin, String activationZip, String flowName, String partyID, String transType, String identifier,
			String cardType) {
		try {

			client = Client.create();
			WebResource webResource;
			if (transType.equalsIgnoreCase("validate")) {
				webResource = client.resource(
						props.getString(props.getString("ENV.") + "END_POINT")
								+ props.getString(props.getString("ENV.") + "WARP_VALAIDATE_ORDER_MANAGEMENT_RESOURCE")).queryParam("client_id",
						props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));
			} else {
				webResource = client.resource(
						props.getString(props.getString("ENV.") + "END_POINT")
								+ props.getString(props.getString("ENV.") + "WARP_SUBMIT_ORDER_MANAGEMENT_RESOURCE")).queryParam("client_id",
						props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));
			}

			client.addFilter(new LoggingFilter(System.out));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "ITQ999").build();
			JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", esnUtil.getSourceSystem()).build();
			JsonObject partyExtensionobj3 = Json.createObjectBuilder().add("name", "vendorStore").add("value", "302").build();
			JsonObject partyExtensionobj4 = Json.createObjectBuilder().add("name", "vendorTerminal").add("value", "302").build();
			JsonObject paymentPlanObj = Json.createObjectBuilder()
												.add("isRecurring", true)
												.add("type", "SERVICE")
												.add("paymentMean",Json.createArrayBuilder()
														.add(Json.createObjectBuilder().add("refID", account.getPaymentSrc() ))).build();
			
			JsonObjectBuilder billingAccountObj = Json.createObjectBuilder();
			billingAccountObj.add("paymentPlan", paymentPlanObj);

			JsonObject relatedPartyobj = Json
					.createObjectBuilder()
					.add("party",
							Json.createObjectBuilder()
									.add("partyID", partyID)
									.add("languageAbility", "ENG")
									.add("partyExtension",
											Json.createArrayBuilder().add(partyExtensionobj1).add(partyExtensionobj2).add(partyExtensionobj3)
													.add(partyExtensionobj4))).add("roleType", "partner").build();

			JsonObject partyExtObj = Json.createObjectBuilder().add("name", "accountEmail").add("value", esnUtil.getCurrentESN().getEmail())

			.build();

			JsonObject partyExtObj1 = Json.createObjectBuilder().add("name", "accountPassword").add("value", "tracfone")

			.build();

			JsonObject relatedPartyobj1 = Json.createObjectBuilder()
					.add("party", Json.createObjectBuilder().add("partyExtension", Json.createArrayBuilder().add(partyExtObj).add(partyExtObj1)))
					.add("roleType", "customer").build();
			List<ESN> allEsns = new ArrayList<ESN>();
			List<ESN> familyesns = esnUtil.getCurrentESN().getFamilyEsns();
			
//			if (identifier.isEmpty() || "PHONE_UPGRADE".equalsIgnoreCase(flowName) || "REACTIVATION".equalsIgnoreCase(flowName)) {
//				
//				allEsns.addAll(familyesns);
//			}
			
			allEsns.add(esnUtil.getCurrentESN());
			allEsns.addAll(familyesns);
			JsonArrayBuilder orderItems;
			orderItems = Json.createArrayBuilder();
			StringTokenizer st = new StringTokenizer(pin,",");
		//	List<String> pinList = new ArrayList<String>();
			Iterator<ESN> esnList = allEsns.iterator();
			int i = 1;
			String flownameTemp = flowName;
			while (st.hasMoreElements() && esnList.hasNext()) {
				String pinPart = (String)st.nextElement();
				String softPin = phoneUtil.getNewPinByPartNumber(pinPart);
				if(pin.equalsIgnoreCase("nopin")){
					softPin = "nopin";
				}
				
				if(flowName.contains("_WENROLLMENT")){
					flownameTemp = flowName.replace("_WENROLLMENT", "");
				}
				JsonObject obj = CreateOrderobj(esnList.next(), Integer.toString(i), transType, softPin, activationZip, flownameTemp, identifier, cardType);
				orderItems.add(obj);
				i++;
			}
			
			//int i = 1;
/*			for (ESN currentEsn : allEsns) {
					for (String pinPart : pinList) {
					String pinNum = "nopin";
					if(transType.equalsIgnoreCase("Validate") && !pin.isEmpty() && !pin.equalsIgnoreCase("nopin")){
						//HashMap<String,String> name= new HashMap<String,String>();
						//for(int i=1;i<=name ;i++){
							pinNum = phoneUtil.getNewPinByPartNumber(pinPart);		
						
						
					}
	//				esn.getTransType();
	//				esn.getDeviceType();
	//				esn.getEsn();
	//				esn.getSim();
	//				esn.getMin();
	//				esn.getPin()
	//				esn.getFromMap("carrierType")
	//				esn.getFromMap("phoneModel")
	//				esn.getFromEsn()
					JsonObject obj = CreateOrderobj(currentEsn, Integer.toString(i), transType, pinNum, activationZip, flowName, identifier, cardType);
					orderItems.add(obj);
					i++;
				}
			}*/
			JsonObject validateInputAll;
			if(flowName.equalsIgnoreCase("ENROLLMENT")){
				
				JsonObject billingAddressObjid = Json.createObjectBuilder().add("street1", "1295 charleston rd").add("street2", "")
						.add("city", "Mountain View").add("stateOrProvince", "CA").add("zipcode", "94043").add("country", "USA").build();

				JsonObject cipherTextObjid = Json.createObjectBuilder().add("cipherText", props.getString(props.getString("ENV.") + "DISCOVER.CIPHER"))
						.build();

				JsonObject cryptoOjbid = Json.createObjectBuilder().add("crypto", cipherTextObjid).build();
				JsonObject paymentMeanObjid = Json.createObjectBuilder().add("creditCard", cryptoOjbid).add("billingAddress", billingAddressObjid)
						.build();
				JsonObject paymentPlanObjid = Json.createObjectBuilder().add("isRecurring", "Y").add("type", "SERVICE")
						.add("paymentMean", Json.createArrayBuilder().add(paymentMeanObjid))

						.build();
				JsonObject billingAccountObjid = Json.createObjectBuilder().add("paymentPlan", paymentPlanObjid).build();
				
				validateInputAll = Json.createObjectBuilder().add("externalID", "ITQ999").add("orderDate", dateFormat.format(date))
						.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj).add(relatedPartyobj1)).add("orderItems", orderItems)
						.add("billingAccount", billingAccountObjid)
						.build();
			}else if(flowName.contains("_WENROLLMENT")) {
				validateInputAll = Json.createObjectBuilder().add("externalID", "ITQ999").add("orderDate", dateFormat.format(date))
						.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj).add(relatedPartyobj1)).add("orderItems", orderItems)
						.add("billingAccount", billingAccountObj)
						.build();
			}else{
				validateInputAll = Json.createObjectBuilder().add("externalID", "ITQ999").add("orderDate", dateFormat.format(date))
						.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj).add(relatedPartyobj1)).add("orderItems", orderItems)
						//.add("", "")
						.build();
			}

			JSONObject jsonRes ;
			String token = genAuthfor(esnUtil.getSourceSystem(), "order management");
			if (webResource.getURI().toString().contains("v1/serviceorder/validate")) {
				jsonRes = callPostService(webResource, validateInputAll, "REST-WARP_ValidateCustomerOrder_" + flowName, token);
			} else if (webResource.getURI().toString().contains("v1/serviceorder")) {
				jsonRes = callPostService(webResource, validateInputAll, "REST-WARP_SubmitCustomerOrder_" + flowName, token);
			} else {
				throw new IllegalArgumentException("Unknown URL for service");
			}

			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			if (responseMessage.equalsIgnoreCase("SUCCESS")) {
				Assert.assertTrue(true);
			} else {
				if (phoneUtil.getEsnLeasedStaus(esnUtil.getCurrentESN().getEsn()).equalsIgnoreCase("1001")) {

				} else {
					Assert.assertTrue(myResponse.getString("message").toUpperCase(), false);
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void submitCustomerOrderFor(String flowName, String cardType) {
		try {
			String partyId;
			if(esnUtil.getSourceSystem().equalsIgnoreCase("API")){
				partyId="TCETRA";
			}else{
				partyId= "WALMART";
			}
			validateCustomerOrder("", "", flowName, partyId, "submit", cardType);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public void CheckByopCoverage(String carrier, String zipCode) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "END_POINT")
								+ props.getString(props.getString("ENV.") + "WARP_ORDER_SERVICE_AVAILABILITY_RESOURCE"))
				.queryParam("partyTransactionID", "123").queryParam("partyID", "WALMART").queryParam("brand", "STRAIGHT_TALK")
				.queryParam("sourceSystem", "WARP").queryParam("lang", "ENG").queryParam("zipCode", zipCode).queryParam("deviceType", "BYOP")
				.queryParam("carrier", carrier).queryParam("client_id", props.getString(props.getString("ENV.") + "WARP_CLIENT_ID"));


		String token = genAuthfor("WARP", "order management");
		callGetService(webResource, "REST-WARP_BYOP Service Availability", token);
	}

	public JsonObject CreateOrderobj(ESN esn, String count, String transType, String pin, String activationZip, String flowName,
			 String identifier, String cardType) {
		
		String deviceType = esn.getDeviceType();
		String esnStr = esn.getEsn();
		String simStr = esn.getSim();
		String min = esn.getMin();
		JsonObject airTimeCardObjid = null;
		JsonObject locationItemsobj;
		JsonObject orderItemsObj;
		JsonObject serviceItemObj;
		JsonObject serviceItemBuildObj;
		JsonArrayBuilder supportingResources = Json.createArrayBuilder();
		if(esnUtil.getCurrentBrand().equalsIgnoreCase("WFM")){
			simStr = "";
		}
		if (transType.equalsIgnoreCase("validate") && !pin.isEmpty()) {
			// pin = phoneUtil.getNewPinByPartNumber(pinPart);
			esn.setPin(pin);
			if (pin.equalsIgnoreCase("nopin")) {
				airTimeCardObjid = Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD").add("productIdentifier", "").build();
			} else {
				airTimeCardObjid = Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD")
						.add("productIdentifier", phoneUtil.getSNPfromRedCode(pin)).build();
			}
			supportingResources.add(airTimeCardObjid);
		} else if (transType.equalsIgnoreCase("submit") && !esn.getPin().isEmpty()) {
			pin = esn.getPin();
			if (pin.equalsIgnoreCase("nopin")) {
				airTimeCardObjid = Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD").add("productIdentifier", "").build();
			} else {
				airTimeCardObjid = Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD")
						.add("productIdentifier", phoneUtil.getSNPfromRedCode(pin)).build();
			}
			supportingResources.add(airTimeCardObjid);
		}else if (transType.equalsIgnoreCase("submit") && flowName.equalsIgnoreCase("ENROLLMENT")){
			
			
			
		}else {
		
			throw new IllegalArgumentException("Unsupported transType: " + transType);
		}
//		ESN esn = esnUtil.getCurrentESN();
		if (!activationZip.isEmpty()) {
			esn.setZipCode(activationZip);
		}

		if (deviceType.matches("BYO.*")) {
			serviceItemBuildObj = Json.createObjectBuilder().add("name", "VZW")
					.add("type", "WIRELESS").build();
			serviceItemObj = Json.createObjectBuilder().add("carrier", Json.createArrayBuilder().add(serviceItemBuildObj)).build();

		} else {
			serviceItemBuildObj = Json.createObjectBuilder().add("name", "carrierName").add("value", "").build();
		//	serviceItemBuildObj = Json.createObjectBuilder().add("name",phoneUtil.getEsnAttribute("brand", esn.getFromEsn().getEsn())).add("type", "WIRELESS").build();
			serviceItemObj = Json.createObjectBuilder().add("carrier", Json.createArrayBuilder().add(serviceItemBuildObj)).build();
		}

		locationItemsobj = Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", esn.getZipCode())).build();
		esnUtil.setFlowName(flowName);

		JsonObject simCardObjid = Json.createObjectBuilder().add("resourceType", "SIM_CARD").add("serialNumber", simStr).build();

		JsonObject partyExtensionobj1;
		JsonObject partyExtensionobj2;
		JsonObject partyExtensionobj3;

		if (deviceType.equalsIgnoreCase("BRANDED") || deviceType.equalsIgnoreCase("BYOP") || deviceType.equalsIgnoreCase("BYOT")
				|| deviceType.equalsIgnoreCase("LEASED")) {
			partyExtensionobj1 = Json.createObjectBuilder().add("name", "manufacturer").add("value", "").build();
			partyExtensionobj2 = Json.createObjectBuilder().add("name", "model").add("value", "").build();
			partyExtensionobj3 = Json.createObjectBuilder().add("name", "isCarrierPreloaded").add("value", "false").build();
		} else {
			partyExtensionobj1 = Json.createObjectBuilder().add("name", "manufacturer").add("value", "APPLE").build();
			partyExtensionobj2 = Json.createObjectBuilder().add("name", "model").add("value", esn.getFromMap("phoneModel")).build();
			partyExtensionobj3 = Json.createObjectBuilder().add("name", "isCarrierPreloaded").add("value", "Y").build();

		}
		JsonObject multiproductObjid = Json
				.createObjectBuilder()
				.add("productSerialNumber", esnStr)
				// phoneUtil.convertMeidDecToHex(esn)

				.add("productCategory", "HANDSET").add("subCategory", deviceType)
				.add("productSpecification", Json.createObjectBuilder().add("brand", esnUtil.getCurrentBrand()))
				.add("productCharacteristics", Json.createArrayBuilder().add(partyExtensionobj1).add(partyExtensionobj2).add(partyExtensionobj3))
				.add("supportingResources", supportingResources.add(simCardObjid)).build();

		JsonObject multiproductEnrollObjid = Json.createObjectBuilder().add("productSerialNumber", esnStr)
				// phoneUtil.convertMeidDecToHex(esn)
				.add("productCategory", "HANDSET").add("productSpecification", Json.createObjectBuilder().add("brand", esnUtil.getCurrentBrand()))
				.add("relatedServices", Json.createArrayBuilder().add(Json.createObjectBuilder().add("id", ""))).build();

		JsonObject orderItemExtensionobj = Json.createObjectBuilder().add("name", "groupIdentifier").add("value", identifier).build();

		JsonObject currentMinobj = Json.createObjectBuilder().add("name", "currentMIN").add("value", identifier).build();

		JsonObject contactMediumobj = Json.createObjectBuilder().add("city", "Mountain View").add("stateOrProvince", "CA")
				.add("street1", "1295 charleston road").add("street2", "").add("zipcode", "94043").build();
		JsonObject customerAccountContactobj = Json.createObjectBuilder()
				.add("contactMedium", Json.createArrayBuilder().add("contactDetails").add(contactMediumobj)).build();

		if ("ACTIVATION".equalsIgnoreCase(flowName) || "REACTIVATION".equalsIgnoreCase(flowName)) {
			if (identifier.isEmpty()) {
				orderItemsObj = Json.createObjectBuilder().add("action", esnUtil.getFlowName()).add("product", multiproductObjid)
						.add("location", locationItemsobj).add("service", serviceItemObj).add("id", count).build();

			} else if (identifier.equalsIgnoreCase(min)) {
				orderItemsObj = Json.createObjectBuilder().add("action", esnUtil.getFlowName()).add("product", multiproductObjid)
						.add("location", locationItemsobj).add("service", serviceItemObj)
						.add("orderItemExtension", supportingResources.add(orderItemExtensionobj)).add("id", count).build();
			} else {
				throw new IllegalArgumentException("Unsupport identifier that is not min: " + identifier);
			}
		} else if ("PHONE_UPGRADE".equalsIgnoreCase(flowName)) {
			orderItemsObj = Json.createObjectBuilder().add("action", esnUtil.getFlowName()).add("product", multiproductObjid)
					.add("location", locationItemsobj).add("service", serviceItemObj)
					.add("orderItemExtension", supportingResources.add(currentMinobj).add(orderItemExtensionobj)).add("id", count).build();
		} else if (flowName.equalsIgnoreCase("external_port")) {
			if (transType.equalsIgnoreCase("validate") && !pin.isEmpty()) {
				supportingResources.add(airTimeCardObjid);
				String extMin = TwistUtils.generateRandomMin();
				esn.setMin(extMin);

				String extCarrier[] = { "AT&T", "SPRINT", "VERIZON", "T-MOBILE" };
				int index = new Random().nextInt(extCarrier.length);
				String extCarrierValue = extCarrier[index];
				esnUtil.setExtCarrierName(extCarrierValue);
			}

			JsonObject orderItemExtensionobj1 = Json.createObjectBuilder().add("name", "currentMIN").add("value", esn.getMin())
					.build();
			JsonObject orderItemExtensionobj5 = Json.createObjectBuilder().add("name", "groupIdentifier").add("value", "").build();

			JsonObject orderItemExtensionobj3 = Json.createObjectBuilder().add("name", "currentServiceProvider")
					.add("value", esnUtil.getExtCarrierName()).build();
			JsonObject orderItemExtensionobj6 = Json.createObjectBuilder().add("name", "currentAccountSecret").add("value", "secret123").build();
			JsonObject orderItemExtensionobj4 = Json.createObjectBuilder().add("name", "currentAccountNumber").add("value", "856987").build();

			JsonObject custAccountExtObj = Json.createObjectBuilder().add("name", "last4DigitsOfSSN").add("value", "5246").build();

			orderItemsObj = Json
					.createObjectBuilder()
					.add("action", "PORT")
					.add("product", multiproductObjid)
					.add("location", locationItemsobj)
					.add("service", serviceItemObj)
					.add("orderItemExtension",
							Json.createArrayBuilder().add(orderItemExtensionobj1).add(orderItemExtensionobj3).add(orderItemExtensionobj4)
									.add(orderItemExtensionobj5).add(orderItemExtensionobj6))
					.add("customerAccount",
							Json.createObjectBuilder()
									.add("customerAccountContact",
											Json.createArrayBuilder().add(
													Json.createObjectBuilder().add(
															"contactMedium",
															Json.createObjectBuilder().add("contactDetails",
																	Json.createArrayBuilder().add(contactMediumobj)))))
									.add("customerAccountExtension", Json.createArrayBuilder().add(custAccountExtObj))).add("id", count).build();

		} else if (flowName.equalsIgnoreCase("internal_port")) {
			String fromEsn = esn.getFromEsn().getEsn();

			JsonObject orderItemExtensionobj1 = Json.createObjectBuilder().add("name", "currentMIN").add("value", esn.getFromEsn().getMin()).build();
			JsonObject orderItemExtensionobj3 = Json.createObjectBuilder().add("name", "currentServiceProvider")
					.add("value", phoneUtil.getEsnAttribute("brand", esn.getFromEsn().getEsn())).build();

			JsonObject orderItemExtensionobj2 = Json.createObjectBuilder().add("name", "handsetLast4Digits")
					.add("value", fromEsn.substring(fromEsn.length() - 4)).build();
			JsonObject orderItemExtensionobj5 = Json.createObjectBuilder().add("name", "groupIdentifier").add("value", "").build();
			orderItemsObj = Json
					.createObjectBuilder()
					.add("action", "PORT")
					.add("product", multiproductObjid)
					.add("location", locationItemsobj)
					.add("service", serviceItemObj)
					.add("orderItemExtension",
							Json.createArrayBuilder().add(orderItemExtensionobj1).add(orderItemExtensionobj2).add(orderItemExtensionobj3)
									.add(orderItemExtensionobj5))
									

					.add("id", count).build();
		} else if (flowName.equalsIgnoreCase("ENROLLMENT")) {

			

			orderItemsObj = Json.createObjectBuilder().add("action", esnUtil.getFlowName())
													  .add("product", multiproductEnrollObjid).add("relatedServices", Json.createArrayBuilder().add(Json.createObjectBuilder().add("id", "")))
													  .add("id", count)
													  .build()
													  ;
		} else {
			throw new IllegalArgumentException("Unsupport flow: " + flowName);
		}

		return orderItemsObj;
	}

	public void CheckByopCoverage(String carrier, String zipCode, String brand, String esn) {
		try {
			client = Client.create();
			client.addFilter(new LoggingFilter(System.out));
			String partyId;
			if(esnUtil.getSourceSystem().equalsIgnoreCase("API")){
				partyId="TCETRA";
			}else{
				partyId= "WALMART";
			}
			WebResource webResource = client
					.resource(
							props.getString(props.getString("ENV.") + "END_POINT")
									+ props.getString(props.getString("ENV.") + "WARP_SERVICE_ELIGIBILITY_RESOURCE"))
					.queryParam("partyTransactionID", "123").queryParam("partyID", partyId).queryParam("brand", brand)
					.queryParam("sourceSystem", "WARP").queryParam("lang", "ENG").queryParam("productSerialNumber", esn)
					.queryParam("zipCode", zipCode).queryParam("carrier", carrier)
					.queryParam("client_id", props.getString(props.getString("ENV.") + esnUtil.getSourceSystem()+"_CLIENT_ID"));

			String token = genAuthfor(esnUtil.getSourceSystem(), "service management");
			JSONObject jsonRes = callGetService(webResource, "REST-"+esnUtil.getSourceSystem()+"_BYOP Eligibility Check", token);

			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void getCustomerMgmtDetails(String namedUserID) {
		try {
			client = Client.create();
			// client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
									+ props.getString(props.getString("ENV.") + "LRP_CUST_MGMT_RESOURCE")).queryParam("partyID", "AUGEO")
					.queryParam("fileds", "")
					// .queryParam("customerID", namedUserID)
					.queryParam("client_id", props.getString(props.getString("ENV.") + "LRP_CLIENT_ID"));

			String token = genAccessTokenUsingAuthCode("LRP", "", generateAuthCode());
			JSONObject jsonRes= callGetService(webResource, "REST-LRP_Customer Mgmt", token);
			
			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			Assert.assertTrue(responseMessage, responseMessage.equalsIgnoreCase("SUCCESS"));

			JSONArray customerAccountBalanceRes = jsonRes.getJSONArray("customerAccountBalance");
			logger.info("customer Account Balance Details");
			for (int i = 0; i < customerAccountBalanceRes.length(); i++) {
				JSONObject jsonobject = customerAccountBalanceRes.getJSONObject(i);
				logger.info(jsonobject.getString("status") + ":" + jsonobject.getInt("amount"));

			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void getReportManagementDetails(String namedUserID) {
		try {
			client = Client.create();
			client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
									+ props.getString(props.getString("ENV.") + "LRP_REPORT_MGMT_RESOURCE")).queryParam("partyID", "AUGEO")
					.queryParam("type", "LOYALTY")
					// .queryParam("customerID",namedUserID)
					.queryParam("client_id", props.getString(props.getString("ENV.") + "LRP_CLIENT_ID"));

			String token = genAccessTokenUsingAuthCode("LRP", "", generateAuthCode());
			JSONObject jsonRes = callGetService(webResource, "REST-LRP_Report Mgmt", token);

			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			if (responseMessage.equalsIgnoreCase("SUCCESS")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(myResponse.getString("message").toUpperCase(), false);
			}

			JSONArray resbody = jsonRes.getJSONArray("body");

			for (int i = 0; i < resbody.length(); i++) {
				JSONObject jsonobject = resbody.getJSONObject(i);
				logger.info(jsonobject.getString("name") + ":" + jsonobject.getString("type") + ":" + jsonobject.getString("value") + " points "
						+ ": on " + jsonobject.getString("transDate"));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void getUsageManagementDetails(String namedUserID, String transaction_type,String category, String subCategory, String benefitsEarned) {
		try {
			ArrayList<Object> benefitDetails = phoneUtil.getRewardBenefitDetails(transaction_type,category, subCategory, benefitsEarned);
			client = Client.create();
			WebResource webResource = client
					.resource(
							props.getString((props.getString("ENV.") + "END_POINT"))
									+ props.getString((props.getString("ENV.") + "LRP_USAGE_MGMT_RESOURCE"))).queryParam("client_id",
							props.getString(props.getString("ENV.") + "LRP_CLIENT_ID"));
			logger.info(webResource.getURI());
			JsonObject characteristicObj1 = Json.createObjectBuilder().add("name", "customerId").add("value", namedUserID).build();

			JsonObject characteristicObj2 = Json.createObjectBuilder().add("name", "LOYALTY_POINTS").add("value", benefitDetails.get(2).toString())
					.build();

			JsonObject eventDetailsObj = Json.createObjectBuilder().add("completionTime", dateFormat.format(cal.getTime()))
					.add("description", benefitDetails.get(5).toString()).add("id", benefitDetails.get(0).toString())
					.add("name", benefitDetails.get(3).toString()).add("status", "COMPLETED").add("type", benefitDetails.get(1).toString())
					.add("characteristic", Json.createArrayBuilder().add(characteristicObj1).add(characteristicObj2)).build();

			JsonObject input = Json.createObjectBuilder().add("id", "ITQ999").add("source", "AUGEO").add("time", dateFormat.format(cal.getTime()))
					.add("type", "LoyaltyOfferNotification").add("event", Json.createArrayBuilder().add(eventDetailsObj)).build();
			
			String token = genAccessTokenUsingAuthCode("LRP", "", generateAuthCode());
			JSONObject jsonRes = callPostService(webResource, input, "REST-LRP_Usage Mgmt", token);
			
			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			if (responseMessage.equalsIgnoreCase("SUCCESS")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(myResponse.getString("message").toUpperCase(), false);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}

	}

	public void getCatalogManagementDetails() {
		try {
			client = Client.create();
			// client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
									+ props.getString(props.getString("ENV.") + "LRP_CATALOG_MGMT_RESOURCE")).queryParam("partyID", "AUGEO")
					.queryParam("type", "LOYALTY_EXT").queryParam("client_id", props.getString(props.getString("ENV.") + "LRP_CLIENT_ID"));

			String token = genAccessTokenUsingAuthCode("LRP", "order management", generateAuthCode());
			JSONObject jsonRes = callGetService(webResource, "REST-LRP_Catalog Mgmt", token);

			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			Assert.assertTrue(responseMessage, responseMessage.equalsIgnoreCase("SUCCESS"));
	
			JSONArray productOfferingArray = jsonRes.getJSONArray("productOffering");
			for (int i = 0; i < productOfferingArray.length(); i++) {
				JSONArray characteristicArray = (JSONArray) productOfferingArray.getJSONObject(i).get("characteristic");
				logger.info(productOfferingArray.getJSONObject(i).get("name"));
				logger.info("\n");
				logger.info(characteristicArray);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void submitProductOrder(String namedUserID, String category, String subCategory, String benefitsEarned) {
		try {
			ArrayList<Object> benefitDetails = phoneUtil.getRewardBenefitDetails("",category, subCategory, benefitsEarned);
			client = Client.create();
			WebResource webResource = client.resource(
					props.getString(props.getString("ENV.") + "END_POINT") + props.getString(props.getString("ENV.") + "LRP_PRODUCT_ORDER_RESOURCE"))
					.queryParam("client_id", props.getString(props.getString("ENV.") + "LRP_CLIENT_ID"));
			client.addFilter(new LoggingFilter(System.out));
			logger.info(webResource.getURI());
			JsonObject PriceObj = Json.createObjectBuilder()
					.add("price", Json.createObjectBuilder().add("amount", (Integer) benefitDetails.get(2)).add("currencyCode", "LOYALTY_POINTS"))
					.build();

			JsonObject productOfferingObj = Json.createObjectBuilder().add("description", benefitDetails.get(5).toString())
					.add("id", benefitDetails.get(0).toString()).add("productCategory", "LOYALTY_EXT").add("name", benefitDetails.get(1).toString())
					.add("productSpecification", Json.createArrayBuilder().add(Json.createObjectBuilder().add("brand", esnUtil.getCurrentBrand())))
					.build();

			JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231").build();

			JsonObject relatedPartiesobj1 = Json
					.createObjectBuilder()
					.add("party",
							Json.createObjectBuilder().add("partyID", "AUGEO")
									.add("partyExtension", Json.createArrayBuilder().add(partyExtensionobj1))).add("roleType", "partner").build();

			JsonObject relatedPartiesobj2 = Json.createObjectBuilder().add("party", Json.createObjectBuilder().add("partyID", namedUserID))
					.add("roleType", "customer").build();

			JsonObject orderPriceObj = Json.createObjectBuilder()
					.add("price", Json.createObjectBuilder().add("amount", (Integer) benefitDetails.get(2)).add("currencyCode", "LOYALTY_POINTS"))
					.add("quantity", "1").build();

			JsonObject specialObjid = Json.createObjectBuilder().add("orderItemPrice", PriceObj).add("productOffering", productOfferingObj)
					.add("action", "ADD").add("quantity", "1").build();

			JsonObject input = Json.createObjectBuilder().add("type", "INTANGIBLE").add("externalID", "ITQ999")
					.add("orderDate", dateFormat.format(cal.getTime())).add("category", "LOYALTY_EXT")
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartiesobj1).add(relatedPartiesobj2))
					.add("orderItems", Json.createArrayBuilder().add(specialObjid)).add("orderPrice", orderPriceObj).build();
			logger.info(input);

			String token = genAccessTokenUsingAuthCode("LRP", "order management", generateAuthCode());
			JSONObject jsonRes = callPostService(webResource, input, "REST-LRP_Submit Product Order", token);
			JSONObject myResponse = jsonRes.getJSONObject("status");
			String responseMessage = myResponse.getString("message").toUpperCase();
			
			Assert.assertTrue(responseMessage, responseMessage.equalsIgnoreCase("SUCCESS"));
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.assertFalse(false);
		}

	}

	public String encryptcc(String cardType) {
		ClientResponse response = null;
		String encryptText = null;
		try {
			String cc = TwistUtils.generateCreditCard(cardType);
			client = Client.create();
			String env=props.getString("ENV");
			String resource;
			if (env.equalsIgnoreCase("TST")){
				resource = "http://sit1esbgateway.tracfone.com/encryptWARP/tst" ;
			}else{
				resource = "http://sit1esbgateway.tracfone.com/encryptWARP" ;
			}
			WebResource webResource = client.resource(resource);
			client.addFilter(new LoggingFilter(System.out));

			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_XML);
			builder.type(MediaType.APPLICATION_XML);

			String input1 = "<API><WARP><Text>%B" + cc
					+ "^TFNAME/TLNAME^20042010000000000000000000000000000567001000?;6011688827932555=16042010000056700100?</Text></WARP></API>";
			logger.info(input1);
			response = builder.post(ClientResponse.class, input1);

			if (response.getStatus() != 200) {
				logger.info(response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String resonseText = response.getEntity(String.class);
			encryptText = parseXml(resonseText, "API/WARP/EncryptedText").get(0);
			return encryptText;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public Hashtable<String, String> CreateCustomer(String partnerID, Hashtable<String, Object> offering){
		Hashtable<String, String> account = new Hashtable<String, String>();
		String emailStr = TwistUtils.createRandomEmail();
		System.out.println("Email: " + emailStr);
		String orgName = "TwistORGName-" + TwistUtils.createRandomUserId();
		System.out.println("ORG Name: " + orgName);
		account.put("orgName", orgName);
		account.put("email", emailStr);
		
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "GOAPI_END_POINT")
								+ props.getString(props.getString("ENV.")+"GOAPI_CUSTOMER_MANAGEMENT_RESOURCE"))
				.queryParam("client_id",props.getString(props.getString("ENV.")	+ "GOAPI_CLIENT_ID"));
		
		JsonObject partyExtensionobj1 = Json.createObjectBuilder()
				.add("name","partySignature")
				.add("value", (String) offering.get("partySignature"))
				.build();
		
		JsonObject partyExtensionobj2 = Json.createObjectBuilder()
				.add("name","sourceSystem")
				.add("value", "API")
				.build();
		
		JsonObject partyExtensionobj3 = Json.createObjectBuilder()
				.add("name","channel")
				.add("value", "B2B")
				.build();
		
		JsonObject partyExtensionobj4 = Json.createObjectBuilder()
				.add("name","BrandName")
				.add("value", "NET10")
				.build();
		
		JsonObject partyExtensionobj5 = Json.createObjectBuilder()
				.add("name","referralCode")
				.add("value", "LEAPABC012359")
				.build();
		
		JsonObject relatedPartiesobj1 = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("organization", Json.createObjectBuilder()
								.add("organizationName",orgName)))
								
				.add("roleType","buyerOrg")
				.build();
		
		JsonObject relatedPartiesobj2 = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyID",(String) offering.get("partyID"))
						.add("individual", Json.createObjectBuilder()
								.add("firstName","TwistFirstName")
								.add("lastName","TwistLastName"))
						.add("partyExtension",Json.createArrayBuilder().add(partyExtensionobj1)))
				.add("roleType","buyerAdmin")
				.build();
		
		JsonObject relatedPartiesobj3 = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyExtension",Json.createArrayBuilder().add(partyExtensionobj2).add(partyExtensionobj3).add(partyExtensionobj4).add(partyExtensionobj5))
						.add("partyID", partnerID))
				.add("roleType","partner")
				.build();
		
		JsonObject customerAccountExtensionobj1 = Json.createObjectBuilder()
				.add("name","PASSWORD")
				.add("value","123456a")
				.build();
		
		JsonObject customerAccountExtensionobj2 = Json.createObjectBuilder()
				.add("name","What was the name of your first boyfriend/girlfriend?")
				.add("type","SECURITYQUESTION")
				.add("value","John")
				.build();
		
		JsonObject customerAccountExtensionobj3 = Json.createObjectBuilder()
				.add("name","username")
				.add("value",emailStr)
				.build();
		
		JsonObject customerAccountsobj = Json.createObjectBuilder()
				.add("Brand","NET10")
				.add("customerAccountExtension", Json.createArrayBuilder()
						.add(customerAccountExtensionobj1)
						.add(customerAccountExtensionobj2)
						.add(customerAccountExtensionobj3))
				.build();
		
		JsonObject contactDetailsobj = Json.createObjectBuilder()
				.add("city","Miami")
				.add("country","USA")
				.add("emailAddress",emailStr)
				.add("stateOrProvince","FL")
				.add("street1","9700 nw 112th ave")
				.add("street2","Unit 106")
				.add("postcode","33178")
				.build();
		
		JsonObject contactMediumobj = Json.createObjectBuilder()
				.add("contactDetails", Json.createArrayBuilder()
						.add(contactDetailsobj))
				.add("telephoneNumber",Json.createArrayBuilder()
						.add("3053053051"))
				.build();
		
		JsonObject input = Json.createObjectBuilder()
				.add("relatedParties",Json.createArrayBuilder()
						.add(relatedPartiesobj1)
						.add(relatedPartiesobj2)
						.add(relatedPartiesobj3))
				.add("customerAccounts",Json.createArrayBuilder().add(customerAccountsobj))
				.add("contactMedium",Json.createArrayBuilder().add(contactMediumobj))
				.build();
			
		String token = genAuthfor("GOAPI", "customer management");
		JSONObject json = callPostService(webResource, input, "GOAPI - CreateAccount", token);
		//TODO: This is always empty
		return account;
	}
	public Hashtable<String, Object> estimateOrder(String partnerID, Hashtable<String, Object> offering,String cellTech) {
		String orderID = null;
		
		try {
			Date javaUtilDate= new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client
					.resource(props.getString(props.getString("ENV.") + "GOAPI_END_POINT")
									+ props.getString(props.getString("ENV.")+"GOAPI_ORDER_MANAGEMENT_RESOURCE")+"/estimate")
					.queryParam("client_id",props.getString(props.getString("ENV.")	+ "GOAPI_CLIENT_ID"));
			
			JsonObjectBuilder productOfferingSim = Json.createObjectBuilder();
			
			if(!cellTech.equalsIgnoreCase("CDMA")){
				productOfferingSim.add("id",(String) offering.get("simID"))
						  .add("productSpecification", Json.createArrayBuilder()
								  .add(Json.createObjectBuilder()
										.add( "id",(String) offering.get("simPart"))
										.add("brand", "NET10")));
			if(cellTech.equalsIgnoreCase("CDMA LTE")){
				productOfferingSim.add("supportingResources", Json.createArrayBuilder()
						  .add(Json.createObjectBuilder()
								.add("resourceType", "ESN")
								.add("serialNumber", esnUtil.getCurrentESN().getEsn())));
			}
			}
			
			JsonObjectBuilder productOfferingPlan = Json.createObjectBuilder();
			
			productOfferingPlan.add("id",(String) offering.get("planID"))
						  .add("productSpecification", Json.createArrayBuilder()
								  .add(Json.createObjectBuilder()
										.add( "id",(String) offering.get("planPart"))
										.add("brand", "NET10")));
			if(!cellTech.equalsIgnoreCase("GSM")){
				productOfferingPlan.add("supportingResources", Json.createArrayBuilder()
						  .add(Json.createObjectBuilder()
								.add("resourceType", "ESN")
								.add("serialNumber", esnUtil.getCurrentESN().getEsn())));
			}
			
			JsonObjectBuilder orderItemsobj1 = Json.createObjectBuilder();
			if(!cellTech.equalsIgnoreCase("CDMA")){
			orderItemsobj1.add("action","ESTIMATE")
						.add("id","1")
						.add("relatedOrderItem",Json.createObjectBuilder()
								.add("relationshipType", "relatedTo")
								.add("characteristicSpecification",Json.createObjectBuilder()
										.add("name","orderItemId")
										.add("value",(String) offering.get("planID"))))
						.add("orderItemPrice",Json.createObjectBuilder()
								.add("price", Json.createObjectBuilder()
										.add("amount",(Float) offering.get("simPrice"))
										.add("currencyCode","USD")))
						.add("productOffering",productOfferingSim)
						.add("quantity","1");
			}
			
			JsonObjectBuilder orderItemsobj2 = Json.createObjectBuilder();
			orderItemsobj2.add("action","ESTIMATE")
						.add("orderItemPrice",Json.createObjectBuilder()
								.add("price", Json.createObjectBuilder()
									.add("amount",(Integer) offering.get("planPrice"))
									.add("currencyCode","USD")))
						.add("productOffering",productOfferingPlan)
						.add("CharacteristicSpecification",Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
										.add("name","AUTOREFILL")
										.add("value","TRUE")))
						.add("quantity","1");
			
			if(cellTech.equalsIgnoreCase("CDMA")){
				orderItemsobj2.add("id","1");
			}else{
				orderItemsobj2.add("id","2");
			}
			
			JsonArrayBuilder orderItemsArrayBuilder = Json.createArrayBuilder();
			if(!cellTech.equalsIgnoreCase("CDMA")){
				orderItemsArrayBuilder.add(orderItemsobj1);
				orderItemsArrayBuilder.add(orderItemsobj2);
				
			}else{
				orderItemsArrayBuilder.add(orderItemsobj2);
			}
			JsonArray orderItemsArray = orderItemsArrayBuilder.build();
			
			JsonObject input = Json.createObjectBuilder()
					.add("relatedParties",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
							.add("party", Json.createObjectBuilder()
									.add("partyID",(String) offering.get("partyID"))
									.add("partyExtension",Json.createArrayBuilder()
											.add(Json.createObjectBuilder()
											.add("name","partySignature")
											.add("value",(String) offering.get("partySignature")))))
							.add("roleType","buyerAdmin"))
							.add(Json.createObjectBuilder()
									.add("party",Json.createObjectBuilder()
											.add("partyID", partnerID)
											.add("partyExtension",Json.createArrayBuilder()
													.add(Json.createObjectBuilder()
															.add("name","BrandName")
															.add("value", "NET10"))
													.add(Json.createObjectBuilder()
															.add("name","Channel")
															.add("value", "B2B"))))
													.add("roleType","partner")))
					.add("externalID","123")
					.add("orderDate",formatter.format(javaUtilDate))
					.add("characteristicSpecifications",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("type","shipmentMode")
								.add("value","3 DAY SHIPPING")))
					.add("orderItems",orderItemsArray)
					.add("productPromotions",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("id","TESTPROMO")))
					.add("location",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("addressType","SHIPPING")
								.add("address",Json.createObjectBuilder()
										.add("zipcode","33178")))
					.add(Json.createObjectBuilder()
								.add("addressType","BILLING")
								.add("address",Json.createObjectBuilder()
										.add("zipcode","33178"))))
					.build();
			
			String token = genAuthfor("GOAPI", "order management");
			
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

			logger.info(webResource.getURI());
			logger.info(input);
			
			lStartTime = System.currentTimeMillis();
			ClientResponse response= builder.post(ClientResponse.class, input.toString());
			lEndTime = System.currentTimeMillis();
			difference = lEndTime - lStartTime;		
			logger.info("Elapsed milliseconds: " + difference);
			
			String responseString = response.getEntity(String.class);
			StringBuffer responseBuffer = new StringBuffer(responseString);
			String requestString = input.toString();
			
			phoneUtil.insertIntoServiceResultsTable("GOAPI - EstimateOrder", requestString, responseBuffer, String.valueOf(difference), webResource.getURI().toString());

			if (response.getStatus() != 200 && response.getStatus() != 202 ) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Failed : HTTP error code :" + response.getStatus());
			}

			
			JSONObject jsonRes = new JSONObject(responseString);

			orderID = jsonRes.getString("id");
			float orderTotal = BigDecimal.valueOf(jsonRes.getJSONObject("orderPrice").getDouble("totalAmountWithDiscountAndTax")).floatValue();
			float simTotal = BigDecimal.valueOf(jsonRes.getJSONArray("orderItems").getJSONObject(0).getJSONObject("orderItemPrice").getDouble("totalAmountWithDiscountAndTax")).floatValue();
			float planTotal = BigDecimal.valueOf(jsonRes.getJSONArray("orderItems").getJSONObject(1).getJSONObject("orderItemPrice").getDouble("totalAmountWithDiscountAndTax")).floatValue();
			String estimateSimId = jsonRes.getJSONArray("orderItems").getJSONObject(0).getString("id");
			String estimatePlanId = jsonRes.getJSONArray("orderItems").getJSONObject(1).getString("id");

			offering.put("orderID", orderID);
			offering.put("orderTotal", orderTotal);
			offering.put("simTotal", simTotal);
			offering.put("planTotal", planTotal);
			offering.put("estimateSimId", estimateSimId);
			offering.put("estimatePlanId", estimatePlanId);

		} catch (Exception e) {
			e.printStackTrace();
			if(orderID.isEmpty() || orderID.equalsIgnoreCase(null)){
				throw new RuntimeException(("Order ID is :" +orderID));
			}
		}
		return offering;
	}
	public String checkEligibility(String partnerID, String carrier, String cellTech, String zipCode, String phoneModel) {
		String isLTE = "NO";
		String isIphone = "NO";
		String esn = phoneUtil.getNewByopCDMAEsn();
		
		if(cellTech.equalsIgnoreCase("CDMA LTE")){
			isLTE="YES";
		}
		
		if(phoneModel.equalsIgnoreCase("APL")){
			isIphone="YES";
		}
		
		try{
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));
			
			WebResource webResource= client
						.resource(props.getString(props.getString("ENV.")+"GOAPI_END_POINT")
										+ props.getString(props.getString("ENV.")+"GOAPI_SERVICE_QUALIFICTION_RESOURCE"))
						.queryParam("client_id", props.getString(props.getString("ENV.")+"GOAPI_CLIENT_ID"));
			
			
			JsonObject input = Json.createObjectBuilder()
					.add("relatedParties",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("party", Json.createObjectBuilder()
									.add("partyID", partnerID)
									.add("languageAbility", "ENG")
									.add("partyExtension",Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
											.add("name", "partyTransactionID")
											.add("value", "1231231234"))
										.add(Json.createObjectBuilder()
											.add("name", "sourceSystem")
											.add("value", "APP"))))
							    .add("roleType", "application")))
					.add("location",Json.createObjectBuilder()
							.add("postalAddress", Json.createObjectBuilder()
								.add("zipcode", zipCode)))
					.add("serviceCategory", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add( "type", "context")
								.add("value", "BYOP_ELIGIBILITY")))
					.add("serviceSpecification",Json.createObjectBuilder()
							.add("brand", "NET10"))
					.add("service", Json.createObjectBuilder()
							.add("carrier", Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
									.add( "name", "carrierName")
									.add("value", carrier))))
					.add("relatedResources", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("serialNumber", esn)
								.add( "name", "productSerialNumber")
								.add("type", "HANDSET")))
					.build();
			String token = genAuthfor("GOAPI", "service-qualification");
			callPostService(webResource, input, "GOAPI - Service Qualification", token);
			
			int edited = 0;
			TwistUtils.setDelay(3);
			for (int i = 0; i < 10 && edited == 0; i++) {
				TwistUtils.setDelay(3+i);
				edited = phoneUtil.finishCdmaByopIgate(esn, carrier, "InActive", isLTE, isIphone, "YES");
				System.out.println("edit: " + edited);
			}
			TwistUtils.setDelay(5);

			JSONObject jsonRes = callPostService(webResource, input, "GOAPI - Service Qualification", token);
			
			String responseString  = jsonRes.toString();
			StringBuffer responseBuffer = new StringBuffer(responseString); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return esn;
	}
	public void registerBYOP(String partnerID, String zipCode, String esn, String carrier, String cellTech, String sim) {
		
		try {
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client
					.resource(props.getString(props.getString("ENV.") + "GOAPI_END_POINT")
									+ props.getString(props.getString("ENV.")+"GOAPI_RESOURCE_MANAGEMENT_RESOURCE"))
					.queryParam("client_id",props.getString(props.getString("ENV.")	+ "GOAPI_CLIENT_ID"));
			
			JsonObjectBuilder physicalResourceObj = Json.createObjectBuilder();
			
			physicalResourceObj.add("resourceCategory","HANDSET")
								.add("resourceSubCategory", "BYOP")
								.add("serialNumber",esn);
						  
			if(cellTech.equalsIgnoreCase("CDMA LTE") & (sim.isEmpty() || sim.equalsIgnoreCase(""))){
				physicalResourceObj.add("supportingResources", Json.createArrayBuilder()
											.add(Json.createObjectBuilder()
													.add("resourceCategory","SIM_SIZE")
													.add("resourceIdentifier", "NANO")));
			}else{
				physicalResourceObj.add("supportingResources", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("resourceCategory","SIM_CARD")
								.add("resourceIdentifier", sim)));
			}
				
			JsonObject input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("party",Json.createObjectBuilder()
									.add("partyID", partnerID)
									.add("languageAbility", "ENG")
									.add("partyExtension", Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
											.add("name", "partyTransactionID")
											.add("value", "1231231234"))
										.add(Json.createObjectBuilder()
											.add("name", "sourceSystem")
											.add("value", "WEB"))))
						.add("roleType", "partner")))
						.add("resource", Json.createObjectBuilder()
								.add("location", Json.createObjectBuilder()
										.add("postalAddress", Json.createObjectBuilder()
												.add("zipcode", zipCode)))
								.add("association", Json.createObjectBuilder()
										.add("role", "REGISTER"))
								.add("resourceSpecification", Json.createObjectBuilder()
										.add("brand", "NET10"))
								.add("physicalResource",physicalResourceObj)
								.add("supportingLogicalResources", Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
												.add("resourceCategory","CARRIER")
												.add("resourceIdentifier",carrier))))
					.build();
			
			String token = genAuthfor("GOAPI", "resource management");
			JSONObject jsonRes = callPostService(webResource, input, "GOAPI - BYOP Registration", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void checkOrderStatus(String orderId, String emailStr) {
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource= client
					.resource(props.getString(props.getString("ENV.")+"GOAPI_END_POINT")
									+ props.getString(props.getString("ENV.")+"GOAPI_ORDER_MANAGEMENT_RESOURCE") + "/status")
					.queryParam("orderId", orderId)
					.queryParam("emailAddress", emailStr)
					.queryParam("client_id", props.getString(props.getString("ENV.")+"GOAPI_CLIENT_ID"));
		
		String token = genAuthfor("GOAPI", "order management");
		JSONObject jsonRes = callGetService(webResource, "GOAPI - Order Status", token);

	}
	public void checkNAPValidation(String partnerID, String zipCode, String esn, String sim) {
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource= client
					.resource(props.getString(props.getString("ENV.")+"GOAPI_END_POINT")
									+ props.getString(props.getString("ENV.")+"GOAPI_SERVICE_QUALIFICTION_RESOURCE"))
					.queryParam("client_id", props.getString(props.getString("ENV.")+"GOAPI_CLIENT_ID"));
		
		JsonObject input = Json.createObjectBuilder()
				.add("relatedParties",Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("party", Json.createObjectBuilder()
								.add("partyID", partnerID)
								.add("languageAbility", "ENG")
								.add("partyExtension",Json.createArrayBuilder()
									.add(Json.createObjectBuilder()
										.add("name", "partyTransactionID")
										.add("value", "1231231234"))
									.add(Json.createObjectBuilder()
										.add("name", "sourceSystem")
										.add("value", "APP"))))
						    .add("roleType", "application")))
				.add("location",Json.createObjectBuilder()
						.add("postalAddress", Json.createObjectBuilder()
							.add("zipcode", zipCode)))
				.add("serviceCategory", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add( "type", "context")
							.add("value", "TECHNOLOGY")))
				.add("serviceSpecification",Json.createObjectBuilder()
						.add("brand", "NET10"))
				.add("relatedResources", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("serialNumber", esn)
							.add( "name", "productSerialNumber")
							.add("type", "HANDSET"))
						.add(Json.createObjectBuilder()
							.add("serialNumber", sim)
							.add( "name", "serialNumber")
							.add("type", "SIM_CARD")))
				.build();
		String token = genAuthfor("GOAPI", "service-qualification");
		callPostService(webResource, input, "GOAPI - NAP Validation", token);
	}
	
	public void submitOrder(String partnerID, Hashtable<String, Object> offering,String cellTech, String emailStr, String orgName ) {
		Hashtable<String, String> cc = encryptPayment("VISA");
		Date javaUtilDate= new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "GOAPI_END_POINT")
								+ props.getString(props.getString("ENV.")+"GOAPI_ORDER_MANAGEMENT_RESOURCE"))
				.queryParam("client_id",props.getString(props.getString("ENV.")	+ "GOAPI_CLIENT_ID"));
		
		JsonObjectBuilder productOfferingSim = Json.createObjectBuilder();
		
		if(!cellTech.equalsIgnoreCase("CDMA")){
			productOfferingSim.add("id",(String) offering.get("simID"))
						.add("category", "SIM")
						.add("productSpecification", Json.createObjectBuilder()
									.add( "id",(String) offering.get("simPart"))
									.add("brand", "NET10"));
		if(cellTech.equalsIgnoreCase("CDMA LTE")){
			productOfferingSim.add("supportingResources", Json.createArrayBuilder()
					  	.add(Json.createObjectBuilder()
					  		.add("resourceType", "ESN")
							.add("serialNumber", esnUtil.getCurrentESN().getEsn())));
		}
		}
		
		JsonObjectBuilder productOfferingPlan = Json.createObjectBuilder();
		
		productOfferingPlan.add("id",(String) offering.get("planID"))
						.add("category", "PLAN")
						.add("productSpecification", Json.createObjectBuilder()
								.add( "id",(String) offering.get("planPart"))
								.add("brand", "NET10"));
		if(!cellTech.equalsIgnoreCase("GSM")){
			productOfferingPlan.add("supportingResources", Json.createArrayBuilder()
					  .add(Json.createObjectBuilder()
							.add("resourceType", "ESN")
							.add("serialNumber", esnUtil.getCurrentESN().getEsn())));
		}
		
		JsonObjectBuilder orderItemsobj1 = Json.createObjectBuilder();
		if(!cellTech.equalsIgnoreCase("CDMA")){
		orderItemsobj1.add("action","NEW")
					.add("id",(String) offering.get("estimateSimId"))
					.add("relatedOrderItem",Json.createObjectBuilder()
							.add("relationshipType", "relatedTo")
							.add("characteristicSpecification",Json.createObjectBuilder()
									.add("name","orderItemId")
									.add("value",(String) offering.get("planID"))))
					.add("orderItemPrice",Json.createObjectBuilder()
							.add("quantity", "1")
							.add("price", Json.createObjectBuilder()
									.add("amount",(Float) offering.get("simTotal"))
									.add("currencyCode","USD")))
					.add("productOffering",productOfferingSim)
					.add("CharacteristicSpecification",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
								.add("name", "TYPE")
								.add("value", "DEVICE"))
							.add(Json.createObjectBuilder()
								.add("name", "FULFILLMENT_TYPE")
								.add("value", "NOW")))
					.add("quantity","1");
		}
		
		JsonObjectBuilder orderItemsobj2 = Json.createObjectBuilder();
		orderItemsobj2.add("action","NEW")
					.add("id",(String) offering.get("estimatePlanId"))
					.add("orderItemPrice",Json.createObjectBuilder()
							.add("quantity", "1")
							.add("price", Json.createObjectBuilder()
								.add("amount",(Float) offering.get("planTotal"))
								.add("currencyCode","USD")))
					.add("productOffering",productOfferingPlan)
					.add("CharacteristicSpecification",Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("name","FULFILLMENT_TYPE")
									.add("value","NOW"))
							.add(Json.createObjectBuilder()
								.add("name", "TYPE")
								.add("value", "PLAN")))
					.add("quantity","1");
		
		
		JsonArrayBuilder orderItemsArrayBuilder = Json.createArrayBuilder();
		if(!cellTech.equalsIgnoreCase("CDMA")){
			orderItemsArrayBuilder.add(orderItemsobj1);
			orderItemsArrayBuilder.add(orderItemsobj2);
		}else{
			orderItemsArrayBuilder.add(orderItemsobj2);
		}
		JsonArray orderItemsArray = orderItemsArrayBuilder.build();
		
		JsonObject input = Json.createObjectBuilder()
				.add("relatedParties",Json.createArrayBuilder()
					.add(Json.createObjectBuilder()
						.add("party", Json.createObjectBuilder()
							.add("organization", Json.createObjectBuilder()
								.add("organizationName", orgName))
							.add("partyExtension",Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
									.add("name", "accountEmail")
									.add("value", emailStr))))
						.add("roleType", "buyerOrg"))
					.add(Json.createObjectBuilder()
						.add("party", Json.createObjectBuilder()
							.add("partyID",(String) offering.get("partyID"))
							.add("individual", Json.createObjectBuilder()
								.add("firstName", "TwistFirstName")
								.add("lastName", "TwistLastName"))
							.add("contactMedium", Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
									.add("contactDetails", Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
											.add("number", "3053053051")))
									.add("type", "telephoneNumber"))
								.add(Json.createObjectBuilder()
									.add("contactDetails", Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
											.add("emailAddress", emailStr)))
									.add("type", "email")))
							.add("partyExtension",Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
									.add("name", "partySignature")
									.add("value", (String) offering.get("partySignature")))))
						.add("roleType", "buyerAdmin"))
				    .add(Json.createObjectBuilder()
						.add("party", Json.createObjectBuilder()
							.add("partyID", partnerID)
							.add("partyExtension",Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
										.add( "name", "BrandName")
										.add( "value", "NET10"))
								.add(Json.createObjectBuilder()
										.add("name", "Channel")
										.add("value", "B2B"))))
						.add("roleType", "partner")))
				.add("id", (String) offering.get("orderID"))
				.add("externalID", "1231231239")
				.add("orderDate",formatter.format(javaUtilDate))
				.add("characteristicSpecifications",Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("type", "shipmentMode")
								.add("value", "3 DAY SHIPPING")))
				.add("billingAccount", Json.createObjectBuilder()
						.add("paymentPlan", Json.createObjectBuilder()
								.add("paymentMean", Json.createArrayBuilder()
										.add(Json.createObjectBuilder()
												.add( "nickname", "MYCC")
												.add("isRecurring", "Y")
												.add("isExisting", "FALSE")
												.add("billingAddress", Json.createObjectBuilder()
														.add("addressLine1", "9700 NW 112th AVE")
														.add("addressLine2", "S25-3")
														.add("city", "Miami")
														.add("country", "USA")
														.add( "stateOrProvince", "FL")
														.add("zipcode", "33178"))
												.add("creditCard", Json.createObjectBuilder()
														.add("paymentSourceNickname", "MYCC")
														.add("phoneNumber", "3053053051")
														.add("cryptoKey", cc.get("cryptoKey"))
														.add("crypto", Json.createObjectBuilder()
																.add("encryptedValue", cc.get("encryptedValue"))))))))
				.add("productPromotions", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("id","ITQ1234")))
				.add("location", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add( "addressType", "SHIPPING")
								.add("address", Json.createObjectBuilder()
										.add("street1", "9700 NW 112th AVE")
										.add("street2", "S25-3")
										.add("city", "Miami")
										.add("country", "USA")
										.add( "stateOrProvince", "FLORIDA")
										.add("zipcode", "33178"))))
				.add("orderItems",orderItemsArray)
				.add("orderPrice",Json.createObjectBuilder()
						.add("price",Json.createObjectBuilder()
								.add("amount", (Float) offering.get("orderTotal"))
								.add("currencyCode", "USD"))
						.add("quantity", "2"))
				.build();
		
		String token =  genAuthfor("GOAPI", "order management");
		JSONObject jsonRes = callPostService(webResource, input, "GOAPI - Submit Order", token);
	}
	
	public Hashtable<String, String> encryptPayment(String cardType) {
		Hashtable<String, String> out = new Hashtable<String, String>();
		String cc = TwistUtils.generateCreditCard(cardType);
		try{
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));
			
			WebResource webResource = client
					.resource("http://sit1esbgateway.tracfone.com/encrypt_json_block_of_data");
			
			JsonObject input = Json.createObjectBuilder()
					.add("cryptoText",Json.createObjectBuilder()
						.add("firstName", "TwistFirstName")
						.add("middleName", "")
						.add("lastName", "TwistLastName")
						.add("expirationMonth", "07")
						.add("expirationYear", "2021")
						.add("cvv", "671")
						.add("accountNumber", cc)
						.add("type", cardType))
					.build();
			
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			
			lStartTime = System.currentTimeMillis();
			ClientResponse response = builder.post(ClientResponse.class,input.toString());
			lEndTime = System.currentTimeMillis();
            difference = lEndTime - lStartTime;
            logger.info("Elapsed milliseconds: " + difference);
		
			if (response.getStatus() != 200) {
				System.out.println(response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			JSONObject jsonRes  = new JSONObject(response.getEntity(String.class));
			
			String responseString  = jsonRes.toString();
			StringBuffer responseBuffer = new StringBuffer(responseString);
			phoneUtil.insertIntoServiceResultsTable("GOAPI - EncryptPayment", input.toString(), responseBuffer,String.valueOf(difference), webResource.getURI().toString());
			
			String cryptoKey = jsonRes.getJSONObject("cryptoText").getString("cryptoKey");
			String encryptedValue = jsonRes.getJSONObject("cryptoText").getJSONObject("crypto").getString("encryptedValue");
			out.put("cryptoKey", cryptoKey);
			out.put("encryptedValue", encryptedValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public JSONObject getSecQuestions(String brand) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP.SEC_QUE_RESOURCE"))
				.queryParam("brandName", brand).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2").queryParam("language", "ENG");

		String token = genAuthforApp("CCU", "client_credentials", "getSecurityQuestions", "", "", account.getBrand());
		JSONObject response = callGetService(webResource, "REST-MyAccount_GetSecurityQuestions", token);
		return response;
	}

	public void addPaymentSourceforWARP() {
		client = Client.create();
		String ccNumber = TwistUtils.generateCreditCard("Visa");
		String pymtNickName = "twist_myaccapp" + TwistUtils.createRandomLong(1, 1000);
		String cipherText = encryptcc("VISA");
		WebResource webResource;
		
		webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
				+ props.getString(props.getString("ENV.") +"ADD_PAYMENT_RESOURCE"))
				.queryParam("client_id", props.getString(props.getString("ENV.")	+ "WARP_CLIENT_ID"));
		
		client.addFilter(new LoggingFilter(System.out));
		
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "ITQ999").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "WARP").build();
		JsonObject partyExtensionobj3 = Json.createObjectBuilder().add("name", "vendorStore").add("value", "302").build();
		JsonObject partyExtensionobj4 = Json.createObjectBuilder().add("name", "vendorTerminal").add("value", "302").build();

		JsonObject relatedPartyobj = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("partyID", "WALMART")
								.add("languageAbility", "ENG")
								.add("partyExtension",
										Json.createArrayBuilder().add(partyExtensionobj1).add(partyExtensionobj2).add(partyExtensionobj3)
												.add(partyExtensionobj4))).add("roleType", "partner").build();

		JsonObject partyExtObj = Json.createObjectBuilder().add("name", "accountEmail").add("value", esnUtil.getCurrentESN().getEmail())

		.build();

		JsonObject partyExtObj1 = Json.createObjectBuilder().add("name", "accountPassword").add("value", "tracfone")

		.build();

		JsonObject relatedPartyobj1 = Json.createObjectBuilder()
				.add("party", Json.createObjectBuilder().add("partyExtension", Json.createArrayBuilder().add(partyExtObj)))
				.add("roleType", "customer").build();
		
		JsonObject billingAddressObjid = Json.createObjectBuilder().add("street1", "9700 112th ave").add("street2", "")
				.add("city", "Medley").add("stateOrProvince", "FL").add("zipcode", "33178").add("country", "USA").build();

		JsonObject cipherTextObjid = Json.createObjectBuilder().add("cipherText", cipherText)
				.build();

		JsonObject cryptoOjbid = Json.createObjectBuilder().add("crypto", cipherTextObjid).build();
		JsonObject paymentMeanObjid = Json.createObjectBuilder().add("creditCard", cryptoOjbid).add("billingAddress", billingAddressObjid)
				.build();
		JsonObject paymentPlanObjid = Json.createObjectBuilder()
				.add("isRecurring", true)
				.add("paymentMean", Json.createArrayBuilder().add(paymentMeanObjid))

				.build();
		JsonObject billingAccountObjid = Json.createObjectBuilder().add("paymentPlan", paymentPlanObjid).build();
		
		JsonObject input = Json.createObjectBuilder()
									.add("billingAccount", billingAccountObjid)
									.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj).add(relatedPartyobj1))
									.build();
		
		String token = genAuthfor("WARP", "billing management");
		JSONObject jsonRes = callPostService(webResource, input, "REST-MyAccount_AddPaymentSource", token);
		try {
			JSONObject paymentMean = jsonRes.getJSONObject("billingAccount").getJSONObject("paymentPlan").getJSONArray("paymentMean").getJSONObject(0);
			String refID = paymentMean.getString("refID");
			account.setPaymentSrc(refID);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	public void addPaymentSourceToAccount(String cardType, ResourceType minOrAccID) {
		client = Client.create();
		String ccNumber = TwistUtils.generateCreditCard(cardType);
		String pymtNickName = "twist_myaccapp" + TwistUtils.createRandomLong(1, 1000);
		WebResource webResource;
		
		if (ResourceType.MIN.equals(minOrAccID)) {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("MAPP_ADD_PAYMENT_PARAM") + "min/" + account.getMin());
		} else {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("MAPP_ADD_PAYMENT_PARAM") + account.getAccountId());
		}
		client.addFilter(new LoggingFilter(System.out));

		/*JsonObject commonObj = Json.createObjectBuilder().add("brandName", account.getBrand()).add("clientAppName", "MYACCOUNT")
				.add("clientAppVersion", "1").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").build();*/
		JsonObject ccReqObj = Json.createObjectBuilder()
				.add("paymentSourceType", "CREDITCARD")
				.add("paymentSourceNickName", pymtNickName)
				.add("paymentSourceNumber", ccNumber)
				.add("creditCardType", cardType)
				.add("addressLine1", "1295 Charleston Rd")
				.add("addressLine2", "").add("city", "Mountain View")
				.add("state", "CA").add("zipcode", "94043")
				.add("country", "USA").add("phoneNumber", "9999999999")
				//.add("routingNumber", "2745123432")
				//.add("accountType", "CHECKING")
				.add("expirationMonth", "08")
				.add("expirationYear", "2020")
				.add("firstName", "Cyber")
				.add("lastName", "Source")
				.add("defaultPaymentSource", "true").build();
		
		JsonObject achReqObj = Json.createObjectBuilder()
				.add("paymentSourceType", "ACH")
				.add("routingNumber", "2745123432")
				.add("accountType", "CHECKING")
				.add("paymentSourceNickName", pymtNickName)
				.add("addressLine1", "1295 Charleston Rd")
				.add("addressLine2", "").add("city", "Mountain View")
				.add("state", "CA").add("zipcode", "94043")
				.add("country", "USA").add("phoneNumber", "9999999999")
				.add("firstName", "Cyber")
				.add("lastName", "Source")
				.add("defaultPaymentSource", "true").build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", ccReqObj).build();

		account.setPaymentSrcNickName(pymtNickName);
		account.setCreditCardNumber(ccNumber);
		
		String token = genAuthforApp("RO", "client_credentials", "addPaymentSource", "", "", account.getBrand()); 
		JSONObject jsonRes = callPostService(webResource, input, "REST-MyAccount_AddPaymentSource", token);
		try {
			JSONObject myResponse = jsonRes.getJSONObject("response");
			int pymtid = myResponse.getInt("paymentSourceId");
			account.setPaymentSourceId(pymtid);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	//do not delete : @mkankanala
	public void addPaymentSourceToAccountforTAS(String cardType,String accountID) {
		client = Client.create();
		JsonObject input;
		String ccNumber="";
		String accountNumber="";
		String last4digits="";
		if(cardType.equalsIgnoreCase("ACH")){
			accountNumber = RandomACHGenerator.getAccountNumber();
			
		}else{
			ccNumber = TwistUtils.generateCreditCard(cardType);
			System.out.println("CCCCCCCCCCCCCCCCCCCC"+ccNumber);
			last4digits = ccNumber.substring(ccNumber.length() - 4);
			esnUtil.getCurrentESN().setLastFourDigits(last4digits);
		}
		
		
		String pymtNickName = "twist_myaccapp" + TwistUtils.createRandomLong(1, 1000);
		WebResource webResource;
		
		
		
		
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("MAPP_ADD_PAYMENT_PARAM") + accountID);
		
		client.addFilter(new LoggingFilter(System.out));

		/*JsonObject commonObj = Json.createObjectBuilder().add("brandName", account.getBrand()).add("clientAppName", "MYACCOUNT")
				.add("clientAppVersion", "1").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").build();*/
		JsonObject ccReqObj = Json.createObjectBuilder()
				.add("paymentSourceType", "CREDITCARD")
				.add("paymentSourceNickName", pymtNickName)
				.add("paymentSourceNumber", ccNumber)
				.add("creditCardType", cardType)
				.add("addressLine1", "100 vail rd")
				.add("addressLine2", "").add("city", "parsippany")
				.add("state", "NJ").add("zipcode", "07054")
				.add("country", "USA").add("phoneNumber", "9999999999")
				//.add("routingNumber", "2745123432")
				//.add("accountType", "CHECKING")
				.add("expirationMonth", "08")
				.add("expirationYear", "2020")
				.add("firstName", "Cyber")
				.add("lastName", "Source")
				.add("defaultPaymentSource", "true")
				.build();
		
		JsonObject achReqObj = Json.createObjectBuilder()
				.add("paymentSourceNumber", accountNumber)
				.add("paymentSourceType", "ACH")
				.add("routingNumber", RandomACHGenerator.getRoutingNumber())
				.add("accountType", "CHECKING")
				.add("paymentSourceNickName", pymtNickName)
				.add("addressLine1", "120 vail rd")
				.add("addressLine2", "").add("city", "Parsippany")
				.add("state", "CA").add("zipcode", "07054")
				.add("country", "USA").add("phoneNumber", "9999999999")
				.add("firstName", "Cyber")
				.add("lastName", "Source")
				.add("defaultPaymentSource", "true").build();

		if(cardType.equalsIgnoreCase("ACH")){
			input = Json.createObjectBuilder().add("common", getCommonObjectTAS()).add("request", achReqObj).build();
			esnUtil.getCurrentESN().setPaymentMethod("ACH");
		}else{
			input = Json.createObjectBuilder().add("common", getCommonObjectTAS()).add("request", ccReqObj).build();
			esnUtil.getCurrentESN().setPaymentMethod("CC");
			esnUtil.getCurrentESN().setAccountNumber(accountNumber);
		}
		
		
		account.setPaymentSrcNickName(pymtNickName);
		account.setCreditCardNumber(ccNumber);
		TwistUtils.setDelay(30);
		String token = genAuthforApp1("RO", "client_credentials", "addPaymentSource", "", "", esnUtil.getCurrentBrand()); 
		JSONObject jsonRes = callPostService(webResource, input, "REST-MyAccount_AddPaymentSource", token);
		try {
			JSONObject myResponse = jsonRes.getJSONObject("response");
			int pymtid = myResponse.getInt("paymentSourceId");
			account.setPaymentSourceId(pymtid);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
		
		//return last4digits;
	}

	
	public JsonObject getCommonObjectTAS() {
		String brand = esnUtil.getCurrentBrand();
		if(brand.equalsIgnoreCase("GO_SMART")){
			brand= "SIMPLE_MOBILE";
		}
		JsonObject commonObj = Json.createObjectBuilder().add("brandName", brand).add("clientAppName", "MYACCOUNT")
				.add("clientAppVersion", "1").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").add("sourceSystem", "APP")
				.build();
		
		JsonObject commonObjWFM = Json.createObjectBuilder().add("brandName", brand).add("clientAppName", "MYACCOUNT").add("clientAppType","FULL")
				.add("clientAppVersion", "999").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").add("sourceSystem", "WEB")
				.add("partyID", "WALMART").add("partyTransactionID", "1234").build();
		
		if (brand.equalsIgnoreCase("WFM"))
			return commonObjWFM;
		else
			return commonObj;
	}

	public void updatePaymentSrc() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("MAPP_UPDATE_PAYMENT_PARAM") + account.getPaymentSourceId());

		/*JsonObject commonObj = Json.createObjectBuilder()
				.add("brandName", account.getBrand())
				.add("clientAppName", "MyAccount").add("clientAppVersion", "1")
				.add("clientAppType", "FULL").add("deviceModel", "IOS")
				.add("OSVersion", "8").add("language", "ENG").build();*/
		
		JsonObject addressObj = Json.createObjectBuilder()
				.add("addressLine1", "100 vail rd")
				.add("addressLine2", "apt k5").add("city", "Parsippany")
				.add("state", "NJ").add("country", "USA")
				.add("zipcode", "07054").build();
		
		JsonObject ccReqObj = Json
				.createObjectBuilder()
				.add("accountId", account.getAccountId())
				.add("creditCardType", account.getPaymentSrc())
				.add("defaultPaymentSource", false)
				.add("expirationMonth", "05")
				.add("expirationYear", "2020")
				.add("paymentSourceNickName", account.getPaymentSrcNickName())
				.add("address", addressObj)
				.add("accountHolderName",
						Json.createObjectBuilder()
								.add("firstName", "tfnameUpdated")
								.add("lastName", "tlnameUpdated").build())
				.build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", ccReqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "updatePaymentSource", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccount_UpdatePaymentSource", token);
	}

	public void deletePaymentSrc(ResourceType minOrAccID) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource;
		if(ResourceType.MIN.equals(minOrAccID)){
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("MAPP_DELETE_PAYMENT_PARAM") + 
					"min/" + account.getMin() + 
					"/paymentSource/" + account.getPaymentSourceId())
					.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
					.queryParam("clientAppVersion", "1").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
					.queryParam("language", "ENG");
		}
		else{
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("MAPP_DELETE_PAYMENT_PARAM") + 
					account.getAccountId() + 
					"/paymentSource/" + account.getPaymentSourceId())
					.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
					.queryParam("clientAppVersion", "1").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
					.queryParam("language", "ENG");
		}
		String token = genAuthforApp("RO", "client_credentials", "deletePaymentSource", "", "", account.getBrand());
		callDeleteService(webResource, "REST-MyAccount_deletePaymentSource", token);
	}

	public void createAccount() {
		JSONObject secQuestions = getSecQuestions(account.getBrand());
		int secQuestionId = 539233188;
		try {
			secQuestionId = secQuestions.getJSONObject("response").getJSONArray("securityQuestions").getJSONObject(0).getInt("securityQuestionId");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("MAPP_CREATE_ACC_RESOURCE") + account.getEmail());

		client.addFilter(new LoggingFilter(System.out));

		JsonObject smediaObj = Json.createObjectBuilder().add("socialMediaUid", "").add("firstName", "").add("lastName", "").add("link", "")
				.add("userName", "").add("gender", "").add("locale", "").add("ageRange", "").add("email", "").add("friendListNode", "")
				.build();
		JsonObject requestObj= null;
	if(account.getBrand().equalsIgnoreCase("SIMPLE_MOBILE")){
		requestObj = Json.createObjectBuilder().add("accountId", 0).add("esn", account.getEsn()).add("sim", account.getSim())
				.add("zipcode", "33178").add("min", "").add("password", account.getPassword()).add("dateOfBirth", "09/09/1999").add("savings", "true")
				.add("offers", "true").add("securityCode", "1234")
			.add("securityQuestionId", 539233188).add("securityAnswer", "Robert")
				 .add("socialMedia", smediaObj)
				.build();
	}else{
		
		requestObj=	Json.createObjectBuilder().add("accountId", 0).add("esn", account.getEsn()).add("sim", account.getSim())
				.add("zipcode", "33178").add("min", "").add("password", account.getPassword()).add("dateOfBirth", "09/09/1999").add("savings", "true")
				.add("offers", "true").add("securityCode", "1234")
		//	     .add("securityQuestionId", 539233188).add("securityAnswer", "Robert")
		//		 .add("socialMedia", smediaObj)
				.build();
		
	}
		account.setSecurityPin("1234");
		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("email",account.getEmail()).add("request", requestObj).build();

		String token =  genAuthforApp("CCU", "client_credentials", "createNewAccount", "", "", account.getBrand());
		JSONObject jsonRes = callPostService(webResource, input, "REST-MyAccount_CreateNewAccount", token);
		try {
			JSONObject myResponse = jsonRes.getJSONObject("response");
			int accId = myResponse.getInt("accountId");
			account.setAccountId(accId);
		} catch (JSONException e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void callAPNSettingsForPhone(String deviceModel) {
		client = Client.create();

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_APN_SETTINGS_RESOURCE") + esnUtil.getCurrentESN().getMin());

		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject())
				.add("request", Json.createObjectBuilder().add("operatingSystemId", deviceModel)).build();

		String token = genAuthforApp("CCU", "client_credentials", "apnSettings", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_apnSettings", token);
	}

	public void forgotPassword() {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_FORGOT_PWD_RESOURCE") + account.getEmail());

		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).build();

		String token = genAuthforApp("CCU", "client_credentials", "forgotPassword", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccoount_ForgotPassword", token);
	}

	public void getAccountDetails(ResourceType accIdOrMin) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String brand = account.getBrand();
		WebResource webResource;
		
		if (ResourceType.MIN.equals(accIdOrMin)) {
			webResource = client
					.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_ACCOUNT_DETAILS") + 
							"min/" + esnUtil.getCurrentESN().getMin())
					.queryParam("brandName", brand).queryParam("clientAppName", "MYACCOUNT")
					.queryParam("clientAppVersion", "2.0").queryParam("clientAppType", "FULL").queryParam("deviceModel", "IOS")
					.queryParam("OSVersion", "10.0.2").queryParam("language", "ENG").queryParam("sourceSystem", "APP");
		}
		else{
			webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_ACCOUNT_DETAILS") + 
						account.getAccountId())
				.queryParam("brandName", brand).queryParam("clientAppName", "MYACCOUNT")
				.queryParam("clientAppVersion", "2.0").queryParam("clientAppType", "FULL").queryParam("deviceModel", "IOS")
				.queryParam("OSVersion", "10.0.2").queryParam("language", "ENG").queryParam("sourceSystem", "APP");
		}
		String token = genAuthforApp("RO", "password", "getAccountDetails", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getAccountDetails", token);
	}

	public void calculateEnrollmentTaxForPlan(String serviceplan) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_ENROLL_TAX_RESOURCE"));

		JsonObject enrollTaxReqObj = Json.createObjectBuilder().add("brandName", account.getBrand())
				.add("paymentSourceId", account.getPaymentSourceId()).add("programId", 5801321).add("promotionCode", "")
				.add("accountId", account.getAccountId()).add("esn", account.getEsn())
				.build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", enrollTaxReqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "calculateEnrollTax", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_calculateEnrollTax", token);
	}

	public void calculatePurchaseTaxForPlan(String serviceplan) {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_PURCHASE_TAX_RESOURCE"));

		client.addFilter(new LoggingFilter(System.out));

		JsonObject enrollTaxReqObj = Json.createObjectBuilder().add("brandName", account.getBrand())
				.add("paymentSourceId", account.getPaymentSourceId()).add("min", esnUtil.getCurrentESN().getMin()).add("promotionCode", "")
				.add("accountId", account.getAccountId()).add("esn", account.getEsn()).add("billingZipCode", "94043")
				.add("cart", Json.createArrayBuilder().add(Json.createObjectBuilder().add("quantity", 1).add("partNumber", serviceplan))).build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", enrollTaxReqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "calculatePurchaseTax", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_calculatePurchaseTax", token);
	}

	public JsonObject getCommonObject() {
		JsonObject commonObj = Json.createObjectBuilder().add("brandName", account.getBrand()).add("clientAppName", "MYACCOUNT")
				.add("clientAppVersion", "1").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").add("sourceSystem", "APP")
				.build();
		
		JsonObject commonObjWFM = Json.createObjectBuilder().add("brandName", account.getBrand()).add("clientAppName", "MYACCOUNT").add("clientAppType","FULL")
				.add("clientAppVersion", "1234").add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").add("sourceSystem", "WEB")
				.add("partyID", "WALMART").add("partyTransactionID", "1234").build();
		
		if (account.getBrand().equalsIgnoreCase("WFM"))
			return commonObjWFM;
		else
			return commonObj;
	}

	public void getPaymentSrcListFor(/*String accountEmail, */ResourceType minOrAccID) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource;
		if(ResourceType.MIN.equals(minOrAccID)){
			webResource = client
					.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_MANAGE_PAYMENT_RESOURCE")
									+ "min/" + account.getMin()).queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
					.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
					.queryParam("language", "ENG");
		}
		else{
			webResource = client
					.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_MANAGE_PAYMENT_RESOURCE")
									+ account.getAccountId()).queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
					.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
					.queryParam("language", "ENG");
		}

		String token = genAuthforApp("RO", "client_credentials", "getPaymentSource", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getPaymentSource", token);
	}

	public void validateUserDevice(String brand, ResourceType minSimOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minSimOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else if (ResourceType.SIM.equals(minSimOrEsn)) {
			resource = "sim/" + account.getSim();
		} else {
			resource = "min/" + account.getMin();
		}
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_VALIDATE_USER_DEVICE") + 
						resource)
				.queryParam("OSVersion", "10.0.2").queryParam("brandName", brand).queryParam("channelId", "123abc")
				.queryParam("clientAppName", "MYACCOUNT").queryParam("clientAppVersion", "2.0").queryParam("deviceId", "123abc")
				.queryParam("deviceModel", "IOS").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCU", "client_credentials", "validateUserDevice", "", "", brand);
		callGetService(webResource, "REST-MyAccount_validateUserDevice", token);
	}

	public void enrollIntoServicePlanWithOption(String servicePlan, String refillType, ResourceType simOrEsn) {
		boolean rType;
		String cvv;
		if (refillType.equalsIgnoreCase("now")) {
			rType = true;
		} else {
			rType = false;
		}

		if (account.getPaymentSrc().equalsIgnoreCase("AmericanExpress")) {
			cvv = "1234";
		} else {
			cvv = "123";
		}
		
		String resource;
		if (ResourceType.ESN.equals(simOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_ENROLL_PLAN_RESOURCE") +
				resource + "/autoRefill/" +
				"servicePlan/" + account.getProgramId());

		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder()
								.add("common", getCommonObject())
								.add("request", Json.createObjectBuilder()
										.add("paymentSourceId", account.getPaymentSourceId())
										.add("cvv", cvv)
										.add("accountId", account.getAccountId())
										.add("enrollNow", rType)
										.add("servicePlanId", servicePlan) 
										.add("isILDVas", false))
								.build();

		String token = genAuthforApp("CCP", "client_credentials", "enrollIntoPlan", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_EnrollIntoPlan", token);
	}
	
	public void accountRecoveryStatus(ResourceType simMinOrEsn) {
		String resource;
		
		WebResource webResource = null;
		String sourceSystem = null;
		if (account.getBrand().equalsIgnoreCase("WFM"))
			sourceSystem = "WEB";
		else
			sourceSystem = "APP";
		
		if (simMinOrEsn != null) {
			if (ResourceType.ESN.equals(simMinOrEsn)) {
				resource = account.getEsn();
			} else if (ResourceType.MIN.equals(simMinOrEsn)) {
				resource = account.getMin();
			} else if (ResourceType.SIM.equals(simMinOrEsn)) {
				resource = account.getSim();
			} else if (ResourceType.EMAIL.equals(simMinOrEsn))
				resource = "email/" + account.getEmail();
			else
				resource = "accountId/" + account.getAccountId();

			client = Client.create();
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_ACCOUNT_RECOVERY")
									+ "accountStatus/" + resource)
					.queryParam("OSVersion", "10.0.2")
					.queryParam("brandName", account.getBrand())
					.queryParam("channelId", "123abc")
					.queryParam("clientAppName", "MYACCOUNT")
					.queryParam("clientAppVersion", "2.0")
					.queryParam("deviceId", "123abc")
					.queryParam("deviceModel", "IOS")
					.queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem);
		} else {
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_ACCOUNT_RECOVERY")
									+ "accountStatus/")
					.queryParam("recoveryIdentifer", account.getMin())
					.queryParam("type", "LINE")
					.queryParam("last4digitsEsn",account.getEsn().substring(account.getEsn().length() - 4))
					.queryParam("last4digitsMin",account.getMin().substring(account.getMin().length() - 4))
					.queryParam("clientAppVersion", "2.0")
					.queryParam("deviceId", "123abc")
					.queryParam("deviceModel", "IOS")
					.queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem);
		}
		client.addFilter(new LoggingFilter(System.out));

		String token = genAuthforApp("CCU", "client_credentials", "accountRecovery", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_AccountRecovery_Status", token);
	}

	public void accountRecoveryValidate(ResourceType simMinOrEsn, String securityPin, String zipCode,
			String question, String answer, String paymentSource) {
		String resource;
		if (ResourceType.ESN.equals(simMinOrEsn)) {
			resource =  account.getEsn();
		} else if (ResourceType.MIN.equals(simMinOrEsn)) {
			resource = account.getMin();
		} else {
			resource = account.getSim();
		}
		
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_ACCOUNT_RECOVERY") +
				"validate/" + resource);

		client.addFilter(new LoggingFilter(System.out));
		
		JsonObjectBuilder request = Json.createObjectBuilder();
		
		if (securityPin != null && !securityPin.isEmpty()) {
			request.add("securityPin", securityPin);
		}
		if (zipCode != null && !zipCode.isEmpty()) {
			request.add("activationZipCode", zipCode);
		}
		if (paymentSource != null && !paymentSource.isEmpty()) {
			request.add("paymentSource", paymentSource);
		}
		if (answer != null && !answer.isEmpty()) {
			request.add("securityQuestion", Json.createObjectBuilder()
					.add("question", question)
					.add("answer", answer));
		}

		JsonObject input = Json.createObjectBuilder()
								.add("common", getCommonObject())
								.add("request", request)
								.build();

		String token = genAuthforApp("CCU", "client_credentials", "accountRecovery", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_AccountRecovery_Validate", token);
	}
	
	public void accountRecoveryCheckpoints(ResourceType simMinOrEsn, String zipCode) {
		String resource;
		if (ResourceType.ESN.equals(simMinOrEsn)) {
			resource =  account.getEsn();
		} else if (ResourceType.MIN.equals(simMinOrEsn)) {
			resource = account.getMin();
		} else {
			resource = account.getSim();
		}
		
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_ACCOUNT_RECOVERY") +
				"checkpoints/" + resource);
		
		JsonObject input = Json.createObjectBuilder()
								.add("common", getCommonObject())
								.add("request", Json.createObjectBuilder()
										.add("activationZipCode", zipCode))
								.build();

		String token = genAuthforApp("CCU", "client_credentials", "accountRecovery", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_AccountRecovery_Checkpoints", token);
	}
	
	public void getDisenrollmentReason() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_REASON"))
				.queryParam("brandName", "STRAIGHT_TALK").queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");
		
		String token = genAuthforApp("RO", "client_credentials", "disenrollmentReason", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_DisenrollmentReason", token);
	}
	
	public void getRetailersInfoForInAroundMileRadius(String brand, String zipCode, String radius) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_RETAILER_LIST_RESOURCE"))
				.queryParam("origin", zipCode).queryParam("maxMatches", "5")
				.queryParam("radious", "5.0");

		String token = genAuthforApp("CCU", "client_credentials", "retailer", "", "", brand);
		callGetService(webResource, "REST-MyAccount_LocationService", token);
	}

	public void forgotUsername() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_FORGOT_USERNAME_RESOURCE") + "9544775079");

		JsonObject accReqObj = Json.createObjectBuilder()
				.add("securityQuestion", Json.createObjectBuilder().add("question", "What is your mothers maiden name").add("answer", "Robert"))
				.build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", accReqObj).build();

		String token = genAuthforApp("CCU", "client_credentials", "forgotUserName", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_forgotUserName", token);
	}

	public void viewListOfFeaturesSupportedBy(String brand) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_BRAND_FEATURES_RESOURCE"));
		String clientAppName;
		if (brand.equalsIgnoreCase("STRAIGHT_TALK")) {
			clientAppName = "com.tracfone.straighttalk.myaccount";
		} else if(brand.equalsIgnoreCase("TOTAL_WIRELESS")) {
			clientAppName = "com.tracfone.total.myaccount";
		} else if(brand.equalsIgnoreCase("SIMPLE_MOBILE")) {
			clientAppName = "com.tracfone.simplemobile.myaccount";
		} else {
			clientAppName = "com.tracfone." + brand.toLowerCase() + ".myaccount";
		}

		JsonObject commonObj = Json.createObjectBuilder().add("brandName", brand)
				.add("clientAppName", clientAppName).add("clientAppVersion", "8")
				.add("deviceModel", "IOS").add("OSVersion", "8").add("language", "ENG").add("sourceSystem", "APP").build();

		JsonObject input = Json.createObjectBuilder().add("common", commonObj).build();

		String token = genAuthforApp("CCU", "client_credentials", "version", "", "", brand);
		callPostService(webResource, input, "REST-MyAccount_Version", token);
	}

	public void getCurrentEnrollmentInformation(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_ENROLLED_PLAN_RESOURCE") + 
				resource)
				.queryParam("brandName", "STRAIGHT_TALK").queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("RO", "client_credentials", "version", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_GetEnrolledPlan", token);
	}
	
	public String getBalanceInquiry(ResourceType minOrEsn) {
		return getBalanceInquiry(minOrEsn, "");
	}
	
	public String getBalanceInquiry(ResourceType minOrEsn, String transactionId) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		if (transactionId != null && !transactionId.isEmpty()) {
			account.setTransID(transactionId);
			resource += "/transactionId/" + transactionId;
		}

		String sourceSystem = "APP";
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_BALANCE") + 
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", sourceSystem);
		
		if(account.getBrand().equalsIgnoreCase("WFM")){
			sourceSystem = "WEB";
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_BALANCE") + 
					resource)
					.queryParam("brandName", account.getBrand())
					.queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem);
		}

		String token = genAuthforApp("RO", "client_credentials", "getBalanceInquiry", "", "", account.getBrand());
		JSONObject json = callGetService(webResource, "REST-MyAccount_GetBalanceInquiry", token);
		
		try {
			String transId = ((JSONObject)json.get("response")).get("transactionId").toString();
			account.setTransID(transId);
			return transId;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void requestBenefitsBalance(ResourceType minOrEsn, String transId) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		
		String sourceSystem = "APP";
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_REQUEST_BENEFITS") + 
				resource +
				"/transactionId/" + transId)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", sourceSystem);
		
		if(account.getBrand().equalsIgnoreCase("WFM")){
			sourceSystem = "WEB";
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_REQUEST_BENEFITS")
									+ resource + "/transactionId/" + transId)
					.queryParam("brandName", account.getBrand())
					.queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem);
		}
		
		String token = genAuthforApp("CCU", "client_credentials", "requestBenefitsBalance", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_RequestBenefitsBalance", token);
	}
	
	public void checkLinkingStatus() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_CHECK_LINKING")) 
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCU", "client_credentials", "checkLinkingStatus", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_CheckLinkingStatus", token);
	}
	
	public void linkAccount() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_LINK_ACCOUNT") +
				account.getAccountId()) 
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("RO", "client_credentials", "linkAccount", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_linkAccount", token);
	}
	
	public void getAllTicketsInformation(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_TICKET_INFO_RESOURCE") +
						resource)
				.queryParam("brandName", "STRAIGHT_TALK").queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "client_credentials", "order-mgmt", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_GetTickets", token);
	}

	public void deenrollEsnFromCurrentEnrollment(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_DEENROLL_PLAN_RESOURCE") + 
				resource + 
				"/program/" + account.getProgramId());

		JsonObject input = Json.createObjectBuilder()
								.add("common", getCommonObject())
								.add("request", Json.createObjectBuilder()
									.add("reason", "Not needed"))
								.build();
		
		String token = genAuthforApp("RO", "client_credentials", "deEnrollFromPlan", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_deEnrollFromPlan", token);
	}

	public void updateNickName(ResourceType minEsnOrGroup, String nickName) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minEsnOrGroup)) {
			resource = "esn/" + account.getEsn();
		} else if (ResourceType.GROUP.equals(minEsnOrGroup)) {
			resource = "groupId/" + account.getGroupId();
		} else {
			resource = "min/" + account.getMin();
		}
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_UPDATE_NICKNAME") + 
				resource);
		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder()
									.add("common", getCommonObject())
									.add("request", Json.createObjectBuilder()
											.add("nickName", nickName)
											.add("accountId", account.getAccountId()))
									.build();

		String token = genAuthforApp("RO", "client_credentials", "updateNickName", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccount_updateNickName", token);
	}

	public void addADeviceToAccount(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_ADD_DEVICE_TO_ACCOUNT") + account.getAccountId());

		JsonObjectBuilder reqObj;
		if (ResourceType.ESN.equals(minOrEsn)) {
			reqObj = Json.createObjectBuilder().add("esn", account.getEsn());
		} else {
			reqObj = Json.createObjectBuilder().add("min", account.getMin());
		}

		JsonObject input = Json.createObjectBuilder()
								.add("common", getCommonObject())
								.add("request", reqObj)
								.build();

		String token = genAuthforApp("RO", "client_credentials", "addDeviceToAccount", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_addDeviceToAccount", token);
	}

	public void deleteDeviceFromAccount(ResourceType minEsnOrGroupESNMIN) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		if (ResourceType.ESN.equals(minEsnOrGroupESNMIN)) {
			resource = "esn/" + account.getEsn();
		} else if(ResourceType.MIN.equals(minEsnOrGroupESNMIN)){
			resource = "min/" + account.getMin();
		} else if (ResourceType.GROUPESN.equals(minEsnOrGroupESNMIN)){
			resource = "group/esn/" + account.getEsn();
		} else
			resource = "group/min/" + account.getMin();
		
		WebResource webResource;
		if (account.getBrand().equalsIgnoreCase("wfm")){
			 webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_WFM_DELETE_DEVICE_ACCOUNT") 
								+ account.getAccountId() + "/"
								+ resource)
					.queryParam("brandName", account.getBrand())
					.queryParam("clientAppName", "myAccount").queryParam("clientAppVersion", "2.0").queryParam("deviceModel", "IOS")
					.queryParam("OSVersion", "8").queryParam("lang", "ENG");
		}else
		{
			 webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_DELETE_DEVICE_ACCOUNT") 
								+ "account/" + account.getAccountId() + "/"
								+ resource)
					.queryParam("brandName", account.getBrand())
					.queryParam("clientAppName", "myAccount").queryParam("clientAppVersion", "2.0").queryParam("deviceModel", "IOS")
					.queryParam("OSVersion", "8").queryParam("language", "ENG");
		}

		String token = genAuthforApp("RO", "client_credentials", "deleteDeviceAccount", "", "", account.getBrand());
		callDeleteService(webResource, "REST-MyAccount_deleteDeviceAccount", token);
	}

	public enum ResourceType {
		MIN, ESN, SIM, GROUP, DEVICETYPE, ACC, GROUPESN, GROUPMIN, EMAIL, DATE , BAN, LINE
	}

	public void getPaymentHistory(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		String startDate = null;
		String endDate = null;
		
		
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "device/" +account.getEsn();
		} else if (ResourceType.MIN.equals(minOrEsn)) {
			resource = "device/" +account.getMin();
		} else if (ResourceType.DATE.equals(minOrEsn)) {
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date today = new Date();
			
			Calendar start = Calendar.getInstance();
	        start.setTime(today);
	        start.add(Calendar.DATE, -30);
	        
	        startDate = dateFormat.format(start.getTime());
	        endDate = dateFormat.format(today);
	        
			resource = "startDate/" + startDate + "/endDate/" + endDate;
		} else {
			resource = "paymentSourceId/" + account.getPaymentSourceId();
		}
		
		String sourceSystem = null;
		if(account.getBrand().equalsIgnoreCase("WFM"))
			sourceSystem = "WEB";
		else 
			sourceSystem = "APP";
		WebResource webResource = null; 
		
		if (ResourceType.ESN.equals(minOrEsn)) {
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_GET_PAYMENT_HISTORY")
									+ "account/"
									+ account.getAccountId()
									+ "/"
									+ resource)
					.queryParam("account_id", Integer.toString(account.getAccountId()))
					.queryParam("brandName", account.getBrand()).queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem)
					.queryParam("esn", account.getEsn());
		} else if (ResourceType.MIN.equals(minOrEsn)){
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_GET_PAYMENT_HISTORY")
									+ "account/"
									+ account.getAccountId()
									+ "/"
									+ resource)
					.queryParam("account_id", Integer.toString(account.getAccountId()))
					.queryParam("brandName", account.getBrand()).queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem)
					.queryParam("min", account.getMin());
		} else if (ResourceType.DATE.equals(minOrEsn)){
			webResource = client
					.resource(
							props.getString(props.getString("ENV.")
									+ "APP.END_POINT")
									+ props.getString("APP_GET_PAYMENT_HISTORY")
									+ "account/"
									+ account.getAccountId()
									+ "/"
									+ resource)
					.queryParam("account_id", Integer.toString(account.getAccountId()))
					.queryParam("brandName", account.getBrand())
					/*.queryParam("clientAppName", "MYAccount")
					.queryParam("clientAppVersion", "1.0")
					.queryParam("deviceModel", "Android")
					.queryParam("OSVersion", "2")*/.queryParam("language", "ENG")
					.queryParam("sourceSystem", sourceSystem)
					.queryParam("startDate", startDate)
					.queryParam("startDate", endDate)/*
															 * .queryParam("offSet",
															 * "0")
															 * .queryParam("limit",
															 * "25")
															 */;
		} else {
			webResource = client
			.resource(
					props.getString(props.getString("ENV.")
							+ "APP.END_POINT")
							+ props.getString("APP_GET_PAYMENT_HISTORY")
							+ "account/"
							+ account.getAccountId()
							+ "/"
							+ resource)
			.queryParam("account_id", Integer.toString(account.getAccountId()))
			.queryParam("brandName", account.getBrand()).queryParam("language", "ENG")
			.queryParam("sourceSystem", sourceSystem)
			.queryParam("paymentSourceID", Integer.toString(account.getPaymentSourceId()));
		}

		String token = genAuthforApp("RO", "", "getPaymentHistory", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getPaymentHistory", token);
	}
	
	public void getPaymentDetails() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_PAYMENT_DETAILS") + "transactionId/"
								+ account.getTransId()).queryParam("brandName", account.getBrand())
				.queryParam("clientAppName", "MYAccount").queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "Android")
				.queryParam("OSVersion", "2").queryParam("language", "ENG").queryParam("sourceSystem", "APP").queryParam("offSet", "0")
				.queryParam("limit", "25");

		String token = genAuthforApp("CCP", "", "getPaymentDetails", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getPaymentDetails", token);
	}

	public void getTransactionHistory(ResourceType minOrEsn) {
		client = Client.create();
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_TRANS_HISTORY") + 
						resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "MYAccount").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "Android").queryParam("OSVersion", "2").queryParam("language", "ENG").queryParam("sourceSystem", "APP")
				.queryParam("offSet", "0").queryParam("limit", "25");

		client.addFilter(new LoggingFilter(System.out));

		String token = genAuthforApp("RO", "", "getTransactionHistory", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getTransactionHistory", token);
	}

	public void enumeratePin(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_MANAGE_PIN") + 
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("RO", "", "managePINCardReserve", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_enumeratePin", token);
	}

	public void movePinToTheTop(ResourceType minOrEsn, String pin) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_MOVE_PIN") + 
				resource + "/" + 
				"pinCardReserve/" + pin);				
		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).build();
		
		String token = genAuthforApp("RO", "", "moveReservedPIN", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccount_moveReservedPIN", token);
	}

	public void redeemPINFromReserve(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_REDEEM_RESERVE_PIN")
				+ resource);

		JsonObject request = Json.createObjectBuilder()
								.add("common",	getCommonObject())
								.add("request", Json.createObjectBuilder()
										.add("redeemNow", true))
								.build();		

		String token = genAuthforApp("RO", "", "redeemPINCardReserve", "", "", account.getBrand());
		callPostService(webResource, request, "REST-MyAccount_redeemPINCardReserve", token);
	}

	public enum Flow {
		REDEMPTION(false), ADD_NOW(false), ADD_TO_RESERVE(false), MANAGE_RESERVE(false), PURCHASE(true), PURCHASE_WITH_ENROLLMENT(true), APPLY_NOW(
				true), ENROLL_IN_AUTO_REFILL(true), SWITCH_PLAN(true), VALUE_PLAN_ENROLLMENT(true), CHANGE_VALUE_PLAN(true), PROMOENROLLMENT(true);

		public final boolean isPurchase;

		private Flow(boolean isPurchase) {
			this.isPurchase = isPurchase;
		}
	}

	public void getRetentionMatrix(ResourceType minOrEsn, Flow flow, String pin, int servicePlanId) {
		client = Client.create();
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		JsonObjectBuilder planIdentifier;
		if (flow.isPurchase) {
			planIdentifier = Json.createObjectBuilder().add("servicePlanId", servicePlanId);
		} else {
			planIdentifier = Json.createObjectBuilder().add("pinCard", pin);
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_RETENTION_MATRIX") + resource);

		JsonObject request = Json
				.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request", Json.createObjectBuilder()
						.add("context", flow.toString())
						.add("planIdentifier", planIdentifier))
				.build();

		client.addFilter(new LoggingFilter(System.out));

		String token = genAuthforApp("RO", "", "retentionMatrix", "", "", account.getBrand());
		callPostService(webResource, request, "REST-MyAccount_retentionMatrix", token);
	}

	public void getManufacturerModel() {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_MODEL"))
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		client.addFilter(new LoggingFilter(System.out));

		String token = genAuthforApp("RO", "", "getManufacturerModel", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getManufacturerModel", token);
	}

	public void getScripts(String plans) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_SCRIPTS") + plans)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG");
		// .queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "", "getScript", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getScript", token);
	}

	public void getRewards(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_REWARDS") + resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "", "getRewards", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getRewards", token);
	}

	public JSONObject getServicePlans(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else if (ResourceType.MIN.equals(minOrEsn)) {
			resource = "min/" + account.getMin();
		} else {
			resource = "deviceType/" + account.getDeviceType();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_PLANS") + 
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "", "servicePlanSelector", "", "", account.getBrand());
		return callGetService(webResource, "REST-MyAccount_servicePlanSelector", token);
	}

	public void getHandsetProtectionPrograms(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_HPP") + 
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "", "getHandsetProtectionPrograms", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getHandsetProtectionPrograms", token);
	}

	public void updateProfileContact(ResourceType minOrAcc) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource;
		JsonObject reqObj;
		
		if (ResourceType.MIN.equals(minOrAcc)) {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("APP_UPDATE_ACCOUNT")+ "min/" + account.getMin() + "/profile");
			client.addFilter(new LoggingFilter(System.out));
			
			reqObj = Json.createObjectBuilder()
					.add("firstName", "TwistFirstName")
					.add("lastName", "TwistLastName")
					.add("dateOfBirth", "02-02-1978")
					.add("password", "tracfone")
					.add("contactPhoneNumber", "3056328784")
					.add("address1", "9700 NW 112th Avenue")
					.add("address2", "308").add("city", "Medley").add("state", "Florida")
					.add("zipcode", "33178").build();
		}
		else{
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("APP_UPDATE_ACCOUNT") + account.getAccountId() + "/profile");
			client.addFilter(new LoggingFilter(System.out));
			
			reqObj = Json.createObjectBuilder()
					.add("firstName", "TwistFirstName")
					.add("lastName", "TwistLastName")
					.add("dateOfBirth", "02-02-1978")
					.add("password", "tracfone").add("state", "FL")
					.add("contactPhoneNumber", "3056328784")
					.add("address1", "9700 NW 112th Avenue")
					.add("city", "Medley")
					.add("zipcode", "33178").add("address2", "308")
					.add("offers", "true").add("savings", "false").build();
		}

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", reqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "updateAccount", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccount_updateAccount", token);
	}
	
	public void updateProfileLoginInformation() {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_UPDATE_LOGIN_INFORMATION") + account.getAccountId() + "/profile");
		client.addFilter(new LoggingFilter(System.out));

		JsonObject reqObj = Json.createObjectBuilder()
				.add("email", account.getEmail())
				.add("password", account.getPassword())
				.add("newPassword", "tracfone1")
				.build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", reqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "updateLoginInformation", "", "", account.getBrand());
		callPutService(webResource, input, "REST-MyAccount_updateLoginInformation", token);
	}

	public void retrieveCustomerAccountProfile(ResourceType accIdOrMin) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String brand = account.getBrand();
		WebResource webResource;
		String sourceSystem = null;
		
		if(account.getBrand().equalsIgnoreCase("WFM"))
			sourceSystem = "WEB";
		else
			sourceSystem = "APP";
/*		ESN chileEsn = esnUtil.popRecentActiveEsn(esnUtil.getCurrentESN().getPartNumber());
		String min = phoneUtil.getMinOfActiveEsn(chileEsn.toString());
		System.out.println("*********************child min is "+min);*/
		
		if (ResourceType.MIN.equals(accIdOrMin)) {
			webResource = client
					.resource(
							props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_ACCOUNT_PROFILE")
									+ "min/" + account.getMin())
					.queryParam("brandName", brand)/*.queryParam("clientAppName", "MYACCOUNT")*/
					/*.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "8")*/
					.queryParam("language", "ENG").queryParam("sourceSystem", sourceSystem);
		}
		else{
			webResource = client
					.resource(
							props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_ACCOUNT_PROFILE")
									+ account.getAccountId())
					.queryParam("brandName", brand).queryParam("clientAppName", "MYACCOUNT")
					.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "8")
					.queryParam("language", "ENG").queryParam("sourceSystem", sourceSystem);
		}
		String token = genAuthforApp("RO", "client_credentials", "getAccountProfile", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_getAccountProfile", token);
	}

	public void redeemPinCard(String pin) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_REDEEM_PIN") +
				"pin/" + pin);
		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder()
									.add("common", getCommonObject())
									.add("request", Json.createObjectBuilder()
											.add("min", account.getMin())
											.add("esn", account.getEsn())
											.add("promoCode", "")
											.add("redeemNow", true))
//												.add("pinCards", Json.createArrayBuilder()
//														.add(Json.createObjectBuilder()
//																.add("pinCard", pin))))
									.build();

		String token = genAuthforApp("CCU", "client_credentials", "redeemPIN", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_redeemPIN", token);
	}

	public void clearOTA(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_CLEAR_OTA") +
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("RO", "client_credentials", "clearOTA", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_clearOTA", token);
	}

	public void confirmPurchase(String pinPart) throws JSONException {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = null;
		if (pinPart.isEmpty() || pinPart.equals(null)) {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
					+ props.getString("APP_CONFIRM_PURCHASE") + "/paymentSource/" + account.getPaymentSourceId());
		} else {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_CONFIRM_PURCHASE") + "partNumber/" + pinPart + "/paymentSource/" + account.getPaymentSourceId());
		}
		client.addFilter(new LoggingFilter(System.out));
		ESN esn = esnUtil.getCurrentESN();

		JsonObject reqObj = Json.createObjectBuilder()
					.add("cvvNumber", "671")
					.add("esn", esn.getEsn())
					.add("min", account.getMin())
					.add("isILDVas", false)
					.add("min", esn.getMin())
					.add("redeemNow", false)
					.add("servicePlanId", esn.getServicePlanId())
					.add("enrollNow", true)
					.add("accountId", account.getAccountId())
					.build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", reqObj).build();

		String token = genAuthforApp("RO", "client_credentials", "confirmPurchase", "", "", account.getBrand());
		JSONObject response = callPostService(webResource, input, "REST-MyAccount_confirmPurchase", token);

		String transId= response.getJSONObject("response").getJSONObject("transactionDetails").getString("transactionId");
		account.setTransID(transId);
	}

	public void validatePromo(String pinPart, String pin, String price, String promo, Flow flow) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_VALIDATE_PROMOTION"));

		JsonObject reqObj = Json.createObjectBuilder()
				.add("esn", esnUtil.getCurrentESN().getEsn())
				.add("promotionCode", promo)
				.add("transactionType", flow.toString()) // PROMOENROLLMENT, PURCHASE, REDEMPTION
				.add("transactionAmount", price)
				.add("cardIdentifier", Json.createObjectBuilder()
						.add("pinCard", pin)
						.add("partNumber", pinPart))
				.build();

		JsonObject input = Json.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request", reqObj)
				.build();

		String token = genAuthforApp("RO", "client_credentials", "validatePromotion", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_validatePromotion", token);
	}

	public void validateAddress(String address1, String city, String state, String country, String zip) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_VALIDATE_ADDRESS"));

		JsonObject addrReqObj = Json.createObjectBuilder().add("id", "").add("addressLine1", address1)
				.add("addressLine2", "").add("city", city).add("country", country).add("locality", "").add("poBoxNumber", "")
				.add("stateOrProvince", state).add("streetName", "").add("zipcode", zip).build();

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).add("request", addrReqObj).build();
		
		String token = genAuthforApp("CCP", "client_credentials", "address-mgmt", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_validateAddress", token);
	}

	public JSONObject callPostService(WebResource webResource, JsonObject input, String serviceName, String token) {
		return callPostService(webResource, input.toString(), serviceName, token);
	}
	
	public JSONObject callPutService(WebResource webResource, JsonObject input, String serviceName, String token) {
		return callPutService(webResource, input.toString(), serviceName, token);
	}
	
	public JSONObject callPostService(WebResource webResource, String input, String serviceName, String token) {
		return callService(webResource, input, serviceName, token, "POST");
	}
	
	public JSONObject callPutService(WebResource webResource, String input, String serviceName, String token) {
		return callService(webResource, input, serviceName, token, "PUT");
	}
	
	public JSONObject callGetService(WebResource webResource, String serviceName, String token) {
		return callService(webResource, null, serviceName, token, "GET");
	}
	
	public JSONObject callDeleteService(WebResource webResource, String serviceName, String token) {
		return callService(webResource, null, serviceName, token, "DELETE");
	}
	
	private JSONObject callService(WebResource webResource, String input, String serviceName, String token, String callType) {
		WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
		builder.type(MediaType.APPLICATION_JSON);
		builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		logger.info(webResource.getURI());
		logger.info(input);
		
		ClientResponse response;
		lStartTime = System.currentTimeMillis();		
		if ("PUT".equalsIgnoreCase(callType)) {
			response = builder.put(ClientResponse.class, input);
		} else if ("POST".equalsIgnoreCase(callType)) {
			response = builder.post(ClientResponse.class, input);
		} else if ("DELETE".equalsIgnoreCase(callType)) {
			response = builder.delete(ClientResponse.class);	
		} else {
			response = builder.get(ClientResponse.class);	
		}
		
		lEndTime = System.currentTimeMillis();
		difference = lEndTime - lStartTime;		
		logger.info("Elapsed milliseconds: " + difference);
		
		String responseString = response.getEntity(String.class);
		
		StringBuffer responseBuffer = new StringBuffer(responseString);
		
		String requestString;
		if (input != null && !input.isEmpty()) {
			requestString = input;
		} else {
			requestString = webResource.getURI().toString();
		}
		
		phoneUtil.insertIntoServiceResultsTable(serviceName, requestString, responseBuffer, String.valueOf(difference), webResource.getURI().toString());

		if (response.getStatus() != 200 && response.getStatus() != 202 ) {
			logger.info("Failed : HTTP error code :" + response.getStatus());
			throw new RuntimeException("Failed : HTTP error code :" + response.getStatus());
		}

		JSONObject result = null;
		
		int code = -1;
		try {
			result = new JSONObject(responseString);
			JSONObject status = result.getJSONObject("status");
			String codeStr = (String) status.get("code");
			code = Integer.parseInt(codeStr);
		} catch (JSONException e) {
			e.printStackTrace();
			//Don't commit 
//			throw new RuntimeException("JSON error: " + e.getMessage());
		}
		if (code == 0 || code == 100 || code == 10013) {
			return result;
		} else {
			throw new RuntimeException("JSON error: " + code);
		}
	}

	public void completePhonebranding() {
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "ITQ999").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "WARP").build();

		JsonObject relatedPartyobj1 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder().add("partyID", "CUST_HASH").add("individual", Json.createObjectBuilder().add("id", "webobjid")))
				.add("roleType", "customer").build();

		JsonObject relatedPartyobj2 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder().add("partyID", "TFAPP-ST").add("languageAbility", "ENG")
								.add("partyExtension", Json.createArrayBuilder().add(partyExtensionobj1).add(partyExtensionobj2)))
				.add("roleType", "application").build();

		JsonObject input = Json
				.createObjectBuilder()
				.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj1).add(relatedPartyobj2))
				.add("resource",
						Json.createObjectBuilder().add("association", Json.createObjectBuilder().add("role", "branding"))
								.add("resourceSpecification", Json.createObjectBuilder().add("brand", "STRAIGHT_TALK"))
								.add("physicalResource", Json.createObjectBuilder().add("resourceCategory", "HANDSET").add("serialNumber", "")))
				.build();
	}

	public void getlistofall(String type) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));

		String resourceType;
		String serviceName;
		if (type.equalsIgnoreCase("port")) {
			resourceType = "APP_PORT_CARRIER_LIST";
			serviceName = "PortCarriersConfig";
		} else if (type.equalsIgnoreCase("carrier")) {
			resourceType = "APP_SERVICE_PROVIDER_LIST";
			serviceName = "ServiceProviderConfig";
		} else {
			throw new IllegalArgumentException("Unsupport type: " + type);
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString(resourceType))
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG")
				.queryParam("category", "SERVICE_PROVIDERS").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCP", "client_credentials", "config-mgmt", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_" + serviceName, token);
	}
		
	public void activatePhone() {
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		//client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME"),  props.getString("OOB_AUTH_PASSWORD") ));
		String oobEndPoint=props.getString("OOB_END_POINT");
		if (oobEndPoint.contains("tst")||oobEndPoint.contains("TST")){
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_TST"),  props.getString("OOB_AUTH_PASSWORD_TST") ));
		}else{
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_SITX"),  props.getString("OOB_AUTH_PASSWORD_SITX") ));
		}	
		
			WebResource webResource= client
					.resource(props.getString("OOB_END_POINT")
					+ "/activate");
			WebResource.Builder builder = webResource
					.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			
			
			JsonObject input = Json
					.createObjectBuilder()
					.add("reseller_id", "RESELLER123")
					.add("imei", esnUtil.getCurrentESN().getEsn())
					.add("zipcode", esnUtil.getCurrentESN().getZipCode())
					.add("iccid",esnUtil.getCurrentESN().getSim())
					.add("airtime_pin",esnUtil.getCurrentESN().getPin())
			//		.add("email",TwistUtils.createRandomEmail())

					.build();

			lStartTime= System.currentTimeMillis();
			ClientResponse response = builder.post(ClientResponse.class,input.toString());
			lEndTime= System.currentTimeMillis();
			
			long lDifference= lEndTime-lStartTime ;
			
			BufferedReader in = null;
			InputStream is = response.getEntityInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			StringBuffer out = new StringBuffer();
			String line;
			try {
				while ((line = in.readLine()) != null) {
					out.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			phoneUtil.insertIntoServiceResultsTable("REST_OOB_Activation", input.toString(), out, String.valueOf(lDifference), webResource.getURI().toString());
			
			if (response.getStatus() != 200) {
				System.out.println(response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

		//	String output = response.getEntity(String.class);
		
	}

	public void refillPhonewithPin() {
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		//client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME"), props.getString("OOB_AUTH_PASSWORD") ));
		String oobEndPoint=props.getString("OOB_END_POINT");
		if (oobEndPoint.contains("tst")||oobEndPoint.contains("TST")){
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_TST"),  props.getString("OOB_AUTH_PASSWORD_TST") ));
		}else{
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_SITX"),  props.getString("OOB_AUTH_PASSWORD_SITX") ));
		}
		
			WebResource webResource= client
					.resource(
							props.getString("OOB_END_POINT")
					+ "/refill");
			WebResource.Builder builder = webResource
					.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			
			
			JsonObject input = Json
					.createObjectBuilder()
					.add("reseller_id", "1234")
					.add("imei", esnUtil.getCurrentESN().getEsn())
					.add("iccid",esnUtil.getCurrentESN().getSim())
					.add("airtime_pin",esnUtil.getCurrentESN().getPin())

					.build();

			lStartTime= System.currentTimeMillis();
			ClientResponse response = builder.post(ClientResponse.class,input.toString());
			lEndTime= System.currentTimeMillis();
			
			long lDifference= lEndTime-lStartTime ;
			BufferedReader in = null;
			InputStream is = response.getEntityInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			StringBuffer out = new StringBuffer();
			String line;
			try {
				while ((line = in.readLine()) != null) {
					out.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			phoneUtil.insertIntoServiceResultsTable("REST_OOB_RefillWithPin", input.toString(), out, String.valueOf(lDifference), webResource.getURI().toString());

			if (response.getStatus() != 200) {
				System.out.println(response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

		//	String output = response.getEntity(String.class);
		
	}

	public void transferMin(String oldesn, String esn, String sim, String oldsim) {
		int i = oldsim.length();
		String oldiccid = oldsim.substring(i-4, i);
		System.out.println(oldsim);
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String oobEndPoint=props.getString("OOB_END_POINT");
		if (oobEndPoint.contains("tst")||oobEndPoint.contains("TST")){
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_TST"),  props.getString("OOB_AUTH_PASSWORD_TST") ));
		}else{
			client.addFilter( new HTTPBasicAuthFilter( props.getString("OOB_AUTH_USER_NAME_SITX"),  props.getString("OOB_AUTH_PASSWORD_SITX") ));
		}
			WebResource webResource= client
					.resource(
							props.getString("OOB_END_POINT")
					+ "/transfermin");
			WebResource.Builder builder = webResource
					.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
		
			JsonObject input = Json
					.createObjectBuilder()
					.add("reseller_id", "1234")
					.add("imei", esnUtil.getCurrentESN().getEsn())
					.add("mobile_number", phoneUtil.getMinOfActiveEsn(oldesn))
					.add("old_iccid",oldiccid)
					.add("iccid",sim)
					.build();
			lStartTime= System.currentTimeMillis();
			ClientResponse response = builder.post(ClientResponse.class,input.toString());
			lEndTime= System.currentTimeMillis();
			
			long lDifference= lEndTime-lStartTime ;
			BufferedReader in = null;
			InputStream is = response.getEntityInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			StringBuffer out = new StringBuffer();
			String line;
			try {
				while ((line = in.readLine()) != null) {
					out.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			phoneUtil.insertIntoServiceResultsTable("REST_OOB_TransferMin",input.toString(),out,String.valueOf(lDifference), webResource.getURI().toString());
			
			if (response.getStatus() != 200) {
				System.out.println(response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
		//	String output = response.getEntity(String.class);
	}
	
	public Hashtable<String, Object> GetProductOffering(String partnerID, String category, String subcategory, String carrier, String cellTech, String zipCode, String productID) {
		
		String tech = cellTech;
		if(cellTech.equalsIgnoreCase("CDMA LTE")){
			tech = "CDMA";
		}
		
		Hashtable<String, Object> out = new Hashtable<String, Object>();
		try{
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));
			
			JsonObjectBuilder criteriaObj = Json.createObjectBuilder()
					.add("Carrier", carrier)
					.add("subCategory", subcategory)
					.add("zipCode", zipCode)
					.add("technology", tech);
			
			if(category.equalsIgnoreCase("SERVICE")){
				if(cellTech.equalsIgnoreCase("CDMA")){
					criteriaObj.add("esn",esnUtil.getCurrentESN().getEsn());
				} else if (!productID.isEmpty()) {
					criteriaObj.add("productID",productID);	
				}
			}
			
			WebResource webResource= client
						.resource(props.getString(props.getString("ENV.")+"GOAPI_END_POINT")
										+ props.getString(props.getString("ENV.")+"GOAPI_PRODUCT_OFFERING_RESOURCE"))
						.queryParam("sourceSystem", "API")
						.queryParam("brand", "NET10")
						.queryParam("channel", "B2B")
						.queryParam("partyTransactionID", "23454")
						.queryParam("partyID", partnerID)
						.queryParam("category", category)
						.queryParam("criteria", criteriaObj.build().toString())
						.queryParam("client_id", props.getString(props.getString("ENV.")+"GOAPI_CLIENT_ID"));
			
			String token = genAuthfor("GOAPI", "catalog management");
			
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

			logger.info(webResource.getURI());
					
			lStartTime = System.currentTimeMillis();
			ClientResponse response = builder.get(ClientResponse.class);
			lEndTime = System.currentTimeMillis();
			difference = lEndTime - lStartTime;
			logger.info("Elapsed milliseconds: " + difference);
			
			String responseString = response.getEntity(String.class);
			StringBuffer responseBuffer = new StringBuffer(responseString);
			
			String requestString = webResource.getURI().toString();
			
			phoneUtil.insertIntoServiceResultsTable("GOAPI - ProductOffering - " + category, requestString, responseBuffer, String.valueOf(difference), webResource.getURI().toString());

			if (response.getStatus() != 200 && response.getStatus() != 202 ) {
				logger.info("Failed : HTTP error code :" + response.getStatus());
				throw new RuntimeException("Failed : HTTP error code :" + response.getStatus());
			}

			JSONObject jsonRes = new JSONObject(responseString);
			
			
			if(category.equalsIgnoreCase("SIM_CARD")){
				
				JSONObject partyRes =  jsonRes.getJSONArray("relatedParties").getJSONObject(0).getJSONObject("party");
				String partySignature = partyRes.getJSONArray("partyExtension").getJSONObject(0).getString("value");
				String partyID = partyRes.getString("partyID");
				System.out.println(partySignature + "\n" + partyID);
				
				JSONObject productOffering = jsonRes.getJSONArray("productOffering").getJSONObject(0);
				String simID =  productOffering.getString("id");
				String simPart = productOffering.getJSONArray("productSpecification").getJSONObject(0).getString("id");
				float simPrice = BigDecimal.valueOf(productOffering.getJSONArray("productOfferingPrice").getJSONObject(0).getJSONObject("price").getDouble("dutyFreeAmount")).floatValue();
				
				out.put("partySignature", partySignature);
				out.put("partyID", partyID);
				out.put("simID",simID);
				out.put("simPart",simPart);
				out.put("simPrice",simPrice);
			}else if(category.equalsIgnoreCase("SERVICE")){
				if(cellTech.equalsIgnoreCase("CDMA")){
					JSONObject partyRes =  jsonRes.getJSONArray("relatedParties").getJSONObject(0).getJSONObject("party");
					String partySignature = partyRes.getJSONArray("partyExtension").getJSONObject(0).getString("value");
					String partyID = partyRes.getString("partyID");
					System.out.println(partySignature + "\n" + partyID);
					out.put("partySignature", partySignature);
					out.put("partyID", partyID);
				}
				
				JSONObject productOffering = jsonRes.getJSONArray("productOffering").getJSONObject(0);
				String planID =  productOffering.getString("id");
				String planPart = productOffering.getJSONArray("productSpecification").getJSONObject(0).getString("id");
				int planPrice = productOffering.getJSONArray("productOfferingPrice").getJSONObject(0).getJSONObject("price").getInt("dutyFreeAmount");
				
				out.put("planID",planID);
				out.put("planPart",planPart);
				out.put("planPrice",planPrice);
			}
			return out;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String callSmartPayServicewithRequestandEndpoint(String request,
			String endpointwsdl) throws Exception {
		
		SOAPConnection soapConnection = null;
		InputStream input = null;
		SOAPMessage soapResponse = null;
		try {
			input = new ByteArrayInputStream(request.getBytes());
            MessageFactory messageFactory = MessageFactory.newInstance();
            MimeHeaders headers = new MimeHeaders();

            SOAPMessage soapMessage = messageFactory.createMessage(headers, input);

            headers = soapMessage.getMimeHeaders();
            //fix
            headers.addHeader("SOAPAction", endpointwsdl);//  + serviceName);
            soapMessage.saveChanges();
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            lStartTime = System.currentTimeMillis();
            soapResponse = soapConnection.call(soapMessage, endpointwsdl);
            lEndTime = System.currentTimeMillis();
            difference = lEndTime - lStartTime;
        
            logger.info("Elapsed milliseconds: " + difference);
            esnUtil.getCurrentESN().putInMap("elpasedtime", String.valueOf(difference));
            
		} catch (SOAPException e) {
			e.printStackTrace();
//			throw(e);
		} finally {			
				try {
					if (soapConnection != null) {
						soapConnection.close();
					}
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {e.printStackTrace();}
			}
            return printSOAPResponse(soapResponse);
	}

	public String estimateOrder(String partnerID, Hashtable<String, Object> offering) {
		String orderID = null;
		
		try {
			Date javaUtilDate= new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client
					.resource(props.getString(props.getString("ENV.") + "GOAPI_END_POINT")
									+ props.getString(props.getString("ENV.")+"GOAPI_ORDER_MANAGEMENT_RESOURCE"))
					.queryParam("client_id",props.getString(props.getString("ENV.")	+ "GOAPI_CLIENT_ID"));
			
			JsonObject partyExtensionobj1 = Json.createObjectBuilder()
					.add("name","partySignature")
					.add("value",(String) offering.get("partySignature"))
					.build();
			
			JsonObject partyExtensionobj2 = Json.createObjectBuilder()
					.add("name","BrandName")
					.add("value", "NET10")
					.build();
			
			JsonObject partyExtensionobj3 = Json.createObjectBuilder()
					.add("name","Channel")
					.add("value", "B2B")
					.build();
			
			JsonObject relatedPartiesobj1 = Json.createObjectBuilder()
					.add("party",Json.createObjectBuilder()
							.add("partyID",(String) offering.get("partyID"))
							.add("partyExtension",Json.createArrayBuilder().add(partyExtensionobj1)))
					.add("roleType","buyerAdmin")
					.build();
			
			JsonObject relatedPartiesobj2 = Json.createObjectBuilder()
					.add("party",Json.createObjectBuilder()
							.add("partyID", partnerID)
							.add("partyExtension",Json.createArrayBuilder().add(partyExtensionobj2).add(partyExtensionobj3)))
					.add("roleType","partner")
					.build();
			
			JsonObject characteristicSpecificationsobj = Json.createObjectBuilder()
					.add("type","shipmentMode")
					.add("value","3 DAY SHIPPING")
					.build();
			
			JsonObject CharacteristicSpecificationsobj1 = Json.createObjectBuilder()
					.add("name","AUTOREFILL")
					.add("value","TRUE")
					.build();
			
			JsonObject orderItemsobj1 = Json.createObjectBuilder()
					.add("action","ESTIMATE")
					.add("id","1")
					.add("relatedOrderItem",Json.createObjectBuilder()
							.add("relationshipType", "relatedTo")
							.add("characteristicSpecification",Json.createObjectBuilder()
									.add("name","orderItemId")
									.add("value",(String) offering.get("planID"))))
					.add("orderItemPrice",Json.createObjectBuilder()
							.add("price", Json.createObjectBuilder()
									.add("amount",(Float) offering.get("simPrice"))
									.add("currencyCode","USD")))
					.add("productOffering",Json.createObjectBuilder()
							.add("id",(String) offering.get("simID"))
							.add("productSpecification", Json.createObjectBuilder()
									.add( "id",(String) offering.get("simPart"))
									.add("brand","NET10")))
					.add("quantity","1")
					.build();
			
			JsonObject orderItemsobj2 = Json.createObjectBuilder()
					.add("id","2")
					.add("action","ESTIMATE")
					.add("orderItemPrice",Json.createObjectBuilder()
							.add("price", Json.createObjectBuilder()
									.add("amount",(Integer) offering.get("planPrice"))
									.add("currencyCode","USD")))
					.add("productOffering",Json.createObjectBuilder()
							.add("id",(String) offering.get("planID"))
							.add("productSpecification", Json.createObjectBuilder()
									.add( "id",(String) offering.get("planPart"))
									.add("brand","NET10")))
					.add("CharacteristicSpecification",Json.createArrayBuilder().add(CharacteristicSpecificationsobj1))
					.add("quantity","1")
					.build();
			
			JsonObject productPromotionsobj = Json.createObjectBuilder()
					.add("id","TESTPROMO")
					.build();
			
			JsonObject locationobj = Json.createObjectBuilder()
					.add("addressType","SHIPPING")
					.add("address",Json.createObjectBuilder()
							.add("zipcode","33178"))
					.build();
			
			JsonObject locationobj1 = Json.createObjectBuilder()
					.add("addressType","BILLING")
					.add("address",Json.createObjectBuilder()
							.add("zipcode","33178"))
					.build();
			
			JsonObject input = Json.createObjectBuilder()
					.add("relatedParties",Json.createArrayBuilder()
							.add(relatedPartiesobj1)
							.add(relatedPartiesobj2))
					.add("externalID","123")
					.add("orderDate",formatter.format(javaUtilDate))
					.add("characteristicSpecifications",Json.createArrayBuilder().add(characteristicSpecificationsobj))
					.add("orderItems",Json.createArrayBuilder().add(orderItemsobj1).add(orderItemsobj2))
					.add("productPromotions",Json.createArrayBuilder().add(productPromotionsobj))
					.add("location",Json.createArrayBuilder().add(locationobj).add(locationobj1))
					.build();

			String token = genAuthfor("GOAPI", "order management");
			JSONObject jsonRes= callPostService(webResource, input, "GOAPI - EstimateOrder", token);
            orderID = jsonRes.getString("id");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderID;
	}
	
	public void getCoverage(String zipcode, String carrier) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		
		if (carrier == null || carrier.isEmpty()) {
			resource = "zipcode/" + zipcode + "/deviceType/" + account.getDeviceType();
		} else {
			resource = "zipcode/" + zipcode + "/deviceType/" + account.getDeviceType() + "/carrier/" + carrier;
		}

		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_COVERAGE") + 
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS")
				.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "IOS").queryParam("OSVersion", "10.0.2")
				.queryParam("language", "ENG").queryParam("sourceSystem", "APP");

		String token = genAuthforApp("CCU", "client_credentials", "order-mgmt", "", "", account.getBrand());
		callGetService(webResource, "REST-MyAccount_BYOPCoverage", token);
	}
	
	public void getServiceQualification(String zipcode, String carrier) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String resource;
		resource = "productSerialNumber/" + account.getEsn() + "/zipcode/" + zipcode + "/carrier/" + carrier;
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION") + resource);

		client.addFilter(new LoggingFilter(System.out));

		JsonObject input = Json.createObjectBuilder().add("common", getCommonObject()).build();

		String token = genAuthforApp("RO", "client_credentials", "service-qualification-mgmt", "", "", account.getBrand());
		callPostService(webResource, input, "REST-MyAccount_ServiceQualificationManagement", token);
	}
	
	public String phoneRegister(String esn, String sim, String brand, String zipcode) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_RESOURCE_INVENTORY"));
						
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231234234424").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "APP").build();

		JsonObject relatedPartyobj1 = Json
				.createObjectBuilder()
				.add("party", Json.createObjectBuilder()
						.add("partyID", "CUST_HASH")
						.add("individual", Json.createObjectBuilder()
								.add("id", "webobjid")))
				.add("roleType", "customer").build();

		JsonObject relatedPartyobj2 = Json
				.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyID", "TFAPP-ST")
						.add("languageAbility", "ENG")
						.add("partyExtension", Json.createArrayBuilder()
								.add(partyExtensionobj1)
								.add(partyExtensionobj2)))
				.add("roleType", "application").build();

		JsonObject supportingResourcesobj = Json.createObjectBuilder()
				.add("resourceCategory", "SIM_CARD")
				.add("resourceIdentifier", sim).build();
		
		JsonObject supportingLogicalResourcesobj = Json.createObjectBuilder()
				.add("resourceCategory", "CARRIER")
				.add("resourceIdentifier", "VZW").build();
		
		JsonObject input = Json
				.createObjectBuilder()
				.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj1).add(relatedPartyobj2))
				.add("resource", Json.createObjectBuilder()
						.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
						.add("association", Json.createObjectBuilder().add("role", "REGISTER"))
						.add("resourceSpecification", Json.createObjectBuilder().add("brand", brand))
						.add("physicalResource", Json.createObjectBuilder()
								.add("resourceCategory", "HANDSET")
								.add("resourceSubCategory", "BYOP")
								.add("serialNumber", esn)
								.add("supportingResources", Json.createArrayBuilder().add(supportingResourcesobj)))
						.add("supportingLogicalResources", Json.createArrayBuilder().add(supportingLogicalResourcesobj)))
				.build();
			
		String token = genAuthforApp("RO", "client_credentials", "inventory-mgmt", "", "", brand);
		return callPostService(webResource, input, "REST-MyAccount_PhoneRegister", token).toString();
	}
	
	public void productOrder(String ESN, String SIM, String MIN, String brand, String zipcode, String action){
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		
		WebResource webResource = null;
		if(ESN.isEmpty() || ESN.equals(null))
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER"));
		else
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER") + "esn/" + ESN);
		
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231234234424").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "APP").build();
		
		JsonObject relatedPartyobj1 = Json.createObjectBuilder()
				.add("party", Json.createObjectBuilder()
						.add("partyID", "CUST_HASH")
						.add("individual", Json.createObjectBuilder()
								.add("id", "webobjid")))
				.add("roleType", "customer").build();

		JsonObject relatedPartyobj2 = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyID", "TFAPP-ST")
						.add("languageAbility", "ENG")
						.add("partyExtension", Json.createArrayBuilder()
								.add(partyExtensionobj1)
								.add(partyExtensionobj2)))
				.add("roleType", "application").build();
		
		JsonObject productPromotionsObj = Json.createObjectBuilder().add("id", "asasdad123123").build();
		
		JsonObject relatedServicesObj = Json.createObjectBuilder()
				.add("id", "236")//need to pass service plan id
				.add("category", "SERVICE_PLAN")
				.add("characteristicSpecification", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("name", "relatedProgramId")
								.add("value", "5801321"))).build();
				
		JsonObject billingAddressObj = Json.createObjectBuilder()
				.add("addressLine1", "4526 Flamingo Rd")
				.add("addressLine2", "Apt No 234").add("city", "Hollywood")
				.add("stateOrProvince", "FL").add("zipCode", "33585")
				.add("country", "USA").build();
		
		JsonObject paymentMeanObj = Json
				.createObjectBuilder()
				.add("id", "2232322")
				.add("isDefault", true)
				.add("nickname", "MyChaseCC")
				.add("type", "CREDITCARD")
				.add("accountHolderName", "JOHN JACKSON")
				.add("firstName", "JOHN")
				.add("lastName", "JACKSON")
				.add("creditCard",
						Json.createObjectBuilder().add("year", 2019)
								.add("month", 8).add("cvv", 456)
								.add("accountNumber", "4012401240124012")
								.add("type", "VISA"))
				.add("billingAddress", billingAddressObj).build(); 
		
		JsonObject locationObj = Json.createObjectBuilder()
				.add("postalAddress", Json.createObjectBuilder()
						.add( "zipcode", zipcode)).build();
		
		JsonObject productObj = Json.createObjectBuilder()
				.add("productSpecification", Json.createObjectBuilder().add("brand", brand))
				.add("relatedServices", Json.createArrayBuilder().add(relatedServicesObj))
				.add("productSerialNumber", ESN)//ESN
				.add("productCategory", "HANDSET")
				.add("supportingResources", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("resourceType", "SIM_CARD")
								.add("serialNumber", SIM))).build();//sim
		
		JsonObject orderItemsObj = null;
		if("ACTIVATION".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
				.add("id", "1")
				.add("action", action)
				.add("quantity", "1")
				.add("productOffering", Json.createObjectBuilder().add("id", "ABCD123223").add("name", "Unlimited Talk Text 5 MB data"))//Need to pass proper id & name
				.add("product", productObj)
				.add("billingAccount", Json.createObjectBuilder()
						.add("paymentPlan", Json.createObjectBuilder()
								.add("isRecurring", false)
								.add("paymentMean", paymentMeanObj)))
				.add("location", locationObj)
				.add("orderItemExtension", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("name", "groupIdentifier")
								.add("value", "6461427268")))
				.build();
		}else if("PORT".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
					.add("id", "1")
					.add("action", action)
					.add("productOffering", Json.createObjectBuilder().add("id", "ABCD123223").add("name", "Unlimited Talk Text 5 MB data"))//Need to pass proper id & name
					.add("product", productObj)
					.add("orderItemExtension", getOrderItemExtensionForPort(MIN))
					.build();
		}
		
		JsonObject input = null;
		if("ACTIVATION".equalsIgnoreCase(action)){
			input = Json
					.createObjectBuilder()
					.add("externalID", "123")
					.add("orderDate", date_formatter.format(new Date()))
					.add("relatedParties",
							Json.createArrayBuilder().add(relatedPartyobj1)
									.add(relatedPartyobj2))
					.add("productPromotions",
							Json.createArrayBuilder().add(productPromotionsObj))
					.add("orderItems",
							Json.createArrayBuilder().add(orderItemsObj))
					.build();
		}else if("PORT".equalsIgnoreCase(action)){
			input = Json
					.createObjectBuilder()
					.add("externalID", "123")
					.add("orderDate", date_formatter.format(new Date()))
					.add("relatedParties",
							Json.createArrayBuilder().add(relatedPartyobj1)
									.add(relatedPartyobj2))
					.add("productPromotions",
							Json.createArrayBuilder().add(productPromotionsObj))
					.add("orderItems",
							Json.createArrayBuilder().add(orderItemsObj))
					.add("quantity", "1")
					.add("billingAccount", Json.createObjectBuilder()
							.add("paymentPlan", Json.createObjectBuilder()
									.add("isRecurring", true)
									.add("paymentMean", paymentMeanObj)))
					.add("location", locationObj)
					.build();
		}
		
		
		String token = genAuthforApp("RO", "client_credentials", "order-mgmt", "", "", brand);
		callPostService(webResource, input, "REST-MyAccount_ProductOrder", token);
	}
	
	public void serviceOrder(String action, String ESN, String MIN, String SIM, String brand, String airtimePin, String zipcode){
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_SERVICE_ORDER"));
		
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231234234424").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "APP").build();
		JsonObject relatedPartyobj1 = Json.createObjectBuilder()
				.add("party", Json.createObjectBuilder()
						.add("partyID", "CUST_HASH")
						.add("individual", Json.createObjectBuilder()
								.add("id", "webobjid")))
				.add("roleType", "customer").build();

		JsonObject relatedPartyobj2 = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyID", "TFAPP-ST")
						.add("languageAbility", "ENG")
						.add("partyExtension", Json.createArrayBuilder()
								.add(partyExtensionobj1)
								.add(partyExtensionobj2)))
				.add("roleType", "application").build();
		
		JsonObject productPromotionsObj = Json.createObjectBuilder().add("id", "asasdad123123").build();
		
		JsonObject orderItemsObj = null;
		if("ACTIVATION".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("product", Json.createObjectBuilder()
							.add("productSerialNumber", ESN)
							.add("productCategory", "HANDSET")
							.add("productSpecification", Json.createObjectBuilder()
									.add("brand", brand))
							.add("relatedServices", Json.createObjectBuilder()
									.add("id", "236")
									.add("category", "SERVICE_PLAN"))
							.add("supportingResources", Json.createArrayBuilder()
									.add(Json.createObjectBuilder()
											.add("resourceType", "AIRTIME_CARD")
											.add("productIdentifier", airtimePin))
									.add(Json.createObjectBuilder()
											.add("resourceType", "SIM_CARD")
											.add("serialNumber", SIM))))
					.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
					.add("orderItemExtension", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("name", "groupIdentifier")
									.add("value", "12345"))
							.add(Json.createObjectBuilder()
									.add("name", "leaseApplicationId")
									.add("value", "AVF12345")))
					.build();
		}
		else if("PROCESS".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("orderItemExtension", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("name", "groupIdentifier")
									.add("value", "12345")))
					.build();
		}
		else if("PHONE_UPGRADE".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("product", Json.createObjectBuilder()
							.add("productSerialNumber", ESN)
							.add("productCategory", "HANDSET")
							.add("productSpecification", Json.createObjectBuilder()
									.add("brand", brand))
							.add("supportingResources", Json.createArrayBuilder()
									.add(Json.createObjectBuilder()
											.add("resourceType", "AIRTIME_CARD")
											.add("productIdentifier", airtimePin))
									.add(Json.createObjectBuilder()
											.add("resourceType", "SIM_CARD")
											.add("serialNumber", SIM))))
					.add("orderItemExtension", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("name", "currentMIN")
									.add("value", MIN)))
					.add("id", "1")
					.build();
		}
		else if("PORT".equalsIgnoreCase(action)){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("product", Json.createObjectBuilder()
							.add("productSerialNumber", ESN)
							.add("productCategory", "HANDSET")
							.add("productSpecification", Json.createObjectBuilder()
									.add("brand", brand))
							.add("supportingResources", Json.createArrayBuilder()
									.add(Json.createObjectBuilder()
											.add("resourceType", "AIRTIME_CARD")
											.add("productIdentifier", airtimePin))
									.add(Json.createObjectBuilder()
											.add("resourceType", "SIM_CARD")
											.add("serialNumber", SIM))))
					.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
					.add("orderItemExtension", getOrderItemExtensionForPort(MIN))
					.add("id", "1")
					.add("relatedServices", Json.createArrayBuilder().add(Json.createObjectBuilder().add("id", "236").add("category", "SERVICE_PLAN")))
					.add("productPromotions", Json.createArrayBuilder().add(Json.createObjectBuilder().add("id", "NEWYEARTF")))
					.add("quantity", "1")
					.build();
		}
		
		JsonObject input = null;
		if("ACTIVATION".equalsIgnoreCase(action) || "PORT".equalsIgnoreCase(action)){
			input = Json.createObjectBuilder()
				.add("externalID", "123")
				.add("orderDate", date_formatter.format(new Date()))
				.add("relatedParties", Json.createArrayBuilder()
						.add(relatedPartyobj1)
						.add(relatedPartyobj2))
				.add("productPromotions", Json.createArrayBuilder().add(productPromotionsObj))
				.add("orderItems", Json.createArrayBuilder().add(orderItemsObj))
				.build();
		}
		else if ("PROCESS".equalsIgnoreCase(action) || "PHONE_UPGRADE".equalsIgnoreCase(action)){
			input = Json.createObjectBuilder()
					.add("externalID", "123")
					.add("orderDate", date_formatter.format(new Date()))
					.add("relatedParties", Json.createArrayBuilder()
							.add(relatedPartyobj1)
							.add(relatedPartyobj2))
					.add("orderItems", Json.createArrayBuilder().add(orderItemsObj))
					.build();
		}
		
		String token = genAuthforApp("RO", "client_credentials", "order-mgmt", "", "", brand);
		callPostService(webResource, input, "REST-MyAccount_ServiceOrder", token);
	}
	
	public void serviceQualification(String serviceCategory, String zipcode, String brand){
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
				
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_SERVICE_QUALIFICATION"));
		
		JsonObject partyExtensionobj1 = Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "1231234234424").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder().add("name", "sourceSystem").add("value", "APP").build();
		
		JsonObject relatedPartyobj = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder()
						.add("partyID", "TFAPP-ST")
						.add("languageAbility", "ENG")
						.add("partyExtension", Json.createArrayBuilder()
								.add(partyExtensionobj1)
								.add(partyExtensionobj2)))
				.add("roleType", "application").build();
		
		
		JsonObject input = null;
		if("ACTIVATION".equalsIgnoreCase(serviceCategory)){
			input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj))
					.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
					.add("serviceCategory", Json.createArrayBuilder().add(Json.createObjectBuilder().add("type","context").add("value",serviceCategory)))
					.add("serviceSpecification", Json.createObjectBuilder().add("brand", brand))
					.add("relatedResources", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("serialNumber", "6454654546454654")
									.add("name", "productSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "90126454654546254654")
									.add("name", "serialNumber")
									.add("type", "SIM_CARD"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "8902645465454645434")
									.add("name", "sourceProductSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "8902645465454645434")
									.add("name", "targetProductSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "89026454654546454634")
									.add("name", "sourceSerialNumber")
									.add("type", "SIM_CARD"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "3078651234")
									.add("name", "currentMin")
									.add("type", "LINE"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "89026454654546454634")
									.add("name", "targetSerialNumber")
									.add("type", "SIM_CARD")))
					.build();
		}else if("SERVICE_COMPATIBILITY".equalsIgnoreCase(serviceCategory)){
			input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj))
					.add("serviceCategory", Json.createArrayBuilder().add(Json.createObjectBuilder().add("type","context").add("value",serviceCategory)))
					.add("serviceSpecification", Json.createObjectBuilder().add("brand", brand))
					.add("relatedResources", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("serialNumber", "6454654546454654")
									.add("name", "productSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "890767125616262")
									.add("name", "serialNumber")
									.add("type", "SIM_CARD"))
							.add(Json.createObjectBuilder()
									.add("productIdentifier", "9999799192919291")
									.add("type", "AIRTIME_CARD")))
					.build();
		}else if("PORT_COVERAGE".equalsIgnoreCase(serviceCategory)){
			input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj))
					.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
					.add("serviceCategory", Json.createArrayBuilder().add(Json.createObjectBuilder().add("type","context").add("value",serviceCategory)))
					.add("serviceSpecification", Json.createObjectBuilder().add("brand", brand))
					.add("relatedResources", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("serialNumber", "6454654546454654")
									.add("name", "productSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "90126454654546454654")
									.add("name", "serialNumber")
									.add("type", "SIM_CARD"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "3078651234")
									.add("name", "currentMin")
									.add("type", "LINE")))
					.build();
		}else if("TECHNOLOGY".equalsIgnoreCase(serviceCategory)){
			input = Json.createObjectBuilder()
					.add("relatedParties", Json.createArrayBuilder().add(relatedPartyobj))
					.add("location", Json.createObjectBuilder().add("postalAddress", Json.createObjectBuilder().add("zipcode", zipcode)))
					.add("serviceCategory", Json.createArrayBuilder().add(Json.createObjectBuilder().add("type","context").add("value",serviceCategory)))
					.add("serviceSpecification", Json.createObjectBuilder().add("brand", brand))
					.add("relatedResources", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("serialNumber", "6454654546454654")
									.add("name", "productSerialNumber")
									.add("type", "HANDSET"))
							.add(Json.createObjectBuilder()
									.add("serialNumber", "90126454654546454654")
									.add("name", "serialNumber")
									.add("type", "SIM_CARD")))
					.build();
		}
		
		String token = genAuthforApp("RO", "client_credentials", "service-qualification-mgmt", "", "", brand);
		callPostService(webResource, input, "REST-MyAccount_ServiceQualification", token);
	}
	
	public JsonObject getOrderItemExtensionForPort(String MIN){
		
		JsonObject orderItemExtensionPort = (JsonObject) Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
						.add("name", "currentMIN")
						.add("value", MIN))
				.add(Json.createObjectBuilder()
						.add("name", "handsetLast4Digits")
						.add("value", "1234"))
				.add(Json.createObjectBuilder()
						.add("name", "currentServiceProvider")
						.add("value", "VZW"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAccountNumber")
						.add("value", "1234445"))
				.add(Json.createObjectBuilder()
						.add("name", "currentPin")
						.add("value", "5462"))
				.add(Json.createObjectBuilder()
						.add("name", "currentVKey")
						.add("value", "1234445"))
				.add(Json.createObjectBuilder()
						.add("name", "currentFullName")
						.add("value", "1234445"))
				.add(Json.createObjectBuilder()
						.add("name", "currentCarrierType")
						.add("value", "WIRELESS"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAddressLine1")
						.add("value", "5646 sw 107th ave"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAddressLine2")
						.add("value", "apt no 235"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAddressCity")
						.add("value", "Miami"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAddressState")
						.add("value", "FL"))
				.add(Json.createObjectBuilder()
						.add("name", "currentAddressZip")
						.add("value", "33178"))
				.add(Json.createObjectBuilder()
						.add("name", "currentCarrier")
						.add("value", "NTELOS"))
				.add(Json.createObjectBuilder()
						.add("name", "ssnLast4Digits")
						.add("value", "2222"))
				.add(Json.createObjectBuilder()
						.add("name", "groupIdentifier")
						.add("value", "6461427268"))
				.add(Json.createObjectBuilder()
						.add("name", "leaseApplicationId")
						.add("value", "AVF12345")).build();
		return orderItemExtensionPort;
		
	}

	public void renewalEnquiry(ResourceType BANorLINE) {
	
		if (account.getBrand().equalsIgnoreCase("WFM")){
			client = Client.create();
			
			String ID;
			if (ResourceType.BAN.equals(BANorLINE)) {
				ID =phoneUtil.getBANFromWebObj(Integer.toString(account.getAccountId()));
			} else {
				ID = account.getMin();
			}
			
			WebResource webResource = client
					.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_RENEWAL_ENQUIRY"))
					.queryParam("identifier",ID).queryParam("category",BANorLINE.toString()).queryParam("lineInquiry", "true").queryParam("zipcode", "33178")
					.queryParam("OSVersion", "android_15").queryParam("brandName", account.getBrand()).queryParam("clientAppName", "com.tracfone.totalwireless.myaccount")
					.queryParam("clientAppVersion", "1.0").queryParam("deviceModel", "Android").queryParam("language", "ENG").queryParam("sourceSystem", "WEB");
	
			client.addFilter(new LoggingFilter(System.out));
	
			String token = genAuthforApp("RO", "client_credentials", "billing-mgmt", "", "", account.getBrand());
			callGetService(webResource, "REST-MyAccount_billing-mgmt", token);
		}
				
	}

	public void getESNAttributes(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource;
		
		if (ResourceType.MIN.equals(minOrEsn)) {
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_VALIDATE_USER_DEVICE")
									+ "min/" + account.getMin())
									.queryParam("brandName", account.getBrand())
									.queryParam("sourceSystem","WEB")
									.queryParam("language", "ENG");
		}
		else{
			webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_VALIDATE_USER_DEVICE")
									+ "esn/"+ account.getEsn())
									.queryParam("brandName", account.getBrand())
									.queryParam("sourceSystem","WEB")
									.queryParam("language", "ENG");
		}
		String token = genAuthforApp("CCP", "client_credentials", "validateUserDevice", "", "", account.getBrand());
		callGetService(webResource, "REST-WFM_GetESNAttiributes", token);
	}

	
}
