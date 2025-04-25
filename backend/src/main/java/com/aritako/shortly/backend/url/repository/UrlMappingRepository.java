package com.aritako.shortly.backend.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.url.model.UrlMapping;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long>{
  Optional<UrlMapping> findByShortCode(String shortCode);
}