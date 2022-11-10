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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class TransitDriverDispatchRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dispatchRecordId;
    @Column(nullable = false)
    @NotNull
    private Boolean transitIsComplete;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date dispatchDate;
    @Column(nullable = false)
    @NotNull
//    @Size(min = 2, max = 24)
    private Long dispatchDuration;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Outlet destinationOutlet;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = false)
    
    @OneToOne
    @JoinColumn(nullable = false)
    private RentalReservation rentalReservation;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Car car;
    

    public TransitDriverDispatchRecord() {
        transitIsComplete = false;
    }

    public TransitDriverDispatchRecord(Boolean transitIsComplete, Date dispatchDate, Long dispatchDuration) {
        this();
        this.transitIsComplete = transitIsComplete;
        this.dispatchDate = dispatchDate;
        this.dispatchDuration = dispatchDuration;
    }
    
   
    public Long getDispatchRecordId() {
        return dispatchRecordId;
    }

    public void setDispatchRecordId(Long dispatchRecordId) {
        this.dispatchRecordId = dispatchRecordId;
    }
    
    /**
     * @return the transitIsComplete
     */
    public Boolean getTransitIsComplete() {
        return transitIsComplete;
    }

    /**
     * @param transitIsComplete the transitIsComplete to set
     */
    public void setTransitIsComplete(Boolean transitIsComplete) {
        this.transitIsComplete = transitIsComplete;
    }

    /**
     * @return the dispatchDate
     */
    public Date getDispatchDate() {
        return dispatchDate;
    }

    /**
     * @param dispatchDate the dispatchDate to set
     */
    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    /**
     * @return the dispatchDuration
     */
    public Long getDispatchDuration() {
        return dispatchDuration;
    }

    /**
     * @param dispatchDuration the dispatchDuration to set
     */
    public void setDispatchDuration(Long dispatchDuration) {
        this.dispatchDuration = dispatchDuration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dispatchRecordId != null ? dispatchRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the dispatchRecordId fields are not set
        if (!(object instanceof TransitDriverDispatchRecord)) {
            return false;
        }
        TransitDriverDispatchRecord other = (TransitDriverDispatchRecord) object;
        if ((this.dispatchRecordId == null && other.dispatchRecordId != null) || (this.dispatchRecordId != null && !this.dispatchRecordId.equals(other.dispatchRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransitDriverDispatchRecord[ id=" + dispatchRecordId + " ]";
    }
    
}
