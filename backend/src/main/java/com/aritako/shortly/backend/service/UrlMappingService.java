package com.aritako.shortly.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.model.UrlMapping;
import com.aritako.shortly.backend.repository.UrlMappingRepository;

@Service
public class UrlMappingService {
  private final UrlMappingRepository urlMappingRepository;

  public UrlMappingService(UrlMappingRepository urlMappingRepository){
    this.urlMappingRepository = urlMappingRepository;
  }

  public String shortenUrl(UUID userId, String originalUrl){
    String shortCode = UUID.randomUUID().toString().substring(0, 6);
    UrlMapping urlMapping = new UrlMapping(userId, originalUrl, shortCode);
    urlMappingRepository.save(urlMapping);
    return shortCode;
  }

  public String getOriginalUrl(String shortcode){
    UrlMapping urlMapping = urlMappingRepository
    .findByShortCode(shortcode).orElseThrow(() -> new RuntimeException("Short code not found"));

    urlMapping.incrementClickCount();
    urlMappingRepository.save(urlMapping);
    return urlMapping.getOriginalUrl();
  }
}
