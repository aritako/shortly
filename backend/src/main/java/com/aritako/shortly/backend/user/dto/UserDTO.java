package com.aritako.shortly.backend.user.dto;

import java.time.LocalDateTime;

import com.aritako.shortly.backend.user.variables.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime createdAt = LocalDateTime.now();
  private UserRoles role = UserRoles.FREE_USER;
}
