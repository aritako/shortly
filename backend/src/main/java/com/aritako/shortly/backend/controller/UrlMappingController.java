package com.aritako.shortly.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.service.UrlMappingService;

@RestController
@RequestMapping("/api")
public class UrlMappingController {

  @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlMappingService urlMappingService;

  public UrlMappingController(UrlMappingService urlMappingService){
    this.urlMappingService = urlMappingService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body){
    UUID userId = UUID.fromString(body.get("userId"));
    String originalUrl = body.get("url");
    String shortCode = urlMappingService.shortenUrl(userId, originalUrl);
    return ResponseEntity.ok(Map.of("shortUrl", baseUrl + "/" + shortCode));
  }

  @GetMapping("/{shortcode}")
  public ResponseEntity<Void> redirect(@PathVariable String shortcode){
    String target = urlMappingService.getOriginalUrl(shortcode);
    return ResponseEntity.status(HttpStatus.FOUND)
    .header(HttpHeaders.LOCATION, target)
    .build();
  }

  @GetMapping("/get/{shortcode}")
  public ResponseEntity<Map<String,String>> getOriginalUrl(@PathVariable String shortcode){
    String originalUrl = this.urlMappingService.getOriginalUrl(shortcode);
    return ResponseEntity.ok(Map.of("originalUrl", originalUrl));
  }
}
