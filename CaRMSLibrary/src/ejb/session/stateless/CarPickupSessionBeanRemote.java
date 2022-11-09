/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarPickup;
import javax.ejb.Remote;
import util.exception.CarPickupRecordExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface CarPickupSessionBeanRemote {
    
    public Long createNewCarPickup(CarPickup newCarPickup) throws CarPickupRecordExistException, UnknownPersistenceException, InputDataValidationException;
    
}
