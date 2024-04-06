package com.ravi.controller;

import com.ravi.Response.AuthResponse;
import com.ravi.Service.CustomerUserDetailsService;
import com.ravi.config.JwtProvider;
import com.ravi.model.Cart;
import com.ravi.model.USER_ROLE;
import com.ravi.model.User;
import com.ravi.repository.CartRepository;
import com.ravi.repository.UserRepository;
import com.ravi.request.LoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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

        logger.info("Inside createUserHandler...");

        logger.info("Checking if the given user already exists by the email");
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            logger.info("Email already in use with another account");
            throw new Exception("Email already in use with another account");
        }

        logger.info("No user with the email found.. Creating new user");

        User createdUser = new User();

        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        logger.info("Passing the password through the password encoder before saving it in db");
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
        logger.error("Generated Token: " + jwt);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        logger.error("AuthResponse: " + authResponse);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        logger.info("Inside signIn..");

        String username = loginRequest.getEmail();
        logger.error("UserName: " + username);

        String password = loginRequest.getPassword();
        logger.error("Password: " + password);

        logger.info("Checking if the UserName and Password is valid");
        Authentication authentication = authenticate(username, password);


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        logger.info("getting role from authority");
        logger.error("Role: " + role);

        String jwt = jwtProvider.generateToken(authentication);
        logger.error("Generated Token: " + jwt);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Signin success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        logger.error("AuthResponse: " + authResponse);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        logger.debug("Getting user from username");
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            // user not found by this username/email
            logger.error("Invalid username");
            throw new BadCredentialsException("Invalid username...");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            // user found but password is wrong
            logger.error("Invalid password");
            throw new BadCredentialsException("Invalid password...");
        }

        // for token based authentication we generally pass password as null
        logger.debug("Password verified. Authenticated");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
