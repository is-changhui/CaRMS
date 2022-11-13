/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class RentalReservation implements Serializable {

    

    /**
     * @param rentalReservationIsCompleted the rentalReservationIsCompleted to set
     */
    public void setRentalReservationIsCompleted(Boolean rentalReservationIsCompleted) {
        this.rentalReservationIsCompleted = rentalReservationIsCompleted;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalReservationId;
    @Column(nullable = false)
    @NotNull
    private Boolean rentalReservationIsPaid;
    @Column(nullable = false)
    @NotNull
    private Boolean rentalReservationIsCancelled;
    @Column(nullable = false)
    @NotNull
    private Boolean rentalReservationHasPickedUp;
    @Column(nullable = false)
    @NotNull
    private Boolean rentalReservationIsCompleted;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal rentalReservationAmount;
    @Column(nullable = false, length = 64)
    @NotNull
//    @Size(min = 8, max = 32)
    private String creditCardName;
    @Column(nullable = false, length = 64)
    @NotNull
//    @Size(min = 8, max = 32)
    private String creditCardNumber;
    @Column(nullable = false, length = 64)
    @NotNull
//    @Size(min = 8, max = 32)
    private String creditCardCVV;
    @Column(nullable = false, length = 64)
    @NotNull
    private String creditCardExpiry;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalReservationPickupDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalReservationReturnDateTime;

    @OneToOne(optional = true)
    private CarCategory carCategory;

    @OneToOne(optional = true)
    private CarModel carModel;

    @OneToOne(optional = true)
    private Car car;

    @ManyToMany
    private List<RentalRate> rentalRates;

    @OneToOne(optional = true, mappedBy = "rentalReservation")
    private TransitDriverDispatchRecord transitDriverDispatchRecord;

    @ManyToOne(optional = true)
    private Partner partner;

    @ManyToOne(optional = true)
    private PartnerCustomer partnerCustomer;

    @ManyToOne(optional = true)
    private Customer customer;

    @OneToOne(optional = false)
    private Outlet pickupOutlet;

    @OneToOne(optional = false)
    private Outlet returnOutlet;


    public RentalReservation() {
        rentalReservationHasPickedUp = false;
        rentalReservationIsCompleted = false;
        rentalReservationIsCancelled = false;
        rentalRates = new ArrayList<>();
        rentalReservationAmount = BigDecimal.valueOf(0.00);
        creditCardName = "";
        creditCardNumber = "";
        creditCardCVV = "";
        creditCardExpiry = "";
    }

    public Long getRentalReservationId() {
        return rentalReservationId;
    }

    public void setRentalReservationId(Long rentalReservationId) {
        this.rentalReservationId = rentalReservationId;
    }

    /**
     * @return the rentalReservationIsPaid
     */
    public Boolean getRentalReservationIsPaid() {
        return rentalReservationIsPaid;
    }

    /**
     * @param rentalReservationIsPaid the rentalReservationIsPaid to set
     */
    public void setRentalReservationIsPaid(Boolean rentalReservationIsPaid) {
        this.rentalReservationIsPaid = rentalReservationIsPaid;
    }

    /**
     * @return the rentalReservationIsCancelled
     */
    public Boolean getRentalReservationIsCancelled() {
        return rentalReservationIsCancelled;
    }

    /**
     * @param rentalReservationIsCancelled the rentalReservationIsCancelled to
     * set
     */
    public void setRentalReservationIsCancelled(Boolean rentalReservationIsCancelled) {
        this.rentalReservationIsCancelled = rentalReservationIsCancelled;
    }

    /**
     * @return the rentalReservationHasPickedUp
     */
    public Boolean getRentalReservationHasPickedUp() {
        return rentalReservationHasPickedUp;
    }

    /**
     * @param rentalReservationHasPickedUp the rentalReservationHasPickedUp to set
     */
    public void setRentalReservationHasPickedUp(Boolean rentalReservationHasPickedUp) {
        this.rentalReservationHasPickedUp = rentalReservationHasPickedUp;
    }
    
    /**
     * @return the rentalReservationIsCompleted
     */
    public Boolean getRentalReservationIsCompleted() {
        return rentalReservationIsCompleted;
    }

    /**
     * @return the rentalReservationAmount
     */
    public BigDecimal getRentalReservationAmount() {
        return rentalReservationAmount;
    }

    /**
     * @param rentalReservationAmount the rentalReservationAmount to set
     */
    public void setRentalReservationAmount(BigDecimal rentalReservationAmount) {
        this.rentalReservationAmount = rentalReservationAmount;
    }

    /**
     * @return the creditCardName
     */
    public String getCreditCardName() {
        return creditCardName;
    }

    /**
     * @param creditCardName the creditCardName to set
     */
    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    /**
     * @return the creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber the creditCardNumber to set
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * @return the creditCardCVV
     */
    public String getCreditCardCVV() {
        return creditCardCVV;
    }

    /**
     * @param creditCardCVV the creditCardCVV to set
     */
    public void setCreditCardCVV(String creditCardCVV) {
        this.creditCardCVV = creditCardCVV;
    }

    /**
     * @return the creditCardExpiry
     */
    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    /**
     * @param creditCardExpiry the creditCardExpiry to set
     */
    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    /**
     * @return the rentalReservationPickupDateTime
     */
    public Date getRentalReservationPickupDateTime() {
        return rentalReservationPickupDateTime;
    }

    /**
     * @param rentalReservationPickupDateTime the
     * rentalReservationPickupDateTime to set
     */
    public void setRentalReservationPickupDateTime(Date rentalReservationPickupDateTime) {
        this.rentalReservationPickupDateTime = rentalReservationPickupDateTime;
    }

    /**
     * @return the rentalReservationReturnDateTime
     */
    public Date getRentalReservationReturnDateTime() {
        return rentalReservationReturnDateTime;
    }

    /**
     * @param rentalReservationReturnDateTime the
     * rentalReservationReturnDateTime to set
     */
    public void setRentalReservationReturnDateTime(Date rentalReservationReturnDateTime) {
        this.rentalReservationReturnDateTime = rentalReservationReturnDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentalReservationId != null ? rentalReservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rentalReservationId fields are not set
        if (!(object instanceof RentalReservation)) {
            return false;
        }
        RentalReservation other = (RentalReservation) object;
        if ((this.rentalReservationId == null && other.rentalReservationId != null) || (this.rentalReservationId != null && !this.rentalReservationId.equals(other.rentalReservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RentalReservation[ id=" + rentalReservationId + " ]";
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<RentalRate> getRentalRates() {
        return rentalRates;
    }

    public void setRentalRates(List<RentalRate> rentalRates) {
        this.rentalRates = rentalRates;
    }

    public TransitDriverDispatchRecord getTransitDriverDispatchRecord() {
        return transitDriverDispatchRecord;
    }

    public void setTransitDriverDispatchRecord(TransitDriverDispatchRecord transitDriverDispatchRecord) {
        this.transitDriverDispatchRecord = transitDriverDispatchRecord;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public PartnerCustomer getPartnerCustomer() {
        return partnerCustomer;
    }

    public void setPartnerCustomer(PartnerCustomer partnerCustomer) {
        this.partnerCustomer = partnerCustomer;
    }

    public Outlet getPickupOutlet() {
        return pickupOutlet;
    }

    public void setPickupOutlet(Outlet pickupOutlet) {
        this.pickupOutlet = pickupOutlet;
    }

    public Outlet getReturnOutlet() {
        return returnOutlet;
    }

    public void setReturnOutlet(Outlet returnOutlet) {
        this.returnOutlet = returnOutlet;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
