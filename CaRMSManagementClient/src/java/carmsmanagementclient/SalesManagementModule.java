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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.CarCategoryNotFoundException;
import util.exception.DeleteRentalRateRecordException;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidRentalRateValidityPeriodException;
import util.exception.RentalRateRecordExistException;
import util.exception.RentalRateRecordNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRentalRateRecordException;

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
                    doViewAllRentalRates();
                } else if (response == 3) {
                    doViewRentalRateDetails();
                } else if (response == 4) {
                    doUpdateRentalRate();
                } else if (response == 5) {
                    doDeleteRentalRate();
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
        System.out.println("*** CaRMS :: Sales :: View All Rental Rates ***\n");

        System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", "ID", "Rate Name", "Car Category", "Rate Type", "Rate Per Day", "Start Date", "End Date", "Is Enabled", "Is Used");

        List<RentalRate> rentalRateList = rentalRateSessionBeanRemote.retrieveAllRentalRates();

        for (RentalRate r : rentalRateList) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String isEnabled = "Enabled";
            if (!r.getIsEnabled()) {
                isEnabled = "Disabled";
            }
            String isUsed = "Used";
            if (!r.getIsUsed()) {
                isUsed = "Not Used";
            }
            String startDateTime = "Always valid";
            if (r.getRentalRateStartDateTime() != null) {
                startDateTime = sdf.format(r.getRentalRateStartDateTime());
            }
            String endDateTime = "Always valid";
            if (r.getRentalRateEndDateTime() != null) {
                endDateTime = sdf.format(r.getRentalRateEndDateTime());
            }
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", r.getRentalRateId(), r.getRentalRateName(), r.getCarCategory().getCategoryName(), r.getRentalRateType(), r.getRentalDailyRate(), startDateTime, endDateTime, isEnabled, isUsed);
        }
    }

    private void doViewRentalRateDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** CaRMS :: Sales :: View Rental Rate Details ***\n");
        System.out.print("Enter Rental Rate Name> ");
        String rentalRateName = scanner.nextLine().trim();
        
        System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", "ID", "Rate Name", "Car Category", "Rate Type", "Rate Per Day", "Start Date", "End Date", "Is Enabled", "Is Used");

        try {
            RentalRate r = rentalRateSessionBeanRemote.retrieveRentalRateByName(rentalRateName);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String isEnabled = "Enabled";
            if (!r.getIsEnabled()) {
                isEnabled = "Disabled";
            }
            String isUsed = "Used";
            if (!r.getIsUsed()) {
                isUsed = "Not Used";
            }
            String startDateTime = "Always valid";
            if (r.getRentalRateStartDateTime() != null) {
                startDateTime = sdf.format(r.getRentalRateStartDateTime());
            }
            String endDateTime = "Always valid";
            if (r.getRentalRateEndDateTime() != null) {
                endDateTime = sdf.format(r.getRentalRateEndDateTime());
            }
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", r.getRentalRateId(), r.getRentalRateName(), r.getCarCategory().getCategoryName(), r.getRentalRateType(), r.getRentalDailyRate(), startDateTime, endDateTime, isEnabled, isUsed);
        } catch (RentalRateRecordNotFoundException ex) {
            System.out.println("An error has occurred while creating the new rental rate!: The record is not found!\n");
        }
    }

    private void doUpdateRentalRate() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** CaRMS :: Sales :: Update Rental Rate ***\n");
        System.out.print("Enter Rental Rate Name> ");
        String rentalRateName = scanner.nextLine().trim();
        RentalRate r = new RentalRate();
        
        System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", "ID", "Rate Name", "Car Category", "Rate Type", "Rate Per Day", "Start Date", "End Date", "Is Enabled", "Is Used");

        try {
            r = rentalRateSessionBeanRemote.retrieveRentalRateByName(rentalRateName);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String isEnabled = "Enabled";
            if (!r.getIsEnabled()) {
                isEnabled = "Disabled";
            }
            String isUsed = "Used";
            if (!r.getIsUsed()) {
                isUsed = "Not Used";
            }
            String startDateTime = "Always valid";
            if (r.getRentalRateStartDateTime() != null) {
                startDateTime = sdf.format(r.getRentalRateStartDateTime());
            }
            String endDateTime = "Always valid";
            if (r.getRentalRateEndDateTime() != null) {
                endDateTime = sdf.format(r.getRentalRateEndDateTime());
            }
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", r.getRentalRateId(), r.getRentalRateName(), r.getCarCategory().getCategoryName(), r.getRentalRateType(), r.getRentalDailyRate(), startDateTime, endDateTime, isEnabled, isUsed);
        } catch (RentalRateRecordNotFoundException ex) {
            System.out.println("An error has occurred while creating the new rental rate!: The record is not found!\n");
        }

        RentalRate updatedRentalRate = new RentalRate();
        updatedRentalRate.setRentalRateId(r.getRentalRateId());
        String input;
        BigDecimal bigDecimalInput;

        System.out.print("Enter Rental Rate Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            updatedRentalRate.setRentalRateName(input);
        } else {
            updatedRentalRate.setRentalRateName(r.getRentalRateName());
        }

        System.out.print("Enter Car Category Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        String carCategoryName;
        if (input.length() > 0) {
            carCategoryName = input;
        } else {
            carCategoryName = r.getCarCategory().getCategoryName();
        }

        System.out.print("Enter Rental Rate Type (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            updatedRentalRate.setRentalRateType(input);
        } else {
            updatedRentalRate.setRentalRateType(r.getRentalRateType());
        }

        try {
            CarCategory carCategory = carCategorySessionBeanRemote.retrieveCarCategoryByName(carCategoryName);
            updatedRentalRate.setCarCategory(carCategory);
            System.out.print("Enter Rental Rate Per Day> ");
            bigDecimalInput = scanner.nextBigDecimal();
            scanner.nextLine();
            updatedRentalRate.setRentalDailyRate(bigDecimalInput);
            // MCUC9.3: validity period (if applicable)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            System.out.print("Is there a validity period? ('Y' to key in start and end date time subseqeuently)> ");
            String hasValidity = scanner.nextLine().trim();
            if (hasValidity.equals("Y")) {
                System.out.print("Enter Rental Rate Start Date (dd/MM/yyyy HH:mm) (blank if no change)> ");
                input = scanner.nextLine().trim();
                Date startDateTime;
                if (input.length() > 0) {
                    startDateTime = sdf.parse(input);
                } else {
                    startDateTime = r.getRentalRateStartDateTime();
                }

                System.out.print("Enter Rental Rate End Date (dd/MM/yyyy HH:mm) (blank if no change)> ");
                input = scanner.nextLine().trim();
                Date endDateTime;
                if (input.length() > 0) {
                    endDateTime = sdf.parse(input);
                } else {
                    endDateTime = r.getRentalRateEndDateTime();
                }
                // User input invalid validity period
                if (startDateTime.after(endDateTime)) {
                    throw new InvalidRentalRateValidityPeriodException("Invalid rental rate validity period: End date cannot be before start date!");
                }
                updatedRentalRate.setRentalRateStartDateTime(startDateTime);
                updatedRentalRate.setRentalRateEndDateTime(endDateTime);
            } else {
                System.out.println("There is no validity period entered. Rental rate will always be valid then.");
            }
            rentalRateSessionBeanRemote.updateRentalRate(updatedRentalRate);
            System.out.println("Rental rate updated successfully!: " + updatedRentalRate.getRentalRateId() + "\n");
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", "ID", "Rate Name", "Car Category", "Rate Type", "Rate Per Day", "Start Date", "End Date", "Is Enabled", "Is Used");
            String isEnabled = "Enabled";
            if (!updatedRentalRate.getIsEnabled()) {
                isEnabled = "Disabled";
            }
            String isUsed = "Used";
            if (!updatedRentalRate.getIsUsed()) {
                isUsed = "Not Used";
            }
            String startDateTime = "Always valid";
            if (updatedRentalRate.getRentalRateStartDateTime() != null) {
                startDateTime = sdf.format(updatedRentalRate.getRentalRateStartDateTime());
            }
            String endDateTime = "Always valid";
            if (updatedRentalRate.getRentalRateEndDateTime() != null) {
                endDateTime = sdf.format(updatedRentalRate.getRentalRateEndDateTime());
            }
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", updatedRentalRate.getRentalRateId(), updatedRentalRate.getRentalRateName(), updatedRentalRate.getCarCategory().getCategoryName(), updatedRentalRate.getRentalRateType(), updatedRentalRate.getRentalDailyRate(), sdf.format(updatedRentalRate.getRentalRateStartDateTime()), sdf.format(updatedRentalRate.getRentalRateEndDateTime()), isEnabled, isUsed);
        } catch (CarCategoryNotFoundException ex) {
            System.out.println("An error has occurred while retrieving car category!: The record is not found!\n");
        } catch (InvalidRentalRateValidityPeriodException ex) {
            System.out.println("Invalid rental rate validity period: End date cannot be before start date!\n");
        } catch (ParseException ex) {
            System.out.println("Invalid start and/or date time format!");
        } catch (UpdateRentalRateRecordException ex) {
            System.out.println("Name of rental rate record to be updated does not match the existing record!");
        } catch (RentalRateRecordNotFoundException ex) {
            System.out.println("Rental Rate ID [" + updatedRentalRate.getRentalRateId() + "] does not exist!");
        }
    }

    private void doDeleteRentalRate() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** CaRMS :: Sales :: Delete Rental Rate ***\n");
        System.out.print("Enter Rental Rate Name> ");
        String rentalRateName = scanner.nextLine().trim();
        RentalRate r = new RentalRate();
        Long rId = r.getRentalRateId();
        
        System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", "ID", "Rate Name", "Car Category", "Rate Type", "Rate Per Day", "Start Date", "End Date", "Is Enabled", "Is Used");

        try {
            r = rentalRateSessionBeanRemote.retrieveRentalRateByName(rentalRateName);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String isEnabled = "Enabled";
            if (!r.getIsEnabled()) {
                isEnabled = "Disabled";
            }
            String isUsed = "Used";
            if (!r.getIsUsed()) {
                isUsed = "Not Used";
            }
            String startDateTime = "Always valid";
            if (r.getRentalRateStartDateTime() != null) {
                startDateTime = sdf.format(r.getRentalRateStartDateTime());
            }
            String endDateTime = "Always valid";
            if (r.getRentalRateEndDateTime() != null) {
                endDateTime = sdf.format(r.getRentalRateEndDateTime());
            }
            System.out.printf("%4s%35s%20s%15s%15s%20s%20s%15s%15s\n", r.getRentalRateId(), r.getRentalRateName(), r.getCarCategory().getCategoryName(), r.getRentalRateType(), r.getRentalDailyRate(), startDateTime, endDateTime, isEnabled, isUsed);
            rentalRateSessionBeanRemote.deleteRentalRate(r.getRentalRateId());
            System.out.println("Rental rate deleted successfully!: " + rId + "\n");
        } catch (RentalRateRecordNotFoundException ex) {
            System.out.println("An error has occurred while creating the new rental rate!: The record is not found!\n");
        } catch (DeleteRentalRateRecordException ex) {
            System.out.println("Rental Rate ID [" + r.getRentalRateId() + "] does not exist!\n");
        }
    }
}
