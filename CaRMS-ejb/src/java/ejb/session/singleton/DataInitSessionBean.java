/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CarCategorySessionBeanLocal;
import ejb.session.stateless.CarModelSessionBeanLocal;
import ejb.session.stateless.CarSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.RentalRateSessionBeanLocal;
import entity.Car;
import entity.CarCategory;
import entity.CarModel;
import entity.Employee;
import entity.Outlet;
import entity.Partner;
import entity.RentalRate;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CarStatusEnum;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RentalRateTypeEnum;
import util.exception.CarCategoryExistException;
import util.exception.CarCategoryNotFoundException;
import util.exception.CarExistException;
import util.exception.CarModelExistException;
import util.exception.CarModelNotEnabledException;
import util.exception.CarModelNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.OutletExistException;
import util.exception.OutletNotFoundException;
import util.exception.PartnerExistException;
import util.exception.PartnerNotFoundException;
import util.exception.RentalRateRecordExistException;
import util.exception.RentalRateRecordNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private RentalRateSessionBeanLocal rentalRateSessionBeanLocal;

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private CarModelSessionBeanLocal carModelSessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB
    private CarCategorySessionBeanLocal carCategorySessionBeanLocal;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    
    @PersistenceContext(unitName = "CaRMS-ejbPU")
    private EntityManager em;


    public DataInitSessionBean() {
        
    }

    
    @PostConstruct
    public void postConstruct() {
        try {
            outletSessionBeanLocal.retrieveOutletById(1l);
            employeeSessionBeanLocal.retrieveEmployeeByEmployeeId(1l);
            carCategorySessionBeanLocal.retrieveCarCategoryById(1l);
            partnerSessionBeanLocal.retrievePartnerById(1l);
        } catch(EmployeeNotFoundException | CarCategoryNotFoundException | OutletNotFoundException | PartnerNotFoundException ex) {
            loadTestData();
        }
    }
    
    private void loadTestData() {
        
        try {
            Long aId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet A", "A", null, null));
            Long bId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet B", "B", null, null));
            Long cId = outletSessionBeanLocal.createNewOutlet(new Outlet("Outlet C", "C", "08:00", "22:00"));
         
            Employee a1 = new Employee("Employee A1", "A1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            Long a1Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a1, aId);
            Employee a2 = new Employee("Employee A2", "A2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            Long a2Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a2, aId);
            Employee a3 = new Employee("Employee A3", "A3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            Long a3Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a3, aId);
            Employee a4 = new Employee("Employee A4", "A4", "password", EmployeeAccessRightEnum.EMPLOYEE);
            Long a4Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a4, aId);
            Employee a5 = new Employee("Employee A5", "A5", "password", EmployeeAccessRightEnum.EMPLOYEE);
            Long a5Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(a5, aId);
            Employee b1 = new Employee("Employee B1", "B1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            Long b1Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b1, bId);
            Employee b2 = new Employee("Employee B2", "B2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            Long b2Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b2, bId);
            Employee b3 = new Employee("Employee B3", "B3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            Long b3Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(b3, bId);
            Employee c1 = new Employee("Employee C1", "C1", "password", EmployeeAccessRightEnum.SALES_MANAGER);
            Long c1Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c1, cId);
            Employee c2 = new Employee("Employee C2", "C2", "password", EmployeeAccessRightEnum.OPERATIONS_MANAGER);
            Long c2Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c2, cId);
            Employee c3 = new Employee("Employee C3", "C3", "password", EmployeeAccessRightEnum.CUSTOMER_SERVICE_EXECUTIVE);
            Long c3Id = employeeSessionBeanLocal.createNewEmployeeJoinOutlet(c3, cId);
            
            Long standardSedanId = carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Standard Sedan"));
            Long familySedanId = carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Family Sedan"));
            Long luxurySedanId = carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("Luxury Sedan"));
            Long suvAndMinivanId = carCategorySessionBeanLocal.createNewCarCategory(new CarCategory("SUV and Minivan"));
            
            CarModel toyotaCorolla = new CarModel("Toyota", "Corolla", Boolean.TRUE);
            Long toyotaCorollaId = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(toyotaCorolla, standardSedanId);
            CarModel hondaCivic = new CarModel("Honda", "Civic", Boolean.TRUE);
            Long hondaCivicId = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(hondaCivic, standardSedanId);
            CarModel nissanSunny = new CarModel("Nissan", "Sunny", Boolean.TRUE);
            Long nissanSunnyId = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(nissanSunny, standardSedanId);
            CarModel mercedesEClass = new CarModel("Mercedes", "E Class", Boolean.TRUE);
            Long mercedesEClassId = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(mercedesEClass, luxurySedanId);
            CarModel bmw5Series = new CarModel("BMW", "5 Series", Boolean.TRUE);
            Long bmw5SeriesId = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(bmw5Series, luxurySedanId);
            CarModel audiA6 = new CarModel("Audi", "A6", Boolean.TRUE);
            Long audiA6Id = carModelSessionBeanLocal.createNewCarModelJoinCarCategory(audiA6, luxurySedanId);
            
            Car a1Tc = new Car("SS00A1TC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long a1TcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(a1Tc, toyotaCorollaId, aId);
            Car a2Tc = new Car("SS00A2TC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long a2TcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(a2Tc, toyotaCorollaId, aId);
            Car a3Tc = new Car("SS00A3TC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long a3TcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(a3Tc, toyotaCorollaId, aId);
            Car b1Hc = new Car("SS00B1HC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long b1HcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(b1Hc, hondaCivicId, bId);
            Car b2Hc = new Car("SS00B2HC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long b2HcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(b2Hc, hondaCivicId, bId);
            Car b3Hc = new Car("SS00B3HC", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long b3HcId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(b3Hc, hondaCivicId, bId);
            Car c1Ns = new Car("SS00C1NS", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long c1NsId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(c1Ns, nissanSunnyId, cId);
            Car c2Ns = new Car("SS00C2NS", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long c2NsId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(c2Ns, nissanSunnyId, cId);
            Car c3Ns = new Car("SS00C3NS", "white", Boolean.TRUE, CarStatusEnum.REPAIR);
            Long c3NsId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(c3Ns, nissanSunnyId, cId);
            Car a4Me = new Car("LS00A4ME", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long a4MeId = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(a4Me, mercedesEClassId, aId);
            Car b4B5 = new Car("LS00B4B5", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long b4B5Id = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(b4B5, bmw5SeriesId, bId);
            Car c4A6 = new Car("LS00C4A6", "white", Boolean.TRUE, CarStatusEnum.AVAILABLE);
            Long c4A6Id = carSessionBeanLocal.createNewCarJoinCarModelJoinOutlet(c4A6, audiA6Id, cId);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date alwaysValidStartDateTime = sdf.parse("01/01/1900 00:00");
            Date alwaysValidEndDateTime = sdf.parse("31/12/2999 23:createNewCarJoinCarModelJoinOutlet59");
            
            RentalRate stdSedanDefault = new RentalRate("Standard Sedan - Default", RentalRateTypeEnum.DEFAULT, BigDecimal.valueOf(100), alwaysValidStartDateTime, alwaysValidEndDateTime, Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(stdSedanDefault, standardSedanId);
            RentalRate stdSedanWeekendPromo = new RentalRate("Standard Sedan - Weekend Promo", RentalRateTypeEnum.PROMOTION, BigDecimal.valueOf(80), sdf.parse("09/12/2022 12:00"), sdf.parse("11/12/2022 00:00"), Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(stdSedanWeekendPromo, standardSedanId);
            RentalRate famSedanDefault = new RentalRate("Family Sedan - Default", RentalRateTypeEnum.DEFAULT, BigDecimal.valueOf(200), alwaysValidStartDateTime, alwaysValidEndDateTime, Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(famSedanDefault, familySedanId);
            RentalRate luxSedanDefault = new RentalRate("Luxury Sedan - Default", RentalRateTypeEnum.DEFAULT, BigDecimal.valueOf(300), alwaysValidStartDateTime, alwaysValidEndDateTime, Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(luxSedanDefault, luxurySedanId);
            RentalRate luxSedanMonday = new RentalRate("Luxury Sedan - Monday", RentalRateTypeEnum.PEAK, BigDecimal.valueOf(310), sdf.parse("05/12/2022 00:00"), sdf.parse("05/12/2022 23:59"), Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(luxSedanMonday, luxurySedanId);
            RentalRate luxSedanTuesday = new RentalRate("Luxury Sedan - Tuesday", RentalRateTypeEnum.PEAK, BigDecimal.valueOf(320), sdf.parse("06/12/2022 00:00"), sdf.parse("06/12/2022 23:59"), Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(luxSedanTuesday, luxurySedanId);
            RentalRate luxSedanWednesday = new RentalRate("Luxury Sedan - Wednesday", RentalRateTypeEnum.PEAK, BigDecimal.valueOf(330), sdf.parse("07/12/2022 00:00"), sdf.parse("07/12/2022 23:59"), Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(luxSedanWednesday, luxurySedanId);
            RentalRate luxSedanWeekdayPromo = new RentalRate("Luxury Sedan - Weekday Promo", RentalRateTypeEnum.PROMOTION, BigDecimal.valueOf(250), sdf.parse("07/12/2022 12:00"), sdf.parse("08/12/2022 12:00"), Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(luxSedanWeekdayPromo, luxurySedanId);
            RentalRate suvAndMinivanDefault = new RentalRate("SUV and Minivan - Default", RentalRateTypeEnum.DEFAULT, BigDecimal.valueOf(400), alwaysValidStartDateTime, alwaysValidEndDateTime, Boolean.TRUE, Boolean.FALSE);
            rentalRateSessionBeanLocal.createNewRentalRateJoinCarCategory(suvAndMinivanDefault, suvAndMinivanId);
            
            partnerSessionBeanLocal.createNewPartner(new Partner("Holiday.com", "holidaymanager", "password"));
            
        } catch (RentalRateRecordExistException | RentalRateRecordNotFoundException | ParseException | CarModelNotEnabledException | CarModelNotFoundException | CarExistException | CarCategoryNotFoundException | CarModelExistException | OutletExistException | OutletNotFoundException | EmployeeUsernameExistException | PartnerExistException | CarCategoryExistException | UnknownPersistenceException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
    }
}
