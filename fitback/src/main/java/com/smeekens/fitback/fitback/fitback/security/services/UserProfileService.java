package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserProfileRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;


    public UserProfile saveUserProfile(UserProfile userProfile, Long id) {
        User user = userRepository.findById(id).get();

        userProfile.setUser(user);

        return userProfileRepository.save(userProfile);
    }

    public void updateUserProfile(Long id, UserProfile newUserProfile) {
        if (!userProfileRepository.existsById(id)) throw new RecordNotFoundException();
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
    }
}
