package com.aritako.shortly.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
  
}
