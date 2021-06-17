package com.app.tamagotchi.requests.users;

import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.model.AuthUser;
import com.app.tamagotchi.model.UserProfile;
import com.app.tamagotchi.requests.auth0.AuthController;
import com.app.tamagotchi.response.HttpException;
import com.app.tamagotchi.utils.Constants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

      AuthUser authUser = new AuthUser(Constants.CLIENT_ID, user.getEmail(), user.getPassword(), Constants.CONNTECTION, user.getFirstName(), user.getLastName());
      AuthController.instance().signUp(authUser);
      dao.saveAndFlush(user);
      return findUserByEmail(user.getEmail());
    } catch (Exception e) {
      throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }

  public User updateUser(User user, String token) throws HttpException {
    try {
      if (user == null || user.getId() == null || findUserById(user.getId()) == null)
        throw new Exception("Unable to perform action! Bad data.");

      UserProfile userProfile = AuthController.instance().findUsersProfile(token);
      AccessToken accessToken = AuthController.instance().genToken(null, true);

      // EMAIL UPDATE
      AuthUser authUser = new AuthUser(Constants.ADMIN_CLIENT_ID, Constants.CONNTECTION, user.getFirstName(), user.getLastName());
      authUser.setEmail(user.getEmail());
      AuthController.instance().updateAuth0User(authUser, userProfile, accessToken, "password");

      // PASSWOPRD UPDATE
      authUser = new AuthUser(Constants.ADMIN_CLIENT_ID, Constants.CONNTECTION, user.getFirstName(), user.getLastName());
      authUser.setPassword(user.getPassword());
      AuthController.instance().updateAuth0User(authUser, userProfile, accessToken, "email");

      dao.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
      User updatedUser = findUserById(user.getId());
      return updatedUser;
    } catch (Exception e) {
      throw new HttpException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public AccessToken login(User user) throws HttpException {
    try {
      User exsistingUser = findUserByEmail(user.getEmail());
      if (exsistingUser == null)
        throw new Exception("Invalid Username or Password.");

      exsistingUser.setPassword(user.getPassword());
      AccessToken accessToken = AuthController.instance().genToken(exsistingUser, false);

      if (accessToken != null) {
        UserProfile userProfile = AuthController.instance().findUsersProfile(accessToken.getAccessToken());
        if (Boolean.FALSE.equals(userProfile.getEmailVerified()))
          throw new Exception("Please verify your email.");
      }

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
