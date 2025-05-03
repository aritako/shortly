package com.aritako.shortly.backend.url.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aritako.shortly.backend.auth.service.AuthService;
import com.aritako.shortly.backend.url.dto.UrlMappingDTO;
import com.aritako.shortly.backend.url.dto.UrlMappingListDTO;
import com.aritako.shortly.backend.url.service.UrlService;
import com.aritako.shortly.backend.user.model.User;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlService urlService;
  private final AuthService authService;
  
  public UrlController(UrlService urlService, AuthService authService){
    this.urlService = urlService;
    this.authService = authService;
  }

  @GetMapping("/")
  public ResponseEntity<UrlMappingListDTO> getUrlMappingList(){
    User user = this.authService.getAuthenticatedUser();
    return ResponseEntity.ok(this.urlService.getUrlMappingList(user));
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<UrlMappingDTO> getOriginalUrl(@PathVariable String shortCode){
    User user = this.authService.getAuthenticatedUser();
    UrlMappingDTO urlMappingInfo = urlService.getUrlMappingInfo(user, shortCode);
    return ResponseEntity.ok(urlMappingInfo);
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> createShortUrl(@RequestBody Map<String, String> body){
    User user = this.authService.getAuthenticatedUser();
    String originalUrl = body.get("url");
    String shortCode = body.getOrDefault(user, null);
    String generateShortCode = this.urlService.shortenUrl(user, originalUrl, shortCode);
    return ResponseEntity.ok(Map.of("shortCode", baseUrl + "/" + generateShortCode));
  }

  @DeleteMapping("/{shortCode}")
  public ResponseEntity<Map<String,String>> deleteShortCode(@PathVariable String shortCode){
    User user = this.authService.getAuthenticatedUser();
    this.urlService.deleteUrlMapping(user, shortCode);
    return ResponseEntity.ok(Map.of("message", "Short Link Deleted."));
  }
}
