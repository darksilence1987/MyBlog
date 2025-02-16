package org.xhite.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhite.myblog.model.AppUser;
import org.xhite.myblog.repository.UserRepository;
import org.xhite.myblog.security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> registrationData) {
        String username = registrationData.get("username");
        String password = registrationData.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }

        AppUser newAppUser = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(newAppUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}