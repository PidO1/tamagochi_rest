package com.app.tamagotchi.requests.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UsersDAO extends JpaRepository<Users, Long> {

  Users findUsersById(Long id);

  Users findUserByEmail(String email);
}
