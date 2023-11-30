package com.clone.controller;

import com.clone.config.TokenProvider;
import com.clone.exception.UserException;
import com.clone.model.User;
import com.clone.repository.UserRepository;
import com.clone.request.LoginRequest;
import com.clone.response.AuthResponse;
import com.clone.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@SuppressWarnings("unused")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetails;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          TokenProvider tokenProvider, CustomUserDetailsService customUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserDetails = customUserDetails;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String full_name = user.getFull_name();
        Optional<User> isEmailExist = Optional.ofNullable(userRepository.findByEmail(email));

        // Check if the given email is already registered in the database
        if(isEmailExist.isPresent()) {
            System.out.println("Found: " + isEmailExist.get().getEmail());
            throw new UserException("Email is already registered with other account");
        }

        // If not, then create new user
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setFull_name(full_name);
        createUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(createUser);

        // Authenticate user and generate JWT token
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token,true);

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        System.out.println("Logging: " + username);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticate (String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("Signing In: " + userDetails);

        if(userDetails == null) {
            System.out.println("Signing In: [USER NOT FOUND]" + null);
            throw new BadCredentialsException("Invalid username or password!");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Sign In Failed: [WRONG PASSWORD]" + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}