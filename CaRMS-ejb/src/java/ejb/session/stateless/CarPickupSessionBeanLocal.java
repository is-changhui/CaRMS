/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarPickup;
import javax.ejb.Local;
import util.exception.CarPickupRecordExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface CarPickupSessionBeanLocal {

    public Long createNewCarPickup(CarPickup newCarPickup) throws CarPickupRecordExistException, UnknownPersistenceException, InputDataValidationException;
    
}