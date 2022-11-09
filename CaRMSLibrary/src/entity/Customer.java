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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class Customer implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String customerEmail;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String customerMobileNum;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String customerUsername;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String customerPassword;
    
    @ManyToOne(optional = true)
    private Partner partner;
    
    

    
    public Customer() {
    }

    public Customer(String customerEmail, String customerMobileNum, String customerUsername, String customerPassword) {
        
        this();
        this.customerEmail = customerEmail;
        this.customerMobileNum = customerMobileNum;
        this.customerUsername = customerUsername;
        this.customerPassword = customerPassword;
    }
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    /**
     * @return the customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * @param customerEmail the customerEmail to set
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @return the customerMobileNum
     */
    public String getCustomerMobileNum() {
        return customerMobileNum;
    }

    /**
     * @param customerMobileNum the customerMobileNum to set
     */
    public void setCustomerMobileNum(String customerMobileNum) {
        this.customerMobileNum = customerMobileNum;
    }

    /**
     * @return the customerUsername
     */
    public String getCustomerUsername() {
        return customerUsername;
    }

    /**
     * @param customerUsername the customerUsername to set
     */
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    /**
     * @return the customerPassword
     */
    public String getCustomerPassword() {
        return customerPassword;
    }

    /**
     * @param customerPassword the customerPassword to set
     */
    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }
    
}
