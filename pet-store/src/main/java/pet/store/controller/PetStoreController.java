package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

  private final PetStoreService petStoreService;
  
@Autowired
public PetStoreController(PetStoreService petStoreService) {
 this.petStoreService = petStoreService; 
}

  
  @PostMapping("/pet_store")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PetStoreData savePetStore(@RequestBody PetStoreData petStoreData) {
    log.info("Request received: {}", petStoreData);
    petStoreService.savePetStore(petStoreData);
    return petStoreData;
  }
  
  @PutMapping("/pet_store/{petStoreId}")
  public PetStoreData updatePetStoreData(@PathVariable Long petStoreId,
      @RequestBody PetStoreData petStoreData) {
    petStoreData.setPetStoreId(petStoreId);
    log.info("Updating Pet Store {}", petStoreData);
    return petStoreService.savePetStore(petStoreData); 
  }

  
  
  @PostMapping("/pet_store/{petStoreId}/employee")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PetStoreEmployee addPetStoreEmployee (
      @PathVariable Long petStoreId, 
      @RequestBody PetStoreEmployee petStoreEmployee) {
    log.info("Request received to add employee. Pet Store ID: {}, Employee: {}",petStoreId, petStoreEmployee);

    return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
   
  }
  
  
  
  
  
} // main class 
