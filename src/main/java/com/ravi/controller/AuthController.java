package com.ravi.controller;

import com.ravi.Response.AuthResponse;
import com.ravi.Service.CustomerUserDetailsService;
import com.ravi.config.JwtProvider;
import com.ravi.model.Cart;
import com.ravi.model.User;
import com.ravi.repository.CartRepository;
import com.ravi.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        logger.info("Checking if the given user already exists by the email");
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            logger.error("Email already in use with another account");
            throw new Exception("Email already in use with another account");
        }

        logger.info("No user with the email found.. Creating new user");

        User createdUser = new User();

        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        logger.trace("Passing the password through the password encoder before saving it in db");
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);
        logger.info("User saved in DB");
        logger.error("Saved user: " + savedUser);

        logger.info("Creating cart for the new user");

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        logger.info("Saving the cart for the newly created user in db");
        cartRepository.save(cart);
        logger.error("Saved cart: " + cart);

        logger.info("Starting process of auth token generation and authentication");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        logger.trace("Generated Token: " + jwt);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        logger.error("AuthResponse: " + authResponse);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

}
