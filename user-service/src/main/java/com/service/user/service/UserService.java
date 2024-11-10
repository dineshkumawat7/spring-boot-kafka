package com.service.user.service;

import com.service.user.entity.User;
import com.service.user.payload.UserRegisterDto;
import com.service.user.payload.UserUpdateDto;
import org.springframework.data.domain.Page;

public interface UserService {
    User createUser(UserRegisterDto userRegisterDto);
    User getUserById(String id);
    Page<User> getPaginatedUser(Integer pageNumber, Integer sizeNumber);
    User updateUser(String id, UserUpdateDto userUpdateDto);
    boolean deleteUser(String id);
}
