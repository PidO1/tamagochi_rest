package com.app.tamagotchi.requests.users;

import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.requests.auth0.AuthController;
import com.app.tamagotchi.response.HttpException;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UsersService {

  @Inject
  private UsersDAO dao;

  public User createUser(User user) throws HttpException {
    try {
      User exsistingUser = findUserByEmail(user.getEmail());
      if (exsistingUser != null)
        throw new Exception("Duplicate User Warning! That email is already registered on the system");

      //TODO: FIX THIS? --- BRAD
      Response response = AuthController.instance().signUp(user);
      dao.saveAndFlush(user);
      return findUserByEmail(user.getEmail());
    } catch (Exception e) {
      throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }

  public User updateUser(User user) throws HttpException {
    try {
      if (user == null || user.getId() == null || findUserById(user.getId()) == null)
        throw new Exception("Unable to perform action! Bad data.");
      dao.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
      return findUserById(user.getId());
    } catch (Exception e) {
      throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  public AccessToken login(User user) throws HttpException {
    try {
      User exsistingUser = findUserByEmail(user.getEmail());
      if (exsistingUser == null)
        throw new Exception("Invalid Username or Password.");

      exsistingUser.setPassword(user.getPassword());
      AccessToken accessToken = AuthController.instance().authLogin(exsistingUser);
      return accessToken;
    } catch (Exception e) {
      throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }

  }

  public List<User> allUsers() throws HttpException {
    try {
      return dao.findAll();
    } catch (Exception e) {
      throw new HttpException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  public User findUserById(Long userId) throws HttpException {
    try {
      User user = dao.findUsersById(userId);
      return user;
    } catch (Exception e) {
      throw new HttpException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  public User findUserByEmail(String email) throws HttpException {
    try {
      User user = dao.findUserByEmail(email);
      return user;
    } catch (Exception e) {
      throw new HttpException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
