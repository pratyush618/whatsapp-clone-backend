package com.clone.serviceImpl;

import com.clone.model.User;
import com.clone.service.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInterface {

    User findByEmail(String email);
    User findUserProfile(String jwt);
    User updateUser(Integer userId, UpdateUserRequest request) throws UserException;
    User findUserById(Integer userId) throws UserException;
    List<User> searchUser(String query);

}