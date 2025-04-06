package com.aritako.shortly.backend.repository;

import com.aritako.shortly.backend.model.UrlMapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long>{
  Optional<UrlMapping> findByShortCode(String shortCode);
}