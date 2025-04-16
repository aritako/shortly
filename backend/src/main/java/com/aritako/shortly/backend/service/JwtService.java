package com.aritako.shortly.backend.service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.model.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  
  //#region Secrets
  @Value("${jwt.secret}")
  private String secretKey;
  
  @Value("${jwt.expiration}")
  private long jwtExpiration;
  //#endregion

  //#region Methods
  // Okay, this is how we generate a JWT token using the secret key and expiration time.
  // The secret key is used to sign the token, and the expiration time is set to 24 hours.
  // The token contains the username of the user as the subject, and it is issued at the current time.
  // The token is then compacted into a string format.
  // The token is signed using the HMAC SHA algorithm with the secret key.
  // Don't forget future me lol


  // Step 1: Create a signing key using the secret key
  private SecretKey getSigningKey(){
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Step 2: Generate a JWT token using the signing key and user information
  public String generateToken(User user){
    return Jwts.builder()
      .subject(user.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 24h expiration
      .signWith(getSigningKey())
      .compact();
  }

  // Step 3: Extract the username and expiration date from the token
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Step 4: Validate the token by checking if the username matches and if the token is not expired
  public boolean isTokenValid(String token, User user) {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Step 5: Generic method to extract claims from the token using a claims resolver function
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
  //#endregion
}
