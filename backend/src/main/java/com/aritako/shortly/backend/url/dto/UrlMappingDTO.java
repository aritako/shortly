package com.aritako.shortly.backend.url.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlMappingDTO {
  private Long id;
  private String originalUrl;
  private String shortCode;
  private Long clickCount;
  private LocalDateTime createdAt;
  private Long userId;
}

