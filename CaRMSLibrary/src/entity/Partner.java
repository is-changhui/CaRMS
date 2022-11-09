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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class Partner implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerName;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerUsername;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String partnerPassword;
    
//    @OneToMany(mappedBy = "partner")
//    private List<Customer> customers;

    
    public Partner() {
    }
    
    public Partner(String partnerName, String partnerUsername, String partnerPassword) {
        this();
        this.partnerName = partnerName;
        this.partnerUsername = partnerUsername;
        this.partnerPassword = partnerPassword;
    }
    
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }
    
    /**
     * @return the partnerName
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * @param partnerName the partnerName to set
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * @return the partnerUsername
     */
    public String getPartnerUsername() {
        return partnerUsername;
    }

    /**
     * @param partnerUsername the partnerUsername to set
     */
    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    /**
     * @return the partnerPassword
     */
    public String getPartnerPassword() {
        return partnerPassword;
    }

    /**
     * @param partnerPassword the partnerPassword to set
     */
    public void setPartnerPassword(String partnerPassword) {
        this.partnerPassword = partnerPassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerId != null ? partnerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partnerId fields are not set
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.partnerId == null && other.partnerId != null) || (this.partnerId != null && !this.partnerId.equals(other.partnerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partner[ id=" + partnerId + " ]";
    }
    
}
