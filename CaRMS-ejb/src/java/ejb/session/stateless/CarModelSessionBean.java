/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarCategory;
import entity.CarModel;
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
import util.exception.CarCategoryNotFoundException;
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

    @EJB
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method"

    public CarModelSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // MCUC14
    @Override
    public Long createNewCarModel(CarModel newCarModel) throws CarModelExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<CarModel>> constraintViolations = validator.validate(newCarModel);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCarModel);
                em.flush();
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

    // For use in DataInitSessionBean
    @Override
    public Long createNewCarModelJoinCarCategory(CarModel newCarModel, Long carCategoryId) throws CarModelExistException, UnknownPersistenceException, InputDataValidationException, CarCategoryNotFoundException {
        try {
            Set<ConstraintViolation<CarModel>> constraintViolations = validator.validate(newCarModel);

            if (constraintViolations.isEmpty()) {
                try {
                    CarCategory carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(carCategoryId);
                    // carCategory (1) ------ CarModel (1..*)
                    carCategory.getCarModels().add(newCarModel);
                    newCarModel.setCarCategory(carCategory);
                    em.persist(newCarModel);
                    em.flush();
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
        } catch (CarCategoryNotFoundException ex) {
            throw new CarCategoryNotFoundException("Car Category ID [" + carCategoryId + "] does not exist!");
        }
    }

    // MCUC15
    @Override
    public List<CarModel> retrieveAllCarModels() {
        // MCUC15.2: ascending order by car category, make, model
        Query query = em.createQuery("SELECT cm FROM CarModel cm ORDER BY cm.carCategory.categoryName, cm.carMake, cm.modelName ASC");
        return query.getResultList();
    }

    @Override
    public CarModel retrieveCarModelById(Long carModelId) throws CarModelNotFoundException {
        CarModel carModelFinding = em.find(CarModel.class, carModelId);

        if (carModelFinding != null) {
            return carModelFinding;
        } else {
            throw new CarModelNotFoundException("CarModel ID [" + carModelId + "] does not exist!");
        }
    }

    @Override
    public CarModel retrieveCarModelByModelName(String carModelName) throws CarModelNotFoundException {
        Query query = em.createQuery("SELECT cm FROM CarModel cm WHERE cm.modelName = :inCarModelName");
        query.setParameter("inCarModelName", carModelName);

        try {
            return (CarModel) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CarModelNotFoundException("Car Model name [" + carModelName + "] does not exist!");
        }
    }

    // MCUC16
    @Override
    public void updateCarModel(CarModel carModel, Long updatedCarCategoryId) throws CarModelNotFoundException, CarCategoryNotFoundException, InputDataValidationException {

        if (carModel != null && carModel.getModelId() != null) {
            Set<ConstraintViolation<CarModel>> constraintViolations = validator.validate(carModel);

            if (constraintViolations.isEmpty()) {
                CarModel carModelRecordToUpdate = retrieveCarModelById(carModel.getModelId());
                carModelRecordToUpdate.setCarMake(carModel.getCarMake());
                carModelRecordToUpdate.setModelName(carModel.getModelName());
                carModelRecordToUpdate.setModelIsEnabled(carModel.isModelIsEnabled());
                carModelRecordToUpdate.setCars(carModel.getCars());
                try {
                    CarCategory carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(updatedCarCategoryId);
                    carModelRecordToUpdate.setCarCategory(carCategory);
                } catch (CarCategoryNotFoundException ex) {
                    throw new CarCategoryNotFoundException("Car Category ID [" + updatedCarCategoryId + "] not found!");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new CarModelNotFoundException("Car Model ID [" + carModel.getModelName() + "] does not exist!");
        }
    }

    // MCUC17
    @Override
    public void deleteCarModel(Long carModelId) throws CarModelNotFoundException {

        try {
            CarModel carModelToRemove = retrieveCarModelById(carModelId);
            // if there are cars tied to this model, then the model is considered being used and cannot be deleted
            if (carModelToRemove.getCars().isEmpty()) {
                em.remove(carModelToRemove);
            } else {
                carModelToRemove.setModelIsEnabled(Boolean.FALSE);
            }
        } catch (CarModelNotFoundException ex) {
            throw new CarModelNotFoundException("Car Model ID [" + carModelId + "] cannot be found!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CarModel>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
