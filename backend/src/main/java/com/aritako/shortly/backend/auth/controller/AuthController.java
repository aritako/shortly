package com.aritako.shortly.backend.auth.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aritako.shortly.backend.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
  
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
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> body, HttpServletResponse response){
      String username = body.get("username");
      String password = body.get("password");
      String email = body.get("email");
      Map<String, String> tokens = authService.register(username, email, password);
      authService.setRefreshTokenCookie(response, tokens.get("refreshToken"));
      return ResponseEntity.ok(Map.of("accessToken", tokens.get("accessToken")));
    }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body, HttpServletRequest request, HttpServletResponse response){
    String username = body.get("username");
    String password = body.get("password");

    String ipAddress = request.getRemoteAddr();
    String userAgent = request.getHeader("User-Agent");
    Map<String, String> tokens = authService.login(username, password, ipAddress, userAgent);
    authService.setRefreshTokenCookie(response, tokens.get("refreshToken"));
    return ResponseEntity.ok(Map.of("accessToken", tokens.get("accessToken")));
  }

  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response){
    String refreshToken = authService.extractRefreshTokenFromCookie(request);
    if (refreshToken == null) {
      throw new RuntimeException("No refresh token cookie found!");
    }
    Map<String, String> tokens = authService.refreshAccessToken(refreshToken);
    // Optionally rotate refresh token here and set new cookie
    // authService.setRefreshTokenCookie(response, tokens.get("refreshToken"));
    return ResponseEntity.ok(Map.of("accessToken", tokens.get("accessToken")));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
    String refreshToken = authService.extractRefreshTokenFromCookie(request);
    if (refreshToken != null) {
      authService.logout(refreshToken);
    }
    authService.clearRefreshTokenCookie(response);
    return ResponseEntity.noContent().build();
  }
}
