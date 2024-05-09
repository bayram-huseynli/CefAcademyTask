package com.example.cefacademyexample.service.impl;

import com.example.cefacademyexample.entity.User;
import com.example.cefacademyexample.dto.UserRequestDto;
import com.example.cefacademyexample.dto.UserResponseDto;
import com.example.cefacademyexample.exception.BadRequestException;
import com.example.cefacademyexample.exception.ConflictException;
import com.example.cefacademyexample.exception.NotFoundException;
import com.example.cefacademyexample.repository.UserRepository;
import com.example.cefacademyexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<UserResponseDto> getAll() {
        log.info("Fetching all users");
        List<UserResponseDto> users = userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
        log.info("Fetched {} users", users.size());
        return users;
    }

    @Override
    public UserResponseDto getById(Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = findById(id);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public void createUser(UserRequestDto userRequestDto) {
        log.info("Creating a new user with details: {}", userRequestDto);
        validateEmailUniqueness(userRequestDto.getEmail());
        validatePhoneNumberUniqueness(userRequestDto.getPhoneNumber());
        validatePasswordsMatch(userRequestDto.getPassword(), userRequestDto.getConfirmPassword());
        User user = modelMapper.map(userRequestDto, User.class);
        userRepository.save(user);
        log.info("Created user with ID: {}", user.getId());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        log.info("Updating user with ID: {}", id);
        User existingUser = findById(id);

        if (!existingUser.getEmail().equals(userRequestDto.getEmail())) {
            validateEmailUniqueness(userRequestDto.getEmail());
        }
        if (!existingUser.getPhoneNumber().equals(userRequestDto.getPhoneNumber())) {
            validatePhoneNumberUniqueness(userRequestDto.getPhoneNumber());
        }
        if (userRequestDto.getPassword() != null) {
            validatePasswordsMatch(userRequestDto.getPassword(), userRequestDto.getConfirmPassword());
        }
        modelMapper.map(userRequestDto, existingUser);
        userRepository.save(existingUser);
        log.info("Updated user with ID: {}", existingUser.getId());
        return modelMapper.map(existingUser, UserResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with ID: {}", id);
        User user = findById(id);
        userRepository.delete(user);
        log.info("Deleted user with ID: {}", id);
    }

    @Override
    public User findById(Long id) {
        log.info("Finding user by ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("No user found with ID: {}", id);
            return new NotFoundException("User not found with ID: " + id);
        });
        log.info("Found user: {}", user);
        return user;
    }
    private void validateEmailUniqueness(String email) {
        log.debug("Validating uniqueness of email: {}", email);
        if (userRepository.existsUserByEmail(email)) {
            log.warn("Validation failed: Email '{}' is already used", email);
            throw new ConflictException("Email already used");
        }
        log.info("Email '{}' is unique", email);
    }

    private void validatePhoneNumberUniqueness(String phoneNumber) {
        log.debug("Validating uniqueness of phone number: {}", phoneNumber);
        if (userRepository.existsUserByPhoneNumber(phoneNumber)) {
            log.warn("Validation failed: Phone number '{}' is already used", phoneNumber);
            throw new ConflictException("Phone number already used");
        }
        log.info("Phone number '{}' is unique", phoneNumber);
    }

    private void validatePasswordsMatch(String password, String confirmPassword) {
        log.debug("Validating that passwords match");
        if (!Objects.equals(password, confirmPassword)) {
            log.warn("Validation failed: Passwords do not match");
            throw new BadRequestException("Passwords don't match");
        }
        log.info("Passwords match");
    }


}
