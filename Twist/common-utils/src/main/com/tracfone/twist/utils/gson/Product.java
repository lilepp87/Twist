
package com.tracfone.twist.utils.gson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productSpecification")
    @Expose
    private ProductSpecification productSpecification;
  /*  @SerializedName("characteristicSpecification")
    @Expose
    private CharacteristicSpecification characteristicSpecification;*/
    @SerializedName("relatedServices")
    @Expose
    private List<RelatedService> relatedServices = null;
    @SerializedName("productSerialNumber")
    @Expose
    private String productSerialNumber;
    @SerializedName("productCategory")
    @Expose
    private String productCategory;
    @SerializedName("supportingResources")
    @Expose
    private List<SupportingResource> supportingResources;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Product() {
    }

    /**
     * 
     * @param productCategory
     * @param productSpecification
     * @param supportingResources
     * @param productSerialNumber
     * @param relatedServices
     */
    public Product(ProductSpecification productSpecification, List<RelatedService> relatedServices, String productSerialNumber, String productCategory, List<SupportingResource> supportingResources) {
        super();
        this.productSpecification = productSpecification;
        this.relatedServices = relatedServices;
        this.productSerialNumber = productSerialNumber;
        this.productCategory = productCategory;
        this.supportingResources = supportingResources;
    }
    //TODO
    public Product(Integer servicePlanId, String productSerialNumber, String productCategory,boolean isRecurring,CharacteristicSpecification characteristicSpecification) {
        super();
//        this.productSpecification = productSpecification;
        this.relatedServices = new ArrayList<RelatedService>();
        relatedServices.add(new RelatedService(servicePlanId,isRecurring,characteristicSpecification));
        this.productSerialNumber = productSerialNumber;
        this.productCategory = productCategory;
      //  this.characteristicSpecification=characteristicSpecification;
    }

   /* public CharacteristicSpecification getCharacteristicSpecification() {
        return characteristicSpecification;
    }

    public void setCharacteristicSpecification(CharacteristicSpecification characteristicSpecification) {
        this.characteristicSpecification = characteristicSpecification;
    }
    */
    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<SupportingResource> getSupportingResources() {
    	if (supportingResources == null) {
    		supportingResources = new ArrayList<SupportingResource>();
    	}
        return supportingResources;
    }

    public void setSupportingResources(List<SupportingResource> supportingResources) {
        this.supportingResources = supportingResources;
    }

    public List<RelatedService> getRelatedServices() {
        return relatedServices;
    }

    public void setRelatedServices(List<RelatedService> relatedServices) {
        this.relatedServices = relatedServices;
    }
    
    public void addLine(String line) {
    	if (line != null && !line.isEmpty()) {
    		getSupportingResources().add(new SupportingResource("LINE", line));
    	}
    }
    
    public void addSim(String sim) {
    	if (sim != null && !sim.isEmpty()) {
    		getSupportingResources().add(new SupportingResource("SIM_CARD", sim));
    	}
    }

}