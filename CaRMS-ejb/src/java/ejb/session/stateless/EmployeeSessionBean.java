/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import entity.Outlet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author Darie
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    
    
    public EmployeeSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    
    @Override
    public Long createNewEmployee(Employee newEmployee) throws EmployeeUsernameExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Employee>>constraintViolations = validator.validate(newEmployee);
        
        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newEmployee);
                em.flush();
                return newEmployee.getEmployeeId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new EmployeeUsernameExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    // MCUC2: For use in DataInitSessionBean
    // When employee created, has to be associated with outlet already and need to persist to em in one go, so logic should be here and DataInit just call this method
    @Override
    public Long createNewEmployeeJoinOutlet(Employee newEmployee, Long outletId) throws EmployeeUsernameExistException, UnknownPersistenceException, InputDataValidationException, OutletNotFoundException {
        try {
            Set<ConstraintViolation<Employee>>constraintViolations = validator.validate(newEmployee);
            
            if (constraintViolations.isEmpty()) {
                try {
                    Outlet outlet = outletSessionBeanLocal.retrieveOutletById(outletId);
                    // outlet (1) ---- employee (1..*)
                    outlet.getEmployees().add(newEmployee);
                    newEmployee.setOutlet(outlet);
                    em.persist(newEmployee);
                    em.flush();
                    return newEmployee.getEmployeeId();
                } catch (PersistenceException ex) {
                    if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                        if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                            throw new EmployeeUsernameExistException();
                        } else {
                            throw new UnknownPersistenceException(ex.getMessage());
                        }
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (OutletNotFoundException ex) {
            throw new OutletNotFoundException("Outlet ID " + outletId + " does not exist!");
        }   
    }
    

    @Override
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }
    
    
    
    @Override
    public Employee retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException {
        Employee employeeEntity = em.find(Employee.class, employeeId);
        
        if (employeeEntity != null) {
            return employeeEntity;
        } else {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist!");
        }
    }
    
    
    
    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeUsername = :inEmployeeUsername");
        query.setParameter("inEmployeeUsername", username);
        
        try {
            return (Employee)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }
    
    
    
    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Employee employeeEntity = retrieveEmployeeByUsername(username);
            
            if (employeeEntity.getEmployeePassword().equals(password)) {              
                return employeeEntity;
            } else {
                throw new InvalidLoginCredentialException("Invalid username and/or password!");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid username and/or password!");
        }
    }
    
    
    
    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException, UpdateEmployeeException, InputDataValidationException
    {
        if(employee != null && employee.getEmployeeId() != null)
        {
            Set<ConstraintViolation<Employee>>constraintViolations = validator.validate(employee);
        
            if(constraintViolations.isEmpty())
            {
                Employee employeeEntityToUpdate = retrieveEmployeeByEmployeeId(employee.getEmployeeId());

                if(employeeEntityToUpdate.getEmployeeUsername().equals(employee.getEmployeeUsername()))
                {
                    employeeEntityToUpdate.setEmployeeName(employee.getEmployeeName());
                    employeeEntityToUpdate.setEmployeeAccessRight(employee.getEmployeeAccessRight());
                }
                else
                {
                    throw new UpdateEmployeeException("Username of employee record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new EmployeeNotFoundException("Employee ID not provided for employee to be updated");
        }
    }

    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Employee>>constraintViolations) {
        String msg = "Input data validation error!:";
            
        for (ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
