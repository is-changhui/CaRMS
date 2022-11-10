/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Outlet;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.OutletExistException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface OutletSessionBeanLocal {

    public Long createNewOutlet(Outlet newOutlet) throws OutletExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Outlet> retrieveAllOutlets();

    public Outlet retrieveOutletById(Long outletId) throws OutletNotFoundException;
    
}
