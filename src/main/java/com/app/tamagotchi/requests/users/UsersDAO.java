package com.app.tamagotchi.requests.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UsersDAO extends JpaRepository<User, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE User SET firstName = :firstName, lastName = :lastName, email = :email WHERE id = :id")
  void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("id")Long id);

  User findUserById(Long id);

  User findUserByEmail(String email);
}
