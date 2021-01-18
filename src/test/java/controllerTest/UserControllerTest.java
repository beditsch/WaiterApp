package controllerTest;

import com.beditsch.project.controller.UserController;
import com.beditsch.project.dto.UserGetResponse;
import com.beditsch.project.dto.UserSignUpResponse;
import com.beditsch.project.exception.AccessDeniedException;
import com.beditsch.project.exception.UsernameAlreadyExistsException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.User;
import com.beditsch.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    SecurityContextHolder securityContextHolder;

    User user = new User();
    final Integer userId = 10;
    final String password = "jdfuygaihkjbhhu";
    final String username = "Martens";

    @BeforeEach
    void setUp() {
        user.setUsername(username);
        user.setId(userId);
        user.setPassword(password);
        user.setRestaurant(new Restaurant());
    }

    @Test
    public final void testGetUser() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
        given(userService.getUserById(any(Integer.class))).willReturn(user);
        UserGetResponse userGetResponse = userController.getUserById(10);
        assertNotNull(userGetResponse);
        assertEquals(userId, userGetResponse.getUserId());
        assertEquals(user.getUsername(), userGetResponse.getUsername());
        assertEquals(user.getRestaurant(), userGetResponse.getRestaurant());
    }

    @Test
    public final void testGetUser_AccessDeniedException() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("123", "123"));
        given(userService.getUserById(any(Integer.class))).willReturn(user);
        assertThrows(AccessDeniedException.class,
                () -> {
                    userController.getUserById(10);
                }
        );
    }



}
