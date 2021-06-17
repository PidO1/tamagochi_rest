package com.app.tamagotchi.requests.pets;


import com.app.tamagotchi.requests.users.User;
import com.app.tamagotchi.response.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tamagotchi.requests.users.UsersService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;


@Service
public class PetsService 
{

  // This class maps the database to a usable interface with processing

  @Inject
  private PetsDAO dao;

  @Inject
  private UsersService usersService;

  public List<Pet> getAllPets() throws HttpException
  {
    List<Pet> pets = dao.findAll();
    pets.removeIf(elem -> elem.getDeleted());
    if (pets.size() == 0) throw new HttpException(HttpStatus.NOT_FOUND, "No pets found");
    return pets;
  }

  public Pet getPetById(Long petId) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");
    return pet;
  }

  public Pet createPet(Pet pet) throws HttpException
  {
    try { pet = Pet.validatePet(pet); }
    catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, "Not a valid pet"); }
    if (pet.getName() == null) throw new HttpException(HttpStatus.BAD_REQUEST, "Give the poor pet a name");
    if (pet.getOwnerId() == null) throw new HttpException(HttpStatus.BAD_REQUEST, "A pet must have an owner");
    try { usersService.findUserById(pet.getOwnerId()); }
    catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, "User with id "+ pet.getOwnerId() +" does not exist"); }

    pet.setDeleted(false);
    if (pet.getHat() != null || pet.getShirt() != null || pet.getPants() != null || pet.getShoes() != null) pet.setLastDressed(new Date());
    Pet createdPet = dao.saveAndFlush(pet);
    if (createdPet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet could not be created");
    return createdPet;
  }

  public Pet changePetById(Long petId, Pet changedPet) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");

    try { changedPet = Pet.validatePet(changedPet); }
    catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, e.getMessage()); }
    if (changedPet.getName() == null) throw new HttpException(HttpStatus.BAD_REQUEST, "Give the poor pet a name");
    if (changedPet.getOwnerId() == null) throw new HttpException(HttpStatus.BAD_REQUEST, "A pet must have an owner");
    try { usersService.findUserById(changedPet.getOwnerId()); }
    catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, "User with id "+ changedPet.getOwnerId() +" does not exist"); }

    changedPet.setId(petId);
    changedPet.setDeleted(false);
    if (changedPet.getHat() != null || changedPet.getShirt() != null || changedPet.getPants() != null || changedPet.getShoes() != null) changedPet.setLastDressed(new Date());
    changedPet = dao.saveAndFlush(changedPet);
    if (changedPet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be changed");
    return changedPet;
  }

  public Pet updatePetById(Long petId, Pet updatedPet) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");

    try { updatedPet = Pet.validatePet(updatedPet); }
    catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, e.getMessage()); }

    if (updatedPet.getHat() != null || updatedPet.getShirt() != null || updatedPet.getPants() != null || updatedPet.getShoes() != null) pet.setLastDressed(new Date());
    if (updatedPet.getName() != null) pet.setName(updatedPet.getName());
    if (updatedPet.getHat() != null) pet.setHat(updatedPet.getHat());
    if (updatedPet.getShirt() != null) pet.setShirt(updatedPet.getShirt());
    if (updatedPet.getPants() != null) pet.setPants(updatedPet.getPants());
    if (updatedPet.getShoes() != null) pet.setShoes(updatedPet.getShoes());
    if (updatedPet.getOwnerId() != null)
    {
      try
      { 
        usersService.findUserById(updatedPet.getOwnerId());
        pet.setOwnerId(updatedPet.getOwnerId());
      }
      catch (Exception e) { throw new HttpException(HttpStatus.BAD_REQUEST, "User with id "+ updatedPet.getOwnerId() +" does not exist"); }
    }

    updatedPet = dao.saveAndFlush(pet);
    if (updatedPet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be updated");
    return updatedPet;
  }

  public void deletePetById(Long petId) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) return; // For idempotency

    pet.setDeleted(true);
    pet = dao.saveAndFlush(pet);
    if (pet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be deleted");
  }

  public void playWithPetById(Long petId) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");

    pet.setLastPlayed(new Date());
    pet = dao.saveAndFlush(pet);
    if (pet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be changed");
  }

  public void feedPetById(Long petId) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");

    pet.setLastFed(new Date());
    pet = dao.saveAndFlush(pet);
    if (pet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be changed");
  }

  public List<Pet> getPetsByOwnerId(Long ownerId) throws HttpException
  {
    List<Pet> pets = dao.findAll();
    pets.removeIf(elem -> elem.getDeleted() || elem.getOwnerId() != ownerId);
    if (pets.size() == 0) throw new HttpException(HttpStatus.NOT_FOUND, "No pets found");
    return pets;
  }
}
