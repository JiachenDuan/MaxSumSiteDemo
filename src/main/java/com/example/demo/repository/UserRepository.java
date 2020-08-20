package com.example.demo.repository;

import com.example.demo.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

  @Query("SELECT u FROM AuthUser u where u.userName = ?1")
  AuthUser findByUserName(String userName);
}
