/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Partner;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InputDataValidationException;
import util.exception.PartnerExistException;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface PartnerSessionBeanRemote {
    
    public Long createNewPartner(Partner newPartner) throws PartnerExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Partner> retrieveAllPartners();

    public Partner retrievePartnerById(Long partnerId) throws PartnerNotFoundException;
    
}
