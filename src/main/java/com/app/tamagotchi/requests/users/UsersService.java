package com.app.tamagotchi.requests.users;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {


  @SneakyThrows
  private void create(Users user){
    if (user.getId() == null) ; //dao.save(user);
    else update(user);
  }

  @SneakyThrows
  private void update(Users user){}

  @SneakyThrows
  public void createUser(Users users) {}

  @SneakyThrows
  public Users verifyUser(Users users) {
   return null;
  }

  @SneakyThrows
  public void updateUser(Users users) {}

  @SneakyThrows
  public void activateUser(String email) {}

  @SneakyThrows
  public void deActivateUser(String email) {}

  @SneakyThrows
  public List<Users> allUsers() {
    return null;
    //return dao.findAll();
  }

  @SneakyThrows
  public Users findUserById(Long userId) {
    return null;
  }

  @SneakyThrows
  public Users findUserByEmail(String email) {
    return null;
  }


}
