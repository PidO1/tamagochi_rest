package com.app.tamagotchi.requests.pets;


import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.persistence.*;


@Entity
@Table(name = "pets")
@Data
public class Pet 
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "last_fed")
  private LocalDateTime last_fed;

  @Column(name = "last_played")
  private LocalDateTime last_played;

  @Column(name = "last_dressed")
  private LocalDateTime last_dressed;

  // TODO: Clothes
}