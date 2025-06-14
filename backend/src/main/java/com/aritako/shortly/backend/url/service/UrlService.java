package com.aritako.shortly.backend.url.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.url.dto.UrlMappingDTO;
import com.aritako.shortly.backend.url.dto.UrlMappingListDTO;
import com.aritako.shortly.backend.url.model.UrlMapping;
import com.aritako.shortly.backend.url.repository.UrlRepository;
import com.aritako.shortly.backend.user.model.User;
@Service
public class UrlService {
  private final UrlRepository urlRepository;
  private final ModelMapper modelMapper;
  public UrlService(UrlRepository urlRepository, ModelMapper modelMapper){
    this.urlRepository = urlRepository;
    this.modelMapper = modelMapper;
  }

  public String shortenUrl(User user, String originalUrl, String defaultShortCode){
    // If the request came with a user-defined shortcode, return it
    // Else, generate a random shortCode
    final String shortCode = (defaultShortCode != null) 
      ? defaultShortCode 
      : UUID.randomUUID()
        .toString().replace("-", "")
        .substring(0, 12);
    UrlMapping urlMapping = new UrlMapping(user, originalUrl, shortCode);
    urlRepository.save(urlMapping);
    return shortCode;
  }

  public String redirectUrl(String shortCode){
    UrlMapping urlMapping = urlRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    this.incrementClickCount(urlMapping);
    return urlMapping.getOriginalUrl();
  }

  public void incrementClickCount(UrlMapping urlMapping){
    urlMapping.incrementClickCount();
    urlRepository.save(urlMapping);
  }

  public UrlMappingDTO getUrlMappingInfo(User user, String shortCode){
    UrlMapping urlMapping = urlRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
    if (!user.getId().equals(urlMapping.getUser().getId())) {
      throw new RuntimeException("Invalid request. User " + user.getUsername() + " does not own this shortcode.");
    }
    return modelMapper.map(urlMapping, UrlMappingDTO.class);
  }

  public UrlMappingListDTO getUrlMappingList(User user, int page, int size){
    Page<UrlMapping> urlMappingsPage = urlRepository.findAllByUser(user, PageRequest.of(page, size));
    List<UrlMappingDTO> urlMappings = urlMappingsPage
      .getContent()
      .stream()
      .map(urlMapping -> modelMapper.map(urlMapping, UrlMappingDTO.class))
      .toList();
    
    return new UrlMappingListDTO(
      (int) urlMappingsPage.getTotalElements(),
      urlMappingsPage.getTotalPages(),
      urlMappingsPage.getNumber(),
      urlMappingsPage.getSize(),
      urlMappings
    );
  }

  public void deleteUrlMapping(User user, String shortCode){
    UrlMapping urlMapping = this.urlRepository
    .findByShortCode(shortCode)
    .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));

    if (!user.equals(urlMapping.getUser())){
      throw new RuntimeException("Invalid request. User " + user.getUsername() + " does not own this short code.");
    }

    urlMapping.setStatus("INACTIVE");
    urlRepository.save(urlMapping);
  }
}
