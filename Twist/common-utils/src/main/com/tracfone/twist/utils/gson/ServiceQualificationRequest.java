
package com.tracfone.twist.utils.gson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceQualificationRequest implements Request {

    @SerializedName("relatedParties")
    @Expose
    private List<RelatedParty> relatedParties = new ArrayList<RelatedParty>();
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("serviceCategory")
    @Expose
    private List<ServiceCategory> serviceCategory = new ArrayList<ServiceCategory>();
    @SerializedName("serviceSpecification")
    @Expose
    private ServiceSpecification serviceSpecification;
    @SerializedName("relatedResources")
    @Expose
    private List<RelatedResource> relatedResources = new ArrayList<RelatedResource>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ServiceQualificationRequest(String brand, String context) {
    	serviceCategory.add(new ServiceCategory("context", context));
    	serviceSpecification = new ServiceSpecification(brand);
    	relatedParties.add(new RelatedParty(brand + "WEB"));
    }

    /**
     * 
     * @param relatedResources
     * @param relatedParties
     * @param serviceCategory
     * @param serviceSpecification
     */
    public ServiceQualificationRequest(List<RelatedParty> relatedParties, List<ServiceCategory> serviceCategory, ServiceSpecification serviceSpecification, List<RelatedResource> relatedResources) {
        super();
        this.relatedParties = relatedParties;
        this.serviceCategory = serviceCategory;
        this.serviceSpecification = serviceSpecification;
        this.relatedResources = relatedResources;
    }

    public List<RelatedParty> getRelatedParties() {
        return relatedParties;
    }

    public void setRelatedParties(List<RelatedParty> relatedParties) {
        this.relatedParties = relatedParties;
    }

    public List<ServiceCategory> getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(List<ServiceCategory> serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public ServiceSpecification getServiceSpecification() {
        return serviceSpecification;
    }

    public void setServiceSpecification(ServiceSpecification serviceSpecification) {
        this.serviceSpecification = serviceSpecification;
    }

    public List<RelatedResource> getRelatedResources() {
        return relatedResources;
    }

    public void setRelatedResources(List<RelatedResource> relatedResources) {
        this.relatedResources = relatedResources;
    }
    
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
    public void addSourceHandset(String esn) {
    	relatedResources.add(new RelatedResource(esn, "sourceProductSerialNumber", "HANDSET"));
    }
    
    public void addTargetHandset(String esn) {
    	relatedResources.add(new RelatedResource(esn, "targetProductSerialNumber", "HANDSET"));
    }
    
    public void addHandset(String esn) {
    	relatedResources.add(new RelatedResource(esn, "productSerialNumber", "HANDSET"));
    }
    
    public void addSim(String sim) {
    	relatedResources.add(new RelatedResource(sim, "serialNumber", "SIM_CARD"));
    }
    
    public void addLine(String min) {
    	relatedResources.add(new RelatedResource(min, "currentMIN", "LINE"));
    }
    
    public void addZip(String zip) {
    	this.setLocation(new Location(new PostalAddress(zip)));
    }

}
