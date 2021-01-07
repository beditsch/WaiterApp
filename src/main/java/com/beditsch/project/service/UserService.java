package com.beditsch.project.service;

import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.model.User;
import com.beditsch.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignUpResponse createUser(UserSignUpRequest newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        User storedUser = userRepository.save(user);

        UserSignUpResponse returnedUser = new UserSignUpResponse();
        returnedUser.setUsername(storedUser.getUsername());
        returnedUser.setUserId(storedUser.getId());

        return returnedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
         if (user == null) throw new UsernameNotFoundException(username);

        return user;
    }




}
