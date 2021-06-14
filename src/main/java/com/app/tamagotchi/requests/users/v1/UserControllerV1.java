package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.enums.NextStep;
import com.app.tamagotchi.interfaces.Secured;
import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.requests.auth0.AuthController;
import com.app.tamagotchi.requests.users.User;
import com.app.tamagotchi.requests.users.UsersService;
import com.app.tamagotchi.response.HttpException;
import com.app.tamagotchi.utils.Constants;
import com.app.tamagotchi.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/users")
@Slf4j
public class UserControllerV1 extends AuthController{

  @Inject
  private UsersService usersService;

  @GetMapping(path = "/HelloWorld")
  @Secured(secureStatus = Secured.SecureStatus.PUBLIC)
  public ResponseEntity helloWorld() {
      return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  @PostMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity createUser(@RequestBody User user) {
    try{
      User createUser = usersService.createUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, createUser, "User registered on the system!",  NextStep.LOGIN.getNextStep());
    }catch (HttpException e){
     log.error(e.getErrorMessage(), e);
    //  Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @PostMapping(value = "/login", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity login(@RequestBody User user) {
    try{
      AccessToken accessToken = usersService.login(user);
      return ControllerUtils.responseOf(HttpStatus.OK, accessToken, "Login successful",  NextStep.CREATE_PET.getNextStep());
    }catch (HttpException e){
      // Sentry.captureException(e);
      log.error(e.getErrorMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @PutMapping(value = "/", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity updateUser(@RequestBody User user) {
    try{
      verifyToken(user.getAccessToken());
      User updatedUser = usersService.updateUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, updatedUser,"User updated!");
    }catch (HttpException e){
      // Sentry.captureException(e);
     log.error(e.getErrorMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @GetMapping(value = "/allUsers/{access_token}", produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity allUsers(@PathVariable(name = "access_token", required = true) String accessToken) {
    try{
      verifyToken(accessToken);
      List<User> users =  usersService.allUsers();
      return ControllerUtils.responseOf(HttpStatus.OK, users, "Users found!");
    }catch (HttpException e){
     log.error(e.getErrorMessage(), e);
    //  Sentry.captureException(e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @GetMapping(value = "/{email}/{access_token}", consumes = Constants.JSON_VALUE, produces = Constants.JSON_VALUE)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity findUserById(@PathVariable(name = "email", required = true) String email,
                                     @PathVariable(name = "access_token", required = true) String accessToken) {
    try {
      verifyToken(accessToken);
      User users = usersService.findUserByEmail(email);
      if (users == null) throw new HttpException(HttpStatus.NOT_FOUND, "User not found!");
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (HttpException e) {
      // Sentry.captureException(e);
     log.error(e.getErrorMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }
}
