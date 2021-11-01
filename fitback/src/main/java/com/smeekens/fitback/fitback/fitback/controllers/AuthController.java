package com.smeekens.fitback.fitback.fitback.controllers;
import com.smeekens.fitback.fitback.fitback.payload.request.LoginRequest;
import com.smeekens.fitback.fitback.fitback.payload.request.SignupRequest;
import com.smeekens.fitback.fitback.fitback.payload.response.JwtResponse;
import com.smeekens.fitback.fitback.fitback.security.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthorizationService authorizationService;

    @Autowired
    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authorizationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return authorizationService.registerUser(signUpRequest);
    }
}
