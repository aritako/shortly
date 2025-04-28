package com.aritako.shortly.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aritako.shortly.backend.auth.service.AuthService;
import com.aritako.shortly.backend.user.dto.UserDTO;
import com.aritako.shortly.backend.user.model.User;
import com.aritako.shortly.backend.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;
  private final AuthService authService;

  public UserController(UserService userService, AuthService authService){
    this.userService = userService;
    this.authService = authService;
  }

  @GetMapping("/")
  public ResponseEntity<UserDTO> getUserInfo(){
    User user = authService.getAuthenticatedUser();
    return ResponseEntity.ok(this.userService.getUserInfo(user));
  }
}
