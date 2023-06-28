package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {

  

  
  private Long employeeId;
  private String employeeFirstName;
  private String employeeLastName;
  private String employeePhone;
  private String employeeJobTitle;
 // private Long petStoreId;
  

 public PetStoreEmployee(Employee employee) {

   employeeId = employee.getEmployeeId();
   employeeFirstName = employee.getEmployeeFirstName();
   employeeLastName = employee.getEmployeeLastName();
   employeePhone = employee.getEmployeePhone();
   employeeJobTitle = employee.getEmployeeJobTitle();
 }
   
//   public PetStoreEmployee(Employee employee, PetStore petStore) {
//     this(employee);
     //this.petStore = petStore;
     //petStoreId = petStore.getPetStoreId();

   

} // pet store employee class 
