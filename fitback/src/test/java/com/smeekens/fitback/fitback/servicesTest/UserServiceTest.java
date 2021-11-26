package com.smeekens.fitback.fitback.servicesTest;

import com.smeekens.fitback.fitback.FitbackApplication;
import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {FitbackApplication.class})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    User user;

    @Test
    public void getAllUsersTest() {
        List<User> testUserList = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        user1.setUsername("test1");
        user2.setUsername("test1");
        user3.setUsername("test1");

        testUserList.add(user1);
        testUserList.add(user2);
        testUserList.add(user3);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(testUserList);

        userService.getAllUsers();

        verify(userRepository, times(1)).findAll();

        assertThat(testUserList.size()).isEqualTo(3);
        assertThat(testUserList.get(0)).isEqualTo(user1);
        assertThat(testUserList.get(1)).isEqualTo(user2);
        assertThat(testUserList.get(2)).isEqualTo(user3);
    }

    @Test
    public void getUserByUsernameTest() {
        // Arrange
        User user = new User();
        user.setUsername("ramon");

        //Act
        Mockito
                .when(userRepository.findByUsername("ramon"))
                .thenReturn(Optional.of(user));

        String username = "ramon";
        String expected = "ramon";

        User found = userService.getUser(username);

        //Assert
        verify(userRepository).findByUsername(expected);
        assertEquals(expected, found.getUsername());
    }

    @Test
    public void getUserExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser(null));
    }

    @Test
    public void deleteUserTest() {
        user = new User();
        user.setId(1L);

        Mockito
                .when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void getUserUserProfileTest() {
        user = new User();
        user.setId(1L);

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("test");
        userProfile.setLastName("test");
        userProfile.setCountry("test country");
        userProfile.setAddress("test address");
        userProfile.setZipcode("4444TE");
        userProfile.setAge(28);
        userProfile.setWeight(80);
        userProfile.setHeight(180);

        user.setUserProfile(userProfile);

        Mockito
                .when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.getUserUserProfile(1L);

        assertThat(user.getUserProfile()).isEqualTo(userProfile);
        assertThat(user.getUserProfile().getFirstName()).isEqualTo("test");
        assertThat(user.getUserProfile().getLastName()).isEqualTo("test");
        assertThat(user.getUserProfile().getCountry()).isEqualTo("test country");
        assertThat(user.getUserProfile().getAddress()).isEqualTo("test address");
        assertThat(user.getUserProfile().getZipcode()).isEqualTo("4444TE");
        assertThat(user.getUserProfile().getAge()).isEqualTo(28);
        assertThat(user.getUserProfile().getWeight()).isEqualTo(80);
        assertThat(user.getUserProfile().getHeight()).isEqualTo(180);
    }

    @Test
    public void getUserUserProfileExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserUserProfile(null));
    }

}
