package com.app.tamagotchi.requests.pets;


import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import com.app.tamagotchi.response.HttpException;
import org.springframework.http.HttpStatus;


@Service
public class PetsService 
{

  // This class maps the database to a usable interface with processing

  @Inject
  private PetsDAO dao;

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
    // TODO: validation of pet data
    pet.setDeleted(false);
    Pet createdPet = dao.saveAndFlush(pet);
    if (createdPet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet could not be created");
    return createdPet;
  }

  public Pet changePetById(Long petId, Pet changedPet) throws HttpException
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null || pet.getDeleted()) throw new HttpException(HttpStatus.NOT_FOUND, "Pet " + petId.toString() + " not found");

    changedPet.setId(petId);
    changedPet.setDeleted(false);

    // TODO: validation of pet data
    changedPet = dao.saveAndFlush(changedPet);
    if (changedPet == null) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Pet " + petId.toString() + " could not be changed");
    return changedPet;
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
}
