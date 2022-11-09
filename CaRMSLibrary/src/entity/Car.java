/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.CarStatusEnum;

/**
 *
 * @author Darie
 */
@Entity
public class Car implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String carLicensePlate;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String carColour;
    @Column(nullable = false)
    @NotNull
    private Boolean carIsUsed;
    @Column(nullable = false)
    @NotNull
    private Boolean carIsDisabled;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private CarStatusEnum carStatus;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 1, max = 32)
    private String carLocation;
    
    
    
    
    public Car() {
        carIsDisabled = false;
    }

    public Car(String carLicensePlate, String carColour, Boolean carIsUsed, Boolean carIsDisabled, CarStatusEnum carStatus, String carLocation) {
        this();
        this.carLicensePlate = carLicensePlate;
        this.carColour = carColour;
        this.carIsUsed = carIsUsed;
        this.carIsDisabled = carIsDisabled;
        this.carStatus = carStatus;
        this.carLocation = carLocation;
    }
    
    
    
    
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }
    
    /**
     * @return the carColour
     */
    public String getCarColour() {
        return carColour;
    }

    /**
     * @param carColour the carColour to set
     */
    public void setCarColour(String carColour) {
        this.carColour = carColour;
    }

    /**
     * @return the carIsUsed
     */
    public Boolean getCarIsUsed() {
        return carIsDisabled;
    }

    /**
     * @param carIsUsed the carIsUsed to set
     */
    public void setCarIsUsed(Boolean carIsUsed) {
        this.carIsDisabled = carIsUsed;
    }

    /**
     * @return the carStatus
     */
    public CarStatusEnum getCarStatus() {
        return carStatus;
    }

    /**
     * @param carStatus the carStatus to set
     */
    public void setCarStatus(CarStatusEnum carStatus) {
        this.carStatus = carStatus;
    }

    /**
     * @return the carLocation
     */
    public String getCarLocation() {
        return carLocation;
    }

    /**
     * @param carLocation the carLocation to set
     */
    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the carId fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Car[ id=" + carId + " ]";
    }

    public Boolean getCarIsDisabled() {
        return carIsDisabled;
    }

    public void setCarIsDisabled(Boolean carIsDisabled) {
        this.carIsDisabled = carIsDisabled;
    }
    
}
