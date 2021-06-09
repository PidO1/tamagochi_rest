package com.app.tamagotchi.requests.pets;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.List;

@Service
public class PetsService {

  @Inject
  private PetsDAO dao;

  @SneakyThrows
  public List<Pets> allPets() {
    return dao.findAll();
  }

  @SneakyThrows
  public Pets findPetById(Long petId) {
    Pets pet = dao.findPetById(petId);
    if (pet == null) throw new Exception("Pet not found!");
    return pet;
  }
}
