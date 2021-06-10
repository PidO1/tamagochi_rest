package com.app.tamagotchi.requests.pets;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.List;

@Service
public class PetService 
{
  @Inject
  private PetDAO dao;

  @SneakyThrows
  public List<Pet> allPets() 
  {
    return dao.findAll();
  }

  @SneakyThrows
  public Pet findPetById(Long petId) 
  {
    Pet pet = dao.findPetById(petId);
    if (pet == null) throw new Exception("Pet not found!");
    return pet;
  }
}
