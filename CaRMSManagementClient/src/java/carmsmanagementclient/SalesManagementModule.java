/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.CarModelSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.CarCategory;
import entity.Employee;
import entity.RentalRate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.CarCategoryNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidRentalRateValidityPeriodException;
import util.exception.RentalRateRecordExistException;
import util.exception.RentalRateRecordNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
public class SalesManagementModule {

    private RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    private OutletSessionBeanRemote outletSessionBeanRemote;
    private CarSessionBeanRemote carSessionBeanRemote;
    private CarModelSessionBeanRemote carModelSessionBeanRemote;
    private CarCategorySessionBeanRemote carCategorySessionBeanRemote;

    private Employee currentEmployeeEntity;

    public SalesManagementModule() {
    }

    public SalesManagementModule(RentalRateSessionBeanRemote rentalRateSessionBeanRemote, OutletSessionBeanRemote outletSessionBeanRemote, CarSessionBeanRemote carSessionBeanRemote, CarModelSessionBeanRemote carModelSessionBeanRemote, CarCategorySessionBeanRemote carCategorySessionBeanRemote, Employee currentEmployeeEntity) {
        this();
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.carModelSessionBeanRemote = carModelSessionBeanRemote;
        this.carCategorySessionBeanRemote = carCategorySessionBeanRemote;
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
                } else if (response == 2) {
                    menuOperations();
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
                    doCreateRentalRate();
                } else if (response == 2) {
//                    doViewAllRentalRates();
                } else if (response == 3) {
//                    doViewRentalRateDetails();
                } else if (response == 4) {
//                    doUpdateRentalRate();
                } else if (response == 5) {
//                    doDeleteRentalRate();
                } else if (response == 6) {
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
//                    doCreateNewModel();
                } else if (response == 2) {
//                    doViewAllModels();
                } else if (response == 3) {
//                    doUpdateModel();
                } else if (response == 4) {
//                    doDeleteModel();
                } else if (response == 5) {
//                    doCreateNewCar();
                } else if (response == 6) {
//                    doViewAllCars();
                } else if (response == 7) {
//                    doViewCarDetails();
                } else if (response == 8) {
//                    doUpdateCar();
                } else if (response == 9) {
//                    doDeleteCar();
                } else if (response == 10) {
//                    doViewTransitDriverDispatchRecordsForCurrentDayReservations();
                } else if (response == 11) {
//                    doAssignTransitDriver();
                } else if (response == 12) {
//                    doUpdateTransitAsCompleted();
                } else if (response == 13) {
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

    private void doCreateRentalRate() {
        Scanner scanner = new Scanner(System.in);
        RentalRate newRentalRate = new RentalRate();

        System.out.println("*** CaRMS :: Sales :: Create Rental Rate ***\n");
        System.out.print("Enter Rental Rate Name> ");
        newRentalRate.setRentalRateName(scanner.nextLine().trim());
        System.out.print("Enter Car Category Name> ");
        String carCategoryName = scanner.nextLine().trim();
        System.out.print("Enter Rental Rate Type> ");
        newRentalRate.setRentalRateType(scanner.nextLine().trim());

        try {
            CarCategory carCategory = carCategorySessionBeanRemote.retrieveCarCategoryByName(carCategoryName);
            newRentalRate.setCarCategory(carCategory);
            System.out.print("Enter Rental Rate Per Day> ");
            newRentalRate.setRentalDailyRate(scanner.nextBigDecimal());
            scanner.nextLine();
            // MCUC9.3: validity period (if applicable)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            System.out.print("Is there a validity period? ('Y' to key in start and end date time subseqeuently)> ");
            String hasValidity = scanner.nextLine().trim();
            if (hasValidity.equals("Y")) {
                System.out.print("Enter Rental Rate Start Date (dd/MM/yyyy HH:mm)> ");
                Date startDateTime = sdf.parse(scanner.nextLine().trim());
                System.out.print("Enter Rental Rate End Date (dd/MM/yyyy HH:mm)> ");
                Date endDateTime = sdf.parse(scanner.nextLine().trim());
                // User input invalid validity period
                if (startDateTime.after(endDateTime)) {
                    throw new InvalidRentalRateValidityPeriodException("Invalid rental rate validity period: End date cannot be before start date!");
                }
                newRentalRate.setRentalRateStartDateTime(startDateTime);
                newRentalRate.setRentalRateEndDateTime(endDateTime);
            } else {
                System.out.println("There is no validity period entered. Rental rate will always be valid then.");
            }
            Long newRentalRateId = rentalRateSessionBeanRemote.createNewRentalRateJoinCarCategory(newRentalRate, carCategory.getCategoryId());
            System.out.println("New rental rate created successfully!: " + newRentalRateId + "\n");
        } catch (RentalRateRecordNotFoundException ex) {
            System.out.println("An error has occurred while creating the new rental rate!: The record already exist!\n");
        } catch (CarCategoryNotFoundException ex) {
            System.out.println("An error has occurred while retrieving car category!: The record is not found!\n");
        } catch (RentalRateRecordExistException ex) {
            System.out.println("An error has occurred while creating the new rental rate!: The record already exist!\n");
        } catch (UnknownPersistenceException ex) {
            System.out.println("An unknown error has occurred while creating the new rental rate!: " + ex.getMessage() + "\n");
        } catch (InputDataValidationException ex) {
            System.out.println("Data validation check failed! Input data did not satisfy 1 or more constraints!");
        } catch (InvalidRentalRateValidityPeriodException ex) {
            System.out.println("Invalid rental rate validity period: End date cannot be before start date!\n");
        } catch (ParseException ex) {
            System.out.println("Invalid start and/or date time format!");
        }
    }

    private void doViewAllRentalRates() {
        
    }

    private void doViewRentalRateDetails() {

    }

    private void doUpdateRentalRate() {

    }

    private void doDeleteRentalRate() {

    }
}
