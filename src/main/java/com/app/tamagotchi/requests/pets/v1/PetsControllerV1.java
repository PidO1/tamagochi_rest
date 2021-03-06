package com.app.tamagotchi.requests.pets.v1;

import com.app.tamagotchi.requests.auth0.AuthController;
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
import com.app.tamagotchi.utils.GenericUtility;
import org.springframework.web.context.request.RequestContextHolder;
import io.sentry.Sentry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/pets")
@Slf4j
@Api(tags = "Pets", description = "These endpoints are used to manage the Pet's details.")
public class PetsControllerV1 extends AuthController
{

  // This class maps all the endpoints for V1 of the Tamagotchi Pets to the
  // PetService

  @Inject
  private PetsService petsService;

  @GetMapping(value = "/")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Retrieves all pets")
  public ResponseEntity getAllPets()
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      List<Pet> pets = petsService.getAllPets();
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pets found");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @GetMapping(value = "/{id}")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Retrieves a pet based on the given Id")
  public ResponseEntity getPetById(@PathVariable(name = "id", required = true) Long petId) 
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      Pet pet = petsService.getPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, pet, "Pet " + petId.toString() + " found");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Creates a pet")
  public ResponseEntity createPet(@RequestBody Pet pet)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      Pet createdPet = petsService.createPet(pet);
      return ControllerUtils.responseOf(HttpStatus.CREATED, createdPet, "Pet " + createdPet.getId().toString() + " created");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Changes a pet based on the provided Id")
  public ResponseEntity changePetById(@PathVariable(name = "id", required = true) Long petId, @RequestBody Pet pet)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      Pet changedPet = petsService.changePetById(petId, pet);
      return ControllerUtils.responseOf(HttpStatus.OK, changedPet, "Pet " + petId.toString() + " changed");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Updates a pet based on the provided id")
  public ResponseEntity updatePetById(@PathVariable(name = "id", required = true) Long petId, @RequestBody Pet pet)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      Pet changedPet = petsService.updatePetById(petId, pet);
      return ControllerUtils.responseOf(HttpStatus.OK, changedPet, "Pet " + petId.toString() + " updated");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @DeleteMapping(value = "/{id}")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Deletes a pet based on the given Id")
  public ResponseEntity changePetById(@PathVariable(name = "id", required = true) Long petId)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      petsService.deletePetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, "Pet " + petId.toString() + " deleted");
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PutMapping(value = "/{id}/play")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Plays with a pet with the provided id")
  public ResponseEntity playWithPetById(@PathVariable(name = "id", required = true) Long petId)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      petsService.playWithPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, "Played with Pet " + petId.toString());
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }

  @PutMapping(value = "/{id}/feed")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Feeds the pet with the provided id")
  public ResponseEntity feedPetById(@PathVariable(name = "id", required = true) Long petId)
  {
    try
    {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      petsService.feedPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, "Fed Pet " + petId.toString());
    }
    catch (HttpException e)
    {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }
}
