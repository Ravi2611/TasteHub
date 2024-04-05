package com.ravi.repository;

import com.ravi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // This comes with many inbuilt methods but if we need custom we can add here by following proper naming convention

    public User findByEmail(String username);
}
