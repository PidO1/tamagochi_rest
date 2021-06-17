package com.app.tamagotchi.requests.users;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonProperty("first_name")
  @Column(name = "first_name")
  private String firstName;

  @JsonProperty("last_name")
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Transient
  @JsonProperty("password")
  private String password;
}