/*
* Team project for course PA165 - Enterprise Applications in Java
* For more informations see file README.md
*/
package cz.muni.fi.pa165_pneuservis.data;

import cz.muni.fi.pa165_pneuservis.model.Customer;
import cz.muni.fi.pa165_pneuservis.model.Order;
import cz.muni.fi.pa165_pneuservis.model.Service;
import cz.muni.fi.pa165_pneuservis.model.ServiceType;
import cz.muni.fi.pa165_pneuservis.model.Tire;
import cz.muni.fi.pa165_pneuservis.service.CustomerService;
import cz.muni.fi.pa165_pneuservis.service.OrderService;
import cz.muni.fi.pa165_pneuservis.service.PneuBusinessException;
import cz.muni.fi.pa165_pneuservis.service.ServiceService;
import cz.muni.fi.pa165_pneuservis.service.TireService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.security.SecureRandom;
import java.util.Date;
import javax.transaction.Transactional;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jozef Sumaj <374029@mail.muni.cz>
 */
@Component
@Transactional
public class PrepareEnvironmentFacadeImpl implements PrepareEnvironmentFacade {
    
    @Autowired
    private TireService tireService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private OrderService orderService;
    
    static Random randomGenerator;
    static Integer index;
    static SecureRandom secureRandom;
    
    //Item
    static String  name;
    static String  description;
    static Double  priceDouble;
    static BigDecimal priceBigDecimal;
    
    //Tire
    static String  brand;
    static Integer width;
    static Integer ratio;
    static Integer rim;
    
    //Customer
    static String  firstName;
    static String  lastName;
    static String  streetName;
    static Integer streetNumber;
    static String  city;
    static String  state;
    static String  postalNumber;
    static String  phoneNumber;
    static String  email;
    static String  password;
    
    //Service
    static ServiceType serviceType;
    
    /**
     * Fills system with test data
     * Password for Customer will be always containing firstName of customer -> "heslo{firstName}"
     */
    @Override
    public void PrepareEnvironment() {
        
        //List of names that will be randomly selected when creating Tires
        ArrayList<String> tireBrand = new ArrayList<>(Arrays.asList("Michelin","Yokohama","Pirelli","Goodyear","Barum"));
        ArrayList<Integer> tireWidth = new ArrayList<>(Arrays.asList(155,165,175,185,195));
        ArrayList<Integer> tireRatio = new ArrayList<>(Arrays.asList(55,60,65,70,75));
        ArrayList<Integer> tireRim = new ArrayList<>(Arrays.asList(14,15,16,17,18,19));
        
        //List of names that will be randomly selected when creating Tires
        ArrayList<String> custFirstName = new ArrayList<>(Arrays.asList("Pavel","Martin","Erik","Honza","Juraj"));
        ArrayList<String> custLastName = new ArrayList<>(Arrays.asList("Horky","Studeny","Tmavy","Bledy","Zeleny"));
        ArrayList<String> custStreetName = new ArrayList<>(Arrays.asList("Vodova","Pekna","Hladka ulica","Krasna","Namestie mesta"));
        ArrayList<String> custCity = new ArrayList<>(Arrays.asList("Brno","Praha","Hradec Kralove","Breclav","Ostrava"));
        ArrayList<String> custMail = new ArrayList<>(Arrays.asList("franta@gmail.com","jitka@seznam.cz","dmitrij@mail.ru","adam@email.com","user@securemail.net"));
        ArrayList<String> custPostal = new ArrayList<>(Arrays.asList("61200","61300","12300","81500","74000"));
           
        Tire tire = null;
        Customer customer = null;
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Service service = null;
        Order order = null;
        randomGenerator = new Random();
        
        //create 10 Tires with random parameters
        for (long i = 1; i <= 10; i++) {
            tire = new Tire();
            
            index = randomGenerator.nextInt(tireBrand.size());
            brand = tireBrand.get(index);
            index = randomGenerator.nextInt(tireWidth.size());
            width = tireWidth.get(index);
            index = randomGenerator.nextInt(tireRatio.size());
            ratio = tireRatio.get(index);
            index = randomGenerator.nextInt(tireRim.size());
            rim = tireRim.get(index);
            
            //Composed tire name and description
            name = brand+"/"+width+"/"+ratio+"/"+rim;
            description = "This is "+brand+" tire with width:"+width+", ratio:"+ratio+" and rim:"+rim;
            
            //Random price from interval 200-600 => (max-min)+min
            priceDouble = Math.random() * 400 + 200;
            priceBigDecimal = BigDecimal.valueOf(priceDouble).setScale(2, RoundingMode.CEILING);
            
            //create tire with random parameters
            tire.setName(name);
            tire.setBrand(brand);
            tire.setDescription(description);
            tire.setWidth(width);
            tire.setRatio(ratio);
            tire.setRim(rim);
            tire.setPrice(priceBigDecimal);
            
            try {
                tireService.createTire(tire);
            } catch (PneuBusinessException ex) {
                //TODO log
            }
        }
        
        int number;
        secureRandom = new SecureRandom();
        String mailAddr;
        
        //create 30 Customers with random parameters
        for (long i = 1; i <= 30; i++) {
            customer = new Customer();
            
            index = randomGenerator.nextInt(custFirstName.size());
            firstName = custFirstName.get(index);
            index = randomGenerator.nextInt(custLastName.size());
            lastName = custLastName.get(index);
            index = randomGenerator.nextInt(custStreetName.size());
            streetName = custStreetName.get(index);
            index = randomGenerator.nextInt(custCity.size());
            city = custCity.get(index);
            index = randomGenerator.nextInt(custPostal.size());
            postalNumber = custPostal.get(index);
            
            //Random street number from interval 1-250 => (max-min)+min
            streetNumber = (int)(Math.random() * 249) + 1;
            
            //Random phone number from interval 700 000 000 - 900 000 000 => (max-min)+min
            number = (int)(Math.random() * 200000000) + 700000000;
            phoneNumber = "+420"+number;
            
            //Random e-mail.
            index = randomGenerator.nextInt(custMail.size());
            email = custMail.get(index);
            
            //create tire with random parameters
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setCity(city);
            customer.setStreetName(streetName);
            customer.setStreetNumber(streetNumber);
            customer.setPostalNumber(postalNumber);
            customer.setState("Ceska republika");
            customer.setPhoneNumber(phoneNumber);
            customer.setEmail(email);
            //Password for Customer will be always containing firstName of customer -> "heslo{firstName}"
            customer.setPassword("heslo" + firstName);
            
            customers.add(customer);
            
            customerService.createCustomer(customer);
        }
        
        //create 20 Services with random parameters
        for (long i = 1; i <= 20; i++) {
            service = new Service();
            
            //Enum name of service
            index = randomGenerator.nextInt(ServiceType.values().length);
            serviceType = ServiceType.values()[index];
            
            //Random price from interval 50-1000 => (max-min)+min
            priceDouble = Math.random() * 950 + 50;
            priceBigDecimal = BigDecimal.valueOf(priceDouble).setScale(2, RoundingMode.CEILING);
            
            //Composed tire name and description
            name = serviceType.toString();
            description = serviceType + " service for price " + priceBigDecimal + " for your vehicle";
            
            service.setServiceType(serviceType);
            service.setName(name);
            service.setDescription(description);
            service.setPrice(priceBigDecimal);
            
            serviceService.createService(service);
        }
        
        
        //create 20 Orders with random parameters
        for (long i = 1; i <= 20; i++) {
            order = new Order();
            
            Random  rnd;
            Date    dt, dt2;
            long    ms, ms2;

            rnd = new Random();
            ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
            ms2 = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
            dt = new Date(ms);
            dt2 = new Date(ms2);
                        
            //Random price from interval 50-1000 => (max-min)+min
            priceDouble = Math.random() * 950 + 50;
            priceBigDecimal = BigDecimal.valueOf(priceDouble).setScale(2, RoundingMode.CEILING);
                        
            order.setTotalPrice(priceBigDecimal);
            order.setCustomer(customers.get((int)i % customers.size()-1));
            order.setCreateDate(dt);
            order.setCompleteDate(dt2);
            
            orderService.createOrder(order);
        }
    }
   
    
}
