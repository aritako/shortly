package com.aritako.shortly.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.model.UrlMapping;
import com.aritako.shortly.backend.service.UrlMappingService;

@RestController
@RequestMapping("/api/url")
public class UrlMappingController {

  @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlMappingService urlMappingService;

  public UrlMappingController(UrlMappingService urlMappingService){
    this.urlMappingService = urlMappingService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body){
    Long userId = Long.valueOf(body.get("userId"));
    String originalUrl = body.get("url");
    String shortCode = body.getOrDefault("shortCode", null);
    String generateShortCode = urlMappingService.shortenUrl(userId, originalUrl, shortCode);
    return ResponseEntity.ok(Map.of("shortUrl", baseUrl + "/" + generateShortCode));
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<UrlMapping> getOriginalUrl(@PathVariable String shortCode){
    UrlMapping urlMappingInfo = urlMappingService.getUrlMappingInfo(shortCode);
    return ResponseEntity.ok(urlMappingInfo);
  }
}
