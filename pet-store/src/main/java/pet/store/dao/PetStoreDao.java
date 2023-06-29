package pet.store.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.PetStore;


public interface PetStoreDao extends JpaRepository<PetStore, Long> {

PetStore findByPetStoreId(Long petStoreId);
  
  
} // main class 
