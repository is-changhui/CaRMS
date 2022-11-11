/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CarExistException;
import util.exception.CarModelNotEnabledException;
import util.exception.CarModelNotFoundException;
import util.exception.CarNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface CarSessionBeanRemote {

    public Long createNewCar(Car newCar) throws CarExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Car> retrieveAllCars();

    public Car retrieveCarByCarId(Long carId) throws CarNotFoundException;
    
    public Long createNewCarJoinCarModelJoinOutlet(Car newCar, Long carModelId, Long outletId) throws CarExistException, UnknownPersistenceException, InputDataValidationException, CarModelNotFoundException, CarModelNotEnabledException, OutletNotFoundException;
    
}
