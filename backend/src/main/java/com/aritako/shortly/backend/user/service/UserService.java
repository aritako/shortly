package com.aritako.shortly.backend.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aritako.shortly.backend.user.dto.UserDTO;
import com.aritako.shortly.backend.user.model.User;
import com.aritako.shortly.backend.user.repository.UserRepository;

@Service
public class UserService {
  private final ModelMapper modelMapper;

  public UserService(UserRepository userRepository, ModelMapper modelMapper){
    this.modelMapper = modelMapper;
  }

  public UserDTO getUserInfo(User user){
    return modelMapper.map(user, UserDTO.class);
  }
}
