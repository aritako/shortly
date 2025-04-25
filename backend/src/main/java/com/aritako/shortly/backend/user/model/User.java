package com.aritako.shortly.backend.user.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aritako.shortly.backend.user.variables.UserRoles;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User implements UserDetails{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "role", nullable = false)
  private UserRoles role = UserRoles.FREE_USER;

  //#region Constructor
  public User(){}

  public User(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
  }
  //#endregion

  //#region Getters
  public Long getId(){
    return id;
  }

  public String getUsername(){
    return username;
  }

  public String getEmail(){
    return email;
  }

  public String getPassword(){
    return password;
  }

  public LocalDateTime getCreatedAt(){
    return createdAt;
  }

  public UserRoles getRole(){
    return role;
  }

  public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  //#endregion

  //#region Setters
  public void setUsername(String username){
    this.username = username;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setRole(UserRoles role){
    this.role = role;
  }
  //#endregion

}
