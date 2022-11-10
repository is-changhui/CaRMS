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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author Darie
 */
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String employeeName;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String employeeUsername;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String employeePassword;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private EmployeeAccessRightEnum employeeAccessRight;
    
//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private Outlet outlet;
    
//    @OneToMany(mappedBy = "transitDriver")
//    private List<TransitDriverDispatchRecord> transitDriverDispatchRecords;
    
    

    
    public Employee() {
    }

    public Employee(String employeeName, String employeeUsername, String employeePassword, EmployeeAccessRightEnum employeeAccessRight) {
        this();
        this.employeeName = employeeName;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
        this.employeeAccessRight = employeeAccessRight;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the employeeUsername
     */
    public String getEmployeeUsername() {
        return employeeUsername;
    }

    /**
     * @param employeeUsername the employeeUsername to set
     */
    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    /**
     * @return the employeePassword
     */
    public String getEmployeePassword() {
        return employeePassword;
    }

    /**
     * @param employeePassword the employeePassword to set
     */
    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    /**
     * @return the employeeAccessRight
     */
    public EmployeeAccessRightEnum getEmployeeAccessRight() {
        return employeeAccessRight;
    }

    /**
     * @param employeeAccessRight the employeeAccessRight to set
     */
    public void setEmployeeAccessRight(EmployeeAccessRightEnum employeeAccessRight) {
        this.employeeAccessRight = employeeAccessRight;
    }
    
    /**
     * @return the outlet
     */
//    public Outlet getOutlet() {
//        return outlet;
//    }

    /**
     * @param outlet the outlet to set
     */
//    public void setOutlet(Outlet outlet) {
//        this.outlet = outlet;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the employeeId fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Employee[ id=" + employeeId + " ]";
    }
    
}
