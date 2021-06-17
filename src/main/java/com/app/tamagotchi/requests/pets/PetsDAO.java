package com.app.tamagotchi.requests.pets;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PetsDAO extends JpaRepository<Pet, Long> 
{

  // These methods map to JPA and thus require specific names
  // See https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

  Pet findPetById(Long id);
  Pet saveAndFlush(Pet pet);

  @Query(value = "SELECT DISTINCT p.*, u.id, u.first_name, u.last_name, u.email from Pets p JOIN users u on u.id = p.owner_id where u.id= :id", nativeQuery = true)
  List<Pet> findPetsTest(@Param("id") Long id);
}