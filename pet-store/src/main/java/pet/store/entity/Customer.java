package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity

public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long customerId;
  private String customerEmail;
  private String customerFirstName;
  private String customerLastName;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
  private Set<PetStore> petStores = new HashSet<>();
}
