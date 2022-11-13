/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsmanagementclient;

import ejb.session.stateless.CarCategorySessionBeanRemote;
import ejb.session.stateless.CarModelSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.EjbTimerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import ejb.session.stateless.RentalReservationSessionBeanRemote;
import ejb.session.stateless.TransitDriverDispatchRecordSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author Darie
 */
public class Main {

    @EJB
    private static TransitDriverDispatchRecordSessionBeanRemote transitDriverDispatchRecordSessionBeanRemote;

    @EJB
    private static EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote;

    @EJB
    private static RentalReservationSessionBeanRemote rentalReservationSessionBeanRemote;

    @EJB
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;

    @EJB
    private static OutletSessionBeanRemote outletSessionBeanRemote;

    @EJB
    private static CarSessionBeanRemote carSessionBeanRemote;

    @EJB
    private static CarModelSessionBeanRemote carModelSessionBeanRemote;

    @EJB
    private static CarCategorySessionBeanRemote carCategorySessionBeanRemote;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(transitDriverDispatchRecordSessionBeanRemote, ejbTimerSessionBeanRemote, rentalReservationSessionBeanRemote, rentalRateSessionBeanRemote, outletSessionBeanRemote, carSessionBeanRemote, carModelSessionBeanRemote, carCategorySessionBeanRemote, employeeSessionBeanRemote);
        mainApp.runApp();
        
    }
    
}
