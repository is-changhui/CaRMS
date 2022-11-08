/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Darie
 */
@Entity
public class RentalRate implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalRateId;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 1, max = 32)
    private String rentalRateName;
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal rentalDailyRate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalRateStartDay;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalRateEndDay;
    @Column(nullable = false)
    @NotNull
    private Boolean isEnabled;
    @Column(nullable = false)
    @NotNull
    private Boolean isUsed;

    
    public RentalRate() {
    }

    public RentalRate(String rentalRateName, BigDecimal rentalDailyRate, Date rentalRateStartDay, Date rentalRateEndDay, Boolean isEnabled, Boolean isUsed) {
        this();
        this.rentalRateName = rentalRateName;
        this.rentalDailyRate = rentalDailyRate;
        this.rentalRateStartDay = rentalRateStartDay;
        this.rentalRateEndDay = rentalRateEndDay;
        this.isEnabled = isEnabled;
        this.isUsed = isUsed;
    }
    
    public Long getRentalRateId() {
        return rentalRateId;
    }

    public void setRentalRateId(Long rentalRateId) {
        this.rentalRateId = rentalRateId;
    }
    
    /**
     * @return the rentalRateName
     */
    public String getRentalRateName() {
        return rentalRateName;
    }

    /**
     * @param rentalRateName the rentalRateName to set
     */
    public void setRentalRateName(String rentalRateName) {
        this.rentalRateName = rentalRateName;
    }

    /**
     * @return the rentalDailyRate
     */
    public BigDecimal getRentalDailyRate() {
        return rentalDailyRate;
    }

    /**
     * @param rentalDailyRate the rentalDailyRate to set
     */
    public void setRentalDailyRate(BigDecimal rentalDailyRate) {
        this.rentalDailyRate = rentalDailyRate;
    }

    /**
     * @return the rentalRateStartDay
     */
    public Date getRentalRateStartDay() {
        return rentalRateStartDay;
    }

    /**
     * @param rentalRateStartDay the rentalRateStartDay to set
     */
    public void setRentalRateStartDay(Date rentalRateStartDay) {
        this.rentalRateStartDay = rentalRateStartDay;
    }

    /**
     * @return the rentalRateEndDay
     */
    public Date getRentalRateEndDay() {
        return rentalRateEndDay;
    }

    /**
     * @param rentalRateEndDay the rentalRateEndDay to set
     */
    public void setRentalRateEndDay(Date rentalRateEndDay) {
        this.rentalRateEndDay = rentalRateEndDay;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return the isUsed
     */
    public Boolean getIsUsed() {
        return isUsed;
    }

    /**
     * @param isUsed the isUsed to set
     */
    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentalRateId != null ? rentalRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rentalRateId fields are not set
        if (!(object instanceof RentalRate)) {
            return false;
        }
        RentalRate other = (RentalRate) object;
        if ((this.rentalRateId == null && other.rentalRateId != null) || (this.rentalRateId != null && !this.rentalRateId.equals(other.rentalRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RentalRate[ id=" + rentalRateId + " ]";
    }
    
}
