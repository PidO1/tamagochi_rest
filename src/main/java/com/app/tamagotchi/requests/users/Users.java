package com.app.tamagotchi.requests.users;


import lombok.Data;

//@Entity
//@Table(name = "users")
@Data
public class Users {

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "user_id")
  private Long id;

//  @Column(name = "first_name")
  private String firstName;

//  @Column(name = "last_name")
  private String lastName;

//  @Column(name = "email")
  private String email;

//  @Column(name = "active")
  private Boolean active;
}