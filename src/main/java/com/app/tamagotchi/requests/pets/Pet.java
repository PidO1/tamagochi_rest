package com.app.tamagotchi.requests.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pets")
@Data
@ApiModel(description = "Details about the pet")
public class Pet {

  // This class describes an object (in Java) which is mapped to a table in the
  // database

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates IDs when an object is created
  @Column(name = "id")
  private Long id;

  @Column(name = "deleted")
  @JsonIgnore
  private Boolean deleted;

  @Column(name = "name")
  private String name;

  @Column(name = "last_fed")
  private Date lastFed;

  @Column(name = "last_played")
  private Date lastPlayed;

  @Column(name = "last_dressed")
  private Date lastDressed;

  // TODO: Clothes
}