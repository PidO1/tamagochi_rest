package com.app.tamagotchi.requests.pets.v1;


import com.app.tamagotchi.requests.pets.Pet;
import com.app.tamagotchi.requests.pets.PetService;
import com.app.tamagotchi.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/pets")
@Slf4j
public class PetsControllerV1 
{
  @Inject
  private PetService petService;

  @GetMapping(value = "/")
  public ResponseEntity allPets() 
  {
    try 
    {
      List<Pet> pets = petService.allPets();
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pets loaded");
    } 
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "Unable to load pets");
    }
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity findPetById(@PathVariable(name = "id", required = true) Long petId) 
  {
    try 
    {
      Pet pet = petService.findPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, pet, "Pet " + petId.toString() + " found");
    } 
    catch (Exception e) 
    {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");
    }
  }

  @PostMapping(value = "/")
  public ResponseEntity createPet(@RequestBody Pet pet)
  {
    try 
    {
      Pet createdPet = petService.saveAndFlush(pet);
      return ControllerUtils.responseOf(HttpStatus.CREATED, createdPet, "Pet created");
    }
    catch (Exception e) 
    {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.INTERNAL_SERVER_ERROR, "Pet could not be created");
    }
  }
}
