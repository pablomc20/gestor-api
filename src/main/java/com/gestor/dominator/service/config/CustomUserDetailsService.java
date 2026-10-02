package com.gestor.dominator.service.config;

import com.gestor.dominator.exceptions.custom.AuthenticationException;
import com.gestor.dominator.model.postgre.auth.User;
import com.gestor.dominator.repository.UserRepository;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "users", key = "#username.toLowerCase().trim()")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhone(username)
                .orElseThrow(() -> AuthenticationException.userNotFound(username));

        return user;
    }
}