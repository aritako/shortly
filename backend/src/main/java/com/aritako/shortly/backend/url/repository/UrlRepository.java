package com.aritako.shortly.backend.url.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.url.model.UrlMapping;
import com.aritako.shortly.backend.user.model.User;

public interface UrlRepository extends JpaRepository<UrlMapping, Long>{
  Optional<UrlMapping> findByShortCode(String shortCode);
  List<UrlMapping> findAllByUser(User user);
}