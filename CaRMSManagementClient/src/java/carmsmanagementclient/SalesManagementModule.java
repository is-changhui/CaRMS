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
    
    public void menuSalesManagement() throws InvalidAccessRightException {
        if ((currentEmployeeEntity.getEmployeeAccessRight() == EmployeeAccessRightEnum.EMPLOYEE) || (currentEmployeeEntity.getEmployeeAccessRight() == EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE)) {
            throw new InvalidAccessRightException("You don't have OPERATIONS MANAGER or SALES MANAGER rights to access the sales management module.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** CaRMS :: Sales Management ***\n");
            System.out.println("1: Sales");
            System.out.println("2: Operations");
            System.out.println("3: Back\n");
            response = 0;
            
            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    menuSales();
                } else if(response == 2) {
                    menuOperations();
                } else if(response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if (response == 3) {
                break;
            }
        }
    }
    
    public void menuSales() throws InvalidAccessRightException {
        if (currentEmployeeEntity.getEmployeeAccessRight() != EmployeeAccessRightEnum.SALES_MANAGER) {
            throw new InvalidAccessRightException("You don't have SALES MANAGER rights to access the rental rate operations.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** CaRMS :: Sales ***\n");
            System.out.println("1: Create Rental Rate");
            System.out.println("2: View All Rental Rates");
            System.out.println("3: View Rental Rate Details");
            System.out.println("4: Update Rental Rate");
            System.out.println("5: Delete Rental Rate");
            System.out.println("6: Back\n");
            response = 0;
            
            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
//                    doCreateRentalRate();
                } else if(response == 2) {
//                    doViewAllRentalRates();
                } else if(response == 3) {
//                    doViewRentalRateDetails();
                } else if(response == 4) {
//                    doUpdateRentalRate();
                } else if(response == 5) {
//                    doDeleteRentalRate();
                } else if(response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if (response == 6) {
                break;
            }
        }
    }
    
    public void menuOperations() throws InvalidAccessRightException {
        if (currentEmployeeEntity.getEmployeeAccessRight() != EmployeeAccessRightEnum.OPERATIONS_MANAGER) {
            throw new InvalidAccessRightException("You don't have OPERATIONS MANAGER rights to access the car model, car and transit driver dispatch record operations.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** CaRMS :: Operations ***\n");
            System.out.println("1: Create New Model");
            System.out.println("2: View All Models");
            System.out.println("3: Update Model");
            System.out.println("4: Delete Model");
            System.out.println("---------------------------");
            System.out.println("5: Create New Car");
            System.out.println("6: View All Cars");
            System.out.println("7: View Car Details");
            System.out.println("8: Update Car");
            System.out.println("9: Delete Car");
            System.out.println("---------------------------");
            System.out.println("10: View Transit Driver Dispatch Records for Current Day Reservations");
            System.out.println("11: Assign Transit Driver");
            System.out.println("12: Update Transit As Completed");
            System.out.println("---------------------------");
            System.out.println("13: Back\n");
            response = 0;
            
            while (response < 1 || response > 13) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
//                    menuSales();
                } else if(response == 2) {
//                    menuOperations();
                } else if(response == 3) {
//                    menuOperations();
                } else if(response == 4) {
//                    menuOperations();
                } else if(response == 5) {
//                    menuOperations();
                } else if(response == 6) {
//                    menuOperations();
                } else if(response == 7) {
//                    menuOperations();
                } else if(response == 8) {
//                    menuOperations();
                } else if(response == 9) {
//                    menuOperations();
                } else if(response == 10) {
//                    menuOperations();
                } else if(response == 11) {
//                    menuOperations();
                } else if(response == 12) {
//                    menuOperations();
                } else if(response == 13) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if (response == 13) {
                break;
            }
        }
    }
    
    
    
}
