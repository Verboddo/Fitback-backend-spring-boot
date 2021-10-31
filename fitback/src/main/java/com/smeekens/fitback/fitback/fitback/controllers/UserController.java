package com.smeekens.fitback.fitback.fitback.controllers;

import com.smeekens.fitback.fitback.fitback.exceptions.NotAuthorizedException;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // end points nummers
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

    @PostMapping(value = "/update-information/{username}")
    public ResponseEntity<Object> updateUserInformation(@PathVariable("username") String username, @RequestBody User user, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.equals(username, principal.getName())) {
            userService.updateUser(username, user);
            return ResponseEntity.ok().build();
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            userService.updateUser(username, user);
            return ResponseEntity.ok().build();
        } else {
            throw new NotAuthorizedException();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
