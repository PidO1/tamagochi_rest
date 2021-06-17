package com.app.tamagotchi.requests.users.v1;

import com.app.tamagotchi.enums.NextStep;
import com.app.tamagotchi.interfaces.Secured;
import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.model.UserProfile;
import com.app.tamagotchi.requests.auth0.AuthController;
import com.app.tamagotchi.requests.users.User;
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
<<<<<<< HEAD
import io.sentry.Sentry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import java.util.List;
=======
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import java.util.List;
import java.lang.Exception;
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b

@RestController
@RequestMapping("tamagotchi/v1/users")
@Slf4j
<<<<<<< HEAD
@Api(tags = "Users", description = "These endpoints are used to manage the user details")
=======
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
public class UserControllerV1 extends AuthController {

  @Inject
  private UsersService usersService;

  @GetMapping(path = "/HelloWorld")
  @Secured(secureStatus = Secured.SecureStatus.PUBLIC)
  public ResponseEntity helloWorld() {
    return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  //CREATE
  @PostMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Creates a new user")
  public ResponseEntity createUser(@RequestBody User user) {
    try {
      User createUser = usersService.createUser(user);
<<<<<<< HEAD
      return ControllerUtils.responseOf(HttpStatus.OK, createUser, "User registered on the system!",
          NextStep.LOGIN.getNextStep());
=======
      return ControllerUtils.responseOf(HttpStatus.OK, createUser, "User registered on the system!", NextStep.VERIFY_EMAIL.getNextStep());
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //LOGIN
  @PostMapping(value = "/login", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Allows a user to login")
  public ResponseEntity login(@RequestBody User user) {
    try {
      AccessToken accessToken = usersService.login(user);
<<<<<<< HEAD
      return ControllerUtils.responseOf(HttpStatus.OK, accessToken, "Login successful",
          NextStep.CREATE_PET.getNextStep());
    } catch (HttpException e) {
      Sentry.captureException(e);
=======
      return ControllerUtils.responseOf(HttpStatus.OK, accessToken, "Login successful", NextStep.CREATE_PET.getNextStep());
    } catch (HttpException e) {
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //UPDATE
  @PutMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  @ApiOperation(value = "Updates a given user")
  public ResponseEntity updateUser(@RequestBody User user) {
    try {
<<<<<<< HEAD
      verifyToken(user.getAccessToken());
      User updatedUser = usersService.updateUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, updatedUser, "User updated!");
    } catch (HttpException e) {
      Sentry.captureException(e);
      log.error(e.getErrorMessage(), e);
=======
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      User updatedUser = usersService.updateUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, updatedUser, "User updated!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  //ALL USERS
  @GetMapping(value = "/", produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
<<<<<<< HEAD
  @ApiOperation(value = "Retrieves all users")
  public ResponseEntity allUsers(@PathVariable(name = "access_token", required = true) String accessToken) {
    try {
      verifyToken(accessToken);
=======
  public ResponseEntity allUsers() {
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
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
<<<<<<< HEAD
  @ApiOperation(value = "Retrieves a user based on the provided email address")
  public ResponseEntity findUserById(@PathVariable(name = "email", required = true) String email,
      @PathVariable(name = "access_token", required = true) String accessToken) {
=======
  public ResponseEntity findUserById(@PathVariable(name = "email", required = true) String email) {
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
    try {
      verifyToken(GenericUtility.getToken(RequestContextHolder.getRequestAttributes()));
      User users = usersService.findUserByEmail(email);
      if (users == null)
        throw new HttpException(HttpStatus.NOT_FOUND, "User not found!");
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (HttpException e) {
      log.error(e.getErrorMessage(), e);
      Sentry.captureException(e);
<<<<<<< HEAD
      log.error(e.getErrorMessage(), e);
=======
>>>>>>> 93baba0bf53a96672e2e5ec5fb593786decf412b
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }
}
