package com.app.tamagotchi.utils;

import com.app.tamagotchi.requests.users.User;
import org.springframework.beans.factory.annotation.Value;

public class Constants {

  public static final String JSON_VALUE = "application/json";
  public static final String URLENCODED_VALUE = "application/x-www-form-urlencoded";
  private static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String URL = "https://dev-jxp7hcj8.eu.auth0.com/";
  public static final String AUDIENCE = "https://tamagotchi/";
  public static final String DOMAIN = "dev-jxp7hcj8.eu.auth0.com";
  public static final String CLIENT_ID = "7BxOnfduCO4hdXXlI54D9hlrJfCIuVOi";
  public static final String CLIENT_SECRET = "XA8_5v6V9G7pc7sUouifsGJKXr8CAJ5T565lYqu4YoS2XunnH9pS0BpStX3E2oe_";
  public static final String CONNTECTION = "tamagotchi-auth0-db";
  public static final String GRANT_TYPE = "password";

  public static final String ADMIN_CLIENT_ID = "jd9osgW1GeUhwJg7tKOdg197Guk0GgM0";
  public static final String ADMIN_CLIENT_SECRET = "9ZVA9NCSst6UpsM9mkiPQpM_1wx6NKoIR53XpNClpPrKRTLGCviWvzAYwMcjB_1J";
  public static final String ADMIN_AUDIENCE = "https://dev-jxp7hcj8.eu.auth0.com/api/v2/";


  public static String loginBuilder(User user, boolean adminToken) {
    StringBuilder str
            = new StringBuilder();
    if (!adminToken) {
      str.append("grant_type=" + Constants.GRANT_TYPE);
      str.append("&client_id=" + Constants.CLIENT_ID);
      str.append("&client_secret=" + Constants.CLIENT_SECRET);
      str.append("&audience=" + Constants.AUDIENCE);
      str.append("&username=" + user.getEmail());
      str.append("&password=" + user.getPassword());
      str.append("&scope=openid");
    } else {
      str.append("grant_type=client_credentials");
      str.append("&client_id=" + Constants.ADMIN_CLIENT_ID);
      str.append("&client_secret=" + Constants.ADMIN_CLIENT_SECRET);
      str.append("&audience=" + Constants.ADMIN_AUDIENCE);
    }


    return str.toString();
  }
}