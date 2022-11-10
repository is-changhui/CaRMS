/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;

/**
 *
 * @author Darie
 */
public class SalesManagementModule {
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    private Employee currentEmployeeEntity;
    

    public SalesManagementModule() {
    }

    public SalesManagementModule(EmployeeSessionBeanRemote employeeSessionBeanRemote, Employee currentEmployeeEntity) {
        this();
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.currentEmployeeEntity = currentEmployeeEntity;
    }
    
    
    
    
    
}
