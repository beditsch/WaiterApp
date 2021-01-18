package serviceTest;

import com.beditsch.project.dto.UserSignInRequest;
import com.beditsch.project.dto.UserSignUpRequest;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.exception.UsernameAlreadyExistsException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.repository.UserRepository;
import com.beditsch.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    String password = "fytguy78t6ftc3yghqved";
    User user = new User();

    @BeforeEach
    public void setUp() {
        user.setId(1);
        user.setUsername("Martens");
        user.setPassword(password);
    }


    @Test
    public void testLoadByUsername() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("KFC");
        user.setRestaurant(restaurant);


        given(userRepository.findByUsername(anyString())).willReturn(user);

        User user1 = (User) userService.loadUserByUsername("Martens");
        assertNotNull(user1);
        assertEquals("Martens", user1.getUsername());
        assertEquals(restaurant, user1.getRestaurant());
    }

    @Test
    public final void testLoadBy_UsernameNotFoundException() {
        given(userRepository.findByUsername(anyString())).willReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.loadUserByUsername("Martens");
                }
                );
    }

    @Test
    public final void testCreateUserMethod(){

        given(userRepository.findByUsername(anyString())).willReturn(null);
        given(bCryptPasswordEncoder.encode(anyString())).willReturn(password);
        given(userRepository.save(any(User.class))).willReturn(user);

        UserSignUpRequest userSignUpRequest = new UserSignUpRequest();
        userSignUpRequest.setUsername("Koko");
        userSignUpRequest.setPassword("vdyahwjbuiwja");
        UserSignUpResponse userSignUpResponse = userService.createUser(userSignUpRequest);

        assertNotNull(userSignUpResponse);
        assertEquals(user.getUsername(), userSignUpResponse.getUsername());
        verify(bCryptPasswordEncoder, times(1)).encode("vdyahwjbuiwja");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public final void testCreateUser_UsernameAlreadyExistsException() {
        given(userRepository.findByUsername(anyString())).willReturn(user);
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest();
        userSignUpRequest.setUsername("Koko");
        userSignUpRequest.setPassword("vdyahwjbuiwja");

        assertThrows(UsernameAlreadyExistsException.class,
                () -> {
                    userService.createUser(userSignUpRequest);
                }
        );
    }









}
