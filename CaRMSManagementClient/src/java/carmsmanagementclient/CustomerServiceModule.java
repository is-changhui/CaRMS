/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author Darie
 */
public class CustomerServiceModule {
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    private Employee currentEmployeeEntity;

    public CustomerServiceModule() {
    }

    public CustomerServiceModule(EmployeeSessionBeanRemote employeeSessionBeanRemote, Employee currentEmployeeEntity) {
        this();
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.currentEmployeeEntity = currentEmployeeEntity;
    }
    
    public void menuCustomerService() throws InvalidAccessRightException
    {
        if(currentEmployeeEntity.getEmployeeAccessRight() != EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE)
        {
            throw new InvalidAccessRightException("You don't have CUSTOMER SERVICE EXECUTIVE rights to access the customer service module.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** CaRMS :: Customer Service ***\n");
            System.out.println("1: Pickup Car");
            System.out.println("2: Return Car");
            System.out.println("3: Back\n");
            response = 0;
            
            while(response < 1 || response > 3)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doPickupCar();
                }
                else if(response == 2)
                {
//                    doReturnCar();
                }
                else if(response == 3)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 3)
            {
                break;
            }
        }
    }
    
    private void doPickupCar() {
        System.out.println("Work in Progress...");
    }
    
    
}
