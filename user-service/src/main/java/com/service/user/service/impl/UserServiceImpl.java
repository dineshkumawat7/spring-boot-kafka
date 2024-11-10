package com.service.user.service.impl;

import com.service.user.entity.User;
import com.service.user.exception.ResourceNotFoundException;
import com.service.user.exception.UserAlreadyExistsException;
import com.service.user.payload.UserRegisterDto;
import com.service.user.payload.UserUpdateDto;
import com.service.user.repository.UserRepo;
import com.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_TOPIC = "users";
    private static final String USER_GROUP = "users-group";
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        if(userRepo.findByEmail(userRegisterDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("an user already register with this email: " + userRegisterDto.getEmail());
        }
        User user = User.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .email(userRegisterDto.getEmail())
                .phone(userRegisterDto.getPhone())
                .isValid(true)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        log.info("Published bank account with ID: {} to topic: {}", user.getId(), USER_TOPIC);
        return userRepo.save(user);
    }


    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));
    }

    @Override
    public Page<User> getPaginatedUser(Integer pageNumber, Integer sizeNumber) {
        Pageable pageable = PageRequest.of(pageNumber, sizeNumber);
        return userRepo.findAll(pageable);
    }

    @Override
    public User updateUser(String id, UserUpdateDto userUpdateDto) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(userUpdateDto.getFirstName());
            existingUser.setLastName(userUpdateDto.getLastName());
            existingUser.setPhone(userUpdateDto.getPhone());
            existingUser.setEmail(userUpdateDto.getEmail());
            existingUser.setUpdatedAt(LocalDateTime.now());
            return userRepo.save(existingUser);
        }
        throw new ResourceNotFoundException("user not found with id: " + id);
    }

    @Override
    public boolean deleteUser(String id) {
        Optional<User> optionalUser = userRepo.findById(id);
        optionalUser.ifPresent(user -> userRepo.delete(user));
        throw new ResourceNotFoundException("user not found with id: " + id);
    }
}
