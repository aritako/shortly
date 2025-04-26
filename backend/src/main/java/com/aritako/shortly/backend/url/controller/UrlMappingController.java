package com.aritako.shortly.backend.url.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.auth.service.AuthService;
import com.aritako.shortly.backend.url.model.UrlMapping;
import com.aritako.shortly.backend.url.service.UrlService;
import com.aritako.shortly.backend.user.model.User;

@RestController
@RequestMapping("/api/url/map")
public class UrlMappingController {

  @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlService urlMappingService;
  private final AuthService authService;

  public UrlMappingController(UrlService urlMappingService, AuthService authService){
    this.urlMappingService = urlMappingService;
    this.authService = authService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body){
    User user = this.authService.getAuthenticatedUser();
    String originalUrl = body.get("url");
    String shortCode = body.getOrDefault("shortCode", null);
    String generateShortCode = urlMappingService.shortenUrl(user, originalUrl, shortCode);
    return ResponseEntity.ok(Map.of("shortUrl", baseUrl + "/" + generateShortCode));
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<UrlMapping> getOriginalUrl(@PathVariable String shortCode){
    UrlMapping urlMappingInfo = urlMappingService.getUrlMappingInfo(shortCode);
    return ResponseEntity.ok(urlMappingInfo);
  }
}
