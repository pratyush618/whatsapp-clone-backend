package com.clone.serviceImpl;

import com.clone.config.TokenProvider;
import com.clone.exception.UserException;
import com.clone.model.User;
import com.clone.repository.UserRepository;
import com.clone.request.UpdateUserRequest;
import com.clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public User findByEmail(String email) throws UserException {
        Optional<User> opt = Optional.ofNullable(userRepository.findByEmail(email));

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User not found with Email: " + email);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);
        if(email == null) {
            throw new BadCredentialsException("Invalid Token Received : User does not exists!");
        }
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User not found with the email: " + email);
        }
        return user;
    }

    @Override
    public void updateUser(Integer userId, UpdateUserRequest request) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User u = user.get();
            if(request.getFull_name() != null) {
                request.setFull_name(u.getFull_name());
            }
            if(request.getProfile_picture() != null) {
                request.setProfile_picture(u.getProfile_picture());
            }
            userRepository.save(u);
            return;
        }
        throw new UserException("User not found with userId: " + userId);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User not found with id: " + userId);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUsers(query);
    }
}
