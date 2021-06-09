package com.app.tamagotchi.requests.pets.v1;


import com.app.tamagotchi.requests.pets.Pets;
import com.app.tamagotchi.requests.pets.PetsService;
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
public class PetsControllerV1 {

  @Inject
  private PetsService petsService;

  @GetMapping(path = "/HelloWorld")
  public ResponseEntity helloWorld() {
      return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  @GetMapping(value = "/all")
  public ResponseEntity allPets() {
    try {
      List<Pets> pets = petsService.allPets();
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pets found!");
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "No Pets available.");
    }
  }

  @GetMapping(value = "/id/{id}")
  public ResponseEntity findPetById(
      @PathVariable(name = "id", required = true) Long petId) {
    try {
      Pets pets = petsService.findPetById(petId);
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pet found!");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "Pet not found!");
    }
  }
}
