package com.app.tamagotchi.requests.users.v1;


import com.app.tamagotchi.requests.users.Users;
import com.app.tamagotchi.requests.users.UsersService;
import com.app.tamagotchi.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      return ControllerUtils.responseOf(HttpStatus.OK, "Hello World!");
  }

  @GetMapping(value = "/all")
  public ResponseEntity allUsers() {
    try{
      List<Users> users =   usersService.allUsers();
      return ControllerUtils.responseOf(HttpStatus.OK, users, "Users found!");
    }catch (Exception e){
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "No Users available.");
    }
  }

  @GetMapping(value = "/id/{id}")
  public ResponseEntity findUserById(
      @PathVariable(name = "id", required = true) Long userId) {
    try {
      Users users = usersService.findUserById(userId);
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "User not found!");
    }
  }

  @GetMapping( value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity findUserById(@RequestBody Users user) {
    try {
      Users users = (Users) usersService.findUserByEmail(user.getEmail());
      return ControllerUtils.responseOf(HttpStatus.OK, users, "User found!");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ControllerUtils.responseOf(HttpStatus.NOT_FOUND, "User not found!");
    }
  }



}
