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
  public void createUser(Users user) {
    try {
      Users exsistingUser = findUserByEmail(user.getEmail());
      if (exsistingUser != null)
        throw new Exception("Duplicate User Warning! That email is already registered on the system");
      dao.saveAndFlush(user);
    } catch (Exception e) {
      throw new Exception(e);
    }

  }

  @SneakyThrows
  public Users updateUser(Users user) {
    try {
      if (user == null || user.getId() == null || findUserById(user.getId()) == null)
        throw new Exception("Unable to perform action! Bad data.");
      dao.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
      return findUserById(user.getId());
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @SneakyThrows
  public List<Users> allUsers() {
    try {
      return dao.findAll();
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @SneakyThrows
  public Users findUserById(Long userId) {
    try {
      Users user = dao.findUsersById(userId);
      return user;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @SneakyThrows
  public Users findUserByEmail(String email) {
    try {
      Users user = dao.findUserByEmail(email);
      return user;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

}
