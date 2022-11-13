/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TransitDriverDispatchRecord;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.OutletNotFoundException;
import util.exception.RentalReservationNotFoundException;
import util.exception.TransitDriverDispatchRecordNotFoundException;

/**
 *
 * @author Darie
 */
@Local
public interface TransitDriverDispatchRecordSessionBeanLocal {

    public Long CreateNewTransitDriverDispatchRecord(Date dispatchDate, Long destinationOutletId, Long rentalReservationId) throws OutletNotFoundException, RentalReservationNotFoundException;

    public TransitDriverDispatchRecord retrieveTransitDriverDispatchRecordById(Long transitDriverDispatchRecordId) throws TransitDriverDispatchRecordNotFoundException;

    public List<TransitDriverDispatchRecord> retrieveAllTransitDriverDispatchRecordsForOutlet(Long outletId, Date transitDate);

    public void assignTransitDriver(Long transitDriverDispatchRecordId, Long transitDriverId);

    public void updateTransitAsCompleted(Long transitDriverDispatchRecordId);
    
}
