package com.aritako.shortly.backend.auth.model;

import java.time.LocalDateTime;

import com.aritako.shortly.backend.user.model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "login_session")
public class LoginSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "refresh_token", nullable = false, unique = true)
  private String refreshToken;
  
  @Column(name = "ip_address")
  private String ipAddress;
  
  @Column(name = "user_agent")
  private String userAgent;
  
  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;
  
  @Column(name = "status")
  private String status = "ACTIVE";

  //#region Constructors
  public LoginSession(){}

  public LoginSession(User user, String refreshToken, String ipAddress, String userAgent, LocalDateTime expiresAt){
    this.user = user;
    this.refreshToken = refreshToken;
    this.ipAddress = ipAddress;
    this.userAgent = userAgent;
    this.expiresAt = expiresAt;
  }
  //#endregion

  //#region Getters
  public Long getId(){
    return id;
  }

  public User getUser(){
    return user;
  }

  public String getRefreshToken(){
    return refreshToken;
  }

  public String getIpAddress(){
    return ipAddress;
  }

  public String getUserAgent(){
    return userAgent;
  }

  public LocalDateTime getCreatedAt(){
    return createdAt;
  }

  public LocalDateTime getExpiresAt(){
    return expiresAt;
  }

  public String getStatus(){
    return status;
  }
  //#endregion

  //#region Setters
  public void setId(Long id){
    this.id = id;
  }

  public void setUser(User user){
    this.user = user;
  }

  public void setRefreshToken(String refreshToken){
    this.refreshToken = refreshToken;
  }

  public void setIpAddress(String ipAddress){
    this.ipAddress = ipAddress;
  }

  public void setUserAgent(String userAgent){
    this.userAgent = userAgent;
  }

  public void setExpiresAt(LocalDateTime expiresAt){
    this.expiresAt = expiresAt;
  }

  public void setStatus(String status){
    this.status = status;
  }
  //#endregion
}
