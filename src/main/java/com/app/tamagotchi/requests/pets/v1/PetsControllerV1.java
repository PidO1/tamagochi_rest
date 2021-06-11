package com.app.tamagotchi.requests.pets.v1;


import com.app.tamagotchi.interfaces.Secured;
import com.app.tamagotchi.requests.pets.Pet;
import com.app.tamagotchi.requests.pets.PetsService;
import com.app.tamagotchi.response.HttpException;
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

  // This class maps all the endpoints for V1 of the Tamagotchi Pets to the PetService

  @Inject
  private PetsService petService;
  private final String JSONObject = MediaType.APPLICATION_JSON_VALUE;

  @GetMapping(value = "/", produces = JSONObject)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity getAllPets() 
  {
    try 
    {
      List<Pet> pets = petService.getAllPets();
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pets found");
    } 
    catch (HttpException e)
    {
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @GetMapping(value = "/{id}", produces = JSONObject)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity getPetById(@PathVariable(name = "id", required = true) Long petId) 
  {
    try 
    {
      Pet pet = petService.getPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, pet, "Pet " + petId.toString() + " found");
    } 
    catch (HttpException e)
    {
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PostMapping(value = "/", consumes = JSONObject, produces = JSONObject)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity createPet(@RequestBody Pet pet)
  {
    try 
    {
      Pet createdPet = petService.createPet(pet);
      return ControllerUtils.responseOf(HttpStatus.CREATED, createdPet, "Pet " + createdPet.getId().toString() + " created");
    }
    catch (HttpException e)
    {
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PutMapping(value = "/{id}", consumes = JSONObject, produces = JSONObject)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity changePetById(@PathVariable(name = "id", required = true) Long petId, @RequestBody Pet pet) 
  {
    try 
    {
      Pet changedPet = petService.changePetById(petId, pet);
      return ControllerUtils.responseOf(HttpStatus.OK, changedPet, "Pet " + petId.toString() + " changed");
    } 
    catch (HttpException e)
    {
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @DeleteMapping(value = "/{id}", produces = JSONObject)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity changePetById(@PathVariable(name = "id", required = true) Long petId) 
  {
    try 
    {
      petService.deletePetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, "Pet " + petId.toString() + " deleted");
    } 
    catch (HttpException e)
    {
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }
}
