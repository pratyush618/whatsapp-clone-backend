package com.clone.serviceImpl;

import com.clone.exception.UserException;
import com.clone.model.User;
import com.clone.repository.UserRepository;
import com.clone.request.UpdateUserRequest;
import com.clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByEmail(String email) throws UserException {
        Optional<User> opt = Optional.ofNullable(userRepository.findByEmail(email));

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User not found with Email: " + email);
    }

    @Override
    public User findUserProfile(String jwt) {
        return null;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest request) {
        return null;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User not found with id: " + userId);
        return null;
    }

    @Override
    public List<User> searchUser(String query) {
        return null;
    }
}
