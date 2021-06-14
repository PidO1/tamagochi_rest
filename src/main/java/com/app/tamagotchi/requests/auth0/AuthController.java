package com.app.tamagotchi.requests.auth0;

import com.app.tamagotchi.model.AccessToken;
import com.app.tamagotchi.model.AuthUser;
import com.app.tamagotchi.model.UserProfile;
import com.app.tamagotchi.requests.users.User;
import com.app.tamagotchi.requests.users.UsersDAO;
import com.app.tamagotchi.response.HttpException;
import com.app.tamagotchi.utils.Constants;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;


public class AuthController {

  private static AuthController authController = null;
  @Inject
  private UsersDAO dao;

  public static synchronized AuthController instance() {
    if (authController == null) authController = new AuthController();
    return authController;
  }

  public Response signUp(User user) throws Exception {

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse(Constants.JSON_VALUE);

    AuthUser authUser = new AuthUser(Constants.CLIENT_ID, user.getEmail(), user.getPassword(), Constants.CONNTECTION, user.getFirstName(), user.getLastName());
    RequestBody body = RequestBody.create(mediaType, new Gson().toJson(authUser));
    Request request = new Request.Builder()
            .url(Constants.URL + "dbconnections/signup")
            .method("POST", body)
            .addHeader("Content-Type", Constants.JSON_VALUE)
            .build();

    Response response = client.newCall(request).execute();
    if (checkResponseCode(response)) throw new HttpException(HttpStatus.valueOf(response.code()), response.message());
    return response;
  }


  public AccessToken authLogin(User user) throws Exception {
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse(Constants.URLENCODED_VALUE);
    RequestBody body = RequestBody.create(mediaType, Constants.loginBuilder(user));
    Request request = new Request.Builder()
            .url(Constants.URL + "oauth/token")
            .method("POST", body)
            .addHeader("Content-Type", Constants.URLENCODED_VALUE)
            .build();
    Response response = client.newCall(request).execute();
    if (checkResponseCode(response)) throw new Exception(response.message());
    AccessToken accessToken = new Gson().fromJson(response.body().string(), AccessToken.class);
    return accessToken;
  }

  public boolean checkResponseCode(Response response) throws Exception {
    if (response.code() >= 400) return true;
    else return false;
  }


  public void verifyToken(String token) throws HttpException {
    try {

      //TODO: Decrypt JWT
      OkHttpClient client = new OkHttpClient().newBuilder()
              .build();
      Request request = new Request.Builder()
              .url(Constants.URL + "userinfo")
              .method("GET", null)
              .addHeader("Authorization", "Bearer " + token)
              .build();
      Response response = client.newCall(request).execute();
      boolean checkCode = checkResponseCode(response);
      if (!checkCode) {
        UserProfile userProfile = new Gson().fromJson(response.body().string(), UserProfile.class);
        if (userProfile != null) {
          User user = dao.findUserByEmail(userProfile.getEmail());
          if(user == null && !(user.getEmail().matches(userProfile.getEmail()))) throw new Exception();
        }
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not verify JWT token integrity!", e);
    }


  }


}