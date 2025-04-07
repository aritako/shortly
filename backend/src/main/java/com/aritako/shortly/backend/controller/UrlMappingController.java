package com.aritako.shortly.backend.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.service.UrlMappingService;

@RestController
@RequestMapping("/api")
public class UrlMappingController {
  private final UrlMappingService urlMappingService;

  public UrlMappingController(UrlMappingService urlMappingService){
    this.urlMappingService = urlMappingService;
  }

  @GetMapping
  public ResponseEntity<String> test(){
    return ResponseEntity.ok("Hello World!!!");
  }

  @PostMapping("/shorten")
  public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body){
    UUID userId = UUID.fromString(body.get("userId"));
    String originalUrl = body.get("url");
    String shortCode = urlMappingService.shortenUrl(userId, originalUrl);
    
    return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:8080/" + shortCode));
  }
}
