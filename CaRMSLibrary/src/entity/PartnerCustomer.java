/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class PartnerCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerCustomerId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerCustomerEmail;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerCustomerFirstName;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerCustomerLastName;
    
    
    @OneToMany(mappedBy = "partnerCustomer")
    private List<RentalReservation> rentalReservations;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Partner partner;

    public PartnerCustomer() {
    }

    
    public PartnerCustomer(String partnerCustomerEmail, String partnerCustomerFirstName, String partnerCustomerLastName) {
        this();
        this.partnerCustomerEmail = partnerCustomerEmail;
        this.partnerCustomerFirstName = partnerCustomerFirstName;
        this.partnerCustomerLastName = partnerCustomerLastName;
    }

    public Long getPartnerCustomerId() {
        return partnerCustomerId;
    }

    public void setPartnerCustomerId(Long partnerCustomerId) {
        this.partnerCustomerId = partnerCustomerId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerCustomerId != null ? partnerCustomerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partnerCustomerId fields are not set
        if (!(object instanceof PartnerCustomer)) {
            return false;
        }
        PartnerCustomer other = (PartnerCustomer) object;
        if ((this.partnerCustomerId == null && other.partnerCustomerId != null) || (this.partnerCustomerId != null && !this.partnerCustomerId.equals(other.partnerCustomerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartnerCustomer[ id=" + partnerCustomerId + " ]";
    }

    public String getPartnerCustomerEmail() {
        return partnerCustomerEmail;
    }

    public void setPartnerCustomerEmail(String partnerCustomerEmail) {
        this.partnerCustomerEmail = partnerCustomerEmail;
    }

    public String getPartnerCustomerFirstName() {
        return partnerCustomerFirstName;
    }

    public void setPartnerCustomerFirstName(String partnerCustomerFirstName) {
        this.partnerCustomerFirstName = partnerCustomerFirstName;
    }

    public String getPartnerCustomerLastName() {
        return partnerCustomerLastName;
    }

    public void setPartnerCustomerLastName(String partnerCustomerLastName) {
        this.partnerCustomerLastName = partnerCustomerLastName;
    }

    public List<RentalReservation> getRentalReservations() {
        return rentalReservations;
    }

    public void setRentalReservations(List<RentalReservation> rentalReservations) {
        this.rentalReservations = rentalReservations;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
}
