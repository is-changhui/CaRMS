/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Darie
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    
    @PostConstruct
    public void postConstruct() {
//        if(em.find(Module.class, 1l) == null)
//        {
//            initialiseData();
//        }
    }
    
//    private void initialiseData() {
//        Module module = new Module("IS2103", "Enterprise Systems Server-side Design and Development");
//        em.persist(module);
//        em.flush();
//        module = new Module("IS3106", "Enterprise Systems Interface Design and Development");
//        em.persist(module);
//        em.flush();
//        module = new Module("IS4103", "Information Systems Capstone Project");
//        em.persist(module);
//        em.flush();
//        module = new Module("IS4151", "Pervasive Technology Solutions and Development");
//        em.persist(module);
//        em.flush();
//    }

}
