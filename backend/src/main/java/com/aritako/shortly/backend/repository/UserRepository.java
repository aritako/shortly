package com.aritako.shortly.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
