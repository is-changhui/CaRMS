/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarModel;
import java.util.List;
import javax.ejb.Local;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarModelExistException;
import util.exception.CarModelNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface CarModelSessionBeanLocal {

    public Long createNewCarModel(CarModel newCarModel) throws CarModelExistException, UnknownPersistenceException, InputDataValidationException;

    public List<CarModel> retrieveAllCarModels();

    public CarModel retrieveCarModelByCarModelId(Long carModelId) throws CarModelNotFoundException;

    public Long createNewCarModelJoinCarCategory(CarModel newCarModel, Long carCategoryId) throws CarModelExistException, UnknownPersistenceException, InputDataValidationException, CarCategoryNotFoundException;

}
