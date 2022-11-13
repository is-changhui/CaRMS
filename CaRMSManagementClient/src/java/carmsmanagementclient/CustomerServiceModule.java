/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.RentalReservationSessionBeanRemote;
import entity.Employee;
import entity.RentalReservation;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InvalidAccessRightException;
import util.exception.RefusalToPayForReservationException;
import util.exception.RentalReservationNotFoundException;

/**
 *
 * @author Darie
 */
public class CustomerServiceModule {

    private RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote;

    private Employee currentEmployeeEntity;

    public CustomerServiceModule() {
    }

    public CustomerServiceModule(RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote, Employee currentEmployeeEntity) {
        this();
        this.rentalReservationSessionBeanRemote = rentalReservationSessionBeanRemote;
        this.currentEmployeeEntity = currentEmployeeEntity;
    }

    public void menuCustomerService() throws InvalidAccessRightException {
        if (currentEmployeeEntity.getEmployeeAccessRight() != EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE) {
            throw new InvalidAccessRightException("You don't have CUSTOMER SERVICE EXECUTIVE rights to access the customer service module.");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CaRMS :: Customer Service ***\n");
            System.out.println("1: Pickup Car");
            System.out.println("2: Return Car");
            System.out.println("3: Back\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doPickupCar();
                } else if (response == 2) {
                    doReturnCar();
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

    private void doPickupCar() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("*** CaRMS :: Customer Service :: Pickup Car ***\n");

        List<RentalReservation> rentalReservationList = rentalReservationSessionBeanRemote.retrieveRentalReservationsByPickupOutlet(currentEmployeeEntity.getOutlet().getOutletId());

        if (rentalReservationList.size() > 0) {
            try {
                System.out.printf("%4s%30s%30s%30s%20s%20s%15s\n", "ID", "License Plate Number", "Make", "Model Name", "Colour", "Car Status", "Is Enabled");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

                for (RentalReservation r : rentalReservationList) {
                    String isPaid = "Paid";
                    if (!r.getRentalReservationIsPaid()) {
                        isPaid = "Unpaid";
                    }
                    String isCancelled = "Ongoing";
                    if (r.getRentalReservationIsCancelled()) {
                        isCancelled = "Cancelled";
                    }
                    String pickupDateTime = sdf.format(r.getRentalReservationPickupDateTime());
                    String returnDateTime = sdf.format(r.getRentalReservationReturnDateTime());

                    System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", r.getRentalReservationId(),
                            r.getCarModel().getCarMake(), r.getCarModel().getModelName(),
                            r.getCarCategory().getCategoryName(), pickupDateTime,
                            returnDateTime, r.getRentalReservationAmount(),
                            isPaid, isCancelled);
                }

                System.out.print("Enter Rental Reservation ID> ");
                Long rentalReservationId = scanner.nextLong();
                scanner.nextLine();

                RentalReservation rentalReservation = rentalReservationSessionBeanRemote.retrieveRentalReservationById(rentalReservationId);
                if (!rentalReservation.getRentalReservationIsPaid()) {
                    System.out.print("Make payment for rental fee now? ('Y' for immediate payment)> ");
                    String reply = scanner.nextLine().trim();
                    if (reply.equals("Y")) {
                        rentalReservation.setRentalReservationIsPaid(Boolean.TRUE);
                        System.out.println("Payment for Rental Reservation is successful! Your credit card ending "
                                + rentalReservation.getCreditCardNumber().substring(rentalReservation.getCreditCardNumber().length() - 4)
                                + " is being charged $" + rentalReservation.getRentalReservationAmount());
                    } else {
                        throw new RefusalToPayForReservationException("Payment for reservation is mandatory before car collection! Please make payment now!");
                    }
                }
                rentalReservationSessionBeanRemote.initiatePickupCar(rentalReservationId);
                System.out.println("Pickup Car successful!");
            } catch (RefusalToPayForReservationException ex) {
                System.out.println("Payment for reservation is mandatory before car collection! Please make payment now!");
            } catch (RentalReservationNotFoundException ex) {
                System.out.println("Rental Reservation does not exist!");
            }
        } else {
            System.out.println("There are no cars waiting for pickup!");
        }
        System.out.print("Press <Enter> key to continue> ");
        scanner.nextLine();
    }

    private void doReturnCar() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("*** CaRMS :: Customer Service :: Return Car ***\n");

        List<RentalReservation> rentalReservationList = rentalReservationSessionBeanRemote.retrieveRentalReservationsByReturnOutlet(currentEmployeeEntity.getOutlet().getOutletId());

        if (rentalReservationList.size() > 0) {
            System.out.printf("%4s%30s%30s%30s%20s%20s%15s\n", "ID", "License Plate Number", "Make", "Model Name", "Colour", "Car Status", "Is Enabled");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

            for (RentalReservation r : rentalReservationList) {
                String isPaid = "Paid";
                if (!r.getRentalReservationIsPaid()) {
                    isPaid = "Unpaid";
                }
                String isCancelled = "Ongoing";
                if (r.getRentalReservationIsCancelled()) {
                    isCancelled = "Cancelled";
                }
                String pickupDateTime = sdf.format(r.getRentalReservationPickupDateTime());
                String returnDateTime = sdf.format(r.getRentalReservationReturnDateTime());

                System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", r.getRentalReservationId(),
                        r.getCarModel().getCarMake(), r.getCarModel().getModelName(),
                        r.getCarCategory().getCategoryName(), pickupDateTime,
                        returnDateTime, r.getRentalReservationAmount(),
                        isPaid, isCancelled);
            }

            System.out.print("Enter Rental Reservation ID> ");
            Long rentalReservationId = scanner.nextLong();
            scanner.nextLine();

            rentalReservationSessionBeanRemote.initiateReturnCar(rentalReservationId);
            System.out.println("Return Car successful!");
        } else {
            System.out.println("There are no cars waiting for return!");
        }
        System.out.print("Press <Enter> key to continue> ");
        scanner.nextLine();
    }
}
