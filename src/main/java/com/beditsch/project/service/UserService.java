package com.beditsch.project.service;

import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.exception.MealNotFoundException;
import com.beditsch.project.exception.UserNotFoundException;
import com.beditsch.project.exception.UsernameAlreadyExistsException;
import com.beditsch.project.model.Meal;
import com.beditsch.project.model.User;
import com.beditsch.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignUpResponse createUser(UserSignUpRequest newUser) {
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            throw new UsernameAlreadyExistsException();
        }

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

    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public User getUserById(Integer userId){
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        return user.get();
    }

}
