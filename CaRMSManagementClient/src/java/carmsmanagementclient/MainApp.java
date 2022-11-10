/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.CarCategory;
import entity.Employee;
import java.util.Scanner;
import util.exception.CarCategoryExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
public class MainApp {
    
    private CarCategorySessionBeanRemote carCategorySessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private Employee currentEmployeeEntity;

    
    public MainApp() {
    }

    public MainApp(CarCategorySessionBeanRemote carCategorySessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this();
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
            
            while(response < 1 || response > 2) {
                System.out.print("> ");
                response = scanner.nextInt();
                if(response == 1) {
                    System.out.println("Login successful!\n");
                    doCreateNewCarCategory();
//                    try {
//                        doLogin();
//                        System.out.println("Login successful!\n");
//                    } catch (InvalidLoginCredentialException ex) {
//                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
//                    }
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
        
        if(username.length() > 0 && password.length() > 0) {
            currentEmployeeEntity = employeeSessionBeanRemote.employeeLogin(username, password);      
        } else {
            throw new InvalidLoginCredentialException("Missing login credentials!");
        }
    }
     
    private void doCreateNewCarCategory() {
        Scanner scanner = new Scanner(System.in);
        CarCategory newCarCategoryEntity = new CarCategory();
        
        System.out.println("*** CaRMS Management Client :: System Administration :: Create New Car Category ***\n");
        System.out.print("Enter Car Category Name> ");
        newCarCategoryEntity.setCategoryName(scanner.nextLine().trim());
        
        
//        Set<ConstraintViolation<StaffEntity>>constraintViolations = validator.validate(newStaffEntity);
//        
//        if(constraintViolations.isEmpty())
//        {
            try
            {
                Long newCarCategoryId = carCategorySessionBeanRemote.createNewCarCategory(newCarCategoryEntity);
                System.out.println("New car category created successfully!: " + newCarCategoryId + "\n");
            }
            catch(CarCategoryExistException ex)
            {
                System.out.println("An error has occurred while creating the new car category: The name already exist! \n");
            }
            catch(UnknownPersistenceException ex)
            {
                System.out.println("An unknown error has occurred while creating the new car category!: " + ex.getMessage() + "\n");
            }
            catch(InputDataValidationException ex)
            {
                System.out.println(ex.getMessage() + "\n");
            }
//        }
//        else
//        {
//            showInputDataValidationErrorsForStaffEntity(constraintViolations);
//        }
    }
}
