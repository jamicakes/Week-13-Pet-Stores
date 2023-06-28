package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Long employeeId;
  
  private String employeeFirstName;
  private String employeeLastName;
  private String employeePhone;
  private String employeeJobTitle;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "pet_store_id")
  private PetStore petStore;

  public void setEmployeePetStoreId(Long petStoreId) {
    // TODO Auto-generated method stub
    
  }

}