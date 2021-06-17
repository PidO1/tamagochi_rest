package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.enums.NextStep;
import com.app.tamagotchi.interfaces.Secured;
import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.requests.auth0.AuthController;
import com.app.tamagotchi.requests.users.User;
import com.app.tamagotchi.requests.pets.Pet;
import com.app.tamagotchi.requests.pets.PetsService;
import com.app.tamagotchi.requests.users.UsersService;
import com.app.tamagotchi.response.HttpException;
import com.app.tamagotchi.utils.Constants;
import com.app.tamagotchi.utils.ControllerUtils;
import com.app.tamagotchi.utils.GenericUtility;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/users")
@Slf4j
public class UserControllerV1 extends AuthController {

  @Inject
  private UsersService usersService;

  @Inject
  private PetsService petsService;

  @GetMapping(path = "/HelloWorld")
  @Secured(secureStatus = Secured.SecureStatus.PUBLIC)
  public ResponseEntity helloWorld() {
    return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  //CREATE
  @PostMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity createUser(@RequestBody User user) {
    try {
      User createUser = usersService.createUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, createUser, "User registered on the system!", NextStep.VERIFY_EMAIL.getNextStep());
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //LOGIN
  @PostMapping(value = "/login", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity login(@RequestBody User user) {
    try {
      AccessToken accessToken = usersService.login(user);
      return ControllerUtils.responseOf(HttpStatus.OK, accessToken, "Login successful", NextStep.CREATE_PET.getNextStep());
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //UPDATE
  @PutMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity updateUser(@RequestBody User user) {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      User updatedUser = usersService.updateUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, updatedUser, "User updated!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //ALL USERS
  @GetMapping(value = "/", produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity allUsers() {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      List<User> users = usersService.allUsers();
      return ControllerUtils.responseOf(HttpStatus.OK, users, "Users found!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //USER BY EMAILS
  @GetMapping(value = "/{email}", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity findUserByEmail(@PathVariable(name = "email", required = true) String email) {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      User users = usersService.findUserByEmail(email);
      if (users == null) throw new HttpException(HttpStatus.NOT_FOUND, "User not found!");
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //USER BY ID
  @GetMapping(value = "/id/{id}", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity findUserById(@PathVariable(name = "id", required = true) long id) {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      User users = usersService.findUserById(id);
      if (users == null) throw new HttpException(HttpStatus.NOT_FOUND, "User not found!");
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //PETS BY USER
  @GetMapping(value = "/{id}/pets")
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity getPetsByOwnerId(@PathVariable(name = "id", required = true) Long ownerId) {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      List<Pet> pets = petsService.getPetsByOwnerId(ownerId);
      return ControllerUtils.responseOf(HttpStatus.OK, pets, "Pets for owner " + ownerId.toString() + " found");
    } catch (HttpException e) {
      Sentry.captureException(e);
      log.error(e.getErrorMessage());
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage());
    }
  }
}
