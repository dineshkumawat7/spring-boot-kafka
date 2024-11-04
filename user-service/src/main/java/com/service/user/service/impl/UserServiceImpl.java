package com.service.user.service.impl;

import com.service.user.entity.User;
import com.service.user.exception.ResourceNotFoundException;
import com.service.user.payload.UserRegisterDto;
import com.service.user.repository.UserRepo;
import com.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        User newUser = User.builder()
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
        kafkaTemplate.send(USER_TOPIC, newUser.getId(), newUser);
        log.info("Published user with ID: {} to topic: {}", newUser.getId(), USER_TOPIC);
        return newUser;
    }

    @KafkaListener(topics = USER_TOPIC, groupId = USER_GROUP)
    public void consumeRegisterUser(User user){
        userRepo.save(user);
        log.info("new user saved in database with id: {} and consume from topic: {} ", user.getId(), USER_TOPIC);
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
}
