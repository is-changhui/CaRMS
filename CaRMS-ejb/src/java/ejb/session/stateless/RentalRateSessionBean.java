/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarCategory;
import entity.RentalRate;
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
import util.exception.InputDataValidationException;
import util.exception.RentalRateRecordExistException;
import util.exception.RentalRateRecordNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRentalRateRecordException;

/**
 *
 * @author Darie
 */
@Stateless
public class RentalRateSessionBean implements RentalRateSessionBeanRemote, RentalRateSessionBeanLocal {

    @EJB
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public RentalRateSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // MCUC9.1: Create a new car rental rate record for a particular car category. 
    // MCUC9.2: Basic attributes should include name, car category, rate per day (i.e., 24 hour period), validity period (if applicable).
    @Override
    public Long createNewRentalRate(RentalRate newRentalRate) throws RentalRateRecordExistException, UnknownPersistenceException, InputDataValidationException {

        Set<ConstraintViolation<RentalRate>> constraintViolations = validator.validate(newRentalRate);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newRentalRate);
                em.flush();
                return newRentalRate.getRentalRateId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new RentalRateRecordExistException();
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
    public Long createNewRentalRateJoinCarCategory(RentalRate newRentalRate, Long carCategoryId) throws RentalRateRecordExistException, UnknownPersistenceException, InputDataValidationException, RentalRateRecordNotFoundException, CarCategoryNotFoundException {
        try {
            Set<ConstraintViolation<RentalRate>> constraintViolations = validator.validate(newRentalRate);

            if (constraintViolations.isEmpty()) {
                try {
                    CarCategory carCategory = carCategorySessionBeanLocal.retrieveCarCategoryById(carCategoryId);
                    // carCategory (1) ------ rentalRate (1..*)
                    newRentalRate.setCarCategory(carCategory);
                    carCategory.getRentalRates().add(newRentalRate);
                    em.persist(newRentalRate);
                    em.flush();
                    return newRentalRate.getRentalRateId();
                } catch (PersistenceException ex) {
                    if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                        if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                            throw new RentalRateRecordExistException();
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
            throw new CarCategoryNotFoundException("Car Category ID " + carCategoryId + " does not exist!");
        }
    }

    // MCUC10.1: Display a list of all car rental rate records in the system.
    @Override
    public List<RentalRate> retrieveAllRentalRates() {

        // MCUC10.2: Records should be sorted in ascending order by car category and validity period.
        Query query = em.createQuery("SELECT r FROM RentalRate r ORDER BY r.carCategory.categoryName, r.rentalRateStartDateTime, r.rentalRateEndDateTime ASC");
        return query.getResultList();
    }

    // MCUC11: View the details of a particular car rental rate record.
    @Override
    public RentalRate retrieveRentalRateById(Long rentalRateId) throws RentalRateRecordNotFoundException {

        RentalRate rentalRate = em.find(RentalRate.class, rentalRateId);

        if (rentalRate != null) {
            return rentalRate;
        } else {
            throw new RentalRateRecordNotFoundException("Rental Rate ID [" + rentalRateId + "] does not exist!");
        }
    }

    @Override
    public RentalRate retrieveRentalRateByName(String rentalRateName) throws RentalRateRecordNotFoundException {
        Query query = em.createQuery("SELECT r FROM RentalRate r WHERE r.rentalRateName = :inrentalRateName");
        query.setParameter("inrentalRateName", rentalRateName);

        try {
            return (RentalRate) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new RentalRateRecordNotFoundException("Rental Rate name [" + rentalRateName + "] does not exist!");
        }
    }

    // MCUC12: Update the details of a particular car rental rate record. 
    @Override
    public void updateRentalRate(RentalRate rentalRate) throws UpdateRentalRateRecordException, RentalRateRecordNotFoundException {

        if (rentalRate != null && rentalRate.getRentalRateId() != null) {

            RentalRate rentalRateRecordToUpdate = retrieveRentalRateById(rentalRate.getRentalRateId());

            if (rentalRateRecordToUpdate.getRentalRateName().equals(rentalRate.getRentalRateName())) {
                rentalRateRecordToUpdate.setRentalRateType(rentalRate.getRentalRateType());
                rentalRateRecordToUpdate.setRentalDailyRate(rentalRate.getRentalDailyRate());
                rentalRateRecordToUpdate.setRentalRateStartDateTime(rentalRate.getRentalRateStartDateTime());
                rentalRateRecordToUpdate.setRentalRateEndDateTime(rentalRate.getRentalRateEndDateTime());
                rentalRateRecordToUpdate.setIsEnabled(rentalRate.getIsEnabled());
                rentalRateRecordToUpdate.setIsUsed(rentalRate.getIsUsed());
                rentalRateRecordToUpdate.setCarCategory(rentalRate.getCarCategory());
            } else {
                throw new UpdateRentalRateRecordException("Name of rental rate record to be updated does not match the existing record!");
            }
        } else {
            throw new RentalRateRecordNotFoundException("Rental Rate ID [" + rentalRate.getRentalRateId() + "] does not exist!");
        }
    }

    // MCUC13
    @Override
    public void deleteRentalRate(Long rentalRateId) throws RentalRateRecordNotFoundException {

        try {
            RentalRate rentalRateToRemove = retrieveRentalRateById(rentalRateId);
            if (!rentalRateToRemove.getIsUsed()) {
                em.remove(rentalRateToRemove);
            } else {
                rentalRateToRemove.setIsEnabled(Boolean.FALSE);
            }
        } catch (RentalRateRecordNotFoundException ex) {
            throw new RentalRateRecordNotFoundException("RentalRate ID [" + rentalRateId + "] is being used at the moment and cannot be deleted!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RentalRate>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
