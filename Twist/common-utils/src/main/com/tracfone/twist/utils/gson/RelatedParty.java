
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedParty {

    @SerializedName("party")
    @Expose
    private Party party;
    @SerializedName("roleType")
    @Expose
    private String roleType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RelatedParty(String partyID) {
    	party = new Party(partyID);
    	roleType = "internal";
    }
    
    public RelatedParty(String webobjid, String secPin) {
    	party = new Party(webobjid, secPin);
    	roleType = "customer";
    }

    /**
     * 
     * @param roleType
     * @param party
     */
    public RelatedParty(Party party, String roleType) {
        super();
        this.party = party;
        this.roleType = roleType;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
