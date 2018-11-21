
package com.tracfone.twist.utils.gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracfone.twist.utils.ESN;
import com.tracfone.twist.utils.ServiceUtil.ResourceType;
import com.tracfone.twist.utils.TwistUtils;
import com.tracfone.twist.utils.WfmUtil;

public class ProductOrderRequest implements Request {

    @SerializedName("externalID")
    @Expose
    private String externalID;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("relatedParties")
    @Expose
    private List<RelatedParty> relatedParties = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("orderItems")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("billingAccount")
    @Expose
    private BillingAccount billingAccount;
    

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductOrderRequest(String brand, String type) {
    	externalID = Long.toString(TwistUtils.createRandomLong(1000, 99999));
    	this.type = type;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-04:00'");
//    	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    	Date date = new Date();
    	orderDate = dateFormat.format(date);
    	relatedParties = new ArrayList<RelatedParty>();
    	relatedParties.add(new RelatedParty(brand + "WEB"));
    }
    
    public ProductOrderRequest(String brand, String type, String pin) {
    	externalID = Long.toString(TwistUtils.createRandomLong(1000, 99999));
    	this.type = type;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    	Date date = new Date();
    	orderDate = dateFormat.format(date);
    	relatedParties = new ArrayList<RelatedParty>();
    	relatedParties.add(new RelatedParty(brand + "WEB"));    	
    }
    
    public void addSecurityPin(String webobjid, String pin) {
    	relatedParties.add(new RelatedParty(webobjid, pin));
    }

    /**
     * 
     * @param orderDate
     * @param relatedParties
     * @param orderItems
     * @param externalID
     * @param type
     * @param billingAccount
     */
    public ProductOrderRequest(String externalID, String orderDate, List<RelatedParty> relatedParties, String type, List<OrderItem> orderItems, BillingAccount billingAccount) {
        super();
        this.externalID = externalID;
        this.orderDate = orderDate;
        this.relatedParties = relatedParties;
        this.type = type;
        this.orderItems = orderItems;
        this.billingAccount = billingAccount;
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<RelatedParty> getRelatedParties() {
        return relatedParties;
    }

    public void setRelatedParties(List<RelatedParty> relatedParties) {
        this.relatedParties = relatedParties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OrderItem> getOrderItems() {
    	if (orderItems == null) {
    		 orderItems = new ArrayList<OrderItem>();
    	}
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }

	public void addOrderItems(List<ESN> esns, String zip, String brand ,boolean isRecurring, WfmUtil wfmUtil) {
		for (ESN esn: esns) {
			addOrderItem(esn, zip, brand,isRecurring, wfmUtil);
		}
	}

	public void addOrderItem(ESN esn, String zip, String brand,boolean isRecurring, WfmUtil wfmUtil) {
		orderItems = getOrderItems();
		
		String action = esn.getLineAction();
		int servicePlanId = esn.getServicePlanId();
		JSONObject selectedPlan = null; 
		try {
			JSONObject catalog = wfmUtil.getCatalog(ResourceType.ESN);
			JSONArray servicePlans = catalog.getJSONObject("response").getJSONArray("servicePlans");
			for (int i = 0; i < servicePlans.length(); i++) {
				JSONObject currentPlan = servicePlans.getJSONObject(i);
				if (currentPlan.getInt("servicePlanId") == servicePlanId) {
					selectedPlan = currentPlan;
					break;
				}
			}
		
			String partClass = selectedPlan.getString("partClassNumber");
			String partNumber = selectedPlan.getString("partNumber");
			String servicePlanName = selectedPlan.getString("servicePlanName");
			
			ProductSpecification productSpecification = new ProductSpecification(partClass, brand);
			CharacteristicSpecification characteristicSpecification = new CharacteristicSpecification("relatedProgramId",selectedPlan.getInt("programId"));
			ProductOffering productOffering = new ProductOffering(partNumber, servicePlanName, productSpecification);
			
			Product product = new Product(servicePlanId, esn.getEsn(), "HANDSET",isRecurring,characteristicSpecification);
			
			
			
			product.addSim(esn.getSim());
			product.addLine(esn.getMin());
			int currentIndex = orderItems.size()+1;
			
			OrderItem item = new OrderItem(currentIndex, action, "1", productOffering, product, zip);
			orderItems.add(item);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
