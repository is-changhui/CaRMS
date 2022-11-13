/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.CarModelSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.EjbTimerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import ejb.session.stateless.RentalReservationSessionBeanRemote;
import ejb.session.stateless.TransitDriverDispatchRecordSessionBeanRemote;
import entity.Employee;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Darie
 */
public class MainApp {

    private TransitDriverDispatchRecordSessionBeanRemote transitDriverDispatchRecordSessionBeanRemote;
    private EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote;
    private RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote;
    private RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    private OutletSessionBeanRemote outletSessionBeanRemote;
    private CarSessionBeanRemote carSessionBeanRemote;
    private CarModelSessionBeanRemote carModelSessionBeanRemote;
    private CarCategorySessionBeanRemote carCategorySessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    private Employee currentEmployeeEntity;

    private CustomerServiceModule customerServiceModule;
    private SalesManagementModule salesManagementModule;

    public MainApp() {
    }

    public MainApp(TransitDriverDispatchRecordSessionBeanRemote transitDriverDispatchRecordSessionBeanRemote, EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote, RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote, RentalRateSessionBeanRemote rentalRateSessionBeanRemote, OutletSessionBeanRemote outletSessionBeanRemote, CarSessionBeanRemote carSessionBeanRemote, CarModelSessionBeanRemote carModelSessionBeanRemote, CarCategorySessionBeanRemote carCategorySessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this();
        this.transitDriverDispatchRecordSessionBeanRemote = transitDriverDispatchRecordSessionBeanRemote;
        this.ejbTimerSessionBeanRemote = ejbTimerSessionBeanRemote;
        this.rentalReservationSessionBeanRemote = rentalReservationSessionBeanRemote;
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.carModelSessionBeanRemote = carModelSessionBeanRemote;
        this.carCategorySessionBeanRemote = carCategorySessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Car Rental Management System (CaRMS) Management Client ***");
            // MCUC5: Input required date to do car allocation manually
            System.out.println("1: Perform Car Allocation");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doAllocateCarToSpecificDayReservations();
                } else if (response == 2) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        customerServiceModule = new CustomerServiceModule(rentalReservationSessionBeanRemote, currentEmployeeEntity);
                        salesManagementModule = new SalesManagementModule(rentalRateSessionBeanRemote, outletSessionBeanRemote, carSessionBeanRemote, carModelSessionBeanRemote, carCategorySessionBeanRemote, currentEmployeeEntity);
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
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
    
    // MCUC5
    private void doAllocateCarToSpecificDayReservations() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.println("*** CaRMS Management Client :: SPECIAL OPS :: Allocate Cars to Specified Day Reservations ***\n");
        
        System.out.print("Enter Date To Perform Car Allocations For (DD/MM/YYYY)> ");
        String specifiedDate = scanner.nextLine().trim();
        try {
            Date date = sdf.parse(specifiedDate);
            ejbTimerSessionBeanRemote.allocateCarsToCurrentDayReservations(date);
            System.out.println("SPECIAL OPS COMPLETED:: Car allocations made successfully for the specified date, " + specifiedDate);
        } catch (ParseException ex) {
            System.out.println("Invalid date format!");
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
            if (response == 3) {
                break;
            }
        }
    }
}
