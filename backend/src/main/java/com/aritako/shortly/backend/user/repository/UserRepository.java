package com.aritako.shortly.backend.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
