/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class Outlet implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String outletName;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String outletAddress;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date outletOpeningHour;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date outletClosingHour;
    
    @OneToMany(mappedBy = "outlet")
    private List<Car> cars;
    
//    @OneToMany(mappedBy = "outlet")
//    private List<Employee> employees;
    
    @OneToMany(mappedBy = "outlet")
    private List<CarPickup> carPickupRecords;
    
    @OneToMany(mappedBy = "outlet")
    private List<CarReturn> carReturnRecords;
    
    @OneToMany(mappedBy = "destinationOutlet")
    private List<TransitDriverDispatchRecord> transitDriverDispatchRecords;


    public Outlet() {
    }

    
    public Outlet(String outletName, String outletAddress, Date outletOpeningHour, Date outletClosingHour) {
        this();
        this.outletName = outletName;
        this.outletAddress = outletAddress;
        this.outletOpeningHour = outletOpeningHour;
        this.outletClosingHour = outletClosingHour;
    }
    

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
    
    /**
     * @return the outletName
     */
    public String getOutletName() {
        return outletName;
    }

    /**
     * @param outletName the outletName to set
     */
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    /**
     * @return the outletAddress
     */
    public String getOutletAddress() {
        return outletAddress;
    }

    /**
     * @param outletAddress the outletAddress to set
     */
    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    /**
     * @return the outletOpeningHour
     */
    public Date getOutletOpeningHour() {
        return outletOpeningHour;
    }

    /**
     * @param outletOpeningHour the outletOpeningHour to set
     */
    public void setOutletOpeningHour(Date outletOpeningHour) {
        this.outletOpeningHour = outletOpeningHour;
    }

    /**
     * @return the outletClosingHour
     */
    public Date getOutletClosingHour() {
        return outletClosingHour;
    }

    /**
     * @param outletClosingHour the outletClosingHour to set
     */
    public void setOutletClosingHour(Date outletClosingHour) {
        this.outletClosingHour = outletClosingHour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outletId != null ? outletId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the outletId fields are not set
        if (!(object instanceof Outlet)) {
            return false;
        }
        Outlet other = (Outlet) object;
        if ((this.outletId == null && other.outletId != null) || (this.outletId != null && !this.outletId.equals(other.outletId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Outlet[ id=" + outletId + " ]";
    }
    
}
