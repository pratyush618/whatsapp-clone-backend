package com.clone.service;

import com.clone.exception.UserException;
import com.clone.model.User;
import com.clone.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User findByEmail(String email) throws UserException;

    User findUserProfile(String jwt) throws UserException;

    void updateUser(Integer userId, UpdateUserRequest request) throws UserException;

    User findUserById(Integer userId) throws UserException;

    List<User> searchUser(String query);

}