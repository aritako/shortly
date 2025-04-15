package com.aritako.shortly.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.model.UrlMapping;
import com.aritako.shortly.backend.service.UrlMappingService;

@RestController
@RequestMapping
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

  @GetMapping("/{shortCode}")
  public ResponseEntity<Void> redirect(@PathVariable String shortCode){
    String target = urlMappingService.redirectUrl(shortCode);
    return ResponseEntity.status(HttpStatus.FOUND)
    .header(HttpHeaders.LOCATION, target)
    .build();
  }

  @GetMapping("/info/{shortCode}")
  public ResponseEntity<UrlMapping> getOriginalUrl(@PathVariable String shortCode){
    UrlMapping urlMappingInfo = urlMappingService.getUrlMappingInfo(shortCode);
    return ResponseEntity.ok(urlMappingInfo);
  }
}
