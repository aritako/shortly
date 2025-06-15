package com.aritako.shortly.backend.url.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.url.model.UrlMapping;
import com.aritako.shortly.backend.user.model.User;

public interface UrlRepository extends JpaRepository<UrlMapping, Long>{
  Optional<UrlMapping> findByShortCode(String shortCode);
  Page<UrlMapping> findAllByUser(User user, Pageable pageable);
}