/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.RentalReservation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.OutletNotFoundException;
import util.exception.RentalReservationNotFoundException;

/**
 *
 * @author Darie
 */
@Stateless
public class EjbTimerSessionBean implements EjbTimerSessionBeanRemote, EjbTimerSessionBeanLocal {

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private TransitDriverDispatchRecordSessionBeanLocal transitDriverDispatchRecordSessionBeanLocal;

    @EJB
    private RentalReservationSessionBeanLocal rentalReservationSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public EjbTimerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Schedule(hour = "2", minute = "0", second = "0", info = "autoDailyCarAllocationAtTwoAmOnly")
    public void automaticDailyCarAllocationTimer() {
        Date today = new Date();
        allocateCarsToCurrentDayReservations(today);
    }

    public void allocateCarsToCurrentDayReservations(Date specifiedDate) {

        Date startDate = specifiedDate;
        Calendar cal = Calendar.getInstance();
        startDate.setHours(2);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 1);
        Date endDate = cal.getTime();

        Query query = em.createQuery("SELECT rr FROM RentalReservation rr WHERE rr.rentalReservationPickupDateTime >= :inStartDate AND rr.rentalReservationPickupDateTime <= :inEndDate");
        query.setParameter("inStartDate", startDate);
        query.setParameter("inEndDate", endDate);

        List<RentalReservation> reservationAllocationList = query.getResultList();
        ArrayList<RentalReservation> transitDispatchRecords = new ArrayList<>();
        boolean allocated = false;

        for (RentalReservation r : reservationAllocationList) {
            if (r.getCarModel() != null) {
                List<Car> cars = carSessionBeanLocal.retrieveCarsByCarModelId(r.getCarModel().getModelId());
                for (Car c : cars) {
                    if ((c.getCarStatus().equals("Available") && c.getRentalReservation() == null)
                            && c.getOutlet().getOutletId().equals(r.getPickupOutlet().getOutletId())) {
                        c.setRentalReservation(r);
                        r.setCar(c);
                        allocated = true;
                        break;
                    }
                }
                for (Car c : cars) {
                    if ((c.getCarStatus().equals("Available") && (c.getRentalReservation() == null))) {
                        c.setRentalReservation(r);
                        r.setCar(c);
                        allocated = true;
                        transitDispatchRecords.add(r);
                        break;
                    }
                }
                for (Car c : cars) {
                    if ((c.getCarStatus().equals("Rented") && (c.getRentalReservation() == null))
                            && c.getRentalReservation().getReturnOutlet().getOutletName().equals(r.getPickupOutlet().getOutletName())) {
                        if (c.getRentalReservation().getRentalReservationReturnDateTime().before(r.getRentalReservationPickupDateTime())) {
                            r.setCar(c);
                            allocated = true;
                            break;
                        }
                    }
                }
                for (Car c : cars) {
                    if ((c.getCarStatus().equals("Rented") && (c.getRentalReservation() == null))
                            && c.getRentalReservation().getReturnOutlet().equals(r.getPickupOutlet())) {
                        Date transitDate = c.getRentalReservation().getRentalReservationReturnDateTime();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(transitDate);
                        calendar.add(Calendar.HOUR, 2);
                        Date transitEndDate = calendar.getTime();
                        if (r.getRentalReservationPickupDateTime().after(transitEndDate)) {
                            r.setCar(c);
                            allocated = true;
                            transitDispatchRecords.add(r);
                            break;
                        }
                    }
                }
            } else {
                List<Car> cars = carSessionBeanLocal.retrieveCarsByCarCategoryId(r.getCarCategory().getCategoryId());
                for (Car c : cars) {
                    if (c.getCarModel().getCarCategory().getCategoryName().equals(r.getCarCategory().getCategoryName())
                            && c.getRentalReservation() == null && c.getOutlet().getOutletId().equals(r.getPickupOutlet().getOutletId())) {
                        r.setCar(c);
                        c.setRentalReservation(r);
                        allocated = true;
                        break;
                    }
                }
                Long carCategoryId = r.getCarCategory().getCategoryId();
                List<Car> carsWithSameCategory = carSessionBeanLocal.retrieveCarsByCarCategoryId(carCategoryId);
                for (Car c : carsWithSameCategory) {
                    if (c.getCarStatus().equals("Available") && c.getRentalReservation() == null) {
                        r.setCar(c);
                        c.setRentalReservation(r);
                        allocated = true;
                        transitDispatchRecords.add(r);
                        break;
                    }
                }
                for (Car c : carsWithSameCategory) {
                    if ((c.getCarStatus().equals("Rented")) && (c.getRentalReservation().getReturnOutlet().equals(r.getPickupOutlet()))) {
                        Date transitDate = c.getRentalReservation().getRentalReservationReturnDateTime();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(transitDate);
                        calendar.add(Calendar.HOUR, 2);
                        Date transitEndDate = calendar.getTime();
                        if (r.getRentalReservationPickupDateTime().after(transitEndDate)) {
                            r.setCar(c);
                            allocated = true;
                            transitDispatchRecords.add(r);
                            break;
                        }
                    }
                }
            }
        }
        generateTransitDriverDispatchRecordsForCurrentDayReservations(specifiedDate, transitDispatchRecords);
    }
    

    public void generateTransitDriverDispatchRecordsForCurrentDayReservations(Date currentDate, ArrayList<RentalReservation> reservationAllocationList) {
        try {
            for (RentalReservation r : reservationAllocationList) {
                // Transit Driver Dispatch Records are to be created, with the start time 2 hrs before reservations as transit time                Date transitDate = currentDate;
                Date transitDate = currentDate;
                Calendar cal = Calendar.getInstance();
                cal.setTime(transitDate);
                cal.add(Calendar.HOUR, -2);
                transitDate = cal.getTime();
                transitDriverDispatchRecordSessionBeanLocal.CreateNewTransitDriverDispatchRecord(currentDate, r.getPickupOutlet().getOutletId(), r.getRentalReservationId());
            }
        } catch (OutletNotFoundException ex) {
            System.out.println("Outlet ID(s) do(es) not exist!");
        } catch (RentalReservationNotFoundException ex) {
            System.out.println("Rental Reservation(s) do(es) not exist!");
        }
    }
}
