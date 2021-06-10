package com.app.tamagotchi.requests.pets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PetDAO extends JpaRepository<Pet, Long> 
{
  Pet findPetById(Long id);
}
