package org.xhite.myblog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xhite.myblog.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(appUser -> org.springframework.security.core.userdetails.User
                        .withUsername(appUser.getUsername())
                        .password(appUser.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}