
package com.tracfone.twist.utils.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedService {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isRecurring")
    @Expose
    private boolean isRecurring;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("isRedeemNow")
    @Expose
    private Boolean isRedeemNow;
	@SerializedName("characteristicSpecification1")
    @Expose
    private List<CharacteristicSpecification> characteristicSpecification1 = null;
	 @SerializedName("characteristicSpecification")
	  @Expose
	   private CharacteristicSpecification characteristicSpecification;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RelatedService() {
    }

    /**
     * 
     * @param id
     * @param category
     */
    public RelatedService(Integer id, String category, Boolean isRedeemNow, List<CharacteristicSpecification> characteristicSpecification) {
        super();
        this.id = id;
        this.category = category;
        this.isRedeemNow = isRedeemNow;
		this.characteristicSpecification1 = characteristicSpecification;
    }
    
    public RelatedService(Integer servicePlanId) {
        super();
        this.id = servicePlanId;
        this.category = "SERVICE_PLAN";
    }
    
    public RelatedService(Integer servicePlanId,boolean isRecurring,CharacteristicSpecification characteristicSpecification) {
        super();
        this.id = servicePlanId;
        this.isRecurring=isRecurring;
        this.category = "SERVICE_PLAN";
        this.characteristicSpecification= characteristicSpecification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsRedeemNow() {
        return isRedeemNow;
    }

    public void setIsRedeemNow(Boolean isRedeemNow) {
        this.isRedeemNow = isRedeemNow;
    }
	
    public List<CharacteristicSpecification> getCharacteristicSpecification() {
        return characteristicSpecification1;
    }

    public void setCharacteristicSpecification(List<CharacteristicSpecification> characteristicSpecification) {
        this.characteristicSpecification1 = characteristicSpecification;
    }

}
