package com.app.tamagotchi.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@ApiModel(description = "Details about the User")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonProperty("first_name")
  @Column(name = "first_name")
  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  @Column(name = "last_name")
  @JsonProperty("last_name")
  private String lastName;

  @Column(name = "email")
  @JsonProperty("Email")
  private String email;

  @Transient
  @JsonProperty("password")
  private String password;

  @Transient
  @JsonProperty("access_token")
  private String accessToken;
}