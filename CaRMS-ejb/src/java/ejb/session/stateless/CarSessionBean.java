/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.CarModel;
import entity.Outlet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarExistException;
import util.exception.CarModelExistException;
import util.exception.CarModelNotEnabledException;
import util.exception.CarModelNotFoundException;
import util.exception.CarNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class CarSessionBean implements CarSessionBeanRemote, CarSessionBeanLocal {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB
    private CarModelSessionBeanLocal carModelSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public CarSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // MCUC 18
    @Override
    public Long createNewCar(Car newCar) throws CarExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(newCar);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCar);
                em.flush();
                return newCar.getCarId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CarExistException();
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

    // For use in DataInitSessionBean
    @Override
    public Long createNewCarJoinCarModelJoinOutlet(Car newCar, Long carModelId, Long outletId) throws CarExistException, UnknownPersistenceException, InputDataValidationException, CarModelNotFoundException, CarModelNotEnabledException, OutletNotFoundException {
        try {
            Set<ConstraintViolation<Car>> constraintViolations = validator.validate(newCar);

            if (constraintViolations.isEmpty()) {
                try {
                    CarModel carModel = carModelSessionBeanLocal.retrieveCarModelByCarModelId(carModelId);
                    Outlet outlet = outletSessionBeanLocal.retrieveOutletById(outletId);
                    // carModel (1) ------ car (1..*)
                    // outlet (0..1) ------ car (0..*)
                    // 1 more layer of check here due to MCUC 17: if carModel is disabled, new car record should not be created, so we have to check this here
                    if (carModel.isModelIsEnabled()) {
                        newCar.setCarModel(carModel);
                        carModel.getCars().add(newCar);
                        newCar.setOutlet(outlet);
                        outlet.getCars().add(newCar);
                        em.persist(newCar);
                        em.flush();
                        return newCar.getCarId();
                    } else {
                        throw new CarModelNotEnabledException("Car (make and) model ID [" + carModelId + "] is not enabled, hence a car record cannot be created!");
                    }

                } catch (PersistenceException ex) {
                    if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                        if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                            throw new CarExistException();
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
        } catch (CarModelNotFoundException ex) {
            throw new CarModelNotFoundException("Car Model ID [" + carModelId + "] does not exist!");
        } catch (OutletNotFoundException ex) {
            throw new OutletNotFoundException("Outlet ID [" + outletId + "] does not exist!");
        }
    }

    @Override
    public List<Car> retrieveAllCars() {
        Query query = em.createQuery("SELECT e FROM Car e");
        return query.getResultList();
    }

    @Override
    public Car retrieveCarByCarId(Long carId) throws CarNotFoundException {
        Car carFinding = em.find(Car.class, carId);

        if (carFinding != null) {
            return carFinding;
        } else {
            throw new CarNotFoundException("Car ID [" + carId + "] does not exist!");
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
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Car>> constraintViolations) {
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
