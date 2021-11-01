package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final FileDBRepository fileDBRepository;

    @Autowired
    public UserService(UserRepository userRepository, FileDBRepository fileDBRepository) {
        this.userRepository = userRepository;
        this.fileDBRepository = fileDBRepository;
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String username) {
        if(userRepository.existsByUsername(username)) {
            return userRepository.findByUsername(username);
        } else {
            throw new UserNotFoundException(username);
        }
    }

    public void updateUser(String username, User newUser) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(newUser.getFullName());
            user.setAddress(newUser.getAddress());
            user.setZipcode(newUser.getZipcode());
            user.setCountry(newUser.getCountry());
            user.setAge(newUser.getAge());
            user.setHeight(newUser.getHeight());
            user.setWeight(newUser.getWeight());
            userRepository.save(user);
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

}
