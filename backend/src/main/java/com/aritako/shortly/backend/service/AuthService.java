package com.aritako.shortly.backend.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.model.LoginSession;
import com.aritako.shortly.backend.model.User;
import com.aritako.shortly.backend.repository.LoginSessionRepository;
import com.aritako.shortly.backend.repository.UserRepository;
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
}