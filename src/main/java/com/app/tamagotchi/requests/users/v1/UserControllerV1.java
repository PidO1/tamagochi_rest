package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.enums.NextStep;
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
import io.swagger.annotations.*;
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
@Api(tags = "Users", description = "These endpoints are used to manage the users.")
public class UserControllerV1 extends AuthController {

  @Inject
  private UsersService usersService;

  //CREATE
  @PostMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @ApiOperation(
          value = "Creates a user",
          notes = "Creating a user will allow for further functionality, such as logging in and creating pets")
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
  @ApiOperation(
          value = "User is able to log into the system",
          notes = "Third Party Authentication - Auth0")
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
  @PatchMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @ApiOperation(value = "Update User information")
  public ResponseEntity updateUser(@RequestBody User user) {
    try {
      String token = GenericUtility.getToken(RequestContextHolder.getRequestAttributes());
      verifyToken(token);
      User updatedUser = usersService.updateUser(user, token);
      return ControllerUtils.responseOf(HttpStatus.OK, "User updated!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //ALL USERS
  @GetMapping(value = "/", produces = Constants.JSON_VALUE)
  @ApiOperation(value = "Provides a list of all users")
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
  @ApiOperation(value = "Provides a user by a given email")
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
