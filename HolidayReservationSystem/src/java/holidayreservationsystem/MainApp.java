package holidayreservationsystem;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.CarModelSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.PartnerCustomerSessionBeanRemote;
import ejb.session.stateless.PartnerSessionBeanRemote;
import ejb.session.stateless.RentalReservationSessionBeanRemote;
import entity.CarCategory;
import entity.CarModel;
import entity.Outlet;
import entity.Partner;
import entity.PartnerCustomer;
import entity.RentalReservation;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarModelNotFoundException;
import util.exception.EmptyRentalRateException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.OutletNotFoundException;
import util.exception.PartnerCustomerNameExistException;
import util.exception.PartnerNotFoundException;
import util.exception.PickupReturnClashException;
import util.exception.PickupReturnTimeException;
import util.exception.RentalReservationNotFoundException;
import util.exception.UnknownPersistenceException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darie
 */
public class MainApp {
    
    private PartnerSessionBeanRemote partnerSessionBeanRemote;
    private RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote;
    private CarModelSessionBeanRemote carModelSessionBeanRemote;
    private CarCategorySessionBeanRemote carCategorySessionBeanRemote;
    private OutletSessionBeanRemote outletSessionBeanRemote;
    private PartnerCustomerSessionBeanRemote partnerCustomerSessionBeanRemote;
    
    private Partner currentPartnerEntity;

    public MainApp() {

    }

    public MainApp(PartnerSessionBeanRemote partnerSessionBeanRemote, RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote, CarModelSessionBeanRemote carModelSessionBeanRemote, CarCategorySessionBeanRemote carCategorySessionBeanRemote, OutletSessionBeanRemote outletSessionBeanRemote, PartnerCustomerSessionBeanRemote partnerCustomerSessionBeanRemote) {
        this();
        this.partnerSessionBeanRemote = partnerSessionBeanRemote;
        this.rentalReservationSessionBeanRemote = rentalReservationSessionBeanRemote;
        this.carModelSessionBeanRemote = carModelSessionBeanRemote;
        this.carCategorySessionBeanRemote = carCategorySessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
        this.partnerCustomerSessionBeanRemote = partnerCustomerSessionBeanRemote;
    }

    

    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to MCR  x  Holiday Reservation System ***");
            System.out.println("1: Partner Login");
            System.out.println("2: Search Car");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    
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
    
    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** MCR  x  Holiday Reservation System :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentPartnerEntity = partnerSessionBeanRemote.partnerLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing login credentials!");
        }
    }
    
    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** MCR  x  Holiday Reservation System ***\n");
            System.out.println("Welcome to Holiday Reservation System, " + currentPartnerEntity.getPartnerName() + "!\n");
            System.out.println("1: Partner Search Car");
            System.out.println("2: Partner View Reservation Details");
            System.out.println("3: Partner Cancel Reservation");
            System.out.println("4: View All Partner Reservations");
            System.out.println("5: Logout\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doPartnerSearchCar();
                } else if (response == 2) {
                    doPartnerViewReservationDetails();
                } else if (response == 3) {
                    System.out.print("Enter Rental Reservation ID> ");
                    Long rentalReservationId = scanner.nextLong();
                    scanner.nextLine();
                    doPartnerCancelReservation(rentalReservationId);
                } else if (response == 4) {
                    doViewAllPartnerReservations();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }

    private void doPartnerSearchCar() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        Boolean availableToReserve = false;

        Long carCategoryId = Long.valueOf("0");
        Long carModelId = Long.valueOf("0");

        Long pickupOutletId;
        Long returnOutletId;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date pickupDateTime;
        Date returnDateTime;

        SimpleDateFormat openingHours = new SimpleDateFormat("HH:mm");

        System.out.println("*** MCR  x  Holiday Reservation System :: Partner Search Car ***\n");

        try {
            System.out.print("Enter desired Pickup Date and Time (DD/MM/YYYY HH:MM)> ");
            String pickupTimeString = scanner.nextLine().trim();
            pickupDateTime = sdf.parse(pickupTimeString);
            Date pickupTime = openingHours.parse(pickupTimeString.substring(11));
            System.out.print("Enter desired Return Date and Time (DD/MM/YYYY HH:MM)> ");
            String returnTimeString = scanner.nextLine().trim();
            returnDateTime = sdf.parse(returnTimeString);
            Date returnTime = openingHours.parse(returnTimeString.substring(11));

            if (pickupDateTime.getTime() - returnDateTime.getTime() >= 0) {
                throw new PickupReturnClashException("Pickup Date and Time cannot be before Return Date and Time!");
            }

            List<Outlet> outletList = outletSessionBeanRemote.retrieveAllOutlets();
            System.out.println("You are given the following available outlets. Please consider the pickup and return outlet IDs carefully...\n");
            System.out.printf("%4s%30s%30s%20s%20s\n", "ID", "Outlet Name", "Address", "Opening Hour", "Closing Hour");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            for (Outlet o : outletList) {
                String openingHour = "All-day";
                if (o.getOutletOpeningHour() != null) {
                    openingHour = openingHours.format(o.getOutletOpeningHour());
                }
                String closingHour = "-";
                if (o.getOutletClosingHour() != null) {
                    closingHour = openingHours.format(o.getOutletClosingHour());
                }
                System.out.printf("%4s%30s%30s%20s%20s\n", o.getOutletId(), o.getOutletName(), o.getOutletAddress(), openingHour, closingHour);
            }

            System.out.print("Enter desired Pickup Outlet ID> ");
            pickupOutletId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter desired Return Outlet ID> ");
            returnOutletId = scanner.nextLong();
            scanner.nextLine();

            Outlet pickupOutlet = outletSessionBeanRemote.retrieveOutletById(pickupOutletId);
            Outlet returnOutlet = outletSessionBeanRemote.retrieveOutletById(returnOutletId);
            if (pickupOutlet.getOutletOpeningHour() != null) {
                Long differenceInOpeningTime = pickupTime.getTime() - pickupOutlet.getOutletOpeningHour().getTime();
                Long differenceInOpeningHour = (differenceInOpeningTime / (60 * 60 * 1000) % 24);
                Long differenceInOpeningMinute = (differenceInOpeningTime / (1000 * 60)) % 60;
                Long differenceInClosingTime = pickupTime.getTime() - pickupOutlet.getOutletClosingHour().getTime();
                Long differenceInClosingHour = (differenceInClosingTime / (60 * 60 * 1000) % 24);
                Long differenceInClosingMinute = (differenceInClosingTime / (1000 * 60)) % 60;

                if ((differenceInOpeningHour < 0) || ((differenceInOpeningHour == 0) && (differenceInOpeningMinute < 0))) {
                    throw new PickupReturnTimeException("Sorry, reservation is not available as your chosen pickup/return time is outside the operating hours of the pickup/return outlets!");
                } else if ((differenceInClosingHour > 0) || ((differenceInClosingHour == 0) && (differenceInClosingMinute > 0))) {
                    throw new PickupReturnTimeException("Sorry, reservation is not available as your chosen pickup/return time is outside the operating hours of the pickup/return outlets!");
                }
            }
            if (returnOutlet.getOutletOpeningHour() != null) {
                Long differenceInOpeningTime = returnTime.getTime() - returnOutlet.getOutletOpeningHour().getTime();
                Long differenceInOpeningHour = (differenceInOpeningTime / (1000 * 60 * 60)) % 24;
                Long differenceInOpeningMinute = (differenceInOpeningTime / (1000 * 60)) % 60;
                Long differenceInClosingTime = returnTime.getTime() - returnOutlet.getOutletClosingHour().getTime();
                Long differenceInClosingHour = (differenceInClosingTime / (1000 * 60 * 60)) % 24;
                Long differenceInClosingMinute = (differenceInClosingTime / (1000 * 60)) % 60;

                if ((differenceInOpeningHour < 0) || ((differenceInOpeningHour == 0) && (differenceInOpeningMinute < 0))) {
                    throw new PickupReturnTimeException("Sorry, reservation is not available as your chosen pickup/return time is outside the operating hours of the pickup/return outlets!");
                } else if ((differenceInClosingHour > 0) || ((differenceInClosingHour == 0) && (differenceInClosingMinute > 0))) {
                    throw new PickupReturnTimeException("Sorry, reservation is not available as your chosen pickup/return time is outside the operating hours of the pickup/return outlets!");
                }
            }
            while (true) {
                System.out.println("Filter by Car Category and Car Model> ");
                System.out.println("1: Search by Car Category");
                System.out.println("2: Search by Car Model");
                response = 0;

                while (response < 1 || response > 2) {
                    System.out.print("> ");
                    response = scanner.nextInt();
                    if (response == 1) {
                        List<CarCategory> carCategoryList = carCategorySessionBeanRemote.retrieveAllCarCategories();
                        System.out.printf("%4s%30s\n", "ID", "Car Category");
                        System.out.println("--------------------------------------------------------------------------------");
                        for (CarCategory cc : carCategoryList) {
                            System.out.printf("%4s%30s\n", cc.getCategoryId(), cc.getCategoryName());
                        }
                        System.out.print("Enter desired Car Category ID> ");
                        carCategoryId = scanner.nextLong();
                        availableToReserve = rentalReservationSessionBeanRemote.searchCarByCarCategory(carCategoryId, pickupDateTime, pickupOutletId, returnDateTime, returnOutletId);
                        break;
                    } else if (response == 2) {
                        List<CarModel> carModelList = carModelSessionBeanRemote.retrieveAllCarModels();
                        System.out.printf("%4s%30s%30s%35s%15s\n", "ID", "Car Make", "Model Name", "Car Category", "Is Enabled");
                        System.out.println("-------------------------------------------------------------------------------------------------------------");
                        for (CarModel cm : carModelList) {
                            String isEnabled = "Enabled";
                            if (!cm.isModelIsEnabled()) {
                                isEnabled = "Disabled";
                            }
                            System.out.printf("%4s%30s%30s%35s%15s\n", cm.getModelId(), cm.getCarMake(), cm.getModelName(), cm.getCarCategory().getCategoryName(), isEnabled);
                        }
                        System.out.print("Enter desired Car Model ID> ");
                        carModelId = scanner.nextLong();
                        availableToReserve = rentalReservationSessionBeanRemote.searchCarByCarModel(carModelId, pickupDateTime, pickupOutletId, returnDateTime, returnOutletId);
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
                if (response == 1 || response == 2) {
                    break;
                }
            }
            scanner.nextLine();
            if (availableToReserve) {
                BigDecimal reservationAmount = carCategorySessionBeanRemote.calculateRentalFee(carCategoryId, pickupDateTime, returnDateTime);
                System.out.println("The expected rental fee amount is $" + reservationAmount);
                if (currentPartnerEntity == null) {
                    System.out.println("Please login to perform reservation-related transactions!");
                } else {
                    System.out.print("Proceed to reserve car? ('Y' to commence reservation process)> ");
                    String reply = scanner.nextLine().trim();
                    if (reply.equals("Y")) {
                        doPartnerReserveCar(carCategoryId, carModelId, pickupDateTime, pickupOutletId, returnDateTime, returnOutletId, reservationAmount);
                    }
                }
            } else {
                System.out.println("There are no available cars under your current search criteria!");
            }
            System.out.print("Press <Enter> key to continue> ");
            scanner.nextLine();
        } catch (ParseException ex) {
            System.out.println("Invalid date time format!");
        } catch (OutletNotFoundException ex) {
            System.out.println("Pickup and/or return outlets don't exist!");
        } catch (CarCategoryNotFoundException ex) {
            System.out.println("Car category input don't exist!");
        } catch (CarModelNotFoundException ex) {
            System.out.println("Car model input don't exist!");
        } catch (PickupReturnTimeException ex) {
            System.out.println(ex.getMessage());
        } catch (EmptyRentalRateException ex) {
            System.out.println("There are no rental rates available for the day!");
        } catch (PickupReturnClashException ex) {
            System.out.println("Pickup Date and Time cannot be before Return Date and Time!");
        }
    }

    private void doPartnerReserveCar(Long carCategoryId, Long carModelId, Date pickupDateTime, Long pickupOutletId, Date returnDateTime, Long returnOutletId, BigDecimal reservationAmount) {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** MCR  x  Holiday Reservation System :: Partner Reserve Car ***\n");

        RentalReservation newRentalReservation = new RentalReservation();

        try {
            newRentalReservation.setRentalReservationAmount(reservationAmount);
            newRentalReservation.setRentalReservationPickupDateTime(pickupDateTime);
            newRentalReservation.setRentalReservationReturnDateTime(returnDateTime);

            System.out.print("Enter Credit Card Name> ");
            String creditCardName = scanner.nextLine().trim();
            newRentalReservation.setCreditCardName(creditCardName);
            System.out.print("Enter Credit Card Number> ");
            String creditCardNum = scanner.nextLine().trim();
            newRentalReservation.setCreditCardNumber(creditCardNum);
            System.out.print("Enter Credit Card CVV> ");
            String creditCardCVV = scanner.nextLine().trim();
            newRentalReservation.setCreditCardCVV(creditCardCVV);
            System.out.print("Enter Credit Card Expiry Date> ");
            String creditCardExpiryDate = scanner.nextLine().trim();
            newRentalReservation.setCreditCardExpiry(creditCardExpiryDate);
            
            PartnerCustomer partnerCustomer = new PartnerCustomer();
            System.out.print("Enter Partner Customer Email Address> ");
            String partnerCustomerEmail = scanner.nextLine().trim();
            partnerCustomer.setPartnerCustomerEmail(partnerCustomerEmail);
            System.out.print("Enter Partner Customer First Name> ");
            String partnerCustomerFirstName = scanner.nextLine().trim();
            partnerCustomer.setPartnerCustomerFirstName(partnerCustomerFirstName);
            System.out.print("Enter Partner Customer Last Name> ");
            String partnerCustomerLastName = scanner.nextLine().trim();
            partnerCustomer.setPartnerCustomerLastName(partnerCustomerLastName);
            
            partnerCustomerSessionBeanRemote.createNewCustomer(partnerCustomer, currentPartnerEntity.getPartnerId());
            

            System.out.print("Make payment now? ('Y' for immediate payment)> ");
            String reply = scanner.nextLine().trim();
            if (reply.equals("Y")) {
                newRentalReservation.setRentalReservationIsPaid(Boolean.TRUE);
                System.out.println("Payment for Rental Reservation is successful!");
            } else {
                newRentalReservation.setRentalReservationIsPaid(Boolean.FALSE);
                System.out.println("Payment for Rental Reservation is being deferred and credit card number ending " + creditCardNum.substring(creditCardNum.length() - 4) + " has been saved successfully!");
            }
            Long newRentalReservationId = rentalReservationSessionBeanRemote.createNewRentalReservationForPartner(newRentalReservation, currentPartnerEntity.getPartnerId(), partnerCustomer.getPartnerCustomerId(),carCategoryId, carModelId, pickupOutletId, returnOutletId);
            System.out.println("New Rental Reservation created successfully!: " + newRentalReservationId);
        } catch (InputDataValidationException ex) {
            System.out.println("Input data validation failed!");
        } catch (PartnerNotFoundException ex) {
            System.out.println("Partner not found!");
        } catch (PartnerCustomerNameExistException ex) {
            System.out.println("Partner Customer name already exists!!");
        } catch (UnknownPersistenceException ex) {
            System.out.println("Unknown Persistence Exception!");
        }

        System.out.print("Press <Enter> key to continue> ");
        scanner.nextLine();
    }

    private void doPartnerCancelReservation(Long rentalReservationId) {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** MCR  x  Holiday Reservation System :: Partner Cancel Reservation ***\n");

        RentalReservation rentalReservationToCancel = new RentalReservation();

        try {
            rentalReservationToCancel = rentalReservationSessionBeanRemote.retrieveRentalReservationById(rentalReservationId);
            BigDecimal penaltyAmount = rentalReservationSessionBeanRemote.calculateCancellationCharges(rentalReservationId);

            if (rentalReservationToCancel.getRentalReservationIsPaid()) {
                System.out.println("We understand you have made payment of $" + rentalReservationToCancel.getRentalReservationAmount()
                        + " for this reservation earlier on. \n"
                        + "After deducting the cancellation penalty amount of $" + penaltyAmount
                        + ", a refund of $" + rentalReservationToCancel.getRentalReservationAmount().subtract(penaltyAmount)
                        + " will be made to your credit card ending with " + rentalReservationToCancel.getCreditCardNumber()
                                .substring(rentalReservationToCancel.getCreditCardNumber().length() - 4));
            }
            System.out.print("Press <Enter> key to continue> ");
            scanner.nextLine();
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation ID [" + rentalReservationToCancel.getRentalReservationId() + "] does not exist!");
        }
    }

    private void doPartnerViewReservationDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** MCR  x  Holiday Reservation System :: Partner View Reservation Details ***\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        RentalReservation r = new RentalReservation();

        System.out.print("Enter Rental Reservation ID> ");
        Long rentalReservationId = scanner.nextLong();
        scanner.nextLine();

        try {
            r = rentalReservationSessionBeanRemote.retrieveRentalReservationById(rentalReservationId);
            System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", "ID", "Car Make", "Model Name", "Car Category",
                    "Pickup Date", "Return Date", "Rental Fee", "Paid", "Cancelled");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
            String isPaid = "Paid";
            if (!r.getRentalReservationIsPaid()) {
                isPaid = "Unpaid";
            }
            String isCancelled = "Ongoing";
            if (r.getRentalReservationIsCancelled()) {
                isCancelled = "Cancelled";
            }
            String carMakeName = "-";
            String carModelName = "-";
            String carCategoryName = "-";
            if (r.getCarModel() != null) {
                carMakeName = r.getCarModel().getCarMake();
                carModelName = r.getCarModel().getModelName();
            }
            if (r.getCarCategory() != null) {
                carCategoryName = r.getCarCategory().getCategoryName();
            }
            String pickupDateTime = sdf.format(r.getRentalReservationPickupDateTime());
            String returnDateTime = sdf.format(r.getRentalReservationReturnDateTime());
            System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", r.getRentalReservationId(),
                    carMakeName, carModelName,
                    carCategoryName, pickupDateTime,
                    returnDateTime, r.getRentalReservationAmount(),
                    isPaid, isCancelled);
            System.out.print("Proceed to cancel reservation? ('Y' to cancel)> ");
            String reply = scanner.nextLine().trim();
            if (reply.equals("Y")) {
                doPartnerCancelReservation(rentalReservationId);
            } else {
                System.out.print("Press <Enter> key to continue> ");
                scanner.nextLine();
            }
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation ID [" + r.getRentalReservationId() + "] does not exist!");
        }
    }

    private void doViewAllPartnerReservations() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("*** MCR  x  Holiday Reservation System :: View All Partner Reservations ***\n");
        System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", "ID", "Car Make", "Model Name", "Car Category",
                "Pickup Date", "Return Date", "Rental Fee", "Paid", "Cancelled");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

        List<RentalReservation> reservationList = rentalReservationSessionBeanRemote.retrieveCustomerRentalReservations(currentPartnerEntity.getPartnerId());

        for (RentalReservation r : reservationList) {
            String isPaid = "Paid";
            if (!r.getRentalReservationIsPaid()) {
                isPaid = "Unpaid";
            }
            String isCancelled = "Ongoing";
            if (r.getRentalReservationIsCancelled()) {
                isCancelled = "Cancelled";
            }
            String carMakeName = "-";
            String carModelName = "-";
            String carCategoryName = "-";
            if (r.getCarModel() != null) {
                carMakeName = r.getCarModel().getCarMake();
                carModelName = r.getCarModel().getModelName();
            }
            if (r.getCarCategory() != null) {
                carCategoryName = r.getCarCategory().getCategoryName();
            }
            String pickupDateTime = sdf.format(r.getRentalReservationPickupDateTime());
            String returnDateTime = sdf.format(r.getRentalReservationReturnDateTime());

            System.out.printf("%4s%20s%25s%20s%20s%20s%15s%15s%15s\n", r.getRentalReservationId(),
                    carMakeName, carModelName,
                    carCategoryName, pickupDateTime,
                    returnDateTime, r.getRentalReservationAmount(),
                    isPaid, isCancelled);
        }
        System.out.print("Press <Enter> key to continue> ");
        scanner.nextLine();
    }
    
//    private static createPartnerCustomer(PartnerCustomer partnerCustomer) {
//        HolidayReservationSystemWebService_Service holidayReservationSystemWebService_Service = new HolidayReservationSystemWebService_Service();
//        
//    }
    
}
