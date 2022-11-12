/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
//    @Column(nullable = false)
//    @NotNull
//    private Boolean carIsUsed;
    @Column(nullable = false)
    @NotNull
    private Boolean carIsEnabled;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String carStatus;
//    @Column(nullable = false, length = 32)
//    @NotNull
////    @Size(min = 1, max = 32)
//    private String carLocation;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CarModel carModel;
    
    @OneToOne(optional = true, mappedBy = "car")
    private RentalReservation rentalReservation;
    
    // To address location of car: if outlet is not null, it is at outlet, else
    // based on carStatusEnum, we will know if its with customer or outlet
    @ManyToOne(optional = true)
    @JoinColumn(nullable = false)
    private Outlet outlet;

    
    public Car() {
        carIsEnabled = true;
    }

    public Car(String carLicensePlate, String carColour, Boolean carIsEnabled, String carStatus) {
        this();
        this.carLicensePlate = carLicensePlate;
        this.carColour = carColour;
        this.carIsEnabled = carIsEnabled;
        this.carStatus = carStatus;
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
        return getCarIsEnabled();
    }

    /**
     * @param carIsUsed the carIsUsed to set
     */
    public void setCarIsUsed(Boolean carIsUsed) {
        this.setCarIsEnabled(carIsUsed);
    }

    /**
     * @return the carStatus
     */
    public String getCarStatus() {
        return carStatus;
    }

    /**
     * @param carStatus the carStatus to set
     */
    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }
    
    public Boolean getCarIsEnabled() {
        return carIsEnabled;
    }

    public void setCarIsEnabled(Boolean carIsEnabled) {
        this.setCarIsEnabled(carIsEnabled);
    }

    
    /**
     * @return the carModel
     */
    public CarModel getCarModel() {
        return carModel;
    }

    /**
     * @param carModel the carModel to set
     */
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    /**
     * @return the outlet
     */
    public Outlet getOutlet() {
        return outlet;
    }

    /**
     * @param outlet the outlet to set
     */
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    /**
     * @return the rentalReservation
     */
    public RentalReservation getRentalReservation() {
        return rentalReservation;
    }

    /**
     * @param rentalReservation the rentalReservation to set
     */
    public void setRentalReservation(RentalReservation rentalReservation) {
        this.rentalReservation = rentalReservation;
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
    
}
