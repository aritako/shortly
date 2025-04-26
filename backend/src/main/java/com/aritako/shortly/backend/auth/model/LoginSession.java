package com.aritako.shortly.backend.auth.model;

import java.time.LocalDateTime;

import com.aritako.shortly.backend.user.model.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}
