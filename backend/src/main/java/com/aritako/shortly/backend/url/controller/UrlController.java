package com.aritako.shortly.backend.url.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aritako.shortly.backend.auth.service.AuthService;
import com.aritako.shortly.backend.url.model.UrlMapping;
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
  public ResponseEntity<List<UrlMapping>> getUrlMappingList(){
    User user = this.authService.getAuthenticatedUser();
    return ResponseEntity.ok(this.urlService.getUrlMappingList(user));
  }
}
