package com.smeekens.fitback.fitback.fitback.controllers;

import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/user-profile")
public class UserProfileController {

    private UserProfileService userProfileService;
    private UserRepository userRepository;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, UserRepository userRepository) {
        this.userProfileService = userProfileService;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity<Object> saveUserProfile(@RequestBody UserProfile userProfile, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        UserProfile userProfile1 = userProfileService.saveUserProfile(userProfile, user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userProfile1).toUri();
        return ResponseEntity.created(location).build();
    }
}
