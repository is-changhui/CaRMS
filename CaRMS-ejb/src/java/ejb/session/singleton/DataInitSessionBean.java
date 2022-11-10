/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CarCategorySessionBeanLocal;
import ejb.session.stateless.CarModelSessionBeanLocal;
import ejb.session.stateless.CarSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.RentalRateSessionBeanLocal;
import entity.CarCategory;
import entity.Employee;
import entity.Outlet;
import entity.Partner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.CarCategoryExistException;
import util.exception.CarCategoryNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.OutletExistException;
import util.exception.OutletNotFoundException;
import util.exception.PartnerExistException;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private RentalRateSessionBeanLocal rentalRateSessionBeanLocal;

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private CarModelSessionBeanLocal carModelSessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    
    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;


    public DataInitSessionBean() {
        
    }

    
    @PostConstruct
    public void postConstruct() {
        try {
            outletSessionBeanLocal.retrieveOutletById(1l);
            employeeSessionBeanLocal.retrieveEmployeeByEmployeeId(1l);
            carCategorySessionBeanLocal.retrieveCarCategoryById(1l);
            partnerSessionBeanLocal.retrievePartnerById(1l);
        } catch(EmployeeNotFoundException | CarCategoryNotFoundException | OutletNotFoundException | PartnerNotFoundException ex) {
            loadTestData();
        }
    }
    
    private void loadTestData() {
        
        try {
            Long aId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet A", "A", null, null));
            Long bId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet B", "B", null, null));
            Long cId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet C", "C", "10:00", "22:00"));
         
            Employee a1 = new Employee("Employee A1", "A1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a1, aId);
            Employee a2 = new Employee("Employee A2", "A2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a2, aId);
            Employee a3 = new Employee("Employee A3", "A3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a3, aId);
            Employee a4 = new Employee("Employee A4", "A4", "password", EmployeeAccessRightEnum.EMPLOYEE);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a4, aId);
            Employee a5 = new Employee("Employee A5", "A5", "password", EmployeeAccessRightEnum.EMPLOYEE);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a5, aId);
            
            Employee b1 = new Employee("Employee B1", "B1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b1, bId);
            Employee b2 = new Employee("Employee B2", "B2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b2, bId);
            Employee b3 = new Employee("Employee B3", "B3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b3, bId);
            
            Employee c1 = new Employee("Employee C1", "C1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c1, cId);
            Employee c2 = new Employee("Employee C2", "C2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c1, cId);
            Employee c3 = new Employee("Employee C3", "C3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c1, cId);
            
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Standard Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Family Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Luxury Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("SUV and Minivan"));
            
            partnerSessionBeanLocal.createNewPartner(new Partner("Holiday.com", "holidaymanager", "password" ));
            
        } catch (OutletExistException | OutletNotFoundException | EmployeeUsernameExistException | PartnerExistException | CarCategoryExistException | UnknownPersistenceException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
    }
}
