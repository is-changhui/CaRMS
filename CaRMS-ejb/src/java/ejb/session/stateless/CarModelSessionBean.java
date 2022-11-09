/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarModel;
import java.util.List;
import java.util.Set;
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
import util.exception.CarModelExistException;
import util.exception.CarModelNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class CarModelSessionBean implements CarModelSessionBeanRemote, CarModelSessionBeanLocal {

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public CarModelSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewCarModel(CarModel newCarModel) throws CarModelExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<CarModel>> constraintViolations = validator.validate(newCarModel);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCarModel);
                return newCarModel.getModelId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CarModelExistException();
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

    @Override
    public List<CarModel> retrieveAllCarModels() {
        Query query = em.createQuery("SELECT e FROM CarModel e");
        return query.getResultList();
    }

    @Override
    public CarModel retrieveCarModelByCarModelId(Long carModelId) throws CarModelNotFoundException {
        CarModel carModelFinding = em.find(CarModel.class, carModelId);

        if (carModelFinding != null) {
            return carModelFinding;
        } else {
            throw new CarModelNotFoundException("CarModel ID " + carModelId + " does not exist!");
        }
    }

//    @Override
//    public Car retrieveCarByModel(String model) throws CarNotFoundException {
//        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeUsername = :inEmployeeUsername");
//        query.setParameter("inEmployeeUsername", username);
//        
//        try {
//            return (Employee)query.getSingleResult();
//        } catch (NoResultException | NonUniqueResultException ex) {
//            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
//        }
//    }
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CarModel>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
//    @Override
//    public Long createNewCar(Car newCar) throws EmployeeUsernameExistException, UnknownPersistenceException, InputDataValidationException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    
}
