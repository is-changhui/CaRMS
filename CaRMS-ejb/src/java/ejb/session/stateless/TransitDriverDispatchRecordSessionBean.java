/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import entity.Outlet;
import entity.RentalReservation;
import entity.TransitDriverDispatchRecord;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.EmployeeNotFoundException;
import util.exception.OutletNotFoundException;
import util.exception.RentalReservationNotFoundException;
import util.exception.TransitDriverDispatchRecordNotFoundException;
import util.exception.TransitDriverOutletMismatchException;

/**
 *
 * @author Darie
 */
@Stateless
public class TransitDriverDispatchRecordSessionBean implements TransitDriverDispatchRecordSessionBeanRemote, TransitDriverDispatchRecordSessionBeanLocal {

    @EJB(name = "OutletSessionBeanLocal")
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @EJB(name = "RentalReservationSessionBeanLocal")
    private RentalReservationSessionBeanLocal rentalReservationSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    
    public TransitDriverDispatchRecordSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long CreateNewTransitDriverDispatchRecord(Date dispatchDate, Long destinationOutletId, Long rentalReservationId) throws OutletNotFoundException, RentalReservationNotFoundException {
        try {
            RentalReservation rentalReservation = rentalReservationSessionBeanLocal.retrieveRentalReservationById(rentalReservationId);
            Outlet destinationOutlet = outletSessionBeanLocal.retrieveOutletById(destinationOutletId);
            TransitDriverDispatchRecord transitDriverDispatchRecord = new TransitDriverDispatchRecord();
            transitDriverDispatchRecord.setDispatchDate(dispatchDate);

            transitDriverDispatchRecord.setDestinationOutlet(destinationOutlet);
            destinationOutlet.getTransitDriverDispatchRecords().add(transitDriverDispatchRecord);

            transitDriverDispatchRecord.setRentalReservation(rentalReservation);
            rentalReservation.setTransitDriverDispatchRecord(transitDriverDispatchRecord);

            em.persist(transitDriverDispatchRecord);
            em.flush();
            return transitDriverDispatchRecord.getDispatchRecordId();
        } catch (RentalReservationNotFoundException ex) {
            throw new RentalReservationNotFoundException("Rental Reservation ID [" + rentalReservationId + "] does not exist!");
        } catch (OutletNotFoundException ex) {
            throw new OutletNotFoundException("Destination Outlet ID [" + destinationOutletId + "] does not exist!");
        }
    }

    @Override
    public TransitDriverDispatchRecord retrieveTransitDriverDispatchRecordById(Long transitDriverDispatchRecordId) throws TransitDriverDispatchRecordNotFoundException {
        TransitDriverDispatchRecord transitDriverDispatchRecordEntity = em.find(TransitDriverDispatchRecord.class, transitDriverDispatchRecordId);

        if (transitDriverDispatchRecordEntity != null) {
            return transitDriverDispatchRecordEntity;
        } else {
            throw new TransitDriverDispatchRecordNotFoundException("TransitDriverDispatchRecord ID {" + transitDriverDispatchRecordId + "] does not exist!");
        }
    }

    // MCUC23
    @Override
    public List<TransitDriverDispatchRecord> retrieveAllTransitDriverDispatchRecordsForOutlet(Long outletId, Date transitDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(transitDate);
        cal.add(Calendar.DATE, 1);
        Date dayAfterTransitDate = cal.getTime();
        Query query = em.createQuery("SELECT tDDR FROM TransitDriverDispatchRecord tDDR WHERE tDDR.destinationOutlet.outletId = :inOutletId AND tDDR.dispatchDate >= :inToday AND tDDR.dispatchDate <= :inDayAfter");
        query.setParameter("inOutletId", outletId);
        query.setParameter("inToday", transitDate);
        query.setParameter("inDayAfter", dayAfterTransitDate);
        return query.getResultList();
    }

    // MCUC24
    @Override
    public void assignTransitDriver(Long transitDriverDispatchRecordId, Long transitDriverId) {
        try {
            TransitDriverDispatchRecord transitDriverDispatchRecord = retrieveTransitDriverDispatchRecordById(transitDriverId);
            Employee transitDriver = employeeSessionBeanLocal.retrieveEmployeeByEmployeeId(transitDriverId);

            if (transitDriverDispatchRecord.getDestinationOutlet().getOutletName().equals(transitDriver.getOutlet().getOutletName())) {
                transitDriverDispatchRecord.setTransitDriver(transitDriver);
                transitDriver.getTransitDriverDispatchRecords().add(transitDriverDispatchRecord);
            } else {
                throw new TransitDriverOutletMismatchException("Transit Driver and outlet mismatch, cannot assign transit driver!");
            }
        } catch (TransitDriverDispatchRecordNotFoundException ex) {
            System.out.println("Transit Driver Dispatch Record ID [" + transitDriverDispatchRecordId + "] does not exist!");
        } catch (EmployeeNotFoundException ex) {
            System.out.println("Employee ID [" + transitDriverId + "] does not exist!");
        } catch (TransitDriverOutletMismatchException ex) {
            System.out.println("Transit Driver and outlet mismatch, cannot assign transit driver!");
        }
    }

    // MCUC25
    @Override
    public void updateTransitAsCompleted(Long transitDriverDispatchRecordId) {
        try {
            TransitDriverDispatchRecord transitDriverDispatchRecord = retrieveTransitDriverDispatchRecordById(transitDriverDispatchRecordId);
            transitDriverDispatchRecord.setTransitIsComplete(Boolean.TRUE);
        } catch (TransitDriverDispatchRecordNotFoundException ex) {
            System.out.println("Transit Driver Dispatch Record ID [" + transitDriverDispatchRecordId + "] does not exist!");
        }
    }
}
