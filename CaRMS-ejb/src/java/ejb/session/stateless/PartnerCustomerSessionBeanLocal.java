/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerCustomer;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PartnerCustomerNameExistException;
import util.exception.PartnerCustomerNotFoundException;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface PartnerCustomerSessionBeanLocal {

    public PartnerCustomer retrievePartnerCustomerById(Long partnerCustomerId) throws PartnerCustomerNotFoundException;

    public Long createNewCustomer(PartnerCustomer newPartnerCustomer, Long partnerId) throws PartnerNotFoundException, PartnerCustomerNameExistException, UnknownPersistenceException, InputDataValidationException;
    
}
