package com.beditsch.project.repository;

import com.beditsch.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //User findUserByEmail(String email);
}
