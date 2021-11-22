package com.smeekens.fitback.fitback;

import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserProfileRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.UserProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {FitbackApplication.class})
public class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    UserProfile userProfile;

    @Mock
    User user;

    @Test
    public void saveUserProfile() {
        user = new User();
        user.setId(1L);

        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setUser(user);

        Mockito
                .when(userRepository.findById(1L))
                        .thenReturn(Optional.of(user));

        Mockito
                .when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));

        userProfileService.saveUserProfile(userProfile, user.getId());

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    public void saveUserProfileException() {
        assertThrows(UserNotFoundException.class, () -> userProfileService.saveUserProfile(null, null));
    }

    @Test
    public void updateUserProfile() {
        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("test");
        userProfile.setLastName("test");
        userProfile.setCountry("test country");
        userProfile.setAddress("test address");
        userProfile.setZipcode("4444TE");
        userProfile.setAge(28);
        userProfile.setWeight(80);
        userProfile.setHeight(180);

        Mockito
                .when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));

        userProfile.setFirstName("new test");
        userProfile.setLastName("new test");
        userProfile.setCountry("new test country");
        userProfile.setAddress("new test address");
        userProfile.setZipcode("4444TN");
        userProfile.setAge(18);
        userProfile.setWeight(70);
        userProfile.setHeight(150);

        userProfileService.updateUserProfile(1L, userProfile);

        verify(userProfileRepository).save(userProfile);
        assertThat(userProfile.getFirstName()).isEqualTo("new test");
        assertThat(userProfile.getLastName()).isEqualTo("new test");
        assertThat(userProfile.getCountry()).isEqualTo("new test country");
        assertThat(userProfile.getAddress()).isEqualTo("new test address");
        assertThat(userProfile.getZipcode()).isEqualTo("4444TN");
        assertThat(userProfile.getAge()).isEqualTo(18);
        assertThat(userProfile.getWeight()).isEqualTo(70);
        assertThat(userProfile.getHeight()).isEqualTo(150);
    }

}
