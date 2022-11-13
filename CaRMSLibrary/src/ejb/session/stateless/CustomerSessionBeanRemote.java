/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface CustomerSessionBeanRemote {
    
    public Long createNewCustomer(Customer newEmployee) throws CustomerUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;
    
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;
}
