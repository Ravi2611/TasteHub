package com.ravi.Service;

import com.ravi.config.JwtProvider;
import com.ravi.model.User;
import com.ravi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImplementation implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found corresponding to email: " + email);
        }

        return user;
    }
}
