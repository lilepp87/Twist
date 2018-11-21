
package com.tracfone.serviceplancommontypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.ResponseResultType;


/**
 * <p>Java class for PlanAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="errorResult" type="{http://www.tracfone.com/CommonTypes}ResponseResultType"/>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanAction", propOrder = {
    "errorResult",
    "action"
})
public class PlanAction {

    protected ResponseResultType errorResult;
    protected List<String> action;

    /**
     * Gets the value of the errorResult property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseResultType }
     *     
     */
    public ResponseResultType getErrorResult() {
        return errorResult;
    }

    /**
     * Sets the value of the errorResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseResultType }
     *     
     */
    public void setErrorResult(ResponseResultType value) {
        this.errorResult = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAction() {
        if (action == null) {
            action = new ArrayList<String>();
        }
        return this.action;
    }

}
