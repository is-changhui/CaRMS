/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RentalReservation;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarModelNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.RentalReservationNotFoundException;

/**
 *
 * @author Darie
 */
@Local
public interface RentalReservationSessionBeanLocal {

    public Long createNewRentalReservation(RentalReservation newRentalReservation, Long customerId, Long carCategoryId, Long carModelId, Long pickupOutletId, Long returnOutletId) throws InputDataValidationException;

    public RentalReservation retrieveRentalReservationById(Long rentalReservationId) throws RentalReservationNotFoundException;

    public List<RentalReservation> retrieveAllRentalReservations();

    public List<RentalReservation> retrieveRentalReservationsByPickupOutlet(Long pickupOutletId);

    public List<RentalReservation> retrieveRentalReservationsByReturnOutlet(Long returnOutletId);

    public Long createNewRentalReservationForPartner(RentalReservation newRentalReservation, Long partnerId, Long partnerCustomerId, Long carCategoryId, Long carModelId, Long pickupOutletId, Long returnOutletId) throws InputDataValidationException;

    public List<RentalReservation> retrieveAllRentalReservationsForPartner(Long partnerId);

    public List<RentalReservation> retrieveCustomerRentalReservations(Long customerId);

    public BigDecimal calculateCancellationCharges(Long rentalReservationId);

    public void initiatePickupCar(Long rentalReservationId);

    public void initiateReturnCar(Long rentalReservationId);

    public Boolean searchCarByCarCategory(Long carCategoryId, Date pickupDateTime, Long pickupOutletId, Date returnDateTime, Long returnOutletId) throws CarCategoryNotFoundException;

    public Boolean searchCarByCarModel(Long carModelId, Date pickupDateTime, Long pickupOutletId, Date returnDateTime, Long returnOutletId) throws CarModelNotFoundException;
    
}
