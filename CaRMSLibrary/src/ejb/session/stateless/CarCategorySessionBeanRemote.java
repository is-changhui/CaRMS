/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarCategory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CarCategoryExistException;
import util.exception.CarCategoryNotFoundException;
import util.exception.EmptyRentalRateException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface CarCategorySessionBeanRemote {
    
    public Long createNewCarCategory(CarCategory newCarCategory) throws CarCategoryExistException, UnknownPersistenceException, InputDataValidationException;

    public List<CarCategory> retrieveAllCarCategories();

    public CarCategory retrieveCarCategoryById(Long carCategoryId) throws CarCategoryNotFoundException;
    
    public CarCategory retrieveCarCategoryByName(String carCategoryName) throws CarCategoryNotFoundException;
    
    public BigDecimal calculateRentalFee(Long carCategoryId, Date pickupDateTime, Date returnDateTime) throws EmptyRentalRateException;
    
}
