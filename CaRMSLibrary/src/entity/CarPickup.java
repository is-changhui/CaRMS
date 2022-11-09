/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class CarPickup implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickupId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date pickupDate;
    
//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private Outlet outlet;
    
    public CarPickup() {
    }

    public CarPickup(Date pickupDate) {
        this.pickupDate = pickupDate;
    }
    


    public Long getPickupId() {
        return pickupId;
    }

    public void setPickupId(Long pickupId) {
        this.pickupId = pickupId;
    }
    
    /**
     * @return the pickupDate
     */
    public Date getPickupDate() {
        return pickupDate;
    }

    /**
     * @param pickupDate the pickupDate to set
     */
    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pickupId != null ? pickupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the pickupId fields are not set
        if (!(object instanceof CarPickup)) {
            return false;
        }
        CarPickup other = (CarPickup) object;
        if ((this.pickupId == null && other.pickupId != null) || (this.pickupId != null && !this.pickupId.equals(other.pickupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CarPickup[ id=" + pickupId + " ]";
    }
    
}
