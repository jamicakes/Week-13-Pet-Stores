package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;
import pet.store.entity.Customer;
import pet.store.entity.Employee; 


@Service
public class PetStoreService {


  @Autowired
  private PetStoreDao petStoreDao;
  
  @Autowired
  private EmployeeDao employeeDao;

  @Autowired
  private CustomerDao customerDao;

  @Transactional(readOnly = false)
  public PetStoreData savePetStore(PetStoreData petStoreData) {
    Long petStoreId = petStoreData.getPetStoreId();
    PetStore petStore = findOrCreatePetStore(petStoreId);
    
    setFieldsInPetStore(petStore, petStoreData); 
    return new PetStoreData(petStoreDao.save(petStore));
  }

  
  private void setFieldsInPetStore(PetStore petStore, PetStoreData petStoreData) {
   petStore.setPetStoreId(petStoreData.getPetStoreId());
   petStore.setPetStoreName(petStoreData.getPetStoreName());
   petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
   petStore.setPetStoreCity(petStoreData.getPetStoreCity());
   petStore.setPetStoreState(petStoreData.getPetStoreState());
   petStore.setPetStoreZip(petStoreData.getPetStoreZip());
   petStore.setPetStorePhone(petStoreData.getPetStorePhone());   
    
  }

  public PetStore findOrCreatePetStore(Long petStoreId) {
    PetStore petStore;
    if (Objects.isNull(petStoreId)) {
      petStore = new PetStore();
    } else {
      petStore = findPetStoreById(petStoreId);


    }
    return petStore;
  }

  public PetStore findPetStoreById(Long petStoreId) {
    return petStoreDao.findById(petStoreId).orElseThrow(
       () -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
  }
  
//  @Transactional(readOnly = false)
//  public PetStoreEmployee saveEmployee (Long petStoreId, 
//      PetStoreEmployee petStoreEmployee) {
//    
//    PetStore petStore = findPetStoreById(petStoreId);
//    Long employeeId = petStoreEmployee.getEmployeeId(); 
//    Employee employee = findOrCreateEmployee(petStoreId, employeeId, petStoreEmployee);
//    
//   Employee  dbEmployee = employeeDao.save(employee);
//    return new PetStoreEmployee(dbEmployee); 
//  }
  
  @Transactional(readOnly = false)
  public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
    PetStore petStore = findPetStoreById(petStoreId);
    Long employeeId = petStoreEmployee.getEmployeeId();
    Employee employee = findOrCreateEmployee(petStoreId, employeeId);

    copyEmployeeFields(employee, petStoreEmployee);
    // Assign the pet store to the employee
    employee.setPetStore(petStore);
    petStore.getEmployees().add(employee);

    Employee dbEmployee = employeeDao.save(employee);
    return new PetStoreEmployee(dbEmployee);
  }


  private void copyEmployeeFields(Employee employee, 
       PetStoreEmployee petStoreEmployee) {
     
   employee.setEmployeeId(petStoreEmployee.getEmployeeId());
   employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
   employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
   employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
   employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
 //  employee.setEmployeePetStoreId(petStoreEmployee.getPetStoreId());  - 
   //don't need petstoreid b/c added to saveEmployee method
  }
  

  private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {


    if (Objects.isNull(employeeId)) {
      return new Employee();
    } 
      return findEmployeeById(petStoreId, employeeId);
    }
  
  
    private Employee findEmployeeById(Long petStoreId, Long employeeId) {
      Employee employee = employeeDao.findById(employeeId).orElseThrow(
          () -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));
      if (employee.getPetStore().getPetStoreId() != petStoreId) {
        throw new IllegalArgumentException("Employee with ID=" + employeeId
            + " is not employeed by Pet Store ID=" + petStoreId + ".");
      }

      return employee;

    }

    @Transactional(readOnly = false)
    public PetStoreCustomer saveCustomer(Long customerId, PetStoreCustomer petStoreCustomer) {
    PetStore petStore = findPetStoreById(petStoreCustomer);
    //(this needs to be changed since it’s a list for many to many)
    Long customerId = petStoreCustomer.getCustomerId();
    Customer customer = findOrCreateCustomer(petStoreId, customerId);

    copyCustomerFields(customer, petStoreCustomer);

    customer.setPetStores((Set<PetStore>) petStore);
    petStore.getCustomers().add(customer); 
    Customer dbCustomer = customerDao.save(customer);
    return new PetStoreCustomer(dbCustomer);
    }

    private void copyCustomerFields(Customer customer, 
        PetStoreCustomer petStoreCustomer) {

    customer.setCustomerId(petStoreCustomer.getCustomerId());
    customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
    customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());

   // for (PetStore petStore : petStoreCustomer.getPetStore)
    // for(Customer customer : petStore.getCustomers()) {
        // customers.add(new PetStoreCustomer(customer)); nn to fill in how to get petStoreId hash set  // assigned to the customer ID  
    }

    private Customer findOrCreateCustomer(Long petStoreId, long customerId) {

    if (Objects.isNull(customerId)) {
        return new Customer();
    } 
    return findCustomerById(petStoreId, customerId);
    }

    private Customer findCustomerById(long petStoreId, Long customerId) {
    Customer customer = customerDao.findById(customerId).orElseThrow(
        () -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));
   
            if(customer.getPetStores().getPetStoreId() != petStoreId) {
        throw new IllegalArgumentException("Employee with ID=" + customerId + 
        " is not a customer of Pet Store ID= " + petStoreId + ".");
    }
        return customer;

    }
    
  } // main pet store service class 
