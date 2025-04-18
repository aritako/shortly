package com.aritako.shortly.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  
  @Value("${BASE_URL}")
  private String baseUrl;

  private final AuthService authService;

  public AuthController(AuthService authService){
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> body){
    String username = body.get("username");
    String password = body.get("password");
    String email = body.get("email");
    return ResponseEntity.ok(authService.register(username, email, password));
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body){
    String username = body.get("username");
    String password = body.get("password");

    return ResponseEntity.ok(authService.login(username, password));
  }
}
