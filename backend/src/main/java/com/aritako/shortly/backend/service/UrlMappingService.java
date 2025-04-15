package com.aritako.shortly.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.model.UrlMapping;
import com.aritako.shortly.backend.model.User;
import com.aritako.shortly.backend.repository.UrlMappingRepository;
import com.aritako.shortly.backend.repository.UserRepository;
@Service
public class UrlMappingService {
  private final UrlMappingRepository urlMappingRepository;
  private final UserRepository userRepository;

  public UrlMappingService(UrlMappingRepository urlMappingRepository, UserRepository userRepository){
    this.urlMappingRepository = urlMappingRepository;
    this.userRepository = userRepository;
  }

  public String shortenUrl(Long userId, String originalUrl){
    String shortCode = UUID.randomUUID()
    .toString().replace("-", "")
    .substring(0, 12);
    User user = userRepository
    .findById(userId)
    .orElseThrow(() -> new RuntimeException("User not found for: " + shortCode));
    UrlMapping urlMapping = new UrlMapping(user, originalUrl, shortCode);
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
