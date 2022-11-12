/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.CarModelSessionBeanRemote;
import ejb.session.stateless.CarPickupSessionBeanRemote;
import ejb.session.stateless.CarReturnSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Darie
 */
public class MainApp {
    
    private RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    private OutletSessionBeanRemote outletSessionBeanRemote;
    private CarSessionBeanRemote carSessionBeanRemote;
    private CarReturnSessionBeanRemote carReturnSessionBeanRemote;
    private CarPickupSessionBeanRemote carPickupSessionBeanRemote;
    private CarModelSessionBeanRemote carModelSessionBeanRemote;
    private CarCategorySessionBeanRemote carCategorySessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    private Employee currentEmployeeEntity;
    
    private CustomerServiceModule customerServiceModule;
    private SalesManagementModule salesManagementModule;

    
    public MainApp() {
    }

    public MainApp(RentalRateSessionBeanRemote rentalRateSessionBeanRemote, OutletSessionBeanRemote outletSessionBeanRemote, CarSessionBeanRemote carSessionBeanRemote, CarReturnSessionBeanRemote carReturnSessionBeanRemote, CarPickupSessionBeanRemote carPickupSessionBeanRemote, CarModelSessionBeanRemote carModelSessionBeanRemote, CarCategorySessionBeanRemote carCategorySessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this();
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.carReturnSessionBeanRemote = carReturnSessionBeanRemote;
        this.carPickupSessionBeanRemote = carPickupSessionBeanRemote;
        this.carModelSessionBeanRemote = carModelSessionBeanRemote;
        this.carCategorySessionBeanRemote = carCategorySessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Car Rental Management System (CaRMS) Management Client ***");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while (response < 1 || response > 2) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        customerServiceModule = new CustomerServiceModule(employeeSessionBeanRemote, currentEmployeeEntity);
                        salesManagementModule = new SalesManagementModule(rentalRateSessionBeanRemote, outletSessionBeanRemote, carSessionBeanRemote, carModelSessionBeanRemote, carCategorySessionBeanRemote, currentEmployeeEntity);
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            if (response == 2) {
                break;
            }
        }
    }
    
    
    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** CaRMS Management Client :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if (username.length() > 0 && password.length() > 0) {
            currentEmployeeEntity = employeeSessionBeanRemote.employeeLogin(username, password);      
        } else {
            throw new InvalidLoginCredentialException("Missing login credentials!");
        }
    }
     
     
    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** CaRMS Management Client ***\n");
            System.out.println("You are login as " + currentEmployeeEntity.getEmployeeName() + " with " + currentEmployeeEntity.getEmployeeAccessRight().toString() + " rights\n");
            System.out.println("1: Sales Management");
            System.out.println("2: Customer Service");
            System.out.println("3: Logout\n");
            response = 0;
            
            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    try {
                        salesManagementModule.menuSalesManagement();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    try {
                        customerServiceModule.menuCustomerService();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            if (response == 3)
            {
                break;
            }
        }
     }
     
//    private void doCreateNewCarCategory() {
//        Scanner scanner = new Scanner(System.in);
//        CarCategory newCarCategoryEntity = new CarCategory();
//        
//        System.out.println("*** CaRMS Management Client :: System Administration :: Create New Car Category ***\n");
//        System.out.print("Enter Car Category Name> ");
//        newCarCategoryEntity.setCategoryName(scanner.nextLine().trim());
//        
//        
////        Set<ConstraintViolation<StaffEntity>>constraintViolations = validator.validate(newStaffEntity);
////        
////        if(constraintViolations.isEmpty())
////        {
//            try
//            {
//                Long newCarCategoryId = carCategorySessionBeanRemote.createNewCarCategory(newCarCategoryEntity);
//                System.out.println("New car category created successfully!: " + newCarCategoryId + "\n");
//            }
//            catch(CarCategoryExistException ex)
//            {
//                System.out.println("An error has occurred while creating the new car category: The name already exist! \n");
//            }
//            catch(UnknownPersistenceException ex)
//            {
//                System.out.println("An unknown error has occurred while creating the new car category!: " + ex.getMessage() + "\n");
//            }
//            catch(InputDataValidationException ex)
//            {
//                System.out.println(ex.getMessage() + "\n");
//            }
////        }
////        else
////        {
////            showInputDataValidationErrorsForStaffEntity(constraintViolations);
////        }
//    }
}
