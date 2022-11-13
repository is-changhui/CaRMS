/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RentalReservation;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Darie
 */
@Local
public interface EjbTimerSessionBeanLocal {

    public void automaticDailyCarAllocationTimer();

    public void allocateCarsToCurrentDayReservations(Date specifiedDate);

    public void generateTransitDriverDispatchRecordsForCurrentDayReservations(Date currentDate, ArrayList<RentalReservation> reservationAllocationList);
    
}
