package com.gestor.dominator.service.config;

import com.gestor.dominator.exceptions.custom.AuthenticationException;
import com.gestor.dominator.model.postgre.auth.User;
import com.gestor.dominator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithDetail(username)
                .orElseThrow(() -> AuthenticationException.userNotFound(username));

        return user;
    }
}