package com.example.cefacademyexample.service;

import com.example.cefacademyexample.entity.User;
import com.example.cefacademyexample.dto.UserRequestDto;
import com.example.cefacademyexample.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getById(Long id);

    void createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Long id,UserRequestDto userRequestDto);

    void delete(Long id);
    User findById(Long id);
}
