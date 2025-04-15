package com.aritako.shortly.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
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
  //#endregion

  //#region Setters
  public void setUsername(String username){
    this.username = username;
  }

  public void setEmail(String email){
    this.email = email;
  }
  //#endregion

}
