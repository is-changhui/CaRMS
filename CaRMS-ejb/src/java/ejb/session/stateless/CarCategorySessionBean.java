/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarCategory;
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
import util.exception.CarCategoryExistException;
import util.exception.CarCategoryNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class CarCategorySessionBean implements CarCategorySessionBeanRemote, CarCategorySessionBeanLocal {

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    
    public CarCategorySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewCarCategory(CarCategory newCarCategory) throws CarCategoryExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<CarCategory>>constraintViolations = validator.validate(newCarCategory);
        
        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCarCategory);
                em.flush();
                return newCarCategory.getCategoryId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CarCategoryExistException();
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
    public List<CarCategory> retrieveAllCarCategories() {
        Query query = em.createQuery("SELECT c FROM CarCategory c");
        return query.getResultList();
    }
    
    
    
    @Override
    public CarCategory retrieveCarCategoryById(Long carCategoryId) throws CarCategoryNotFoundException {
        CarCategory carCategoryEntity = em.find(CarCategory.class, carCategoryId);
        
        if (carCategoryEntity != null) {
            return carCategoryEntity;
        } else {
            throw new CarCategoryNotFoundException("Car Category ID [" + carCategoryId + "] does not exist!");
        }
    }
    
    @Override
    public CarCategory retrieveCarCategoryByName(String carCategoryName) throws CarCategoryNotFoundException {
        Query query = em.createQuery("SELECT c FROM CarCategory c WHERE c.categoryName = :incarCategoryName");
        query.setParameter("incarCategoryName", carCategoryName);

        try {
            return (CarCategory) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CarCategoryNotFoundException("Car Category name [" + carCategoryName + "] does not exist!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CarCategory>>constraintViolations) {
        String msg = "Input data validation error!:";
            
        for (ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
