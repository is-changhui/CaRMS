/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.CarCategory;
import entity.CarModel;
import entity.Customer;
import entity.Outlet;
import entity.Partner;
import entity.PartnerCustomer;
import entity.RentalReservation;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarModelNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OutletNotFoundException;
import util.exception.PartnerCustomerNotFoundException;
import util.exception.PartnerNotFoundException;
import util.exception.RentalReservationNotFoundException;

/**
 *
 * @author Darie
 */
@Stateless
public class RentalReservationSessionBean implements RentalReservationSessionBeanRemote, RentalReservationSessionBeanLocal {

    @EJB
    private PartnerCustomerSessionBeanLocal partnerCustomerSessionBeanLocal;

    @EJB
    private CarModelSessionBeanLocal carModelSessionBeanLocal;

    @EJB
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public RentalReservationSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Long createNewRentalReservation(RentalReservation newRentalReservation, Long customerId, Long carCategoryId, Long carModelId, Long pickupOutletId, Long returnOutletId) throws InputDataValidationException {
        try {
            Set<ConstraintViolation<RentalReservation>> constraintViolations = validator.validate(newRentalReservation);
            if (constraintViolations.isEmpty()) {
                Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
                
                Outlet pickupOutlet = outletSessionBeanLocal.retrieveOutletById(pickupOutletId);
                newRentalReservation.setPickupOutlet(pickupOutlet);
                Outlet returnOutlet = outletSessionBeanLocal.retrieveOutletById(returnOutletId);
                newRentalReservation.setReturnOutlet(returnOutlet);
                
                CarCategory carCategory = new CarCategory();
                CarModel carModel = new CarModel();
                // There is a choice for customer to select based on car category or car model
                if (carModelId <= 0) {
                    carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(carCategoryId);
                    newRentalReservation.setCarCategory(carCategory);
                } else {
                    carModel = carModelSessionBeanLocal.retrieveCarModelById(carModelId);
                    carCategory = carModel.getCarCategory();
                    newRentalReservation.setCarCategory(carCategory);
                    newRentalReservation.setCarModel(carModel);
                }
                newRentalReservation.setCustomer(customer);
                customer.getRentalReservations().add(newRentalReservation);
                
                em.persist(newRentalReservation);
                em.flush();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer ID [" + customerId + "] does not exist!");
        } catch (OutletNotFoundException ex) {
            System.out.println("One or both Outlet ID(s) [" + pickupOutletId + ", " + returnOutletId + "] do(es) not exist!");
        } catch (CarCategoryNotFoundException ex) {
            System.out.println("Car Category ID [" + carCategoryId + "] does not exist!");
        } catch (CarModelNotFoundException ex) {
            System.out.println("CarModel ID [" + carModelId + "] does not exist!");
        }
        return newRentalReservation.getRentalReservationId();
    }
  
    @Override
    public RentalReservation retrieveRentalReservationById(Long rentalReservationId) throws RentalReservationNotFoundException {

        RentalReservation rentalReservation = em.find(RentalReservation.class, rentalReservationId);

        if (rentalReservationId != null) {
            return rentalReservation;
        } else {
            throw new RentalReservationNotFoundException("Rental Reservation ID [" + rentalReservationId + "] does not exist!");
        }
    }
    
    @Override
    public List<RentalReservation> retrieveAllRentalReservations() {
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr");
        return query.getResultList();
    }
    
    @Override
    public List<RentalReservation> retrieveRentalReservationsByPickupOutlet(Long pickupOutletId) {
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.pickupOutlet.outletId = :inOutletId AND rr.rentalReservationHasPickedUp = FALSE AND rr.car IS NOT NULL");
        query.setParameter("inOutletId", pickupOutletId);
        return query.getResultList();
    }
    
    @Override
    public List<RentalReservation> retrieveRentalReservationsByReturnOutlet(Long returnOutletId) {
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.returnOutlet.outletId = :inOutletId AND rr.rentalReservationHasPickedUp = TRUE AND rr.car IS NOT NULL AND rr.rentalReservationIsCompleted = FALSE");
        query.setParameter("inOutletId", returnOutletId);
        return query.getResultList();
    }
    
    @Override
    public Long createNewRentalReservationForPartner(RentalReservation newRentalReservation, Long partnerId, Long partnerCustomerId, Long carCategoryId, Long carModelId, Long pickupOutletId, Long returnOutletId) throws InputDataValidationException {
        try {
            Set<ConstraintViolation<RentalReservation>> constraintViolations = validator.validate(newRentalReservation);
            if (constraintViolations.isEmpty()) {
                Partner partner = partnerSessionBeanLocal.retrievePartnerById(partnerId);
                newRentalReservation.setPartner(partner);
                PartnerCustomer partnerCustomer = partnerCustomerSessionBeanLocal.retrievePartnerCustomerById(partnerCustomerId);
                newRentalReservation.setPartnerCustomer(partnerCustomer);
                Outlet pickupOutlet = outletSessionBeanLocal.retrieveOutletById(pickupOutletId);
                newRentalReservation.setPickupOutlet(pickupOutlet);
                Outlet returnOutlet = outletSessionBeanLocal.retrieveOutletById(returnOutletId);
                newRentalReservation.setReturnOutlet(returnOutlet);
                
                partnerCustomer.getRentalReservations().add(newRentalReservation);
                
                CarCategory carCategory = new CarCategory();
                CarModel carModel = new CarModel();
                // There is a choice for customer to select based on car category or car model
                if (carModelId <= 0) {
                    carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(carCategoryId);
                    newRentalReservation.setCarCategory(carCategory);
                } else {
                    carModel = carModelSessionBeanLocal.retrieveCarModelById(carModelId);
//                    carCategory = carModel.getCarCategory();
//                    newRentalReservation.setCarCategory(carCategory);
                    newRentalReservation.setCarModel(carModel);
                }
                em.persist(newRentalReservation);
                em.flush();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PartnerCustomerNotFoundException ex) {
            System.out.println("Partner Customer ID " + partnerCustomerId + " does not exist!");
        } catch (OutletNotFoundException ex) {
            System.out.println("One or both Outlet ID(s) [" + pickupOutletId + ", " + returnOutletId + "] do(es) not exist!");
        } catch (CarCategoryNotFoundException ex) {
            System.out.println("Car Category ID [" + carCategoryId + "] does not exist!");
        } catch (CarModelNotFoundException ex) {
            System.out.println("CarModel ID [" + carModelId + "] does not exist!");
        } catch (PartnerNotFoundException ex) {
            System.out.println("Partner ID [" + partnerId + "] does not exist!");
        }
        return newRentalReservation.getRentalReservationId();
    }
    
    @Override
    public List<RentalReservation> retrieveAllRentalReservationsForPartner(Long partnerId) {
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.partner.partnerId = :inPartnerId");
        query.setParameter("inPartnerId", partnerId);
        return query.getResultList();
    }
    
    // For Merlion's customers to view their rental reservations
    // For Merlion partners' customers, although reservations are tied to partner customers too, retrieveAllRentalReservationsForPartner is sufficient
    @Override
    public List<RentalReservation> retrieveCustomerRentalReservations(Long customerId) {
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.customer.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();
    }

    @Override
    public BigDecimal calculateCancellationCharges(Long rentalReservationId) {
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        try {
            RentalReservation rentalReservationToCancel = retrieveRentalReservationById(rentalReservationId);
            rentalReservationToCancel.setRentalReservationIsCancelled(Boolean.TRUE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date rentalReservationStartDate = rentalReservationToCancel.getRentalReservationPickupDateTime();
            Date currentDate = new Date();
            Long differenceInDays = (rentalReservationStartDate.getTime() - currentDate.getTime()) / (24*60*60*1000);
            BigDecimal rentalReservationAmount = rentalReservationToCancel.getRentalReservationAmount();

            // PP Narrative pg 2: Penalty rates differ by duration away from reservation date
            // In best case scenario, the cancellation is at least 14 days, so the declared amount above is the correct penalty rate
            
            // Less than 14 days but at least 7 days before pickup - 20% penalty
            if ((differenceInDays < 14) && (differenceInDays >= 7)) {
                penaltyAmount = rentalReservationAmount.multiply(BigDecimal.valueOf(0.2));
            }
            // Less than 7 days but at least 3 days before pickup - 50% penalty
            if ((differenceInDays < 7) && (differenceInDays >= 3)) {
                penaltyAmount = rentalReservationAmount.multiply(BigDecimal.valueOf(0.5));
            }
            // Less than 3 days before pickup - 70% penalty
            if (differenceInDays < 3) {
                penaltyAmount = rentalReservationAmount.multiply(BigDecimal.valueOf(0.7));
            }
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation ID [" + rentalReservationId + "] does not exist!");
        }
        return penaltyAmount;
    }
    
    @Override
    public void initiatePickupCar(Long rentalReservationId) {
        try {
            RentalReservation rentalReservation = retrieveRentalReservationById(rentalReservationId);
            Car reservationCar = rentalReservation.getCar();
            
            reservationCar.setRentalReservation(rentalReservation);
            reservationCar.setOutlet(null);
            reservationCar.setCarStatus("Rented");
            
            rentalReservation.getPickupOutlet().getCars().remove(reservationCar);
            rentalReservation.setRentalReservationIsPaid(Boolean.TRUE);
            rentalReservation.setRentalReservationHasPickedUp(Boolean.TRUE);
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation ID [" + rentalReservationId + "] does not exist!");
        }
    }
    
    @Override
    public void initiateReturnCar(Long rentalReservationId) {
        try {
            RentalReservation rentalReservation = retrieveRentalReservationById(rentalReservationId);
            Outlet returnOutlet = rentalReservation.getReturnOutlet();
            Car reservationCar = rentalReservation.getCar();
            
            returnOutlet.getCars().add(reservationCar);
            reservationCar.setRentalReservation(null);
            reservationCar.setOutlet(returnOutlet);
            reservationCar.setCarStatus("Available");
            
            rentalReservation.getPickupOutlet().getCars().remove(reservationCar);
            rentalReservation.setRentalReservationIsCompleted(Boolean.TRUE);
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation ID [" + rentalReservationId + "] does not exist!");
        }
    }
    
    // RCUC 3.1: search category according to pickup date/time, pickup outlet, return date/time and return outlet
    @Override
    public Boolean searchCarByCarCategory(Long carCategoryId, Date pickupDateTime, Long pickupOutletId, Date returnDateTime, Long returnOutletId) throws CarCategoryNotFoundException {
        List<RentalReservation> rentalReservationList = new ArrayList<RentalReservation>();
        
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carCategory.categoryId = :inCategoryId AND rr.rentalReservationPickupDateTime <= :inPickupDateTime AND rr.rentalReservationReturnDateTime <= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inCategoryId", carCategoryId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carCategory.categoryId = :inCategoryId AND rr.rentalReservationPickupDateTime >= :inPickupDateTime AND rr.rentalReservationReturnDateTime <= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inCategoryId", carCategoryId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carCategory.categoryId = :inCategoryId AND rr.rentalReservationPickupDateTime >= :inPickupDateTime AND rr.rentalReservationReturnDateTime > :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inCategoryId", carCategoryId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carCategory.categoryId = :inCategoryId AND rr.rentalReservationPickupDateTime <= :inPickupDateTime AND rr.rentalReservationReturnDateTime >= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inCategoryId", carCategoryId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        rentalReservationList.addAll(query.getResultList());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickupDateTime);
        cal.add(Calendar.HOUR,-2);
        
        Date transitDate = cal.getTime();
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carCategory.categoryId = :inCategoryId AND rr.rentalReservationPickupDateTime < :inPickupDateTime AND rr.rentalReservationReturnDateTime > :inTransitDateTime AND rr.returnOutlet.outletId = :inPickupOutletId AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inCategoryId", carCategoryId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inTransitDateTime", transitDate);
        query.setParameter("inPickupOutletId", pickupOutletId);
        rentalReservationList.addAll(query.getResultList());
        
        CarCategory carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(carCategoryId);
        List<Car> carList = new ArrayList<Car>();
        
        for (CarModel m : carCategory.getCarModels()) {
            carList.addAll(m.getCars());
        }
        System.out.println("There are " + carList.size() + " cars");
        System.out.println("There are " + rentalReservationList.size() + " rental reservations");
        
        return (carList.size()) > (rentalReservationList.size());
    }
    
    @Override
    public Boolean searchCarByCarModel(Long carModelId, Date pickupDateTime, Long pickupOutletId, Date returnDateTime, Long returnOutletId) throws CarModelNotFoundException {
        List<RentalReservation> rentalReservationList = new ArrayList<RentalReservation>();
        
        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carModel.modelId = :inModelId AND rr.rentalReservationPickupDateTime <= :inPickupDateTime AND rr.rentalReservationReturnDateTime <= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inModelId", carModelId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        System.out.println(query.getResultList());
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carModel.modelId = :inModelId AND rr.rentalReservationPickupDateTime >= :inPickupDateTime AND rr.rentalReservationReturnDateTime <= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inModelId", carModelId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        System.out.println(query.getResultList());
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carModel.modelId = :inModelId AND rr.rentalReservationPickupDateTime >= :inPickupDateTime AND rr.rentalReservationReturnDateTime > :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inModelId", carModelId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        System.out.println(query.getResultList());
        rentalReservationList.addAll(query.getResultList());
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carModel.modelId = :inModelId AND rr.rentalReservationPickupDateTime <= :inPickupDateTime AND rr.rentalReservationReturnDateTime >= :inReturnDateTime AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inModelId", carModelId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inReturnDateTime", returnDateTime);
        System.out.println(query.getResultList());
        rentalReservationList.addAll(query.getResultList());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickupDateTime);
        cal.add(Calendar.HOUR,-2);
        
        Date transitDate = cal.getTime();
        
        query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.carModel.modelId = :inModelId AND rr.rentalReservationPickupDateTime < :inPickupDateTime AND rr.rentalReservationReturnDateTime > :inTransitDateTime AND rr.returnOutlet.outletId = :inPickupOutletId AND rr.rentalReservationIsCancelled = FALSE");
        query.setParameter("inModelId", carModelId);
        query.setParameter("inPickupDateTime", pickupDateTime);
        query.setParameter("inTransitDateTime", transitDate);
        query.setParameter("inPickupOutletId", pickupOutletId);
        System.out.println(query.getResultList());
        rentalReservationList.addAll(query.getResultList());
        
        CarModel carModel = carModelSessionBeanLocal.retrieveCarModelById(carModelId);
        
        return (carModel.getCars().size()) > rentalReservationList.size();
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RentalReservation>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

}
