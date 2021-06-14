package com.app.tamagotchi.model;

import lombok.Data;

@Data
public class AuthUser {

  private String client_id;
  private String email;
  private String password;
  private String connection;
  private String given_name;
  private String family_name;

  public AuthUser() {
    super();
  }

  public AuthUser(String client_id, String email, String password, String connection, String given_name, String family_name) {
    this.client_id = client_id;
    this.email = email;
    this.password = password;
    this.connection = connection;
    this.given_name = given_name;
    this.family_name = family_name;
  }


}
