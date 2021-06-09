package com.app.tamagotchi.requests.users;

import lombok.RequiredArgsConstructor;
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
    Users user = dao.findUsersById(userId);
    if (user == null) throw new Exception("User not found!");
    return user;
  }

  @SneakyThrows
  public Users findUserByEmail(String email) {
    Users user = dao.findUserByEmail(email);
    if (user == null) throw new Exception("User not found!");
    return user;
  }
}
