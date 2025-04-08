package com.aritako.shortly.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "url_mappings")
public class UrlMapping {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "original_url", nullable = false)
  private String originalUrl;

  @Column(name = "short_code", nullable = false)
  private String shortCode;

  @Column(name = "click_count", columnDefinition = "TEXT")
  private Long clickCount = 0L;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  // #region Constructor
  public UrlMapping(){}
  
  public UrlMapping(UUID userId, String originalUrl, String shortCode) {
    this.userId = userId;
    this.originalUrl  = originalUrl;
    this.shortCode = shortCode;
    this.clickCount = 0L;
  }
  // #endregion

  // #region Getters
  public Long getId(){
    return id;
  }

  public UUID getUserId(){
    return userId;
  }

  public Long getClickCount(){
    return clickCount;
  }

  public String getOriginalUrl(){
    return originalUrl;
  }
  // #endregion

  // #region Setters
  public void setUserId(UUID userId){
    this.userId = userId;
  }

  public void setOriginalUrl(String originalUrl){
    this.originalUrl = originalUrl;
  }

  public void setShortCode(String shortCode){
    this.shortCode = shortCode;
  }

  public void incrementClickCount(){
    this.clickCount += 1;
  }

  public void setClickCount(Long clickCount){
    this.clickCount = clickCount;
  }
  //#endregion 

}
