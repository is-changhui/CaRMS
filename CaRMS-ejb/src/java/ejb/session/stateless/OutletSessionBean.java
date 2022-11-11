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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.EmployeeNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OutletExistException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateEmployeeException;
import util.exception.UpdateOutletException;

/**
 *
 * @author Darie
 */
@Stateless
public class OutletSessionBean implements OutletSessionBeanRemote, OutletSessionBeanLocal {

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OutletSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewOutlet(Outlet newOutlet) throws OutletExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Outlet>>constraintViolations = validator.validate(newOutlet);
        
        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newOutlet);
                em.flush();
                return newOutlet.getOutletId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new OutletExistException();
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
    public List<Outlet> retrieveAllOutlets() {
        Query query = em.createQuery("SELECT o FROM Outlet o");
        return query.getResultList();
    }
    
    
    
    @Override
    public Outlet retrieveOutletById(Long outletId) throws OutletNotFoundException {
        Outlet outletEntity = em.find(Outlet.class, outletId);
        
        if (outletEntity != null) {
            return outletEntity;
        } else {
            throw new OutletNotFoundException("Outlet ID [" + outletId + "] does not exist!");
        }
    }
    
    @Override
    public void updateOutlet(Outlet outlet) throws OutletNotFoundException, UpdateOutletException, InputDataValidationException
    {
        if(outlet != null && outlet.getOutletId() != null)
        {
            Set<ConstraintViolation<Outlet>>constraintViolations = validator.validate(outlet);
        
            if(constraintViolations.isEmpty())
            {
                Outlet outletEntityToUpdate = retrieveOutletById(outlet.getOutletId());

                if(outletEntityToUpdate.getOutletName().equals(outlet.getOutletName()))
                {
                    outletEntityToUpdate.setOutletName(outlet.getOutletName());
                    outletEntityToUpdate.setOutletAddress(outlet.getOutletAddress());
                    outletEntityToUpdate.setOutletOpeningHour(outlet.getOutletOpeningHour());
                    outletEntityToUpdate.setOutletClosingHour(outlet.getOutletClosingHour());
                }
                else
                {
                    throw new UpdateOutletException("Name of outlet record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new OutletNotFoundException("Outlet ID [" + outlet.getOutletId() + "] does not exist!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Outlet>>constraintViolations) {
        String msg = "Input data validation error!:";
            
        for (ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
}
