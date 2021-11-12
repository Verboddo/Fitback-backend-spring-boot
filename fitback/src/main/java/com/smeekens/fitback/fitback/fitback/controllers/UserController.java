package com.smeekens.fitback.fitback.fitback.controllers;

import com.smeekens.fitback.fitback.fitback.exceptions.NotAuthorizedException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import com.smeekens.fitback.fitback.fitback.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users, only for admin
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    // Get user by username, only for logged-in user or admin
    @GetMapping(value = "/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.equals(username, principal.getName())) {
            return ResponseEntity.ok().body(userService.getUser(username));
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.ok().body(userService.getUser(username));
        } else {
            throw new NotAuthorizedException();
        }
    }

    // Delete user by id, only admin can do this
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/userprofile")
    public ResponseEntity<Object> getUserUserProfile(@PathVariable("id") Long id) {
        UserProfile userProfiles = userService.getUserUserProfile(id);
        return ResponseEntity.ok(userProfiles);
    }

}
