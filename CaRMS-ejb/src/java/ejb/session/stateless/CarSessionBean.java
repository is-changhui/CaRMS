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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import util.exception.CarExistException;
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
                    CarModel carModel = carModelSessionBeanLocal.retrieveCarModelById(carModelId);
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
        // MCUC19.2: ascending order by car category, make, model, license plate
        Query query = em.createQuery("SELECT c FROM Car c ORDER BY c.carModel.carCategory.categoryName, c.carModel.carMake, c.carModel.modelName, c.carLicensePlate");
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
    
    @Override
    public Car retrieveCarByLicensePlate(String licensePlate) throws CarNotFoundException {
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.carLicensePlate = :inLicensePlate");
        query.setParameter("inLicensePlate", licensePlate);

        try {
            return (Car) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CarNotFoundException("Car license plate number [" + licensePlate + "] does not exist!");
        }
    }
    
    @Override
    public List<Car> retrieveCarsByCarCategoryId(Long carCategoryId) {
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.carModel.carCategory.categoryId = :inCarCategoryId");
        query.setParameter("inCarCategoryId", carCategoryId);

        return query.getResultList();
    }
    
    
    @Override
    public List<Car> retrieveCarsByCarModelId(Long modelId) {
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.carModel.modelId = :inCarModelId");
        query.setParameter("inCarModelId", modelId);

        return query.getResultList();
    }

    @Override
    public void updateCar(Car car) throws InputDataValidationException, CarNotFoundException {
        if (car != null && car.getCarId() != null) {

            Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

            if (constraintViolations.isEmpty()) {
                // here we only allow basic characteristics of car to be changed and not car model since it should be fixed
                Car carToUpdate = retrieveCarByCarId(car.getCarId());
                carToUpdate.setCarLicensePlate(car.getCarLicensePlate());
                carToUpdate.setCarColour(car.getCarColour());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new CarNotFoundException("Car ID [" + car.getCarId() + "] does not exist!");
        }
    }

    @Override
    public void deleteCar(Long carId, Long carModelId) throws CarNotFoundException {
        try {
            Car carToRemove = retrieveCarByCarId(carId);
            CarModel carModel = carModelSessionBeanLocal.retrieveCarModelById(carModelId);
            if (carToRemove.getRentalReservation() == null) {
                carModel.getCars().remove(carToRemove);
                em.remove(carToRemove);
            } else {
                carToRemove.setCarIsEnabled(Boolean.FALSE);
                carToRemove.setOutlet(null);
            }
        } catch (CarNotFoundException ex) {
            throw new CarNotFoundException("Car ID [" + carId + "] cannot be found!");
        } catch (CarModelNotFoundException ex) {
            throw new CarNotFoundException("Car Model ID [" + carModelId + "] cannot be found!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Car>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
