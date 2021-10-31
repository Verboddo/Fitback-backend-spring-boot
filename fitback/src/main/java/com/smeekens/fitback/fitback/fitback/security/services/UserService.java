package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.BadRequestException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
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
            throw new BadRequestException("Did not find user");
        }
    }

    public void deleteUserById(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new BadRequestException("User not found");
        }
    }

}
