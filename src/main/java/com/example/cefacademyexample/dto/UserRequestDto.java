package com.example.cefacademyexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    String name;

    String surname;

    String phoneNumber;

    String email;

    String password;

    String confirmPassword;
}
