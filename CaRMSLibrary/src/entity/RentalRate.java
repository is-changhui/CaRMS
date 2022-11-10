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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.RentalRateTypeEnum;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private RentalRateTypeEnum rentalRateType;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal rentalDailyRate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @NotNull
    private Date rentalRateStartDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @NotNull
    private Date rentalRateEndDateTime;
    @Column(nullable = false)
    @NotNull
    private Boolean isEnabled;
    @Column(nullable = false)
    @NotNull
    private Boolean isUsed;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CarCategory carCategory;
    
    
    public RentalRate() {
        isEnabled = true;
        isUsed = false;
    }

    public RentalRate(String rentalRateName, RentalRateTypeEnum rentalRateType, BigDecimal rentalDailyRate, Date rentalRateStartDateTime, Date rentalRateEndDateTime, Boolean isEnabled, Boolean isUsed) {
        this();
        this.rentalRateName = rentalRateName;
        this.rentalRateType = rentalRateType;
        this.rentalDailyRate = rentalDailyRate;
        this.rentalRateStartDateTime = rentalRateStartDateTime;
        this.rentalRateEndDateTime = rentalRateEndDateTime;
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
     * @return the rentalRateType
     */
    public RentalRateTypeEnum getRentalRateType() {
        return rentalRateType;
    }

    /**
     * @param rentalRateType the rentalRateType to set
     */
    public void setRentalRateType(RentalRateTypeEnum rentalRateType) {
        this.rentalRateType = rentalRateType;
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
     * @return the rentalRateStartDateTime
     */
    public Date getRentalRateStartDateTime() {
        return rentalRateStartDateTime;
    }

    /**
     * @param rentalRateStartDateTime the rentalRateStartDateTime to set
     */
    public void setRentalRateStartDateTime(Date rentalRateStartDateTime) {
        this.rentalRateStartDateTime = rentalRateStartDateTime;
    }

    /**
     * @return the rentalRateEndDateTime
     */
    public Date getRentalRateEndDateTime() {
        return rentalRateEndDateTime;
    }

    /**
     * @param rentalRateEndDateTime the rentalRateEndDateTime to set
     */
    public void setRentalRateEndDateTime(Date rentalRateEndDateTime) {
        this.rentalRateEndDateTime = rentalRateEndDateTime;
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
    
    /**
     * @return the carCategory
     */
    public CarCategory getCarCategory() {
        return carCategory;
    }

    /**
     * @param carCategory the carCategory to set
     */
    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
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
