package com.app.tamagotchi.requests.pets;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public interface PetsDAO extends JpaRepository<Pet, Long> 
{

  // These methods map to JPA and thus require specific names
  // See https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

  Pet findPetById(Long id);
  Pet saveAndFlush(Pet pet);
}
