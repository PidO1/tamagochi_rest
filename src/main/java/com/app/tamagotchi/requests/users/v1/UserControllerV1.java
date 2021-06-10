package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.enums.NextStep;
import com.app.tamagotchi.interfaces.Secured;
import com.app.tamagotchi.requests.users.User;
import com.app.tamagotchi.requests.users.UsersService;
import com.app.tamagotchi.response.HttpException;
import com.app.tamagotchi.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/users")
@Slf4j
public class UserControllerV1 {

  @Inject
  private UsersService usersService;
  private final String mediaType = MediaType.APPLICATION_JSON_VALUE;

  @GetMapping(path = "/HelloWorld")
  @Secured(secureStatus = Secured.SecureStatus.PUBLIC)
  public ResponseEntity helloWorld() {
      return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  @PostMapping(value = "/", consumes = mediaType, produces = mediaType)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity createUser(@RequestBody User user) {
    try{
      User createUser = usersService.createUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, createUser, "User registered on the system!",  NextStep.LOGIN.getNextStep());
    }catch (HttpException e){
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @PutMapping(value = "/", consumes = mediaType, produces = mediaType)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity updateUser(@RequestBody User user) {
    try{
      User updatedUser = usersService.updateUser(user);
      return ControllerUtils.responseOf(HttpStatus.OK, updatedUser,"User updated!");
    }catch (HttpException e){
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @GetMapping(value = "/", produces = mediaType)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity allUsers() {
    try{
      List<User> users =  usersService.allUsers();
      return ControllerUtils.responseOf(HttpStatus.OK, users, "Users found!");
    }catch (HttpException e){
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }

  @GetMapping(value = "/{email}", consumes = mediaType, produces = mediaType)
  @Secured(secureStatus = Secured.SecureStatus.PRIVATE)
  public ResponseEntity findUserById(@PathVariable(name = "email", required = true) String email) {
    try {
      User users = usersService.findUserByEmail(email);
      if (users == null) throw new HttpException(HttpStatus.NOT_FOUND, "User not found!");
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (HttpException e) {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(e.getHttpStatus(), e.getErrorMessage(), NextStep.REDO.getNextStep());
    }
  }
}
