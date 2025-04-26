package com.aritako.shortly.backend.url.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.url.model.UrlMapping;
import com.aritako.shortly.backend.url.repository.UrlRepository;
import com.aritako.shortly.backend.user.model.User;
@Service
public class UrlService {
  private final UrlRepository urlRepository;

  public UrlService(UrlRepository urlRepository){
    this.urlRepository = urlRepository;
  }

  public String shortenUrl(User user, String originalUrl, String defaultShortCode){
    // If the request came with a user-defined shortcode, return it
    // Else, generate a random shortCode
    final String shortCode = (defaultShortCode != null) 
      ? defaultShortCode 
      : UUID.randomUUID()
        .toString().replace("-", "")
        .substring(0, 12);
    UrlMapping urlMapping = new UrlMapping(user, originalUrl, shortCode);
    urlRepository.save(urlMapping);
    return shortCode;
  }

  public String redirectUrl(String shortCode){
    UrlMapping urlMapping = urlRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    this.incrementClickCount(urlMapping);
    return urlMapping.getOriginalUrl();
  }

  public void incrementClickCount(UrlMapping urlMapping){
    urlMapping.incrementClickCount();
    urlRepository.save(urlMapping);
  }

  public UrlMapping getUrlMappingInfo(String shortCode){
    UrlMapping urlMapping = urlRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    return urlMapping;
  }

  public List<UrlMapping> getUrlMappingList(User user){
    List<UrlMapping> urlMappingList = urlRepository.findAllByUser(user);
    return urlMappingList;
  }
}
