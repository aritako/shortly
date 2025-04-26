package com.aritako.shortly.backend.url.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.auth.service.AuthService;
import com.aritako.shortly.backend.url.service.UrlService;
import com.aritako.shortly.backend.user.model.User;

@RestController
@RequestMapping("/api/url/map")
public class UrlMappingController {

  @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlService urlService;
  private final AuthService authService;

  public UrlMappingController(UrlService urlService, AuthService authService){
    this.urlService = urlService;
    this.authService = authService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body){
    User user = this.authService.getAuthenticatedUser();
    String originalUrl = body.get("url");
    String shortCode = body.getOrDefault("shortCode", null);
    String generateShortCode = urlService.shortenUrl(user, originalUrl, shortCode);
    return ResponseEntity.ok(Map.of("shortUrl", baseUrl + "/" + generateShortCode));
  }
}
