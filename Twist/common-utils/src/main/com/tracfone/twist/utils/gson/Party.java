
package com.tracfone.twist.utils.gson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracfone.twist.utils.TwistUtils;

public class Party {

    @SerializedName("individual")
    @Expose
    private Individual individual;
    @SerializedName("partyID")
    @Expose
    private String partyID;
    @SerializedName("languageAbility")
    @Expose
    private String languageAbility;
    @SerializedName("partyExtension")
    @Expose
    private List<PartyExtension> partyExtension;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Party(String partyID) {
    	this.partyID = partyID;
    	languageAbility = "ENG";
    	getPartyExtension().add(new PartyExtension("partyTransactionID", "web_" + Long.toString(TwistUtils.createRandomLong(100000000L, 999999999999L))));
    	getPartyExtension().add(new PartyExtension("sourceSystem", "WEB"));
    }
    
    public Party(String webobjid, String secPin) {
    	this.partyID = "CUST_HASH";
    	this.individual = new Individual(webobjid);
    	getPartyExtension().add(new PartyExtension("partySecret", secPin));
    }

    /**
     * 
     * @param partyExtension
     * @param partyID
     * @param languageAbility
     */
    public Party(String partyID, String languageAbility, List<PartyExtension> partyExtension) {
        super();
        this.partyID = partyID;
        this.languageAbility = languageAbility;
        this.partyExtension = partyExtension;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public String getLanguageAbility() {
        return languageAbility;
    }

    public void setLanguageAbility(String languageAbility) {
        this.languageAbility = languageAbility;
    }

    public List<PartyExtension> getPartyExtension() {
    	if (partyExtension == null) {
    		partyExtension = new ArrayList<PartyExtension>();
    	}
        return partyExtension;
    }

    public void setPartyExtension(List<PartyExtension> partyExtension) {
        this.partyExtension = partyExtension;
    }

}
