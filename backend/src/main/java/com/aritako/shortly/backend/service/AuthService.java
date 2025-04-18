package com.aritako.shortly.backend.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.model.User;
import com.aritako.shortly.backend.repository.UserRepository;
@Service
public class AuthService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+[]{}|;:',.<>?/`~";

  public AuthService(
    UserRepository userRepository, 
    PasswordEncoder passwordEncoder, 
    JwtService jwtService, 
    AuthenticationManager authenticationManager
  ){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

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
    String jwt = jwtService.generateToken(user);
    return Map.of("token", jwt);
  }

  public Map<String, String> login(String username, String password){
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException e){
      throw new RuntimeException("Invalid credentials!");
    }

    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found!"));
    
      String jwt = jwtService.generateToken(user);
      return Map.of("token", jwt);
  }

  private boolean isValidPasswordLength(String password) {
    return password.length() >= 8;
  }

  private boolean containsNumbers(String password){
    return password.chars().anyMatch(Character::isDigit);
  }

  private boolean containsSpecialCharacters(String password){
    return password.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
  }
}