package com.aritako.shortly.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.aritako.shortly.backend.service.UrlMappingService;

@RestController
public class RedirectController {
  
  @Value("${BASE_URL}")
  private String baseUrl;

  private final UrlMappingService urlMappingService;

  public RedirectController(UrlMappingService urlMappingService){
    this.urlMappingService = urlMappingService;
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<Void> redirect(@PathVariable String shortCode){
    String target = urlMappingService.redirectUrl(shortCode);
    return ResponseEntity.status(HttpStatus.FOUND)
    .header(HttpHeaders.LOCATION, target)
    .build();
  }
}
