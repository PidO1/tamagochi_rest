package com.app.tamagotchi.requests.pets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PetsDAO extends JpaRepository<Pets, Long> {

  Pets findPetById(Long id);
}
