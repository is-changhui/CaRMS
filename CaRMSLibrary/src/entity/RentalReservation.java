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

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalReservationId;
    @Column(nullable = false)
    private Boolean rentalReservationIsPaid;
    @Column(nullable = false)
    private Boolean rentalReservationIsCancelled;
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date creditCardExpiry;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalReservationPickupDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date rentalReservationReturnDate;
    
    
    @OneToOne(optional = true)
    private Car car;
    
    @ManyToMany
    private List<RentalRate> rentalRates;
    
    @OneToOne(optional = true, mappedBy = "rentalReservation")
    private TransitDriverDispatchRecord transitDriverDispatchRecord;
    
    @ManyToOne(optional = true)
    private Partner partner;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @OneToOne(optional = false)
    private Outlet pickUpOutlet;
    
    @OneToOne(optional = false)
    private Outlet returnOutlet;
    
    @OneToOne(optional = false)
    private CarPickup carPickupRecord;
    
    @OneToOne(optional = false)
    private CarReturn carReturnRecord;
    
    
    
    public RentalReservation() {
        rentalReservationIsCancelled = false;
        rentalRates = new ArrayList<>();
    }

    public RentalReservation(Boolean reservationIsPaid, Boolean reservationIsCancelled, BigDecimal reservationAmount, String creditCardName, String creditCardNumber, String creditCardCVV, Date creditCardExpiry, Date reservationPickupDate, Date reservationReturnDate) {
        this();
        this.rentalReservationIsPaid = reservationIsPaid;
        this.rentalReservationIsCancelled = reservationIsCancelled;
        this.rentalReservationAmount = reservationAmount;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCVV = creditCardCVV;
        this.creditCardExpiry = creditCardExpiry;
        this.rentalReservationPickupDate = reservationPickupDate;
        this.rentalReservationReturnDate = reservationReturnDate;
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
     * @param rentalReservationIsCancelled the rentalReservationIsCancelled to set
     */
    public void setRentalReservationIsCancelled(Boolean rentalReservationIsCancelled) {
        this.rentalReservationIsCancelled = rentalReservationIsCancelled;
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
    public Date getCreditCardExpiry() {
        return creditCardExpiry;
    }

    /**
     * @param creditCardExpiry the creditCardExpiry to set
     */
    public void setCreditCardExpiry(Date creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    /**
     * @return the rentalReservationPickupDate
     */
    public Date getRentalReservationPickupDate() {
        return rentalReservationPickupDate;
    }

    /**
     * @param rentalReservationPickupDate the rentalReservationPickupDate to set
     */
    public void setRentalReservationPickupDate(Date rentalReservationPickupDate) {
        this.rentalReservationPickupDate = rentalReservationPickupDate;
    }

    /**
     * @return the rentalReservationReturnDate
     */
    public Date getRentalReservationReturnDate() {
        return rentalReservationReturnDate;
    }

    /**
     * @param rentalReservationReturnDate the rentalReservationReturnDate to set
     */
    public void setRentalReservationReturnDate(Date rentalReservationReturnDate) {
        this.rentalReservationReturnDate = rentalReservationReturnDate;
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
    
}
