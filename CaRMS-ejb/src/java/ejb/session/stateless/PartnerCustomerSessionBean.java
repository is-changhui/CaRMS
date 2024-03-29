/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Partner;
import entity.PartnerCustomer;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PartnerCustomerNameExistException;
import util.exception.PartnerCustomerNotFoundException;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class PartnerCustomerSessionBean implements PartnerCustomerSessionBeanRemote, PartnerCustomerSessionBeanLocal {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PartnerCustomerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewCustomer(PartnerCustomer newPartnerCustomer, Long partnerId) throws PartnerNotFoundException, PartnerCustomerNameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<PartnerCustomer>> constraintViolations = validator.validate(newPartnerCustomer);

            if (constraintViolations.isEmpty()) {

                Partner partner = partnerSessionBeanLocal.retrievePartnerById(partnerId);
                // Partner (1) ----- PartnerCustomer (0..*)
                newPartnerCustomer.setPartner(partner);
                partner.getPartnerCustomers().add(newPartnerCustomer);
                em.persist(newPartnerCustomer);
                em.flush();
                return newPartnerCustomer.getPartnerCustomerId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new PartnerCustomerNameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }

    }

    @Override
    public PartnerCustomer retrievePartnerCustomerById(Long partnerCustomerId) throws PartnerCustomerNotFoundException {
        PartnerCustomer partnerCustomerEntity = em.find(PartnerCustomer.class, partnerCustomerId);

        if (partnerCustomerEntity != null) {
            return partnerCustomerEntity;
        } else {
            throw new PartnerCustomerNotFoundException("Partner Customer ID " + partnerCustomerId + " does not exist!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartnerCustomer>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
