package com.aritako.shortly.backend.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aritako.shortly.backend.repository.UserRepository;
import com.aritako.shortly.backend.service.JwtService;
import com.aritako.shortly.backend.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
  
  private final JwtService jwtService;
  private final UserRepository userRepository;

  public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
    ) throws ServletException, IOException {
      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String userName;

      // Filter out unauthorized requests
      if(authHeader == null || !authHeader.startsWith("Bearer ")){
        filterChain.doFilter(request, response);
        return;
      }

      // Extract JWT
      jwt = authHeader.substring(7);
      userName = jwtService.extractUsername(jwt);

      // Authentication Logic
      if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
        // Optional contains the user if found, otherwise it is empty
        // This is a good use case for Optional, as it allows us to avoid null checks and handle the absence of a value gracefully.
        Optional<User> optionalUser = userRepository.findByUsername(userName);
          
          // isPresent is a method of Optional class to check for nullity
          if (optionalUser.isPresent()){
            User user = optionalUser.get();

            if (jwtService.isTokenValid(jwt, user)){
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
              
              authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          }
      }
      
      filterChain.doFilter(request, response);
  }
}
