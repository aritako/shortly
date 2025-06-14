package com.aritako.shortly.backend.auth.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.auth.model.LoginSession;
import com.aritako.shortly.backend.auth.repository.LoginSessionRepository;
import com.aritako.shortly.backend.shared.service.JwtService;
import com.aritako.shortly.backend.user.model.User;
import com.aritako.shortly.backend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

@Service
public class AuthService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final LoginSessionRepository loginSessionRepository;
  private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+[]{}|;:',.<>?/`~";

  public AuthService(
    UserRepository userRepository, 
    PasswordEncoder passwordEncoder, 
    JwtService jwtService, 
    AuthenticationManager authenticationManager,
    LoginSessionRepository loginSessionRepository
  ){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.loginSessionRepository = loginSessionRepository;
  }
  //#region Authentication Methods
  public Map<String, String> register(String username, String email, String password){
    if (username == null || email == null || password == null){
      throw new RuntimeException("Request must contain the following fields: username, email, password.");
    }
    
    if (userRepository.findByUsername(username).isPresent()){
      throw new RuntimeException("Username already exists!");
    }

    if (userRepository.findByEmail(email).isPresent()){
      throw new RuntimeException("Email already in use!");
    }

    if (!isValidPasswordLength(password)){
      throw new RuntimeException("Password must be at least 8 characters long.");
    }

    if (!containsNumbers(password)){
      throw new RuntimeException("Password must contain at least one number.");
    }

    if (!containsSpecialCharacters(password)){
      throw new RuntimeException("Password must contain a special character.");
    }

    User user = new User(
      username,
      email,
      passwordEncoder.encode(password)
    );

    userRepository.save(user);
    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
  }

  public Map<String, String> login(String username, String password, String ipAddress, String userAgent){
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException e){
      throw new RuntimeException("Invalid credentials!");
    }

    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found!"));
    
    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    LoginSession loginSession = new LoginSession(
      user,
      refreshToken,
      ipAddress,
      userAgent,
      LocalDateTime.now().plusDays(7)
    );
    loginSessionRepository.save(loginSession);
    return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
  }

  public Map<String, String> refreshAccessToken(String refreshToken){
    LoginSession session = loginSessionRepository
    .findByRefreshToken(refreshToken)
    .orElseThrow(() -> new RuntimeException("Invalid Refresh Token!"));

    if (!"ACTIVE".equals(session.getStatus())){
      throw new RuntimeException("Refresh Token No Longer Valid!");
    }

    if (session.getExpiresAt().isBefore(LocalDateTime.now())){
      session.setStatus("EXPIRED");
      loginSessionRepository.save(session);
      throw new RuntimeException("Refresh Token Expired!");
    }
    
    User user = session.getUser();
    String newAccessToken = jwtService.generateAccessToken(user);

    return Map.of("accessToken", newAccessToken);
  }

  public void logout(String refreshToken) {
    LoginSession session = loginSessionRepository
    .findByRefreshToken(refreshToken)
    .orElseThrow(() -> new RuntimeException("Refresh Token Not Found!"));

    session.setStatus("REVOKED");
    loginSessionRepository.save(session);
  }

  public User getAuthenticatedUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user;
  }
  //#endregion

  //#region Utility methods
  private boolean isValidPasswordLength(String password) {
    return password.length() >= 8;
  }

  private boolean containsNumbers(String password){
    return password.chars().anyMatch(Character::isDigit);
  }

  private boolean containsSpecialCharacters(String password){
    return password.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
  }
  //#endregion

  // Helper to set the refresh token cookie
  public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // Set to true in production (requires HTTPS)
    cookie.setPath("/");
    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
    response.addCookie(cookie);
  }

  // Helper to clear the refresh token cookie
  public void clearRefreshTokenCookie(HttpServletResponse response) {
    Cookie cookie = new Cookie("refreshToken", "");
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // Set to true in production
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }

  // Helper to extract refresh token from cookie
  public String extractRefreshTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }
    return null;
  }
}