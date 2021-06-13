package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.exceptions.ApiRequestException;
import com.app.tamagotchi.requests.users.Users;
import com.app.tamagotchi.requests.users.UsersService;
import com.app.tamagotchi.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.Exception;
import io.sentry.Sentry;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("tamagotchi/v1/user")
@Slf4j
public class UserControllerV1 {

  @Inject
  private UsersService usersService;

  @GetMapping(path = "/HelloWorld")
  public ResponseEntity helloWorld() {
    try {
      return new ResponseEntity<String>("hello world!", HttpStatus.OK);

    } catch (ApiRequestException e) {
      Sentry.captureException(e);
      throw new ApiRequestException(e.getMessage(), e, e.getHttpStatus());
      
    }
  }

  @GetMapping(value = "/all")
  public ResponseEntity allUsers() {
    List<Users> users =   usersService.allUsers();
    return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
  }

  @GetMapping(value = "/id/{id}")
  public ResponseEntity findUserById(
      @PathVariable(name = "id", required = true) Long userId) {
    try {
      Users users = (Users) usersService.findUserById(userId);
      return new ResponseEntity<Users>(users, HttpStatus.OK);
    } catch (ApiRequestException e) {
      Sentry.captureException(e);
      throw new ApiRequestException(e.getMessage(), e, e.getHttpStatus());
      
    }
  }

  @GetMapping(value = "/email/{email}")
  public ResponseEntity findUserById(
      @PathVariable(name = "email", required = true) String email) {
    try {
      Users users = (Users) usersService.findUserByEmail(email);
      return new ResponseEntity<Users>(users, HttpStatus.OK);
    } catch (ApiRequestException e) {
      Sentry.captureException(e);
      throw new ApiRequestException(e.getMessage(), e, e.getHttpStatus());
      
    }
  }



}
