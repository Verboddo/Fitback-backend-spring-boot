package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
       if(optionalUser.isPresent()) {
           User user = optionalUser.get();

           return user;
        } else {
            throw new UserNotFoundException(username);
        }
    }

    public void deleteUserById(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    public Set<Feedback> getUserFeedback(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getFeedbacks();
    }

    public UserProfile getUserUserProfile(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getUserProfile();
    }

    public FileDB getUserFiles(Long id) {
        Optional<User> user = userRepository.findById(id);
        return (FileDB) user.get().getFileDB();
    }

}
