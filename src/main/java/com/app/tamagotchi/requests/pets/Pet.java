package com.app.tamagotchi.requests.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

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

  @Column(name = "hat")
  private String hat;

  @Column(name = "shirt")
  private String shirt;

  @Column(name = "pants")
  private String pants;

  @Column(name = "shoes")
  private String shoes;

  public static Pet validatePet(Pet pet) throws Exception {
    if (pet == null)
      throw new Exception("Invalid pet data provided");
    if (pet.getId() != null)
      pet.setId(null);
    if (pet.getDeleted() != null)
      pet.setDeleted(null);
    if (pet.getLastFed() != null)
      pet.setLastFed(null);
    if (pet.getLastPlayed() != null)
      pet.setLastPlayed(null);
    if (pet.getLastDressed() != null)
      pet.setLastDressed(null);

    Boolean valid = false;
    if (pet.getName() != null)
      valid = true;
    if (pet.getHat() != null)
      valid = true;
    if (pet.getShirt() != null)
      valid = true;
    if (pet.getPants() != null)
      valid = true;
    if (pet.getShoes() != null)
      valid = true;
    if (!valid)
      throw new Exception("Invalid pet data provided");

    return pet;
  }
}
