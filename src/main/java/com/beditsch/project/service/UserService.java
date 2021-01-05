package com.beditsch.project.service;

import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.model.User;
import com.beditsch.project.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserSignUpResponse createUser(UserSignUpRequest newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());

        User storedUser = userRepository.save(user);

        UserSignUpResponse returnedUser = new UserSignUpResponse();
        returnedUser.setUsername(storedUser.getUsername());
        returnedUser.setUserId(storedUser.getId());

        return returnedUser;
    }

}
