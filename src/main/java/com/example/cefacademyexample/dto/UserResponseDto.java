package com.example.cefacademyexample.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    Long id;

    String name;

    String surname;

    String phoneNumber;

    String email;

    String password;

    String confirmPassword;
}
