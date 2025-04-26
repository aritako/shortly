package com.aritako.shortly.backend.url.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.aritako.shortly.backend.user.model.User;

@Data
@Entity
@Table(name = "url_mappings")
public class UrlMapping {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "original_url", nullable = false)
  private String originalUrl;

  @Column(name = "short_code", nullable = false, unique = true)
  private String shortCode;

  @Column(name = "click_count")
  private Long clickCount = 0L;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  // #region Constructor
  public UrlMapping(){}
  
  public UrlMapping(User user, String originalUrl, String shortCode) {
    this.user = user;
    this.originalUrl  = originalUrl;
    this.shortCode = shortCode;
    this.clickCount = 0L;
  }
  // #endregion

  //#region Custom Methods
  public void incrementClickCount(){
    this.clickCount += 1;
  }
  //#endregion 

}
