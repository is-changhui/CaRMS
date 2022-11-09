/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReturn;
import javax.ejb.Remote;
import util.exception.CarReturnRecordExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface CarReturnSessionBeanRemote {
    
    public Long createNewCarReturn(CarReturn newCarReturn) throws CarReturnRecordExistException, UnknownPersistenceException, InputDataValidationException;
    
}
