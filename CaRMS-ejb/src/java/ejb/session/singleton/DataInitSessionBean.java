/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CarCategorySessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.CarCategory;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.CarCategoryExistException;
import util.exception.CarCategoryNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
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
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

//    @EJB
//    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    

    
    

    public DataInitSessionBean() {
        
    }

    
    @PostConstruct
    public void postConstruct() {
        try {
            employeeSessionBeanLocal.retrieveEmployeeByEmployeeId(1l);
            carCategorySessionBeanLocal.retrieveCarCategoryById(1l);
        } catch(EmployeeNotFoundException | CarCategoryNotFoundException ex) {
            loadTestData();
        }
//        try {
//            carSessionBeanLocal.retrieveCarByCarId(1l);
//        } catch(CarNotFoundException ex) {
//            loadTestData();
//        }
    }
    
    private void loadTestData() {
        
        try {
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee A1", "A1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee A2", "A2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee A3", "A3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee A4", "A4", "password", EmployeeAccessRightEnum.EMPLOYEE));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee A5", "A5", "password", EmployeeAccessRightEnum.EMPLOYEE));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee B1", "B1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee B2", "B2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee B3", "B3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C1", "C1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C2", "C2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C3", "C3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Standard Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Family Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Luxury Sedan"));
            carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("SUV and Minivan"));
        } catch (EmployeeUsernameExistException | CarCategoryExistException | UnknownPersistenceException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
//        try {
//            carSessionBeanLocal.createNewCar(new Car("Employee A1", "A1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee A2", "A2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee A3", "A3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee A4", "A4", "password", EmployeeAccessRightEnum.EMPLOYEE));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee A5", "A5", "password", EmployeeAccessRightEnum.EMPLOYEE));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee B1", "B1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
//            carSessionBeanLocal.createNewEmployee(new Employee("Employee B2", "B2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
//            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee B3", "B3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
//            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C1", "C1", "password", EmployeeAccessRightEnum.SALES_MANAGER));
//            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C2", "C2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER));
//            employeeSessionBeanLocal.createNewEmployee(new Employee("Employee C3", "C3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE));
//        } catch (EmployeeUsernameExistException | UnknownPersistenceException | InputDataValidationException ex) {
//            ex.printStackTrace();
//        }
    }
}
