package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserProfileRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public UserProfile saveUserProfile(UserProfile userProfile, Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userRepository.findById(id).get();

            userProfile.setUser(user);

            return userProfileRepository.save(userProfile);
        } else {
            throw new UserNotFoundException();
        }

    }

    public void updateUserProfile(Long id, UserProfile newUserProfile) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileRepository.findById(id).get();
            userProfile.setFirstName(newUserProfile.getFirstName());
            userProfile.setLastName(newUserProfile.getLastName());
            userProfile.setAddress(newUserProfile.getAddress());
            userProfile.setZipcode(newUserProfile.getZipcode());
            userProfile.setCountry(newUserProfile.getCountry());
            userProfile.setAge(newUserProfile.getAge());
            userProfile.setHeight(newUserProfile.getHeight());
            userProfile.setWeight(newUserProfile.getWeight());
            userProfileRepository.save(userProfile);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
