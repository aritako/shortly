package com.aritako.shortly.backend.url.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlMappingListDTO {
  private int totalElements;
  private int totalPages;
  private int currentPage;
  private int pageSize;
  private List<UrlMappingDTO> urls;
}
