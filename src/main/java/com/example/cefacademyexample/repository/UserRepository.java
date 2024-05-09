package com.example.cefacademyexample.repository;

import com.example.cefacademyexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsUserByEmail(String email);
    Boolean existsUserByPhoneNumber(String phoneNumber);



}
