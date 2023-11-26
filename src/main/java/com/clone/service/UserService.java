package com.clone.service;

import com.clone.exception.UserException;
import com.clone.model.User;
import com.clone.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email) throws UserException;
    User findUserProfile(String jwt);
    User updateUser(Integer userId, UpdateUserRequest request) throws UserException;
    Optional<User> findUserById(Integer userId) throws UserException;
    List<User> searchUser(String query);

}