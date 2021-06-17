package com.app.tamagotchi.requests.pets;


import com.app.tamagotchi.requests.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "pets")
@Data
public class Pet 
{

  // This class describes an object (in Java) which is mapped to a table in the database

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates IDs when an object is created
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinTable(name="users",
//          joinColumns={@JoinColumn(name="user.id")},
//          inverseJoinColumns={@JoinColumn(name="pet.id")})
  @JoinColumn(name="user_id", nullable=false)
  @Fetch(FetchMode.JOIN)
  private User owner;

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

  @Transient
  @JsonProperty("owner_id")
  private Long ownerId;

  public static Pet validatePet(Pet pet) throws Exception
  {
    if (pet == null) throw new Exception("Invalid pet data provided");
    if (pet.getId() != null) pet.setId(null);
    if (pet.getDeleted() != null) pet.setDeleted(null);
    if (pet.getLastFed() != null) pet.setLastFed(null);
    if (pet.getLastPlayed() != null) pet.setLastPlayed(null);
    if (pet.getLastDressed() != null) pet.setLastDressed(null);
    if (pet.getOwner() != null) pet.setOwner(null);

    Boolean valid = false;
    if (pet.getName() != null) valid = true;
    if (pet.getHat() != null) valid = true;
    if (pet.getShirt() != null) valid = true;
    if (pet.getPants() != null) valid = true;
    if (pet.getShoes() != null) valid = true;
    if (pet.getOwnerId() != null ) valid = true;
    if (!valid) throw new Exception("Invalid pet data provided");

    return pet;
  }
}
