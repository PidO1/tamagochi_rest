package com.app.tamagotchi.requests.users;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UsersService {

  @Inject
  private UsersDAO dao;

  @SneakyThrows
  public List<Users> allUsers() {
    return dao.findAll();
  }

  @SneakyThrows
  public Users findUserById(Long userId) {
    return dao.findUsersById(userId);
  }

  @SneakyThrows
  public Users findUserByEmail(String email) {
    return dao.findUserByEmail(email);
  }

}
