package com.tracfone.twist.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.tracfone.twist.utils.ServiceUtil.ResourceType;
import com.tracfone.twist.utils.gson.BankAccount;
import com.tracfone.twist.utils.gson.BillingAccount;
import com.tracfone.twist.utils.gson.CreditCard;
import com.tracfone.twist.utils.gson.ProductOrderRequest;
import com.tracfone.twist.utils.gson.CommonWrapper;
import com.tracfone.twist.utils.gson.ServiceQualificationRequest;

public class WfmUtil {

	private static Logger logger = LogManager.getLogger(ServiceUtil.class
			.getName());
	private ResourceBundle props = ResourceBundle.getBundle("Utils");
	@Autowired
	private ServiceUtil serviceUtil;
	@Autowired
	private Account account;
	@Autowired
	private ESNUtil esnUtil;
	@Autowired
	private PhoneUtil phoneUtil;
	public Client client;
	private SimpleDateFormat dateFormat;
	private Calendar cal;
	long lStartTime;
	long lEndTime;
	long difference;
	private Gson gson = new Gson();

	public WfmUtil() {

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

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}

			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());

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

	public JsonObject getWebObjidPartyObject() {

		JsonObject partyobj = Json
				.createObjectBuilder()

				.add("party",
						Json.createObjectBuilder().add(
								"individual",
								Json.createObjectBuilder()
										.add("id",
												Integer.toString(account
														.getAccountId()))))
				.add("roleType", "customer")
				// .add("partyExtension",Json.createArrayBuilder()
				// .add(Json.createObjectBuilder()
				// .add("name","partySecret")
				// .add("value" ,"1231"))))
				.build();

		return partyobj;

	}

	public JsonObject getSOWebObjidPartyObj() {

		JsonObject partyobj = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("individual",
										Json.createObjectBuilder().add(
												"id",
												Integer.toString(account
														.getAccountId())))
								.add("partyExtension",
										Json.createArrayBuilder()
												.add(Json
														.createObjectBuilder()
														.add("name",
																"partySecret")
														.add("value",
																account.getSecurityPin()))))
				.add("roleType", "customer").build();

		return partyobj;
	}

	public JsonObject getPartnerPartyObjectForPortAndUpgrade() {

		JsonObject partyExtensionobj = Json.createObjectBuilder()
				.add("name", "partySecret").add("value", "1234").build();

		JsonObject partyExtensionobj1 = Json.createObjectBuilder()
				.add("name", "partyTransactionID")
				.add("value", "1231234234424").build();

		JsonObject partyExtensionobj2 = Json.createObjectBuilder()
				.add("name", "sourceSystem").add("value", "WEB").build();

		// JsonObject partyExtensionobj3 =
		// Json.createObjectBuilder().add("name", "accountEmail").add("value",
		// "260642101780380WFM@GMAIL.COM").build();

		JsonObject partyobj2 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("partyID", "WFMWEB")
								.add("languageAbility", "ENG")
								.add("individual",
										Json.createObjectBuilder().add(
												"id",
												Integer.toString(account
														.getAccountId())))
								// .add("_firstName", "Rajiv")
								// .add("_lastName", "Chau"))
								.add("partyExtension",
										Json.createArrayBuilder()
												// .add(Json.createObjectBuilder())
												.add(partyExtensionobj)
												.add(partyExtensionobj1)
												.add(partyExtensionobj2)))
				.add("roleType", "customer").build();

		return partyobj2;

	}

	public JsonObject getPartnerPartyObject() {

		JsonObject partyExtensionobj = Json.createObjectBuilder()
				.add("name", "partyTransactionID")
				.add("value", "1231234234424").build();

		JsonObject partyExtensionobj1 = Json.createObjectBuilder()
				.add("name", "sourceSystem").add("value", "WEB").build();

		JsonObject partyobj2 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("partyID", "WFMWEB")
								.add("languageAbility", "ENG")
								.add("partyExtension",
										Json.createArrayBuilder()
												.add(partyExtensionobj)
												.add(partyExtensionobj1)))
				.add("roleType", "internal").build();

		return partyobj2;

	}

	public JsonObject getCommonObject() {

		JsonObject commonObj = Json
				.createObjectBuilder()
				.add("brandName", account.getBrand())
				.add("language", "ENG")
				.add("sourceSystem", "WEB")
				.add("partyTransactionID",
						TwistUtils.createRandomLong(1000, 1000000)).build();

		return commonObj;

	}

	public void addBillingDiscount(String min, String email) {
		client = Client.create();
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_BILLING_DISCOUNT"));

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		JsonObject relatedObj1 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder().add(
								"partyExtension",
								Json.createArrayBuilder()
										.add(Json
												.createObjectBuilder()
												.add("name",
														"partyTransactionID")
												.add("value", "1434"))
										.add(Json.createObjectBuilder()
												.add("name", "deviceSecret")
												.add("value", "1234"))))
				.add("roleType", "customer").build();

		JsonObject relatedObj2 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("partyID", "WEB")
								.add("languageAbility", "ENG")
								.add("partyExtension",
										Json.createArrayBuilder()
												.add(Json
														.createObjectBuilder()
														.add("name",
																"partyTransactionID")
														.add("value", "1434"))
												.add(Json
														.createObjectBuilder()
														.add("name",
																"sourceSystem")
														.add("value", "WEB"))))
				.add("roleType", "internal").build();

		JsonObject productObj = Json
				.createObjectBuilder()
				.add("productSerialNumber", min)
				.add("productCategory", "LINE")
				.add("productSpecification",
						Json.createObjectBuilder().add("brand", "WFM")).build();

		JsonObject discountObj = Json.createObjectBuilder()
				.add("name", "employeeDiscount")
				.add("value", "EMPLOYEE_DISCOUNT").build();

		JsonObject input = Json
				.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request",
						Json.createObjectBuilder()
								.add("date", dateFormat.format(date))
								.add("relatedParties",
										Json.createArrayBuilder()
												.add(relatedObj1)
												.add(relatedObj2))
								.add("product", productObj)
								.add("discounts",
										Json.createArrayBuilder().add(
												discountObj))).build();

		account.setEmail(email);
		account.setPassword("tracfone");

		String token = serviceUtil.genAuthforApp("CCU", "client_credentials",
				"billing-mgmt", "", "", "WFM");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input,
				"REST-MyAccount_CreateNewAccount", token);

		System.out.println(input.toString());
	}

	public void serviceQualificationByNapAndPin(String action, String Zipcode,
			String PinPart) {

		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		JsonObject reqObj;
		WebResource webResource = client.resource(props.getString(props
				.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION"));
		JsonObject partyExtensionobj1 = Json.createObjectBuilder()
				.add("name", "_partySecret").add("value", "1234").build();
		JsonObject partyExtensionobj2 = Json.createObjectBuilder()
				.add("name", "_partyTransactionID")
				.add("value", "1231234234424").build();
		JsonObject partyExtensionobj3 = Json.createObjectBuilder()
				.add("name", "sourceSystem").add("value", "WEB").build();
		JsonObject relatedPartiesobj1 = Json
				.createObjectBuilder()
				.add("party",
						Json.createObjectBuilder()
								.add("partyID", "WFMWEB|WFMAPP")
								.add("languageAbility", "ENG")
								.add("individual",
										Json.createObjectBuilder()
												.add("id", "WEBOBJID")
												.add("firstName", "Rajiv")
												.add("lastName", "Chau"))
								.add("partyExtension",
										Json.createArrayBuilder()
												.add(partyExtensionobj1)
												.add(partyExtensionobj2)
												.add(partyExtensionobj3)))
				.add("roleType", "customer").build();

		JsonObject input = null;
		if ("NAP".equalsIgnoreCase(action)) {
			input = Json
					.createObjectBuilder()
					.add("common", getCommonObject())
					.add("request",
							Json.createObjectBuilder()

									.add("relatedParties",
											Json.createArrayBuilder().add(
													relatedPartiesobj1))
									.add("location",
											Json.createObjectBuilder().add(
													"postalAddress",
													Json.createObjectBuilder()
															.add("zipcode",
																	Zipcode)))
									.add("serviceCategory",
											Json.createArrayBuilder()
													.add(Json
															.createObjectBuilder()
															.add("type",
																	"context")
															.add("_value",
																	"ACTIVATION|PORT_COVERAGE|TECHNOLOGY")
															.add("value",
																	"TECHNOLOGY")))
									.add("serviceSpecification",
											Json.createObjectBuilder()
													.add("brand",
															account.getBrand()))
									.add("relatedResources",
											Json.createArrayBuilder()
													.add(Json
															.createObjectBuilder()
															.add("serialNumber",
																	account.getEsn())
															.add("name",
																	"productSerialNumber")
															.add("type",
																	"HANDSET"))
													.add(Json
															.createObjectBuilder()
															.add("serialNumber",
																	account.getSim())
															.add("name",
																	"serialNumber")
															.add("type",
																	"SIM_CARD"))))

					.build();
			System.out.println("call Token");
		} else if ("AIRTIMEPIN".equalsIgnoreCase(action)) {
			input = Json
					.createObjectBuilder()
					.add("common", getCommonObject())
					.add("request",
							Json.createObjectBuilder()

									.add("relatedParties",
											Json.createArrayBuilder().add(
													relatedPartiesobj1))
									.add("location",
											Json.createObjectBuilder().add(
													"postalAddress",
													Json.createObjectBuilder()
															.add("zipcode",
																	Zipcode)))
									.add("serviceCategory",
											Json.createArrayBuilder()
													.add(Json
															.createObjectBuilder()
															.add("type",
																	"context")
															.add("_value",
																	"ACTIVATION|PORT_COVERAGE")
															.add("value",
																	"SERVICE_COMPATIBILITY")))
									.add("serviceSpecification",
											Json.createObjectBuilder()
													.add("brand",
															account.getBrand()))
									.add("relatedResources",
											Json.createArrayBuilder()
													.add(Json
															.createObjectBuilder()
															.add("serialNumber",
																	account.getEsn())
															.add("name",
																	"productSerialNumber")
															.add("type",
																	"HANDSET"))
													.add(Json
															.createObjectBuilder()
															.add("serialNumber",
																	account.getSim())
															.add("name",
																	"serialNumber")
															.add("type",
																	"SIM_CARD"))
													.add(Json
															.createObjectBuilder()
															// .add("productIdentifier",
															// PinPart)
															.add("serialNumber",
																	PinPart)
															.add("name",
																	"serialNumber")
															.add("type",
																	"AIRTIME_CARD"))))
					.build();
		}
		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"service-qualification-mgmt", "", "", account.getBrand());
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input,
				"REST-Service Qualification", token);
		try {
			JSONObject myResponse = jsonRes.getJSONObject("response");
			// int accId = myResponse.getInt("accountId");
			// account.setAccountId(accId);
		} catch (JSONException e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	public void productorderachact() {

		account.setFlowName("actwithcc");
		String type = null;
		String action = null;

		if (account.getFlowName().equalsIgnoreCase("actwithcc")) {
			type = "ACTIVATE_LINE";
			action = "ACTIVATION";

		} else if (account.getFlowName().equalsIgnoreCase("reactwithcc")) {
			type = "REACTIVATE_LINE";
			action = "REACTIVATION";
		}

		client = Client.create();
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER"));

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		JsonObject productSpecificObj = Json.createObjectBuilder()
				.add("id", "WFMU0049").add("brand", "WFM").build();

		JsonObject productSpecificObj1 = Json.createObjectBuilder()
				.add("id", "WFMU0049").add("brand", "WFM").build();

		JsonObject ProductOfferingObj = Json.createObjectBuilder()
				.add("id", "WFMAPPU0049")
				.add("name", "WFM Unlimited Talk and Text 5GB")
				.add("productSpecification", productSpecificObj)

				.build();

		JsonObject relatedServiceObj = null;
		if (account.getFlowName().equalsIgnoreCase("actwithcc")) {

			relatedServiceObj = Json.createObjectBuilder().add("id", "484")
					.add("category", "SERVICE_PLAN").build();

		} else if (account.getFlowName().equalsIgnoreCase("actwithcc")) {
			relatedServiceObj = Json
					.createObjectBuilder()
					.add("id", "236")
					.add("category", "SERVICE_PLAN")
					.add("characteristicSpecification",
							Json.createArrayBuilder().add(
									Json.createObjectBuilder()
											.add("name", "relatedProgramId")
											.add("value", "5801321"))).build();

		}

		JsonObject supportingResourceObj = Json.createObjectBuilder()
				.add("resourceType", "SIM_CARD")
				.add("serialNumber", account.getSim()).build();

		JsonObject orderItemsObj = Json
				.createObjectBuilder()
				.add("id", "1")
				.add("action", action)
				.add("quantity", "1")
				.add("productOffering", ProductOfferingObj)
				.add("product",
						Json.createObjectBuilder()
								.add("productSpecification",
										productSpecificObj1)
								.add("relatedServices",
										Json.createArrayBuilder().add(
												relatedServiceObj))
								.add("productSerialNumber", account.getEsn())
								.add("productCategory", "HANDSET")
								.add("supportingResources",
										Json.createArrayBuilder().add(
												supportingResourceObj)))
				// .add("customerAccount",Json.createObjectBuilder().add("pin",
				// "1234"))
				.add("location",
						Json.createObjectBuilder().add(
								"postalAddress",
								Json.createObjectBuilder().add("zipcode",
										"33178"))).build();

		JsonObject billingAddrObj = Json.createObjectBuilder()
				.add("addressLine1", "1295 charleston rd")
				.add("addressLine2", "").add("city", "Mountain View")
				.add("stateOrProvince", "CA").add("zipCode", "94043")
				.add("country", "USA").build();

		JsonObject paymentMeanObj = null;

		if (account.getFlowName().equalsIgnoreCase("actwithach")) {
			paymentMeanObj = Json
					.createObjectBuilder()
					.add("id", "1000000623")
					.add("isDefault", true)
					.add("savePaymentInfo", true)
					.add("nickname", "test")
					.add("type", "ACH")
					.add("firstName", "Murali")
					.add("lastName", "kan")
					.add("bankAccount",
							Json.createObjectBuilder()
									.add("accountNumber", "121042882")
									.add("routingNumber", "4103")
									.add("type", "CHECKING"))
					.add("billingAddress", billingAddrObj).build();
		} else if (account.getFlowName().equalsIgnoreCase("actwithcc")) {
			paymentMeanObj = Json
					.createObjectBuilder()
					.add("id", "1000000623")
					.add("isDefault", true)
					.add("savePaymentInfo", true)
					.add("nickname", "test")
					.add("type", "CREDITCARD")
					.add("firstName", "Murali")
					.add("lastName", "kan")
					.add("creditCard",
							Json.createObjectBuilder().add("year", "2021")
									.add("month", "08").add("cvv", "123")
									.add("accountNumber", "4485300368377065")
									.add("type", "VISA"))
					.add("billingAddress", billingAddrObj).build();
		}

		JsonObject billingAccObj = Json
				.createObjectBuilder()
				.add("id", "1000000623")
				.add("paymentPlan",
						Json.createObjectBuilder().add("isRecurring", false)
								.add("paymentMean", paymentMeanObj))// .add("billingAddress",
																	// billingAddrObj))
				.build();

		JsonObject input = Json
				.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request",
						Json.createObjectBuilder()
								.add("externalID", "WFMITQ999")
								.add("orderDate", dateFormat.format(date))
								.add("relatedParties",
										Json.createArrayBuilder()
												.add(getWebObjidPartyObject())
												.add(getPartnerPartyObject()))
								.add("type", type)
								.add("orderItems",
										Json.createArrayBuilder().add(
												orderItemsObj))
								.add("billingAccount", billingAccObj)).build();

		// account.setEmail("hmby40ekt@tracfone.com");
		// account.setPassword("tracfone");

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"order-mgmt", "", "", "WFM");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input,
				"REST-MyAccount_CreateNewAccount", token);
		System.out.println(input.toString());

		/*
		 * try { // JSONObject myResponse = jsonRes.getJSONObject("response");
		 * // int accId = myResponse.getInt("accountId"); //
		 * account.setAccountId(accId); } catch (JSONException e) {
		 * logger.info(e.getMessage()); throw new RuntimeException(e); }
		 */

	}

	public void serviceOrder(String pin, String action) {
		client = Client.create();
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT")+ props.getString("APP_SERVICE_ORDER"));

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		JsonObject orderItemsObj = null;
		if(action.equalsIgnoreCase("ACTIVATION")){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("customerAccount",Json.createObjectBuilder().add("pin", "5678"))
					.add("location",Json.createObjectBuilder().add("postalAddress",Json.createObjectBuilder().add("zipcode", "33178")))
					.add("product", Json.createObjectBuilder()
							.add("productCategory", "HANDSET")
							.add("productSerialNumber",account.getEsn())
							.add("productSpecification",Json.createObjectBuilder().add("brand", account.getBrand()))
//							.add("relatedServices",Json.createArrayBuilder().add(Json.createObjectBuilder().add("category", "SERVICE_PLAN").add("id", "482")))
							.add("supportingResources",Json.createArrayBuilder()
									.add(Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD").add("serialNumber", pin))
									.add(Json.createObjectBuilder().add("resourceType", "SIM_CARD").add("serialNumber", account.getSim()))))
					.build();
		}else if(action.equalsIgnoreCase("REACTIVATION")){
			orderItemsObj = Json.createObjectBuilder()
					.add("action", action)
					.add("customerAccount",Json.createObjectBuilder().add("pin", "5678"))
					.add("location",Json.createObjectBuilder().add("postalAddress",Json.createObjectBuilder().add("zipcode", "33178")))
					.add("product", Json.createObjectBuilder()
							.add("productCategory", "HANDSET")
							.add("productSerialNumber",account.getEsn())
							.add("productSpecification",Json.createObjectBuilder().add("brand", "WFM"))
							.add("supportingResources",Json.createArrayBuilder()
									.add(Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD").add("serialNumber", pin))
									.add(Json.createObjectBuilder().add("resourceType", "LINE").add("productIdentifier",account.getMin()))))
					.build();
		}else if(action.equalsIgnoreCase("REDEMPTION")){
			orderItemsObj = Json.createObjectBuilder().add("id", "1")
					.add("action", action)
					.add("customerAccount",Json.createObjectBuilder().add("pin", "5678"))
					.add("location",Json.createObjectBuilder().add("postalAddress",Json.createObjectBuilder().add("zipcode", "33178")))
					.add("product", Json.createObjectBuilder()
							.add("productCategory", "HANDSET")
							.add("productSerialNumber",account.getEsn())
							.add("productSpecification",Json.createObjectBuilder().add("brand", "WFM"))
							.add("supportingResources",Json.createArrayBuilder()
									.add(Json.createObjectBuilder().add("resourceType", "AIRTIME_CARD").add("serialNumber", pin))))
					.build();
		}

		JsonObjectBuilder req = Json.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request",
						Json.createObjectBuilder()
								.add("externalID", "WFMITQ999")
								.add("orderDate", dateFormat.format(date))
								.add("relatedParties",
										Json.createArrayBuilder()
												.add(getSOWebObjidPartyObj())
												.add(getPartnerPartyObject()))
								.add("orderItems", Json.createArrayBuilder()
												.add(orderItemsObj)));
		
		if(!action.equalsIgnoreCase("ACTIVATION")){
			req.add("billingAccount", Json.createObjectBuilder()
					.add( "id", account.getBanID()));
		}

		JsonObject input= req.build();
		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"order-mgmt", "", "",account.getBrand());
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input,
				"REST-"+account.getBrand()+" Service Order Act With Pin", token);

		System.out.println(input.toString());
	}

	public void serviceOrderPotrsAndUpgrade(String Pin, String flowName) {
		account.setFlowName(flowName);
		String type = null;
		String action = null;

		if (account.getFlowName().equalsIgnoreCase("InternalPort")) {
			type = "ACTIVATE_BAN";
			action = "PORT";

		} else if (account.getFlowName().equalsIgnoreCase("ExternalPort")) {
			type = "ACTIVATE_LINE";
			action = "PORT";
		} else if (account.getFlowName().equalsIgnoreCase("PhoneUpgrade")) {
			type = "ACTIVATE_LINE";
			action = "PHONE_UPGRADE";
		}
		client = Client.create();
		WebResource webResource = client
				.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_SERVICE_ORDER"));

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		JsonObject productspecobj = Json.createObjectBuilder()
				.add("brand", account.getBrand()).build();

		JsonObject supportingResObj = Json.createObjectBuilder()
				.add("resourceType", "AIRTIME_CARD").add("serialNumber", Pin)
				.build();

		JsonObject supportingResObj1 = Json.createObjectBuilder()
				.add("resourceType", "SIM_CARD")
				.add("serialNumber", account.getSim()).build();

		JsonObject productobj = null;
		if (account.getFlowName().equalsIgnoreCase("ExternalPort")
				|| account.getFlowName().equalsIgnoreCase("InternalPort")) {
			productobj = Json
					.createObjectBuilder()
					.add("productSerialNumber", account.getEsn())
					.add("productCategory", "HANDSET")
					.add("subCategory", "BYOP")
					.add("productSpecification", productspecobj)
					.add("supportingResources",
							Json.createArrayBuilder().add(supportingResObj)
									.add(supportingResObj1)).build();
		} else if (account.getFlowName().equalsIgnoreCase("PhoneUpgrade")) {
			productobj = Json
					.createObjectBuilder()
					.add("productSerialNumber", account.getEsn())
					.add("productCategory", "HANDSET")
					.add("subCategory", "BYOP")
					.add("productSpecification", productspecobj)
					.add("supportingResources",
							Json.createArrayBuilder().add(supportingResObj1))
					.build();
		}

		JsonObject orderItemExtensionobj = null;
		if (account.getFlowName().equalsIgnoreCase("ExternalPort")) {
			orderItemExtensionobj = Json.createObjectBuilder()
					.add("name", "currentMIN").add("value", "4098930662")
					.build();
		}

		else if (account.getFlowName().equalsIgnoreCase("InternalPort")) {
			orderItemExtensionobj = Json.createObjectBuilder()
					.add("name", "currentMIN")
					.add("value", esnUtil.getCurrentESN().getMin()).build();
		}

		else if (account.getFlowName().equalsIgnoreCase("PhoneUpgrade")) {
			orderItemExtensionobj = Json.createObjectBuilder()
					.add("name", "currentMIN").add("value", account.getMin())
					.build();
		}

		JsonObject orderItemExtensionobj1 = Json.createObjectBuilder()
				.add("name", "handsetLast4Digits").add("value", "1875").build();
		JsonObject orderItemExtensionobj2 = Json.createObjectBuilder()
				.add("name", "currentAccountNumber").add("value", "12345")
				.build();
		JsonObject orderItemExtensionobj3 = Json.createObjectBuilder()
				.add("name", "currentAccountSecret").add("value", "2345")
				.build();

		JsonObject contactDetailsobj1 = Json.createObjectBuilder()
				.add("city", "Miami").add("country", "USA")
				.add("zipcode", "33178").add("stateOrProvince", "FL")
				.add("street1", "Street Address Line 1")
				.add("street2", "Street Address Line 2").build();

		JsonObject contactDetailsobj2 = Json.createObjectBuilder()
				.add("number", "7891000234").build();

		JsonObject contactDetailsobj3 = Json.createObjectBuilder()
				.add("emailAddress", account.getEmail())
				// .add("type", "email")
				.build();

		JsonObject customerAccountExtension1 = Json.createObjectBuilder()
				.add("name", "partySecret").add("value", "1234").build();
		JsonObject customerAccountExtension2 = Json.createObjectBuilder()
				.add("name", "last4DigitsOfSSN").add("value", "4321").build();

		JsonObject customerAccountobj = Json
				.createObjectBuilder()
				.add("customerAccountContact",
						Json.createArrayBuilder()
								.add(Json
										.createObjectBuilder()
										.add("contactMedium",
												Json.createObjectBuilder()
														.add("contactDetails",
																Json.createArrayBuilder()
																		.add(contactDetailsobj1))
														.add("type",
																"postalAddress")))
								.add(Json
										.createObjectBuilder()
										.add("contactDetails",
												Json.createArrayBuilder().add(
														contactDetailsobj2))
										.add("type", "telephoneNumber"))
								.add(Json
										.createObjectBuilder()
										.add("contactDetails",
												Json.createArrayBuilder().add(
														contactDetailsobj3))
										.add("type", "email")))
				.add("customerAccountExtension",
						Json.createArrayBuilder()
								.add(customerAccountExtension1)
								.add(customerAccountExtension2))
				.add("pin", "1234").build();

		JsonObject customerAccountobj1 = Json
				.createObjectBuilder()
				.add("customerAccount",
						Json.createObjectBuilder()
								.add("customerAccountContact",
										Json.createArrayBuilder()
												.add(Json
														.createObjectBuilder()
														.add("contactMedium",
																Json.createObjectBuilder()
																		.add("contactDetails",
																				Json.createArrayBuilder()
																						.add(contactDetailsobj1)))
														.add("type",
																"postalAddress"))
												.add(Json
														.createObjectBuilder()
														.add("contactDetails",
																Json.createArrayBuilder()
																		.add(contactDetailsobj2))
														.add("type",
																"telephoneNumber"))
												.add(Json
														.createObjectBuilder()
														.add("contactDetails",
																Json.createArrayBuilder()
																		.add(contactDetailsobj3))
														.add("type", "email")))
								.add("customerAccountExtension",
										Json.createArrayBuilder().add(
												customerAccountExtension1))
								.add("pin", "1234")).build();

		JsonObject customerAccountobj2 = Json
				.createObjectBuilder()
				.add("customerAccount",
						Json.createObjectBuilder()
								.add("customerAccountContact",
										Json.createArrayBuilder()
												.add(Json
														.createObjectBuilder()
														.add("contactMedium",
																Json.createObjectBuilder()
																		.add("contactDetails",
																				Json.createArrayBuilder()
																						.add(contactDetailsobj1)))
														.add("type",
																"postalAddress"))
												.add(Json
														.createObjectBuilder()
														.add("contactDetails",
																Json.createArrayBuilder()
																		.add(contactDetailsobj2))
														.add("type",
																"telephoneNumber"))
												.add(Json
														.createObjectBuilder()
														.add("contactDetails",
																Json.createArrayBuilder()
																		.add(contactDetailsobj3))
														.add("type", "email")))
								.add("customerAccountExtension",
										Json.createArrayBuilder().add(
												customerAccountExtension1))
								.add("pin", "1234")).build();

		JsonObject carrierobj = Json.createObjectBuilder().add("name", "VZW")
				.add("type", "WIRELESS").build();

		JsonObject relatedServicesObj = Json.createObjectBuilder()
				.add("id", "484").add("category", "SERVICE_PLAN").build();

		JsonObject orderItemsObj = null;
		if (account.getFlowName().equalsIgnoreCase("ExternalPort")) {
			orderItemsObj = Json
					.createObjectBuilder()
					.add("id", "1")
					.add("action", action)
					.add("product", productobj)
					.add("orderItemExtension",
							Json.createArrayBuilder()
									.add(orderItemExtensionobj)
									.add(orderItemExtensionobj1)
									.add(orderItemExtensionobj2)
									.add(orderItemExtensionobj3))
					.add("customerAccount", customerAccountobj)
					.add("service",
							Json.createObjectBuilder().add("carrier",
									Json.createArrayBuilder().add(carrierobj)))
					.add("relatedServices",
							Json.createArrayBuilder().add(relatedServicesObj))
					.add("location",
							Json.createObjectBuilder().add(
									"postalAddress",
									Json.createObjectBuilder().add("zipcode",
											"33178"))).add("quantity", "1")
					.build();
		}

		else if (account.getFlowName().equalsIgnoreCase("InternalPort")) {
			orderItemsObj = Json
					.createObjectBuilder()
					.add("id", "1")
					.add("action", action)
					.add("product", productobj)
					.add("orderItemExtension",
							Json.createArrayBuilder()
									.add(orderItemExtensionobj))
					.add("customerAccount",
							Json.createObjectBuilder().add("customerAccount",
									customerAccountobj1))
					.add("relatedServices",
							Json.createArrayBuilder().add(relatedServicesObj))
					.add("location",
							Json.createObjectBuilder().add(
									"postalAddress",
									Json.createObjectBuilder().add("zipcode",
											"33178"))).add("quantity", "1")
					.build();
		} else if (account.getFlowName().equalsIgnoreCase("PhoneUpgrade")) {
			orderItemsObj = Json
					.createObjectBuilder()
					.add("id", "1253")
					.add("action", action)
					.add("product", productobj)
					.add("orderItemExtension",
							Json.createArrayBuilder()
									.add(orderItemExtensionobj))
					.add("customerAccount", customerAccountobj2)
					.add("relatedServices",
							Json.createArrayBuilder().add(relatedServicesObj))
					.add("location",
							Json.createObjectBuilder().add(
									"postalAddress",
									Json.createObjectBuilder().add("zipcode",
											"33178"))).add("quantity", "1")
					.build();
		}

		JsonObject input = Json
				.createObjectBuilder()
				.add("common", getCommonObject())
				.add("request",
						Json.createObjectBuilder()
								.add("externalID", "WFMITQ999")
								.add("orderDate", dateFormat.format(date))
								// .add("relatedParties",Json.createArrayBuilder()
								// .add(getPartnerPartyObjectWithoutAcc())
								// .add(getPartnerPartyObject()))
								.add("relatedParties",
										Json.createArrayBuilder()
												.add(getPartnerPartyObjectForPortAndUpgrade()))
								.add("_comment",
										"ACTIVATE_LINE, REACTIVATE_LINE, ACTIVATE_BAN, REDEEM_LINE , REDEEM_BAN")
								.add("type", type)
								.add("orderItems",
										Json.createArrayBuilder().add(
												orderItemsObj)))
				.add("billingAccount",
						Json.createObjectBuilder().add("id", "1000001280"))
				.build();

		String restServiceName = null;
		if (account.getFlowName().equalsIgnoreCase("InternalPort")) {

			restServiceName = "REST-WFM Service Order Internal Port";

		}

		else if (account.getFlowName().equalsIgnoreCase("ExternalPort")) {

			restServiceName = "REST-WFM Service Order External Port";
		} else {

			restServiceName = "REST-WFM Service Order PhoneUpgrade";
		}

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"order-mgmt", "", "", "WFM");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input,
				restServiceName, token);
	}

	public void getServiceQualificationForUpgrade(ESN toEsn, String fromEsn,
			String min) {
		ServiceQualificationRequest req = new ServiceQualificationRequest(account.getBrand(), "ACTIVATION");
		req.addTargetHandset(toEsn.getEsn());
		if (!toEsn.getSim().isEmpty()) {
			req.addSim(toEsn.getSim());
		}
		req.addSourceHandset(fromEsn);
		req.addLine(min);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"service-qualification-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props
				.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json,
				"REST-Service_Qualification_Upgrade", token);

		/*
		 * RestAssured.useRelaxedHTTPSValidation(); String resource; resource =
		 * "productSerialNumber/" + account.getEsn() + "/zipcode/" + zipcode +
		 * "/carrier/" + carrier;
		 * 
		 * String url = props.getString(props.getString("ENV.") +
		 * "APP.END_POINT") + props.getString("APP_SERVICE_QUALIFICATION");
		 * given(). parameters("firstName", "John", "lastName", "Doe"). b
		 * when(). post("/greetXML"). then(). body("greeting.firstName",
		 * equalTo("John")).
		 */
	}

	public void getServiceQualificationForInternalPort(ESN toEsn, String min,
			String zip) {
		ServiceQualificationRequest req = new ServiceQualificationRequest(account.getBrand(), "ACTIVATION");
		req.addHandset(toEsn.getEsn());
		if (!toEsn.getSim().isEmpty()) {
			req.addSim(toEsn.getSim());
		}
		req.addZip(zip);
		req.addLine(min);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"service-qualification-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props
				.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json,
				"REST-Service_Qualification_IntPort", token);
	}

	public void getServiceQualificationForExternalPort(ESN toEsn, String min,
			String zip) {
		ServiceQualificationRequest req = new ServiceQualificationRequest(account.getBrand(), "ACTIVATION");
		req.addHandset(toEsn.getEsn());
		req.addHandset(toEsn.getEsn());
		if (!toEsn.getSim().isEmpty()) {
			req.addSim(toEsn.getSim());
		}
		req.addZip(zip);
		req.addLine(min);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"service-qualification-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props
				.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json,
				"REST-Service_Qualification_ExtPort", token);

		getServiceQualificationForPortCoverage(toEsn, min, zip);
	}

	public void getServiceQualificationForPortCoverage(ESN toEsn, String min,
			String zip) {
		ServiceQualificationRequest req = new ServiceQualificationRequest(account.getBrand(), "PORT_COVERAGE");
		req.addHandset(toEsn.getEsn());
		req.addHandset(toEsn.getEsn());
		if (!toEsn.getSim().isEmpty()) {
			req.addSim(toEsn.getSim());
		}
		req.addZip(zip);
		req.addLine(min);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);

		String token = serviceUtil.genAuthforApp("RO", "client_credentials",
				"service-qualification-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props
				.getString("ENV.") + "APP.END_POINT")
				+ props.getString("APP_SERVICE_QUALIFICATION"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json, "REST-Service_Qualification_PortCoverage", token);
	}
	
	public void activateBANWithCreditCard(List<ESN> esns, String zip, boolean isRecurring, boolean savePayment, CreditCard credit) {
		ProductOrderRequest req = new ProductOrderRequest(account.getBrand(), "ACTIVATE_BAN");
		String webobjid = Integer.toString(account.getAccountId());
		req.addSecurityPin(webobjid, account.getSecurityPin());
		BillingAccount billing = new BillingAccount(isRecurring, savePayment, true, "TwistFirst", "TwistLast", credit);
		req.setBillingAccount(billing);
		//One of the below account pin if new account. ban otherwise
		
//		billing.setId(banid);
		
		
		//ESN needs to set SIM, line action & service plan id
		//ESN needs to set MIN if upgrade or port
		// esn secuirty PIN?
		req.addOrderItems(esns, zip, account.getBrand(),isRecurring, this);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);
		
		String token = serviceUtil.genAuthforApp("RO", "client_credentials", "order-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json, "REST-ProductOrder_ActivateLine", token);
		System.out.println(jsonRes.toString());
	}
	
	public void activateBANWithACH(List<ESN> esn, String zip, boolean isRecurring, boolean savePayment, BankAccount bank) {
		ProductOrderRequest req = new ProductOrderRequest(account.getBrand(), "ACTIVATE_BAN");
		String webobjid = Integer.toString(account.getAccountId());
		req.addSecurityPin(webobjid, account.getSecurityPin());
		BillingAccount billing = new BillingAccount(isRecurring, savePayment, true, "TwistFirst", "TwistLast", bank);
		req.setBillingAccount(billing);
		//One of the below account pin if new account. ban otherwise
		
//		billing.setId(banid);
		
		
		//ESN needs to set SIM, line action & service plan id
		//ESN needs to set MIN if upgrade or port
		// esn secuirty PIN?
		req.addOrderItems(esn, zip, account.getBrand(),isRecurring, this);
		CommonWrapper wrapper = new CommonWrapper(account.getBrand(), req);
		String json = gson.toJson(wrapper);
		
		String token = serviceUtil.genAuthforApp("RO", "client_credentials", "order-mgmt", "", "", account.getBrand());
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER"));

		JSONObject jsonRes = serviceUtil.callPostService(webResource, json, "REST-ProductOrder_ActivateLine", token);
		System.out.println(jsonRes.toString());
	}

	public JSONObject getCatalog(ResourceType minOrEsn) {
		client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		String resource;
		if (ResourceType.ESN.equals(minOrEsn)) {
			resource = "esn/" + account.getEsn();
		} else {
			resource = "min/" + account.getMin();
		}
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_GET_PLANS") +
				resource)
				.queryParam("brandName", account.getBrand()).queryParam("clientAppName", "IOS").queryParam("clientAppVersion", "1.0")
				.queryParam("deviceModel", "IOS").queryParam("OSVersion", "8").queryParam("language", "ENG").queryParam("sourceSystem", "APP");
	
		String token = serviceUtil.genAuthforApp("RO", "client_credentials", "servicePlanSelector", "", "", account.getBrand());
		JSONObject jsonRes = serviceUtil.callGetService(webResource, "REST-MyAccount_GetCatalog", token);
		return jsonRes;
		
	}
	
	public void estimateOrder() {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "APP.END_POINT") + props.getString("APP_PRODUCT_ORDER") + "estimate");

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		JsonObject orderItemsObj = Json.createObjectBuilder()
					.add("id", "1")
					.add("action", "ESTIMATE")
					.add("quantity", 1)
					.add("productOffering", Json.createObjectBuilder()
							.add("id", "WFMAPPU0049")
							.add("name", "WFM Unl Talk and Text 10GB")
							.add("productSpecification",Json.createObjectBuilder().add("id", "WFMU0049").add("brand", "WFM"))
							.add("supportingResources",Json.createArrayBuilder()
									.add(Json.createObjectBuilder().add("serialNumber", account.getMin()).add("resourceType", "LINE"))))
					.build();
		
		JsonObject input = Json.createObjectBuilder()
				.add("common",getCommonObject())
				.add("request",Json.createObjectBuilder()
						.add("relatedParties",Json.createArrayBuilder()
								.add(getSOWebObjidPartyObj())
								.add(getPartnerPartyObject()))
						.add("orderDate",dateFormat.format(date))
						.add("type","REDEEM_BAN")
						.add("orderItems", Json.createArrayBuilder()
								.add(orderItemsObj))
						.add("billingAccount", Json.createObjectBuilder()
								.add( "id", account.getAccountId()))
						.add("location", Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
										.add("addressType","BILLING")
										.add("address",Json.createObjectBuilder().add("zipcode","33178")))))
				.build();
		
	
		String token =  serviceUtil.genAuthforApp("RO", "client_credentials", "order-mgmt", "", "","WFM");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input, "REST-WFM EstimateOrder", token);

		System.out.println(input.toString());
	}
	
	public void requestRefill(String banID) {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
				+ props.getString(props.getString("ENV.") + "FISERV_BILLING_MANAGEMENT")
				+banID+"/payment").queryParam("client_id", "05f6b0ce-e9ed-47d1-b93a-bbfc49a0b635");

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		JsonObject input = Json.createObjectBuilder()
				.add("externalId", "FISERV_RECEIPT_NUMBER")
				.add("orderDate", dateFormat.format(date))
				.add("relatedParties",Json.createArrayBuilder().add(getFiservRelatedParties()))
				.add("paymentPlan",Json.createObjectBuilder().add("amount", 25)
						.add("paymentMean",Json.createObjectBuilder().add("type", "CASH")))
				.build();
	
		String token =  serviceUtil.genAuthfor("FISERV", "");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input, "REST-FISERV RequestRefill", token);
	}
	
	public void requestForCancelRefill(String banID) {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
				+ props.getString(props.getString("ENV.") + "FISERV_BILLING_MANAGEMENT")
				+banID+"/cancel-payment").queryParam("client_id", "05f6b0ce-e9ed-47d1-b93a-bbfc49a0b635");

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		JsonObject input = Json.createObjectBuilder()
				.add("externalId", "FISERV_RECEIPT_NUMBER")
				.add("orderDate", dateFormat.format(date))
				.add("relatedParties",Json.createArrayBuilder().add(getFiservRelatedParties()))
				.add("paymentPlan",Json.createObjectBuilder().add("amount", 25)
						.add("paymentMean",Json.createObjectBuilder().add("type", "CASH")))
				.build();
	
		String token =  serviceUtil.genAuthfor("FISERV", "");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input, "REST-FISERV CancelRefill", token);
	}

	private JsonObject getFiservRelatedParties() {
		
		JsonObject relatedParties = Json.createObjectBuilder()
				.add("party", Json.createObjectBuilder()
						.add("partyID", "FISERV")
						.add("languageAbility", "ENG")
						.add("partyExtension", Json.createArrayBuilder()
								.add(Json.createObjectBuilder().add("name", "sourceSystem").add("value", "API"))
								.add(Json.createObjectBuilder().add("name", "brand").add("value", "WFM"))
								.add(Json.createObjectBuilder().add("name", "vendorStore").add("value", "302"))
								.add(Json.createObjectBuilder().add("name", "vendorTerminal").add("value", "302"))))
				.add("roleType", "partner")
				.build();

		return relatedParties;
		
	}
	
	public void getAccountDetails(String banID) {
		client = Client.create();
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT")
				+ props.getString(props.getString("ENV.") + "FISERV_BILLING_MANAGEMENT") + "balance")
				.queryParam("identifier", banID)
				.queryParam("partyID", "FISERV")
				.queryParam("brandName", "WFM")
				.queryParam("sourceSystem", "INDIRECT")
				.queryParam("client_id", "05f6b0ce-e9ed-47d1-b93a-bbfc49a0b635");

		client.addFilter(new LoggingFilter(System.out));
		String token =  serviceUtil.genAuthfor("FISERV", "");
		JSONObject jsonRes = serviceUtil.callGetService(webResource,"REST-FISERV GetAccountDetails", token);
	}

	public void registerIntoEDP() {
		client = Client.create();
		
		WebResource webResource = client.resource(props.getString(props.getString("ENV.") + "END_POINT") + "/pep/billing-mgmt/v1/billing-account/discounts").queryParam("client_id", "05f6b0ce-e9ed-47d1-b93a-bbfc49a0b635");

		client.addFilter(new LoggingFilter(System.out));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		

		JsonObject relatedParties = Json.createObjectBuilder()
				.add("party",Json.createObjectBuilder().add("individual", Json.createObjectBuilder()
																.add("firstName", "murali")
																.add("lastName", "kankanala")
																.add("partyExtension", Json.createArrayBuilder()
																			.add(Json.createObjectBuilder().add("name", "facilityNumber").add("value", "12345")).add(Json.createObjectBuilder().add("name", "deviceSecret").add("value", "1234")))))
																.add("roleType", "partner")
				.add("party", Json.createObjectBuilder()
														.add("languageAbility", "ENG")
														.add("partyExtension", Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "partyTransactionID").add("value", "web_1231234234424")))
														.add("partyID", "CUST_HASH"))
				.add("roleType", "internal")
																.build();
		
		JsonObject input = Json.createObjectBuilder()
				.add("common",getCommonObject())
				.add("request",Json.createObjectBuilder()
						.add("date",dateFormat.format(date))
						.add("discounts",Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "employeeDiscount").add("value", "EMPLOYEE_DISCOUNT")))
						.add("product", Json.createObjectBuilder().add("productCategory", "LINE").add("productSerialNumber", esnUtil.getCurrentESN().getEsn()).add("productSpecification",Json.createObjectBuilder().add("brand", esnUtil.getCurrentBrand())))
						.add("relatedParties", relatedParties))
				.build();
		String token = serviceUtil.genAuthforApp("CCU", "client_credentials",
				"billing-mgmt", "", "", "WFM");
		JSONObject jsonRes = serviceUtil.callPostService(webResource, input, "REST_WFM_BenefitHubRegistration", token);
		
		
	}
	
}



