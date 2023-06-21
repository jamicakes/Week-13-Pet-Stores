package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor
public class PetStoreCustomer {



  private Long customerId;
  private String customerEmail;
  private String customerFirstName;
  private String customerLastName;
  
  
  
  public PetStoreCustomer(Customer customer) {

    customerId = customer.getCustomerId();
    customerEmail = customer.getCustomerEmail();
    customerFirstName = customer.getCustomerFirstName();
    customerLastName = customer.getCustomerLastName();
  }
  
} // pets store customer controller class 
