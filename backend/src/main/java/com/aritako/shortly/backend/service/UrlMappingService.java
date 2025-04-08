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
    String shortCode = UUID.randomUUID()
    .toString().replace("-", "")
    .substring(0, 12);
    UrlMapping urlMapping = new UrlMapping(userId, originalUrl, shortCode);
    urlMappingRepository.save(urlMapping);
    return shortCode;
  }

  public String redirectUrl(String shortCode){
    UrlMapping urlMapping = urlMappingRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    this.incrementClickCount(urlMapping);
    return urlMapping.getOriginalUrl();
  }

  public void incrementClickCount(UrlMapping urlMapping){
    urlMapping.incrementClickCount();
    urlMappingRepository.save(urlMapping);
  }

  public UrlMapping getUrlMappingInfo(String shortCode){
    UrlMapping urlMapping = urlMappingRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    return urlMapping;
  }
}
