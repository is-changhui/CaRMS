/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RentalRate;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CarCategoryNotFoundException;
import util.exception.DeleteRentalRateRecordException;
import util.exception.InputDataValidationException;
import util.exception.RentalRateRecordExistException;
import util.exception.RentalRateRecordNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRentalRateRecordException;

/**
 *
 * @author Darie
 */
@Remote
public interface RentalRateSessionBeanRemote {
    
    public Long createNewRentalRate(RentalRate newRentalRate) throws RentalRateRecordExistException, UnknownPersistenceException, InputDataValidationException;

    public List<RentalRate> retrieveAllRentalRates();

    public RentalRate retrieveRentalRateById(Long rentalRateId) throws RentalRateRecordNotFoundException;

    public RentalRate retrieveRentalRateByName(String rentalRateName) throws RentalRateRecordNotFoundException;

    public void updateRentalRate(RentalRate rentalRate) throws UpdateRentalRateRecordException, RentalRateRecordNotFoundException;

    public void deleteRentalRate(Long rentalRateId) throws RentalRateRecordNotFoundException, DeleteRentalRateRecordException;
    
    public Long createNewRentalRateJoinCarCategory(RentalRate newRentalRate, Long carCategoryId) throws RentalRateRecordExistException, UnknownPersistenceException, InputDataValidationException, RentalRateRecordNotFoundException, CarCategoryNotFoundException;
}
